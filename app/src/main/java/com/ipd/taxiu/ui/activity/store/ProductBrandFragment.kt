package com.ipd.taxiu.ui.activity.store

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.ipd.taxiu.R
import com.ipd.taxiu.adapter.ProductBrandAdapter
import com.ipd.taxiu.bean.ProductBrandBean
import com.ipd.taxiu.ui.ListFragment
import com.mcxtzhang.indexlib.suspension.SuspensionDecoration
import kotlinx.android.synthetic.main.fragment_product_brand_list.view.*
import rx.Observable


class ProductBrandFragment : ListFragment<List<ProductBrandBean>, ProductBrandBean>() {

    companion object {
        fun newInstance(): ProductBrandFragment {
            val fragment = ProductBrandFragment()
            return fragment
        }
    }

    override fun getContentLayout(): Int = R.layout.fragment_product_brand_list
    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        setLoadMoreEnable(false)
        mContentView.index_bar.setNeedRealIndex(true)
    }

    override fun loadListData(): Observable<List<ProductBrandBean>> {
        return Observable.create<List<ProductBrandBean>> {
            val list: ArrayList<ProductBrandBean> = arrayListOf(
                    ProductBrandBean("阿拉斯加雪橇犬"),
                    ProductBrandBean("爱尔兰犬"),
                    ProductBrandBean("澳大利亚牧羊犬"),
                    ProductBrandBean("阿哥廷杜高犬"),
                    ProductBrandBean("比熊犬"),
                    ProductBrandBean("泰迪犬"),
                    ProductBrandBean("自主权"),
                    ProductBrandBean("阿拉斯加雪橇犬"),
                    ProductBrandBean("爱尔兰犬"),
                    ProductBrandBean("澳大利亚牧羊犬"),
                    ProductBrandBean("阿哥廷杜高犬"),
                    ProductBrandBean("比熊犬"),
                    ProductBrandBean("泰迪犬"),
                    ProductBrandBean("自主权"),
                    ProductBrandBean("牧羊犬")
            )
            it.onNext(list)
            it.onCompleted()
        }
    }

    override fun isNoMoreData(result: List<ProductBrandBean>): Int {
        if (result == null || result.isEmpty()) {
            return EMPTY_DATA
        }
        return NORMAL
    }

    private var mAdapter: ProductBrandAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = ProductBrandAdapter(mActivity, data, {
                ProductListActivity.launch(mActivity)
            })

            recycler_view.addItemDecoration(SuspensionDecoration(mActivity, data)
                    .setColorTitleBg(resources.getColor(R.color.aqua_haze)))

            val layoutManager = LinearLayoutManager(mActivity)
            recycler_view.layoutManager = layoutManager
            mContentView.index_bar.setmLayoutManager(layoutManager)
            recycler_view.adapter = mAdapter


        } else {
            mAdapter?.notifyDataSetChanged()
        }

        mContentView.index_bar.dataHelper.sortSourceDatas(data)
        mContentView.index_bar.setmSourceDatas(data)//设置数据
                .invalidate()
    }

    override fun addData(isRefersh: Boolean, result: List<ProductBrandBean>) {
        data?.addAll(result)
    }


}