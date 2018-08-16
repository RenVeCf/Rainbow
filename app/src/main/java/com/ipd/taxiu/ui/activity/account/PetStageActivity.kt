package com.ipd.taxiu.ui.activity.account

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.BaseResult
import com.ipd.taxiu.bean.LoginBean
import com.ipd.taxiu.platform.http.ApiManager
import com.ipd.taxiu.platform.http.Response
import com.ipd.taxiu.platform.http.RxScheduler
import com.ipd.taxiu.ui.BaseUIActivity
import kotlinx.android.synthetic.main.activity_pet_stage.*
import kotlinx.android.synthetic.main.base_toolbar.*

class PetStageActivity : BaseUIActivity() {

    companion object {
        fun launch(activity: Activity, userId: String) {
            val intent = Intent(activity, PetStageActivity::class.java)
            intent.putExtra("userId", userId)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "您目前是"
    override fun getContentLayout(): Int = R.layout.activity_pet_stage

    private val userId: String by lazy { intent.getStringExtra("userId") }

    override fun initToolbar() {
        super.initToolbar()
        toolbar?.navigationIcon = null
    }

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
        btn_next.setOnClickListener {
            ApiManager.getService().petStage(userId, getCheckedStage())
                    .compose(RxScheduler.applyScheduler())
                    .subscribe(object : Response<BaseResult<LoginBean>>(mActivity, true) {
                        override fun _onNext(result: BaseResult<LoginBean>) {
                            if (result.code == 0) {
                                PetKindActivity.launch(mActivity)
                                finish()
                            } else {
                                toastShow(result.msg)
                            }
                        }
                    })

        }
    }

    private fun setChecked(resId: Int) {
        iv_old.isSelected = resId == R.id.iv_old
        iv_new.isSelected = resId == R.id.iv_new
        iv_ready.isSelected = resId == R.id.iv_ready
        btn_next.isEnabled = true
    }

    private fun getCheckedStage(): String {
        if (iv_old.isSelected) return "3"
        if (iv_new.isSelected) return "2"
        if (iv_ready.isSelected) return "1"
        return "1"
    }

    override fun onBackPressed() {
    }
}