package com.ipd.taxiu.adapter

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.StoreIndexVideoBean
import com.ipd.taxiu.bean.StoreProductScreenBean
import com.ipd.taxiu.bean.StoreSpecialHeaderBean
import com.ipd.taxiu.ui.activity.store.ProductDetailActivity
import com.ipd.taxiu.utils.IndicatorHelper
import kotlinx.android.synthetic.main.layout_menu.view.*
import kotlinx.android.synthetic.main.layout_store_banner.view.*
import kotlinx.android.synthetic.main.layout_store_small_banner.view.*

/**
 * Created by jumpbox on 2017/8/31.
 */
class StoreSpecialAdapter(val context: Context, private val list: List<Any>?, val onScreenItemClick: (pos: Int) -> Unit) : RecyclerView.Adapter<StoreSpecialAdapter.ViewHolder>() {

    object ItemType {
        const val HEADER: Int = 0
        const val RECOMMEND_VIDEO: Int = 1
        const val PRODUCT_SCREEN: Int = 2
        const val PRODUCT: Int = 3
    }


    override fun getItemCount(): Int {
        return list?.size ?: 0
    }


    override fun getItemViewType(position: Int): Int {
        val info = list!![position]
        return when (info) {
            is StoreSpecialHeaderBean -> {//头部
                ItemType.HEADER
            }
            is StoreIndexVideoBean -> {//视频
                ItemType.RECOMMEND_VIDEO
            }
            is StoreProductScreenBean -> {//筛选
                ItemType.PRODUCT_SCREEN
            }
            else -> {//推荐商品
                ItemType.PRODUCT
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return when (viewType) {
            ItemType.HEADER -> {
                ViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_store_special_header, parent, false))
            }
            ItemType.RECOMMEND_VIDEO -> {
                ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_store_recommend_video, parent, false))
            }
            ItemType.PRODUCT_SCREEN -> {
                ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_store_special_screen, parent, false))
            }
            else -> {
                ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_product, parent, false))
            }

        }


    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            ItemType.HEADER -> {
                val headerInfo = list!![position] as StoreSpecialHeaderBean
                holder.itemView.menu_layout.setMenu(headerInfo.menuList)

                holder.itemView.store_banner.adapter = BannerPagerAdapter(context, headerInfo.bannerList)
                IndicatorHelper.newInstance().setRes(R.mipmap.boutique_selected, R.mipmap.boutique_unselected)
                        .setIndicator(context, headerInfo.bannerList.size, holder.itemView.store_banner, holder.itemView.store_banner_indicator, object : IndicatorHelper.MyPagerChangeListener {
                            override fun onPageSelected(pos: Int) {
                            }

                            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                            }

                            override fun onPageScrollStateChanged(state: Int) {
                            }

                        })
                if (!holder.itemView.store_banner.isAutoScroll) {
                    holder.itemView.store_banner.startAutoScroll()
                }

                holder.itemView.store_banner_small.adapter = SmallBannerPagerAdapter(context, headerInfo.bannerList)
                IndicatorHelper.newInstance().setRes(R.mipmap.boutique_selected, R.mipmap.boutique_unselected)
                        .setIndicator(context, headerInfo.smallBannerList.size, holder.itemView.store_banner_small, holder.itemView.small_banner_indicator, object : IndicatorHelper.MyPagerChangeListener {
                            override fun onPageSelected(pos: Int) {
                            }

                            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                            }

                            override fun onPageScrollStateChanged(state: Int) {
                            }

                        })
                if (!holder.itemView.store_banner_small.isAutoScroll) {
                    holder.itemView.store_banner_small.startAutoScroll()
                }

            }
            ItemType.RECOMMEND_VIDEO -> {

            }
            ItemType.PRODUCT_SCREEN -> {
                holder.itemView.setOnClickListener {
                    onScreenItemClick.invoke(position)
                }

            }
            else -> {
                holder.itemView.setOnClickListener {
                    //商品详情
                    ProductDetailActivity.launch(context as Activity)
                }

            }
        }

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}