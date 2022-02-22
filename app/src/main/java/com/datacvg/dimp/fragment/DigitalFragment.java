package com.datacvg.dimp.fragment;
import android.Manifest;
import android.net.Uri;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.datacvg.dimp.R;
import com.datacvg.dimp.baseandroid.retrofit.RxObserver;
import com.datacvg.dimp.baseandroid.utils.FileUtils;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.RxUtils;
import com.datacvg.dimp.baseandroid.utils.ShareContentType;
import com.datacvg.dimp.baseandroid.utils.ShareUtils;
import com.datacvg.dimp.baseandroid.utils.StatusBarUtil;
import com.datacvg.dimp.baseandroid.utils.ToastUtils;
import com.datacvg.dimp.bean.PageItemBean;
import com.datacvg.dimp.event.DigitalEditEvent;
import com.datacvg.dimp.event.PageCompleteEvent;
import com.datacvg.dimp.event.SelectPageEvent;
import com.datacvg.dimp.event.SelectParamsEvent;
import com.datacvg.dimp.event.ToAddIndexEvent;
import com.datacvg.dimp.presenter.DigitalPresenter;
import com.datacvg.dimp.view.DigitalView;
import com.datacvg.dimp.widget.DigitalTitleNavigator;
import com.enlogy.statusview.StatusRelativeLayout;
import com.tbruyelle.rxpermissions2.RxPermissions;
import net.lucode.hackware.magicindicator.FragmentContainerHelper;
import net.lucode.hackware.magicindicator.MagicIndicator;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-09-29
 * @Description : 数字神经
 */
public class DigitalFragment extends BaseFragment<DigitalView, DigitalPresenter>
        implements DigitalView, DigitalTitleNavigator.OnTabSelectedListener {
    @BindView(R.id.status_title)
    StatusRelativeLayout statusTitle ;
    @BindView(R.id.tv_manage)
    TextView tvManage ;
    @BindView(R.id.magic_title)
    MagicIndicator magicTitle ;
    @BindView(R.id.rel_share)
    RelativeLayout relShare ;

    private DigitalTitleNavigator titleNavigator ;
    private FragmentContainerHelper mTitleFragmentContainerHelper ;
    private FragmentTransaction fragmentTransaction;
    private BoardFragment boardFragment ;
    private BudgetFragment budgetFragment ;
    private PageItemBean pageItemBean ;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_digital;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void setupView(View rootView) {
        StatusBarUtil.setStatusBarColor(getActivity()
                ,mContext.getResources().getColor(R.color.c_FFFFFF));
        statusTitle.setOnItemClickListener(R.id.tv_complete,view -> {
            PLog.e("完成");
            statusTitle.showContent();
            EventBus.getDefault().post(new DigitalEditEvent(true));
        });
        initTitleMagicTitle();
    }

    /**
     * 初始化标题指示器
     */
    private void initTitleMagicTitle() {
        List<String> titles = Arrays.asList(resources.getStringArray(R.array.digital_title));
        titleNavigator = new DigitalTitleNavigator(mContext,titles,false);
        titleNavigator.setOnTabSelectedListener(this);
        magicTitle.setNavigator(titleNavigator);
        mTitleFragmentContainerHelper = new FragmentContainerHelper();
        mTitleFragmentContainerHelper.attachMagicIndicator(magicTitle);
        showFragment(0);
    }

    @Override
    protected void setupData() {

    }

    @OnClick({R.id.tv_manage,R.id.img_share,R.id.img_select})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.tv_manage :
                PLog.e("管理");
                statusTitle.showExtendContent();
                EventBus.getDefault().post(new DigitalEditEvent(false));
//                EventBus.getDefault().post(new EditEvent());
                statusTitle.setOnItemClickListener(R.id.img_addIndex,v -> {
                    EventBus.getDefault().post(new ToAddIndexEvent());
                });
                break;

            case R.id.img_share :
                new RxPermissions(getActivity())
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
                                    File file = FileUtils.screenshot(getActivity()) ;
                                    if(file != null){
                                        Uri mUri = Uri.fromFile(file);
                                        new ShareUtils
                                                .Builder(getActivity())
                                                .setContentType(ShareContentType.IMAGE)
                                                .setShareFileUri(mUri)
                                                .setTitle("DIMP")
                                                .build()
                                                .shareBySystem();
                                    }
                                }else{
                                    ToastUtils.showLongToast(resources.getString(R.string.dont_allow_permissions));
                                }
                            }
                        });
                break;

            case R.id.img_select :
                if (pageItemBean == null){
                    ToastUtils.showLongToast(resources.getString(R.string.temporarily_no_data));
                }else{
                    EventBus.getDefault().post(new SelectParamsEvent());
                }
                break;
        }
    }

    /**
     * 标题切换
     * @param position
     */
    @Override
    public void onTabSelected(int position) {
        mTitleFragmentContainerHelper.handlePageSelected(position);
        tvManage.setVisibility(position == 0 ? View.VISIBLE : View.GONE);
        showFragment(position);
    }

    private void showFragment(int position) {
        FragmentManager fragmentManager = getChildFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        hideFragment(fragmentTransaction);
        switch (position){
            case 0 :
                if(null != boardFragment){
                    fragmentTransaction.show(boardFragment);
                }else{
                    boardFragment = new BoardFragment();
                    fragmentTransaction.add(R.id.content,boardFragment);
                }
                fragmentTransaction.commitAllowingStateLoss();
                break;

            case 1 :
                budgetFragment = BudgetFragment.newInstance(pageItemBean);
                fragmentTransaction.add(R.id.content,budgetFragment);
                fragmentTransaction.commitAllowingStateLoss();
                break;
        }
    }

    private void hideFragment(FragmentTransaction fragmentTransaction) {
        if(null != boardFragment){
            fragmentTransaction.hide(boardFragment);
        }
        if(null != budgetFragment){
            fragmentTransaction.hide(budgetFragment);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(PageCompleteEvent event){
        statusTitle.showContent();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(SelectPageEvent event){
        pageItemBean = event.getPageItemBean();
    }
}
