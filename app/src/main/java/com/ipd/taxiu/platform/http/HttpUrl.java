package com.ipd.taxiu.platform.http;

/**
 * Created by jumpbox on 16/5/2.
 */
public interface HttpUrl {
    String SERVER_URL = "http://121.199.8.244:9386/TX/";
    String IMAGE_URL = "http://pic.csjc19.com/";
    String VIDEO_URL = "http://video.csjc19.com/";

    //account
    String REGISTER_SMS_CODE = "app_user/sendCode.do";
    String REGISTER = "app_user/register.do";
    String LOGIN = "app_user/login.do";
    String PHONE_LOGIN = "app_user/verifyCode.do";
    String PHONE_LOGIN_SMS_CODE = "app_user/sendCode2.do";
    String FORGET_PASSWORD = "app_user/forgetPwd.do";

    //home
    String HOME = "app_show/home.do";

    //pet
    String PET_STAGE = "app_user/updateStep.do";
    String PET_KIND_LIST = "app_pet/typeList.do";
    String PET_GET_LIST = "app_pet/getList.do";
    String PET_GET_INFO = "app_pet/getInfo.do";
    String PET_UPDATE = "app_pet/update.do";
    String PET_ADD = "app_pet/add.do";
    String PET_DELETE = "app_pet/delete.do";


    //store
    String STORE_GIFT = "app_shop/getGift.do";
    String STORE_GIFT_TAKE_IT = "app_shop/toGift.do";
    String STORE_INDEX = "app_shop/home.do";
    String STORE_SECOND_INDEX = "app_shop/typeArea.do";
    String STORE_GUESS_LIST = "app_shop/likeList.do";
    String STORE_PARENT_SHOP_TYPE = "app_shop_type/getList.do";
    String STORE_CHILD_SHOP_TYPE = "app_shop/shopType2.do";
    String STORE_BRAND_LIST = "app_brand/getList.do";
    String STORE_VIDEO_DETAIL = "app_tide_video/getInfo.do";
    String STORE_PRODUCT_DETAIL = "app_product/getInfo.do";
    String STORE_PRODUCT_PARAM = "app_product/proParam.do";
    String STORE_SEARCH_HISTORY = "app_product/searchHis.do";
    String STORE_CLEAR_SEARCH_HISTORY = "app_product/clearHis.do";
    String STORE_PRODUCT_LIST = "app_shop/productAll.do";
    String STORE_PRODUCT_EXPERT_SCREEN = "app_shop/proFilter.do";

    //store activity
    String STORE_TODAY_PRODUCT_FLASH_SALE = "app_activity/bestPrefer.do";
    String STORE_PRODUCT_FLASH_SALE = "app_activity/getList.do";
    String STORE_PRODUCT_FLASH_SALE_REMIND = "app_activity/toRemind.do";
    String STORE_CLEARANCE_PRODUCT = "app_activity/pullList.do";
    String STORE_NEW_PRODUCT = "app_activity/newList.do";

    //store video
    String STORE_TODAY_RECOMMEND_VIDEO = "app_tide_video/todayRecom.do";
    String STORE_VIDEO_TABS = "app_tide_video/videoGroup.do";
    String STORE_VIDEO_LIST = "app_tide_video/getList.do";

    //taxiu
    String TAXIU_LABLE_LIST = "app_show/tipList.do";
    String TAXIU_LIST = "app_show/getList.do";
    String PUBLISH_TAXIU = "app_show/add.do";
    String TAXIU_DETAIL = "app_show/getInfo.do";
    String TAXIU_COMMENT = "app_show/commentList.do";
    String TAXIU_TO_COMMENT = "app_show/toComment.do";
    String TAXIU_PRAISE = "app_show/toPraise.do";
    String TAXIU_COLLECT = "app_show/toCollect.do";
    String TAXIU_COMMENT_DETAIL = "app_show/getComment.do";
    String TAXIU_REPLY_LIST = "app_show/replyList.do";
    String TAXIU_REPLY_MORE = "app_show/replyMore.do";
    String TAXIU_FIRST_REPLY = "app_show/toReply.do";
    String TAXIU_SECOND_REPLY = "app_show/toReplyMore.do";
    String TAXIU_GET_PET = "app_show/getPet.do";

    //话题
    String TOPIC_LIST = "app_topic/getList.do";
    String TOPIC_DETAIL = "app_topic/getInfo.do";
    String TOPIC_COMMENT = "app_topic/commentList.do";
    String TOPIC_TO_COMMENT = "app_topic/toComment.do";
    String TOPIC_COMMENT_DETAIL = "app_topic/getComment.do";
    String TOPIC_REPLY_LIST = "app_topic/replyList.do";
    String TOPIC_REPLY_MORE = "app_topic/replyMore.do";
    String TOPIC_FIRST_REPLY = "app_topic/toReply.do";
    String TOPIC_SECOND_REPLY = "app_topic/toReplyMore.do";

    //问答
    String PUBLISH_TALK = "app_question/add.do";
    String TALK_LIST = "app_question/getList.do";
    String TALK_DETAIL = "app_question/getInfo.do";
    String TALK_COMMENT = "app_question/commentList.do";
    String TALK_TO_COMMENT = "app_question/toComment.do";
    String TALK_FIRST_REPLY = "app_question/toReply.do";
    String TALK_SECOND_REPLY = "app_question/toReplyMore.do";
    String TALK_REPLY_MORE = "app_question/replyMore.do";

    //课堂
    String CLASS_ROOM_LIST = "app_class_room/getList.do";
    String CLASS_ROOM_DETAIL = "app_class_room/getInfo.do";


    //signin
    String SIGN_IN_INFO = "app_sign/getInfo.do";
    String SIGN_IN_LIST = "app_sign/getList.do";
    String SIGN_IN = "app_sign/add.do";


    //tools
    String UPLOAD_PIC = "app_pic/uploadPic.do";
    String UPLOAD_VIDEO = "app_video/uploadVideo.do";

    //city
    String GET_LIST_ALL = "app_region/getListAll.do";

    //address
    String ADD_ADDRESS = "app_address/add.do";
    String GET_LIST_ADDRESS = "app_address/getList.do";
    String GET_ADDRESS_INFO = "app_address/getInfo.do";
    String ADDRESS_UPDATE = "app_address/update.do";
    String ADDRESS_DELETE = "app_address/delete.do";

    //mine
    String GET_USER_INFO = "app_user/getInfo.do";
    String UPDATE_PWD = "app_user/updatePwd.do";
    String USER_UPDATE = "app_user/update.do";
    String FRIEND_LIST = "app_user/friendList.do";
    String ATTENTION = "app_user/attention.do";
    String ATTENTION_LIST = "app_user/attentionList.do";
    String OTHER = "app_user/other.do";

    //常见问题
    String QUESTION_LIST = "app_text/questionList.do";

    //积分列表
    String SCORE_LIST = "app_score/getList.do";
    String SCORE_EXCHANGE_LIST = "app_score/exchangeList.do";
    String SCORE_COUPON_INFO = "app_score/couponInfo.do";
    String SCORE_TO_EXCHANGE = "app_score/toExchange.do";
    String SCORE_EXCHANGE_HIS = "app_score/exchangeHis.do";
    String SCORE_EXCHANGE_INFO = "app_score/exchangeInfo.do";
    String SCORE_COUPON_LIST = "app_score/couponList.do";

    //text
    String TEXT_INFO = "app_text/getInfo.do";


    //余额
    String BALANCE_INFO = "app_balance/balanceInfo.do";
    String BALANCE_BILL = "app_balance/getList.do";
    String BALANCE_WITHDRAW_HINT = "app_balance/balanceTip.do";
    String BALANCE_WITHDRAW = "app_balance/toCash.do";
    String BANK_TYPE_LIST = "app_bank_type/getList.do";
    String ADD_BANK_CARD = "app_bank_card/add.do";
    String CHANGE_BANK_CARD = "app_bank_card/update.do";
    String BANK_CARD_LIST = "app_bank_card/getList.do";
    String BANK_CARD_INFO = "app_bank_card/getInfo.do";


}
