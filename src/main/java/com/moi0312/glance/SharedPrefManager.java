package com.moi0312.glance;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

public class SharedPrefManager {

    //shared preference file name
    public static final String DEFAULT_SHARED_PREF = "default_shared_pref";

    //keys of SharedPreferenceManager
//    public static class SPKey {
//        public static final String STR_API_KEY = "api_key";
//        public static final String INT_RENTAL_PROGRESS = "RentalProcess_Status";
//        public static final String BOOL_REMEMBER_USER = "remember_user";
//        public static final String FLOAT_WEATHER_TEMPERATURE = "weather_temperature";
//    }
    //value of SPKey
//    public static class SPValue {
//        public static final int NONE = 0;
//    }

    //get SharedPreferences
    public static SharedPreferences getSharedPreferences(Context context){
        return context.getSharedPreferences(DEFAULT_SHARED_PREF, Context.MODE_MULTI_PROCESS);
    }
    public static SharedPreferences getSharedPreferences(Context context, String sharedPreferenceName){
        return context.getSharedPreferences(sharedPreferenceName, Context.MODE_MULTI_PROCESS);
    }
    //remove
    public static void removeKey(Context context, String key) {
        getSharedPreferences(context).edit().remove(key).commit();
    }
    public static void removeKey(String sharedPreferenceName, Context context, String key) {
        getSharedPreferences(context, sharedPreferenceName).edit().remove(key).commit();
    }

    //---------------------- String ----------------------
    public static void put(Context context, String key, String value){
//        getSharedPreferences(context).edit().putString(key, value).apply();
        getSharedPreferences(context).edit().putString(key, value).commit();
    }
    public static String getString(Context context, String key, String defaultValue){
        return getSharedPreferences(context).getString(key, defaultValue);
    }
    public static void put(String sharedPreferenceName, Context context, String key, String value){
        getSharedPreferences(context, sharedPreferenceName).edit().putString(key, value).commit();
    }
    public static String getString(String sharedPreferenceName, Context context, String key, String defaultValue){
        return getSharedPreferences(context, sharedPreferenceName).getString(key, defaultValue);
    }

    //---------------------- boolean ----------------------
    public static void put(Context context, String key, boolean boolValue){
        getSharedPreferences(context).edit().putBoolean(key, boolValue).commit();
    }
    public static boolean getBoolean(Context context, String key, boolean defaultValue){
        return getSharedPreferences(context).getBoolean(key, defaultValue);
    }

    public static void put(String sharedPreferenceName, Context context, String key, boolean boolValue){
        getSharedPreferences(context).edit().putBoolean(key, boolValue).commit();
    }
    public static boolean getBoolean(String sharedPreferenceName, Context context, String key, boolean defaultValue){
        return getSharedPreferences(context).getBoolean(key, defaultValue);
    }

    //---------------------- int ----------------------
    public static void put(Context context, String key, int intValue){
        getSharedPreferences(context).edit().putInt(key, intValue).commit();
    }
    public static int getInt(Context context, String key, int defaultValue){
        return getSharedPreferences(context).getInt(key, defaultValue);
    }
    public static void put(String sharedPreferenceName, Context context, String key, int intValue){
        getSharedPreferences(context, sharedPreferenceName).edit().putInt(key, intValue).commit();
    }
    public static int getInt(String sharedPreferenceName, Context context, String key, int defaultValue){
        return getSharedPreferences(context, sharedPreferenceName).getInt(key, defaultValue);
    }

    //---------------------- long ----------------------
    public static void put(Context context, String key, long longValue){
        getSharedPreferences(context).edit().putLong(key, longValue).commit();
    }
    public static long getLong(Context context, String key, long defaultValue){
        return getSharedPreferences(context).getLong(key, defaultValue);
    }
    public static void put(String sharedPreferenceName, Context context, String key, long longValue){
        getSharedPreferences(context, sharedPreferenceName).edit().putLong(key, longValue).apply();
    }
    public static long getLong(String sharedPreferenceName, Context context, String key, long defaultValue){
        return getSharedPreferences(context, sharedPreferenceName).getLong(key, defaultValue);
    }

    //---------------------- float ----------------------
    public static void put(Context context, String key, float floatValue){
        getSharedPreferences(context).edit().putFloat(key, floatValue).commit();
    }
    public static float getFloat(Context context, String key, float defaultValue){
        return getSharedPreferences(context).getFloat(key, defaultValue);
    }
    public static void put(String sharedPreferenceName, Context context, String key, float floatValue){
        getSharedPreferences(context, sharedPreferenceName).edit().putFloat(key, floatValue).apply();
    }
    public static float getFloat(String sharedPreferenceName, Context context, String key, float defaultValue){
        return getSharedPreferences(context, sharedPreferenceName).getFloat(key, defaultValue);
    }

    //---------------------- Set<String> ----------------------
    public static void put(Context context, String key, Set<String> stringSet){
        getSharedPreferences(context).edit().putStringSet(key, stringSet).commit();
    }
    public static Set<String> getStringSet(Context context, String key, Set<String> defaultValue){
        return getSharedPreferences(context).getStringSet(key, defaultValue);
    }
    public static void put(String sharedPreferenceName, Context context, String key, Set<String> stringSet){
        getSharedPreferences(context, sharedPreferenceName).edit().putStringSet(key, stringSet).apply();
    }
    public static Set<String> getStringSet(String sharedPreferenceName, Context context, String key, Set<String> defaultValue){
        return getSharedPreferences(context, sharedPreferenceName).getStringSet(key, defaultValue);
    }
}
