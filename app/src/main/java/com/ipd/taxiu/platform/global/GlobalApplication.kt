package com.ipd.taxiu.platform.global

import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.support.multidex.MultiDexApplication
import com.squareup.leakcanary.LeakCanary
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
        mContext = this@GlobalApplication
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        LeakCanary.install(this)

    }


}