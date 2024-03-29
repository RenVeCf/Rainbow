package com.ipd.rainbow.widget.camera.listener;

import android.graphics.Bitmap;

/**
 * =====================================
 * 作    者: 陈嘉桐
 * 版    本：1.1.4
 * 创建日期：2017/4/25
 * 描    述：
 * =====================================
 */
public interface RecordResultListener {
    void onRecodResult(boolean isShort, String path, Bitmap firstFrame);
    void onConfirm();
}
