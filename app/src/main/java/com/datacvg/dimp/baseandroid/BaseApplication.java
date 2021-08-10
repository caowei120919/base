package com.datacvg.dimp.baseandroid;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.StrictMode;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.datacvg.dimp.R;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.download.DownloadConfiguration;
import com.datacvg.dimp.baseandroid.download.DownloadManager;
import com.datacvg.dimp.baseandroid.dragger.component.AppComponent;
import com.datacvg.dimp.baseandroid.dragger.component.DaggerAppComponent;
import com.datacvg.dimp.baseandroid.dragger.module.AppModule;
import com.datacvg.dimp.baseandroid.greendao.bean.ModuleInfo;
import com.datacvg.dimp.baseandroid.greendao.controller.DbModuleInfoController;
import com.datacvg.dimp.baseandroid.manager.BaseAppManager;
import com.datacvg.dimp.baseandroid.retrofit.helper.PreferencesHelper;
import com.datacvg.dimp.baseandroid.utils.AndroidUtils;
import com.datacvg.dimp.baseandroid.utils.LanguageUtils;
import com.facebook.stetho.Stetho;
import com.orhanobut.hawk.Hawk;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshFooter;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshFooterCreator;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshHeaderCreator;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-07-22
 * @Description : 基类application，处理一些事件处理
 */
public class BaseApplication extends Application {
    private static AppComponent mAppComponent;
    private static BaseAppManager mAppManager;
    private static RefWatcher mRefWatcher;

    public static boolean DEBUGMODE = false;
    private int[] normalIds = {R.mipmap.tab_index_normal,R.mipmap.tab_textreport_normal
            ,R.mipmap.tab_report_normal,R.mipmap.tab_action_normal,R.mipmap.tab_screen_normal
            ,R.mipmap.tab_account_normal};
    private int[] selectedIds = {R.mipmap.tab_index_selected,R.mipmap.tab_textreport_selected
            ,R.mipmap.tab_report_selected,R.mipmap.tab_action_selected,R.mipmap.tab_screen_selected
            ,R.mipmap.tab_account_selected};

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate() {
        super.onCreate();

        AndroidUtils.init(this);

        checkLanguage();

        initAppInject(this);

        initDownloader(this);

        Hawk.init(this).build();

        buildAppModule();

        registerFlutter();
    }

    /**
     * 切换语言
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void checkLanguage() {
        /**
         * 对于7.0以下，需要在Application创建的时候进行语言切换
         */
        String language = PreferencesHelper.get(Constants.APP_LANGUAGE,Constants.APP_LANGUAGE);
        if(language.equals(Constants.LANGUAGE_AUTO)){
            return;
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            LanguageUtils.changeAppLanguage(this, language);
        }
    }

    /**
     * 构建App初始模块
     *      已经构建过则不需做重复操作
     */
    private void buildAppModule() {
        if(DbModuleInfoController.getInstance(this).getModuleList().size() > 0){
            return;
        }
        String[] module_title = this.getResources().getStringArray(R.array.module_title) ;
        String[] module_id = this.getResources().getStringArray(R.array.module_id) ;
        String[] module_fragment = this.getResources().getStringArray(R.array.module_fragment);
        for (int i = 0; i < module_title.length; i ++){
            ModuleInfo moduleInfo = new ModuleInfo();
            moduleInfo.setModule_id(i);
            moduleInfo.setModule_name(module_title[i]);
            moduleInfo.setModule_res_id(module_id[i]);
            moduleInfo.setModule_checked(i != 4);
            moduleInfo.setModule_permission(i==5);
            moduleInfo.setModule_normal_res(normalIds[i]);
            moduleInfo.setModule_selected_res(selectedIds[i]);
            moduleInfo.setModule_fragment_name(module_fragment[i]);
            DbModuleInfoController.getInstance(this).insertModuleInfo(moduleInfo);
        }
    }

    /**
     * 注册flutter
     */
    private void registerFlutter() {

    }

    public static RefWatcher getRefWatcher() {
        return mRefWatcher;
    }

    public static void setDebugMode(boolean debugmode) {
        BaseApplication.DEBUGMODE = debugmode;

        if (BaseApplication.DEBUGMODE) {
            mRefWatcher = LeakCanary.install((Application) AndroidUtils.getContext());

            Stetho.initializeWithDefaults(AndroidUtils.getContext());

            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()
                    .penaltyLog()
                    .build());

            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .penaltyLog()
                    .build());

        }
    }

    private static void initDownloader(Context context) {
        DownloadConfiguration configuration = new DownloadConfiguration();
        configuration.setMaxThreadNum(6);
        DownloadManager.getInstance().init(context, configuration);
    }

    public static BaseAppManager getAppManager() {
        if (mAppManager == null) {
            synchronized (BaseApplication.class) {
                if (mAppManager == null) {
                    mAppManager = BaseAppManager.getManager();
                }
            }
        }
        return mAppManager;
    }

    public void initAppInject(Context context) {
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule((Application) context))
                .build();
    }

    public static AppComponent getAppComponent() {
        return mAppComponent;
    }

    @Override
    public void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    public static void exitApp() {
        mAppManager.exitApp();
    }

    static {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.c_999999, android.R.color.white);//全局设置主题颜色
                return new ClassicsHeader(context);
            }
        });
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

    public static void restartApp(@NonNull Activity activity) {
        Intent intent = activity.getPackageManager()
                .getLaunchIntentForPackage(activity.getPackageName());
        Class<? extends Activity> resolvedActivityClass;
        if (intent != null && intent.getComponent() != null) {
            try {
                resolvedActivityClass = (Class<? extends Activity>) Class.forName(intent.getComponent()
                        .getClassName());
                intent.setClass(activity, resolvedActivityClass);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK
                        | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                if (intent.getComponent() != null) {
                    intent.setAction(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_LAUNCHER);
                }
                activity.finish();
                activity.startActivity(intent);
                exitApp();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

}
