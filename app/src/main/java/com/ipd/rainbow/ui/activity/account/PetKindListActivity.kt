package com.ipd.rainbow.ui.activity.account

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.ipd.rainbow.R
import com.ipd.rainbow.ui.BaseUIActivity
import com.ipd.rainbow.ui.fragment.account.PetKindListFragment

class PetKindListActivity : BaseUIActivity() {

    companion object {
        val DOG = 1
        val CAT = 2

        fun launch(activity: Activity, type: Int, userId: String) {
            val intent = Intent(activity, PetKindListActivity::class.java)
            intent.putExtra("type", type)
            intent.putExtra("userId", userId)
            activity.startActivity(intent)
        }
    }

    private val mType: Int by lazy { intent.getIntExtra("type", DOG) }
    private val mUserId: String by lazy { intent.getStringExtra("userId") }
    override fun getToolbarTitle(): String = "${if (mType == DOG) "汪星人" else "喵星人"}品种大全"
    override fun getContentLayout(): Int = R.layout.activity_container

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
        supportFragmentManager.beginTransaction().replace(R.id.fl_container, PetKindListFragment.newInstance(mType,mUserId)).commit()
    }

    override fun initListener() {

    }
}