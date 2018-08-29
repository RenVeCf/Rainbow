package com.ipd.taxiu.ui.activity.mine;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
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

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Miss on 2018/7/26
 * 个人资料
 */
public class EditTagActivity extends BaseUIActivity {
    private String tag;
    @BindView(R.id.et_signature)
    EditText editText;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_person_tag;
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {
        ButterKnife.bind(this);
        initToolbar();
        tag = getIntent().getStringExtra("Tag");
    }

    @Override
    protected void loadData() {
        editText.setText(tag);
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
            Intent intent = new Intent();
            intent.putExtra("signature", editText.getText().toString());
            setResult(RESULT_OK,intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
