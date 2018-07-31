package com.ipd.taxiu.ui.activity.balance;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ipd.taxiu.R;
import com.ipd.taxiu.ui.BaseUIActivity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by Miss on 2018/7/30
 * 我的余额
 */
public class MyBalanceActivity extends BaseUIActivity implements View.OnClickListener{
    private TextView btn_account_balance,btn_withdraw_deposit,btn_my_bank_card;
    @Override
    protected int getContentLayout() {
        return R.layout.activity_my_balance;
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {
        initToolbar();
        btn_account_balance = findViewById(R.id.btn_account_balance);
        btn_withdraw_deposit = findViewById(R.id.btn_withdraw_deposit);
        btn_my_bank_card = findViewById(R.id.btn_my_bank_card);
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initListener() {
        btn_account_balance.setOnClickListener(this);
        btn_withdraw_deposit.setOnClickListener(this);
        btn_my_bank_card.setOnClickListener(this);
    }

    @NotNull
    @Override
    protected String getToolbarTitle() {
        return "我的余额";
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.btn_account_balance:
                intent = new Intent(this,BalanceBillActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_withdraw_deposit:
                intent = new Intent(this,WithdrawDepositActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_my_bank_card:
                intent = new Intent(this,BankCardActivity.class);
                intent.putExtra("bankType","添加银行卡");
                startActivity(intent);
                break;
        }
    }
}
