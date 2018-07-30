package com.ipd.taxiu.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by jumpbox on 2017/4/10.
 */

public class ScrollCenterRecyclerView extends RecyclerView {
    public ScrollCenterRecyclerView(Context context) {
        super(context);
    }

    public ScrollCenterRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollCenterRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void scrollCenterByPosition(int position) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) getLayoutManager();
        float centerY = getMeasuredHeight() / 2 - (layoutManager.findViewByPosition(position).getMeasuredHeight() / 2);
        layoutManager.scrollToPositionWithOffset(position, (int) centerY);
    }


}
