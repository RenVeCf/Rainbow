package com.ipd.taxiu.ui.activity.store.video

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import cn.jzvd.Jzvd
import cn.sharesdk.framework.Platform
import com.ipd.taxiu.R
import com.ipd.taxiu.adapter.StoreIndexRecommendVideoAdapter
import com.ipd.taxiu.adapter.VideoProductAdapter
import com.ipd.taxiu.bean.StoreVideoDetailBean
import com.ipd.taxiu.imageload.ImageLoader
import com.ipd.taxiu.platform.global.Constant
import com.ipd.taxiu.platform.http.HttpUrl
import com.ipd.taxiu.presenter.store.StoreVideoDetailPresenter
import com.ipd.taxiu.ui.BaseUIActivity
import com.ipd.taxiu.ui.activity.store.ProductDetailActivity
import com.ipd.taxiu.utils.ShareCallback
import com.ipd.taxiu.widget.ShareDialog
import com.ipd.taxiu.widget.ShareDialogClick
import kotlinx.android.synthetic.main.activity_store_video_detail.*
import kotlinx.android.synthetic.main.item_store_video.*
import java.util.*

class StoreVideoDetailActivity : BaseUIActivity(), StoreVideoDetailPresenter.IStoreVideoDetailView {


    companion object {

        fun launch(activity: Activity, videoId: String) {
            val intent = Intent(activity, StoreVideoDetailActivity::class.java)
            intent.putExtra("videoId", videoId)
            activity.startActivity(intent)
        }
    }

    private val mVideoId: String by lazy { intent.getStringExtra("videoId") }

    override fun getToolbarTitle(): String = "视频详情"

    override fun getContentLayout(): Int = R.layout.activity_store_video_detail

    private var mPresenter: StoreVideoDetailPresenter? = null
    override fun onViewAttach() {
        super.onViewAttach()
        mPresenter = StoreVideoDetailPresenter()
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
        mPresenter?.loadVideoDetail(mVideoId)
    }

    override fun initListener() {

    }

    private var mDetailInfo: StoreVideoDetailBean? = null
    override fun loadVideoDetailSuccess(info: StoreVideoDetailBean) {
        mDetailInfo = info
        showContent()
        video_player.setUp(HttpUrl.VIDEO_URL + info.URL, "", Jzvd.SCREEN_WINDOW_NORMAL)
        video_player.startButton.performClick()
        ImageLoader.loadNoPlaceHolderImg(mActivity, info.LOGO, video_player.thumbImageView)

        tv_taxiu_name.text = info.TITLE
        tv_video_viewers.text = info.BROWSE.toString()
        tv_video_time.text = info.TIME_LENGTH


        tv_video_product.visibility = if (info.PRODUCT_LIST == null || info.PRODUCT_LIST.isEmpty()) View.GONE else View.VISIBLE

        product_recycler_view.adapter = VideoProductAdapter(mActivity, info.PRODUCT_LIST, {
            ProductDetailActivity.launch(mActivity, it.PRODUCT_ID, it.FORM_ID)
        })


        recommend_video_recycler_view.adapter = StoreIndexRecommendVideoAdapter(mActivity, info.VIDEO_LIST, {
            //视频详情
            launch(mActivity, it.VIDEO_ID.toString())
        })

    }

    override fun loadVideoDetailFail(errMsg: String) {
        showError(errMsg)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_share, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_share) {
            //分享
            if (mDetailInfo == null) return true
            val dialog = ShareDialog(mActivity)
            dialog.setShareDialogOnClickListener(ShareDialogClick()
                    .setShareTitle(mDetailInfo?.TITLE ?: "潮品视频")
                    .setShareContent(Constant.SHARE_VIDEO_CONTENT)
                    .setShareLogoUrl(if (TextUtils.isEmpty(mDetailInfo?.LOGO)) "" else HttpUrl.IMAGE_URL + mDetailInfo?.LOGO)
                    .setShareUrl(HttpUrl.APK_DOWNLOAD_URL)
                    .setCallback(object : ShareDialogClick.MainPlatformActionListener {
                        override fun onComplete(platform: Platform?, i: Int, hashMap: HashMap<String, Any>?) {
                            toastShow(true, "分享成功")
                            ShareCallback.shareVideo(mDetailInfo!!.VIDEO_ID)
                        }

                        override fun onError(platform: Platform?, i: Int, throwable: Throwable?) {
                            toastShow("分享失败")
                        }

                        override fun onCancel(platform: Platform?, i: Int) {
                            toastShow("取消分享")
                        }

                    }))
            dialog.show()


            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (Jzvd.backPress()) {
            return
        }
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        Jzvd.releaseAllVideos()
    }


}