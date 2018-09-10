package com.ipd.taxiu.ui.fragment

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.ipd.taxiu.R
import com.ipd.taxiu.adapter.HomeAdapter
import com.ipd.taxiu.bean.HomeBean
import com.ipd.taxiu.bean.ShowPetBean
import com.ipd.taxiu.imageload.ImageLoader
import com.ipd.taxiu.platform.http.HttpUrl
import com.ipd.taxiu.presenter.ShowPetPresenter
import com.ipd.taxiu.presenter.store.TaxiuDetailPresenter
import com.ipd.taxiu.ui.ListFragment
import com.ipd.taxiu.ui.activity.pet.AddPetActivity
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.layout_home_header_expanded.*
import rx.Observable

class HomeFragment : ListFragment<HomeBean, Any>(), ShowPetPresenter.IShowPetView {
    var mPresenter: ShowPetPresenter<ShowPetPresenter.IShowPetView>? = null
    override fun getContentLayout(): Int = R.layout.fragment_home

    override fun loadListData(): Observable<HomeBean> {
        return Observable.create {
            val homeBean = HomeBean()
            homeBean.buildInfo()
            it.onNext(homeBean)
            it.onCompleted()
        }
    }

    override fun onViewAttach() {
        super.onViewAttach()
        mPresenter = ShowPetPresenter()
        mPresenter?.attachView(mActivity, this)
    }

    override fun onViewDetach() {
        super.onViewDetach()
        mPresenter?.detachView()
        mPresenter = null
    }

    override fun loadData() {
        super.loadData()
        mPresenter?.showPet()
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
    }

    override fun isNoMoreData(result: HomeBean): Int {
        return NORMAL
    }

    private var mAdapter: HomeAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = HomeAdapter(mActivity, data)
            recycler_view.layoutManager = LinearLayoutManager(mActivity)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: HomeBean) {
        if (isRefresh) {
            data?.add(result.banner)
            data?.add(result.boutique)
            data?.add(result.topic)
            data?.add(result.talk)
            data?.add(result.classRoom)
        }
        data?.addAll(result.taxiuList)
    }

    override fun getSuccess(data: ShowPetBean) {
        ll_pet_show.visibility = View.VISIBLE
        ll_pet_age.visibility = View.VISIBLE
        tv_pet_no.visibility = View.GONE
        ll_to_add_pet.visibility = View.GONE

        ImageLoader.loadImgFromLocal(mActivity, HttpUrl.IMAGE_URL + data.LOGO, civ_avatar)
        tv_name.text = data.NICKNAME
        if (data.GENDER == 1) {
            tv_phone.text = "我是男孩纸"
        }
        if (data.GENDER == 2) {
            tv_phone.text = "我是女孩纸"
        }
        tv_age.text = data.MONTH_NUM.toString() + "个月" + data.DAY_NUM + "天"
    }

    override fun getFail(errMsg: String) {
        ll_pet_show.visibility = View.GONE
        ll_pet_age.visibility = View.GONE
        tv_pet_no.visibility = View.VISIBLE
        ll_to_add_pet.visibility = View.VISIBLE

        ll_to_add_pet.setOnClickListener {
            val intent = Intent(mActivity, AddPetActivity::class.java)
            intent.putExtra("petWay", 1)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        mPresenter?.showPet()
    }

}