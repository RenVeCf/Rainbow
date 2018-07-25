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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ipd.jumpbox.jumpboxlibrary.utils.ToastCommom;
import com.ipd.taxiu.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Miss on 2018/7/20
 * 选择邀请的好友
 */
public class ChooseFriendDialog extends Dialog implements View.OnClickListener{
    private Context context;
    private LinearLayout share_wechat,share_friend,share_qq,share_qzone,ll_close;


    public ChooseFriendDialog(@NonNull Context context, int themeResId ) {
        super(context, themeResId);
        this.context = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_choose_friend);
        initWidget();
        setOnClickListener();
        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = AbsListView.LayoutParams.MATCH_PARENT;
        lp.y = 0;//设置Dialog距离底部的距离
        dialogWindow.setAttributes(lp);

    }

    private void initWidget(){
        share_wechat = findViewById(R.id.share_wechat);
        share_friend = findViewById(R.id.share_friend);
        share_qq = findViewById(R.id.share_qq);
        share_qzone = findViewById(R.id.share_qzone);
        ll_close = findViewById(R.id.ll_close);
    }

    private void setOnClickListener(){
        share_wechat.setOnClickListener(this);
        share_friend.setOnClickListener(this);
        share_qq.setOnClickListener(this);
        share_qzone.setOnClickListener(this);
        ll_close.setOnClickListener(this);
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.share_wechat:
                ToastCommom.getInstance().show(context,"分享成功");
                dismiss();
                break;
            case R.id.share_friend:
                ToastCommom.getInstance().show(context,"分享成功");
                dismiss();
                break;
            case R.id.share_qq:
                ToastCommom.getInstance().show(context,"分享成功");
                dismiss();
                break;
            case R.id.share_qzone:
                ToastCommom.getInstance().show(context,"分享成功");
                dismiss();
                break;
            case R.id.ll_close:
                dismiss();
                break;
        }
    }
}
