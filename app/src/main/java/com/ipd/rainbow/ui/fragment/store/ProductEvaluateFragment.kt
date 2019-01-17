package com.ipd.rainbow.ui.fragment.store

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.ipd.rainbow.R
import com.ipd.rainbow.adapter.ProductEvaluateAdapter
import com.ipd.rainbow.bean.*
import com.ipd.rainbow.platform.global.Constant
import com.ipd.rainbow.platform.global.GlobalParam
import com.ipd.rainbow.platform.http.ApiManager
import com.ipd.rainbow.platform.http.Response
import com.ipd.rainbow.platform.http.RxScheduler
import com.ipd.rainbow.ui.ListFragment
import com.ipd.rainbow.widget.ProductEvaluateView
import com.ipd.rainbow.widget.ProgressLayout
import kotlinx.android.synthetic.main.fragment_product_evaluate_list.view.*
import rx.Observable

class ProductEvaluateFragment : ListFragment<EvaluateResult<List<ProductEvaluateBean>>, Any>() {
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

    override fun initListener() {
        super.initListener()
        mContentView.product_evaluate_view.setOnCheckedChangeListener(object : ProductEvaluateView.OnCheckedChangeListener {
            override fun onChange(modelInfo: ProductEvaluateLableBean) {
                onRefresh(true)
            }

        })
    }


    private var mScreenList: List<ProductEvaluateLableBean>? = null
    override fun getListData(isRefresh: Boolean) {
        if (mScreenList == null) {
            checkNeedShowProgress()
            ApiManager.getService().storeProductEvaluateLable(GlobalParam.getUserIdOrJump(), mProductId, mFormId)
                    .compose(RxScheduler.applyScheduler())
                    .subscribe(object : Response<BaseResult<List<ProductEvaluateLableBean>>>() {
                        override fun _onNext(result: BaseResult<List<ProductEvaluateLableBean>>) {
                            if (result.code == 0) {
                                mScreenList = result?.data ?: arrayListOf()

                                mScreenList?.forEach {
                                    mContentView.product_evaluate_view.addView(it)
                                }

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


    override fun loadListData(): Observable<EvaluateResult<List<ProductEvaluateBean>>> {
        val type = mScreenList?.get(mContentView.product_evaluate_view.getCheckedPos())?.TYPE ?: 0
        return ApiManager.getService().storeProductEvaluateList(GlobalParam.getUserId(), mProductId, mFormId, type, page, Constant.PAGE_SIZE)
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
            mAdapter = ProductEvaluateAdapter(mActivity, data, {
                //itemClick

            })
//            recycler_view.addItemDecoration(object : RecyclerView.ItemDecoration() {
//                override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
//                    outRect?.bottom = DensityUtil.dip2px(mActivity, 8f)
//                }
//            })
            recycler_view.layoutManager = LinearLayoutManager(mActivity)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: EvaluateResult<List<ProductEvaluateBean>>) {
        if (isRefresh) {
            data?.add(ProductEvaluateHeaderBean(result.total, result.GOOD_PERCENT))
        }
        data?.addAll(result?.data ?: arrayListOf())
    }

}