package com.ipd.jumpbox.jumpboxlibrary.widget.cropimageview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.ipd.jumpbox.jumpboxlibrary.R;

/**
 * Created by jumpbox on 2016/11/3.
 */

public class FloatDrawableView extends FrameLayout {


    public FloatDrawableView(Context context) {
        super(context);
        init();
    }

    public FloatDrawableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FloatDrawableView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        View cropAreaView = LayoutInflater.from(getContext()).inflate(R.layout.layout_crop_area, this, false);
        addView(cropAreaView);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        ViewGroup parentView = (ViewGroup) getParent();
        if (mCropAreaWidth <= 0) {
            mCropAreaWidth = parentView.getMeasuredWidth();
            mCropAreaHeight = mCropAreaWidth;
        }

        int width = MeasureSpec.makeMeasureSpec(mCropAreaWidth, MeasureSpec.EXACTLY);
        int height = MeasureSpec.makeMeasureSpec(mCropAreaHeight, MeasureSpec.EXACTLY);
        super.onMeasure(width, height);
    }


    private int mCropAreaWidth = 0;
    private int mCropAreaHeight = 0;

    /**
     * 设置裁剪框大小
     */
    public void setCropAreaSize(int width, int height) {
        mCropAreaWidth = width;
        mCropAreaHeight = height;
        requestLayout();
    }
}
