package com.ipd.taxiu.adapter

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.TalkBean

class HotTalkPagerAdapter(val context: Context, val list: List<TalkBean>?,val itemClick:(info:TalkBean)->Unit) : PagerAdapter() {

    private val mInflater: LayoutInflater by lazy { LayoutInflater.from(context) }

    override fun getCount(): Int = list?.size ?: 0


    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val info = list!![position]
        val mContentView = mInflater.inflate(R.layout.item_index_talk, null, false)
        mContentView.setOnClickListener { itemClick.invoke(info) }
        container.addView(mContentView)
        return mContentView
    }


    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getPageWidth(position: Int): Float {
        return 0.7f
    }

}