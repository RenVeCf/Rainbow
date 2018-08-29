package com.ipd.taxiu.ui.activity.talk

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.BaseResult
import com.ipd.taxiu.bean.TalkBean
import com.ipd.taxiu.event.UpdateTalkListEvent
import com.ipd.taxiu.platform.global.GlobalParam
import com.ipd.taxiu.platform.http.ApiManager
import com.ipd.taxiu.platform.http.Response
import com.ipd.taxiu.platform.http.RxScheduler
import com.ipd.taxiu.ui.BaseUIActivity
import kotlinx.android.synthetic.main.activity_publish_talk.*
import org.greenrobot.eventbus.EventBus

class PublishTalkActivity : BaseUIActivity() {

    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, PublishTalkActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "发布问答"

    override fun getContentLayout(): Int = R.layout.activity_publish_talk

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {

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
            val content = et_content.text.toString().trim()
            val score = et_score.text.toString().trim()
            if (TextUtils.isEmpty(content)) {
                toastShow("请输入您的疑惑")
                return false
            }

            ApiManager.getService().publishTalk(GlobalParam.getUserIdOrJump(), content, if (TextUtils.isEmpty(score)) "0" else score)
                    .compose(RxScheduler.applyScheduler())
                    .subscribe(object : Response<BaseResult<TalkBean>>(mActivity, true) {
                        override fun _onNext(result: BaseResult<TalkBean>) {
                            if (result.code == 0) {
                                EventBus.getDefault().post(UpdateTalkListEvent())
                                toastShow(true, "发布成功")
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


}