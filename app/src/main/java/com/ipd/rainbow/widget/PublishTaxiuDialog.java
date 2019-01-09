package com.ipd.rainbow.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.ipd.rainbow.R;
import com.ipd.rainbow.ui.activity.taxiu.PublishTaxiuActivity;


public class PublishTaxiuDialog extends Dialog {

    private View mContentView;

    public PublishTaxiuDialog(@NonNull Context context) {
        super(context, R.style.FullScreenDialogStyle);
        init(context);
    }

    public PublishTaxiuDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init(context);
    }

    protected PublishTaxiuDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }

    private void init(final Context context) {
        mContentView = LayoutInflater.from(context).inflate(R.layout.layout_publish_taxiu, null);
        setContentView(mContentView);

        mContentView.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mContentView.findViewById(R.id.ll_video).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                PublishTaxiuActivity.Companion.launch((Activity) context, PublishTaxiuActivity.Companion.getVIDEO());

            }
        });
        mContentView.findViewById(R.id.ll_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                PublishTaxiuActivity.Companion.launch((Activity) context, PublishTaxiuActivity.Companion.getIMAGE());
            }
        });


        getWindow().setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(params);

    }


}
