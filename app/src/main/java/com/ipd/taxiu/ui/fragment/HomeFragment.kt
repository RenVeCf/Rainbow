package com.ipd.taxiu.ui.fragment

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.ipd.taxiu.R
import com.ipd.taxiu.adapter.HomeAdapter
import com.ipd.taxiu.bean.BaseResult
import com.ipd.taxiu.bean.HomeBean
import com.ipd.taxiu.bean.HomeResultBean
import com.ipd.taxiu.bean.TaxiuBean
import com.ipd.taxiu.event.UpdateHomeEvent
import com.ipd.taxiu.imageload.ImageLoader
import com.ipd.taxiu.platform.global.Constant
import com.ipd.taxiu.platform.global.GlobalParam
import com.ipd.taxiu.platform.http.ApiManager
import com.ipd.taxiu.platform.http.HttpUrl
import com.ipd.taxiu.platform.http.Response
import com.ipd.taxiu.platform.http.RxScheduler
import com.ipd.taxiu.ui.ListFragment
import com.ipd.taxiu.ui.activity.pet.AddPetActivity
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.layout_home_header_expanded.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import rx.Observable

class HomeFragment : ListFragment<BaseResult<List<TaxiuBean>>, Any>() {
    override fun getContentLayout(): Int = R.layout.fragment_home


    override fun onViewAttach() {
        super.onViewAttach()
        EventBus.getDefault().register(this)
    }

    override fun onViewDetach() {
        super.onViewDetach()
        EventBus.getDefault().unregister(this)
    }

    private lateinit var mHomeInfo: HomeBean
    override fun getListData(isRefresh: Boolean) {
        if (isRefresh) {
            checkNeedShowProgress()
            ApiManager.getService().home(GlobalParam.getUserIdOrJump())
                    .compose(RxScheduler.applyScheduler())
                    .subscribe(object : Response<BaseResult<HomeResultBean>>() {
                        override fun _onNext(result: BaseResult<HomeResultBean>) {
                            if (result.code == 0) {
                                val homeResult = result.data
                                //目前没有宠物，也返回了宠物对象，只能判断宠物ID是否为0
                                if (homeResult.PET == null || homeResult.PET.PET_ID == 0) {
                                    onWithoutPet()
                                } else {
                                    onHasPet(homeResult.PET)
                                }
                                mHomeInfo = HomeBean()
                                //轮播图
                                mHomeInfo.headerInfo = HomeBean.IndexHeaderbean(homeResult.BANNER_LIST, homeResult.PET)
                                //它秀精选
                                mHomeInfo.boutique = HomeBean.IndexBoutiqueBean(homeResult.SHOW_LIST)
                                //热门话题
                                mHomeInfo.topic = HomeBean.IndexTopicBean(homeResult.TOPIC_DATA)
                                //热门问答
                                mHomeInfo.talk = HomeBean.IndexTalkBean(homeResult.QUESTION_LIST)
                                //热门课堂
                                mHomeInfo.classRoom = HomeBean.IndexClassRoomBean(homeResult.CLASS_DATA)

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

    override fun loadListData(): Observable<BaseResult<List<TaxiuBean>>> {
        return ApiManager.getService().taxiuList(GlobalParam.getUserIdOrJump(), Constant.PAGE_SIZE, page, 2, "")

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

    override fun isNoMoreData(result: BaseResult<List<TaxiuBean>>): Int {
        if (page > INIT_PAGE && (result.data == null || result.data.isEmpty())) {
            return NO_MORE_DATA
        }
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

    override fun addData(isRefresh: Boolean, result: BaseResult<List<TaxiuBean>>) {
        if (isRefresh) {
            data?.add(mHomeInfo.headerInfo)
            data?.add(mHomeInfo.boutique)
            data?.add(mHomeInfo.topic)
            data?.add(mHomeInfo.talk)
            data?.add(mHomeInfo.classRoom)
        }
        data?.addAll(result?.data?: arrayListOf())
    }

    private fun onHasPet(data: HomeResultBean.PETBean) {
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

    private fun onWithoutPet() {
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

    @Subscribe
    fun onMainEvent(event: UpdateHomeEvent) {
        isCreate = true
        onRefresh()
    }

}