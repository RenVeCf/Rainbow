package com.ipd.jumpbox.jumpboxlibrary.utils;


import android.content.Context;
import android.content.DialogInterface;

import com.ipd.jumpbox.jumpboxlibrary.R;


public class LoadingUtils {
    private static LoadingDialog dialog;


    public static void show(Context context) {
        show(context, "");
    }

    public static void show(Context context, String loadingText) {
        if (dialog != null && dialog.isShowing()) {
            try {
                dialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                dialog = null;
            }
        }
        dialog = new LoadingDialog(context,
                R.style.ActionSheetDialogStyle, R.layout.view_tips_loading, loadingText);
        dialog.setCanceledOnTouchOutside(false);
        try {
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        dialog = null;
    }

    public static void setOnKeyListener(DialogInterface.OnKeyListener onKeyListener) {
        if (dialog != null) {
            dialog.setOnKeyListener(onKeyListener);
        }
    }
}
