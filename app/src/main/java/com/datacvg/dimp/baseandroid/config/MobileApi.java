package com.datacvg.dimp.baseandroid.config;
import com.datacvg.dimp.baseandroid.retrofit.bean.BaseBean;
import com.datacvg.dimp.bean.ActionForIndexBean;
import com.datacvg.dimp.bean.ActionPlanIndexListBean;
import com.datacvg.dimp.bean.ActionPlanListBean;
import com.datacvg.dimp.bean.AllIndexBean;
import com.datacvg.dimp.bean.ChartListBean;
import com.datacvg.dimp.bean.CommentListBean;
import com.datacvg.dimp.bean.DefaultReportBean;
import com.datacvg.dimp.bean.DefaultUserListBean;
import com.datacvg.dimp.bean.DeletePageBean;
import com.datacvg.dimp.bean.DigitalPageBean;
import com.datacvg.dimp.bean.DimensionForTimeBean;
import com.datacvg.dimp.bean.DimensionListBean;
import com.datacvg.dimp.bean.DimensionPositionBean;
import com.datacvg.dimp.bean.EChartListBean;
import com.datacvg.dimp.bean.ImageResBean;
import com.datacvg.dimp.bean.IndexBean;
import com.datacvg.dimp.bean.IndexChartInfoBean;
import com.datacvg.dimp.bean.IndexTreeListBean;
import com.datacvg.dimp.bean.JudgeJobBean;
import com.datacvg.dimp.bean.KpiPermissionDataBean;
import com.datacvg.dimp.bean.MessageBean;
import com.datacvg.dimp.bean.ModuleListBean;
import com.datacvg.dimp.bean.PerformanceBean;
import com.datacvg.dimp.bean.ReadMessageBean;
import com.datacvg.dimp.bean.RecommendIndexBean;
import com.datacvg.dimp.bean.RemarkListBean;
import com.datacvg.dimp.bean.ReportBean;
import com.datacvg.dimp.bean.ReportListBean;
import com.datacvg.dimp.bean.ReportParamsBean;
import com.datacvg.dimp.bean.ReportTrashBean;
import com.datacvg.dimp.bean.ReportTrashListBean;
import com.datacvg.dimp.bean.SavePageBean;
import com.datacvg.dimp.bean.ScreenDetailBean;
import com.datacvg.dimp.bean.ScreenListBean;
import com.datacvg.dimp.bean.ScreenReportListBean;
import com.datacvg.dimp.bean.SearchListReportBean;
import com.datacvg.dimp.bean.SetDefaultResBean;
import com.datacvg.dimp.bean.TableInfoBean;
import com.datacvg.dimp.bean.TableListBean;
import com.datacvg.dimp.bean.TableParamInfoListBean;
import com.datacvg.dimp.bean.TaskInfoBean;
import com.datacvg.dimp.bean.TimeValueBean;
import com.datacvg.dimp.bean.UserJobsListBean;
import com.datacvg.dimp.bean.UserLoginBean;
import java.util.HashMap;
import java.util.Map;
import java.util.function.DoubleUnaryOperator;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
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
    @POST("api/mobile/login/userlogin")
    Observable<BaseBean> login(@Body Map<String, String> params);

    /**
     * 获取用户模块
     * @return
     */
    @GET("api/mobile/usermodel/usermodellist")
    Observable<BaseBean<ModuleListBean>> getPermissionModule();

    /**
     * 登出
     * @return
     */
    @GET("api/mobile/login/userloginout")
    Observable<BaseBean> loginOut();

    /**
     * 获取用户关联岗位信息
     * @param userPkid  用户标识id
     * @return
     */
    @GET("api/mobile/login/userposition")
    Observable<BaseBean<UserJobsListBean>> getJob(@Query("userPkid") String userPkid);

    /**
     * 查询报告
     * @param reportType
     * @param t
     * @return
     */
    @GET("api/dataengine/dataexporler/report/folder")
    Observable<BaseBean<ReportListBean>> getReport(@Query("parentId") String parentId,
                                                   @Query("type") String reportType,
                                                   @Query("_t") String t);

    /**
     * 获取所有大屏
     * @param screenType
     * @return
     */
    @GET("api/dataengine/largescreen/list")
    Observable<BaseBean<ScreenListBean>> getScreenList(@Query("sortVal") String screenType);

    /**
     * 获取指标列表
     * @return
     */
    @POST("api/ddb/indexpad/index/position")
    Observable<BaseBean<DimensionPositionBean>> getIndexPosition(@Body Map map);

    /**
     * 获取组织维度
     * @return
     */
    @POST("api/ddb/indexpad/indexChart/dimension")
    Observable<BaseBean<DimensionListBean>> getDimension(@Body Map map);

    /**
     * 获取其他维度
     * @return
     */
    @POST("api/ddb/indexpad/indexChart/other/dimension")
    Observable<BaseBean<DimensionListBean>> getOtherDimension(@Body Map map);

    /**
     * 获取图表详细数据
     * @param map
     * @return
     */
    @POST("api/mobile/indexpad/echarts")
    Observable<BaseBean<ChartListBean>> getCharts(@Body Map map);

    /**
     * 获取任务列表
     * @param map
     * @return
     */
    @POST("api/mobile/actionplan/infotask")
    Observable<BaseBean<ActionPlanListBean>> getActionList(@Body Map map);

    /**
     * 获取大屏详情
     * @param screen_id
     * @return
     */
    @GET("api/dataengine/largescreen/{id}")
    Observable<BaseBean<ScreenDetailBean>> getScreenDetail(@Path("id") String screen_id);

    /**
     * 获取消息
     * @param pageIndex
     * @param pageSize
     * @param module_id
     * @param read_flag
     * @return
     */
    @GET("api/mobile/login/message_info")
    Observable<BaseBean<MessageBean>> getMessage(@Query("pageIndex") String pageIndex,
                                                 @Query("pageSize") String pageSize,
                                                 @Query("module_id") String module_id,
                                                 @Query("read_flag") String read_flag);

    /**
     * 获取指标信息
     * @return
     */
    @GET("api/mobile/indexpad/index/classification")
    Observable<BaseBean<IndexBean>> getIndex();

    /**
     * 改变选择指标的顺序
     * @param indexIds  指标id拼接
     * @return
     */
    @POST("api/mobile/indexpad/position/change")
    Observable<BaseBean<String>> changeSelectedIndex(@Query("indexIds") String indexIds);


    /**
     * 查询报表列表数据
     * @param tableType 设备类型 3标识app
     * @return
     */
    @GET("api/mobile/mobilereport/h5/app_showcxtableauinfo")
    Observable<BaseBean<TableListBean>> getTableList(@Query("deviceType") String tableType);

    /**
     * 获取图片二进制资源文件
     * @param res_id
     * @return
     */
    @GET("api/mobile/mobilereport/app_readResImg")
    Observable<ImageResBean> getImageRes(@Query("res_id") String res_id);

    /**
     * 将消息设置为已读
     * @param doRead
     * @param id
     * @param read
     * @param module_id
     * @return
     */
    @GET("api/mobile/login/message_service")
    Observable<BaseBean<ReadMessageBean>> doReadMessage(@Query("key") String doRead,
                                                        @Query("message_id") String id,
                                                        @Query("read") String read,
                                                        @Query("module_id") String module_id) ;

    /**
     * 获取管理画布参数
     * @param reportId
     * @return
     */
    @GET("api/mobile/usermodel/getreportparameters")
    Observable<BaseBean<ReportParamsBean>> getReportParameters(@Query("model_id") String reportId);

    /**
     * 扫码登录
     * @param params
     * @return
     */
    @POST("api/mobile/login/mobsrv/scancode")
    Observable<BaseBean<String>> webLogin(@QueryMap Map<String, String> params);

    /**
     * 大屏投放
     * @param params
     * @return
     */
    @POST("api/dataengine/largescreen/websocket")
    Observable<BaseBean<String>> confirmOnTheScreen(@Body Map<String, String> params);

    @POST("api/dataengine/largescreen/websocket")
    Observable<BaseBean<String>> deleteOnTheScreen(@Body Map<String, String> params);

    /**
     * 获取报表参数
     * @param res_id
     * @return
     */
    @GET("api/report/params/init")
    Observable<BaseBean<TableParamInfoListBean>> getResParamInfo(@Query("resId") String res_id);

    /**
     * 查询报表配置
     * @param params
     * @return
     */
    @POST("api/mobile/mobilereport/h5/app_show_report")
    Observable<BaseBean<TableInfoBean>> getTableUrl(@Body Map<String, String> params);

    /**
     * 查询所有维度下人员信息
     * @return
     */
    @GET("api/mobile/actionplan/getdefaultuser")
    Observable<BaseBean<DefaultUserListBean>> getDefaultUser();

    /**
     * 查询指标
     * @return
     */
    @GET("api/mobile/actionplan/infoindex")
    Observable<BaseBean<ActionPlanIndexListBean>> getActionPlanIndex();

    @Headers({"Domain-Name: upload_file"})
    @Multipart
    @POST("/upload")
    Observable<BaseBean> uploadFile(@PartMap Map<String, RequestBody> partMap);

    /**
     * 查询报表相关评论
     * @return
     */
    @GET("api/report/comments/info/list")
    Observable<BaseBean<CommentListBean>> getTableComment(@Query("resId") String resId,
                                                          @Query("param") String param);

    /**
     * 提交评论
     * @param requestBodyMap
     * @return
     */
    @Headers({"Domain-Name: ddb_api"})
    @Multipart
    @POST("report/comments/submit/comments/reply")
    Observable<BaseBean> submitComments(@PartMap Map<String, RequestBody> requestBodyMap);

    @POST("api/mobile/indexpad/list")
    Observable<BaseBean<IndexTreeListBean>> getIndexTree(@Body Map map);

    @POST("api/ddb/actionplan/add")
    Observable<BaseBean> createTask(@Body Map map);

    @GET("api/mobile/actionplan/infodetail")
    Observable<BaseBean<TaskInfoBean>> getTaskInfo(@Query("taskId") String taskId,
                                                   @Query("userType") int userType,
                                                   @Query("userId") String userId,
                                                   @Query("language") String language);

    @GET("https://api.powerbi.cn/powerbi/globalservice/v201606/clusterdetails")
    Observable<String> getPowerBiInfo(@Header("authorization") String token);

    /**
     * 获取数字神经多页数据
     * @return
     */
    @GET("api/ddb/indexpad/position/page")
    Observable<BaseBean<DigitalPageBean>> getDigitalPage();

    /**
     * 获取默认时间权限
     * @return
     */
    @GET("api/ddb/indexpad/time/default/rule")
    Observable<BaseBean<TimeValueBean>> getTimeVal();

    /**
     * 获取对应图表数据
     * @param params
     * @return
     */
    @POST("api/ddb/indexpad/indexChart")
    Observable<BaseBean<EChartListBean>> getEChart(@Body HashMap params);

    /**
     * 根据page页删除
     * @param page
     * @return
     */
    @GET("api/ddb/indexpad/page/delete/{id}")
    Observable<BaseBean<DeletePageBean>> deletePageRequest(@Path("id") String page);

    /**
     * 保存页码指标信息
     * @param params
     * @return
     */
    @POST("api/ddb/indexpad/position/change")
    Observable<BaseBean<SavePageBean>> changeChart(@Body Map params);

    /**
     * 根据时间维度获取指标信息
     * @param timeVal
     * @return
     */
    @GET("api/ddb/indexpad/indexPageDimension")
    Observable<BaseBean<DimensionForTimeBean>> getIndexPageDimension(@Query("timeVal") String timeVal);

    /**
     * 添加页面信息
     * @param params
     * @return
     */
    @POST("api/ddb/indexpad/indexPageRelation")
    Observable<BaseBean> addPageRequest(@Body Map params);

    @POST("api/ddb/indexpad/indexChart/info")
    Observable<BaseBean<IndexChartInfoBean>> getIndexForDimension(@Body Map params);

    /**
     * 根据page页获取推荐指标
     * @param pageNo
     * @return
     */
    @GET("api/ddb/indexpad/indexChart/relation")
    Observable<BaseBean<RecommendIndexBean>> getRecommendIndexInfo(@Query("pageNo") String pageNo);

    /**
     * 获取所有哦指标信息
     * @return
     */
    @GET("api/ddb/indexpad/able/add/index/info")
    Observable<BaseBean<AllIndexBean>> getAllIndexInfo();

    /**
     * 获取默认报表相关信息
     * @param params
     * @return
     */
    @POST("api/mobile/mobilereport/getDefaultResInfo")
    Observable<DefaultReportBean> getDefaultReport(@Body Map params);

    /**
     * 设置为默认报表
     * @param map
     * @return
     */
    @POST("api/mobile/mobilereport/setDefaultRes")
    Observable<SetDefaultResBean> setReportToDefault(@Body Map map);

    @DELETE("api/mobile/mobilereport/cancelDefaultRes/{id}")
    Observable<BaseBean> cancelReportForDefault(@Path("id") String res_pkid);

    @POST("api/ddb/indexpad/position/budget")
    Observable<BaseBean<PerformanceBean>> getBudget(@Body Map params);

    /**
     * 根据指标获取关联报表
     * @param indexId
     * @return
     */
    @GET("api/mobile/indexpad/index/reports")
    Observable<BaseBean<TableListBean>> getReportByIndexId(@Query("indexId") String indexId);

    /**
     * 获取指标关联行动方案
     * @param params
     * @return
     */
    @POST("api/ddb/actionplan/search")
    Observable<BaseBean<ActionForIndexBean>> getActionForIndexId(@Body Map params);

    /**
     * 获取指标关联备注信息
     * @param params
     * @return
     */
    @POST("api/mobile/indexpad/index/remark/get")
    Observable<BaseBean<RemarkListBean>> getRemark(@Body Map params);

    /**
     * 发送备注信息
     * @param params
     * @return
     */
    @POST("api/mobile/indexpad/index/remark/save")
    Observable<BaseBean> saveRemark(@Body Map params);

    /**
     * 获取自定义阈值名称
     * @return
     */
    @GET("api/ddb/indexpad/kpi/permission")
    Observable<BaseBean<KpiPermissionDataBean>> getPermissionName();

    /**
     * 提交评论信息
     * @param params
     * @return
     */
    @POST("api/mobile/login/userfeedback")
    Observable<BaseBean> submitFeedBack(@Body Map params);

    /**
     * 上传用户信息
     * @param params
     * @param userId
     * @return
     */
    @POST("api/mobile/login/uploadheadimg")
    Observable<BaseBean> uploadAvatar(@Query("userId") String userId
            ,@Body Map params);

    /**
     * 上传缩略图
     * @param reportId
     * @param params
     * @return
     */
    @POST("api/dataengine/dataexporler/report/upload_thumbnails")
    Observable<BaseBean> uploadModelThumb(@Query("reportId") String reportId
            ,@Query("modelType") String type
            ,@Body Map params);

    /**
     * 删除报告
     * @param params
     * @return
     */
    @HTTP(method = "DELETE",path = "api/dataengine/dataexporler/report/folder",hasBody = true)
    Observable<BaseBean> deleteReport(@Body Map params);

    /**
     * 获取文件资源
     * @param reportId
     * @param type
     * @return
     */
    @GET("api/dataengine/dataexporler/report/export")
    Observable<String> downReportFile(@Query("resId") String reportId
            ,@Query("resType") String type);

    /**
     * 查询回收站所有资源文件
     * @param reportType
     * @param t
     * @return
     */
    @GET("api/dataengine/dataexporler/model/recycle")
    Observable<BaseBean<ReportTrashListBean>> queryReport(@Query("type") String reportType, @Query("_t") String t);

    /**
     * 回收站删除
     * @param params
     * @return
     */
    @HTTP(method = "DELETE",path = "api/dataengine/dataexporler/model/recycle",hasBody = true)
    Observable<Object> deleteReportOnTrash(@Body Map params);

    /**
     * 回收站还原
     * @param params
     * @return
     */
    @HTTP(method = "PUT",path = "api/dataengine/dataexporler/model/recycle",hasBody = true)
    Observable<Object> restoreOnTrash(@Body Map params);

    /**
     * 根据关键字查询报告
     * @param searchText
     * @return
     */
    @GET("api/portal/user/search_reports")
    Observable<BaseBean<SearchListReportBean>> searchReport(@Query("clname") String searchText);

    @POST("api/dataengine/dataexporler/report/screen")
    Observable<BaseBean> addToScreenRequest(@Body Map params);

    /**
     * 判断岗位是否可用
     * @param currentUserPkid
     * @param chooseUserPkid
     * @return
     */
    @GET("api/mobile/login/userchange/getcredentials")
    Observable<JudgeJobBean> judgeJobAvailability(@Query("userPkid") String currentUserPkid,
                                                  @Query("targetUserPkid") String chooseUserPkid);

    /**
     * 获取要切换的岗位信息
     * @param chooseUserId
     * @return
     */
    @GET("api/mobile/login/switchposition")
    Observable<BaseBean<UserLoginBean>> getJosInformation(@Query("loginName") String chooseUserId);

    /**
     * 注销岗位信息
     * @param token
     * @return
     */
    @GET("api/mobile/login/userchange/logoutticket")
    Observable<JudgeJobBean> logoutTicket(@Query("authorization") String token);

    /**
     * 删除回收站数据
     * @param type
     * @param s
     * @return
     */
    @GET("api/dataengine/dataexporler/model/recycle")
    Observable<BaseBean> clearReportOnTrash(@Query("type") String type,
                                            @Query("_t") String s);

    @GET("api/ddb/actionplan/behavior/operate")
    Observable<BaseBean> operateTask(@Query("key") String key,
                                     @Query("taskId") String taskId,
                                     @Query("text") String text,
                                     @Query("time") String time,
                                     @Query("confirm") String confirm,
                                     @Query("t") String _t);

    /**
     * 获取大屏报告列表
     * @return
     */
    @GET("api/dataengine/largescreen/data/report")
    Observable<BaseBean<ScreenReportListBean>> getReportInScreen();

    /**
     * 添加到大屏
     * @param params
     * @return
     */
    @POST("api/dataengine/largescreen/app/report")
    Observable<BaseBean> addScreenReport(@Body Map params);
}
