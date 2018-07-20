package com.ipd.taxiu.widget;

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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ipd.taxiu.R;
import com.ipd.taxiu.ui.activity.order.OrderDetailActivity;

import java.util.List;


/**
 * Created by Miss on 2018/7/20
 */
public class PayWayDialog extends Dialog implements View.OnClickListener{
    public  static int BALANCE = 2;
    public  static int ALIPAY = 0;
    public  static int WECHAT = 1;
    private ImageView iv_close,check_alipay,check_wechat,check_balance;
    private RelativeLayout rl_alipay_pay,rl_wechat_pay,rl_balance_pay;
    private TextView btn_confirm,tv_balance;

    private List<ImageView> checks;
    /** 区别三种支付方式 2:余额 0:支付宝 1:微信支付 **/
    public static int payWay = ALIPAY;

    private Context context;


    public PayWayDialog(@NonNull Context context, int themeResId ) {
        super(context, themeResId);
        this.context = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_pay_way);
        initWidget();
        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = AbsListView.LayoutParams.MATCH_PARENT;
        lp.y = 0;//设置Dialog距离底部的距离
        dialogWindow.setAttributes(lp);

    }

    private void initWidget(){
        iv_close = findViewById(R.id.iv_close);
        check_alipay = findViewById(R.id.check_alipay_pay);
        check_wechat = findViewById(R.id.check_wechat_pay);
        check_balance = findViewById(R.id.check_balance_pay);
        btn_confirm = findViewById(R.id.btn_confirm);
        tv_balance = findViewById(R.id.tv_balance);

        rl_alipay_pay = findViewById(R.id.rl_alipay_pay);
        rl_wechat_pay = findViewById(R.id.rl_wechat_pay);
        rl_balance_pay = findViewById(R.id.rl_balance_pay);

        rl_alipay_pay.setOnClickListener(this);
        rl_wechat_pay.setOnClickListener(this);
        rl_balance_pay.setOnClickListener(this);
        iv_close.setOnClickListener(this);
        btn_confirm.setOnClickListener(this);
    }

    /**
     * 改变选中
     */
    private void checkChanges(int index) {
        for (int i = 0; i < 3; i++) {
            if (i != index) {
                checks.get(i).setVisibility(View.GONE);
            }
        }
        payWay = index;
        checks.get(index).setVisibility(View.VISIBLE);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.check_alipay_pay:
                checkChanges(0);
                break;
            case R.id.check_wechat_pay:
                checkChanges(1);
                break;
            case R.id.check_balance_pay:
                checkChanges(2);
                break;
            case R.id.iv_close:
                dismiss();
                break;
            case R.id.btn_confirm:
                Toast.makeText(context,"支付成功",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
