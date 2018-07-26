package com.ipd.taxiu.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.item_flash_sale_header.view.*

class FlashSaleTabLayout : LinearLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onFinishInflate() {
        super.onFinishInflate()

        rl_today.setOnClickListener {
            switchTab(0)
        }

        rl_tomorrow.setOnClickListener {
            switchTab(1)
        }

        switchTab(0)
    }

    private fun switchTab(pos: Int) {
        if (rl_today.isSelected && pos == 0) return
        else if (rl_tomorrow.isSelected && pos == 1) return
        rl_today.isSelected = pos == 0
        today_icon.isSelected = pos == 0
        today_time.isSelected = pos == 0
        today_title.isSelected = pos == 0
        rl_tomorrow.isSelected = pos == 1
        tomorrow_icon.isSelected = pos == 1
        tomorrow_time.isSelected = pos == 1
        tomorrow_title.isSelected = pos == 1

        onChange?.invoke(pos)
    }

    private var onChange: ((pos: Int) -> Unit)? = null

    fun setTabListener(onChange: (pos: Int) -> Unit) {
        this.onChange = onChange
    }

}