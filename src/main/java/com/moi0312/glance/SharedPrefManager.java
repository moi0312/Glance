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

    /**
     *
     * @param context
     * @param sharedPreferenceName  sharedPreferenceName
     * @return
     */
    public static SharedPreferences getSharedPreferences(Context context, String sharedPreferenceName){
        return context.getSharedPreferences(sharedPreferenceName, Context.MODE_MULTI_PROCESS);
    }
    //remove
    /**
     * remove sharedPreference value by Key
     * @param context
     * @param key       key
     */
    public static void removeKey(Context context, String key) {
        getSharedPreferences(context).edit().remove(key).commit();
    }

    /**
     * remove sharedPreference value by Key
     * @param sharedPreferenceName  sharedPreferenceName
     * @param context
     * @param key                   key
     */
    public static void removeKey(String sharedPreferenceName, Context context, String key) {
        getSharedPreferences(context, sharedPreferenceName).edit().remove(key).commit();
    }

    //---------------------- String --------------------------------------------
    /**
     * put string value by key
     * @param context
     * @param key       key
     * @param value     value
     */
    public static void put(Context context, String key, String value){
//        getSharedPreferences(context).edit().putString(key, value).apply();
        getSharedPreferences(context).edit().putString(key, value).commit();
    }

    /**
     * get string value by key
     * @param context
     * @param key           key
     * @param defaultValue  defaultValue
     * @return
     */
    public static String getString(Context context, String key, String defaultValue){
        return getSharedPreferences(context).getString(key, defaultValue);
    }

    /**
     * put string value by key
     * @param sharedPreferenceName  sharedPreferenceName
     * @param context
     * @param key                   key
     * @param value                 value
     */
    public static void put(String sharedPreferenceName, Context context, String key, String value){
        getSharedPreferences(context, sharedPreferenceName).edit().putString(key, value).commit();
    }

    /**
     * get string value by key
     * @param sharedPreferenceName  sharedPreferenceName
     * @param context
     * @param key                   key
     * @param defaultValue                 value
     * @return
     */
    public static String getString(String sharedPreferenceName, Context context, String key, String defaultValue){
        return getSharedPreferences(context, sharedPreferenceName).getString(key, defaultValue);
    }

    //---------------------- boolean --------------------------------------------
    /**
     * put boolean value by key
     * @param context
     * @param key           key
     * @param boolValue
     */
    public static void put(Context context, String key, boolean boolValue){
        getSharedPreferences(context).edit().putBoolean(key, boolValue).commit();
    }

    /**
     * get boolean value by key
     * @param context
     * @param key           key
     * @param defaultValue  defaultValue
     * @return
     */
    public static boolean getBoolean(Context context, String key, boolean defaultValue){
        return getSharedPreferences(context).getBoolean(key, defaultValue);
    }

    /**
     * put boolean value by key
     * @param sharedPreferenceName  sharedPreferenceName
     * @param context
     * @param key                   key
     * @param boolValue             boolValue
     */
    public static void put(String sharedPreferenceName, Context context, String key, boolean boolValue){
        getSharedPreferences(context).edit().putBoolean(key, boolValue).commit();
    }

    /**
     * get boolean value by key
     * @param sharedPreferenceName  sharedPreferenceName
     * @param context
     * @param key                   key
     * @param defaultValue          defaultValue
     * @return
     */
    public static boolean getBoolean(String sharedPreferenceName, Context context, String key, boolean defaultValue){
        return getSharedPreferences(context).getBoolean(key, defaultValue);
    }

    //---------------------- int ----------------------
    /**
     * put int value by key
     * @param context
     * @param key
     * @param intValue
     */
    public static void put(Context context, String key, int intValue){
        getSharedPreferences(context).edit().putInt(key, intValue).commit();
    }

    /**
     * get int value by key
     * @param context
     * @param key           key
     * @param defaultValue  defaultValue
     * @return
     */
    public static int getInt(Context context, String key, int defaultValue){
        return getSharedPreferences(context).getInt(key, defaultValue);
    }

    /**
     * put int value by key
     * @param sharedPreferenceName  sharedPreferenceName
     * @param context
     * @param key                   key
     * @param intValue
     */
    public static void put(String sharedPreferenceName, Context context, String key, int intValue){
        getSharedPreferences(context, sharedPreferenceName).edit().putInt(key, intValue).commit();
    }

    /**
     * get int value by key
     * @param sharedPreferenceName  sharedPreferenceName
     * @param context
     * @param key                   key
     * @param defaultValue          defaultValue
     * @return
     */
    public static int getInt(String sharedPreferenceName, Context context, String key, int defaultValue){
        return getSharedPreferences(context, sharedPreferenceName).getInt(key, defaultValue);
    }

    //---------------------- long ----------------------

    /**
     * put long value by key
     * @param context
     * @param key           key
     * @param longValue     longValue
     */
    public static void put(Context context, String key, long longValue){
        getSharedPreferences(context).edit().putLong(key, longValue).commit();
    }

    /**
     * get long value by key
     * @param context
     * @param key           key
     * @param defaultValue  defaultValue
     * @return
     */
    public static long getLong(Context context, String key, long defaultValue){
        return getSharedPreferences(context).getLong(key, defaultValue);
    }

    /**
     * put long value by key
     * @param sharedPreferenceName  sharedPreferenceName
     * @param context
     * @param key                   key
     * @param longValue             longValue
     */
    public static void put(String sharedPreferenceName, Context context, String key, long longValue){
        getSharedPreferences(context, sharedPreferenceName).edit().putLong(key, longValue).apply();
    }

    /**
     * get long value by key
     * @param sharedPreferenceName  sharedPreferenceName
     * @param context
     * @param key                   key
     * @param defaultValue          defaultValue
     * @return
     */
    public static long getLong(String sharedPreferenceName, Context context, String key, long defaultValue){
        return getSharedPreferences(context, sharedPreferenceName).getLong(key, defaultValue);
    }

    //---------------------- float ----------------------

    /**
     * put float value by key
     * @param context
     * @param key           key
     * @param floatValue    floatValue
     */
    public static void put(Context context, String key, float floatValue){
        getSharedPreferences(context).edit().putFloat(key, floatValue).commit();
    }

    /**
     * get float value by key
     * @param context
     * @param key           key
     * @param defaultValue  defaultValue
     * @return
     */
    public static float getFloat(Context context, String key, float defaultValue){
        return getSharedPreferences(context).getFloat(key, defaultValue);
    }

    /**
     * put float value by key
     * @param sharedPreferenceName  sharedPreferenceName
     * @param context
     * @param key                   key
     * @param floatValue            floatValue
     */
    public static void put(String sharedPreferenceName, Context context, String key, float floatValue){
        getSharedPreferences(context, sharedPreferenceName).edit().putFloat(key, floatValue).apply();
    }

    /**
     * get float value by key
     * @param sharedPreferenceName  sharedPreferenceName
     * @param context
     * @param key                   key
     * @param defaultValue          defaultValue
     * @return
     */
    public static float getFloat(String sharedPreferenceName, Context context, String key, float defaultValue){
        return getSharedPreferences(context, sharedPreferenceName).getFloat(key, defaultValue);
    }

    //---------------------- Set<String> ----------------------

    /**
     * put Set&lt;String&gt; by key
     * @param context
     * @param key           key
     * @param stringSet     stringSet
     */
    public static void put(Context context, String key, Set<String> stringSet){
        getSharedPreferences(context).edit().putStringSet(key, stringSet).commit();
    }

    /**
     * get Set&lt;String&gt; by key
     * @param context
     * @param key           key
     * @param defaultValue  defaultValue
     * @return
     */
    public static Set<String> getStringSet(Context context, String key, Set<String> defaultValue){
        return getSharedPreferences(context).getStringSet(key, defaultValue);
    }

    /**
     * put Set&lt;String&gt; by key
     * @param sharedPreferenceName  sharedPreferenceName
     * @param context
     * @param key                   key
     * @param stringSet             stringSet
     */
    public static void put(String sharedPreferenceName, Context context, String key, Set<String> stringSet){
        getSharedPreferences(context, sharedPreferenceName).edit().putStringSet(key, stringSet).apply();
    }

    /**
     * get Set&lt;String&gt; by key
     * @param sharedPreferenceName  sharedPreferenceName
     * @param context
     * @param key                   key
     * @param defaultValue          defaultValue
     * @return
     */
    public static Set<String> getStringSet(String sharedPreferenceName, Context context, String key, Set<String> defaultValue){
        return getSharedPreferences(context, sharedPreferenceName).getStringSet(key, defaultValue);
    }
}
