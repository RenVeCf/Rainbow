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
import com.ipd.taxiu.bean.BannerBean;
import com.ipd.taxiu.bean.BaseResult;
import com.ipd.taxiu.bean.ProductModelResult;
import com.ipd.taxiu.event.UpdateCartEvent;
import com.ipd.taxiu.imageload.ImageLoader;
import com.ipd.taxiu.platform.global.GlobalApplication;
import com.ipd.taxiu.platform.global.GlobalParam;
import com.ipd.taxiu.platform.http.ApiManager;
import com.ipd.taxiu.platform.http.Response;
import com.ipd.taxiu.platform.http.RxScheduler;
import com.ipd.taxiu.ui.activity.PictureAndVideoPreviewActivity;
import com.ipd.taxiu.ui.activity.trade.ConfirmOrderActivity;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


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

    //购物车、立即购买、加入拼团、拼团立即购买
    public static final int CART = 0, BUY = 1, SPELL_JOIN = 2, SPELL_NOW = 3;

    public void setData(final int type, String logo, final boolean isGroup, final ProductModelResult modelResult, final int activityId) {
        LinearLayout ll_product_model = mContentView.findViewById(R.id.ll_product_model);
        ViewGroup productLayout = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.item_product_model, null);
        TextView modelView = productLayout.findViewById(R.id.tv_model_name);
        modelView.setText(modelResult.modelName);

        final ProductModelView productModelView = productLayout.findViewById(R.id.product_model_view);

        final ImageView productImageView = mContentView.findViewById(R.id.iv_product_img);
        final TextView productPriceView = mContentView.findViewById(R.id.tv_cart_product_price);
        productModelView.setOnCheckedChangeListener(new ProductModelView.OnCheckedChangeListener() {
            @Override
            public void onChange(@NotNull final ProductModelResult.ProductModelBean modelInfo) {
                ImageLoader.loadNoPlaceHolderImg(getContext(), modelInfo.LOGO, productImageView);
                if (type == SPELL_NOW) {
                    productPriceView.setText("￥" + modelInfo.PRICE);
                } else {
                    productPriceView.setText("￥" + modelInfo.CURRENT_PRICE);
                }

                productImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayList<BannerBean> list = new ArrayList<>();
                        list.add(new BannerBean(modelInfo.LOGO));
                        PictureAndVideoPreviewActivity.Companion.launch(getContext(), list, 0);
                    }
                });
            }
        });

        for (int j = 0; j < modelResult.data.size(); j++) {
            productModelView.addView(modelResult.data.get(j));
        }
        ll_product_model.addView(productLayout);


        final CartOperationView operationView = mContentView.findViewById(R.id.operation_view);
        if (modelResult.data != null && !modelResult.data.isEmpty()) {
            if (type == SPELL_NOW) {
                productPriceView.setText("￥" + modelResult.data.get(0).PRICE);
            } else {
                productPriceView.setText("￥" + modelResult.data.get(0).CURRENT_PRICE);
            }

        }

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
                        if (type == CART) {
                            ApiManager.getService().cartAdd(GlobalParam.getUserIdOrJump(), modelInfo.PRODUCT_ID, modelInfo.FORM_ID, operationView.getNum())
                                    .compose(RxScheduler.Companion.<BaseResult<Integer>>applyScheduler())
                                    .subscribe(new Response<BaseResult<Integer>>(getContext(), true) {
                                        @Override
                                        protected void _onNext(BaseResult<Integer> result) {
                                            if (result.code == 0) {
                                                EventBus.getDefault().post(new UpdateCartEvent());
                                                ToastCommom.getInstance().show(GlobalApplication.Companion.getMContext(), true, "已加入购物车");
                                                dismiss();
                                            } else {
                                                ToastCommom.getInstance().show(GlobalApplication.Companion.getMContext(), result.msg);
                                            }

                                        }
                                    });
                        } else if (type == BUY || type == SPELL_NOW) {
                            ConfirmOrderActivity.Companion.launch(getContext(), modelInfo.PRODUCT_ID, modelInfo.FORM_ID, operationView.getNum(), isGroup, ConfirmOrderActivity.Companion.getNORMAL());
                            dismiss();
                        } else {
                            ConfirmOrderActivity.Companion.launch(getContext(), activityId, modelInfo.PRODUCT_ID, modelInfo.FORM_ID, operationView.getNum(), ConfirmOrderActivity.Companion.getSPELL());
                            dismiss();
                        }

                    }
                });

    }


}
