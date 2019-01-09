package com.ipd.rainbow.ui.activity.store

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.ipd.rainbow.R
import com.ipd.rainbow.adapter.ProductBrandAdapter
import com.ipd.rainbow.bean.BaseResult
import com.ipd.rainbow.bean.ProductBrandBean
import com.ipd.rainbow.platform.global.GlobalParam
import com.ipd.rainbow.platform.http.ApiManager
import com.ipd.rainbow.ui.ListFragment
import com.ipd.rainbow.utils.StoreType
import kotlinx.android.synthetic.main.fragment_product_brand_list.view.*
import rx.Observable


class ProductBrandFragment : ListFragment<BaseResult<List<ProductBrandBean>>, ProductBrandBean>() {

    companion object {
        fun newInstance(): ProductBrandFragment {
            val fragment = ProductBrandFragment()
            return fragment
        }
    }

    override fun getContentLayout(): Int = R.layout.fragment_product_brand_list

    override fun initView(bundle: Bundle?) {
        swipe_load_layout = mRootView?.findViewById(R.id.swipe_load_layout)!!
        swipe_load_layout.isRefreshEnabled = false
        setLoadMoreEnable(false)
        mContentView.swipe_target.setOverlayStyle_Center()
    }

    override fun loadListData(): Observable<BaseResult<List<ProductBrandBean>>> {
        return ApiManager.getService().storeBrandList(GlobalParam.getUserId(),StoreType.PRODUCT_BRAND_ALL)
    }

    override fun isNoMoreData(result: BaseResult<List<ProductBrandBean>>): Int {
        if (page == INIT_PAGE && (result.data == null || result.data.isEmpty())) {
            return EMPTY_DATA
        } else if (result.data == null || result.data.isEmpty()) {
            return NO_MORE_DATA
        }
        return NORMAL
    }

    private var mAdapter: ProductBrandAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = ProductBrandAdapter(mActivity, {
                ProductListActivity.launch(mActivity,it.BRAND_NAME)
            })

            val layoutManager = LinearLayoutManager(mActivity)
            mContentView.swipe_target.setLayoutManager(layoutManager)
            mContentView.swipe_target.setAdapter(mAdapter)
            mAdapter?.setDatas(data)
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefersh: Boolean, result: BaseResult<List<ProductBrandBean>>) {
        data?.addAll(result?.data?: arrayListOf())
    }


}