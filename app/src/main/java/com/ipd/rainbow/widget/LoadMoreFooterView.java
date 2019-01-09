package com.ipd.rainbow.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.ipd.rainbow.R;
import com.ipd.jumpbox.jumpboxlibrary.widget.ProgressWheel;
import com.ipd.jumpbox.jumpboxlibrary.widget.swipetoloadlayout.SwipeLoadMoreTrigger;
import com.ipd.jumpbox.jumpboxlibrary.widget.swipetoloadlayout.SwipeTrigger;

public class LoadMoreFooterView extends LinearLayout implements SwipeTrigger, SwipeLoadMoreTrigger {
    public LoadMoreFooterView(Context context) {
        super(context);
        init();
    }

    public LoadMoreFooterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadMoreFooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        progress_bar = (ProgressWheel) findViewById(R.id.progress_bar);
    }

    ProgressWheel progress_bar;

    private void init() {

    }

    @Override
    public void onLoadMore() {
    }

    @Override
    public void onPrepare() {
    }

    @Override
    public void onMove(int yScrolled, boolean isComplete, boolean automatic) {
    }

    @Override
    public void onRelease() {
    }

    @Override
    public void onComplete() {
    }

    @Override
    public void onReset() {
    }
}