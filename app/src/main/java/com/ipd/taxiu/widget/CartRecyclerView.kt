package com.ipd.taxiu.widget

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet

class CartRecyclerView : RecyclerView {
    object CartType {
        const val EMPTY_CART = 1
        const val CART_PRODUCT = 2
        const val RECOMMEND_PRODUCT_HEADER = 3
        const val RECOMMEND_PRODUCT = 4
    }


    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        init()
    }

    //    private var itemDecoration:DefaultItemDecoration? = null
    private fun init() {
//        if (itemDecoration == null){
//            itemDecoration = DefaultItemDecoration(resources.getColor(R.color.transparent), 2, 2, CartType.RECOMMEND_PRODUCT_HEADER)
//            addItemDecoration(itemDecoration)
//        }

        val gridLayoutManager = GridLayoutManager(context, 2)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                if (adapter == null) return gridLayoutManager.spanCount
                val itemViewType = adapter.getItemViewType(position)
                return when (itemViewType) {
                    CartType.EMPTY_CART -> 2
                    CartType.CART_PRODUCT -> 2
                    CartType.RECOMMEND_PRODUCT_HEADER -> 2
                    CartType.RECOMMEND_PRODUCT -> 1
                    else -> gridLayoutManager.spanCount
                }
            }
        }
        layoutManager = gridLayoutManager

//        setSwipeMenuCreator { leftMenu, rightMenu, position ->
//            when (adapter.getItemViewType(position)) {
//                CartType.CART_PRODUCT -> {
//                    val deleteItem = SwipeMenuItem(context)
//                    deleteItem.text = "删除"
//                    deleteItem.setTextColor(resources.getColor(R.color.white))
//                    deleteItem.setBackground(R.drawable.shape_cart_delete)
//                    deleteItem.height = ViewGroup.LayoutParams.MATCH_PARENT
//                    deleteItem.width = DensityUtil.dip2px(context, 50f)
//                    rightMenu.addMenuItem(deleteItem)
//                }
//                else -> {
//
//                }
//            }
//        }
//
//        setSwipeMenuItemClickListener { menuBridge, position ->
//            menuBridge.closeMenu()
//            LogUtils.e("tag", "delete product")
//        }
    }

}