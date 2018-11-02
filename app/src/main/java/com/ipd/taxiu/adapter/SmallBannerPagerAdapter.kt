package com.ipd.taxiu.adapter

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.BannerBean
import com.ipd.taxiu.imageload.ImageLoader
import com.ipd.taxiu.utils.BannerUtils
import kotlinx.android.synthetic.main.layout_banner_small.view.*

class SmallBannerPagerAdapter(val context: Context, val list: List<BannerBean>?) : PagerAdapter() {

    private val mInflater: LayoutInflater by lazy { LayoutInflater.from(context) }

    override fun getCount(): Int = list?.size ?: 0


    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val info = list!![position]
        val mContentView = mInflater.inflate(R.layout.layout_banner_small, container, false)
        ImageLoader.loadNoPlaceHolderImg(context, info.LOGO, mContentView.iv_image)
        mContentView.setOnClickListener {
            BannerUtils.setBannerItemClick(context, info)
        }
        container.addView(mContentView)
        return mContentView
    }


    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

}