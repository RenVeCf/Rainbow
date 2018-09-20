package com.ipd.taxiu.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * Created by jumpbox on 2017/4/20.
 */

public class AutoScrollerViewPager extends ViewPager {


    private long interval = 6000;
    public static final int SCROLL_WHAT = 0;
    private boolean isAutoScroll = true;
    private Handler handler;
    private static final boolean isBorderAnimation = true;

    public AutoScrollerViewPager(Context context) {
        super(context);
        init();
    }

    private void init() {
        handler = new MyHandler();

    }

    public AutoScrollerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void startAutoScroll() {
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        isAutoScroll = true;
        sendScrollMessage(interval);
    }


    /**
     * stop auto scroll
     */
    public void stopAutoScroll() {
        isAutoScroll = false;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
//        handler.removeMessages(SCROLL_WHAT);
    }

    private void sendScrollMessage(long delayTimeInMills) {
        /** remove messages before, keeps one message is running at most **/
        handler.removeMessages(SCROLL_WHAT);
        handler.sendEmptyMessageDelayed(SCROLL_WHAT, delayTimeInMills);
    }


    private class MyHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case SCROLL_WHAT:
                    scrollOnce();
                    sendScrollMessage(interval);
                default:
                    break;
            }
        }
    }

    /**
     * scroll only once
     */
    public void scrollOnce() {
        PagerAdapter adapter = getAdapter();
        int currentItem = getCurrentItem();
        int totalCount;
        if (adapter == null || (totalCount = adapter.getCount()) <= 1) {
            return;
        }
        int nextItem = currentItem + 1 >= totalCount ? 0 : currentItem + 1;
        setCurrentItem(nextItem, isBorderAnimation);
    }

    public boolean isAutoScroll() {
        return isAutoScroll;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (isAutoScroll) {
            startAutoScroll();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (isAutoScroll) {
            if (handler != null) {
                handler.removeCallbacksAndMessages(null);
            }
        }
    }
}
