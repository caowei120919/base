package com.datacvg.dimp.baseandroid.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.datacvg.dimp.baseandroid.utils.Command;
import com.datacvg.dimp.baseandroid.utils.PLog;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-07-28
 * @Description :
 */
public class CVGBaseAlertDialog extends Dialog {
    private Command clkBackPressed;
    private OnPreDismissListener onPreDismissListener;

    public CVGBaseAlertDialog(Context context, View layout, int style) {
        super(context, style);
        setContentView(layout);

        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }

    public void setOnBackPressed(Command clkBackPressed) {
        this.clkBackPressed = clkBackPressed;
    }

    @Override
    public void onBackPressed() {
        if (clkBackPressed != null) {
            clkBackPressed.execute();
        }
        super.onBackPressed();
    }

    public void setOnPreDismissListener(OnPreDismissListener onPreDismissListener) {
        this.onPreDismissListener = onPreDismissListener;
    }

    @Override
    public void dismiss() {
        if (onPreDismissListener != null) {
            onPreDismissListener.onPreDismiss();
        } else {
            try {
                super.dismiss();
            } catch (Exception e) {
                PLog.e(e.getMessage());
            }
        }
    }

    public void reallyDismiss() {
        if(this.isShowing()){
            try {
                super.dismiss();
            } catch (Exception e) {
                PLog.e(e.getMessage());
            }
        }
    }

    public interface OnPreDismissListener {
        void onPreDismiss();
    }
}
