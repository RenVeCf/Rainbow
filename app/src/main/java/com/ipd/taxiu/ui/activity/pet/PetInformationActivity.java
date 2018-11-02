package com.ipd.taxiu.ui.activity.pet;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ipd.jumpbox.jumpboxlibrary.widget.CircleImageView;
import com.ipd.taxiu.R;
import com.ipd.taxiu.bean.PetBean;
import com.ipd.taxiu.event.UpdateHomeEvent;
import com.ipd.taxiu.imageload.ImageLoader;
import com.ipd.taxiu.platform.http.HttpUrl;
import com.ipd.taxiu.presenter.PetPresenter;
import com.ipd.taxiu.ui.BaseUIActivity;
import com.ipd.taxiu.widget.MessageDialog;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * Created by Miss on 2018/7/27
 * 宠物资料
 */
public class PetInformationActivity extends BaseUIActivity implements View.OnClickListener, PetPresenter.IPetInfoView, PetPresenter.IPetDeleteView {
    private TextView btn_delete_pet;
    private PetPresenter mPresenter;
    private int petId, petKindId;

    CircleImageView civ_header;

    TextView tv_pet_name;

    TextView tv_pet_kind;

    TextView tv_pet_birthday;

    TextView tv_pet_sex;

    TextView tv_pet_status;

    ImageView icon_sex;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_pet_information;
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {
        initToolbar();
        petId = getIntent().getIntExtra("PET_ID", 0);
        petKindId = getIntent().getIntExtra("PET_TYPE_ID", 0);
        btn_delete_pet = findViewById(R.id.btn_delete_pet);
        civ_header = findViewById(R.id.civ_header);
        tv_pet_name = findViewById(R.id.tv_pet_name);
        tv_pet_kind = findViewById(R.id.tv_pet_kind);
        tv_pet_birthday = findViewById(R.id.tv_pet_birthday);
        tv_pet_sex = findViewById(R.id.tv_pet_sex);
        tv_pet_status = findViewById(R.id.tv_pet_status);
        icon_sex = findViewById(R.id.icon_sex);
    }

    @Override
    protected void onViewAttach() {
        super.onViewAttach();
        mPresenter = new PetPresenter();
        mPresenter.attachView(this, this);
    }

    @Override
    protected void onViewDetach() {
        super.onViewDetach();
        mPresenter.detachView();
        mPresenter = null;
    }

    @Override
    protected void loadData() {
        mPresenter.petGetInfo(petId);
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
        if (id == R.id.pet_edit) {
            Intent intent = new Intent(this, AddPetActivity.class);
            intent.putExtra("petWay", 2);
            intent.putExtra("PET_ID", petId);
            intent.putExtra("PET_TYPE_ID", petKindId);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
                mPresenter.petDelete(petId);
                builder.getDialog().dismiss();
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
    public void getInfoSuccess(@NotNull PetBean data) {
        ImageLoader.loadImgFromLocal(this, HttpUrl.IMAGE_URL + data.LOGO, civ_header);
        tv_pet_name.setText(data.NICKNAME);
        tv_pet_kind.setText(data.PET_TYPE_NAME);
        tv_pet_birthday.setText(data.BIRTHDAY);
        int sex = data.GENDER;
        if (sex == 1) {
            tv_pet_sex.setText("男孩子");
            icon_sex.setImageResource(R.mipmap.icon_pet_boy);
        }
        if (sex == 2) {
            tv_pet_sex.setText("女孩子");
            icon_sex.setImageResource(R.mipmap.icon_pet_girl);
        }
        int status = data.STATUS;
        switch (status) {
            case 1:
                tv_pet_status.setText("正常");
                break;
            case 2:
                tv_pet_status.setText("寻求好心人领养");
                break;
            case 3:
                tv_pet_status.setText("寻求配偶");
                break;
            case 4:
                tv_pet_status.setText("走失了");
                break;
        }
    }

    @Override
    public void getInfoFail(@NotNull String errMsg) {
        toastShow(errMsg);
        civ_header.setImageResource(R.mipmap.mine_default_header);
    }

    @Override
    public void deleteSuccess(String errMsg) {
        EventBus.getDefault().post(new UpdateHomeEvent());
        toastShow(errMsg);
        finish();
    }

    @Override
    public void deleteFail(@NotNull String errMsg) {
        toastShow(errMsg);
    }
}
