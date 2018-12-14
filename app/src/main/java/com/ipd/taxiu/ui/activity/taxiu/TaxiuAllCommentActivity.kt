package com.ipd.taxiu.ui.activity.taxiu

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.ipd.taxiu.R
import com.ipd.taxiu.ui.BaseUIActivity
import com.ipd.taxiu.ui.fragment.taxiu.TaxiuAllCommentFragment

class TaxiuAllCommentActivity : BaseUIActivity() {

    companion object {
        fun launch(activity: Activity, taxiuId: Int = -1, isAdmin: Boolean = false) {
            val intent = Intent(activity, TaxiuAllCommentActivity::class.java)
            intent.putExtra("taxiuId", taxiuId)
            intent.putExtra("isAdmin", isAdmin)
            activity.startActivity(intent)
        }
    }

    private val isAdmin: Boolean by lazy { intent.getBooleanExtra("isAdmin", false) }
    private val taxiuId: Int by lazy { intent.getIntExtra("taxiuId", -1) }

    override fun getToolbarTitle(): String = "用户评论"

    override fun getContentLayout(): Int = R.layout.activity_container

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
        supportFragmentManager.beginTransaction().replace(R.id.fl_container, TaxiuAllCommentFragment.newInstance(taxiuId)).commit()
    }

    override fun initListener() {
    }


}