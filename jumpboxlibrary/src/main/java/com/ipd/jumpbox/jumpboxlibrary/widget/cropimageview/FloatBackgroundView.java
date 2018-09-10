package com.ipd.jumpbox.jumpboxlibrary.widget.cropimageview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jumpbox on 2016/11/4.
 */

public class FloatBackgroundView extends View {

    private Paint mPaint;

    public FloatBackgroundView(Context context) {
        super(context);
        init();
    }


    public FloatBackgroundView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FloatBackgroundView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.parseColor("#a0000000"));


    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        ViewGroup parentView = (ViewGroup) getParent();
        if (mCropAreaWidth <= 0) {
            mCropAreaWidth = parentView.getMeasuredWidth();
            mCropAreaHeight = mCropAreaWidth;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //顶部
        Rect rect = new Rect(0, 0, getMeasuredWidth(), (getMeasuredHeight() - mCropAreaHeight) / 2);
        canvas.drawRect(rect, mPaint);

        //底部
        rect = new Rect(0, getMeasuredHeight() - (getMeasuredHeight() - mCropAreaHeight) / 2, getMeasuredWidth(), getMeasuredHeight());
        canvas.drawRect(rect, mPaint);

        int centerTop = (getMeasuredHeight() - mCropAreaHeight) / 2;
        int centerBottom = getMeasuredHeight() - (getMeasuredHeight() - mCropAreaHeight) / 2;
        int centerRight = (getMeasuredWidth() - mCropAreaWidth) / 2;
        rect = new Rect(0, centerTop, centerRight, centerBottom);
        canvas.drawRect(rect, mPaint);

        rect = new Rect(centerRight + mCropAreaWidth, centerTop, getMeasuredWidth(), centerBottom);
        canvas.drawRect(rect, mPaint);


    }


    private int mCropAreaWidth = 0;
    private int mCropAreaHeight = 0;

    /**
     * 设置裁剪框大小
     */
    public void setCropAreaSize(int width, int height) {
        mCropAreaWidth = width;
        mCropAreaHeight = height;
        invalidate();
    }
}
