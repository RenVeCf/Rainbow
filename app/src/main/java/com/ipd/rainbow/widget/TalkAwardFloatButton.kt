package com.ipd.rainbow.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import com.ipd.rainbow.R

class TalkAwardFloatButton : ImageView {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var mShowType = ShowType.HAS_NOT_AWARD
    private var showTypeChange: ((showType: Int) -> Unit)? = null

    object ShowType {
        const val HAS_NOT_AWARD = 0
        const val HAS_AWARD = 2
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        setResByType()

        setOnClickListener {
            mShowType = if (mShowType == ShowType.HAS_NOT_AWARD) ShowType.HAS_AWARD else ShowType.HAS_NOT_AWARD
            setResByType()
            showTypeChange?.invoke(mShowType)
        }
    }


    private fun setResByType() {
        when (mShowType) {
            ShowType.HAS_AWARD -> setImageResource(R.mipmap.has_award)
            ShowType.HAS_NOT_AWARD -> setImageResource(R.mipmap.has_not_award)
        }
    }


    fun setShowTypeChange(showTypeChange: (showType: Int) -> Unit) {
        this.showTypeChange = showTypeChange

    }

    fun getShowType(): Int {
        return mShowType
    }

}