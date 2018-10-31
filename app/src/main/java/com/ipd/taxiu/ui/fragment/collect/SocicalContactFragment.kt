package com.ipd.taxiu.ui.fragment.collect

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.ipd.taxiu.R
import com.ipd.taxiu.adapter.ContactAdapter
import com.ipd.taxiu.bean.AttentionBean
import com.ipd.taxiu.platform.global.Constant
import com.ipd.taxiu.platform.global.GlobalParam
import com.ipd.taxiu.platform.http.ApiManager
import com.ipd.taxiu.presenter.MinePresenter
import com.ipd.taxiu.ui.ListFragment
import rx.Observable
import java.util.*

/**
 * Created by Miss on 2018/7/19
 * 社交
 */
class SocicalContactFragment : ListFragment<List<AttentionBean>, AttentionBean>(), MinePresenter.IAttentionView {

    companion object {
        fun newInstance(TYPE: Int): SocicalContactFragment {
            val fragment = SocicalContactFragment()
            val bundle = Bundle()
            bundle.putInt("TYPE", TYPE)
            fragment.arguments = bundle
            return fragment
        }
    }


    private var mPresenter: MinePresenter<MinePresenter.IAttentionView>? = null
    override fun onViewAttach() {
        super.onViewAttach()
        mPresenter = MinePresenter()
        mPresenter?.attachView(mActivity, this)
    }


    override fun onViewDetach() {
        super.onViewDetach()
        mPresenter?.detachView()
        mPresenter = null
    }

    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        progress_layout.setEmptyViewRes(R.layout.layout_empty_contact)
    }

    private val mType by lazy { arguments.getInt("TYPE") }
    override fun loadListData(): Observable<List<AttentionBean>> {
        return ApiManager.getService().attentionList(Constant.PAGE_SIZE, GlobalParam.getUserId(), page, mType + 1)
                .map { listBaseResult ->
                    val fans = ArrayList<AttentionBean>()
                    if (listBaseResult.code == 0) {
                        fans.addAll(listBaseResult.data)
                    }
                    fans
                }
    }

    override fun onResume() {
        super.onResume()
        onRefresh()
    }

    override fun isNoMoreData(result: List<AttentionBean>): Int {
        if (page == INIT_PAGE && (result == null || result.isEmpty())) {
            return EMPTY_DATA
        } else if (result == null || result.isEmpty()) {
            return NO_MORE_DATA
        }
        return NORMAL
    }

    private var clickItemPos = -1
    private var mAdapter: ContactAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = ContactAdapter(mActivity, data, { position, info ->
                clickItemPos = position
                mPresenter?.attention(info.USER_ID)
            })
            recycler_view.layoutManager = LinearLayoutManager(context)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: List<AttentionBean>) {
        data?.addAll(result)
    }

    override fun onSuccess(msg: String, isAttent: Int) {
        toastShow(true,msg)
        if (clickItemPos == -1) return
        if (mType == 0){
            data?.removeAt(clickItemPos)
            if (data == null || data!!.isEmpty()){
                progress_layout.showEmpty()
            }
            mAdapter?.notifyItemRemoved(clickItemPos)

        }else{
            data?.get(clickItemPos)?.IS_ATTEN = isAttent
            mAdapter?.notifyItemChanged(clickItemPos)
        }
    }

    override fun onFail(errMsg: String) {
        toastShow(errMsg)
    }
}
