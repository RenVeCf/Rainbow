package com.ipd.taxiu.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by jumpbox on 2017/12/8.
 */

public class MyScrollView extends ScrollView {
    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private float lastY = 0;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float dy = ev.getY() - lastY;
                int scrollY = getScrollY() + getMeasuredHeight();
//                Log.e("tag", "scrollY:" + scrollY + ",measureHeight:" + getChildAt(0).getMeasuredHeight());
                if (dy < 0 && scrollY >= getChildAt(0).getMeasuredHeight()) {
                    return false;
                }
                break;
            case MotionEvent.ACTION_UP:
                lastY = 0;
                break;
        }

        return super.onTouchEvent(ev);
    }

}
