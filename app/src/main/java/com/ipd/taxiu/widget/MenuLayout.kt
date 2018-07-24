package com.ipd.taxiu.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import com.ipd.taxiu.R
import com.ipd.taxiu.adapter.MenuCategoryAdapter
import com.ipd.taxiu.bean.MenuBean
import kotlinx.android.synthetic.main.item_menu.view.*
import kotlinx.android.synthetic.main.layout_menu.view.*

class MenuLayout : LinearLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val mInflater: LayoutInflater by lazy { LayoutInflater.from(context) }

    private var menuList: List<MenuBean>? = null
    private var mCurPosition = 0

    fun setMenu(menuList: List<MenuBean>) {
        this.menuList = menuList
        mCurPosition = 0

        ll_menu.removeAllViews()
        menuList.forEachIndexed { index, menuBean ->
            val menuView = mInflater.inflate(R.layout.item_menu, this, false)
            menuView.tv_menu_title.text = menuBean.menu
            menuView.setBackgroundResource(when (index) {
                0 -> R.drawable.selector_store_left_menu
                menuList.size - 1 -> R.drawable.selector_store_right_menu
                else -> R.drawable.selector_store_normal_menu
            })
            menuView.setOnClickListener {
                setMenuIsChecked(false, mCurPosition)
                mCurPosition = index
                setMenuIsChecked(true, mCurPosition)
                onMenuSwitch()
            }

            ll_menu.addView(menuView)
            setMenuIsChecked(index == mCurPosition, index)
        }
        setData()
    }

    private fun setData() {
        if (menuList == null || menuList!!.isEmpty() || menuList!!.size <= mCurPosition) return
        category_recycler_view.adapter = MenuCategoryAdapter(context, menuList!![mCurPosition].list, {

        })

    }

    fun onMenuSwitch() {
        setData()
    }

    private fun setMenuIsChecked(isChecked: Boolean, position: Int) {
        if (position >= ll_menu.childCount) return
        val menuView = ll_menu.getChildAt(position) as ViewGroup
        menuView.isSelected = isChecked
        menuView.tv_menu_title.setTextColor(resources.getColor(if (isChecked) R.color.colorPrimaryDark else R.color.LightGrey))
    }


}