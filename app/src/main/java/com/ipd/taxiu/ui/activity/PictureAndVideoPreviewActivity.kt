package com.ipd.taxiu.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.jzvd.Jzvd
import com.github.chrisbanes.photoview.PhotoView
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.BannerBean
import com.ipd.taxiu.imageload.ImageLoader
import com.ipd.taxiu.platform.http.HttpUrl
import com.ipd.taxiu.ui.BaseActivity
import com.ipd.taxiu.utils.PictureUtils
import kotlinx.android.synthetic.main.activity_picture_look.*
import kotlinx.android.synthetic.main.layout_video_preview.view.*


/**
 * Created by jumpbox on 2017/8/7.
 */

class PictureAndVideoPreviewActivity : BaseActivity() {

    companion object {
        fun launch(activity: Activity, pictureList: ArrayList<BannerBean>, curPos: Int = 0) {
            val intent = Intent(activity, PictureAndVideoPreviewActivity::class.java)
            intent.putParcelableArrayListExtra("list", pictureList)
            intent.putExtra("curPos", curPos)
            activity.startActivity(intent)
        }
    }


    private var mPictureList: ArrayList<BannerBean>? = null
    private var mCurPos: Int = 0

    override fun getBaseLayout(): Int {
        return R.layout.activity_picture_video_look
    }

    override fun initView(bundle: Bundle?) {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)

        mPictureList = intent.getParcelableArrayListExtra<BannerBean>("list")
        if (mPictureList == null || mPictureList!!.isEmpty()) {
            finish()
        }

        mCurPos = intent.getIntExtra("curPos", 0)
        tv_index.text = (mCurPos + 1).toString() + "/" + mPictureList!!.size
    }

    override fun loadData() {

    }

    override fun initListener() {
        view_pager.adapter = object : PagerAdapter() {
            override fun getCount(): Int {
                return if (mPictureList == null) 0 else mPictureList!!.size
            }

            override fun isViewFromObject(view: View, `object`: Any): Boolean {
                return view === `object`
            }

            override fun instantiateItem(container: ViewGroup, position: Int): Any {
                lateinit var contentView: View
                val bannerInfo = mPictureList!![position]
                if (bannerInfo.isVideo) {
                    contentView = LayoutInflater.from(mActivity).inflate(R.layout.layout_video_preview, container, false)
                    contentView.video_player.setUp(HttpUrl.VIDEO_URL + bannerInfo.videoUrl, "", Jzvd.SCREEN_WINDOW_FULLSCREEN)
                } else {
                    contentView = LayoutInflater.from(mActivity).inflate(R.layout.layout_preview, container, false)
                    val photoView = contentView.findViewById<View>(R.id.photo_view) as PhotoView
                    val saveView = contentView.findViewById<View>(R.id.tv_save)
                    val imagePath = bannerInfo.LOGO
                    ImageLoader.loadImgWithPlaceHolder(mActivity, imagePath, R.mipmap.banner_default, photoView)
                    photoView.setOnClickListener {
                        finish()
                    }
                    saveView.setOnClickListener {
                        saveView.isEnabled = false
                        PictureUtils.savePhotoAndRefreshGallery(mActivity, imagePath, {
                            saveView.isEnabled = true
                            toastShow(it == 0, if (it == 0) "保存成功" else "保存失败")
                        })
                    }

                }

                container.addView(contentView)
                return contentView
            }

            override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
                container.removeView(`object` as View)
            }
        }

        view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                tv_index.text = (position + 1).toString() + "/" + mPictureList!!.size
                Jzvd.releaseAllVideos()
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })

        view_pager.currentItem = mCurPos

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
