package com.datacvg.dimp.activity;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.datacvg.dimp.R;
import com.datacvg.dimp.adapter.DimensionNameAreaAdapter;
import com.datacvg.dimp.adapter.DimensionNameOrgAdapter;
import com.datacvg.dimp.adapter.DimensionNameProAdapter;
import com.datacvg.dimp.adapter.IndexForDimensionAdapter;
import com.datacvg.dimp.adapter.SelectAreaDimensionAdapter;
import com.datacvg.dimp.adapter.SelectOrgDimensionAdapter;
import com.datacvg.dimp.adapter.SelectProDimensionAdapter;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.retrofit.helper.PreferencesHelper;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.StatusBarUtil;
import com.datacvg.dimp.baseandroid.utils.ToastUtils;
import com.datacvg.dimp.bean.AddPageRequestBean;
import com.datacvg.dimp.bean.DimensionBean;
import com.datacvg.dimp.bean.DimensionForTimeBean;
import com.datacvg.dimp.bean.DimensionNodeListBean;
import com.datacvg.dimp.bean.IndexChartBean;
import com.datacvg.dimp.bean.IndexChartInfoBean;
import com.datacvg.dimp.event.AddPageEvent;
import com.datacvg.dimp.presenter.AddIndexPagePresenter;
import com.datacvg.dimp.view.AddIndexPageView;
import com.datacvg.dimp.widget.IndexForDimensionTitleNavigator;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.lucode.hackware.magicindicator.FragmentContainerHelper;
import net.lucode.hackware.magicindicator.MagicIndicator;
import org.greenrobot.eventbus.EventBus;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-06-01
 * @Description :看板设置页面
 */
public class AddIndexPageActivity extends BaseActivity<AddIndexPageView, AddIndexPagePresenter>
        implements AddIndexPageView, DimensionNameOrgAdapter.DimensionNameOrgClickListener
        , DimensionNameProAdapter.DimensionNameProClickListener
        , DimensionNameAreaAdapter.DimensionNameAreaClickListener
        , SelectOrgDimensionAdapter.SelectOrgClickListener
        , SelectProDimensionAdapter.SelectProClickListener
        , SelectAreaDimensionAdapter.SelectAreaDimensionClickListener
        , IndexForDimensionAdapter.OnIndexCheckedClick, IndexForDimensionTitleNavigator.OnTabSelectedListener {
    @BindView(R.id.tv_title)
    TextView tvTitle ;
    @BindView(R.id.img_right)
    ImageView imgRight ;
    @BindView(R.id.rel_dimensionTwo)
    RelativeLayout relDimensionTwo ;
    @BindView(R.id.rel_dimensionThree)
    RelativeLayout relDimensionThree ;
    @BindView(R.id.img_addDimensionOne)
    ImageView imgAddDimensionOne ;
    @BindView(R.id.tv_addOrCancel)
    TextView tvAddOrCancel ;
    @BindView(R.id.tv_timeType)
    TextView tvTimeType ;
    @BindView(R.id.lin_dimensionPopOne)
    LinearLayout linDimensionPopOne ;
    @BindView(R.id.recycle_dimensionOne)
    RecyclerView recycleDimensionOne ;
    @BindView(R.id.recycle_dimensionDetailOne)
    RecyclerView recycleDimensionDetailOne ;
    @BindView(R.id.lin_dimensionPopTwo)
    LinearLayout linDimensionPopTwo ;
    @BindView(R.id.recycle_dimensionTwo)
    RecyclerView recycleDimensionTwo ;
    @BindView(R.id.recycle_dimensionDetailTwo)
    RecyclerView recycleDimensionDetailTwo ;
    @BindView(R.id.lin_dimensionPopThree)
    LinearLayout linDimensionPopThree ;
    @BindView(R.id.recycle_dimensionThree)
    RecyclerView recycleDimensionThree ;
    @BindView(R.id.recycle_dimensionDetailThree)
    RecyclerView recycleDimensionDetailThree ;
    @BindView(R.id.tv_dimensionNameOne)
    TextView tvDimensionNameOne;
    @BindView(R.id.tv_dimensionNodeNameOne)
    TextView tvDimensionNodeNameOne ;
    @BindView(R.id.tv_dimensionNameTwo)
    TextView tvDimensionNameTwo;
    @BindView(R.id.tv_dimensionNodeNameTwo)
    TextView tvDimensionNodeNameTwo ;
    @BindView(R.id.tv_dimensionNameThree)
    TextView tvDimensionNameThree;
    @BindView(R.id.tv_dimensionNodeNameThree)
    TextView tvDimensionNodeNameThree ;
    @BindView(R.id.magic_indicator)
    MagicIndicator magicIndicator ;
    @BindView(R.id.recycle_index)
    RecyclerView recycleIndex ;
    @BindView(R.id.cb_selectAll)
    CheckBox cbSelectAll ;

    private PopupWindow timeTypePop ;
    private String timeType = "month" ;
    private AddPageRequestBean addPageRequestBean = new AddPageRequestBean();
    private List<DimensionForTimeBean.DimensionRelationBean.DimensionNameBean> dimensionNameBeans
            = new ArrayList<>() ;
    private List<DimensionBean> dimensionNodeBeans = new ArrayList<>() ;
    private ArrayList<IndexChartInfoBean.IndexChartDetailBean> indexChart = new ArrayList<>();
    private List<IndexChartBean> detailBeans = new ArrayList<>() ;
    private JsonObject dimensionJson ;
    private DimensionNameOrgAdapter dimensionNameOrgAdapter ;
    private DimensionNameProAdapter dimensionNameProAdapter ;
    private DimensionNameAreaAdapter dimensionNameAreaAdapter ;
    private SelectOrgDimensionAdapter selectOrgDimensionAdapter ;
    private SelectProDimensionAdapter selectProDimensionAdapter ;
    private SelectAreaDimensionAdapter selectAreaDimensionAdapter ;
    private IndexForDimensionTitleNavigator titleNavigator ;
    private FragmentContainerHelper mFragmentContainerHelper ;
    private IndexForDimensionAdapter indexForDimensionAdapter ;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_index_page;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void setupView() {
        StatusBarUtil.setStatusBarColor(mContext
                ,mContext.getResources().getColor(R.color.c_FFFFFF));
        tvTitle.setText(resources.getString(R.string.setup_board));
//        imgRight.setImageBitmap(BitmapFactory.decodeResource(resources,R.mipmap.icon_search));
        initAdapter();
        tvTimeType.setText(resources.getString(R.string.month));
        initTitleNavigator();
    }

    @Override
    protected void setupData(Bundle savedInstanceState) {
        String pageNo = Integer.valueOf(getIntent()
                .getStringExtra(Constants.EXTRA_DATA_FOR_BEAN)) + 1 +"";
        addPageRequestBean.setTimeType(timeType);
        addPageRequestBean.setPadNo(pageNo);
        getPresenter().getIndexPageDimension(PreferencesHelper.get(Constants.USER_DEFAULT_MONTH
                ,"").replaceAll("/",""));
    }

    @OnClick({R.id.img_left,R.id.img_right,R.id.img_addDimensionOne,R.id.img_deleteDimension
            ,R.id.img_deleteDimensionTwo,R.id.tv_addOrCancel,R.id.tv_timeType
            ,R.id.tv_dimensionNameOne,R.id.tv_dimensionNameTwo,R.id.tv_dimensionNameThree
            ,R.id.tv_dimensionNodeNameOne,R.id.tv_dimensionNodeNameTwo,R.id.tv_dimensionNodeNameThree
            ,R.id.cb_selectAll,R.id.btn_login})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.img_left :
                finish();
                break;
            case R.id.img_right :
//                Intent intent = new Intent(mContext,SearchIndexActivity.class);
//                intent.putExtra(com.datacvg.sempmobile.Definition.DEFINITION_CONSTANT_ARRAY,indexChartInfoBean);
//                mContext.startActivity(intent);
                break;
            case R.id.img_addDimensionOne :
                if(relDimensionTwo.getVisibility() != View.VISIBLE){
                    relDimensionTwo.setVisibility(View.VISIBLE);
                    break;
                }
                if(relDimensionThree.getVisibility() != View.VISIBLE){
                    relDimensionThree.setVisibility(View.VISIBLE);
                    imgAddDimensionOne
                            .setImageBitmap(BitmapFactory.decodeResource(resources,R.mipmap.add_unuserless));
                }
                break;
            case R.id.img_deleteDimension :
                relDimensionTwo.setVisibility(View.GONE);
                dimensionNameProAdapter.setSelectedBean(null);
                imgAddDimensionOne
                        .setImageBitmap(BitmapFactory.decodeResource(resources,R.mipmap.add));
                DimensionForTimeBean.DimensionRelationBean.DimensionNameBean bean
                        = (DimensionForTimeBean.DimensionRelationBean.DimensionNameBean) tvDimensionNameTwo.getTag();
                if(bean !=null){
                    for (DimensionForTimeBean.DimensionRelationBean.DimensionNameBean nodeBean : dimensionNameBeans){
                        if(nodeBean.getD_res_id().equals(bean.getD_res_id())){
                            nodeBean.setSelected(false);
                        }
                    }
                }
                dimensionNameOrgAdapter.setDimensionNameBeans(dimensionNameBeans);
                dimensionNameProAdapter.setDimensionNameBeans(dimensionNameBeans);
                dimensionNameAreaAdapter.setDimensionNameBeans(dimensionNameBeans);
                tvDimensionNodeNameTwo.setText("");
                tvDimensionNodeNameTwo.setTag(null);
                tvDimensionNameTwo.setText("");
                tvDimensionNameTwo.setTag(null);
                break;
            case R.id.img_deleteDimensionTwo :
                relDimensionThree.setVisibility(View.GONE);
                dimensionNameAreaAdapter.setSelectedBean(null);
                imgAddDimensionOne
                        .setImageBitmap(BitmapFactory.decodeResource(resources,R.mipmap.add));
                DimensionForTimeBean.DimensionRelationBean.DimensionNameBean dimensionNameBean
                        = (DimensionForTimeBean.DimensionRelationBean.DimensionNameBean) tvDimensionNameThree.getTag();
                if(dimensionNameBean != null){
                    for (DimensionForTimeBean.DimensionRelationBean.DimensionNameBean nodeBean : dimensionNameBeans){
                        if(nodeBean.getD_res_id().equals(dimensionNameBean.getD_res_id())){
                            nodeBean.setSelected(false);
                        }
                }
                }
                dimensionNameOrgAdapter.setDimensionNameBeans(dimensionNameBeans);
                dimensionNameProAdapter.setDimensionNameBeans(dimensionNameBeans);
                dimensionNameAreaAdapter.setDimensionNameBeans(dimensionNameBeans);

                tvDimensionNodeNameThree.setText("");
                tvDimensionNodeNameThree.setTag(null);
                tvDimensionNameThree.setText("");
                tvDimensionNameThree.setTag(null);
                break;
            case R.id.tv_addOrCancel :
                tvAddOrCancel.setSelected(!tvAddOrCancel.isSelected());
                tvAddOrCancel.setText(tvAddOrCancel.isSelected()
                        ? resources.getString(R.string.cancel_adding_all_metrics)
                        : resources.getString(R.string.add_all_metrics));
                break;
            case R.id.tv_timeType :
                if(timeTypePop == null){
                    createTimeTypePopWindow();
                }else{
                    if(timeTypePop.isShowing()){
                        timeTypePop.dismiss();
                    }else {
                        timeTypePop.showAsDropDown(tvTimeType);
                    }
                }
                break;
            case R.id.tv_dimensionNameOne :
                linDimensionPopOne.setVisibility(linDimensionPopOne.getVisibility() == View.VISIBLE
                        ? View.GONE : View.VISIBLE);
                linDimensionPopTwo.setVisibility(View.GONE);
                linDimensionPopThree.setVisibility(View.GONE);
                DimensionForTimeBean.DimensionRelationBean.DimensionNameBean orgSelectedBean
                        = (DimensionForTimeBean.DimensionRelationBean.DimensionNameBean) tvDimensionNameOne.getTag();
                if(orgSelectedBean != null){
                    for (DimensionForTimeBean.DimensionRelationBean.DimensionNameBean nodeBean : dimensionNameBeans){
                        if(orgSelectedBean.getD_res_id().equals(nodeBean.getD_res_id())){
                            nodeBean.setSelected(false);
                        }
                    }
                }
                if(linDimensionPopOne.getVisibility() == View.VISIBLE){
                    recycleDimensionDetailOne.setVisibility(View.INVISIBLE);
                    dimensionNameOrgAdapter.notifyDataSetChanged();
                    recycleDimensionOne.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.tv_dimensionNameTwo :
                linDimensionPopTwo.setVisibility(linDimensionPopTwo.getVisibility() == View.VISIBLE
                        ? View.GONE : View.VISIBLE);
                linDimensionPopOne.setVisibility(View.GONE);
                linDimensionPopThree.setVisibility(View.GONE);
                DimensionForTimeBean.DimensionRelationBean.DimensionNameBean proSelectedBean
                        = (DimensionForTimeBean.DimensionRelationBean.DimensionNameBean) tvDimensionNameTwo.getTag();
                if(proSelectedBean != null){
                    for (DimensionForTimeBean.DimensionRelationBean.DimensionNameBean nodeBean : dimensionNameBeans){
                        if(proSelectedBean.getD_res_id().equals(nodeBean.getD_res_id())){
                            nodeBean.setSelected(false);
                        }
                    }
                }
                if(linDimensionPopTwo.getVisibility() == View.VISIBLE){
                    recycleDimensionTwo.setVisibility(View.VISIBLE);
                    dimensionNameProAdapter.notifyDataSetChanged();
                    recycleDimensionDetailTwo.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.tv_dimensionNameThree :
                linDimensionPopThree.setVisibility(linDimensionPopThree.getVisibility() == View.VISIBLE
                        ? View.GONE : View.VISIBLE);
                linDimensionPopOne.setVisibility(View.GONE);
                linDimensionPopTwo.setVisibility(View.GONE);
                DimensionForTimeBean.DimensionRelationBean.DimensionNameBean selectedBean
                        = (DimensionForTimeBean.DimensionRelationBean.DimensionNameBean) tvDimensionNameThree.getTag();
                if(selectedBean != null){
                    for (DimensionForTimeBean.DimensionRelationBean.DimensionNameBean nodeBean : dimensionNameBeans){
                        if(selectedBean.getD_res_id().equals(nodeBean.getD_res_id())){
                            nodeBean.setSelected(false);
                        }
                    }
                }
                if(linDimensionPopThree.getVisibility() == View.VISIBLE){
                    dimensionNameAreaAdapter.notifyDataSetChanged();
                    recycleDimensionThree.setVisibility(View.VISIBLE);
                    recycleDimensionDetailThree.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.tv_dimensionNodeNameOne :
                linDimensionPopOne.setVisibility(linDimensionPopOne.getVisibility() == View.VISIBLE
                        ? View.GONE : View.VISIBLE);
                linDimensionPopTwo.setVisibility(View.GONE);
                linDimensionPopThree.setVisibility(View.GONE);
                if(linDimensionPopOne.getVisibility() == View.VISIBLE){
                    recycleDimensionDetailOne.setVisibility(View.VISIBLE);
                    recycleDimensionOne.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.tv_dimensionNodeNameTwo :
                linDimensionPopTwo.setVisibility(linDimensionPopTwo.getVisibility() == View.VISIBLE
                        ? View.GONE : View.VISIBLE);
                linDimensionPopOne.setVisibility(View.GONE);
                linDimensionPopThree.setVisibility(View.GONE);
                if(linDimensionPopTwo.getVisibility() == View.VISIBLE){
                    recycleDimensionTwo.setVisibility(View.INVISIBLE);
                    recycleDimensionDetailTwo.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.tv_dimensionNodeNameThree :
                linDimensionPopThree.setVisibility(linDimensionPopThree.getVisibility() == View.VISIBLE
                        ? View.GONE : View.VISIBLE);
                linDimensionPopOne.setVisibility(View.GONE);
                linDimensionPopTwo.setVisibility(View.GONE);
                if(linDimensionPopThree.getVisibility() == View.VISIBLE){
                    recycleDimensionThree.setVisibility(View.INVISIBLE);
                    recycleDimensionDetailThree.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.cb_selectAll :
                checkPresentIndexForSelected(cbSelectAll.isChecked());
                break;
            case R.id.btn_login :
                if(TextUtils.isEmpty(addPageRequestBean.getPadName())){
                    ToastUtils.showLongToast(resources.getString(R.string.please_enter_a_page_name));
                    return;
                }
                if(!((tvDimensionNameOne.getTag() != null && tvDimensionNodeNameOne != null)
                        || ((tvDimensionNameTwo.getTag() != null && tvDimensionNodeNameTwo != null))
                        || ((tvDimensionNameThree.getTag() != null && tvDimensionNodeNameThree != null)))){
                    ToastUtils.showLongToast(resources.getString(R.string.please_select_dimension));
                    return;
                }
                sortDimensionId();
                setRequestIndexIds();
                if(addPageRequestBean.getIndexes() == null
                        || addPageRequestBean.getIndexes().size() == 0){
                    ToastUtils.showLongToast(resources.getString(R.string.please_select_at_least_one_metric));
                    return;
                }
                PLog.e(addPageRequestBean.getClass().getSimpleName());
                addPageRequestToService();
                break;
        }
    }

    private void addPageRequestToService() {
        HashMap params = new HashMap();
        params.put("dimensions",addPageRequestBean.getDimensions().toArray());
        params.put("indexes", addPageRequestBean.getIndexes().toArray());
        params.put("padName",addPageRequestBean.getPadName());
        params.put("padNo",addPageRequestBean.getPadNo());
        params.put("timeType",addPageRequestBean.getTimeType());
        getPresenter().addPageRequest(params);
    }

    /**
     * 选择的指标添加到请求中
     */
    private void setRequestIndexIds() {
        List<String> requestIndexId = new ArrayList<>() ;
        for (IndexChartInfoBean.IndexChartDetailBean indexChartBean : indexChart){
            if(indexChartBean.getDetail() != null && indexChartBean.getDetail().size() > 0){
                for (IndexChartBean detailBean : indexChartBean.getDetail()){
                    if(detailBean.getSelected()){
                        requestIndexId.add(detailBean.getIndex_id());
                    }
                }
            }
        }
        addPageRequestBean.setIndexes(requestIndexId);
    }

    /**
     * 根据维度排序选择,排列选择维度的id
     */
    private void sortDimensionId() {
        List<String> requestIndexIds = new ArrayList<>() ;
        Collections.sort(dimensionNameBeans);
        for (DimensionForTimeBean.DimensionRelationBean.DimensionNameBean dimensionNameBean : dimensionNameBeans){
            if(tvDimensionNameOne.getTag() != null){
                if(tvDimensionNameOne.getTag() != null
                        && ((DimensionForTimeBean.DimensionRelationBean.DimensionNameBean)
                        tvDimensionNameOne.getTag()).getD_res_id().equals(dimensionNameBean.getD_res_id())){
                    requestIndexIds.add(((DimensionBean)tvDimensionNodeNameOne.getTag()).getId());
                }
            }
            if(tvDimensionNameTwo.getTag() != null){
                if(tvDimensionNameTwo.getTag() != null
                        && ((DimensionForTimeBean.DimensionRelationBean.DimensionNameBean)
                        tvDimensionNameTwo.getTag()).getD_res_id().equals(dimensionNameBean.getD_res_id())){
                    requestIndexIds.add(((DimensionBean)tvDimensionNodeNameTwo.getTag()).getId());
                }
            }
            if(tvDimensionNameThree.getTag() != null){
                if(tvDimensionNameThree.getTag() != null
                        && ((DimensionForTimeBean.DimensionRelationBean.DimensionNameBean)
                        tvDimensionNameThree.getTag()).getD_res_id().equals(dimensionNameBean.getD_res_id())){
                    requestIndexIds.add(((DimensionBean)tvDimensionNodeNameThree.getTag()).getId());
                }
            }
        }
        addPageRequestBean.setDimensions(requestIndexIds);
    }

    /**
     * 创建时间类型弹出popwindow
     */
    private void createTimeTypePopWindow() {
        View contentView = LayoutInflater.from(mContext)
                .inflate(R.layout.item_pup_time_type, null);
        timeTypePop = new PopupWindow(contentView,
                tvTimeType.getMeasuredWidth(), ViewGroup.LayoutParams.WRAP_CONTENT, true);
        contentView.findViewById(R.id.tv_year).setOnClickListener(v -> {
            timeTypePop.dismiss();
            addPageRequestBean.setTimeType("year");
            tvTimeType.setText(resources.getString(R.string.year));
            linDimensionPopOne.setVisibility(View.GONE);
            linDimensionPopTwo.setVisibility(View.GONE);
            linDimensionPopThree.setVisibility(View.GONE);
            getPresenter().getIndexPageDimension(PreferencesHelper.get(Constants.USER_DEFAULT_YEAR
                    ,"").replaceAll("/",""));
        });
        contentView.findViewById(R.id.tv_month).setOnClickListener(v -> {
            timeTypePop.dismiss();
            tvTimeType.setText(resources.getString(R.string.month));
            addPageRequestBean.setTimeType("year");
            linDimensionPopOne.setVisibility(View.GONE);
            linDimensionPopTwo.setVisibility(View.GONE);
            linDimensionPopThree.setVisibility(View.GONE);
            getPresenter().getIndexPageDimension(PreferencesHelper.get(Constants.USER_DEFAULT_MONTH
                    ,"").replaceAll("/",""));
        });
        contentView.findViewById(R.id.tv_day).setOnClickListener(v -> {
            timeTypePop.dismiss();
            tvTimeType.setText(resources.getString(R.string.day));
            addPageRequestBean.setTimeType("date");
            linDimensionPopOne.setVisibility(View.GONE);
            linDimensionPopTwo.setVisibility(View.GONE);
            linDimensionPopThree.setVisibility(View.GONE);
            getPresenter().getIndexPageDimension(PreferencesHelper.get(Constants.USER_DEFAULT_DAY
                    ,"").replaceAll("/",""));
        });
        timeTypePop.setTouchable(true);
        timeTypePop.setOutsideTouchable(false);
        timeTypePop.setBackgroundDrawable(resources.getDrawable(R.drawable.shape_bg_f8f8fa));
        timeTypePop.showAsDropDown(tvTimeType);
    }

    /**
     * 将当前指标设置为选择
     * @param checked
     */
    private void checkPresentIndexForSelected(boolean checked) {
        for (IndexChartBean detailBean : detailBeans){
            detailBean.setSelected(checked);
        }
        indexForDimensionAdapter.notifyDataSetChanged();
    }

    /**
     * 初始化指标标题
     */
    private void initTitleNavigator() {
        titleNavigator = new IndexForDimensionTitleNavigator(mContext,indexChart);
        titleNavigator.setOnTabSelectedListener(this);
        magicIndicator.setNavigator(titleNavigator);
        mFragmentContainerHelper = new FragmentContainerHelper() ;
        mFragmentContainerHelper.attachMagicIndicator(magicIndicator);
    }


    private void initAdapter() {
        dimensionNameOrgAdapter = new DimensionNameOrgAdapter(mContext,dimensionNameBeans,this);
        LinearLayoutManager linearLayoutManagerOrg = new LinearLayoutManager(mContext);
        recycleDimensionOne.setLayoutManager(linearLayoutManagerOrg);
        recycleDimensionOne.setAdapter(dimensionNameOrgAdapter);

        dimensionNameProAdapter = new DimensionNameProAdapter(mContext,dimensionNameBeans,this);
        LinearLayoutManager linearLayoutManagerPro = new LinearLayoutManager(mContext);
        recycleDimensionTwo.setLayoutManager(linearLayoutManagerPro);
        recycleDimensionTwo.setAdapter(dimensionNameProAdapter);

        dimensionNameAreaAdapter = new DimensionNameAreaAdapter(mContext,dimensionNameBeans,this);
        LinearLayoutManager linearLayoutManagerArea = new LinearLayoutManager(mContext);
        recycleDimensionThree.setLayoutManager(linearLayoutManagerArea);
        recycleDimensionThree.setAdapter(dimensionNameAreaAdapter);

        selectOrgDimensionAdapter = new SelectOrgDimensionAdapter(mContext,dimensionNodeBeans,this);
        LinearLayoutManager linearLayoutManagerSelectOrg = new LinearLayoutManager(mContext);
        recycleDimensionDetailOne.setLayoutManager(linearLayoutManagerSelectOrg);
        recycleDimensionDetailOne.setAdapter(selectOrgDimensionAdapter);

        selectProDimensionAdapter = new SelectProDimensionAdapter(mContext,dimensionNodeBeans,this);
        LinearLayoutManager linearLayoutManagerSelectPro = new LinearLayoutManager(mContext);
        recycleDimensionDetailTwo.setLayoutManager(linearLayoutManagerSelectPro);
        recycleDimensionDetailTwo.setAdapter(selectProDimensionAdapter);

        selectAreaDimensionAdapter = new SelectAreaDimensionAdapter(mContext,dimensionNodeBeans,this);
        LinearLayoutManager linearLayoutManagerSelectArea = new LinearLayoutManager(mContext);
        recycleDimensionDetailThree.setLayoutManager(linearLayoutManagerSelectArea);
        recycleDimensionDetailThree.setAdapter(selectAreaDimensionAdapter);

        indexForDimensionAdapter = new IndexForDimensionAdapter(mContext,detailBeans,this);
        LinearLayoutManager linearLayoutManagerForIndex = new LinearLayoutManager(mContext);
        recycleIndex.setLayoutManager(linearLayoutManagerForIndex);
        recycleIndex.setAdapter(indexForDimensionAdapter);
    }

    @OnTextChanged(value = R.id.edit_pageName,callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onMerchantNameTextChange(Editable editable){
        addPageRequestBean.setPadName(editable.toString().trim()) ;
    }

    @Override
    public void onDimensionOrgClick(DimensionForTimeBean.DimensionRelationBean.DimensionNameBean bean) {
        tvDimensionNameOne.setText(bean.getD_res_name());
        tvDimensionNameOne.setTag(bean);
        bean.setSelected(true);
        dimensionNameProAdapter.setDimensionNameBeans(dimensionNameBeans);
        dimensionNameAreaAdapter.setDimensionNameBeans(dimensionNameBeans);
        linDimensionPopOne.setVisibility(View.GONE);
        getDimensionList(bean.getD_res_id(),tvDimensionNodeNameOne);
        recycleDimensionDetailOne.setVisibility(View.VISIBLE);
        selectOrgDimensionAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDimensionProClick(DimensionForTimeBean.DimensionRelationBean.DimensionNameBean bean) {
        tvDimensionNameTwo.setText(bean.getD_res_name());
        tvDimensionNameTwo.setTag(bean);
        bean.setSelected(true);
        dimensionNameOrgAdapter.setDimensionNameBeans(dimensionNameBeans);
        dimensionNameAreaAdapter.setDimensionNameBeans(dimensionNameBeans);
        linDimensionPopTwo.setVisibility(View.GONE);
        getDimensionList(bean.getD_res_id(),tvDimensionNodeNameTwo);
        recycleDimensionDetailTwo.setVisibility(View.VISIBLE);
        selectProDimensionAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDimensionAreaClick(DimensionForTimeBean.DimensionRelationBean.DimensionNameBean bean) {
        tvDimensionNameThree.setText(bean.getD_res_name());
        tvDimensionNameThree.setTag(bean);
        linDimensionPopThree.setVisibility(View.GONE);
        bean.setSelected(true);
        dimensionNameOrgAdapter.setDimensionNameBeans(dimensionNameBeans);
        dimensionNameProAdapter.setDimensionNameBeans(dimensionNameBeans);
        getDimensionList(bean.getD_res_id(),tvDimensionNodeNameThree);
        recycleDimensionDetailThree.setVisibility(View.VISIBLE);
        selectAreaDimensionAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSelectOrgClick(DimensionBean bean) {
        tvDimensionNodeNameOne.setText(bean.getD_res_name());
        tvDimensionNodeNameOne.setTag(bean);
        linDimensionPopOne.setVisibility(View.GONE);
        getIndexForDimension();
    }

    @Override
    public void onSelectProClick(DimensionBean bean) {
        tvDimensionNodeNameTwo.setText(bean.getD_res_name());
        tvDimensionNodeNameTwo.setTag(bean);
        linDimensionPopTwo.setVisibility(View.GONE);
        getIndexForDimension();
    }

    @Override
    public void onSelectAreaClick(DimensionBean bean) {
        tvDimensionNodeNameThree.setText(bean.getD_res_name());
        tvDimensionNodeNameThree.setTag(bean);
        linDimensionPopThree.setVisibility(View.GONE);
        getIndexForDimension();
    }

    @Override
    public void onIndexClick(boolean isSelected) {
        if(!isSelected){
            tvAddOrCancel.setSelected(false);
            cbSelectAll.setChecked(false);
        }else{
            tvAddOrCancel.setSelected(checkAllIndex());
            cbSelectAll.setChecked(checkPresenterIndex());
        }
    }

    @Override
    public void onTabSelected(int position) {
        mFragmentContainerHelper.handlePageSelected(position);
        detailBeans.clear();
        detailBeans.addAll(indexChart.get(position).getDetail());
        cbSelectAll.setChecked(checkPresenterIndex());
        indexForDimensionAdapter.notifyDataSetChanged();
    }

    /**
     * 根据维度id获取子维度信息
     * @param keyId
     */
    private void getDimensionList(String keyId,TextView tagView) {
        PLog.e(dimensionJson.get(keyId).toString());
        DimensionNodeListBean bean = new Gson().fromJson(dimensionJson.get(keyId)
                ,DimensionNodeListBean.class);
        dimensionNodeBeans.clear();
        dimensionNodeBeans.addAll(bean);
        if(bean != null && bean.size() > 0){
            tagView.setTag(bean.get(0));
            tagView.setText(bean.get(0).getD_res_name());
            getIndexForDimension();
        }
    }

    /**
     * 检查当前展示页的指标
     * @return
     */
    private boolean checkPresenterIndex() {
        for (IndexChartBean detailBean : detailBeans){
            if (!detailBean.getSelected()){
                return false ;
            }
        }
        return true;
    }

    /**
     * 检查所有指标选中状态
     * @return
     */
    private boolean checkAllIndex() {
        for (IndexChartInfoBean.IndexChartDetailBean indexChartBean : indexChart){
            if(indexChartBean.getDetail() != null && indexChartBean.getDetail().size() > 0){
                for (IndexChartBean detailBean : indexChartBean.getDetail()){
                    if (!detailBean.getSelected()){
                        return false ;
                    }
                }
            }
        }
        return true;
    }

    /**
     * 根据维度获取指标信息
     */
    private void getIndexForDimension() {
        Map params = new HashMap() ;
        List<String> arr = new ArrayList<>() ;
        if(tvDimensionNodeNameOne.getTag() != null){
            arr.add(((DimensionBean)tvDimensionNodeNameOne.getTag()).getId()) ;
        }
        if(tvDimensionNodeNameTwo.getTag() != null){
            arr.add(((DimensionBean)tvDimensionNodeNameTwo.getTag()).getId());
        }
        if(tvDimensionNodeNameThree.getTag() != null){
            arr.add(((DimensionBean)tvDimensionNodeNameThree.getTag()).getId());
        }
        params.put("dimensionArr",arr.toArray());
        getPresenter().getIndexForDimension(params);
    }

    @Override
    public void getIndexSuccess(DimensionForTimeBean data) {
        indexChart.clear();
        titleNavigator.notifyDataSetChanged();
        detailBeans.clear();
        indexForDimensionAdapter.notifyDataSetChanged();
        tvDimensionNameOne.setTag(null);
        tvDimensionNameOne.setText("");
        tvDimensionNodeNameOne.setTag(null);
        tvDimensionNodeNameOne.setText("");
        tvDimensionNodeNameTwo.setTag(null);
        tvDimensionNodeNameTwo.setText("");
        tvDimensionNameTwo.setTag(null);
        tvDimensionNameTwo.setText("");
        tvDimensionNodeNameThree.setTag(null);
        tvDimensionNodeNameThree.setText("");
        tvDimensionNameThree.setTag(null);
        tvDimensionNameThree.setText("");

        dimensionNameBeans.clear();
        dimensionNameBeans.addAll(data
                .getDimensionRelation().getDimensionName());
        dimensionJson = data.getDimensionRelation()
                .getDimensionResult();
        dimensionNameOrgAdapter.setDimensionNameBeans(dimensionNameBeans);
        dimensionNameProAdapter.setDimensionNameBeans(dimensionNameBeans);
        dimensionNameAreaAdapter.setDimensionNameBeans(dimensionNameBeans);
    }

    @Override
    public void addPageSuccess() {
        ToastUtils.showLongToast(mContext.getResources().getString(R.string.add_success));
        EventBus.getDefault().post(new AddPageEvent());
        finish();
    }

    @Override
    public void getIndexForDimensionSuccess(IndexChartInfoBean data) {
        indexChart.clear();
        indexChart.addAll(data.getIndexChart());
        titleNavigator.notifyDataSetChanged();
        if(data.getIndexChart() != null
                && data.getIndexChart().size() > 0){
            detailBeans.clear();
            if(data.getIndexChart().get(0).getDetail() != null){
                detailBeans.addAll(data.getIndexChart().get(0).getDetail());
            }
            indexForDimensionAdapter.notifyDataSetChanged();
        }
    }
}
