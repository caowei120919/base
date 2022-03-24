package com.datacvg.dimp.activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.datacvg.dimp.R;
import com.datacvg.dimp.adapter.ReportInScreenAdapter;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.ToastUtils;
import com.datacvg.dimp.bean.ScreenBean;
import com.datacvg.dimp.bean.ScreenReportBean;
import com.datacvg.dimp.bean.ScreenReportListBean;
import com.datacvg.dimp.event.AddToScreenReportEvent;
import com.datacvg.dimp.presenter.ScreenReportPresenter;
import com.datacvg.dimp.view.ScreenReportView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2022-03-24
 * @Description : 大屏报告选择
 */
public class ScreenReportActivity extends BaseActivity<ScreenReportView, ScreenReportPresenter>
        implements ScreenReportView {
    @BindView(R.id.tv_title)
    TextView tvTitle ;
    @BindView(R.id.rv_screen_report)
    RecyclerView rvScreenReport ;

    private ScreenBean screenBean ;
    private List<ScreenReportBean> screenReportBeans = new ArrayList<>() ;
    private List<ScreenReportBean> screenReportChildBeans = new ArrayList<>() ;
    private ReportInScreenAdapter adapter ;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_screen_report;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void setupView() {
        tvTitle.setText(resources.getString(R.string.add_the_statements));
        screenBean = (ScreenBean) getIntent().getSerializableExtra(Constants.EXTRA_DATA_FOR_BEAN);
        adapter = new ReportInScreenAdapter(mContext,screenReportBeans,screenReportChildBeans);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        rvScreenReport.setLayoutManager(linearLayoutManager);
        rvScreenReport.setAdapter(adapter);
    }

    @Override
    protected void setupData(Bundle savedInstanceState) {
        getPresenter().getReportInScreen();
    }

    @OnClick({R.id.img_left,R.id.btn_report_save})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.img_left :
                finish();

            case R.id.btn_report_save :
                //选中的大屏数据
                Map params = new HashMap();
                List<Map<String,String>> reportList = new ArrayList<>() ;
                for (ScreenReportBean bean : screenReportChildBeans) {
                    if (bean.getOpen() && !"FOLDER".equals(bean.getRes_showtype())) {
                        Map<String,String> map = new HashMap();
                        map.put("reportId",bean.getRes_id());
                        if ("model_report".equals( bean.getRes_showtype()) || "share_report".equals(bean.getRes_showtype())) {
                            map.put("reportType",bean.getRes_showtype());
                        } else {
                            map.put("reportType","report");
                        }
                        reportList.add(map);
                    }
                }
                params.put("reportList",reportList);
                params.put("screenId",screenBean.getScreen_id());
                PLog.e("=============" + reportList.size());
                if(!reportList.isEmpty()){
                    getPresenter().addScreenReport(params);
                }else {
                    ToastUtils.showLongToast(resources.getString(R.string.report_not_selected));
                }
                break;
        }
    }

    @Override
    public void getReportInScreenSuccess(ScreenReportListBean data) {
        screenReportBeans.clear();
        screenReportChildBeans.clear();
        for (ScreenReportBean bean : data){
            if(bean.getRes_parentid().equals("0")){
                screenReportBeans.add(bean);
            }else{
                screenReportChildBeans.add(bean);
            }
        }
        Collections.reverse(screenReportChildBeans);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void addToScreenSuccess() {
        ToastUtils.showLongToast(resources.getString(R.string.add_success));
        EventBus.getDefault().post(new AddToScreenReportEvent());
        finish();
    }
}
