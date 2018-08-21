package com.ipd.taxiu.platform.http;


import com.ipd.taxiu.bean.AddressBean;
import com.ipd.taxiu.bean.AttentionBean;
import com.ipd.taxiu.bean.BaseResult;
import com.ipd.taxiu.bean.IntegralBean;
import com.ipd.taxiu.bean.LoginBean;
import com.ipd.taxiu.bean.PetBean;
import com.ipd.taxiu.bean.ProvinceBean;
import com.ipd.taxiu.bean.PetKindListBean;
import com.ipd.taxiu.bean.QuestionBean;
import com.ipd.taxiu.bean.RegisterBean;
import com.ipd.taxiu.bean.SignInDayBean;
import com.ipd.taxiu.bean.SignInInfoBean;
import com.ipd.taxiu.bean.SignInResuleBean;
import com.ipd.taxiu.bean.TextBean;
import com.ipd.taxiu.bean.UpdatePwdBean;
import com.ipd.taxiu.bean.UserBean;

import java.util.List;

import java.util.ArrayList;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
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
                                                      @Field("TEL") long TEL,
                                                      @Field("USER_ID") String USER_ID,
                                                      @Field("ADDRESS_ID") String ADDRESS_ID);


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
    Observable<BaseResult<AttentionBean>> attention(@Field("ATTEN_ID") int ATTEN_ID,
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

    /**
     * 积分账单列表
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

}
