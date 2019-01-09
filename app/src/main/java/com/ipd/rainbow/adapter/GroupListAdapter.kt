package com.ipd.rainbow.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.sharesdk.framework.Platform
import com.ipd.jumpbox.jumpboxlibrary.utils.ToastCommom
import com.ipd.rainbow.R
import com.ipd.rainbow.bean.GroupBean
import com.ipd.rainbow.platform.global.Constant
import com.ipd.rainbow.platform.global.GlobalApplication
import com.ipd.rainbow.platform.http.HttpUrl
import com.ipd.rainbow.utils.ShareCallback
import com.ipd.rainbow.widget.ShareDialog
import com.ipd.rainbow.widget.ShareDialogClick
import kotlinx.android.synthetic.main.item_group_list.view.*
import java.util.*

/**
Created by Miss on 2018/8/13
 */
class GroupListAdapter(val context: Context, private val data: List<GroupBean>?, private val itemClick: (info: GroupBean) -> Unit) : RecyclerView.Adapter<GroupListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_group_list, parent, false))
    }

    override fun getItemCount(): Int = data?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = data!![position]

        holder.itemView.order_number.text = "${info.TEAM_NUM}人团，还差${info.JOIN_NUM}人成团"
        holder.itemView.commodity_status.text = when (info.TEAM_STATUS) {
            2 -> "已成团"
            3 -> "未成团"
            else -> "待成团"
        }


        holder.itemView.tv_commodity_pay.text = "￥${info.PAY_FEE}"

        holder.itemView.tv_confirm.visibility = if (info.TEAM_STATUS == 1) View.VISIBLE else View.GONE

        holder.itemView.tv_confirm.setOnClickListener {
            val dialog = ShareDialog(context)
            dialog.setShareDialogOnClickListener(ShareDialogClick()
                    .setShareTitle(info.PRODUCT_LIST[0].PROCUCT_NAME)
                    .setShareContent(Constant.SHARE_PRODUCT_CONTENT)
                    .setShareLogoUrl(HttpUrl.IMAGE_URL + info.PRODUCT_LIST[0].LOGO)
                    .setShareUrl(HttpUrl.APK_DOWNLOAD_URL)
                    .setCallback(object : ShareDialogClick.MainPlatformActionListener {
                        override fun onComplete(platform: Platform?, i: Int, hashMap: HashMap<String, Any>?) {
                            ToastCommom.getInstance().show(GlobalApplication.mContext, true, "分享成功")
                            ShareCallback.shareProduct(info.PRODUCT_LIST[0].PRODUCT_ID, info.PRODUCT_LIST[0].FORM_ID)
                        }

                        override fun onError(platform: Platform?, i: Int, throwable: Throwable?) {
                            ToastCommom.getInstance().show(GlobalApplication.mContext, true, "分享失败")
                        }

                        override fun onCancel(platform: Platform?, i: Int) {
                            ToastCommom.getInstance().show(GlobalApplication.mContext, true, "取消分享")
                        }

                    }))
            dialog.show()
        }

        holder.itemView.product_recycler_view.adapter = OrderProductAdapter(context, info.PRODUCT_LIST, {
            itemClick?.invoke(info)
        })

        holder.itemView.setOnClickListener {
            itemClick?.invoke(info)
        }

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}