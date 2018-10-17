package com.ipd.taxiu.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.ipd.taxiu.R;
import com.ipd.taxiu.adapter.ProductCouponAdapter;
import com.ipd.taxiu.bean.ExchangeBean;

import java.util.ArrayList;
import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;


public class ProductCouponDialog extends Dialog {

    private View mContentView;

    public ProductCouponDialog(@NonNull Context context) {
        super(context, R.style.FullScreenDialogStyle);
        init(context);
    }

    public ProductCouponDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init(context);
    }

    protected ProductCouponDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }

    private void init(Context context) {
        mContentView = LayoutInflater.from(context).inflate(R.layout.layout_product_coupon, null);
        setContentView(mContentView);

        mContentView.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mCouponList = new ArrayList();
        RecyclerView param_recycler_view = mContentView.findViewById(R.id.coupon_recycler_view);
        param_recycler_view.setAdapter(mAdapter = new ProductCouponAdapter(context, mCouponList, new Function1<ExchangeBean, Unit>() {
            @Override
            public Unit invoke(ExchangeBean info) {

                return null;
            }
        }));


        getWindow().setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(params);

    }


    private ProductCouponAdapter mAdapter;
    private List<ExchangeBean> mCouponList;

    public void setData(List<ExchangeBean> paramList) {
        mCouponList.clear();
        mCouponList.addAll(paramList);
        mAdapter.notifyDataSetChanged();
    }


}
