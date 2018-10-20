package com.ipd.taxiu.widget

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import com.ipd.jumpbox.jumpboxlibrary.utils.LogUtils

class StoreSpecialRecyclerView : RecyclerView {


    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        init()
    }

    private var mSuspensionListener: SuspensionListener? = null
    private fun init() {
        val layoutManager = LinearLayoutManager(context)
        this.layoutManager = layoutManager

//        addItemDecoration(object : ItemDecoration() {
//            override fun onDrawOver(c: Canvas?, parent: RecyclerView?, state: State?) {
//                super.onDrawOver(c, parent, state)
//
//
//            }
//        })

        addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val suspensionView = layoutManager.findViewByPosition(2)
                if (suspensionView?.top ?: 0 >= 0) {
                    mSuspensionListener?.onChange(false)
                } else {
                    mSuspensionListener?.onChange(true)
                }
            }
        })
    }

    fun setSuspensionListener(listener: SuspensionListener) {
        this.mSuspensionListener = listener
    }


    interface SuspensionListener {
        fun onChange(isShow: Boolean)
    }


}