package com.ipd.rainbow.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.jumpbox.jumpboxlibrary.utils.ToastCommom
import com.ipd.rainbow.R
import com.ipd.rainbow.bean.BaseResult
import com.ipd.rainbow.bean.ProductBean
import com.ipd.rainbow.imageload.ImageLoader
import com.ipd.rainbow.platform.global.GlobalApplication
import com.ipd.rainbow.platform.global.GlobalParam
import com.ipd.rainbow.platform.http.ApiManager
import com.ipd.rainbow.platform.http.Response
import com.ipd.rainbow.platform.http.RxScheduler
import com.ipd.rainbow.utils.StoreType
import com.ipd.rainbow.utils.StringUtils
import kotlinx.android.synthetic.main.item_product.view.*

/**
 * Created by jumpbox on 2017/8/31.
 */
class ProductAdapter(val context: Context, private val list: List<ProductBean>?, private val itemClick: (info: ProductBean) -> Unit) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    override fun getItemCount(): Int = list?.size ?: 0

    object ItemType {
        const val LIST = 0
        const val GRID = 1
    }

    var mType: Int = ItemType.GRID

    fun switchShowType(): Int {
        mType = if (mType == ItemType.LIST) ItemType.GRID else ItemType.LIST
        return mType
    }

    override fun getItemViewType(position: Int): Int {
        return mType
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return when (viewType) {
            ItemType.LIST -> {
                ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_product, parent, false))
            }
            else -> {
                ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_product_grid, parent, false))
            }
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = list!![position]
        when (getItemViewType(position)) {
            ItemType.LIST -> {
                holder.itemView.iv_new_product.visibility = if (info.isNew) View.VISIBLE else View.GONE

                ImageLoader.loadNoPlaceHolderImg(context, info.LOGO, holder.itemView.iv_product_img)
                holder.itemView.tv_product_name.text = info.NAME
                holder.itemView.tv_product_price.text = "￥${StringUtils.formatPrice(info.CURRENT_PRICE)}"
                holder.itemView.tv_product_price_old.text = "￥${StringUtils.formatPrice(info.PRICE)}"
                holder.itemView.tv_product_price_old.visibility = if (info.KIND == StoreType.PRODUCT_NORMAL) View.GONE else View.VISIBLE

                holder.itemView.tv_product_evalute.text = "评价 ${info.ASSESS}"
                holder.itemView.tv_product_sales.text = "销量 ${info.SALE}"

                holder.itemView.tv_product_lable.visibility = if (info.KIND == 1) View.GONE else View.VISIBLE
                holder.itemView.tv_product_lable.text = info.kindStr

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
            ItemType.GRID -> {
                ImageLoader.loadNoPlaceHolderImg(context, info.LOGO, holder.itemView.iv_product_img)
                holder.itemView.tv_product_name.text = info.NAME

                holder.itemView.tv_product_unit.visibility = if (StringUtils.priceNeedEncry(info.CURRENT_PRICE, info.KIND)) View.GONE else View.VISIBLE
                holder.itemView.tv_product_price.text = StringUtils.getEncryPrice(false, info.CURRENT_PRICE, info.KIND)

                holder.itemView.tv_product_evalute.text = "评 ${info.ASSESS}"
                holder.itemView.tv_product_sales.text = "销 ${info.SALE}"

            }
        }
        holder.itemView.setOnClickListener { itemClick.invoke(info) }

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}