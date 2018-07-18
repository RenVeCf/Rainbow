package com.ipd.jumpbox.jumpboxlibrary.utils;


import android.os.Handler;
import android.os.Looper;

/**
 * Created by jumpbox on 2017/3/20.
 */

public class ThreadUtils {
    public static void runOnThread(final CallBack callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                callback.onCallback();

            }
        }).start();
    }

    public static void runOnThreadDelay(final long delay, final CallBack callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(delay);
                    callback.onCallback();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    public static void runOnUIThread(final CallBack callback) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                callback.onCallback();
            }
        });
    }

    public static void runOnUIThreadDelay(long delay, final CallBack callback) {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                callback.onCallback();
            }
        }, delay);
    }


    public interface CallBack {
        void onCallback();

    }

}
