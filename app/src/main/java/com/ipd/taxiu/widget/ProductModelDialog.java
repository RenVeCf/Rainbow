package com.ipd.taxiu.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.ipd.taxiu.R;


public class ProductModelDialog extends Dialog {

    private View mContentView;

    public ProductModelDialog(@NonNull Context context) {
        super(context, R.style.FullScreenDialogStyle);
        init(context);
    }

    public ProductModelDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init(context);
    }

    protected ProductModelDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }

    private void init(Context context) {
        mContentView = LayoutInflater.from(context).inflate(R.layout.layout_product_model, null);
        setContentView(mContentView);

        LinearLayout ll_product_model = mContentView.findViewById(R.id.ll_product_model);
        for (int i = 0; i < 2; i++) {
            ViewGroup productLayout = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.item_product_model, null);
            ProductModelView productModelView = productLayout.findViewById(R.id.product_model_view);
            for (int j = 0; j < 10; j++) {
                productModelView.addView();
            }
            ll_product_model.addView(productLayout);
        }

        getWindow().setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(params);

    }


}
