package com.datacvg.dimp.fragment;
import android.Manifest;
import android.view.View;
import android.widget.TextView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.datacvg.dimp.R;
import com.datacvg.dimp.baseandroid.retrofit.RxObserver;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.RxUtils;
import com.datacvg.dimp.baseandroid.utils.ShareUtils;
import com.datacvg.dimp.baseandroid.utils.StatusBarUtil;
import com.datacvg.dimp.event.AddIndexEvent;
import com.datacvg.dimp.event.EditEvent;
import com.datacvg.dimp.event.ToAddIndexEvent;
import com.datacvg.dimp.presenter.DigitalPresenter;
import com.datacvg.dimp.view.DigitalView;
import com.datacvg.dimp.widget.TitleNavigator;
import com.enlogy.statusview.StatusRelativeLayout;
import com.tbruyelle.rxpermissions2.RxPermissions;
import net.lucode.hackware.magicindicator.FragmentContainerHelper;
import net.lucode.hackware.magicindicator.MagicIndicator;

import org.greenrobot.eventbus.EventBus;

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
        implements DigitalView, TitleNavigator.OnTabSelectedListener {
    @BindView(R.id.status_title)
    StatusRelativeLayout statusTitle ;
    @BindView(R.id.tv_manage)
    TextView tvManage ;
    @BindView(R.id.magic_title)
    MagicIndicator magicTitle ;

    private TitleNavigator titleNavigator ;
    private FragmentContainerHelper mTitleFragmentContainerHelper ;
    private FragmentTransaction fragmentTransaction;
    private BoardFragment boardFragment ;
    private BudgetFragment budgetFragment ;

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
        });
        statusTitle.setOnItemClickListener(R.id.img_addIndex,view -> {
            PLog.e("添加指标");
        });
        initTitleMagicTitle();
    }

    /**
     * 初始化标题指示器
     */
    private void initTitleMagicTitle() {
        List<String> titles = Arrays.asList(resources.getStringArray(R.array.digital_title));
        titleNavigator = new TitleNavigator(mContext,titles);
        titleNavigator.setOnTabSelectedListener(this);
        magicTitle.setNavigator(titleNavigator);
        mTitleFragmentContainerHelper = new FragmentContainerHelper() ;
        mTitleFragmentContainerHelper.attachMagicIndicator(magicTitle);
        showFragment(0);
    }

    @Override
    protected void setupData() {

    }

    @OnClick({R.id.tv_manage,R.id.img_share})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.tv_manage :
                PLog.e("管理");
                EventBus.getDefault().post(new EditEvent());
                statusTitle.showExtendContent();
                statusTitle.setOnItemClickListener(R.id.img_addIndex,view1 -> {
                    PLog.e("添加选择指标");
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
                                PLog.e("分享");
                            }
                        });
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
                if(null != budgetFragment){
                    fragmentTransaction.show(budgetFragment);
                }else{
                    budgetFragment = new BudgetFragment();
                    fragmentTransaction.add(R.id.content,budgetFragment);
                }
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
}
