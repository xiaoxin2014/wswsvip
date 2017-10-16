package com.wdhhr.wswsvipnew.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.view.inputmethod.InputMethodManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by felear on 2016/11/17.
 * email:985979637@qq.com
 */
public class SystemUtil {

    public static final String HIDE_FOLDER = ".hidefolder";
    protected static Context mContext;

    public SystemUtil() {
    }

    public static void setContext(Context context) {
        mContext = context;
    }

    public static Context getContext() {
        return mContext;
    }

    public static String getRoot() {
        return getSDCardFolder().getAbsolutePath();
    }

    public static File getSDCardFolder() {
        File root = null;
        if("mounted".equals(Environment.getExternalStorageState())) {
            root = Environment.getExternalStorageDirectory();
        } else {
            root = Environment.getDataDirectory();
        }

        return root;
    }

    public static String getHideFolder() {
        String path = getRoot() + File.separator + ".hidefolder";
        File f = new File(path);
        if(!f.exists()) {
            f.mkdir();
        }

        return path;
    }

    public static ArrayList<PackageInfo> getInstall() {
        ArrayList list = new ArrayList();
        PackageManager pm = mContext.getPackageManager();
        List packages = pm.getInstalledPackages(0);
        Iterator var4 = packages.iterator();

        while(var4.hasNext()) {
            PackageInfo info = (PackageInfo)var4.next();
            if(info.packageName.startsWith("com.")) {
                list.add(info);
            }
        }

        return list;
    }

    public static boolean findInstalled(String packageName) {
        if(packageName == null) {
            return false;
        } else {
            boolean flag = false;
            PackageManager pm = mContext.getPackageManager();
            List packages = pm.getInstalledPackages(0);
            Iterator var5 = packages.iterator();

            while(var5.hasNext()) {
                PackageInfo info = (PackageInfo)var5.next();
                if(info.packageName.equals(packageName)) {
                    flag = true;
                    break;
                }
            }

            return flag;
        }
    }

    public static boolean checkFlash() {
        PackageManager pm = mContext.getPackageManager();
        List infoList = pm.getInstalledPackages(4);
        Iterator var3 = infoList.iterator();

        while(var3.hasNext()) {
            PackageInfo info = (PackageInfo)var3.next();
            if("com.adobe.flashplayer".equals(info.packageName)) {
                return true;
            }
        }

        return false;
    }

    public static void hidKeyBoard() {
        try {
            InputMethodManager e = (InputMethodManager)mContext.getSystemService("input_method");
            if(((Activity)mContext).getCurrentFocus() != null && ((Activity)mContext).getCurrentFocus().getWindowToken() != null) {
                e.hideSoftInputFromWindow(((Activity)mContext).getCurrentFocus().getWindowToken(), 2);
            }
        } catch (Exception var1) {
            var1.printStackTrace();
        }

    }

    public static boolean isServiceRunning(String className) {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager)mContext.getSystemService("activity");
        List serviceList = activityManager.getRunningServices(30);
        if(serviceList.size() <= 0) {
            return false;
        } else {
            for(int i = 0; i < serviceList.size(); ++i) {
                if(((ActivityManager.RunningServiceInfo)serviceList.get(i)).service.getClassName().equals(className)) {
                    isRunning = true;
                    break;
                }
            }

            return isRunning;
        }
    }

    public static boolean installApk(String path) {
        File file = new File(path);
        if(!file.exists()) {
            return false;
        } else if(!path.endsWith(".apk")) {
            return false;
        } else {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setFlags(268435456);
            intent.setDataAndType(Uri.parse("file://" + path), "application/vnd.android.package-archive");
            mContext.startActivity(intent);
            return true;
        }
    }

    public static boolean installApp(String path, Activity activity, int req) {
        if(activity == null) {
            return false;
        } else {
            File file = new File(path);
            if(!file.exists()) {
                return false;
            } else if(!path.endsWith(".apk")) {
                return false;
            } else {
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.setFlags(268435456);
                intent.setDataAndType(Uri.parse("file://" + path), "application/vnd.android.package-archive");
                activity.startActivityForResult(intent, req);
                return true;
            }
        }
    }

    public static boolean removeApp(String pkgName) {
        if(!isApkInstalled(pkgName)) {
            return false;
        } else {
            Uri packageURI = Uri.parse("package:" + pkgName);
            Intent uninstallIntent = new Intent("android.intent.action.DELETE", packageURI);
            mContext.startActivity(uninstallIntent);
            return true;
        }
    }

    public static void openApp(String packageName) throws PackageManager.NameNotFoundException {
        PackageInfo pi = mContext.getPackageManager().getPackageInfo(packageName, 0);
        PackageManager pm = mContext.getPackageManager();
        Intent resolveIntent = new Intent("android.intent.action.MAIN", null);
        resolveIntent.addCategory("android.intent.category.LAUNCHER");
        resolveIntent.setPackage(pi.packageName);
        List apps = pm.queryIntentActivities(resolveIntent, 0);
        ResolveInfo ri = (ResolveInfo)apps.iterator().next();
        if(ri != null) {
            String className = ri.activityInfo.name;
            Intent intent = new Intent("android.intent.action.MAIN");
            intent.addCategory("android.intent.category.LAUNCHER");
            ComponentName cn = new ComponentName(packageName, className);
            intent.setComponent(cn);
            intent.addFlags(268435456);
            mContext.startActivity(intent);
        }

    }

    public static boolean isApkInstalled(String pkgName) {
        try {
            mContext.getPackageManager().getPackageInfo(pkgName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException var2) {
            var2.printStackTrace();
            return false;
        }
    }

    public static SharedPreferences getSharedPreferences(String spName) {
        SharedPreferences sp;
        if(spName != null) {
            sp = mContext.getSharedPreferences(spName, 0);
        } else {
            sp = PreferenceManager.getDefaultSharedPreferences(mContext);
        }

        return sp;
    }

    public static String getSharedString(String key) {
        return getSharedPreferences(null).getString(key, null);
    }

    public static String getSharedString(String spName, String key, String defValue) {
        return getSharedPreferences(spName).getString(key, defValue);
    }

    public static int getSharedInt(String key, int defValue) {
        return getSharedPreferences(null).getInt(key, defValue);
    }

    public static long getSharedLong(String key, long defValue) {
        return getSharedPreferences(null).getLong(key, defValue);
    }

    public static boolean getSharedBoolean(String key, boolean defValue) {
        return getSharedPreferences(null).getBoolean(key, defValue);
    }

    public static String getSharedString(String key, String defValue) {
        return getSharedPreferences(null).getString(key, defValue);
    }

    public static int getSharedInt(String spName, String key, int defValue) {
        return getSharedPreferences(null).getInt(key, defValue);
    }

    public static long getSharedLong(String spName, String key, int defValue) {
        return getSharedPreferences(null).getLong(key, (long)defValue);
    }

    public static void setSharedInt(String key, int value) {
        SharedPreferences.Editor editor = getSharedPreferences(null).edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static void setSharedLong(String key, long value) {
        SharedPreferences.Editor editor = getSharedPreferences(null).edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public static void setSharedBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = getSharedPreferences(null).edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static void setSharedString(String key, String value) {
        SharedPreferences.Editor editor = getSharedPreferences(null).edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static void setSharedSerializable(String key, Serializable value) throws IOException {
        File f = new File(getAppCacheFile(), key);
        setSerializableObject(f, value);
    }

    public static Object getSharedSerializable(String key) throws IOException, ClassNotFoundException {
        File f = new File(getAppCacheFile(), key);
        return getSerializableObject(f);
    }

    public static File getAppCacheFile() {
        return new File(getHideFolder());
    }

    public static Object getSerializableObject(String objPath) throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(objPath));
        Object retValue = in.readObject();
        in.close();
        return retValue;
    }

    public static Object getSerializableObject(File objFile) throws IOException, ClassNotFoundException {
        return getSerializableObject(objFile.getAbsolutePath());
    }

    public static void setSerializableObject(File file, Object value) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
        out.writeObject(value);
        out.flush();
        out.close();
    }

    public static void setSerializableObject(String path, Object value) throws IOException {
        setSerializableObject(new File(path), value);
    }

    public static PackageInfo getSystemPackageInfo() {
        try {
            return mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException var1) {
            var1.printStackTrace();
            return null;
        }
    }

    public static String getSystemVersion() {
        PackageInfo pi = getSystemPackageInfo();
        return pi != null?pi.versionName:null;
    }

    public static int getSystemVersionCode() {
        PackageInfo pi = getSystemPackageInfo();
        return pi != null?pi.versionCode:-1;
    }

}
