package com.ipd.taxiu.ui.fragment.store

import android.support.v7.widget.LinearLayoutManager
import com.ipd.taxiu.adapter.ClearanceProductAdapter
import com.ipd.taxiu.bean.ProductBean
import com.ipd.taxiu.ui.ListFragment
import com.ipd.taxiu.ui.activity.store.ProductDetailActivity
import rx.Observable

class ClearanceProductFragment : ListFragment<List<ProductBean>, ProductBean>() {

    companion object {
        fun newInstance(): ClearanceProductFragment {
            val fragment = ClearanceProductFragment()
            return fragment
        }
    }

    override fun loadListData(): Observable<List<ProductBean>> {
        return Observable.create<List<ProductBean>> {
            val productList = ArrayList<ProductBean>()
            for (index: Int in 0 until 10) {
                productList.add(ProductBean())
            }
            it.onNext(productList)
            it.onCompleted()
        }
    }

    override fun isNoMoreData(result: List<ProductBean>): Int {
        return NORMAL
    }

    private var mAdapter: ClearanceProductAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = ClearanceProductAdapter(mActivity, data, {
                //商品详情
                ProductDetailActivity.launch(mActivity)
            })
            recycler_view.layoutManager = LinearLayoutManager(mActivity)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: List<ProductBean>) {
        data?.addAll(result)
    }


}