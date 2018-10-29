package com.ipd.taxiu.ui.activity.referral

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.ipd.taxiu.R
import com.ipd.taxiu.adapter.HomepageAdapter
import com.ipd.taxiu.bean.OtherBean
import com.ipd.taxiu.imageload.ImageLoader
import com.ipd.taxiu.platform.http.HttpUrl
import com.ipd.taxiu.presenter.MinePresenter
import com.ipd.taxiu.ui.BaseUIActivity
import com.ipd.taxiu.utils.User
import kotlinx.android.synthetic.main.activity_homepage.*
import kotlinx.android.synthetic.main.item_friend_honmpage_header.*

/**
 * Created by Miss on 2018/7/25
 * 查看他人主页
 */
class HomepageActivity : BaseUIActivity(), MinePresenter.IAttentionView, MinePresenter.IOtherView {

    companion object {
        fun launch(activity: Activity, otherUserId: Int) {
            val intent = Intent(activity, HomepageActivity::class.java)
            intent.putExtra("otherUserId", otherUserId)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarLayout() = -1
    override fun getContentLayout() = R.layout.activity_homepage

    private var mPresenter: MinePresenter<MinePresenter.IOtherView>? = null
    override fun onViewAttach() {
        super.onViewAttach()
        mPresenter = MinePresenter()
        mPresenter!!.attachView(this, this)
    }

    override fun onViewDetach() {
        super.onViewDetach()
        mPresenter!!.detachView()
        mPresenter = null
    }

    private val mOtherUserId: Int by lazy { intent.getIntExtra("otherUserId", -1) }
    override fun initView(bundle: Bundle?) {

    }

    override fun loadData() {
        mPresenter?.other(mOtherUserId.toString())
    }

    override fun initListener() {
        iv_back.setOnClickListener {
            finish()
        }
    }

    override fun onFail(errMsg: String) {
        toastShow(errMsg)
    }

    override fun onSuccess(msg: String, data: Int) {
        toastShow(true, msg)
        User.setAttentBtnStyle(mActivity, data, ll_attention)
    }

    private var mAdapter: HomepageAdapter? = null
    override fun onGetOtherSuccess(data: OtherBean) {
        User.setAttentBtnStyle(mActivity, data.IS_ATTEN, ll_attention)
        ll_attention.setOnClickListener {
            mPresenter?.attention(mOtherUserId)
        }

        ImageLoader.loadAvatar(this, HttpUrl.IMAGE_URL + data.LOGO, civ_header)
        tv_friend_nickname.text = data.NICKNAME
        tv_tag.text = data.TAG
        tv_attention_num.text = data.ATTENTION_NUM.toString() + ""
        tv_fans_num.text = data.FANS_NUM.toString() + ""

//        mAdapter = HomepageAdapter(this, data.)
//        swipe_target.layoutManager = LinearLayoutManager(this)
//        swipe_target.adapter = mAdapter
    }

    override fun onGetOtherFail(errMsg: String) {
        toastShow(errMsg)
    }
}
