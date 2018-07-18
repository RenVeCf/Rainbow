package com.ipd.taxiu.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.taxiu.ui.BaseUIFragment

/**
 * Created by jumpbox on 2017/5/24.
 */
abstract class LazyLoadFragment : BaseUIFragment() {

    private var firstLoad = true
    private var userVisiable = false
    private var isViewCreated = false

    /**
     * 在ViewPager中使用懒加载必须设置为true，否则在onCreate和setUserVisibleHint中都会调用加载数据方法
     * @param needLazyLoad
     */
    var needLazyLoad = false

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        isViewCreated = true
        super.onViewCreated(view, savedInstanceState)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        userVisiable = isVisibleToUser
        //View绘制完成但未加载过数据执行数据加载
        if (userVisiable && firstLoad && isViewCreated) {
            prepareLoadData()
        }
    }


    override fun loadData() {
        if (needLazyLoad) {
            if (userVisiable && firstLoad) {
                prepareLoadData()
            }
        } else {
            prepareLoadData()
        }
    }

    private fun prepareLoadData() {
        firstLoad = false
        loadDataWhenVisible()
    }

    abstract fun loadDataWhenVisible()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mRootView == null) {
            firstLoad = true
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
//        mRootView = null//如果需要缓存每个fragment注释该行
    }

}