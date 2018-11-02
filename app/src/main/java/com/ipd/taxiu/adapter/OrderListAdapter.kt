package com.ipd.taxiu.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.OrderBean
import com.ipd.taxiu.utils.Order
import kotlinx.android.synthetic.main.item_order_list.view.*

/**
 * Created by Miss on 2018/7/19
 */
class OrderListAdapter(private val context: Context, private val list: List<OrderBean>?, private val itemClickListener: Order.OrderItemClickListener) : RecyclerView.Adapter<OrderListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_order_list, parent, false))
    }

    override fun onBindViewHolder(holder: OrderListAdapter.ViewHolder, position: Int) {
        val info = list!![position]
        holder.itemView.tv_order_number.text = info.ORDER_NO

        if (info.status == Order.PAYMENT) {
            holder.itemView.commodity_pay.text = "应付(含运费) :"
            holder.itemView.tv_commodity_pay.text = "￥" + info.PAYABLE_FEE
        } else {
            holder.itemView.commodity_pay.text = "实付(含运费) :"
            holder.itemView.tv_commodity_pay.text = "￥" + info.PAY_FEE
        }

        holder.itemView.product_recycler_view.adapter = OrderProductAdapter(context, info.PRODUCT_LIST, {
            itemClickListener.onItemClick(info)
        })
        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(info)
        }


        when (info.status) {
            Order.PAYMENT -> {
                holder.itemView.order_status.text = "待付款"
                holder.itemView.tv_confirm.text = "付款"
                holder.itemView.tv_cancel.text = "取消"
                holder.itemView.tv_confirm.visibility = View.VISIBLE
                holder.itemView.tv_cancel.visibility = View.VISIBLE

                holder.itemView.tv_confirm.setOnClickListener {
                    //付款
                    itemClickListener.onPayment(info)
                }
                holder.itemView.tv_cancel.setOnClickListener {
                    //取消
                    itemClickListener.onCancelOrder(info)
                }
            }
            Order.WAIT_SEND -> {
                holder.itemView.order_status.text = "待发货"
                holder.itemView.tv_cancel.visibility = View.GONE
                holder.itemView.tv_confirm.text = "详情"
                holder.itemView.tv_confirm.visibility = View.VISIBLE
//                holder.itemView.tv_confirm.setBackgroundResource(R.drawable.shape_order_btn_cancel)
//                holder.itemView.tv_confirm.setTextColor(context.resources.getColor(R.color.black))

                holder.itemView.tv_confirm.setOnClickListener {
                    //详情
                    itemClickListener.onItemClick(info)
                }
                holder.itemView.tv_cancel.setOnClickListener {
                    //null
                }
            }
            Order.WAIT_RECEIVE -> {
                holder.itemView.order_status.text = "待收货"
                holder.itemView.tv_confirm.text = "收货"
                holder.itemView.tv_cancel.text = "物流"
                holder.itemView.tv_confirm.visibility = View.VISIBLE
                holder.itemView.tv_cancel.visibility = View.VISIBLE

                holder.itemView.tv_confirm.setOnClickListener {
                    //收货
                    itemClickListener.onReceived(info)
                }
                holder.itemView.tv_cancel.setOnClickListener {
                    //物流
                    itemClickListener.onExpress(info)
                }
            }
            Order.EVALUATE -> {
                holder.itemView.order_status.text = "待评价"
                holder.itemView.tv_confirm.text = "评价"
                holder.itemView.tv_cancel.text = "再次购买"
                holder.itemView.tv_confirm.visibility = View.VISIBLE
                holder.itemView.tv_cancel.visibility = View.VISIBLE

                holder.itemView.tv_confirm.setOnClickListener {
                    //评价
                    itemClickListener.onEvaluate(info)
                }
                holder.itemView.tv_cancel.setOnClickListener {
                    //再次购买
                    itemClickListener.onBuyAgain(info)
                }
            }
            Order.FINFISH -> {
                holder.itemView.order_status.text = "已完成"
                holder.itemView.tv_confirm.text = "再次购买"
                holder.itemView.tv_confirm.visibility = View.VISIBLE
                holder.itemView.tv_cancel.visibility = View.GONE

                holder.itemView.tv_confirm.setOnClickListener {
                    //再次购买
                    itemClickListener.onBuyAgain(info)
                }
                holder.itemView.tv_cancel.setOnClickListener {
                }
            }
            else -> {
            }
        }

//        val tv_confirmText = holder.itemView.tv_confirm.text.toString()
//        holder.itemView.tv_confirm.setOnClickListener {
//            val intent: Intent
//            when (tv_confirmText) {
//                "付款" -> {
//                    intent = Intent(context, OrderDetailActivity::class.java)
//                    intent.putExtra("order_status", Order.PAYMENT)
//                    context.startActivity(intent)
//                }
//                "详情" -> {
//                    intent = Intent(context, OrderDetailActivity::class.java)
//                    intent.putExtra("order_status", Order.WAIT_SEND)
//                    context.startActivity(intent)
//                }
//                "收货" -> {
//                    intent = Intent(context, OrderDetailActivity::class.java)
//                    intent.putExtra("order_status", Order.WAIT_RECEIVE)
//                    context.startActivity(intent)
//                }
//                "评价" -> {
//                    intent = Intent(context, OrderDetailActivity::class.java)
//                    intent.putExtra("order_status", Order.EVALUATE)
//                    context.startActivity(intent)
//                }
//            }
//        }
//
//        val tv_cancelText = holder.itemView.tv_cancel.text.toString()
//        holder.itemView.tv_cancel.setOnClickListener {
//            when (tv_cancelText) {
//                "取消" -> initMessageDialog(context as Activity)
//                "物流" -> {
//                    val intent = Intent(context, LogisticsDetailActivity::class.java)
//                    context.startActivity(intent)
//                }
//                "再次购买" -> {
//                }
//            }
//        }
    }

    override fun getItemCount() = list?.size ?: 0

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}
