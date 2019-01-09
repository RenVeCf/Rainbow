package com.ipd.rainbow.adapter

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.rainbow.R
import com.ipd.rainbow.bean.TalkBean
import com.ipd.rainbow.imageload.ImageLoader
import kotlinx.android.synthetic.main.item_index_talk.view.*

class HotTalkPagerAdapter(val context: Context, val list: List<TalkBean>?, val itemClick: (info: TalkBean) -> Unit) : PagerAdapter() {

    private val mInflater: LayoutInflater by lazy { LayoutInflater.from(context) }

    override fun getCount(): Int = list?.size ?: 0


    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val info = list!![position]
        val mContentView = mInflater.inflate(R.layout.item_index_talk, null, false)
        mContentView.tv_title.text = info.CONTENT
        mContentView.tv_publisher_nickname.text = info.NICKNAME
        ImageLoader.loadAvatar(context, info.LOGO, mContentView.civ_publisher_avatar)

        //积分奖励
        mContentView.tv_award_integral.text = info.SCORE.toString()
        mContentView.ll_award.visibility = if (info.SCORE == 0) View.GONE else View.VISIBLE


        if (info.ANSWER_DATA == null || info.ANSWER_DATA.USER_ID == 0) {
            mContentView.view_line.visibility = View.GONE
            mContentView.cl_answer.visibility = View.GONE
        } else {
            mContentView.view_line.visibility = View.VISIBLE
            mContentView.cl_answer.visibility = View.VISIBLE
            ImageLoader.loadAvatar(context, info.ANSWER_DATA.LOGO, mContentView.civ_answer_avatar)
            mContentView.tv_answer_nickname.text = info.ANSWER_DATA.NICKNAME
            mContentView.tv_answer_content.text = info.ANSWER_DATA.CONTENT
            ImageLoader.loadAvatar(context, info.ANSWER_DATA.LOGO, mContentView.civ_answer_avatar)
        }

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