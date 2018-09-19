package com.ipd.taxiu.ui.fragment.store.flashsale

import android.support.v7.widget.LinearLayoutManager
import com.ipd.taxiu.adapter.FlashSaleAdapter
import com.ipd.taxiu.bean.BaseResult
import com.ipd.taxiu.bean.FlashSaleProductBean
import com.ipd.taxiu.platform.global.Constant
import com.ipd.taxiu.platform.global.GlobalParam
import com.ipd.taxiu.platform.http.ApiManager
import com.ipd.taxiu.platform.http.Response
import com.ipd.taxiu.platform.http.RxScheduler
import com.ipd.taxiu.ui.ListFragment
import com.ipd.taxiu.ui.activity.store.ProductDetailActivity
import com.ipd.taxiu.utils.StoreType
import rx.Observable

class FlashSaleListFragment : ListFragment<BaseResult<List<FlashSaleProductBean>>, FlashSaleProductBean>() {

    companion object {
        fun newInstance(): FlashSaleListFragment {
            val fragment = FlashSaleListFragment()
            return fragment
        }
    }


    private var mTodayFlashSaleProduct: FlashSaleProductBean? = null
    override fun getListData(isRefresh: Boolean) {
        if (isRefresh) {
            checkNeedShowProgress()
            ApiManager.getService().storeTodayProductFlashSale(GlobalParam.getUserIdOrJump())
                    .compose(RxScheduler.applyScheduler())
                    .subscribe(object : Response<BaseResult<FlashSaleProductBean>>() {
                        override fun _onNext(result: BaseResult<FlashSaleProductBean>) {
                            if (result.code == 0) {
                                mTodayFlashSaleProduct = result.data
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
            mAdapter = FlashSaleAdapter(mActivity, mTodayFlashSaleProduct, data, { itemClickType, info ->
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
            mAdapter?.mType = mType
            recycler_view.layoutManager = LinearLayoutManager(mActivity)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.mType = mType
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: BaseResult<List<FlashSaleProductBean>>) {
        data?.addAll(result?.data)
    }


}