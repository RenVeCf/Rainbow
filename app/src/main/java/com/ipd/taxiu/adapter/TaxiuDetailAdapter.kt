package com.ipd.taxiu.adapter

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import cn.jzvd.JZMediaManager
import cn.jzvd.Jzvd
import cn.jzvd.JzvdMgr
import cn.jzvd.JzvdStd
import com.ipd.jumpbox.jumpboxlibrary.utils.LogUtils
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.TaxiuCommentBean
import com.ipd.taxiu.bean.TaxiuDetailBean
import com.ipd.taxiu.bean.VideoShowBean
import com.ipd.taxiu.imageload.ImageLoader
import com.ipd.taxiu.ui.activity.PictureLookActivity
import com.ipd.taxiu.ui.activity.referral.HomepageActivity
import com.ipd.taxiu.ui.activity.taxiu.TaxiuDetailActivity
import com.ipd.taxiu.utils.StringUtils
import com.ipd.taxiu.utils.User
import com.ipd.taxiu.widget.CommentSortLayout
import kotlinx.android.synthetic.main.item_topic_comment.view.*
import kotlinx.android.synthetic.main.layout_post_user.view.*
import kotlinx.android.synthetic.main.layout_share_menu.view.*
import kotlinx.android.synthetic.main.layout_taxiu_header.view.*


/**
 * Created by jumpbox on 2017/8/31.
 */
class TaxiuDetailAdapter(val context: Context, private val detailData: TaxiuDetailBean, private val sortChange: (sortType: Int) -> Unit, private val list: List<TaxiuCommentBean>?, private val itemClick: (pos: Int, resId: Int, info: TaxiuCommentBean?) -> Unit) : RecyclerView.Adapter<TaxiuDetailAdapter.ViewHolder>() {

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

    private val mListener = object : RecyclerView.OnChildAttachStateChangeListener {
        override fun onChildViewDetachedFromWindow(view: View?) {
            LogUtils.e("tag", "onChildViewDetachedFromWindow")

        }

        override fun onChildViewAttachedToWindow(view: View?) {
            LogUtils.e("tag", "onChildViewAttachedToWindow")
            val jzvd: JzvdStd? = view?.findViewById(R.id.video_player)
            if (jzvd != null && jzvd!!.jzDataSource.containsTheUrl(JZMediaManager.getCurrentUrl())) {
                val currentJzvd = JzvdMgr.getCurrentJzvd()
                if (currentJzvd != null && currentJzvd.currentScreen != Jzvd.SCREEN_WINDOW_FULLSCREEN) {
                    Jzvd.releaseAllVideos()
                }
            }
            view?.findViewById<View>(R.id.start)?.performClick()
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
//                holder.itemView.flow_layout.removeAllViews()
//                //标签
//                detailData.TipList.forEach {
//                    val lableView = LayoutInflater.from(context).inflate(R.layout.item_lable, holder.itemView.flow_layout, false) as TextView
//                    lableView.text = it.TIP
//                    holder.itemView.flow_layout.addView(lableView)
//                }
                ImageLoader.loadAvatar(context, detailData.User.LOGO, holder.itemView.civ_publisher_avatar)
                holder.itemView.tv_nickname.text = detailData.User.NICKNAME
                holder.itemView.tv_user_desc.text = detailData.User.tag

                holder.itemView.ll_attention.setOnClickListener {
                    itemClick.invoke(position, it.id, null)
                }


                if (TextUtils.isEmpty(detailData.URL)) {
                    val pics = StringUtils.splitImages(detailData.PIC)
                    holder.itemView.media_recycler_view.adapter = MediaPictureAdapter(context, pics, { list, pos ->
                        PictureLookActivity.launch(context as Activity?, ArrayList(list), pos, PictureLookActivity.URL)
                    })
                } else {
                    holder.itemView.media_recycler_view.adapter = MediaVideoPlayAdapter(context, arrayListOf(VideoShowBean(detailData.LOGO, detailData.URL)), { info, pos ->
                    })
                    holder.itemView.media_recycler_view.removeOnChildAttachStateChangeListener(mListener)
                    holder.itemView.media_recycler_view.addOnChildAttachStateChangeListener(mListener)
                }

                holder.itemView.tv_taxiu_desc.text = detailData.CONTENT
//                holder.itemView.tv_taxiu_lable.text = detailData.TIP
                holder.itemView.taxiu_lable_show_layout.setLableInfo(detailData.ShowTipList)

                holder.itemView.tv_taxiu_publish_time.text = detailData.CREATETIME
                holder.itemView.tv_viewers_num.text = detailData.BROWSE.toString()
                holder.itemView.ll_zan.isSelected = detailData.IS_PRAISE == 1
                holder.itemView.iv_zan.isSelected = detailData.IS_PRAISE == 1
                holder.itemView.tv_zan.text = detailData.PRAISE.toString()
                holder.itemView.tv_zan.isSelected = detailData.IS_PRAISE == 1
                holder.itemView.tv_zan_extra.isSelected = detailData.IS_PRAISE == 1
                holder.itemView.tv_comment_num.text = "${detailData.COMMENT_NUM}人参与了评论"

                holder.itemView.ll_zan.setOnClickListener {
                    itemClick.invoke(position, holder.itemView.iv_zan.id, null)
                }


                holder.itemView.layout_post_user.visibility = if (detailData.User.IS_SELF == 1) View.GONE else View.VISIBLE
                holder.itemView.flow_layout.visibility = if (detailData.User.IS_SELF == 1) View.GONE else View.VISIBLE
                holder.itemView.iv_zan.visibility = if (detailData.User.IS_SELF == 1) View.GONE else View.VISIBLE
                holder.itemView.tv_zan.visibility = if (detailData.User.IS_SELF == 1) View.GONE else View.VISIBLE
                holder.itemView.rl_share.visibility = if (detailData.User.IS_SELF == 1) View.GONE else View.VISIBLE

                setAttent(position, holder.itemView.ll_attention)


                holder.itemView.ll_comment_sort.setSortChange(object : CommentSortLayout.SortChangeListener {
                    override fun onChange(sortType: Int) {
                        sortChange.invoke(sortType)
                    }
                })

                holder.itemView.ll_wechat.setOnClickListener {
                    if (context is TaxiuDetailActivity){
                        context.getShareDialogClick(detailData)
                                .WechatOnclick()
                    }
                }
                holder.itemView.ll_moment.setOnClickListener {
                    if (context is TaxiuDetailActivity){
                        context.getShareDialogClick(detailData)
                                .momentsOnclick()
                    }
                }
                holder.itemView.ll_qq.setOnClickListener {
                    if (context is TaxiuDetailActivity){
                        context.getShareDialogClick(detailData)
                                .QQOnclick()
                    }
                }
                holder.itemView.ll_qzone.setOnClickListener {
                    if (context is TaxiuDetailActivity){
                        context.getShareDialogClick(detailData)
                                .QQZoneOnclick()
                    }
                }
                holder.itemView.ll_sina.setOnClickListener {
                    if (context is TaxiuDetailActivity){
                        context.getShareDialogClick(detailData)
                                .SinaOnclick()
                    }
                }

                holder.itemView.civ_publisher_avatar.setOnClickListener {
                    HomepageActivity.launch(context as Activity,detailData.USER_ID)
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


    fun setAttent(position: Int, ll_attent: LinearLayout) {
        User.setAttentBtnStyle(context, detailData.User.IS_ATTEN, ll_attent)

        ll_attent.setOnClickListener {
            itemClick.invoke(position, ll_attent.id, null)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}