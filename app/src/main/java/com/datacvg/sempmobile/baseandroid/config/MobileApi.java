package com.datacvg.sempmobile.baseandroid.config;

import com.datacvg.sempmobile.baseandroid.retrofit.bean.BaseBean;
import com.datacvg.sempmobile.bean.ModuleListBean;
import com.datacvg.sempmobile.bean.UserJobsListBean;
import com.datacvg.sempmobile.bean.UserLoginBean;
import java.util.Map;
import java.util.function.DoubleUnaryOperator;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
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
}
