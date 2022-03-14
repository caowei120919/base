package com.datacvg.dimp.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.datacvg.dimp.R;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.retrofit.RxObserver;
import com.datacvg.dimp.baseandroid.utils.FileUtils;
import com.datacvg.dimp.baseandroid.utils.LanguageUtils;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.RxUtils;
import com.datacvg.dimp.baseandroid.utils.ShareContentType;
import com.datacvg.dimp.baseandroid.utils.ShareUtils;
import com.datacvg.dimp.baseandroid.utils.StatusBarUtil;
import com.datacvg.dimp.baseandroid.utils.ToastUtils;
import com.datacvg.dimp.bean.IndexTreeBean;
import com.datacvg.dimp.bean.IndexTreeListBean;
import com.datacvg.dimp.bean.IndexTreeNeedBean;
import com.datacvg.dimp.presenter.IndexTreePresenter;
import com.datacvg.dimp.view.IndexTreeView;
import com.datacvg.dimp.widget.IndexTreeViewFlower;
import com.datacvg.dimp.widget.TitleNavigator;
import com.google.gson.Gson;
import com.tbruyelle.rxpermissions2.RxPermissions;

import net.lucode.hackware.magicindicator.FragmentContainerHelper;
import net.lucode.hackware.magicindicator.MagicIndicator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-12-10
 * @Description : 指标树页面
 */
public class IndexTreeActivity extends BaseActivity<IndexTreeView, IndexTreePresenter>
        implements IndexTreeView, TitleNavigator.OnTabSelectedListener, IndexTreeViewFlower.OnClickChangeListener, IndexTreeViewFlower.OnItemClickListener {
    @BindView(R.id.img_create)
    ImageView imgCreate ;
    @BindView(R.id.img_edit)
    ImageView imgEdit ;
    @BindView(R.id.img_share)
    ImageView imgShare ;
    @BindView(R.id.tv_smallSize)
    TextView tvSmallSize ;
    @BindView(R.id.tv_middleSize)
    TextView tvMiddleSize ;
    @BindView(R.id.tv_bigSize)
    TextView tvBigSize ;
    @BindView(R.id.tv_title)
    TextView tvTitle ;
    @BindView(R.id.magic_indicator)
    MagicIndicator magicIndicator ;
    @BindView(R.id.indexTreeView)
    IndexTreeViewFlower indexTreeView ;

    private List<String> titles = new ArrayList<>();
    private TitleNavigator titleNavigator ;
    private boolean isEditStatus = false ;
    private IndexTreeNeedBean indexTreeNeedBean ;
    private FragmentContainerHelper mFragmentContainerHelper ;
    private int otherIndex = 0 ;
    private IndexTreeListBean indexTreeBean ;
    private IndexTreeListBean selectedIndexBeans = new IndexTreeListBean();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_index_tree;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void setupView() {
        StatusBarUtil.setStatusBarColor(mContext
                ,mContext.getResources().getColor(R.color.c_FFFFFF));
        tvBigSize.setTextColor(resources.getColor(R.color.c_000000));
        tvBigSize.setTypeface(Typeface.DEFAULT_BOLD);
        tvSmallSize.setTextColor(resources.getColor(R.color.c_999999));
        tvMiddleSize.setTextColor(resources.getColor(R.color.c_999999));
    }

    @Override
    protected void setupData(Bundle savedInstanceState) {
        indexTreeNeedBean = (IndexTreeNeedBean) getIntent()
                .getSerializableExtra(Constants.EXTRA_DATA_FOR_BEAN);
        if(indexTreeNeedBean == null){
            finish();
            return;
        }
        tvTitle.setText(LanguageUtils.isZh(mContext) ? indexTreeNeedBean.getDimension_clName()
                : indexTreeNeedBean.getDimension_flName());
        titles.add(resources.getString(R.string.index));
        if (!TextUtils.isEmpty(indexTreeNeedBean.getOrgName())){
            titles.add(indexTreeNeedBean.getOrgName());
        }
        if (!TextUtils.isEmpty(indexTreeNeedBean.getpName())){
            titles.add(indexTreeNeedBean.getpName());
        }
        if (!TextUtils.isEmpty(indexTreeNeedBean.getFuName())){
            titles.add(indexTreeNeedBean.getFuName());
        }
        titleNavigator = new TitleNavigator(mContext,titles,false);
        titleNavigator.setOnTabSelectedListener(this);
        magicIndicator.setNavigator(titleNavigator);
        mFragmentContainerHelper = new FragmentContainerHelper();
        mFragmentContainerHelper.attachMagicIndicator(magicIndicator);
        mFragmentContainerHelper.handlePageSelected(otherIndex);
        indexTreeView.setClickChangeListener(this);
        indexTreeView.setOnItemClickListener(this);
        getIndexTreeData() ;
    }

    @OnClick({R.id.back,R.id.img_share,R.id.img_edit,R.id.img_create,R.id.tv_smallSize
            ,R.id.tv_middleSize,R.id.tv_bigSize,R.id.rel_detail})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.back :
                finish();
                break;

            /**
             * 分享
             */
            case R.id.img_share :
                new RxPermissions(mContext)
                        .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .compose(RxUtils.applySchedulersLifeCycle(getMvpView()))
                        .subscribe(new RxObserver<Boolean>(){
                            @Override
                            public void onComplete() {
                                super.onComplete();
                            }

                            @Override
                            public void onNext(Boolean aBoolean) {
                                super.onNext(aBoolean);
                                if(aBoolean){
                                    File file = FileUtils.screenshot(mContext) ;
                                    if(file != null){
                                        Uri mUri = Uri.fromFile(file);
                                        new ShareUtils
                                                .Builder(mContext)
                                                .setContentType(ShareContentType.IMAGE)
                                                .setShareFileUri(mUri)
                                                .build()
                                                .shareBySystem();
                                    }
                                }else{
                                    ToastUtils.showLongToast(resources.getString(R.string.dont_allow_permissions));
                                }
                            }
                        });
                break;

            case R.id.img_edit :
                if(!isEditStatus){
                    imgEdit.setImageBitmap(BitmapFactory.decodeResource(resources
                            ,R.mipmap.icon_index_edit));
                    imgCreate.setImageBitmap(BitmapFactory.decodeResource(resources
                            ,R.mipmap.icon_task_creat));
                }else{
                    imgEdit.setImageBitmap(BitmapFactory.decodeResource(resources
                            ,R.mipmap.icon_index_common));
                    imgCreate.setImageBitmap(BitmapFactory.decodeResource(resources
                            ,R.mipmap.icon_task_common));
                }
                isEditStatus = !isEditStatus ;
                indexTreeView.setEditStatus(isEditStatus);
                break;

            case R.id.img_create :
                if(!isEditStatus){
                    return;
                }
                if(selectedIndexBeans != null && selectedIndexBeans.size() > 0){
                    Intent intent = new Intent(mContext,NewTaskActivity.class);
                    intent.putExtra(Constants.EXTRA_DATA_FOR_SCAN,false);
                    intent.putExtra(Constants.EXTRA_DATA_FOR_BEAN,selectedIndexBeans);
                    intent.putExtra(Constants.EXTRA_DATA_FOR_ALBUM
                            ,indexTreeNeedBean);
                    mContext.startActivity(intent);
                }else{
                    ToastUtils.showLongToast(resources
                            .getString(R.string.at_least_one_indicator_can_be_selected_before_an_action_plan_can_be_issued));
                }
                break;

            case R.id.tv_smallSize :
                if(isEditStatus){
                    return;
                }
                tvSmallSize.setTextColor(resources.getColor(R.color.c_000000));
                tvSmallSize.setTypeface(Typeface.DEFAULT_BOLD);
                tvMiddleSize.setTextColor(resources.getColor(R.color.c_999999));
                tvBigSize.setTextColor(resources.getColor(R.color.c_999999));
                indexTreeView.drawIndexTree(indexTreeBean,3);
                break;

            case R.id.tv_middleSize :
                if(isEditStatus){
                    return;
                }
                tvMiddleSize.setTextColor(resources.getColor(R.color.c_000000));
                tvMiddleSize.setTypeface(Typeface.DEFAULT_BOLD);
                tvSmallSize.setTextColor(resources.getColor(R.color.c_999999));
                tvBigSize.setTextColor(resources.getColor(R.color.c_999999));
                indexTreeView.drawIndexTree(indexTreeBean,2);
                break;

            case R.id.tv_bigSize :
                if(isEditStatus){
                    return;
                }
                tvBigSize.setTextColor(resources.getColor(R.color.c_000000));
                tvBigSize.setTypeface(Typeface.DEFAULT_BOLD);
                tvSmallSize.setTextColor(resources.getColor(R.color.c_999999));
                tvMiddleSize.setTextColor(resources.getColor(R.color.c_999999));
                indexTreeView.drawIndexTree(indexTreeBean,1);
                break;

            case R.id.rel_detail :

                break;
        }
    }

    @Override
    public void onTabSelected(int position) {
        if(isEditStatus){
            return;
        }
        selectedIndexBeans.clear();
        mFragmentContainerHelper.handlePageSelected(position);
        indexTreeNeedBean.setType(position == 0 ? "4" : position + "");
        getIndexTreeData() ;
    }

    /**
     * 获取指标树数据
     */
    private void getIndexTreeData() {
        Map map = new Gson().fromJson(new Gson().toJson(indexTreeNeedBean),Map.class);
        getPresenter().getIndexTree(map);
    }

    /**
     * 获取指标树数据成功
     * @param resdata
     */
    @Override
    public void getIndexTreeSuccess(IndexTreeListBean resdata) {
        indexTreeBean = resdata ;
        if(resdata != null){
            indexTreeView.drawIndexTree(resdata,1);
        }
        PLog.e(indexTreeView.getChildCount() + "");
    }

    /**
     * 下发选中状态切换
     * @param isClick
     * @param bean
     */
    @Override
    public void OnClickChange(boolean isClick, IndexTreeBean bean) {
        if(isClick){
            selectedIndexBeans.add(bean);
        }else{
            selectedIndexBeans.remove(bean);
        }
    }

    /**
     * 指标树点击事件
     * @param bean
     */
    @Override
    public void onItemClick(IndexTreeBean bean) {
        if (isEditStatus){
            return;
        }
        Intent intent = new Intent(mContext,IndexPopActivity.class);
        intent.putExtra(Constants.EXTRA_DATA_FOR_BEAN,bean);
        intent.putExtra(Constants.EXTRA_DATA_FOR_SCAN,indexTreeNeedBean);
        mContext.startActivity(intent);
    }
}
