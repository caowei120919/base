package com.datacvg.dimp.baseandroid.widget;

import android.content.Context;
import android.widget.Button;

import com.datacvg.dimp.R;
import com.datacvg.dimp.baseandroid.utils.AndroidUtils;
import com.datacvg.dimp.baseandroid.utils.ViewUtils;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-07-28
 * @Description :
 */
public final class OKDialog extends AbstractDialog{
    public OKDialog(final Context context) {
        super(context);
        this.setOnClickPositiveButtonListener(v -> dismiss());
        final Button btnPositive = (Button) m_vAlertDialog.findViewById(R.id.btnPositive);
        btnPositive.setTextColor(AndroidUtils.getContext()
                .getResources().getColor(R.color.c_303030));
        setWindowWidth(ViewUtils.dip2px(265));
    }

    @Override
    protected int getAlertDialogViewRes() {
        return R.layout.alertdialog_onlymessage_ok;
    }
}
