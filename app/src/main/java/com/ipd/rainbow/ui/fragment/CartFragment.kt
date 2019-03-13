package com.ipd.rainbow.ui.fragment

import android.os.Bundle
import android.text.TextUtils
import com.ipd.jumpbox.jumpboxlibrary.utils.CommonUtils
import com.ipd.rainbow.MainActivity
import com.ipd.rainbow.R
import com.ipd.rainbow.adapter.CartAdapter
import com.ipd.rainbow.bean.BaseResult
import com.ipd.rainbow.bean.CartProductBean
import com.ipd.rainbow.event.UpdateCartEvent
import com.ipd.rainbow.platform.global.GlobalParam
import com.ipd.rainbow.platform.http.ApiManager
import com.ipd.rainbow.presenter.store.CartPresenter
import com.ipd.rainbow.ui.ListFragment
import com.ipd.rainbow.ui.activity.trade.ConfirmOrderActivity
import com.ipd.rainbow.utils.CartCallback
import com.ipd.rainbow.utils.StringUtils
import com.ipd.rainbow.widget.MessageDialog
import kotlinx.android.synthetic.main.base_toolbar.view.*
import kotlinx.android.synthetic.main.fragment_cart.view.*
import kotlinx.android.synthetic.main.layout_empty_cart.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import rx.Observable

class CartFragment : ListFragment<BaseResult<List<CartProductBean>>, CartProductBean>(), CartCallback, CartPresenter.ICartView {

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

    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        val emptyView = layoutInflater.inflate(R.layout.layout_empty_cart, getProgressLayout(), false)
        emptyView.tv_go_shopping.setOnClickListener {
            //商城
            if (mActivity is MainActivity) {
                (mActivity as MainActivity).switchToStore()
            }
        }
        getProgressLayout().setEmptyViewRes(emptyView)
        setLoadMoreEnable(false)
    }

    override fun initListener() {
        super.initListener()
        mContentView.tv_confirm.setOnClickListener {
            //结算
            var cartIds = ""
            data?.forEach {
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
            if (data == null || data!!.isEmpty()) {
                toastShow("购物车空空如也~~")
                return@setOnClickListener
            }
            val check = !mContentView.cb_all_check.isChecked
            mContentView.cb_all_check.isChecked = check
            data?.forEach {
                it.isChecked = check
            }
            mAdapter?.notifyDataSetChanged()
            onCartProductCheckChange()
        }
    }


    override fun loadListData(): Observable<BaseResult<List<CartProductBean>>> {
        return ApiManager.getService().cartList(GlobalParam.getUserIdOrJump())
    }

    override fun isNoMoreData(result: BaseResult<List<CartProductBean>>): Int {
        if (page == INIT_PAGE && (result.data == null || result.data.isEmpty())) {
            return EMPTY_DATA
        } else if (result.data == null || result.data.isEmpty()) {
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

    override fun addData(isRefresh: Boolean, result: BaseResult<List<CartProductBean>>) {
        if (isRefresh) {
            onCartProductCheckChange()
        }
        data?.addAll(0, result?.data ?: arrayListOf())
    }


    override fun onDelete(pos: Int, cartProductBean: CartProductBean) {
        MessageDialog.Builder(mActivity)
                .setTitle("提示")
                .setMessage("确定删除该商品吗?")
                .setCommit("确定") {
                    it.dismiss()
                    mPresenter?.deleteCartProduct(pos, cartProductBean)
                }
                .setCancel("再想想") {
                    it.dismiss()
                }.show()
    }

    override fun onCartProductNumChange(cartId: Int, num: Int, callback: (isSuccess: Boolean) -> Unit) {
        mPresenter?.changeCartNum(cartId, num, callback)
    }

    override fun onCartProductCheckChange() {
        if (data == null || data!!.isEmpty()) {
            mContentView.tv_cart_total_price.text = "0"
            mContentView.tv_confirm.text = "结算(0)"
            return
        }
        var checkedNum = 0
        var totalPrice = 0.0
        var isAllChecked = true
        data?.forEach {
            if (it.isChecked) {
//                checkedNum += it.NUM
                checkedNum += 1
                totalPrice += it.SUB_TOTAL.toDouble()
            } else {
                isAllChecked = false
            }
        }
        mContentView.tv_cart_total_price.text = StringUtils.formatPrice(CommonUtils.beautifulDouble(totalPrice))
        mContentView.tv_confirm.text = "结算($checkedNum)"
        mContentView.cb_all_check.isChecked = isAllChecked
    }

    override fun cartDeleteSuccess(pos: Int, cartProductBean: CartProductBean) {
        data?.removeAt(pos)

        if (data?.isEmpty() == true) {
            getProgressLayout().showEmpty()
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