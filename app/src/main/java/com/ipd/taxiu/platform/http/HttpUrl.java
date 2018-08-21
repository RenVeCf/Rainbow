package com.ipd.taxiu.platform.http;

/**
 * Created by jumpbox on 16/5/2.
 */
public interface HttpUrl {
    String SERVER_URL = "http://121.199.8.244:9386/TX/";
    String IMAGE_URL = "http://pic.csjc19.com/";

    //account
    String REGISTER_SMS_CODE = "app_user/sendCode.do";
    String REGISTER = "app_user/register.do";
    String LOGIN = "app_user/login.do";
    String PHONE_LOGIN = "app_user/verifyCode.do";
    String PHONE_LOGIN_SMS_CODE = "app_user/sendCode2.do";
    String FORGET_PASSWORD = "app_user/forgetPwd.do";


    //pet
    String PET_STAGE = "app_user/updateStep.do";
    String PET_KIND_LIST = "app_pet/typeList.do";
    String PET_GET_LIST = "app_pet/getList.do";
    String PET_GET_INFO = "app_pet/getInfo.do";
    String PET_UPDATE= "app_pet/update.do";
    String PET_ADD= "app_pet/add.do";
    String PET_DELETE= "app_pet/delete.do";

    //signin
    String SIGN_IN_INFO = "app_sign/getInfo.do";
    String SIGN_IN_LIST = "app_sign/getList.do";
    String SIGN_IN = "app_sign/add.do";


    //tools
    String UPLOAD_PIC = "app_pic/uploadPic.do";

    //city
    String GET_LIST_ALL ="app_region/getListAll.do";

    //address
    String ADD_ADDRESS="app_address/add.do";
    String GET_LIST_ADDRESS="app_address/getList.do";
    String GET_ADDRESS_INFO="app_address/getInfo.do";
    String ADDRESS_UPDATE="app_address/update.do";
    String ADDRESS_DELETE="app_address/delete.do";

    //mine
    String GET_USER_INFO="app_user/getInfo.do";
    String UPDATE_PWD="app_user/updatePwd.do";
    String USER_UPDATE="app_user/update.do";
    String FRIEND_LIST="app_user/friendList.do";
    String ATTENTION="app_user/attention.do";
    String ATTENTION_LIST="app_user/attentionList.do";

    //常见问题
    String QUESTION_LIST="app_text/questionList.do";

    //积分列表
    String SCORE_LIST="app_score/getList.do";

    //text
    String TEXT_INFO="app_text/getInfo.do";


}
