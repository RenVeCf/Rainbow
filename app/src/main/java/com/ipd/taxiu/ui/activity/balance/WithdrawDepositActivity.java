package com.ipd.taxiu.ui.activity.balance;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ipd.taxiu.R;
import com.ipd.taxiu.ui.BaseUIActivity;
import com.ipd.taxiu.widget.ApplyForDialog;
import com.ipd.taxiu.widget.AuditSuccessDialog;
import com.ipd.taxiu.widget.MessageDialog;
import com.ipd.taxiu.widget.PickerUtil;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Miss on 2018/7/30
 * 余额提现
 */
public class WithdrawDepositActivity extends BaseUIActivity implements View.OnClickListener {
    private RelativeLayout rl_choose_bank;
    private TextView tv_choose_bank;
    private List<String> data;
    private EditText et_money;
    private TextView btn_confirm;
    private TextView tv_account_balance;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_withdraw_deposit;
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {
        initToolbar();
        rl_choose_bank = findViewById(R.id.rl_choose_bank);
        tv_choose_bank = findViewById(R.id.tv_choose_bank);
        et_money = findViewById(R.id.et_money);
        btn_confirm = findViewById(R.id.btn_confirm);
        tv_account_balance = findViewById(R.id.tv_account_balance);
    }

    @Override
    protected void loadData() {
        et_money.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                double balance = Double.parseDouble(tv_account_balance.getText().toString());
                if (!s.toString().equals("")) {
                    if (balance >= Double.parseDouble(s.toString()) && Double.parseDouble(s.toString()) >= 100) {
                        btn_confirm.setBackgroundResource(R.drawable.shape_btn_enable);
                        btn_confirm.setClickable(true);
                    } else {
                        btn_confirm.setBackgroundResource(R.drawable.shape_btn_disable);
                        btn_confirm.setClickable(false);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void initListener() {
        rl_choose_bank.setOnClickListener(this);
        btn_confirm.setOnClickListener(this);
    }

    @NotNull
    @Override
    protected String getToolbarTitle() {
        return "余额提现";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_choose_bank:
                Intent intent = new Intent(this,BankCardActivity.class);
                intent.putExtra("bankType","选择银行卡");
                startActivity(intent);
                break;
            case R.id.btn_confirm:
                initDialog();
                break;
        }
    }

    private void initDialog() {
        ApplyForDialog.Builder builder = new ApplyForDialog.Builder(this);
        builder.setTitle("提现申请已提交");
        builder.setMessage("审核通过后，3-5个工作日到账");
        builder.setCommit("我知道了", new ApplyForDialog.OnClickListener() {
            @Override
            public void onClick(ApplyForDialog.Builder builder) {
                builder.getDialog().dismiss();
            }

        });
        builder.getDialog().show();

    }
}
