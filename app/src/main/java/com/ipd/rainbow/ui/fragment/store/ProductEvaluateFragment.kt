package com.ipd.rainbow.ui.fragment.store

import android.graphics.Rect
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import com.ipd.jumpbox.jumpboxlibrary.utils.DensityUtil
import com.ipd.rainbow.R
import com.ipd.rainbow.adapter.ProductEvaluateAdapter
import com.ipd.rainbow.bean.EvaluateResult
import com.ipd.rainbow.bean.ProductEvaluateBean
import com.ipd.rainbow.platform.global.Constant
import com.ipd.rainbow.platform.global.GlobalParam
import com.ipd.rainbow.platform.http.ApiManager
import com.ipd.rainbow.ui.ListFragment
import com.ipd.rainbow.ui.activity.store.ProductEvaluateActivity
import com.ipd.rainbow.widget.ProgressLayout
import kotlinx.android.synthetic.main.fragment_product_evaluate_list.view.*
import kotlinx.android.synthetic.main.item_product_evaluate_header.view.*
import rx.Observable

class ProductEvaluateFragment : ListFragment<EvaluateResult<List<ProductEvaluateBean>>, ProductEvaluateBean>() {
    companion object {
        fun newInstance(productId: Int, formId: Int): ProductEvaluateFragment {
            val topicListFragment = ProductEvaluateFragment()
            val bundle = Bundle()
            bundle.putInt("productId", productId)
            bundle.putInt("formId", formId)
            topicListFragment.arguments = bundle
            return topicListFragment
        }
    }

    override fun getContentLayout(): Int = R.layout.fragment_product_evaluate_list

    private val mProductId by lazy { arguments.getInt("productId") }
    private val mFormId by lazy { arguments.getInt("formId") }


    override fun getProgressLayout(): ProgressLayout {
        return mContentView.evaluate_progress_layout
    }

    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        getProgressLayout().setEmptyViewRes(R.layout.layout_empty_evaluate)
    }


    override fun loadListData(): Observable<EvaluateResult<List<ProductEvaluateBean>>> {
        return ApiManager.getService().storeProductEvaluateList(GlobalParam.getUserId(), mProductId, mFormId, 0, page, Constant.PAGE_SIZE)
    }

    override fun isNoMoreData(result: EvaluateResult<List<ProductEvaluateBean>>): Int {
        if (page == INIT_PAGE && (result.data == null || result.data.isEmpty())) {
            return EMPTY_DATA
        } else if (result.data == null || result.data.isEmpty()) {
            return NO_MORE_DATA
        }
        return NORMAL
    }

    private var mAdapter: ProductEvaluateAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = ProductEvaluateAdapter(mActivity, data) {
                //itemClick
                ProductEvaluateActivity.launch(mActivity, it.ASSESS_ID)
            }
            recycler_view.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            recycler_view.addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(outRect: Rect, view: View?, parent: RecyclerView, state: RecyclerView.State?) {
                    val position = parent.getChildAdapterPosition(view)
                    outRect.top = DensityUtil.dip2px(mActivity, 4f)
                    outRect.bottom = DensityUtil.dip2px(mActivity, 4f)
                    if (position % 2 == 0) {
                        outRect.left = DensityUtil.dip2px(mActivity, 8f)
                        outRect.right = DensityUtil.dip2px(mActivity, 4f)
                    } else {
                        outRect.left = DensityUtil.dip2px(mActivity, 4f)
                        outRect.right = DensityUtil.dip2px(mActivity, 8f)
                    }
                }
            })

            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: EvaluateResult<List<ProductEvaluateBean>>) {
        if (isRefresh) {
            mContentView.tv_evaluate_num.text = "商品评价（${result.total}）"
        }
        data?.addAll(result?.data ?: arrayListOf())
    }

}