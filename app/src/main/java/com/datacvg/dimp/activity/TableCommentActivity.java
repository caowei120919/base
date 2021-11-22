package com.datacvg.dimp.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.datacvg.dimp.R;
import com.datacvg.dimp.adapter.CommentListAdapter;
import com.datacvg.dimp.adapter.PhotoAdapter;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.baseandroid.utils.GlideLoader;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.ToastUtils;
import com.datacvg.dimp.bean.CommentBean;
import com.datacvg.dimp.bean.CommentListBean;
import com.datacvg.dimp.bean.TableBean;
import com.datacvg.dimp.event.DeletePhotoEvent;
import com.datacvg.dimp.event.SelectChooseContactEvent;
import com.datacvg.dimp.presenter.TableCommentPresenter;
import com.datacvg.dimp.view.TableCommentView;
import com.lcw.library.imagepicker.ImagePicker;

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
    @BindView(R.id.grid_photo)
    GridView gridPhoto ;


    private TableBean tableBean ;
    private String params = "{}" ;
    private List<CommentBean> commentBeans = new ArrayList<>() ;
    private CommentListAdapter adapter ;
    private PhotoAdapter photoAdapter ;
    protected final String AT = "@" ;
    private List<String> photoPaths = new ArrayList<>() ;

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
        photoAdapter = new PhotoAdapter(mContext,photoPaths);
        gridPhoto.setAdapter(photoAdapter);
        getPresenter().getTableComment(tableBean.getRes_id(), Uri.encode(params));
    }

    @OnClick({R.id.img_closeComment,R.id.img_submit,R.id.img_addPicture,R.id.img_at})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.img_closeComment:
                    finish();
                break;

            case R.id.img_submit :
                if(TextUtils.isEmpty(edComment.getText().toString())){
                    ToastUtils.showLongToast(resources.getString(R.string.comments_cannot_be_empty));
                    return;
                }
                break;

            case R.id.img_addPicture :
                PLog.e("添加图片");

                if(photoPaths.size() >= 5){
                    ToastUtils.showLongToast(resources.getString(R.string.upload_up_to_six_images));
                }else{
                    ImagePicker.getInstance()
                            .setTitle(mContext.getResources().getString(R.string.select_picture))
                            .setImageLoader(new GlideLoader())
                            .showCamera(false)
                            .showImage(true)
                            .showVideo(false)
                            .setSingleType(true)
                            .setMaxCount(6 - photoPaths.size())
                            .start(mContext, Constants.REQUEST_OPEN_CAMERA);
                }
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

    @OnTextChanged(value = R.id.ed_comment,callback = OnTextChanged.Callback.TEXT_CHANGED)
    public void onUserNameTextChange(CharSequence s ,int start,int before,int count){
        if(start==s.length()-1 && s.toString().endsWith(AT)){
//            startActivity(new Intent(mContext, ContactActivity.class));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(DeletePhotoEvent event){
        if(event!= null){
            photoPaths.remove(event.getPosition());
        }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constants.REQUEST_OPEN_CAMERA:
                    photoPaths.addAll(data
                            .getStringArrayListExtra(ImagePicker.EXTRA_SELECT_IMAGES));
                    photoAdapter.notifyDataSetChanged();
                    break;
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(SelectChooseContactEvent event){

    }
}
