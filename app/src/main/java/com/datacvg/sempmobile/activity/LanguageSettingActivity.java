package com.datacvg.sempmobile.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.datacvg.sempmobile.R;
import com.datacvg.sempmobile.adapter.LanguageAdapter;
import com.datacvg.sempmobile.baseandroid.config.Constants;
import com.datacvg.sempmobile.baseandroid.retrofit.helper.PreferencesHelper;
import com.datacvg.sempmobile.baseandroid.utils.AndroidUtils;
import com.datacvg.sempmobile.baseandroid.utils.LanguageUtils;
import com.datacvg.sempmobile.baseandroid.utils.StatusBarUtil;
import com.datacvg.sempmobile.baseandroid.widget.CVGOKCancelWithTitle;
import com.datacvg.sempmobile.bean.LanguageBean;
import com.datacvg.sempmobile.presenter.LanguageSettingPresenter;
import com.datacvg.sempmobile.view.LanguageSettingView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-09-28
 * @Description :设置语言
 */
public class LanguageSettingActivity extends BaseActivity<LanguageSettingView, LanguageSettingPresenter>
        implements LanguageSettingView, LanguageAdapter.ChangeLanguageClick {

    @BindView(R.id.tv_title)
    TextView tvTile ;

    @BindView(R.id.recycler_language)
    RecyclerView recyclerLanguage ;

    private LanguageAdapter adapter ;
    private List<LanguageBean> languageBeans = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_language;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void setupView() {
        StatusBarUtil.setStatusBarColor(mContext
                ,mContext.getResources().getColor(R.color.c_FFFFFF));
        tvTile.setText(resources.getString(R.string.language_settings));
    }

    @Override
    protected void setupData(Bundle savedInstanceState) {
        String[] languageNames = resources.getStringArray(R.array.language_name);
        String[] languageValues = resources.getStringArray(R.array.language_value);
        for (int i = 0 ; i < languageNames.length ; i++){
            LanguageBean bean = new LanguageBean(languageNames[i],languageValues[i]) ;
            languageBeans.add(bean);
        }
        adapter = new LanguageAdapter(mContext,languageBeans,this);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(RecyclerView.VERTICAL);
        manager.setAutoMeasureEnabled(true);
        recyclerLanguage.setLayoutManager(manager);
        recyclerLanguage.setAdapter(adapter);

    }

    @OnClick({R.id.img_left})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.img_left :
                    finish();
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void changeLanguageClick(int position) {
        CVGOKCancelWithTitle dialogOKCancel = new CVGOKCancelWithTitle(mContext);
        dialogOKCancel.setMessage(resources
                .getString(R.string.are_you_sure_you_want_to_change_the_application_language));
        dialogOKCancel.getPositiveButton().setText(mContext.getResources().getString(R.string.confirm));
        dialogOKCancel.setOnClickPositiveButtonListener(view -> {
            changeLanguage(languageBeans.get(position).getValue());
            dialogOKCancel.dismiss();
        });
        dialogOKCancel.setOnClickListenerNegativeBtn(view -> {
            dialogOKCancel.dismiss();
        });
        dialogOKCancel.show();
    }

    /**
     * 如果是7.0以下，我们需要调用changeAppLanguage方法，
     * 如果是7.0及以上系统，直接把我们想要切换的语言类型保存在SharedPreferences中即可
     * 然后重新启动MainActivity
     * @param language
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void changeLanguage(String language) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            LanguageUtils.changeAppLanguage(AndroidUtils.getContext(), language);
        }
        PreferencesHelper.put(Constants.APP_LANGUAGE, language);
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
