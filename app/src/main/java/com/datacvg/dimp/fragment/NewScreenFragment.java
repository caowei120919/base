package com.datacvg.dimp.fragment;

import android.view.View;
import android.widget.RelativeLayout;
import com.datacvg.dimp.R;
import com.datacvg.dimp.baseandroid.utils.StatusBarUtil;
import com.datacvg.dimp.presenter.NewScreenPresenter;
import com.datacvg.dimp.view.NewScreenView;
import com.enlogy.statusview.StatusRelativeLayout;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-10-21
 * @Description : 新屏
 */
public class NewScreenFragment extends BaseFragment<NewScreenView, NewScreenPresenter>
        implements NewScreenView {
    @BindView(R.id.rel_screenSizeOfNine)
    RelativeLayout relScreenSizeOfNine ;
    @BindView(R.id.rel_screenSizeOfTen)
    RelativeLayout relScreenSizeOfTen ;
    @BindView(R.id.rel_screenSizeOfOne)
    RelativeLayout relScreenSizeOfOne ;
    @BindView(R.id.rel_screenSizeOfCustom)
    RelativeLayout relScreenSizeOfCustom ;
    @BindView(R.id.status_screen)
    StatusRelativeLayout statusScreen ;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_new_screen;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected void setupView(View rootView) {
        StatusBarUtil.setStatusBarColor(getActivity()
                ,mContext.getResources().getColor(R.color.c_FFFFFF));
        relScreenSizeOfNine.setSelected(true);
        relScreenSizeOfTen.setSelected(false);
        relScreenSizeOfOne.setSelected(false);
        relScreenSizeOfCustom.setSelected(false);
    }

    @Override
    protected void setupData() {

    }

    @OnClick({R.id.rel_screenSizeOfNine,R.id.rel_screenSizeOfTen,
            R.id.rel_screenSizeOfOne,R.id.rel_screenSizeOfCustom})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rel_screenSizeOfNine :
                relScreenSizeOfNine.setSelected(true);
                relScreenSizeOfTen.setSelected(false);
                relScreenSizeOfOne.setSelected(false);
                relScreenSizeOfCustom.setSelected(false);
                statusScreen.showContent();
                break;

            case R.id.rel_screenSizeOfTen :
                relScreenSizeOfNine.setSelected(false);
                relScreenSizeOfTen.setSelected(true);
                relScreenSizeOfOne.setSelected(false);
                relScreenSizeOfCustom.setSelected(false);
                statusScreen.showContent();
                break;

            case R.id.rel_screenSizeOfOne :
                relScreenSizeOfNine.setSelected(false);
                relScreenSizeOfTen.setSelected(false);
                relScreenSizeOfOne.setSelected(true);
                relScreenSizeOfCustom.setSelected(false);
                statusScreen.showEmptyContent();
                break;

            case R.id.rel_screenSizeOfCustom :
                relScreenSizeOfNine.setSelected(false);
                relScreenSizeOfTen.setSelected(false);
                relScreenSizeOfOne.setSelected(false);
                relScreenSizeOfCustom.setSelected(true);
                statusScreen.showExtendContent();
                break;
        }
    }
}
