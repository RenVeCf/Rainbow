package com.ipd.rainbow.adapter

import android.app.Activity
import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.rainbow.R
import com.ipd.rainbow.bean.BannerBean
import com.ipd.rainbow.bean.ProductBean
import com.ipd.rainbow.bean.StoreIndexHeaderBean
import com.ipd.rainbow.bean.StoreIndexSpecialBean
import com.ipd.rainbow.imageload.ImageLoader
import com.ipd.rainbow.ui.activity.store.ClearanceProductActivity
import com.ipd.rainbow.ui.activity.store.NewProductListActivity
import com.ipd.rainbow.ui.activity.store.ProductDetailActivity
import com.ipd.rainbow.ui.activity.store.ProductListActivity
import com.ipd.rainbow.ui.activity.store.flashsale.FlashSaleActivity
import com.ipd.rainbow.ui.activity.store.video.StoreVideoIndexActivity
import com.ipd.rainbow.utils.BannerUtils
import com.ipd.rainbow.utils.IndicatorHelper
import kotlinx.android.synthetic.main.item_lable.view.*
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


                val productList = ArrayList<ProductBean>()
                for (index in 0 until 10) {
                    val productBean = ProductBean()
                    productBean.LOGO = "/upload/product/20190125/cklfxara5ldbd0yggqvk0vfnfxzqnu5k.jpg"
                    productBean.PROCUCT_NAME = "测试1"
                    productBean.CURRENT_PRICE = "590.00"
                    productList.add(productBean)
                }
                holder.itemView.new_product_recycler_view.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                holder.itemView.new_product_recycler_view.adapter = StoreNewProductAdapter(context, productList) {

                }

                holder.itemView.sales_product_recycler_view.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                holder.itemView.sales_product_recycler_view.adapter = StoreNewProductAdapter(context, productList) {

                }



                setPublishListener(holder, headerInfo)
            }
            ItemType.SPECIAL -> {
                val specialInfo = list!![position] as StoreIndexSpecialBean


//                holder.itemView.tv_special_name.text = specialInfo.TYPE_NAME + "专区"
//                ImageLoader.loadNoPlaceHolderImg(context, specialInfo.ICON, holder.itemView.iv_special_icon)
//                ImageLoader.loadNoPlaceHolderImg(context, specialInfo.PIC, holder.itemView.iv_special_banner)
//
//                holder.itemView.lable_flow_layout.removeAllViews()
//                specialInfo.BRAND_LIST.forEach { info ->
//                    val lableView = LayoutInflater.from(context).inflate(R.layout.item_lable, holder.itemView.lable_flow_layout, false)
//                    lableView.tv_lable_name.text = info.BRAND_NAME
//                    lableView.setOnClickListener {
//                        //商品列表
//                        ProductListActivity.launch(context as Activity, searchKey = info.BRAND_NAME)
//                    }
//                    holder.itemView.lable_flow_layout.addView(lableView)
//                }
//                holder.itemView.special_product_recycler_view.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
//                holder.itemView.special_product_recycler_view.adapter = SpecialProductAdapter(context, specialInfo.PRODUCT_LIST) {
//                    //商品详情
//                    ProductDetailActivity.launch(context as Activity, it.PRODUCT_ID, it.FORM_ID)
//                }
//                holder.itemView.ll_special_more.setOnClickListener {
//                    //查看更多
////                    StoreSpecialActivity.launch(context as Activity, specialInfo.TYPE_ID, specialInfo.TYPE_NAME)
//                    ProductListActivity.launch(context as Activity, specialInfo.TYPE_NAME, areaTypeId = specialInfo.TYPE_ID)
//                }
//
//                holder.itemView.iv_special_banner.setOnClickListener {
//                    val bannerBean = BannerBean()
//                    bannerBean.CATEGORY = specialInfo.KIND
//                    bannerBean.URL = specialInfo.URL
//                    bannerBean.CONTENT = specialInfo.CONTENT
//                    bannerBean.PRODUCT_ID = specialInfo.PRODUCT_ID
//                    bannerBean.FORM_ID = specialInfo.FORM_ID
//                    BannerUtils.setBannerItemClick(context, bannerBean)
//                }

            }
            else -> {
                val productInfo = list!![position] as ProductBean
                ImageLoader.loadNoPlaceHolderImg(context, productInfo.LOGO, holder.itemView.iv_product_img)
                holder.itemView.tv_product_name.text = productInfo.PROCUCT_NAME
                holder.itemView.tv_product_price.text = "￥${productInfo.PRICE}"
                holder.itemView.tv_product_evalute.text = "评价 ${productInfo.REPLY}"
                holder.itemView.tv_product_sales.text = "销量 ${productInfo.FORM_BUYNUM}"


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
            StoreVideoIndexActivity.launch(context as Activity, headerInfo.type)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}