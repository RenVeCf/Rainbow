package com.ipd.rainbow.ui.activity.address

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import com.ipd.jumpbox.jumpboxlibrary.utils.CommonUtils
import com.ipd.rainbow.R
import com.ipd.rainbow.bean.AddressBean
import com.ipd.rainbow.event.UpdateAddressEvent
import com.ipd.rainbow.platform.global.GlobalParam
import com.ipd.rainbow.platform.http.RxScheduler
import com.ipd.rainbow.presenter.AddressPresenter
import com.ipd.rainbow.ui.BaseUIActivity
import com.ipd.rainbow.widget.MessageDialog
import com.ipd.rainbow.widget.PickerUtil
import kotlinx.android.synthetic.main.activity_add_address.*
import org.greenrobot.eventbus.EventBus
import rx.Observable
import rx.Subscriber

/**
Created by Miss on 2018/8/10
 *添加收货地址
 */
class AddAddressActivity : BaseUIActivity(), AddressPresenter.IAddAddressView,
        AddressPresenter.IAddressInfoView, AddressPresenter.IAddressUpdateView, AddressPresenter.IAddressDeleteView {
    private val pickerUtil by lazy { PickerUtil() }
    private var status: Int = 2
    private val addressType by lazy { intent.getIntExtra("addressType", 0) }
    private val addressId by lazy { intent.getStringExtra("addressId") }
    private var mPresenter: AddressPresenter<AddressPresenter.IAddAddressView>? = null
    private var mPresenterInfo: AddressPresenter<AddressPresenter.IAddressInfoView>? = null
    private var mPresenterUpdate: AddressPresenter<AddressPresenter.IAddressUpdateView>? = null
    private var mPresenterDelete: AddressPresenter<AddressPresenter.IAddressDeleteView>? = null

    private var city: String = ""
    private var dist: String = ""
    private var prov: String = ""

    //addressType 1.添加地址 2.修改地址 其他：选择地址
    companion object {
        fun launch(activity: Activity, addressType: Int, addressId: String) {
            val intent = Intent(activity, AddAddressActivity::class.java)
            intent.putExtra("addressType", addressType)
            intent.putExtra("addressId", addressId)
            activity.startActivity(intent)
        }
    }

    override fun onViewAttach() {
        super.onViewAttach()
        mPresenter = AddressPresenter()
        mPresenter?.attachView(this, this)

        mPresenterInfo = AddressPresenter()
        mPresenterInfo?.attachView(this, this)

        mPresenterUpdate = AddressPresenter()
        mPresenterUpdate?.attachView(this, this)

        mPresenterDelete = AddressPresenter()
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
        return R.layout.activity_add_address
    }

    override fun getToolbarTitle(): String {
        return when (addressType) {
            1 -> "添加收货地址"
            2 -> "修改收货地址"
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
            val address: String = et_detailed_address.text.toString().trim()

            var cityString: String = tv_choose_city.text.toString()
            if (!cityString.isEmpty()) {
                val strs = cityString.split(" ")
                city = strs[1]
                dist = strs[2]
                prov = strs[0]
            }
            val recipient: String = et_recipients.text.toString().trim()
            val tel = et_phone_number.text.toString().trim()

            if (TextUtils.isEmpty(recipient)) {
                toastShow("请输入收货人姓名")
                return@setOnClickListener
            }
            if (!CommonUtils.isMobileNO(tel)) {
                toastShow("请输入正确的手机号")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(address)) {
                toastShow("详细地址不能为空")
                return@setOnClickListener
            }

            val userId = GlobalParam.getUserId()
            if (addressType == 1) {
                getStatus()
                mPresenter?.addAddress(address, city, dist, prov, recipient, status, tel, userId)
            } else if (addressType == 2) {
                getStatus()
                mPresenterUpdate?.getAddressUpdate(address, city, dist, prov, recipient, status, tel, userId, addressId)
            }
        }

        rl_address.setOnClickListener {
            pickerUtil.showPickerView(this, tv_choose_city)
        }
    }

    private fun initMessageDialog() {
        val builder = MessageDialog.Builder(this)
        builder.setTitle("确认要删除该收货地址吗？")
        builder.setMessage("删除后不可恢复，请谨慎操作")
        builder.setCommit("确认删除") { builder ->
            mPresenterDelete?.deleteAddress(GlobalParam.getUserId(), addressId)
            builder.dialog.dismiss()
        }
        builder.setCancel("暂不删除") { builder -> builder.dialog.dismiss() }
        builder.dialog.show()
    }

    override fun getAddressInfoSuccess(data: AddressBean) {
        et_recipients.setText(data.RECIPIENT)
        et_phone_number.setText(data.TEL.toString())
        tv_choose_city.text = data.PROV + " " + data.CITY + " " + data.DIST
        et_detailed_address.setText(data.ADDRESS)
        cb_default.isChecked = data.STATUS != 1
    }

    override fun getAddressInfoFail(errMsg: String) {
        toastShow(errMsg)
    }


    override fun onAddSuccess() {
        EventBus.getDefault().post(UpdateAddressEvent())
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
        EventBus.getDefault().post(UpdateAddressEvent())
        toastShow("修改成功")
        finish()
    }

    override fun deleteFail(errMsg: String) {
        toastShow(errMsg)
    }

    override fun deleteSuccess() {
        EventBus.getDefault().post(UpdateAddressEvent())
        toastShow("删除成功")
        finish()
    }
}