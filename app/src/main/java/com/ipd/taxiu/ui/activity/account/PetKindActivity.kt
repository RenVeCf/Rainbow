package com.ipd.taxiu.ui.activity.account

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.ipd.taxiu.ChoosePetKindEvent
import com.ipd.taxiu.R
import com.ipd.taxiu.ui.BaseUIActivity
import com.ipd.taxiu.ui.activity.pet.AddPetActivity
import kotlinx.android.synthetic.main.activity_pet_kind.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class PetKindActivity : BaseUIActivity() {

    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, PetKindActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "选择宠物种类"
    override fun getContentLayout(): Int = R.layout.activity_pet_kind

    override fun onViewAttach() {
        super.onViewAttach()
        EventBus.getDefault().register(this)
    }

    override fun onViewDetach() {
        super.onViewDetach()
        EventBus.getDefault().unregister(this)
    }

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
    }

    override fun initListener() {
        rl_dog.setOnClickListener { PetKindListActivity.launch(mActivity, PetKindListActivity.DOG) }
        rl_cat.setOnClickListener { PetKindListActivity.launch(mActivity, PetKindListActivity.CAT) }
        btn_next.setOnClickListener {
            val intent = Intent(mActivity, AddPetActivity::class.java)
            startActivity(intent)
        }
    }

    @Subscribe
    fun onMainEvent(event: ChoosePetKindEvent) {
        when (event.type) {
            PetKindListActivity.DOG -> {
                tv_cat.visibility = View.GONE
                tv_dog.visibility = View.VISIBLE
                tv_dog.text = String.format(resources.getString(R.string.pet_kind_choose), event.petKind.NAME)
            }
            PetKindListActivity.CAT -> {
                tv_dog.visibility = View.GONE
                tv_cat.visibility = View.VISIBLE
                tv_cat.text = event.petKind.NAME
                tv_cat.text = String.format(resources.getString(R.string.pet_kind_choose), event.petKind.NAME)
            }
        }
        btn_next.isEnabled = true
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_skip, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_skip) {
            //跳过
            val intent = Intent(mActivity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            return true
        }

        return super.onOptionsItemSelected(item)
    }

}