package com.ipd.rainbow.receiver

import android.app.ActivityManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import cn.jpush.android.api.JPushInterface
import com.ipd.rainbow.ui.activity.message.MessageActivity
import com.ipd.rainbow.ui.activity.order.OrderDetailActivity
import org.json.JSONException
import org.json.JSONObject

class PushReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val bundle = intent.extras
        Log.e("push", "onReceive - " + intent.action!!)
        printBundle(bundle)

        when {
            JPushInterface.ACTION_REGISTRATION_ID == intent.action -> {
                //JPush用户注册成功

            }
            JPushInterface.ACTION_MESSAGE_RECEIVED == intent.action -> { //接受到推送下来的自定义消息
                // Push Talk messages are push down by custom message format

            }
            JPushInterface.ACTION_NOTIFICATION_RECEIVED == intent.action -> { //接受到推送下来的通知

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
        if (!TextUtils.isEmpty(extras)) {
            val jsonObject = JSONObject(extras)
            //            jsonObject = jsonObject.getJSONObject("extra");
            val messageType = jsonObject.getString("TYPE")
            when (messageType.toInt()) {
                1 -> {
                    val category = jsonObject.getString("CATEGORY")
                    var pos = 0
                    when (category.toInt()) {
                        1 -> {
                            pos = 0
                        }
                        2 -> {
                            pos = 1
                        }
                        3 -> {
                            pos = 2
                        }
                    }

                    val intent = Intent(context, MessageActivity::class.java)
                    intent.putExtra("pos", pos)
                    openActivity(context, intent)
                }
                2 -> {
                    val orderId = jsonObject.getInt("ORDER_ID")
                    val intent = Intent(context, OrderDetailActivity::class.java)
                    intent.putExtra("orderId", orderId)
                    openActivity(context, intent)
                }
            }
        }
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