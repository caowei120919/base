package com.datacvg.dimp.baseandroid.utils;

import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

/**
 * FileName: MyAppGlideModule
 * Author: 曹伟
 * Date: 2019/11/26 15:22
 * Description:
 */
@GlideModule
public class MyAppGlideModule extends AppGlideModule {
    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}
