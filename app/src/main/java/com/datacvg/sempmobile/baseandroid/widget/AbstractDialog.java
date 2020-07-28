package com.datacvg.sempmobile.baseandroid.widget;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.StringRes;

import com.datacvg.sempmobile.R;
import com.datacvg.sempmobile.baseandroid.utils.AndroidUtils;
import com.datacvg.sempmobile.baseandroid.utils.PLog;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-07-28
 * @Description :
 */
public abstract class AbstractDialog {
    protected Context context;
    protected CVGBaseAlertDialog m_dlg = null;
    protected View m_vAlertDialog = null;
    private int width = -1;
    private int height = -1;

    protected AbstractDialog(final Context context) {
        this.context = context;
        create();
    }

    public void dismiss() {
        if (m_dlg != null) {
            try {
                m_dlg.dismiss();
            } catch (Exception ignored) {
            }
        }
        m_dlg = null;
    }

    public final TextView getContentTextView() {
        return (TextView) m_vAlertDialog.findViewById(R.id.tvMessage);
    }

    public final void setCancelable(final boolean cancelable) {
        if (m_dlg == null) {
            return;
        }
        m_dlg.setCancelable(cancelable);
    }

    public final void setCanceledOnTouchOutside(final boolean cancelable) {
        if (m_dlg == null) {
            return;
        }
        m_dlg.setCanceledOnTouchOutside(cancelable);
    }

    public final void setMessage(final String message) {
        TextView tvMessage = (TextView) m_vAlertDialog.findViewById(R.id.tvMessage);
        tvMessage.setText(message);
    }

    public final void setMessage(@StringRes int message) {
        TextView messageContent = (TextView) m_vAlertDialog.findViewById(R.id.tvMessage);
        messageContent.setText(message);
    }

    public final void setMessageRes(int nMessageResId) {
        TextView tvMessage = (TextView) m_vAlertDialog.findViewById(R.id.tvMessage);
        tvMessage.setText(nMessageResId);
    }

    public TextView getMessageText() {
        return (TextView) m_vAlertDialog.findViewById(R.id.tvMessage);
    }

    public final void setOnClickPositiveButtonListener(final View.OnClickListener l) {
        View btnPositive = m_vAlertDialog.findViewById(R.id.btnPositive);
        if (btnPositive != null) {
            ((TextView) btnPositive).setTextColor(AndroidUtils.getContext()
                    .getResources().getColor(R.color.c_303030));
        }
        if (btnPositive != null) {
            btnPositive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (l != null) {
                        l.onClick(v);
                    }
                    dismiss();
                }
            });
        }
    }

    public final void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        if (m_dlg == null) {
            return;
        }
        m_dlg.setOnDismissListener(onDismissListener);
    }

    public void setOnCancelListener(final DialogInterface.OnCancelListener onCancelListener) {
        if (m_dlg != null) {
            m_dlg.setOnCancelListener(onCancelListener);
        }
    }

    public void setOnShowListener(final DialogInterface.OnShowListener onShowListener) {
        if (m_dlg != null) {
            m_dlg.setOnShowListener(onShowListener);
        }
    }

    public boolean isShowing() {
        return m_dlg != null && m_dlg.isShowing();
    }

    public final void show() {
        if (m_dlg == null) {
            return;
        }
        try {
            Activity activity = (Activity) context;
            if (!activity.isFinishing()) {
                m_dlg.show();
            }
        } catch (Exception e) {
            PLog.e(e.getMessage());
            return;
        }

        if (getWindowWidth() != -1) {
            Window window = m_dlg.getWindow();
            if (getHeight() > 0) {
                window.setLayout(getWindowWidth(), getHeight());
            } else {
                window.setLayout(getWindowWidth(), LinearLayout.LayoutParams.WRAP_CONTENT);
            }
            m_vAlertDialog.setMinimumWidth(getWindowWidth());
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.gravity = Gravity.CENTER;
        }
    }

    private void create() {
        m_vAlertDialog = getAlertDialogView();
        m_dlg = new CVGBaseAlertDialog(context, m_vAlertDialog, getDialogStyle());

        Window window = m_dlg.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();

        lp.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lp.dimAmount = 0.35f;
        window.setAttributes(lp);
        setCancelable(false);

        View btnNegative = m_vAlertDialog.findViewById(R.id.btnNegative);
        if (btnNegative != null && btnNegative instanceof TextView) {
            ((TextView) btnNegative).setTextColor(AndroidUtils.getContext()
                    .getResources().getColor(R.color.c_FF5592F4));
        }
        if (btnNegative != null) {
            btnNegative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }
    }

    protected int getDialogStyle() {
        return R.style.AlertDialog;
    }

    protected View getAlertDialogView() {
        final LayoutInflater inflate = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflate.inflate(getAlertDialogViewRes(), null);
    }

    private int getWindowWidth() {
        return width;
    }

    protected void setWindowWidth(final int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    protected abstract int getAlertDialogViewRes();

    public View getPositiveView() {
        return m_vAlertDialog.findViewById(R.id.btnPositive);
    }

    public View getNegativeView() {
        return m_vAlertDialog.findViewById(R.id.btnNegative);
    }
}
