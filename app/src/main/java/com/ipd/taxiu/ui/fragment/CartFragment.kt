package com.ipd.taxiu.ui.fragment

import com.ipd.taxiu.R
import com.ipd.taxiu.adapter.CartAdapter
import com.ipd.taxiu.bean.*
import com.ipd.taxiu.ui.ListFragment
import com.ipd.taxiu.ui.activity.trade.ConfirmOrderActivity
import com.ipd.taxiu.utils.CartCallback
import com.ipd.taxiu.widget.MessageDialog
import kotlinx.android.synthetic.main.base_toolbar.view.*
import kotlinx.android.synthetic.main.fragment_cart.view.*
import rx.Observable
import java.util.*

class CartFragment : ListFragment<ShopCartBean, Any>(), CartCallback {

    override fun getTitleLayout(): Int = R.layout.base_toolbar

    override fun getContentLayout(): Int = R.layout.fragment_cart

    override fun initTitle() {
        super.initTitle()
        mHeaderView.tv_title.text = "购物车"
    }

    override fun initListener() {
        super.initListener()
        mContentView.tv_confirm.setOnClickListener {
            //结算
            ConfirmOrderActivity.launch(mActivity)
        }
    }

    override fun loadListData(): Observable<ShopCartBean> {
        return Observable.create<ShopCartBean> {
            val shopCartBean = ShopCartBean()
            shopCartBean.cartProductList = ArrayList()

            val cartNum = Random().nextInt(10)
            for (index: Int in 0 until cartNum) {
                shopCartBean.cartProductList.add(CartProductBean())
            }
            shopCartBean.productList = ArrayList()
            for (index: Int in 0 until 10) {
                shopCartBean.productList.add(ProductBean())
            }
            it.onNext(shopCartBean)
            it.onCompleted()
        }
    }

    override fun isNoMoreData(result: ShopCartBean): Int {
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

    override fun addData(isRefresh: Boolean, result: ShopCartBean) {
        if (isRefresh) {
            if (result.cartProductList == null || result.cartProductList.isEmpty()) {
                data?.add(0, EmptyCartProductBean())//空购物车
            } else {
                data?.addAll(result.cartProductList)
            }
            data?.add(RecommendProductHeaderBean())//为您推荐
        }
        data?.addAll(result.productList)//产品
    }

    override fun onRecommendProductItemClick(productBean: ProductBean) {

    }

    override fun onDelete(pos: Int, cartProductBean: CartProductBean) {
        MessageDialog.Builder(mActivity)
                .setTitle("提示")
                .setMessage("确定删除该商品吗?")
                .setCommit("确定", {
                    it.dismiss()
                })
                .setCancel("再想想", {
                    it.dismiss()
                }).show()
    }
}