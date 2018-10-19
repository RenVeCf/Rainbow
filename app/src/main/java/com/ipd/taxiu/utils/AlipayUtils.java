package com.ipd.taxiu.utils;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.alipay.sdk.app.PayTask;

import java.util.Map;


/**
 * Created by jumpbox on 16/6/2.
 */
public class AlipayUtils {
    private static AlipayUtils alipayUtils;
    private OnPayListener onPayListener;

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

                    break;
                }
                default:
                    break;
            }
        }
    };

    public void release() {
        mHandler = null;
        onPayListener = null;
        alipayUtils = null;
    }

    public interface OnPayListener {
        void onPaySuccess();

        void onPayWait();

        void onPayFail();
    }


}
