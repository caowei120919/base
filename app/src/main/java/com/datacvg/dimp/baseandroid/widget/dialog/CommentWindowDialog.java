package com.datacvg.dimp.baseandroid.widget.dialog;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.datacvg.dimp.R;
import com.datacvg.dimp.adapter.ChooseIndexAdapter;
import com.datacvg.dimp.adapter.CommentListAdapter;
import com.datacvg.dimp.adapter.CommentPictureAdapter;
import com.datacvg.dimp.bean.CommentBean;
import com.datacvg.dimp.event.DeleteCommentEvent;
import com.datacvg.dimp.widget.DividerItemDecoration;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;


/**
 * FileName: BaseWindowDialog
 * Author: 曹伟
 * Date: 2019/11/14 11:47
 * Description:
 */

public class CommentWindowDialog implements TextWatcher {
    private Dialog mDialog;
    private Window mDialogWindow;
    private DialogViewHolder dialogVh;
    private View mRootView;
    private List<String> imagePaths = new ArrayList<>() ;
    private Context mContext ;
    private Dialog dialogN ;
    private CommentViewClick commentViewClick ;
    private GridLayoutManager layoutManager;
    private CommentPictureAdapter adapter ;
    private CommentListAdapter commentListAdapter ;
    private String mComments = "" ;
    private EditText edComment ;
    private List<CommentBean> mCommentBeans = new ArrayList<>() ;
    private RecyclerView recycleComment ;

    public CommentWindowDialog(Context context, List<String> imagePaths
            ,CommentViewClick commentViewClick,List<CommentBean> mCommentBeans) {
        this.mCommentBeans = mCommentBeans ;
        this.imagePaths = imagePaths ;
        this.commentViewClick = commentViewClick ;
        mContext = context ;
        dialogVh = DialogViewHolder.get(context, R.layout.dialog_comment_display);
        mRootView = dialogVh.getConvertView();
        mDialog = new Dialog(context, R.style.dialog);

        mDialog.setContentView(mRootView);
        mDialogWindow = mDialog.getWindow();
        if(mDialogWindow != null){
            mDialogWindow.setBackgroundDrawableResource(R.drawable.shape_ffffff);
        }
        convert(dialogVh);
    }
    /**B
     * 把弹出框view holder传出去
     */
    private void convert(DialogViewHolder holder) {
        holder.getConvertView().findViewById(R.id.img_closeComment).setOnClickListener(view -> {
            imagePaths.clear();
            EventBus.getDefault().post(new DeleteCommentEvent(true));
            mDialog.dismiss();
        });
        holder.getConvertView().findViewById(R.id.img_addPicture).setOnClickListener(view -> {
            showPhotoOrCamera() ;
        });
        holder.getConvertView().findViewById(R.id.img_send).setOnClickListener(view -> {
            commentViewClick.sendComments(mComments,imagePaths);
        });
        edComment = holder.getConvertView().findViewById(R.id.ed_comment);
        edComment.addTextChangedListener(this);
        RecyclerView recyclePicture = holder.getConvertView().findViewById(R.id.recycle_picture);

        layoutManager = new GridLayoutManager(mContext,3);
        adapter = new CommentPictureAdapter(mContext,imagePaths);
        recyclePicture.setLayoutManager(layoutManager);
        recyclePicture.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view
                    , @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = (int) mContext.getResources().getDimension(R.dimen.W10);
            }
        });
        recyclePicture.setAdapter(adapter);

        RecyclerView recycleComment = holder.getConvertView().findViewById(R.id.recycle_comment);
        commentListAdapter = new CommentListAdapter(mContext,mCommentBeans);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        recycleComment.setLayoutManager(linearLayoutManager);
        recycleComment.setAdapter(commentListAdapter);
    }

    private void showPhotoOrCamera() {
        if(dialogN != null){
            dialogN.show();
        }else{
            //实例化对象
            dialogN = new AlertDialog.Builder(mContext).create();
            //显示dialog
            dialogN.show();
            dialogN.setCanceledOnTouchOutside(false);
            //让dialog显示到屏幕的中间
            Window window = dialogN.getWindow();
            window.setGravity(Gravity.BOTTOM);
            window.setDimAmount(0.2f);
            //把要显示的布局加到dialog中
            window.setContentView(R.layout.select_dialog);
            window.setBackgroundDrawableResource(android.R.color.transparent);
            window.findViewById(R.id.tv_first).setOnClickListener(view ->  {
                commentViewClick.takeCamera();
                if (dialogN != null){
                    dialogN.cancel();
                }
            });
            window.findViewById(R.id.tv_second).setOnClickListener(view ->  {
                commentViewClick.openAlbum();
                if (dialogN != null){
                    dialogN.cancel();
                }
            });
            window.findViewById(R.id.tv_cancel).setOnClickListener(view ->  {
                if (dialogN != null){
                    dialogN.cancel();
                }
            });
        }
    }

    /**
     * 评论成功.清除所有数据.
     */
    public void submitSuccess() {
        imagePaths.clear();
        adapter.notifyDataSetChanged();
        edComment.removeTextChangedListener(this);
        edComment.setText("");
        edComment.clearFocus();
        edComment.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        mComments = editable.toString().trim();
    }

    /**
     * 刷新报表评论
     * @param commentBeans
     */
    public void refreshComment(List<CommentBean> commentBeans) {
        mCommentBeans.clear();
        mCommentBeans.addAll(commentBeans);
        commentListAdapter.setCommentBeans(mCommentBeans);
    }

    public interface CommentViewClick{
        void takeCamera() ;
        void openAlbum() ;
        void sendComments(String mComments, List<String> imagePaths);
    }

    public static AlertDialog.Builder createNormalDialogBuilder(Context context, String title, String message) {
        return new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message);
    }

    public View getChildView(int childId){
        return dialogVh.getConvertView().findViewById(childId);
    }

    /**
     * 显示dialog
     */
    public CommentWindowDialog showDialog() {
        if (mDialog != null && !mDialog.isShowing()) {
            mDialog.show();
        }
        return this;
    }

    /**
     *
     * @param light 弹出时背景亮度 值为0.0~1.0    1.0表示全黑  0.0表示全白
     * @return
     */
    public CommentWindowDialog backgroundLight(double light) {
        if (light < 0.0 || light > 1.0){
            return this;
        }
        WindowManager.LayoutParams lp = mDialogWindow.getAttributes();
        lp.dimAmount = (float) light;
        mDialogWindow.setAttributes(lp);
        return this;
    }

    /**
     * 从底部一直弹到中间
     */
    @SuppressLint("NewApi")
    public CommentWindowDialog fromBottomToMiddle() {
        mDialogWindow.setWindowAnimations(R.style.window_bottom_in_bottom_out);
        return this;
    }

    /**
     * 从底部弹出
     */
    public CommentWindowDialog fromBottom() {
        fromBottomToMiddle();
        mDialogWindow.setGravity(Gravity.CENTER | Gravity.BOTTOM);
        return this;
    }

    /**
     * 从左边一直弹到中间退出也是到左边
     */
    public CommentWindowDialog fromLeftToMiddle() {
        mDialogWindow.setWindowAnimations(R.style.window_left_in_left_out);
        mDialogWindow.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        mDialogWindow.setGravity(Gravity.CENTER | Gravity.LEFT);
        return this;
    }

    /**
     *  从右边一直弹到中间退出也是到右边
     * @return
     */
    public CommentWindowDialog fromRightToMiddle() {
        mDialogWindow.setWindowAnimations(R.style.window_right_in_right_out);
        mDialogWindow.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        mDialogWindow.setGravity(Gravity.RIGHT);
//
        return this;
    }

    /**
     * 从顶部弹出 从顶部弹出  保持在顶部
     * @return
     */
    public CommentWindowDialog fromTop() {
        fromTopToMiddle();
        mDialogWindow.setGravity(Gravity.CENTER | Gravity.TOP);
        return this;
    }

    /**
     * 从顶部谈到中间  从顶部弹出  保持在中间
     * @return
     */
    public CommentWindowDialog fromTopToMiddle() {
        mDialogWindow.setWindowAnimations(R.style.window_top_in_top_out);
        mDialogWindow.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        return this;
    }
    /**
     *
     * @param style 显示一个Dialog自定义一个弹出方式  具体怎么写 可以模仿上面的
     * @return
     */
    public CommentWindowDialog showDialog(@StyleRes int style) {
        mDialogWindow.setWindowAnimations(style);
        mDialog.show();
        return this;
    }

    /**
     *
     * @param isAnimation 如果为true 就显示默认的一个缩放动画
     * @return
     */
    public CommentWindowDialog showDialog(boolean isAnimation) {
        mDialogWindow.setWindowAnimations(R.style.dialog_scale_animstyle);
        mDialog.show();
        return this;
    }

    /**
     * 全屏显示
     */
    public CommentWindowDialog fullScreen() {
        WindowManager.LayoutParams wl = mDialogWindow.getAttributes();
        wl.height = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        mDialog.onWindowAttributesChanged(wl);
        return this;
    }


    public CommentWindowDialog setOnKeyListener(DialogInterface.OnKeyListener onKeyListener){
        mDialog.setOnKeyListener(onKeyListener);
        return this;
    }
    /**
     * 全屏宽度
     */
    public CommentWindowDialog fullWidth() {
        WindowManager.LayoutParams wl = mDialogWindow.getAttributes();
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        mDialog.onWindowAttributesChanged(wl);
        return this;
    }

    /**
     * 全屏高度
     */
    public CommentWindowDialog fullHeight() {
        WindowManager.LayoutParams wl = mDialogWindow.getAttributes();
        wl.height = ViewGroup.LayoutParams.MATCH_PARENT;
        mDialog.onWindowAttributesChanged(wl);
        return this;
    }

    /**
     *
     * @param width  自定义的宽度
     * @param height  自定义的高度
     * @return
     */
    public CommentWindowDialog setWidthAndHeight(int width, int height) {
        WindowManager.LayoutParams wl = mDialogWindow.getAttributes();
        wl.width = width;
        wl.height = height;
        mDialog.onWindowAttributesChanged(wl);
        return this;
    }

    /**
     * cancel dialog
     */
    public void cancelDialog() {
        if (mDialog != null && mDialog.isShowing()){
            dismiss();
        }
    }

    /**
     * cancel dialog
     */
    public void dismiss() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }
    /**
     * 设置监听
     */
    public CommentWindowDialog setDialogDismissListener(DialogInterface.OnDismissListener listener) {
        mDialog.setOnDismissListener(listener);
        return this;
    }

    /**
     * 设置监听
     */
    public CommentWindowDialog setOnCancelListener(DialogInterface.OnCancelListener listener) {
        mDialog.setOnCancelListener(listener);
        return this;
    }

    /**
     * 设置是否能取消
     */
    public CommentWindowDialog setCancelAble(boolean cancel) {
        mDialog.setCancelable(cancel);
        return this;
    }



    /**
     * 设置触摸其他地方是否能取消
     */
    public CommentWindowDialog setCanceledOnTouchOutside(boolean cancel) {
        mDialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    public void setPicturePath(List<String> imagePaths) {
        adapter.notifyDataSetChanged();
    }
}
