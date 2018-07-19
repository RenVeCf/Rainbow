package com.ipd.taxiu.widget

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import com.ipd.taxiu.adapter.PictureAdapter
import com.ipd.taxiu.bean.PictureBean
import com.ipd.taxiu.ui.activity.PhotoSelectActivity

class PictureRecyclerView : RecyclerView {
    val MAX_IMAGE_COUNT = 3
    private var pictureList: ArrayList<PictureBean> = arrayListOf()

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)


    fun init() {
        layoutManager = GridLayoutManager(context, MAX_IMAGE_COUNT)
        adapter = PictureAdapter(context, pictureList, MAX_IMAGE_COUNT)
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                PhotoSelectActivity.REQUEST_CODE -> {
                    val pictureListResult = data?.getSerializableExtra("pictureList") as List<PictureBean>
                    this.pictureList.addAll(0, pictureListResult)
                    adapter.notifyItemRangeChanged(0, adapter.itemCount)
                }
            }
        }
    }
}