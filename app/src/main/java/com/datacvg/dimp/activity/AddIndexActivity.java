package com.datacvg.dimp.activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.datacvg.dimp.R;
import com.datacvg.dimp.adapter.IndexOfBottomAdapter;
import com.datacvg.dimp.adapter.IndexOfMineAdapter;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.utils.StatusBarUtil;
import com.datacvg.dimp.bean.DimensionPositionBean;
import com.datacvg.dimp.bean.EChartListBean;
import com.datacvg.dimp.bean.IndexChartBean;
import com.datacvg.dimp.bean.IndexDetailListBean;
import com.datacvg.dimp.bean.SearchIndexBean;
import com.datacvg.dimp.event.AddIndexEvent;
import com.datacvg.dimp.event.AddOrRemoveIndexEvent;
import com.datacvg.dimp.event.ChangeIndexEvent;
import com.datacvg.dimp.event.SearchIndexBeanSuccessEvent;
import com.datacvg.dimp.presenter.AddIndexPresenter;
import com.datacvg.dimp.view.AddIndexView;
import com.datacvg.dimp.widget.IndexTitleNavigator;
import net.lucode.hackware.magicindicator.FragmentContainerHelper;
import net.lucode.hackware.magicindicator.MagicIndicator;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-06-02
 * @Description : 添加指标
 */
public class AddIndexActivity extends BaseActivity<AddIndexView, AddIndexPresenter>
        implements AddIndexView, IndexTitleNavigator.OnTabSelectedListener {
    @BindView(R.id.tv_title)
    TextView tvTitle ;
    @BindView(R.id.img_right)
    ImageView imgRight ;
    @BindView(R.id.tv_recommend)
    TextView tvRecommend ;
    @BindView(R.id.tv_all)
    TextView tvAll ;
    @BindView(R.id.recycler_indexOfMine)
    RecyclerView recyclerIndexOfMine ;
    @BindView(R.id.magic_indicatorForIndex)
    MagicIndicator magicIndicatorForIndex ;
    @BindView(R.id.recycle_indexInfo)
    RecyclerView recycleIndexInfo ;

    private EChartListBean eChartListBean ;
    private List<IndexChartBean> showMineIndexBeans = new ArrayList<>();
    private List<IndexChartBean> recommendIndexBeans = new ArrayList<>();
    private List<IndexChartBean> allIndexBeans = new ArrayList<>();
    private IndexOfMineAdapter adapter ;
    private IndexTitleNavigator titleNavigator ;
    private List<IndexDetailListBean> showIndexTitles = new ArrayList<>() ;
    private List<IndexDetailListBean> recommendTitles = new ArrayList<>() ;
    private List<IndexDetailListBean> allTitles = new ArrayList<>() ;
    private FragmentContainerHelper mFragmentContainerHelper ;
    private IndexOfBottomAdapter indexOfBottomAdapter ;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_index;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void setupView() {
        StatusBarUtil.setStatusBarColor(mContext
                ,mContext.getResources().getColor(R.color.c_FFFFFF));
        tvTitle.setText(resources.getString(R.string.add_indicators));
        imgRight.setImageBitmap(BitmapFactory.decodeResource(resources,R.mipmap.icon_search));
        tvRecommend.setSelected(true);
        tvAll.setSelected(false);
        initAdapter();
        initMagicIndicator() ;
    }

    private void initMagicIndicator() {
        titleNavigator = new IndexTitleNavigator(mContext, showIndexTitles);
        titleNavigator.setOnTabSelectedListener(this);
        magicIndicatorForIndex.setNavigator(titleNavigator);
        mFragmentContainerHelper = new FragmentContainerHelper() ;
        mFragmentContainerHelper.attachMagicIndicator(magicIndicatorForIndex);
    }

    private void initAdapter() {
        adapter = new IndexOfMineAdapter(mContext,showMineIndexBeans);
        GridLayoutManager indexLayoutManager = new GridLayoutManager(mContext,3);
        recyclerIndexOfMine.setLayoutManager(indexLayoutManager);
        recyclerIndexOfMine.setAdapter(adapter);

        indexOfBottomAdapter = new IndexOfBottomAdapter(mContext,showIndexTitles);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        recycleIndexInfo.setLayoutManager(manager);
        recycleIndexInfo.setAdapter(indexOfBottomAdapter);
    }

    @Override
    protected void setupData(Bundle savedInstanceState) {
        eChartListBean = (EChartListBean) getIntent()
                .getSerializableExtra(Constants.EXTRA_DATA_FOR_BEAN);
        for (IndexChartBean chartBean : eChartListBean.getIndexChart()){
            chartBean.setSelected(true);
            showMineIndexBeans.add(chartBean);
        }
        adapter.notifyDataSetChanged();
        getPresenter().getRecommendIndexInfo(eChartListBean.getPageNo());
        getPresenter().getAllIndexInfo();
    }

    @OnClick({R.id.img_left,R.id.img_right,R.id.tv_recommend,R.id.tv_all})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.img_left :
                DimensionPositionBean dimensionPositionBean = new DimensionPositionBean() ;
                List<DimensionPositionBean.IndexPositionBean> indexPositionBeans = new ArrayList<>();
                for (IndexChartBean bean: showMineIndexBeans) {
                    DimensionPositionBean.IndexPositionBean indexPositionBean
                            = new DimensionPositionBean.IndexPositionBean();
                    indexPositionBean.setIndex_id(bean.getIndex_id());
                    indexPositionBean.setIndex_pkid(bean.getIndex_pkid());
                    indexPositionBean.setSize_x(bean.getChart_wide());
                    indexPositionBean.setSize_y(bean.getChart_high());
                    indexPositionBean.setChart_type(TextUtils.isEmpty(bean.getPage_chart_type()) ?
                            bean.getChart_type().split(",")[0]:bean.getPage_chart_type());
                    indexPositionBean.setAnalysis_dimension(bean.getAnalysis_dimension());
                    indexPositionBeans.add(indexPositionBean);
                }
                dimensionPositionBean.setIndexPosition(indexPositionBeans);
                EventBus.getDefault().post(new AddIndexEvent(dimensionPositionBean
                        ,eChartListBean.getPageNo()));
                finish();
                break;
            case R.id.img_right :
                SearchIndexBean bean = new SearchIndexBean();
                bean.setIndexChartBeans(allIndexBeans);
                Intent intent = new Intent(mContext,SearchIndexActivity.class);
                intent.putExtra(Constants.EXTRA_DATA_FOR_BEAN,bean);
                mContext.startActivity(intent);
                break;

            case R.id.tv_recommend :
                tvRecommend.setSelected(true);
                tvAll.setSelected(false);
                showIndexTitles.clear();
                showIndexTitles.addAll(recommendTitles);
                titleNavigator.notifyDataSetChanged();
                mFragmentContainerHelper.handlePageSelected(0);
                indexOfBottomAdapter.notifyDataSetChanged();
                break;
            case R.id.tv_all :
                tvRecommend.setSelected(false);
                tvAll.setSelected(true);
                showIndexTitles.clear();
                showIndexTitles.addAll(allTitles);
                titleNavigator.notifyDataSetChanged();
                mFragmentContainerHelper.handlePageSelected(0);
                indexOfBottomAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void onTabSelected(int position) {
        mFragmentContainerHelper.handlePageSelected(position);
        recycleIndexInfo.scrollToPosition(position);
    }

    /**
     * 获取推荐指标成功
     * @param indexChartRelation
     */
    @Override
    public void getRecommendIndexSuccess(List<IndexDetailListBean> indexChartRelation) {
        changeIndexChartBeanState(indexChartRelation);
        recommendIndexBeans.clear();
        recommendTitles.addAll(indexChartRelation);
        for (IndexDetailListBean detailListBean : indexChartRelation){
            recommendIndexBeans.addAll(detailListBean.getDetail());
        }
        showIndexTitles.clear();
        showIndexTitles.addAll(recommendTitles);
        titleNavigator.notifyDataSetChanged();
        indexOfBottomAdapter.notifyDataSetChanged();
    }

    private void changeIndexChartBeanState(List<IndexDetailListBean> indexChartRelation) {
    for (IndexDetailListBean indexDetailListBean : indexChartRelation){
        for (IndexChartBean insideIndexChartBean : indexDetailListBean.getDetail()){
            insideIndexChartBean.setSelected(false);
            for (IndexChartBean indexChartBean : showMineIndexBeans){
                if(insideIndexChartBean.getIndex_pkid().equals(indexChartBean.getIndex_pkid())){
                    insideIndexChartBean.setSelected(true);
                }
            }
        }
    }
    }

    /**
     * 获取所有指标信息成功
     * @param addAbleIndexInfo
     */
    @Override
    public void getAllIndexSuccess(List<IndexDetailListBean> addAbleIndexInfo) {
        changeIndexChartBeanState(addAbleIndexInfo);
        allIndexBeans.clear();
        allTitles.addAll(addAbleIndexInfo);
        for (IndexDetailListBean detailListBean : addAbleIndexInfo){
            allIndexBeans.addAll(detailListBean.getDetail());
        }
    }

    @Override
    public void saveSuccess() {
        EventBus.getDefault().post(new ChangeIndexEvent());
        finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(AddOrRemoveIndexEvent event){
        if(event.getBean().getSelected()){
            for (Iterator<IndexChartBean> it = showMineIndexBeans.iterator(); it.hasNext();){
                IndexChartBean chartBean = it.next();
                if(chartBean.getIndex_pkid().equals(event.getBean().getIndex_pkid())){
                    it.remove();
                }
            }

            for (IndexDetailListBean indexDetailListBean : showIndexTitles){
                for (IndexChartBean chartBean : indexDetailListBean.getDetail()){
                    if(chartBean.getIndex_pkid().equals(event.getBean().getIndex_pkid())){
                        chartBean.setSelected(false);
                    }
                }
            }

            for (IndexChartBean indexChartBean : allIndexBeans){
                if(indexChartBean.getIndex_pkid().equals(event.getBean().getIndex_pkid())){
                    indexChartBean.setSelected(false);
                }
            }

            for (IndexChartBean indexChartBean : recommendIndexBeans){
                if(indexChartBean.getIndex_pkid().equals(event.getBean().getIndex_pkid())){
                    indexChartBean.setSelected(false);
                }
            }
        }else{
            for (IndexDetailListBean indexDetailListBean : showIndexTitles){
                for (IndexChartBean chartBean : indexDetailListBean.getDetail()){
                    if(chartBean.getIndex_pkid().equals(event.getBean().getIndex_pkid())){
                        chartBean.setSelected(true);
                    }
                }
            }

            for (IndexChartBean indexChartBean : allIndexBeans){
                if(indexChartBean.getIndex_pkid().equals(event.getBean().getIndex_pkid())){
                    indexChartBean.setSelected(true);
                }
            }

            for (IndexChartBean indexChartBean : recommendIndexBeans){
                if(indexChartBean.getIndex_pkid().equals(event.getBean().getIndex_pkid())){
                    indexChartBean.setSelected(true);
                }
            }
            showMineIndexBeans.add(event.getBean());
        }
        adapter.notifyDataSetChanged();
        indexOfBottomAdapter.notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(SearchIndexBeanSuccessEvent event){
        allIndexBeans.clear();
        allIndexBeans.addAll(event.getSearchIndexBean().getIndexChartBeans());
        for (IndexChartBean chartBean : allIndexBeans){
            if (chartBean.getSelected()){
                showMineIndexBeans.add(chartBean);
            }
        }
        adapter.notifyDataSetChanged();
    }
}
