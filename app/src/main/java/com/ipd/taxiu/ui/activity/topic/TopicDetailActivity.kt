package com.ipd.taxiu.ui.activity.topic

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.ipd.taxiu.MainActivity
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.TopicDetailBean
import com.ipd.taxiu.event.UpdateCollectTopicEvent
import com.ipd.taxiu.presenter.store.TopicDetailPresenter
import com.ipd.taxiu.ui.BaseUIActivity
import com.ipd.taxiu.ui.fragment.topic.TopicDetailFragment
import kotlinx.android.synthetic.main.activity_topic_detail.*
import org.greenrobot.eventbus.EventBus

class TopicDetailActivity : BaseUIActivity(), TopicDetailPresenter.ITopicDetailView {

    companion object {
        fun launch(activity: Activity, topicId: Int) {
            val intent = Intent(activity, TopicDetailActivity::class.java)
            intent.putExtra("topicId", topicId)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "话题详情"

    override fun getContentLayout(): Int = R.layout.activity_topic_detail

    private val topicId: Int by lazy { intent.getIntExtra("topicId", -1) }


    var mPresenter: TopicDetailPresenter? = null
    override fun onViewAttach() {
        super.onViewAttach()
        mPresenter = TopicDetailPresenter()
        mPresenter?.attachView(this, this)
    }

    override fun onViewDetach() {
        super.onViewDetach()
        mPresenter?.detachView()
        mPresenter = null
    }

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
        showProgress()
        mPresenter?.loadDetail(topicId)
    }

    override fun initListener() {
        tv_comment_topic.setOnClickListener {
            //参与话题
            PublishTopicCommentActivity.launch(mActivity, topicId)
        }
    }


    private var detailInfo: TopicDetailBean? = null
    override fun loadDetailSuccess(detail: TopicDetailBean) {
        showContent()
        detailInfo = detail
        iv_collect.isSelected = detailInfo?.IS_COLLECT ?: 0 == 1
        iv_collect.setOnClickListener {
            mPresenter?.toCollect(detail.TOPIC_ID)
        }


        val fragment = TopicDetailFragment.newInstance(topicId)
        fragment.setDetailData(detail)
        supportFragmentManager.beginTransaction().replace(R.id.fl_container, fragment).commit()
    }

    override fun loadDetailFail(errMsg: String) {
        showError(errMsg)
    }

    override fun collectSuccess() {
        EventBus.getDefault().post(UpdateCollectTopicEvent())
        detailInfo?.IS_COLLECT = if (detailInfo?.IS_COLLECT == 0) 1 else 0
        iv_collect.isSelected = detailInfo?.IS_COLLECT ?: 0 == 1
    }

    override fun collectFail(errMsg: String) {
        toastShow(errMsg)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.topic_detail_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_index) {
            //首页
            MainActivity.launch(mActivity)
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}