package com.ipd.taxiu.platform.global;

import android.text.TextUtils;

import com.ipd.jumpbox.jumpboxlibrary.utils.SharedPreferencesUtil;

/**
 * Created by jumpbox on 16/4/19.
 */
public class GlobalParam {

    public static void saveUserToken(String userToken) {
        SharedPreferencesUtil.saveStringData(GlobalApplication.Companion.getMContext(), "userToken", userToken);
    }

    public static String getUserToken() {
        return SharedPreferencesUtil.getStringData(GlobalApplication.Companion.getMContext(), "userToken", "");
    }

    public static void saveUserId(String userId) {
        SharedPreferencesUtil.saveStringData(GlobalApplication.Companion.getMContext(), "userId", userId);
    }

    public static String getUserId() {
        String userId = SharedPreferencesUtil.getStringData(GlobalApplication.Companion.getMContext(), "userId", "");
        if (TextUtils.isEmpty(userId)) {
            return "";
        }
        return userId;
    }


    public static void onExitUser() {
        GlobalParam.saveUserId("");
        GlobalParam.saveUserToken("");
    }

    public static String getUserIdOrJump() {
        if (!isLoginOrJump()) return "";
        return getUserId();
    }


    public static boolean isLoginOrJump() {
//        String userId = getUserId();
//        if (TextUtils.isEmpty(userId)) {
//            Intent intent = new Intent(GlobalApplication.Companion.getMContext(), LoginActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            GlobalApplication.Companion.getMContext().startActivity(intent);
//            return false;
//        }
        return true;
    }

    public static boolean isLogin() {
        String userId = getUserId();
        if (TextUtils.isEmpty(userId)) {
            return false;
        }
        return true;
    }

}
