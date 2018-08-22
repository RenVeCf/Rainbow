package com.ipd.taxiu.ui.activity.address

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.AddressBean
import com.ipd.taxiu.bean.ProvinceBean
import com.ipd.taxiu.platform.global.GlobalParam
import com.ipd.taxiu.presenter.AddressPresenter
import com.ipd.taxiu.ui.BaseUIActivity
import com.ipd.taxiu.widget.MessageDialog
import com.ipd.taxiu.widget.PickerUtil
import kotlinx.android.synthetic.main.activity_add_address.*

/**
Created by Miss on 2018/8/10
 *添加收货地址
 */
class AddAddressActivity : BaseUIActivity(), AddressPresenter.IAddAddressView, AddressPresenter.ICityView,
        AddressPresenter.IAddressInfoView ,AddressPresenter.IAddressUpdateView,AddressPresenter.IAddressDeleteView{
    private val pickerUtil = PickerUtil()
    private var status: Int = 2
    private val addressType by lazy { intent.getIntExtra("addressType", 0) }
    private val addressId by lazy { intent.getStringExtra("addressId")}
    private var mPresenter: AddressPresenter<AddressPresenter.IAddAddressView>? = null
    private var mPresenterCity: AddressPresenter<AddressPresenter.ICityView>? = null
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

        mPresenterCity = AddressPresenter()
        mPresenterCity?.attachView(this, this)

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

        mPresenterCity?.detachView()
        mPresenterCity = null

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
        if (addressType == 2){
            mPresenter?.getAddressInfo(GlobalParam.getUserId(), addressId)
        }
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
        mPresenterCity?.getCityList("0", GlobalParam.getUserId())
        getStatus()
    }

    fun getStatus(){
        status = if (cb_default.isChecked) {
            2
        } else {
            1
        }
    }

    override fun initListener() {
        btn_save.setOnClickListener {
            val address: String = et_detailed_address.text.toString()

            var cityString: String = tv_choose_city.text.toString()
            if (!cityString.isEmpty()) {
                val strs = cityString.split(" ")
                city = strs[1]
                dist = strs[2]
                prov = strs[0]
            }
            val recipient: String = et_recipients.text.toString()
            val tel = et_phone_number.text.toString()
            val userId = GlobalParam.getUserId()
            if (addressType == 1) {
                getStatus()
                mPresenter?.addAddress(address, city, dist, prov, recipient, status, tel, userId)
            } else if (addressType == 2){
                getStatus()
                mPresenterUpdate?.getAddressUpdate(address,city,dist,prov,recipient,status,tel.toLong(),userId,addressId)
                finish()
            }
        }

        rl_address.setOnClickListener {
            pickerUtil.showPickerView(this, tv_choose_city)
        }
    }

    override fun onAddSuccess() {
        toastShow("添加成功")
        finish()
    }

    override fun onAddFail(errMsg: String) {
        toastShow(errMsg)
    }


    private fun initMessageDialog() {
        val builder = MessageDialog.Builder(this)
        builder.setTitle("确认要删除该收货地址吗？")
        builder.setMessage("删除后不可恢复，请谨慎操作")
        builder.setCommit("确认删除") { builder ->
            mPresenterDelete?.deleteAddress(GlobalParam.getUserId(),addressId)
            builder.dialog.dismiss()
            finish()
        }
        builder.setCancel("暂不删除") { builder -> builder.dialog.dismiss() }
        builder.dialog.show()
    }

    override fun getListFail(errMsg: String) {
        toastShow(errMsg)
    }

    override fun getListSuccess(data: ArrayList<ProvinceBean>) {
        pickerUtil.initJsonData(this)
    }

    override fun getAddressInfoSuccess(data: AddressBean) {
        et_recipients.setText(data.RECIPIENT)
        et_phone_number.setText(data.TEL.toString())
        tv_choose_city.text = data.PROV+" "+data.CITY+" "+data.DIST
        et_detailed_address.setText(data.ADDRESS)
        cb_default.isChecked = data.STATUS != 1
    }

    override fun getAddressInfoFail(errMsg: String) {
        toastShow(errMsg)
    }

    override fun updateFail(errMsg: String) {
        toastShow(errMsg)
    }

    override fun updateSuccess() {
        toastShow("修改成功")
    }

    override fun deleteFail(errMsg: String) {
        toastShow(errMsg)
    }

    override fun deleteSuccess() {
        loadData()
        toastShow("删除成功")
    }
}