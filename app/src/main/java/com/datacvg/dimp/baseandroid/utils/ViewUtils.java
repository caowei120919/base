package com.datacvg.dimp.baseandroid.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Build;

import android.text.Html;
import android.text.Layout;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * Created with IntelliJ IDEA. User: Frank Date: 13-8-23 Time: 上午9:52 To change
 * this template use File | Settings | File Templates.
 */
@SuppressWarnings("deprecation")
public class ViewUtils {
    private final static int UPPER_LEFT_X = 0;
    private final static int UPPER_LEFT_Y = 0;
    private static GetBitmapColorCallBack getBitmapColorCallBack;

    public static void setBitmapColorCallBackListener(GetBitmapColorCallBack callback) {
        getBitmapColorCallBack = callback;
    }


    public static void switchViewVisibility(View... targetViews) {
        for (View v : targetViews) {
            if (v != null) {
                v.setVisibility(v.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
            }
        }
    }

    public static void setViewShow(View... targetViews) {
        for (View v : targetViews) {
            if (v != null) {
                v.setVisibility(View.VISIBLE);
            }
        }
    }

    public static void setViewHide(View... targetViews) {
        for (View v : targetViews) {
            if (v != null) {
                v.setVisibility(View.GONE);
            }
        }
    }

    public static void setViewInvisible(View... targetViews) {
        for (View v : targetViews) {
            if (v != null) {
                v.setVisibility(View.INVISIBLE);
            }
        }
    }

    public static void setViewVisibility(boolean show, View... targetViews) {
        for (View v : targetViews) {
            if (v != null) {
                v.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        }
    }

    public static void setBackgroundResource(int resid, View... targetViews) {
        for (View v : targetViews) {
            if (v != null) {
                v.setBackgroundResource(resid);
            }
        }
    }

    public static void setBackgroundDrawable(Drawable drawable, View... targetViews) {
        for (View v : targetViews) {
            setBackgroundDrawable(v, drawable.getConstantState().newDrawable());
        }
    }

    public static void setBackgroundDrawable(View view, Drawable drawable) {
        if (view != null) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                view.setBackgroundDrawable(drawable);
            } else {
                view.setBackground(drawable);
            }
        }
    }


    private static Bitmap drawableBitmapDrawableToBitmapWithMask(Drawable draSrc, Bitmap bmpMask, int nDesWidth,
                                                                 int nDesHeight) {
        Bitmap bmpResult = Bitmap.createBitmap(nDesWidth, nDesHeight, Config.ARGB_8888);

        Canvas canvas = new Canvas(bmpResult);
        drawBitmapDrawableToCanvas(canvas, draSrc, nDesWidth, nDesHeight);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawBitmap(bmpMask, 0, 0, paint);
        paint.setXfermode(null);

        return bmpResult;
    }


    private static void drawBitmapDrawableToCanvas(Canvas canvas, Drawable draSrc, int nDesWidth, int nDesHeight) {
        Bitmap bmpSrc = ((BitmapDrawable) draSrc).getBitmap();
        if (bmpSrc == null){
            return;
        }

        Matrix matrix = new Matrix();
        int nSrcW = bmpSrc.getWidth();
        int nSrcH = bmpSrc.getHeight();
        float fScaleW = nDesWidth / (float) nSrcW;
        float fScaleH = nDesHeight / (float) nSrcH;
        matrix.postScale(fScaleW, fScaleH);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        canvas.drawBitmap(bmpSrc, 0, 0, paint);
    }

    public static float density = AndroidUtils.getContext().getResources().getDisplayMetrics().density;

    public static void setDensity(float value) {
        density = value;
    }

    public static int dip2px(float dpValue) {
        return (int) (dpValue * density + 0.5f);
    }

    public static int dp(float value) {
        if (value == 0) {
            return 0;
        }
        return (int) Math.ceil(density * value);
    }

    public static int dip2px(String dpValue) {
        try {
            if (dpValue.endsWith("dp")) {
                float dp = Integer.parseInt(dpValue.substring(0, dpValue.indexOf("dp")));
                return dip2px(dp);
            } else if (dpValue.endsWith("px")) {
                return Integer.parseInt(dpValue.substring(0, dpValue.indexOf("px")));
            }
        } catch (Exception e) {
            PLog.e("dip to px error!");
        }
        return 0;

    }

    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static Drawable convertViewToDrawable(View view) {
        int spec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(spec, spec);
        view.layout(UPPER_LEFT_X, UPPER_LEFT_Y, view.getMeasuredWidth(), view.getMeasuredHeight());
        Bitmap b = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Config.ARGB_8888);
        Canvas c = new Canvas(b);
        c.translate(-view.getScrollX(), -view.getScrollY());
        view.draw(c);
        view.setDrawingCacheEnabled(true);
        Bitmap cacheBmp = view.getDrawingCache();
        Bitmap viewBmp = cacheBmp.copy(Config.ARGB_8888, true);
        cacheBmp.recycle();
        view.destroyDrawingCache();
        return new BitmapDrawable(viewBmp);
    }

    /**
     * @return px
     */
    public static int getDesiredWidth(TextView v) {
        TextPaint paint = v.getPaint();
        return (int) Layout.getDesiredWidth(v.getText().toString(), 0,
                v.getText().length(), paint);
    }


    private static boolean isMediaPlayerAvailable(Context context, Intent intent) {
        return context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY).size() > 0;
    }



    public static Bitmap loadBitmapFromView(View v) {
        if (v == null) {
            return null;
        }
        Bitmap screenshot;
        screenshot = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Config.ARGB_8888);
        Canvas c = new Canvas(screenshot);
        c.translate(-v.getScrollX(), -v.getScrollY());
        v.draw(c);
        return screenshot;
    }

    public static String formatTalkTimeForUI(int time) {
        int minute = (int) Math.floor(time / 60);
        int seconds = (time % 60);
        return String.format("%2d:%02d", minute, seconds);
    }


    public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
        if (bitmap == null) {
            return null;
        }
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    public static Drawable getDrawableFromBitmap(Resources rec, Bitmap bitmap) {
        Bitmap bm = bitmap;
        BitmapDrawable bd = new BitmapDrawable(rec, bm);
        return bd;
    }

    public static Bitmap getBitmapFromDrawable(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else if (drawable instanceof NinePatchDrawable) {
            Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), drawable.getOpacity() != PixelFormat.OPAQUE ? Config.ARGB_8888
                    : Config.RGB_565);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            drawable.draw(canvas);
            return bitmap;
        } else {
            return null;
        }
    }

    public static void showKeyboard(View view) {
        if (view == null) {
            return;
        }
        try {
            InputMethodManager inputManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void hideKeyboard(View view) {
        if (view == null) {
            return;
        }
        try {
            InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (!imm.isActive()) {
                return;
            }
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Rect getViewPosInfo(View view) {
        Rect rect = new Rect();
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
        rect.left = x;
        rect.top = y;

        int width = view.getWidth();
        int height = view.getHeight();
        rect.right = rect.left + width;
        rect.bottom = rect.top + height;
        return rect;
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Config.ARGB_8888
                        : Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static View inflateView(LayoutInflater inflater, int id) {
        return inflateView(inflater, id, null);
    }
    public static View inflateView(Context context,  int id) {
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflateView(inflater, id, null);
    }

    public static View inflateView(LayoutInflater inflater, int id, ViewGroup root) {
        if (inflater != null && id > 0) {
            return inflater.inflate(id, root);
        }
        return null;
    }

    public static int colors(int res) {
        return AndroidUtils.getContext().getResources().getColor(res);
    }

    public static void setTextHtml(String content, TextView textView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            textView.setText(Html.fromHtml(content, Html.FROM_HTML_MODE_LEGACY));
        } else {
            textView.setText(Html.fromHtml(content));
        }
    }


    public interface GetBitmapColorCallBack {
        void getBitmapColor(int color);
    }

    public static void handlePopupWindowBugWithAndroidN(PopupWindow popupWindow) {
        if (popupWindow == null) {
            return;
        }
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
            popupWindow.update();
        }
    }

    public static boolean isUrlNeedAddHttpHead(String url) {
        return url != null && !url.startsWith("http");
    }
}
