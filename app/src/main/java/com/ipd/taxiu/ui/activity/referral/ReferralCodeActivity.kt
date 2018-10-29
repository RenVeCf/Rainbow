package com.ipd.taxiu.ui.activity.referral

import android.content.Intent
import android.os.Bundle
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.BaseResult
import com.ipd.taxiu.bean.RecommendInfoBean
import com.ipd.taxiu.imageload.ImageLoader
import com.ipd.taxiu.platform.global.GlobalParam
import com.ipd.taxiu.platform.http.ApiManager
import com.ipd.taxiu.platform.http.Response
import com.ipd.taxiu.platform.http.RxScheduler
import com.ipd.taxiu.ui.BaseUIActivity
import com.ipd.taxiu.widget.ChooseFriendDialog
import kotlinx.android.synthetic.main.layout_referral_code.*

/**
 * Created by Miss on 2018/7/25
 * 我的推荐码
 */
class ReferralCodeActivity : BaseUIActivity() {
    override fun getToolbarTitle() = "我的推荐码"

    override fun getContentLayout() = R.layout.activity_referral_code

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
                            tv_recommend_code.text ="推荐码:${info.MY_CODE}"
                            ImageLoader.loadNoPlaceHolderImg(mActivity,info.TWO_CODE,iv_qr_code)

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
        btn_invite_friends.setOnClickListener {
            val dialog = ChooseFriendDialog(this, R.style.recharge_pay_dialog, 1)
            dialog.show()
        }
        tv_friend_list.setOnClickListener {
            intent = Intent(this, FriendListActivity::class.java)
            startActivity(intent)
        }
        tv_recommend_earning!!.setOnClickListener {
            intent = Intent(this, RecommendEarningsActivity::class.java)
            startActivity(intent)
        }
    }


}
