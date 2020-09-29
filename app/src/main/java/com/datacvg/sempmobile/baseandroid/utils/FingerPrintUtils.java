package com.datacvg.sempmobile.baseandroid.utils;

import android.app.KeyguardManager;
import android.content.Context;
import android.os.Build;

import androidx.core.hardware.fingerprint.FingerprintManagerCompat;

import com.datacvg.sempmobile.R;

/**
 * @Author : T-Bag (茶包)
 * @Time : 2020-09-29
 * @Description :
 */
public class FingerPrintUtils {
    /**
     * 判断是否支持指纹识别
     */
    public static boolean supportFingerprint(Context mContext) {
        if (Build.VERSION.SDK_INT < 23) {
            ToastUtils.showShortToast(mContext.getResources()
                    .getString(R.string.your_system_version_is_too_low_to_support_fingerprint_functionality));
            return false;
        } else {
            KeyguardManager keyguardManager = mContext.getSystemService(KeyguardManager.class);
            FingerprintManagerCompat fingerprintManager = FingerprintManagerCompat.from(mContext);
            if (!fingerprintManager.isHardwareDetected()) {
                ToastUtils.showShortToast(mContext.getResources()
                        .getString(R.string.your_system_version_is_too_low_to_support_fingerprint_functionality));
                return false;
            } else if (keyguardManager != null && !keyguardManager.isKeyguardSecure()) {
                ToastUtils.showShortToast(mContext.getResources()
                        .getString(R.string.your_mobile_phone_does_not_support_fingerprint_function));
                return false;
            } else if (!fingerprintManager.hasEnrolledFingerprints()) {
                ToastUtils.showShortToast(mContext.getResources()
                        .getString(R.string.you_need_to_add_at_least_one_fingerprint_to_your_system_settings));
                return false;
            }
        }
        return true;
    }
}
