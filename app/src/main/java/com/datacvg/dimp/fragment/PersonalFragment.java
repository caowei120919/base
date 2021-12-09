package com.datacvg.dimp.fragment;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.datacvg.dimp.R;
import com.datacvg.dimp.activity.FeedBackActivity;
import com.datacvg.dimp.activity.InComeDetailActivity;
import com.datacvg.dimp.activity.MessageCentreActivity;
import com.datacvg.dimp.activity.ScanActivity;
import com.datacvg.dimp.activity.SettingActivity;
import com.datacvg.dimp.adapter.JobAdapter;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.retrofit.RxObserver;
import com.datacvg.dimp.baseandroid.retrofit.helper.PreferencesHelper;
import com.datacvg.dimp.baseandroid.utils.FileUtils;
import com.datacvg.dimp.baseandroid.utils.GlideLoader;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.RxUtils;
import com.datacvg.dimp.baseandroid.utils.StatusBarUtil;
import com.datacvg.dimp.baseandroid.utils.ToastUtils;
import com.datacvg.dimp.bean.MessageBean;
import com.datacvg.dimp.bean.UserJobsBean;
import com.datacvg.dimp.bean.UserJobsListBean;
import com.datacvg.dimp.event.ChangeUnReadMessageEvent;
import com.datacvg.dimp.event.LoginOutEvent;
import com.datacvg.dimp.event.SwitchUserEvent;
import com.datacvg.dimp.presenter.PersonPresenter;
import com.datacvg.dimp.view.PersonView;
import com.datacvg.dimp.widget.CircleNumberView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.lcw.library.imagepicker.ImagePicker;
import com.mylhyl.superdialog.SuperDialog;
import com.tbruyelle.rxpermissions2.RxPermissions;
import org.greenrobot.eventbus.EventBus;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import static android.app.Activity.RESULT_OK;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-09-29
 * @Description : 个人中心
 */
public class PersonalFragment extends BaseFragment<PersonView, PersonPresenter> implements PersonView, JobAdapter.OnJobChangeListener {

    @BindView(R.id.tv_title)
    TextView tvTitle ;
    @BindView(R.id.img_left)
    ImageView imgLeft ;
    @BindView(R.id.img_right)
    ImageView imgRight ;
    @BindView(R.id.circle_head)
    CircleImageView circleHead ;
    @BindView(R.id.tv_name)
    TextView tvName ;
    @BindView(R.id.tv_companyName)
    TextView tvCompanyName ;
    @BindView(R.id.tv_jobName)
    TextView tvJobName ;
    @BindView(R.id.tv_unReadMessageCount)
    CircleNumberView tvUnReadMessageCount ;

    private List<UserJobsBean> userJobsBeans = new ArrayList<>();
    private IntentIntegrator mIntentIntegrator ;
    private int pageSize = Constants.MAX_PAGE_SIZE ;
    private int pageIndex = 1;
    private String module_id = "" ;
    private PopupWindow menuWindow ;
    /**
     * T for true and F for false
     */
    private String read_flag = "F" ;
    private List<String> permissions = new ArrayList<>();
    private int unReadMessage = 0 ;
    private Uri imageUri ;
    private File mTmpFile ;
    private String changeAvatarPath;
    private UserJobsBean userJobsBean = null ;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_person;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void setupView(View rootView) {
        StatusBarUtil.setStatusBarColor(getActivity()
                ,mContext.getResources().getColor(R.color.c_FFFFFF));
    }

    @Override
    protected void setupData() {
        tvTitle.setText(resources.getString(R.string.personal_center));
        imgLeft.setVisibility(View.GONE);
        imgRight.setImageBitmap(BitmapFactory.decodeResource(resources,R.mipmap.vpn_scan));
        tvCompanyName.setText(PreferencesHelper.get(Constants.USER_ORG_NAME,""));
        tvName.setText(PreferencesHelper.get(Constants.USER_NAME,""));
        String currentPkId = PreferencesHelper.get(Constants.USER_CURRENT_PKID,"");
        GlideUrl imgUrl = new GlideUrl(Constants.BASE_MOBILE_URL + Constants.HEAD_IMG_URL
                + (TextUtils.isEmpty(currentPkId) ? PreferencesHelper.get(Constants.USER_PKID,"") : currentPkId)
                , new LazyHeaders.Builder().addHeader(Constants.AUTHORIZATION,Constants.token).build());
        Glide.with(mContext).load(imgUrl).diskCacheStrategy(DiskCacheStrategy.NONE).into(circleHead);
        if(TextUtils.isEmpty(currentPkId)){
            getPresenter().getJob(PreferencesHelper.get(Constants.USER_PKID,""));
        }else {
            getPresenter().getJob(currentPkId);
        }
        getPresenter().getMessage(pageIndex+ "",pageSize + "",module_id,read_flag);
    }

    @OnClick({R.id.rel_setting,R.id.rel_logout,R.id.img_right,R.id.rel_message
            ,R.id.rel_feedback,R.id.circle_head,R.id.tv_jobName})
    public void OnClick(View view){
        switch (view.getId()){
            /**
             * 设置
             */
            case R.id.rel_setting :
                startActivity(new Intent(mContext, SettingActivity.class));
                break;

            /**
             * 退出登录
             */
            case R.id.rel_logout :
                    getPresenter().loginOut();
                break;

            case R.id.img_right :
                new RxPermissions(getActivity())
                        .request(Manifest.permission.CAMERA
                                , Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .compose(RxUtils.applySchedulersLifeCycle(getMvpView()))
                        .subscribe(new RxObserver<Boolean>(){
                            @Override
                            public void onNext(Boolean aBoolean) {
                                if (aBoolean) {      //授权通过拍摄照片
                                    mIntentIntegrator = new IntentIntegrator(getActivity());
                                    mIntentIntegrator.setCaptureActivity(ScanActivity.class);
                                    mIntentIntegrator.addExtra(Constants.EXTRA_DATA_FOR_SCAN
                                            ,Constants.SCAN_FOR_LOGIN);
                                    mIntentIntegrator.addExtra(Constants.EXTRA_DATA_FOR_ALBUM,false);
                                    mIntentIntegrator.initiateScan();
                                }
                            }
                        });
                break;

            /**
             * 消息中心
             */
            case R.id.rel_message :
                    mContext.startActivity(new Intent(mContext, MessageCentreActivity.class));
                break;

            /**
             * 意见反馈
             */
            case R.id.rel_feedback :
                    mContext.startActivity(new Intent(mContext, FeedBackActivity.class));
                break;

            /**
             * 更换用户头像
             */
            case R.id.circle_head :
                List<String> listButton = new ArrayList<>();
                listButton.add(resources.getString(R.string.taking_pictures));
                listButton.add(resources.getString(R.string.photo_album));
                new SuperDialog.Builder(getActivity())
                        .setCanceledOnTouchOutside(true)
                        .setItems(listButton,resources.getColor(R.color.c_303030),24,80
                                , new SuperDialog.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(int position) {
                                        switch (position){
                                            case 0 :
                                                tackPhoto();
                                                break;

                                            case 1 :
                                                chooseForAlbum();
                                                break;
                                        }
                                    }
                                })
                        .setNegativeButton(resources.getString(R.string.cancel)
                                ,resources.getColor(R.color.c_da3a16),24,80
                                , null)
                        .build();
                break;

            case R.id.tv_jobName :
                PLog.e("切换岗位");
                showJobSelectPop();
                break;
        }
    }

    /**
     * 选择岗位弹窗
     */
    private void showJobSelectPop() {
        View containView = LayoutInflater.from(mContext).inflate(R.layout.item_select_job,
                null,false);
        RecyclerView recycleJob = containView.findViewById(R.id.recycle_job);
        JobAdapter jobAdapter = new JobAdapter(mContext,this,userJobsBeans);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        recycleJob.setLayoutManager(manager);
        recycleJob.setAdapter(jobAdapter);
        menuWindow = new PopupWindow(containView, tvJobName.getWidth(), (int) resources.getDimension(R.dimen.H360));
        menuWindow.setFocusable(true);
        menuWindow.setBackgroundDrawable(new BitmapDrawable());
        menuWindow.showAsDropDown(tvJobName);
        menuWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                menuWindow=null;
            }
        });
    }

    /**
     * 相册选取
     */
    private void chooseForAlbum() {
        ImagePicker.getInstance()
                .setTitle(mContext.getResources().getString(R.string.select_picture))
                .setImageLoader(new GlideLoader())
                .showCamera(false)
                .showImage(true)
                .showVideo(false)
                .setSingleType(true)
                .setMaxCount(1)
                .start(getActivity(), Constants.REQUEST_OPEN_CAMERA);
    }

    /**
     * 拍照
     */
    private void tackPhoto() {
        new RxPermissions(getActivity())
                .request(Manifest.permission.CAMERA
                        , Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .compose(RxUtils.applySchedulersLifeCycle(getMvpView()))
                .subscribe(new RxObserver<Boolean>(){
                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean){      //授权通过拍摄照片
                            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            if (cameraIntent.resolveActivity(mContext.getPackageManager()) != null){
                                mTmpFile = FileUtils.createTmpFile(mContext);
                                //通过FileProvider创建一个content类型的Uri
                                imageUri = FileProvider.getUriForFile(mContext
                                        , "com.datacvg.dimp.fileprovider", mTmpFile);
                                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                                startActivityForResult(cameraIntent, Constants.REQUEST_TAKE_PHOTO);
                            }
                        }else{
                            ToastUtils.showLongToast(mContext.getResources()
                                    .getString(R.string.dont_allow_permissions));
                        }
                    }
                });
    }

    /**
     * 成功退出登录
     */
    @Override
    public void loginOutSuccess() {
        Constants.BASE_FIS_URL = "" ;
        Constants.BASE_MOBILE_URL = "" ;
        Constants.BASE_UPLOAD_URL = "" ;

        Constants.token = "" ;
        RetrofitUrlManager.getInstance()
                .setGlobalDomain(Constants.BASE_URL);
        RetrofitUrlManager.getInstance().setRun(false);

        EventBus.getDefault().post(new LoginOutEvent());
    }

    /**
     * 用户职位获取成功
     * @param resdata
     */
    @Override
    public void getUseJobsSuccess(UserJobsListBean resdata) {
        userJobsBeans = resdata ;
        String currentPkId = PreferencesHelper.get(Constants.USER_PKID,"") ;
        for (UserJobsBean bean: resdata) {
            if(TextUtils.isEmpty(bean.getUser_pkid())){
                continue;
            }else{
                if (bean.getUser_pkid().equals(currentPkId)){
                    tvJobName.setText(bean.getPost_clname());
                }
            }
        }
    }

    @Override
    public void getMessageSuccess(MessageBean resdata) {
        if (resdata == null){
            return;
        }
        if(resdata.getPermissions() == null || resdata.getPermissions().size() == 0){
            return;
        }
        permissions.addAll(resdata.getPermissions());
        for (MessageBean.ResultBean bean : resdata.getResult()){
            if(permissions.contains(bean.getModule_id())){
                unReadMessage ++ ;
            }
        }
        if(unReadMessage > 0){
            tvUnReadMessageCount.setNumber(unReadMessage+ "");
            EventBus.getDefault().post(new ChangeUnReadMessageEvent(unReadMessage));
        }
    }

    /**
     * 岗位切换成功
     */
    @Override
    public void switchJobSuccess() {
//        tvJobName.setText(userJobsBean.getPost_clname());
        EventBus.getDefault().post(new SwitchUserEvent());
        setupData();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode==RESULT_OK){
            switch (requestCode) {
                case Constants.REQUEST_TAKE_PHOTO :
                    changeAvatarPath = mTmpFile.getAbsolutePath();
                    Glide.with(mContext)
                            .load(changeAvatarPath)
                            .into(circleHead);
                    uploadUserAvatar();
                    break;

                case Constants.REQUEST_OPEN_CAMERA :
                    changeAvatarPath = data.getStringArrayListExtra(ImagePicker
                            .EXTRA_SELECT_IMAGES).get(0) ;
                    Glide.with(mContext)
                            .load(changeAvatarPath)
                            .into(circleHead);
                    uploadUserAvatar();
                    break;
            }
        }
    }

    /**
     * 上传用户头像
     */
    private void uploadUserAvatar() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Bitmap bitmap = BitmapFactory.decodeFile(changeAvatarPath);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        String avatarString = Base64.encodeToString(bytes,Base64.CRLF);
        Map params = new HashMap();
        params.put("image",avatarString);
        getPresenter().uploadAvatar(params);
    }

    /**
     * 岗位切换监听
     * @param userJobsBean
     */
    @Override
    public void onJobChangeListener(UserJobsBean userJobsBean) {
        this.userJobsBean = userJobsBean ;
        if(menuWindow != null && menuWindow.isShowing()){
            menuWindow.dismiss();
        }
        judgeJobAvailability(userJobsBean);
    }

    /**
     * 判选择岗位是否可用
     * @param userJobsBean
     */
    private void judgeJobAvailability(UserJobsBean userJobsBean) {
        String currentPkId = PreferencesHelper.get(Constants.USER_CURRENT_PKID,"");
        if(TextUtils.isEmpty(currentPkId)){
            currentPkId = PreferencesHelper.get(Constants.USER_PKID,"");
        }
        getPresenter().judgeJobAvailability(currentPkId
                ,userJobsBean.getUser_pkid(),userJobsBean.getUser_id());
    }
}
