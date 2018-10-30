package com.ipd.taxiu.ui.activity.referral

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.ipd.taxiu.R
import com.ipd.taxiu.ui.BaseUIActivity
import com.ipd.taxiu.ui.fragment.referral.HomePageFragment

/**
 * Created by Miss on 2018/7/25
 * 查看他人主页
 */
class HomepageActivity : BaseUIActivity() {

    companion object {
        fun launch(activity: Activity, otherUserId: Int) {
            val intent = Intent(activity, HomepageActivity::class.java)
            intent.putExtra("otherUserId", otherUserId)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarLayout() = -1
    override fun getContentLayout() = R.layout.activity_container


    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    private val mOtherUserId: Int by lazy { intent.getIntExtra("otherUserId", -1) }
    override fun loadData() {
        supportFragmentManager.beginTransaction().replace(R.id.fl_container, HomePageFragment.newInstance(mOtherUserId)).commit()
    }

    override fun initListener() {

    }


//    private var mPresenter: MinePresenter<MinePresenter.IOtherView>? = null
//    private var mListPresenter: ListPresenter<BaseResult<List<TaxiuBean>>>? = null
//    override fun onViewAttach() {
//        super.onViewAttach()
//        mPresenter = MinePresenter()
//        mPresenter!!.attachView(this, this)
//        mListPresenter = ListPresenter()
//        mListPresenter?.attachView(this, this)
//    }
//
//    override fun onViewDetach() {
//        super.onViewDetach()
//        mPresenter!!.detachView()
//        mPresenter = null
//        mListPresenter?.detachView()
//        mListPresenter = null
//    }
//
//    override fun initView(bundle: Bundle?) {
//
//    }
//
//    override fun loadData() {
//        mPresenter?.other(mOtherUserId.toString())
//
//
//    }
//
//    override fun initListener() {
//        iv_back.setOnClickListener {
//            finish()
//        }
//    }
//
//    override fun onFail(errMsg: String) {
//        toastShow(errMsg)
//    }
//
//    override fun onSuccess(msg: String, data: Int) {
//        toastShow(true, msg)
//        User.setAttentBtnStyle(mActivity, data, ll_attention)
//    }
//
//    private var mAdapter: HomepageAdapter? = null
//    override fun onGetOtherSuccess(data: OtherBean) {
//        User.setAttentBtnStyle(mActivity, data.IS_ATTEN, ll_attention)
//        ll_attention.setOnClickListener {
//            mPresenter?.attention(mOtherUserId)
//        }
//
//        ImageLoader.loadAvatar(this, HttpUrl.IMAGE_URL + data.LOGO, civ_header)
//        tv_friend_nickname.text = data.NICKNAME
//        tv_tag.text = data.TAG
//        tv_attention_num.text = data.ATTENTION_NUM.toString() + ""
//        tv_fans_num.text = data.FANS_NUM.toString() + ""
//
////        mAdapter = HomepageAdapter(this, data.)
////        swipe_target.layoutManager = LinearLayoutManager(this)
////        swipe_target.adapter = mAdapter
//    }
//
//    override fun onGetOtherFail(errMsg: String) {
//        toastShow(errMsg)
//    }
}
