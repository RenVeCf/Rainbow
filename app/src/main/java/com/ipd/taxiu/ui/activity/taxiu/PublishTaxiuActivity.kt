package com.ipd.taxiu.ui.activity.taxiu

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.TaxiuLableBean
import com.ipd.taxiu.presenter.store.PublishTaxiuPresenter
import com.ipd.taxiu.ui.BaseUIActivity
import kotlinx.android.synthetic.main.activity_publish_taxiu.*

class PublishTaxiuActivity : BaseUIActivity(), PublishTaxiuPresenter.IPublishTaxiuView {

    companion object {
        val VIDEO = 0
        val IMAGE = 1
        fun launch(activity: Activity, type: Int) {
            val intent = Intent(activity, PublishTaxiuActivity::class.java)
            intent.putExtra("type", type)
            activity.startActivity(intent)
        }
    }

    private val mType: Int by lazy { intent.getIntExtra("type", VIDEO) }

    override fun getToolbarTitle(): String = if (mType == VIDEO) "发布视频" else "发布图片"

    override fun getContentLayout(): Int = R.layout.activity_publish_taxiu


    private var mPresenter: PublishTaxiuPresenter? = null
    override fun onViewAttach() {
        super.onViewAttach()
        mPresenter = PublishTaxiuPresenter()
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
        if (mType == VIDEO) {
            ll_video.visibility = View.VISIBLE
            picture_recycler_view.visibility = View.GONE

        } else {
            ll_video.visibility = View.GONE
            picture_recycler_view.visibility = View.VISIBLE
            picture_recycler_view.init()
        }

        mPresenter?.loadTaxiuLable()

    }

    override fun initListener() {
    }

    private var lables: List<TaxiuLableBean>? = null
    override fun loadTaxiuLableSuccess(lables: List<TaxiuLableBean>) {
        this.lables = lables
        lables.forEach {
            lable_layout.addView(it)
        }

    }

    override fun loadTaxiuLableFail(errMsg: String) {
        toastShow(errMsg)
    }

    override fun publishTaxiuSuccess() {
        toastShow("发布成功")
        finish()
    }

    override fun publishTaxiuFail(errMsg: String) {
        toastShow(errMsg)
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
            val checkedPos = lable_layout.getCheckedPos()
            if (TextUtils.isEmpty(content)) {
                toastShow("请输入您此刻的想法")
                return false
            }
            if (checkedPos == -1) {
                toastShow("请选择标签")
                return false
            }
            if (checkedPos >= lables?.size ?: 0) {
                return false
            }
            val lableInfo = lables!![checkedPos]
            if (!cb_user_agent.isChecked) {
                toastShow("请阅读并同意《版权说明》")
                return false
            }
            if (mType == IMAGE) {
                //图片
                val pictureList = picture_recycler_view.getPictureList()
//                if (pictureList.isEmpty()) {
//                    toastShow("至少选择一张图片")
//                    return false
//                }

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
                mPresenter?.publishTaxiuImage(content, picStr, lableInfo.SHOW_TIP_ID)
            } else {
                //视频

            }

            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        picture_recycler_view.onActivityResult(requestCode, resultCode, data)
    }

}