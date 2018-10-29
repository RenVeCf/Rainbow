package com.ipd.taxiu.ui.fragment.store

import android.graphics.Rect
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.ipd.jumpbox.jumpboxlibrary.utils.DensityUtil
import com.ipd.taxiu.R
import com.ipd.taxiu.adapter.ProductEvaluateAdapter
import com.ipd.taxiu.bean.BaseResult
import com.ipd.taxiu.bean.ProductEvaluateBean
import com.ipd.taxiu.bean.ProductEvaluateLableBean
import com.ipd.taxiu.platform.global.Constant
import com.ipd.taxiu.platform.global.GlobalParam
import com.ipd.taxiu.platform.http.ApiManager
import com.ipd.taxiu.ui.ListFragment
import com.ipd.taxiu.widget.ProductEvaluateView
import com.ipd.taxiu.widget.ProgressLayout
import kotlinx.android.synthetic.main.fragment_product_evaluate_list.view.*
import rx.Observable

class ProductEvaluateFragment : ListFragment<BaseResult<List<ProductEvaluateBean>>, ProductEvaluateBean>() {
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


    private val mScreenList = listOf(
            ProductEvaluateLableBean(0, "全部", ""),
            ProductEvaluateLableBean(1, "有图", ""),
            ProductEvaluateLableBean(2, "好评", ""),
            ProductEvaluateLableBean(3, "中评", ""),
            ProductEvaluateLableBean(4, "差评", "")
    )

    override fun getProgressLayout(): ProgressLayout {
        return mContentView.evaluate_progress_layout
    }

    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        getProgressLayout().setEmptyViewRes(R.layout.layout_empty_evaluate)

        mScreenList.forEach {
            mContentView.product_evaluate_view.addView(it)
        }
    }

    override fun initListener() {
        super.initListener()
        mContentView.product_evaluate_view.setOnCheckedChangeListener(object : ProductEvaluateView.OnCheckedChangeListener {
            override fun onChange(modelInfo: ProductEvaluateLableBean) {
                onRefresh(true)
            }

        })
    }

    override fun loadListData(): Observable<BaseResult<List<ProductEvaluateBean>>> {
        return ApiManager.getService().storeProductEvaluateList(GlobalParam.getUserId(), mProductId, mFormId, mScreenList[mContentView.product_evaluate_view.getCheckedPos()].type, page, Constant.PAGE_SIZE)
    }

    override fun isNoMoreData(result: BaseResult<List<ProductEvaluateBean>>): Int {
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

    override fun addData(isRefresh: Boolean, result: BaseResult<List<ProductEvaluateBean>>) {
        data?.addAll(result?.data ?: arrayListOf())
    }

}