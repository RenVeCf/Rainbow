package com.ipd.taxiu.adapter

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.TopicCommentBean
import com.ipd.taxiu.bean.TopicDetailBean
import com.ipd.taxiu.imageload.ImageLoader
import com.ipd.taxiu.ui.activity.PictureLookActivity
import com.ipd.taxiu.ui.activity.topic.TopicDetailActivity
import com.ipd.taxiu.utils.HtmlImageGetter
import com.ipd.taxiu.utils.StringUtils
import com.ipd.taxiu.utils.WebUtils
import com.ipd.taxiu.widget.CommentSortLayout
import kotlinx.android.synthetic.main.item_topic_comment.view.*
import kotlinx.android.synthetic.main.layout_share_menu.view.*
import kotlinx.android.synthetic.main.layout_topic_header.view.*

/**
 * Created by jumpbox on 2017/8/31.
 */
class TopicDetailAdapter(val context: Context, private val detailData: TopicDetailBean, private val sortChange: (sortType: Int) -> Unit, private val list: List<TopicCommentBean>?, private val itemClick: (pos: Int, resId: Int, info: TopicCommentBean?) -> Unit) : RecyclerView.Adapter<TopicDetailAdapter.ViewHolder>() {

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
                ViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_topic_header, parent, false))
            }
            else -> {
                ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_topic_comment, parent, false))
            }
        }

    }

    val html: String = "<style>img{width:100%;height:auto}</style><p style='text-align:center;'><img src='http://47.96.238.44:8888/images/admin/20180910/20180910202348_959.png' alt='' width='400' height='247' title='' align='' /></p><p style='text-align:center;'>保持边缘清晰，画面居中，不得歪斜。</p><p style='text-align:center;'><img src='http://47.96.238.44:8888/images/admin/20180910/20180910202618_176.png' alt='' width='400' height='247' title='' align='' /></p>'"

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            ItemType.HEADER -> {
                if (!TextUtils.isEmpty(detailData.LOGO)) {
                    holder.itemView.iv_topic_image.visibility = View.VISIBLE
                    ImageLoader.loadNoPlaceHolderImg(context, detailData.LOGO, holder.itemView.iv_topic_image)
                } else {
                    holder.itemView.iv_topic_image.visibility = View.GONE
                }
                holder.itemView.tv_topic_title.text = detailData.TITLE
//                holder.itemView.tv_topic_desc.text = detailData.CONTENT
                holder.itemView.topic_web.loadData(WebUtils.getHtmlData(detailData.CONTENT_DETAIL), "text/html; charset=UTF-8", null)

                holder.itemView.tv_taxiu_publish_time.text = detailData.CREATETIME
                holder.itemView.tv_viewers_num.text = detailData.BROWSE.toString()
                holder.itemView.iv_zan.isSelected = detailData.IS_PRAISE == 1
                holder.itemView.tv_zan.text = detailData.PRAISE.toString()
                holder.itemView.tv_zan.isSelected = detailData.IS_PRAISE == 1
                holder.itemView.tv_comment_join_num.text = "${detailData.COMMENT_NUM}人参与了该话题的讨论"


                holder.itemView.iv_zan.setOnClickListener {
                    itemClick.invoke(position, holder.itemView.iv_zan.id, null)
                }


                holder.itemView.ll_comment_sort.setSortChange(object : CommentSortLayout.SortChangeListener {
                    override fun onChange(sortType: Int) {
                        sortChange.invoke(sortType)
                    }
                })

                holder.itemView.ll_wechat.setOnClickListener {
                    if (context is TopicDetailActivity) {
                        context.getShareDialogClick(detailData)
                                .WechatOnclick()
                    }
                }
                holder.itemView.ll_moment.setOnClickListener {
                    if (context is TopicDetailActivity) {
                        context.getShareDialogClick(detailData)
                                .momentsOnclick()
                    }
                }
                holder.itemView.ll_qq.setOnClickListener {
                    if (context is TopicDetailActivity) {
                        context.getShareDialogClick(detailData)
                                .QQOnclick()
                    }
                }
                holder.itemView.ll_qzone.setOnClickListener {
                    if (context is TopicDetailActivity) {
                        context.getShareDialogClick(detailData)
                                .QQZoneOnclick()
                    }
                }
                holder.itemView.ll_sina.setOnClickListener {
                    if (context is TopicDetailActivity) {
                        context.getShareDialogClick(detailData)
                                .SinaOnclick()
                    }
                }

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
                holder.itemView.tv_comment_zan_num.isSelected = info.IS_PRAISE == 1


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