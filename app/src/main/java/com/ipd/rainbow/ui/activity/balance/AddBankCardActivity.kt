package com.ipd.rainbow.ui.activity.balance

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import com.ipd.rainbow.R
import com.ipd.rainbow.bean.BankCardBean
import com.ipd.rainbow.bean.BankTypeListBean
import com.ipd.rainbow.event.UpdateBankCardEvent
import com.ipd.rainbow.presenter.store.BankPresenter
import com.ipd.rainbow.ui.BaseUIActivity
import com.ipd.rainbow.utils.BankCardUtils
import com.ipd.rainbow.widget.BankCardTextWatcher
import com.ipd.rainbow.widget.PickerUtil
import kotlinx.android.synthetic.main.activity_add_bank_card.*
import org.greenrobot.eventbus.EventBus

/**
Created by Miss on 2018/8/10
添加银行卡
 */
class AddBankCardActivity : BaseUIActivity(), BankPresenter.IBankView {
    private var bankType: Int = 0
    private val pickerUtil = PickerUtil()

    companion object {
        fun launch(activity: Activity, bankType: Int, bankCardId: String? = null) {
            val intent = Intent(activity, AddBankCardActivity::class.java)
            intent.putExtra("bankType", bankType)
            if (bankType == MyBalanceActivity.UPDATE_BANK_CARD) {
                if (TextUtils.isEmpty(bankCardId)) throw IllegalArgumentException("bankCardId should not null")
                intent.putExtra("bankCardId", bankCardId)
            }
            activity.startActivity(intent)
        }
    }

    private var mPresenter: BankPresenter? = null
    override fun onViewAttach() {
        super.onViewAttach()
        mPresenter = BankPresenter()
        mPresenter?.attachView(this, this)
    }

    override fun onViewDetach() {
        super.onViewDetach()
        mPresenter?.detachView()
        mPresenter = null
    }

    override fun getContentLayout(): Int = R.layout.activity_add_bank_card
    override fun getToolbarTitle(): String {
        bankType = intent.getIntExtra("bankType", 0)
        if (bankType == MyBalanceActivity.ADD_BANK_CARD) {
            return "添加银行卡"
        }
        return "修改银行卡"
    }

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    private var bankCardId: String = ""
    override fun loadData() {
        BankCardTextWatcher.bind(et_bank_card_code)
        if (bankType == MyBalanceActivity.UPDATE_BANK_CARD) {
            bankCardId = intent.getStringExtra("bankCardId")
            mPresenter?.getBankCardInfo(bankCardId)
        }

    }

    override fun initListener() {
        ll_card_type.setOnClickListener { mPresenter?.loadBankTypeList() }
        btn_save.setOnClickListener {
            if (bankTypeInfo == null) {
                toastShow("请选择银行")
                return@setOnClickListener
            }
            var bankCard: String = et_bank_card_code.text.toString().trim()
            var accountName: String = et_account_name.text.toString().trim()
            var bankDeposit: String = tv_bank_deposit.text.toString().trim()
            if (!bankCard.isEmpty()) {
                bankCard = bankCard.replace(" ", "")
            }
            if (!BankCardUtils.checkBankCard(bankCard)) {
                toastShow("请填写正确的银行卡号")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(accountName)) {
                toastShow("请输入银行卡对应的真实姓名")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(bankDeposit)) {
                toastShow("请输入银行卡对应开户行")
                return@setOnClickListener
            }

            if (bankType == MyBalanceActivity.ADD_BANK_CARD) {
                mPresenter?.addBankCard(accountName, bankDeposit, bankTypeInfo?.BANK_TYPE_ID.toString(), bankCard)
            }
            if (bankType == MyBalanceActivity.UPDATE_BANK_CARD) {
                mPresenter?.changeBankCard(bankCardId, accountName, bankDeposit, bankTypeInfo?.BANK_TYPE_ID.toString(), bankCard)
            }
        }
    }

    private var bankTypeInfo: BankTypeListBean? = null
    override fun loadBankTypeSuccess(bankList: List<BankTypeListBean>) {
        pickerUtil.initBankCardOption(this, bankList, tv_choose_bank_type, { options1, options2, options3, v ->
            bankTypeInfo = bankList[options1]
            tv_choose_bank_type.text = bankTypeInfo?.BANK_NAME
        })
    }

    override fun loadBankCardInfoSuccess(bankInfo: BankCardBean) {
        tv_choose_bank_type.text = bankInfo.BANK_NAME
        et_bank_card_code.setText(bankInfo.CARD_NUM)
        et_account_name.setText(bankInfo.ACCOUNT_NAME)
        tv_bank_deposit.setText(bankInfo.BANK_DEPOSIT)
        bankTypeInfo = BankTypeListBean(bankInfo.BANK_TYPE_ID, bankInfo.BANK_NAME)
    }

    override fun loadBankCardInfoFail(errMsg: String) {
        showError(errMsg)
    }

    override fun loadBankTypeFail(errMsg: String) {
        toastShow(errMsg)
    }

    override fun addBankSuccess() {
        EventBus.getDefault().post(UpdateBankCardEvent())
        toastShow(true,"添加成功")
        finish()
    }

    override fun addBankFail(errMsg: String) {
        toastShow(errMsg)
    }

    override fun changeBankSuccess() {
        EventBus.getDefault().post(UpdateBankCardEvent())
        toastShow(true,"修改成功")
        finish()
    }

    override fun changeBankFail(errMsg: String) {
        toastShow(errMsg)
    }
}