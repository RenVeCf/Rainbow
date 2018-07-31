package com.ipd.taxiu.ui.fragment.mine.published

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.ipd.taxiu.R
import com.ipd.taxiu.adapter.ClassRoomAdapter
import com.ipd.taxiu.bean.ClassRoomBean
import com.ipd.taxiu.ui.ListFragment
import com.ipd.taxiu.ui.activity.classroom.ClassRoomDetailActivity
import com.ipd.taxiu.ui.activity.classroom.OwnedClassRoomActivity
import rx.Observable

class MineClassRoomFragment : ListFragment<List<ClassRoomBean>, ClassRoomBean>() {
    companion object {
        fun newInstance(): MineClassRoomFragment {
            return MineClassRoomFragment()
        }
    }

    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        progress_layout.setEmptyViewRes(R.layout.layout_empty_classroom)
    }

    override fun loadListData(): Observable<List<ClassRoomBean>> {
        return Observable.create<List<ClassRoomBean>> {
            val list: ArrayList<ClassRoomBean> = ArrayList()
            for (i: Int in 0 until 10) {
                val info = ClassRoomBean()
                info.isBuy = true
                list.add(info)
            }
            it.onNext(list)
            it.onCompleted()
        }
    }

    override fun isNoMoreData(result: List<ClassRoomBean>): Int {
        if (result == null || result.isEmpty()) return EMPTY_DATA
        return NORMAL
    }

    private var mAdapter: ClassRoomAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = ClassRoomAdapter(mActivity, data, {
                //itemClick
                OwnedClassRoomActivity.launch(mActivity)

            })
            recycler_view.layoutManager = LinearLayoutManager(mActivity)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: List<ClassRoomBean>) {
        data?.addAll(result)
    }

}