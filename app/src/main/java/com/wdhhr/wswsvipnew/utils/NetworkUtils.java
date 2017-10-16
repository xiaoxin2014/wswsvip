package com.wdhhr.wswsvipnew.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by felear on 2016/11/17.
 * email:985979637@qq.com
 */
public class NetworkUtils {

    private static Context mContext;

    public NetworkUtils() {
    }

    public static void setContext(Context context) {
        mContext = context;
    }

    public static Context getContext() {
        return mContext;
    }

    public static ConnectivityManager getConnManager() {
        return (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    public static boolean isOnline() {
        NetworkInfo info = getConnManager().getActiveNetworkInfo();
        return info != null && info.isConnected();
    }

    public static boolean isWifiOnline() {
        NetworkInfo info = getConnManager().getNetworkInfo(1);
        return info != null && info.isConnected();
    }

    public static void start3G(boolean enabled) {
        ConnectivityManager connectivityManager = (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        Class connectivityManagerClass = null;
        Field connectivityManagerField = null;
        Class iConnectivityManagerClass = null;
        Object iConnectivityManagerObject = null;
        Method setMobileDataEnabledMethod = null;

        try {
            connectivityManagerClass = Class.forName(connectivityManager.getClass().getName());
            connectivityManagerField = connectivityManagerClass.getDeclaredField("mService");
            connectivityManagerField.setAccessible(true);
            iConnectivityManagerObject = connectivityManagerField.get(connectivityManager);
            iConnectivityManagerClass = Class.forName(iConnectivityManagerObject.getClass().getName());
            setMobileDataEnabledMethod = iConnectivityManagerClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
            setMobileDataEnabledMethod.setAccessible(true);
            setMobileDataEnabledMethod.invoke(iConnectivityManagerObject, Boolean.valueOf(enabled));
        } catch (ClassNotFoundException var8) {
            var8.printStackTrace();
        } catch (NoSuchFieldException var9) {
            var9.printStackTrace();
        } catch (SecurityException var10) {
            var10.printStackTrace();
        } catch (NoSuchMethodException var11) {
            var11.printStackTrace();
        } catch (IllegalArgumentException var12) {
            var12.printStackTrace();
        } catch (IllegalAccessException var13) {
            var13.printStackTrace();
        } catch (InvocationTargetException var14) {
            var14.printStackTrace();
        }

    }

    public static boolean getMobileDataStatus() {
        String methodName = "getMobileDataEnabled";
        ConnectivityManager mConnectivityManager = (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        Class cmClass = mConnectivityManager.getClass();
        Boolean isOpen = null;

        try {
            Method e = cmClass.getMethod(methodName, (Class[])null);
            isOpen = (Boolean)e.invoke(mConnectivityManager, (Object[])null);
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return isOpen.booleanValue();
    }

    public static boolean is3GAvailable() {
        ConnectivityManager connectivity = (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivity == null) {
            return false;
        } else {
            NetworkInfo info = connectivity.getNetworkInfo(0);
            return info != null && info.isConnectedOrConnecting();
        }
    }

    public static byte getNetworkType() {
        ConnectivityManager manager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = manager.getActiveNetworkInfo(); // 获取网络状态信息
        if (netInfo != null && netInfo.isAvailable()) {//网络状态不能为空，并且是有效的
            return (byte) netInfo.getType();
        }
        return -1;
    }

}
