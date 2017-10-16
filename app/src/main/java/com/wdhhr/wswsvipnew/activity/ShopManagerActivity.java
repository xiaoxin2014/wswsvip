package com.wdhhr.wswsvipnew.activity;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wdhhr.wswsvipnew.MyApplication;
import com.wdhhr.wswsvipnew.R;
import com.wdhhr.wswsvipnew.adapter.MyFragmentPagerAdapter;
import com.wdhhr.wswsvipnew.base.BaseActivity;
import com.wdhhr.wswsvipnew.constant.EventConstants;
import com.wdhhr.wswsvipnew.fragment.ShopManagerBrandFragment;
import com.wdhhr.wswsvipnew.fragment.ShopManagerSingProductFragment;
import com.wdhhr.wswsvipnew.utils.ImageUtils;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;

import butterknife.BindView;

public class ShopManagerActivity extends BaseActivity {


    @BindView(R.id.shop_head)
    ImageView mShopHead;
    @BindView(R.id.tv_shop_name)
    TextView mTvShopName;
    @BindView(R.id.tv_business_name)
    TextView mTvBusinessName;
    @BindView(R.id.tv_login)
    TextView mTvLogin;
    @BindView(R.id.title)
    TextView mTvTitle;
    @BindView(R.id.tv_back)
    TextView tvBack;
    @BindView(R.id.tv_back_white)
    TextView tvBackWhite;
    @BindView(R.id.tv_right)
    TextView mTvRight;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.tabs_layout)
    TabLayout tabsLayout;
    @BindView(R.id.vp_content)
    ViewPager vpContent;
    @BindView(R.id.layout_shop_top)
    LinearLayout mLayoutShopTop;
    @BindView(R.id.shop_bg)
    RelativeLayout mShopBg;
    private ArrayList<String> mTitleList;
    private ArrayList<Fragment> mFragmentList;
    private MyFragmentPagerAdapter mPagerAdapter;
    private static final String TAG = "ShopManagerActivity";
    private int flag = 0;

    @Override
    protected int setViewId() {
        return R.layout.activity_shop_manager;
    }

    @Override
    protected void init() {

        //初始化标题栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        tvBackWhite.setVisibility(View.VISIBLE);
        tvBack.setVisibility(View.GONE);
        mTvTitle.setTextColor(getResources().getColor(R.color.white));
        mTvRight.setTextColor(getResources().getColor(R.color.white));
        mTvTitle.setText(R.string.shop_manager);
        mTvRight.setText(R.string.order);

        mTvLogin.setVisibility(View.GONE);
        mTvShopName.setVisibility(View.VISIBLE);
        mTvBusinessName.setVisibility(View.VISIBLE);

        // 设置ViewPager 和 Tab
        mTitleList = new ArrayList<>();
        mTitleList.add(getStr(R.string.single_product));
        mTitleList.add(getStr(R.string.brand));
        mFragmentList = new ArrayList<>();
        mFragmentList.add(new ShopManagerSingProductFragment());
        mFragmentList.add(new ShopManagerBrandFragment());
        mPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragmentList, mTitleList);
        vpContent.setAdapter(mPagerAdapter);
        tabsLayout.setupWithViewPager(vpContent);
        tabsLayout.setTabsFromPagerAdapter(mPagerAdapter);
        vpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (i == 0) {
                    mTvRight.setVisibility(View.VISIBLE);
                } else {
                    mTvRight.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }

    @Override
    protected void initEvent() {
        mShopBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.getUserInfo() != null) {
                    readyGo(ShopInfomationActivity.class);
                } else {
                    readyGo(LoginActivity.class);
                }
            }
        });
    }

    @Override
    protected void loadData() {
        if (MyApplication.shopInfo!=null){
            ImageUtils.loadCircleImageUrl(mShopHead,MyApplication.shopInfo.getBusinessPic(),R.mipmap.icon_head,this);
            ImageUtils.loadBackgroundUrl(mShopBg,MyApplication.shopInfo.getBusinessBackImg(),R.mipmap.bg_my_shop,this);

            if (TextUtils.isEmpty(MyApplication.shopInfo.getBusinessName())) {
                mTvShopName.setText(MyApplication.getUserInfo().getUserPhone() + getResources().getString(R.string.business_name_after));
            } else {
                mTvShopName.setText(MyApplication.shopInfo.getBusinessName());
            }
            mTvBusinessName.setText(MyApplication.shopInfo.getBusinessContactName()+getResources().getString(R.string.business_name_after));
        }else {
            mTvLogin.setVisibility(View.VISIBLE);
            mTvShopName.setVisibility(View.INVISIBLE);
            mTvBusinessName.setVisibility(View.INVISIBLE);
            mShopHead.setImageResource(R.mipmap.icon_head);
            mShopBg.setBackgroundResource(R.mipmap.bg_my_shop);
        }


    }

    //用户修改店铺信息时刷新店铺信息
    @Subscriber(tag = EventConstants.UPDATE_SHOP_INFO)
    private void UpdateBusinessInfo(int num) {
        loadData();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_back_white:
                finish();
                break;
            case R.id.tv_login:
                readyGo(LoginActivity.class);
                break;
            case R.id.tv_right:
                if (flag == 0) {
                    //点击排序
                    flag = 1;
                    mTvRight.setText(getResources().getString(R.string.complete));
                    EventBus.getDefault().post(1, EventConstants.BRAND_SORT);
                } else {
                    //点击完成
                    flag = 0;
                    mTvRight.setText(getResources().getString(R.string.order));
                    EventBus.getDefault().post(0, EventConstants.BRAND_COMPLETE);

                }
                break;

        }
    }

}
