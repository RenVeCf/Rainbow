package com.ipd.taxiu.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.ipd.taxiu.R;

/**
 * 提现申请
 */
public class ApplyForDialog extends Dialog {

    private View mContentView;
    private TextView tv_title, tv_message,btn_submit;

    public ApplyForDialog(@NonNull Context context) {
        super(context, R.style.dialogWithoutAnim);
        init(context);
    }

    public ApplyForDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init(context);
    }

    protected ApplyForDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }

    private void init(Context context) {
        mContentView = LayoutInflater.from(context).inflate(R.layout.dialog_audit, null, false);
        tv_title = (TextView) mContentView.findViewById(R.id.tv_title);
        tv_message = (TextView) mContentView.findViewById(R.id.tv_message);
        btn_submit = mContentView.findViewById(R.id.btn_know);
        setContentView(mContentView);

    }

    private void setTitle(String title) {
        tv_title.setVisibility(View.VISIBLE);
        tv_title.setText(title);
    }

    private void setMessage(String message) {
        tv_message.setText(message);
    }

    private void setPositiveButton(String submitStr,final View.OnClickListener onClickListener) {
        btn_submit.setText(submitStr);
        btn_submit.setOnClickListener(onClickListener);
    }


    public static class Builder {
        private Context mContext;
        private ApplyForDialog mDialog;

        public Builder(Context context) {
            mContext = context;
            mDialog = new ApplyForDialog(context);

        }

        public Builder setTitle(String title) {
            mDialog.setTitle(title);
            return this;
        }

        public Builder setMessage(String message) {
            mDialog.setMessage(message);
            return this;
        }

        public Builder setCommit(String submitStr,final OnClickListener onClickListener) {
            mDialog.setPositiveButton(submitStr, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onClickListener != null) {
                        onClickListener.onClick(Builder.this);
                    }
                }
            });
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            mDialog.setCancelable(cancelable);
            return this;
        }

        public void show() {
            mDialog.show();
        }

        public void dismiss() {
            if (mDialog != null) {
                mDialog.dismiss();
                mDialog = null;
            }

        }

        public Dialog getDialog() {
            return mDialog;
        }

    }

    public interface OnClickListener {
        void onClick(Builder builder);

    }
}
