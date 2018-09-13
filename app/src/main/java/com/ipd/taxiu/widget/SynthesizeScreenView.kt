package com.ipd.taxiu.widget

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.widget.PopupWindow
import com.ipd.jumpbox.jumpboxlibrary.utils.PopupUtils
import com.ipd.taxiu.R
import kotlinx.android.synthetic.main.layout_normal_screen.view.*

class SynthesizeScreenView(val activity: Activity, val sortType: ScreenLayout.ScreenType, val dropDownView: View, val backgroundView: View?, val onSortChange: (pos: Int, isSelected: Boolean) -> Unit) {
    val inflater: LayoutInflater by lazy { LayoutInflater.from(activity) }
    val contentView: View by lazy { inflater.inflate(R.layout.layout_normal_screen, null) }


    fun showView() {
        setView()
    }

    var popup: PopupWindow? = null
    private fun setView() {
        setSelected(if (sortType == ScreenLayout.ScreenType.NORMAL_SORT) 0 else if (sortType == ScreenLayout.ScreenType.COMMENT_SORT) 1 else 2, true)

        contentView.ll_normal_sort.setOnClickListener {
            popup?.dismiss()
            val isSelected = !contentView.ll_normal_sort.isSelected
            setSelected(0, isSelected)
            onSortChange.invoke(0, isSelected)
        }
        contentView.ll_comment_sort.setOnClickListener {
            popup?.dismiss()
            val isSelected = !contentView.ll_comment_sort.isSelected
            setSelected(1, isSelected)
            onSortChange.invoke(1, isSelected)
        }

        popup = PopupUtils.getPopup(activity, contentView, activity.window)
        backgroundView?.alpha = 0.5f
        popup?.showAsDropDown(dropDownView)
        popup?.setOnDismissListener {
            backgroundView?.alpha = 1f
        }
    }

    private fun setSelected(pos: Int, isSelected: Boolean) {
        contentView.ll_normal_sort.isSelected = if (pos == 0) isSelected else false
        contentView.ll_normal_sort.getChildAt(0).isSelected = if (pos == 0) isSelected else false
        contentView.ll_normal_sort.getChildAt(1).isSelected = if (pos == 0) isSelected else false

        contentView.ll_comment_sort.isSelected = if (pos == 1) isSelected else false
        contentView.ll_comment_sort.getChildAt(0).isSelected = if (pos == 1) isSelected else false
        contentView.ll_comment_sort.getChildAt(1).isSelected = if (pos == 1) isSelected else false
    }
}