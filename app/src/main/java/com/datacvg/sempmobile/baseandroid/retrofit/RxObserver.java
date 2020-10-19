package com.datacvg.sempmobile.baseandroid.retrofit;

import android.net.ParseException;

import com.datacvg.sempmobile.baseandroid.config.Constants;
import com.datacvg.sempmobile.baseandroid.retrofit.bean.BaseBean;
import com.datacvg.sempmobile.baseandroid.utils.ToastUtils;
import com.datacvg.sempmobile.bean.ServiceBean;
import com.datacvg.sempmobile.event.RefreshTokenEvent;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-07-22
 * @Description :
 */
public class RxObserver<T> implements Observer <T>{
    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T t) {
        if(t instanceof BaseBean){
            if(((BaseBean) t).getStatus() == Constants.SERVICE_CODE_SUCCESS_MOBILE
                    || ((BaseBean) t).getStatus() == Constants.SERVICE_CODE_SUCCESS_FIS){
                onNext(t);
            }else if(((BaseBean) t).getStatus() == Constants.SERVICE_CODE_FAIL_FOR_TOKEN){
                EventBus.getDefault().post(new RefreshTokenEvent());
            }else {
                ToastUtils.showLongToast(((BaseBean) t).getMessage());
            }
        }else if(t instanceof ServiceBean){
            if(((ServiceBean) t).getStatus() == 1){
                onNext(t);
            }else{
                ToastUtils.showLongToast(((ServiceBean) t).getMessage());
            }
        }
    }

    @Override
    public void onError(Throwable e) {
        String msg = "";
        if (e instanceof ConnectException) {
                msg = "网络不可用";
        } else if (e instanceof UnknownHostException) {
            msg = "网络不可用";
        } else if (e instanceof SocketTimeoutException) {
            msg = "请求网络超时";
        } else if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            msg = convertStatusCode(httpException);
        } else if (e instanceof JsonParseException || e instanceof ParseException || e instanceof JSONException || e instanceof JsonIOException) {
            msg = "数据解析错误";
        }

        if (!msg.isEmpty()) {
            ToastUtils.showShortToastSafe(msg);
        }
    }

    private String convertStatusCode(HttpException httpException) {
        String msg;
        if (httpException.code() == 500) {
            msg = "服务器发生错误";
        } else if (httpException.code() == 404) {
            msg = "请求地址不存在";
        } else if (httpException.code() == 403) {
            msg = "请求被服务器拒绝";
        } else if (httpException.code() == 307) {
            msg = "请求被重定向到其他页面";
        } else {
            msg = httpException.message();
        }
        return msg;
    }

    @Override
    public void onComplete() {

    }
}
