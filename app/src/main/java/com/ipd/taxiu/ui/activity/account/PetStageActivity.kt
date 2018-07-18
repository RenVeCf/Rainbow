package com.ipd.taxiu.ui.activity.account

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.ipd.taxiu.R
import com.ipd.taxiu.ui.BaseUIActivity
import kotlinx.android.synthetic.main.activity_pet_stage.*

class PetStageActivity : BaseUIActivity() {

    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, PetStageActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "您目前是"
    override fun getContentLayout(): Int = R.layout.activity_pet_stage

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
    }

    override fun initListener() {
        fl_pet_level_old.setOnClickListener {
            setChecked(iv_old.id)
        }
        fl_pet_level_new.setOnClickListener {
            setChecked(iv_new.id)
        }
        fl_pet_level_ready.setOnClickListener {
            setChecked(iv_ready.id)
        }
        btn_next.setOnClickListener { PetKindActivity.launch(mActivity)}
    }

    private fun setChecked(resId: Int) {
        iv_old.isSelected = resId == R.id.iv_old
        iv_new.isSelected = resId == R.id.iv_new
        iv_ready.isSelected = resId == R.id.iv_ready
        btn_next.isEnabled = true
    }
}