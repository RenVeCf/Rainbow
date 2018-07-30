package com.ipd.jumpbox.jumpboxlibrary.utils;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.ipd.jumpbox.jumpboxlibrary.R;
import com.ipd.jumpbox.jumpboxlibrary.widget.wheelview.WheelView;
import com.ipd.jumpbox.jumpboxlibrary.widget.wheelview.adapters.ArrayWheelAdapter;
import com.ipd.jumpbox.jumpboxlibrary.widget.wheelview.adapters.ListWheelAdapter;

import java.util.List;


public class ListPopupUtils {


    private static PopupWindow mPopupWindow;

    public static void showView(Activity activity, String[] content, final OnFinishListener listener, View parent) {
        mPopupWindow = null;
        View view = View.inflate(activity, R.layout.custom_select_view, null);
        final WheelView wv_view = (WheelView) view.findViewById(R.id.wv_view);
        TextView tv_commit = (TextView) view.findViewById(R.id.tv_commit);
        TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        tv_commit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
                mPopupWindow = null;
                if (listener != null) {
                    listener.onFinish(wv_view);
                }
            }
        });
        tv_cancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
                mPopupWindow = null;
            }
        });

        ArrayWheelAdapter adapter = new ArrayWheelAdapter(activity, content);
        adapter.setTextSize(18);
        wv_view.setViewAdapter(adapter);
        wv_view.setCurrentItem(1);
        wv_view.setVisibleItems(7);

        mPopupWindow = PopupUtils.showViewAtBottom(activity, view, activity.getWindow(), parent,null);
    }



    public interface OnFinishListener {
        void onFinish(WheelView view);
    }


    public static <T> void showView(Activity activity, List<T> content, final OnFinishListener listener, View parent) {
        View view = View.inflate(activity, R.layout.custom_select_view, null);
        final WheelView wv_view = (WheelView) view.findViewById(R.id.wv_view);
        TextView tv_commit = (TextView) view.findViewById(R.id.tv_commit);
        TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        tv_commit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
                mPopupWindow = null;
                if (listener != null) {
                    listener.onFinish(wv_view);
                }
            }
        });

        tv_cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopupWindow.dismiss();
                mPopupWindow = null;
            }
        });

        wv_view.setViewAdapter(new ListWheelAdapter<>(activity, content));
        wv_view.setCurrentItem(1);
        wv_view.setVisibleItems(7);


        mPopupWindow = PopupUtils.showViewAtBottom(activity, view, activity.getWindow(), parent,null);
    }
}
