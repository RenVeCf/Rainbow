package com.ipd.taxiu.ui.activity.pet;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.ipd.jumpbox.jumpboxlibrary.utils.BitmapUtils;
import com.ipd.jumpbox.jumpboxlibrary.utils.CommonUtils;
import com.ipd.jumpbox.jumpboxlibrary.utils.SharedPreferencesUtil;
import com.ipd.jumpbox.jumpboxlibrary.utils.ToastCommom;
import com.ipd.jumpbox.jumpboxlibrary.widget.CircleImageView;
import com.ipd.taxiu.ChoosePetKindEvent;
import com.ipd.taxiu.R;
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
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Miss on 2018/7/27
 * 添加宠物
 */
public class AddPetActivity extends BaseUIActivity implements View.OnClickListener {
    private CircleImageView civ_header;
    private TextView tv_pet_kind, tv_birthday, tv_sex, tv_status;
    private PickerUtil pickerUtil = new PickerUtil();

    private List<String> data;
    private String petWay;
    private String path;

    @Override
    protected int getContentLayout() {
        petWay = getIntent().getStringExtra("petWay");
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
    }

    @Override
    protected void loadData() {

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
    }

    @Override
    protected void onViewDetach() {
        super.onViewDetach();
        EventBus.getDefault().unregister(this);
    }

    @NotNull
    @Override
    protected String getToolbarTitle() {
        if (petWay != null) {
            return "添加宠物";
        }else {
            return "添加宠物信息";
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
        if (id == R.id.pet_save) {
            if (petWay != null) {
                toastShow("添加成功");
                finish();
            }else {
                finish();
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
                civ_header.setImageBitmap(mBitmap);
            }

        }
    }

    @Subscribe
    public void onMainEvent(ChoosePetKindEvent event){
        switch (event.type){
            case 0:
                tv_pet_kind.setText(event.petKind.NAME);
                break;
            case 1:
                tv_pet_kind.setText(event.petKind.NAME);
                break;
        }
    }

}
