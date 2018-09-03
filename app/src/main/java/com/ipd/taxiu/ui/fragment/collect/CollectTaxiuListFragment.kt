package com.ipd.taxiu.ui.fragment.collect

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
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

class CollectTaxiuListFragment : ListFragment<BaseResult<List<TaxiuBean>>, TaxiuBean>() {
    companion object {
        fun newInstance(): CollectTaxiuListFragment {
            val fragment = CollectTaxiuListFragment()
            return fragment
        }
    }

    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        progress_layout.setEmptyViewRes(R.layout.layout_empty_taxiu)
    }

    override fun loadListData(): Observable<BaseResult<List<TaxiuBean>>> {
        return  ApiManager.getService().taxiuList(GlobalParam.getUserIdOrJump(), Constant.PAGE_SIZE, page, 1, "")
    }

    override fun isNoMoreData(result: BaseResult<List<TaxiuBean>>): Int {
        if (result == null || result.data.isEmpty()) {
            return if (page == INIT_PAGE){
                EMPTY_DATA
            }else{
                NO_MORE_DATA
            }
        }
        return NORMAL
    }

    private var mAdapter: TaxiuAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = TaxiuAdapter(mActivity, data, {
                //itemClick
                TaxiuDetailActivity.launch(mActivity)
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