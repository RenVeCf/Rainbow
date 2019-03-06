package com.ipd.rainbow.ui.activity.store

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.ipd.rainbow.R
import com.ipd.rainbow.adapter.BannerPagerAdapter
import com.ipd.rainbow.bean.BannerBean
import com.ipd.rainbow.bean.BaseResult
import com.ipd.rainbow.bean.ProductEvaluateBean
import com.ipd.rainbow.imageload.ImageLoader
import com.ipd.rainbow.platform.global.GlobalParam
import com.ipd.rainbow.platform.http.ApiManager
import com.ipd.rainbow.platform.http.Response
import com.ipd.rainbow.platform.http.RxScheduler
import com.ipd.rainbow.ui.BaseUIActivity
import com.ipd.rainbow.ui.activity.PictureLookActivity
import com.ipd.rainbow.utils.StringUtils
import kotlinx.android.synthetic.main.activity_product_evaluate.*
import java.util.*

class ProductEvaluateActivity : BaseUIActivity() {

    companion object {
        fun launch(activity: Activity, evaluateId: Int) {
            val intent = Intent(activity, ProductEvaluateActivity::class.java)
            intent.putExtra("evaluateId", evaluateId)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "评价详情"

    override fun getContentLayout(): Int = R.layout.activity_product_evaluate

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    private val mEvaluateId by lazy { intent.getIntExtra("evaluateId", 0) }
    override fun loadData() {
        showProgress()
        ApiManager.getService().storeProductEvaluateDetail(GlobalParam.getRequestUserId(), mEvaluateId)
                .compose(RxScheduler.applyScheduler())
                .subscribe(object : Response<BaseResult<ProductEvaluateBean>>() {
                    override fun _onNext(result: BaseResult<ProductEvaluateBean>) {
                        if (result.code == 0) {
                            val info = result.data
                            val bannerList = StringUtils.splitImages(info.PIC)
                            evaluate_banner.adapter = BannerPagerAdapter(mActivity, bannerList.map { BannerBean(it) }) { pos, info ->
                                PictureLookActivity.launch(mActivity, ArrayList(bannerList), 0, PictureLookActivity.URL)
                            }

                            ImageLoader.loadAvatar(mActivity, info.USER_LOGO, civ_avatar)
                            tv_nickname.text = info.USER_NICKNAME
                            rating_star.setStar(info.TOTAL_SCORE.toFloat())
                            tv_content.text = info.CONTENT
                            tv_time.text = info.CREATETIME

                            showContent()
                        } else {
                            showError(result.msg)
                        }
                    }

                    override fun onError(e: Throwable?) {
                        showError()
                    }
                })
    }

    override fun initListener() {
    }


}