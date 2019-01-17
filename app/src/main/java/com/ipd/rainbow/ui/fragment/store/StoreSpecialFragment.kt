package com.ipd.rainbow.ui.fragment.store

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.ipd.jumpbox.jumpboxlibrary.utils.DensityUtil
import com.ipd.rainbow.MainActivity
import com.ipd.rainbow.R
import com.ipd.rainbow.adapter.StoreSpecialAdapter
import com.ipd.rainbow.bean.*
import com.ipd.rainbow.platform.global.Constant
import com.ipd.rainbow.platform.global.GlobalParam
import com.ipd.rainbow.platform.http.ApiManager
import com.ipd.rainbow.platform.http.Response
import com.ipd.rainbow.platform.http.RxScheduler
import com.ipd.rainbow.ui.ListFragment
import com.ipd.rainbow.utils.ProductScreenView
import com.ipd.rainbow.widget.StoreSpecialRecyclerView
import kotlinx.android.synthetic.main.fragment_store_special.view.*
import rx.Observable

class StoreSpecialFragment : ListFragment<BaseResult<List<ProductBean>>, Any>(), ProductScreenView {

    companion object {
        fun newInstance(areaId: Int, areaTitle: String): StoreSpecialFragment {
            val fragment = StoreSpecialFragment()
            val bundle = Bundle()
            bundle.putInt("areaId", areaId)
            bundle.putString("areaTitle", areaTitle)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getContentLayout(): Int = R.layout.fragment_store_special


    private val mAreaId: Int by lazy { arguments.getInt("areaId", 0) }
    private val mAreaTitle: String by lazy { arguments.getString("areaTitle", "") }

    override fun initListener() {
        super.initListener()
        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (recyclerView == null || recyclerView.childCount <= 0) return
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                mContentView.iv_scroll_top.visibility = if (linearLayoutManager.findFirstVisibleItemPosition() > 0) View.VISIBLE else View.GONE
            }
        })
        mContentView.iv_scroll_top.setOnClickListener {
            recycler_view?.smoothScrollToPosition(0)
        }
        mContentView.iv_store_home.setOnClickListener {
            MainActivity.launch(mActivity)
        }

        mContentView.swipe_target.setSuspensionListener(object : StoreSpecialRecyclerView.SuspensionListener {
            override fun onChange(isShow: Boolean) {
            }
        })
    }

    private lateinit var mStoreAreaInfo: StoreSpecialBean
    override fun getListData(isRefresh: Boolean) {
        if (isRefresh) {
            checkNeedShowProgress()
            ApiManager.getService().storeAreaIndex(GlobalParam.getUserIdOrJump(), mAreaId)
                    .compose(RxScheduler.applyScheduler())
                    .subscribe(object : Response<BaseResult<StoreAreaIndexResultBean>>() {
                        override fun _onNext(result: BaseResult<StoreAreaIndexResultBean>) {
                            if (result.code == 0) {
                                val data = result.data
                                mStoreAreaInfo = StoreSpecialBean()
                                val storeHeaderBean = StoreSpecialHeaderBean()
                                //banner
                                storeHeaderBean.bannerList = data.BANNER_LIST
                                //分类
                                storeHeaderBean.menuList = data.AREA_TYPE.AREA_LIST
                                //小banner
                                storeHeaderBean.smallBannerList = data.BANNER_LIST2
                                mStoreAreaInfo.headerInfo = storeHeaderBean
                                //推荐视频
                                mStoreAreaInfo.recommendVideo = StoreIndexVideoBean(data.VIDEO_LIST)

                                getParentListData(isRefresh)
                            } else {
                                showError(result.msg)
                            }

                        }

                        override fun onError(e: Throwable?) {
                            showError("连接服务器失败")
                        }

                    })
        } else {
            super.getListData(isRefresh)
        }
    }

    private fun getParentListData(isRefresh: Boolean) {
        super.getListData(isRefresh)
    }


    override fun loadListData(): Observable<BaseResult<List<ProductBean>>> {
        val compositeValue = getCompositeValue()
        val saleValue = getSaleValue()
        val priceValue = getPriceValue()
        val maxPrice = getMaxPrice()
        val minPrice = getMinPrice()
        val brandValue = getBrandValue()
        val applyValue = getApplyValue()
        val sizeValue = getSizeValue()
        val petTypeValue = getPetTypeValue()
        val netContentValue = getNetContentValue()
        val tasteValue = getTasteValue()
        val countryValue = getCountryValue()
        val thingTypeValue = getThingTypeValue()

        return ApiManager.getService().storeProductList(GlobalParam.getUserIdOrJump(), Constant.PAGE_SIZE, page, brandValue,
                compositeValue, "", maxPrice, minPrice, priceValue, saleValue,
                applyValue, sizeValue, petTypeValue, netContentValue, tasteValue, countryValue, thingTypeValue, 0, mAreaId)
    }

    override fun isNoMoreData(result: BaseResult<List<ProductBean>>): Int {
        if (page > INIT_PAGE && (result.data == null || result.data.isEmpty())) {
            return NO_MORE_DATA
        }
        return NORMAL
    }

    private var mAdapter: StoreSpecialAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = StoreSpecialAdapter(mActivity, mAreaTitle, data, {
                if (recycler_view.layoutManager is LinearLayoutManager) {
                    (recycler_view.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(it, -DensityUtil.dip2px(mActivity, 12f))
                }
            })
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: BaseResult<List<ProductBean>>) {
        if (isRefresh) {
            data?.add(mStoreAreaInfo.headerInfo)
            data?.add(mStoreAreaInfo.recommendVideo)
            data?.add(StoreProductScreenBean())
        }
        data?.addAll(result?.data ?: arrayListOf())
    }


    private var mMinPrice = 0f
    private var mMaxPrice = 0f
    //保存已勾选的筛选条件
    private val mScreenMap: HashMap<String, ProductExpertScreenBean.ScreenInfo> = hashMapOf()

    override fun getCompositeValue(): Int = 0
    override fun getSaleValue(): Int = 0
    override fun getPriceValue(): Int = 0
    override fun getMinPrice(): Float = mMinPrice
    override fun getMaxPrice(): Float = mMaxPrice
    override fun getBrandValue(): String = (mScreenMap["品牌"]?.NAME) ?: ""
    override fun getApplyValue(): String = (mScreenMap["适用阶段"]?.NAME) ?: ""
    override fun getSizeValue(): String = (mScreenMap["宠物体型"]?.NAME) ?: ""
    override fun getPetTypeValue(): String = (mScreenMap["宠物品种"]?.NAME) ?: ""
    override fun getNetContentValue(): String = (mScreenMap["净含量"]?.NAME) ?: ""
    override fun getTasteValue(): String = (mScreenMap["口味"]?.NAME) ?: ""
    override fun getCountryValue(): String = (mScreenMap["国家"]?.NAME) ?: ""
    //    override fun getThingTypeValue(): String = mScreenResult?.type ?: ""
    override fun getThingTypeValue(): String = ""


}