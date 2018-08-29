package com.ipd.taxiu.ui.fragment.taxiu

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.ipd.taxiu.adapter.TaxiuDetailAdapter
import com.ipd.taxiu.bean.CommentResult
import com.ipd.taxiu.bean.MoreCommentReplyBean
import com.ipd.taxiu.bean.TaxiuCommentBean
import com.ipd.taxiu.bean.TaxiuDetailBean
import com.ipd.taxiu.event.UpdateTaxiuCommentEvent
import com.ipd.taxiu.platform.global.Constant
import com.ipd.taxiu.platform.global.GlobalParam
import com.ipd.taxiu.platform.http.ApiManager
import com.ipd.taxiu.presenter.store.TaxiuDetailChildPresenter
import com.ipd.taxiu.ui.ListFragment
import com.ipd.taxiu.ui.activity.topic.TopicPeopleCommentActivity
import com.ipd.taxiu.utils.CommentType
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import rx.Observable

class TaxiuDetailFragment : ListFragment<CommentResult<List<TaxiuCommentBean>>, TaxiuCommentBean>(),TaxiuDetailChildPresenter.ITaxiuDetailChildView {

    companion object {
        fun newInstance(taxiuId: Int): TaxiuDetailFragment {
            val fragment = TaxiuDetailFragment()
            val bundle = Bundle()
            bundle.putInt("taxiuId", taxiuId)
            fragment.arguments = bundle
            return fragment
        }
    }

    private var mPresenter: TaxiuDetailChildPresenter? = null
    override fun onViewAttach() {
        super.onViewAttach()
        EventBus.getDefault().register(this)
        mPresenter = TaxiuDetailChildPresenter()
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

    override fun loadListData(): Observable<CommentResult<List<TaxiuCommentBean>>> {
        return ApiManager.getService().taxiuComment(GlobalParam.getUserId(), Constant.PAGE_SIZE, page, 1, taxiuId)
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
            mAdapter = TaxiuDetailAdapter(mActivity, detailData, data, {
                //itemClick
                TopicPeopleCommentActivity.launch(mActivity, it.NICKNAME, CommentType.TAXIU, it.COMMENT_ID)
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


    override fun attentionSuccess(detail: MoreCommentReplyBean) {

    }

    override fun attentionFail(errMsg: String) {
    }

    override fun praiseSuccess(pos: Int, category: String) {
    }

    override fun praiseFail(errMsg: String) {
    }

}