package com.datacvg.sempmobile.baseandroid.config;

import com.datacvg.sempmobile.baseandroid.retrofit.bean.BaseBean;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-07-23
 * @Description :
 */
public interface UploadApi {


    @Multipart
    @POST("/member_user")
    Observable<BaseBean> uploadFileWithPartMap(@PartMap Map<String, RequestBody> partMap);
}
