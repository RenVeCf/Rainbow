package com.ipd.taxiu.ui.fragment.balance

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.ipd.taxiu.R
import com.ipd.taxiu.adapter.BankCardAdapter
import com.ipd.taxiu.bean.BankCardBean
import com.ipd.taxiu.bean.BaseResult
import com.ipd.taxiu.event.UpdateBankCardEvent
import com.ipd.taxiu.platform.global.Constant
import com.ipd.taxiu.platform.global.GlobalParam
import com.ipd.taxiu.platform.http.ApiManager
import com.ipd.taxiu.ui.ListFragment
import com.ipd.taxiu.ui.activity.balance.AddBankCardActivity
import kotlinx.android.synthetic.main.layout_bank_card.view.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import rx.Observable

/**
 * Created by Miss on 2018/8/6
 */
class BankCardFragment : ListFragment<BaseResult<List<BankCardBean>>, BankCardBean>(), View.OnClickListener {

    companion object {
        fun newInstance(bankType: Int): BankCardFragment {
            val fragment = BankCardFragment()
            val bundle = Bundle()
            bundle.putInt("bankType", bankType)
            fragment.arguments = bundle
            return fragment
        }
    }

    private var mAdapter: BankCardAdapter? = null

    override fun getContentLayout(): Int {
        return R.layout.layout_bank_card
    }

    override fun onViewAttach() {
        super.onViewAttach()
        EventBus.getDefault().register(this)
    }

    override fun onViewDetach() {
        super.onViewDetach()
        EventBus.getDefault().unregister(this)
    }

    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        progress_layout.setEmptyViewRes(R.layout.layout_empty_bank)
    }

    override fun loadListData(): Observable<BaseResult<List<BankCardBean>>> {
        return ApiManager.getService().bankCardList(GlobalParam.getUserIdOrJump(), Constant.PAGE_SIZE, page)
    }


    override fun isNoMoreData(result: BaseResult<List<BankCardBean>>): Int {
        if (page == INIT_PAGE && (result.data == null || result.data.isEmpty())) {
            return EMPTY_DATA
        } else if (result.data == null || result.data.isEmpty()) {
            return NO_MORE_DATA
        }
        return NORMAL
    }

    override fun setOrNotifyAdapter() {
        val args = arguments
        val bankType = args.getInt("bankType")
        if (mAdapter == null) {
            mAdapter = BankCardAdapter(context, data, bankType)
            recycler_view.layoutManager = LinearLayoutManager(context)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: BaseResult<List<BankCardBean>>) {
        data?.addAll(result?.data ?: arrayListOf())
    }

    override fun initListener() {
        super.initListener()
        progress_layout.btn_add_card.setOnClickListener(this)
        mContentView.btn_add_card.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_add_card -> AddBankCardActivity.launch(mActivity, 1)
        }
    }

    @Subscribe
    fun onMainEvent(event: UpdateBankCardEvent) {
        isCreate = true
        onRefresh()
    }
}
