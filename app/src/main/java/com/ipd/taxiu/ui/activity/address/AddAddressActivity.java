package com.ipd.taxiu.ui.activity.address;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.ipd.taxiu.MainActivity;
import com.ipd.taxiu.R;
import com.ipd.taxiu.ui.BaseUIActivity;
import com.ipd.taxiu.ui.activity.order.RequestReturnActivity;
import com.ipd.taxiu.widget.MessageDialog;
import com.ipd.taxiu.widget.PickerUtil;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.ipd.taxiu.adapter.OrderListAdapter.EVALUATE;

/**
 * Created by Miss on 2018/7/25
 * 添加收货地址
 */
public class AddAddressActivity extends BaseUIActivity implements View.OnClickListener{
    private String addressType;
    private Button button;
    private RelativeLayout rl_address;
    private PickerUtil pickerUtil = new PickerUtil();
    private TextView tv_choose_city;
    @Override
    protected int getContentLayout() {
        addressType = getIntent().getStringExtra("addressType");
        return R.layout.activity_add_address;
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {
        initToolbar();
        button = findViewById(R.id.btn_save);
        rl_address = findViewById(R.id.rl_address);
        tv_choose_city = findViewById(R.id.tv_choose_city);
    }

    @Override
    protected void loadData() {
        pickerUtil.initJsonData(this);
    }

    @Override
    protected void initListener() {
        button.setOnClickListener(this);
        rl_address.setOnClickListener(this);
    }

    @NotNull
    @Override
    protected String getToolbarTitle() {
        return "添加收货地址";
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (addressType.equals("修改地址")) {
            getMenuInflater().inflate(R.menu.menu_delete_address, menu);
            return true;
        }else
            return  false;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item != null) {
           initMessageDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_save:
                if (addressType.equals("添加地址")){
                    toastShow("添加成功");
                    finish();
                }else {
                    toastShow("修改成功");
                    finish();
                }
                break;
            case R.id.rl_address:
                pickerUtil.showPickerView(this,tv_choose_city);
                break;
        }
    }

    /**
     * 取消订单提示框
     **/
    private void initMessageDialog() {
        MessageDialog.Builder builder = new MessageDialog.Builder(this);
        builder.setTitle("确认要删除该收货地址吗？");
        builder.setMessage("删除后不可恢复，请谨慎操作");
        builder.setCommit("确认删除", new MessageDialog.OnClickListener() {
            @Override
            public void onClick(MessageDialog.Builder builder) {
                toastShow("删除成功");
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
}
