package com.ipd.taxiu.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.jumpbox.jumpboxlibrary.utils.CommonUtils
import com.ipd.taxiu.R
import com.ipd.taxiu.widget.ChooseFriendDialog
import kotlinx.android.synthetic.main.item_group_detail_header.view.*
import kotlinx.android.synthetic.main.item_order_detail_footer.view.*

/**
Created by Miss on 2018/8/13
拼团详情adapter
 */
class GroupDetailAdapter (val context: Context, private val data: List<String>,private val GroupStatus:Int): RecyclerView.Adapter<GroupDetailAdapter.ViewHolder>(){
    private var mRecyclerView: RecyclerView? = null
    private var VIEW_FOOTER: View? = null
    private var VIEW_HEADER: View? = null

    //Type
    private val TYPE_NORMAL = 1000
    private val TYPE_HEADER = 1001
    private val TYPE_FOOTER = 1002

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return when (viewType) {
            TYPE_FOOTER -> ViewHolder(this!!.VIEW_FOOTER!!)
            TYPE_HEADER -> ViewHolder(this!!.VIEW_HEADER!!)
            else -> ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_order_detail,parent,false))
        }
    }

    override fun getItemCount(): Int {
        var count = data?.size ?: 0
        if (VIEW_FOOTER != null) {
            count++
        }

        if (VIEW_HEADER != null) {
            count++
        }
        return count
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        if (!isHeaderView(position) && !isFooterView(position)) {
            if (haveHeaderView()) position-1
            //商品信息
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            isHeaderView(position) -> TYPE_HEADER
            isFooterView(position) -> TYPE_FOOTER
            else -> TYPE_NORMAL
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView?) {
        try {
            if (mRecyclerView == null && mRecyclerView != recyclerView) {
                mRecyclerView = recyclerView
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun getLayout(layoutId: Int): View {
        return LayoutInflater.from(context).inflate(layoutId, null)
    }

    fun addHeaderView(headerView: View) {
        if (haveHeaderView()) {
            throw IllegalStateException("hearview has already exists!")
        } else {
            //避免出现宽度自适应
            val params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            headerView.layoutParams = params
            VIEW_HEADER = headerView
            notifyItemInserted(0)

            when (GroupStatus) {
                1 -> {
                    headerView.tv_group_status.text = "待成团"
                    headerView.iv_order_status.setImageResource(R.mipmap.wait_group)
                }
                2 -> {
                    headerView.tv_group_status.text = "待发货"
                    headerView.iv_order_status.setImageResource(R.mipmap.detail_wait_shipments)
                }
                else -> {
                    headerView.tv_group_status.text = "未成团"
                    headerView.iv_order_status.setImageResource(R.mipmap.no_group)
                }
            }

            headerView.btn_invite_friends.setOnClickListener {
                val dialog = ChooseFriendDialog(context, R.style.recharge_pay_dialog, 2)
                dialog.show()
            }

            headerView.tv_hour.setStopTime(java.lang.Long.valueOf("14903387320000"))
            headerView.tv_hour.setTextViewPadding(5,0,5,0)
            headerView.tv_hour.setTextViewMargin(5,0,5,0)
        }

    }

    fun addFooterView(footerView: View) {
        if (haveFooterView()) {
            throw IllegalStateException("footerView has already exists!")
        } else {
            val params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            footerView.layoutParams = params
            VIEW_FOOTER = footerView
            notifyItemInserted(itemCount - 1)

            footerView.payment_method.visibility = View.VISIBLE
            footerView.payment_time.visibility = View.VISIBLE
            footerView.tv_invoice_title.visibility = View.VISIBLE
            footerView.tv_copy.setOnClickListener { CommonUtils.copyText(context, footerView.tv_order_code) }
        }
    }


    private fun haveHeaderView(): Boolean {
        return VIEW_HEADER != null
    }

    fun haveFooterView(): Boolean {
        return VIEW_FOOTER != null
    }

    private fun isHeaderView(position: Int): Boolean {
        return haveHeaderView() && position == 0
    }

    private fun isFooterView(position: Int): Boolean {
        return haveFooterView() && position == itemCount - 1
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}