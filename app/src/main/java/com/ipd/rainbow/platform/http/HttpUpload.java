package com.ipd.rainbow.platform.http;

import android.util.Log;

import com.ipd.rainbow.bean.UploadResultBean;

import java.io.File;
import java.util.List;
import java.util.TreeMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Miss on 2018/8/24
 */
public class HttpUpload {
    private static String logo = "";

    public static Observable uploadFile(List<File> list) {
        if (list == null || list.size() == 0)
            return null;
        TreeMap<String, RequestBody> map = new TreeMap<>();
        for (File file : list) {
            if (file.exists()) {
                map.put("PICS\";filename=\"" + System.currentTimeMillis() + file.getName(),
                        RequestBody.create(MediaType.parse("image/*"), file));
            }
        }
        Observable<UploadResultBean> observable = ApiManager.getService().uploadPicture(map);
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UploadResultBean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("TAG", e.getMessage());
                    }

                    @Override
                    public void onNext(UploadResultBean stringBaseResult) {
                        logo = stringBaseResult.data2;
                    }
                });
        return observable;
    }

    public static String getLogo() {
        return logo;
    }
}
