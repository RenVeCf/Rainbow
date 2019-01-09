package com.ipd.rainbow.ui.fragment.group

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.ipd.rainbow.R
import com.ipd.rainbow.adapter.GroupListAdapter
import com.ipd.rainbow.bean.BaseResult
import com.ipd.rainbow.bean.GroupBean
import com.ipd.rainbow.platform.global.Constant
import com.ipd.rainbow.platform.global.GlobalParam
import com.ipd.rainbow.platform.http.ApiManager
import com.ipd.rainbow.ui.ListFragment
import com.ipd.rainbow.ui.activity.group.GroupDetailActivity
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
