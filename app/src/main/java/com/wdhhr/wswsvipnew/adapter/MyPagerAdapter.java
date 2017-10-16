package com.wdhhr.wswsvipnew.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Felear on 2016/6/8.
 */
public class MyPagerAdapter extends PagerAdapter {

    private final List<String> mTitleList;
    private List<View> views;

    public MyPagerAdapter(List<View> views, List<String> titleList){
        this.views = views;
        mTitleList = titleList;
    }

    @Override
    public int getCount() {
        return views == null ? 0 : views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        container.addView(views.get(position));
        return views.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((View)object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (mTitleList != null) {
            return mTitleList.get(position);
        }
        return super.getPageTitle(position);
    }
}
