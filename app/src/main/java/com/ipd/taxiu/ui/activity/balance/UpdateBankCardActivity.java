package com.ipd.taxiu.ui.activity.balance;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
 * 修改银行卡
 */
public class UpdateBankCardActivity extends BaseUIActivity implements View.OnClickListener {
    private TextView tv_choose_bank_type, btn_save;
    private List<String> data;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_update_bank_card;
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
        return "修改银行卡";
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_delete_address, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.delete_address) {
            initMessageDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initMessageDialog() {
        MessageDialog.Builder builder = new MessageDialog.Builder(this);
        builder.setTitle("确认要删除该银行卡吗？");
        builder.setMessage("删除后不可恢复，请谨慎操作");
        builder.setCommit("确认删除", new MessageDialog.OnClickListener() {
            @Override
            public void onClick(MessageDialog.Builder builder) {
                toastShow(true, "删除成功");
                builder.getDialog().dismiss();
                finish();
            }
        });
        builder.setCancel("暂不删除", new MessageDialog.OnClickListener() {
            @Override
            public void onClick(MessageDialog.Builder builder) {
                builder.getDialog().dismiss();
            }
        });
        builder.getDialog().show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_choose_bank_type:
                PickerUtil pickerUtil = new PickerUtil();
                pickerUtil.initBankCardOption(this, data, tv_choose_bank_type);
                break;
            case R.id.btn_save:
                toastShow("修改成功");
                finish();
                break;
        }
    }

    private void iniaData() {
        data = new ArrayList<>();
        data.add("中国银行");
        data.add("中国建设银行");
        data.add("中国农业银行");
        data.add("中国工商银行");
        data.add("中国交通银行");
    }
}
