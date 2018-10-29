package com.ipd.taxiu.utils;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.alipay.sdk.app.AuthTask;
import com.alipay.sdk.app.PayTask;
import com.ipd.taxiu.platform.global.Constant;

import java.util.Map;


/**
 * Created by jumpbox on 16/6/2.
 */
public class AlipayUtils {
    private static AlipayUtils alipayUtils;
    private OnPayListener onPayListener;
    private OnAuthListener onAuthListener;

    public static AlipayUtils getInstance() {
        if (alipayUtils == null) {
            alipayUtils = new AlipayUtils();
        }
        return alipayUtils;
    }


    public void alipay(final Activity context, String money, OnPayListener onPayListener) {
        this.onPayListener = onPayListener;

//        ApiManager.getService().rechargeAlipay(GlobalParam.getUserIdAndJump(), money, "alipay")
//                .compose(RxScheduler.Companion.<BaseResult<String>>applyScheduler())
//                .subscribe(new Response<BaseResult<String>>(context, true) {
//                    @Override
//                    protected void _onNext(final BaseResult<String> stringBaseResult) {
//                        if (stringBaseResult.response == 0) {
//                            genAlipay(context, stringBaseResult.data);
//                        } else {
//                            ToastCommom.getInstance().show(GlobalApplication.Companion.getMContext(), stringBaseResult.desc);
//                        }
//                    }
//                });
    }

    public void alipayByData(Activity activity, String data, OnPayListener onPayListener) {
        this.onPayListener = onPayListener;
        genAlipay(activity, data);
    }

    /**
     * 支付宝账户授权业务
     */
    public void authV2(final Activity activity, OnAuthListener onAuthListener) {
        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * authInfo的获取必须来自服务端；
         */
        this.onAuthListener = onAuthListener;

        boolean rsa2 = (Constant.ALIPAY_RSA2PrivateKey.length() > 0);
        Map<String, String> authInfoMap = OrderInfoUtil2_0.buildAuthInfoMap(Constant.ALIPAY_PID, Constant.ALIPAY_APPID, System.currentTimeMillis() + "", rsa2);
        String info = OrderInfoUtil2_0.buildOrderParam(authInfoMap);

        String privateKey = Constant.ALIPAY_RSA2PrivateKey;
        String sign = OrderInfoUtil2_0.getSign(authInfoMap, privateKey, rsa2);
        final String authInfo = info + "&" + sign;
        Runnable authRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造AuthTask 对象
                AuthTask authTask = new AuthTask(activity);
                // 调用授权接口，获取授权结果
                Map<String, String> result = authTask.authV2(authInfo, true);

                Message msg = new Message();
                msg.what = SDK_CHECK_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread authThread = new Thread(authRunnable);
        authThread.start();
    }

    private void genAlipay(final Activity activity, final String data) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(activity);
                Map<String, String> result = alipay.payV2(data, true);
                Log.e("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        }).start();

    }


    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_CHECK_FLAG = 2;

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);

                    // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                    String resultInfo = payResult.getResult();

                    String resultStatus = payResult.getResultStatus();

                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        if (onPayListener != null) {
                            onPayListener.onPaySuccess();
                        }

                    } else {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            if (onPayListener != null) {
                                onPayListener.onPayWait();
                            }
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            if (onPayListener != null) {
                                onPayListener.onPayFail();
                            }
                        }
                    }
                    break;
                }
                case SDK_CHECK_FLAG: {
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj,true);
                    String resultStatus = authResult.getResultStatus();
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        if (onAuthListener != null) {
                            onAuthListener.onAuthSuccess(authResult.getAuthCode());
                        }

                    } else {
                        if (onAuthListener != null) {
                            onAuthListener.onAuthFail();
                        }
                    }

                    break;
                }
                default:
                    break;
            }
        }
    };

    public void release() {
        mHandler = null;
        onAuthListener = null;
        onPayListener = null;
        alipayUtils = null;
    }

    public interface OnPayListener {
        void onPaySuccess();

        void onPayWait();

        void onPayFail();
    }

    public interface OnAuthListener {
        void onAuthSuccess(String authId);

        void onAuthFail();
    }


}
