package com.ipd.rainbow.ui.fragment.live

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.ipd.rainbow.adapter.LiveAdapter
import com.ipd.rainbow.bean.BaseResult
import com.ipd.rainbow.bean.TaxiuBean
import com.ipd.rainbow.platform.global.Constant
import com.ipd.rainbow.platform.global.GlobalParam
import com.ipd.rainbow.platform.http.ApiManager
import com.ipd.rainbow.ui.ListFragment
import rx.Observable

class LiveListFragment : ListFragment<BaseResult<List<TaxiuBean>>, TaxiuBean>() {
    companion object {
        fun newInstance(categoryId: Int): LiveListFragment {
            val fragment = LiveListFragment()
            val bundle = Bundle()
            bundle.putInt("categoryId", categoryId)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun needLazyLoad(): Boolean = true

    private val categoryId: Int by lazy { arguments.getInt("categoryId", 0) }
    override fun loadListData(): Observable<BaseResult<List<TaxiuBean>>> {
        return ApiManager.getService().taxiuList(GlobalParam.getUserIdOrJump(), Constant.PAGE_SIZE, page, categoryId, "")
    }

    override fun isNoMoreData(result: BaseResult<List<TaxiuBean>>): Int {
        if (page == INIT_PAGE && (result.data == null || result.data.isEmpty())) {
            return EMPTY_DATA
        } else if (result.data == null || result.data.isEmpty()) {
            return NO_MORE_DATA
        }
        return NORMAL
    }

    private var mAdapter: LiveAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = LiveAdapter(mActivity, data) {
                //itemClick
            }
            recycler_view.layoutManager = LinearLayoutManager(mActivity)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: BaseResult<List<TaxiuBean>>) {
        data?.addAll(result?.data ?: arrayListOf())
    }

}