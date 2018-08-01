package com.ipd.taxiu.ui.activity.taxiu

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.*
import com.ipd.taxiu.R
import com.ipd.taxiu.ui.BaseUIActivity
import kotlinx.android.synthetic.main.activity_publish_taxiu.*

class PublishTaxiuActivity : BaseUIActivity() {

    companion object {
        val VIDEO = 0
        val IMAGE = 1
        fun launch(activity: Activity, type: Int) {
            val intent = Intent(activity, PublishTaxiuActivity::class.java)
            intent.putExtra("type", type)
            activity.startActivity(intent)
        }
    }

    private val mType: Int by lazy { intent.getIntExtra("type", VIDEO) }

    override fun getToolbarTitle(): String = if (mType == VIDEO) "发布视频" else "发布图片"

    override fun getContentLayout(): Int = R.layout.activity_publish_taxiu

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
        if (mType == VIDEO) {
            ll_video.visibility = View.VISIBLE
            picture_recycler_view.visibility = View.GONE

        } else {
            ll_video.visibility = View.GONE
            picture_recycler_view.visibility = View.VISIBLE
            picture_recycler_view.init()
        }
        for (j in 0..11) {
            lable_layout.addView()
        }
    }

    override fun initListener() {
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.publish_topic_comment_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_publish) {
            //发布
            toastShow(true, "发布成功")
            finish()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        picture_recycler_view.onActivityResult(requestCode, resultCode, data)
    }

}