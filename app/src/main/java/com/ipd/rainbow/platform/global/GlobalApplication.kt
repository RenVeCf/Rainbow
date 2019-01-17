package com.ipd.rainbow.platform.global

import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.os.Process
import android.support.multidex.MultiDexApplication
import cn.jpush.android.api.JPushInterface
import com.mob.MobSDK
import com.tencent.bugly.crashreport.CrashReport
import kotlin.properties.Delegates


/**
 * Created by jumpbox on 2018/6/6.
 */
class GlobalApplication : MultiDexApplication() {
    companion object {
        var mContext: Application by Delegates.notNull()
        fun getCurProcessName(ctx: Context, pid: Int): String? {
            val activityManager = ctx.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val runningAppProcesses = activityManager.runningAppProcesses
            runningAppProcesses.forEach {
                if (it.pid == pid) {
                    return it.processName
                }
            }
            return null
        }


    }


    override fun onCreate() {
        super.onCreate()
        if (getCurProcessName(this, Process.myPid()) != packageName) {
            return
        }

        mContext = this@GlobalApplication

//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return
//        }
//        LeakCanary.install(this)

        CrashReport.initCrashReport(applicationContext, "5236c8068d", false)
//        Ntalker.getBaseInstance().initSDK(mContext, Constant.XIAONENG_ID, Constant.XIAONENG_SDK_KEY)
        JPushInterface.init(this)

        MobSDK.init(this)
    }


}