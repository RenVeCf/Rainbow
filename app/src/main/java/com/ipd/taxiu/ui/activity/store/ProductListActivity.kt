package com.ipd.taxiu.ui.activity.store

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import com.ipd.jumpbox.jumpboxlibrary.utils.LogUtils
import com.ipd.taxiu.R
import com.ipd.taxiu.adapter.ProductAdapter
import com.ipd.taxiu.bean.ProductExpertScreenBean
import com.ipd.taxiu.bean.ScreenResult
import com.ipd.taxiu.platform.global.GlobalParam
import com.ipd.taxiu.platform.http.ApiManager
import com.ipd.taxiu.platform.http.Response
import com.ipd.taxiu.platform.http.RxScheduler
import com.ipd.taxiu.ui.BaseActivity
import com.ipd.taxiu.ui.fragment.store.ProductListFragment
import com.ipd.taxiu.utils.ProductScreenUtil
import com.ipd.taxiu.utils.ProductScreenView
import com.ipd.taxiu.widget.ProductExpertScreenLayout
import com.ipd.taxiu.widget.ScreenLayout
import kotlinx.android.synthetic.main.activity_product_list.*
import kotlinx.android.synthetic.main.drawer_product_list.*
import kotlinx.android.synthetic.main.product_list_toolbar.*

class ProductListActivity : BaseActivity(), ProductScreenView {

    companion object {
        fun launch(activity: Activity, searchKey: String = "", areaTypeId: Int = 0, shopTypeId: Int = 0) {
            val intent = Intent(activity, ProductListActivity::class.java)
            intent.putExtra("searchKey", searchKey)
            intent.putExtra("areaTypeId", areaTypeId)
            intent.putExtra("shopTypeId", shopTypeId)
            activity.startActivity(intent)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        LogUtils.e("tag", intent.toString())
        mSearchKey = intent?.getStringExtra("searchKey") ?: ""
        mAreaTypeId = intent?.getIntExtra("areaTypeId", 0) ?: 0
        mShopTypeId = intent?.getIntExtra("shopTypeId", 0) ?: 0
        onReset()
    }

    override fun getBaseLayout(): Int = R.layout.activity_product_list

    var mSearchKey: String = ""
    var mAreaTypeId: Int = 0
    var mShopTypeId: Int = 0
    var screenLayout: ScreenLayout? = null
    private var mScreenResult: ScreenResult? = null
    private var mMinPrice = 0f
    private var mMaxPrice = 0f
    //保存已勾选的筛选条件
    private val mScreenMap: HashMap<String, ProductExpertScreenBean.ScreenInfo> = hashMapOf()

    /**
     * 重置筛选条件
     */
    private fun onReset() {
        et_search.text = mSearchKey
        mScreenResult = null
        price_flow_layout.clearCheck()
        price_flow_layout.removeAllViews()
        et_min_price.setText("")
        et_max_price.setText("")

        mMinPrice = 0f
        mMaxPrice = 0f
        mScreenMap.clear()
        if (cl_screen.childCount > 1) {
            cl_screen.removeViews(1, cl_screen.childCount - 1)
        }

        drawer_layout.closeDrawer(Gravity.END)
        screenLayout?.onCancelExpertSort()
    }

    override fun initView(bundle: Bundle?) {
        mSearchKey = intent.getStringExtra("searchKey")
        mAreaTypeId = intent.getIntExtra("areaTypeId", 0)
        mShopTypeId = intent.getIntExtra("shopTypeId", 0)
        et_search.text = mSearchKey

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
        iv_show_type.setOnClickListener {
            val curShowType = mFragment.switchShowType()
            iv_show_type.setImageResource(if (curShowType == ProductAdapter.ItemType.LIST) R.mipmap.product_list_list else R.mipmap.product_list_grid)
        }

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
                ApiManager.getService().storeProductExpertScreen(GlobalParam.getUserIdOrJump(), mSearchKey, mAreaTypeId, mShopTypeId)
                        .compose(RxScheduler.applyScheduler())
                        .subscribe(object : Response<ScreenResult>(mActivity, true) {
                            override fun _onNext(result: ScreenResult) {
                                if (result.code == 0) {
                                    mScreenResult = result
                                    //price
                                    price_flow_layout.setCheckedChangeListener {
                                        if (it == null) {
                                            et_min_price.setText("")
                                            et_max_price.setText("")
                                        } else {
                                            et_min_price.setText(it.MIN_PRICE)
                                            et_max_price.setText(it.MAX_PRICE)
                                        }
                                    }

                                    if (result.data.PRICE_DATA != null) {
                                        result.data.PRICE_DATA.LIST.forEach {
                                            price_flow_layout.addView(it, true)
                                        }
                                    }

                                    //其他条件
                                    val storeScreenProductList = ProductScreenUtil.getStoreScreenProductList(result.data)
                                    storeScreenProductList.forEach {
                                        val productExpertScreenLayout = mInflater.inflate(R.layout.item_product_expert_screen, cl_screen, false) as ProductExpertScreenLayout
                                        productExpertScreenLayout.tag = it.TITLE
                                        productExpertScreenLayout.setData(it)
                                        cl_screen.addView(productExpertScreenLayout)
                                    }

                                    tv_screen_confirm.setOnClickListener {
                                        //确定
                                        var minPrice = et_min_price.text.toString().trim()
                                        var maxPrice = et_max_price.text.toString().trim()

//                                        val pos = price_flow_layout.getCheckedScreenInfo()
//                                        if (pos != -1) {
//                                            val screenInfo = result.data.PRICE_DATA.LIST[pos]
//                                            minPrice = screenInfo.MIN_PRICE
//                                            maxPrice = screenInfo.MAX_PRICE
//                                        }

                                        mMinPrice = if (!TextUtils.isEmpty(minPrice)) minPrice.toFloat() else 0f
                                        mMaxPrice = if (!TextUtils.isEmpty(maxPrice)) maxPrice.toFloat() else 0f


                                        for (index in 1..cl_screen.childCount) {
                                            if (cl_screen.getChildAt(index) !is ProductExpertScreenLayout) {
                                                continue
                                            }
                                            val productExpertScreenLayout = cl_screen.getChildAt(index) as ProductExpertScreenLayout
                                            val screenInfo = productExpertScreenLayout.getCheckedScreenInfo()
                                            if (screenInfo != null) {
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
    override fun getMinPrice(): Float = mMinPrice
    override fun getMaxPrice(): Float = mMaxPrice
    override fun getBrandValue(): String = (mScreenMap["品牌"]?.MODULE_ID ?: "").toString()
    override fun getApplyValue(): String = (mScreenMap["适用阶段"]?.MODULE_ID ?: "").toString()
    override fun getSizeValue(): String = (mScreenMap["宠物体型"]?.MODULE_ID ?: "").toString()
    override fun getPetTypeValue(): String = (mScreenMap["宠物品种"]?.MODULE_ID ?: "").toString()
    override fun getNetContentValue(): String = (mScreenMap["净含量"]?.MODULE_ID ?: "").toString()
    override fun getTasteValue(): String = (mScreenMap["口味"]?.MODULE_ID ?: "").toString()
    override fun getCountryValue(): String = (mScreenMap["国家"]?.MODULE_ID ?: "").toString()
    //    override fun getThingTypeValue(): String = mScreenResult?.type ?: ""
    override fun getThingTypeValue(): String = ""
}