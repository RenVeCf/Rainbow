package com.ipd.rainbow.ui.fragment.store

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import com.ipd.jumpbox.jumpboxlibrary.utils.LogUtils
import com.ipd.rainbow.adapter.NewProductAdapter
import com.ipd.rainbow.bean.BaseResult
import com.ipd.rainbow.bean.ProductBean
import com.ipd.rainbow.platform.global.Constant
import com.ipd.rainbow.platform.global.GlobalParam
import com.ipd.rainbow.platform.http.ApiManager
import com.ipd.rainbow.ui.ListFragment
import com.ipd.rainbow.ui.activity.store.ProductDetailActivity
import com.ipd.rainbow.ui.activity.store.StoreSalesActivity
import rx.Observable

class SalesProductListFragment : ListFragment<BaseResult<List<ProductBean>>, ProductBean>() {

    companion object {
        fun newInstance(type: Int): SalesProductListFragment {
            val fragment = SalesProductListFragment()
            val bundle = Bundle()
            bundle.putInt("type", type)
            fragment.arguments = bundle
            return fragment
        }
    }


    private val mType by lazy { arguments.getInt("type") }
    override fun loadListData(): Observable<BaseResult<List<ProductBean>>> {
        return ApiManager.getService().storeProductSales(GlobalParam.getUserIdOrJump(), mType, Constant.PAGE_SIZE, page)
    }

    override fun isNoMoreData(result: BaseResult<List<ProductBean>>): Int {
        if (page == INIT_PAGE && (result.data == null || result.data.isEmpty())) {
            return EMPTY_DATA
        } else if (result.data == null || result.data.isEmpty()) {
            return NO_MORE_DATA
        }
        return NORMAL
    }

    private var mAdapter: NewProductAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = NewProductAdapter(mActivity, data) {
                //商品详情
                ProductDetailActivity.launch(mActivity, it.PRODUCT_ID, it.FORM_ID)
            }
            if (activity !is StoreSalesActivity) {
                throw IllegalStateException("parent activity error!")
            }

            mAdapter?.mType = (activity as StoreSalesActivity).mCurShowType
            if (mAdapter?.mType == NewProductAdapter.ItemType.LIST) {
                recycler_view.layoutManager = LinearLayoutManager(mActivity)
            } else {
                recycler_view.layoutManager = GridLayoutManager(mActivity, 2)
            }

            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    fun setShowType(showType: Int) {
        try {
            if (showType == NewProductAdapter.ItemType.LIST) {
                recycler_view.layoutManager = LinearLayoutManager(mActivity)
            } else {
                recycler_view.layoutManager = GridLayoutManager(mActivity, 2)
            }
            mAdapter?.mType = showType
            mAdapter?.notifyDataSetChanged()
        } catch (e: UninitializedPropertyAccessException) {
            LogUtils.e("tag", "UnInit...")
        }
    }

    override fun addData(isRefresh: Boolean, result: BaseResult<List<ProductBean>>) {
        data?.addAll(result?.data ?: arrayListOf())
    }


}