package com.moi0312.glance;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;

import java.util.Date;
import java.util.List;

public class AppUtilty {
    private static final String TAG = AppUtilty.class.getSimpleName();

    /** Open another app.
     * @param context current Context, like Activity, App, or Service
     * @param packageName the full package name of the app to open
     * @return true if likely successful, false if unsuccessful
     */
    public static boolean launchApp(Context context, String packageName) {
        if(packageName == null){ return false; }
        PackageManager packageManager = context.getPackageManager();
        Intent i = packageManager.getLaunchIntentForPackage(packageName);
        if (i == null) {
            //the package does not contain an activity in the category CATEGORY_LAUNCHER or CATEGORY_INFO, or packageName is not recognized.
            return false;
        }
        i.addCategory(Intent.CATEGORY_LAUNCHER);
        context.startActivity(i);
        return true;
    }

    public static void launchOrInstall(Context context, String packageName) {
        if(packageName != null && !packageName.equals("")){
            boolean isAppFound = AppUtilty.launchApp(context, packageName);
            if(!isAppFound){
                openMarket(context, packageName);
            }
        }
    }

    public static void openMarket(Context context, String packageName) {
        //download app from google play
        Intent playDetailIntent = new Intent(Intent.ACTION_VIEW);
//                playDetailIntent.setData(Uri.parse("market://search?q=" + packageName)); //for search
        playDetailIntent.setData(Uri.parse("market://details?id=" + packageName));
        context.startActivity(playDetailIntent);
    }

    /**
     * get app icon by packageName
     * @param context
     * @param packageName
     * @return
     */
    public static Drawable getAppIcon(Context context, String packageName) {
        Drawable appIcon = null;
        try {
            appIcon = context.getPackageManager().getApplicationIcon(packageName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return appIcon;
    }

    public static CharSequence getAppName(Context context, String packageName) {
        CharSequence appName = null;
        ApplicationInfo appInfo = null;
        PackageManager packageManager = context.getPackageManager();
        try {
            appInfo = packageManager.getApplicationInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if(appInfo != null){
            appName = packageManager.getApplicationLabel(appInfo);
        }
        return appName;
    }

    public static String getAppVerName(Context context, String packageName) {
        String appVerName = null;
        try {
            appVerName = context.getPackageManager().getPackageInfo(packageName, PackageManager.PERMISSION_GRANTED).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return appVerName;
    }

    /**
     * @param context
     * @return
     */
    public static String getTopProcessName(Context context){
        if(context != null){
            ActivityManager activityManager =(ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1){
                return getTopProcessNameByUsageStats(context);
            }else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                //only get all runningAppProcesses under Android 5.1(API level 22). API 22 and later will only get self
                List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();
//            Log.v(TAG, "runningAppProcesses: " + runningAppProcesses.size());
                return runningAppProcesses.get(0).processName;
            }else{
                return activityManager.getRunningTasks(1).get(0).topActivity.getPackageName();
            }
        }else{
            return null;
        }
    }

    /**
     * need permission "android.permission.PACKAGE_USAGE_STATS"
     * @param context
     * @return
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static String getTopProcessNameByUsageStats(Context context){
        Date date = new Date();
        UsageStatsManager usageStatsManager=(UsageStatsManager)context.getSystemService(Context.USAGE_STATS_SERVICE);
        List<UsageStats>  listUsageStats = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, date.getTime() - 2000, date.getTime());
        if(listUsageStats.size()>0){
            long lastestTimeUsed = 0;
            UsageStats lastUsedItem = listUsageStats.get(0);
            for(int i=0;i<listUsageStats.size();i++){
//                Log.v(TAG, "USAGE: "+i+" : "+listUsageStats.get(i).getPackageName()+" : "+listUsageStats.get(i).getLastTimeUsed());
                UsageStats item = listUsageStats.get(i);
                if(item.getLastTimeUsed() > lastestTimeUsed){
                    lastestTimeUsed = item.getLastTimeUsed();
                    lastUsedItem = item;
                }
            }
//            Log.v(TAG, "last USAGE: "+lastUsedItem.getPackageName());
            return lastUsedItem.getPackageName();
        }
        return null;
    }

}
