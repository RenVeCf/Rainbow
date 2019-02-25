package com.ipd.rainbow.ui.activity.referral

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import cn.sharesdk.framework.Platform
import com.ipd.rainbow.R
import com.ipd.rainbow.bean.BaseResult
import com.ipd.rainbow.bean.RecommendInfoBean
import com.ipd.rainbow.imageload.ImageLoader
import com.ipd.rainbow.platform.global.Constant
import com.ipd.rainbow.platform.global.GlobalParam
import com.ipd.rainbow.platform.http.ApiManager
import com.ipd.rainbow.platform.http.HttpUrl
import com.ipd.rainbow.platform.http.Response
import com.ipd.rainbow.platform.http.RxScheduler
import com.ipd.rainbow.ui.BaseUIActivity
import com.ipd.rainbow.utils.ShareCallback
import com.ipd.rainbow.widget.ShareDialog
import com.ipd.rainbow.widget.ShareDialogClick
import kotlinx.android.synthetic.main.activity_referral_code.*
import kotlinx.android.synthetic.main.layout_referral_code.*
import java.util.*

/**
 * Created by Miss on 2018/7/25
 * 我的推荐码
 */
class ReferralCodeActivity : BaseUIActivity() {


    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, ReferralCodeActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarLayout(): Int = R.layout.referral_toolbar
    override fun getToolbarTitle() = "我的二维码"

    override fun getContentLayout() = R.layout.activity_referral_code

    override fun initToolbar() {
        super.initToolbar()
        val toolbar: Toolbar? = findViewById(R.id.toolbar)
        toolbar?.setNavigationIcon(R.mipmap.back_referral)
    }

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
        showProgress()
        ApiManager.getService().recommendInfo(GlobalParam.getUserIdOrJump())
                .compose(RxScheduler.applyScheduler())
                .subscribe(object : Response<BaseResult<RecommendInfoBean>>() {
                    override fun _onNext(result: BaseResult<RecommendInfoBean>) {
                        if (result.code == 0) {
                            val info = result.data
                            ImageLoader.loadAvatar(mActivity, info.LOGO, civ_my_header)
                            header_name.text = info.NICKNAME
                            tv_recommend_code.text = "推荐码:${info.MY_CODE}"
                            ImageLoader.loadNoPlaceHolderImg(mActivity, info.TWO_CODE, iv_qr_code)

                            iv_wechat.setOnClickListener {
                                getShareClick().WechatOnclick()
                            }
                            iv_friend.setOnClickListener {
                                getShareClick().momentsOnclick()
                            }


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

    private fun getShareClick(): ShareDialogClick {
        return ShareDialogClick()
                .setShareTitle(Constant.SHARE_INVITE_TITLE)
                .setShareContent(Constant.SHARE_INVITE_CONTENT)
                .setShareLogoUrl("")
                .setShareUrl(HttpUrl.APK_DOWNLOAD_URL)
                .setCallback(object : ShareDialogClick.MainPlatformActionListener {
                    override fun onComplete(platform: Platform?, i: Int, hashMap: HashMap<String, Any>?) {
                        toastShow(true, "分享成功")
                        ShareCallback.shareUser()
                    }

                    override fun onError(platform: Platform?, i: Int, throwable: Throwable?) {
                        toastShow("分享失败")
                    }

                    override fun onCancel(platform: Platform?, i: Int) {
                        toastShow("取消分享")
                    }

                })
    }

    override fun initListener() {
    }


}
