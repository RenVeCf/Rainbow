package com.ipd.taxiu.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.ipd.taxiu.R;
import com.ipd.taxiu.adapter.ProductParamAdapter;
import com.ipd.taxiu.bean.ProductParamBean;
import com.ipd.taxiu.bean.TalkBean;

import java.util.ArrayList;
import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;


public class ProductParamDialog extends BottomSheetDialog {

    private View mContentView;

    public ProductParamDialog(@NonNull Context context) {
        super(context, R.style.FullScreenDialogStyle);
        init(context);
    }

    public ProductParamDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init(context);
    }

    protected ProductParamDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }

    private ProductParamAdapter mAdapter;
    private List<ProductParamBean> mParamList;

    private void init(Context context) {
        mContentView = LayoutInflater.from(context).inflate(R.layout.layout_product_param, null);
        setContentView(mContentView);

        mParamList = new ArrayList();
        RecyclerView param_recycler_view = mContentView.findViewById(R.id.param_recycler_view);
        param_recycler_view.setAdapter(mAdapter = new ProductParamAdapter(context, mParamList, new Function1<ProductParamBean, Unit>() {
            @Override
            public Unit invoke(ProductParamBean info) {

                return null;
            }
        }));
//        mContentView.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dismiss();
//            }
//        });


        getWindow().setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(params);
    }

    public void setData(List<ProductParamBean> paramList) {
        mParamList.addAll(paramList);
        mAdapter.notifyDataSetChanged();
    }


}
