package com.ipd.taxiu.ui.activity.store.video

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.ipd.taxiu.R
import com.ipd.taxiu.adapter.ProductAdapter
import com.ipd.taxiu.bean.ProductBean
import com.ipd.taxiu.ui.BaseUIActivity
import kotlinx.android.synthetic.main.activity_store_video_detail.*

class StoreVideoDetailActivity : BaseUIActivity() {
    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, StoreVideoDetailActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "视频详情"

    override fun getContentLayout(): Int = R.layout.activity_store_video_detail

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
        product_recycler_view.adapter = ProductAdapter(mActivity, arrayListOf(ProductBean(), ProductBean()), {

        })
    }

    override fun initListener() {
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_share, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_share) {
            //分享

            return true
        }

        return super.onOptionsItemSelected(item)
    }


}