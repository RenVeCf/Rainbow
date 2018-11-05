package com.ipd.taxiu.adapter

import android.content.Context
import android.graphics.Paint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.jumpbox.jumpboxlibrary.utils.ToastCommom
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.BaseResult
import com.ipd.taxiu.bean.ProductBean
import com.ipd.taxiu.imageload.ImageLoader
import com.ipd.taxiu.platform.global.GlobalApplication
import com.ipd.taxiu.platform.global.GlobalParam
import com.ipd.taxiu.platform.http.ApiManager
import com.ipd.taxiu.platform.http.Response
import com.ipd.taxiu.platform.http.RxScheduler
import kotlinx.android.synthetic.main.item_product_new.view.*

/**
 * Created by jumpbox on 2017/8/31.
 */
class NewProductAdapter(val context: Context, private val list: List<ProductBean>?, private val itemClick: (info: ProductBean) -> Unit) : RecyclerView.Adapter<NewProductAdapter.ViewHolder>() {

    override fun getItemCount(): Int = list?.size ?: 0


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_product_new, parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = list!![position]
        holder.itemView.tv_product_price_old.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG

        ImageLoader.loadNoPlaceHolderImg(context, info.LOGO, holder.itemView.iv_product_img)
        holder.itemView.tv_product_name.text = info.PROCUCT_NAME
        holder.itemView.tv_product_price.text = "￥${info.CURRENT_PRICE}"
        holder.itemView.tv_product_price_old.text = "￥${info.REFER_PRICE}"
        holder.itemView.tv_product_sales.text = "已售 ${info.BUYNUM}"


        holder.itemView.setOnClickListener {
            itemClick.invoke(info)
        }
        holder.itemView.iv_add_to_cart.setOnClickListener {
            ApiManager.getService().cartAdd(GlobalParam.getUserIdOrJump(), info.PRODUCT_ID, info.FORM_ID, 1)
                    .compose(RxScheduler.applyScheduler())
                    .subscribe(object : Response<BaseResult<Int>>(context, true) {
                        override fun _onNext(result: BaseResult<Int>) {
                            if (result.code == 0) {
                                ToastCommom.getInstance().show(GlobalApplication.mContext, true, context.resources.getString(R.string.add_cart_success))
                            } else {
                                ToastCommom.getInstance().show(GlobalApplication.mContext, result.msg)
                            }
                        }
                    })
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}