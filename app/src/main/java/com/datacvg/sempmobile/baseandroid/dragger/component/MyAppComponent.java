package com.datacvg.sempmobile.baseandroid.dragger.component;



import com.datacvg.sempmobile.baseandroid.config.FisApi;
import com.datacvg.sempmobile.baseandroid.config.LoginApi;
import com.datacvg.sempmobile.baseandroid.config.MobileApi;
import com.datacvg.sempmobile.baseandroid.config.UploadApi;
import com.datacvg.sempmobile.baseandroid.dragger.module.MyAppModule;
import com.datacvg.sempmobile.baseandroid.dragger.scope.MyAppScope;

import dagger.Component;

@MyAppScope
@Component(dependencies = AppComponent.class, modules = MyAppModule.class
       /* modules = {MyAppModule.class, AppModule.class}*/)
public interface MyAppComponent {

    LoginApi retrofitLoginApiHelper();

    UploadApi retrofitUploadApiHelper();

    MobileApi retrofitMobileApiHelper();

    FisApi retrofitFisApiHelper();

}
