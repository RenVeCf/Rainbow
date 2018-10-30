package com.ipd.taxiu.ui.fragment.message

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.ipd.taxiu.R
import com.ipd.taxiu.adapter.OrderMessageAdapter
import com.ipd.taxiu.adapter.OtherMessageAdapter
import com.ipd.taxiu.adapter.SystemMessageAdapter
import com.ipd.taxiu.bean.BaseResult
import com.ipd.taxiu.bean.MessageBean
import com.ipd.taxiu.bean.OrderMessageBean
import com.ipd.taxiu.bean.SystemMessageBean
import com.ipd.taxiu.platform.global.Constant
import com.ipd.taxiu.platform.global.GlobalParam
import com.ipd.taxiu.platform.http.ApiManager
import com.ipd.taxiu.ui.ListFragment
import rx.Observable
import java.util.*

/**
 * Created by Miss on 2018/7/19
 * 消息
 */
class MessageFragment : ListFragment<BaseResult<List<MessageBean>>, MessageBean>() {

    companion object {
        fun newInstance(categoryId: Int): MessageFragment {
            val fragment = MessageFragment()
            val bundle = Bundle()
            bundle.putInt("categoryId", categoryId)
            fragment.arguments = bundle
            return fragment
        }
    }

    private var mAdapter: OrderMessageAdapter? = null
    private var systemMessageAdapter: SystemMessageAdapter? = null
    private var otherMessageAdapter: OtherMessageAdapter? = null

    private val categoryId: Int by lazy { arguments.getInt("categoryId", 0) }

    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        getProgressLayout().setEmptyViewRes(R.layout.layout_empty_message)
    }


    override fun loadListData(): Observable<BaseResult<List<MessageBean>>> {
        return ApiManager.getService().newsList(GlobalParam.getUserIdOrJump(), Constant.PAGE_SIZE, page, categoryId)
    }


    override fun isNoMoreData(result: BaseResult<List<MessageBean>>): Int {
        if (page == INIT_PAGE && (result.data == null || result.data.isEmpty())) {
            return EMPTY_DATA
        } else if (result.data == null || result.data.isEmpty()) {
            return NO_MORE_DATA
        }
        return NORMAL
    }

    override fun setOrNotifyAdapter() {
        if (categoryId == 1) {
            if (mAdapter == null) {
                mAdapter = OrderMessageAdapter(context, data as ArrayList<OrderMessageBean>?)
                recycler_view.layoutManager = LinearLayoutManager(context)
                recycler_view.adapter = mAdapter
            } else {
                mAdapter?.notifyDataSetChanged()
            }
        } else if (categoryId == 2) {
            if (systemMessageAdapter == null) {
                systemMessageAdapter = SystemMessageAdapter(context, data as ArrayList<SystemMessageBean>?)
                recycler_view.layoutManager = LinearLayoutManager(context)
                recycler_view.adapter = systemMessageAdapter
            } else {
                systemMessageAdapter?.notifyDataSetChanged()
            }
        } else if (categoryId == 3) {
            if (otherMessageAdapter == null) {
                otherMessageAdapter = OtherMessageAdapter(context, data as ArrayList<String>?)
                recycler_view.layoutManager = LinearLayoutManager(context)
                recycler_view.adapter = otherMessageAdapter
            } else {
                otherMessageAdapter?.notifyDataSetChanged()
            }
        }
    }

    override fun addData(isRefresh: Boolean, result: BaseResult<List<MessageBean>>) {
        data?.addAll(result?.data ?: arrayListOf())
    }


}
