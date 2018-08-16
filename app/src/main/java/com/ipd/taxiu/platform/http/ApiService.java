package com.ipd.taxiu.platform.http;


import com.ipd.taxiu.bean.BaseResult;
import com.ipd.taxiu.bean.LoginBean;
import com.ipd.taxiu.bean.PetKindListBean;
import com.ipd.taxiu.bean.RegisterBean;
import com.ipd.taxiu.bean.SignInDayBean;
import com.ipd.taxiu.bean.SignInInfoBean;
import com.ipd.taxiu.bean.SignInResuleBean;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by jumpbox on 2017/7/24.
 */

public interface ApiService {


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


}
