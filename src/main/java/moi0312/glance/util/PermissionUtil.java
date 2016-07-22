package moi0312.glance.util;

import android.app.Activity;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Process;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created on 2016/5/13.
 */
public class PermissionUtil {
    private static final String TAG = PermissionUtil.class.getSimpleName();
    public static final int REQ_PERMISSION = 63; //36604

    /**
     * check permision(s)
     * @param context
     * @param permissionNames   the constant value(s) in Manifest.permission
     * @return  String[] of denied permissions
     */
    public static String[] checkPermission(Context context, String... permissionNames) {
        ArrayList<String> result = new ArrayList<>();
        int targetSdkVersion = DeviceUtility.getTargetSdkVersion(context, context.getApplicationContext().getPackageName());
        if(targetSdkVersion >= 23){
            for(int i = 0; i < permissionNames.length; i++) {
                String permission = permissionNames[i];
                boolean permissionGranted = context.checkPermission(permission, Process.myPid(), Process.myUid()) == PackageManager.PERMISSION_GRANTED;

                Log.v(TAG, "permissionCheck: " + permission + " : " + permissionGranted);
                if (!permissionGranted) {
                    result.add(permission);
                }
            }
        }else{
            for (int i = 0; i < permissionNames.length; i++) {
                String permission = permissionNames[i];
                String strOp = AppOpsManager.permissionToOp(permission);
                boolean permissionGranted = context.checkPermission(permission, Process.myPid(), Process.myUid()) == PackageManager.PERMISSION_GRANTED;
//            if (strOp != null) {
//                AppOpsManager mgrAppOps = (AppOpsManager)context.getSystemService(Context.APP_OPS_SERVICE);
//                permissionGranted = (
//                    mgrAppOps.checkOpNoThrow(strOp, android.os.Process.myUid(), context.getPackageName())
//                        == AppOpsManager.MODE_ALLOWED);
//            }else{
//                permissionGranted = (
//                    context.checkPermission(permission, android.os.Process.myPid(), android.os.Process.myUid())
//                        == PackageManager.PERMISSION_GRANTED);
//            }





                Log.v(TAG, "permissionCheck: " + permission + " : " + permissionGranted);
                if (!permissionGranted) {
                    result.add(permission);
                }
            }
        }
        return result.toArray(new String[result.size()]);
    }


    public static boolean checkShouldShowRationale(Activity activity, String permission) {
        boolean shouldShowRationale = ActivityCompat.shouldShowRequestPermissionRationale(activity, permission);
        Log.v(TAG, permission + " shouldShowRationale: " + shouldShowRationale);
        return shouldShowRationale;
    }

    public static boolean[] checkShouldShowRationale(Activity activity, String[] permissionNames) {
        boolean[] result = new boolean[permissionNames.length];
        for (int i = 0; i < permissionNames.length; i++) {
            result[i] = checkShouldShowRationale(activity, permissionNames[i]);
        }
        return result;
    }

    /**
     *
     * @param activity  activity should override ActivityCompat.onRequestPermissionsResult() to handle permission request results
     * @param permissionNames
     * @return  String array of permissions which should show rationale
     */
    public static String[] requestPermission(Activity activity, String... permissionNames) {
        ArrayList<String> needRationale = new ArrayList<>();
        ArrayList<String> toBeRequest = new ArrayList<>();
        boolean[] shouldShowRationaleArray = checkShouldShowRationale(activity, permissionNames);
        for (int i = 0; i < permissionNames.length; i++) {
            if (shouldShowRationaleArray[i]) {
                //user denied permission and check "never ask again"
                needRationale.add(permissionNames[i]);
            } else {
                // user denied permission or never asked before, request again.
                toBeRequest.add(permissionNames[i]);
            }
        }
        int toBeRequestCount= toBeRequest.size();
        if(toBeRequestCount>0){
            requestPermissionDirectly(activity, toBeRequest.toArray(new String[toBeRequest.size()]));
        }
        return needRationale.toArray(new String[needRationale.size()]);
    }

    /**
     * directly request permissions (show system permission request dialog)
     * @param activity
     * @param permissionNames
     */
    public static void requestPermissionDirectly(Activity activity, String... permissionNames) {
        ActivityCompat.requestPermissions(activity, permissionNames, REQ_PERMISSION);
    }

    /**
     * check and simply ask all denied permission.<br/>
     * if you need to check wheather permission should show rationale,<br/>
     * use requestPermission to checkPermission result
     * @param activity  activity should override ActivityCompat.onRequestPermissionsResult() to handle permission request results
     * @param permissionNames
     */
    public static void checkAndRequestDirectly(Activity activity, String... permissionNames){
        requestPermissionDirectly(
                activity,
                checkPermission(activity, permissionNames)
        );
    }
}
