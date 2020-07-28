package com.datacvg.sempmobile.baseandroid.widget;

import android.content.Context;
import android.widget.TextView;

import com.datacvg.sempmobile.R;
import com.datacvg.sempmobile.baseandroid.utils.ViewUtils;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-07-28
 * @Description :
 */
public class CVGOKCancelWithTitle extends OkCancelDialog{
    public CVGOKCancelWithTitle(final Context context) {
        super(context);
        setCancelable(true);
        setWindowWidth(ViewUtils.dip2px(265));
    }

    public CVGOKCancelWithTitle(final Context context, int width) {
        super(context);
        setCancelable(true);
        setWindowWidth(width);
    }

    @Override
    protected int getAlertDialogViewRes() {
        return R.layout.alertdialog_onlymessage_okcancel_withtitle;
    }

    public final void setTitle(final int resourceId) {
        setTitle(context.getString(resourceId));
    }

    public void setTitle(final String title) {
        ((TextView) m_vAlertDialog.findViewById(R.id.tvTitle)).setText(title);
    }

    public TextView getTitleView() {
        return (TextView) m_vAlertDialog.findViewById(R.id.tvTitle);
    }

    @Override
    protected int getDialogStyle() {
        return R.style.MDStyleDialogRoundBg;
    }
}
