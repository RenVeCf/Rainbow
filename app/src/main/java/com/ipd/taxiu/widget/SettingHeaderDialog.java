package com.ipd.taxiu.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.TextView;

import com.ipd.jumpbox.jumpboxlibrary.utils.CommonUtils;
import com.ipd.jumpbox.jumpboxlibrary.utils.SharedPreferencesUtil;
import com.ipd.jumpbox.jumpboxlibrary.utils.ToastCommom;
import com.ipd.jumpbox.jumpboxlibrary.widget.CircleImageView;
import com.ipd.taxiu.R;
import com.ipd.taxiu.platform.global.GlobalApplication;
import com.ipd.taxiu.ui.activity.CropActivity;
import com.ipd.taxiu.ui.activity.PhotoSelectActivity;
import com.ipd.taxiu.utils.PictureChooseUtils;

import java.io.File;
import java.util.List;
import java.util.logging.Logger;

import okhttp3.logging.HttpLoggingInterceptor;


/**
 * Created by Miss on 2018/7/20
 */
public class SettingHeaderDialog extends Dialog implements View.OnClickListener {
    private TextView tv_photo_shoot,tv_album_choose,tv_cancel;
    private Activity context;


    public SettingHeaderDialog(@NonNull Activity context, int themeResId ) {
        super(context, themeResId);
        this.context = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_choose_image);
        initWidget();
        setOnClickListener();
        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = AbsListView.LayoutParams.MATCH_PARENT;
        lp.y = 0;//设置Dialog距离底部的距离
        dialogWindow.setAttributes(lp);
    }

    private void initWidget() {
        tv_photo_shoot = findViewById(R.id.tv_photo_shoot);
        tv_album_choose = findViewById(R.id.tv_album_choose);
        tv_cancel = findViewById(R.id.tv_cancel);
    }

    private void setOnClickListener() {
        tv_photo_shoot.setOnClickListener(this);
        tv_album_choose.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_photo_shoot:
                PictureChooseUtils.toTakePhoto(context);
                dismiss();
                break;
            case R.id.tv_album_choose:
                PhotoSelectActivity.launch(context,1);
                dismiss();
                break;
            case R.id.tv_cancel:
                dismiss();
                break;
        }
    }
}
