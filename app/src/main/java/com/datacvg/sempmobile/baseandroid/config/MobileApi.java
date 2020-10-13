package com.datacvg.sempmobile.baseandroid.config;

import com.datacvg.sempmobile.baseandroid.retrofit.bean.BaseBean;
import com.datacvg.sempmobile.bean.DimensionListBean;
import com.datacvg.sempmobile.bean.ModuleListBean;
import com.datacvg.sempmobile.bean.OtherDimensionBean;
import com.datacvg.sempmobile.bean.ScreenListBean;
import com.datacvg.sempmobile.bean.UserJobsListBean;
import com.datacvg.sempmobile.bean.UserLoginBean;
import java.util.Map;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-07-28
 * @Description :
 */
public interface MobileApi {

    /**
     * 登录
     * @param params    登录请求封装的数据集
     * @return
     */
    @POST("login/userlogin")
    Observable<BaseBean<UserLoginBean>> login(@Body Map<String, String> params);

    /**
     * 获取用户模块
     * @return
     */
    @GET("usermodel/usermodellist")
    Observable<BaseBean<ModuleListBean>> getPermissionModule();

    /**
     * 登出
     * @return
     */
    @GET("login/userloginout")
    Observable<BaseBean> loginOut();

    /**
     * 获取用户关联岗位信息
     * @param userPkid  用户标识id
     * @return
     */
    @GET("login/userposition")
    Observable<BaseBean<UserJobsListBean>> getJob(@Query("userPkid") String userPkid);

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

    /**
     * 获取所有大屏
     * @param screenType
     * @return
     */
    @Headers({"Domain-Name: fis_api"})
    @GET("largescreen/list")
    Observable<ScreenListBean> getScreenList(@Query("sortVal") String screenType);

    /**
     * 获取指标列表
     * @return
     */
    @Headers({"Domain-Name: local_api"})
    @GET("indexpad/index/position_v2")
    Observable<BaseBean<String>> getIndexPosition();

    /**
     * 获取组织维度
     * @return
     */
    @GET("indexpad/dimention")
    Observable<BaseBean<DimensionListBean>> getDimension();

    /**
     * 获取其他维度
     * @return
     */
    @GET("indexpad/other/dimention")
    Observable<BaseBean<OtherDimensionBean>> getOtherDimension();
}
