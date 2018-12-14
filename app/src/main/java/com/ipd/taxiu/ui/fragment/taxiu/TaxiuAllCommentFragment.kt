package com.ipd.taxiu.ui.fragment.taxiu

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.ipd.taxiu.R
import com.ipd.taxiu.adapter.TaxiuAllCommentAdapter
import com.ipd.taxiu.bean.CommentResult
import com.ipd.taxiu.bean.TaxiuCommentBean
import com.ipd.taxiu.event.UpdateTaxiuCommentEvent
import com.ipd.taxiu.platform.global.Constant
import com.ipd.taxiu.platform.global.GlobalParam
import com.ipd.taxiu.platform.http.ApiManager
import com.ipd.taxiu.presenter.store.PostDetailChildPresenter
import com.ipd.taxiu.ui.ListFragment
import com.ipd.taxiu.ui.activity.topic.TopicPeopleCommentActivity
import com.ipd.taxiu.utils.CommentType
import com.ipd.taxiu.widget.CommentSortLayout
import kotlinx.android.synthetic.main.item_topic_comment.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import rx.Observable

class TaxiuAllCommentFragment : ListFragment<CommentResult<List<TaxiuCommentBean>>, TaxiuCommentBean>(), PostDetailChildPresenter.ITaxiuDetailChildView {

    companion object {
        fun newInstance(taxiuId: Int): TaxiuAllCommentFragment {
            val fragment = TaxiuAllCommentFragment()
            val bundle = Bundle()
            bundle.putInt("taxiuId", taxiuId)
            fragment.arguments = bundle
            return fragment
        }
    }

    private var mPresenter: PostDetailChildPresenter? = null
    override fun onViewAttach() {
        super.onViewAttach()
        EventBus.getDefault().register(this)
        mPresenter = PostDetailChildPresenter()
        mPresenter?.attachView(mActivity, this)
    }

    override fun onViewDetach() {
        super.onViewDetach()
        EventBus.getDefault().unregister(this)
        mPresenter?.detachView()
        mPresenter = null
    }

    private val taxiuId: Int by lazy { arguments.getInt("taxiuId", -1) }

    private var mSortType = CommentSortLayout.TIME
    override fun loadListData(): Observable<CommentResult<List<TaxiuCommentBean>>> {
        return ApiManager.getService().taxiuComment(GlobalParam.getUserId(), Constant.PAGE_SIZE, page, mSortType, taxiuId)
    }

    override fun isNoMoreData(result: CommentResult<List<TaxiuCommentBean>>): Int {
        if (page == INIT_PAGE && (result.data == null || result.data.isEmpty())) {
            return EMPTY_DATA
        } else if (result.data == null || result.data.isEmpty()) {
            return NO_MORE_DATA
        }
        return NORMAL
    }

    private var mAdapter: TaxiuAllCommentAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = TaxiuAllCommentAdapter(mActivity, {
                mSortType = it
                onRefresh(true)
            }, data, { pos, res, info ->
                //itemClick
                when (res) {
                    R.id.ll_comment_zan -> {
                        mPresenter?.praise(pos, CommentType.TAXIU_PRAISE_COMMENT, info!!.COMMENT_ID)
                    }
                    else -> {
                        if (info != null)
                            TopicPeopleCommentActivity.launch(mActivity, info.NICKNAME, CommentType.TAXIU, info.COMMENT_ID)
                    }
                }

            })
            recycler_view.layoutManager = LinearLayoutManager(mActivity)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: CommentResult<List<TaxiuCommentBean>>) {
        data?.addAll(result.data ?: arrayListOf())
    }

    @Subscribe
    fun onMainEvent(event: UpdateTaxiuCommentEvent) {
        onRefresh()
    }


    override fun attentionSuccess(isAttent: Int) {
    }

    override fun attentionFail(errMsg: String) {
        toastShow(errMsg)
    }

    override fun praiseSuccess(pos: Int, category: String) {
        val info = data?.get(pos)
        if (info != null) {
            info.IS_PRAISE = if (info.IS_PRAISE == 1) 0 else 1
            val holder = recycler_view.findViewHolderForAdapterPosition(pos)
            if (holder != null) {
                holder.itemView.iv_comment_zan.isSelected = info.IS_PRAISE == 1
                var num = holder.itemView.tv_comment_zan_num.text.toString().toInt()
                holder.itemView.tv_comment_zan_num.text = if (info.IS_PRAISE == 1) "${num + 1}" else "${num - 1}"
                holder.itemView.tv_comment_zan_num.isSelected = info.IS_PRAISE == 1

            }
        }
    }

    override fun praiseFail(errMsg: String) {
    }

}