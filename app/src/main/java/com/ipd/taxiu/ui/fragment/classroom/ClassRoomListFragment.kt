package com.ipd.taxiu.ui.fragment.classroom

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.ipd.taxiu.R
import com.ipd.taxiu.adapter.ClassRoomAdapter
import com.ipd.taxiu.bean.BaseResult
import com.ipd.taxiu.bean.ClassRoomBean
import com.ipd.taxiu.platform.global.Constant
import com.ipd.taxiu.platform.global.GlobalParam
import com.ipd.taxiu.platform.http.ApiManager
import com.ipd.taxiu.ui.ListFragment
import com.ipd.taxiu.ui.activity.classroom.ClassRoomDetailActivity
import com.ipd.taxiu.ui.activity.classroom.OwnedClassRoomActivity
import rx.Observable

class ClassRoomListFragment : ListFragment<BaseResult<List<ClassRoomBean>>, ClassRoomBean>() {
    companion object {
        fun newInstance(): ClassRoomListFragment {
            return ClassRoomListFragment()
        }
    }

    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        progress_layout.setEmptyViewRes(R.layout.layout_empty_classroom)
    }

    override fun loadListData(): Observable<BaseResult<List<ClassRoomBean>>> {
        return ApiManager.getService().classroomList(GlobalParam.getUserIdOrJump(), Constant.PAGE_SIZE, page, "")
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
            mAdapter = ClassRoomAdapter(mActivity, data, {
                //itemClick
                if (it.IS_BUY == 1) {
                    //已购买
                    OwnedClassRoomActivity.launch(mActivity, it.CLASS_ROOM_ID)
                } else {
                    ClassRoomDetailActivity.launch(mActivity, it.CLASS_ROOM_ID)
                }
            })
            recycler_view.layoutManager = LinearLayoutManager(mActivity)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: BaseResult<List<ClassRoomBean>>) {
        data?.addAll(result?.data)
    }

}