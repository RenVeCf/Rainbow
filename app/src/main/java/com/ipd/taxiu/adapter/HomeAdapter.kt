package com.ipd.taxiu.adapter

import android.app.Activity
import android.content.Context
import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import com.ipd.jumpbox.jumpboxlibrary.utils.DensityUtil
import com.ipd.jumpbox.jumpboxlibrary.utils.LogUtils
import com.ipd.taxiu.MainActivity
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.HomeBean
import com.ipd.taxiu.bean.TaxiuBean
import com.ipd.taxiu.platform.global.GlobalParam
import com.ipd.taxiu.ui.activity.classroom.ClassRoomDetailActivity
import com.ipd.taxiu.ui.activity.classroom.ClassRoomIndexActivity
import com.ipd.taxiu.ui.activity.talk.TalkDetailActivity
import com.ipd.taxiu.ui.activity.talk.TalkIndexActivity
import com.ipd.taxiu.ui.activity.taxiu.TaxiuDetailActivity
import com.ipd.taxiu.ui.activity.topic.TopicDetailActivity
import com.ipd.taxiu.ui.activity.topic.TopicIndexActivity
import com.ipd.taxiu.utils.IndicatorHelper
import com.ipd.taxiu.widget.PetLifeLineView
import kotlinx.android.synthetic.main.item_header.view.*
import kotlinx.android.synthetic.main.item_hot_classroom.view.*
import kotlinx.android.synthetic.main.item_hot_talk.view.*
import kotlinx.android.synthetic.main.item_hot_topic.view.*
import kotlinx.android.synthetic.main.item_index_taxiu.view.*
import kotlinx.android.synthetic.main.item_taxiu.view.*
import kotlinx.android.synthetic.main.item_taxiu_boutique.view.*
import kotlinx.android.synthetic.main.layout_store_banner.view.*

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
            is HomeBean.IndexHeaderbean -> {//头部
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
                val headerInfo = list!![position] as HomeBean.IndexHeaderbean

                holder.itemView.store_banner.adapter = BannerPagerAdapter(context, headerInfo.banners)
                IndicatorHelper.newInstance().setRes(R.mipmap.boutique_selected, R.mipmap.boutique_unselected)
                        .setIndicator(context, headerInfo.banners.size, holder.itemView.store_banner, holder.itemView.store_banner_indicator, null)
                if (!holder.itemView.store_banner.isAutoScroll) {
                    holder.itemView.store_banner.startAutoScroll()
                }

                //宠物生命线
                if (headerInfo.petInfo == null || headerInfo.petInfo.PET_ID == 0) {
                    holder.itemView.cl_user_extend.visibility = View.GONE
                } else {
                    holder.itemView.cl_user_extend.visibility = View.VISIBLE
                    var petLifeLineList = headerInfo.petInfo.DAY_LIST ?: arrayListOf()

                    holder.itemView.pet_life_line_view.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                        override fun onGlobalLayout() {
                            holder.itemView.pet_life_line_view.viewTreeObserver.removeGlobalOnLayoutListener(this)
                            holder.itemView.pet_life_line_view.setLifeLineData(petLifeLineList)
                        }
                    })

                    holder.itemView.pet_life_line_view.setPositionChangeListener(object : PetLifeLineView.OnPositionChangeListener {
                        override fun onChange(pos: Int) {
                            val lifeLineInfo = petLifeLineList[pos]
                            holder.itemView.tv_life_line_title.text = "${lifeLineInfo.MONTH_NUM}个月${lifeLineInfo.DAY_NUM}天"
                            holder.itemView.tv_cur_date.text = lifeLineInfo.date

//                            val text = "“${context.getString(R.string.home_pet_talk)}”"
                            if (TextUtils.isEmpty(lifeLineInfo.CONTENT)) {
                                holder.itemView.tv_pet_talk.visibility = View.GONE
                            } else {
                                holder.itemView.tv_pet_talk.visibility = View.VISIBLE
                                val text = "“${lifeLineInfo.CONTENT}”"
                                val spannedString = SpannableString(text)
                                spannedString.setSpan(StyleSpan(Typeface.BOLD), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                                spannedString.setSpan(AbsoluteSizeSpan(22, true), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                                spannedString.setSpan(ForegroundColorSpan(context.resources.getColor(R.color.concrete)), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                                spannedString.setSpan(StyleSpan(Typeface.BOLD), text.length - 1, text.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                                spannedString.setSpan(AbsoluteSizeSpan(22, true), text.length - 1, text.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                                spannedString.setSpan(ForegroundColorSpan(context.resources.getColor(R.color.concrete)), text.length - 1, text.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                                holder.itemView.tv_pet_talk.text = spannedString
                            }
                        }
                    })

                }


                holder.itemView.ll_topic.setOnClickListener {
                    TopicIndexActivity.launch(context as Activity)
                }//话题
                holder.itemView.ll_taxiu.setOnClickListener {
                    if (context is MainActivity) {
                        context.switchToTaxiu()
                    }
                }//它秀
                holder.itemView.ll_talk.setOnClickListener {
                    TalkIndexActivity.launch(context as Activity)
                }//问答
                holder.itemView.ll_classroom.setOnClickListener {
                    ClassRoomIndexActivity.launch(context as Activity)
                }//课堂

            }
            ItemType.TAXIU_BOUTIQUE -> {
                val boutiqueInfo = list!![position] as HomeBean.IndexBoutiqueBean
                holder.itemView.boutique_view_pager.adapter = BoutiquePagerAdapter(context, boutiqueInfo.taxiuBoutiqueList, {
                    //它秀详情
                    TaxiuDetailActivity.launch(context as Activity, it.SHOW_ID, GlobalParam.getUserId() == it.USER_ID.toString())
                })
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
                val topicInfo = list!![position] as HomeBean.IndexTopicBean
                holder.itemView.topic_layout.setData(topicInfo.topic)
                holder.itemView.cl_topic_title.setOnClickListener {
                    //更多热门
                    TopicIndexActivity.launch(context as Activity)
                }
                holder.itemView.setOnClickListener {
                    //话题详情
                    TopicDetailActivity.launch(context as Activity, topicInfo.topic.TOPIC_ID)
                }

            }
            ItemType.HOT_TALK -> {
                val talkInfo = list!![position] as HomeBean.IndexTalkBean
                holder.itemView.cl_talk_title.setOnClickListener {
                    //更多热门
                    TalkIndexActivity.launch(context as Activity)
                }

                holder.itemView.talk_view_pager.pageMargin = DensityUtil.dip2px(context, 12f)
                holder.itemView.talk_view_pager.adapter = HotTalkPagerAdapter(context, talkInfo.talkList, {
                    //问答详情
                    TalkDetailActivity.launch(context as Activity, it.QUESTION_ID, it.NICKNAME, GlobalParam.getUserId() == it.USER_ID.toString())
                })

            }
            ItemType.HOT_CLASSROOM -> {
                val classroomInfo = list!![position] as HomeBean.IndexClassRoomBean
                holder.itemView.classroom_layout.setData(classroomInfo.classRoom)

                holder.itemView.cl_classroom_title.setOnClickListener {
                    //更多课堂
                    ClassRoomIndexActivity.launch(context as Activity)
                }
                holder.itemView.setOnClickListener {
                    //课堂详情
                    ClassRoomDetailActivity.launch(context as Activity, classroomInfo.classRoom.CLASS_ROOM_ID)
                }

            }
            else -> {
                val taxiuInfo = list!![position] as TaxiuBean
                if (position > 0 && list!![position - 1] is TaxiuBean) {
                    holder.itemView.cl_taxiu_hint.visibility = View.GONE
                } else {
                    holder.itemView.cl_taxiu_hint.visibility = View.VISIBLE
                }
                holder.itemView.taxiu_layout.setData(taxiuInfo)

                holder.itemView.taxiu_layout.media_recycler_view.setOnTouchListener { v, event ->
                    holder.itemView.onTouchEvent(event)
                }

                holder.itemView.setOnClickListener {
                    //它秀详情
                    TaxiuDetailActivity.launch(context as Activity, taxiuInfo.SHOW_ID, GlobalParam.getUserId() == taxiuInfo.USER_ID.toString())
                }
            }
        }


    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}