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
    private TextView icon_title;
    //邀请类型 1.推荐码邀请  2邀请拼团
    private int inviteType;


    public ChooseFriendDialog(@NonNull Context context, int themeResId ,int inviteType) {
        super(context, themeResId);
        this.context = context;
        this.inviteType = inviteType;
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
        icon_title = findViewById(R.id.icon_title);
    }

    private void setOnClickListener(){
        share_wechat.setOnClickListener(this);
        share_friend.setOnClickListener(this);
        share_qq.setOnClickListener(this);
        share_qzone.setOnClickListener(this);
        ll_close.setOnClickListener(this);
    }


    private void inviteShow(){
        if (inviteType == 1) {
            icon_title.setText("选择的邀请好友");
            ToastCommom.getInstance().show(context, "分享成功");
            dismiss();
        }else if (inviteType == 2){
            icon_title.setText("邀请好友参团");
            ToastCommom.getInstance().show(context, "邀请成功");
            dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.share_wechat:
                inviteShow();
                break;
            case R.id.share_friend:
                inviteShow();
                break;
            case R.id.share_qq:
                inviteShow();
                break;
            case R.id.share_qzone:
                inviteShow();
                break;
            case R.id.ll_close:
                dismiss();
                break;
        }
    }


}
