package com.ipd.taxiu.receiver

import android.app.ActivityManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import cn.jpush.android.api.JPushInterface
import org.json.JSONException

class PushReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val bundle = intent.extras
        Log.e("push", "onReceive - " + intent.action!!)
        printBundle(bundle)

        when {
            JPushInterface.ACTION_REGISTRATION_ID == intent.action -> {
                //JPush用户注册成功

            }
            JPushInterface.ACTION_MESSAGE_RECEIVED == intent.action -> //接受到推送下来的自定义消息
                // Push Talk messages are push down by custom message format

                try {
                    processMessage(context, bundle!!)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            JPushInterface.ACTION_NOTIFICATION_RECEIVED == intent.action -> //接受到推送下来的通知
                try {
                    processMessage(context, bundle!!)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            JPushInterface.ACTION_NOTIFICATION_OPENED == intent.action -> //用户点击打开了通知
                try {
                    processMessage(context, bundle!!)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            else -> {
                //未处理action

            }
        }

    }


    fun printBundle(bundle: Bundle?) {
        for (key in bundle!!.keySet()) {
            Log.e("Bundle Content", "Key=" + key + ", content=" + bundle.getString(key))
        }
    }

    @Throws(JSONException::class)
    private fun processMessage(context: Context, bundle: Bundle) {
        var extras = bundle.getString(JPushInterface.EXTRA_EXTRA)
//        extras = extras!!.replace("\"".toRegex(), "")//去掉反斜杠
//        if (!TextUtils.isEmpty(extras)) {
//            val jsonObject = JSONObject(extras)
//            //            jsonObject = jsonObject.getJSONObject("extra");
//            val messageType = jsonObject.getString("type")
//            if (messageType == "money") {
//                //未支付的问诊
//                val topActivity = getTopActivity(context)
//                if (!TextUtils.isEmpty(topActivity) && !topActivity!!.contains("ImageTextInquiryPayActivity")) {
//                    val inquiryNo = jsonObject.getString("number")
//                    val intent = Intent(context, ImageTextInquiryPayActivity::class.java)
//                    intent.putExtra("inquiryId", inquiryNo)
//                    openActivity(context, intent)
//                }
//            }
//        }
    }


    private fun openActivity(context: Context, intent: Intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }


    private fun getTopActivity(context: Context): String? {
        val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val info = manager.getRunningTasks(1)[0]
        return info?.topActivity?.className
    }
}