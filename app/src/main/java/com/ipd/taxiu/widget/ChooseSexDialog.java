package com.ipd.taxiu.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.ipd.jumpbox.jumpboxlibrary.utils.ToastCommom;
import com.ipd.taxiu.R;


/**
 * Created by Miss on 2018/7/20
 * 选择性别
 */
public class ChooseSexDialog extends Dialog implements View.OnClickListener {
    private ImageView iv_sex_boy,iv_sex_girl;
    private Context context;
    private TextView textView;


    public ChooseSexDialog(@NonNull Context context, int themeResId,TextView textView) {
        super(context, themeResId);
        this.context = context;
        this.textView = textView;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_choose_sex);
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
        iv_sex_boy = findViewById(R.id.iv_sex_boy);
        iv_sex_girl = findViewById(R.id.iv_sex_girl);
    }

    private void setOnClickListener() {
        iv_sex_boy.setOnClickListener(this);
        iv_sex_girl.setOnClickListener(this);
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_sex_boy:
                textView.setText("男");
                dismiss();
                break;
            case R.id.iv_sex_girl:
                textView.setText("女");
                dismiss();
                break;
        }
    }
}
