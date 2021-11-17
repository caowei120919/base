package com.datacvg.dimp.baseandroid.config;

import com.datacvg.dimp.baseandroid.retrofit.bean.BaseBean;
import com.datacvg.dimp.bean.CheckVersionBean;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-07-23
 * @Description :
 */
public interface UploadApi {


    @Multipart
    @POST("/member_user")
    Observable<BaseBean> uploadFileWithPartMap(@PartMap Map<String, RequestBody> partMap);

    @Multipart
    @POST("api/dataengine/dataexporler/report/upload_thumbnails")
    Observable<BaseBean> uploadFile(@Query("reportId") String reportId
            ,@Query("modelType") String type
            ,@PartMap Map<String, RequestBody> partMap);

    @POST("updateapp/getVersion")
    Observable<CheckVersionBean> checkVersion(@Body Map params);
}
