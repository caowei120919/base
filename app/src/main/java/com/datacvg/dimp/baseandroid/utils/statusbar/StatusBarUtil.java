package com.datacvg.dimp.baseandroid.utils.statusbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.ColorInt;

import com.datacvg.dimp.R;

/**
 * Created by Jaeger on 16/2/14.
 * <p>
 * Email: chjie.jaeger@gmail.com
 * GitHub: https://github.com/laobie
 */
public class StatusBarUtil {

    @ColorInt
    public static int getStatusBarColor(Context context) {
        final int[] ATTRS = new int[]{R.attr.colorPrimaryDark};
        TypedArray a = context.getTheme().obtainStyledAttributes(ATTRS);
        final int color = a.getColor(0, Color.TRANSPARENT);
        a.recycle();
        return color;
    }

    /**
     * 获取状态栏高度
     *
     * @param context context
     * @return 状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        int height;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 获得状态栏高度
            final int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
            height = context.getResources().getDimensionPixelSize(resourceId);
        } else {
            height = 0;
        }
        return height;
    }

    /**
     * 获取NavigationBar高度
     *
     * @param context
     * @return
     */
    public static int getNavigationBarHeight(Context context) {
        int height;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 获得状态栏高度
            final int resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
            height = context.getResources().getDimensionPixelSize(resourceId);
        } else {
            height = 0;
        }
        return height;
    }
}