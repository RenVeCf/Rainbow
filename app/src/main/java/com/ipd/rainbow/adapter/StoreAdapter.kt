package com.ipd.rainbow.adapter

import android.app.Activity
import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.rainbow.MainActivity
import com.ipd.rainbow.R
import com.ipd.rainbow.bean.ProductBean
import com.ipd.rainbow.bean.StoreIndexHeaderBean
import com.ipd.rainbow.bean.StoreIndexSpecialBean
import com.ipd.rainbow.imageload.ImageLoader
import com.ipd.rainbow.ui.activity.store.ProductDetailActivity
import com.ipd.rainbow.ui.activity.store.ProductListActivity
import com.ipd.rainbow.ui.activity.store.StoreSalesActivity
import com.ipd.rainbow.ui.activity.store.grouppurchase.GroupPurchaseActivity
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

                holder.itemView.iv_live.setOnClickListener {
                    if (context is MainActivity) {
                        context.switchToLive()
                    }
                }

                //菜单
                holder.itemView.store_menu.adapter = StoreIndexCategoryAdapter(context, headerInfo.categoryList) { pos, info ->
                    when (pos) {
                        1 -> GroupPurchaseActivity.launch(context as Activity)
                        else -> ProductListActivity.launch(context as Activity, typeId = info.TYPE_ID, typeTitle = info.NAME)
                    }
                }


                //每日上新
                holder.itemView.ll_today_new_more.setOnClickListener {
                    StoreSalesActivity.launch(context as Activity, 0)
                }
                holder.itemView.new_product_recycler_view.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                holder.itemView.new_product_recycler_view.adapter = StoreNewProductAdapter(context, headerInfo.todayNew) {
                    ProductDetailActivity.launch(context as Activity, it.PRODUCT_ID, it.FORM_ID)
                }

                //今日特价
                holder.itemView.ll_today_sales_more.setOnClickListener {
                    StoreSalesActivity.launch(context as Activity, 1)
                }
                holder.itemView.sales_product_recycler_view.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                holder.itemView.sales_product_recycler_view.adapter = StoreNewProductAdapter(context, headerInfo.todaySale) {
                    ProductDetailActivity.launch(context as Activity, it.PRODUCT_ID, it.FORM_ID)
                }

                //分类海报
                val categoryPic = arrayListOf(holder.itemView.iv_store_vip, holder.itemView.iv_store_new, holder.itemView.iv_store_group, holder.itemView.iv_store_stock)
                categoryPic.forEachIndexed { index, imageView ->
                    var logo = headerInfo.categoryPicList?.get(index)?.LOGO
                    if (!TextUtils.isEmpty(logo)) {
                        ImageLoader.loadNoPlaceHolderImg(context, logo, imageView)
                    }
                }


                setPublishListener(holder, headerInfo)
            }
            ItemType.SPECIAL -> {
                //活动专区
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

                    holder.itemView.ll_sales_product_left.setOnClickListener {
                        ProductDetailActivity.launch(context as Activity, leftProduct.PRODUCT_ID, leftProduct.FORM_ID)
                    }
                    holder.itemView.ll_sales_product_right.setOnClickListener {
                        if (rightProduct == null) return@setOnClickListener
                        ProductDetailActivity.launch(context as Activity, rightProduct.PRODUCT_ID, rightProduct.FORM_ID)
                    }

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

                holder.itemView.ll_special_more.setOnClickListener {
                    //查看更多
                    ProductListActivity.launch(context as Activity, typeId = specialInfo.TYPE_ID, typeTitle = specialInfo.NAME)
                }

            }
            else -> {
                //商品列表
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
            ProductListActivity.launch(context as Activity, typeId = 1, typeTitle = "彩虹会员专享")
        }
        holder.itemView.iv_store_new.setOnClickListener {
            //上新
            StoreSalesActivity.launch(context as Activity, 0)
        }
        holder.itemView.iv_store_group.setOnClickListener {
            //团购
            GroupPurchaseActivity.launch(context as Activity)
        }
        holder.itemView.iv_store_stock.setOnClickListener {
            //库存
            StoreSalesActivity.launch(context as Activity, 2)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}