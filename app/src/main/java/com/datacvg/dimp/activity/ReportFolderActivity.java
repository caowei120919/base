package com.datacvg.dimp.activity;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.datacvg.dimp.R;
import com.datacvg.dimp.adapter.ReportListAdapter;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.utils.LanguageUtils;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.StatusBarUtil;
import com.datacvg.dimp.bean.ReportBean;
import com.datacvg.dimp.bean.ReportListBean;
import com.datacvg.dimp.presenter.ReportFolderPresenter;
import com.datacvg.dimp.view.ReportFolderView;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-09-17
 * @Description :管理画布文件夹详情
 */
public class ReportFolderActivity extends BaseActivity<ReportFolderView, ReportFolderPresenter>
        implements ReportFolderView {
    @BindView(R.id.tv_title)
    TextView tvTitle ;
    @BindView(R.id.img_right)
    ImageView imgRight ;
    @BindView(R.id.img_other)
    ImageView imgOther ;

    private ReportBean reportBean ;
    private String folderType ;
    private ReportListAdapter adapter ;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_report_folder;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void setupView() {
        StatusBarUtil.setStatusBarColor(mContext
                ,mContext.getResources().getColor(R.color.c_FFFFFF));

        reportBean = (ReportBean) getIntent().getSerializableExtra(Constants.EXTRA_DATA_FOR_BEAN);
        folderType = getIntent().getStringExtra(Constants.EXTRA_DATA_FOR_SCAN);
        if(reportBean == null || TextUtils.isEmpty(folderType)){
            finish();
            return;
        }
        imgRight.setImageBitmap(BitmapFactory.decodeResource(resources,R.mipmap.icon_sort));
        imgOther.setImageBitmap(BitmapFactory.decodeResource(resources,R.mipmap.icon_search_of_report));
        switch (folderType){
            case Constants.REPORT_MINE :
                tvTitle.setText(LanguageUtils.isZh(mContext) ? reportBean.getModel_clname()
                        : reportBean.getModel_flname());
                break;
        }
    }

    @Override
    protected void setupData(Bundle savedInstanceState) {
        switch (folderType){
            case Constants.REPORT_MINE :
                getPresenter().getReportOnFolder(folderType
                        ,reportBean.getModel_id()
                        ,String.valueOf(System.currentTimeMillis()));
                break;
        }
    }

    @OnClick({R.id.img_right,R.id.img_other})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.img_other :
                PLog.e("跳转到搜索");
                break;

            case R.id.img_right :
                PLog.e("排序");
                break;
        }
    }

    @Override
    public void getReportSuccess(ReportListBean data) {

    }
}
