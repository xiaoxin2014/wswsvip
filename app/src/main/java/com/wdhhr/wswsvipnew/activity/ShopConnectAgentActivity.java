package com.wdhhr.wswsvipnew.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.wdhhr.wswsvipnew.R;
import com.wdhhr.wswsvipnew.adapter.MyFragmentPagerAdapter;
import com.wdhhr.wswsvipnew.base.BaseActivity;
import com.wdhhr.wswsvipnew.constant.EventConstants;
import com.wdhhr.wswsvipnew.fragment.BetweenPushFragment;
import com.wdhhr.wswsvipnew.fragment.DirectPushFragment;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ShopConnectAgentActivity extends BaseActivity {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_back)
    TextView tvBack;
    @BindView(R.id.tabs_layout)
    TabLayout tabsLayout;
    @BindView(R.id.vp_content)
    ViewPager vpContent;

    private String TAG = ShopConnectAgentActivity.class.getSimpleName();
    private ArrayList<String> mTitleList;
    private ArrayList<Fragment> mFragmentList;
    private MyFragmentPagerAdapter mAdapter;

    @Override
    protected int setViewId() {
        return R.layout.activity_shop_connect_agent;
    }

    @Override
    protected void init() {
        title.setText(R.string.shop_connect_agent);
        tvBack.setVisibility(View.VISIBLE);
        mFragmentList = new ArrayList<>();
        mTitleList = new ArrayList<>();
        DirectPushFragment directPushFragment = new DirectPushFragment();
        BetweenPushFragment betweenPushFragment = new BetweenPushFragment();
        mFragmentList.add(directPushFragment);
        mFragmentList.add(betweenPushFragment);
        mTitleList.add("直推(0)");
        mTitleList.add("间推(0)");
        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragmentList, mTitleList);
        vpContent.setAdapter(mAdapter);
        tabsLayout.setupWithViewPager(vpContent);
        tabsLayout.setTabsFromPagerAdapter(mAdapter);
        vpContent.setCurrentItem(0);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void loadData() {

    }

    @Subscriber(tag = EventConstants.UPDATE_PUSH_PERSON_NUM)
    private void update_push_person_num(List<String> num) {
        tabsLayout.getTabAt(0).setText(num.get(0));
        tabsLayout.getTabAt(1).setText(num.get(1));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
        }
    }
}
