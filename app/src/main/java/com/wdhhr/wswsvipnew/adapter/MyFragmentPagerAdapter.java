package com.wdhhr.wswsvipnew.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by felear on 2017/8/15 0015.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList;
    private List<String> mTitleList;

    public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> titleList) {
        super(fm);
        mFragmentList = fragmentList;
        mTitleList = titleList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (mTitleList != null) {
            return mTitleList.get(position);
        }
        return super.getPageTitle(position);
    }

    public void setTitleList(List<String> titleList) {
        mTitleList = titleList;
    }
}
