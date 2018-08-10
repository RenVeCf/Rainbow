package com.ipd.taxiu.ui.fragment.store

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.ipd.taxiu.MainActivity
import com.ipd.taxiu.R
import com.ipd.taxiu.adapter.ProductAdapter
import com.ipd.taxiu.bean.ProductBean
import com.ipd.taxiu.ui.ListFragment
import com.ipd.taxiu.ui.activity.store.ProductDetailActivity
import com.ipd.taxiu.widget.ScreenLayout
import kotlinx.android.synthetic.main.fragment_product_list.view.*
import rx.Observable

class ProductListFragment : ListFragment<List<ProductBean>, ProductBean>() {

    companion object {
        fun newInstance(): ProductListFragment {
            val fragment = ProductListFragment()
            return fragment
        }
    }

    override fun getContentLayout(): Int = R.layout.fragment_product_list

    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        val screenLayout = mRootView?.findViewById<ScreenLayout>(R.id.screen_layout_container)
        screenLayout?.setBackgroupView(recycler_view)
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

    override fun loadListData(): Observable<List<ProductBean>> {
        return Observable.create<List<ProductBean>> {
            val productList = ArrayList<ProductBean>()
            for (index: Int in 0 until 10) {
                productList.add(ProductBean())
            }
            it.onNext(productList)
            it.onCompleted()
        }
    }

    override fun isNoMoreData(result: List<ProductBean>): Int {
        return NORMAL
    }

    private var mAdapter: ProductAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = ProductAdapter(mActivity, data, {
                //商品详情
                ProductDetailActivity.launch(mActivity)
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

    override fun addData(isRefresh: Boolean, result: List<ProductBean>) {
        data?.addAll(result)
    }


}