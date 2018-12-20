package com.ipd.taxiu.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

public class DisallowEditText extends EditText {

    public DisallowEditText(Context context) {
        super(context);
        init();
    }

    public DisallowEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DisallowEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean result = super.onTouchEvent(event);
        getParent().requestDisallowInterceptTouchEvent(true);
        return result;
    }

}

