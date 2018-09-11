package com.ipd.taxiu.adapter

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.TaxiuCommentBean
import com.ipd.taxiu.bean.TaxiuDetailBean
import com.ipd.taxiu.bean.VideoShowBean
import com.ipd.taxiu.imageload.ImageLoader
import com.ipd.taxiu.ui.activity.PictureLookActivity
import com.ipd.taxiu.ui.activity.VideoActivity
import com.ipd.taxiu.utils.StringUtils
import kotlinx.android.synthetic.main.item_topic_comment.view.*
import kotlinx.android.synthetic.main.layout_post_user.view.*
import kotlinx.android.synthetic.main.layout_taxiu_header.view.*

/**
 * Created by jumpbox on 2017/8/31.
 */
class TaxiuDetailAdapter(val context: Context, private val detailData: TaxiuDetailBean, private val list: List<TaxiuCommentBean>?, private val itemClick: (pos: Int, resId: Int, info: TaxiuCommentBean?) -> Unit) : RecyclerView.Adapter<TaxiuDetailAdapter.ViewHolder>() {

    override fun getItemCount(): Int = list?.size?.plus(1) ?: 1

    object ItemType {
        const val HEADER = 0
        const val COMMENT = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> ItemType.HEADER
            else -> ItemType.COMMENT
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return when (viewType) {
            ItemType.HEADER -> {
                ViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_taxiu_header, parent, false))
            }
            else -> {
                ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_topic_comment, parent, false))
            }
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            ItemType.HEADER -> {
                holder.itemView.flow_layout.removeAllViews()
                //标签
                detailData.TipList.forEach {
                    val lableView = LayoutInflater.from(context).inflate(R.layout.item_lable, holder.itemView.flow_layout, false) as TextView
                    lableView.text = it.TIP
                    holder.itemView.flow_layout.addView(lableView)
                }
                ImageLoader.loadAvatar(context, detailData.User.LOGO, holder.itemView.civ_publisher_avatar)
                holder.itemView.tv_nickname.text = detailData.User.NICKNAME
                holder.itemView.tv_user_desc.text = detailData.User.TAG


                if (TextUtils.isEmpty(detailData.URL)) {
                    val pics = StringUtils.splitImages(detailData.PIC)
                    holder.itemView.media_recycler_view.adapter = MediaPictureAdapter(context, pics, { list, pos ->
                        PictureLookActivity.launch(context as Activity?, ArrayList(list), pos, PictureLookActivity.URL)
                    })
                } else {
                    holder.itemView.media_recycler_view.adapter = MediaVideoAdapter(context, arrayListOf(VideoShowBean(detailData.LOGO, detailData.URL)), { info, pos ->
                        VideoActivity.launch(context as Activity, info.videoUrl)
                    })
                }

                holder.itemView.tv_taxiu_desc.text = detailData.CONTENT
                holder.itemView.tv_taxiu_lable.text = detailData.TIP
                holder.itemView.tv_taxiu_publish_time.text = detailData.CREATETIME
                holder.itemView.tv_viewers_num.text = detailData.BROWSE.toString()
                holder.itemView.iv_zan.isSelected = detailData.IS_PRAISE == 1
                holder.itemView.tv_zan.text = detailData.PRAISE.toString()
                holder.itemView.tv_comment_num.text = "${detailData.COMMENT_NUM}人参与了评论"

                holder.itemView.iv_zan.setOnClickListener {
                    itemClick.invoke(position, holder.itemView.iv_zan.id, null)
                }


                holder.itemView.layout_post_user.visibility = if (detailData.User.IS_SELF == 1) View.GONE else View.VISIBLE
                holder.itemView.flow_layout.visibility = if (detailData.User.IS_SELF == 1) View.GONE else View.VISIBLE
                holder.itemView.iv_zan.visibility = if (detailData.User.IS_SELF == 1) View.GONE else View.VISIBLE
                holder.itemView.tv_zan.visibility = if (detailData.User.IS_SELF == 1) View.GONE else View.VISIBLE
                holder.itemView.rl_share.visibility = if (detailData.User.IS_SELF == 1) View.GONE else View.VISIBLE

            }
            ItemType.COMMENT -> {
                val info = list!![position - 1]
                ImageLoader.loadAvatar(context, info.LOGO, holder.itemView.civ_commenter_avatar)
                holder.itemView.tv_commenter_name.text = info.NICKNAME
                holder.itemView.tv_answer_content.text = info.CONTENT
                holder.itemView.tv_comment_time.text = info.CREATETIME
                holder.itemView.tv_comment_viewers_num.text = info.BROWSE.toString()
                holder.itemView.tv_comment_reply_num.text = info.REPLY.toString()
                holder.itemView.tv_comment_zan_num.text = info.PRAISE.toString()
                holder.itemView.iv_comment_zan.isSelected = info.IS_PRAISE == 1

                val pics = StringUtils.splitImages(info.PIC)
                if (pics.isNotEmpty()) {
                    holder.itemView.image_media_view.visibility = View.VISIBLE
                    holder.itemView.image_media_view.image_media_view.adapter = MediaPictureAdapter(context, pics, { list, pos ->
                        PictureLookActivity.launch(context as Activity?, ArrayList(list), pos, PictureLookActivity.URL)
                    })
                } else {
                    holder.itemView.image_media_view.visibility = View.GONE
                }

                holder.itemView.setOnClickListener {
                    itemClick.invoke(position, holder.itemView.id, info)

                }

                holder.itemView.ll_comment_zan.setOnClickListener {
                    itemClick.invoke(position, holder.itemView.ll_comment_zan.id, info)
                }

            }
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}