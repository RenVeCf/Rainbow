package com.ipd.taxiu.ui.fragment.account

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import com.ipd.taxiu.ChoosePetKindEvent
import com.ipd.taxiu.R
import com.ipd.taxiu.adapter.PetKindAdapter
import com.ipd.taxiu.bean.PetInfoBean
import com.ipd.taxiu.ui.ListFragment
import com.ipd.taxiu.ui.activity.account.PetKindListActivity
import kotlinx.android.synthetic.main.fragment_index_bar_list.view.*
import org.greenrobot.eventbus.EventBus
import rx.Observable


class PetKindListFragment : ListFragment<List<PetInfoBean>, PetInfoBean>() {

    companion object {
        fun newInstance(type: Int): PetKindListFragment {
            val fragment = PetKindListFragment()
            val bundle = Bundle()
            bundle.putInt("type", type)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getContentLayout(): Int = R.layout.fragment_index_bar_list
    override fun initView(bundle: Bundle?) {
        super.initView(bundle)
        setLoadMoreEnable(false)
        mContentView.index_bar.setNeedRealIndex(true)
    }

    private val mType: Int by lazy { arguments.getInt("type", PetKindListActivity.DOG) }
    override fun loadListData(): Observable<List<PetInfoBean>> {
        return Observable.create<List<PetInfoBean>> {
            val list: ArrayList<PetInfoBean> = arrayListOf(
                    PetInfoBean(R.mipmap.pet_kind1, "阿拉斯加雪橇犬"),
                    PetInfoBean(R.mipmap.pet_kind1, "爱尔兰犬"),
                    PetInfoBean(R.mipmap.pet_kind1, "澳大利亚牧羊犬"),
                    PetInfoBean(R.mipmap.pet_kind1, "阿哥廷杜高犬"),
                    PetInfoBean(R.mipmap.pet_kind2, "比熊犬"),
                    PetInfoBean(R.mipmap.pet_kind1, "泰迪犬"),
                    PetInfoBean(R.mipmap.pet_kind1, "自主权"),
                    PetInfoBean(R.mipmap.pet_kind1, "阿拉斯加雪橇犬"),
                    PetInfoBean(R.mipmap.pet_kind1, "爱尔兰犬"),
                    PetInfoBean(R.mipmap.pet_kind1, "澳大利亚牧羊犬"),
                    PetInfoBean(R.mipmap.pet_kind1, "阿哥廷杜高犬"),
                    PetInfoBean(R.mipmap.pet_kind2, "比熊犬"),
                    PetInfoBean(R.mipmap.pet_kind1, "泰迪犬"),
                    PetInfoBean(R.mipmap.pet_kind1, "自主权"),
                    PetInfoBean(R.mipmap.pet_kind1, "牧羊犬")
            )
            it.onNext(list)
            it.onCompleted()
        }
    }

    override fun isNoMoreData(result: List<PetInfoBean>): Int {
        if (result == null || result.isEmpty()) {
            return NO_MORE_DATA
        }
        return NORMAL
    }

    private var mAdapter: PetKindAdapter? = null
    override fun setOrNotifyAdapter() {
        if (mAdapter == null) {
            mAdapter = PetKindAdapter(mActivity, data, {
                EventBus.getDefault().post(ChoosePetKindEvent(mType, it))
                mActivity.finish()
            })
            val layoutManager = GridLayoutManager(mActivity, 2)
            recycler_view.layoutManager = layoutManager
            mContentView.index_bar.setmLayoutManager(layoutManager)
            recycler_view.adapter = mAdapter
        } else {
            mAdapter?.notifyDataSetChanged()
        }

        mContentView.index_bar.dataHelper.sortSourceDatas(data)
        mContentView.index_bar.setmSourceDatas(data)//设置数据
                .invalidate()
    }

    override fun addData(isRefersh: Boolean, result: List<PetInfoBean>) {
        data?.addAll(result)
    }


}