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
import com.ipd.taxiu.bean.TalkBean;

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
        RecyclerView coupon_recycler_view = mContentView.findViewById(R.id.coupon_recycler_view);
        List<TalkBean> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            list.add(new TalkBean());
        }
        coupon_recycler_view.setAdapter(new ProductCouponAdapter(context, list, new Function1<TalkBean, Unit>() {
            @Override
            public Unit invoke(TalkBean talkBean) {

                return null;
            }
        }));


        getWindow().setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(params);

    }


}
