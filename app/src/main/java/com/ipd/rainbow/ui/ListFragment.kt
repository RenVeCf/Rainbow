package com.ipd.rainbow.ui

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.jumpbox.jumpboxlibrary.widget.swipetoloadlayout.OnLoadMoreListener
import com.ipd.jumpbox.jumpboxlibrary.widget.swipetoloadlayout.OnRefreshListener
import com.ipd.jumpbox.jumpboxlibrary.widget.swipetoloadlayout.SwipeToLoadLayout
import com.ipd.rainbow.R
import com.ipd.rainbow.bean.BaseResult
import com.ipd.rainbow.presenter.ListPresenter
import rx.Observable
import java.util.*

/**
 * Created by jumpbox on 2017/5/24.
 */
abstract class ListFragment<T, E> : LazyLoadFragment(), OnRefreshListener, OnLoadMoreListener, ListPresenter.IListView<T> {

    val EMPTY_DATA = 1
    val NO_MORE_DATA = 2
    val NORMAL = 0
    val INIT_PAGE = 0

    protected lateinit var recycler_view: RecyclerView
    protected lateinit var swipe_load_layout: SwipeToLoadLayout
    protected var isCreate = true
    protected var data: ArrayList<E>? = null
    protected var page = INIT_PAGE

    override fun getTitleLayout(): Int = -1

    override fun getContentLayout(): Int = R.layout.fragment_list

    private var mPresenter: ListPresenter<T>? = null

    override fun onViewAttach() {
        mPresenter = ListPresenter()
        mPresenter?.attachView(mActivity, this)
    }

    override fun onViewDetach() {
        mPresenter?.detachView()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mRootView == null) {
            //初始化参数
            isCreate = true
            page = INIT_PAGE
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun initView(bundle: Bundle?) {
        recycler_view = mRootView?.findViewById(R.id.swipe_target)!!
        swipe_load_layout = mRootView?.findViewById(R.id.swipe_load_layout)!!
    }


    override fun initListener() {
        swipe_load_layout.setOnRefreshListener(this)
        swipe_load_layout.setOnLoadMoreListener(this)
    }


    override fun loadDataWhenVisible() {
        getListData(true)
    }


    /**
     * 是否显示加载进度页面
     */
    override fun checkNeedShowProgress() {
        if (isCreate || getProgressLayout().isError() || getProgressLayout().isEmpty()) {
            showProgress()
        }
    }

    /**
     * 获取数据接口

     * @param isRefresh
     */
    open fun getListData(isRefresh: Boolean) {
        mPresenter?.loadListData(loadListData(), isRefresh, isCreate)
    }


    /**
     * 调取数据接口
     * @return
     */
    abstract fun loadListData(): Observable<T>


    /**
     * 是否没有更多数据了
     * EMPTY_DATA = 1,//空数据
     * NO_MORE_DATA = 2,//没有更多数据
     * NORMAL = 0//正常

     * @param result
     * *
     * @return
     */
    abstract fun isNoMoreData(result: T): Int

    abstract fun setOrNotifyAdapter()

    /**
     * 添加数据

     * @param result
     */
    abstract fun addData(isRefresh: Boolean, result: T)

    /**
     * 刷新
     */
    override fun onRefresh() {
        page = INIT_PAGE
        getListData(true)
    }

    fun onRefresh(isCreate: Boolean) {
        this.isCreate = isCreate
        onRefresh()
    }

    /**
     * 加载更多
     */
    override fun onLoadMore() {
        getListData(false)
    }


    /**
     * 重置数据
     */
    fun resetData() {
        page = INIT_PAGE
        if (data != null) data?.clear()
    }

    /**
     * 获取数据是否成功

     * @param result
     * *
     * @return
     */
    override fun getDataSuccess(result: T): String? {
        if (result is BaseResult<*>) {
            val response = result
            if (response.code != 0 && response.code != 10000) {
                return response.msg
            }
        } else {
            return ""
        }
        return ""

    }

    /**
     * 加载数据成功

     * @param isRefresh
     * *
     * @param result
     */
    override fun loadListDataSuccess(isRefresh: Boolean, result: T) {
        if (data == null) data = ArrayList()
        val isNoMoreResult = isNoMoreData(result)
        if (isNoMoreResult == EMPTY_DATA) {
            onDataEmpty()
        } else if (swipe_load_layout.isLoadingMore && isNoMoreResult == NO_MORE_DATA) {
            //当前处于下拉加载更多状态，并且没有更多数据
            swipe_load_layout.isLoadingMore = false
            swipe_load_layout.isLoadMoreEnabled = false
            toastShow(getString(R.string.NO_MORE_DATA))
        } else {
            showContent()
            if (isCreate) {
                isCreate = false
            }
            if (isRefresh) {
                resetData()
            }

            page += 1
            addData(isRefresh, result)
            setOrNotifyAdapter()

            if (isRefresh) {
                if (isNoMoreResult == NORMAL && getLoadMoreEnable()) {
                    swipe_load_layout.isLoadMoreEnabled = true
                }
            }
        }

    }

    /**
     * 加载数据失败

     * @param msg
     */
    override fun firstLoadListDataError(isRrefresh: Boolean, msg: String) {
        showError(msg, View.OnClickListener { getListData(isRrefresh) })
    }

    /**
     * 加载数据失败
     */
    override fun loadListDataError(errMsg: String) {
        toastShow(errMsg)
    }

    /**
     * 加载数据完成
     */
    override fun loadListDataComplete() {
        swipe_load_layout.isRefreshing = false
        swipe_load_layout.isLoadingMore = false
    }


    /**
     * 没有数据
     */
    open fun onDataEmpty() {
        getProgressLayout().showEmpty()
//        firstLoadListDataError(true, getString(R.string.EMPTY_DATA))
    }


    private var mIsOpenLoadMore = true//是否开启加载更多

    fun setLoadMoreEnable(loadMoreEnable: Boolean) {
        this.mIsOpenLoadMore = loadMoreEnable
        swipe_load_layout.isLoadMoreEnabled = loadMoreEnable
    }

    //是否开启加载更多
    fun getLoadMoreEnable(): Boolean = mIsOpenLoadMore
}