package com.ipd.taxiu.platform.http;


import com.ipd.taxiu.bean.AddressBean;
import com.ipd.taxiu.bean.AttentionBean;
import com.ipd.taxiu.bean.BalanceBillBean;
import com.ipd.taxiu.bean.BalanceResult;
import com.ipd.taxiu.bean.BankCardBean;
import com.ipd.taxiu.bean.BankTypeListBean;
import com.ipd.taxiu.bean.BaseResult;
import com.ipd.taxiu.bean.CartCashBean;
import com.ipd.taxiu.bean.CartProductBean;
import com.ipd.taxiu.bean.ClassRoomBean;
import com.ipd.taxiu.bean.ClassRoomOrderDetailBean;
import com.ipd.taxiu.bean.ClassRoomResult;
import com.ipd.taxiu.bean.CommentDetailBean;
import com.ipd.taxiu.bean.CommentResult;
import com.ipd.taxiu.bean.EarningsResult;
import com.ipd.taxiu.bean.EvaluateResult;
import com.ipd.taxiu.bean.ExchangeBean;
import com.ipd.taxiu.bean.ExchangeHisBean;
import com.ipd.taxiu.bean.FlashSaleProductBean;
import com.ipd.taxiu.bean.FlashSaleTimeBean;
import com.ipd.taxiu.bean.GroupBean;
import com.ipd.taxiu.bean.GroupOrderDetailBean;
import com.ipd.taxiu.bean.HomeResultBean;
import com.ipd.taxiu.bean.IntegralBean;
import com.ipd.taxiu.bean.LoginBean;
import com.ipd.taxiu.bean.MessageBean;
import com.ipd.taxiu.bean.MoreCommentReplyBean;
import com.ipd.taxiu.bean.OrderBean;
import com.ipd.taxiu.bean.OrderDetailBean;
import com.ipd.taxiu.bean.PayResult;
import com.ipd.taxiu.bean.PetBean;
import com.ipd.taxiu.bean.PetBibleBean;
import com.ipd.taxiu.bean.PetKindListBean;
import com.ipd.taxiu.bean.ProductBean;
import com.ipd.taxiu.bean.ProductBrandBean;
import com.ipd.taxiu.bean.ProductCategoryChildBean;
import com.ipd.taxiu.bean.ProductCategoryParentBean;
import com.ipd.taxiu.bean.ProductDetailBean;
import com.ipd.taxiu.bean.ProductEvaluateBean;
import com.ipd.taxiu.bean.ProductEvaluateLableBean;
import com.ipd.taxiu.bean.ProductModelResult;
import com.ipd.taxiu.bean.ProductParamBean;
import com.ipd.taxiu.bean.ProvinceBean;
import com.ipd.taxiu.bean.PurchaseProductBean;
import com.ipd.taxiu.bean.QuestionBean;
import com.ipd.taxiu.bean.RecommendEarningsBean;
import com.ipd.taxiu.bean.RecommendInfoBean;
import com.ipd.taxiu.bean.RegisterBean;
import com.ipd.taxiu.bean.ReturnBean;
import com.ipd.taxiu.bean.ReturnDetailBean;
import com.ipd.taxiu.bean.ReturnOrderInfoBean;
import com.ipd.taxiu.bean.ReturnReasonBean;
import com.ipd.taxiu.bean.ReturnResult;
import com.ipd.taxiu.bean.ScreenResult;
import com.ipd.taxiu.bean.ShowPetBean;
import com.ipd.taxiu.bean.SignInDayBean;
import com.ipd.taxiu.bean.SignInInfoBean;
import com.ipd.taxiu.bean.SignInResuleBean;
import com.ipd.taxiu.bean.StoreAreaIndexResultBean;
import com.ipd.taxiu.bean.StoreIndexResultBean;
import com.ipd.taxiu.bean.StoreSearchHistroyBean;
import com.ipd.taxiu.bean.StoreSecondIndexResultBean;
import com.ipd.taxiu.bean.StoreVideoBean;
import com.ipd.taxiu.bean.StoreVideoDetailBean;
import com.ipd.taxiu.bean.StoreVideoTabBean;
import com.ipd.taxiu.bean.TalkBean;
import com.ipd.taxiu.bean.TalkCommentBean;
import com.ipd.taxiu.bean.TalkDetailBean;
import com.ipd.taxiu.bean.TaxiuBean;
import com.ipd.taxiu.bean.TaxiuCommentBean;
import com.ipd.taxiu.bean.TaxiuDetailBean;
import com.ipd.taxiu.bean.TaxiuLableBean;
import com.ipd.taxiu.bean.TextBean;
import com.ipd.taxiu.bean.TopicBean;
import com.ipd.taxiu.bean.TopicCommentBean;
import com.ipd.taxiu.bean.TopicCommentReplyBean;
import com.ipd.taxiu.bean.TopicDetailBean;
import com.ipd.taxiu.bean.UpdatePwdBean;
import com.ipd.taxiu.bean.UploadResultBean;
import com.ipd.taxiu.bean.UserBean;
import com.ipd.taxiu.bean.UserHomeBean;
import com.ipd.taxiu.bean.VersionBean;
import com.ipd.taxiu.bean.WebBean;
import com.ipd.taxiu.bean.WechatBean;
import com.ipd.taxiu.bean.WithdrawHintBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import rx.Observable;

/**
 * Created by jumpbox on 2017/7/24.
 */

public interface ApiService {
    /**
     * city
     *
     * @param REGION_ID
     * @param USER_ID
     * @return
     */
    @FormUrlEncoded
    @POST(HttpUrl.GET_LIST_ALL)
    Observable<BaseResult<ArrayList<ProvinceBean>>> getListAll(@Field("REGION_ID") String REGION_ID,
                                                               @Field("USER_ID") String USER_ID);


    /**
     * account
     */
    @FormUrlEncoded
    @POST(HttpUrl.REGISTER_SMS_CODE)
    Observable<BaseResult<String>> registerSmsCode(@Field("PHONE") String PHONE,
                                                   @Field("TYPE") String TYPE);

    @FormUrlEncoded
    @POST(HttpUrl.REGISTER)
    Observable<BaseResult<RegisterBean>> register(@Field("CODE") String code,
                                                  @Field("PHONE") String PHONE,
                                                  @Field("PASSWORD") String PASSWORD,
                                                  @Field("RECOMMEND_CODE") String RECOMMEND_CODE);

    @FormUrlEncoded
    @POST(HttpUrl.LOGIN)
    Observable<BaseResult<LoginBean>> login(@Field("PHONE") String PHONE,
                                            @Field("PASSWORD") String PASSWORD);


    @FormUrlEncoded
    @POST(HttpUrl.PHONE_LOGIN_SMS_CODE)
    Observable<BaseResult<String>> phoneLoginSmsCode(@Field("PHONE") String PHONE,
                                                     @Field("TYPE") String TYPE);

    @FormUrlEncoded
    @POST(HttpUrl.BINDING_PHONE)
    Observable<BaseResult<LoginBean>> bindingPhone(@Field("PHONE") String PHONE,
                                                   @Field("CODE") String CODE,
                                                   @Field("TYPE") int TYPE,
                                                   @Field("OPENID") String OPENID,
                                                   @Field("LOGO") String LOGO,
                                                   @Field("NICKNAME") String NICKNAME);

    @FormUrlEncoded
    @POST(HttpUrl.SKIP_BINDING_PHONE)
    Observable<BaseResult<LoginBean>> skipBindingPhone(@Field("TYPE") int TYPE,
                                                       @Field("OPENID") String OPENID,
                                                       @Field("LOGO") String LOGO,
                                                       @Field("NICKNAME") String NICKNAME);

    @FormUrlEncoded
    @POST(HttpUrl.BINDING_PHONE_SKIP_USER)
    Observable<BaseResult<LoginBean>> BindingPhoneSkipUser(@Field("USER_ID") String USER_ID,
                                                           @Field("PHONE") String PHONE,
                                                           @Field("CODE") String CODE);

    @FormUrlEncoded
    @POST(HttpUrl.BINDING_PHONE_SMS_CODE)
    Observable<BaseResult<String>> bindingPhoneSmsCode(@Field("PHONE") String PHONE,
                                                       @Field("TYPE") String TYPE);

    @FormUrlEncoded
    @POST(HttpUrl.PHONE_LOGIN)
    Observable<BaseResult<LoginBean>> phoneLogin(@Field("CODE") String code,
                                                 @Field("PHONE") String PHONE);

    @FormUrlEncoded
    @POST(HttpUrl.FORGET_PASSWORD)
    Observable<BaseResult<LoginBean>> forgetPassword(@Field("CODE") String code,
                                                     @Field("PHONE") String PHONE,
                                                     @Field("PASSWORD") String PASSWORD);

    @FormUrlEncoded
    @POST(HttpUrl.PET_STAGE)
    Observable<BaseResult<LoginBean>> petStage(@Field("USER_ID") String USER_ID,
                                               @Field("STEP") String STEP);

    @FormUrlEncoded
    @POST(HttpUrl.THIRD_LOGIN)
    Observable<BaseResult<LoginBean>> thirdLogin(@Field("OPENID") String OPENID,
                                                 @Field("TYPE") String TYPE);


    /**
     * home
     */
    @FormUrlEncoded
    @POST(HttpUrl.HOME)
    Observable<BaseResult<HomeResultBean>> home(@Field("USER_ID") String USER_ID);

    /**
     * pet
     */

    @FormUrlEncoded
    @POST(HttpUrl.PET_KIND_LIST)
    Observable<BaseResult<List<PetKindListBean>>> petKindList(@Field("USER_ID") String USER_ID,
                                                              @Field("CATEGORY") String CATEGORY);

    @FormUrlEncoded
    @POST(HttpUrl.PET_GET_LIST)
    Observable<BaseResult<List<PetBean>>> petGetList(@Field("COUNT") int COUNT,
                                                     @Field("PAGE") int PAGE,
                                                     @Field("USER_ID") String USER_ID);

    @FormUrlEncoded
    @POST(HttpUrl.PET_GET_INFO)
    Observable<BaseResult<PetBean>> petGetInfo(@Field("PET_ID") int PET_ID,
                                               @Field("USER_ID") String USER_ID);

    @FormUrlEncoded
    @POST(HttpUrl.PET_DELETE)
    Observable<BaseResult<PetBean>> petDelete(@Field("PET_ID") int PET_ID,
                                              @Field("USER_ID") String USER_ID);

    @FormUrlEncoded
    @POST(HttpUrl.PET_UPDATE)
    Observable<BaseResult<PetBean>> petUpdate(@Field("BIRTHDAY") String BIRTHDAY,
                                              @Field("GENDER") int GENDER,
                                              @Field("LOGO") String LOGO,
                                              @Field("NICKNAME") String NICKNAME,
                                              @Field("PET_TYPE_ID") int PET_TYPE_ID,
                                              @Field("STATUS") int STATUS,
                                              @Field("PET_ID") int PET_ID,
                                              @Field("USER_ID") String USER_ID,
                                              @Field("CATEGORY") int CATEGORY);

    @FormUrlEncoded
    @POST(HttpUrl.PET_ADD)
    Observable<BaseResult<PetBean>> petAdd(@Field("BIRTHDAY") String BIRTHDAY,
                                           @Field("GENDER") int GENDER,
                                           @Field("LOGO") String LOGO,
                                           @Field("NICKNAME") String NICKNAME,
                                           @Field("PET_TYPE_ID") int PET_TYPE_ID,
                                           @Field("STATUS") int STATUS,
                                           @Field("USER_ID") String USER_ID);

    /**
     * sign in
     */
    @FormUrlEncoded
    @POST(HttpUrl.SIGN_IN_INFO)
    Observable<BaseResult<SignInInfoBean>> signInInfo(@Field("USER_ID") String USER_ID);

    @FormUrlEncoded
    @POST(HttpUrl.SIGN_IN_LIST)
    Observable<BaseResult<List<SignInDayBean>>> signInList(@Field("USER_ID") String USER_ID);

    @FormUrlEncoded
    @POST(HttpUrl.SIGN_IN)
    Observable<BaseResult<SignInResuleBean>> signIn(@Field("USER_ID") String USER_ID);

    /**
     * 它秀
     */

    @FormUrlEncoded
    @POST(HttpUrl.TAXIU_LIST)
    Observable<BaseResult<List<TaxiuBean>>> taxiuList(@Field("USER_ID") String USER_ID,
                                                      @Field("COUNT") int COUNT,
                                                      @Field("PAGE") int PAGE,
                                                      @Field("TYPE") int TYPE,
                                                      @Field("KEYWORDS") String KEYWORDS);

    @FormUrlEncoded
    @POST(HttpUrl.TAXIU_MINE_LIST)
    Observable<BaseResult<List<TaxiuBean>>> mineTaxiuList(@Field("USER_ID") String USER_ID,
                                                          @Field("COUNT") int COUNT,
                                                          @Field("PAGE") int PAGE,
                                                          @Field("TYPE") int TYPE);

    @FormUrlEncoded
    @POST(HttpUrl.TAXIU_DELETE)
    Observable<BaseResult<TaxiuBean>> taxiuDelete(@Field("USER_ID") String USER_ID,
                                                  @Field("SHOW_ID") int SHOW_ID);

    @FormUrlEncoded
    @POST(HttpUrl.TAXIU_LABLE_LIST)
    Observable<BaseResult<List<TaxiuLableBean>>> taxiuLableList(@Field("USER_ID") String USER_ID);

    @FormUrlEncoded
    @POST(HttpUrl.PUBLISH_TAXIU)
    Observable<BaseResult<TaxiuLableBean>> publishTaxiu(@Field("USER_ID") String USER_ID,
                                                        @Field("CONTENT") String CONTENT,
                                                        @Field("LOGO") String LOGO,
                                                        @Field("PIC") String PIC,
                                                        @Field("SHOW_TIP_ID") String SHOW_TIP_ID,
                                                        @Field("TYPE") String TYPE,
                                                        @Field("URL") String URL,
                                                        @Field("WIDTH") String WIDTH,
                                                        @Field("HEIGHT") String HEIGHT);

    @FormUrlEncoded
    @POST(HttpUrl.TAXIU_DETAIL)
    Observable<BaseResult<TaxiuDetailBean>> taxiuDetail(@Field("USER_ID") String USER_ID,
                                                        @Field("SHOW_ID") int SHOW_ID);

    @FormUrlEncoded
    @POST(HttpUrl.TAXIU_COMMENT)
    Observable<CommentResult<List<TaxiuCommentBean>>> taxiuComment(@Field("USER_ID") String USER_ID,
                                                                   @Field("COUNT") int COUNT,
                                                                   @Field("PAGE") int PAGE,
                                                                   @Field("TYPE") int TYPE,
                                                                   @Field("SHOW_ID") int SHOW_ID);

    @FormUrlEncoded
    @POST(HttpUrl.TAXIU_TO_COMMENT)
    Observable<CommentResult<TaxiuCommentBean>> taxiuToComment(@Field("USER_ID") String USER_ID,
                                                               @Field("CONTENT") String CONTENT,
                                                               @Field("PIC") String PIC,
                                                               @Field("SHOW_ID") int SHOW_ID);

    @FormUrlEncoded
    @POST(HttpUrl.TAXIU_PRAISE)
    Observable<BaseResult<TaxiuDetailBean>> taxiuPraise(@Field("USER_ID") String USER_ID,
                                                        @Field("CATEGORY") String CATEGORY,
                                                        @Field("MODULE_ID") int MODULE_ID);

    @FormUrlEncoded
    @POST(HttpUrl.TAXIU_COLLECT)
    Observable<BaseResult<TaxiuDetailBean>> taxiuCollect(@Field("USER_ID") String USER_ID,
                                                         @Field("CATEGORY") String CATEGORY,
                                                         @Field("MODULE_ID") int MODULE_ID);

    @FormUrlEncoded
    @POST(HttpUrl.TAXIU_COMMENT_DETAIL)
    Observable<BaseResult<CommentDetailBean>> taxiuCommentDetail(@Field("USER_ID") String USER_ID,
                                                                 @Field("COMMENT_ID") int COMMENT_ID);

    @FormUrlEncoded
    @POST(HttpUrl.TAXIU_REPLY_LIST)
    Observable<BaseResult<List<TopicCommentReplyBean>>> taxiuReplyList(@Field("USER_ID") String USER_ID,
                                                                       @Field("COUNT") int COUNT,
                                                                       @Field("PAGE") int PAGE,
                                                                       @Field("COMMENT_ID") int COMMENT_ID);

    @FormUrlEncoded
    @POST(HttpUrl.TAXIU_REPLY_MORE)
    Observable<BaseResult<MoreCommentReplyBean>> taxiuReplyMore(@Field("USER_ID") String USER_ID,
                                                                @Field("REPLY_ID") int REPLY_ID);

    @FormUrlEncoded
    @POST(HttpUrl.TAXIU_FIRST_REPLY)
    Observable<BaseResult<MoreCommentReplyBean>> taxiuFirstReply(@Field("USER_ID") String USER_ID,
                                                                 @Field("COMMENT_ID") int COMMENT_ID,
                                                                 @Field("CONTENT") String CONTENT);

    @FormUrlEncoded
    @POST(HttpUrl.TAXIU_SECOND_REPLY)
    Observable<BaseResult<MoreCommentReplyBean>> taxiuSecondReply(@Field("USER_ID") String USER_ID,
                                                                  @Field("REPLY_ID") int REPLY_ID,
                                                                  @Field("TARGET_ID") int TARGET_ID,
                                                                  @Field("CONTENT") String CONTENT);

    @FormUrlEncoded
    @POST(HttpUrl.TAXIU_GET_PET)
    Observable<BaseResult<ShowPetBean>> taxiuShowPet(@Field("USER_ID") String USER_ID);


    /**
     * 话题
     */

    @FormUrlEncoded
    @POST(HttpUrl.TOPIC_LIST)
    Observable<BaseResult<List<TopicBean>>> topicList(@Field("USER_ID") String USER_ID,
                                                      @Field("COUNT") int COUNT,
                                                      @Field("PAGE") int PAGE,
                                                      @Field("TYPE") int TYPE,
                                                      @Field("KEYWORDS") String KEYWORDS);


    @FormUrlEncoded
    @POST(HttpUrl.TOPIC_DETAIL)
    Observable<BaseResult<TopicDetailBean>> topicDetail(@Field("USER_ID") String USER_ID,
                                                        @Field("TOPIC_ID") int TOPIC_ID);


    @FormUrlEncoded
    @POST(HttpUrl.TOPIC_COMMENT)
    Observable<CommentResult<List<TopicCommentBean>>> topicComment(@Field("USER_ID") String USER_ID,
                                                                   @Field("COUNT") int COUNT,
                                                                   @Field("PAGE") int PAGE,
                                                                   @Field("TYPE") int TYPE,
                                                                   @Field("TOPIC_ID") int TOPIC_ID);

    @FormUrlEncoded
    @POST(HttpUrl.TOPIC_TO_COMMENT)
    Observable<BaseResult<TopicCommentBean>> topicToComment(@Field("USER_ID") String USER_ID,
                                                            @Field("CONTENT") String CONTENT,
                                                            @Field("PIC") String PIC,
                                                            @Field("TOPIC_ID") int TOPIC_ID);

    @FormUrlEncoded
    @POST(HttpUrl.TOPIC_COMMENT_DETAIL)
    Observable<BaseResult<CommentDetailBean>> topicCommentDetail(@Field("USER_ID") String USER_ID,
                                                                 @Field("PARTAKE_ID") int PARTAKE_ID);


    @FormUrlEncoded
    @POST(HttpUrl.TOPIC_REPLY_LIST)
    Observable<BaseResult<List<TopicCommentReplyBean>>> topicReplyList(@Field("USER_ID") String USER_ID,
                                                                       @Field("COUNT") int COUNT,
                                                                       @Field("PAGE") int PAGE,
                                                                       @Field("PARTAKE_ID") int PARTAKE_ID);


    @FormUrlEncoded
    @POST(HttpUrl.TOPIC_REPLY_MORE)
    Observable<BaseResult<MoreCommentReplyBean>> topicReplyMore(@Field("USER_ID") String USER_ID,
                                                                @Field("REPLY_ID") int REPLY_ID);

    @FormUrlEncoded
    @POST(HttpUrl.TOPIC_FIRST_REPLY)
    Observable<BaseResult<MoreCommentReplyBean>> topicFirstReply(@Field("USER_ID") String USER_ID,
                                                                 @Field("PARTAKE_ID") int PARTAKE_ID,
                                                                 @Field("CONTENT") String CONTENT);

    @FormUrlEncoded
    @POST(HttpUrl.TOPIC_SECOND_REPLY)
    Observable<BaseResult<MoreCommentReplyBean>> topicSecondReply(@Field("USER_ID") String USER_ID,
                                                                  @Field("REPLY_ID") int REPLY_ID,
                                                                  @Field("TARGET_ID") int TARGET_ID,
                                                                  @Field("CONTENT") String CONTENT);

    /**
     * 问答
     */

    @FormUrlEncoded
    @POST(HttpUrl.TALK_LIST)
    Observable<BaseResult<List<TalkBean>>> talkList(@Field("USER_ID") String USER_ID,
                                                    @Field("COUNT") int COUNT,
                                                    @Field("PAGE") int PAGE,
                                                    @Field("TYPE") int TYPE,
                                                    @Field("KEYWORDS") String KEYWORDS,
                                                    @Field("CATEGORY") String CATEGORY);

    @FormUrlEncoded
    @POST(HttpUrl.TALK_MINE_LIST)
    Observable<BaseResult<List<TalkBean>>> mineTalkList(@Field("USER_ID") String USER_ID,
                                                        @Field("COUNT") int COUNT,
                                                        @Field("PAGE") int PAGE,
                                                        @Field("TYPE") int TYPE);


    @FormUrlEncoded
    @POST(HttpUrl.PUBLISH_TALK)
    Observable<BaseResult<TalkBean>> publishTalk(@Field("USER_ID") String USER_ID,
                                                 @Field("CONTENT") String CONTENT,
                                                 @Field("SCORE") String SCORE);

    @FormUrlEncoded
    @POST(HttpUrl.TALK_BEST_ANSWER)
    Observable<BaseResult<TalkBean>> bestAnswer(@Field("USER_ID") String USER_ID,
                                                @Field("QUESTION_ID") int QUESTION_ID,
                                                @Field("ANSWER_ID") int ANSWER_ID);


    @FormUrlEncoded
    @POST(HttpUrl.TALK_DETAIL)
    Observable<BaseResult<TalkDetailBean>> talkDetail(@Field("USER_ID") String USER_ID,
                                                      @Field("QUESTION_ID") int QUESTION_ID);


    @FormUrlEncoded
    @POST(HttpUrl.TALK_COMMENT)
    Observable<CommentResult<List<TalkCommentBean>>> talkComment(@Field("USER_ID") String USER_ID,
                                                                 @Field("COUNT") int COUNT,
                                                                 @Field("PAGE") int PAGE,
                                                                 @Field("TYPE") int TYPE,
                                                                 @Field("QUESTION_ID") int QUESTION_ID);

    @FormUrlEncoded
    @POST(HttpUrl.TALK_TO_COMMENT)
    Observable<BaseResult<TalkCommentBean>> talkToComment(@Field("USER_ID") String USER_ID,
                                                          @Field("CONTENT") String CONTENT,
                                                          @Field("QUESTION_ID") int QUESTION_ID);


    @FormUrlEncoded
    @POST(HttpUrl.TALK_FIRST_REPLY)
    Observable<BaseResult<MoreCommentReplyBean>> talkFirstReply(@Field("USER_ID") String USER_ID,
                                                                @Field("ANSWER_ID") int ANSWER_ID,
                                                                @Field("CONTENT") String CONTENT);

    @FormUrlEncoded
    @POST(HttpUrl.TALK_FIRST_REPLY)
    Observable<BaseResult<MoreCommentReplyBean>> talkSecondReply(@Field("USER_ID") String USER_ID,
                                                                 @Field("ANSWER_ID") int ANSWER_ID,
                                                                 @Field("TARGET_ID") int TARGET_ID,
                                                                 @Field("CONTENT") String CONTENT);


    @FormUrlEncoded
    @POST(HttpUrl.TALK_REPLY_MORE)
    Observable<BaseResult<MoreCommentReplyBean>> talkReplyMore(@Field("USER_ID") String USER_ID,
                                                               @Field("ANSWER_ID") int ANSWER_ID);


    @FormUrlEncoded
    @POST(HttpUrl.TALK_DELETE)
    Observable<BaseResult<TalkBean>> talkDelete(@Field("USER_ID") String USER_ID,
                                                @Field("QUESTION_ID") int QUESTION_ID);

    /**
     * 课堂
     */

    @FormUrlEncoded
    @POST(HttpUrl.CLASS_ROOM_LIST)
    Observable<BaseResult<List<ClassRoomBean>>> classroomList(@Field("USER_ID") String USER_ID,
                                                              @Field("COUNT") int COUNT,
                                                              @Field("PAGE") int PAGE,
                                                              @Field("KEYWORDS") String KEYWORDS);

    @FormUrlEncoded
    @POST(HttpUrl.CLASS_ROOM_BUYED_LIST)
    Observable<BaseResult<List<ClassRoomBean>>> classroomBuyedList(@Field("USER_ID") String USER_ID,
                                                                   @Field("COUNT") int COUNT,
                                                                   @Field("PAGE") int PAGE);

    @FormUrlEncoded
    @POST(HttpUrl.CLASS_ROOM_DETAIL)
    Observable<BaseResult<ClassRoomBean>> classroomDetail(@Field("USER_ID") String USER_ID,
                                                          @Field("CLASS_ROOM_ID") int CLASS_ROOM_ID);


    @FormUrlEncoded
    @POST(HttpUrl.CLASS_ROOM_PAY)
    Observable<ClassRoomResult<String>> classroomBalance(@Field("USER_ID") String user_id,
                                                         @Field("CLASS_ROOM_ID") int CLASS_ROOM_ID,
                                                         @Field("PAYWAY") int PAYWAY);

    @FormUrlEncoded
    @POST(HttpUrl.CLASS_ROOM_PAY)
    Observable<ClassRoomResult<String>> classroomAlipay(@Field("USER_ID") String user_id,
                                                        @Field("CLASS_ROOM_ID") int CLASS_ROOM_ID,
                                                        @Field("PAYWAY") int PAYWAY);

    @FormUrlEncoded
    @POST(HttpUrl.CLASS_ROOM_PAY)
    Observable<ClassRoomResult<WechatBean>> classroomWechat(@Field("USER_ID") String user_id,
                                                            @Field("CLASS_ROOM_ID") int CLASS_ROOM_ID,
                                                            @Field("PAYWAY") int PAYWAY);

    @FormUrlEncoded
    @POST(HttpUrl.CLASS_ROOM_ORDER_DETAIL)
    Observable<BaseResult<ClassRoomOrderDetailBean>> classroomOrderDetail(@Field("USER_ID") String user_id,
                                                                          @Field("ORDER_NO") String ORDER_NO);

    /**
     * store
     */

    @FormUrlEncoded
    @POST(HttpUrl.STORE_GIFT)
    Observable<BaseResult<StoreIndexResultBean>> storeGift(@Field("USER_ID") String USER_ID);

    @FormUrlEncoded
    @POST(HttpUrl.STORE_GIFT_TAKE_IT)
    Observable<BaseResult<List<ExchangeHisBean>>> storeGiftTakeIt(@Field("USER_ID") String USER_ID);

    @FormUrlEncoded
    @POST(HttpUrl.STORE_INDEX)
    Observable<BaseResult<StoreIndexResultBean>> storeIndex(@Field("USER_ID") String USER_ID,
                                                            @Field("CATEGORY") int CATEGORY);

    @FormUrlEncoded
    @POST(HttpUrl.STORE_SECOND_INDEX)
    Observable<BaseResult<StoreSecondIndexResultBean>> storeSecondIndex(@Field("USER_ID") String USER_ID,
                                                                        @Field("CATEGORY") int CATEGORY,
                                                                        @Field("AREA_ID") int AREA_ID);


    @FormUrlEncoded
    @POST(HttpUrl.STORE_GUESS_LIST)
    Observable<BaseResult<List<ProductBean>>> storeGuessLike(@Field("CATEGORY") int CATEGORY,
                                                             @Field("AREA_ID") int AREA_ID,
                                                             @Field("COUNT") int count,
                                                             @Field("USER_ID") String user_id,
                                                             @Field("PAGE") int page);

    @FormUrlEncoded
    @POST(HttpUrl.STORE_PARENT_SHOP_TYPE)
    Observable<BaseResult<List<ProductCategoryParentBean>>> storeParentShopType(@Field("USER_ID") String USER_ID,
                                                                                @Field("CATEGORY") int CATEGORY);

    @FormUrlEncoded
    @POST(HttpUrl.STORE_CHILD_SHOP_TYPE)
    Observable<BaseResult<ProductCategoryChildBean>> storeChildShopType(@Field("USER_ID") String USER_ID,
                                                                        @Field("TYPE_ID") String TYPE_ID);

    @FormUrlEncoded
    @POST(HttpUrl.STORE_BRAND_LIST)
    Observable<BaseResult<List<ProductBrandBean>>> storeBrandList(@Field("USER_ID") String user_id,
                                                                  @Field("TYPE") int TYPE);

    @FormUrlEncoded
    @POST(HttpUrl.STORE_VIDEO_DETAIL)
    Observable<BaseResult<StoreVideoDetailBean>> storeVideoDetail(@Field("VIDEO_ID") String VIDEO_ID,
                                                                  @Field("USER_ID") String user_id);

    @FormUrlEncoded
    @POST(HttpUrl.STORE_PRODUCT_DETAIL)
    Observable<BaseResult<ProductDetailBean>> storeProductDetail(@Field("USER_ID") String user_id,
                                                                 @Field("PRODUCT_ID") int PRODUCT_ID,
                                                                 @Field("FORM_ID") int FORM_ID);

    @FormUrlEncoded
    @POST(HttpUrl.STORE_PRODUCT_PARAM)
    Observable<BaseResult<List<ProductParamBean>>> storeProductParam(@Field("USER_ID") String user_id,
                                                                     @Field("PRODUCT_ID") int PRODUCT_ID,
                                                                     @Field("FORM_ID") int FORM_ID);

    @FormUrlEncoded
    @POST(HttpUrl.STORE_SEARCH_HISTORY)
    Observable<StoreSearchHistroyBean> storeSearchHistory(@Field("USER_ID") String user_id);

    @FormUrlEncoded
    @POST(HttpUrl.STORE_CLEAR_SEARCH_HISTORY)
    Observable<BaseResult<ProductDetailBean>> storeClearSearchHistory(@Field("USER_ID") String user_id);

    @FormUrlEncoded
    @POST(HttpUrl.STORE_PRODUCT_LIST)
    Observable<BaseResult<List<ProductBean>>> storeProductList(@Field("USER_ID") String user_id,
                                                               @Field("COUNT") int COUNT,
                                                               @Field("PAGE") int PAGE,
                                                               @Field("BRAND") String BRAND,
                                                               @Field("COMPOSITE") int COMPOSITE,
                                                               @Field("KEYWORDS") String KEYWORDS,
                                                               @Field("MAX_PRICE") float MAX_PRICE,
                                                               @Field("MIN_PRICE") float MIN_PRICE,
                                                               @Field("PRICE_SORT") int PRICE_SORT,
                                                               @Field("SALES") int SALES,
                                                               @Field("APPLY") String APPLY,
                                                               @Field("SIZE") String SIZE,
                                                               @Field("PET_TYPE") String PET_TYPE,
                                                               @Field("NET_CONTENT") String NET_CONTENT,
                                                               @Field("TASTE") String TASTE,
                                                               @Field("COUNTRY") String COUNTRY,
                                                               @Field("THING_TYPE") String THING_TYPE,
                                                               @Field("AREA_TYPE_ID") int AREA_TYPE_ID,
                                                               @Field("SHOP_TYPE_ID") int SHOP_TYPE_ID);

    @FormUrlEncoded
    @POST(HttpUrl.STORE_PRODUCT_EXPERT_SCREEN)
    Observable<ScreenResult> storeProductExpertScreen(@Field("USER_ID") String user_id,
                                                      @Field("KEYWORDS") String KEYWORDS,
                                                      @Field("AREA_ID") int AREA_ID,
                                                      @Field("TYPE_ID") int TYPE_ID);

    @FormUrlEncoded
    @POST(HttpUrl.STORE_PRODUCT_MODEL)
    Observable<ProductModelResult> storeProductModel(@Field("USER_ID") String user_id,
                                                     @Field("PRODUCT_ID") int PRODUCT_ID,
                                                     @Field("FORM_ID") int FORM_ID,
                                                     @Field("ACTIVITY_ID") int ACTIVITY_ID);

    @FormUrlEncoded
    @POST(HttpUrl.STORE_PRODUCT_COUPON)
    Observable<BaseResult<List<ExchangeBean>>> storeProductCoupon(@Field("USER_ID") String user_id,
                                                                  @Field("PRODUCT_ID") int PRODUCT_ID,
                                                                  @Field("FORM_ID") int FORM_ID);

    @FormUrlEncoded
    @POST(HttpUrl.STORE_TAKE_IT_COUPON)
    Observable<BaseResult<ExchangeBean>> takeItCoupon(@Field("USER_ID") String user_id,
                                                      @Field("COUPON_ID") int COUPON_ID);

    @FormUrlEncoded
    @POST(HttpUrl.STORE_PRODUCT_COLLECT)
    Observable<BaseResult<ExchangeBean>> storeProductCollect(@Field("USER_ID") String user_id,
                                                             @Field("PRODUCT_ID") int PRODUCT_ID,
                                                             @Field("FORM_ID") int FORM_ID);

    @FormUrlEncoded
    @POST(HttpUrl.STORE_AREA_INDEX)
    Observable<BaseResult<StoreAreaIndexResultBean>> storeAreaIndex(@Field("USER_ID") String user_id,
                                                                    @Field("SHOP_TYPE_ID") int SHOP_TYPE_ID);

    @FormUrlEncoded
    @POST(HttpUrl.STORE_PRODUCT_EVALUATE_LIST)
    Observable<EvaluateResult<List<ProductEvaluateBean>>> storeProductEvaluateList(@Field("USER_ID") String user_id,
                                                                                   @Field("PRODUCT_ID") int PRODUCT_ID,
                                                                                   @Field("FORM_ID") int FORM_ID,
                                                                                   @Field("TYPE") int TYPE,
                                                                                   @Field("PAGE") int PAGE,
                                                                                   @Field("COUNT") int COUNT);

    @FormUrlEncoded
    @POST(HttpUrl.STORE_PRODUCT_EVALUATE_LABLE_LIST)
    Observable<BaseResult<List<ProductEvaluateLableBean>>> storeProductEvaluateLable(@Field("USER_ID") String user_id,
                                                                                     @Field("PRODUCT_ID") int PRODUCT_ID,
                                                                                     @Field("FORM_ID") int FORM_ID);


    /**
     * 商品活动
     */
    @FormUrlEncoded
    @POST(HttpUrl.STORE_TODAY_PRODUCT_FLASH_SALE)
    Observable<BaseResult<FlashSaleProductBean>> storeTodayProductFlashSale(@Field("USER_ID") String user_id);

    @FormUrlEncoded
    @POST(HttpUrl.STORE_PRODUCT_FLASH_SALE)
    Observable<BaseResult<List<FlashSaleProductBean>>> storeProductFlashSale(@Field("USER_ID") String user_id,
                                                                             @Field("COUNT") int COUNT,
                                                                             @Field("PAGE") int PAGE,
                                                                             @Field("TYPE") int TYPE);

    @FormUrlEncoded
    @POST(HttpUrl.STORE_PRODUCT_FLASH_SALE_REMIND)
    Observable<BaseResult<FlashSaleProductBean>> storeProductFlashSaleRemind(@Field("USER_ID") String user_id,
                                                                             @Field("PRODUCT_ID") int PRODUCT_ID,
                                                                             @Field("FORM_ID") int FORM_ID);

    @FormUrlEncoded
    @POST(HttpUrl.STORE_CLEARANCE_PRODUCT)
    Observable<BaseResult<List<ProductBean>>> storeProductClearance(@Field("USER_ID") String user_id,
                                                                    @Field("COUNT") int COUNT,
                                                                    @Field("PAGE") int PAGE,
                                                                    @Field("CATEGORY") int CATEGORY);

    @FormUrlEncoded
    @POST(HttpUrl.STORE_NEW_PRODUCT)
    Observable<BaseResult<List<ProductBean>>> storeProductNew(@Field("USER_ID") String user_id,
                                                              @Field("COUNT") int COUNT,
                                                              @Field("PAGE") int PAGE);

    @FormUrlEncoded
    @POST(HttpUrl.STORE_FLASH_SALE_TIME)
    Observable<BaseResult<List<FlashSaleTimeBean>>> storeFlashSaleTime(@Field("USER_ID") String user_id);

    /**
     * store video
     */
    @FormUrlEncoded
    @POST(HttpUrl.STORE_TODAY_RECOMMEND_VIDEO)
    Observable<BaseResult<StoreVideoBean>> storeTodayRecommendVideo(@Field("USER_ID") String user_id,
                                                                    @Field("CATEGORY") int CATEGORY);

    @FormUrlEncoded
    @POST(HttpUrl.STORE_VIDEO_TABS)
    Observable<BaseResult<List<StoreVideoTabBean>>> storeVideoTabs(@Field("USER_ID") String user_id,
                                                                   @Field("CATEGORY") int CATEGORY);

    @FormUrlEncoded
    @POST(HttpUrl.STORE_VIDEO_LIST)
    Observable<BaseResult<List<StoreVideoBean>>> storeVideoList(@Field("USER_ID") String user_id,
                                                                @Field("CATEGORY") int CATEGORY,
                                                                @Field("COUNT") int COUNT,
                                                                @Field("PAGE") int PAGE,
                                                                @Field("SHOP_TYPE_ID") int SHOP_TYPE_ID);

    /**
     * 拼团
     */
    @FormUrlEncoded
    @POST(HttpUrl.STORE_SPELL)
    Observable<BaseResult<List<PurchaseProductBean>>> storeSpell(@Field("USER_ID") String user_id,
                                                                 @Field("COUNT") int COUNT,
                                                                 @Field("PAGE") int PAGE);


    @FormUrlEncoded
    @POST(HttpUrl.STORE_SPELL_CASH)
    Observable<BaseResult<CartCashBean>> spellCash(@Field("USER_ID") String user_id,
                                                   @Field("ADDRESS_ID") int ADDRESS_ID,
                                                   @Field("USE_COUPON") int USE_COUPON,
                                                   @Field("EXCHANGE_ID") int EXCHANGE_ID,
                                                   @Field("NUM") int NUM,
                                                   @Field("ACTIVITY_ID") int ACTIVITY_ID,
                                                   @Field("PRODUCT_ID") int PRODUCT_ID,
                                                   @Field("FORM_ID") int FORM_ID);


    @FormUrlEncoded
    @POST(HttpUrl.STORE_SPELL_CONFIRM_ORDER)
    Observable<PayResult<WechatBean>> spellConfirmWechat(@Field("USER_ID") String user_id,
                                                         @Field("ACTIVITY_ID") int ACTIVITY_ID,
                                                         @Field("ADDRESS_ID") String ADDRESS_ID,
                                                         @Field("INVOICE_HEAD") String INVOICE_HEAD,
                                                         @Field("INVOICE_NUM") String INVOICE_NUM,
                                                         @Field("INVOICE_TYPE") int INVOICE_TYPE,
                                                         @Field("PAYWAY") int PAYWAY,
                                                         @Field("USE_COUPON") int USE_COUPON,
                                                         @Field("EXCHANGE_ID") int EXCHANGE_ID,
                                                         @Field("NUM") int NUM,
                                                         @Field("PRODUCT_ID") int PRODUCT_ID,
                                                         @Field("FORM_ID") int FORM_ID);

    @FormUrlEncoded
    @POST(HttpUrl.STORE_SPELL_CONFIRM_ORDER)
    Observable<PayResult<String>> spellConfirm(@Field("USER_ID") String user_id,
                                               @Field("ACTIVITY_ID") int ACTIVITY_ID,
                                               @Field("ADDRESS_ID") String ADDRESS_ID,
                                               @Field("INVOICE_HEAD") String INVOICE_HEAD,
                                               @Field("INVOICE_NUM") String INVOICE_NUM,
                                               @Field("INVOICE_TYPE") int INVOICE_TYPE,
                                               @Field("PAYWAY") int PAYWAY,
                                               @Field("USE_COUPON") int USE_COUPON,
                                               @Field("EXCHANGE_ID") int EXCHANGE_ID,
                                               @Field("NUM") int NUM,
                                               @Field("PRODUCT_ID") int PRODUCT_ID,
                                               @Field("FORM_ID") int FORM_ID);

    @FormUrlEncoded
    @POST(HttpUrl.SPELL_MINE_LIST)
    Observable<BaseResult<List<GroupBean>>> mineSpellList(@Field("USER_ID") String user_id,
                                                          @Field("PAGE") int PAGE,
                                                          @Field("COUNT") int COUNT,
                                                          @Field("TYPE") int TYPE);

    @FormUrlEncoded
    @POST(HttpUrl.SPELL_ORDER_DETAIL)
    Observable<BaseResult<GroupOrderDetailBean>> spellOrderDetail(@Field("USER_ID") String user_id,
                                                                  @Field("ORDER_ID") int ORDER_ID);

    /**
     * cart
     */
    @FormUrlEncoded
    @POST(HttpUrl.CART_ADD)
    Observable<BaseResult<Integer>> cartAdd(@Field("USER_ID") String user_id,
                                            @Field("PRODUCT_ID") int PRODUCT_ID,
                                            @Field("FORM_ID") int FORM_ID,
                                            @Field("NUM") int NUM);

    @FormUrlEncoded
    @POST(HttpUrl.CART_LIST)
    Observable<BaseResult<List<CartProductBean>>> cartList(@Field("USER_ID") String user_id);

    @FormUrlEncoded
    @POST(HttpUrl.CART_CHANGE)
    Observable<BaseResult<String>> cartChange(@Field("USER_ID") String user_id,
                                              @Field("NUM") int NUM,
                                              @Field("CART_ID") int CART_ID);

    @FormUrlEncoded
    @POST(HttpUrl.CART_DELETE)
    Observable<BaseResult<String>> cartDelete(@Field("USER_ID") String user_id,
                                              @Field("CART_ID") int CART_ID);


    @FormUrlEncoded
    @POST(HttpUrl.CART_CASH)
    Observable<BaseResult<CartCashBean>> cartCash(@Field("USER_ID") String user_id,
                                                  @Field("CART_IDS") String CART_IDS,
                                                  @Field("ADDRESS_ID") int ADDRESS_ID,
                                                  @Field("USE_COUPON") int USE_COUPON,
                                                  @Field("EXCHANGE_ID") int EXCHANGE_ID,
                                                  @Field("IS_CART") int IS_CART,
                                                  @Field("NUM") int NUM,
                                                  @Field("PRODUCT_ID") int PRODUCT_ID,
                                                  @Field("FORM_ID") int FORM_ID,
                                                  @Field("IS_GROUP") int IS_GROUP);

    @FormUrlEncoded
    @POST(HttpUrl.CART_COUPON)
    Observable<BaseResult<List<ExchangeBean>>> cartCoupon(@Field("USER_ID") String user_id,
                                                          @Field("CART_IDS") String CART_IDS,
                                                          @Field("IS_CART") int IS_CART,
                                                          @Field("NUM") int NUM,
                                                          @Field("PRODUCT_ID") int PRODUCT_ID,
                                                          @Field("FORM_ID") int FORM_ID);

    @FormUrlEncoded
    @POST(HttpUrl.CART_CONFIRM_ORDER)
    Observable<PayResult<String>> cartConfirm(@Field("USER_ID") String user_id,
                                              @Field("CART_IDS") String CART_IDS,
                                              @Field("ADDRESS_ID") String ADDRESS_ID,
                                              @Field("INVOICE_HEAD") String INVOICE_HEAD,
                                              @Field("INVOICE_NUM") String INVOICE_NUM,
                                              @Field("INVOICE_TYPE") int INVOICE_TYPE,
                                              @Field("PAYWAY") int PAYWAY,
                                              @Field("USE_COUPON") int USE_COUPON,
                                              @Field("EXCHANGE_ID") int EXCHANGE_ID,
                                              @Field("IS_CART") int IS_CART,
                                              @Field("NUM") int NUM,
                                              @Field("PRODUCT_ID") int PRODUCT_ID,
                                              @Field("FORM_ID") int FORM_ID);

    @FormUrlEncoded
    @POST(HttpUrl.CART_CONFIRM_ORDER)
    Observable<PayResult<WechatBean>> cartConfirmWechat(@Field("USER_ID") String user_id,
                                                        @Field("CART_IDS") String CART_IDS,
                                                        @Field("ADDRESS_ID") String ADDRESS_ID,
                                                        @Field("INVOICE_HEAD") String INVOICE_HEAD,
                                                        @Field("INVOICE_NUM") String INVOICE_NUM,
                                                        @Field("INVOICE_TYPE") int INVOICE_TYPE,
                                                        @Field("PAYWAY") int PAYWAY,
                                                        @Field("USE_COUPON") int USE_COUPON,
                                                        @Field("EXCHANGE_ID") int EXCHANGE_ID,
                                                        @Field("IS_CART") int IS_CART,
                                                        @Field("NUM") int NUM,
                                                        @Field("PRODUCT_ID") int PRODUCT_ID,
                                                        @Field("FORM_ID") int FORM_ID);


    @FormUrlEncoded
    @POST(HttpUrl.CART_RECOMMEND)
    Observable<BaseResult<List<ProductBean>>> cartRecommend(@Field("COUNT") int count,
                                                            @Field("USER_ID") String user_id,
                                                            @Field("PAGE") int page);

    /**
     * order
     */
    @FormUrlEncoded
    @POST(HttpUrl.ORDER_LIST)
    Observable<BaseResult<List<OrderBean>>> orderList(@Field("USER_ID") String user_id,
                                                      @Field("COUNT") int COUNT,
                                                      @Field("PAGE") int PAGE,
                                                      @Field("TYPE") int TYPE);

    @FormUrlEncoded
    @POST(HttpUrl.ORDER_DETAIL)
    Observable<BaseResult<OrderDetailBean>> orderDetail(@Field("USER_ID") String user_id,
                                                        @Field("ORDER_ID") int ORDER_ID);

    @FormUrlEncoded
    @POST(HttpUrl.ORDER_CANCEL)
    Observable<BaseResult<OrderDetailBean>> orderCancel(@Field("USER_ID") String user_id,
                                                        @Field("ORDER_ID") int ORDER_ID);

    @FormUrlEncoded
    @POST(HttpUrl.ORDER_RECEIVED)
    Observable<BaseResult<OrderDetailBean>> orderReceived(@Field("USER_ID") String user_id,
                                                          @Field("ORDER_ID") int ORDER_ID);

    @FormUrlEncoded
    @POST(HttpUrl.ORDER_DELETE)
    Observable<BaseResult<OrderDetailBean>> orderDelete(@Field("USER_ID") String user_id,
                                                        @Field("ORDER_ID") int ORDER_ID);

    @FormUrlEncoded
    @POST(HttpUrl.ORDER_EVALUATE_PRODUCT_LIST)
    Observable<BaseResult<List<ProductBean>>> orderEvaluateProductList(@Field("USER_ID") String user_id,
                                                                       @Field("ORDER_ID") int ORDER_ID);

    @FormUrlEncoded
    @POST(HttpUrl.ORDER_PUBLISH_EVALUATE)
    Observable<BaseResult<ProductBean>> orderPublishEvaluate(@Field("USER_ID") String user_id,
                                                             @Field("ORDER_ID") int ORDER_ID,
                                                             @Field("ASSESS_JSON") String ASSESS_JSON,
                                                             @Field("DESC_SCORE") int DESC_SCORE,
                                                             @Field("SERVICE_SCORE") int SERVICE_SCORE,
                                                             @Field("WL_SCORE") int WL_SCORE);


    @FormUrlEncoded
    @POST(HttpUrl.ORDER_BALANCE)
    Observable<BaseResult<Integer>> orderBalance(@Field("USER_ID") String user_id,
                                                 @Field("ORDER_ID") int ORDER_ID);

    @FormUrlEncoded
    @POST(HttpUrl.ORDER_ALIPAY)
    Observable<BaseResult<String>> orderAlipay(@Field("USER_ID") String user_id,
                                               @Field("ORDER_ID") int ORDER_ID);

    @FormUrlEncoded
    @POST(HttpUrl.ORDER_WECHAT)
    Observable<BaseResult<WechatBean>> orderWechat(@Field("USER_ID") String user_id,
                                                   @Field("ORDER_ID") int ORDER_ID);


    @FormUrlEncoded
    @POST(HttpUrl.ORDER_RETURN_INFO)
    Observable<BaseResult<ReturnOrderInfoBean>> orderReturnInfo(@Field("USER_ID") String user_id,
                                                                @Field("ORDER_ID") int ORDER_ID,
                                                                @Field("ORDER_DETAIL_ID") int ORDER_DETAIL_ID);

    @FormUrlEncoded
    @POST(HttpUrl.ORDER_RETURN_REASON)
    Observable<BaseResult<List<ReturnReasonBean>>> orderReturnReason(@Field("USER_ID") String user_id,
                                                                     @Field("CATEGORY") int CATEGORY);

    @FormUrlEncoded
    @POST(HttpUrl.ORDER_REQUEST_RETURN)
    Observable<BaseResult<String>> orderRequestReturn(@Field("USER_ID") String user_id,
                                                      @Field("ORDER_ID") int ORDER_ID,
                                                      @Field("ORDER_DETAIL_ID") int ORDER_DETAIL_ID,
                                                      @Field("APPLY_NUM") int APPLY_NUM,
                                                      @Field("CATEGORY") String CATEGORY,
                                                      @Field("REASON") String REASON,
                                                      @Field("CONTENT") String CONTENT,
                                                      @Field("PIC") String PIC);

    @FormUrlEncoded
    @POST(HttpUrl.ORDER_BUY_AGAIN)
    Observable<BaseResult<OrderDetailBean>> orderBuyAgain(@Field("USER_ID") String user_id,
                                                          @Field("ORDER_ID") int ORDER_ID);


    /**
     * return
     */
    @FormUrlEncoded
    @POST(HttpUrl.RETURN_LIST)
    Observable<BaseResult<List<ReturnBean>>> returnList(@Field("USER_ID") String user_id,
                                                        @Field("COUNT") int COUNT,
                                                        @Field("PAGE") int PAGE,
                                                        @Field("TYPE") int TYPE);

    @FormUrlEncoded
    @POST(HttpUrl.RETURN_DETAIL)
    Observable<BaseResult<ReturnDetailBean>> returnDetail(@Field("USER_ID") String user_id,
                                                          @Field("REFUND_ID") int REFUND_ID);

    @FormUrlEncoded
    @POST(HttpUrl.RETURN_EXPRESS_INFO)
    Observable<ReturnResult> returnExpressInfo(@Field("USER_ID") String user_id);

    @FormUrlEncoded
    @POST(HttpUrl.RETURN_COMMIT_EXPRESS)
    Observable<ReturnResult> returnCommitExpress(@Field("USER_ID") String user_id,
                                                 @Field("REFUND_ID") int REFUND_ID,
                                                 @Field("POST_NUM") String POST_NUM,
                                                 @Field("POST_COMPANY") String POST_COMPANY);

    /**
     * address
     *
     * @return
     */
    @FormUrlEncoded
    @POST(HttpUrl.GET_LIST_ADDRESS)
    Observable<BaseResult<List<AddressBean>>> getListAddress(@Field("COUNT") int count,
                                                             @Field("USER_ID") String user_id,
                                                             @Field("PAGE") int page);

    @FormUrlEncoded
    @POST(HttpUrl.ADD_ADDRESS)
    Observable<BaseResult<AddressBean>> addAddress(@Field("ADDRESS") String ADDRESS,
                                                   @Field("CITY") String CITY,
                                                   @Field("DIST") String DIST,
                                                   @Field("PROV") String PROV,
                                                   @Field("RECIPIENT") String RECIPIENT,
                                                   @Field("STATUS") int STATUS,
                                                   @Field("TEL") Long TEL,
                                                   @Field("USER_ID") String USER_ID);

    @FormUrlEncoded
    @POST(HttpUrl.GET_ADDRESS_INFO)
    Observable<BaseResult<AddressBean>> getAddressInfo(@Field("USER_ID") String USER_ID,
                                                       @Field("ADDRESS_ID") String ADDRESS_ID);

    @FormUrlEncoded
    @POST(HttpUrl.ADDRESS_DELETE)
    Observable<BaseResult<AddressBean>> addressDelete(@Field("USER_ID") String USER_ID,
                                                      @Field("ADDRESS_ID") String ADDRESS_ID);

    @FormUrlEncoded
    @POST(HttpUrl.ADDRESS_UPDATE)
    Observable<BaseResult<AddressBean>> addressUpdate(@Field("ADDRESS") String ADDRESS,
                                                      @Field("CITY") String CITY,
                                                      @Field("DIST") String DIST,
                                                      @Field("PROV") String PROV,
                                                      @Field("RECIPIENT") String RECIPIENT,
                                                      @Field("STATUS") int STATUS,
                                                      @Field("TEL") String TEL,
                                                      @Field("USER_ID") String USER_ID,
                                                      @Field("ADDRESS_ID") String ADDRESS_ID);


    /**
     * user
     *
     * @param USER_ID
     * @return
     */
    @FormUrlEncoded
    @POST(HttpUrl.USER_HOME)
    Observable<BaseResult<UserHomeBean>> getUserHome(@Field("USER_ID") String USER_ID);

    @FormUrlEncoded
    @POST(HttpUrl.GET_USER_INFO)
    Observable<BaseResult<UserBean>> getUserInfo(@Field("USER_ID") String USER_ID);

    @FormUrlEncoded
    @POST(HttpUrl.UPDATE_PWD)
    Observable<BaseResult<UpdatePwdBean>> updatePwd(@Field("USER_ID") String USER_ID,
                                                    @Field("NEW_PASSWORD") String NEW_PASSWORD,
                                                    @Field("OLD_PASSWORD") String OLD_PASSWORD);

    @FormUrlEncoded
    @POST(HttpUrl.USER_UPDATE)
    Observable<BaseResult<UpdatePwdBean>> updateUser(@Field("BIRTHDAY") String BIRTHDAY,
                                                     @Field("GENDER") int GENDER,
                                                     @Field("LOGO") String LOGO,
                                                     @Field("NICKNAME") String NICKNAME,
                                                     @Field("PET_TIME") String PET_TIME,
                                                     @Field("TAG") String TAG,
                                                     @Field("USERNAME") String USERNAME,
                                                     @Field("USER_ID") String USER_ID);

    @FormUrlEncoded
    @POST(HttpUrl.FRIEND_LIST)
    Observable<BaseResult<List<AttentionBean>>> getFriendList(@Field("COUNT") int COUNT,
                                                              @Field("USER_ID") String USER_ID,
                                                              @Field("PAGE") int PAGE);

    @FormUrlEncoded
    @POST(HttpUrl.ATTENTION)
    Observable<BaseResult<Integer>> attention(@Field("ATTEN_ID") int ATTEN_ID,
                                              @Field("USER_ID") String USER_ID);

    @FormUrlEncoded
    @POST(HttpUrl.ATTENTION_LIST)
    Observable<BaseResult<List<AttentionBean>>> attentionList(@Field("COUNT") int COUNT,
                                                              @Field("USER_ID") String USER_ID,
                                                              @Field("PAGE") int PAGE,
                                                              @Field("TYPE") int TYPE);


    @FormUrlEncoded
    @POST(HttpUrl.QUESTION_LIST)
    Observable<BaseResult<List<QuestionBean>>> questionList(@Field("COUNT") int COUNT,
                                                            @Field("USER_ID") String USER_ID,
                                                            @Field("PAGE") int PAGE);

    @FormUrlEncoded
    @POST(HttpUrl.OTHER)
    Observable<BaseResult<UserBean>> other(@Field("USER_ID") String USER_ID,
                                           @Field("OTHER_USER_ID") int OTHER_USER_ID);

    @FormUrlEncoded
    @POST(HttpUrl.OTHER_TAXIU)
    Observable<BaseResult<List<TaxiuBean>>> otherTaxiu(@Field("USER_ID") String USER_ID,
                                                       @Field("COUNT") int COUNT,
                                                       @Field("PAGE") int PAGE,
                                                       @Field("OTHER_USER_ID") int OTHER_USER_ID);

    /**
     * 积分账单列表
     *
     * @param COUNT
     * @param USER_ID
     * @param PAGE
     * @return
     */
    @FormUrlEncoded
    @POST(HttpUrl.SCORE_LIST)
    Observable<BaseResult<List<IntegralBean>>> scoreList(@Field("COUNT") int COUNT,
                                                         @Field("USER_ID") String USER_ID,
                                                         @Field("PAGE") int PAGE);

    @FormUrlEncoded
    @POST(HttpUrl.SCORE_EXCHANGE_LIST)
    Observable<BaseResult<List<ExchangeBean>>> scoreExchangeList(@Field("COUNT") int COUNT,
                                                                 @Field("USER_ID") String USER_ID,
                                                                 @Field("PAGE") int PAGE);

    @FormUrlEncoded
    @POST(HttpUrl.SCORE_COUPON_INFO)
    Observable<BaseResult<ExchangeBean>> scoreCouponInfo(@Field("COUPON_ID") int COUPON_ID,
                                                         @Field("USER_ID") String USER_ID);

    @FormUrlEncoded
    @POST(HttpUrl.SCORE_TO_EXCHANGE)
    Observable<BaseResult<ExchangeBean>> toExchange(@Field("COUPON_ID") int COUPON_ID,
                                                    @Field("USER_ID") String USER_ID);

    @FormUrlEncoded
    @POST(HttpUrl.SCORE_EXCHANGE_HIS)
    Observable<BaseResult<List<ExchangeHisBean>>> exchangeHis(@Field("COUNT") int COUNT,
                                                              @Field("USER_ID") String USER_ID,
                                                              @Field("PAGE") int PAGE);

    @FormUrlEncoded
    @POST(HttpUrl.SCORE_EXCHANGE_INFO)
    Observable<BaseResult<ExchangeHisBean>> exchangeInfo(@Field("EXCHANGE_ID") int EXCHANGE_ID,
                                                         @Field("USER_ID") String USER_ID);

    @FormUrlEncoded
    @POST(HttpUrl.SCORE_COUPON_LIST)
    Observable<BaseResult<List<ExchangeHisBean>>> couponList(@Field("COUNT") int COUNT,
                                                             @Field("USER_ID") String USER_ID,
                                                             @Field("PAGE") int PAGE,
                                                             @Field("USE_STATUS") int USE_STATUS);


    @FormUrlEncoded
    @POST(HttpUrl.TEXT_INFO)
    Observable<BaseResult<TextBean>> getTextInfo(@Field("CATEGORY") int CATEGORY,
                                                 @Field("USER_ID") String USER_ID);

    /**
     * 余额
     */
    @FormUrlEncoded
    @POST(HttpUrl.BALANCE_INFO)
    Observable<BalanceResult> getBalanceInfo(@Field("USER_ID") String USER_ID);


    @FormUrlEncoded
    @POST(HttpUrl.BALANCE_BILL)
    Observable<BaseResult<List<BalanceBillBean>>> balanceBill(@Field("USER_ID") String USER_ID,
                                                              @Field("COUNT") int COUNT,
                                                              @Field("PAGE") int PAGE);

    @FormUrlEncoded
    @POST(HttpUrl.BANK_TYPE_LIST)
    Observable<BaseResult<List<BankTypeListBean>>> bankTypeList(@Field("USER_ID") String USER_ID);

    @FormUrlEncoded
    @POST(HttpUrl.BANK_CARD_LIST)
    Observable<BaseResult<List<BankCardBean>>> bankCardList(@Field("USER_ID") String USER_ID,
                                                            @Field("COUNT") int COUNT,
                                                            @Field("PAGE") int PAGE);

    @FormUrlEncoded
    @POST(HttpUrl.BANK_CARD_INFO)
    Observable<BaseResult<BankCardBean>> bankCardInfo(@Field("USER_ID") String USER_ID,
                                                      @Field("BANK_CARD_ID") String BANK_CARD_ID);

    @FormUrlEncoded
    @POST(HttpUrl.ADD_BANK_CARD)
    Observable<BaseResult<List<BankTypeListBean>>> addBankCard(@Field("USER_ID") String USER_ID,
                                                               @Field("ACCOUNT_NAME") String ACCOUNT_NAME,
                                                               @Field("BANK_DEPOSIT") String BANK_DEPOSIT,
                                                               @Field("BANK_TYPE_ID") String BANK_TYPE_ID,
                                                               @Field("CARD_NUM") String CARD_NUM);

    @FormUrlEncoded
    @POST(HttpUrl.CHANGE_BANK_CARD)
    Observable<BaseResult<List<BankTypeListBean>>> changeBankCard(@Field("USER_ID") String USER_ID,
                                                                  @Field("BANK_CARD_ID") String BANK_CARD_ID,
                                                                  @Field("ACCOUNT_NAME") String ACCOUNT_NAME,
                                                                  @Field("BANK_DEPOSIT") String BANK_DEPOSIT,
                                                                  @Field("BANK_TYPE_ID") String BANK_TYPE_ID,
                                                                  @Field("CARD_NUM") String CARD_NUM);


    @FormUrlEncoded
    @POST(HttpUrl.BALANCE_WITHDRAW_HINT)
    Observable<WithdrawHintBean> balanceWithdrawHint(@Field("USER_ID") String USER_ID);

    @FormUrlEncoded
    @POST(HttpUrl.BALANCE_WITHDRAW)
    Observable<WithdrawHintBean> balanceWithdraw(@Field("USER_ID") String USER_ID,
                                                 @Field("BANK_CARD_ID") String BANK_CARD_ID,
                                                 @Field("MONEY") String MONEY);

    /**
     * 收藏
     */
    @FormUrlEncoded
    @POST(HttpUrl.COLLECT_PRODUCT)
    Observable<BaseResult<List<ProductBean>>> productCollect(@Field("USER_ID") String user_id,
                                                             @Field("COUNT") int count,
                                                             @Field("PAGE") int page);

    @FormUrlEncoded
    @POST(HttpUrl.COLLECT_TAXIU)
    Observable<BaseResult<List<TaxiuBean>>> taxiuCollect(@Field("USER_ID") String user_id,
                                                         @Field("COUNT") int count,
                                                         @Field("PAGE") int page);

    @FormUrlEncoded
    @POST(HttpUrl.COLLECT_CLASSROOM)
    Observable<BaseResult<List<ClassRoomBean>>> classroomCollect(@Field("USER_ID") String user_id,
                                                                 @Field("COUNT") int count,
                                                                 @Field("PAGE") int page);

    @FormUrlEncoded
    @POST(HttpUrl.COLLECT_TOPIC)
    Observable<BaseResult<List<TopicBean>>> topicCollect(@Field("USER_ID") String user_id,
                                                         @Field("COUNT") int count,
                                                         @Field("PAGE") int page);

    @FormUrlEncoded
    @POST(HttpUrl.COLLECT_TALK)
    Observable<BaseResult<List<TalkBean>>> talkCollect(@Field("USER_ID") String user_id,
                                                       @Field("COUNT") int count,
                                                       @Field("PAGE") int page);


    /**
     * 推荐
     */
    @FormUrlEncoded
    @POST(HttpUrl.RECOMMEND_INFO)
    Observable<BaseResult<RecommendInfoBean>> recommendInfo(@Field("USER_ID") String user_id);


    @FormUrlEncoded
    @POST(HttpUrl.RECOMMEND_EARNINGS)
    Observable<EarningsResult<List<RecommendEarningsBean>>> recommendEarnings(@Field("USER_ID") String user_id,
                                                                              @Field("COUNT") int count,
                                                                              @Field("PAGE") int page);

    @FormUrlEncoded
    @POST(HttpUrl.AVAILABLE_BALANCE)
    Observable<EarningsResult<String>> availableBalance(@Field("USER_ID") String user_id);

    @FormUrlEncoded
    @POST(HttpUrl.AVAILABLE_INTEGRAL)
    Observable<EarningsResult<String>> availableIntegral(@Field("USER_ID") String user_id);


    @FormUrlEncoded
    @POST(HttpUrl.WEB_INFO)
    Observable<BaseResult<WebBean>> webInfo(@Field("CATEGORY") String CATEGORY);

    /**
     * 消息
     */
    @FormUrlEncoded
    @POST(HttpUrl.NEWS)
    Observable<BaseResult<List<MessageBean>>> newsList(@Field("USER_ID") String USER_ID,
                                                       @Field("COUNT") int COUNT,
                                                       @Field("PAGE") int PAGE,
                                                       @Field("CATEGORY") int CATEGORY);


    @FormUrlEncoded
    @POST(HttpUrl.VERSION_CHECK)
    Observable<BaseResult<VersionBean>> versionCheck(@Field("PLATFORM") int PLATFORM,
                                                     @Field("VERSION_NO") String VERSION_NO);


    /**
     * 分享
     */
    @FormUrlEncoded
    @POST(HttpUrl.SHARE_TAXIU)
    Observable<BaseResult<String>> shareTaxiu(@Field("USER_ID") String USER_ID,
                                              @Field("SHOW_ID") int SHOW_ID);

    @FormUrlEncoded
    @POST(HttpUrl.SHARE_TOPIC)
    Observable<BaseResult<String>> shareTopic(@Field("USER_ID") String USER_ID,
                                              @Field("TOPIC_ID") int TOPIC_ID);

    @FormUrlEncoded
    @POST(HttpUrl.SHARE_TALK)
    Observable<BaseResult<String>> shareTalk(@Field("USER_ID") String USER_ID,
                                             @Field("QUESTION_ID") int QUESTION_ID);

    @FormUrlEncoded
    @POST(HttpUrl.SHARE_CLASSROOM)
    Observable<BaseResult<String>> shareClassroom(@Field("USER_ID") String USER_ID,
                                                  @Field("CLASS_ROOM_ID") int CLASS_ROOM_ID);

    @FormUrlEncoded
    @POST(HttpUrl.SHARE_PRODUCT)
    Observable<BaseResult<String>> shareProduct(@Field("USER_ID") String USER_ID,
                                                @Field("PRODUCT_ID") int PRODUCT_ID,
                                                @Field("FORM_ID") int FORM_ID);

    @FormUrlEncoded
    @POST(HttpUrl.SHARE_VIDEO)
    Observable<BaseResult<String>> shareVideo(@Field("USER_ID") String USER_ID,
                                              @Field("VIDEO_ID") int VIDEO_ID);

    @FormUrlEncoded
    @POST(HttpUrl.SHARE_USER)
    Observable<BaseResult<String>> shareUser(@Field("USER_ID") String USER_ID);

    /**
     * 宠物宝典
     */
    @FormUrlEncoded
    @POST(HttpUrl.PET_BIBLE)
    Observable<BaseResult<List<PetBibleBean>>> petBible(@Field("USER_ID") String USER_ID);


    //tools
    @Multipart
    @POST(HttpUrl.UPLOAD_PIC)
    Observable<UploadResultBean> uploadPicture(@PartMap Map<String, RequestBody> map);

    @Multipart
    @POST(HttpUrl.UPLOAD_VIDEO)
    Observable<UploadResultBean> uploadVideo(@PartMap Map<String, RequestBody> map);

}
