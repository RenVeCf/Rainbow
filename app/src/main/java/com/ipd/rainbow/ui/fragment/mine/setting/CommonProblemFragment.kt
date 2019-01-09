package com.ipd.rainbow.ui.fragment.mine.setting

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.ipd.rainbow.R
import com.ipd.rainbow.adapter.CommonProblemAdapter
import com.ipd.rainbow.bean.QuestionBean
import com.ipd.rainbow.platform.global.GlobalParam
import com.ipd.rainbow.platform.http.ApiManager
import rx.Observable

/**
Created by Miss on 2018/8/20
 */
class CommonProblemFragment : com.ipd.rainbow.ui.ListFragment<List<QuestionBean>, QuestionBean>() {
    companion object {
        fun newInstance(): CommonProblemFragment {
            return CommonProblemFragment()
        }
    }
    override fun loadListData(): Observable<List<QuestionBean>> {
        return ApiManager.getService().questionList(100, GlobalParam.getUserId(),page)
                .map ({
                    val list = ArrayList<QuestionBean>()
                   if (it.code == 0){
                       list.addAll(it.data)
                   }
                    list
                })
    }

    override fun getContentLayout(): Int = R.layout.activity_common_problem

    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        swipe_load_layout = mRootView?.findViewById(R.id.swipe_load_layout)!!
        swipe_load_layout.isRefreshEnabled = false
        setLoadMoreEnable(false)
    }

    override fun isNoMoreData(result: List<QuestionBean>): Int {
        if (result == null || result.isEmpty()) return EMPTY_DATA
        return NORMAL
    }

    private var mAdapter: CommonProblemAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = CommonProblemAdapter(mActivity, this!!.data!!)
            recycler_view.layoutManager = LinearLayoutManager(mActivity)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: List<QuestionBean>) {
        data?.addAll(result)
    }
}