package com.wdhhr.wswsvipnew.widget;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.wdhhr.wswsvipnew.R;
import com.wdhhr.wswsvipnew.adapter.MyPagerAdapter;
import com.wdhhr.wswsvipnew.utils.ImageUtils;
import com.wdhhr.wswsvipnew.utils.StatusBarUtil;
import com.wdhhr.wswsvipnew.widget.photoimage.PhotoView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/9 0009.
 */

public class ImageDetailPopupWindow extends PopupWindow {

    private ViewGroup.LayoutParams mParams;
    private ViewPager mViewPager;
    private MyPagerAdapter mAdapter;
    private Activity mActivity;
    private List<View> viewList = new ArrayList<>();
    private List<String> mUrlList;

    public ImageDetailPopupWindow(final Activity activity, List<String> urlList) {
        super(LayoutInflater.from(activity).inflate(R.layout.pw_show_image, null, false)
                , ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);

        if (urlList != null) {
            mUrlList = urlList;
        }

        mActivity = activity;
        mParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);


        mViewPager = (ViewPager) getContentView().findViewById(R.id.viewPager);
        mAdapter = new MyPagerAdapter(viewList, null);
        mViewPager.setAdapter(mAdapter);

        refresh();

        setTouchable(true);
        // 设置背景颜色
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //  弹出窗监听
        setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                StatusBarUtil.setFullScreen(mActivity,false);
                WindowManager.LayoutParams params = mActivity.getWindow().getAttributes();
                params.alpha = 1;
                mActivity.getWindow().setAttributes(params);
            }
        });
    }

    public void show(final int position, boolean setFullscreen) {
        if (setFullscreen){
            StatusBarUtil.setFullScreen(mActivity,true);
        }

        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showAtLocation(mActivity.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
                WindowManager.LayoutParams params = mActivity.getWindow().getAttributes();
                params.alpha = 0;
                mActivity.getWindow().setAttributes(params);
                setCurrentItem(position);
            }
        });

    }

    private void refresh() {
        if (mUrlList == null) {
            return;
        }
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                viewList.clear();
                for (int i = 0; i < mUrlList.size(); i++) {
                    PhotoView view = new PhotoView(mActivity);
                    view.setLayoutParams(mParams);
                    view.enable();
                    view.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    ImageUtils.loadImageUrl(view, mUrlList.get(i), mActivity);
                    viewList.add(view);
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ImageDetailPopupWindow.this.dismiss();
                        }
                    });
                }
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    public void setUrlList(List<String> urlList) {
        this.mUrlList = urlList;
        refresh();
    }

    public void setCurrentItem(int position) {
        mViewPager.setCurrentItem(position);
    }


}
