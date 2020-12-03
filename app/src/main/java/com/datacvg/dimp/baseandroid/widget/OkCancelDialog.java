package com.datacvg.dimp.baseandroid.widget;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.StringRes;

import com.datacvg.dimp.R;
import com.datacvg.dimp.baseandroid.utils.AndroidUtils;
import com.datacvg.dimp.baseandroid.utils.PLog;
import com.datacvg.dimp.baseandroid.utils.ViewUtils;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-07-28
 * @Description :
 */
public class OkCancelDialog extends AbstractDialog{
    private View m_vExtraLayout = null;
    private TextView btnNegative;
    private TextView btnPositive;
    public boolean isAddExtraView = false;

    public OkCancelDialog(final Context content) {
        super(content);

        btnNegative = (TextView) m_vAlertDialog.findViewById(R.id.btnNegative);
        btnPositive = (TextView) m_vAlertDialog.findViewById(R.id.btnPositive);
        try {
            btnPositive.setTextColor(AndroidUtils.getContext()
                    .getResources().getColor(R.color.c_343434));
            btnNegative.setTextColor(AndroidUtils.getContext()
                    .getResources().getColor(R.color.c_343434));
        } catch (Exception e) {
            PLog.e(e.getMessage());
        }
        setWindowWidth(ViewUtils.dip2px(265));
    }

    @Override
    public final void dismiss() {
        super.dismiss();

        if (m_vExtraLayout != null) {
            LinearLayout llMessage = (LinearLayout) m_vAlertDialog.findViewById(R.id.llMessage);
            llMessage.removeView(m_vExtraLayout);
            m_vExtraLayout = null;
        }
        if (context != null) {
            context = null;
        }
    }

    @Override
    protected int getAlertDialogViewRes() {
        return R.layout.alertdialog_onlymessage_okcancel;
    }

    public final void addExtraLayout(View vExtra) {
        LinearLayout llMessage = (LinearLayout) m_vAlertDialog.findViewById(R.id.llMessage);
        if (isAddExtraView) {
            setTitleMarginBottom();
        }
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, llMessage
                .getResources().getDimensionPixelSize(R.dimen.H100));
        lp.setMargins(0,
                llMessage.getResources().getDimensionPixelSize(R.dimen.H10), 0, 0);
        llMessage.addView(vExtra, lp);
        m_vExtraLayout = vExtra;
    }

    public void setTitleMarginBottom() {
        TextView title = (TextView) m_vAlertDialog.findViewById(R.id.tvTitle);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        title.setSingleLine(false);
        params.bottomMargin = ViewUtils.dip2px(5);
        params.topMargin = ViewUtils.dip2px(25);
        title.setLayoutParams(params);
    }

    public final TextView getMessageView() {
        return (TextView) m_vAlertDialog.findViewById(R.id.tvMessage);
    }

    public final TextView getNegativeButton() {
        return btnNegative;
    }

    public void setLeftButtonTextAndOnClick(String msg, View.OnClickListener onClickListener) {
        getNegativeButton().setText(msg);
        setOnClickListenerNegativeBtn(onClickListener);
    }

    public void setRightButtonTextAndOnClick(String msg, View.OnClickListener onClickListener) {
        getPositiveButton().setText(msg);
        setOnClickPositiveButtonListener(onClickListener);
    }

    public void setLeftButtonTextAndOnClick(@StringRes int msg, View.OnClickListener onClickListener) {
        getNegativeButton().setText(msg);
        setOnClickListenerNegativeBtn(onClickListener);
    }

    public void setRightButtonTextAndOnClick(@StringRes int msg, View.OnClickListener onClickListener) {
        getPositiveButton().setText(msg);
        setOnClickPositiveButtonListener(onClickListener);
    }

    public final TextView getPositiveButton() {
        return btnPositive;
    }

    public void setOnClickListenerNegativeBtn(final View.OnClickListener l) {
        TextView btnNegative = (TextView) m_vAlertDialog.findViewById(R.id.btnNegative);

        if (btnNegative != null) {
            btnNegative.setOnClickListener(v -> {
                dismiss();
                if (l != null) {
                    l.onClick(v);
                }
            });
        }
    }
}
