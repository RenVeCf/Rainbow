package com.ipd.rainbow

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import com.ipd.rainbow.platform.global.GlobalParam
import com.ipd.rainbow.ui.BaseActivity
import com.ipd.rainbow.ui.fragment.*
import com.ipd.rainbow.utils.VersionUtils
import com.ipd.rainbow.widget.SignInPopup
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, MainActivity::class.java)
            activity.startActivity(intent)
        }

        fun launch(activity: Activity, action: String) {
            val intent = Intent(activity, MainActivity::class.java)
            intent.putExtra("action", action)
            activity.startActivity(intent)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val action = intent?.getStringExtra("action")
        when (action) {
            "cart" -> {
                changePage(2)
            }
            "index" -> {
                changePage(0)
            }
        }

    }

    private val fragmentManager: FragmentManager by lazy { supportFragmentManager }

    override fun getBaseLayout(): Int = R.layout.activity_main

    override fun initView(bundle: Bundle?) {
        changePage(0)
    }

    override fun loadData() {
        if (!GlobalParam.getFirstEnterHome()) {
            checkIsSignIn()
        }
        //版本检测
        VersionUtils.check(mActivity)
    }

    /**
     * 是否签到
     */
    private fun checkIsSignIn() {
//        ApiManager.getService().signInInfo(GlobalParam.getUserIdOrJump())
//                .compose(RxScheduler.applyScheduler())
//                .subscribe(object : Response<BaseResult<SignInInfoBean>>() {
//                    override fun _onNext(result: BaseResult<SignInInfoBean>) {
//                        if (result.code == 0 && result.data.STATUS == 0) {
//                            showSignInView()
//                        }
//                    }
//                })
    }

    private fun showSignInView() {
        SignInPopup(mActivity).showPopupWindow()
    }

    override fun initListener() {
        tabs.forEachIndexed { index, layout ->
            layout.setOnClickListener {
                changePage(index)
            }
        }
    }


    private fun changePage(pos: Int) {
        setTabChecked(pos)
        setTabSelection(pos)
    }


    private val tabs: Array<LinearLayout> by lazy { arrayOf(ll_store, ll_taxiu, ll_cart, ll_msg, ll_mine) }
    private fun setTabChecked(pos: Int) {
//        if (pos == 3) {
//            //消息
//            MessageActivity.launch(mActivity)
//            return
//        }

        tabs.forEachIndexed { index, layout ->
            layout.getChildAt(0).isSelected = pos == index
            layout.getChildAt(1).isSelected = pos == index
            if (pos == index) {
                var animView: View = layout.getChildAt(0)
                val mainTabAnim = AnimationUtils.loadAnimation(mActivity, R.anim.main_tab_anim)
                animView.startAnimation(mainTabAnim)
            }
        }
    }


    private var storeFragment: StoreFragment? = null
    private var liveFragment: LiveFragment? = null
    private var cartFragment: CartFragment? = null
    private var messageFragment: HomeMessageFragment? = null
    private var mineFragment: MineFragment? = null

    /**
     * 选中的页面
     *
     * @param position
     */
    private fun setTabSelection(position: Int) {
        val transaction = fragmentManager.beginTransaction()
        hideFragments(transaction)
        when (position) {
            0 -> if (storeFragment == null) {
                storeFragment = StoreFragment()
                transaction.add(R.id.fl_container, storeFragment)
            } else {
                transaction.show(storeFragment)
            }
            1 -> if (liveFragment == null) {
                liveFragment = LiveFragment()
                transaction.add(R.id.fl_container, liveFragment)
            } else {
                transaction.show(liveFragment)
            }
            2 -> if (cartFragment == null) {
                cartFragment = CartFragment()
                transaction.add(R.id.fl_container, cartFragment)
            } else {
                transaction.show(cartFragment)
            }
            3 -> if (messageFragment == null) {
                messageFragment = HomeMessageFragment()
                transaction.add(R.id.fl_container, messageFragment)
            } else {
                transaction.show(messageFragment)
            }
            4 -> if (mineFragment == null) {
                mineFragment = MineFragment()
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
        if (storeFragment != null) {
            transaction.hide(storeFragment)
        }
        if (liveFragment != null) {
            transaction.hide(liveFragment)
        }
        if (cartFragment != null) {
            transaction.hide(cartFragment)
        }
        if (messageFragment != null) {
            transaction.hide(messageFragment)
        }
        if (mineFragment != null) {
            transaction.hide(mineFragment)
        }
    }


    override fun onAttachFragment(fragment: Fragment?) {
        if (fragment == null) return
        when (fragment) {
            is StoreFragment -> storeFragment = fragment
            is LiveFragment -> liveFragment = fragment
            is CartFragment -> cartFragment = fragment
            is HomeMessageFragment -> messageFragment = fragment
            is MineFragment -> mineFragment = fragment
        }
    }

    fun switchToStore() {
        changePage(0)
    }

    fun switchToLive() {
        changePage(1)
    }


    private val WAITTIME: Long = 2000
    private var touchTime: Long = 0

    override fun onBackPressed() {
        val currentTime = System.currentTimeMillis()
        if (currentTime - touchTime >= WAITTIME) {
            toastShow(true, "再按一次退出")
            touchTime = currentTime
        } else {
            finish()
        }
    }


}
