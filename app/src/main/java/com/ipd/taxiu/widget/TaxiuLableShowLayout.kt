package com.ipd.taxiu.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.ipd.taxiu.R

class TaxiuLableShowLayout : LinearLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    private val mLayoutInflater: LayoutInflater by lazy { LayoutInflater.from(context) }

    fun setLableInfo(lables: List<String>) {
        removeAllViews()
        lables.forEach {
            val lableView = mLayoutInflater.inflate(R.layout.item_taxiu_show_lable, this, false) as TextView
            lableView.text = it
            addView(lableView)
        }
    }
}