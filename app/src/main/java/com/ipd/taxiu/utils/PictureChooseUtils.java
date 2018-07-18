package com.ipd.taxiu.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.ipd.jumpbox.jumpboxlibrary.utils.CommonUtils;
import com.ipd.jumpbox.jumpboxlibrary.utils.MySelfSheetDialog;
import com.ipd.taxiu.R;
import com.ipd.taxiu.platform.global.GlobalApplication;

import java.io.File;

/**
 * Created by jumpbox on 2018/4/18.
 */

public class PictureChooseUtils {
    private static String photoSaveName = "";
    public static final int PHOTOZOOM = 0;
    public static final int PHOTOTAKE = 1;
    public static final int IMAGE_COMPLETE = 2;

    public static void showDialog(final Activity activity) {
        new MySelfSheetDialog(activity).builder().addSheetItem(activity.getResources().getString(R.string.photo),
                MySelfSheetDialog.SheetItemColor.Black, new MySelfSheetDialog.OnSheetItemClickListener() {

                    @Override
                    public void onClick(int which) {
                        photoSaveName = String.valueOf(System.currentTimeMillis()) + ".png";
                        Uri imageUri = null;
                        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        imageUri = Uri.fromFile(new File(CommonUtils.getExternalFilesDirPath(GlobalApplication.Companion.getMContext(),
                                Environment.DIRECTORY_PICTURES), photoSaveName));
                        openCameraIntent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
                        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        activity.startActivityForResult(openCameraIntent, PHOTOTAKE);
                    }
                }).addSheetItem(activity.getResources().getString(R.string.camera), MySelfSheetDialog.SheetItemColor.Black, new MySelfSheetDialog.OnSheetItemClickListener() {

            @Override
            public void onClick(int which) {
                Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
                openAlbumIntent.setType("image/*");
                activity.startActivityForResult(openAlbumIntent, PHOTOZOOM);
            }
        }).show();
    }

    public static String getPhotoSaveName() {
        return photoSaveName;
    }
}
