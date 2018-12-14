package com.ipd.taxiu.ui.fragment.taxiu

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.ipd.taxiu.R
import com.ipd.taxiu.adapter.TaxiuDetailAdapter
import com.ipd.taxiu.bean.CommentResult
import com.ipd.taxiu.bean.TaxiuCommentBean
import com.ipd.taxiu.bean.TaxiuDetailBean
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
import kotlinx.android.synthetic.main.layout_post_user.view.*
import kotlinx.android.synthetic.main.layout_taxiu_header.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import rx.Observable

class TaxiuDetailFragment : ListFragment<CommentResult<List<TaxiuCommentBean>>, TaxiuCommentBean>(), PostDetailChildPresenter.ITaxiuDetailChildView {

    companion object {
        fun newInstance(taxiuId: Int): TaxiuDetailFragment {
            val fragment = TaxiuDetailFragment()
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
    private lateinit var detailData: TaxiuDetailBean

    fun setDetailData(data: TaxiuDetailBean) {
        detailData = data
    }

    private var mSortType = CommentSortLayout.TIME
    override fun loadListData(): Observable<CommentResult<List<TaxiuCommentBean>>> {
        return ApiManager.getService().taxiuComment(GlobalParam.getUserId(), Constant.PAGE_SIZE, page, mSortType, taxiuId)
    }

    override fun isNoMoreData(result: CommentResult<List<TaxiuCommentBean>>): Int {
        if (page > INIT_PAGE && (result.data == null || result.data.isEmpty())) {
            return NO_MORE_DATA
        }
        return NORMAL
    }

    private var mAdapter: TaxiuDetailAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = TaxiuDetailAdapter(mActivity, detailData, {
                mSortType = it
                onRefresh(true)
            }, data, { pos, res, info ->
                //itemClick
                when (res) {
                    R.id.iv_zan -> {
                        mPresenter?.praise(pos, CommentType.TAXIU_PRAISE, detailData.SHOW_ID)
                    }
                    R.id.ll_comment_zan -> {
                        mPresenter?.praise(pos, CommentType.TAXIU_PRAISE_COMMENT, info!!.COMMENT_ID)
                    }
                    R.id.ll_attention -> {
                        mPresenter?.attention(detailData.User.USER_ID)
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
        detailData.COMMENT_NUM = result.join_num
        data?.addAll(result.data ?: arrayListOf())
    }

    @Subscribe
    fun onMainEvent(event: UpdateTaxiuCommentEvent) {
        onRefresh()
    }


    override fun attentionSuccess(isAttent: Int) {
        detailData.User.IS_ATTEN = isAttent
        mAdapter?.setAttent(0, recycler_view.layoutManager.findViewByPosition(0).ll_attention)
    }

    override fun attentionFail(errMsg: String) {
        toastShow(errMsg)
    }

    override fun praiseSuccess(pos: Int, category: String) {
        when (pos) {
            0 -> {
                detailData.IS_PRAISE = if (detailData.IS_PRAISE == 1) 0 else 1
                val holder = recycler_view.findViewHolderForAdapterPosition(pos)
                if (holder != null) {
                    holder.itemView.iv_zan.isSelected = detailData.IS_PRAISE == 1
                    var num = holder.itemView.tv_zan.text.toString().toInt()
                    holder.itemView.tv_zan.text = if (detailData.IS_PRAISE == 1) "${num + 1}" else "${num - 1}"
                    holder.itemView.tv_zan.isSelected = detailData.IS_PRAISE == 1
                    holder.itemView.ll_zan.isSelected = detailData.IS_PRAISE == 1
                    holder.itemView.tv_zan_extra.isSelected = detailData.IS_PRAISE == 1
                }
            }
            else -> {
                val info = data?.get(pos - 1)
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
        }
    }

    override fun praiseFail(errMsg: String) {
    }

}