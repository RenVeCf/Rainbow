package com.ipd.rainbow.ui.fragment.order

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.ipd.rainbow.R
import com.ipd.rainbow.adapter.ReturnAdapter
import com.ipd.rainbow.bean.BaseResult
import com.ipd.rainbow.bean.ReturnBean
import com.ipd.rainbow.platform.global.Constant
import com.ipd.rainbow.platform.global.GlobalParam
import com.ipd.rainbow.platform.http.ApiManager
import com.ipd.rainbow.ui.ListFragment
import com.ipd.rainbow.ui.activity.order.ReturnDetailActivity
import rx.Observable

/**
 * Created by Miss on 2018/7/19
 * 退款退货审核信息
 */
class ReturnInfoFragment : ListFragment<BaseResult<List<ReturnBean>>, ReturnBean>() {

    companion object {
        fun newInstance(categoryId: Int): ReturnInfoFragment {
            val fragment = ReturnInfoFragment()
            val bundle = Bundle()
            bundle.putInt("categoryId", categoryId + 1)
            fragment.arguments = bundle
            return fragment
        }
    }


    private val mCategoryId: Int by lazy { arguments.getInt("categoryId", 0) }
    private var mAdapter: ReturnAdapter? = null

    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        progress_layout.setEmptyViewRes(R.layout.layout_empty_return)
    }

    override fun loadListData(): Observable<BaseResult<List<ReturnBean>>> {
        return ApiManager.getService().returnList(GlobalParam.getUserIdOrJump(), Constant.PAGE_SIZE, page, mCategoryId)
    }

    override fun isNoMoreData(result: BaseResult<List<ReturnBean>>): Int {
        if (page == INIT_PAGE && (result.data == null || result.data.isEmpty())) {
            return EMPTY_DATA
        } else if (result.data == null || result.data.isEmpty()) {
            return NO_MORE_DATA
        }
        return NORMAL
    }

    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = ReturnAdapter(context, data, {
                ReturnDetailActivity.launch(mActivity, it.REFUND_ID)
            })
            recycler_view.layoutManager = LinearLayoutManager(context)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: BaseResult<List<ReturnBean>>) {
        data?.addAll(result?.data ?: arrayListOf())
    }


}
