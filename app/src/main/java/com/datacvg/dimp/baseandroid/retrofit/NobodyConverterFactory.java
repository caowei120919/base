package com.datacvg.dimp.baseandroid.retrofit;

import com.datacvg.dimp.baseandroid.retrofit.bean.EmptyBean;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-07-22
 * @Description :
 */
public class NobodyConverterFactory extends Converter.Factory {
    public static final NobodyConverterFactory create() {
        return new NobodyConverterFactory();
    }

    private NobodyConverterFactory() {
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                            Retrofit retrofit) {
        //判断当前的类型是否是我们需要处理的类型
        if (EmptyBean.class.equals(type)) {
            //是则创建一个Converter返回转换数据
            return new Converter<ResponseBody, EmptyBean>() {
                @Override
                public EmptyBean convert(ResponseBody value) throws IOException {
                    //这里直接返回null是因为我们不需要使用到响应体,本来也没有响应体.
                    //返回的对象会存到response.body()里.
                    return null;
                }
            };
        }
        return null;
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type
            ,Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return null;
    }

    @Override
    public Converter<?, String> stringConverter(Type type, Annotation[] annotations,
                                                Retrofit retrofit) {
        return null;
    }
}
