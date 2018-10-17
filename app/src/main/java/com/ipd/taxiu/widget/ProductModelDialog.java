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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ipd.jumpbox.jumpboxlibrary.utils.ToastCommom;
import com.ipd.taxiu.R;
import com.ipd.taxiu.bean.BaseResult;
import com.ipd.taxiu.bean.ProductModelResult;
import com.ipd.taxiu.event.UpdateCartEvent;
import com.ipd.taxiu.platform.global.GlobalApplication;
import com.ipd.taxiu.platform.global.GlobalParam;
import com.ipd.taxiu.platform.http.ApiManager;
import com.ipd.taxiu.platform.http.Response;
import com.ipd.taxiu.platform.http.RxScheduler;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;


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

        getWindow().setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(params);

    }

    public void setData(final ProductModelResult modelResult) {
        LinearLayout ll_product_model = mContentView.findViewById(R.id.ll_product_model);
        ViewGroup productLayout = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.item_product_model, null);
        TextView modelView = productLayout.findViewById(R.id.tv_model_name);
        modelView.setText(modelResult.modelName);

        final ProductModelView productModelView = productLayout.findViewById(R.id.product_model_view);
        for (int j = 0; j < modelResult.data.size(); j++) {
            productModelView.addView(modelResult.data.get(j));
        }
        ll_product_model.addView(productLayout);


        final ImageView productImageView = mContentView.findViewById(R.id.iv_product_img);
        final TextView productPriceView = mContentView.findViewById(R.id.tv_cart_product_price);
        final CartOperationView operationView = mContentView.findViewById(R.id.operation_view);
        if (modelResult.data != null && !modelResult.data.isEmpty())
            productPriceView.setText("￥" + modelResult.data.get(0).CURRENT_PRICE);
        productModelView.setOnCheckedChangeListener(new ProductModelView.OnCheckedChangeListener() {
            @Override
            public void onChange(@NotNull ProductModelResult.ProductModelBean modelInfo) {
                productPriceView.setText("￥" + modelInfo.CURRENT_PRICE);
            }
        });

        mContentView.findViewById(R.id.tv_add_cart)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //加入购物车
                        int checkedPos = productModelView.getCheckedPos();
                        if (modelResult.data == null || checkedPos >= modelResult.data.size()) {
                            return;
                        }
                        ProductModelResult.ProductModelBean modelInfo = modelResult.data.get(checkedPos);
                        ApiManager.getService().cartAdd(GlobalParam.getUserIdOrJump(), modelInfo.PRODUCT_ID, modelInfo.FORM_ID, operationView.getNum())
                                .compose(RxScheduler.Companion.<BaseResult<Integer>>applyScheduler())
                                .subscribe(new Response<BaseResult<Integer>>(getContext(), true) {
                                    @Override
                                    protected void _onNext(BaseResult<Integer> result) {
                                        if (result.code == 0) {
                                            EventBus.getDefault().post(new UpdateCartEvent());
                                            ToastCommom.getInstance().show(GlobalApplication.Companion.getMContext(),true, "已加入购物车");
                                            dismiss();
                                        } else {
                                            ToastCommom.getInstance().show(GlobalApplication.Companion.getMContext(), result.msg);
                                        }

                                    }
                                });

                    }
                });

    }


}
