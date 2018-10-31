package com.ipd.taxiu.ui.activity.store

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.view.ViewGroup
import cn.sharesdk.framework.Platform
import cn.xiaoneng.uiapi.Ntalker
import com.ipd.taxiu.MainActivity
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.BaseResult
import com.ipd.taxiu.bean.ExchangeBean
import com.ipd.taxiu.bean.ProductDetailBean
import com.ipd.taxiu.bean.ProductModelResult
import com.ipd.taxiu.event.UpdateCollectProductEvent
import com.ipd.taxiu.platform.global.Constant
import com.ipd.taxiu.platform.global.GlobalParam
import com.ipd.taxiu.platform.http.ApiManager
import com.ipd.taxiu.platform.http.HttpUrl
import com.ipd.taxiu.platform.http.Response
import com.ipd.taxiu.platform.http.RxScheduler
import com.ipd.taxiu.ui.BaseUIActivity
import com.ipd.taxiu.ui.fragment.store.ProductDetailFragment
import com.ipd.taxiu.ui.fragment.store.ProductEvaluateFragment
import com.ipd.taxiu.utils.ShareCallback
import com.ipd.taxiu.utils.StoreType
import com.ipd.taxiu.utils.StringUtils
import com.ipd.taxiu.widget.ProductModelDialog
import com.ipd.taxiu.widget.ShareDialog
import com.ipd.taxiu.widget.ShareDialogClick
import kotlinx.android.synthetic.main.activity_product_detail.*
import kotlinx.android.synthetic.main.product_detail_toolbar.*
import org.greenrobot.eventbus.EventBus
import java.util.*

class ProductDetailActivity : BaseUIActivity() {
    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, ProductDetailActivity::class.java)
            activity.startActivity(intent)
        }

        fun launch(activity: Activity, productId: Int, fromId: Int) {
            val intent = Intent(activity, ProductDetailActivity::class.java)
            intent.putExtra("productId", productId)
            intent.putExtra("fromId", fromId)
            activity.startActivity(intent)
        }
    }

    private val mProductId by lazy { intent.getIntExtra("productId", -1) }
    private val mFromId by lazy { intent.getIntExtra("fromId", -1) }
    var mActivityId = -1

    override fun getToolbarLayout(): Int = R.layout.product_detail_toolbar

    override fun getContentLayout(): Int = R.layout.activity_product_detail

    override fun initView(bundle: Bundle?) {
        ll_collect.isEnabled = false
    }

    private val detailFragment: ProductDetailFragment by lazy { ProductDetailFragment.newInstance(mProductId, mFromId) }
    private val evaluateFragment: ProductEvaluateFragment by lazy { ProductEvaluateFragment.newInstance(mProductId, mFromId) }
    override fun loadData() {
        switchTab(0)
        view_pager.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return if (position == 0) detailFragment else evaluateFragment
            }

            override fun getCount(): Int = 2
        }

    }

    private var mProductInfo: ProductDetailBean? = null
    private var mProductModelResult: ProductModelResult? = null
    override fun initListener() {
        iv_back.setOnClickListener { finish() }
        rl_product.setOnClickListener {
            view_pager.setCurrentItem(0, false)
            detailFragment.switchPage(0)
            switchTab(0)
        }
        rl_detail.setOnClickListener {
            view_pager.setCurrentItem(0, false)
            detailFragment.switchPage(1)
            switchTab(1)
        }
        rl_evaluate.setOnClickListener {
            switchToEvaluate()
        }

        tv_add_cart.setOnClickListener {
            //加入购物车
            if (mProductInfo == null) return@setOnClickListener
            if (mProductInfo?.KIND == StoreType.PRODUCT_GROUP_PURCHASE) {
                getProductModelInfo(ProductModelDialog.BUY)
            } else {
                getProductModelInfo(ProductModelDialog.CART)
            }

        }
        tv_buy.setOnClickListener {
            //立即购买
            if (mProductInfo?.KIND == StoreType.PRODUCT_GROUP_PURCHASE) {
                getProductModelInfo(ProductModelDialog.SPELL)
            } else {
                getProductModelInfo(ProductModelDialog.BUY)
            }
        }

        ll_kefu.setOnClickListener {
            //客服
            Ntalker.getBaseInstance().startChat(mActivity, Constant.XIAONENG_KEFU_ID, "客服接待", null)
        }
        ll_collect.setOnClickListener {
            //收藏
            ApiManager.getService().storeProductCollect(GlobalParam.getUserIdOrJump(), mProductId, mFromId)
                    .compose(RxScheduler.applyScheduler())
                    .subscribe(object : Response<BaseResult<ExchangeBean>>(mActivity, true) {
                        override fun _onNext(result: BaseResult<ExchangeBean>) {
                            if (result.code == 0) {
                                EventBus.getDefault().post(UpdateCollectProductEvent())
                                iv_collect.isSelected = !iv_collect.isSelected
                            } else {
                                toastShow(result.msg)
                            }
                        }
                    })

        }
        iv_cart.setOnClickListener {
            MainActivity.launch(mActivity, "cart")
        }
    }

    private fun showProductModelDialog(type: Int) {
        val dialog = ProductModelDialog(mActivity)
        val logos = StringUtils.splitImages(mProductInfo?.LOGO ?: "")
        dialog.setData(type, if (logos == null || logos.isEmpty()) "" else logos[0], mProductModelResult, mActivityId)
        dialog.show()
    }

    private fun getProductModelInfo(type: Int) {
        if (mActivityId == -1) return
        if (mProductModelResult == null) {
            ApiManager.getService().storeProductModel(GlobalParam.getUserIdOrJump(), mProductId, mFromId, mActivityId)
                    .compose(RxScheduler.applyScheduler())
                    .subscribe(object : Response<ProductModelResult>(mActivity, true) {
                        override fun _onNext(result: ProductModelResult) {
                            if (result.code == 0) {
                                mProductModelResult = result
                                showProductModelDialog(type)
                            } else {
                                toastShow(result.msg)
                            }
                        }
                    })
        } else {
            showProductModelDialog(type)
        }

    }

    fun switchToEvaluate() {
        view_pager.setCurrentItem(1, false)
        switchTab(2)
    }

    fun switchTab(pos: Int) {
        for (index: Int in 0 until ll_tab.childCount) {
            val tabView = ll_tab.getChildAt(index) as ViewGroup
            tabView.getChildAt(0).isSelected = index == pos
            tabView.getChildAt(1).setBackgroundColor(resources.getColor(if (index == pos) R.color.colorPrimaryDark else R.color.transparent))
        }
    }

    fun setCollect(isCollect: Boolean) {
        ll_collect.isEnabled = true
        iv_collect.isSelected = isCollect
    }

    fun setProductDetail(info: ProductDetailBean) {
        mProductInfo = info
        mActivityId = mProductInfo?.ACTIVITY_ID ?: -1
        setCollect(mProductInfo?.IS_COLLECT == 1)

        if (mProductInfo?.KIND == StoreType.PRODUCT_GROUP_PURCHASE) {
            tv_add_cart.textSize = 12f
            tv_buy.textSize = 12f
            tv_add_cart.text = "￥${mProductInfo?.PRICE}\n单独购买"
            tv_buy.text = "￥${mProductInfo?.CURRENT_PRICE}\n参团购买"
        }

        iv_share.setOnClickListener {
            val dialog = ShareDialog(mActivity)
            dialog.setShareDialogOnClickListener(ShareDialogClick()
                    .setShareTitle(info.PROCUCT_NAME)
                    .setShareContent(Constant.SHARE_PRODUCT_CONTENT)
                    .setShareLogoUrl(HttpUrl.IMAGE_URL + info.LOGO)
                    .setShareUrl(HttpUrl.APK_DOWNLOAD_URL)
                    .setCallback(object : ShareDialogClick.MainPlatformActionListener {
                        override fun onComplete(platform: Platform?, i: Int, hashMap: HashMap<String, Any>?) {
                            toastShow(true, "分享成功")
                            ShareCallback.shareProduct(info.PRODUCT_ID, info.FORM_ID)
                        }

                        override fun onError(platform: Platform?, i: Int, throwable: Throwable?) {
                            toastShow("分享失败")
                        }

                        override fun onCancel(platform: Platform?, i: Int) {
                            toastShow("取消分享")
                        }

                    }))
            dialog.show()
        }

    }

}