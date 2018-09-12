package com.ipd.taxiu.ui.activity.taxiu

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.ipd.taxiu.MainActivity
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.TaxiuDetailBean
import com.ipd.taxiu.presenter.store.TaxiuDetailPresenter
import com.ipd.taxiu.ui.BaseUIActivity
import com.ipd.taxiu.ui.fragment.taxiu.TaxiuDetailFragment
import com.ipd.taxiu.widget.MessageDialog
import kotlinx.android.synthetic.main.activity_taxiu_detail.*
import kotlinx.android.synthetic.main.admin_taxiu_toolbar.*
import cn.jzvd.Jzvd



class TaxiuDetailActivity : BaseUIActivity(), TaxiuDetailPresenter.ITaxiuDetailView {


    companion object {
        fun launch(activity: Activity, taxiuId: Int = -1, isAdmin: Boolean = false) {
            val intent = Intent(activity, TaxiuDetailActivity::class.java)
            intent.putExtra("taxiuId", taxiuId)
            intent.putExtra("isAdmin", isAdmin)
            activity.startActivity(intent)
        }
    }

    private val isAdmin: Boolean by lazy { intent.getBooleanExtra("isAdmin", false) }
    private val taxiuId: Int by lazy { intent.getIntExtra("taxiuId", -1) }

    override fun getToolbarLayout(): Int {
        return if (isAdmin) {
            R.layout.admin_taxiu_toolbar
        } else {
            super.getToolbarLayout()
        }
    }

    override fun getToolbarTitle(): String = "它秀详情"

    override fun getContentLayout(): Int = R.layout.activity_taxiu_detail


    var mPresenter: TaxiuDetailPresenter? = null
    override fun onViewAttach() {
        super.onViewAttach()
        mPresenter = TaxiuDetailPresenter()
        mPresenter?.attachView(this, this)
    }

    override fun onViewDetach() {
        super.onViewDetach()
        mPresenter?.detachView()
        mPresenter = null
    }


    override fun initView(bundle: Bundle?) {
        initToolbar()
        ll_bottom_menu.visibility = if (isAdmin) View.GONE else View.VISIBLE
    }

    override fun loadData() {
        showProgress()
        mPresenter?.loadDetail(taxiuId)
    }

    override fun initListener() {
        tv_comment_topic.setOnClickListener {
            //参与话题
            PublishTaxiuCommentActivity.launch(mActivity, taxiuId)
        }
        if (isAdmin) {
            fl_delete.setOnClickListener {
                MessageDialog.Builder(mActivity)
                        .setTitle("确认要删除它秀内容吗?")
                        .setMessage("删除后不可恢复，请谨慎操作。")
                        .setCommit("确认删除", { builder ->
                            builder.dismiss()
                        })
                        .setCancel("暂不删除", { builder ->
                            builder.dismiss()
                        }).show()
            }
        }

        iv_collect.setOnClickListener {
            //收藏
            mPresenter?.toCollect(taxiuId)

        }
    }


    private var detailInfo: TaxiuDetailBean? = null
    override fun loadDetailSuccess(detail: TaxiuDetailBean) {
        showContent()
        detailInfo = detail
        iv_collect.isSelected = detailInfo?.IS_COLLECT ?: 0 == 1

        val fragment = TaxiuDetailFragment.newInstance(taxiuId)
        fragment.setDetailData(detail)
        supportFragmentManager.beginTransaction().replace(R.id.fl_container, fragment).commit()
    }

    override fun loadDetailFail(errMsg: String) {
        showError(errMsg)
    }

    override fun collectSuccess() {
        detailInfo?.IS_COLLECT = if (detailInfo?.IS_COLLECT == 0) 1 else 0
        iv_collect.isSelected = detailInfo?.IS_COLLECT ?: 0 == 1
    }

    override fun collectFail(errMsg: String) {
        toastShow(errMsg)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (isAdmin) {
            return false
        }
        menuInflater.inflate(R.menu.topic_detail_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_index) {
            //首页
            MainActivity.launch(mActivity)
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (Jzvd.backPress()) {
            return
        }
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        Jzvd.releaseAllVideos()
    }

}