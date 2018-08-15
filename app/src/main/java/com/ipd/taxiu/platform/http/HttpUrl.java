package com.ipd.taxiu.platform.http;

/**
 * Created by jumpbox on 16/5/2.
 */
public interface HttpUrl {
    String SERVER_URL = "http://121.199.8.244:9886/TX/";
    String IMAGE_URL = "http://121.199.8.244:9886/";


    //account
    String REGISTER_SMS_CODE = "app_user/sendCode.do";
    String REGISTER = "app_user/register.do";


    //tools
    String UPLOAD_PIC = "app_pic/uploadPic.do";


}
