package com.ipd.taxiu.ui.activity.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ipd.taxiu.R;
import com.ipd.taxiu.adapter.OrderDetailAdapter;
import com.ipd.taxiu.bean.OrderDetailBean;
import com.ipd.taxiu.ui.BaseUIActivity;
import com.ipd.taxiu.widget.MessageDialog;
import com.ipd.taxiu.widget.PayWayDialog;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import static com.ipd.taxiu.adapter.OrderListAdapter.DELIVERY;
import static com.ipd.taxiu.adapter.OrderListAdapter.DETAIL;
import static com.ipd.taxiu.adapter.OrderListAdapter.EVALUATE;
import static com.ipd.taxiu.adapter.OrderListAdapter.PAYMENT;

/**
 * Created by Miss on 2018/7/20
 * 订单详情
 */
public class OrderDetailActivity extends BaseUIActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    private OrderDetailAdapter adapter;
    private ArrayList<OrderDetailBean> datas;
    private PayWayDialog payWayDialog;
    private String orderStatus;

    private LinearLayout button1;
    private LinearLayout button4;
    private LinearLayout button2;
    private LinearLayout button3;

    private TextView tv_button1, tv_button2, tv_button3, tv_button4;


    @Override
    protected int getContentLayout() {
        return R.layout.activity_order_detail;
    }

    @Override
    protected void initView(@Nullable Bundle bundle) {
        initToolbar();
        orderStatus = getIntent().getStringExtra("order_status");
        recyclerView = findViewById(R.id.recycler_view_order_detail);

        tv_button1 = findViewById(R.id.tv_order_button1);
        tv_button2 = findViewById(R.id.tv_order_button2);
        tv_button3 = findViewById(R.id.tv_order_button3);
        tv_button4 = findViewById(R.id.tv_order_button4);

        button1 = findViewById(R.id.ll_order_button1);
        button2 = findViewById(R.id.ll_order_button2);
        button3 = findViewById(R.id.ll_order_button3);
        button4 = findViewById(R.id.ll_order_button4);

        setButtonStatus();
    }

    /**
     * 根据不同状态按钮选择不一样
     */
    private void setButtonStatus() {
        if (orderStatus.equals(PAYMENT)) {
            tv_button1.setText("取消订单");
            tv_button4.setText("立即付款");

            button4.setVisibility(View.VISIBLE);
            button1.setVisibility(View.VISIBLE);
            button2.setVisibility(View.GONE);
            button3.setVisibility(View.GONE);
        } else if (orderStatus.equals(DETAIL)) {
            tv_button2.setText("申请退款");

            button4.setVisibility(View.GONE);
            button1.setVisibility(View.GONE);
            button3.setVisibility(View.GONE);
            button2.setVisibility(View.VISIBLE);
        } else if (orderStatus.equals(DELIVERY)) {
            tv_button1.setText("申请退款");
            tv_button4.setText("确认收货");
            tv_button2.setText("查看物流");

            button4.setVisibility(View.VISIBLE);
            button1.setVisibility(View.VISIBLE);
            button2.setVisibility(View.VISIBLE);
            button3.setVisibility(View.GONE);
        } else if (orderStatus.equals(EVALUATE)) {
            tv_button1.setText("删除订单");
            tv_button2.setText("查看物流");
            tv_button3.setText("再次购买");
            tv_button4.setText("评价晒单");

            button4.setVisibility(View.VISIBLE);
            button1.setVisibility(View.VISIBLE);
            button2.setVisibility(View.VISIBLE);
            button3.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void loadData() {
        initData();
        adapter = new OrderDetailAdapter(datas, this, orderStatus);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.addFooterView(LayoutInflater.from(this).inflate(R.layout.item_order_detail_footer, null));
        adapter.addHeaderView(LayoutInflater.from(this).inflate(R.layout.item_order_detail_header, null));
    }

    @Override
    protected void initListener() {
        tv_button1.setOnClickListener(this);
        tv_button2.setOnClickListener(this);
        tv_button3.setOnClickListener(this);
        tv_button4.setOnClickListener(this);
    }

    @NotNull
    @Override
    protected String getToolbarTitle() {
        return "订单详情";
    }

    private void initData() {
        datas = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            datas.add(new OrderDetailBean());
        }
    }

    /**
     * 支付框
     **/
    private void initpayWayDialog() {
        payWayDialog = new PayWayDialog(this, R.style.recharge_pay_dialog);
        payWayDialog.show();
    }

    /**
     * 取消订单提示框
     **/
    private void initMessageDialog(String title, String message, String commitStr, String cancelStr, final String toastMsg) {
        MessageDialog.Builder builder = new MessageDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCommit(commitStr, new MessageDialog.OnClickListener() {
            @Override
            public void onClick(MessageDialog.Builder builder) {
                toastShow(toastMsg);
                builder.getDialog().dismiss();
            }
        });
        builder.setCancel(cancelStr, new MessageDialog.OnClickListener() {
            @Override
            public void onClick(MessageDialog.Builder builder) {
                builder.getDialog().dismiss();
            }
        });
        builder.getDialog().show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_order_button1:
                if (orderStatus.equals(PAYMENT)) {
                    initMessageDialog("确认要取消该订单吗？",
                            "订单取消后不可恢复，需重新购买，请谨慎操作。",
                            "确认取消",
                            "暂不取消",
                            "取消成功");
                } else if (orderStatus.equals(DELIVERY)) {
                    startReturnActivity();
                } else if (orderStatus.equals(EVALUATE)) {
                    initMessageDialog("确认要删除该订单吗？",
                            "订单删除后不可撤销，请谨慎操作。",
                            "确认删除",
                            "暂不删除",
                            "删除成功");
                }
                break;
            case R.id.tv_order_button2:
                if (orderStatus.equals(DETAIL)) {
                   startReturnActivity();
                }
                break;
            case R.id.tv_order_button3:
                break;
            case R.id.tv_order_button4:
                if (orderStatus.equals(PAYMENT)) {
                    initpayWayDialog();
                } else if (orderStatus.equals(DELIVERY)) {
                    initMessageDialog("确认要确认收货吗？",
                            "请确保您已收到商品，确认收货后，不可撤销，请谨慎操作。",
                            "确认收货",
                            "暂不收货",
                            "收货成功");
                } else if (orderStatus.equals(EVALUATE)) {
                    Intent intent = new Intent(this,EvaluateActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }

    private void startReturnActivity(){
        Intent intent = new Intent(this,RequestReturnMoneyActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //如果是【已完成】 添加申请退货选择
         if (orderStatus.equals(EVALUATE)) {
             getMenuInflater().inflate(R.menu.order_sales_return, menu);
             return true;
         }else
             return  false;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item != null) {
            int id = item.getItemId();
            if (id == R.id.order_sales_return) {
                Intent intent = new Intent(this,RequestReturnActivity.class);
                startActivity(intent);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
