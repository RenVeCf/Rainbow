package com.ipd.taxiu.ui.activity.mine;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.ipd.jumpbox.jumpboxlibrary.utils.BitmapUtils;
import com.ipd.jumpbox.jumpboxlibrary.utils.CommonUtils;
import com.ipd.jumpbox.jumpboxlibrary.widget.CircleImageView;
import com.ipd.taxiu.R;
import com.ipd.taxiu.bean.LocalPictureBean;
import com.ipd.taxiu.bean.PictureBean;
import com.ipd.taxiu.platform.global.GlobalApplication;
import com.ipd.taxiu.ui.BaseUIActivity;
import com.ipd.taxiu.ui.activity.CropActivity;
import com.ipd.taxiu.ui.activity.PhotoSelectActivity;
import com.ipd.taxiu.utils.PictureChooseUtils;
import com.ipd.taxiu.widget.ChooseSexDialog;
import com.ipd.taxiu.widget.PickerUtil;
import com.ipd.taxiu.widget.SettingHeaderDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.ipd.taxiu.utils.PictureChooseUtils.PHOTOZOOM;


/**
 * Created by Miss on 2018/7/26
 * 个人资料
 */
public class PersonInformationActivity extends BaseUIActivity implements View.OnClickListener {
    private CircleImageView circleImageView;
    private TextView tv_birthday, tv_sex, tv_how_long, tv_person_tag;

    public static int REQUEST_CODE = 7879;
    private String path;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_person_infomation;
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {
        initToolbar();
        circleImageView = findViewById(R.id.civ_header);
        tv_birthday = findViewById(R.id.tv_birthday);
        tv_sex = findViewById(R.id.tv_sex);
        tv_how_long = findViewById(R.id.tv_how_long);
        tv_person_tag = findViewById(R.id.tv_person_tag);
    }

    @Override
    protected void loadData() {

    }

    @Override
    protected void initListener() {
        circleImageView.setOnClickListener(this);
        tv_birthday.setOnClickListener(this);
        tv_sex.setOnClickListener(this);
        tv_how_long.setOnClickListener(this);
        tv_person_tag.setOnClickListener(this);
    }

    @NotNull
    @Override
    protected String getToolbarTitle() {
        return "个人资料";
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
            toastShow("保存成功");
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.civ_header:
                PictureChooseUtils.showDialog(this);
                break;
            case R.id.tv_birthday:
                getDate(tv_birthday, "选择您的生日");
                break;
            case R.id.tv_sex:
                ChooseSexDialog sexDialog = new ChooseSexDialog(this, R.style.recharge_pay_dialog, tv_sex,
                        "选择您的性别", "男", "女");
                sexDialog.show();
                break;
            case R.id.tv_how_long:
                getDate(tv_how_long, "选择首次养宠时间");
                break;
            case R.id.tv_person_tag:
                Intent intent = new Intent(this, EditTagActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;
        }
    }


    //获取选择日期弹框
    private void getDate(TextView textView, String title) {
        PickerUtil pickerUtil = new PickerUtil();
        pickerUtil.initLunarPicker(this, textView, title);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == PictureChooseUtils.PHOTOTAKE) {
                path = CommonUtils.getPhotoSavePath(this, Environment.DIRECTORY_PICTURES) + "/" + PictureChooseUtils.getPhotoSaveName();
                CropActivity.launch(this, path);
            }

            if (requestCode == PictureChooseUtils.PHOTOZOOM) {
                if (data == null)
                    return;
                path = BitmapUtils.getInstance().getPath(this, data.getData());
                CropActivity.launch(this, path);

            }

            if (requestCode == PictureChooseUtils.IMAGE_COMPLETE) {//截图返回
                if (data == null) return;
                path = data.getStringExtra("path");
                Bitmap mBitmap = BitmapFactory.decodeFile(path);
                circleImageView.setImageBitmap(mBitmap);
            }


            if (requestCode == REQUEST_CODE) {
                if (data != null) {
                    String str = data.getStringExtra("signature");
                    tv_person_tag.setText(str);
                }
            }
        }
    }

}
