package com.moi0312.glance;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.google.common.base.Preconditions;

import java.lang.reflect.Method;

/**
 * base on <a href="http://stackoverflow.com/a/23651004">this post</a>
 */
public final class WifiApManager {
    private static final String TAG = WifiApManager.class.getSimpleName();

    private final WifiManager mWifiManager;
    private Method wifiControlMethod;
    private Method wifiApConfigurationMethod;
    private Method wifiApState;

    public WifiApManager(Context context) throws SecurityException, NoSuchMethodException {
        context = Preconditions.checkNotNull(context);
        mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
//        wifiControlMethod = mWifiManager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class,boolean.class);
//        wifiApConfigurationMethod = mWifiManager.getClass().getMethod("getWifiApConfiguration", null);
//        wifiApState = mWifiManager.getClass().getMethod("getWifiApState");
    }

    public boolean setWifiApState(WifiConfiguration config, boolean enabled) {
        config = Preconditions.checkNotNull(config);
        try {
            if (enabled) {
                mWifiManager.setWifiEnabled(!enabled);
            }
            if(wifiControlMethod == null){
                wifiControlMethod = mWifiManager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class,boolean.class);
            }
            return (Boolean) wifiControlMethod.invoke(mWifiManager, config, enabled);
        } catch (Exception e) {
            Log.e(TAG, "", e);
            return false;
        }
    }

    public WifiConfiguration getWifiApConfiguration() {
        try{
            if(wifiApConfigurationMethod == null){
                wifiApConfigurationMethod = mWifiManager.getClass().getMethod("getWifiApConfiguration", null);
            }
            return (WifiConfiguration)wifiApConfigurationMethod.invoke(mWifiManager, null);
        }catch(Exception e) {
            return null;
        }
    }

    /**
     *
     * @return WifiManager.WIFI_STATE_DISABLING (value 0), <br/>
     * WifiManager.WIFI_STATE_DISABLED (value 1), <br/>
     * WifiManager.WIFI_STATE_ENABLING (value 2), <br/>
     * WifiManager.WIFI_STATE_ENABLED (value 3), <br/>
     * WifiManager.WIFI_STATE_UNKNOWN (value 4)
     */
    public int getWifiApState() {
        try {
            if(wifiApState == null){
                wifiApState = mWifiManager.getClass().getMethod("getWifiApState");
            }
            return (Integer)wifiApState.invoke(mWifiManager);
        } catch (Exception e) {
            Log.e(TAG, "", e);
            return WifiManager.WIFI_STATE_UNKNOWN;
        }
    }

    /**
     * simply enable/diable wifi ap
     * @param enable
     * @return
     */
    public boolean setWifiApEnabled(boolean enable) {
        int wifiApState = getWifiApState();
        if(enable){
            if(wifiApState <= WifiManager.WIFI_STATE_DISABLED || wifiApState == WifiManager.WIFI_STATE_UNKNOWN){
                return setWifiApState(getWifiApConfiguration(), enable);
            }
        }else{
            if(wifiApState >= WifiManager.WIFI_STATE_ENABLING){
                return setWifiApState(getWifiApConfiguration(), enable);
            }
        }
        return enable;
    }
}