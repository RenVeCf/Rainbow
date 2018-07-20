package com.ipd.taxiu.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.ipd.taxiu.R;


public class MessageDialog extends Dialog {

    private View mContentView;
    private TextView tv_title, tv_message, tv_cancel, tv_commit;

    public MessageDialog(@NonNull Context context) {
        super(context, R.style.dialogWithoutAnim);
        init(context);
    }

    public MessageDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init(context);
    }

    protected MessageDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }

    private void init(Context context) {
        mContentView = LayoutInflater.from(context).inflate(R.layout.layout_message, null, false);
        tv_title = (TextView) mContentView.findViewById(R.id.tv_title);
        tv_message = (TextView) mContentView.findViewById(R.id.tv_message);
        tv_commit = (TextView) mContentView.findViewById(R.id.tv_confirm);
        tv_cancel = (TextView) mContentView.findViewById(R.id.tv_cancel);
        setContentView(mContentView);

    }

    private void setTitle(String title) {
        tv_title.setVisibility(View.VISIBLE);
        tv_title.setText(title);
    }

    private void setMessage(String message) {
        tv_message.setText(message);
    }

    private void setPositiveButton(String commitStr, final View.OnClickListener onClickListener) {
        tv_commit.setText(commitStr);
        tv_commit.setOnClickListener(onClickListener);
    }

    private void setNegativeButton(String cancelStr, final View.OnClickListener onClickListener) {
        tv_cancel.setText(cancelStr);
        tv_cancel.setOnClickListener(onClickListener);
    }


    public static class Builder {
        private Context mContext;
        private MessageDialog mDialog;

        public Builder(Context context) {
            mContext = context;
            mDialog = new MessageDialog(context);

        }

        public Builder setTitle(String title) {
            mDialog.setTitle(title);
            return this;
        }

        public Builder setMessage(String message) {
            mDialog.setMessage(message);
            return this;
        }

        public Builder setCommit(String commitStr, final OnClickListener onClickListener) {
            mDialog.setPositiveButton(commitStr, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onClickListener != null) {
                        onClickListener.onClick(Builder.this);
                    }
                }
            });
            return this;
        }

        public Builder setCancel(String cancelStr, final OnClickListener onClickListener) {
            mDialog.setNegativeButton(cancelStr, new View.OnClickListener() {
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
