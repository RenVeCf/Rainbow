package com.ipd.rainbow

import android.Manifest
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import com.ipd.rainbow.platform.global.GlobalParam
import com.ipd.rainbow.platform.http.RxScheduler
import com.ipd.rainbow.ui.BaseActivity
import com.ipd.rainbow.ui.activity.account.LoginActivity
import com.tbruyelle.rxpermissions2.RxPermissions
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
        RxPermissions(this)
                .request(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA)
                .subscribe {
                    if (it) {
                        Observable.timer(3000, TimeUnit.MILLISECONDS)
                                .compose(RxScheduler.applyScheduler())
                                .subscribe(object : Subscriber<Long>() {
                                    override fun onError(e: Throwable?) {
                                    }

                                    override fun onNext(t: Long?) {
                                    }

                                    override fun onCompleted() {
//                                        if (GlobalParam.getFirstEnter()) {
//                                            WelcomeActivity.launch(mActivity)
//                                        } else {
                                            if (GlobalParam.isLogin()) {
                                                MainActivity.launch(mActivity)
                                            } else {
                                                LoginActivity.launch(mActivity)
                                            }
//                                        }
                                        finish()
                                    }

                                })
                    } else {
                        val builder = AlertDialog.Builder(mActivity)
                        builder.setMessage("需要开启以下权限才能使用")
                                .setPositiveButton("确定") { dialog, which ->
                                    dialog.dismiss()
                                    loadData()
                                }
                                .setNegativeButton("取消") { dialog, which ->
                                    dialog.dismiss()
                                    finish()
                                }.show()
                    }
                }


    }

    override fun initListener() {
    }


}