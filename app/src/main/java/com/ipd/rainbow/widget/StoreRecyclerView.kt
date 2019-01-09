package com.ipd.rainbow.widget

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import com.ipd.rainbow.adapter.StoreAdapter

class StoreRecyclerView : RecyclerView {


    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        init()
    }

    private fun init() {
        val gridLayoutManager = GridLayoutManager(context, 2)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                if (adapter == null) return gridLayoutManager.spanCount
                val itemViewType = adapter.getItemViewType(position)
                return when (itemViewType) {
                    StoreAdapter.ItemType.HEADER_CAT, StoreAdapter.ItemType.HEADER_DOG, StoreAdapter.ItemType.SECOND_HEADER -> gridLayoutManager.spanCount
                    StoreAdapter.ItemType.SPECIAL -> gridLayoutManager.spanCount
                    StoreAdapter.ItemType.RECOMMEND_VIDEO -> gridLayoutManager.spanCount
                    StoreAdapter.ItemType.RECOMMEND_PRODUCT_HEADER -> gridLayoutManager.spanCount
                    else -> 1
                }
            }
        }
        layoutManager = gridLayoutManager

//        addItemDecoration(object : ItemDecoration() {
//            override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: State?) {
//                val position = parent?.getChildAdapterPosition(view) ?: 0
//                if (adapter?.getItemViewType(position) == StoreAdapter.ItemType.RECOMMEND_PRODUCT) {
//                    outRect?.left = DensityUtil.dip2px(context, 8f)
//                    outRect?.top = DensityUtil.dip2px(context, 2f)
//                    outRect?.right = DensityUtil.dip2px(context, 8f)
//                    outRect?.bottom = DensityUtil.dip2px(context, 2f)
//                } else {
//                    outRect?.left = 0
//                    outRect?.top = 0
//                    outRect?.right = 0
//                    outRect?.bottom = 0
//                }
//            }
//        })
    }


}