package com.ipd.jumpbox.jumpboxlibrary.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ipd.jumpbox.jumpboxlibrary.R;


/**
 * 自定义toast
 */
public class ToastCommom {

    private static ToastCommom toastCommom;

    private Toast toast;

    public static ToastCommom getInstance() {
        if (toastCommom == null) {
            toastCommom = new ToastCommom();
        }
        return toastCommom;
    }

    public void show(Context context, String tvString) {
        show(context, false, tvString);
    }

    public void show(Context context, boolean success, String tvString) {
        View layout = LayoutInflater.from(context).inflate(R.layout.toast_dialog2, null);
        ImageView status = layout.findViewById(R.id.iv_status);
        status.setImageResource(success ? R.mipmap.toast_success : R.mipmap.toast_fail);
        TextView text = layout.findViewById(R.id.toast_des);
        text.setText(tvString);
        if (toast == null) {
            toast = new Toast(context.getApplicationContext());
            toast.setGravity(Gravity.BOTTOM, 0, DensityUtil.dip2px(context.getApplicationContext(), 40));
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(layout);
            toast.show();
        } else {
            toast.setView(layout);
            toast.show();
        }

    }

}
