package com.ipd.rainbow.ui.activity.store

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import com.ipd.jumpbox.jumpboxlibrary.utils.LogUtils
import com.ipd.rainbow.R
import com.ipd.rainbow.bean.ProductExpertScreenBean
import com.ipd.rainbow.bean.ScreenResult
import com.ipd.rainbow.platform.global.GlobalParam
import com.ipd.rainbow.platform.http.ApiManager
import com.ipd.rainbow.platform.http.Response
import com.ipd.rainbow.platform.http.RxScheduler
import com.ipd.rainbow.ui.BaseActivity
import com.ipd.rainbow.ui.fragment.store.ProductListFragment
import com.ipd.rainbow.utils.ProductScreenView
import com.ipd.rainbow.widget.ProductExpertScreenLayout
import com.ipd.rainbow.widget.ScreenLayout
import kotlinx.android.synthetic.main.activity_product_list.*
import kotlinx.android.synthetic.main.drawer_product_list.*
import kotlinx.android.synthetic.main.product_list_toolbar.*

class ProductListActivity : BaseActivity(), ProductScreenView {

    companion object {
        fun launch(activity: Activity, searchKey: String = "", typeId: Int = 0, typeTitle: String = "") {
            val intent = Intent(activity, ProductListActivity::class.java)
            intent.putExtra("searchKey", searchKey)
            intent.putExtra("typeId", typeId)
            intent.putExtra("typeTitle", typeTitle)
            activity.startActivity(intent)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        LogUtils.e("tag", intent.toString())
        mSearchKey = intent?.getStringExtra("searchKey") ?: ""
        mTypeId = intent?.getIntExtra("typeId", 0) ?: 0
        mTypeTitle = intent?.getStringExtra("typeTitle") ?: ""
        onReset()
    }

    override fun getBaseLayout(): Int = R.layout.activity_product_list

    var mSearchKey: String = ""
    var mTypeId: Int = 0
    var mTypeTitle: String = ""
    var screenLayout: ScreenLayout? = null
    private var mScreenResult: ScreenResult? = null
    //保存已勾选的筛选条件
    private val mScreenMap: HashMap<String, List<ProductExpertScreenBean>> = hashMapOf()

    /**
     * 重置筛选条件
     */
    private fun onReset() {
        setHeader()
        mScreenResult = null

        mScreenMap.clear()
        cl_screen.removeAllViews()

        drawer_layout.closeDrawer(Gravity.END)
        screenLayout?.onCancelExpertSort()
    }


    private fun setHeader() {
        if (mTypeId <= 0) {
            ll_search.visibility = View.VISIBLE
            tv_title.visibility = View.GONE
            et_search.text = mSearchKey
        } else {
            ll_search.visibility = View.GONE
            tv_title.visibility = View.VISIBLE
            tv_title.text = mTypeTitle
        }
    }

    override fun initView(bundle: Bundle?) {
        mSearchKey = intent.getStringExtra("searchKey")
        mTypeId = intent.getIntExtra("typeId", 0)
        mTypeTitle = intent.getStringExtra("typeTitle")
        setHeader()

        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

        screenLayout = findViewById(R.id.screen_layout_container)
        screenLayout?.setBackgroupView(fl_container)

    }

    private lateinit var mFragment: ProductListFragment
    override fun loadData() {

        mFragment = ProductListFragment.newInstance()
        mFragment.setProductScreenView(this)
        supportFragmentManager.beginTransaction().replace(R.id.fl_container, mFragment).commit()
    }

    private val mInflater: LayoutInflater by lazy { LayoutInflater.from(mActivity) }
    override fun initListener() {

        iv_back.setOnClickListener { finish() }
//        iv_show_type.setOnClickListener {
//
//            val curShowType = mFragment.setShowType()
//            iv_show_type.setImageResource(if (curShowType == ProductAdapter.ItemType.LIST) R.mipmap.product_list_list else R.mipmap.product_list_grid)
//        }

        screenLayout?.setSortTypeChangeListener(object : ScreenLayout.OnSortTypeChangeListener {
            override fun onChange(sortType: ScreenLayout.ScreenType) {
                mFragment?.refreshWithProgress()
            }
        })

        screenLayout?.setExpertScreenClickListener {
            //高级筛选
//            val memory = Observable.create<ScreenResult> {
//                it.onNext(mScreenResult)
//                it.onCompleted()
//            }.map {
//                LogUtils.e("tag", "from memory")
//                it
//            }
//
//            val network = ApiManager.getService().storeProductExpertScreen(GlobalParam.getUserIdOrJump(), mSearchKey)
//                    .compose(RxScheduler.applyScheduler())
//                    .map {
//                        LogUtils.e("tag", "from network")
//                        it
//                    }

            if (mScreenResult == null) {
                ApiManager.getService().storeProductExpertScreen(GlobalParam.getUserIdOrJump(), mTypeId)
                        .compose(RxScheduler.applyScheduler())
                        .subscribe(object : Response<ScreenResult>(mActivity, true) {
                            override fun _onNext(result: ScreenResult) {
                                if (result.code == 0) {
                                    mScreenResult = result

                                    //其他条件
                                    val storeScreenProductList = result.data
                                    storeScreenProductList.forEach {
                                        val productExpertScreenLayout = mInflater.inflate(R.layout.item_product_expert_screen, cl_screen, false) as ProductExpertScreenLayout
                                        productExpertScreenLayout.tag = it.TITLE
                                        productExpertScreenLayout.setData(it)
                                        cl_screen.addView(productExpertScreenLayout)
                                    }

                                    tv_screen_confirm.setOnClickListener {
                                        //确定筛选
                                        for (index in 0..cl_screen.childCount) {
                                            if (cl_screen.getChildAt(index) !is ProductExpertScreenLayout) {
                                                continue
                                            }
                                            val productExpertScreenLayout = cl_screen.getChildAt(index) as ProductExpertScreenLayout
                                            val screenInfo = productExpertScreenLayout.getCheckedScreenInfo()
                                            if (screenInfo != null && screenInfo.isNotEmpty()) {
                                                //保存到map中(key:筛选分类名称,value:选中的子分类信息)
                                                mScreenMap[productExpertScreenLayout.tag.toString()] = screenInfo
                                            } else {
                                                mScreenMap.remove(productExpertScreenLayout.tag.toString())
                                            }
                                        }
                                        LogUtils.e("tag", "mScreenMap:$mScreenMap")
                                        //切换图标及刷新页面
                                        screenLayout?.onExpertSort()
                                        drawer_layout.closeDrawer(Gravity.END)
                                    }
                                    tv_screen_reset.setOnClickListener {
                                        //重置
                                        onReset()
                                    }
                                    drawer_layout.openDrawer(Gravity.END)
                                } else {
                                    toastShow(result.msg)
                                }
                            }
                        })
            } else {
                drawer_layout.openDrawer(Gravity.END)
            }
        }

//        val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        et_search.setOnEditorActionListener { v, actionId, event ->
//            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//                //搜索
//                val searchKey = et_search.text.toString().trim()
//                mSearchKey = searchKey
//                mFragment.onSearch(searchKey)
//
//                manager.hideSoftInputFromWindow(currentFocus.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
//                return@setOnEditorActionListener true
//            }
//            return@setOnEditorActionListener false
//        }
        et_search.setOnClickListener {
            StoreSearchActivity.launch(mActivity)
        }

    }

    override fun getCompositeValue(): Int = screenLayout?.getCompositeValue() ?: 0
    override fun getSaleValue(): Int = screenLayout?.getSaleValue() ?: 0
    override fun getPriceValue(): Int = screenLayout?.getPriceValue() ?: 0

    override fun getBrandValue(): String = getScreenValueIds(mScreenMap["品牌"])
    override fun getCategoryValue(): String = getScreenValueIds(mScreenMap["分类"])
    override fun getCountryValue(): String = getScreenValueIds(mScreenMap["国家"])

    private fun getScreenValueIds(list: List<ProductExpertScreenBean>?): String {
        var ids = ""
        list?.forEachIndexed { index, info ->
            ids += info.COMMON_ID
            if (index < list.size - 1) ids += ","
        }
        return ids
    }
}