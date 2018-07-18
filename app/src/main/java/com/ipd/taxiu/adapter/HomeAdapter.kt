package com.ipd.taxiu.adapter

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.jumpbox.jumpboxlibrary.utils.DensityUtil
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.HomeBean
import com.ipd.taxiu.bean.TaxiuBean
import com.ipd.taxiu.ui.activity.topic.TopicIndexActivity
import com.ipd.taxiu.utils.IndicatorHelper
import kotlinx.android.synthetic.main.item_header.view.*
import kotlinx.android.synthetic.main.item_hot_talk.view.*
import kotlinx.android.synthetic.main.item_index_taxiu.view.*
import kotlinx.android.synthetic.main.item_taxiu_boutique.view.*

/**
 * Created by jumpbox on 2017/8/31.
 */
class HomeAdapter(val context: Context, private val list: List<Any>?) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    object ItemType {
        const val HEADER: Int = 0
        const val TAXIU_BOUTIQUE: Int = 1
        const val HOT_TOPIC: Int = 2
        const val HOT_TALK: Int = 3
        const val HOT_CLASSROOM: Int = 4
        const val TAXIU_RECOMMEND: Int = 5
    }


    override fun getItemCount(): Int {
        return list?.size ?: 0
    }


    override fun getItemViewType(position: Int): Int {
        val info = list!![position]
        when (info) {
            is HomeBean.IndexBannerBean -> {//头部
                return ItemType.HEADER
            }
            is HomeBean.IndexBoutiqueBean -> {//它秀精选
                return ItemType.TAXIU_BOUTIQUE
            }
            is HomeBean.IndexTopicBean -> {//热门话题
                return ItemType.HOT_TOPIC
            }
            is HomeBean.IndexTalkBean -> {//热门问答
                return ItemType.HOT_TALK
            }
            is HomeBean.IndexClassRoomBean -> {//热门课程
                return ItemType.HOT_CLASSROOM
            }
            else -> {//它秀推荐
                return ItemType.TAXIU_RECOMMEND
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return when (viewType) {
            ItemType.HEADER -> {
                ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_header, parent, false))
            }
            ItemType.TAXIU_BOUTIQUE -> {
                ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_taxiu_boutique, parent, false))
            }
            ItemType.HOT_TOPIC -> {
                ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_hot_topic, parent, false))
            }
            ItemType.HOT_TALK -> {
                ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_hot_talk, parent, false))
            }
            ItemType.HOT_CLASSROOM -> {
                ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_hot_classroom, parent, false))
            }
            else -> {
                ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_index_taxiu, parent, false))
            }
        }


    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            ItemType.HEADER -> {
                holder.itemView.ll_topic.setOnClickListener { TopicIndexActivity.launch(context as Activity) }//话题

            }
            ItemType.TAXIU_BOUTIQUE -> {
                val boutiqueInfo = list!![position] as HomeBean.IndexBoutiqueBean
                holder.itemView.boutique_view_pager.adapter = BoutiquePagerAdapter(context, boutiqueInfo.taxiuBoutiqueList)
                IndicatorHelper.newInstance().setRes(R.mipmap.boutique_selected, R.mipmap.boutique_unselected)
                        .setIndicator(context, boutiqueInfo.taxiuBoutiqueList.size, holder.itemView.boutique_view_pager, holder.itemView.ll_indicator, object : IndicatorHelper.MyPagerChangeListener {
                            override fun onPageSelected(pos: Int) {
                            }

                            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                            }

                            override fun onPageScrollStateChanged(state: Int) {
                            }

                        })
            }
            ItemType.HOT_TOPIC -> {

            }
            ItemType.HOT_TALK -> {
                val talkInfo = list!![position] as HomeBean.IndexTalkBean
                holder.itemView.talk_view_pager.pageMargin = DensityUtil.dip2px(context, 12f)
                holder.itemView.talk_view_pager.adapter = HotTalkPagerAdapter(context, talkInfo.talkList)

            }
            ItemType.HOT_CLASSROOM -> {

            }
            else -> {
                if (position > 0 && list!![position - 1] is TaxiuBean) {
                    holder.itemView.cl_taxiu_hint.visibility = View.GONE
                } else {
                    holder.itemView.cl_taxiu_hint.visibility = View.VISIBLE
                }

            }
        }


    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}