package com.ipd.taxiu.ui.fragment.classroom

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import com.ipd.taxiu.R
import com.ipd.taxiu.adapter.ProductAdapter
import com.ipd.taxiu.adapter.TaxiuAdapter
import com.ipd.taxiu.bean.BaseResult
import com.ipd.taxiu.bean.TaxiuBean
import com.ipd.taxiu.event.UpdateCollectProductEvent
import com.ipd.taxiu.event.UpdateCollectTaxiuEvent
import com.ipd.taxiu.platform.global.Constant
import com.ipd.taxiu.platform.global.GlobalParam
import com.ipd.taxiu.platform.http.ApiManager
import com.ipd.taxiu.ui.ListFragment
import com.ipd.taxiu.ui.activity.store.ProductDetailActivity
import com.ipd.taxiu.ui.activity.taxiu.TaxiuDetailActivity
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import rx.Observable

class CollectTaxiuFragment : ListFragment<BaseResult<List<TaxiuBean>>, TaxiuBean>() {
    companion object {
        fun newInstance(): CollectTaxiuFragment {
            return CollectTaxiuFragment()
        }
    }

    override fun onViewAttach() {
        super.onViewAttach()
        EventBus.getDefault().register(this)
    }

    override fun onViewDetach() {
        super.onViewDetach()
        EventBus.getDefault().unregister(this)
    }

    override fun needLazyLoad() = true

    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        progress_layout.setEmptyViewRes(R.layout.empty_base)
    }

    override fun loadListData(): Observable<BaseResult<List<TaxiuBean>>> {
        return ApiManager.getService().taxiuCollect(GlobalParam.getUserIdOrJump(), Constant.PAGE_SIZE, page)
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
                TaxiuDetailActivity.launch(mActivity,it.SHOW_ID,it.USER_ID.toString() == GlobalParam.getUserIdOrJump())
            })
            recycler_view.layoutManager = LinearLayoutManager(mActivity)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: BaseResult<List<TaxiuBean>>) {
        data?.addAll(result?.data ?: arrayListOf())
    }

    @Subscribe
    fun onMainEvent(event: UpdateCollectTaxiuEvent) {
        if (isFirstLoad()) return
        onRefresh(true)
    }

}