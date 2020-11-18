package com.datacvg.sempmobile.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.datacvg.sempmobile.R;
import com.datacvg.sempmobile.adapter.ScreenDetailAdapter;
import com.datacvg.sempmobile.baseandroid.config.Constants;
import com.datacvg.sempmobile.baseandroid.retrofit.RxObserver;
import com.datacvg.sempmobile.baseandroid.utils.PLog;
import com.datacvg.sempmobile.baseandroid.utils.RxUtils;
import com.datacvg.sempmobile.baseandroid.utils.StatusBarUtil;
import com.datacvg.sempmobile.bean.ScreenBean;
import com.datacvg.sempmobile.bean.ScreenDetailBean;
import com.datacvg.sempmobile.bean.ScreenFormatBean;
import com.datacvg.sempmobile.presenter.ScreenDetailPresenter;
import com.datacvg.sempmobile.view.ScreenDetailView;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.tbruyelle.rxpermissions2.RxPermissions;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-10-22
 * @Description :
 */
public class ScreenDetailActivity extends BaseActivity<ScreenDetailView, ScreenDetailPresenter>
        implements ScreenDetailView, ScreenDetailAdapter.OnScreenDetailClick {

    @BindView(R.id.tv_title)
    TextView tvTitle ;
    @BindView(R.id.img_right)
    ImageView imgRight ;
    @BindView(R.id.tv_name)
    TextView tvName ;
    @BindView(R.id.tv_size)
    TextView tvSize ;
    @BindView(R.id.rel_screenPlayControl)
    RelativeLayout relScreenPlayControl ;
    @BindView(R.id.img_playOrStop)
    ImageView imgPlayOrStop ;
    @BindView(R.id.recycler_playList)
    RecyclerView recyclerPlayList ;

    private String title ;
    private ScreenBean bean ;
    private IntentIntegrator mIntentIntegrator ;

    private ScreenDetailAdapter adapter ;
    private List<ScreenDetailBean.ListBean> beans = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_screen_detail;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void setupView() {
        StatusBarUtil.setStatusBarColor(mContext
                ,mContext.getResources().getColor(R.color.c_FFFFFF));
    }

    @Override
    protected void setupData(Bundle savedInstanceState) {
        title = getIntent().getStringExtra("title");
        bean = (ScreenBean) getIntent().getSerializableExtra(Constants.EXTRA_DATA_FOR_BEAN);

        tvTitle.setText(title);
        imgRight.setImageBitmap(BitmapFactory.decodeResource(resources,R.mipmap.vpn_scan));
        tvName.setText(bean.getScreen_name());
        ScreenFormatBean screenFormatBean = new Gson().fromJson(bean.getScreen_format()
                ,ScreenFormatBean.class);
        tvSize.setText(screenFormatBean.getSize()
                + mContext.getResources().getString(R.string.inch) + screenFormatBean.getType()
                + (TextUtils.isEmpty(screenFormatBean.getDirection()) ? ""
                : screenFormatBean.getDirection().equals("horizontal") ?
                mContext.getResources().getString(R.string.landscape) :
                mContext.getResources().getString(R.string.vertical_screen)));

        adapter = new ScreenDetailAdapter(mContext,beans,this);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(RecyclerView.VERTICAL);
        manager.setAutoMeasureEnabled(true);
        recyclerPlayList.setLayoutManager(manager);
        recyclerPlayList.setAdapter(adapter);

        getPresenter().getScreenDetail(bean.getScreen_id());
    }

    @OnClick({R.id.img_left,R.id.img_playOrStop,R.id.img_next
            ,R.id.img_pre,R.id.tv_add,R.id.img_right})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.img_left :
                    finish();
                break;

            case R.id.img_right :
                new RxPermissions(mContext)
                        .request(Manifest.permission.CAMERA
                                , Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .compose(RxUtils.applySchedulersLifeCycle(getMvpView()))
                        .subscribe(new RxObserver<Boolean>(){
                            @Override
                            public void onNext(Boolean aBoolean) {
                                if (aBoolean) {      //授权通过拍摄照片
                                    mIntentIntegrator = new IntentIntegrator(mContext);
                                    mIntentIntegrator.addExtra(Constants.EXTRA_DATA_FOR_ALBUM,false) ;
                                    mIntentIntegrator.addExtra(Constants.EXTRA_DATA_FOR_SCAN,Constants.SCAN_FOR_SCREEN) ;
                                    mIntentIntegrator.setCaptureActivity(ScanActivity.class);
                                    mIntentIntegrator.initiateScan();
                                }
                            }
                        });
                break;

            /**
             * 暂停 自动播放
             */
            case R.id.img_playOrStop :
                PLog.e("开始，暂停");
                break;

            /**
             * 下一张
             */
            case R.id.img_next :
                PLog.e("下一张");
                break;

            /**
             * 上一张
             */
            case R.id.img_pre :
                PLog.e("上一张");
                break;

            /**
             * 添加报表或图片
             */
            case R.id.tv_add :
                PLog.e("添加报表或图片");
                break;
        }
    }

    /**
     * 获取大屏详情成功
     * @param bean
     */
    @Override
    public void getScreenDetailSuccess(ScreenDetailBean bean) {
        beans.clear();
        beans.addAll(bean.getList());
        adapter.notifyDataSetChanged();
    }

    /**
     * 单个删除
     * @param position
     */
    @Override
    public void onDeleteClick(int position) {

    }

    /**
     * 单个设置
     * @param position
     */
    @Override
    public void onSettingClick(int position) {
        Intent intent = new Intent(mContext,ScreenSettingActivity.class);
        intent.putExtra(Constants.EXTRA_DATA_FOR_BEAN,beans.get(position));
        startActivity(intent);
    }
}
