package com.ipd.taxiu.ui.fragment.store

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.ipd.taxiu.MainActivity
import com.ipd.taxiu.R
import com.ipd.taxiu.adapter.ProductAdapter
import com.ipd.taxiu.bean.BaseResult
import com.ipd.taxiu.bean.ProductBean
import com.ipd.taxiu.platform.global.Constant
import com.ipd.taxiu.platform.global.GlobalParam
import com.ipd.taxiu.platform.http.ApiManager
import com.ipd.taxiu.ui.ListFragment
import com.ipd.taxiu.ui.activity.store.ProductDetailActivity
import com.ipd.taxiu.utils.ProductScreenView
import kotlinx.android.synthetic.main.fragment_product_list.view.*
import rx.Observable

class ProductListFragment : ListFragment<BaseResult<List<ProductBean>>, ProductBean>() {

    companion object {
        fun newInstance(searchKey: String): ProductListFragment {
            val fragment = ProductListFragment()
            val bundle = Bundle()
            bundle.putString("searchKey", searchKey)
            fragment.arguments = bundle
            return fragment
        }
    }

    private var mSearchKey: String = ""
    fun setSearchKey(searchKey: String) {
        mSearchKey = searchKey
    }

    override fun getContentLayout(): Int = R.layout.fragment_product_list

    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        mSearchKey = arguments.getString("searchKey", "")
    }

    override fun initListener() {
        super.initListener()
        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (recyclerView == null || recyclerView.childCount <= 0) return
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                mContentView.iv_scroll_top.visibility = if (linearLayoutManager.findFirstVisibleItemPosition() > 10) View.VISIBLE else View.GONE
            }
        })

        mContentView.iv_scroll_top.setOnClickListener {
            recycler_view?.smoothScrollToPosition(0)
        }
        mContentView.iv_store_home.setOnClickListener {
            MainActivity.launch(mActivity)
        }
    }

    private var mScreenView: ProductScreenView? = null
    fun setProductScreenView(screenView: ProductScreenView) {
        mScreenView = screenView
    }

    /**
     * 注释请看{@link com.ipd.taxiu.utils.ProductScreenView}
     */
    override fun loadListData(): Observable<BaseResult<List<ProductBean>>> {
        val compositeValue = mScreenView?.getCompositeValue() ?: 0
        val saleValue = mScreenView?.getSaleValue() ?: 0
        val priceValue = mScreenView?.getPriceValue() ?: 0
        val maxPrice = mScreenView?.getMaxPrice() ?: 0f
        val minPrice = mScreenView?.getMinPrice() ?: 0f
        val brandValue = mScreenView?.getBrandValue() ?: ""
        val applyValue = mScreenView?.getApplyValue() ?: ""
        val sizeValue = mScreenView?.getSizeValue() ?: ""
        val petTypeValue = mScreenView?.getPetTypeValue() ?: ""
        val netContentValue = mScreenView?.getNetContentValue() ?: ""
        val tasteValue = mScreenView?.getTasteValue() ?: ""
        val countryValue = mScreenView?.getCountryValue() ?: ""
        val thingTypeValue = mScreenView?.getThingTypeValue() ?: ""

        return ApiManager.getService().storeProductList(GlobalParam.getUserIdOrJump(), Constant.PAGE_SIZE, page, brandValue,
                compositeValue, mSearchKey, maxPrice, minPrice, priceValue, saleValue,
                applyValue, sizeValue, petTypeValue, netContentValue, tasteValue, countryValue, thingTypeValue, "0","0")
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
                //商品详情
                ProductDetailActivity.launch(mActivity, it.PRODUCT_ID, it.FORM_ID)
            })
            recycler_view.layoutManager = LinearLayoutManager(mActivity)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    fun switchShowType(): Int {
        val showType = mAdapter?.switchShowType() ?: ProductAdapter.ItemType.LIST
        if (showType == ProductAdapter.ItemType.LIST) {
            recycler_view.layoutManager = LinearLayoutManager(mActivity)
        } else {
            recycler_view.layoutManager = GridLayoutManager(mActivity, 2)
        }
        mAdapter?.notifyDataSetChanged()
        return showType
    }

    override fun addData(isRefresh: Boolean, result: BaseResult<List<ProductBean>>) {
        data?.addAll(result?.data ?: arrayListOf())
    }

    fun onSearch(searchKey: String) {
        mSearchKey = searchKey
        refreshWithProgress()
    }

    fun refreshWithProgress() {
        isCreate = true
        onRefresh()
    }


}