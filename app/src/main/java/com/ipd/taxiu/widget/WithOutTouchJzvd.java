package com.ipd.taxiu.widget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.ipd.taxiu.R;

import cn.jzvd.JzvdStd;

public class WithOutTouchJzvd extends JzvdStd {

    public WithOutTouchJzvd(Context context) {
        super(context);
    }

    public WithOutTouchJzvd(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void showProgressDialog(float deltaX, String seekTime, long seekTimePosition, String totalTime, long totalTimeDuration) {

    }

    @Override
    public void dismissProgressDialog() {

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.back) {
            ((Activity) getContext()).finish();
        } else {
            super.onClick(v);
        }
    }
}
