package com.ipd.rainbow.ui.fragment

import android.os.AsyncTask
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.ipd.rainbow.R
import com.ipd.rainbow.adapter.StoreAdapter
import com.ipd.rainbow.bean.*
import com.ipd.rainbow.event.LoginSuccessEvent
import com.ipd.rainbow.platform.global.Constant
import com.ipd.rainbow.platform.global.GlobalParam
import com.ipd.rainbow.platform.http.ApiManager
import com.ipd.rainbow.platform.http.Response
import com.ipd.rainbow.platform.http.RxScheduler
import com.ipd.rainbow.ui.ListFragment
import com.ipd.rainbow.ui.activity.store.StoreSearchActivity
import com.ipd.rainbow.widget.StoreGiftGetSuccessPopup
import com.ipd.rainbow.widget.StoreGiftPopup
import kotlinx.android.synthetic.main.fragment_store.view.*
import kotlinx.android.synthetic.main.store_toolbar.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import rx.Observable

class StoreFragment : ListFragment<BaseResult<List<ProductBean>>, Any>() {
    override fun getTitleLayout(): Int = R.layout.store_toolbar

    override fun getContentLayout(): Int = R.layout.fragment_store

    private lateinit var mStoreIndexInfo: StoreIndexBean


    override fun onViewAttach() {
        super.onViewAttach()
        EventBus.getDefault().register(this)
    }

    override fun onViewDetach() {
        super.onViewDetach()
        EventBus.getDefault().unregister(this)
    }


//    override fun loadDataWhenVisible() {
//        super.loadDataWhenVisible()
//        //商城礼包
//        ApiManager.getService().storeGift(GlobalParam.getUserIdOrJump())
//                .compose(RxScheduler.applyScheduler())
//                .subscribe(object : Response<BaseResult<StoreIndexResultBean>>() {
//                    override fun _onNext(result: BaseResult<StoreIndexResultBean>) {
//                        if (result.code == 0) {
//                            StoreGiftPopup(mActivity) {
//                                //领取礼包
//                                takeItGift(it)
//                            }.showPopupWindow()
//                        }
//                    }
//                })
//    }

    private fun takeItGift(popup: StoreGiftPopup) {
        ApiManager.getService().storeGiftTakeIt(GlobalParam.getUserIdOrJump())
                .compose(RxScheduler.applyScheduler())
                .subscribe(object : Response<BaseResult<List<ExchangeHisBean>>>(mActivity, true) {
                    override fun _onNext(result: BaseResult<List<ExchangeHisBean>>) {
                        if (result.code == 0) {
                            popup.dismiss()
                            StoreGiftGetSuccessPopup(mActivity, result.data).showPopupWindow()
                        } else {
                            toastShow(result.msg)
                        }
                    }
                })
    }

    override fun getListData(isRefresh: Boolean) {
        if (isRefresh) {
            checkNeedShowProgress()
            ApiManager.getService().storeIndex(GlobalParam.getRequestUserId())
                    .compose(RxScheduler.applyScheduler())
                    .subscribe(object : Response<BaseResult<StoreIndexResultBean>>() {
                        override fun _onNext(result: BaseResult<StoreIndexResultBean>) {
                            if (result.code == 0) {
                                val data = result.data
                                mStoreIndexInfo = StoreIndexBean()
                                mStoreIndexInfo.headerInfo = StoreIndexHeaderBean()
                                //banner
                                mStoreIndexInfo.headerInfo.bannerList = data.BANNER_LIST
                                //菜单

//                                val storeMenu = listOf(
//                                        StoreMenuBean(0, R.mipmap.menu_rainbow_vip, "彩虹会员"),
//                                        StoreMenuBean(0, R.mipmap.menu_group_purchase, "超值团购"),
//                                        StoreMenuBean(0, R.mipmap.menu_bag, "时尚包包"),
//                                        StoreMenuBean(0, R.mipmap.menu_headgear, "精品首饰"),
//                                        StoreMenuBean(0, R.mipmap.menu_food, "休闲食品"),
//                                        StoreMenuBean(0, R.mipmap.menu_kitchen, "品质厨房"),
//                                        StoreMenuBean(0, R.mipmap.menu_bed, "床上用品"),
//                                        StoreMenuBean(0, R.mipmap.menu_life_use, "生活日用")
//                                )
                                mStoreIndexInfo.headerInfo.categoryList = data.TYPE_LIST
                                mStoreIndexInfo.headerInfo.categoryPicList = data.PIC_LIST
                                mStoreIndexInfo.headerInfo.todayNew = data.NEW_PRODUCT_LIST
                                //区域分区
                                mStoreIndexInfo.headerInfo.todaySale = data.RUSH_PRODUCT_LIST
                                //专区
                                mStoreIndexInfo.specialList = data.TYPE_PRODUCT_LIST

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
            recycler_view.smoothScrollToPosition(0)
        }
        mHeaderView.ll_search.setOnClickListener {
            StoreSearchActivity.launch(mActivity)
        }

        mContentView.iv_scroll_kefu.setOnClickListener {
            //客服
//            val chatParams = ChatParamsBody()
//            chatParams.itemparams.appgoodsinfo_type = CoreData.SHOW_GOODS_BY_ID
//            chatParams.itemparams.clientgoodsinfo_type = CoreData.SHOW_GOODS_BY_ID
//            chatParams.itemparams.clicktoshow_type = CoreData.CLICK_TO_APP_COMPONENT
//            Ntalker.getBaseInstance().startChat(mActivity, Constant.XIAONENG_KEFU_ID, "客服接待", chatParams)
        }

    }

    override fun loadListData(): Observable<BaseResult<List<ProductBean>>> {
        return ApiManager.getService().storeGuessLike(Constant.PAGE_SIZE, GlobalParam.getRequestUserId(), page)
    }

    override fun isNoMoreData(result: BaseResult<List<ProductBean>>): Int {
        if (page > INIT_PAGE && (result.data == null || result.data.isEmpty())) {
            return NO_MORE_DATA
        }
        return NORMAL
    }

    private var mAdapter: StoreAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = StoreAdapter(mActivity, data) { type ->
                isCreate = true
                onRefresh()
            }
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: BaseResult<List<ProductBean>>) {
        if (isRefresh) {
            data?.add(mStoreIndexInfo.headerInfo)
            data?.addAll(mStoreIndexInfo.specialList)
        }
        data?.addAll(result?.data ?: arrayListOf())
    }

    @Subscribe
    fun onMainEvent(event: LoginSuccessEvent) {
        onRefresh(true)
    }


}