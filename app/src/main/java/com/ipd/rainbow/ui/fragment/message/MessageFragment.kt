package com.ipd.rainbow.ui.fragment.message

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.ipd.rainbow.R
import com.ipd.rainbow.adapter.MessageAdapter
import com.ipd.rainbow.adapter.OtherMessageAdapter
import com.ipd.rainbow.bean.BaseResult
import com.ipd.rainbow.bean.MessageBean
import com.ipd.rainbow.event.UpdateUserInfoEvent
import com.ipd.rainbow.platform.global.Constant
import com.ipd.rainbow.platform.global.GlobalParam
import com.ipd.rainbow.platform.http.ApiManager
import com.ipd.rainbow.ui.ListFragment
import com.ipd.rainbow.ui.activity.account.BindingPhoneActivity
import com.ipd.rainbow.ui.activity.web.WebActivity
import org.greenrobot.eventbus.Subscribe
import rx.Observable

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

    private var mAdapter: MessageAdapter? = null
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
        if (categoryId == 1 || categoryId == 2) {
            if (mAdapter == null) {
                mAdapter = MessageAdapter(context, data) {
                    if (it.CATEGORY == 2 && it.TYPE == 3) {
                        BindingPhoneActivity.launch(mActivity)
                    }

                }
                recycler_view.layoutManager = LinearLayoutManager(context)
                recycler_view.adapter = mAdapter
            } else {
                mAdapter?.notifyDataSetChanged()
            }
        } else if (categoryId == 3) {
            if (otherMessageAdapter == null) {
                otherMessageAdapter = OtherMessageAdapter(context, data) {
                    WebActivity.launch(mActivity, WebActivity.HTML, it.CONTENT, "详情")
                }
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

    @Subscribe
    fun onMainEvent(event: UpdateUserInfoEvent) {
        if (categoryId == 2) {
            onRefresh(true)
        }
    }


}
