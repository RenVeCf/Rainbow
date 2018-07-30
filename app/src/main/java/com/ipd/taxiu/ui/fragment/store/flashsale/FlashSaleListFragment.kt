package com.ipd.taxiu.ui.fragment.store.flashsale

import android.support.v7.widget.LinearLayoutManager
import com.ipd.taxiu.adapter.FlashSaleAdapter
import com.ipd.taxiu.bean.FlashSaleProductBean
import com.ipd.taxiu.bean.ProductBean
import com.ipd.taxiu.ui.ListFragment
import com.ipd.taxiu.ui.activity.store.ProductDetailActivity
import rx.Observable

class FlashSaleListFragment : ListFragment<FlashSaleProductBean, ProductBean>() {

    companion object {
        fun newInstance(): FlashSaleListFragment {
            val fragment = FlashSaleListFragment()
            return fragment
        }
    }

    private var mType = 0
    override fun loadListData(): Observable<FlashSaleProductBean> {
        return Observable.create<FlashSaleProductBean> {
            val flashSaleProductBean = FlashSaleProductBean()
            flashSaleProductBean.cheapestProduct = ProductBean()
            flashSaleProductBean.productList = ArrayList()
            for (index: Int in 0 until 10) {
                flashSaleProductBean.isStart = mType == 0
                flashSaleProductBean.productList.add(ProductBean())
            }
            it.onNext(flashSaleProductBean)
            it.onCompleted()
        }
    }

    override fun isNoMoreData(result: FlashSaleProductBean): Int {
        return NORMAL
    }

    private var mAdapter: FlashSaleAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = FlashSaleAdapter(mActivity, data, {
                //商品详情
                ProductDetailActivity.launch(mActivity)
            }, {
                //tab切换
                mType = it
                isCreate = true
                onRefresh()
            })
            recycler_view.layoutManager = LinearLayoutManager(mActivity)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: FlashSaleProductBean) {
        if (isRefresh) {
            data?.add(result.cheapestProduct)
        }
        data?.addAll(result.productList)
    }


}