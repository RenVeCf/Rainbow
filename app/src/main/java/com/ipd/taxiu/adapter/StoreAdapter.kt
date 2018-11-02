package com.ipd.taxiu.adapter

import android.app.Activity
import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.*
import com.ipd.taxiu.imageload.ImageLoader
import com.ipd.taxiu.ui.activity.store.*
import com.ipd.taxiu.ui.activity.store.flashsale.FlashSaleActivity
import com.ipd.taxiu.ui.activity.store.grouppurchase.GroupPurchaseActivity
import com.ipd.taxiu.ui.activity.store.video.StoreVideoDetailActivity
import com.ipd.taxiu.ui.activity.store.video.StoreVideoIndexActivity
import com.ipd.taxiu.utils.BannerUtils
import com.ipd.taxiu.utils.IndicatorHelper
import com.ipd.taxiu.utils.StorePetSpecialType
import kotlinx.android.synthetic.main.item_lable.view.*
import kotlinx.android.synthetic.main.item_product_grid.view.*
import kotlinx.android.synthetic.main.item_store_index_special.view.*
import kotlinx.android.synthetic.main.item_store_recommend_video.view.*
import kotlinx.android.synthetic.main.layout_store_banner.view.*
import kotlinx.android.synthetic.main.layout_store_cat_header.view.*
import kotlinx.android.synthetic.main.layout_store_dog_header.view.*
import kotlinx.android.synthetic.main.layout_store_home_function.view.*
import kotlinx.android.synthetic.main.layout_store_menu.view.*
import kotlinx.android.synthetic.main.layout_store_second_header.view.*
import kotlinx.android.synthetic.main.layout_store_small_banner.view.*

/**
 * Created by jumpbox on 2017/8/31.
 */
class StoreAdapter(val context: Context, private val list: List<Any>?, val onPetTypeChange: (type: Int) -> Unit) : RecyclerView.Adapter<StoreAdapter.ViewHolder>() {

    object ItemType {
        const val HEADER_DOG: Int = 0
        const val HEADER_CAT: Int = 1
        const val SECOND_HEADER: Int = 6
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
            is StoreSecondIndexHeaderBean -> {//二级页面头部
                ItemType.SECOND_HEADER
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
            ItemType.SECOND_HEADER -> {
                ViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_store_second_header, parent, false))
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

                holder.itemView.tv_dog_store_menu_title.setTextColor(context.resources.getColor(R.color.colorPrimaryDark))
                holder.itemView.tv_cat_store_menu_title.setTextColor(context.resources.getColor(R.color.LightGrey))
                holder.itemView.ll_dog.isSelected = true
                holder.itemView.ll_dog.setOnClickListener { }
                holder.itemView.ll_cat.setOnClickListener {
                    onPetTypeChange(StoreIndexBean.CAT)
                }

                holder.itemView.fl_small_dog.setOnClickListener {
                    StoreSecondIndexActivity.launch(context as Activity, StorePetSpecialType.SMALL_DOG)
                }
                holder.itemView.fl_big_dog.setOnClickListener {
                    StoreSecondIndexActivity.launch(context as Activity, StorePetSpecialType.BIG_DOG)
                }
                holder.itemView.fl_young_dog.setOnClickListener {
                    StoreSecondIndexActivity.launch(context as Activity, StorePetSpecialType.YOUNG_DOG)
                }



                setPublishListener(holder, headerInfo)
            }
            ItemType.HEADER_CAT -> {
                val headerInfo = list!![position] as StoreIndexHeaderBean

                holder.itemView.tv_cat_store_menu_title.setTextColor(context.resources.getColor(R.color.colorPrimaryDark))
                holder.itemView.tv_dog_store_menu_title.setTextColor(context.resources.getColor(R.color.LightGrey))
                holder.itemView.ll_cat.isSelected = true
                holder.itemView.ll_cat.setOnClickListener { }
                holder.itemView.ll_dog.setOnClickListener {
                    onPetTypeChange(StoreIndexBean.DOG)
                }

                holder.itemView.fl_young_cat.setOnClickListener {
                    StoreSecondIndexActivity.launch(context as Activity, StorePetSpecialType.YOUNG_CAT)
                }
                holder.itemView.fl_adult_cat.setOnClickListener {
                    StoreSecondIndexActivity.launch(context as Activity, StorePetSpecialType.ADULT_CAT)
                }

                setPublishListener(holder, headerInfo)

            }
            ItemType.SECOND_HEADER -> {
                val headerInfo = list!![position] as StoreSecondIndexHeaderBean

                holder.itemView.store_banner.adapter = BannerPagerAdapter(context, headerInfo.bannerList)
                IndicatorHelper.newInstance().setRes(R.mipmap.boutique_selected, R.mipmap.boutique_unselected)
                        .setIndicator(context, headerInfo.bannerList.size, holder.itemView.store_banner, holder.itemView.store_banner_indicator, null)
                if (!holder.itemView.store_banner.isAutoScroll) {
                    holder.itemView.store_banner.startAutoScroll()
                }

                holder.itemView.second_category_recycler_view.adapter = StoreIndexCategoryAdapter(context, headerInfo.categoryList, {
                    //专区
                    StoreSpecialActivity.launch(context as Activity, it.TYPE_ID,it.TYPE_NAME)
                })


            }
            ItemType.SPECIAL -> {
                val specialInfo = list!![position] as StoreIndexSpecialBean
                holder.itemView.tv_special_name.text = specialInfo.TYPE_NAME
                ImageLoader.loadNoPlaceHolderImg(context, specialInfo.ICON, holder.itemView.iv_special_icon)
                ImageLoader.loadNoPlaceHolderImg(context, specialInfo.PIC, holder.itemView.iv_special_banner)

                holder.itemView.lable_flow_layout.removeAllViews()
                specialInfo.BRAND_LIST.forEach { info ->
                    val lableView = LayoutInflater.from(context).inflate(R.layout.item_lable, holder.itemView.lable_flow_layout, false)
                    lableView.tv_lable_name.text = info.BRAND_NAME
                    lableView.setOnClickListener {
                        //商品列表
                        ProductListActivity.launch(context as Activity, searchKey = info.BRAND_NAME)
                    }
                    holder.itemView.lable_flow_layout.addView(lableView)
                }
                holder.itemView.special_product_recycler_view.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
                holder.itemView.special_product_recycler_view.adapter = SpecialProductAdapter(context, specialInfo.PRODUCT_LIST, {
                    //商品详情
                    ProductDetailActivity.launch(context as Activity, it.PRODUCT_ID, it.FORM_ID)
                })
                holder.itemView.ll_special_more.setOnClickListener {
                    //查看更多
                    StoreSpecialActivity.launch(context as Activity,specialInfo.TYPE_ID,specialInfo.TYPE_NAME)
                }

                holder.itemView.iv_special_banner.setOnClickListener {
                    val bannerBean = BannerBean()
                    bannerBean.CATEGORY = specialInfo.KIND
                    bannerBean.URL = specialInfo.URL
                    bannerBean.CONTENT = specialInfo.CONTENT
                    bannerBean.PRODUCT_ID = specialInfo.PRODUCT_ID
                    bannerBean.FORM_ID = specialInfo.FORM_ID
                    BannerUtils.setBannerItemClick(context,bannerBean)
                }

            }
            ItemType.RECOMMEND_VIDEO -> {
                val recommendInfo = list!![position] as StoreIndexVideoBean
                holder.itemView.recommend_video_recycler_view.adapter = StoreIndexRecommendVideoAdapter(context, recommendInfo.videoList, {
                    //视频详情
                    StoreVideoDetailActivity.launch(context as Activity, it.VIDEO_ID.toString())
                })

            }
            ItemType.RECOMMEND_PRODUCT_HEADER -> {

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

        //下方小banner
        holder.itemView.store_banner_small.adapter = SmallBannerPagerAdapter(context, headerInfo.smallBannerList)
        IndicatorHelper.newInstance().setRes(R.mipmap.boutique_selected, R.mipmap.boutique_unselected)
                .setIndicator(context, headerInfo.smallBannerList.size, holder.itemView.store_banner_small, holder.itemView.small_banner_indicator, null)
        if (!holder.itemView.store_banner_small.isAutoScroll) {
            holder.itemView.store_banner_small.startAutoScroll()
        }

        //专区分类
        holder.itemView.dog_category_recycler_view.adapter = StoreIndexCategoryAdapter(context, headerInfo.categoryList, {
            //专区
            StoreSpecialActivity.launch(context as Activity, it.TYPE_ID,it.TYPE_NAME)
        })

        holder.itemView.iv_store_purchase.setOnClickListener {
            //限时抢购
            FlashSaleActivity.launch(context as Activity)
        }
        holder.itemView.iv_store_spell.setOnClickListener {
            //团购
            GroupPurchaseActivity.launch(context as Activity)
        }
        holder.itemView.iv_store_new.setOnClickListener {
            //上新
            NewProductListActivity.launch(context as Activity)
        }
        holder.itemView.iv_store_clear.setOnClickListener {
            //清仓
            ClearanceProductActivity.launch(context as Activity)
        }
        holder.itemView.iv_store_video.setOnClickListener {
            //视频
            StoreVideoIndexActivity.launch(context as Activity, headerInfo.type)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}