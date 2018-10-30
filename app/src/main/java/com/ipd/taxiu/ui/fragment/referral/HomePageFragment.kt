package com.ipd.taxiu.ui.fragment.referral

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.ipd.taxiu.R
import com.ipd.taxiu.adapter.TaxiuAdapter
import com.ipd.taxiu.bean.BaseResult
import com.ipd.taxiu.bean.OtherBean
import com.ipd.taxiu.bean.TaxiuBean
import com.ipd.taxiu.imageload.ImageLoader
import com.ipd.taxiu.platform.global.Constant
import com.ipd.taxiu.platform.global.GlobalParam
import com.ipd.taxiu.platform.http.ApiManager
import com.ipd.taxiu.platform.http.Response
import com.ipd.taxiu.platform.http.RxScheduler
import com.ipd.taxiu.ui.ListFragment
import com.ipd.taxiu.ui.activity.taxiu.TaxiuDetailActivity
import com.ipd.taxiu.utils.User
import com.ipd.taxiu.widget.ProgressLayout
import kotlinx.android.synthetic.main.activity_homepage.view.*
import kotlinx.android.synthetic.main.item_friend_honmpage_header.view.*
import rx.Observable

class HomePageFragment : ListFragment<BaseResult<List<TaxiuBean>>, TaxiuBean>() {
    companion object {
        fun newInstance(otherUserId: Int): HomePageFragment {
            val topicListFragment = HomePageFragment()
            val bundle = Bundle()
            bundle.putInt("otherUserId", otherUserId)
            topicListFragment.arguments = bundle
            return topicListFragment
        }
    }

    override fun getProgressLayout(): ProgressLayout {
        return mContentView.taxiu_progress_layout
    }

    override fun getContentLayout(): Int = R.layout.activity_homepage

    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        getProgressLayout().setEmptyViewRes(R.layout.layout_empty_taxiu)
    }

    override fun initListener() {
        super.initListener()
        mContentView.iv_back.setOnClickListener {
            mActivity.finish()
        }
    }


    override fun getListData(isRefresh: Boolean) {
        if (isRefresh) {
            checkNeedShowProgress()
            ApiManager.getService().other(GlobalParam.getUserIdOrJump(), mOtherUserId)
                    .compose(RxScheduler.applyScheduler())
                    .subscribe(object : Response<BaseResult<OtherBean>>() {
                        override fun _onNext(result: BaseResult<OtherBean>) {
                            if (result.code == 0) {
                                val data = result.data
                                User.setAttentBtnStyle(mActivity, data.IS_ATTEN, mContentView.ll_attention)
                                mContentView.ll_attention.setOnClickListener {
                                    attention(mOtherUserId)
                                }
                                ImageLoader.loadAvatar(mActivity, data.LOGO, mContentView.civ_header)
                                mContentView.tv_friend_nickname.text = data.NICKNAME
                                mContentView.tv_tag.text = data.TAG
                                mContentView.tv_attention_num.text = data.ATTENTION_NUM.toString() + ""
                                mContentView.tv_fans_num.text = data.FANS_NUM.toString() + ""


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


    private fun attention(attentId: Int) {
        ApiManager.getService().attention(attentId, GlobalParam.getUserId())
                .compose(RxScheduler.applyScheduler())
                .subscribe(object : Response<BaseResult<Int>>(mActivity, true) {
                    override fun _onNext(result: BaseResult<Int>) {
                        if (result.code == 0) {
                            User.setAttentBtnStyle(mActivity, result.data, mContentView.ll_attention)
                        } else {
                            toastShow(result.msg)
                        }
                    }
                })
    }


    private val mOtherUserId by lazy { arguments.getInt("otherUserId", 0) }
    override fun loadListData(): Observable<BaseResult<List<TaxiuBean>>> {
        return ApiManager.getService().otherTaxiu(GlobalParam.getUserId(), Constant.PAGE_SIZE, page, mOtherUserId)
    }

    override fun isNoMoreData(result: BaseResult<List<TaxiuBean>>): Int {
        if (page == INIT_PAGE && (result.data == null || result.data.isEmpty())) {
            return EMPTY_DATA
        } else if (result.data == null || result.data.isEmpty()) {
            return NO_MORE_DATA
        }
        return NORMAL
    }

    private var mAdapter: TaxiuAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = TaxiuAdapter(mActivity, data, {
                //itemClick
                TaxiuDetailActivity.launch(mActivity, it.SHOW_ID, GlobalParam.getUserId() == it.USER_ID.toString())
            })
            recycler_view.layoutManager = LinearLayoutManager(mActivity)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: BaseResult<List<TaxiuBean>>) {
        data?.addAll(result?.data?: arrayListOf())
    }

}