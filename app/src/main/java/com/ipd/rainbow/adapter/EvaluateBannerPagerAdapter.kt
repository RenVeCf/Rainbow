package com.ipd.rainbow.adapter

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.rainbow.R
import com.ipd.rainbow.bean.BannerBean
import com.ipd.rainbow.imageload.ImageLoader
import com.ipd.rainbow.utils.BannerUtils
import kotlinx.android.synthetic.main.layout_banner.view.*

class EvaluateBannerPagerAdapter(val context: Context, val list: List<BannerBean>?, val itemClick: ((pos: Int, info: BannerBean) -> Unit)? = null) : PagerAdapter() {

    private val mInflater: LayoutInflater by lazy { LayoutInflater.from(context) }

    override fun getCount(): Int = list?.size ?: 0


    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val info = list!![position]
        val mContentView = mInflater.inflate(R.layout.layout_evaluate_banner, container, false)
        ImageLoader.loadNoPlaceHolderImg(context, info.LOGO, mContentView.iv_image)
        mContentView.iv_play.visibility = if (info.isVideo) View.VISIBLE else View.GONE
        mContentView.setOnClickListener {
            if (itemClick != null) {
                itemClick.invoke(position, info)
            } else {
                BannerUtils.setBannerItemClick(context, info)
            }
        }
        container.addView(mContentView)
        return mContentView
    }


    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

}