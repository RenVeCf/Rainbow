package com.ipd.taxiu.ui.fragment.mine.published

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.ipd.taxiu.R
import com.ipd.taxiu.adapter.TaxiuAdapter
import com.ipd.taxiu.bean.TaxiuBean
import com.ipd.taxiu.ui.ListFragment
import com.ipd.taxiu.ui.activity.taxiu.TaxiuDetailActivity
import rx.Observable

class PublishedTaxiuFragment : ListFragment<List<TaxiuBean>, TaxiuBean>() {
    companion object {
        fun newInstance(type: Int): PublishedTaxiuFragment {
            val fragment = PublishedTaxiuFragment()
            val bundle = Bundle()
            bundle.putInt("type", type)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        progress_layout.setEmptyViewRes(R.layout.layout_empty_taxiu)
    }

    private val type: Int by lazy { arguments.getInt("type", 0) }
    override fun loadListData(): Observable<List<TaxiuBean>> {
        return Observable.create<List<TaxiuBean>> {
            val list: ArrayList<TaxiuBean> = ArrayList()
            for (i: Int in 0 until 10) {
                val info = TaxiuBean()
                if (type == 0) {
                    info.isMine = true
                }
                list.add(info)
            }
            it.onNext(list)
            it.onCompleted()
        }
    }

    override fun isNoMoreData(result: List<TaxiuBean>): Int {
        if (result == null || result.isEmpty()) return EMPTY_DATA
        return NORMAL
    }

    private var mAdapter: TaxiuAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = TaxiuAdapter(mActivity, data, {
                //itemClick
                TaxiuDetailActivity.launch(mActivity,it)
            })
            recycler_view.layoutManager = LinearLayoutManager(mActivity)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: List<TaxiuBean>) {
        data?.addAll(result)
    }

}