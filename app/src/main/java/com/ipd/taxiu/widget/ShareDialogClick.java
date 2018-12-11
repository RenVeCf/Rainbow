package com.ipd.taxiu.widget;

import android.content.res.AssetManager;
import android.text.TextUtils;

import com.ipd.jumpbox.jumpboxlibrary.utils.ThreadUtils;
import com.ipd.taxiu.platform.global.GlobalApplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

public class ShareDialogClick implements ShareDialog.ShareDialogOnclickListener, PlatformActionListener {
    public String shareTitle = "测试标题";
    public String shareTitleUrl = "http://www.baidu.com";
    public String shareLogoUrl = "";
    public String shareText = "分享内容";
    public String shareImageUrl = "http://www.someserver.com/测试图片网络地址.jpg";
    public String shareSite = "站点名称";
    public String shareSiteUrl = "http://www.baidu.com";
    public int shareType = Platform.SHARE_WEBPAGE;


    public static String LOGO_IMG_PATH;

    public ShareDialogClick() {
        copyLogo();
    }


    public ShareDialogClick setShareTitle(String title) {
        shareTitle = title;
        return this;
    }

    public ShareDialogClick setShareContent(String content) {
        shareText = content;
        return this;
    }

    public ShareDialogClick setShareUrl(String url) {
        shareTitleUrl = url;
        shareSiteUrl = url;
        return this;
    }

    public ShareDialogClick setShareLogoUrl(String url) {
        shareLogoUrl = url;
        return this;
    }

    public ShareDialogClick setShareType(int type) {
        shareType = type;
        return this;
    }

    private MainPlatformActionListener mPlatformActionListener;

    public ShareDialogClick setCallback(MainPlatformActionListener listener) {
        mPlatformActionListener = listener;
        return this;
    }


    /**
     *
     */
    @Override
    public void momentsOnclick() {
        Platform.ShareParams sp = new WechatMoments.ShareParams();
        sp.setTitle(shareTitle);
        sp.setText(shareText);
        if (TextUtils.isEmpty(shareLogoUrl)) {
            sp.setImagePath(LOGO_IMG_PATH);
        } else {
            sp.setImageUrl(shareLogoUrl);
        }
        sp.setUrl(shareSiteUrl);
        sp.setShareType(shareType);
        Platform moments = ShareSDK.getPlatform(WechatMoments.NAME);
        // 设置分享事件回调（注：回调放在不能保证在主线程调用，不可以在里面直接处理UI操作）
        moments.setPlatformActionListener(this);
        // 执行图文分享
        moments.share(sp);
    }

    @Override
    public void QQOnclick() {
        Platform.ShareParams sp = new QQ.ShareParams();
        sp.setTitle(shareTitle);
        sp.setTitleUrl(shareTitleUrl); // 标题的超链接
        sp.setText(shareText);
        if (TextUtils.isEmpty(shareLogoUrl)) {
            sp.setImagePath(LOGO_IMG_PATH);
        } else {
            sp.setImageUrl(shareLogoUrl);
        }
        sp.setSite(shareSite);
        sp.setSiteUrl(shareSiteUrl);
        sp.setShareType(shareType);
        Platform qq = ShareSDK.getPlatform(QQ.NAME);
        qq.setPlatformActionListener(this);
        qq.share(sp);

    }

    @Override
    public void QQZoneOnclick() {
        Platform.ShareParams sp = new QZone.ShareParams();
        sp.setTitle(shareTitle);
        sp.setTitleUrl(shareTitleUrl); // 标题的超链接
        sp.setText(shareText);
        if (TextUtils.isEmpty(shareLogoUrl)) {
            sp.setImagePath(LOGO_IMG_PATH);
        } else {
            sp.setImageUrl(shareLogoUrl);
        }
        sp.setSite(shareSite);
        sp.setSiteUrl(shareSiteUrl);
        sp.setShareType(shareType);
        Platform moments = ShareSDK.getPlatform(QZone.NAME);
        // 设置分享事件回调（注：回调放在不能保证在主线程调用，不可以在里面直接处理UI操作）
        moments.setPlatformActionListener(this);
        // 执行图文分享
        moments.share(sp);
    }

    @Override
    public void SinaOnclick() {
        Platform.ShareParams sp = new SinaWeibo.ShareParams();
        sp.setTitle(shareTitle);
        sp.setTitleUrl(shareTitleUrl); // 标题的超链接
        sp.setText(shareText);
        // sp.setImageUrl(shareImageUrl);
        if (TextUtils.isEmpty(shareLogoUrl)) {
            sp.setImagePath(LOGO_IMG_PATH);
        } else {
            sp.setImageUrl(shareLogoUrl);
        }
        sp.setSite(shareSite);
        sp.setSiteUrl(shareSiteUrl);
        sp.setShareType(shareType);
        Platform qq = ShareSDK.getPlatform(SinaWeibo.NAME);
        qq.setPlatformActionListener(this);
        qq.share(sp);

    }

    @Override
    public void WechatOnclick() {
        Platform.ShareParams sp = new Wechat.ShareParams();
        sp.setTitle(shareTitle);
        if (TextUtils.isEmpty(shareLogoUrl)) {
            sp.setImagePath(LOGO_IMG_PATH);
        } else {
            sp.setImageUrl(shareLogoUrl);
        }
        sp.setUrl(shareSiteUrl);
        sp.setText(shareText);
        sp.setShareType(shareType);
        Platform tentWeibo = ShareSDK.getPlatform(Wechat.NAME);
        tentWeibo.setPlatformActionListener(this);
        tentWeibo.share(sp);
    }

    @Override
    public void onCancel(final Platform arg0, final int arg1) {
        ThreadUtils.runOnUIThread(new ThreadUtils.CallBack() {
            @Override
            public void onCallback() {
                if (mPlatformActionListener != null)
                    mPlatformActionListener.onCancel(arg0, arg1);
            }
        });
    }

    @Override
    public void onComplete(final Platform arg0, final int arg1, final HashMap<String, Object> arg2) {
        ThreadUtils.runOnUIThread(new ThreadUtils.CallBack() {
            @Override
            public void onCallback() {
                if (mPlatformActionListener != null)
                    mPlatformActionListener.onComplete(arg0, arg1, arg2);
            }
        });
    }

    @Override
    public void onError(final Platform arg0, final int arg1, final Throwable arg2) {
        ThreadUtils.runOnUIThread(new ThreadUtils.CallBack() {
            @Override
            public void onCallback() {
                if (mPlatformActionListener != null)
                    mPlatformActionListener.onError(arg0, arg1, arg2);
            }
        });
    }


    private void copyLogo() {
        LOGO_IMG_PATH = GlobalApplication.Companion.getMContext().getFilesDir().getAbsolutePath() + "/logo.png";

        AssetManager am = GlobalApplication.Companion.getMContext().getAssets();
        FileOutputStream fos = null;
        InputStream logo = null;

        try {
            logo = am.open("logo.png");
            File logoFile = new File(LOGO_IMG_PATH);
            if (!logoFile.exists()) {
                fos = new FileOutputStream(logoFile);
                int len = 0;
                byte[] buffer = new byte[1024];
                while ((len = logo.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                }

                fos.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (logo != null) {
                try {
                    logo.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                logo = null;
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                fos = null;
            }
        }
    }

    public interface MainPlatformActionListener {

        void onComplete(Platform platform, int i, HashMap<String, Object> hashMap);

        void onError(Platform platform, int i, Throwable throwable);

        void onCancel(Platform platform, int i);
    }

}
