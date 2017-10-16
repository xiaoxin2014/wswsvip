package com.wdhhr.wswsvipnew.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.wdhhr.wswsvipnew.R;
import com.wdhhr.wswsvipnew.adapter.MyFragmentPagerAdapter;
import com.wdhhr.wswsvipnew.base.BaseActivity;
import com.wdhhr.wswsvipnew.base.BaseFragment;
import com.wdhhr.wswsvipnew.constant.EventConstants;
import com.wdhhr.wswsvipnew.constant.OrderConstants;
import com.wdhhr.wswsvipnew.fragment.OrderListFragment;
import com.wdhhr.wswsvipnew.model.cache.PayResultBean;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;

import butterknife.BindView;

public class MyOrderListActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView mTvTitle;
    @BindView(R.id.tv_back)
    TextView mTvBack;
    @BindView(R.id.vp_content)
    ViewPager mVpContent;
    @BindView(R.id.tabs_layout)
    TabLayout mTabsLayout;

    private ArrayList<String> mTitleList = new ArrayList<>();
    private ArrayList<Fragment> mFragmentList = new ArrayList<>();

    private static final String TAG = "MyOrderListActivity";
    private MyFragmentPagerAdapter mFragmentPagerAdapter;
    private int mPosition;

    @Override
    protected int setViewId() {
        return R.layout.activity_my_order_list;
    }

    @Override
    protected void init() {

        mPosition = getIntent().getExtras().getInt("position", 0);
        boolean isBusiness = getIntent().getExtras().getBoolean(OrderConstants.KEY_MODE_ORDER_BUSINESS, false);
        boolean isService = getIntent().getExtras().getBoolean(OrderConstants.KEY_MODE_ORDER_SERVICE, false);

        Log.d(TAG, isBusiness + " isBusiness");

        mTvBack.setVisibility(View.VISIBLE);
        // 判断页面状态
        mTitleList.add(getStr(R.string.all));
        ArrayList<Integer> listMode = new ArrayList<>();
        listMode.add(OrderConstants.MODE_ORDER_ALL);

        if (isService) {
            if (isBusiness) {
                mTvTitle.setText(R.string.customer_service);
            } else {
                mTvTitle.setText(R.string.order_service);
            }
            listMode.add(OrderConstants.MODE_ORDER_REFUNDING);
            listMode.add(OrderConstants.MODE_ORDER_REFUND_SUCCESSFUL);
            listMode.add(OrderConstants.MODE_ORDER_REFUND_FAIL);
            mTitleList.add(getStr(R.string.order_status_refunding));
            mTitleList.add(getStr(R.string.order_status_refund_successful));
            mTitleList.add(getStr(R.string.order_status_refund_fail));
        } else {
            if (isBusiness) {
                mTvTitle.setText(R.string.customer);
            } else {
                mTvTitle.setText(R.string.my_order);
            }
            listMode.add(OrderConstants.MODE_ORDER_PENDING_PAY);
            listMode.add(OrderConstants.MODE_ORDER_PENDING_SHIPMENT);
            listMode.add(OrderConstants.MODE_ORDER_SHIPPED);
            mTitleList.add(getStr(R.string.pending_pay));
            mTitleList.add(getStr(R.string.pending_shipment));
            mTitleList.add(getStr(R.string.my_shipped));
        }


        for (int i = 0; i < listMode.size(); i++) {
            mFragmentList.add(OrderListFragment.newInstance(listMode.get(i), isBusiness, isService));
        }

        // 不让销毁页面
        mVpContent.setOffscreenPageLimit(3);
        mFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragmentList, mTitleList);
        mVpContent.setAdapter(mFragmentPagerAdapter);

        mTabsLayout.setupWithViewPager(mVpContent);
        mTabsLayout.setTabsFromPagerAdapter(mFragmentPagerAdapter);

        mVpContent.setCurrentItem(mPosition);
    }

    @Override
    protected void initEvent() {

    }

    @Subscriber(tag = EventConstants.PAY_RESULT)
    private void payFresh(PayResultBean payResultBean) {
        if (payResultBean.getResult() != 10000) {
            showLongToast(payResultBean.getMsg());
        }
    }


    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            default:
                ((BaseFragment) mFragmentList.get(mVpContent.getCurrentItem())).onClick(view);
        }
    }

}
