package com.ipd.rainbow.ui.fragment.balance

import android.support.v7.widget.LinearLayoutManager

import com.ipd.rainbow.adapter.BalanceBillAdapter
import com.ipd.rainbow.bean.BalanceBillBean
import com.ipd.rainbow.bean.BaseResult
import com.ipd.rainbow.platform.global.Constant
import com.ipd.rainbow.platform.global.GlobalParam
import com.ipd.rainbow.platform.http.ApiManager
import com.ipd.rainbow.ui.ListFragment

import rx.Observable

/**
 * Created by Miss on 2018/8/6
 */
class BalanceBillFragment : ListFragment<BaseResult<List<BalanceBillBean>>, BalanceBillBean>() {

    companion object {
        fun newInstance(): BalanceBillFragment {
            return BalanceBillFragment()
        }
    }

    private var mAdapter: BalanceBillAdapter? = null

    override fun loadListData(): Observable<BaseResult<List<BalanceBillBean>>> {
        return ApiManager.getService().balanceBill(GlobalParam.getUserIdOrJump(), Constant.PAGE_SIZE, page)
    }

    override fun isNoMoreData(result: BaseResult<List<BalanceBillBean>>): Int {
        if (page == INIT_PAGE && (result.data == null || result.data.isEmpty())) {
            return EMPTY_DATA
        } else if (result.data == null || result.data.isEmpty()) {
            return NO_MORE_DATA
        }
        return NORMAL
    }

    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = BalanceBillAdapter(context, data)
            recycler_view.layoutManager = LinearLayoutManager(context)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: BaseResult<List<BalanceBillBean>>) {
        data?.addAll(result?.data?: arrayListOf())
    }
}
