package com.ipd.jumpbox.jumpboxlibrary.utils;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;


public class PopupUtils {


    public static PopupWindow showView(Context context, View contentView, final Window window, View dropDownView) {
        PopupWindow popup = getPopup(context, contentView, window);
        backgroundAlpha(window, 0.5f);
        popup.showAsDropDown(dropDownView);
        popup.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                backgroundAlpha(window, 1f);
            }
        });
        return popup;
    }

    public static PopupWindow showViewAtBottom(Context context, View view, final Window window, View parent, final OnDismissListener dismissListener) {
        final PopupWindow popup = getPopup(context, view, window);
        backgroundAlpha(window, 0.5f);
        popup.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        popup.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        popup.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                backgroundAlpha(window, 1f);
                if (dismissListener != null) {
                    dismissListener.onDismiss();
                }
            }
        });
        return popup;
    }

    public static PopupWindow showViewAtCenter(Context context, View view, final Window window, View parent,
                                               final OnMessageDismissListener listener) {
        PopupWindow popup = getPopup(context, view, window);
        backgroundAlpha(window, 0.5f);
        popup.showAtLocation(parent, Gravity.CENTER, 0, 0);
        popup.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                backgroundAlpha(window, 1f);
                if (listener != null) {
                    listener.onDismiss();
                }
            }
        });
        return popup;
    }


    public static PopupWindow showViewAtCenter(PopupWindow popup, final Window window, View parent,
                                               final OnMessageDismissListener listener) {
        backgroundAlpha(window, 0.5f);
        popup.showAtLocation(parent, Gravity.CENTER, 0, 0);
        popup.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                backgroundAlpha(window, 1f);
                if (listener != null) {
                    listener.onDismiss();
                }
            }
        });
        return popup;
    }

    public static void backgroundAlpha(Window window, float alpha) {
        LayoutParams params = window.getAttributes();
        params.alpha = alpha;
        window.setAttributes(params);
    }


    public static PopupWindow getPopup(Context context, View contentView, Window window) {
        PopupWindow popup = new PopupWindow(context);
        popup.setHeight(LayoutParams.WRAP_CONTENT);
        popup.setWidth(LayoutParams.MATCH_PARENT);
        popup.setContentView(contentView);
        popup.setBackgroundDrawable(new BitmapDrawable());
        popup.setFocusable(true);
        return popup;
    }


    public interface OnMessageDismissListener {
        void onDismiss();

    }

}
