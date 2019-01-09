package com.ipd.rainbow.adapter

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.jumpbox.jumpboxlibrary.utils.ToastCommom
import com.ipd.rainbow.R
import com.ipd.rainbow.bean.*
import com.ipd.rainbow.imageload.ImageLoader
import com.ipd.rainbow.platform.global.GlobalApplication
import com.ipd.rainbow.platform.global.GlobalParam
import com.ipd.rainbow.platform.http.ApiManager
import com.ipd.rainbow.platform.http.Response
import com.ipd.rainbow.platform.http.RxScheduler
import com.ipd.rainbow.ui.activity.store.ProductDetailActivity
import com.ipd.rainbow.ui.activity.store.video.StoreVideoDetailActivity
import com.ipd.rainbow.utils.IndicatorHelper
import com.ipd.rainbow.utils.StoreType
import kotlinx.android.synthetic.main.item_product.view.*
import kotlinx.android.synthetic.main.item_special_recommend_header.view.*
import kotlinx.android.synthetic.main.item_store_recommend_video.view.*
import kotlinx.android.synthetic.main.layout_menu.view.*
import kotlinx.android.synthetic.main.layout_store_banner.view.*
import kotlinx.android.synthetic.main.layout_store_small_banner.view.*
import kotlinx.android.synthetic.main.layout_store_special_header.view.*

/**
 * Created by jumpbox on 2017/8/31.
 */
class StoreSpecialAdapter(val context: Context, val areaTitle: String, private val list: List<Any>?, val onScreenItemClick: (pos: Int) -> Unit) : RecyclerView.Adapter<StoreSpecialAdapter.ViewHolder>() {

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
                ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_special_recommend_header, parent, false))
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

                holder.itemView.tv_special_title.text = "${areaTitle}专区"

                holder.itemView.menu_layout.setMenu(headerInfo.menuList)

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

            }
            ItemType.RECOMMEND_VIDEO -> {
                val recommendInfo = list!![position] as StoreIndexVideoBean
                holder.itemView.recommend_video_recycler_view.adapter = StoreIndexRecommendVideoAdapter(context, recommendInfo.videoList, {
                    //视频详情
                    StoreVideoDetailActivity.launch(context as Activity, it.VIDEO_ID.toString())
                })
            }
            ItemType.PRODUCT_SCREEN -> {
//                holder.itemView.screen_layout.disallowClickable()
//                holder.itemView.setOnClickListener {
//                    onScreenItemClick.invoke(position)
//                    return@setOnClickListener
//                }
                holder.itemView.tv_recommend_title.text = areaTitle
            }
            else -> {
                val productInfo = list!![position] as ProductBean
                holder.itemView.iv_new_product.visibility = if (productInfo.isNew) View.VISIBLE else View.GONE

                ImageLoader.loadNoPlaceHolderImg(context, productInfo.LOGO, holder.itemView.iv_product_img)
                holder.itemView.tv_product_name.text = productInfo.PROCUCT_NAME
                holder.itemView.tv_product_price.text = "￥${productInfo.CURRENT_PRICE}"
                holder.itemView.tv_product_price_old.text = "￥${productInfo.REFER_PRICE}"
                holder.itemView.tv_product_price_old.visibility = if (productInfo.KIND == StoreType.PRODUCT_NORMAL) View.GONE else View.VISIBLE


                holder.itemView.tv_product_evalute.text = "评价 ${productInfo.REPLY}"
                holder.itemView.tv_product_sales.text = "销量 ${productInfo.BUYNUM}"

                holder.itemView.tv_product_lable.visibility = if (productInfo.KIND == 1) View.GONE else View.VISIBLE
                holder.itemView.tv_product_lable.text = productInfo.kindStr

                holder.itemView.setOnClickListener {
                    //商品详情
                    ProductDetailActivity.launch(context as Activity, productInfo.PRODUCT_ID, productInfo.FORM_ID)
                }

                holder.itemView.iv_add_to_cart.setOnClickListener {
                    ApiManager.getService().cartAdd(GlobalParam.getUserIdOrJump(), productInfo.PRODUCT_ID, productInfo.FORM_ID, 1)
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
        }

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}