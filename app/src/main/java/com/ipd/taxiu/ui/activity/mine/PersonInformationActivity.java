package com.ipd.taxiu.ui.activity.mine;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ipd.jumpbox.jumpboxlibrary.utils.BitmapUtils;
import com.ipd.jumpbox.jumpboxlibrary.utils.CommonUtils;
import com.ipd.jumpbox.jumpboxlibrary.widget.CircleImageView;
import com.ipd.taxiu.R;
import com.ipd.taxiu.bean.UserBean;
import com.ipd.taxiu.event.UpdateUserInfoEvent;
import com.ipd.taxiu.imageload.ImageLoader;
import com.ipd.taxiu.platform.http.HttpUpload;
import com.ipd.taxiu.platform.http.HttpUrl;
import com.ipd.taxiu.presenter.MinePresenter;
import com.ipd.taxiu.ui.BaseUIActivity;
import com.ipd.taxiu.ui.activity.CropActivity;
import com.ipd.taxiu.ui.activity.account.BindingPhoneActivity;
import com.ipd.taxiu.utils.PictureChooseUtils;
import com.ipd.taxiu.widget.ChooseSexDialog;
import com.ipd.taxiu.widget.PickerUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Miss on 2018/7/26
 * 个人资料
 */
public class PersonInformationActivity extends BaseUIActivity implements View.OnClickListener, MinePresenter.IUserInfoView, MinePresenter.IUpdateUserView {
    private TextView tv_birthday, tv_sex, tv_how_long, tv_person_tag;

    public static int REQUEST_CODE = 7879;
    private String path = "";

    private MinePresenter mPresenter;
    CircleImageView civ_header;

    EditText tv_nickname;

    TextView et_phone_number;

    EditText et_name;
    ImageView iv_bind_arrow;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_person_infomation;
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {
        initToolbar();
        tv_birthday = findViewById(R.id.tv_birthday);
        tv_sex = findViewById(R.id.tv_sex);
        tv_how_long = findViewById(R.id.tv_how_long);
        tv_person_tag = findViewById(R.id.tv_person_tag);
        civ_header = findViewById(R.id.civ_header);
        tv_nickname = findViewById(R.id.tv_nickname);
        et_phone_number = findViewById(R.id.et_phone_number);
        et_name = findViewById(R.id.et_name);
        iv_bind_arrow = findViewById(R.id.iv_bind_arrow);
    }

    @Override
    protected void onViewAttach() {
        super.onViewAttach();
        EventBus.getDefault().register(this);
        mPresenter = new MinePresenter();
        mPresenter.attachView(this, this);
    }

    @Override
    protected void onViewDetach() {
        super.onViewDetach();
        EventBus.getDefault().unregister(this);
        mPresenter.detachView();
        mPresenter = null;
    }

    @Override
    protected void loadData() {
        mPresenter.getUserInfo();
    }

    @Override
    protected void initListener() {
        civ_header.setOnClickListener(this);
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
            String birthday = tv_birthday.getText().toString();
            String sex = tv_sex.getText().toString();
            int gender = 0;
            if (sex.equals("男")) {
                gender = 1;
            } else if (sex.equals("女")) {
                gender = 2;
            } else {
                gender = 0;
            }
            String nickname = tv_nickname.getText().toString();
            String pet_time = tv_how_long.getText().toString();
            String tag = tv_person_tag.getText().toString();
            String username = et_name.getText().toString();
            mPresenter.updateUser(birthday, gender, HttpUpload.getLogo(), nickname, pet_time, tag, username);
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
                intent.putExtra("Tag", tv_person_tag.getText().toString());
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
                CropActivity.Companion.launch(this, path);
            }

            if (requestCode == PictureChooseUtils.PHOTOZOOM) {
                if (data == null)
                    return;
                path = BitmapUtils.getInstance().getPath(this, data.getData());
                CropActivity.Companion.launch(this, path);

            }

            if (requestCode == PictureChooseUtils.IMAGE_COMPLETE) {//截图返回
                if (data == null) return;
                path = data.getStringExtra("path");
                Bitmap mBitmap = BitmapFactory.decodeFile(path);
                civ_header.setImageBitmap(mBitmap);
            }

            File file = new File(path);
            if (file.exists()) {
                List<File> list = new ArrayList<>();
                list.add(file);
                HttpUpload.uploadFile(list);
            }

            if (requestCode == REQUEST_CODE) {
                if (data != null) {
                    String str = data.getStringExtra("signature");
                    tv_person_tag.setText(str);
                } else {
                    tv_person_tag.setText("");
                }
            }
        }
    }

    @Override
    public void getInfoSuccess(@NotNull UserBean data) {
        if (data != null) {
            ImageLoader.loadImgFromLocal(this, HttpUrl.IMAGE_URL + data.LOGO, civ_header);
            tv_nickname.setText(data.NICKNAME);
            if (TextUtils.isEmpty(data.PHONE)) {
                et_phone_number.setText("");
                iv_bind_arrow.setVisibility(View.VISIBLE);

                findViewById(R.id.rl_phone_number).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        BindingPhoneActivity.Companion.launch(getMActivity());
                    }
                });
            } else {
                et_phone_number.setText(data.PHONE);
                iv_bind_arrow.setVisibility(View.GONE);

                findViewById(R.id.rl_phone_number).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }

            et_name.setText(data.USERNAME);
            tv_birthday.setText(data.BIRTHDAY);
            if (data.GENDER == 1) {
                tv_sex.setText("男");
            } else if (data.GENDER == 2) {
                tv_sex.setText("女");
            } else {
                tv_sex.setText("未知");
            }
            tv_how_long.setText(data.PET_TIME);
            tv_person_tag.setText(data.getTag());
        }

    }

    @Override
    public void getInfoFail(@NotNull String errMsg) {
        toastShow(errMsg);
    }

    @Override
    public void updateUserSuccess() {
        toastShow(true, "保存成功");
        finish();
    }

    @Override
    public void updateUserFail(@NotNull String errMsg) {
        toastShow(errMsg);
    }


    @Subscribe
    public void onMainEvent(UpdateUserInfoEvent event){
        loadData();
    }
}