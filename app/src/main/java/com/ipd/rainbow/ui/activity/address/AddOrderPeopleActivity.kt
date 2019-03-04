package com.ipd.rainbow.ui.activity.address

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import com.ipd.jumpbox.jumpboxlibrary.utils.CommonUtils
import com.ipd.rainbow.R
import com.ipd.rainbow.bean.OrderPeopleBean
import com.ipd.rainbow.event.UpdateOrderPeopleEvent
import com.ipd.rainbow.platform.global.GlobalParam
import com.ipd.rainbow.platform.http.RxScheduler
import com.ipd.rainbow.presenter.OrderPeoplePresenter
import com.ipd.rainbow.ui.BaseUIActivity
import com.ipd.rainbow.widget.MessageDialog
import com.ipd.rainbow.widget.PickerUtil
import kotlinx.android.synthetic.main.activity_add_order_people.*
import org.greenrobot.eventbus.EventBus
import rx.Observable
import rx.Subscriber

/**
Created by Miss on 2018/8/10
 *添加订购人
 */
class AddOrderPeopleActivity : BaseUIActivity(), OrderPeoplePresenter.IAddAddressView,
        OrderPeoplePresenter.IAddressInfoView, OrderPeoplePresenter.IAddressUpdateView, OrderPeoplePresenter.IAddressDeleteView {
    private val pickerUtil by lazy { PickerUtil() }
    private var status: Int = 2
    private val addressType by lazy { intent.getIntExtra("addressType", 0) }
    private val addressId by lazy { intent.getStringExtra("addressId") }
    private var mPresenter: OrderPeoplePresenter<OrderPeoplePresenter.IAddAddressView>? = null
    private var mPresenterInfo: OrderPeoplePresenter<OrderPeoplePresenter.IAddressInfoView>? = null
    private var mPresenterUpdate: OrderPeoplePresenter<OrderPeoplePresenter.IAddressUpdateView>? = null
    private var mPresenterDelete: OrderPeoplePresenter<OrderPeoplePresenter.IAddressDeleteView>? = null

    //addressType 1.添加地址 2.修改地址 其他：选择地址
    companion object {
        fun launch(activity: Activity, addressType: Int, addressId: String) {
            val intent = Intent(activity, AddOrderPeopleActivity::class.java)
            intent.putExtra("addressType", addressType)
            intent.putExtra("addressId", addressId)
            activity.startActivity(intent)
        }
    }

    override fun onViewAttach() {
        super.onViewAttach()
        mPresenter = OrderPeoplePresenter()
        mPresenter?.attachView(this, this)

        mPresenterInfo = OrderPeoplePresenter()
        mPresenterInfo?.attachView(this, this)

        mPresenterUpdate = OrderPeoplePresenter()
        mPresenterUpdate?.attachView(this, this)

        mPresenterDelete = OrderPeoplePresenter()
        mPresenterDelete?.attachView(this, this)
    }

    override fun onViewDetach() {
        super.onViewDetach()
        mPresenter?.detachView()
        mPresenter = null

        mPresenterInfo?.detachView()

        mPresenterUpdate?.detachView()
        mPresenterUpdate = null

        mPresenterDelete?.detachView()
        mPresenterDelete = null
    }

    override fun getContentLayout(): Int {
        return R.layout.activity_add_order_people
    }

    override fun getToolbarTitle(): String {
        return when (addressType) {
            1 -> "新增订购人"
            2 -> "修改订购人"
            else -> "选择收货地址"
        }
    }

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return if (addressType == 2) {
            menuInflater.inflate(R.menu.menu_delete_address, menu)
            true
        } else false
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item != null) {
            initMessageDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun loadData() {
        showProgress()
        Observable.create<Long> {
            try {
                pickerUtil.initJsonData(mActivity)
                it.onNext(0)
            } catch (e: Exception) {
                it.onError(e)
            }
        }.compose(RxScheduler.applyScheduler())
                .subscribe(object : Subscriber<Long>() {
                    override fun onNext(t: Long?) {
                        showContent()
                        if (addressType == 2) {
                            mPresenterInfo?.getAddressInfo(GlobalParam.getUserId(), addressId)
                        }
                    }

                    override fun onCompleted() {
                    }

                    override fun onError(e: Throwable?) {
                        showError()
                    }

                })
    }

    fun getStatus() {
        status = if (cb_default.isChecked) {
            2
        } else {
            1
        }
    }

    override fun initListener() {
        btn_save.setOnClickListener {

            val recipient: String = et_recipients.text.toString().trim()
            val tel = et_phone_number.text.toString().trim()
            val cardNumber = et_card_number.text.toString().trim()

            if (TextUtils.isEmpty(recipient)) {
                toastShow("请输入订购人姓名")
                return@setOnClickListener
            }
            if (!CommonUtils.isMobileNO(tel)) {
                toastShow("请输入正确的手机号")
                return@setOnClickListener
            }
            if (cardNumber.length != 18) {
                toastShow("请输入正确的身份证号码")
                return@setOnClickListener
            }

            val userId = GlobalParam.getUserId()
            if (addressType == 1) {
                getStatus()
                mPresenter?.addAddress(recipient, status, tel, cardNumber, userId)
            } else if (addressType == 2) {
                getStatus()
                mPresenterUpdate?.getAddressUpdate(recipient, status, tel, cardNumber, userId, addressId)
            }
        }

    }

    private fun initMessageDialog() {
        val builder = MessageDialog.Builder(this)
        builder.setTitle("确认要删除该订购人信息吗？")
        builder.setMessage("删除后不可恢复，请谨慎操作")
        builder.setCommit("确认删除") { builder ->
            mPresenterDelete?.deleteAddress(GlobalParam.getUserId(), addressId)
            builder.dialog.dismiss()
        }
        builder.setCancel("暂不删除") { builder -> builder.dialog.dismiss() }
        builder.dialog.show()
    }

    override fun getAddressInfoSuccess(data: OrderPeopleBean) {
        et_recipients.setText(data.TRUENAME)
        et_phone_number.setText(data.PHONE.toString())
        et_card_number.setText(data.IDENTITY)
        cb_default.isChecked = data.STATUS != 1
    }

    override fun getAddressInfoFail(errMsg: String) {
        toastShow(errMsg)
    }


    override fun onAddSuccess() {
        EventBus.getDefault().post(UpdateOrderPeopleEvent())
        toastShow(true, "添加成功")
        finish()
    }

    override fun onAddFail(errMsg: String) {
        toastShow(errMsg)
    }


    override fun updateFail(errMsg: String) {
        toastShow(errMsg)
    }

    override fun updateSuccess() {
        EventBus.getDefault().post(UpdateOrderPeopleEvent())
        toastShow(true,"修改成功")
        finish()
    }

    override fun deleteFail(errMsg: String) {
        toastShow(errMsg)
    }

    override fun deleteSuccess() {
        EventBus.getDefault().post(UpdateOrderPeopleEvent())
        toastShow(true,"删除成功")
        finish()
    }
}