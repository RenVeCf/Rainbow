package com.ipd.taxiu.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.ProgressBar
import com.ipd.jumpbox.jumpboxlibrary.utils.DensityUtil
import com.ipd.taxiu.R

class PercentTextProgressBar : ProgressBar {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)


    private val mPaint: Paint by lazy {
        val paint = Paint()
        paint.isAntiAlias = true
        paint.textSize = resources.getDimension(R.dimen.progress_text_size)
        paint.style = Paint.Style.FILL
        paint.strokeWidth = 1f
        paint.color = Color.WHITE
        paint.textAlign = Paint.Align.CENTER
        paint
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val text = "$progress%"
        val progressLenght = measuredWidth.toFloat() / max * progress

        var textLeft = getTextWidth(text) / 2 + DensityUtil.dip2px(context,4f)
        if (getTextWidth(text) < progressLenght) {
            textLeft = progressLenght / 2
        }

        val fontMetrics = mPaint.fontMetrics
        val baseLine = measuredHeight / 2f - (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.top
        canvas?.drawText(text, textLeft, baseLine, mPaint)

    }

    private fun getTextWidth(text: String): Float {
        return mPaint.measureText(text)
    }

}