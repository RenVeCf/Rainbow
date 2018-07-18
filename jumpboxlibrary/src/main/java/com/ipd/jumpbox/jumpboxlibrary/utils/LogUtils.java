package com.ipd.jumpbox.jumpboxlibrary.utils;

import android.util.Log;

public class LogUtils {
    private static boolean isDebug = true;

    public static void e(Class<?> clzz, String msg) {
        e(clzz.getSimpleName(), msg);
    }

    public static void e(String tag, String msg) {
        if (isDebug) {
            Log.e(tag, msg);
        }
    }

    public static void v(Class<?> clzz, String msg) {
        v(clzz.getSimpleName(), msg);
    }

    public static void v(String tag, String msg) {
        if (isDebug) {
            Log.v(tag, msg);
        }
    }
}
