package com.ipd.rainbow.platform.global;

import android.content.Intent;
import android.text.TextUtils;

import com.ipd.jumpbox.jumpboxlibrary.utils.SharedPreferencesUtil;
import com.ipd.rainbow.ui.activity.account.LoginActivity;

/**
 * Created by jumpbox on 16/4/19.
 */
public class GlobalParam {


    public static boolean getFirstEnterMine() {
        return SharedPreferencesUtil.getBooleanData(GlobalApplication.Companion.getMContext(), "firstEnterMine", true);
    }

    public static void setFirstEnterMine(boolean isfirst) {
        SharedPreferencesUtil.saveBooleanData(GlobalApplication.Companion.getMContext(), "firstEnterMine", isfirst);
    }

    public static boolean getFirstEnterHome() {
        return SharedPreferencesUtil.getBooleanData(GlobalApplication.Companion.getMContext(), "firstEnterHome", true);
    }

    public static void setFirstEnterHome(boolean isfirst) {
        SharedPreferencesUtil.saveBooleanData(GlobalApplication.Companion.getMContext(), "firstEnterHome", isfirst);
    }

    public static boolean getFirstEnter() {
        return SharedPreferencesUtil.getBooleanData(GlobalApplication.Companion.getMContext(), "firstEnter", true);
    }

    public static void setFirstEnter(boolean isfirst) {
        SharedPreferencesUtil.saveBooleanData(GlobalApplication.Companion.getMContext(), "firstEnter", isfirst);
    }

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
        String userId = getUserId();
        if (TextUtils.isEmpty(userId)) {
            Intent intent = new Intent(GlobalApplication.Companion.getMContext(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            GlobalApplication.Companion.getMContext().startActivity(intent);
            return false;
        }
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
