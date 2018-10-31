package com.ipd.taxiu.ui.activity.classroom

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.ipd.jumpbox.jumpboxlibrary.bitmap.BitmapUtils
import com.ipd.jumpbox.jumpboxlibrary.utils.CommonUtils
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.BaseResult
import com.ipd.taxiu.bean.ClassRoomOrderDetailBean
import com.ipd.taxiu.imageload.ImageLoader
import com.ipd.taxiu.platform.global.GlobalParam
import com.ipd.taxiu.platform.http.ApiManager
import com.ipd.taxiu.platform.http.Response
import com.ipd.taxiu.platform.http.RxScheduler
import com.ipd.taxiu.ui.BaseUIActivity
import kotlinx.android.synthetic.main.activity_own_classroom.*

class OwnedClassRoomActivity : BaseUIActivity() {

    companion object {
        fun launch(activity: Activity, orderNo: String) {
            val intent = Intent(activity, OwnedClassRoomActivity::class.java)
            intent.putExtra("orderNo", orderNo)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "已购买课堂"

    override fun getContentLayout(): Int = R.layout.activity_own_classroom

    private val mOrderNo: String by lazy { intent.getStringExtra("orderNo") ?: "" }
    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
        showProgress()
        ApiManager.getService().classroomOrderDetail(GlobalParam.getUserIdOrJump(), mOrderNo)
                .compose(RxScheduler.applyScheduler())
                .subscribe(object : Response<BaseResult<ClassRoomOrderDetailBean>>() {
                    override fun _onNext(result: BaseResult<ClassRoomOrderDetailBean>) {
                        if (result.code == 0) {
                            loadClassRoomOrderDetailSuccess(result.data)
                            showContent()
                        } else {
                            showError(result.msg)
                        }
                    }

                    override fun onError(e: Throwable?) {
                        showError()
                    }
                })

    }

    private var mClassroomId: Int = 0
    private fun loadClassRoomOrderDetailSuccess(info: ClassRoomOrderDetailBean) {
        mClassroomId = info.CLASS_ROOM_ID
        tv_pay_price.text = "￥${info.PAY_FEE}"
        tv_pay_type.text = when (info.PAYWAY) {1 -> "支付宝支付"
            2 -> "微信支付"
            else -> "余额支付"
        }
        tv_pay_time.text = info.PAYTIME

        tv_classroom_name.text = info.CLASS_DATA.TITLE
        tv_classroom_lenght.text = info.CLASS_DATA.TIME_LENGTH
        tv_classroom_time.text = info.CLASS_DATA.BEGIN_TIME
        tv_classroom_teacher.text = info.CLASS_DATA.TEACHER
        ImageLoader.loadNoPlaceHolderImg(mActivity, info.CLASS_DATA.TWO_CODE, iv_classroom_code)

        tv_save_code.setOnClickListener {
            tv_save_code.isEnabled = false
            BitmapUtils.getInstance().savePhotoAndRefreshGallery(mActivity,iv_classroom_code, CommonUtils.getPhotoSavePath(mActivity) + "/" + System.currentTimeMillis() + ".png")
            toastShow(true,"保存成功")
            tv_save_code.isEnabled = true
        }


    }

    override fun initListener() {
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_classroom_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.classroom_detail) {
            if (mClassroomId == 0) return false
            ClassRoomDetailActivity.launch(this, mClassroomId)
            return true
        }

        return super.onOptionsItemSelected(item)
    }

}