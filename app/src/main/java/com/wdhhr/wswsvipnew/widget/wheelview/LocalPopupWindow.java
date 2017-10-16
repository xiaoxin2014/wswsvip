package com.wdhhr.wswsvipnew.widget.wheelview;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.wdhhr.wswsvipnew.R;
import com.wdhhr.wswsvipnew.model.dataBase.CityBean;

import java.util.List;

/**
 * Created by felear on 2017/8/25 0025.
 */

public class LocalPopupWindow extends PopupWindow {


    private final Activity mActivity;
    private final LocalPicker mLocalPicker;

    public LocalPopupWindow(Activity activity) {
        super(new LocalPicker(activity), ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,
                true);
        mActivity = activity;

        mLocalPicker = (LocalPicker) getContentView();

        setTouchable(true);
        // 设置背景颜色
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setAnimationStyle(R.style.pw_slide);

        //  弹出窗监听
        setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams params = mActivity.getWindow().getAttributes();
                params.alpha = 1;
                mActivity.getWindow().setAttributes(params);
            }
        });
    }

    public void addHeaderView(View view) {
        mLocalPicker.addView(view,0);
    }

    public String getLocalName() {
        return mLocalPicker.getLocalName();
    }

    public List<CityBean> getLocalBean() {

        return mLocalPicker.getLocalBean();
    }

    public void setData(List<CityBean> data,int provinceId, int cityId, int countId) {
        mLocalPicker.setData(data,provinceId,cityId,countId);
    }

    public void setData(List<CityBean> data,String provinceId, String cityId, String countId) {
        mLocalPicker.setData(data,Integer.parseInt(provinceId),Integer.parseInt(cityId),Integer.parseInt(countId));
    }

    public void setData(List<CityBean> data) {
        mLocalPicker.setData(data);
    }

    public void setCurrentItem(int provinceId, int cityId, int countId) {
        mLocalPicker.setCurrentItem(provinceId,cityId,countId);
    }

    public void refresh() {
        mLocalPicker.refresh();
    }

}
