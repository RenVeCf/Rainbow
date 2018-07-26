package com.ipd.taxiu.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.item_flash_sale_header.view.*

class FlashSaleTabLayout:LinearLayout{
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onFinishInflate() {
        super.onFinishInflate()

        rl_today.setOnClickListener {

        }

        rl_tomorrow.setOnClickListener {

        }

    }

    private fun switchTab(){
    }

}