package com.ipd.taxiu.adapter

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ipd.jumpbox.jumpboxlibrary.utils.ToastCommom
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.BaseResult
import com.ipd.taxiu.bean.TaxiuCommentBean
import com.ipd.taxiu.bean.TaxiuDetailBean
import com.ipd.taxiu.imageload.ImageLoader
import com.ipd.taxiu.platform.global.GlobalApplication
import com.ipd.taxiu.platform.global.GlobalParam
import com.ipd.taxiu.platform.http.ApiManager
import com.ipd.taxiu.platform.http.Response
import com.ipd.taxiu.platform.http.RxScheduler
import com.ipd.taxiu.ui.activity.PictureLookActivity
import com.ipd.taxiu.utils.CommentType
import com.ipd.taxiu.utils.StringUtils
import kotlinx.android.synthetic.main.item_topic_comment.view.*
import kotlinx.android.synthetic.main.layout_post_user.view.*
import kotlinx.android.synthetic.main.layout_taxiu_header.view.*

/**
 * Created by jumpbox on 2017/8/31.
 */
class TaxiuDetailAdapter(val context: Context, private val detailData: TaxiuDetailBean, private val list: List<TaxiuCommentBean>?, private val itemClick: (info: TaxiuCommentBean) -> Unit) : RecyclerView.Adapter<TaxiuDetailAdapter.ViewHolder>() {

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

                val pics = StringUtils.splitImages(detailData.PIC)
                holder.itemView.media_recycler_view.adapter = MediaPictureAdapter(context, pics, { list, pos ->
                    PictureLookActivity.launch(context as Activity?, ArrayList(list), pos, PictureLookActivity.URL)
                })

                holder.itemView.tv_taxiu_desc.text = detailData.CONTENT
                holder.itemView.tv_taxiu_lable.text = detailData.TIP
                holder.itemView.tv_taxiu_publish_time.text = detailData.CREATETIME
                holder.itemView.tv_viewers_num.text = detailData.BROWSE.toString()
                holder.itemView.iv_zan.isSelected = detailData.IS_PRAISE == 1
                holder.itemView.tv_zan.text = detailData.PRAISE.toString()
                holder.itemView.tv_comment_num.text = "${detailData.COMMENT_NUM}人参与了评论"

                holder.itemView.iv_zan.setOnClickListener {
                    ApiManager.getService().taxiuPraise(GlobalParam.getUserIdOrJump(), CommentType.TAXIU_PRAISE, detailData.SHOW_ID)
                            .compose(RxScheduler.applyScheduler())
                            .subscribe(object : Response<BaseResult<TaxiuDetailBean>>(context, true) {
                                override fun _onNext(result: BaseResult<TaxiuDetailBean>) {
                                    if (result.code == 0) {
                                        detailData.IS_PRAISE = if (detailData.IS_PRAISE == 0) 1 else 0
                                        holder.itemView.iv_zan.isSelected = detailData.IS_PRAISE == 1
                                        if (detailData.IS_PRAISE == 0) detailData.PRAISE-- else detailData.PRAISE++
                                        holder.itemView.tv_zan.text = detailData.PRAISE.toString()

                                    } else {
                                        ToastCommom.getInstance().show(GlobalApplication.mContext, result.msg)
                                    }
                                }

                            })

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
                holder.itemView.tv_content.text = info.CONTENT
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
                    itemClick.invoke(info)

                }

                holder.itemView.ll_comment_zan.setOnClickListener {
                    ApiManager.getService().taxiuPraise(GlobalParam.getUserIdOrJump(), CommentType.TAXIU_PRAISE_COMMENT, info.COMMENT_ID)
                            .compose(RxScheduler.applyScheduler())
                            .subscribe(object : Response<BaseResult<TaxiuDetailBean>>(context, true) {
                                override fun _onNext(result: BaseResult<TaxiuDetailBean>) {
                                    if (result.code == 0) {
                                        info.IS_PRAISE = if (info.IS_PRAISE == 0) 1 else 0
                                        holder.itemView.iv_comment_zan.isSelected = info.IS_PRAISE == 1
                                        if (info.IS_PRAISE == 0) info.PRAISE-- else info.PRAISE++
                                        holder.itemView.tv_comment_zan_num.text = info.PRAISE.toString()

                                    } else {
                                        ToastCommom.getInstance().show(GlobalApplication.mContext, result.msg)
                                    }
                                }

                            })

                }

            }
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}