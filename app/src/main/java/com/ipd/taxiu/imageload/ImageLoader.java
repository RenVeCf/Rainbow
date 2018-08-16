package com.ipd.taxiu.imageload;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.ipd.taxiu.R;
import com.ipd.taxiu.platform.http.HttpUrl;

/**
 * Created by jumpbox on 2017/9/21.
 */

public class ImageLoader {

    public static void loadNoPlaceHolderImg(Context context, Object url, ImageView view) {
        if (url instanceof Integer) {
            Glide.with(context).load(url).into(view);
            return;
        }
        Glide.with(context).load( url).into(view);
    }

    public static void loadImgWithPlaceHolder(Context context, String url, int placeHolderRes, ImageView view) {
        Glide.with(context).load( url)
                .apply(new RequestOptions()
                        .placeholder(placeHolderRes)
                        .error(placeHolderRes))
                .into(view);
    }


    public static void loadImgFromLocal(Context context, String path, ImageView view) {
        Glide.with(context).load(path).into(view);
    }

    public static void getBitmapFromUrl(Context context, String url, final OnResourceReadyListener listener) {
        Glide.with(context)
                .asDrawable()
                .load(url)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        if (listener != null) listener.onResourceReady(resource);
                    }
                });
    }

    public static void loadAvatar(final Context context, Object url, final ImageView view) {
        Glide.with(context).asBitmap().load( url)
                .apply(new RequestOptions()
                        .placeholder(R.mipmap.avatar_default)
                        .error(R.mipmap.avatar_default)
                        .centerCrop()
                )
                .into(view);
//                .into(new SimpleTarget<Bitmap>() {
//                    @Override
//                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
//                        RoundedBitmapDrawable circularBitmapDrawable =
//                                RoundedBitmapDrawableFactory.create(context.getResources(), resource);
//                        circularBitmapDrawable.setCircular(true);
//                        view.setImageDrawable(circularBitmapDrawable);
//                    }
//                });
    }

    public interface OnResourceReadyListener {
        void onResourceReady(Drawable resource);
    }
}
