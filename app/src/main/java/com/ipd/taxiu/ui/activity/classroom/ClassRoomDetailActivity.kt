package com.ipd.taxiu.ui.activity.classroom

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Html
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.ClassRoomBean
import com.ipd.taxiu.imageload.ImageLoader
import com.ipd.taxiu.presenter.store.ClassroomDetailPresenter
import com.ipd.taxiu.ui.BaseUIActivity
import com.ipd.taxiu.utils.HtmlImageGetter
import kotlinx.android.synthetic.main.activity_classroom_detail.*

class ClassRoomDetailActivity : BaseUIActivity(), ClassroomDetailPresenter.IClassroomDetailView {

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
        mPresenter = ClassroomDetailPresenter()
        mPresenter?.attachView(this, this)
    }

    override fun onViewDetach() {
        super.onViewDetach()
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
        rl_buy.setOnClickListener { OwnedClassRoomActivity.launch(mActivity) }
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

        tv_answer_content.text = Html.fromHtml(detail.CONTENT, HtmlImageGetter(mActivity, tv_answer_content), null)

    }

    override fun loadDetailFail(errMsg: String) {
        showError(errMsg)
    }

    override fun collectSuccess() {
        iv_collect.isSelected = !iv_collect.isSelected
    }

    override fun collectFail(errMsg: String) {
        toastShow(errMsg)
    }

}