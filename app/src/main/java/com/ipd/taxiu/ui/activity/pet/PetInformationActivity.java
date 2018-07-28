package com.ipd.taxiu.ui.activity.pet;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.ipd.taxiu.R;
import com.ipd.taxiu.ui.BaseUIActivity;
import com.ipd.taxiu.widget.MessageDialog;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import butterknife.BindView;

/**
 * Created by Miss on 2018/7/27
 * 宠物资料
 */
public class PetInformationActivity extends BaseUIActivity implements View.OnClickListener{
    private TextView btn_delete_pet;
    @Override
    protected int getContentLayout() {
        return R.layout.activity_pet_information;
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {
        initToolbar();
        btn_delete_pet = findViewById(R.id.btn_delete_pet);
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initListener() {
        btn_delete_pet.setOnClickListener(this);
    }

    @NotNull
    @Override
    protected String getToolbarTitle() {
        return "宠物资料";
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_pet_edit, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.pet_edit){
            Intent intent = new Intent(this,AddPetActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_delete_pet:
                initMessageDialog();
                break;
        }
    }

    private void initMessageDialog() {
        MessageDialog.Builder builder = new MessageDialog.Builder(this);
        builder.setTitle("确认要删除该宠物信息吗？");
        builder.setMessage("宠物信息删除后，不可恢复，须重新添加，请谨慎操作");
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
