package moi0312.glance;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;

/**
 * <a href="http://android-developers.blogspot.tw/2011/03/identifying-app-installations.html">Identifying App Installations</a>
 */
public class DeviceUtility {

    /**
     * Depending on the network technology. Return the IMEI, MEID, or ESN of the phone, which is unique to that piece of hardware.<br/>
     * devices that don’t have telephony hardware just don’t have this kind of unique identifier.<br/><br/>
     * need permission<br/>&lt;uses-permission android:name="android.permission.READ_PHONE_STATE" /&gt;
     *
     * @param context   to get TELEPHONY_SERVICE
     * @return an uniqe device id
     */
    public static String getDeviceID(Context context){
        TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = telephonyManager.getDeviceId();
        return deviceId;
//        String simSerialNumber = telephonyManager.getSimSerialNumber();
//        String androidId = android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
//        UUID deviceUuid = new UUID(androidId.hashCode(), ((long)deviceId.hashCode() << 32) | simSerialNumber.hashCode());
//        String deviceUUID = deviceUuid.toString();
//        return deviceUUID;

    }

    /**
     * <b>Settings.Secure.ANDROID_ID</b> is a 64-bit quantity that is generated and stored when the device first boots.<br/>
     * It is reset when the device is wiped.
     *
     * @param context
     * @return
     */
    public static String getAndroidID(Context context){
        String androidId = Settings.Secure.getString(
                context.getContentResolver(),
                Settings.Secure.ANDROID_ID
        );
        return androidId;
    }

    /**
     * by check if AppOpsManager.checkOpNoThrow returns AppOpsManager.MODE_ALLOWED or not.
     * @param context
     * @param appOpsManagerOperations AppOpsManager.OPSTR_XXXX(ex: AppOpsManager.OPSTR_GET_USAGE_STATS)
     * @return
     */
    public static boolean checkOperationsAllowed(Context context, String appOpsManagerOperations){
        try {
            PackageManager packageManager = context.getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
            AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            int mode = appOpsManager.checkOpNoThrow(appOpsManagerOperations, applicationInfo.uid, applicationInfo.packageName);
            return (mode == AppOpsManager.MODE_ALLOWED);

        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }

    }

    public static final int SET_TEATHER =     100001;
    public static final int SET_LOCALE =      100002;
    public static final int SET_SECURITY =    100102;
    public static final int SET_USAGE_STATS = 100103;
    /**
     *
     * @param context
     * @param settingType   DeviceUtility.SET_XXXX
     */
    public static void gotoDeviceSetting(Context context, int settingType) {
        String packageName = "com.android.settings";
        String className = null;
        switch(settingType){
            case SET_TEATHER:
                className = packageName.concat(".TetherSettings");
                break;
            case SET_SECURITY:
                className = packageName.concat(".SecuritySettings");
                break;
            case SET_USAGE_STATS:
                packageName = null;
                className = Settings.ACTION_USAGE_ACCESS_SETTINGS;
                break;
            case SET_LOCALE:
                packageName = null;
                className = Settings.ACTION_LOCALE_SETTINGS;
                break;
        }
        Intent settingIntent = null;
        if(packageName == null){
            settingIntent = new Intent(className);
        }else if(className != null){
            settingIntent = new Intent();
            settingIntent.setClassName(packageName, className);
        }
        settingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(settingIntent);
    }
}