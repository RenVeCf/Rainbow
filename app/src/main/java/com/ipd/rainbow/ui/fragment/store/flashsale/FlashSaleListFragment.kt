package com.ipd.rainbow.ui.fragment.store.flashsale

import android.support.v7.widget.LinearLayoutManager
import com.ipd.rainbow.adapter.FlashSaleAdapter
import com.ipd.rainbow.bean.BaseResult
import com.ipd.rainbow.bean.FlashSaleHeaerInfo
import com.ipd.rainbow.bean.FlashSaleProductBean
import com.ipd.rainbow.bean.FlashSaleTimeBean
import com.ipd.rainbow.platform.global.Constant
import com.ipd.rainbow.platform.global.GlobalParam
import com.ipd.rainbow.platform.http.ApiManager
import com.ipd.rainbow.platform.http.Response
import com.ipd.rainbow.platform.http.RxScheduler
import com.ipd.rainbow.ui.ListFragment
import com.ipd.rainbow.ui.activity.store.ProductDetailActivity
import com.ipd.rainbow.utils.StoreType
import rx.Observable

class FlashSaleListFragment : ListFragment<BaseResult<List<FlashSaleProductBean>>, FlashSaleProductBean>() {

    companion object {
        fun newInstance(): FlashSaleListFragment {
            val fragment = FlashSaleListFragment()
            return fragment
        }
    }


    private var mFlashSaleHeaerInfo: FlashSaleHeaerInfo? = null
    override fun getListData(isRefresh: Boolean) {
        if (isRefresh) {
            checkNeedShowProgress()
            Observable.zip(
                    getTodayFlashSale(),
                    getFlashSaleTime()
            ) { t1, t2 ->
                val flashSaleHeaerInfo = FlashSaleHeaerInfo()
                if ((t1.code == 0 || t1.code == 10000) && t2.code == 0) {
                    flashSaleHeaerInfo.todayProduct = t1.data
                    flashSaleHeaerInfo.timeList = t2.data
                }
                flashSaleHeaerInfo
            }
                    .compose(RxScheduler.applyScheduler())
                    .subscribe(object : Response<FlashSaleHeaerInfo>() {
                        override fun _onNext(result: FlashSaleHeaerInfo?) {
                            mFlashSaleHeaerInfo = result
                            getParentListData(isRefresh)
                        }

                        override fun onError(e: Throwable?) {
                            showError()
                        }
                    })

//                    ApiManager.getService().storeTodayProductFlashSale(GlobalParam.getUserIdOrJump())
//                    .compose(RxScheduler.applyScheduler())
//                    .subscribe(object : Response<BaseResult<FlashSaleProductBean>>() {
//                        override fun _onNext(result: BaseResult<FlashSaleProductBean>) {
//                            if (result.code == 0 || result.code == 10000) {
//                                mTodayFlashSaleProduct = result.data
//                                getParentListData(isRefresh)
//                            } else {
//                                showError(result.msg)
//                            }
//                        }
//
//                        override fun onError(e: Throwable?) {
//                            showError("连接服务器失败")
//                        }
//                    })
        } else {
            super.getListData(isRefresh)
        }
    }

    private fun getTodayFlashSale(): Observable<BaseResult<FlashSaleProductBean>> {
        return ApiManager.getService().storeTodayProductFlashSale(GlobalParam.getUserIdOrJump())
    }

    private fun getFlashSaleTime(): Observable<BaseResult<List<FlashSaleTimeBean>>> {
        return ApiManager.getService().storeFlashSaleTime(GlobalParam.getUserIdOrJump())
    }

    private fun getParentListData(isRefresh: Boolean) {
        super.getListData(isRefresh)
    }


    private var mType = StoreType.FLASH_SALE_TODAY
    override fun loadListData(): Observable<BaseResult<List<FlashSaleProductBean>>> {
        return ApiManager.getService().storeProductFlashSale(GlobalParam.getUserIdOrJump(), Constant.PAGE_SIZE, page, mType)
    }

    override fun isNoMoreData(result: BaseResult<List<FlashSaleProductBean>>): Int {
        if (page > INIT_PAGE && (result.data == null || result.data.isEmpty())) {
            return NO_MORE_DATA
        }
        return NORMAL
    }

    private var mAdapter: FlashSaleAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = FlashSaleAdapter(mActivity, data, { itemClickType, info ->
                when (itemClickType) {
                    FlashSaleAdapter.ItemClickType.ITEM,
                    FlashSaleAdapter.ItemClickType.PURCHASE -> {
                        //商品详情
                        ProductDetailActivity.launch(mActivity, info.PRODUCT_ID, info.FORM_ID)
                    }
                    FlashSaleAdapter.ItemClickType.REMIND_ME,
                    FlashSaleAdapter.ItemClickType.CANCEL_REMIND -> {
                        //提醒
                        ApiManager.getService().storeProductFlashSaleRemind(GlobalParam.getUserIdOrJump(), info.PRODUCT_ID, info.FORM_ID)
                                .compose(RxScheduler.applyScheduler())
                                .subscribe(object : Response<BaseResult<FlashSaleProductBean>>(mActivity, true) {
                                    override fun _onNext(result: BaseResult<FlashSaleProductBean>) {
                                        if (result.code == 0) {
                                            info.IS_REMIND = if (info.IS_REMIND == 0) 1 else 0
                                            mAdapter?.notifyDataSetChanged()
                                        } else {
                                            toastShow(result.msg)
                                        }
                                    }
                                })
                    }
                }
            }, {
                //tab切换
                mType = it
                isCreate = true
                onRefresh()
            })
            mAdapter?.setHeaderInfo(mFlashSaleHeaerInfo)
            mAdapter?.mType = mType
            recycler_view.layoutManager = LinearLayoutManager(mActivity)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.setHeaderInfo(mFlashSaleHeaerInfo)
            mAdapter?.mType = mType
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: BaseResult<List<FlashSaleProductBean>>) {
        data?.addAll(result?.data ?: arrayListOf())
    }


}