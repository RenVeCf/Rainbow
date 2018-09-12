package com.ipd.taxiu.widget.camera

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.activity_shoot_video.view.*

class ShootVideoLayout:RelativeLayout{
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)



    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (event.pointerCount == 1) {
                    //显示对焦指示器
                    setFocusViewWidthAnimation(event.x, event.y)
                }
                if (event.pointerCount == 2) {
                }
            }
        }
        return true
    }

    //对焦框指示器动画
    private fun setFocusViewWidthAnimation(x: Float, y: Float) {
        if (handlerFoucs(x, y)) {
            CameraInterface.getInstance().handleFocus(context, x, y, {
                fouce_view.visibility = View.INVISIBLE
            })
        }
    }

    fun handlerFoucs(x: Float, y: Float): Boolean {
        var x = x
        var y = y
        fouce_view.visibility = View.VISIBLE
        if (x < fouce_view.width / 2) {
            x = (fouce_view.width / 2).toFloat()
        }
        if (x > measuredWidth - fouce_view.width / 2) {
            x = (measuredWidth - fouce_view.width / 2).toFloat()
        }
        if (y < fouce_view.width / 2) {
            y = (fouce_view.width / 2).toFloat()
        }
        fouce_view.x = x - fouce_view.width / 2
        fouce_view.y = y - fouce_view.height / 2
        val scaleX = ObjectAnimator.ofFloat(fouce_view, "scaleX", 1f, 0.6f)
        val scaleY = ObjectAnimator.ofFloat(fouce_view, "scaleY", 1f, 0.6f)
        val alpha = ObjectAnimator.ofFloat(fouce_view, "alpha", 1f, 0.4f, 1f, 0.4f, 1f, 0.4f, 1f)
        val animSet = AnimatorSet()
        animSet.play(scaleX).with(scaleY).before(alpha)
        animSet.duration = 400
        animSet.start()
        return true
    }

}