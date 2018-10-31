package com.ipd.taxiu

import android.os.Bundle
import android.view.View
import com.ipd.taxiu.platform.global.GlobalParam
import com.ipd.taxiu.platform.http.RxScheduler
import com.ipd.taxiu.ui.BaseActivity
import com.ipd.taxiu.ui.activity.account.LoginActivity
import rx.Observable
import rx.Subscriber
import java.util.concurrent.TimeUnit

/**
 * Created by jumpbox on 2018/5/9.
 */
class SplashActivity : BaseActivity() {


    override fun getBaseLayout(): Int = R.layout.activity_splash

    override fun initView(bundle: Bundle?) {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
    }

    override fun loadData() {
        Observable.timer(3000, TimeUnit.MILLISECONDS)
                .compose(RxScheduler.applyScheduler())
                .subscribe(object : Subscriber<Long>() {
                    override fun onError(e: Throwable?) {
                    }

                    override fun onNext(t: Long?) {
                    }

                    override fun onCompleted() {
                        if (GlobalParam.getFirstEnter()) {
                            WelcomeActivity.launch(mActivity)
                        } else {
                            if (GlobalParam.isLogin()) {
                                MainActivity.launch(mActivity)
                            } else {
                                LoginActivity.launch(mActivity)
                            }
                        }
                        finish()
                    }

                })
    }

    override fun initListener() {
    }


}