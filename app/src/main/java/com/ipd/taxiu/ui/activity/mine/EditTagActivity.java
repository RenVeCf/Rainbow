package com.ipd.taxiu.ui.activity.mine;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.ipd.jumpbox.jumpboxlibrary.utils.CommonUtils;
import com.ipd.jumpbox.jumpboxlibrary.widget.CircleImageView;
import com.ipd.taxiu.R;
import com.ipd.taxiu.platform.global.GlobalApplication;
import com.ipd.taxiu.ui.BaseUIActivity;
import com.ipd.taxiu.widget.ChooseSexDialog;
import com.ipd.taxiu.widget.PickerUtil;
import com.ipd.taxiu.widget.SettingHeaderDialog;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * Created by Miss on 2018/7/26
 * 个人资料
 */
public class EditTagActivity extends BaseUIActivity{

    @Override
    protected int getContentLayout() {
        return R.layout.activity_person_tag;
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {
        initToolbar();
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initListener() {
    }

    @NotNull
    @Override
    protected String getToolbarTitle() {
        return "个人标签";
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_person_infomation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item_save) {
           finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
