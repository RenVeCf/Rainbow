package com.ipd.rainbow.ui.fragment.store

import android.net.http.SslError
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.webkit.*
import com.ipd.rainbow.R
import com.ipd.rainbow.bean.BaseResult
import com.ipd.rainbow.bean.ProductDetailBean
import com.ipd.rainbow.bean.ProductParamBean
import com.ipd.rainbow.platform.global.GlobalParam
import com.ipd.rainbow.platform.http.ApiManager
import com.ipd.rainbow.platform.http.Response
import com.ipd.rainbow.platform.http.RxScheduler
import com.ipd.rainbow.ui.BaseFragment
import com.ipd.rainbow.widget.ProductParamDialog
import kotlinx.android.synthetic.main.layout_product_detail_bottom.view.*

class ProductDetailBottomFragment : BaseFragment() {
    override fun getBaseLayout(): Int = R.layout.layout_product_detail_bottom

    override fun initView(bundle: Bundle?) {
    }

    private lateinit var mProductInfo: ProductDetailBean
    fun setDetailData(info: ProductDetailBean) {
        mProductInfo = info
    }

    override fun loadData() {
        val settings = mRootView?.web_view?.settings
        settings?.javaScriptEnabled = true
//        settings?.javaScriptCanOpenWindowsAutomatically = true
//        settings?.useWideViewPort = true
//        settings?.loadWithOverviewMode = true
//        settings?.displayZoomControls = false
//        settings?.layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS

        mRootView?.web_view?.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                if (newProgress < 100) {
                    mRootView?.progress_bar?.visibility = View.VISIBLE
                } else if (newProgress >= 100) {
                    mRootView?.progress_bar?.visibility = View.GONE
                }
                mRootView?.progress_bar?.progress = newProgress
            }

        }



        mRootView?.web_view?.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }

            override fun onReceivedSslError(view: WebView, handler: SslErrorHandler, error: SslError) {
                handler.proceed()
            }
        }
//        val htmlData = "<style>img{max-width:100%}</style><p><span style='font-size:14px;'>兑换流程：</span></p><p><span style=font-size:12px;>1、选择需要兑换的商品并点击进入详情界面；</span><br /><span style='font-size:12px;'> 2、点击立即兑换，进行兑换商品；</span><br /><span style='font-size:12px;'> 3、填写收货信息，我们将在3-5个工作日内完成发货；</span></p><p><span style='font-size:14px;'>注意事项：</span></p><p><span style='font-size:12px;'>1、请确定您的积分余额大于所兑换商品的积分；</span><br /><span style='font-size:12px;'> 2、所有兑换商品发货方式均为顺丰速运发货；</span><br /><span style='font-size:12px;'> 3、客户电话：400-821-8661；</span></p><p><span style='font-size:14px;'>商品参数：</span><br /> <span style='font-size:12px;'>商品编号: FFIE398L-9185000C0</span><br /><span style='font-size:12px;'> 所属分类：浴室挂件</span></p><div style='text-align:center;'><img src='http://121.199.8.244:7100/pic/20170712/1499842970671.png' alt=\"\" /></div>"
        val htmlData = mProductInfo.CONTENT
        if (!TextUtils.isEmpty(htmlData)) {
            mRootView?.web_view?.loadData(getHtmlData(htmlData), "text/html; charset=UTF-8", null)
        }
    }

    private fun getHtmlData(bodyHTML: String): String {
        val head = "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " +
                "<style>img{max-width:100% !important; width:auto; height:auto;}</style>" +
                "</head>"
        return "<html>$head<body style:'height:auto;max-width: 100%; width:auto;'>$bodyHTML</body></html>"
    }

    override fun initListener() {
        mRootView?.ll_product_param?.setOnClickListener {
            loadProductParam()
        }
    }

    private fun loadProductParam() {
        ApiManager.getService().storeProductParam(GlobalParam.getRequestUserId(), mProductInfo?.PRODUCT_ID, mProductInfo?.FORM_ID)
                .compose(RxScheduler.applyScheduler())
                .subscribe(object : Response<BaseResult<List<ProductParamBean>>>(mActivity, true) {
                    override fun _onNext(result: BaseResult<List<ProductParamBean>>) {
                        if (result.code == 0) {
                            val paramDialog = ProductParamDialog(mActivity)
                            paramDialog.setData(result.data)
                            paramDialog.show()
                        } else {
                            toastShow(result.msg)
                        }
                    }
                })


    }

    override fun onDestroy() {
        super.onDestroy()
        mRootView?.web_view?.removeAllViews()
        mRootView?.web_view?.destroy()
    }

}