package com.ipd.taxiu.ui.fragment.classroom

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import com.ipd.taxiu.R
import com.ipd.taxiu.adapter.ProductAdapter
import com.ipd.taxiu.bean.BaseResult
import com.ipd.taxiu.bean.ProductBean
import com.ipd.taxiu.event.UpdateCollectProductEvent
import com.ipd.taxiu.platform.global.Constant
import com.ipd.taxiu.platform.global.GlobalParam
import com.ipd.taxiu.platform.http.ApiManager
import com.ipd.taxiu.ui.ListFragment
import com.ipd.taxiu.ui.activity.store.ProductDetailActivity
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import rx.Observable

class CollectStoreFragment : ListFragment<BaseResult<List<ProductBean>>, ProductBean>() {
    companion object {
        fun newInstance(): CollectStoreFragment {
            return CollectStoreFragment()
        }
    }

    override fun onViewAttach() {
        super.onViewAttach()
        EventBus.getDefault().register(this)
    }

    override fun onViewDetach() {
        super.onViewDetach()
        EventBus.getDefault().unregister(this)
    }

    override fun needLazyLoad() = true


    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        progress_layout.setEmptyViewRes(R.layout.empty_base)
    }

    override fun loadListData(): Observable<BaseResult<List<ProductBean>>> {
        return ApiManager.getService().productCollect(GlobalParam.getUserIdOrJump(), Constant.PAGE_SIZE, page)
    }

    override fun isNoMoreData(result: BaseResult<List<ProductBean>>): Int {
        if (page == INIT_PAGE && (result.data == null || result.data.isEmpty())) {
            return EMPTY_DATA
        } else if (result.data == null || result.data.isEmpty()) {
            return NO_MORE_DATA
        }
        return NORMAL
    }

    private var mAdapter: ProductAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = ProductAdapter(mActivity, data, {
                //itemClick
                ProductDetailActivity.launch(mActivity, it.PRODUCT_ID, it.FORM_ID)
            })
            mAdapter?.switchShowType()
            recycler_view.layoutManager = GridLayoutManager(mActivity, 2)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: BaseResult<List<ProductBean>>) {
        data?.addAll(result?.data ?: arrayListOf())
    }

    @Subscribe
    fun onMainEvent(event: UpdateCollectProductEvent) {
        if (isFirstLoad()) return
        onRefresh(true)
    }

}