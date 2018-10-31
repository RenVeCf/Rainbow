package com.ipd.taxiu.ui.fragment.group

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.ipd.taxiu.R
import com.ipd.taxiu.adapter.GroupListAdapter
import com.ipd.taxiu.bean.BaseResult
import com.ipd.taxiu.bean.GroupBean
import com.ipd.taxiu.platform.global.Constant
import com.ipd.taxiu.platform.global.GlobalParam
import com.ipd.taxiu.platform.http.ApiManager
import com.ipd.taxiu.ui.ListFragment
import com.ipd.taxiu.ui.activity.group.GroupDetailActivity
import rx.Observable

/**
 * Created by Miss on 2018/7/19
 * 拼团
 */
class GroupFragment : ListFragment<BaseResult<List<GroupBean>>, GroupBean>() {
    companion object {
        fun newInstance(categoryId: Int): GroupFragment {
            val fragment = GroupFragment()
            val bundle = Bundle()
            bundle.putInt("categoryId", categoryId)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun needLazyLoad() = true

    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        progress_layout.setEmptyViewRes(R.layout.layout_empty_group)
    }

    private val mCategoryId by lazy { arguments.getInt("categoryId", 0) }
    override fun loadListData(): Observable<BaseResult<List<GroupBean>>> {
        return ApiManager.getService().mineSpellList(GlobalParam.getUserId(), page, Constant.PAGE_SIZE, mCategoryId)
    }

    override fun isNoMoreData(result: BaseResult<List<GroupBean>>): Int {
        if (page == INIT_PAGE && (result.data == null || result.data.isEmpty())) {
            return EMPTY_DATA
        } else if (result.data == null || result.data.isEmpty()) {
            return NO_MORE_DATA
        }
        return NORMAL
    }

    private var mAdapter: GroupListAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = GroupListAdapter(context, data, {
                GroupDetailActivity.launch(mActivity, it.ORDER_ID)
            })
            recycler_view.layoutManager = LinearLayoutManager(context)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: BaseResult<List<GroupBean>>) {
        data?.addAll(result?.data ?: arrayListOf())
    }

}
