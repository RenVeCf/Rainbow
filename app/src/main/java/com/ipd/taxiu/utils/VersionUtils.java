package com.ipd.taxiu.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AlertDialog;

import com.ipd.taxiu.bean.BaseResult;
import com.ipd.taxiu.bean.VersionBean;
import com.ipd.taxiu.platform.http.ApiManager;
import com.ipd.taxiu.platform.http.Response;
import com.ipd.taxiu.platform.http.RxScheduler;

/**
 * Created by jumpbox on 2017/10/20.
 */

public class VersionUtils {
    public static void check(final Context context) {
        try {
            String versionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            ApiManager.getService().versionCheck(1, versionName)
                    .compose(RxScheduler.Companion.<BaseResult<VersionBean>>applyScheduler())
                    .subscribe(new Response<BaseResult<VersionBean>>() {
                        @Override
                        protected void _onNext(BaseResult<VersionBean> result) {
                            if (result.code == 0) {
                                if (result.data.PLATFORM == 1) {//android
                                    if (result.data.NEWS == 0) {
                                        String title = "发现新版本:" + result.data.VERSION_NO;
                                        String content = result.data.INTRO;
                                        boolean forceUpdate = result.data.MODIFY == 0 ? false : true;
                                        showUpdateDialog(context, title, content, result.data.URL, forceUpdate);
                                    }
                                }
                            } else if (result.code == 10006) {
                                String title = "发现新版本";
                                String content = "当前版本太旧啦~赶快下载更新吧";
                                showUpdateDialog(context, title, content, result.data.URL, true);

                            } else {
                                //do nothing
                            }
                        }
                    });
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void showUpdateDialog(final Context context, String title, String content, final String downloadUrl, boolean forceUpdate) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(content)
                .setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        Uri content_url = Uri.parse(downloadUrl);
                        intent.setData(content_url);
                        context.startActivity(intent);
                    }
                });
        if (!forceUpdate) {
            builder.setCancelable(true);
            builder.setNegativeButton("暂不更新", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        } else {
            builder.setCancelable(false);
        }
        builder.show();

    }
}
