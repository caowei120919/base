package com.datacvg.dimp.baseandroid.config;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-07-23
 * @Description :
 */
public interface LoginApi {

    /**
     * 获取河狸项目license code
     * @param customUrl 请求的url
     * @return
     */
    @GET
    Observable<String> getCustomLicense(@Url String customUrl);
}
