package com.datacvg.sempmobile.baseandroid.retrofit.helper;

import com.datacvg.sempmobile.baseandroid.config.Constants;
import com.google.gson.GsonBuilder;
import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * FileName: RetrofitHelper
 * Author: 曹伟
 * Date: 2019/9/16 14:46
 * Description:
 */

@Singleton
public class RetrofitHelper {

    private Retrofit mRetrofit = null;

    @Inject
    public RetrofitHelper() {
        mRetrofit = new Retrofit.Builder().baseUrl(Constants.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .setDateFormat("yyyy-MM-dd HH:mm:ss")
                        .create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    /**
     * .addConverterFactory(ScalarsConverterFactory.create())
     .addConverterFactory(GsonConverterFactory.create(onCreateGson()))
     .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
     * @return
     */

    public Retrofit getRetrofit() {
        return mRetrofit;
    }

    /* ------------------------fileupload------------------------- */
//    public interface UploadApi {
//        @Multipart
//        @POST("file/upload.do")
//        Observable<ResponseBody> uploadFileWithPartMap(@PartMap() Map<String, RequestBody> partMap, @Part MultipartBody.Part file);
//
//    }
//
//    public static final String MULTIPART_FORM_DATA = "multipart/form-data";
//
//    private static MultipartBody.Part prepareFilePart(String partName, String filepath) {
//        File file = new File(filepath);
//        // create RequestBody instance from file
//        RequestBody requestFile = RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), file);
//        // MultipartBody.Part is used to send also the actual file name
//        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
//    }
//
//    private static RequestBody createPartFromString(String descriptionString) {
//        return RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), descriptionString);
//    }
//
//    public static Observable<ResponseBody> uploadFileWithPartMap(String uploadBaseUrl, Map<String, String> paramMap, String filekey, String filepath) {
//        // create part for file (photo, video, ...)
//        MultipartBody.Part body = prepareFilePart(filekey, filepath);
//        HashMap<String, RequestBody> hashMap = new HashMap<>();
//        for (Map.Entry<String, String> entry : paramMap.entrySet()) {
//            RequestBody requestBody = createPartFromString(entry.getValue());
//            hashMap.put(entry.getKey(), requestBody);
//        }
//
//        UploadApi service = new Retrofit.Builder().baseUrl(uploadBaseUrl)
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
//                        .create()))
//                .build()
//                .create(UploadApi.class);
//        return service.uploadFileWithPartMap(hashMap, body);
//    }
}
