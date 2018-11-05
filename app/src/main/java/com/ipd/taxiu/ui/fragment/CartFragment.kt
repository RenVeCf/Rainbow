package com.ipd.taxiu.ui.fragment

import android.text.TextUtils
import com.ipd.taxiu.R
import com.ipd.taxiu.adapter.CartAdapter
import com.ipd.taxiu.bean.*
import com.ipd.taxiu.event.UpdateCartEvent
import com.ipd.taxiu.platform.global.Constant
import com.ipd.taxiu.platform.global.GlobalParam
import com.ipd.taxiu.platform.http.ApiManager
import com.ipd.taxiu.platform.http.Response
import com.ipd.taxiu.platform.http.RxScheduler
import com.ipd.taxiu.presenter.store.CartPresenter
import com.ipd.taxiu.ui.ListFragment
import com.ipd.taxiu.ui.activity.store.ProductDetailActivity
import com.ipd.taxiu.ui.activity.trade.ConfirmOrderActivity
import com.ipd.taxiu.utils.CartCallback
import com.ipd.taxiu.widget.MessageDialog
import kotlinx.android.synthetic.main.base_toolbar.view.*
import kotlinx.android.synthetic.main.fragment_cart.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import rx.Observable

class CartFragment : ListFragment<BaseResult<List<ProductBean>>, Any>(), CartCallback, CartPresenter.ICartView {

    override fun getTitleLayout(): Int = R.layout.base_toolbar

    override fun getContentLayout(): Int = R.layout.fragment_cart


    private var mPresenter: CartPresenter? = null
    override fun onViewAttach() {
        super.onViewAttach()
        EventBus.getDefault().register(this)
        mPresenter = CartPresenter()
        mPresenter?.attachView(mActivity, this)
    }

    override fun onViewDetach() {
        super.onViewDetach()
        EventBus.getDefault().unregister(this)
        mPresenter?.detachView()
        mPresenter = null
    }

    override fun initTitle() {
        super.initTitle()
        mHeaderView.tv_title.text = "购物车"
    }

    override fun initListener() {
        super.initListener()
        mContentView.tv_confirm.setOnClickListener {
            //结算
            var cartIds = ""
            mCartList?.forEach {
                if (it.isChecked) {
                    cartIds += "${it.CART_ID},"
                }
            }
            if (TextUtils.isEmpty(cartIds)) {
                toastShow("请选择要结算的商品")
                return@setOnClickListener
            }
            cartIds = cartIds.substring(0, cartIds.length - 1)

            ConfirmOrderActivity.launch(mActivity, cartIds)
        }

        mContentView.ll_all_check.setOnClickListener {
            //全选、反选
            if (mCartList == null || mCartList!!.isEmpty()) {
                toastShow("购物车空空如也~~")
                return@setOnClickListener
            }
            val check = !mContentView.cb_all_check.isChecked
            mContentView.cb_all_check.isChecked = check
            mCartList?.forEach {
                it.isChecked = check
            }
            mAdapter?.notifyDataSetChanged()
            onCartProductCheckChange()
        }
    }

    private var mCartList: ArrayList<CartProductBean>? = null
    override fun getListData(isRefresh: Boolean) {
        if (isRefresh) {
            checkNeedShowProgress()
            ApiManager.getService().cartList(GlobalParam.getUserIdOrJump())
                    .compose(RxScheduler.applyScheduler())
                    .subscribe(object : Response<BaseResult<List<CartProductBean>>>() {
                        override fun _onNext(result: BaseResult<List<CartProductBean>>) {
                            if (result.code == 0 || result.code == 10000) {
                                mCartList = ArrayList(result?.data ?: arrayListOf())
                                getParentListData(isRefresh)
                            } else {
                                showError(result.msg)
                            }

                        }

                        override fun onError(e: Throwable?) {
                            showError("连接服务器失败")
                        }

                    })
        } else {
            super.getListData(isRefresh)
        }
    }

    private fun getParentListData(isRefresh: Boolean) {
        super.getListData(isRefresh)
    }


    override fun loadListData(): Observable<BaseResult<List<ProductBean>>> {
        return ApiManager.getService().cartRecommend(Constant.PAGE_SIZE, GlobalParam.getUserIdOrJump(), page)
    }

    override fun isNoMoreData(result: BaseResult<List<ProductBean>>): Int {
        if (page > INIT_PAGE && (result.data == null || result.data.isEmpty())) {
            return NO_MORE_DATA
        }
        return NORMAL
    }

    private var mAdapter: CartAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = CartAdapter(mActivity, data, this)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: BaseResult<List<ProductBean>>) {
        if (isRefresh) {
            onCartProductCheckChange()
            if (mCartList == null || mCartList!!.isEmpty()) {
                data?.add(0, EmptyCartProductBean())//空购物车
            } else {
                data?.addAll(0, mCartList!!)
            }
            data?.add(RecommendProductHeaderBean())//为您推荐
        }
        data?.addAll(result?.data ?: arrayListOf<ProductBean>())
    }

    override fun onRecommendProductItemClick(productBean: ProductBean) {
        ProductDetailActivity.launch(mActivity, productBean.PRODUCT_ID, productBean.FORM_ID)
    }

    override fun onDelete(pos: Int, cartProductBean: CartProductBean) {
        MessageDialog.Builder(mActivity)
                .setTitle("提示")
                .setMessage("确定删除该商品吗?")
                .setCommit("确定", {
                    it.dismiss()
                    mPresenter?.deleteCartProduct(pos, cartProductBean)
                })
                .setCancel("再想想", {
                    it.dismiss()
                }).show()
    }

    override fun onCartProductNumChange(cartId: Int, num: Int, callback: (isSuccess: Boolean) -> Unit) {
        mPresenter?.changeCartNum(cartId, num, callback)
    }

    override fun onCartProductCheckChange() {
        if (mCartList == null || mCartList!!.isEmpty()) {
            mContentView.tv_cart_total_price.text = "0.0"
            mContentView.tv_confirm.text = "结算(0)"
            return
        }
        var checkedNum = 0
        var totalPrice = 0.0
        var isAllChecked = true
        mCartList?.forEach {
            if (it.isChecked) {
                checkedNum += it.NUM
                totalPrice += it.SUB_TOTAL.toDouble()
            } else {
                isAllChecked = false
            }
        }
        mContentView.tv_cart_total_price.text = totalPrice.toString()
        mContentView.tv_confirm.text = "结算($checkedNum)"
        mContentView.cb_all_check.isChecked = isAllChecked
    }

    override fun cartDeleteSuccess(pos: Int, cartProductBean: CartProductBean) {
        mCartList?.remove(cartProductBean)
        data?.removeAt(pos)
//        if (data?.isEmpty() == true || data?.get(0) is RecommendProductHeaderBean) {
//            data?.add(0, EmptyCartProductBean())
//            setOrNotifyAdapter()
//        } else {
//            mAdapter?.notifyItemRemoved(pos)
//        }

        if (data?.isEmpty() == true || data?.get(0) is RecommendProductHeaderBean) {
            data?.add(0, EmptyCartProductBean())
        }
        setOrNotifyAdapter()
        onCartProductCheckChange()
    }

    override fun cartDeleteFail(errMsg: String) {
        toastShow(errMsg)
    }

    @Subscribe
    fun onMainEvent(event: UpdateCartEvent) {
        isCreate = true
        onRefresh()
    }
}