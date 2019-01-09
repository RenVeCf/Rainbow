package com.ipd.rainbow.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.TextView;

import com.ipd.jumpbox.jumpboxlibrary.utils.ToastCommom;
import com.ipd.rainbow.R;


/**
 * Created by Miss on 2018/7/20
 */
public class ChooseImageDialog extends Dialog implements View.OnClickListener {
    private TextView tv_photo_shoot,tv_album_choose,tv_cancel;
    private Context context;
    private String takePhoto;


    public ChooseImageDialog(@NonNull Context context, int themeResId ,String takePhoto) {
        super(context, themeResId);
        this.context = context;
        this.takePhoto = takePhoto;
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

        tv_photo_shoot.setText(takePhoto);
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
                ToastCommom.getInstance().show(context,takePhoto);
                break;
            case R.id.tv_album_choose:
                dismiss();
                break;
            case R.id.tv_cancel:
                dismiss();
                break;
        }
    }
}
