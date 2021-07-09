package com.datacvg.dimp.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.datacvg.dimp.R;
import com.datacvg.dimp.adapter.CommentListAdapter;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.bean.CommentBean;
import com.datacvg.dimp.bean.CommentListBean;
import com.datacvg.dimp.bean.TableBean;
import com.datacvg.dimp.event.SelectChooseContactEvent;
import com.datacvg.dimp.presenter.TableCommentPresenter;
import com.datacvg.dimp.view.TableCommentView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2021-01-05
 * @Description : 报表评论
 */
public class TableCommentActivity extends BaseActivity<TableCommentView, TableCommentPresenter>
        implements TableCommentView {
    @BindView(R.id.recycle_comment)
    RecyclerView recycleComment ;
    @BindView(R.id.ed_comment)
    EditText edComment ;


    private TableBean tableBean ;
    private String params = "{}" ;
    private List<CommentBean> commentBeans = new ArrayList<>() ;
    private CommentListAdapter adapter ;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_table_comment;
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected void setupView() {
        adapter = new CommentListAdapter(mContext,commentBeans);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        recycleComment.setLayoutManager(manager);
        recycleComment.setAdapter(adapter);
    }

    @Override
    protected void setupData(Bundle savedInstanceState) {
        tableBean = (TableBean) getIntent().getSerializableExtra(Constants.EXTRA_DATA_FOR_BEAN);
        if(tableBean == null){
            finish();
            return;
        }
        getPresenter().getTableComment(tableBean.getRes_id(), Uri.encode(params));
    }

    @OnClick({R.id.img_closeComment,R.id.img_submit,R.id.img_addPicture,R.id.img_at})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.img_closeComment:
                    finish();
                break;

            case R.id.img_submit :
                PLog.e("发送");
                break;

            case R.id.img_addPicture :
                PLog.e("添加图片");
                break;

            case R.id.img_at :
                PLog.e("@功能");
                startActivity(new Intent(mContext, ContactActivity.class));
                break;
        }
    }

    @OnTextChanged(value = R.id.ed_comment,callback = OnTextChanged.Callback.AFTER_TEXT_CHANGED)
    public void onUserNameTextChange(Editable editable){

    }

    @Override
    public void getTableCommentSuccess(CommentListBean data) {
        if (data.getCommentInfoList().isEmpty()){
            return;
        }
        commentBeans.clear();
        for (CommentBean commentBean : data.getCommentInfoList()){
            if(commentBean.getParent_id().equals("0")){
                commentBean.setLevel(0);
                commentBeans.add(commentBean);
            }
        }

        for (CommentBean commentBean : commentBeans){
            for (CommentBean bean : data.getCommentInfoList()){
                if(bean.getParent_id().equals(commentBean.getComment_pkid())){
                    commentBean.setLevel(1);
                    commentBean.getChildComment().add(bean);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(SelectChooseContactEvent event){

    }
}
