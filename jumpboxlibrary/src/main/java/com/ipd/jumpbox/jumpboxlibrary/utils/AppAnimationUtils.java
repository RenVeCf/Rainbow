package com.ipd.jumpbox.jumpboxlibrary.utils;

import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

/**
 * Created by jumpbox on 2017/2/28.
 */

public class AppAnimationUtils {
    public static RotateAnimation getRotateAnimation(){
        RotateAnimation rotateAnimation =new RotateAnimation(0f,360f, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF,0.5f);
        rotateAnimation.setDuration(1000);
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        return rotateAnimation;
    }

}
