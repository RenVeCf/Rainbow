package com.ipd.jumpbox.jumpboxlibrary.utils;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;

import com.ipd.jumpbox.jumpboxlibrary.R;


public class PopupUtils {


    public static PopupWindow showView(Context context, View contentView, final Window window, View dropDownView) {
        PopupWindow popup = getPopupNoAnim(context, contentView, window);

        popup.showAsDropDown(dropDownView);
        popup.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                backgroundAlpha(window, 1f);
            }
        });
        return popup;
    }


    public static PopupWindow showViewAtBottom(Context context, View view, final Window window, View parent) {
        return showViewAtBottom(context, view, window, parent, null);
    }

    public static PopupWindow showViewAtBottom(Context context, View view, final Window window, View parent, final OnDismissListener dismissListener) {
        final PopupWindow popup = getPopup(context, view, window);
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

    public static PopupWindow showViewAtCenter(Context context, View view, final Window window, View parent) {
        return showViewAtCenter(context, view, window, parent, null);
    }

    public static PopupWindow showViewAtCenter(Context context, View view, final Window window, View parent,
                                               final OnMessageDismissListener listener) {
        PopupWindow popup = getPopupNoAnim(context, view, window);

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
        popup.setAnimationStyle(R.style.PopupAnimation);
        popup.setHeight(LayoutParams.WRAP_CONTENT);
        popup.setWidth(LayoutParams.MATCH_PARENT);
        popup.setContentView(contentView);
        popup.setBackgroundDrawable(new BitmapDrawable());
        popup.setFocusable(true);
        backgroundAlpha(window, 0.5f);
        return popup;
    }

    public static PopupWindow getPopupNoAnim(Context context, View contentView, Window window) {
        final PopupWindow popup = new PopupWindow(context);
        popup.setHeight(LayoutParams.WRAP_CONTENT);
        popup.setWidth(LayoutParams.MATCH_PARENT);
        popup.setContentView(contentView);
        popup.setBackgroundDrawable(new BitmapDrawable());
        popup.setFocusable(true);
        popup.setTouchable(true);
        popup.setOutsideTouchable(true);
        popup.setTouchInterceptor(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    popup.dismiss();
                    return true;
                }
                return false;
            }
        });

        return popup;
    }


    public interface OnMessageDismissListener {
        void onDismiss();

    }

}
