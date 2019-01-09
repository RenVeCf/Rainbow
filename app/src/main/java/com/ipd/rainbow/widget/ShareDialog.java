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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ipd.rainbow.R;


/**
 * Created by Miss on 2018/7/20
 * 选择邀请的好友
 */
public class ShareDialog extends Dialog implements View.OnClickListener {
    private LinearLayout share_wechat, share_friend, share_qq, share_qzone, share_weibo, ll_close;
    private TextView icon_title;
    private String mIconTitle = "将内容分享至";


    public ShareDialog(@NonNull Context context) {
        super(context, R.style.recharge_pay_dialog);
    }

    private ShareDialogOnclickListener mListener = new ShareDialogClick();

    public void setShareDialogOnClickListener(ShareDialogOnclickListener listener) {
        mListener = listener;
    }

    public void setIconTitle(String title) {
        mIconTitle = title;
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

    private void initWidget() {
        share_wechat = findViewById(R.id.share_wechat);
        share_friend = findViewById(R.id.share_friend);
        share_weibo = findViewById(R.id.share_weibo);
        share_qq = findViewById(R.id.share_qq);
        share_qzone = findViewById(R.id.share_qzone);
        ll_close = findViewById(R.id.ll_close);
        icon_title = findViewById(R.id.icon_title);

        icon_title.setText(mIconTitle);
    }

    private void setOnClickListener() {
        share_wechat.setOnClickListener(this);
        share_friend.setOnClickListener(this);
        share_qq.setOnClickListener(this);
        share_qzone.setOnClickListener(this);
        ll_close.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.share_wechat:
                mListener.WechatOnclick();
                dismiss();
                break;
            case R.id.share_friend:
                mListener.momentsOnclick();
                dismiss();
                break;
            case R.id.share_qq:
                mListener.QQOnclick();
                dismiss();
                break;
            case R.id.share_qzone:
                mListener.QQZoneOnclick();
                dismiss();
                break;
            case R.id.share_weibo:
                mListener.SinaOnclick();
                dismiss();
                break;
            case R.id.ll_close:
                dismiss();
                break;
        }
    }


    public interface ShareDialogOnclickListener {
        // 点击微信
        void WechatOnclick();

        // 点击朋友圈
        void momentsOnclick();

        // 点击QQ
        void QQOnclick();

        // 点击QQ空间
        void QQZoneOnclick();

        // 点击新浪微博
        void SinaOnclick();

    }


}
