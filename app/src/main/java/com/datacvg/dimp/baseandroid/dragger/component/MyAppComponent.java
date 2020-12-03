package com.datacvg.dimp.baseandroid.dragger.component;



import com.datacvg.dimp.baseandroid.config.FisApi;
import com.datacvg.dimp.baseandroid.config.LoginApi;
import com.datacvg.dimp.baseandroid.config.MobileApi;
import com.datacvg.dimp.baseandroid.config.UploadApi;
import com.datacvg.dimp.baseandroid.dragger.module.MyAppModule;
import com.datacvg.dimp.baseandroid.dragger.scope.MyAppScope;

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
