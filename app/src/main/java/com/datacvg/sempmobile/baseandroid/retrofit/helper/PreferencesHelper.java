package com.datacvg.sempmobile.baseandroid.retrofit.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import com.datacvg.sempmobile.baseandroid.utils.AndroidUtils;
import com.orhanobut.hawk.Hawk;

import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;


/**
 * FileName: PreferencesHelper
 * Author: 曹伟
 * Date: 2019/9/16 17:34
 * Description:
 */

@Singleton
public class PreferencesHelper {

    private static final String SHAREDPREFERENCES_NAME = "my_sprefs";

    @Inject
    public PreferencesHelper() {

    }

    public static SharedPreferences getPreferences(String name) {
        return AndroidUtils.getContext().getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public static boolean get(String key, boolean defValue) {
        return get(SHAREDPREFERENCES_NAME, key, defValue);
    }

    public static int get(String key, int defValue) {
        return get(SHAREDPREFERENCES_NAME, key, defValue);
    }

    public static float get(String key, float defValue) {
        return get(SHAREDPREFERENCES_NAME, key, defValue);
    }

    public static long get(String key, long defValue) {
        return get(SHAREDPREFERENCES_NAME, key, defValue);
    }

    public static String get(String key, String defValue) {
        return get(SHAREDPREFERENCES_NAME, key, defValue);
    }

    public static Set<String> get(String key, Set<String> defValue) {
        return get(SHAREDPREFERENCES_NAME, key, defValue);
    }

    public static boolean get(String name, String key, boolean defValue) {
        return getPreferences(name).getBoolean(key, defValue);
    }

    public static int get(String name, String key, int defValue) {
        return getPreferences(name).getInt(key, defValue);
    }

    public static float get(String name, String key, float defValue) {
        return getPreferences(name).getFloat(key, defValue);
    }

    public static long get(String name, String key, long defValue) {
        return getPreferences(name).getLong(key, defValue);
    }

    public static String get(String name, String key, String defValue) {
        return getPreferences(name).getString(key, defValue);
    }

    public static Set<String> get(String name, String key, Set<String> defValue) {
        return getPreferences(name).getStringSet(key, defValue);
    }

    public static void put(String key, boolean value) {
        put(SHAREDPREFERENCES_NAME, key, value);
    }

    public static void put(String key, int value) {
        put(SHAREDPREFERENCES_NAME, key, value);
    }

    public static void put(String key, float value) {
        put(SHAREDPREFERENCES_NAME, key, value);
    }

    public static void put(String key, long value) {
        put(SHAREDPREFERENCES_NAME, key, value);
    }

    public static void put(String key, String value) {
        put(SHAREDPREFERENCES_NAME, key, value);
    }

    public static void put(String key, Set<String> value) {
        put(SHAREDPREFERENCES_NAME, key, value);
    }

    public static void put(String name, String key, boolean value) {
        if (Build.VERSION.SDK_INT >= 9) {
            getPreferences(name).edit().putBoolean(key, value).apply();
        } else {
            getPreferences(name).edit().putBoolean(key, value).commit();
        }
    }

    public static void put(String name, String key, int value) {
        if (Build.VERSION.SDK_INT >= 9) {
            getPreferences(name).edit().putInt(key, value).apply();
        } else {
            getPreferences(name).edit().putInt(key, value).commit();
        }
    }

    public static void put(String name, String key, float value) {
        if (Build.VERSION.SDK_INT >= 9) {
            getPreferences(name).edit().putFloat(key, value).apply();
        } else {
            getPreferences(name).edit().putFloat(key, value).commit();
        }
    }

    public static void put(String name, String key, long value) {
        if (Build.VERSION.SDK_INT >= 9) {
            getPreferences(name).edit().putLong(key, value).apply();
        } else {
            getPreferences(name).edit().putLong(key, value).commit();
        }
    }

    public static void put(String name, String key, String value) {
        if (Build.VERSION.SDK_INT >= 9) {
            getPreferences(name).edit().putString(key, value).apply();
        } else {
            getPreferences(name).edit().putString(key, value).commit();
        }
    }

    public static void put(String name, String key, Set<String> value) {
        if (Build.VERSION.SDK_INT >= 9) {
            getPreferences(name).edit().putStringSet(key, value).apply();
        } else {
            getPreferences(name).edit().putStringSet(key, value).commit();
        }
    }

    public static void remove(String key) {
        remove(SHAREDPREFERENCES_NAME, key);
    }

    public static void remove(String name, String key) {
        if (Build.VERSION.SDK_INT >= 9) {
            getPreferences(name).edit().remove(key).apply();
        } else {
            getPreferences(name).edit().remove(key).commit();
        }
    }


    public static void clear() {
        clear(SHAREDPREFERENCES_NAME);
    }

    public static void clear(String name) {
        if (Build.VERSION.SDK_INT >= 9) {
            getPreferences(name).edit().clear().apply();
        } else {
            getPreferences(name).edit().clear().commit();
        }
    }

    public static <T> void putJavaBeanObject(T javabeanobject, String constantTag) {
        Hawk.put(constantTag, javabeanobject);
    }

    public static <T> T getJavaBeanObject(String constantTag) {
        // must note the return value may be null.
        return Hawk.get(constantTag);
    }
}
