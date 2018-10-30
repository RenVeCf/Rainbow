package com.ipd.taxiu.ui.fragment.store

import android.support.v7.widget.LinearLayoutManager
import com.ipd.taxiu.adapter.NewProductAdapter
import com.ipd.taxiu.bean.BaseResult
import com.ipd.taxiu.bean.ProductBean
import com.ipd.taxiu.platform.global.Constant
import com.ipd.taxiu.platform.global.GlobalParam
import com.ipd.taxiu.platform.http.ApiManager
import com.ipd.taxiu.ui.ListFragment
import com.ipd.taxiu.ui.activity.store.ProductDetailActivity
import rx.Observable

class NewProductListFragment : ListFragment<BaseResult<List<ProductBean>>, ProductBean>() {

    companion object {
        fun newInstance(): NewProductListFragment {
            val fragment = NewProductListFragment()
            return fragment
        }
    }


    override fun loadListData(): Observable<BaseResult<List<ProductBean>>> {
        return ApiManager.getService().storeProductNew(GlobalParam.getUserIdOrJump(), Constant.PAGE_SIZE, page)
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
            mAdapter = NewProductAdapter(mActivity, data, {
                //商品详情
                ProductDetailActivity.launch(mActivity, it.PRODUCT_ID, it.FORM_ID)
            })
            recycler_view.layoutManager = LinearLayoutManager(mActivity)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: BaseResult<List<ProductBean>>) {
        data?.addAll(result?.data?: arrayListOf())
    }


}