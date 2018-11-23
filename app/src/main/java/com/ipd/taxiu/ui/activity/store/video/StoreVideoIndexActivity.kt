package com.ipd.taxiu.ui.activity.store.video

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.view.View
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.BaseResult
import com.ipd.taxiu.bean.StoreVideoBean
import com.ipd.taxiu.bean.StoreVideoTabBean
import com.ipd.taxiu.imageload.ImageLoader
import com.ipd.taxiu.platform.global.GlobalParam
import com.ipd.taxiu.platform.http.ApiManager
import com.ipd.taxiu.platform.http.Response
import com.ipd.taxiu.platform.http.RxScheduler
import com.ipd.taxiu.ui.BaseUIActivity
import com.ipd.taxiu.ui.fragment.store.StoreVideoListFragment
import com.ipd.taxiu.utils.StorePetSpecialType
import kotlinx.android.synthetic.main.activity_store_video_index.*
import kotlinx.android.synthetic.main.item_list_store_video.*
import rx.Observable

class StoreVideoIndexActivity : BaseUIActivity() {

    companion object {
        fun launch(activity: Activity, type: Int) {
            val intent = Intent(activity, StoreVideoIndexActivity::class.java)
            intent.putExtra("type", type)
            activity.startActivity(intent)
        }
    }

    private val mType: Int by lazy { intent.getIntExtra("type", StorePetSpecialType.DOG) }
    override fun getToolbarTitle(): String = "潮品视频"

    override fun getContentLayout(): Int = R.layout.activity_store_video_index

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {

        val todayRecommendVideo = ApiManager.getService().storeTodayRecommendVideo(GlobalParam.getUserIdOrJump(), mType)
        val videoTabs = ApiManager.getService().storeVideoTabs(GlobalParam.getUserIdOrJump(), mType)

        showProgress()
        Observable.zip(todayRecommendVideo, videoTabs, { today, tabs ->
            val list = arrayListOf<Any>()
            list.add(today)
            list.add(tabs)
            list
        }).compose(RxScheduler.applyScheduler())
                .subscribe(object : Response<List<Any>>() {
                    override fun _onNext(result: List<Any>) {
                        val todayResult = result[0] as BaseResult<StoreVideoBean>
                        val tabsResult = result[1] as BaseResult<List<StoreVideoTabBean>>
                        when {
                            tabsResult.code != 0 -> showError(tabsResult.msg)
                            else -> {
                                showContent()
                                if (todayResult.code != 0 || todayResult == null || todayResult.data == null){
                                    stickynavlayout_topview.visibility = View.GONE
                                }else{
                                    stickynavlayout_topview.visibility = View.VISIBLE
                                    val todayInfo = todayResult.data
                                    cl_content.setOnClickListener {
                                        //今日推荐视频详情
                                        StoreVideoDetailActivity.launch(mActivity, todayInfo.VIDEO_ID.toString())
                                    }
                                    ImageLoader.loadNoPlaceHolderImg(mActivity, todayInfo.LOGO, iv_image)
                                    tv_taxiu_name.text = todayInfo.TITLE
                                    tv_video_viewers.text = todayInfo.BROWSE.toString()
                                    tv_video_time.text = todayInfo.TIME_LENGTH
                                }

                                setupTabs(tabsResult.data)
                            }
                        }

                    }

                    override fun onError(e: Throwable?) {
                        showError("连接服务器失败")
                    }
                })


    }

    private fun setupTabs(tabs: List<StoreVideoTabBean>) {
        view_pager.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return StoreVideoListFragment.newInstance(mType, tabs[position].SHOP_TYPE_ID)
            }

            override fun getCount(): Int = tabs.size

            override fun getPageTitle(position: Int): CharSequence {
                return tabs[position].TYPE_NAME
            }
        }
        tab_layout.setupWithViewPager(view_pager)
    }

    override fun initListener() {
    }

}