package com.ipd.taxiu.widget

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.View
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.ProductExpertScreenBean
import kotlinx.android.synthetic.main.item_product_expert_screen.view.*

class ProductExpertScreenLayout : ConstraintLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var MAX_SHOW = 3
    private var mScreenInfo: ProductExpertScreenBean? = null
    private var isShowMore = false

    override fun onFinishInflate() {
        super.onFinishInflate()
        setShowMoreImgRes()
        ll_more.setOnClickListener {
            if (isBrandScreen()){


            }else{
                isShowMore = !isShowMore
                setContentUI()
            }
        }
    }


    fun setData(screenInfo: ProductExpertScreenBean) {
        mScreenInfo = screenInfo
        if (isBrandScreen()) {
            MAX_SHOW = 9
        }

        setContentUI()
    }

    private fun setContentUI() {
        flow_layout.removeAllViews()
        if (mScreenInfo == null) return
        tv_screen_name.text = mScreenInfo?.TITLE ?: ""
        val screenList = mScreenInfo?.LIST ?: listOf()
        val size = screenList?.size
        if (size > MAX_SHOW && isShowMore) {
            screenList?.forEach {
                flow_layout.addView(it)
            }
        } else {
            val end = if (size > MAX_SHOW) MAX_SHOW else size
            for (index in 0 until end) {
                flow_layout.addView(screenList?.get(index))
            }
        }
        setShowMoreImgRes()
    }

    fun getCheckedPos(): Int {
        return flow_layout.getCheckedPos()
    }

    private fun setShowMoreImgRes() {
        if (isBrandScreen()) {
            ll_more.visibility = View.VISIBLE
            iv_show_more.setImageResource(R.mipmap.arrow_right)
        } else {
            if (mScreenInfo?.LIST?.size ?: 0 <= MAX_SHOW) {
                ll_more.visibility = View.GONE
            } else {
                ll_more.visibility = View.VISIBLE
                iv_show_more.setImageResource(if (isShowMore) R.mipmap.arrow_top_gray else R.mipmap.arrow_bottom_gray)
            }
        }
    }


    private fun isBrandScreen(): Boolean {
        return mScreenInfo != null && mScreenInfo!!.TITLE == "品牌"
    }
}