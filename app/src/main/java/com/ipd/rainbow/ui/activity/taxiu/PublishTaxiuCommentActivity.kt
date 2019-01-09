package com.ipd.rainbow.ui.activity.taxiu

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import com.ipd.rainbow.R
import com.ipd.rainbow.bean.BaseResult
import com.ipd.rainbow.bean.TaxiuCommentBean
import com.ipd.rainbow.event.UpdateTaxiuCommentEvent
import com.ipd.rainbow.platform.global.GlobalParam
import com.ipd.rainbow.platform.http.ApiManager
import com.ipd.rainbow.platform.http.Response
import com.ipd.rainbow.platform.http.RxScheduler
import com.ipd.rainbow.ui.BaseUIActivity
import kotlinx.android.synthetic.main.activity_publish_topic_comment.*
import org.greenrobot.eventbus.EventBus

class PublishTaxiuCommentActivity : BaseUIActivity() {

    companion object {
        fun launch(activity: Activity, taxiuId: Int) {
            val intent = Intent(activity, PublishTaxiuCommentActivity::class.java)
            intent.putExtra("taxiuId", taxiuId)
            activity.startActivity(intent)
        }
    }

    private val taxiuId: Int by lazy { intent.getIntExtra("taxiuId", -1) }

    override fun getToolbarTitle(): String = "写评论"

    override fun getContentLayout(): Int = R.layout.activity_publish_topic_comment

    override fun initView(bundle: Bundle?) {
        initToolbar()
        et_content.hint = "写下您的评论内容"
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

            ApiManager.getService().taxiuToComment(GlobalParam.getUserIdOrJump(), content, picStr, taxiuId)
                    .compose(RxScheduler.applyScheduler())
                    .subscribe(object : Response<BaseResult<TaxiuCommentBean>>(mActivity, true) {
                        override fun _onNext(result: BaseResult<TaxiuCommentBean>) {
                            if (result.code == 0) {
                                EventBus.getDefault().post(UpdateTaxiuCommentEvent())
                                toastShow(true,"发布成功")
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