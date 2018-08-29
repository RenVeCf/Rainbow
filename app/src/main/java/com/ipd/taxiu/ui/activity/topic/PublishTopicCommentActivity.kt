package com.ipd.taxiu.ui.activity.topic

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.BaseResult
import com.ipd.taxiu.bean.TopicCommentBean
import com.ipd.taxiu.event.UpdateTopicCommentEvent
import com.ipd.taxiu.platform.global.GlobalParam
import com.ipd.taxiu.platform.http.ApiManager
import com.ipd.taxiu.platform.http.Response
import com.ipd.taxiu.platform.http.RxScheduler
import com.ipd.taxiu.ui.BaseUIActivity
import kotlinx.android.synthetic.main.activity_publish_topic_comment.*
import org.greenrobot.eventbus.EventBus

class PublishTopicCommentActivity : BaseUIActivity() {

    companion object {
        fun launch(activity: Activity, topicId: Int) {
            val intent = Intent(activity, PublishTopicCommentActivity::class.java)
            intent.putExtra("topicId", topicId)
            activity.startActivity(intent)
        }
    }

    private val topicId: Int by lazy { intent.getIntExtra("topicId", -1) }

    override fun getToolbarTitle(): String = "参与话题"

    override fun getContentLayout(): Int = R.layout.activity_publish_topic_comment

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
        picture_recycler_view.init()
    }

    override fun initListener() {
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.publish_topic_comment_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_publish) {
            //发布
            //发布
            val content = et_content.text.toString().trim()
            if (TextUtils.isEmpty(content)) {
                toastShow("请写下您的评论内容")
                return false
            }

            val pictureList = picture_recycler_view.getPictureList()
            var picStr = ""
            var uploadStatus = true
            pictureList.forEach {
                if (TextUtils.isEmpty(it.url)) {
                    uploadStatus = false
                    return@forEach
                }
                picStr += "${it.url};"
            }
            if (!uploadStatus) {
                toastShow("图片未上传成功，请先上传图片")
                return false
            }

            ApiManager.getService().topicToComment(GlobalParam.getUserIdOrJump(), content, picStr, topicId)
                    .compose(RxScheduler.applyScheduler())
                    .subscribe(object : Response<BaseResult<TopicCommentBean>>(mActivity, true) {
                        override fun _onNext(result: BaseResult<TopicCommentBean>) {
                            if (result.code == 0) {
                                EventBus.getDefault().post(UpdateTopicCommentEvent())
                                toastShow("发布成功")
                                finish()
                            } else {
                                toastShow(result.msg)
                            }
                        }
                    })

            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        picture_recycler_view.onActivityResult(requestCode, resultCode, data)
    }

}