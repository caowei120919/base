package com.datacvg.dimp.activity;

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
import com.datacvg.dimp.R;
import com.datacvg.dimp.adapter.ScreenDetailAdapter;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.retrofit.RxObserver;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.RxUtils;
import com.datacvg.dimp.baseandroid.utils.StatusBarUtil;
import com.datacvg.dimp.baseandroid.utils.Handlers;
import com.datacvg.dimp.baseandroid.widget.CVGOKCancelWithTitle;
import com.datacvg.dimp.bean.ScreenBean;
import com.datacvg.dimp.bean.ScreenDetailBean;
import com.datacvg.dimp.bean.ScreenFormatBean;
import com.datacvg.dimp.bean.WebSocketLinkBean;
import com.datacvg.dimp.bean.WebSocketMessageBean;
import com.datacvg.dimp.event.ForScreenSuccessEvent;
import com.datacvg.dimp.presenter.ScreenDetailPresenter;
import com.datacvg.dimp.socket.ScreenWebSocket;
import com.datacvg.dimp.socket.listener.ScreenWebSocketListener;
import com.datacvg.dimp.view.ScreenDetailView;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okio.ByteString;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-10-22
 * @Description :
 */
public class ScreenDetailActivity extends BaseActivity<ScreenDetailView, ScreenDetailPresenter>
        implements ScreenDetailView, ScreenDetailAdapter.OnScreenDetailClick, ScreenWebSocketListener {

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
    private ScreenWebSocket screenWebSocket;
    private WebSocketLinkBean linkBean ;
    private WebSocket webSocket ;
    private boolean hasLinked = false ;
    /**
     * 是播放还是暂停状态
     *      按钮点击会切换状态
     */
    private boolean isStart = false ;

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
        Constants.screenBean = bean ;
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
                    Constants.screenBean = null ;
                    finish();
                break;

            case R.id.img_right :
                if (hasLinked){
                    exitWebSocket();
                }else{
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
                                        mIntentIntegrator.addExtra(Constants.EXTRA_DATA_FOR_SCAN
                                                ,Constants.SCAN_FOR_SCREEN) ;
                                        mIntentIntegrator.addExtra("title",title);
                                        mIntentIntegrator.setCaptureActivity(ScanActivity.class);
                                        mIntentIntegrator.initiateScan();
                                    }
                                }
                            });
                }
                break;

            /**
             * 暂停 自动播放
             */
            case R.id.img_playOrStop :
                if(linkBean != null){
                    getPresenter().confirmOnTheScreen("app",Constants.token,linkBean.getScreenTime()
                            ,new Gson().toJson(bean),isStart ? Constants.SCREEN_PAUSE : Constants.SCREEN_START
                            ,linkBean.getCurrentPosition() + ""
                            ,Constants.COMMON_CODE,linkBean.getTargetIp());
                }
                break;

            /**
             * 下一张
             */
            case R.id.img_next :
                PLog.e("下一张");
                getPresenter().confirmOnTheScreen("app",Constants.token,linkBean.getScreenTime()
                        ,new Gson().toJson(bean),isStart ? Constants.SCREEN_PAUSE : Constants.SCREEN_START
                        ,linkBean.getCurrentPosition() == adapter.getItemCount() - 1 ? 0 + ""
                                : linkBean.getCurrentPosition() + 1 + ""
                        ,Constants.COMMON_CODE,linkBean.getTargetIp());
                break;

            /**
             * 上一张
             */
            case R.id.img_pre :
                PLog.e("上一张");
                getPresenter().confirmOnTheScreen("app",Constants.token,linkBean.getScreenTime()
                        ,new Gson().toJson(bean),isStart ? Constants.SCREEN_PAUSE : Constants.SCREEN_START
                        ,linkBean.getCurrentPosition() == 0 ? adapter.getItemCount() - 1 + ""
                                : linkBean.getCurrentPosition() - 1 + ""
                        ,Constants.COMMON_CODE,linkBean.getTargetIp());
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
     * 主动退出大屏
     */
    private void exitWebSocket() {
        CVGOKCancelWithTitle dialogOKCancel = new CVGOKCancelWithTitle(mContext);
        dialogOKCancel.setMessage(mContext.getResources()
                .getString(R.string.sure_to_exit_the_projection_screen));
        dialogOKCancel.getPositiveButton().setText(mContext.getResources().getString(R.string.confirm));
        dialogOKCancel.setOnClickPositiveButtonListener(view -> {
            if(linkBean != null){
                getPresenter().confirmOnTheScreen("app",Constants.token,linkBean.getScreenTime()
                        ,new Gson().toJson(bean),isStart ? Constants.SCREEN_CLOSE : Constants.SCREEN_START
                        ,linkBean.getCurrentPosition() + ""
                        ,Constants.COMMON_CODE,linkBean.getTargetIp());
            }
        });
        dialogOKCancel.setOnClickListenerNegativeBtn(view -> {
            dialogOKCancel.dismiss();
        });
        dialogOKCancel.show();
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

    @Override
    public void forScreenFailed(String scIndexStatus) {
        switch (scIndexStatus){
            case Constants.SCREEN_START :

                break;

            case Constants.SCREEN_PAUSE :

                break;

            case Constants.SCREEN_NEXT :

                break;

            case Constants.SCREEN_UPPER :

                break;

            case Constants.SCREEN_CLOSE :

                break;


        }
    }

    @Override
    public void forScreenSuccess(String scIndexStatus) {
        switch (scIndexStatus){
            case Constants.SCREEN_START :
                    isStart = true ;
                    imgPlayOrStop.setImageBitmap(BitmapFactory
                            .decodeResource(resources,R.mipmap.screen_pause_gray));
                break;

            case Constants.SCREEN_PAUSE :
                    isStart = false ;
                    imgPlayOrStop.setImageBitmap(BitmapFactory
                            .decodeResource(resources,R.mipmap.screen_play_gray));
                break;

            case Constants.SCREEN_NEXT :
                    PLog.e("下一个");
                break;

            case Constants.SCREEN_UPPER :
                    PLog.e("上一个");
                break;

            case Constants.SCREEN_CLOSE :
                    Constants.linkBeans.remove(linkBean);
                    showOrHiddenController(false);
                break;
        }
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

    /**
     * 投屏成功，返回建立websocket链接
     * @param event
     */
    @Subscribe (threadMode = ThreadMode.MAIN)
    public void OnEvent(ForScreenSuccessEvent event){
        createWebSocket();
    }

    /**
     * 建立websocket
     */
    private void createWebSocket() {
        screenWebSocket = ScreenWebSocket.get(this);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60,TimeUnit.SECONDS)
                .connectTimeout(60,TimeUnit.SECONDS)
                .pingInterval(30, TimeUnit.SECONDS)
                .build();
        String webSocketUrl = Constants.BASE_FIS_URL + "largescreen?code=" + Constants.token ;
        Request request = new Request.Builder().url(webSocketUrl).build() ;
        webSocket = okHttpClient.newWebSocket(request,screenWebSocket);
    }

    /**
     * 建立了链接
     * @param webSocket
     * @param response
     */
    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        PLog.e(TAG,"onOpen()");
        requestWebSocket();
    }

    @Override
    public void onClosed(WebSocket webSocket, int code, String reason) {
        PLog.e(TAG,"onClosed()");
        showOrHiddenController(false);
        this.webSocket = null ;
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        PLog.e(TAG,"onClosing()");
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        PLog.e(TAG,"onFailure()");
        showOrHiddenController(false);
        for (WebSocketLinkBean linkBean : Constants.linkBeans){
            if(linkBean.getScreenId().equals(bean.getScreen_id())){
                Constants.linkBeans.remove(linkBean);
            }
        }
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        PLog.e(TAG,"onMessage(text) -------->> " + text);
        WebSocketMessageBean webSocketMessageBean = new Gson().fromJson(text,WebSocketMessageBean.class);
        if(!TextUtils.isEmpty(webSocketMessageBean.getIp())){
            showOrHiddenController(true);
            return;
        }
        linkBean.setCurrentPosition(Integer
                    .parseInt(webSocketMessageBean.getScIndexStatus()));
    }

    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
        PLog.e(TAG,"onMessage(bytes)");
    }

    /**
     * 展示或者隐藏控制器
     * @param isShow
     */
    private void showOrHiddenController(boolean isShow) {
        hasLinked = isShow ;
        Handlers.sharedHandler(mContext).post(()->{
            if(relScreenPlayControl != null && imgRight != null){
                relScreenPlayControl.setVisibility(isShow ? View.VISIBLE : View.GONE);
                imgPlayOrStop.setImageBitmap(BitmapFactory.decodeResource(resources,isStart
                        ? R.mipmap.screen_pause_gray : R.mipmap.screen_play_gray));
                imgRight.setImageBitmap(BitmapFactory.decodeResource(resources,isShow
                        ? R.mipmap.account_logout : R.mipmap.vpn_scan));
            }
        });
    }

    /**
     * 查询当前状态
     */
    private void requestWebSocket() {
        for (WebSocketLinkBean webSocketLinkBean : Constants.linkBeans){
            if(webSocketLinkBean.getScreenId().equals(bean.getScreen_id())){
                linkBean = webSocketLinkBean ;
            }
        }

        if (linkBean == null){
            return;
        }

        getPresenter().confirmOnTheScreen("app",Constants.token,linkBean.getScreenTime()
                ,new Gson().toJson(bean),isStart ? Constants.SCREEN_REQUEST : Constants.SCREEN_START
                ,linkBean.getCurrentPosition() + ""
                ,Constants.COMMON_CODE,linkBean.getTargetIp());
    }

    @Override
    protected void onDestroy() {
        if(webSocket != null){
            webSocket.close(1000,"finish");
            webSocket = null ;
        }
        super.onDestroy();
    }
}
