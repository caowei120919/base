package com.datacvg.dimp.baseandroid.config;

import com.datacvg.dimp.baseandroid.retrofit.bean.BaseBean;
import com.datacvg.dimp.bean.ActionPlanIndexListBean;
import com.datacvg.dimp.bean.ActionPlanListBean;
import com.datacvg.dimp.bean.ChartListBean;
import com.datacvg.dimp.bean.CommentListBean;
import com.datacvg.dimp.bean.DefaultUserListBean;
import com.datacvg.dimp.bean.DimensionListBean;
import com.datacvg.dimp.bean.DimensionPositionListBean;
import com.datacvg.dimp.bean.ImageResBean;
import com.datacvg.dimp.bean.IndexBean;
import com.datacvg.dimp.bean.IndexTreeListBean;
import com.datacvg.dimp.bean.MessageBean;
import com.datacvg.dimp.bean.ModuleListBean;
import com.datacvg.dimp.bean.ReadMessageBean;
import com.datacvg.dimp.bean.ReportListBean;
import com.datacvg.dimp.bean.ReportParamsBean;
import com.datacvg.dimp.bean.ScreenDetailBean;
import com.datacvg.dimp.bean.ScreenListBean;
import com.datacvg.dimp.bean.TableInfoBean;
import com.datacvg.dimp.bean.TableListBean;
import com.datacvg.dimp.bean.TableParamInfoListBean;
import com.datacvg.dimp.bean.TaskInfoBean;
import com.datacvg.dimp.bean.UserJobsListBean;
import com.datacvg.dimp.bean.UserLoginBean;
import java.util.Map;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

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
    Observable<BaseBean<ReportListBean>> getReport(@Query("type") String reportType,
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
     * @param mTimeValue
     * @param mOrgDimension
     * @param mFuDimension
     * @param mPDimension
     * @return
     */
    @GET("indexpad/index/position")
    Observable<BaseBean<DimensionPositionListBean>> getIndexPosition(@Query("timeval") String mTimeValue,
                                                                     @Query("orgDimension") String mOrgDimension,
                                                                     @Query("fuDimension") String mFuDimension,
                                                                     @Query("pDimension") String mPDimension);

    /**
     * 获取组织维度
     * @return
     */
    @Headers({"Domain-Name: ddb_api"})
    @GET("ddb/indexpad/dimension/get")
    Observable<BaseBean<DimensionListBean>> getDimension(@Query("timeVal") String timeVal);

    /**
     * 获取其他维度
     * @return
     */
    @Headers({"Domain-Name: ddb_api"})
    @POST("ddb/indexpad/other/dimension/get")
    Observable<BaseBean<DimensionListBean>> getOtherDimension(@Body Map map);

    /**
     * 获取图表详细数据
     * @param map
     * @return
     */
    @POST("indexpad/echarts")
    Observable<BaseBean<ChartListBean>> getCharts(@Body Map map);

    /**
     * 获取任务列表
     * @param map
     * @return
     */
    @POST("actionplan/infotask")
    Observable<BaseBean<ActionPlanListBean>> getActionList(@Body Map map);

    /**
     * 获取大屏详情
     * @param screen_id
     * @return
     */
    @Headers({"Domain-Name: fis_api"})
    @GET("largescreen/{id}")
    Observable<BaseBean<ScreenDetailBean>> getScreenDetail(@Path("id") String screen_id);

    /**
     * 获取消息
     * @param pageIndex
     * @param pageSize
     * @param module_id
     * @param read_flag
     * @return
     */
    @GET("login/message_info")
    Observable<BaseBean<MessageBean>> getMessage(@Query("pageIndex") String pageIndex,
                                                 @Query("pageSize") String pageSize,
                                                 @Query("module_id") String module_id,
                                                 @Query("read_flag") String read_flag);

    /**
     * 获取指标信息
     * @return
     */
    @GET("indexpad/index/classification")
    Observable<BaseBean<IndexBean>> getIndex();

    /**
     * 改变选择指标的顺序
     * @param indexIds  指标id拼接
     * @return
     */
    @POST("indexpad/position/change")
    Observable<BaseBean<String>> changeSelectedIndex(@Query("indexIds") String indexIds);


    /**
     * 查询报表列表数据
     * @param tableType 设备类型 3标识app
     * @return
     */
    @GET("mobilereport/h5/app_showcxtableauinfo")
    Observable<BaseBean<TableListBean>> getTableList(@Query("deviceType") String tableType);

    /**
     * 获取图片二进制资源文件
     * @param res_id
     * @return
     */
    @GET("mobilereport/app_readResImg")
    Observable<ImageResBean> getImageRes(@Query("res_id") String res_id);

    /**
     * 将消息设置为已读
     * @param doRead
     * @param id
     * @param read
     * @param module_id
     * @return
     */
    @GET("login/message_service")
    Observable<BaseBean<ReadMessageBean>> doReadMessage(@Query("key") String doRead,
                                                        @Query("message_id") String id,
                                                        @Query("read") String read,
                                                        @Query("module_id") String module_id);

    /**
     * 获取管理画布参数
     * @param reportId
     * @return
     */
    @GET("usermodel/getreportparameters")
    Observable<BaseBean<ReportParamsBean>> getReportParameters(@Query("model_id") String reportId);

    /**
     * 扫码登录
     * @param params
     * @return
     */
    @POST("login/mobsrv/scancode")
    Observable<BaseBean<String>> webLogin(@QueryMap Map<String, String> params);

    /**
     * 大屏投放
     * @param params
     * @return
     */
    @Headers({"Domain-Name: fis_api"})
    @POST("largescreen/websocket")
    Observable<BaseBean<String>> confirmOnTheScreen(@Body Map<String, String> params);

    /**
     * 获取报表参数
     * @param res_id
     * @return
     */
    @GET("mobilereport/app_getresparaminfobyresid")
    Observable<BaseBean<TableParamInfoListBean>> getResParamInfo(@Query("resId") String res_id);

    /**
     * 查询报表配置
     * @param params
     * @return
     */
    @POST("mobilereport/h5/app_show_report")
    Observable<BaseBean<TableInfoBean>> getTableUrl(@Body Map<String, String> params);

    /**
     * 查询所有维度下人员信息
     * @return
     */
    @GET("actionplan/getdefaultuser")
    Observable<BaseBean<DefaultUserListBean>> getDefaultUser();

    /**
     * 查询指标
     * @return
     */
    @GET("actionplan/infoindex")
    Observable<BaseBean<ActionPlanIndexListBean>> getActionPlanIndex();

    @Headers({"Domain-Name: upload_file"})
    @Multipart
    @POST("/upload")
    Observable<BaseBean> uploadFile(@PartMap Map<String, RequestBody> partMap);

    /**
     * 查询报表相关评论
     * @return
     */
    @Headers({"Domain-Name: ddb_api"})
    @GET("report/comments/info/list")
    Observable<BaseBean<CommentListBean>> getTableComment(@Query("resId") String resId,
                                                          @Query("param") String param);

    /**
     * 提交评论
     * @param requestBodyMap
     * @return
     */
    @Headers({"Domain-Name: ddb_api"})
    @Multipart
    @POST("report/comments/submit/comments/reply ")
    Observable<BaseBean> submitComments(@PartMap Map<String, RequestBody> requestBodyMap);

    @POST("indexpad/list")
    Observable<BaseBean<IndexTreeListBean>> getIndexTree(@Body Map map);

    @POST("actionplan/creattask")
    Observable<BaseBean> createTask(@Body Map map);

    @GET("actionplan/infodetail")
    Observable<BaseBean<TaskInfoBean>> getTaskInfo(@Query("taskId") String taskId,
                                                   @Query("userType") int user_type,
                                                   @Query("userId") String userId,
                                                   @Query("language") String language);

    @GET("https://api.powerbi.cn/powerbi/globalservice/v201606/clusterdetails")
    Observable<String> getPowerBiInfo(@Header("authorization") String token);
}
