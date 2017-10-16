package com.wdhhr.wswsvipnew.widget.wheelview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.wdhhr.wswsvipnew.R;
import com.wdhhr.wswsvipnew.model.dataBase.CityBean;
import com.wdhhr.wswsvipnew.utils.DeviceUtils;

import java.util.ArrayList;
import java.util.List;


public class LocalPicker extends LinearLayout {

    private WheelView wv_province;
    private WheelView wv_city;
    private WheelView wv_county;

    private List<CityBean> mListProvince = new ArrayList<>();
    private List<CityBean> mListCurCity = new ArrayList<>();
    private List<CityBean> mListCurCounty = new ArrayList<>();

    private static final String TAG = "LocalPicker";

    private List<CityBean> mListAllCityBean;
    private int miPoiProvince = 28;
    private int miPoiCity = 0;
    private int miPoiCounty;
    private boolean isRefresh;

    public LocalPicker(Context context) {
        super(context);
    }

    public LocalPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setData(List<CityBean> data) {
        setData(data, 0, 0, 0);
    }

    public void setData(List<CityBean> data, int provinceId, int cityId, int countId) {
        // initView
        setBackgroundColor(0xfff5f5f5);
        setOrientation(VERTICAL);

        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        addView(linearLayout);

        mListAllCityBean = data;

        // 添加省集合
        for (int i = 0; i < mListAllCityBean.size(); i++) {
            CityBean cityBean = mListAllCityBean.get(i);
            if (cityBean.getFID() == 0) {
                mListProvince.add(cityBean);
            }

        }

        // 添加省数组
        String[] proviceData = new String[mListProvince.size()];
        for (int i = 0; i < mListProvince.size(); i++) {
            CityBean cityBean = mListProvince.get(i);
            proviceData[i] = cityBean.getSoftName();
            if (cityBean.getAREA_ID() == provinceId) {
                miPoiProvince = i;
            }
        }

        LayoutParams params = new LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);

        params.topMargin = DeviceUtils.dip2px(10);
        params.bottomMargin = DeviceUtils.dip2px(10);
        // 省
        wv_province = new WheelView(getContext());
        wv_province.setLayoutParams(params);
        wv_province.setAdapter(new StringWheelAdapter(0, proviceData.length - 1, proviceData));// 设置"省"的显示数据
        linearLayout.addView(wv_province);
        Log.d(TAG, miPoiProvince + " position");

        // 市
        wv_city = new WheelView(getContext());
        wv_city.setLayoutParams(params);
        linearLayout.addView(wv_city);


        // 县
        wv_county = new WheelView(getContext());
        wv_county.setLayoutParams(params);
        linearLayout.addView(wv_county);

        // 添加"省"监听
        wv_province.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                if (!isRefresh) {
                    miPoiProvince = newValue;
                    setCityData(0, 0);
                }
            }
        });


        // 添加"市"监听
        wv_city.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                if (!isRefresh) {
                    miPoiCity = newValue;
                    setCountyData(0);
                }
            }
        });

        wv_county.addChangingListener(new OnWheelChangedListener() {
            @Override
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                if (!isRefresh) {
                    miPoiCounty = newValue;
                }
            }
        });


        // 设置数据
        wv_province.setCurrentItem(miPoiProvince);
        setCityData(cityId, countId);

        // 根据屏幕密度来指定选择器字体的大小(不同屏幕可能不同)
        int textSize = getContext().getResources().getDimensionPixelSize(R.dimen.font_tips);
        wv_county.TEXT_SIZE = textSize;
        wv_city.TEXT_SIZE = textSize;
        wv_province.TEXT_SIZE = textSize;

        Log.d(TAG, System.currentTimeMillis() + " E");


    }

    // 获取市集合
    private void setCityData(int cityId, int countId) {

        int fid = mListProvince.get(miPoiProvince).getAREA_ID();

        mListCurCity.clear();
        for (int i = 0; i < mListAllCityBean.size(); i++) {
            CityBean cityBean = mListAllCityBean.get(i);
            if (cityBean.getFID() == fid) {
                mListCurCity.add(cityBean);
            }
        }

        // 添加市数组
        String[] cityData = new String[mListCurCity.size()];
        if (miPoiProvince == 28) {
            miPoiCity = 3;
        } else {
            miPoiCity = 0;
        }
        for (int i = 0; i < mListCurCity.size(); i++) {
            CityBean cityBean = mListCurCity.get(i);
            cityData[i] = mListCurCity.get(i).getSoftName();
            if (cityBean.getAREA_ID() == cityId) {
                miPoiCity = i;
            }
        }
        wv_city.setAdapter(new StringWheelAdapter(0, cityData.length - 1, cityData));
        wv_city.setCurrentItem(miPoiCity);

        setCountyData(countId);
    }

    // 获取县集合
    private void setCountyData(int countId) {

        int area_id = mListCurCity.get(miPoiCity).getAREA_ID();

        mListCurCounty.clear();
        for (int i = 0; i < mListAllCityBean.size(); i++) {
            CityBean cityBean = mListAllCityBean.get(i);
            if (cityBean.getFID() == area_id) {
                mListCurCounty.add(cityBean);
            }
        }
        // 添加省数组
        String[] countyData = new String[mListCurCounty.size()];
        miPoiCounty = 0;
        for (int i = 0; i < mListCurCounty.size(); i++) {

            CityBean cityBean = mListCurCounty.get(i);
            countyData[i] = mListCurCounty.get(i).getSoftName();
            if (cityBean.getAREA_ID() == countId) {
                miPoiCounty = i;
            }
        }

        wv_county.setAdapter(new StringWheelAdapter(0, countyData.length - 1, countyData));
        wv_county.setCurrentItem(miPoiCounty);

    }

    public String getLocalName() {
        return mListProvince.get(miPoiProvince).getNAME() + " "
                + mListCurCity.get(miPoiCity).getNAME() + " "
                + mListCurCounty.get(miPoiCounty).getNAME();

    }

    public List<CityBean> getLocalBean() {

        ArrayList<CityBean> cityList = new ArrayList<>();
        cityList.add(mListProvince.get(miPoiProvince));
        cityList.add(mListCurCity.get(miPoiCity));
        cityList.add(mListCurCounty.get(miPoiCounty));
        return cityList;
    }

    public void setCurrentItem(int provinceId, int cityId, int countId) {
        // 省
        for (int i = 0; i < mListProvince.size(); i++) {
            if (mListProvince.get(i).getAREA_ID() == provinceId) {
                wv_province.setCurrentItem(i);
                miPoiProvince = i;
                setCityData(cityId, countId);
                break;
            }
        }

    }

    public void refresh() {
        Log.d(TAG, miPoiProvince + " " + miPoiCity + " " + miPoiCounty);
        isRefresh = true;

        if (miPoiProvince == 0) {
            wv_province.setCurrentItem(1);
        } else {
            wv_province.setCurrentItem(0);
        }

        if (miPoiCity == 0) {
            wv_city.setCurrentItem(1);
        } else {
            wv_city.setCurrentItem(0);
        }

        if (miPoiCounty == 0) {
            wv_county.setCurrentItem(1);
        } else {
            wv_county.setCurrentItem(0);
        }

        postDelayed(new Runnable() {
            @Override
            public void run() {
                wv_province.setCurrentItem(miPoiProvince);
                wv_city.setCurrentItem(miPoiCity);
                wv_county.setCurrentItem(miPoiCounty);
            }
        }, 150);

        postDelayed(new Runnable() {
            @Override
            public void run() {
                isRefresh = false;
            }
        }, 200);
    }


}
