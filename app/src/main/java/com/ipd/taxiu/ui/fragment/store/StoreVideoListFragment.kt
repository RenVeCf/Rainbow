package com.ipd.taxiu.ui.fragment.store

import android.graphics.Rect
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.ipd.taxiu.R
import com.ipd.taxiu.adapter.StoreVideoAdapter
import com.ipd.taxiu.bean.BaseResult
import com.ipd.taxiu.bean.StoreVideoBean
import com.ipd.taxiu.platform.global.Constant
import com.ipd.taxiu.platform.global.GlobalParam
import com.ipd.taxiu.platform.http.ApiManager
import com.ipd.taxiu.ui.ListFragment
import com.ipd.taxiu.ui.activity.store.video.StoreVideoDetailActivity
import rx.Observable

class StoreVideoListFragment : ListFragment<BaseResult<List<StoreVideoBean>>, StoreVideoBean>() {
    companion object {
        fun newInstance(type: Int, showTypeId: Int): StoreVideoListFragment {
            val fragment = StoreVideoListFragment()
            val bundle = Bundle()
            bundle.putInt("type", type)
            bundle.putInt("showTypeId", showTypeId)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun needLazyLoad(): Boolean = true

    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        progress_layout.setEmptyViewRes(R.layout.layout_empty_video)
        swipe_load_layout.isRefreshEnabled = false
    }

    private val type: Int by lazy { arguments.getInt("type", 0) }
    private val showTypeId: Int by lazy { arguments.getInt("showTypeId", 0) }
    override fun loadListData(): Observable<BaseResult<List<StoreVideoBean>>> {
        return ApiManager.getService().storeVideoList(GlobalParam.getUserIdOrJump(), type, Constant.PAGE_SIZE, page, showTypeId)
    }

    override fun isNoMoreData(result: BaseResult<List<StoreVideoBean>>): Int {
        if (page == INIT_PAGE && (result.data == null || result.data.isEmpty())) {
            return EMPTY_DATA
        } else if (result.data == null || result.data.isEmpty()) {
            return NO_MORE_DATA
        }
        return NORMAL
    }

    private var mAdapter: StoreVideoAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = StoreVideoAdapter(mActivity, data, {
                //itemClick
                StoreVideoDetailActivity.launch(mActivity,it.VIDEO_ID.toString())

            })
            recycler_view.addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
                    outRect?.bottom = 2
                }
            })
            recycler_view.layoutManager = LinearLayoutManager(mActivity)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: BaseResult<List<StoreVideoBean>>) {
        data?.addAll(result?.data?: arrayListOf())
    }

}