package com.ipd.taxiu.ui.fragment.classroom

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import com.ipd.taxiu.R
import com.ipd.taxiu.adapter.ClassRoomAdapter
import com.ipd.taxiu.adapter.ProductAdapter
import com.ipd.taxiu.adapter.TaxiuAdapter
import com.ipd.taxiu.bean.BaseResult
import com.ipd.taxiu.bean.ClassRoomBean
import com.ipd.taxiu.event.UpdateCollectClassroomEvent
import com.ipd.taxiu.event.UpdateCollectProductEvent
import com.ipd.taxiu.event.UpdateCollectTaxiuEvent
import com.ipd.taxiu.platform.global.Constant
import com.ipd.taxiu.platform.global.GlobalParam
import com.ipd.taxiu.platform.http.ApiManager
import com.ipd.taxiu.ui.ListFragment
import com.ipd.taxiu.ui.activity.classroom.ClassRoomDetailActivity
import com.ipd.taxiu.ui.activity.store.ProductDetailActivity
import com.ipd.taxiu.ui.activity.taxiu.TaxiuDetailActivity
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import rx.Observable

class CollectClassroomFragment : ListFragment<BaseResult<List<ClassRoomBean>>, ClassRoomBean>() {
    companion object {
        fun newInstance(): CollectClassroomFragment {
            return CollectClassroomFragment()
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

    override fun loadListData(): Observable<BaseResult<List<ClassRoomBean>>> {
        return ApiManager.getService().classroomCollect(GlobalParam.getUserIdOrJump(), Constant.PAGE_SIZE, page)
    }

    override fun isNoMoreData(result: BaseResult<List<ClassRoomBean>>): Int {
        if (page == INIT_PAGE && (result.data == null || result.data.isEmpty())) {
            return EMPTY_DATA
        } else if (result.data == null || result.data.isEmpty()) {
            return NO_MORE_DATA
        }
        return NORMAL
    }

    private var mAdapter: ClassRoomAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = ClassRoomAdapter(mActivity, data, false, {
                //itemClick
                ClassRoomDetailActivity.launch(mActivity, it.CLASS_ROOM_ID)
            })
            recycler_view.layoutManager = LinearLayoutManager(mActivity)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: BaseResult<List<ClassRoomBean>>) {
        data?.addAll(result?.data ?: arrayListOf())
    }

    @Subscribe
    fun onMainEvent(event: UpdateCollectClassroomEvent) {
        if (isFirstLoad()) return
        onRefresh(true)
    }

}