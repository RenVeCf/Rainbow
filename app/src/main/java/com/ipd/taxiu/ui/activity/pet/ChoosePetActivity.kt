package com.ipd.taxiu.ui.activity.pet

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.ipd.jumpbox.jumpboxlibrary.utils.SharedPreferencesUtil
import com.ipd.taxiu.ChoosePetKindEvent
import com.ipd.taxiu.R
import com.ipd.taxiu.ui.BaseUIActivity
import com.ipd.taxiu.ui.activity.account.PetKindListActivity
import kotlinx.android.synthetic.main.activity_pet_kind.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

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
            PetKindListActivity.launch(mActivity, PetKindListActivity.DOG)
            finish()
        }
        rl_cat.setOnClickListener {
            PetKindListActivity.launch(mActivity, PetKindListActivity.CAT)
            finish()
        }
    }
}