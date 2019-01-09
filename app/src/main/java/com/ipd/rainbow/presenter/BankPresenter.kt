package com.ipd.rainbow.presenter.store

import com.ipd.rainbow.bean.BankCardBean
import com.ipd.rainbow.bean.BankTypeListBean
import com.ipd.rainbow.bean.BaseResult
import com.ipd.rainbow.model.BasicModel
import com.ipd.rainbow.platform.global.GlobalParam
import com.ipd.rainbow.platform.http.ApiManager
import com.ipd.rainbow.platform.http.Response
import com.ipd.rainbow.presenter.BasePresenter

class BankPresenter : BasePresenter<BankPresenter.IBankView, BasicModel>() {
    override fun initModel() {
        mModel = BasicModel()
    }


    private var bankList: List<BankTypeListBean>? = null
    fun loadBankTypeList() {
        if (bankList != null && bankList!!.isNotEmpty()) {
            mView?.loadBankTypeSuccess(bankList!!)
            return
        }
        mModel?.getNormalRequestData(ApiManager.getService().bankTypeList(GlobalParam.getUserIdOrJump()),
                object : Response<BaseResult<List<BankTypeListBean>>>(mContext, true) {
                    override fun _onNext(result: BaseResult<List<BankTypeListBean>>) {
                        if (result.code == 0) {
                            bankList = result.data
                            mView?.loadBankTypeSuccess(result.data)
                        } else {
                            mView?.loadBankTypeFail(result.msg)
                        }
                    }
                })


    }

    fun getBankCardInfo(bankCardId: String) {
        mModel?.getNormalRequestData(ApiManager.getService().bankCardInfo(GlobalParam.getUserIdOrJump(), bankCardId),
                object : Response<BaseResult<BankCardBean>>(mContext, true) {
                    override fun _onNext(result: BaseResult<BankCardBean>) {
                        if (result.code == 0) {
                            mView?.loadBankCardInfoSuccess(result.data)
                        } else {
                            mView?.loadBankCardInfoFail(result.msg)
                        }
                    }
                })
    }

    fun addBankCard(accountName: String, bankDeposit: String, bankTypeId: String, cardNo: String) {
        mModel?.getNormalRequestData(ApiManager.getService().addBankCard(GlobalParam.getUserIdOrJump(), accountName, bankDeposit, bankTypeId, cardNo),
                object : Response<BaseResult<List<BankTypeListBean>>>(mContext, true) {
                    override fun _onNext(result: BaseResult<List<BankTypeListBean>>) {
                        if (result.code == 0) {
                            mView?.addBankSuccess()
                        } else {
                            mView?.addBankFail(result.msg)
                        }
                    }
                })
    }

    fun changeBankCard(bankCardId: String, accountName: String, bankDeposit: String, bankTypeId: String, cardNo: String) {
        mModel?.getNormalRequestData(ApiManager.getService().changeBankCard(GlobalParam.getUserIdOrJump(), bankCardId, accountName, bankDeposit, bankTypeId, cardNo),
                object : Response<BaseResult<List<BankTypeListBean>>>(mContext, true) {
                    override fun _onNext(result: BaseResult<List<BankTypeListBean>>) {
                        if (result.code == 0) {
                            mView?.changeBankSuccess()
                        } else {
                            mView?.changeBankFail(result.msg)
                        }
                    }
                })
    }


    interface IBankView {
        fun loadBankTypeSuccess(bankList: List<BankTypeListBean>)
        fun loadBankTypeFail(errMsg: String)
        fun loadBankCardInfoSuccess(bankInfo: BankCardBean)
        fun loadBankCardInfoFail(errMsg: String)
        fun addBankSuccess()
        fun addBankFail(errMsg: String)
        fun changeBankSuccess()
        fun changeBankFail(errMsg: String)
    }
}