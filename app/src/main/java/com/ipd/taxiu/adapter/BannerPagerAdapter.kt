package com.ipd.taxiu.adapter

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.BannerBean
import com.ipd.taxiu.imageload.ImageLoader
import kotlinx.android.synthetic.main.layout_banner.view.*

class BannerPagerAdapter(val context: Context, val list: List<BannerBean>?) : PagerAdapter() {

    private val mInflater: LayoutInflater by lazy { LayoutInflater.from(context) }

    override fun getCount(): Int = list?.size ?: 0


    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val info = list!![position]
        val mContentView = mInflater.inflate(R.layout.layout_banner, container, false)
        mContentView.iv_image.setImageResource(info.res)
        ImageLoader.loadNoPlaceHolderImg(context, info.LOGO, mContentView.iv_image)
        container.addView(mContentView)
        return mContentView
    }


    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

}