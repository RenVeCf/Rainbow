package com.ipd.taxiu.ui.fragment.referral

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.ipd.taxiu.R
import com.ipd.taxiu.adapter.EarningDetailAdapter
import com.ipd.taxiu.bean.EarningsResult
import com.ipd.taxiu.bean.RecommendEarningsBean
import com.ipd.taxiu.platform.global.Constant
import com.ipd.taxiu.platform.global.GlobalParam
import com.ipd.taxiu.platform.http.ApiManager
import com.ipd.taxiu.ui.ListFragment
import kotlinx.android.synthetic.main.activity_recommend_earning.*
import rx.Observable

/**
 * Created by Miss on 2018/8/6
 */
class RecommendEarningsFragment : ListFragment<EarningsResult<List<RecommendEarningsBean>>, RecommendEarningsBean>() {

    companion object {
        fun newInstance(): RecommendEarningsFragment {
            return RecommendEarningsFragment()
        }
    }


    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        progress_layout.setEmptyViewRes(R.layout.layout_empty_earning)
    }

    override fun getContentLayout(): Int {
        return R.layout.activity_recommend_earning
    }

    override fun loadListData(): Observable<EarningsResult<List<RecommendEarningsBean>>> {
        return ApiManager.getService().recommendEarnings(GlobalParam.getUserIdOrJump(), Constant.PAGE_SIZE, page)
    }


    override fun isNoMoreData(result: EarningsResult<List<RecommendEarningsBean>>): Int {
        if (page == INIT_PAGE && (result.data == null || result.data.isEmpty())) {
            return EMPTY_DATA
        } else if (result.data == null || result.data.isEmpty()) {
            return NO_MORE_DATA
        }
        return NORMAL
    }

    private var mAdapter: EarningDetailAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = EarningDetailAdapter(context, data)
            recycler_view.layoutManager = LinearLayoutManager(context)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: EarningsResult<List<RecommendEarningsBean>>) {
        tv_earning_number.text = result.sum.toString()
        data?.addAll(result?.data ?: arrayListOf())
    }
}
