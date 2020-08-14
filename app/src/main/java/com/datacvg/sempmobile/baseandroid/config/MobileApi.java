package com.datacvg.sempmobile.baseandroid.config;

import com.datacvg.sempmobile.baseandroid.retrofit.bean.BaseBean;
import com.datacvg.sempmobile.bean.UserLoginBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;
/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-07-28
 * @Description :
 */
public interface MobileApi {

    @POST("login/userlogin")
    Observable<BaseBean<UserLoginBean>> login(@Body Map<String, String> params);
}
