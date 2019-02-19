package com.ipd.rainbow.adapter

import android.app.Activity
import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.rainbow.R
import com.ipd.rainbow.bean.ProductBean
import com.ipd.rainbow.bean.StoreIndexHeaderBean
import com.ipd.rainbow.bean.StoreIndexSpecialBean
import com.ipd.rainbow.imageload.ImageLoader
import com.ipd.rainbow.ui.activity.store.ClearanceProductActivity
import com.ipd.rainbow.ui.activity.store.NewProductListActivity
import com.ipd.rainbow.ui.activity.store.ProductDetailActivity
import com.ipd.rainbow.ui.activity.store.flashsale.FlashSaleActivity
import com.ipd.rainbow.utils.IndicatorHelper
import kotlinx.android.synthetic.main.item_product_grid.view.*
import kotlinx.android.synthetic.main.item_store_index_special.view.*
import kotlinx.android.synthetic.main.item_store_today_new.view.*
import kotlinx.android.synthetic.main.item_store_today_sales.view.*
import kotlinx.android.synthetic.main.layout_store_banner.view.*
import kotlinx.android.synthetic.main.layout_store_header.view.*
import kotlinx.android.synthetic.main.layout_store_home_function.view.*

/**
 * Created by jumpbox on 2017/8/31.
 */
class StoreAdapter(val context: Context, private val list: List<Any>?, val onPetTypeChange: (type: Int) -> Unit) : RecyclerView.Adapter<StoreAdapter.ViewHolder>() {

    object ItemType {
        const val HEADER: Int = 0
        const val SPECIAL: Int = 2
        const val RECOMMEND_PRODUCT: Int = 5
    }


    override fun getItemCount(): Int {
        return list?.size ?: 0
    }


    override fun getItemViewType(position: Int): Int {
        val info = list!![position]
        return when (info) {
            is StoreIndexHeaderBean -> {//头部
                ItemType.HEADER
            }
            is StoreIndexSpecialBean -> {//专区
                ItemType.SPECIAL
            }
            else -> {//推荐商品
                ItemType.RECOMMEND_PRODUCT
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return when (viewType) {
            ItemType.HEADER -> {
                ViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_store_header, parent, false))
            }
            ItemType.SPECIAL -> {
                ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_store_index_special, parent, false))
            }
            else -> {
                ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_product_grid, parent, false))
            }

        }


    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            ItemType.HEADER -> {
                val headerInfo = list!![position] as StoreIndexHeaderBean

                //菜单
                holder.itemView.store_menu.adapter = StoreIndexCategoryAdapter(context, headerInfo.categoryList) {
                    //专区
//                    ProductListActivity.launch(context as Activity, "", shopTypeId = it.TYPE_ID)
                }


                holder.itemView.new_product_recycler_view.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                holder.itemView.new_product_recycler_view.adapter = StoreNewProductAdapter(context, headerInfo.todayNew) {

                }

                holder.itemView.sales_product_recycler_view.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                holder.itemView.sales_product_recycler_view.adapter = StoreNewProductAdapter(context, headerInfo.todaySale) {

                }



                setPublishListener(holder, headerInfo)
            }
            ItemType.SPECIAL -> {
                val specialInfo = list!![position] as StoreIndexSpecialBean

                ImageLoader.loadNoPlaceHolderImg(context, specialInfo.PIC, holder.itemView.iv_special_banner)
                holder.itemView.tv_special_name.text = specialInfo.NAME
                holder.itemView.tv_special_desc.text = specialInfo.CONTENT

                val productList = specialInfo.PRODUCT_LIST
                if (productList == null || productList.isEmpty()) {
                    holder.itemView.ll_sales_product_left.visibility = View.GONE
                    holder.itemView.ll_sales_product_right.visibility = View.GONE
                } else {
                    val leftProduct = productList[0]
                    val rightProduct = if (productList.size >= 2) productList[1] else null

                    //left
                    holder.itemView.ll_sales_product_left.visibility = View.VISIBLE
                    ImageLoader.loadNoPlaceHolderImg(context, leftProduct.LOGO, holder.itemView.iv_special_product_left)
                    holder.itemView.tv_special_product_left_name.text = leftProduct.NAME
                    holder.itemView.tv_special_product_left_price.text = "￥${leftProduct.CURRENT_PRICE}"

                    //right
                    if (rightProduct != null) {
                        holder.itemView.ll_sales_product_right.visibility = View.VISIBLE
                        ImageLoader.loadNoPlaceHolderImg(context, rightProduct.LOGO, holder.itemView.iv_special_product_right)
                        holder.itemView.tv_special_product_right_name.text = rightProduct.NAME
                        holder.itemView.tv_special_product_right_price.text = "￥${rightProduct.CURRENT_PRICE}"

                    } else {
                        holder.itemView.ll_sales_product_right.visibility = View.GONE
                    }

                }
            }
            else -> {
                val productInfo = list!![position] as ProductBean
                ImageLoader.loadNoPlaceHolderImg(context, productInfo.LOGO, holder.itemView.iv_product_img)
                holder.itemView.tv_product_name.text = productInfo.NAME
                holder.itemView.tv_product_price.text = "${productInfo.PRICE}"
                holder.itemView.tv_product_evalute.text = "评 ${productInfo.ASSESS}"
                holder.itemView.tv_product_sales.text = "售 ${productInfo.SALE}"


                holder.itemView.setOnClickListener {
                    //商品详情
                    ProductDetailActivity.launch(context as Activity, productInfo.PRODUCT_ID, productInfo.FORM_ID)
                }

            }
        }

    }

    private fun setPublishListener(holder: ViewHolder, headerInfo: StoreIndexHeaderBean) {
        //顶部大banner
        holder.itemView.store_banner.adapter = BannerPagerAdapter(context, headerInfo.bannerList)
        IndicatorHelper.newInstance().setRes(R.mipmap.boutique_selected, R.mipmap.boutique_unselected)
                .setIndicator(context, headerInfo.bannerList.size, holder.itemView.store_banner, holder.itemView.store_banner_indicator, null)
        if (!holder.itemView.store_banner.isAutoScroll) {
            holder.itemView.store_banner.startAutoScroll()
        }

        holder.itemView.iv_store_vip.setOnClickListener {
            //彩虹购VIP
            FlashSaleActivity.launch(context as Activity)
        }
        holder.itemView.iv_store_new.setOnClickListener {
            //上新
            NewProductListActivity.launch(context as Activity)
        }
        holder.itemView.iv_store_group.setOnClickListener {
            //团购
            ClearanceProductActivity.launch(context as Activity)
        }
        holder.itemView.iv_store_stock.setOnClickListener {
            //库存

        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}