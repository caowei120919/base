package com.datacvg.dimp.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.datacvg.dimp.R;
import com.datacvg.dimp.adapter.RemarkAdapter;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.retrofit.helper.PreferencesHelper;
import com.datacvg.dimp.baseandroid.utils.DisplayUtils;
import com.datacvg.dimp.baseandroid.utils.LanguageUtils;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.TimeUtils;
import com.datacvg.dimp.baseandroid.utils.ToastUtils;
import com.datacvg.dimp.bean.IndexTreeBean;
import com.datacvg.dimp.bean.IndexTreeNeedBean;
import com.datacvg.dimp.bean.RemarkBean;
import com.datacvg.dimp.bean.RemarkListBean;
import com.datacvg.dimp.presenter.RemarkPresenter;
import com.datacvg.dimp.view.RemarkView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-07-30
 * @Description :
 */
public class RemarkFragment extends BaseFragment<RemarkView, RemarkPresenter> implements RemarkView {

    @BindView(R.id.tv_time)
    TextView tvTime ;
    @BindView(R.id.tv_orgName)
    TextView tvOrgName ;
    @BindView(R.id.tv_proName)
    TextView tvProName;
    @BindView(R.id.tv_areaName)
    TextView tvAreaName ;
    @BindView(R.id.img_send)
    ImageView imgSend ;
    @BindView(R.id.recycler_remark)
    RecyclerView recyclerRemark ;
    @BindView(R.id.tv_emptyView)
    TextView tvEmptyView ;
    @BindView(R.id.ed_remark)
    EditText edRemark ;

    private IndexTreeBean indexTreeBean ;
    private IndexTreeNeedBean indexTreeNeedBean;
    private RemarkAdapter adapter ;
    private List<RemarkBean> remarkBeans = new ArrayList<>() ;
    private String remark ;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_remark;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void setupView(View rootView) {
        indexTreeBean = (IndexTreeBean) getArguments()
                .getSerializable(Constants.EXTRA_DATA_FOR_BEAN);
        indexTreeNeedBean = (IndexTreeNeedBean) getArguments()
                .getSerializable(Constants.EXTRA_DATA_FOR_SCAN);
        initAdapter();
    }

    /**
     * 初始化适配器
     */
    private void initAdapter() {
        adapter = new RemarkAdapter(mContext,remarkBeans);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        recyclerRemark.setLayoutManager(manager);
        recyclerRemark.setAdapter(adapter);
    }

    @Override
    protected void setupData() {
        getRemark();
    }

    private void getRemark() {
        Map<String,String> params = new HashMap<>();
        params.put("index_id",indexTreeBean.getIndex_id());
        params.put("rootid",indexTreeBean.getI_res_rootid());
        params.put("timeVal",indexTreeNeedBean.getTimeVal());
        switch (indexTreeNeedBean.getType()){
            case "1" :
                params.put("dimention_one",indexTreeBean.getI_res_id());
                params.put("dimention_two",indexTreeNeedBean.getFuDimension());
                params.put("dimention_three",indexTreeNeedBean.getpDimension());
                break;

            case "2" :
                params.put("dimention_one",indexTreeNeedBean.getOrgDimension());
                params.put("dimention_two",indexTreeBean.getI_res_id());
                params.put("dimention_three",indexTreeNeedBean.getpDimension());
                break;

            case "3" :
                params.put("dimention_one",indexTreeNeedBean.getOrgDimension());
                params.put("dimention_two",indexTreeNeedBean.getFuDimension());
                params.put("dimention_three",indexTreeBean.getI_res_id());
                break;

            case "4" :
                params.put("dimention_one",indexTreeNeedBean.getOrgDimension());
                params.put("dimention_two",indexTreeNeedBean.getFuDimension());
                params.put("dimention_three",indexTreeNeedBean.getpDimension());
                break;
        }
        getPresenter().getRemark(params);
    }

    public static RemarkFragment newInstance(IndexTreeBean indexTreeBean, IndexTreeNeedBean indexTreeNeedBean) {
        Bundle args = new Bundle();
        args.putSerializable(Constants.EXTRA_DATA_FOR_BEAN,indexTreeBean);
        args.putSerializable(Constants.EXTRA_DATA_FOR_SCAN,indexTreeNeedBean);
        RemarkFragment fragment = new RemarkFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 备注输入监听
     */
    @OnTextChanged(value = R.id.ed_remark,callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onRemarkTextChange(Editable editable){
        remark = editable.toString().trim();
    }

    @OnClick({R.id.img_send})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.img_send :
                    if(remark.isEmpty()){
                        ToastUtils.showLongToast(resources.getString(R.string.please_enter_remarks));
                        return;
                    }
                    Map<String,String> params = new HashMap<>();
                    params.put("userid", PreferencesHelper.get(Constants.USER_ID,""));
                    params.put("rootIndexId", indexTreeBean.getIndex_id());
                    params.put("rootIndexName", indexTreeBean.getIndex_clname());
                    params.put("time", indexTreeNeedBean.getTimeVal());
                    params.put("splitMessage", "");
                    params.put("orgvalue", TextUtils.isEmpty(indexTreeNeedBean.getOrgValue()) ? "" : indexTreeNeedBean.getOrgValue());
                    params.put("pvalue", TextUtils.isEmpty(indexTreeNeedBean.getpValue()) ? "" : indexTreeNeedBean.getpValue());
                    params.put("fuvalue", TextUtils.isEmpty(indexTreeNeedBean.getFuValue()) ? "" : indexTreeNeedBean.getFuValue());
                    params.put("indexid", indexTreeBean.getIndex_id());
                    params.put("type", indexTreeNeedBean.getType());
                    params.put("lang", LanguageUtils.getLanguage(mContext));
                    params.put("remark", remark);
                    params.put("splitMessage", "");
                    params.put("splitMessage", "");
                    params.put("splitMessage", "");
                    switch (indexTreeNeedBean.getType()){
                        case "1" :
                            params.put("dimention_one",indexTreeBean.getI_res_id());
                            params.put("dimention_two",indexTreeNeedBean.getFuDimension());
                            params.put("dimention_three",indexTreeNeedBean.getpDimension());
                            break;

                        case "2" :
                            params.put("dimention_one",indexTreeNeedBean.getOrgDimension());
                            params.put("dimention_two",indexTreeBean.getI_res_id());
                            params.put("dimention_three",indexTreeNeedBean.getpDimension());
                            break;

                        case "3" :
                            params.put("dimention_one",indexTreeNeedBean.getOrgDimension());
                            params.put("dimention_two",indexTreeNeedBean.getFuDimension());
                            params.put("dimention_three",indexTreeBean.getI_res_id());
                            break;

                        case "4" :
                            params.put("dimention_one",indexTreeNeedBean.getOrgDimension());
                            params.put("dimention_two",indexTreeNeedBean.getFuDimension());
                            params.put("dimention_three",indexTreeNeedBean.getpDimension());
                            break;
                    }
                    getPresenter().sendRemark(params);
                break;
        }
    }

    /**
     * 获取备注信息成功
     * @param resdata
     */
    @Override
    public void getRemarkSuccess(RemarkListBean resdata) {
        remarkBeans.clear();
        remarkBeans.addAll(resdata);
        if(remarkBeans.isEmpty()){
            tvEmptyView.setVisibility(View.VISIBLE);
            tvTime.setVisibility(View.GONE);
            tvOrgName.setVisibility(View.GONE);
            tvProName.setVisibility(View.GONE);
            tvAreaName.setVisibility(View.GONE);
        }else{
            tvEmptyView.setVisibility(View.GONE);
            tvTime.setVisibility(View.VISIBLE);
            switch (indexTreeNeedBean.getType()) {
                case "year" :
                    tvTime.setText(resources.getString(R.string.time) + " : " + indexTreeNeedBean.getTimeVal());
                    break;

                case "month" :
                    StringBuilder builder = new StringBuilder(indexTreeNeedBean.getTimeVal());
                    builder.insert(4,"-");
                    tvTime.setText(resources.getString(R.string.time) + " : " + builder);
                    break;

                default:
                    StringBuilder builderData = new StringBuilder(indexTreeNeedBean.getTimeVal());
                    builderData.insert(6,"-").insert(4,"-");
                    tvTime.setText(resources.getString(R.string.time) + " : " + builderData);
                    break;
            }
            if (!TextUtils.isEmpty(indexTreeNeedBean.getOrgName())){
                tvOrgName.setVisibility(View.VISIBLE);
                tvOrgName.setText(resources.getString(R.string.organization) + " : " + indexTreeNeedBean.getOrgName());
            }
            if (!TextUtils.isEmpty(indexTreeNeedBean.getFuName())){
                tvProName.setVisibility(View.VISIBLE);
                tvProName.setText(resources.getString(R.string.product) + " : " + indexTreeNeedBean.getFuName());
            }
            if (!TextUtils.isEmpty(indexTreeNeedBean.getpName())){
                tvAreaName.setVisibility(View.VISIBLE);
                tvAreaName.setText(resources.getString(R.string.region) + " : " + indexTreeNeedBean.getpName());
            }
        }
    }

    @Override
    public void saveRemarkSuccess() {
        edRemark.setText("");
        DisplayUtils.hideSoftInput(getActivity());
        ToastUtils.showLongToast(resources.getString(R.string.save_success));
        getRemark();
    }
}
