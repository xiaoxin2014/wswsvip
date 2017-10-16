package com.wdhhr.wswsvipnew.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wdhhr.wswsvipnew.R;
import com.wdhhr.wswsvipnew.adapter.MyFragmentPagerAdapter;
import com.wdhhr.wswsvipnew.base.BaseActivity;
import com.wdhhr.wswsvipnew.constant.HomeContants;
import com.wdhhr.wswsvipnew.constant.ShopSearchConstants;
import com.wdhhr.wswsvipnew.fragment.Home_GoodsFragment;
import com.wdhhr.wswsvipnew.model.dataBase.CategoryListBean;
import com.wdhhr.wswsvipnew.utils.WindowUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ShopSearchSubActivity extends BaseActivity {


    @BindView(R.id.title)
    TextView mTvTitle;
    @BindView(R.id.tv_back)
    TextView mTvBack;
    @BindView(R.id.iv_more)
    ImageView mIvMore;
    @BindView(R.id.vp_content)
    ViewPager vpContent;
    @BindView(R.id.tabs_layout)
    TabLayout mTabsLayout;

    private String mcatetoryId;
    private String TAG = ShopSearchSubActivity.class.getSimpleName();
    private ArrayList<String> mTitleList;
    private ArrayList<Fragment> mFragmentList;
    private PopupWindow mPwSort;
    private Home_GoodsFragment mCurrentFragment;
    private List<CategoryListBean> mCategoryList;

    @Override
    protected int setViewId() {
        return R.layout.activity_shop_search_sub;
    }

    @Override
    protected void init() {
        String json_arr = getIntent().getExtras().getString("json_arr");
        String title = getIntent().getExtras().getString("title");
        int position = getIntent().getExtras().getInt("position");

        mCategoryList = new Gson().fromJson(json_arr, new TypeToken<List<CategoryListBean>>() {
        }.getType());
        CategoryListBean categoryListBean = mCategoryList.get(position);

        mTvTitle.setText(title);

        mTvBack.setVisibility(View.VISIBLE);
        mIvMore.setVisibility(View.VISIBLE);


        mFragmentList = new ArrayList<>();
        mTitleList = new ArrayList<>();


        int currentPage = -1;
        for (int i = -1; i < mCategoryList.size(); i++) {
            Fragment fragment = null;
            if (i == -1) {
                mTitleList.add(getStr(R.string.search_all));
                fragment = Home_GoodsFragment.newInstance(categoryListBean.getFid(), HomeContants.MODE_CATETORY_SHOP);
            } else {
                CategoryListBean category = mCategoryList.get(i);
                mTitleList.add(category.getCategoryName());
                fragment = Home_GoodsFragment.newInstance(category.getCategoryId(), HomeContants.MODE_CATETORY_SHOP);
            }

            if (i >= 0 && (TextUtils.equals(mCategoryList.get(i).getCategoryId()
                    , categoryListBean.getCategoryId()))) {
                currentPage = i + 1;
                mCurrentFragment = (Home_GoodsFragment) fragment;
            }

            mFragmentList.add(fragment);
        }

        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mFragmentList, mTitleList);

        vpContent.setAdapter(adapter);

        mTabsLayout.setupWithViewPager(vpContent);
        mTabsLayout.setTabsFromPagerAdapter(adapter);

        vpContent.setCurrentItem(currentPage);

        // 弹出窗
        mPwSort = WindowUtils.getAlphaPw(this, R.layout.pw_search_result_sort);
        mPwSort.setAnimationStyle(android.support.v7.appcompat.R.anim.abc_popup_enter);
    }

    @Override
    protected void initEvent() {
        vpContent.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mCurrentFragment = (Home_GoodsFragment) mFragmentList.get(position);
                mPwSort = WindowUtils.getAlphaPw(ShopSearchSubActivity.this, R.layout.pw_search_result_sort);
                mPwSort.setAnimationStyle(android.support.v7.appcompat.R.anim.abc_popup_enter);
            }
        });
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
            case R.id.iv_more:
                // 搜索页面
                Bundle bundle = new Bundle();
                bundle.putBoolean("isSingle", true);
                readyGo(ShopSearchActivity.class, bundle);
                break;
            case R.id.tv_sort:
                // 排序弹出窗
                mPwSort.showAsDropDown(mTabsLayout, 0, 0);
                break;
            case R.id.rb_default:
                // 默认排序
                WindowUtils.closePW(mPwSort);
                mCurrentFragment.setSort(ShopSearchConstants.ORDER_DEFAULT);
                break;
            case R.id.rb_profit_desc:
                // 利润降序
                WindowUtils.closePW(mPwSort);
                mCurrentFragment.setSort(ShopSearchConstants.ORDER_PROFIT_DESC);
                break;
            case R.id.rb_price_desc:
                // 价格降序
                WindowUtils.closePW(mPwSort);
                mCurrentFragment.setSort(ShopSearchConstants.ORDER_PRICE_DESC);
                break;
            case R.id.rb_price_asc:
                // 价格升序
                WindowUtils.closePW(mPwSort);
                mCurrentFragment.setSort(ShopSearchConstants.ORDER_PRICE_ASC);
                break;
            case R.id.v_close:
                WindowUtils.closePW(mPwSort);
                break;
        }
    }

}
