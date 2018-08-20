package com.ipd.jumpbox.jumpboxlibrary.utils;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtils {

    public static final String KEFU_PHONE = "400888888";

    public static String getDiscount(String priceStr, String oldPriceStr) {
        float price = Float.parseFloat(priceStr);
        float oldPrice = Float.parseFloat(oldPriceStr);
        float discount = price / oldPrice * 10;

        String ms;
        try {
            BigDecimal bigDecimal = new BigDecimal(discount);
            ms = bigDecimal.setScale(1, BigDecimal.ROUND_HALF_UP).toString();
        } catch (Exception e) {
            ms = "0";
        }
        return ms + "折";
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobiles) {
        /*
         * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
         * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
         * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
         */
        String telRegex = "[1]\\d{10}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles))
            return false;
        else
            return mobiles.matches(telRegex);
    }

    /**
     * 验证手机格式
     */
    public static boolean passwordIsLegal(String password) {
        String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";
        if (TextUtils.isEmpty(password) || password.length() < 6 || password.length() > 16)
            return false;
        else
            return password.matches(regex);
    }

    public static void callPhone(Context context, String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        context.startActivity(intent);
    }


    public static String getPhotoSavePath(Context context) {
        String path = context.getExternalFilesDir(null).getAbsolutePath();
        return path;
    }

    public static String getPhotoSavePath(Context context, String type) {
        String path = context.getExternalFilesDir(type).getAbsolutePath();
        return path;
    }

    /**
     * 获取app外置存储文件夹路径 /.android/packageName/
     *
     * @param context
     * @param type
     * @return
     */
    public static String getExternalFilesDirPath(Context context, String type) {
        return context.getExternalFilesDir(type).getAbsolutePath();
    }

    public static void callKeFu(Context context) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + KEFU_PHONE));
        context.startActivity(intent);
    }

    public static String getTimeStrBySecond(long time) {
        time = time;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(time);
    }


    public static String getDateStrBySecond(long time) {
        time = time;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(time);
    }

    public static String getTimerByMilliSecond(long milliSecond) {
        long totalSecond = milliSecond / 1000;

        String second = totalSecond % 60 >= 10 ? totalSecond % 60 + "" : "0" + (totalSecond % 60);
        String minute = totalSecond / 60 >= 10 ? totalSecond / 60 + "" : "0" + (totalSecond / 60);
        return minute + ":" + second;
    }


    /**
     * 根据生日获取年龄
     *
     * @param birthDay
     * @return
     * @throws Exception
     */
    public static int getAgeByBirth(Date birthDay) throws Exception {
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthDay)) {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        } else if (birthDay.getTime() > cal.getTimeInMillis()) {
            return -1;
        }


        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;
            } else {
                age--;
            }
        }
        System.out.println("age:" + age);
        return age;
    }


    /**
     * 保留两位小数点
     *
     * @param priceStr
     * @return
     */
    public static String getPriceDecimal(String priceStr) {
        try {
            BigDecimal bigDecimal = new BigDecimal(priceStr);
            priceStr = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).toString();

            String ms = priceStr.substring(priceStr.length() - 2, priceStr.length());
            return ms;

        } catch (Exception e) {
            e.printStackTrace();
            return "00";
        }
    }

    /**
     * 加密手机号中间四位
     *
     * @param phone
     * @return
     */
    public static String getEncryPhone(String phone) {
        if (!isMobileNO(phone)) {
            return "";
        }
        StringBuilder sb = new StringBuilder(phone);
        sb.replace(3, 3 + 4, "****");
        return sb.toString();
    }

    // 判断一个字符串是否含有数字
    public static boolean hasDigit(String content) {
        boolean flag = false;
        Pattern p = Pattern.compile(".*\\d+.*");
        Matcher m = p.matcher(content);
        if (m.matches())
            flag = true;
        return flag;
    }

    //该判断字符串中是否包含字母
    public static boolean hasLetter(String content) {
        String regex = ".*[a-zA-Z]+.*";
        Matcher m = Pattern.compile(regex).matcher(content);
        return m.matches();
    }

    //复制运单号
    public static void copyText(Context context, TextView textView) {
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(textView.getText().toString());
        new ToastCommom().show(context, "运单号已复制");
    }

    /**
     * 去掉某个字符后面的所有字符
     *
     * @param text
     * @param character
     * @return
     */
    public static String textCut(String text, String character) {
        if (text != "" && text != null) {
            int i;
            String newStr = text;
            if (text.indexOf(character) != -1) {
                i = text.indexOf(character);
                newStr = text.substring(0, i);
            }
            return newStr;
        } else {
            return text;
        }
    }

}
