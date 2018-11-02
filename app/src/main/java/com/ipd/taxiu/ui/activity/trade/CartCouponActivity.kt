package com.ipd.taxiu.ui.activity.trade

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.ipd.taxiu.R
import com.ipd.taxiu.event.ChooseCouponEvent
import com.ipd.taxiu.ui.BaseUIActivity
import com.ipd.taxiu.ui.fragment.trade.CartCouponFragment
import kotlinx.android.synthetic.main.activity_cart_coupon_container.*
import org.greenrobot.eventbus.EventBus

/**
 * 选择优惠券
 */
class CartCouponActivity : BaseUIActivity() {

    companion object {
        fun launch(context: Context, cartIds: String, isCart: Int, num: Int, productId: Int, formId: Int) {
            val intent = Intent(context, CartCouponActivity::class.java)
            intent.putExtra("cartIds", cartIds)
            intent.putExtra("isCart", isCart)
            intent.putExtra("productId", productId)
            intent.putExtra("formId", formId)
            intent.putExtra("num", num)
            context.startActivity(intent)
        }
    }

    override fun getContentLayout() = R.layout.activity_cart_coupon_container

    override fun getToolbarTitle() = "选择优惠券"


    private val mCartIds: String by lazy { intent.getStringExtra("cartIds") ?: "" }
    private val mIsCart: Int by lazy { intent.getIntExtra("isCart", 0) }
    private val mProductId: Int by lazy { intent.getIntExtra("productId", 0) }
    private val mFormId: Int by lazy { intent.getIntExtra("formId", 0) }
    private val mNum: Int by lazy { intent.getIntExtra("num", 0) }

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
        supportFragmentManager.beginTransaction().replace(R.id.fl_container, CartCouponFragment.newInstance(mCartIds, mIsCart, mProductId, mFormId, mNum)).commit()
    }

    override fun initListener() {
        tv_not_use.setOnClickListener {
            //不使用优惠券
            EventBus.getDefault().post(ChooseCouponEvent(null))
            finish()
        }

    }


}
