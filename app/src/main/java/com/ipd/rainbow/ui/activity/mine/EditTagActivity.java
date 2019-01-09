package com.ipd.rainbow.ui.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.ipd.rainbow.R;
import com.ipd.rainbow.ui.BaseUIActivity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * Created by Miss on 2018/7/26
 * 个人资料
 */
public class EditTagActivity extends BaseUIActivity {
    private String tag;
    EditText editText;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_person_tag;
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {
        initToolbar();
        tag = getIntent().getStringExtra("Tag");
        editText = findViewById(R.id.et_signature);
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
            setResult(RESULT_OK, intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
