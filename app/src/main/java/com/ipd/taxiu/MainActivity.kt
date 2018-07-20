package com.ipd.taxiu

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.view.View
import android.widget.LinearLayout
import com.ipd.taxiu.ui.BaseActivity
import com.ipd.taxiu.ui.fragment.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, MainActivity::class.java)
            activity.startActivity(intent)
        }
    }

    private val fragmentManager: FragmentManager by lazy { supportFragmentManager }

    override fun getBaseLayout(): Int = R.layout.activity_main

    override fun initView(bundle: Bundle?) {
        changePage(0)
    }

    override fun loadData() {
    }

    override fun initListener() {
        tabs.forEachIndexed { index, layout ->
            layout.setOnClickListener {
                changePage(index)
            }
        }

        iv_taxiu_checked.setOnClickListener {

        }//发布它秀
    }


    private fun changePage(pos: Int) {
        setTabChecked(pos)
        setTabSelection(pos)
    }


    private val tabs: Array<LinearLayout> by lazy { arrayOf(ll_home, ll_store, ll_taxiu, ll_cart, ll_mine) }
    private fun setTabChecked(pos: Int) {
        tabs.forEachIndexed { index, layout ->
            layout.getChildAt(0).isSelected = pos == index
            layout.getChildAt(1).isSelected = pos == index
        }
        tabs[2].visibility = if (pos == 2) View.INVISIBLE else View.VISIBLE
        iv_taxiu_checked.visibility = if (pos == 2) View.VISIBLE else View.GONE
    }


    private var homeFragment: HomeFragment? = null
    private var storeFragment: StoreFragment? = null
    private var taxiuFragment: TaxiuFragment? = null
    private var cartFragment: CartFragment? = null
    private var mineFragment: PersonFragment? = null

    /**
     * 选中的页面
     *
     * @param position
     */
    private fun setTabSelection(position: Int) {
        val transaction = fragmentManager.beginTransaction()
        hideFragments(transaction)
        when (position) {
            0 -> if (homeFragment == null) {
                homeFragment = HomeFragment()
                transaction.add(R.id.fl_container, homeFragment)
            } else {
                transaction.show(homeFragment)
            }
            1 -> if (storeFragment == null) {
                storeFragment = StoreFragment()
                transaction.add(R.id.fl_container, storeFragment)
            } else {
                transaction.show(storeFragment)
            }
            2 -> if (taxiuFragment == null) {
                taxiuFragment = TaxiuFragment()
                transaction.add(R.id.fl_container, taxiuFragment)
            } else {
                transaction.show(taxiuFragment)
            }
            3 -> if (cartFragment == null) {
                cartFragment = CartFragment()
                transaction.add(R.id.fl_container, cartFragment)
            } else {
                transaction.show(cartFragment)
            }
            4 -> if (mineFragment == null) {
                mineFragment = PersonFragment()
                transaction.add(R.id.fl_container, mineFragment)
            } else {
                transaction.show(mineFragment)

            }
        }
        // 提交
        transaction.commit()
    }

    /**
     * 隐藏所有的页面
     */
    private fun hideFragments(transaction: FragmentTransaction) {
        if (homeFragment != null) {
            transaction.hide(homeFragment)
        }
        if (storeFragment != null) {
            transaction.hide(storeFragment)
        }
        if (taxiuFragment != null) {
            transaction.hide(taxiuFragment)
        }
        if (cartFragment != null) {
            transaction.hide(cartFragment)
        }
        if (mineFragment != null) {
            transaction.hide(mineFragment)
        }
    }


    override fun onAttachFragment(fragment: Fragment?) {
        if (fragment == null) return
        when (fragment) {
            is HomeFragment -> homeFragment = fragment
            is StoreFragment -> storeFragment = fragment
            is TaxiuFragment -> taxiuFragment = fragment
            is CartFragment -> cartFragment = fragment
            is PersonFragment -> mineFragment = fragment
        }
    }

    fun switchToTaxiu(){
        changePage(2)
    }


}
