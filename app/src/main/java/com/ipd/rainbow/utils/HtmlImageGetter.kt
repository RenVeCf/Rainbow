package com.ipd.rainbow.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.text.Html
import android.widget.TextView
import com.ipd.rainbow.imageload.ImageLoader


class HtmlImageGetter(val context: Context, val textView: TextView) : Html.ImageGetter {

    override fun getDrawable(source: String): Drawable? {
        var drawableWrapper = MyDrawableWrapper()

        ImageLoader.getBitmapFromUrl(context, source, {
            val width = it.intrinsicWidth
            val height = it.intrinsicHeight
            drawableWrapper.drawable = it
            drawableWrapper.setBounds(0, 0, width, height)
            drawableWrapper.drawable?.setBounds(0, 0, width, height)
            textView.text = textView.text
            textView.invalidate()
        })
        return drawableWrapper
    }

    internal inner class MyDrawableWrapper : BitmapDrawable() {
        var drawable: Drawable? = null
        override fun draw(canvas: Canvas) {
            if (drawable != null)
                drawable!!.draw(canvas)
        }
    }

}