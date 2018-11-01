package com.ipd.taxiu.ui.activity.pet

import android.os.Bundle
import com.ipd.taxiu.R
import com.ipd.taxiu.adapter.PetBibleAdapter
import com.ipd.taxiu.bean.BaseResult
import com.ipd.taxiu.bean.PetBibleBean
import com.ipd.taxiu.platform.global.GlobalParam
import com.ipd.taxiu.platform.http.ApiManager
import com.ipd.taxiu.platform.http.Response
import com.ipd.taxiu.platform.http.RxScheduler
import com.ipd.taxiu.ui.BaseUIActivity
import com.ipd.taxiu.ui.activity.web.WebActivity
import kotlinx.android.synthetic.main.activity_pet_bible.*

/**
 * Created by Miss on 2018/7/26
 */
class PetBibleActivity : BaseUIActivity() {
    override fun getToolbarTitle() = "宠物宝典"

    override fun getContentLayout() = R.layout.activity_pet_bible

    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
        showProgress()
        ApiManager.getService().petBible(GlobalParam.getUserId())
                .compose(RxScheduler.applyScheduler())
                .subscribe(object : Response<BaseResult<List<PetBibleBean>>>() {
                    override fun _onNext(result: BaseResult<List<PetBibleBean>>) {
                        if (result.code == 0) {
                            bible_recycler_view.adapter = PetBibleAdapter(mActivity, result.data) {
                                WebActivity.launch(mActivity, WebActivity.URL, it.URL, it.NAME)
                            }
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

    override fun initListener() {

    }


}
