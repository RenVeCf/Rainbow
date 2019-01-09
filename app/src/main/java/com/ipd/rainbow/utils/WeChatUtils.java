package com.ipd.rainbow.utils;

import android.content.Context;

import com.ipd.jumpbox.jumpboxlibrary.utils.ToastCommom;
import com.ipd.rainbow.bean.WechatBean;
import com.ipd.rainbow.platform.global.GlobalApplication;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


/**
 * Created by jumpbox on 16/6/2.
 */
public class WeChatUtils {
    private static WeChatUtils weChatUtils;
    private static IWXAPI api;

    public static WeChatUtils getInstance(Context context) {
        if (weChatUtils == null) {
            weChatUtils = new WeChatUtils();
            // 通过WXAPIFactory工厂，获取IWXAPI的实例
            api = WXAPIFactory.createWXAPI(context, null);
        }
        return weChatUtils;
    }


    public void startPay(WechatBean wechatBean) {
        if (wechatBean == null) return;
        if (!api.isWXAppInstalled()) {
            ToastCommom.getInstance().show(GlobalApplication.Companion.getMContext(), "您还没有安装微信");
            return;
        }
        PayReq req = genPayParam(wechatBean);
        api.sendReq(req);
    }

    private PayReq genPayParam(WechatBean wechatInfo) {
        PayReq req = new PayReq();
        req.appId = wechatInfo.APPID;
        req.partnerId = wechatInfo.MCHID;
        req.prepayId = wechatInfo.PRE_PAYID;
        req.nonceStr = wechatInfo.NONCE_STR;
        req.timeStamp = wechatInfo.TIMESTAMP;
        req.packageValue = wechatInfo.PACKAGE;
        req.sign = wechatInfo.SIGN;
        return req;
    }


    public void release() {
        api = null;
        weChatUtils = null;
    }

}
