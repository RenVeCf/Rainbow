package com.ipd.taxiu.platform.http;


import com.ipd.taxiu.bean.AddressBean;
import com.ipd.taxiu.bean.AttentionBean;
import com.ipd.taxiu.bean.BaseResult;
import com.ipd.taxiu.bean.ClassRoomBean;
import com.ipd.taxiu.bean.CommentDetailBean;
import com.ipd.taxiu.bean.CommentResult;
import com.ipd.taxiu.bean.IntegralBean;
import com.ipd.taxiu.bean.LoginBean;
import com.ipd.taxiu.bean.MoreCommentReplyBean;
import com.ipd.taxiu.bean.OtherBean;
import com.ipd.taxiu.bean.PetBean;
import com.ipd.taxiu.bean.PetKindListBean;
import com.ipd.taxiu.bean.ProvinceBean;
import com.ipd.taxiu.bean.QuestionBean;
import com.ipd.taxiu.bean.RegisterBean;
import com.ipd.taxiu.bean.SignInDayBean;
import com.ipd.taxiu.bean.SignInInfoBean;
import com.ipd.taxiu.bean.SignInResuleBean;
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
    Observable<BaseResult<String>> registerSmsCode(@Field("PHONE") String PHONE);

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
    Observable<BaseResult<String>> phoneLoginSmsCode(@Field("PHONE") String PHONE);

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
                                              @Field("USER_ID") String USER_ID);

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
                                                        @Field("URL") String URL);

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
    @POST(HttpUrl.PUBLISH_TALK)
    Observable<BaseResult<TalkBean>> publishTalk(@Field("USER_ID") String USER_ID,
                                                 @Field("CONTENT") String CONTENT,
                                                 @Field("SCORE") String SCORE);


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
    @POST(HttpUrl.CLASS_ROOM_DETAIL)
    Observable<BaseResult<ClassRoomBean>> classroomDetail(@Field("USER_ID") String USER_ID,
                                                          @Field("CLASS_ROOM_ID") int CLASS_ROOM_ID);


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
    Observable<BaseResult<OtherBean>> other(@Field("USER_ID") String USER_ID,
                                            @Field("OTHER_USER_ID") String OTHER_USER_ID);

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
    @POST(HttpUrl.TEXT_INFO)
    Observable<BaseResult<TextBean>> getTextInfo(@Field("CATEGORY") int CATEGORY,
                                                 @Field("USER_ID") String USER_ID);


    //tools
    @Multipart
    @POST(HttpUrl.UPLOAD_PIC)
    Observable<UploadResultBean> uploadPicture(@PartMap Map<String, RequestBody> map);

}
