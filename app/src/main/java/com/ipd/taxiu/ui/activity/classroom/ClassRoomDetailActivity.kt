package com.ipd.taxiu.ui.activity.classroom

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.text.TextUtils
import android.view.View
import cn.sharesdk.framework.Platform
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.ClassRoomBean
import com.ipd.taxiu.bean.WechatBean
import com.ipd.taxiu.event.PayRequestEvent
import com.ipd.taxiu.event.UpdateCollectClassroomEvent
import com.ipd.taxiu.imageload.ImageLoader
import com.ipd.taxiu.platform.global.Constant
import com.ipd.taxiu.platform.http.HttpUrl
import com.ipd.taxiu.presenter.store.ClassroomDetailPresenter
import com.ipd.taxiu.ui.BaseUIActivity
import com.ipd.taxiu.utils.*
import com.ipd.taxiu.widget.ChoosePayTypeLayout
import com.ipd.taxiu.widget.PayWayDialog
import com.ipd.taxiu.widget.ShareDialog
import com.ipd.taxiu.widget.ShareDialogClick
import kotlinx.android.synthetic.main.activity_classroom_detail.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.*

class ClassRoomDetailActivity : BaseUIActivity(), ClassroomDetailPresenter.IClassroomDetailView, AlipayUtils.OnPayListener {

    companion object {
        fun launch(activity: Activity, classroomId: Int) {
            val intent = Intent(activity, ClassRoomDetailActivity::class.java)
            intent.putExtra("classroomId", classroomId)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "课堂详情"

    override fun getContentLayout(): Int = R.layout.activity_classroom_detail


    var mPresenter: ClassroomDetailPresenter? = null
    override fun onViewAttach() {
        super.onViewAttach()
        EventBus.getDefault().register(this)
        mPresenter = ClassroomDetailPresenter()
        mPresenter?.attachView(this, this)
    }

    override fun onViewDetach() {
        super.onViewDetach()
        EventBus.getDefault().unregister(this)
        mPresenter?.detachView()
        mPresenter = null
    }


    private val classroomId: Int by lazy { intent.getIntExtra("classroomId", -1) }

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    val html: String = "<style>img{max-width:100%}</style><p><span style='font-size:14px;'>兑换流程：</span></p><p><span style=font-size:12px;>1、选择需要兑换的商品并点击进入详情界面；</span><br /><span style='font-size:12px;'> 2、点击立即兑换，进行兑换商品；</span><br /><span style='font-size:12px;'> 3、填写收货信息，我们将在3-5个工作日内完成发货；</span></p><p><span style='font-size:14px;'>注意事项：</span></p><p><span style='font-size:12px;'>1、请确定您的积分余额大于所兑换商品的积分；</span><br /><span style='font-size:12px;'> 2、所有兑换商品发货方式均为顺丰速运发货；</span><br /><span style='font-size:12px;'> 3、客户电话：400-821-8661；</span></p><p><span style='font-size:14px;'>商品参数：</span><br /> <span style='font-size:12px;'>商品编号: FFIE398L-9185000C0</span><br /><span style='font-size:12px;'> 所属分类：浴室挂件</span></p><div style='text-align:center;'><img src='http://121.199.8.244:7100/pic/20170712/1499842970671.png' alt=\"\" /></div>"
    override fun loadData() {
        showProgress()
        mPresenter?.loadDetail(classroomId)
    }

    override fun initListener() {
    }

    override fun loadDetailSuccess(detail: ClassRoomBean) {
        showContent()
        ImageLoader.loadNoPlaceHolderImg(mActivity, detail.LOGO, iv_classroom_image)
        tv_classroom_name.text = detail.TITLE
        tv_classroom_lenght.text = detail.TIME_LENGTH
        tv_classroom_time.text = detail.BEGIN_TIME
        tv_classroom_teacher.text = detail.TEACHER
        tv_classroom_price.text = "￥${detail.PRICE}"
        iv_collect.isSelected = detail.IS_COLLECT == 1

        fl_collect.setOnClickListener {
            mPresenter?.toCollect(classroomId)
        }

        cl_buy.isEnabled = detail.CLASS_STATE != 3
        if (detail.CLASS_STATE == 3) {
            tv_buy_end.visibility = View.VISIBLE
            tv_classroom_price.visibility = View.GONE
            tv_buy_now.visibility = View.GONE

            rl_buy.setOnClickListener {}
        } else {
            tv_buy_end.visibility = View.GONE
            tv_classroom_price.visibility = View.VISIBLE
            tv_buy_now.visibility = View.VISIBLE

            rl_buy.setOnClickListener {
                PayWayDialog(this, R.style.recharge_pay_dialog)
                        .show()
            }
        }


//        tv_answer_content.text = Html.fromHtml(detail.CONTENT, HtmlImageGetter(mActivity, tv_answer_content), null)
        web_view.loadData(WebUtils.getHtmlData(detail.CONTENT), "text/html; charset=UTF-8", null)

        fl_share.setOnClickListener {
            val dialog = ShareDialog(mActivity)
            dialog.setShareDialogOnClickListener(ShareDialogClick()
                    .setShareTitle(detail.TITLE)
                    .setShareContent(Constant.SHARE_CLASSROOM_CONTENT)
                    .setShareLogoUrl(HttpUrl.IMAGE_URL + detail.LOGO)
                    .setShareUrl(HttpUrl.APK_DOWNLOAD_URL)
                    .setCallback(object : ShareDialogClick.MainPlatformActionListener {
                        override fun onComplete(platform: Platform?, i: Int, hashMap: HashMap<String, Any>?) {
                            toastShow(true, "分享成功")
                            ShareCallback.shareClassroom(detail.CLASS_ROOM_ID)
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

    override fun loadDetailFail(errMsg: String) {
        showError(errMsg)
    }

    override fun collectSuccess() {
        EventBus.getDefault().post(UpdateCollectClassroomEvent())
        iv_collect.isSelected = !iv_collect.isSelected
    }

    override fun collectFail(errMsg: String) {
        toastShow(errMsg)
    }


    @Subscribe
    fun onMainEvent(event: PayRequestEvent) {
        when (event.payType) {
            ChoosePayTypeLayout.PayType.WECHAT -> {
                mPresenter?.wechat(classroomId)
            }
            ChoosePayTypeLayout.PayType.ALIPAY -> {
                mPresenter?.alipay(classroomId)
            }
            ChoosePayTypeLayout.PayType.BALANCE -> {
                mPresenter?.balance(classroomId)
            }
        }
    }

    private var mOrderNo: String = ""
    override fun classroomWechatSuccess(orderNo: String, wechatInfo: WechatBean) {
        mOrderNo = orderNo
        WeChatUtils.getInstance(mActivity).startPay(wechatInfo)
    }

    override fun classroomAlipaySuccess(orderNo: String, info: String) {
        mOrderNo = orderNo
        AlipayUtils.getInstance().alipayByData(mActivity, info, this)
    }

    override fun classroomBalanceSuccess(orderNo: String) {
        mOrderNo = orderNo
        onPaySuccess()
    }

    override fun onPaySuccess() {
        toastShow(true, "购买成功")
        if (TextUtils.isEmpty(mOrderNo)) return
        OwnedClassRoomActivity.launch(mActivity, mOrderNo)
    }

    override fun payFail(errMsg: String) {
        toastShow(errMsg)
    }

    override fun onPayWait() {
    }

    override fun onPayFail() {
        toastShow("支付失败")
    }

    override fun onDestroy() {
        super.onDestroy()
        AlipayUtils.getInstance().release()
        WeChatUtils.getInstance(mActivity).release()
    }
}