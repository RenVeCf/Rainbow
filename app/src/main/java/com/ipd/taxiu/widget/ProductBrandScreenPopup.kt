package com.ipd.taxiu.widget

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.ipd.taxiu.R
import com.ipd.taxiu.adapter.ProductScreenRecommendBrandAdapter
import com.ipd.taxiu.adapter.ProductScreenSortBrandAdapter
import com.ipd.taxiu.bean.BaseResult
import com.ipd.taxiu.bean.ProductBrandBean
import com.ipd.taxiu.bean.ProductExpertScreenBean
import com.ipd.taxiu.platform.global.GlobalParam
import com.ipd.taxiu.platform.http.ApiManager
import com.ipd.taxiu.platform.http.Response
import com.ipd.taxiu.platform.http.RxScheduler
import com.ipd.taxiu.utils.StoreType
import kotlinx.android.synthetic.main.fragment_product_brand_list.view.*
import kotlinx.android.synthetic.main.layout_product_recommend_brand.view.*
import razerdp.basepopup.BasePopupWindow

class ProductBrandScreenPopup : BasePopupWindow {

    private var recommendView: ViewGroup? = null
    private var sortView: ViewGroup? = null

    constructor(context: Context?) : super(context) {
        val viewPager = findViewById(R.id.view_pager) as ViewPager
        viewPager.adapter = BrandPagerAdapter()

        findViewById(R.id.iv_back).setOnClickListener {
            dismiss()
        }

        findViewById(R.id.tv_brand_reset).setOnClickListener {
            //重置
            mRecommendAdapter?.reset()
            mSortAdapter?.reset()
        }
        findViewById(R.id.tv_brand_confirm).setOnClickListener {
            //确定
            var brandInfo: ProductExpertScreenBean.ScreenInfo? = null
            val currentItem = viewPager.currentItem
            when (currentItem) {
                0 -> {
                    val curCheckedPos = mRecommendAdapter?.getCurCheckedPos() ?: -1
                    if (curCheckedPos > -1 && curCheckedPos < mRecommendBrandList?.size ?: 0) {
                        val productBrandBean = mRecommendBrandList!![curCheckedPos]
                        brandInfo = ProductExpertScreenBean.ScreenInfo(productBrandBean.BRAND_ID, productBrandBean.BRAND_NAME)
                    }
                }
                1 -> {
                    brandInfo = mSortAdapter?.getCheckedScreenInfo()
                }
            }
            dismiss()
            mOnConfirmCallback?.invoke(brandInfo)
        }



        recommendView = findViewById(R.id.ll_recommend) as ViewGroup
        sortView = findViewById(R.id.ll_sort) as ViewGroup

        recommendView?.setOnClickListener { viewPager.currentItem = 0 }
        sortView?.setOnClickListener { viewPager.currentItem = 1 }

        setTabsResByPos(0)
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                setTabsResByPos(position)
            }

            override fun onPageSelected(position: Int) {
            }
        })
    }


    private var mOnConfirmCallback: ((info: ProductExpertScreenBean.ScreenInfo?) -> Unit)? = null
    fun setOnConfirmCallback(onConfirmCallback: (info: ProductExpertScreenBean.ScreenInfo?) -> Unit): ProductBrandScreenPopup {
        mOnConfirmCallback = onConfirmCallback
        return this
    }

    private fun setTabsResByPos(position: Int) {
        recommendView?.getChildAt(0)?.isSelected = position == 0
        recommendView?.getChildAt(1)?.visibility = if (position == 0) View.VISIBLE else View.INVISIBLE
        sortView?.getChildAt(0)?.isSelected = position == 1
        sortView?.getChildAt(1)?.visibility = if (position == 1) View.VISIBLE else View.INVISIBLE
    }

    override fun onCreatePopupView(): View {
        return createPopupById(R.layout.layout_product_brand_screen)
    }

    override fun getClickToDismissView(): View? {
        return this.findViewById(R.id.view)
    }

    override fun initShowAnimation(): Animation? {
        return AnimationUtils.loadAnimation(context, R.anim.product_screen_brand_enter)
    }

    override fun initExitAnimation(): Animation {
        return AnimationUtils.loadAnimation(context, R.anim.product_screen_brand_exit)
    }

    override fun initAnimaView(): View? = findViewById(R.id.cl_content)


    private var mRecommendBrandList: List<ProductBrandBean>? = null
    private var mSortBrandList: List<ProductBrandBean>? = null
    private val mInflater by lazy { LayoutInflater.from(context) }
    private var mRecommendAdapter: ProductScreenRecommendBrandAdapter? = null
    private var mSortAdapter: ProductScreenSortBrandAdapter? = null

    inner class BrandPagerAdapter : PagerAdapter() {
        override fun instantiateItem(container: ViewGroup?, position: Int): Any {
            return when (position) {
                0 -> {
                    val recommendBrandView = mInflater.inflate(R.layout.layout_product_recommend_brand, container, false) as ProgressLayout
                    setRecommendBrandData(recommendBrandView)
                    container?.addView(recommendBrandView)
                    recommendBrandView
                }
                else -> {
                    val sortBrandView = mInflater.inflate(R.layout.layout_product_sort_brand, container, false) as ProgressLayout
                    setSortBrandData(sortBrandView)
                    container?.addView(sortBrandView)
                    sortBrandView
                }
            }
        }

        /**
         * 字母排序
         */
        private fun setSortBrandData(sortBrandView: ProgressLayout) {
            if (mSortBrandList == null) {
                sortBrandView.showLoading()
                ApiManager.getService().storeBrandList(GlobalParam.getUserIdOrJump(), StoreType.PRODUCT_BRAND_RECOMMEND)
                        .compose(RxScheduler.applyScheduler())
                        .subscribe(object : Response<BaseResult<List<ProductBrandBean>>>() {
                            override fun _onNext(result: BaseResult<List<ProductBrandBean>>) {
                                if (result.code == 0) {
                                    sortBrandView.showContent()
                                    mSortBrandList = result.data
                                    setSortAdapter(sortBrandView)

                                } else {
                                    sortBrandView.showError(result.msg, {
                                        setSortBrandData(sortBrandView)
                                    })
                                }
                            }

                            override fun onError(e: Throwable?) {
                                sortBrandView.showError("连接服务器失败", {
                                    setSortBrandData(sortBrandView)
                                })
                            }
                        })
            } else {
                setSortAdapter(sortBrandView)
            }
        }

        private fun setSortAdapter(sortBrandView: ProgressLayout) {
            val layoutManager = LinearLayoutManager(context)
            sortBrandView.swipe_target.setLayoutManager(layoutManager)
            mSortAdapter = ProductScreenSortBrandAdapter(context)
            sortBrandView.swipe_target.setAdapter(mSortAdapter)
            mSortAdapter?.setDatas(mSortBrandList)
        }


        /**
         * 推荐品牌
         */
        private fun setRecommendBrandData(recommendBrandView: ProgressLayout) {
            if (mRecommendBrandList == null) {
                recommendBrandView.showLoading()
                ApiManager.getService().storeBrandList(GlobalParam.getUserIdOrJump(), StoreType.PRODUCT_BRAND_RECOMMEND)
                        .compose(RxScheduler.applyScheduler())
                        .subscribe(object : Response<BaseResult<List<ProductBrandBean>>>() {
                            override fun _onNext(result: BaseResult<List<ProductBrandBean>>) {
                                if (result.code == 0) {
                                    recommendBrandView.showContent()
                                    mRecommendBrandList = result.data
                                    setRecommendAdapter(recommendBrandView)

                                } else {
                                    recommendBrandView.showError(result.msg, {
                                        setRecommendBrandData(recommendBrandView)
                                    })
                                }
                            }

                            override fun onError(e: Throwable?) {
                                recommendBrandView.showError("连接服务器失败", {
                                    setRecommendBrandData(recommendBrandView)
                                })
                            }
                        })
            } else {
                setRecommendAdapter(recommendBrandView)
            }

        }

        private fun setRecommendAdapter(recommendBrandView: ProgressLayout) {
            mRecommendAdapter = ProductScreenRecommendBrandAdapter(context, mRecommendBrandList)
            recommendBrandView.recommend_brand_recycler_view.itemAnimator.changeDuration = 0
            recommendBrandView.recommend_brand_recycler_view.adapter = mRecommendAdapter
        }

        override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
            container?.removeView(`object` as View?)
        }

        override fun isViewFromObject(view: View?, target: Any?): Boolean {
            return view == target
        }

        override fun getCount(): Int = 2

    }

}