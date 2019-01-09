package com.ipd.rainbow.ui.fragment.pet

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.ipd.rainbow.R
import com.ipd.rainbow.adapter.MyPetAdapter
import com.ipd.rainbow.bean.BaseResult
import com.ipd.rainbow.bean.PetBean
import com.ipd.rainbow.bean.UserBean
import com.ipd.rainbow.platform.global.Constant
import com.ipd.rainbow.platform.global.GlobalParam
import com.ipd.rainbow.platform.http.ApiManager
import com.ipd.rainbow.platform.http.Response
import com.ipd.rainbow.platform.http.RxScheduler
import com.ipd.rainbow.ui.ListFragment
import com.ipd.rainbow.ui.activity.account.PetStageActivity
import com.ipd.rainbow.ui.activity.pet.AddPetActivity
import rx.Observable
import java.util.*

/**
Created by Miss on 2018/8/19
 */
class MyPetFragment : ListFragment<List<PetBean>, PetBean>() {
    private var mAdapter: MyPetAdapter? = null

    companion object {
        fun newInstance(): MyPetFragment {
            return MyPetFragment()
        }
    }

    override fun getContentLayout(): Int = R.layout.activity_my_pet

    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        progress_layout.setEmptyViewRes(R.layout.layout_empty_pet)
    }

    override fun initListener() {
        super.initListener()
        mRootView?.findViewById<ImageView>(R.id.iv_back)!!.setOnClickListener { mActivity.finish() }
        mRootView?.findViewById<TextView>(R.id.tv_add_pet)!!.setOnClickListener { startActivity() }

        var view: View = progress_layout.getEmptyViewRes(R.layout.layout_empty_pet)
        view.findViewById<TextView>(R.id.tv_add_pet).setOnClickListener { startActivity() }
        view.findViewById<TextView>(R.id.empty_add).setOnClickListener { startActivity() }
        view.findViewById<ImageView>(R.id.iv_back).setOnClickListener { mActivity.finish() }
    }

    override fun loadListData(): Observable<List<PetBean>> {
        return ApiManager.getService().petGetList(Constant.PAGE_SIZE, page, GlobalParam.getUserId())
                .map {
                    val list = ArrayList<PetBean>()
                    if (it.code == 0) {
                        list.addAll(it.data)
                    }
                    list
                }
    }

    override fun isNoMoreData(result: List<PetBean>): Int {
        return if (result == null || result.isEmpty()) {
            if (page == INIT_PAGE) {
                EMPTY_DATA
            } else {
                NO_MORE_DATA
            }
        } else NORMAL
    }

    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = MyPetAdapter(context, data)
            recycler_view?.layoutManager = LinearLayoutManager(context)
            recycler_view?.adapter = mAdapter
        } else {
            mAdapter!!.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: List<PetBean>) {
        data!!.addAll(result)
    }

    fun startActivity() {
        ApiManager.getService().getUserInfo(GlobalParam.getUserIdOrJump())
                .compose(RxScheduler.applyScheduler())
                .subscribe(object : Response<BaseResult<UserBean>>(mActivity, true) {
                    override fun _onNext(result: BaseResult<UserBean>) {
                        if (result.code == 0) {
                            if (result.data.STEP == 0) {
                                PetStageActivity.launch(mActivity, GlobalParam.getUserId())
                            } else {
                                val intent = Intent(mActivity, AddPetActivity::class.java)
                                intent.putExtra("petWay", 1)
                                startActivity(intent)
                            }
                        } else {
                            toastShow(result.msg)
                        }
                    }
                })
    }
}