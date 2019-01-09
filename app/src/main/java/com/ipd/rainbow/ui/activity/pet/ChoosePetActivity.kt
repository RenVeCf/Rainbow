package com.ipd.rainbow.ui.activity.pet

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.ipd.rainbow.R
import com.ipd.rainbow.platform.global.GlobalParam
import com.ipd.rainbow.ui.BaseUIActivity
import com.ipd.rainbow.ui.activity.account.PetKindListActivity
import kotlinx.android.synthetic.main.activity_pet_kind.*

class ChoosePetActivity : BaseUIActivity() {

    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, ChoosePetActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "选择宠物种类"
    override fun getContentLayout(): Int = R.layout.activity_choose_pet_kind


    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
    }

    override fun initListener() {
        rl_dog.setOnClickListener {
            PetKindListActivity.launch(mActivity, PetKindListActivity.DOG,GlobalParam.getUserIdOrJump())
            finish()
        }
        rl_cat.setOnClickListener {
            PetKindListActivity.launch(mActivity, PetKindListActivity.CAT,GlobalParam.getUserIdOrJump())
            finish()
        }
    }
}