package com.datacvg.sempmobile.baseandroid.config;

import com.datacvg.sempmobile.baseandroid.retrofit.bean.BaseBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-07-28
 * @Description :
 */
public interface FisApi {
    /**
     * 查询报告
     * @param reportType
     * @param t
     * @return
     */
    @Headers({"Domain-Name: fis_api"})
    @GET("dataexporler/report/folder")
    Observable<BaseBean<String>> getReport(@Query("type") String reportType,
                                           @Query("_t") String t);
}
