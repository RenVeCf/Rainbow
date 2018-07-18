package com.ipd.taxiu.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.ipd.taxiu.R;


/**
 * Created by jumpbox on 2017/8/18.
 */

public class HomeScrollBehavior extends AppBarLayout.ScrollingViewBehavior {

    private ValueAnimator mAlphaAnimator;

    public HomeScrollBehavior() {
    }

    public HomeScrollBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private View mOverlayView;
    private float initTop = 0;//初始top
    private static final float MAX_OFFSET = 100;
    private boolean isAnima = false;

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        if (mOverlayView == null) {
            mOverlayView = parent.findViewById(R.id.tv_title);
            initTop = child.getTop();
        }
        float dy = initTop - child.getTop();//y轴偏移
        float alpha = dy / MAX_OFFSET;
        if (alpha > 1) {
            alpha = 1f;
        } else if (alpha < 0) {
            alpha = 0f;
        }
        mOverlayView.setVisibility(alpha == 1 ? View.GONE : View.VISIBLE);
        mOverlayView.setAlpha(1 - alpha);
//        Log.e("tag", "initTop:"+initTop+",curTop:"+child.getTop()+",dy:" + dy + ",alpha:" + (1-alpha));

        return super.onDependentViewChanged(parent, child, dependency);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        return true;
    }

    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        onScrollCheckOverlay(child);
    }

    public void onScrollCheckOverlay(View child) {
        float dy = initTop - child.getTop();//y轴偏移
        if (dy > MAX_OFFSET && mOverlayView.getVisibility() == View.VISIBLE) {
            //隐藏
            startAlphaAnim(mOverlayView, false);
        } else if (dy < MAX_OFFSET && mOverlayView.getVisibility() == View.GONE && !isAnima) {
            //显示
            startAlphaAnim(mOverlayView, true);
        }
    }

    public void startAlphaAnim(final View view, final boolean isHide) {
        Log.e("tag", isHide ? "隐藏" : "显示");
        if (mAlphaAnimator != null) {
            mAlphaAnimator.cancel();
        }
        if (!isHide) {
            mOverlayView.setVisibility(View.VISIBLE);
        }

        isAnima = true;
        mAlphaAnimator = ObjectAnimator.ofFloat(isHide ? 1 : 0, isHide ? 0 : 1);
        mAlphaAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                view.setAlpha((Float) animation.getAnimatedValue());
            }
        });
        mAlphaAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                isAnima = false;
                mAlphaAnimator.removeListener(this);
                if (isHide) {
                    mOverlayView.setVisibility(View.GONE);
                }
            }
        });
        mAlphaAnimator.setDuration(500);
        mAlphaAnimator.start();
    }
}
