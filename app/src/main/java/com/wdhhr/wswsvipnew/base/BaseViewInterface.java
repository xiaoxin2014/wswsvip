package com.wdhhr.wswsvipnew.base;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Administrator on 2017/8/30 0030.
 */

public interface BaseViewInterface {

    void showLoadPw();

    void dismissLoadPw();

    void showSelTipsPw(int strId, final OnSelTipsPwSureListener onSelTipsPwSureListener);

    void showShortToast(int strId);

    void showLongToast(int strId);

    void showLongToast(String str);

    void showShortToast(String str);

    Activity getBaseActivity();

    void readyGo(Class<?> clazz, Bundle bundle);

    void readyGo(Class<?> clazz);

    String getStrFormat(int id, int num);

    String getStrFormat(int id, String str);

    String getStr(int id);

    int getCol(int id);

}
