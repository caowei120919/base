package com.datacvg.dimp.baseandroid.dragger.component;

import android.app.Activity;


import com.datacvg.dimp.activity.AddIndexActivity;
import com.datacvg.dimp.activity.AddIndexPageActivity;
import com.datacvg.dimp.activity.AddReportToScreenActivity;
import com.datacvg.dimp.activity.ChartDetailActivity;
import com.datacvg.dimp.activity.ConfirmInfoActivity;
import com.datacvg.dimp.activity.ContactActivity;
import com.datacvg.dimp.activity.DepartmentActivity;
import com.datacvg.dimp.activity.FeedBackActivity;
import com.datacvg.dimp.activity.IndexDetailActivity;
import com.datacvg.dimp.activity.IndexPopActivity;
import com.datacvg.dimp.activity.IndexTreeActivity;
import com.datacvg.dimp.activity.LanguageSettingActivity;
import com.datacvg.dimp.activity.LoginActivity;
import com.datacvg.dimp.activity.LoginWebActivity;
import com.datacvg.dimp.activity.MainActivity;
import com.datacvg.dimp.activity.MessageCentreActivity;
import com.datacvg.dimp.activity.MessageListActivity;
import com.datacvg.dimp.activity.ModuleSettingActivity;
import com.datacvg.dimp.activity.MyIndexActivity;
import com.datacvg.dimp.activity.NewTaskActivity;
import com.datacvg.dimp.activity.QRCodeActivity;
import com.datacvg.dimp.activity.ReportDetailActivity;
import com.datacvg.dimp.activity.ReportFolderActivity;
import com.datacvg.dimp.activity.ReportGridOnFolderActivity;
import com.datacvg.dimp.activity.ScanActivity;
import com.datacvg.dimp.activity.ScreenDetailActivity;
import com.datacvg.dimp.activity.ScreenResultActivity;
import com.datacvg.dimp.activity.ScreenSettingActivity;
import com.datacvg.dimp.activity.SearchIndexActivity;
import com.datacvg.dimp.activity.SearchReportActivity;
import com.datacvg.dimp.activity.SelectDimensionActivity;
import com.datacvg.dimp.activity.SelectFilterActivity;
import com.datacvg.dimp.activity.SelectTableParamActivity;
import com.datacvg.dimp.activity.SettingActivity;
import com.datacvg.dimp.activity.SettingVpnActivity;
import com.datacvg.dimp.activity.SnapShotActivity;
import com.datacvg.dimp.activity.SplashActivity;
import com.datacvg.dimp.activity.TableCommentActivity;
import com.datacvg.dimp.activity.TableDetailActivity;
import com.datacvg.dimp.activity.TableFolderActivity;
import com.datacvg.dimp.activity.TaskDetailActivity;
import com.datacvg.dimp.activity.TestRealActivity;
import com.datacvg.dimp.baseandroid.dragger.module.ActivityModule;
import com.datacvg.dimp.baseandroid.dragger.scope.ActivityScope;

import dagger.Component;

/**
 * @author 曹伟
 */
@ActivityScope
@Component(dependencies = MyAppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    Activity getActivity();

    void inject(SplashActivity splashActivity);

    void inject(LoginActivity loginActivity);

    void inject(MainActivity mainActivity);

    void inject(SettingVpnActivity settingVpnActivity);

    void inject(QRCodeActivity qrCodeActivity);

    void inject(ScanActivity scanActivity);

    void inject(SettingActivity settingActivity);

    void inject(LanguageSettingActivity languageSettingActivity);

    void inject(ModuleSettingActivity moduleSettingActivity);

    void inject(MyIndexActivity myIndexActivity);

    void inject(NewTaskActivity newTaskActivity);

    void inject(ScreenDetailActivity screenDetailActivity);

    void inject(ScreenSettingActivity screenSettingActivity);

    void inject(TestRealActivity testRealActivity);

    void inject(TableFolderActivity tableFolderActivity);

    void inject(MessageCentreActivity messageCentreActivity);

    void inject(MessageListActivity messageListActivity);

    void inject(ReportDetailActivity reportDetailActivity);

    void inject(LoginWebActivity loginWebActivity);

    void inject(ConfirmInfoActivity confirmInfoActivity);

    void inject(ScreenResultActivity screenResultActivity);

    void inject(TableDetailActivity tableDetailActivity);

    void inject(SelectTableParamActivity selectTableParamActivity);

    void inject(ContactActivity contactActivity);

    void inject(DepartmentActivity departmentActivity);

    void inject(IndexDetailActivity indexDetailActivity);

    void inject(IndexTreeActivity indexTreeActivity);

    void inject(TaskDetailActivity taskDetailActivity);

    void inject(TableCommentActivity tableCommentActivity);

    void inject(AddIndexPageActivity addIndexPageActivity);

    void inject(AddIndexActivity addIndexActivity);

    void inject(ChartDetailActivity chartDetailActivity);

    void inject(SelectDimensionActivity selectDimensionActivity);

    void inject(SearchIndexActivity searchIndexActivity);

    void inject(SelectFilterActivity selectFilterActivity);

    void inject(IndexPopActivity indexPopActivity);

    void inject(SnapShotActivity snapShotActivity);

    void inject(FeedBackActivity feedBackActivity);

    void inject(ReportFolderActivity reportFolderActivity);

    void inject(SearchReportActivity searchReportActivity);

    void inject(AddReportToScreenActivity addReportToScreenActivity);

    void inject(ReportGridOnFolderActivity reportGridOnFolderActivity);
}
