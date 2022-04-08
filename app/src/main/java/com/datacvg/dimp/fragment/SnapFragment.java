package com.datacvg.dimp.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.datacvg.dimp.R;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.TimeUtils;
import com.datacvg.dimp.bean.KpiPermissionDataBean;
import com.datacvg.dimp.bean.SaveDataListBean;
import com.datacvg.dimp.bean.TaskInfoBean;
import com.datacvg.dimp.presenter.SnapPresenter;
import com.datacvg.dimp.view.SnapView;
import com.datacvg.dimp.widget.VerticalProgressBar;
import com.enlogy.statusview.StatusRelativeLayout;
import com.google.gson.Gson;
import java.util.Arrays;
import java.util.List;
import butterknife.BindView;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-08-03
 * @Description : 行动方案
 */
public class SnapFragment extends BaseFragment<SnapView, SnapPresenter> implements SnapView {
    @BindView(R.id.status_snapTop)
    StatusRelativeLayout statusSnapTop ;
    @BindView(R.id.status_snap)
    StatusRelativeLayout statusSnap ;
    @BindView(R.id.tv_target)
    TextView tvTarget ;
    @BindView(R.id.tv_targetValue)
    TextView tvTargetValue ;
    @BindView(R.id.tv_targetDescribe)
    TextView tvTargetDescribe ;
    @BindView(R.id.tv_challenge)
    TextView tvChallenge ;
    @BindView(R.id.tv_challengeValue)
    TextView tvChallengeValue ;
    @BindView(R.id.tv_challengeDescribe)
    TextView tvChallengeDescribe ;
    @BindView(R.id.tv_minimum)
    TextView tvMinimum ;
    @BindView(R.id.tv_minimumValue)
    TextView tvMinimumValue ;
    @BindView(R.id.tv_minimumDescribe)
    TextView tvMinimumDescribe ;
    @BindView(R.id.view_snap)
    View viewSnap ;
    @BindView(R.id.view_none)
    View viewNone ;
    @BindView(R.id.tv_startTime)
    TextView tvStartTime ;
    @BindView(R.id.tv_startValue)
    TextView tvStartValue ;
    @BindView(R.id.tv_endTime)
    TextView tvEndTime ;
    @BindView(R.id.tv_currentTime)
    TextView tvCurrentTime ;
    @BindView(R.id.verticalPro)
    VerticalProgressBar verticalPro ;
    @BindView(R.id.rel_current)
    RelativeLayout relCurrent ;

    private TaskInfoBean.FastPhotoOldBean fastPhotoOldBean ;
    private PopupWindow timeTypePop ;
    private KpiPermissionDataBean kpiPermissionDataBean ;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_snap;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void setupView(View rootView) {
        fastPhotoOldBean = (TaskInfoBean.FastPhotoOldBean) getArguments()
                .getSerializable(Constants.EXTRA_DATA_FOR_BEAN);
        kpiPermissionDataBean = (KpiPermissionDataBean) getArguments()
                .getSerializable(Constants.EXTRA_DATA_FOR_SCAN);
        if(fastPhotoOldBean == null){
            return;
        }
        if (TextUtils.isEmpty(fastPhotoOldBean.getTree_type()) || fastPhotoOldBean.getTree_type().equals("4")){
            statusSnapTop.showContent();
            statusSnap.showExtendContent();
            statusSnapTop.findViewById(R.id.rel_dimension).setOnClickListener(view -> {
                if(timeTypePop == null){
                    showDimensionDialog(statusSnapTop,Arrays.asList(fastPhotoOldBean.getAlldeimension().split(",")));
                }else{
                    darkenBackground(0.6f);
                    timeTypePop.showAsDropDown(statusSnapTop);
                }
            });
            initSnapTopView(statusSnapTop,Arrays.asList(fastPhotoOldBean.getAlldeimension().split(",")));
            initSnapIndex(statusSnap,fastPhotoOldBean.getIndex_clname(),fastPhotoOldBean.getIndex_unit());
        }else{
            statusSnapTop.showExtendContent();
            statusSnap.findViewById(R.id.rel_dimension).setOnClickListener(view -> {
                if(timeTypePop == null){
                    showDimensionDialog(statusSnap,Arrays.asList(fastPhotoOldBean.getAlldeimension().split(",")));
                }else{
                    darkenBackground(0.6f);
                    timeTypePop.showAsDropDown(statusSnap);
                }
            });
            statusSnap.showContent();
            initSnapTopView(statusSnap,Arrays.asList(fastPhotoOldBean.getAlldeimension().split(",")));
            initSnapIndex(statusSnapTop,fastPhotoOldBean.getIndex_clname(),fastPhotoOldBean.getIndex_unit());
        }

        if(!TextUtils.isEmpty(fastPhotoOldBean.getFast_code()) && fastPhotoOldBean.getFast_code().equals("400")){
            viewNone.setVisibility(View.VISIBLE);
            viewSnap.setVisibility(View.GONE);
            if(!TextUtils.isEmpty(fastPhotoOldBean.getNo_permission_name())){
                ((TextView)viewNone.findViewById(R.id.tv_permission)).setText(resources.getString(R.string.the_current_user_does_not_have_permission_for_dimension).replace("#1",fastPhotoOldBean.getNo_permission_name()));
            }
        }else{
            viewNone.setVisibility(View.GONE);
            viewSnap.setVisibility(View.VISIBLE);
        }
        List<TaskInfoBean.FastPhotoOldBean.SaveDataBean> saveDataBeans = new Gson().fromJson(fastPhotoOldBean.getSave_data(), SaveDataListBean.class);
        if(!fastPhotoOldBean.getDemention_info().isEmpty()){
            for (TaskInfoBean.FastPhotoOldBean.DementionInfoBean bean : fastPhotoOldBean.getDemention_info()){
                switch (bean.getType()){
                    /**
                     * 保底
                     */
                    case "1" :
                        tvMinimum.setVisibility(View.VISIBLE);
                        if(kpiPermissionDataBean != null
                                && kpiPermissionDataBean.getKpiPermission() != null
                                && kpiPermissionDataBean.getKpiPermission().getThresholdName() != null){
                            tvMinimum.setText(kpiPermissionDataBean.getKpiPermission()
                                    .getThresholdName().getMinimum());
                        }else{
                            tvMinimum.setText(resources.getString(R.string.guaranteed));
                        }
                        tvMinimumValue.setText(bean.getThreshold_value() + " " + bean.getIndex_value_unit());
                        break;

                    /**
                     * 目标
                     */
                    case "2" :
                        tvTarget.setVisibility(View.VISIBLE);
                        if(kpiPermissionDataBean != null
                                && kpiPermissionDataBean.getKpiPermission() != null
                                && kpiPermissionDataBean.getKpiPermission().getThresholdName() != null){
                            tvTarget.setText(kpiPermissionDataBean.getKpiPermission().getThresholdName().getTarget());
                        }else{
                            tvTarget.setText(resources.getString(R.string.target));
                        }
                        tvTargetValue.setText(bean.getThreshold_value() + " " + bean.getIndex_value_unit());
                        if(fastPhotoOldBean.getAction_type().equals("sand")){
                            if(!saveDataBeans.isEmpty() && saveDataBeans.size() > 0){
                                tvTargetValue.setText(saveDataBeans.get(0).getGoal_value() + saveDataBeans.get(0).getValue_unit());
                            }else {
                                tvTargetValue.setText(saveDataBeans.get(0).getGoal_value() + saveDataBeans.get(0).getValue_unit());
                            }
                        }
                        break;

                    /**
                     * 挑战
                     */
                    case "3" :
                        tvChallenge.setVisibility(View.VISIBLE);
                        if(kpiPermissionDataBean != null
                                && kpiPermissionDataBean.getKpiPermission() != null
                                && kpiPermissionDataBean.getKpiPermission().getThresholdName() != null){
                            tvChallenge.setText(kpiPermissionDataBean.getKpiPermission().getThresholdName().getChallenge());
                        }else{
                            tvChallenge.setText(resources.getString(R.string.challenge));
                        }
                        tvChallengeValue.setText(bean.getThreshold_value() + " " + bean.getIndex_value_unit());
                        break;
                }
                switch (bean.getIndex_threshold_type()){
                    case "1" :
                        double value = 0 ;

                        break;

                    case "2" :

                        break;

                    default:

                        break;
                }
            }
        }
        tvStartTime.setText(TextUtils.isEmpty(fastPhotoOldBean.getAction_time()) ? "" : fastPhotoOldBean.getAction_time());
        if (!saveDataBeans.isEmpty() && !TextUtils.isEmpty(saveDataBeans.get(0).getIndex_data())){
            tvStartValue.setText(saveDataBeans.get(0).getIndex_data());
        }
        tvEndTime.setText(TextUtils.isEmpty(fastPhotoOldBean.getDeadline()) ? "" : fastPhotoOldBean.getDeadline());
        if(!TextUtils.isEmpty(fastPhotoOldBean.getAction_time()) && !TextUtils.isEmpty(fastPhotoOldBean.getDeadline())){
            if (fastPhotoOldBean != null && fastPhotoOldBean.getNewval() != null){

            }
//            String newValString = new Gson().toJson(fastPhotoOldBean.getNewval());
//
//            if(fastPhotoOldBean.getNewval().isEmpty()){
//                verticalPro.setProgress(0);
//            }else{
//                verticalPro.setProgress((int) ((float)TimeUtils.countDays(fastPhotoOldBean.getAction_time())/(float) (TimeUtils.differentDays(TimeUtils.parse(fastPhotoOldBean.getAction_time())
//                        ,TimeUtils.parse(fastPhotoOldBean.getDeadline()))) * 100));
//            }
        }
    }


    private void showDimensionDialog(StatusRelativeLayout statusSnap,List<String> dimensions) {
        View contentView = LayoutInflater.from(mContext)
                .inflate(R.layout.item_pup_dimension_snap, null);
        timeTypePop = new PopupWindow(contentView,
                statusSnap.getMeasuredWidth(), ViewGroup.LayoutParams.WRAP_CONTENT, true);
        timeTypePop.setBackgroundDrawable(resources.getDrawable(android.R.color.transparent));
        switch (dimensions.size()){
            case 1 :
                ((TextView)contentView.findViewById(R.id.tv_org)).setText(resources.getString(R.string.organization) +  " : " + dimensions.get(0));
                break;

            case 2 :
                ((TextView)contentView.findViewById(R.id.tv_org)).setText(resources.getString(R.string.organization) +  " : " + dimensions.get(0));
                ((TextView)contentView.findViewById(R.id.tv_area)).setText(resources.getString(R.string.region) +  " : " + dimensions.get(1));
                break;

            case 3 :
                ((TextView)contentView.findViewById(R.id.tv_org)).setText(resources.getString(R.string.organization) +  " : " + dimensions.get(0));
                ((TextView)contentView.findViewById(R.id.tv_area)).setText(resources.getString(R.string.region) +  " : " + dimensions.get(1));
                ((TextView)contentView.findViewById(R.id.tv_pro)).setText(resources.getString(R.string.product) +  " : " + dimensions.get(2));
                break;
        }
        timeTypePop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                darkenBackground(1f);
            }
        });
        timeTypePop.setTouchable(true);
        timeTypePop.setOutsideTouchable(false);
        darkenBackground(0.6f);
        timeTypePop.setBackgroundDrawable(resources.getDrawable(R.drawable.shape_bg_f8f8fa));
        timeTypePop.showAsDropDown(statusSnap);
    }

    private void initSnapIndex(StatusRelativeLayout statusSnap, String index_clname, String index_unit) {
        ((TextView) statusSnap.findViewById(R.id.tv_indexName)).setText(index_clname);
        ((TextView) statusSnap.findViewById(R.id.tv_indexUnit)).setText(resources.getString(R.string.unit)
                + " : " + (TextUtils.isEmpty(index_unit) ? "" : index_unit));
        PLog.e(((TextView) statusSnap.findViewById(R.id.tv_indexUnit)).getText().toString());
    }

    private void initSnapTopView(StatusRelativeLayout statusSnap, List<String> dimensions) {
        if(dimensions.isEmpty()){
            return;
        }
        switch (dimensions.size()){
            case 1 :
                statusSnap.findViewById(R.id.tv_orgName).setVisibility(View.VISIBLE);
                ((TextView) statusSnap.findViewById(R.id.tv_orgName)).setText(resources.getString(R.string.organization) + ":" + dimensions.get(0));
                break;

            case 2 :
                statusSnap.findViewById(R.id.tv_orgName).setVisibility(View.VISIBLE);
                ((TextView) statusSnap.findViewById(R.id.tv_orgName)).setText(resources.getString(R.string.organization) + ":" + dimensions.get(0));
                statusSnap.findViewById(R.id.tv_proLine).setVisibility(View.VISIBLE);
                statusSnap.findViewById(R.id.tv_proName).setVisibility(View.VISIBLE);
                ((TextView) statusSnap.findViewById(R.id.tv_proName)).setText(resources.getString(R.string.region) + ":" + dimensions.get(1));
                break;

            case 3 :
                statusSnap.findViewById(R.id.tv_orgName).setVisibility(View.VISIBLE);
                ((TextView) statusSnap.findViewById(R.id.tv_orgName)).setText(resources.getString(R.string.organization) + ":" + dimensions.get(0));
                statusSnap.findViewById(R.id.tv_proLine).setVisibility(View.VISIBLE);
                statusSnap.findViewById(R.id.tv_proName).setVisibility(View.VISIBLE);
                ((TextView) statusSnap.findViewById(R.id.tv_proName)).setText(resources.getString(R.string.region) + ":" + dimensions.get(1));

                statusSnap.findViewById(R.id.tv_areaLine).setVisibility(View.VISIBLE);
                statusSnap.findViewById(R.id.tv_areaName).setVisibility(View.VISIBLE);
                ((TextView) statusSnap.findViewById(R.id.tv_areaName)).setText(resources.getString(R.string.product) + ":" + dimensions.get(2));
                break;
        }
    }

    @Override
    protected void setupData() {

    }

    /**
     * 改变背景颜色
     */
    private void darkenBackground(Float bgColor){
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgColor;
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getActivity().getWindow().setAttributes(lp);
    }

    public static SnapFragment newInstance(TaskInfoBean.FastPhotoOldBean bean,KpiPermissionDataBean kpiPermissionDataBean) {
        Bundle args = new Bundle();
        args.putSerializable(Constants.EXTRA_DATA_FOR_BEAN,bean);
        args.putSerializable(Constants.EXTRA_DATA_FOR_SCAN,kpiPermissionDataBean);
        SnapFragment fragment = new SnapFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
