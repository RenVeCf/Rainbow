package com.ipd.taxiu.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.ipd.taxiu.utils.StoreType
import kotlinx.android.synthetic.main.item_flash_sale_header.view.*

class FlashSaleTabLayout : LinearLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onFinishInflate() {
        super.onFinishInflate()

        rl_today.setOnClickListener {
            switchTab(StoreType.FLASH_SALE_TODAY)
        }

        rl_tomorrow.setOnClickListener {
            switchTab(StoreType.FLASH_SALE_TOMORROW)
        }

        switchTab(StoreType.FLASH_SALE_TODAY)
    }

    private fun switchTab(type: Int) {
        if (rl_today.isSelected && type == StoreType.FLASH_SALE_TODAY) return
        else if (rl_tomorrow.isSelected && type == StoreType.FLASH_SALE_TOMORROW) return
        rl_today.isSelected = type == StoreType.FLASH_SALE_TODAY
        today_icon.isSelected = type == StoreType.FLASH_SALE_TODAY
        today_time.isSelected = type == StoreType.FLASH_SALE_TODAY
        today_title.isSelected = type == StoreType.FLASH_SALE_TODAY
        rl_tomorrow.isSelected = type == StoreType.FLASH_SALE_TOMORROW
        tomorrow_icon.isSelected = type == StoreType.FLASH_SALE_TOMORROW
        tomorrow_time.isSelected = type == StoreType.FLASH_SALE_TOMORROW
        tomorrow_title.isSelected = type == StoreType.FLASH_SALE_TOMORROW

        onChange?.invoke(type)
    }

    private var onChange: ((pos: Int) -> Unit)? = null

    fun setTabListener(onChange: (pos: Int) -> Unit) {
        this.onChange = onChange
    }

}