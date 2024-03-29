package com.ipd.rainbow.platform.http;

/**
 * Created by jumpbox on 16/5/2.
 */
public interface HttpUrl {
    String BASE_URL = "https://rainbowgou.com/";
    String WEB_URL = "https://manage.csjc19.com:8080/";
    String SERVER_URL = BASE_URL + "Rainbow/";
    String IMAGE_URL = BASE_URL;
    String VIDEO_URL = "https://video.csjc19.com/";
    String APK_DOWNLOAD_URL = BASE_URL + "taxiu/download.html";

    //account
    String REGISTER_SMS_CODE = "app_user/sendCode.do";
    String REGISTER = "app_user/register.do";
    String LOGIN = "app_user/login.do";
    String PHONE_LOGIN = "app_user/verifyCode.do";
    String BINDING_PHONE = "app_user/bind.do";
    String BINDING_PHONE_SKIP_USER = "app_user/toBind.do";
    String SKIP_BINDING_PHONE = "app_user/jumpBind.do";
    String FORGET_PASSWORD = "app_user/forgetPwd.do";
    String THIRD_LOGIN = "app_user/open.do";

    //home
    String HOME = "app_show/home.do";

    //store
    String STORE_GIFT = "app_shop/getGift.do";
    String STORE_GIFT_TAKE_IT = "app_shop/toGift.do";
    String STORE_INDEX = "app_shop/home.do";
    String STORE_GUESS_LIST = "app_shop/getList.do";
    String STORE_PARENT_SHOP_TYPE = "app_shop_type/getList.do";
    String STORE_CHILD_SHOP_TYPE = "app_shop/shopType2.do";
    String STORE_BRAND_LIST = "app_brand/getList.do";
    String STORE_VIDEO_DETAIL = "app_tide_video/getInfo.do";
    String STORE_PRODUCT_DETAIL = "app_product/getInfo.do";
    String STORE_PRODUCT_PARAM = "app_product/proParam.do";
    String STORE_SEARCH_HISTORY = "app_product/searchHis.do";
    String STORE_CLEAR_SEARCH_HISTORY = "app_product/clearHis.do";
    String STORE_PRODUCT_LIST = "app_product/getList.do";
    String STORE_PRODUCT_EXPERT_SCREEN = "app_shop/proFilter.do";
    String STORE_PRODUCT_MODEL = "app_product/formList.do";
    String STORE_PRODUCT_COUPON = "app_product/couponList.do";
    String STORE_TAKE_IT_COUPON = "app_product/toExchange.do";
    String STORE_PRODUCT_COLLECT = "app_collect/toCollect.do";
    String STORE_AREA_INDEX = "app_shop/area.do";
    String STORE_PRODUCT_EVALUATE_LIST = "app_assess/getList.do";
    String STORE_PRODUCT_EVALUATE_DETAIL = "app_assess/getInfo.do";


    //cart
    String CART_ADD = "app_cart/add.do";
    String CART_LIST = "app_cart/getList.do";
    String CART_CHANGE = "app_cart/update.do";
    String CART_DELETE = "app_cart/delete.do";
    String CART_CASH = "app_cart/toCash.do";
    String CART_COUPON = "app_cart/couponList.do";
    String CART_CONFIRM_ORDER = "app_order/submit.do";
    String CART_RECOMMEND = "app_shop/recomList.do";


    //order
    String ORDER_LIST = "app_order/getList.do";
    String ORDER_DETAIL = "app_order/getInfo.do";
    String ORDER_CANCEL = "app_order/cancle.do";
    String ORDER_RECEIVED = "app_order/receive.do";
    String ORDER_DELETE = "app_order/delete.do";
    String ORDER_EVALUATE_PRODUCT_LIST = "app_order/assessPro.do";
    String ORDER_PUBLISH_EVALUATE = "app_assess/add.do";
    String ORDER_BALANCE = "app_pay/balance.do";
    String ORDER_ALIPAY = "app_pay/alipay.do";
    String ORDER_WECHAT = "app_pay/wechat.do";
    String ORDER_RETURN_INFO = "app_refund/detail.do";
    String ORDER_RETURN_REASON = "app_refund/typeList2.do";
    String ORDER_REQUEST_RETURN = "app_refund/add.do";
    String ORDER_BUY_AGAIN = "app_order/againBuy.do";

    //return
    String RETURN_LIST = "app_refund/getList.do";
    String RETURN_DETAIL = "app_refund/refundDetail.do";
    String RETURN_EXPRESS_INFO = "app_refund/info.do";
    String RETURN_COMMIT_EXPRESS = "app_refund/toPost.do";

    //store activity
    String STORE_PRODUCT_SALES = "app_product/activityList.do";
    String STORE_TODAY_PRODUCT_FLASH_SALE = "app_activity/bestPrefer.do";
    String STORE_PRODUCT_FLASH_SALE = "app_activity/getList.do";
    String STORE_PRODUCT_FLASH_SALE_REMIND = "app_activity/toRemind.do";
    String STORE_CLEARANCE_PRODUCT = "app_activity/pullList.do";
    String STORE_FLASH_SALE_TIME = "app_activity/rushTime.do";

    //拼团
    String STORE_SPELL = "app_product/teamList.do";
    String STORE_SPELL_CASH = "app_order/toTeamCash.do";
    String STORE_SPELL_CONFIRM_ORDER = "app_order/submitTeam.do";
    String SPELL_MINE_LIST = "app_team/selfList.do";
    String SPELL_ORDER_DETAIL = "app_team/getInfo.do";

    //taxiu
    String TAXIU_LIST = "app_show/getList.do";

    //signin
    String SIGN_IN_INFO = "app_sign/getInfo.do";
    String SIGN_IN_LIST = "app_sign/getList.do";
    String SIGN_IN = "app_sign/add.do";


    //tools
    String UPLOAD_PIC = "app_pic/uploadPic.do";
    String UPLOAD_VIDEO = "app_video/uploadVideo.do";
    String VERSION_CHECK = "app_version/getInfo.do";

    //city
    String GET_LIST_ALL = "app_region/getListAll.do";

    //address
    String ADD_ADDRESS = "app_address/add.do";
    String GET_LIST_ADDRESS = "app_address/getList.do";
    String GET_ADDRESS_INFO = "app_address/getInfo.do";
    String ADDRESS_UPDATE = "app_address/update.do";
    String ADDRESS_DELETE = "app_address/delete.do";
    //orderPeople
    String ADD_ORDER_PEOPLE = "app_subscriber/add.do";
    String GET_LIST_ORDER_PEOPLE = "app_subscriber/getList.do";
    String GET_ORDER_PEOPLE_INFO = "app_subscriber/getInfo.do";
    String ORDER_PEOPLE_UPDATE = "app_subscriber/update.do";
    String ORDER_PEOPLE_DELETE = "app_subscriber/delete.do";



    //mine
    String USER_HOME = "app_user/home.do";
    String GET_USER_INFO = "app_user/getInfo.do";
    String VIP_INFO = "app_user/userInfo.do";
    String VIP_PRICE_INFO = "app_user/upInfo.do";
    String RECHARGE_VIP = "app_user/goUp.do";
    String CLOSE_VIP_AUTO_PAY = "app_user/closeAutoPay.do";
    String UPDATE_PWD = "app_user/updatePwd.do";
    String USER_UPDATE = "app_user/update.do";
    String FRIEND_LIST = "app_user/friendList.do";
    String ATTENTION = "app_user/attention.do";
    String ATTENTION_LIST = "app_user/attentionList.do";
    String OTHER = "app_user/other.do";
    String OTHER_TAXIU = "app_show/other.do";

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
    String AVAILABLE_BALANCE = "app_user/validBalance.do";
    String AVAILABLE_INTEGRAL = "app_user/validScore.do";

    //收藏
    String COLLECT_PRODUCT = "app_product/collectList.do";

    //推荐
    String RECOMMEND_INFO = "app_user/recomInfo.do";
    String RECOMMEND_EARNINGS = "app_commend_income/getList.do";

    String WEB_INFO = "app_text/getInfo.do";

    //消息
    String NEWS = "app_news/getList.do";

    //分享
    String SHARE_TAXIU = "app_show/share.do";
    String SHARE_TOPIC = "app_topic/share.do";
    String SHARE_TALK = "app_question/share.do";
    String SHARE_CLASSROOM = "app_class_room/share.do";
    String SHARE_PRODUCT = "app_product/share.do";
    String SHARE_USER = "app_user/share.do";
    String SHARE_VIDEO = "app_tide_video/share.do";


    //html5
    String USER_AGENT = "upload/text/1.html";
    String ABOUT_US = "upload/text/2.html";
    String INTEGRAL_RULE = "upload/text/3.html";
    String VERSION_INFO = "upload/text/4.html";


}
