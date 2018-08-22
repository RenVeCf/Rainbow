package com.ipd.taxiu.ui.fragment.pet

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.ipd.taxiu.R
import com.ipd.taxiu.adapter.MyPetAdapter
import com.ipd.taxiu.bean.PetBean
import com.ipd.taxiu.platform.global.GlobalParam
import com.ipd.taxiu.platform.http.ApiManager
import com.ipd.taxiu.ui.ListFragment
import com.ipd.taxiu.ui.activity.pet.AddPetActivity
import kotlinx.android.synthetic.main.header_my_pet.*
import org.w3c.dom.Text
import rx.Observable
import java.util.ArrayList

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
        mRootView?.findViewById<ImageView>(R.id.iv_back)!!.setOnClickListener { mActivity.finish()}
        mRootView?.findViewById<TextView>(R.id.tv_add_pet)!!.setOnClickListener { startActivity() }

        var view:View = progress_layout.getEmptyViewRes(R.layout.layout_empty_pet)
        view.findViewById<TextView>(R.id.tv_add_pet).setOnClickListener { startActivity() }
        view.findViewById<TextView>(R.id.empty_add).setOnClickListener { startActivity() }
        view.findViewById<ImageView>(R.id.iv_back).setOnClickListener { mActivity.finish() }
    }

    override fun loadListData(): Observable<List<PetBean>> {
        return ApiManager.getService().petGetList(10, page, GlobalParam.getUserId())
                .map {
                    val list = ArrayList<PetBean>()
                    if (it.code == 0) {
                        list.addAll(it.data)
                    }
                    list
                }
    }

    override fun isNoMoreData(result: List<PetBean>):Int{
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
        val intent = Intent(mActivity, AddPetActivity::class.java)
        intent.putExtra("petWay",1)
        startActivity(intent)
    }
}