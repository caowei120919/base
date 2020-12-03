package com.datacvg.dimp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.datacvg.dimp.R;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.utils.StatusBarUtil;
import com.datacvg.dimp.bean.MessageBean;
import com.datacvg.dimp.presenter.MessageCentrePresenter;
import com.datacvg.dimp.view.MessageCentreView;
import com.datacvg.dimp.widget.CircleNumberView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-11-09
 * @Description : 消息中心
 */
public class MessageCentreActivity extends BaseActivity<MessageCentreView, MessageCentrePresenter>
        implements MessageCentreView {
    @BindView(R.id.tv_title)
    TextView tvTitle ;
    @BindView(R.id.rel_action)
    RelativeLayout relAction ;
    @BindView(R.id.tv_actionUnReadMessageCount)
    CircleNumberView actionUnReadMessageCount ;
    @BindView(R.id.rel_warning)
    RelativeLayout relWarning ;
    @BindView(R.id.tv_warningUnReadMessageCount)
    CircleNumberView warningUnReadMessageCount ;
    @BindView(R.id.rel_report)
    RelativeLayout relReport ;
    @BindView(R.id.tv_reportUnReadMessageCount)
    CircleNumberView reportUnReadMessageCount ;
    @BindView(R.id.rel_management)
    RelativeLayout relManagement ;
    @BindView(R.id.tv_managementUnReadMessageCount)
    CircleNumberView managementUnReadMessageCount ;

    private int pageSize = Constants.MAX_PAGE_SIZE ;
    private int pageIndex = 1;
    private String module_id = "" ;
    /**
     * T for true and F for false
     */
    private String read_flag = "F" ;
    private List<String> permissions = new ArrayList<>() ;
    private int actionUnReadCount = 0 ;
    private int warnUnReadCount = 0 ;
    private int reportUnReadCount = 0 ;
    private int commentUnReadCount = 0 ;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message_centre;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void setupView() {
        StatusBarUtil.setStatusBarColor(mContext,resources.getColor(R.color.c_FFFFFF));
    }

    @Override
    protected void setupData(Bundle savedInstanceState) {
        tvTitle.setText(resources.getString(R.string.the_message_center));
        getMessage();
    }

    /**
     * 查询消息
     */
    private void getMessage() {
        getPresenter().getMessage(pageIndex+ "",pageSize + "",module_id,read_flag);
    }

    @OnClick({R.id.img_left,R.id.rel_action,R.id.rel_warning,R.id.rel_report,R.id.rel_management})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.img_left :
                    finish();
                break;

            case R.id.rel_action :
                    Intent intent = new Intent(mContext,MessageListActivity.class);
                    intent.putExtra("module_id",Constants.MSG_ACTION);
                    mContext.startActivity(intent);
                break;

            case R.id.rel_warning :
                Intent intentWarning = new Intent(mContext,MessageListActivity.class);
                intentWarning.putExtra("module_id",Constants.MSG_WARN);
                mContext.startActivity(intentWarning);
                break;

            case R.id.rel_report :
                Intent intentReport = new Intent(mContext,MessageListActivity.class);
                intentReport.putExtra("module_id",Constants.MSG_REPORTCOMMENT);
                mContext.startActivity(intentReport);
                break;

            case R.id.rel_management :
                Intent intentManager = new Intent(mContext,MessageListActivity.class);
                intentManager.putExtra("module_id",Constants.MSG_DASHBOARD);
                mContext.startActivity(intentManager);
                break;
        }
    }

    /**
     * 获取消息成功
     * @param resdata
     */
    @Override
    public void getMessageSuccess(MessageBean resdata) {
        if (resdata == null){
            return;
        }
        if(resdata.getPermissions() == null || resdata.getPermissions().size() == 0){
            return;
        }
        List<MessageBean.ResultBean> resultBeans = resdata.getResult();
        permissions.addAll(resdata.getPermissions());
        relAction.setVisibility(permissions.contains(Constants.MSG_ACTION)
                ? View.VISIBLE : View.GONE);
        relWarning.setVisibility(permissions.contains(Constants.MSG_WARN)
                ? View.VISIBLE : View.GONE);
        relReport.setVisibility(permissions.contains(Constants.MSG_REPORTCOMMENT)
                ? View.VISIBLE : View.GONE);
        relManagement.setVisibility(permissions.contains(Constants.MSG_DASHBOARD)
                ? View.VISIBLE : View.GONE);
        for (MessageBean.ResultBean bean : resultBeans){
            if (!permissions.contains(bean.getModule_id())){
                continue;
            }
            switch (bean.getModule_id()){
                case Constants.MSG_ACTION :
                        actionUnReadCount ++ ;
                    break;

                case Constants.MSG_WARN :
                        warnUnReadCount ++ ;
                    break;

                case Constants.MSG_REPORTCOMMENT :
                        reportUnReadCount ++ ;
                    break;

                case Constants.MSG_DASHBOARD :
                        commentUnReadCount ++ ;
                    break;

                default:

                    break;
            }
        }
        actionUnReadMessageCount.setNumber(actionUnReadCount + "");
        warningUnReadMessageCount.setNumber(warnUnReadCount + "");
        reportUnReadMessageCount.setNumber(reportUnReadCount + "");
        managementUnReadMessageCount.setNumber(commentUnReadCount + "");
    }
}
