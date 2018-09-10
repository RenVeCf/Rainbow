package com.ipd.jumpbox.jumpboxlibrary.widget.cropimageview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

/**
 * Created by jumpbox on 2016/11/3.
 */

public class CropImageView extends FrameLayout {
    private static final String TAG = "CropImageView";
    private ZoomImageView mPhotoView;
    private FloatDrawableView mFloatDrawableView;
    private int mCropAreaWidth = 0;//裁剪框宽度
    private int mCropAreaHeight = 0;//裁剪框高度
    private FloatBackgroundView mFloatBackgroundView;

    public CropImageView(Context context) {
        super(context);
        init();
    }

    public CropImageView(Context context, AttributeSet attr) {
        super(context, attr);
        init();
    }

    public CropImageView(Context context, AttributeSet attr, int defStyle) {
        super(context, attr, defStyle);
        init();
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            if (childView instanceof FloatDrawableView) {
                childView.measure(0, 0);
                int childWidth = childView.getMeasuredWidth();
                int childHeight = childView.getMeasuredHeight();
                int myHeight = getMeasuredHeight();
                int myWidth = getMeasuredWidth();
                float l = (myWidth - childWidth) / 2f;
                float t = (myHeight - childHeight) / 2f;
                childView.layout((int) l, (int) t, (int) l + childWidth, (int) t + childHeight);//将裁剪框放置控件中间
            }
        }


    }

    private void init() {
        addView(buildPhotoView());
        addView(buildBackgroupView());
        addView(buildCropAreaView());
        rebuildContent();
    }

    private void rebuildContent() {
        mFloatDrawableView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mFloatDrawableView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                RectF floatDrawableRectF = new RectF(mFloatDrawableView.getLeft(), mFloatDrawableView.getTop(),
                        mFloatDrawableView.getRight(), mFloatDrawableView.getBottom());
                mPhotoView.setFloatDrawableRect(floatDrawableRectF);

            }
        });
    }

    private ZoomImageView buildPhotoView() {
        mPhotoView = new ZoomImageView(getContext());
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mPhotoView.setLayoutParams(params);
        return mPhotoView;
    }


    /**
     * 蒙层
     *
     * @return
     */
    private View buildBackgroupView() {
        mFloatBackgroundView = new FloatBackgroundView(getContext());
        mFloatBackgroundView.setCropAreaSize(mCropAreaWidth, mCropAreaHeight);//设置裁剪框大小
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mFloatBackgroundView.setLayoutParams(params);
        return mFloatBackgroundView;
    }

    /**
     * 裁剪框
     *
     * @return
     */
    private FloatDrawableView buildCropAreaView() {
        mFloatDrawableView = new FloatDrawableView(getContext());
        return mFloatDrawableView;
    }

    public ZoomImageView getPhotoView() {
        if (mPhotoView == null) {
            throw new RuntimeException("photoView is Empty");
        }
        return mPhotoView;
    }

    public void setCropAreaSize(int width, int height) {
        mCropAreaWidth = width;
        mCropAreaHeight = height;

        mFloatBackgroundView.setCropAreaSize(mCropAreaWidth, mCropAreaHeight);
        mFloatDrawableView.setCropAreaSize(mCropAreaWidth, mCropAreaHeight);
        rebuildContent();

    }


    public Bitmap cropImage() {
        if (mPhotoView != null) {
            Bitmap tmpBitmap = null;
            try {

                setDrawingCacheEnabled(true);
                Bitmap cacheBitmap = getDrawingCache();
                tmpBitmap = Bitmap.createBitmap(cacheBitmap, 0, 0, cacheBitmap.getWidth(), cacheBitmap.getHeight());
                cacheBitmap.recycle();
                setDrawingCacheEnabled(false);


                Bitmap bitmap = Bitmap.createBitmap(tmpBitmap, (int) (mFloatDrawableView.getX()),
                        (int) (mFloatDrawableView.getY()), mFloatDrawableView.getWidth(), mFloatDrawableView.getHeight(),
                        null, false);
                return bitmap;


            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (tmpBitmap != null) {
                    tmpBitmap.recycle();
                    tmpBitmap = null;
                }
            }

        }
        return null;
    }


}
