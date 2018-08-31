package com.ipd.taxiu.ui.activity.classroom

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.Menu
import android.view.MenuItem
import com.ipd.taxiu.R
import com.ipd.taxiu.ui.BaseUIActivity
import com.ipd.taxiu.utils.HtmlImageGetter
import kotlinx.android.synthetic.main.activity_classroom_detail.*

class OwnedClassRoomActivity : BaseUIActivity() {

    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, OwnedClassRoomActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "已购买课堂"

    override fun getContentLayout(): Int = R.layout.activity_own_classroom

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {

    }

    override fun initListener() {
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_classroom_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.classroom_detail) {
            ClassRoomDetailActivity.launch(this,-1)
            return true
        }

        return super.onOptionsItemSelected(item)
    }

}