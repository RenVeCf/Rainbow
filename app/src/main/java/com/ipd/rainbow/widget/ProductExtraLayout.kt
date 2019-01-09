package com.ipd.rainbow.widget

import android.content.Context
import android.graphics.Paint
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.ipd.jumpbox.jumpboxlibrary.utils.ThreadUtils
import com.ipd.rainbow.R
import com.ipd.rainbow.bean.ProductDetailBean
import com.ipd.rainbow.utils.StoreType
import com.ipd.rainbow.utils.StringUtils
import kotlinx.android.synthetic.main.layout_product_extra_clearance.view.*
import kotlinx.android.synthetic.main.layout_product_extra_flash_sale.view.*
import java.util.*

class ProductExtraLayout : FrameLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    private var mType: Int = StoreType.PRODUCT_NORMAL
    private var mProductInfo: ProductDetailBean? = null
    private val mInflater by lazy { LayoutInflater.from(context) }

    fun setProductInfo(productInfo: ProductDetailBean) {
        mType = productInfo.KIND
        mProductInfo = productInfo
        resetProductUI()
    }

    private fun resetProductUI() {
        removeAllViews()
        when (mType) {
            StoreType.PRODUCT_NORMAL,
            StoreType.PRODUCT_GROUP_PRODUCT -> {
                visibility = View.GONE
            }
            StoreType.PRODUCT_FLASH_SALE -> {
                visibility = View.VISIBLE
                onProductFlashSale()
            }
            StoreType.PRODUCT_CLEARANCE -> {
                visibility = View.VISIBLE
                onProductClearance()
            }
            StoreType.PRODUCT_NEW -> {
                visibility = View.VISIBLE
                onProductNew()
            }
            StoreType.PRODUCT_GROUP_PURCHASE -> {
                visibility = View.VISIBLE
                onProductGroupPurchase()
            }
        }
    }

    private fun onProductGroupPurchase() {
        val purchaseView = mInflater.inflate(R.layout.layout_product_extra_flash_sale, this, false)
        purchaseView.tv_flash_sale_price.text = mProductInfo?.CURRENT_PRICE
        purchaseView.tv_flash_sale_price_old.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
        purchaseView.tv_flash_sale_price_old.text = "￥${mProductInfo?.REFER_PRICE ?: ""}"
        purchaseView.tv_flash_sale_price_buy_num.text = "${mProductInfo?.TEAM_NUM}人团，${mProductInfo?.JOIN_NUM}人已参团"

        purchaseView.setBackgroundResource(R.color.colorPrimaryDark)
        purchaseView.tv_flash_sale_price_time.text = "距结束还剩"
        mCurTime = (mProductInfo?.SYS_TIME_STAMP ?: "0").toLong()

        if (mCurTime < (mProductInfo?.END_TIME_STAMP ?: "0").toLong()) {
            mTimer = Timer()
            mFlashSaleTimerTask = FlashSaleTimerTask(purchaseView)
            mTimer?.schedule(mFlashSaleTimerTask, 0 - mCurTime % 1000, 1000L)
        }

        addView(purchaseView)
    }

    private fun onProductNew() {
        val newView = mInflater.inflate(R.layout.layout_product_extra_clearance, this, false)
        newView.tv_clearance_price.text = mProductInfo?.CURRENT_PRICE
        newView.tv_clearance_price_old.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
        newView.tv_clearance_price_old.text = "￥${mProductInfo?.REFER_PRICE ?: ""}"
        newView.tv_clearance_buy_num.text = String.format(resources.getString(R.string.product_sales), mProductInfo?.BUYNUM)

        addView(newView)
    }

    private fun onProductClearance() {
        val clearanceView = mInflater.inflate(R.layout.layout_product_extra_clearance, this, false)
        clearanceView.tv_clearance_price.text = mProductInfo?.CURRENT_PRICE
        clearanceView.tv_clearance_price_old.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
        clearanceView.tv_clearance_price_old.text = "￥${mProductInfo?.REFER_PRICE ?: ""}"
        when {
            mProductInfo?.PULL_CATEGORY == 2 -> clearanceView.tv_clearance_buy_num.text = String.format(resources.getString(R.string.store_gift_coupon_date), mProductInfo?.VALID_TIME)
            mProductInfo?.PULL_CATEGORY == 4 -> clearanceView.tv_clearance_buy_num.text = String.format(resources.getString(R.string.product_sales), mProductInfo?.BUYNUM)
            else -> clearanceView.tv_clearance_buy_num.text = String.format(resources.getString(R.string.product_stock), mProductInfo?.STOCK)
        }

        addView(clearanceView)
    }

    private var mCurTime = System.currentTimeMillis()
    private var mTimer: Timer? = null
    private var mFlashSaleTimerTask: FlashSaleTimerTask? = null
    private fun onProductFlashSale() {
        if (mProductInfo?.RUSH_STATE == 0) {
            visibility = View.GONE
            return
        }

        visibility = View.VISIBLE
        val flashSaleView = mInflater.inflate(R.layout.layout_product_extra_flash_sale, this, false)
        flashSaleView.tv_flash_sale_price.text = mProductInfo?.CURRENT_PRICE
        flashSaleView.tv_flash_sale_price_old.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
        flashSaleView.tv_flash_sale_price_old.text = "￥${mProductInfo?.REFER_PRICE ?: ""}"

        when (mProductInfo?.RUSH_STATE) {
            1 -> {
                flashSaleView.setBackgroundResource(R.color.medium_sea_green)
                flashSaleView.tv_flash_sale_price_buy_num.text = String.format(resources.getString(R.string.product_purchase_remind_num), mProductInfo?.REMIND_NUM)
                flashSaleView.tv_flash_sale_price_time.text = "距开始还剩"
                mCurTime = (mProductInfo?.SYS_TIME_STAMP ?: "0").toLong()

                if (mCurTime < (mProductInfo?.START_TIME_STAMP ?: "0").toLong()) {
                    mTimer = Timer()
                    mFlashSaleTimerTask = FlashSaleTimerTask(flashSaleView)
                    mTimer?.schedule(mFlashSaleTimerTask, 1000 - mCurTime % 1000, 1000L)
                }
            }
            2 -> {
                flashSaleView.setBackgroundResource(R.color.colorPrimaryDark)
                flashSaleView.tv_flash_sale_price_time.text = "距结束还剩"
                flashSaleView.tv_flash_sale_price_buy_num.text = String.format(resources.getString(R.string.product_purchase_num), mProductInfo?.BUYNUM)
                mCurTime = (mProductInfo?.SYS_TIME_STAMP ?: "0").toLong()

                if (mCurTime < (mProductInfo?.END_TIME_STAMP ?: "0").toLong()) {
                    mTimer = Timer()
                    mFlashSaleTimerTask = FlashSaleTimerTask(flashSaleView)
                    mTimer?.schedule(mFlashSaleTimerTask, 0 - mCurTime % 1000, 1000L)
                }

            }
            3 -> {
                flashSaleView.tv_flash_sale_end.visibility = View.VISIBLE
                flashSaleView.ll_flash_sale_end_time.visibility = View.GONE
                flashSaleView.tv_flash_sale_price_time.visibility = View.GONE

            }
        }
        addView(flashSaleView)
    }

    private fun onRefreshData() {
        release()
    }


    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        release()
    }

    private fun release() {
        mTimer?.cancel()
        mTimer = null
        mFlashSaleTimerTask?.cancel()
        mFlashSaleTimerTask = null
    }


    inner class FlashSaleTimerTask(private val flashSaleView: View) : TimerTask() {
        override fun run() {
            ThreadUtils.runOnUIThread {
                when (mProductInfo?.RUSH_STATE) {
                    1 -> {
                        mCurTime += 1000
                        val startTime = (mProductInfo?.START_TIME_STAMP ?: "0").toLong()
                        if (mCurTime >= startTime) {
                            //刷新数据
                            onRefreshData()
                        } else {
                            StringUtils.getCountDownByTime(startTime - mCurTime, { hours, minutes, second ->
                                flashSaleView.tv_group_purchase_hours.text = hours
                                flashSaleView.tv_group_purchase_minute.text = minutes
                                flashSaleView.tv_group_purchase_second.text = second
                            })
                        }
                    }
                    2 -> {
                        mCurTime += 1000
                        val endTime = (mProductInfo?.END_TIME_STAMP ?: "0").toLong()
                        if (mCurTime >= endTime) {
                            //刷新数据
                            onRefreshData()
                        } else {
                            StringUtils.getCountDownByTime(endTime - mCurTime, { hours, minutes, second ->
                                flashSaleView.tv_group_purchase_hours.text = hours
                                flashSaleView.tv_group_purchase_minute.text = minutes
                                flashSaleView.tv_group_purchase_second.text = second
                            })
                            mFlashSaleTimerTask?.scheduledExecutionTime()

                        }
                    }
                }
//                LogUtils.e("tag","system:$mCurTime--start:${mProductInfo?.START_TIME_STAMP}--end:${mProductInfo?.END_TIME_STAMP}")
            }
        }
    }
}