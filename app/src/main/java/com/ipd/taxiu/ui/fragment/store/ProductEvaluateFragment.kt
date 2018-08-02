package com.ipd.taxiu.ui.fragment.store

import android.graphics.Rect
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.ipd.jumpbox.jumpboxlibrary.utils.DensityUtil
import com.ipd.taxiu.R
import com.ipd.taxiu.adapter.ProductEvaluateAdapter
import com.ipd.taxiu.bean.ProductEvaluateBean
import com.ipd.taxiu.ui.ListFragment
import rx.Observable

class ProductEvaluateFragment : ListFragment<List<ProductEvaluateBean>, ProductEvaluateBean>() {
    companion object {
        fun newInstance(productId: String): ProductEvaluateFragment {
            val topicListFragment = ProductEvaluateFragment()
            val bundle = Bundle()
            bundle.putString("productId", productId)
            topicListFragment.arguments = bundle
            return topicListFragment
        }
    }

    override fun getContentLayout(): Int = R.layout.fragment_product_evaluate_list

    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        progress_layout.setEmptyViewRes(R.layout.layout_empty_evaluate)
    }

    override fun loadListData(): Observable<List<ProductEvaluateBean>> {
        return Observable.create<List<ProductEvaluateBean>> {
            val list = ArrayList<ProductEvaluateBean>()
            for (i: Int in 0 until 10) {
                list.add(ProductEvaluateBean())
            }
            it.onNext(list)
            it.onCompleted()
        }
    }

    override fun isNoMoreData(result: List<ProductEvaluateBean>): Int {
        return NORMAL
    }

    private var mAdapter: ProductEvaluateAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = ProductEvaluateAdapter(mActivity, data, {
                //itemClick

            })
            recycler_view.addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
                    outRect?.bottom = DensityUtil.dip2px(mActivity, 8f)
                }
            })
            recycler_view.layoutManager = LinearLayoutManager(mActivity)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: List<ProductEvaluateBean>) {
        data?.addAll(result)
    }

}