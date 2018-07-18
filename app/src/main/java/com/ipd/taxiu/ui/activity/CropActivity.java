package com.ipd.taxiu.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.ipd.jumpbox.jumpboxlibrary.bitmap.BitmapUtils;
import com.ipd.jumpbox.jumpboxlibrary.utils.CommonUtils;
import com.ipd.jumpbox.jumpboxlibrary.utils.LoadingUtils;
import com.ipd.jumpbox.jumpboxlibrary.widget.cropimageview.CropImageView;
import com.ipd.taxiu.R;
import com.ipd.taxiu.platform.global.GlobalApplication;
import com.ipd.taxiu.ui.BaseUIActivity;
import com.ipd.taxiu.utils.PictureChooseUtils;

import java.io.File;

/**
 * Created by jumpbox on 2017/3/3.
 */

public class CropActivity extends BaseUIActivity {

    public static void launch(Activity activity, String path) {
        Intent intent = new Intent(activity, CropActivity.class);
        intent.putExtra("path", path);
        activity.startActivityForResult(intent, PictureChooseUtils.IMAGE_COMPLETE);
    }

    private CropImageView mCropImageView;
    private String mPath;


    @Override
    public String getToolbarTitle() {
        return "裁剪";
    }

    @Override
    public int getContentLayout() {
        return R.layout.activity_crop;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        initToolbar();
        mCropImageView = (CropImageView) findViewById(R.id.crop_image_view);

        mPath = getIntent().getStringExtra("path");
        mCropImageView.getPhotoView().setImageDrawable(new BitmapDrawable(BitmapUtils.getInstance().reSizeBitmap(this, new File(mPath))));
    }

    @Override
    public void loadData() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_photo_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save) {
            //保存
            Bitmap bitmap = mCropImageView.cropImage();
            LoadingUtils.show(this);
            //裁剪后的图片要保存的路径
            String path = CommonUtils.getPhotoSavePath(GlobalApplication.Companion.getMContext()) + "/" + "avatar.png";
            File avatarFile = new File(path);
            //删除已经存在的图片
            if (avatarFile.exists()) {
                avatarFile.delete();
            }

            com.ipd.jumpbox.jumpboxlibrary.utils.BitmapUtils.savePhotoToSDCard(bitmap, path);

            uploadPic(path);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void uploadPic(String path) {
        result("", "", path);
    }


    public void result(String url, String name, String path) {
        try {
            Intent intent = new Intent();
            intent.putExtra("url", url);
            intent.putExtra("name", name);
            intent.putExtra("path", path);
            setResult(RESULT_OK, intent);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            LoadingUtils.dismiss();
        }
    }

    @Override
    protected void onViewAttach() {

    }

    @Override
    protected void onViewDetach() {

    }
}
