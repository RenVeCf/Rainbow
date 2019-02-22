package com.ipd.rainbow.widget

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.view.View
import com.ipd.rainbow.R
import com.ipd.rainbow.bean.ProductExpertScreenBean
import com.ipd.rainbow.bean.ProductExpertScreenParentBean
import kotlinx.android.synthetic.main.item_product_expert_screen.view.*

class ProductExpertScreenLayout : ConstraintLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var MAX_SHOW = 9
    private var mScreenInfo: ProductExpertScreenParentBean? = null
    private var isShowMore = false

    override fun onFinishInflate() {
        super.onFinishInflate()
        setShowMoreImgRes()
        ll_more.setOnClickListener {
            isShowMore = !isShowMore
            setContentUI()
        }
    }


    fun setData(screenInfo: ProductExpertScreenParentBean) {
        mScreenInfo = screenInfo
        setContentUI()
    }

    private fun setContentUI() {
        flow_layout.removeAllViews()
        if (mScreenInfo == null) return
        tv_screen_name.text = mScreenInfo?.TITLE ?: ""//分类名称
        val screenList = mScreenInfo?.LIST ?: listOf()//分类列表
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

    /**
     * 获取选中的筛选条件
     */
    fun getCheckedScreenInfo(): List<ProductExpertScreenBean>? {
        return mScreenInfo?.LIST?.filter {
            it.isChecked
        }
    }

    private fun setShowMoreImgRes() {
        if (mScreenInfo?.LIST?.size ?: 0 <= MAX_SHOW) {
            ll_more.visibility = View.GONE
        } else {
            ll_more.visibility = View.VISIBLE
            iv_show_more.setImageResource(if (isShowMore) R.mipmap.arrow_top_gray else R.mipmap.arrow_bottom_gray)
        }
    }

}