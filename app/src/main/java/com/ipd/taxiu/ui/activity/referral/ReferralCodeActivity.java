package com.ipd.taxiu.ui.activity.referral;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ipd.taxiu.R;
import com.ipd.taxiu.ui.BaseUIActivity;
import com.ipd.taxiu.widget.ChooseFriendDialog;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by Miss on 2018/7/25
 * 我的推荐码
 */
public class ReferralCodeActivity extends BaseUIActivity implements View.OnClickListener{
    private Button btn_invite_friends;
    private TextView tv_friend_list,tv_recommend_earning;
    @Override
    protected int getContentLayout() {
        return R.layout.activity_referral_code;
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {
        initToolbar();
        btn_invite_friends = findViewById(R.id.btn_invite_friends);
        tv_friend_list = findViewById(R.id.tv_friend_list);
        tv_recommend_earning = findViewById(R.id.tv_recommend_earning);
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initListener() {
        btn_invite_friends.setOnClickListener(this);
        tv_friend_list.setOnClickListener(this);
        tv_recommend_earning.setOnClickListener(this);
    }

    @NotNull
    @Override
    protected String getToolbarTitle() {
        return "我的推荐码";
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.btn_invite_friends:
                ChooseFriendDialog dialog = new ChooseFriendDialog(this,R.style.recharge_pay_dialog,1);
                dialog.show();
                break;
            case R.id.tv_friend_list:
                intent = new Intent(this,FriendListActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_recommend_earning:
                intent = new Intent(this,RecommendEarningsActivity.class);
                startActivity(intent);
                break;
        }
    }
}
