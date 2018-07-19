package com.ipd.taxiu.model;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.ipd.taxiu.bean.LocalDirectoryBean;
import com.ipd.taxiu.bean.LocalPictureBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jumpbox on 2017/8/7.
 */

public class PhotoSelectModel extends BaseModel {


    public List<LocalPictureBean> loadPictureByDirectoryName(Context context, String directoryName) {
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Images.ImageColumns.DATA, MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME};
        String sortOrder = MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC";

        Cursor cursor;
        if (!TextUtils.isEmpty(directoryName)) {
            String selection = MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME + "=?";
            String[] selectionArgs = {directoryName};
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, sortOrder);
        } else {
            cursor = context.getContentResolver().query(uri, projection, null, null, sortOrder);
        }


        List<LocalPictureBean> localPictureList = new ArrayList<>();
        while (cursor.moveToNext()) {
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA));
            String directory = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME));
            LocalPictureBean localPictureBean = new LocalPictureBean(path, directory);
            localPictureList.add(localPictureBean);
        }

        return localPictureList;
    }

    public List<LocalDirectoryBean> loadAllDirectory(Context context) {
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Images.ImageColumns.DATA, MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME};
        String selection = "0=0) GROUP BY (" + MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME;

        Cursor cursor = context.getContentResolver().query(uri, projection, selection, null, null);

        List<LocalDirectoryBean> directoryList = new ArrayList<>();
        while (cursor.moveToNext()) {
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA));
            String directory = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME));
            directoryList.add(new LocalDirectoryBean(directory, directory, path));
        }

        return directoryList;
    }
}
