package com.ipd.taxiu

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.ipd.taxiu.platform.global.GlobalParam
import com.ipd.taxiu.ui.BaseActivity
import com.ipd.taxiu.ui.activity.account.LoginActivity
import kotlinx.android.synthetic.main.activity_welcome.*


/**
 * Created by jumpbox on 2018/5/9.
 */
class WelcomeActivity : BaseActivity() {
    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, WelcomeActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getBaseLayout(): Int = R.layout.activity_welcome

    override fun initView(bundle: Bundle?) {
        if (Build.VERSION.SDK_INT in 12..18) { // lower api
            val v = this.window.decorView
            v.systemUiVisibility = View.GONE
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            val decorView = window.decorView
            val uiOptions = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_FULLSCREEN)
            decorView.systemUiVisibility = uiOptions

        }
    }

    override fun loadData() {
        view_pager.adapter = object : PagerAdapter() {

            private val images = intArrayOf(R.mipmap.welcome_1, R.mipmap.welcome_2, R.mipmap.welcome_3, R.mipmap.welcome_4)
            override fun getCount(): Int {
                return images.size
            }

            override fun isViewFromObject(view: View, `object`: Any): Boolean {
                return view === `object`
            }

            override fun instantiateItem(container: ViewGroup, position: Int): Any {
                val imageView = LayoutInflater.from(mActivity).inflate(R.layout.layout_welcome, container, false) as ImageView
                imageView.setImageResource(images[position])
                imageView.setOnClickListener {
                    if (position == images.size - 1) {
                        GlobalParam.setFirstEnter(false)
                        if (GlobalParam.isLogin()) {
                            MainActivity.launch(mActivity)
                        } else {
                            LoginActivity.launch(mActivity)
                        }
                    }

                }
                container.addView(imageView)
                return imageView
            }

            override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
                container.removeView(`object` as View)
            }
        }
    }

    override fun initListener() {
    }


}