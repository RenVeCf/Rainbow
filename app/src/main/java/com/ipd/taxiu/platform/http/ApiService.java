package com.ipd.taxiu.platform.http;


import com.ipd.taxiu.bean.BaseResult;
import com.ipd.taxiu.bean.LoginBean;
import com.ipd.taxiu.bean.RegisterBean;

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


}
