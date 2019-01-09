package com.ipd.rainbow.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.jumpbox.jumpboxlibrary.utils.ToastCommom
import com.ipd.rainbow.R
import com.ipd.rainbow.bean.BaseResult
import com.ipd.rainbow.bean.ExchangeBean
import com.ipd.rainbow.platform.global.GlobalApplication
import com.ipd.rainbow.platform.global.GlobalParam
import com.ipd.rainbow.platform.http.ApiManager
import com.ipd.rainbow.platform.http.Response
import com.ipd.rainbow.platform.http.RxScheduler
import kotlinx.android.synthetic.main.item_product_coupon.view.*

/**
 * Created by jumpbox on 2017/8/31.
 */
class ProductCouponAdapter(val context: Context, private val list: List<ExchangeBean>?, private val itemClick: (info: ExchangeBean) -> Unit) : RecyclerView.Adapter<ProductCouponAdapter.ViewHolder>() {

    override fun getItemCount(): Int = list?.size ?: 0


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_product_coupon, parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = list!![position]

        holder.itemView.tv_coupon_money.text = info.PRICE
        holder.itemView.tv_coupon_condition.text = "满${info.SATISFY_PRICE}可用"
        holder.itemView.tv_coupon_validity.text = "有效期至 ${info.END_TIME}"
        holder.itemView.tv_coupon_name.text = if (info.CATEGORY == 1) "单品类优惠券" else "全品类优惠券"
        holder.itemView.tv_take_it.visibility = if (info.IS_RECEIVE == 0) View.VISIBLE else View.GONE

        holder.itemView.tv_take_it.setOnClickListener {
            //领取优惠券
            ApiManager.getService().takeItCoupon(GlobalParam.getUserIdOrJump(), info.COUPON_ID)
                    .compose(RxScheduler.applyScheduler())
                    .subscribe(object : Response<BaseResult<ExchangeBean>>(context, true) {
                        override fun _onNext(result: BaseResult<ExchangeBean>) {
                            if (result.code == 0) {
                                info.IS_RECEIVE = 1
                                notifyItemChanged(position)
                            } else {
                                ToastCommom.getInstance().show(GlobalApplication.mContext, result.msg)
                            }
                        }
                    })

        }


        holder.itemView.setOnClickListener {
            itemClick.invoke(info)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}