package com.ipd.rainbow.utils;


import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ipd.rainbow.R;


public class IndicatorHelper {

    private int selected_res = R.mipmap.boutique_selected;
    private int unselected_res = R.mipmap.boutique_unselected;

    public static IndicatorHelper newInstance() {
        return new IndicatorHelper();
    }

    public IndicatorHelper setRes(int selectedRes, int unSelectedRes) {
        selected_res = selectedRes;
        unselected_res = unSelectedRes;
        return this;
    }

    public void setIndicator(Context context, final int count, ViewPager viewPager, final LinearLayout ll_indicator,
                             final MyPagerChangeListener listener) {
        if (count <= 0) {
            return;
        }

        ll_indicator.removeAllViews();
        for (int i = 0; i < count; i++) {
            ImageView view = new ImageView(context);
            if (i == 0) {
                view.setBackgroundResource(selected_res);
            } else {
                view.setBackgroundResource(unselected_res);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT);
                params.leftMargin = 10;
                view.setLayoutParams(params);
            }
            ll_indicator.addView(view);
        }
        viewPager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                for (int i = 0; i < ll_indicator.getChildCount(); i++) {
                    ImageView view = (ImageView) ll_indicator.getChildAt(i);
                    if (i == arg0 % count) {
                        view.setBackgroundResource(selected_res);
                    } else {
                        view.setBackgroundResource(unselected_res);
                    }
                }

                if (listener != null) {
                    listener.onPageSelected(arg0);
                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (listener != null) {
                    listener.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (listener != null) {
                    listener.onPageScrollStateChanged(state);
                }
            }
        });
    }

    public interface MyPagerChangeListener {
        void onPageSelected(int pos);

        void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);

        void onPageScrollStateChanged(int state);
    }
}
