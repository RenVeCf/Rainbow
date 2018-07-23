package com.ipd.taxiu.adapter

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.*
import com.ipd.taxiu.utils.IndicatorHelper
import kotlinx.android.synthetic.main.item_lable.view.*
import kotlinx.android.synthetic.main.item_store_index_special.view.*
import kotlinx.android.synthetic.main.layout_store_dog_header.view.*

/**
 * Created by jumpbox on 2017/8/31.
 */
class StoreAdapter(val context: Context, private val list: List<Any>?, val onPetTypeChange: (type: Int) -> Unit) : RecyclerView.Adapter<StoreAdapter.ViewHolder>() {

    object ItemType {
        const val HEADER_DOG: Int = 0
        const val HEADER_CAT: Int = 1
        const val SPECIAL: Int = 2
        const val RECOMMEND_VIDEO: Int = 3
        const val RECOMMEND_PRODUCT_HEADER: Int = 4
        const val RECOMMEND_PRODUCT: Int = 5
    }


    override fun getItemCount(): Int {
        return list?.size ?: 0
    }


    override fun getItemViewType(position: Int): Int {
        val info = list!![position]
        return when (info) {
            is StoreIndexHeaderBean -> {//头部
                if ((info).type == StoreIndexBean.DOG) {
                    ItemType.HEADER_DOG
                } else {
                    ItemType.HEADER_CAT
                }
            }
            is StoreIndexSpecialBean -> {//专区
                ItemType.SPECIAL
            }
            is StoreIndexVideoBean -> {//视频
                ItemType.RECOMMEND_VIDEO
            }
            is StoreRecommendProductHeaderBean -> {//推荐商品头部
                ItemType.RECOMMEND_PRODUCT_HEADER
            }
            else -> {//推荐商品
                ItemType.RECOMMEND_PRODUCT
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return when (viewType) {
            ItemType.HEADER_DOG -> {
                ViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_store_dog_header, parent, false))
            }
            ItemType.HEADER_CAT -> {
                ViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_store_cat_header, parent, false))
            }
            ItemType.SPECIAL -> {
                ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_store_index_special, parent, false))
            }
            ItemType.RECOMMEND_VIDEO -> {
                ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_store_recommend_video, parent, false))
            }
            ItemType.RECOMMEND_PRODUCT_HEADER -> {
                ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_store_recommend_header, parent, false))
            }
            else -> {
                ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_product_grid, parent, false))
            }

        }


    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            ItemType.HEADER_DOG -> {
                val headerInfo = list!![position] as StoreIndexHeaderBean

                holder.itemView.ll_dog.isSelected = true
                holder.itemView.ll_dog.setOnClickListener { }
                holder.itemView.ll_cat.setOnClickListener {
                    onPetTypeChange(StoreIndexBean.CAT)
                }

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

                holder.itemView.dog_category_recycler_view.adapter = StoreIndexCategoryAdapter(context, headerInfo.categoryList, {

                })


            }
            ItemType.HEADER_CAT -> {
                val headerInfo = list!![position] as StoreIndexHeaderBean

                holder.itemView.ll_cat.isSelected = true
                holder.itemView.ll_cat.setOnClickListener { }
                holder.itemView.ll_dog.setOnClickListener {
                    onPetTypeChange(StoreIndexBean.DOG)
                }

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

                holder.itemView.dog_category_recycler_view.adapter = StoreIndexCategoryAdapter(context, headerInfo.categoryList, {

                })


            }
            ItemType.SPECIAL -> {
                val specialInfo = list!![position] as StoreIndexSpecialBean
                holder.itemView.tv_special_name.text = specialInfo.specialName
                holder.itemView.iv_special_icon.setImageResource(specialInfo.specialRes)

                holder.itemView.lable_flow_layout.removeAllViews()
                specialInfo.lableList.forEach {
                    val lableView = LayoutInflater.from(context).inflate(R.layout.item_lable, holder.itemView.lable_flow_layout, false)
                    lableView.tv_lable_name.text = it
                    holder.itemView.lable_flow_layout.addView(lableView)
                }
                holder.itemView.special_product_recycler_view.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                holder.itemView.special_product_recycler_view.adapter = SpecialProductAdapter(context, specialInfo.productList, {
                    //itemClick

                })
            }
            ItemType.RECOMMEND_VIDEO -> {

            }
            ItemType.RECOMMEND_PRODUCT_HEADER -> {

            }
            else -> {

            }
        }

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}