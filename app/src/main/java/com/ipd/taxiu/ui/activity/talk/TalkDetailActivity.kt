package com.ipd.taxiu.ui.activity.talk

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import cn.sharesdk.framework.Platform
import com.ipd.taxiu.MainActivity
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.BaseResult
import com.ipd.taxiu.bean.TalkCommentBean
import com.ipd.taxiu.bean.TalkDetailBean
import com.ipd.taxiu.event.UpdateCollectTalkEvent
import com.ipd.taxiu.event.UpdateTalkListEvent
import com.ipd.taxiu.platform.global.Constant
import com.ipd.taxiu.platform.global.GlobalParam
import com.ipd.taxiu.platform.http.ApiManager
import com.ipd.taxiu.platform.http.HttpUrl
import com.ipd.taxiu.platform.http.Response
import com.ipd.taxiu.platform.http.RxScheduler
import com.ipd.taxiu.presenter.store.TalkDetailPresenter
import com.ipd.taxiu.ui.BaseUIActivity
import com.ipd.taxiu.ui.fragment.talk.TalkDetailFragment
import com.ipd.taxiu.utils.ShareCallback
import com.ipd.taxiu.widget.MessageDialog
import com.ipd.taxiu.widget.ReplyDialog
import com.ipd.taxiu.widget.ShareDialog
import com.ipd.taxiu.widget.ShareDialogClick
import kotlinx.android.synthetic.main.activity_talk_detail.*
import kotlinx.android.synthetic.main.admin_taxiu_toolbar.*
import org.greenrobot.eventbus.EventBus
import java.util.*

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

    override fun getToolbarLayout(): Int {
        return if (isAdmin) {
            R.layout.admin_taxiu_toolbar
        } else {
            super.getToolbarLayout()
        }
    }

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
        if (isAdmin) {
            fl_delete.setOnClickListener {
                MessageDialog.Builder(mActivity)
                        .setTitle("确认要删除问答内容吗?")
                        .setMessage("删除后不可恢复，请谨慎操作。")
                        .setCommit("确认删除", { builder ->
                            builder.dismiss()
                            mPresenter?.delete(talkId)
                        })
                        .setCancel("暂不删除", { builder ->
                            builder.dismiss()
                        }).show()
            }
        }
        circle_comment.setOnClickListener {
            if (detailInfo == null) return@setOnClickListener
            TalkAllCommentActivity.launch(mActivity, talkId, isAdmin, detailInfo!!.IS_SURE)
        }

    }

    private var detailInfo: TalkDetailBean? = null
    override fun loadDetailSuccess(detail: TalkDetailBean) {
        detailInfo = detail

        circle_comment.setCircleNum(detailInfo?.REPLY ?: 0)

        if (isAdmin) {
            ll_bottom_menu.visibility = View.GONE
        }

        iv_collect.isSelected = detailInfo?.IS_COLLECT ?: 0 == 1
        circle_collect.setOnClickListener {
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

        if (isAdmin) {
            iv_share.setOnClickListener {
                val dialog = ShareDialog(mActivity)
                dialog.setShareDialogOnClickListener(getShareDialogClick(detail))
                dialog.show()
            }
        } else {
            circle_share.setOnClickListener {
                val dialog = ShareDialog(mActivity)
                dialog.setShareDialogOnClickListener(getShareDialogClick(detail))
                dialog.show()
            }
        }
        showContent()
    }

    fun getShareDialogClick(detail: TalkDetailBean): ShareDialog.ShareDialogOnclickListener {
        return ShareDialogClick()
                .setShareTitle(Constant.SHARE_TALK_CONTENT)
                .setShareContent(detail.CONTENT)
                .setShareLogoUrl("")
                .setShareUrl(HttpUrl.APK_DOWNLOAD_URL)
                .setCallback(object : ShareDialogClick.MainPlatformActionListener {
                    override fun onComplete(platform: Platform?, i: Int, hashMap: HashMap<String, Any>?) {
                        toastShow(true, "分享成功")
                        ShareCallback.shareTalk(detail.QUESTION_ID)
                    }

                    override fun onError(platform: Platform?, i: Int, throwable: Throwable?) {
                        toastShow("分享失败")
                    }

                    override fun onCancel(platform: Platform?, i: Int) {
                        toastShow("取消分享")
                    }

                })
    }

    override fun loadDetailFail(errMsg: String) {
        showError(errMsg)
    }

    override fun collectSuccess() {
        EventBus.getDefault().post(UpdateCollectTalkEvent())
        detailInfo?.IS_COLLECT = if (detailInfo?.IS_COLLECT == 0) 1 else 0
        iv_collect.isSelected = detailInfo?.IS_COLLECT ?: 0 == 1
    }

    override fun collectFail(errMsg: String) {
        toastShow(errMsg)
    }

    override fun deleteSuccess() {
        EventBus.getDefault().post(UpdateTalkListEvent())
        finish()
    }

    override fun deleteFail(errMsg: String) {
        toastShow(errMsg)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (isAdmin) {
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
        }

        return super.onOptionsItemSelected(item)
    }
}