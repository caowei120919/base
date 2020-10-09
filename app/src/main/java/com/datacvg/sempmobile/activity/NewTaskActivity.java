package com.datacvg.sempmobile.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.datacvg.sempmobile.R;
import com.datacvg.sempmobile.baseandroid.utils.StatusBarUtil;
import com.datacvg.sempmobile.baseandroid.utils.TimeUtils;
import com.datacvg.sempmobile.presenter.NewTaskPresenter;
import com.datacvg.sempmobile.view.NewTaskView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-10-09
 * @Description :新建任务
 */
public class NewTaskActivity extends BaseActivity<NewTaskView, NewTaskPresenter>
        implements NewTaskView {
    @BindView(R.id.tv_title)
    TextView tvTitle ;
    @BindView(R.id.tv_right)
    TextView tvRight ;
    @BindView(R.id.tv_date)
    TextView tvDate ;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_new_task;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void setupView() {
        StatusBarUtil.setStatusBarColor(mContext
                ,resources.getColor(R.color.c_FFFFFF));
    }

    @Override
    protected void setupData(Bundle savedInstanceState) {
        tvTitle.setText(resources.getString(R.string.a_new_task));
        tvRight.setText(resources.getString(R.string.create));
        tvDate.setText(tvDate.getText().toString().replace("#1"
                , TimeUtils.getCurDateStr(TimeUtils.FORMAT_YMD)));
    }

    @OnClick({R.id.img_left,R.id.tv_date})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.img_left :
                    finish();
                break;

            case R.id.tv_date :

                break;
        }
    }
}
