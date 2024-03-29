package com.ipd.rainbow.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.ipd.rainbow.R;
import com.ipd.rainbow.bean.BaseResult;
import com.ipd.rainbow.bean.EarningsResult;
import com.ipd.rainbow.event.PayRequestEvent;
import com.ipd.rainbow.platform.global.GlobalParam;
import com.ipd.rainbow.platform.http.ApiManager;
import com.ipd.rainbow.platform.http.Response;
import com.ipd.rainbow.platform.http.RxScheduler;

import org.greenrobot.eventbus.EventBus;


/**
 * Created by Miss on 2018/7/20
 */
public class PayWayDialog extends Dialog implements View.OnClickListener {
    private ImageView iv_close;
    private TextView btn_confirm;
    private ChoosePayTypeLayout choose_pay_type_layout;


    public PayWayDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_pay_way);
        initWidget();
        setOnClickListener();
        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = AbsListView.LayoutParams.MATCH_PARENT;
        lp.y = 0;//设置Dialog距离底部的距离
        dialogWindow.setAttributes(lp);


        final TextView walletBalance = findViewById(R.id.tv_wallet_balance);

        ApiManager.getService().availableBalance(GlobalParam.getUserId())
                .compose(RxScheduler.Companion.<EarningsResult<String>>applyScheduler())
                .subscribe(new Response<BaseResult<String>>() {
                    @Override
                    protected void _onNext(BaseResult<String> result) {
                        if (result.code == 0) {
                            walletBalance.setText("余额：￥" + result.data);
                        }
                    }
                });


    }

    private void initWidget() {
        iv_close = findViewById(R.id.iv_close);
        btn_confirm = findViewById(R.id.btn_confirm);
        choose_pay_type_layout = findViewById(R.id.choose_pay_type_layout);

    }

    private void setOnClickListener() {
        iv_close.setOnClickListener(this);
        btn_confirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close:
                dismiss();
                break;
            case R.id.btn_confirm:
                switch (choose_pay_type_layout.getPayType()) {
                    case ChoosePayTypeLayout.PayType.ALIPAY: {
                        alipay();
                        break;
                    }
                    case ChoosePayTypeLayout.PayType.WECHAT: {
                        wechat();
                        break;
                    }
                    case ChoosePayTypeLayout.PayType.BALANCE: {
                        balance();
                        break;
                    }
                }
        }
    }

    private void balance() {
        EventBus.getDefault().post(new PayRequestEvent(ChoosePayTypeLayout.PayType.BALANCE));
        dismiss();
    }

    private void wechat() {
        EventBus.getDefault().post(new PayRequestEvent(ChoosePayTypeLayout.PayType.WECHAT));
        dismiss();
    }

    private void alipay() {
        EventBus.getDefault().post(new PayRequestEvent(ChoosePayTypeLayout.PayType.ALIPAY));
        dismiss();
    }

}
