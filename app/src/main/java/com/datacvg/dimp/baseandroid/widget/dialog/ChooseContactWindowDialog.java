package com.datacvg.dimp.baseandroid.widget.dialog;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import androidx.annotation.StyleRes;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.datacvg.dimp.R;
import com.datacvg.dimp.adapter.ChooseContactAdapter;
import com.datacvg.dimp.baseandroid.config.Constants;
import com.datacvg.dimp.greendao.bean.DepartmentBean;
import com.datacvg.dimp.greendao.controller.DbDepartmentController;
import com.datacvg.dimp.widget.DividerItemDecoration;
import java.util.ArrayList;
import java.util.List;


/**
 * FileName: BaseWindowDialog
 * Author: 曹伟
 * Date: 2019/11/14 11:47
 * Description:
 */

public  class ChooseContactWindowDialog {

    private TextView tvTypeName ;
    private TextView tvHeadTypeName ;
    private RecyclerView recycleChoose ;
    private ChooseContactAdapter adapter ;
    private Context mContext ;

    private Dialog mDialog;
    private Window mDialogWindow;
    private DialogViewHolder dialogVh;
    private View mRootView;
    private LinearLayoutManager layoutManager;
    private int chooseType = Constants.CHOOSE_TYPE_HEAD ;

    public void setChooseType(int chooseType) {
        if(this.chooseType == chooseType){
            return;
        }
        this.chooseType = chooseType ;
        tvTypeName.setText(chooseType == Constants.CHOOSE_TYPE_HEAD ? mContext.getResources().getString(R.string.head)
                : mContext.getResources().getString(R.string.assistant));
        tvHeadTypeName.setText(mContext.getResources().getString(R.string.organization_dimension));
        adapter.setChooseType(chooseType);
    }

    public ChooseContactWindowDialog(Context context, int chooseType) {
        this.chooseType = chooseType ;
        mContext = context ;
        dialogVh = DialogViewHolder.get(context,R.layout.dialog_choose_department_contact);
        mRootView = dialogVh.getConvertView();
        mDialog = new Dialog(context, R.style.dialog);

        mDialog.setContentView(mRootView);
        mDialogWindow = mDialog.getWindow();
        if(mDialogWindow != null){
            mDialogWindow.setBackgroundDrawableResource(R.drawable.shape_ffffff);
        }
        convert(dialogVh);
    }

    private void convert(DialogViewHolder dialogVh) {
        tvTypeName = dialogVh.getConvertView().findViewById(R.id.tv_typeName);
        tvTypeName.setText(chooseType == Constants.CHOOSE_TYPE_HEAD ? mContext.getResources().getString(R.string.head)
                : mContext.getResources().getString(R.string.assistant));
        tvHeadTypeName = dialogVh.getConvertView().findViewById(R.id.tv_headTypeName);
        tvHeadTypeName.setText(mContext.getResources().getString(R.string.organization_dimension));
        recycleChoose = dialogVh.getConvertView().findViewById(R.id.recycle_choose);

        layoutManager = new LinearLayoutManager(mContext);
        adapter = new ChooseContactAdapter(mContext,chooseType);
        recycleChoose.setLayoutManager(layoutManager);
        recycleChoose.addItemDecoration(new DividerItemDecoration(mContext
                , DividerItemDecoration.VERTICAL_LIST));
        recycleChoose.setAdapter(adapter);
    }

    /**
     * 显示dialog
     */
    public ChooseContactWindowDialog showDialog() {
        if (mDialog != null && !mDialog.isShowing()) {
            adapter.notifyDataSetChanged();
            mDialog.show();
        }
        return this;
    }

    /**
     *
     * @param light 弹出时背景亮度 值为0.0~1.0    1.0表示全黑  0.0表示全白
     * @return
     */
    public ChooseContactWindowDialog backgroundLight(double light) {
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
    public ChooseContactWindowDialog fromBottomToMiddle() {
        mDialogWindow.setWindowAnimations(R.style.window_bottom_in_bottom_out);
        return this;
    }

    /**
     * 从底部弹出
     */
    public ChooseContactWindowDialog fromBottom() {
        fromBottomToMiddle();
        mDialogWindow.setGravity(Gravity.CENTER | Gravity.BOTTOM);
        return this;
    }

    /**
     * 从左边一直弹到中间退出也是到左边
     */
    public ChooseContactWindowDialog fromLeftToMiddle() {
        mDialogWindow.setWindowAnimations(R.style.window_left_in_left_out);
        mDialogWindow.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        mDialogWindow.setGravity(Gravity.CENTER | Gravity.LEFT);
        return this;
    }

    /**
     *  从右边一直弹到中间退出也是到右边
     * @return
     */
    public ChooseContactWindowDialog fromRightToMiddle() {
        mDialogWindow.setWindowAnimations(R.style.window_right_in_right_out);
        mDialogWindow.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        mDialogWindow.setGravity(Gravity.RIGHT);
        return this;
    }

    /**
     * 从顶部弹出 从顶部弹出  保持在顶部
     * @return
     */
    public ChooseContactWindowDialog fromTop() {
        fromTopToMiddle();
        mDialogWindow.setGravity(Gravity.CENTER | Gravity.TOP);
        return this;
    }

    /**
     * 从顶部谈到中间  从顶部弹出  保持在中间
     * @return
     */
    public ChooseContactWindowDialog fromTopToMiddle() {
        mDialogWindow.setWindowAnimations(R.style.window_top_in_top_out);
        mDialogWindow.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        return this;
    }
    /**
     *
     * @param style 显示一个Dialog自定义一个弹出方式  具体怎么写 可以模仿上面的
     * @return
     */
    public ChooseContactWindowDialog showDialog(@StyleRes int style) {
        mDialogWindow.setWindowAnimations(style);
        mDialog.show();
        return this;
    }

    /**
     *
     * @param isAnimation 如果为true 就显示默认的一个缩放动画
     * @return
     */
    public ChooseContactWindowDialog showDialog(boolean isAnimation) {
        mDialogWindow.setWindowAnimations(R.style.dialog_scale_animstyle);
        mDialog.show();
        return this;
    }

    /**
     * 全屏显示
     */
    public ChooseContactWindowDialog fullScreen() {
        WindowManager.LayoutParams wl = mDialogWindow.getAttributes();
        wl.height = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        mDialog.onWindowAttributesChanged(wl);
        return this;
    }


    public ChooseContactWindowDialog setOnKeyListener(DialogInterface.OnKeyListener onKeyListener){
        mDialog.setOnKeyListener(onKeyListener);
        return this;
    }
    /**
     * 全屏宽度
     */
    public ChooseContactWindowDialog fullWidth() {
        WindowManager.LayoutParams wl = mDialogWindow.getAttributes();
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        mDialog.onWindowAttributesChanged(wl);
        return this;
    }

    /**
     * 全屏高度
     */
    public ChooseContactWindowDialog fullHeight() {
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
    public ChooseContactWindowDialog setWidthAndHeight(int width, int height) {
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
    public ChooseContactWindowDialog setDialogDismissListener(DialogInterface.OnDismissListener listener) {
        mDialog.setOnDismissListener(listener);
        return this;
    }

    /**
     * 设置监听
     */
    public ChooseContactWindowDialog setOnCancelListener(DialogInterface.OnCancelListener listener) {
        mDialog.setOnCancelListener(listener);
        return this;
    }

    /**
     * 设置是否能取消
     */
    public ChooseContactWindowDialog setCancelAble(boolean cancel) {
        mDialog.setCancelable(cancel);
        return this;
    }



    /**
     * 设置触摸其他地方是否能取消
     */
    public ChooseContactWindowDialog setCanceledOnTouchOutside(boolean cancel) {
        mDialog.setCanceledOnTouchOutside(cancel);
        return this;
    }
}
