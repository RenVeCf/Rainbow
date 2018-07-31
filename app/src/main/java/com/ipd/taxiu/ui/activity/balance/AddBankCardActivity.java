package com.ipd.taxiu.ui.activity.balance;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ipd.taxiu.R;
import com.ipd.taxiu.ui.BaseUIActivity;
import com.ipd.taxiu.widget.MessageDialog;
import com.ipd.taxiu.widget.PickerUtil;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Miss on 2018/7/30
 * 添加银行卡
 */
public class AddBankCardActivity extends BaseUIActivity implements View.OnClickListener {
    private TextView tv_choose_bank_type;
    private  List<String> data;
    private TextView btn_save;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_add_bank_card;
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {
        initToolbar();
        tv_choose_bank_type = findViewById(R.id.tv_choose_bank_type);
        btn_save = findViewById(R.id.btn_save);
    }

    @Override
    protected void loadData() {
        iniaData();
    }

    @Override
    protected void initListener() {
        tv_choose_bank_type.setOnClickListener(this);
        btn_save.setOnClickListener(this);
    }

    @NotNull
    @Override
    protected String getToolbarTitle() {
        return "添加银行卡";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_choose_bank_type:
                PickerUtil pickerUtil = new PickerUtil();
                pickerUtil.initBankCardOption(this, data, tv_choose_bank_type);
                break;
            case R.id.btn_save:
               toastShow("添加成功");
               finish();
                break;
        }
    }


    private   void iniaData() {
        data = new ArrayList<>();
        data.add("中国银行");
        data.add("中国建设银行");
        data.add("中国农业银行");
        data.add("中国工商银行");
        data.add("中国交通银行");
    }
}
