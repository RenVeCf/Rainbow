package com.ipd.taxiu.ui.activity.taxiu

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import cn.jzvd.Jzvd
import cn.sharesdk.framework.Platform
import com.ipd.taxiu.MainActivity
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.TaxiuDetailBean
import com.ipd.taxiu.event.UpdateCollectTaxiuEvent
import com.ipd.taxiu.event.UpdateMineTaxiuEvent
import com.ipd.taxiu.platform.global.Constant
import com.ipd.taxiu.platform.http.HttpUrl
import com.ipd.taxiu.presenter.store.TaxiuDetailPresenter
import com.ipd.taxiu.ui.BaseUIActivity
import com.ipd.taxiu.ui.fragment.taxiu.TaxiuDetailFragment
import com.ipd.taxiu.utils.ShareCallback
import com.ipd.taxiu.utils.StringUtils
import com.ipd.taxiu.widget.MessageDialog
import com.ipd.taxiu.widget.ShareDialog
import com.ipd.taxiu.widget.ShareDialogClick
import kotlinx.android.synthetic.main.activity_taxiu_detail.*
import kotlinx.android.synthetic.main.admin_taxiu_toolbar.*
import org.greenrobot.eventbus.EventBus
import java.util.*

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
                            mPresenter?.delete(taxiuId)
                        })
                        .setCancel("暂不删除", { builder ->
                            builder.dismiss()
                        }).show()
            }
        }

        circle_collect.setOnClickListener {
            //收藏
            mPresenter?.toCollect(taxiuId)

        }

        circle_comment.setOnClickListener {
            TaxiuAllCommentActivity.launch(mActivity, taxiuId, isAdmin)
        }

    }


    private var detailInfo: TaxiuDetailBean? = null
    override fun loadDetailSuccess(detail: TaxiuDetailBean) {
        showContent()
        detailInfo = detail
        circle_comment.setCircleNum(detailInfo?.REPLY ?: 0)
        iv_collect.isSelected = detailInfo?.IS_COLLECT ?: 0 == 1

        val fragment = TaxiuDetailFragment.newInstance(taxiuId)
        fragment.setDetailData(detail)
        supportFragmentManager.beginTransaction().replace(R.id.fl_container, fragment).commit()

        if (isAdmin) {
            iv_share.setOnClickListener {
                val dialog = ShareDialog(mActivity)
                dialog.setShareDialogOnClickListener(getShareDialogClick(detail))
                dialog.show()
            }
        } else {
            circle_share.setOnClickListener {
                val dialog = ShareDialog(mActivity)
                dialog.setShareDialogOnClickListener(getShareDialogClick(detail))
                dialog.show()
            }
        }
    }

    fun getShareDialogClick(detail: TaxiuDetailBean): ShareDialog.ShareDialogOnclickListener {
        var pic = HttpUrl.IMAGE_URL + detail.LOGO
        if (detail.TYPE == 2) {
            val pics = StringUtils.splitImages(detail.PIC)
            pic = if (pics == null || pics.isEmpty()) "" else HttpUrl.IMAGE_URL + pics[0]
        }
        return ShareDialogClick()
                .setShareTitle(Constant.SHARE_TAXIU_CONTENT)
                .setShareContent(detail.CONTENT)
                .setShareLogoUrl(pic)
                .setCallback(object : ShareDialogClick.MainPlatformActionListener {
                    override fun onComplete(platform: Platform?, i: Int, hashMap: HashMap<String, Any>?) {
                        toastShow(true, "分享成功")
                        ShareCallback.shareTaxiu(detail.SHOW_ID)
                    }

                    override fun onError(platform: Platform?, i: Int, throwable: Throwable?) {
                        toastShow("分享失败")
                    }

                    override fun onCancel(platform: Platform?, i: Int) {
                        toastShow("取消分享")
                    }

                })
                .setShareUrl(HttpUrl.APK_DOWNLOAD_URL)
    }

    override fun loadDetailFail(errMsg: String) {
        showError(errMsg)
    }

    override fun collectSuccess() {
        EventBus.getDefault().post(UpdateCollectTaxiuEvent())
        detailInfo?.IS_COLLECT = if (detailInfo?.IS_COLLECT == 0) 1 else 0
        iv_collect.isSelected = detailInfo?.IS_COLLECT ?: 0 == 1
    }

    override fun collectFail(errMsg: String) {
        toastShow(errMsg)
    }

    override fun deleteSuccess() {
        EventBus.getDefault().post(UpdateMineTaxiuEvent())
        finish()
    }

    override fun deleteFail(errMsg: String) {
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