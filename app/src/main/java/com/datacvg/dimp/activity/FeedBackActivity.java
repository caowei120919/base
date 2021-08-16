package com.datacvg.dimp.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.datacvg.dimp.R;
import com.datacvg.dimp.baseandroid.utils.StatusBarUtil;
import com.datacvg.dimp.baseandroid.utils.ToastUtils;
import com.datacvg.dimp.presenter.FeedBackPresenter;
import com.datacvg.dimp.view.FeedBackView;
import java.util.HashMap;
import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-08-16
 * @Description :
 */
public class FeedBackActivity extends BaseActivity<FeedBackView, FeedBackPresenter>
        implements FeedBackView {
    @BindView(R.id.tv_title)
    TextView tvTitle ;
    @BindView(R.id.edit_feedback)
    EditText editFeedback ;

    private String feedbackMessage ;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void setupView() {
        StatusBarUtil.setStatusBarColor(mContext
                ,mContext.getResources().getColor(R.color.c_FFFFFF));
        tvTitle.setText(resources.getString(R.string.feedback));
    }

    @Override
    protected void setupData(Bundle savedInstanceState) {

    }

    /**
     * 企业码输入监听
     */
    @OnTextChanged(value = R.id.edit_feedback,callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onCodeTextChange(Editable editable){
        feedbackMessage = editable.toString().trim();
    }

    @OnClick({R.id.img_left,R.id.btn_submit})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.img_left :
                    finish();
                break;

            case R.id.btn_submit :
                if(TextUtils.isEmpty(feedbackMessage)){
                    ToastUtils.showLongToast(resources.getString(R.string.please_input_feedback));
                    return;
                }
                HashMap<String,String> params = new HashMap<>();
                params.put("feedback_content",feedbackMessage);
                getPresenter().submitFeedBack(params);
                break;
        }
    }

    @Override
    public void submitFeedBackSuccess() {
        editFeedback.setText("");
        feedbackMessage = "" ;
    }
}
