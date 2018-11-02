package com.ipd.taxiu.ui.fragment.trade

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.ipd.taxiu.R
import com.ipd.taxiu.adapter.CartCouponAdapter
import com.ipd.taxiu.bean.BaseResult
import com.ipd.taxiu.bean.ExchangeBean
import com.ipd.taxiu.event.ChooseCouponEvent
import com.ipd.taxiu.platform.global.GlobalParam
import com.ipd.taxiu.platform.http.ApiManager
import com.ipd.taxiu.ui.ListFragment
import org.greenrobot.eventbus.EventBus
import rx.Observable

class CartCouponFragment : ListFragment<BaseResult<List<ExchangeBean>>, ExchangeBean>() {
    companion object {
        fun newInstance(cartIds: String, isCart: Int, num: Int, productId: Int, formId: Int): CartCouponFragment {
            val fragment = CartCouponFragment()
            val bundle = Bundle()
            bundle.putString("cartIds", cartIds)
            bundle.putInt("isCart", isCart)
            bundle.putInt("num", num)
            bundle.putInt("productId", productId)
            bundle.putInt("formId", formId)
            fragment.arguments = bundle
            return fragment
        }
    }


    private val mCartIds: String by lazy { arguments.getString("cartIds") ?: "" }
    private val mIsCart: Int by lazy { arguments.getInt("isCart", 0) }
    private val mProductId: Int by lazy { arguments.getInt("productId", 0) }
    private val mFormId: Int by lazy { arguments.getInt("formId", 0) }
    private val mNum: Int by lazy { arguments.getInt("num", 0) }
    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        progress_layout.setEmptyViewRes(R.layout.layout_empty_coupon)
    }

    override fun loadListData(): Observable<BaseResult<List<ExchangeBean>>> {
        return ApiManager.getService().cartCoupon(GlobalParam.getUserIdOrJump(), mCartIds, mIsCart, mNum, mProductId, mFormId)
    }

    override fun isNoMoreData(result: BaseResult<List<ExchangeBean>>): Int {
        if (page == INIT_PAGE && (result.data == null || result.data.isEmpty())) {
            return EMPTY_DATA
        } else if (result.data == null || result.data.isEmpty()) {
            return NO_MORE_DATA
        }
        return NORMAL
    }

    private var mAdapter: CartCouponAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = CartCouponAdapter(mActivity, data) {
                //选择优惠券
                EventBus.getDefault().post(ChooseCouponEvent(it))
                mActivity.finish()
            }
            recycler_view.layoutManager = LinearLayoutManager(mActivity)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefresh: Boolean, result: BaseResult<List<ExchangeBean>>) {
        data?.addAll(result?.data ?: arrayListOf())
    }

}