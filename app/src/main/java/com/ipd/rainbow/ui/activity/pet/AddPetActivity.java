package com.ipd.rainbow.ui.activity.pet;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.ipd.jumpbox.jumpboxlibrary.utils.BitmapUtils;
import com.ipd.jumpbox.jumpboxlibrary.utils.CommonUtils;
import com.ipd.jumpbox.jumpboxlibrary.widget.CircleImageView;
import com.ipd.rainbow.ChoosePetKindEvent;
import com.ipd.rainbow.R;
import com.ipd.rainbow.bean.PetBean;
import com.ipd.rainbow.event.UpdateHomeEvent;
import com.ipd.rainbow.imageload.ImageLoader;
import com.ipd.rainbow.platform.http.HttpUpload;
import com.ipd.rainbow.platform.http.HttpUrl;
import com.ipd.rainbow.presenter.PetPresenter;
import com.ipd.rainbow.ui.BaseUIActivity;
import com.ipd.rainbow.ui.activity.CropActivity;
import com.ipd.rainbow.utils.PictureChooseUtils;
import com.ipd.rainbow.widget.ChooseSexDialog;
import com.ipd.rainbow.widget.PickerUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Miss on 2018/7/27
 * 添加宠物
 */
public class AddPetActivity extends BaseUIActivity implements View.OnClickListener, PetPresenter.IPetInfoView,
        PetPresenter.IPetUpdateView, PetPresenter.IPetAddView {
    private CircleImageView civ_header;
    private TextView tv_pet_kind, tv_birthday, tv_sex, tv_status;
    private EditText tv_nickname;
    private AppCompatCheckBox cb_is_show;
    private PickerUtil pickerUtil = new PickerUtil();

    private List<String> data;
    //petWay 1.添加宠物 2.编辑宠物
    private int petWay;
    private String userId;
    private String path = "";
    private boolean isChooseImg = false;

    private PetPresenter mPresenter;
    private int petId;
    private int petKindId;

    @Override
    protected int getContentLayout() {
        userId = getIntent().getStringExtra("userId");
        petWay = getIntent().getIntExtra("petWay", 0);
        petId = getIntent().getIntExtra("PET_ID", 0);
        petKindId = getIntent().getIntExtra("PET_TYPE_ID", 0);
        return R.layout.activity_add_pet;
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {
        initToolbar();
        civ_header = findViewById(R.id.civ_header);
        tv_pet_kind = findViewById(R.id.tv_pet_kind);
        tv_birthday = findViewById(R.id.tv_birthday);
        tv_sex = findViewById(R.id.tv_sex);
        tv_status = findViewById(R.id.tv_status);
        tv_nickname = findViewById(R.id.tv_nickname);
        cb_is_show = findViewById(R.id.cb_is_show);
    }

    @Override
    protected void loadData() {
        if (petWay == 1) {
            civ_header.setImageResource(R.mipmap.mine_default_header);
        }
    }

    @Override
    protected void initListener() {
        civ_header.setOnClickListener(this);
        tv_pet_kind.setOnClickListener(this);
        tv_birthday.setOnClickListener(this);
        tv_sex.setOnClickListener(this);
        tv_status.setOnClickListener(this);
    }

    @Override
    protected void onViewAttach() {
        super.onViewAttach();
        EventBus.getDefault().register(this);
        mPresenter = new PetPresenter();
        mPresenter.attachView(this, this);
    }

    @Override
    protected void onViewDetach() {
        super.onViewDetach();
        EventBus.getDefault().unregister(this);
        mPresenter.detachView();
        mPresenter = null;
    }

    @NotNull
    @Override
    protected String getToolbarTitle() {
        if (petWay == 2) {
            mPresenter.petGetInfo(petId);
            return "填写宠物信息";
        } else {
            return "添加宠物";
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_pet_save, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        String birthday = tv_birthday.getText().toString();
        birthday = birthday.replace("-", ".");
        String gender = tv_sex.getText().toString();
        int sex = 0;
        if (gender != "") {
            if (gender.equals("GG")) {
                sex = 1;
            }
            if (gender.equals("MM")) {
                sex = 2;
            }
        }
        String nickname = tv_nickname.getText().toString().trim();
        if (TextUtils.isEmpty(nickname)){
            toastShow("请输入宠物昵称");
            return false;
        }


        String statusStr = tv_status.getText().toString().trim();
        int status = 0;
        if (statusStr != "") {
            if (statusStr.equals("正常")) {
                status = 1;
            }
            if (statusStr.equals("寻求好心人领养")) {
                status = 2;
            }
            if (statusStr.equals("寻求配偶")) {
                status = 3;
            }
            if (statusStr.equals("走失了")) {
                status = 4;
            }
        }
        int category;
        if (cb_is_show.isChecked()) {
            category = 1;
        } else {
            category = 0;
        }
        if (id == R.id.pet_save) {
            if (isChooseImg) {
                path = HttpUpload.getLogo();
            }
            if (petWay == 2) {
                mPresenter.petUpdate(birthday, sex, path, nickname, petKindId, status, petId, category);
            } else {
                mPresenter.petAdd(userId, birthday, sex, path, nickname, petKindId, status);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.civ_header:
                PictureChooseUtils.showDialog(this);
                break;
            case R.id.tv_pet_kind:
                Intent intent = new Intent(this, ChoosePetActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_birthday:
                pickerUtil.initLunarPicker(this, tv_birthday, "选择宠物生日");
                break;
            case R.id.tv_sex:
                ChooseSexDialog sexDialog = new ChooseSexDialog(this, R.style.recharge_pay_dialog, tv_sex,
                        "选择宠物性别", "GG", "MM");
                sexDialog.show();
                break;
            case R.id.tv_status:
                initData();
                pickerUtil.initCustomOptionPicker(this, data, tv_status);
                break;

        }
    }

    private void initData() {
        data = new ArrayList<>();
        data.add("正常");
        data.add("寻求好心人领养");
        data.add("寻求配偶");
        data.add("走失了");
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
                isChooseImg = true;
            }

            File file = new File(path);
            if (file.exists()) {
                List<File> list = new ArrayList<>();
                list.add(file);
                HttpUpload.uploadFile(list);
            }
        }
    }

    @Subscribe
    public void onMainEvent(ChoosePetKindEvent event) {
        petKindId = event.petKind.PET_TYPE_ID;
        switch (event.type) {
            case 1:
                tv_pet_kind.setText(event.petKind.NAME);
                break;
            case 2:
                tv_pet_kind.setText(event.petKind.NAME);
                break;
        }
    }

    @Override
    public void getInfoSuccess(@NotNull PetBean data) {
        path = data.LOGO;
        ImageLoader.loadImgFromLocal(this, HttpUrl.IMAGE_URL + data.LOGO, civ_header);
        tv_nickname.setText(data.NICKNAME);
        tv_pet_kind.setText(data.PET_TYPE_NAME);
        tv_birthday.setText(data.BIRTHDAY);
        int sex = data.GENDER;
        if (sex == 1) {
            tv_sex.setText("GG");
        }
        if (sex == 2) {
            tv_sex.setText("MM");
        }
        int status = data.STATUS;
        switch (status) {
            case 1:
                tv_status.setText("正常");
                break;
            case 2:
                tv_status.setText("寻求好心人领养");
                break;
            case 3:
                tv_status.setText("寻求配偶");
                break;
            case 4:
                tv_status.setText("走失了");
                break;
        }
        if (data.CATEGORY == 0) {
            cb_is_show.setChecked(false);
        }
        if (data.CATEGORY == 1) {
            cb_is_show.setChecked(true);
            cb_is_show.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                    } else {
                        cb_is_show.setChecked(true);
                        toastShow("不能取消默认");
                    }
                }
            });
        }
    }

    @Override
    public void getInfoFail(@NotNull String errMsg) {
        toastShow(errMsg);
        civ_header.setImageResource(R.mipmap.mine_default_header);
    }

    @Override
    public void updateSuccess() {
        EventBus.getDefault().post(new UpdateHomeEvent());
        toastShow(true,"修改成功");
        finish();
    }

    @Override
    public void updateFail(@NotNull String errMsg) {
        toastShow(errMsg);
    }

    @Override
    public void addSuccess() {
        EventBus.getDefault().post(new UpdateHomeEvent());
        toastShow(true,"添加成功");
        finish();
    }

    @Override
    public void addFail(@NotNull String errMsg) {
        toastShow(errMsg);
    }
}
