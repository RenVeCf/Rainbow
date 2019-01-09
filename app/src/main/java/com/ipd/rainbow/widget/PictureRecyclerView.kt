package com.ipd.rainbow.widget

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import com.ipd.rainbow.adapter.PictureAdapter
import com.ipd.rainbow.bean.PictureBean
import com.ipd.rainbow.ui.activity.PhotoSelectActivity

class PictureRecyclerView : RecyclerView {
    var MAX_IMAGE_COUNT = 3
    private var pictureList: ArrayList<PictureBean> = arrayListOf()

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)


    fun init() {
        init(MAX_IMAGE_COUNT)
    }

    fun init(maxImage: Int) {
        MAX_IMAGE_COUNT = maxImage
        layoutManager = GridLayoutManager(context, MAX_IMAGE_COUNT)
        adapter = PictureAdapter(context, pictureList, MAX_IMAGE_COUNT)
    }

    override fun getAdapter(): PictureAdapter {
        return super.getAdapter() as PictureAdapter
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

    fun getPictureList(): ArrayList<PictureBean> {
        return pictureList
    }
}