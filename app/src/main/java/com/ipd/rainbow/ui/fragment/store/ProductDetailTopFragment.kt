package com.ipd.rainbow.ui.fragment.store

import android.graphics.Paint
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import android.view.ViewTreeObserver
import com.ipd.rainbow.R
import com.ipd.rainbow.adapter.BannerPagerAdapter
import com.ipd.rainbow.adapter.MediaPictureAdapter
import com.ipd.rainbow.adapter.PackageProductAdapter
import com.ipd.rainbow.bean.BannerBean
import com.ipd.rainbow.bean.ProductDetailBean
import com.ipd.rainbow.imageload.ImageLoader
import com.ipd.rainbow.ui.BaseUIFragment
import com.ipd.rainbow.ui.activity.PictureAndVideoPreviewActivity
import com.ipd.rainbow.ui.activity.PictureLookActivity
import com.ipd.rainbow.ui.activity.store.ProductDetailActivity
import com.ipd.rainbow.utils.IndicatorHelper
import com.ipd.rainbow.utils.StoreType
import com.ipd.rainbow.utils.StringUtils
import kotlinx.android.synthetic.main.fragment_product_detail_top.view.*
import kotlinx.android.synthetic.main.item_product_evaluate.view.*
import kotlinx.android.synthetic.main.layout_option_package.view.*
import kotlinx.android.synthetic.main.layout_store_banner.view.*

class ProductDetailTopFragment : BaseUIFragment() {
    override fun getTitleLayout(): Int = -1

    override fun getContentLayout(): Int = R.layout.fragment_product_detail_top

    private lateinit var mProductInfo: ProductDetailBean
    fun setDetailData(info: ProductDetailBean) {
        mProductInfo = info
    }

    override fun initView(bundle: Bundle?) {
        mContentView.tv_old_price.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
        mContentView.store_banner.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                mContentView.store_banner.viewTreeObserver.removeGlobalOnLayoutListener(this)
                val params = mContentView.store_banner.layoutParams
                params.height = mContentView.store_banner.measuredWidth
                mContentView.store_banner.requestLayout()

            }
        })
    }

    override fun loadData() {
        val bannerList = ArrayList(StringUtils.splitImages(mProductInfo.PICS).map { BannerBean(it) })
        if (!TextUtils.isEmpty(mProductInfo.VIDEO)) {
            bannerList.add(0, BannerBean(mProductInfo.VIDEO, true, mProductInfo.VIDEO_URL))
        }

        mContentView.store_banner.adapter = BannerPagerAdapter(context, bannerList, { pos, info ->
            PictureAndVideoPreviewActivity.launch(mActivity, bannerList, pos)
        })
        IndicatorHelper.newInstance().setRes(R.mipmap.boutique_selected, R.mipmap.boutique_unselected)
                .setIndicator(context, bannerList.size, mContentView.store_banner, mContentView.store_banner_indicator, object : IndicatorHelper.MyPagerChangeListener {
                    override fun onPageSelected(pos: Int) {
                    }

                    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                    }

                    override fun onPageScrollStateChanged(state: Int) {
                    }

                })
        mContentView.store_banner.startAutoScroll()

        when (mProductInfo.KIND) {
            StoreType.PRODUCT_NORMAL -> {
                mContentView.ll_price.visibility = View.VISIBLE
                mContentView.layout_option_package.visibility = View.GONE


                mContentView.ll_price.visibility = View.VISIBLE

                if (mProductInfo.GROUP_LIST != null && mProductInfo.GROUP_LIST.isNotEmpty()) {
                    //可选套餐
                    setOptionPackage()
                }

            }
            StoreType.PRODUCT_GROUP_PRODUCT -> {
                //可选套餐
                setOptionPackage()
            }
            else -> {
                mContentView.ll_price.visibility = View.GONE
                mContentView.layout_option_package.visibility = View.GONE

            }
        }


        mContentView.rl_product_extra.setProductInfo(mProductInfo)
        mContentView.tv_cart_product_name.text = mProductInfo.PROCUCT_NAME
        mContentView.tv_price.text = mProductInfo.CURRENT_PRICE
        mContentView.tv_old_price.text = "￥${mProductInfo.REFER_PRICE}"
        mContentView.tv_old_price.visibility = if (mProductInfo.KIND == StoreType.PRODUCT_NORMAL) View.GONE else View.VISIBLE


        if (mProductInfo.POST_FEE.toFloat() == 0f) {
            mContentView.tv_express_fee.text = "快递：免运费"
        } else {
            mContentView.tv_express_fee.text = "快递：￥${mProductInfo.POST_FEE}"
        }
        mContentView.tv_sales.text = "月销${mProductInfo.BUYNUM}件"
        mContentView.tv_ship_address.text = mProductInfo.ADDRESS

//
//        //优惠券
//        val layoutInflater = LayoutInflater.from(mActivity)
//        if (mProductInfo.COUPON_LIST == null || mProductInfo.COUPON_LIST.isEmpty()) {
//            mContentView.ll_coupon.visibility = View.GONE
//        } else {
//            mContentView.ll_coupon.visibility = View.VISIBLE
//            mProductInfo.COUPON_LIST.forEachIndexed { index, info ->
//                if (index >= 3) return@forEachIndexed
//                val couponLableView = layoutInflater.inflate(R.layout.item_product_coupon_lable, mContentView.ll_coupon_lable, false) as TextView
//                couponLableView.text = "满${info.SATISFY_PRICE}减${info.PRICE}"
//                mContentView.ll_coupon_lable.addView(couponLableView)
//            }
//        }


        //评价
//        mContentView.tv_evaluate_num.text = "商品评价（${mProductInfo.REPLY}）"
//        mContentView.tv_evaluate_percent.text = "${(mProductInfo.GOOD_PERCENT * 100).toInt()}%"
//        if (mProductInfo.ASSESS_DATA == null || mProductInfo.ASSESS_DATA!!.ASSESS_ID == 0) {
//            mContentView.fl_evaluate.visibility = View.GONE
//        } else {
//            mContentView.fl_evaluate.visibility = View.VISIBLE
//            val evaluateInfo = mProductInfo.ASSESS_DATA
//            ImageLoader.loadAvatar(mActivity, evaluateInfo.USER_LOGO, mContentView.civ_avatar)
//            mContentView.tv_username.text = evaluateInfo.USER_NICKNAME
//            mContentView.tv_evaluate_time.text = evaluateInfo.CREATETIME
//            mContentView.tv_answer_content.text = evaluateInfo.CONTENT
//            mContentView.rating_star.setStar(evaluateInfo.TOTAL_SCORE.toFloat())
//
//            if (TextUtils.isEmpty(evaluateInfo.PIC)) {
//                mContentView.image_recycler_view.visibility = View.GONE
//            } else {
//                mContentView.image_recycler_view.visibility = View.VISIBLE
//                mContentView.image_recycler_view.layoutManager = GridLayoutManager(mActivity, 4)
//                mContentView.image_recycler_view.adapter = MediaPictureAdapter(mActivity, StringUtils.splitImages(evaluateInfo.PIC)) { list, pos ->
//                    PictureLookActivity.launch(mActivity, ArrayList(list), pos, PictureLookActivity.URL)
//                }
//            }
//
//            mContentView.rl_all_evaluate.setOnClickListener {
//                //全部评价
//                if (mActivity is ProductDetailActivity) {
//                    (mActivity as ProductDetailActivity).switchToEvaluate()
//                }
//            }
//
//        }


    }

    private fun setOptionPackage() {
        mContentView.layout_option_package.visibility = View.VISIBLE
        mContentView.package_recycler_view.layoutManager = LinearLayoutManager(mActivity, RecyclerView.HORIZONTAL, false)
        mContentView.package_recycler_view.adapter = PackageProductAdapter(mActivity, mProductInfo.GROUP_LIST) {
            ProductDetailActivity.launch(mActivity, it.PRODUCT_ID, it.FORM_ID)
        }
        mContentView.cb_option_package.setOnCheckedChangeListener { buttonView, isChecked ->
            mProductInfo.isGroup = isChecked
        }
    }

    override fun initListener() {
//        mContentView.ll_coupon.setOnClickListener {
//            //领券
//            ApiManager.getService().storeProductCoupon(GlobalParam.getUserIdOrJump(), mProductInfo?.PRODUCT_ID, mProductInfo?.FORM_ID)
//                    .compose(RxScheduler.applyScheduler())
//                    .subscribe(object : Response<BaseResult<List<ExchangeBean>>>(mActivity, true) {
//                        override fun _onNext(result: BaseResult<List<ExchangeBean>>) {
//                            if (result.code == 0) {
//                                val couponDialog = ProductCouponDialog(mActivity)
//                                couponDialog.setData(result.data)
//                                couponDialog.show()
//                            } else {
//                                toastShow(result.msg)
//                            }
//                        }
//                    })
//        }

    }

    override fun onDestroy() {
        super.onDestroy()
        mContentView.store_banner.stopAutoScroll()
    }

}