package com.ipd.taxiu.ui.fragment.store

import android.support.v7.widget.LinearLayoutManager
import com.ipd.taxiu.adapter.ProductAdapter
import com.ipd.taxiu.bean.ProductBean
import com.ipd.taxiu.ui.ListFragment
import com.ipd.taxiu.ui.activity.store.ProductDetailActivity
import rx.Observable

class NewProductListFragment : ListFragment<List<ProductBean>, ProductBean>() {

    companion object {
        fun newInstance(): NewProductListFragment {
            val fragment = NewProductListFragment()
            return fragment
        }
    }


    override fun loadListData(): Observable<List<ProductBean>> {
        return Observable.create<List<ProductBean>> {
            val productList = ArrayList<ProductBean>()
            for (index: Int in 0 until 10) {
                val productBean = ProductBean()
                productBean.isNew = true
                productList.add(productBean)
            }
            it.onNext(productList)
            it.onCompleted()
        }
    }

    override fun isNoMoreData(result: List<ProductBean>): Int {
        return NORMAL
    }

    private var mAdapter: ProductAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = ProductAdapter(mActivity, data, {
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