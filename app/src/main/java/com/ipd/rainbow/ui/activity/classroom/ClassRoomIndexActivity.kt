package com.ipd.rainbow.ui.activity.classroom

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.ipd.rainbow.R
import com.ipd.rainbow.ui.BaseUIActivity
import com.ipd.rainbow.ui.activity.SearchActivity
import com.ipd.rainbow.ui.fragment.classroom.ClassRoomListFragment

class ClassRoomIndexActivity : BaseUIActivity() {

    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, ClassRoomIndexActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "课堂"

    override fun getContentLayout(): Int = R.layout.activity_container

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
        supportFragmentManager.beginTransaction().replace(R.id.fl_container, ClassRoomListFragment.newInstance()).commit()

    }

    override fun initListener() {
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_search) {
            //搜索
            SearchActivity.launch(mActivity, SearchActivity.SearchType.CLASSROOM)
            return true
        }

        return super.onOptionsItemSelected(item)
    }

}