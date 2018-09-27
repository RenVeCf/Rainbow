package com.ipd.taxiu.ui.fragment.account

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import com.ipd.taxiu.ChoosePetKindEvent
import com.ipd.taxiu.R
import com.ipd.taxiu.adapter.PetKindAdapter
import com.ipd.taxiu.bean.PetInfoBean
import com.ipd.taxiu.platform.http.ApiManager
import com.ipd.taxiu.ui.ListFragment
import com.ipd.taxiu.ui.activity.account.PetKindListActivity
import kotlinx.android.synthetic.main.fragment_index_bar_list.view.*
import org.greenrobot.eventbus.EventBus
import rx.Observable


class PetKindListFragment : ListFragment<List<PetInfoBean>, PetInfoBean>() {

    companion object {
        fun newInstance(type: Int, userId: String): PetKindListFragment {
            val fragment = PetKindListFragment()
            val bundle = Bundle()
            bundle.putInt("type", type)
            bundle.putString("userId", userId)
            fragment.arguments = bundle
            return fragment
        }
    }


    override fun getContentLayout(): Int = R.layout.fragment_index_bar_list
    override fun initView(bundle: Bundle?) {
        swipe_load_layout = mRootView?.findViewById(R.id.swipe_load_layout)!!
        swipe_load_layout.isRefreshEnabled = false
        setLoadMoreEnable(false)
        mContentView.swipe_target.setOverlayStyle_Center()

    }

    private val mType: Int by lazy { arguments.getInt("type", PetKindListActivity.DOG) }
    private val mUserId: String by lazy { arguments.getString("userId", "") }
    override fun loadListData(): Observable<List<PetInfoBean>> {
//        return Observable.create<List<PetInfoBean>> {
//            val list: ArrayList<PetInfoBean> = arrayListOf(
//                    PetInfoBean(R.mipmap.pet_kind1, "阿拉斯加雪橇犬"),
//                    PetInfoBean(R.mipmap.pet_kind1, "爱尔兰犬"),
//                    PetInfoBean(R.mipmap.pet_kind1, "澳大利亚牧羊犬"),
//                    PetInfoBean(R.mipmap.pet_kind1, "阿哥廷杜高犬"),
//                    PetInfoBean(R.mipmap.pet_kind1, "阿拉斯基犬"),
//                    PetInfoBean(R.mipmap.pet_kind2, "比熊犬"),
//                    PetInfoBean(R.mipmap.pet_kind1, "泰迪犬"),
//                    PetInfoBean(R.mipmap.pet_kind1, "自主权"),
//                    PetInfoBean(R.mipmap.pet_kind1, "阿拉斯加雪橇犬"),
//                    PetInfoBean(R.mipmap.pet_kind1, "爱尔兰犬"),
//                    PetInfoBean(R.mipmap.pet_kind1, "澳大利亚牧羊犬"),
//                    PetInfoBean(R.mipmap.pet_kind1, "阿哥廷杜高犬"),
//                    PetInfoBean(R.mipmap.pet_kind2, "比熊犬"),
//                    PetInfoBean(R.mipmap.pet_kind1, "泰迪犬"),
//                    PetInfoBean(R.mipmap.pet_kind1, "自主权"),
//                    PetInfoBean(R.mipmap.pet_kind1, "牧羊犬"),
//                    PetInfoBean(R.mipmap.pet_kind1, "和马犬"),
//                    PetInfoBean(R.mipmap.pet_kind1, "虎犬"),
//                    PetInfoBean(R.mipmap.pet_kind1, "哈士奇")
//            )
//            it.onNext(list)
//            it.onCompleted()
//        }
        return ApiManager.getService().petKindList(mUserId,
                mType.toString()).map {
            val list = ArrayList<PetInfoBean>()
            if (it.code == 0) {
                if (it.data != null) {
                    it.data.forEach { petInfo ->
                        list.addAll(petInfo.list)
                    }
                }
            }
            list
        }
    }

    override fun isNoMoreData(result: List<PetInfoBean>): Int {
        if (result == null || result.isEmpty()) {
            return EMPTY_DATA
        }
        return NORMAL
    }

    private var mAdapter: PetKindAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = PetKindAdapter(mActivity, {
                EventBus.getDefault().post(ChoosePetKindEvent(mType, it))
                mActivity.finish()
            })
            val layoutManager = GridLayoutManager(mActivity, 2)
            mContentView.swipe_target.setLayoutManager(layoutManager)
            mContentView.swipe_target.setAdapter(mAdapter)
            mAdapter?.setDatas(data)
        } else {
            mAdapter?.notifyDataSetChanged()
        }
    }

    override fun addData(isRefersh: Boolean, result: List<PetInfoBean>) {
        data?.addAll(result)
    }


}