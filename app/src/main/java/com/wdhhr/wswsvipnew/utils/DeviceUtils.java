package com.wdhhr.wswsvipnew.utils;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import java.lang.reflect.Field;

/**
 * Created by felear on 2016/11/17.
 * email:985979637@qq.com
 */
public class DeviceUtils {

    private static final int MB = 1048576;
    private static Context mContext;

    public DeviceUtils() {
    }

    public static void setContext(Context context) {
        mContext = context;
    }

    public static Context getContext() {
        return mContext;
    }

    public static int getScreenWdith() {
        Display display = getDisplay();
        return display.getWidth();
    }

    public static int getScreenHeight() {
        Display display = getDisplay();
        return display.getHeight();
    }

    public static Display getDisplay() {
        Display display = ((WindowManager)mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        return display;
    }

    public static DisplayMetrics getDisPlayMetrics() {
        Display display = getDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        display.getMetrics(dm);
        return dm;
    }

    public static int getScreenDpi() {
        DisplayMetrics dm = getDisPlayMetrics();
        return dm.densityDpi;
    }

    public static int px2sp(float pxValue) {
        DisplayMetrics metrics = getDisPlayMetrics();
        return (int)(pxValue / metrics.scaledDensity + 0.5F);
    }

    public static int sp2px(float spValue) {
        DisplayMetrics metrics = getDisPlayMetrics();
        return (int)(spValue * metrics.scaledDensity + 0.5F);
    }

    public static int px2dip(float pxValue) {
        DisplayMetrics dm = getDisPlayMetrics();
        return (int)(pxValue / dm.density + 0.5F);
    }

    public static int dip2px(float dipValue) {
        DisplayMetrics dm = getDisPlayMetrics();
        return (int)(dipValue * dm.density + 0.5F);
    }

    public static int freeSpaceOnSd() {
        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
        double sdFreeMB = (double)stat.getAvailableBlocks() * (double)stat.getBlockSize() / 1048576.0D;
        return (int)sdFreeMB;
    }

    public static int getStatuBarHeight() {
        Class c = null;
        Object obj = null;
        Field field = null;
        boolean x = false;
        int sbar = 0;

        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            int x1 = Integer.parseInt(field.get(obj).toString());
            sbar = mContext.getResources().getDimensionPixelSize(x1);
        } catch (Exception var6) {
            Log.i("error", "get status bar height fail");
            var6.printStackTrace();
        }

        return sbar;
    }

}
