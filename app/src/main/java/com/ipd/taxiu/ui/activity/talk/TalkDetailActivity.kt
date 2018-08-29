package com.ipd.taxiu.ui.activity.talk

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.ipd.taxiu.MainActivity
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.BaseResult
import com.ipd.taxiu.bean.TalkCommentBean
import com.ipd.taxiu.bean.TalkDetailBean
import com.ipd.taxiu.platform.global.GlobalParam
import com.ipd.taxiu.platform.http.ApiManager
import com.ipd.taxiu.platform.http.Response
import com.ipd.taxiu.platform.http.RxScheduler
import com.ipd.taxiu.presenter.store.TalkDetailPresenter
import com.ipd.taxiu.ui.BaseUIActivity
import com.ipd.taxiu.ui.fragment.talk.TalkDetailFragment
import com.ipd.taxiu.widget.ReplyDialog
import kotlinx.android.synthetic.main.activity_talk_detail.*

class TalkDetailActivity : BaseUIActivity(), TalkDetailPresenter.ITalkDetailView {

    companion object {
        fun launch(activity: Activity, talkId: Int = -1, nickname: String = "", isAdmin: Boolean = false) {
            val intent = Intent(activity, TalkDetailActivity::class.java)
            intent.putExtra("talkId", talkId)
            intent.putExtra("isAdmin", isAdmin)
            intent.putExtra("nickname", nickname)
            activity.startActivity(intent)
        }
    }


    var mPresenter: TalkDetailPresenter? = null
    override fun onViewAttach() {
        super.onViewAttach()
        mPresenter = TalkDetailPresenter()
        mPresenter?.attachView(this, this)
    }

    override fun onViewDetach() {
        super.onViewDetach()
        mPresenter?.detachView()
        mPresenter = null
    }


    private val isAdmin: Boolean by lazy { intent.getBooleanExtra("isAdmin", false) }
    private val talkId: Int by lazy { intent.getIntExtra("talkId", -1) }

    override fun getToolbarTitle(): String = if (isAdmin) "我的提问" else "${intent.getStringExtra("nickname")}的提问"

    override fun getContentLayout(): Int = R.layout.activity_talk_detail

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
        showProgress()
        mPresenter?.loadDetail(talkId)
    }

    override fun initListener() {

    }

    private var detailInfo: TalkDetailBean? = null
    override fun loadDetailSuccess(detail: TalkDetailBean) {
        showContent()
        detailInfo = detail
        iv_collect.isSelected = detailInfo?.IS_COLLECT ?: 0 == 1
        iv_collect.setOnClickListener {
            mPresenter?.toCollect(talkId)
        }

        val fragment = TalkDetailFragment.newInstance(talkId, isAdmin)
        fragment.setDetailData(detail)
        supportFragmentManager.beginTransaction().replace(R.id.fl_container, fragment).commit()

        tv_comment_topic.setOnClickListener {
            //参与话题
            ReplyDialog("添加一条答案", {
                //添加答案
                ApiManager.getService().talkToComment(GlobalParam.getUserIdOrJump(), it, talkId)
                        .compose(RxScheduler.applyScheduler())
                        .subscribe(object : Response<BaseResult<TalkCommentBean>>(mActivity, true) {
                            override fun _onNext(result: BaseResult<TalkCommentBean>) {
                                if (result.code == 0) {
                                    fragment.onRefresh()
                                } else {
                                    toastShow(result.msg)
                                }
                            }
                        })

            }).show(supportFragmentManager, TalkDetailActivity::class.java.name)
        }
    }

    override fun loadDetailFail(errMsg: String) {
        showError(errMsg)
    }

    override fun collectSuccess() {
        detailInfo?.IS_COLLECT = if (detailInfo?.IS_COLLECT == 0) 1 else 0
        iv_collect.isSelected = detailInfo?.IS_COLLECT ?: 0 == 1
    }

    override fun collectFail(errMsg: String) {
        toastShow(errMsg)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (isAdmin) {
            menuInflater.inflate(R.menu.menu_share, menu)
        } else {
            menuInflater.inflate(R.menu.topic_detail_menu, menu)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_index) {
            //首页
            MainActivity.launch(mActivity)
            return true
        } else if (id == R.id.action_share) {
            //分享

            return true
        }

        return super.onOptionsItemSelected(item)
    }
}