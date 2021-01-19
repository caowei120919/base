package com.datacvg.dimp.baseandroid.retrofit;

import android.net.ParseException;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.retrofit.bean.BaseBean;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.ToastUtils;
import com.datacvg.dimp.bean.ImageResBean;
import com.datacvg.dimp.bean.ServiceBean;
import com.datacvg.dimp.event.RefreshTokenEvent;
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

    /**
     * 校验接口返回数据
     * @param t
     * @param <T>
     * @return  数据接口请求成功返回true  否则返回false
     */
    public static <T> boolean checkJsonCode(T t) {
        if (t == null) {
            return false;
        }
        if(t instanceof BaseBean){
            if (((BaseBean) t).getStatus() == Constants.SERVICE_CODE_SUCCESS_FIS
                    || ((BaseBean) t).getStatus() == Constants.SERVICE_CODE_SUCCESS_MOBILE) {
                return true ;
            }else if(((BaseBean) t).getStatus() == Constants.SERVICE_CODE_FAIL_FOR_TOKEN){
                EventBus.getDefault().post(new RefreshTokenEvent());
                return false ;
            }
            ToastUtils.showLongToast(((BaseBean) t).getMessage());
            return false ;
        }
        return false ;
    }

}
