package com.datacvg.sempmobile.baseandroid.utils;

import android.app.Activity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.request.RequestOptions;
import com.lzy.imagepicker.loader.ImageLoader;


/**
 * FileName: GlideLoader
 * Author: 曹伟
 * Date: 2019/11/26 15:07
 * Description:
 */

public class GlideLoader implements ImageLoader {
    private RequestOptions mOptions = new RequestOptions()
            .centerCrop()
            .format(DecodeFormat.PREFER_RGB_565);

    private RequestOptions mPreOptions = new RequestOptions()
            .skipMemoryCache(true);

    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
        //小图加载
        Glide.with(imageView.getContext()).load(path).apply(mOptions).into(imageView);
    }

    @Override
    public void displayImagePreview(Activity activity, String path, ImageView imageView, int width, int height) {
        //大图加载
        Glide.with(imageView.getContext()).load(path).apply(mPreOptions).into(imageView);
    }

    @Override
    public void clearMemoryCache() {
        //清理缓存
        Glide.get(AndroidUtils.getContext()).clearMemory();
    }
}
