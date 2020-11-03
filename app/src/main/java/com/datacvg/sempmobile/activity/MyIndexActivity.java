package com.datacvg.sempmobile.activity;

import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.datacvg.sempmobile.R;
import com.datacvg.sempmobile.adapter.MyIndexAdapter;
import com.datacvg.sempmobile.adapter.OtherIndexAdapter;
import com.datacvg.sempmobile.baseandroid.utils.PLog;
import com.datacvg.sempmobile.baseandroid.utils.StatusBarUtil;
import com.datacvg.sempmobile.bean.IndexBean;
import com.datacvg.sempmobile.event.ChangeIndexEvent;
import com.datacvg.sempmobile.presenter.MyIndexPresenter;
import com.datacvg.sempmobile.view.MyIndexView;
import com.datacvg.sempmobile.widget.TitleNavigator;

import net.lucode.hackware.magicindicator.FragmentContainerHelper;
import net.lucode.hackware.magicindicator.MagicIndicator;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-09-30
 * @Description : 我的指标
 */
public class MyIndexActivity extends BaseActivity<MyIndexView, MyIndexPresenter>
        implements MyIndexView, MyIndexAdapter.OnMyIndexClickListener
        , TitleNavigator.OnTabSelectedListener
        , OtherIndexAdapter.OnOtherIndexClickListener {
    @BindView(R.id.tv_title)
    TextView tvTitle ;
    @BindView(R.id.recycler_index)
    RecyclerView recyclerIndex ;
    @BindView(R.id.magic_indicator)
    MagicIndicator magicIndicator ;
    @BindView(R.id.recycler_otherIndex)
    RecyclerView recyclerOtherIndex ;

    private TitleNavigator titleNavigator ;
    private MyIndexAdapter myIndexAdapter ;
    private OtherIndexAdapter otherIndexAdapter ;
    private List<IndexBean.MyListBean> myListBeans = new ArrayList<>() ;
    private List<IndexBean.EndResultBean> otherIndexBeans = new ArrayList<>();
    private List<IndexBean.EndIndexBean> endIndexBeans = new ArrayList<>();
    private List<String> titles = new ArrayList<>();
    private FragmentContainerHelper mFragmentContainerHelper ;
    private int otherIndex = 0 ;
    private boolean hasChange = false ;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_index;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void setupView() {
        StatusBarUtil.setStatusBarColor(mContext
                ,mContext.getResources().getColor(R.color.c_FFFFFF));

        myIndexAdapter = new MyIndexAdapter(mContext,myListBeans,this);
        recyclerIndex.setLayoutManager(new GridLayoutManager(mContext,3));
        recyclerIndex.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view
                    , @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = (int) resources.getDimension(R.dimen.W10);
            }
        });
        recyclerIndex.setAdapter(myIndexAdapter);

        otherIndexAdapter = new OtherIndexAdapter(mContext,endIndexBeans,this);
        recyclerOtherIndex.setLayoutManager(new GridLayoutManager(mContext,3));
        recyclerOtherIndex.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view
                    , @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = (int) resources.getDimension(R.dimen.W10);
            }
        });
        recyclerOtherIndex.setAdapter(otherIndexAdapter);
    }

    @Override
    protected void setupData(Bundle savedInstanceState) {
        tvTitle.setText(resources.getString(R.string.digital_nervous));
        mFragmentContainerHelper = new FragmentContainerHelper();
        getPresenter().getIndex();
    }

    @OnClick({R.id.img_left})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.img_left :
                    if(hasChange){
                        StringBuilder indexIds = new StringBuilder() ;
                        if(myListBeans.size() > 0){
                            for (int i = 0 ; i < myListBeans.size() ; i ++){
                                if(i == myListBeans.size() - 1){
                                    indexIds.append(myListBeans.get(i).getId());
                                }else{
                                    indexIds.append(myListBeans.get(i).getId() + ",");
                                }
                            }
                        }
                        getPresenter().changeSelectedIndex(indexIds.toString());
                    }else{
                        finish();
                    }
                break;
        }
    }

    /**
     * 获取指标成功
     * @param resdata
     */
    @Override
    public void getIndexSuccess(IndexBean resdata) {
        myListBeans.clear();
        otherIndexBeans.clear();
        myListBeans.addAll(resdata.getMyList());
        otherIndexBeans.addAll(resdata.getEndResult());
        myIndexAdapter.notifyDataSetChanged();

        for (IndexBean.EndResultBean bean:resdata.getEndResult()){
            titles.add(bean.getIndex_classification_name());
        }
        if (titles.size() < 0){
            return;
        }
        titleNavigator = new TitleNavigator(mContext,titles);
        titleNavigator.setOnTabSelectedListener(this);
        magicIndicator.setNavigator(titleNavigator);
        mFragmentContainerHelper.attachMagicIndicator(magicIndicator);
        mFragmentContainerHelper.handlePageSelected(otherIndex);
        endIndexBeans.clear();
        if(resdata.getEndResult().get(otherIndex) != null
                && resdata.getEndResult().get(otherIndex).getList().size() > 0){
            endIndexBeans.addAll(resdata.getEndResult().get(otherIndex).getList());
        }
    }

    /**
     * 修改指标成功
     */
    @Override
    public void changeIndexSuccess() {
        EventBus.getDefault().post(new ChangeIndexEvent());
        finish();
    }

    /**
     * 修改指标失败
     */
    @Override
    public void changeIndexFail() {
        finish();
    }

    /**
     * 我的指标单个被选择
     * @param position
     */
    @Override
    public void onMyIndexClick(int position) {
        hasChange = true ;
        IndexBean.MyListBean bean = myListBeans.get(position);
        for (IndexBean.EndResultBean endResultBean : otherIndexBeans){
            if(bean.getIndex_type().equals(endResultBean.getIndex_type())){
                IndexBean.EndIndexBean endIndexBean = new IndexBean.EndIndexBean();
                endIndexBean.setIndex_type(bean.getIndex_type());
                endIndexBean.setIndex_id(bean.getId());
                endIndexBean.setChart_high(bean.getSize_y() + "");
                endIndexBean.setChart_wide(bean.getSize_x() + "");
                endIndexBean.setAnalysis_dimension(bean.getAnalysis_dimension());
                endIndexBean.setChart_type(bean.getChart_type());
                endIndexBean.setIndex_clname(bean.getTitle());
                endResultBean.getList().add(endIndexBean);
            }
        }
        myListBeans.remove(bean);
        endIndexBeans.clear();
        endIndexBeans.addAll(otherIndexBeans.get(otherIndex).getList());
        otherIndexAdapter.notifyDataSetChanged();
        myIndexAdapter.notifyDataSetChanged();
    }

    /**
     * title选项选择
     * @param position
     */
    @Override
    public void onTabSelected(int position) {
        mFragmentContainerHelper.handlePageSelected(position);
        endIndexBeans.clear();
        endIndexBeans.addAll(otherIndexBeans.get(position).getList());
        otherIndexAdapter.notifyDataSetChanged();
        otherIndex = position ;
        PLog.e("选中的选项为" + position + ",标题为" + titles.get(position));
    }

    /**
     * 其他指标单个被选择
     * @param position
     */
    @Override
    public void onOtherIndexClick(int position) {
        hasChange = true ;
        otherIndexBeans.get(otherIndex).getList().remove(position);
        IndexBean.EndIndexBean endIndexBean = endIndexBeans.get(position);
        IndexBean.MyListBean bean = new IndexBean.MyListBean();
        bean.setIndex_type(endIndexBean.getIndex_type());
        bean.setId(endIndexBean.getIndex_id());
        bean.setSize_y(Integer.valueOf(endIndexBean.getChart_high()));
        bean.setSize_x(Integer.valueOf(endIndexBean.getChart_wide()));
        bean.setAnalysis_dimension(endIndexBean.getAnalysis_dimension());
        bean.setChart_type(endIndexBean.getChart_type());
        bean.setTitle(TextUtils.isEmpty(endIndexBean.getChart_top_title())
                ? endIndexBean.getIndex_clname() : endIndexBean.getChart_top_title());
        myListBeans.add(bean);
        endIndexBeans.remove(position);
        myIndexAdapter.notifyDataSetChanged();
        otherIndexAdapter.notifyDataSetChanged();
    }
}
