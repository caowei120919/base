package com.datacvg.dimp.baseandroid.oklog;


import com.datacvg.dimp.baseandroid.greendao.bean.DebugInfo;
import com.datacvg.dimp.baseandroid.greendao.controller.DbDebugInfoController;
import com.datacvg.dimp.baseandroid.utils.AndroidUtils;
import com.datacvg.dimp.baseandroid.utils.TimeUtils;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * FileName: OkLogInterceptor
 * Author: 曹伟
 * Date: 2019/9/16 16:16
 * Description:
 */

public class OkLogInterceptor implements Interceptor {

    private final LogDataInterceptor logDataInterceptor;

    public OkLogInterceptor() {
        this.logDataInterceptor = new LogDataInterceptor();
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        BaseLogDataInterceptor.RequestLogData<Request> requestLogData = logDataInterceptor.processRequest(chain);
        LogDataBuilder logDataBuilder = requestLogData.getLogData();
        DebugInfo debugInfo = new DebugInfo();
        long startNs = System.nanoTime();
        Response response;
        try {
            response = chain.proceed(requestLogData.getRequest());
        } catch (Exception e) {
            logDataBuilder.requestFailed();
            debugInfo.setDebugtype("network");
            logDataBuilder.responseBody("<-- HTTP FAILED: " + e.getMessage());
            debugInfo.setDebuginfo(logDataBuilder.toString());
            debugInfo.setDebugtime(TimeUtils.getCurDateStr(TimeUtils.FORMAT_YMDH_CN));
            DbDebugInfoController.getInstance(AndroidUtils.getContext())
                    .insertDebugInfo(debugInfo);
            throw e;
        }
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);
        logDataBuilder.responseDurationMs(tookMs);

        BaseLogDataInterceptor.ResponseLogData<Response> responseLogData = logDataInterceptor.processResponse(logDataBuilder, response);
        debugInfo.setDebugtype("network");
        debugInfo.setDebuginfo(logDataBuilder.toString());
        debugInfo.setDebugtime(TimeUtils.getCurDateStr(TimeUtils.FORMAT_YMDH_CN));
        DbDebugInfoController.getInstance(AndroidUtils.getContext())
                .insertDebugInfo(debugInfo);
        return responseLogData.getResponse();
    }

}
