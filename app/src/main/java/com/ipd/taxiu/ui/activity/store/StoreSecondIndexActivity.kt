package com.ipd.taxiu.ui.activity.store

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.StoreAreaBean
import com.ipd.taxiu.ui.BaseUIActivity
import com.ipd.taxiu.ui.fragment.store.StoreSecondIndexFragment
import com.ipd.taxiu.utils.StorePetSpecialType
import com.ipd.taxiu.widget.CurFragmentPagerAdapter
import kotlinx.android.synthetic.main.activity_store_second_index.*
import kotlinx.android.synthetic.main.second_store_toolbar.*

class StoreSecondIndexActivity : BaseUIActivity() {

    companion object {
        fun launch(activity: Activity, type: Int, areaList: List<StoreAreaBean>, pos: Int) {
            val intent = Intent(activity, StoreSecondIndexActivity::class.java)
            intent.putExtra("type", type)
            intent.putExtra("pos", pos)
            intent.putParcelableArrayListExtra("areaList", ArrayList(areaList))
            activity.startActivity(intent)
        }
    }

    override fun getToolbarLayout(): Int = R.layout.second_store_toolbar
    override fun getContentLayout(): Int = R.layout.activity_store_second_index

    private val mType: Int by lazy { intent.getIntExtra("type", StorePetSpecialType.DOG) }
    private val mPos: Int by lazy { intent.getIntExtra("pos", 0) }
    private val mAreaList: ArrayList<StoreAreaBean> by lazy { intent.getParcelableArrayListExtra<StoreAreaBean>("areaList") }
//    private val mAreaId by lazy {
//        mAreaList?.get(mPos)?.SHOP_AREA_ID
//    }

    override fun initView(bundle: Bundle?) {

    }

    override fun loadData() {
        view_pager.adapter = object : CurFragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return StoreSecondIndexFragment.newInstance(mType, mAreaList[position].SHOP_AREA_ID)
            }

            override fun getCount(): Int {
//                return when (StorePetSpecialType.getParentTypeByType(mType)) {
//                    StorePetSpecialType.DOG -> StorePetSpecialType.DOG_TABS.size
//                    StorePetSpecialType.CAT -> StorePetSpecialType.CAT_TABS.size
//                    else -> 0
//                }
                return mAreaList.size
            }

            override fun getPageTitle(position: Int): CharSequence {
//                return when (StorePetSpecialType.getParentTypeByType(mType)) {
//                    StorePetSpecialType.DOG -> StorePetSpecialType.DOG_TAB_TITLES[position]
//                    StorePetSpecialType.CAT -> StorePetSpecialType.CAT_TAB_TITLES[position]
//                    else -> ""
//                }
                return mAreaList[position].AREA_NAME
            }

        }
//        view_pager.currentItem = StorePetSpecialType.getPositionByType(mType)
        view_pager.currentItem = mPos
        tab_layout.setupWithViewPager(view_pager)
    }

    override fun initListener() {
        iv_back.setOnClickListener { finish() }

        tv_search.setOnClickListener {
            StoreSearchActivity.launch(mActivity)
        }

        iv_category.setOnClickListener {
            ProductCategoryActivity.launch(mActivity)
        }

    }
}