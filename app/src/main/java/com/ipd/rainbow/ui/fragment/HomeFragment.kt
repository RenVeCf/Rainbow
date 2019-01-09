package com.ipd.rainbow.ui.fragment

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.ipd.rainbow.R
import com.ipd.rainbow.adapter.HomeAdapter
import com.ipd.rainbow.bean.*
import com.ipd.rainbow.event.UpdateHomeEvent
import com.ipd.rainbow.imageload.ImageLoader
import com.ipd.rainbow.platform.global.Constant
import com.ipd.rainbow.platform.global.GlobalParam
import com.ipd.rainbow.platform.http.ApiManager
import com.ipd.rainbow.platform.http.HttpUrl
import com.ipd.rainbow.platform.http.Response
import com.ipd.rainbow.platform.http.RxScheduler
import com.ipd.rainbow.ui.ListFragment
import com.ipd.rainbow.ui.activity.account.PetStageActivity
import com.ipd.rainbow.ui.activity.pet.AddPetActivity
import com.ipd.rainbow.utils.GuideUtil
import com.ipd.rainbow.widget.guideview.GuideBuilder
import com.ipd.rainbow.widget.guideview.component.GuideClassroomComponent
import com.ipd.rainbow.widget.guideview.component.GuideTaXiuComponent
import com.ipd.rainbow.widget.guideview.component.GuideTalkComponent
import com.ipd.rainbow.widget.guideview.component.GuideTopicComponent
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.layout_home_header_expanded.*
import kotlinx.android.synthetic.main.layout_home_header_expanded.view.*
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
                                if (homeResult.SHOW_LIST != null && homeResult.SHOW_LIST.isNotEmpty()) {
                                    mHomeInfo.boutique = HomeBean.IndexBoutiqueBean(homeResult.SHOW_LIST)
                                }
                                //热门话题
                                if (homeResult.TOPIC_DATA != null) {
                                    mHomeInfo.topic = HomeBean.IndexTopicBean(homeResult.TOPIC_DATA)
                                }
                                //热门问答
                                if (homeResult.QUESTION_LIST != null && homeResult.QUESTION_LIST.isNotEmpty()) {
                                    mHomeInfo.talk = HomeBean.IndexTalkBean(homeResult.QUESTION_LIST)
                                }
                                //热门课堂
                                if (homeResult.CLASS_DATA != null) {
                                    mHomeInfo.classRoom = HomeBean.IndexClassRoomBean(homeResult.CLASS_DATA)
                                }

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
            showGuideView()
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: BaseResult<List<TaxiuBean>>) {
        if (isRefresh) {
            data?.add(mHomeInfo.headerInfo)
            if (mHomeInfo.boutique != null) data?.add(mHomeInfo.boutique)
            if (mHomeInfo.topic != null && mHomeInfo.topic.topic.TOPIC_ID != 0) data?.add(mHomeInfo.topic)
            if (mHomeInfo.talk != null) data?.add(mHomeInfo.talk)
            if (mHomeInfo.classRoom != null && mHomeInfo.classRoom.classRoom.CLASS_ROOM_ID != 0) data?.add(mHomeInfo.classRoom)
        }
        data?.addAll(result?.data ?: arrayListOf())
    }

    private fun onHasPet(data: HomeResultBean.PETBean) {
        ll_pet_show.visibility = View.VISIBLE
        ll_pet_age.visibility = View.VISIBLE
        tv_pet_no.visibility = View.GONE
        ll_to_add_pet.visibility = View.GONE

        ImageLoader.loadImgFromLocal(mActivity, HttpUrl.IMAGE_URL + data.LOGO, civ_avatar)
        tv_name.text = data.NICKNAME
        if (data.GENDER == 1) {
            tv_phone.text = "我是男孩子"
        }
        if (data.GENDER == 2) {
            tv_phone.text = "我是女孩子"
        }
        tv_age.text = data.MONTH_NUM.toString() + "个月" + data.DAY_NUM + "天"
    }

    private fun onWithoutPet() {
        ll_pet_show.visibility = View.GONE
        ll_pet_age.visibility = View.GONE
        tv_pet_no.visibility = View.VISIBLE
        ll_to_add_pet.visibility = View.VISIBLE
        civ_avatar.setImageResource(R.mipmap.avatar_default)

        ll_to_add_pet.setOnClickListener {
            ApiManager.getService().getUserInfo(GlobalParam.getUserIdOrJump())
                    .compose(RxScheduler.applyScheduler())
                    .subscribe(object : Response<BaseResult<UserBean>>(mActivity, true) {
                        override fun _onNext(result: BaseResult<UserBean>) {
                            if (result.code == 0) {
                                if (result.data.STEP == 0) {
                                    PetStageActivity.launch(mActivity, GlobalParam.getUserId())
                                } else {
                                    val intent = Intent(mActivity, AddPetActivity::class.java)
                                    intent.putExtra("petWay", 1)
                                    startActivity(intent)
                                }
                            } else {
                                toastShow(result.msg)
                            }
                        }
                    })
        }
    }

    @Subscribe
    fun onMainEvent(event: UpdateHomeEvent) {
        isCreate = true
        onRefresh()
    }


    fun showGuideView() {
        if (!GlobalParam.getFirstEnterHome()) return
        mContentView.ll_to_add_pet.post {
            if (!GlobalParam.getFirstEnterHome()) return@post
            GlobalParam.setFirstEnterHome(false)
            if (mContentView.ll_to_add_pet.visibility == View.GONE) {
                showExtraGuideView()
            } else {
                GuideUtil.getAddPetGuide(R.id.ll_to_add_pet, object : GuideBuilder.OnVisibilityChangedListener {
                    override fun onShown() {
                    }

                    override fun onDismiss() {
                        showExtraGuideView()
                    }

                }).show(mActivity)
            }
        }
    }

    fun showExtraGuideView() {
        GuideUtil.getStoreGuide(R.id.ll_store, object : GuideBuilder.OnVisibilityChangedListener {
            override fun onShown() {
            }

            override fun onDismiss() {
                GuideUtil.getHomeCategoryGuide(R.id.ll_topic, GuideTopicComponent(), object : GuideBuilder.OnVisibilityChangedListener {
                    override fun onShown() {

                    }

                    override fun onDismiss() {
                        GuideUtil.getHomeCategoryGuide(R.id.ll_taxiu, GuideTaXiuComponent(), object : GuideBuilder.OnVisibilityChangedListener {
                            override fun onShown() {

                            }

                            override fun onDismiss() {
                                GuideUtil.getHomeCategoryGuide(R.id.ll_talk, GuideTalkComponent(), object : GuideBuilder.OnVisibilityChangedListener {
                                    override fun onShown() {

                                    }

                                    override fun onDismiss() {
                                        GuideUtil.getHomeCategoryGuide(R.id.ll_classroom, GuideClassroomComponent(), object : GuideBuilder.OnVisibilityChangedListener {
                                            override fun onShown() {

                                            }

                                            override fun onDismiss() {
                                                GuideUtil.getMineGuide(R.id.ll_mine, object : GuideBuilder.OnVisibilityChangedListener {
                                                    override fun onShown() {

                                                    }

                                                    override fun onDismiss() {
                                                    }

                                                }).show(mActivity)
                                            }

                                        }).show(mActivity)
                                    }

                                }).show(mActivity)
                            }

                        }).show(mActivity)
                    }

                }).show(mActivity)
            }

        }).show(mActivity)

    }
}