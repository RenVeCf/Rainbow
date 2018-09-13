package com.ipd.taxiu.ui.fragment.taxiu

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.ipd.taxiu.R
import com.ipd.taxiu.adapter.TaxiuAdapter
import com.ipd.taxiu.bean.BaseResult
import com.ipd.taxiu.bean.TaxiuBean
import com.ipd.taxiu.platform.global.Constant
import com.ipd.taxiu.platform.global.GlobalParam
import com.ipd.taxiu.platform.http.ApiManager
import com.ipd.taxiu.ui.ListFragment
import com.ipd.taxiu.ui.activity.taxiu.TaxiuDetailActivity
import rx.Observable

class TaxiuListFragment : ListFragment<BaseResult<List<TaxiuBean>>, TaxiuBean>() {
    companion object {
        fun newInstance(categoryId: Int): TaxiuListFragment {
            val fragment = TaxiuListFragment()
            val bundle = Bundle()
            bundle.putInt("categoryId", categoryId)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun needLazyLoad(): Boolean = true

    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        progress_layout.setEmptyViewRes(R.layout.layout_empty_taxiu)
    }

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

    private var mAdapter: TaxiuAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = TaxiuAdapter(mActivity, data, {
                //itemClick
                TaxiuDetailActivity.launch(mActivity, it.SHOW_ID, GlobalParam.getUserId() == it.USER_ID.toString())
            })
            recycler_view.layoutManager = LinearLayoutManager(mActivity)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: BaseResult<List<TaxiuBean>>) {
        data?.addAll(result.data)
    }

}