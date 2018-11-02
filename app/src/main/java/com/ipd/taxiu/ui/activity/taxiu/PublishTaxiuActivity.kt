package com.ipd.taxiu.ui.activity.taxiu

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.ipd.jumpbox.jumpboxlibrary.utils.MySelfSheetDialog
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.TaxiuLableBean
import com.ipd.taxiu.bean.UploadResultBean
import com.ipd.taxiu.event.VideoResultEvent
import com.ipd.taxiu.imageload.ImageLoader
import com.ipd.taxiu.platform.http.HttpUrl
import com.ipd.taxiu.presenter.store.PublishTaxiuPresenter
import com.ipd.taxiu.ui.BaseUIActivity
import com.ipd.taxiu.ui.activity.ShootVideoActivity
import com.ipd.taxiu.ui.activity.VideoActivity
import com.ipd.taxiu.ui.activity.VideoSelectActivity
import com.ipd.taxiu.ui.activity.web.WebActivity
import com.ipd.taxiu.utils.UploadUtils
import com.ipd.taxiu.utils.trimvideo.TrimVideoUtil
import com.ipd.taxiu.widget.MessageDialog
import kotlinx.android.synthetic.main.activity_publish_taxiu.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

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
        EventBus.getDefault().register(this)
        mPresenter = PublishTaxiuPresenter()
        mPresenter?.attachView(this, this)
    }

    override fun onViewDetach() {
        super.onViewDetach()
        EventBus.getDefault().unregister(this)
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
        ll_video.setOnClickListener {
            MySelfSheetDialog(mActivity).builder().addSheetItem(resources.getString(R.string.shoot_video),
                    MySelfSheetDialog.SheetItemColor.colorPrimaryDark) {
                ShootVideoActivity.launch(mActivity)
            }.addSheetItem(resources.getString(R.string.video_from_local), MySelfSheetDialog.SheetItemColor.colorPrimaryDark) {
                VideoSelectActivity.launch(mActivity)
            }.show()
        }

        ll_video_finish.setOnClickListener {
            if (videoEvent == null || TextUtils.isEmpty(videoEvent!!.videoPath)) return@setOnClickListener
            VideoActivity.launch(mActivity, videoEvent?.videoPath ?: "")
        }

        iv_delete_video.setOnClickListener {
            val builder = MessageDialog.Builder(mActivity)
            builder.setTitle("提示")
                    .setMessage("您确定要移除视频?")
                    .setCommit("确定", { builder ->
                        builder.dismiss()
                        videoEvent = null
                        ll_video_normal.visibility = View.VISIBLE
                        ll_video_finish.visibility = View.GONE
                    })
                    .setCancel("取消", { builder ->
                        builder.dismiss()
                    }).show()
        }

        tv_version_info.setOnClickListener {
            WebActivity.launch(mActivity, WebActivity.URL, HttpUrl.WEB_URL + HttpUrl.VERSION_INFO, "版权说明")
        }
    }


    private var lables: List<TaxiuLableBean>? = null
    override fun loadTaxiuLableSuccess(lables: List<TaxiuLableBean>) {
        this.lables = lables

        setLable()
        ll_lable_title.setOnClickListener {
            isShowMore = !isShowMore
            iv_show_more.setImageResource(if (isShowMore) R.mipmap.arrow_top_gray else R.mipmap.arrow_bottom_gray)
            setLable()
        }

    }

    private var isShowMore: Boolean = false
    private val maxNum = 10
    private fun setLable() {
        lable_layout.removeAllViews()
        if (lables?.size ?: 0 > maxNum && !isShowMore) {
            for (index in 0 until maxNum) {
                lable_layout.addView(lables!![index])
            }
        } else {
            lables?.forEach {
                lable_layout.addView(it)
            }
        }
    }

    override fun loadTaxiuLableFail(errMsg: String) {
        toastShow(errMsg)
    }

    override fun publishTaxiuSuccess() {
        toastShow(true, "发布成功")
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
                if (videoEvent == null) {
                    toastShow("请先选择视频")
                    return false
                }

                TrimVideoUtil.getVideoWidthHeight(videoEvent!!.videoPath, { errorCode, width, height ->
                    if (errorCode == 0) {
                        UploadUtils.uploadVideo(mActivity, true, videoEvent?.videoPath, object : UploadUtils.UploadCallback {
                            override fun onProgress(progress: Int) {

                            }

                            override fun uploadSuccess(resultBean: UploadResultBean) {
                                mPresenter?.publishTaxiuVideo(content, videoEvent!!.videoCover, resultBean.data, lableInfo.SHOW_TIP_ID, width, height)
                            }

                            override fun uploadFail(errMsg: String) {
                                toastShow(errMsg)
                            }

                        })
                    } else {
                        toastShow("未找到视频文件")
                    }
                })
            }

            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        picture_recycler_view.onActivityResult(requestCode, resultCode, data)
    }

    private var videoEvent: VideoResultEvent? = null
    @Subscribe
    fun onMainEvent(event: VideoResultEvent) {
        videoEvent = event
        ll_video_normal.visibility = View.GONE
        ll_video_finish.visibility = View.VISIBLE
        ImageLoader.loadNoPlaceHolderImg(mActivity, event.videoCover, iv_video_img)

    }

}