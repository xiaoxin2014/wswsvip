package com.wdhhr.wswsvipnew.utils;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wdhhr.wswsvipnew.model.dataBase.CityBean;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by felear on 2017/8/25 0025.
 */

public class LocalUtils {

    private final static List<CityBean> mListAllCityBean = new ArrayList<>();
    private static Context mContext;
    private static final String TAG = "LocalUtils";

    public static void setLocalList(Context context, final String data) {

        mContext = context;

        new Thread(new Runnable() {
            @Override
            public void run() {
                // 读取本地数据
                String json = null;
                try {
                    InputStream open = mContext.getAssets().open(data);
                    ByteArrayOutputStream mBos = new ByteArrayOutputStream();
                    int len;
                    byte buffer[] = new byte[1024];
                    while ((len = open.read(buffer)) != -1) {
                        mBos.write(buffer, 0, len);
                    }
                    json = mBos.toString();
                } catch (IOException e) {
                    Log.d(TAG, e.getMessage() + "");
                }

                List<CityBean> list = new Gson().fromJson(json, new TypeToken<List<CityBean>>() {
                }.getType());

                if (list != null) {
                    mListAllCityBean.addAll(list);
                }

                for (int i = 0; i < mListAllCityBean.size(); i++) {
                    CityBean cityBean = mListAllCityBean.get(i);
                    if (cityBean.getNAME().length() > 7) {
                        cityBean.setSoftName(cityBean.getNAME().substring(0, 7) + "..");
                    } else {
                        cityBean.setSoftName(cityBean.getNAME());
                    }
                }
            }
        }).start();

    }

    public static List<CityBean> getLocalList() {
        Log.d(TAG, mListAllCityBean.size() + "");
        return mListAllCityBean;
    }

    public static String getLocalName(String provinceId, String cityId, String countyId, String join) {
        return getLocalName(Integer.parseInt(provinceId), Integer.parseInt(cityId), Integer.parseInt(countyId), join);
    }

    public static String getLocalName(int provinceId, int cityId, int countyId, String join) {
        String province = null;
        String city = null;
        String county = null;
        Log.d(TAG, "getLocalName: " + mListAllCityBean.size());
        for (int i = 0, j = 0; i < mListAllCityBean.size() && j < 3; i++) {
            CityBean cityBean = mListAllCityBean.get(i);
            if (cityBean.getAREA_ID() == provinceId) {
                province = cityBean.getNAME();
                j++;
            }
            if (cityBean.getAREA_ID() == cityId) {
                city = cityBean.getNAME();
                j++;
            }
            if (cityBean.getAREA_ID() == countyId) {
                county = cityBean.getNAME();
                j++;
            }
        }

        return province + join + city + join + county;
    }

}
