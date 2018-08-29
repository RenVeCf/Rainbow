package com.ipd.taxiu.widget

import android.content.Context
import android.graphics.Rect
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View
import com.ipd.jumpbox.jumpboxlibrary.utils.DensityUtil

class MediaRecyclerView : RecyclerView {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)


    private val itemDecoration: ItemDecoration by lazy {
        object : ItemDecoration() {
            override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: State?) {
                if (parent?.layoutManager is GridLayoutManager) {
                    val gridLayoutManager = parent?.layoutManager as GridLayoutManager
                    val position = parent?.getChildAdapterPosition(view)
                    outRect?.left = if (position % gridLayoutManager.spanCount == 0) 0 else DensityUtil.dip2px(context, 6f)
                    outRect?.top = 0
                    outRect?.right = if (position % gridLayoutManager.spanCount == gridLayoutManager.spanCount) 0 else DensityUtil.dip2px(context, 6f)
                    outRect?.bottom = 0
                } else {
                    outRect?.left = 0
                    outRect?.top = 0
                    outRect?.right = 0
                    outRect?.bottom = 0
                }
            }
        }
    }

    override fun setAdapter(adapter: Adapter<*>?) {
        val itemCount = adapter?.itemCount
        layoutManager = when (itemCount) {
            1 -> {
                LinearLayoutManager(context)
            }
            2 -> {
                GridLayoutManager(context, 2)
            }
            else -> {
                GridLayoutManager(context, 3)
            }
        }

        val curItemDecoration = getItemDecorationAt(0)
        if (curItemDecoration == null) {
            addItemDecoration(itemDecoration)
        }
        super.setAdapter(adapter)
    }
}