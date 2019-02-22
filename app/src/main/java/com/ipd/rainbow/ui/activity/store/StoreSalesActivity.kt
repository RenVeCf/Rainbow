package com.ipd.rainbow.ui.activity.store

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.widget.Toolbar
import com.ipd.rainbow.R
import com.ipd.rainbow.adapter.NewProductAdapter
import com.ipd.rainbow.adapter.ProductAdapter
import com.ipd.rainbow.ui.BaseUIActivity
import com.ipd.rainbow.ui.fragment.store.SalesProductListFragment
import kotlinx.android.synthetic.main.activity_store_sales.*
import kotlinx.android.synthetic.main.store_sales_toolbar.*

/**
Created by Miss on 2018/8/10
我的拼团
 */
class StoreSalesActivity : BaseUIActivity() {
    private val titles = arrayOf("每日上新", "今日特价", "优选库存")

    companion object {
        fun launch(activity: Activity, position: Int) {
            val intent = Intent(activity, StoreSalesActivity::class.java)
            intent.putExtra("position", position)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarLayout(): Int = R.layout.store_sales_toolbar
    override fun getContentLayout(): Int = R.layout.activity_store_sales
    override fun initToolbar() {
        val toolbar: Toolbar? = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        toolbar?.setNavigationIcon(R.mipmap.back)
        toolbar?.setNavigationOnClickListener { onBackPressed() }
    }

    override fun initView(bundle: Bundle?) {
        initToolbar()

    }

    private val fragments = Array(titles.size) {
        SalesProductListFragment.newInstance(when (it) {
            0 -> 2//上新
            1 -> 1//特价
            else -> 3//库存
        })

    }

    private val mPosition by lazy { intent.getIntExtra("position", 0) }
    override fun loadData() {
        view_pager.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return fragments[position]
            }

            override fun getCount(): Int = titles.size
        }
        tab_layout.setViewPager(view_pager, titles)
        view_pager.currentItem = mPosition
    }

    var mCurShowType = NewProductAdapter.ItemType.LIST
    override fun initListener() {
        iv_show_type.setOnClickListener {
            mCurShowType = if (mCurShowType == NewProductAdapter.ItemType.LIST) NewProductAdapter.ItemType.GRID else NewProductAdapter.ItemType.LIST
            fragments?.forEach {
                it.setShowType(mCurShowType)
            }
            iv_show_type.setImageResource(if (mCurShowType == ProductAdapter.ItemType.LIST) R.mipmap.product_list_list else R.mipmap.product_list_grid)

        }
    }


}