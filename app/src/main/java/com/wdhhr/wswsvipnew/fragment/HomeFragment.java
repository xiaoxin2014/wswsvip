package com.wdhhr.wswsvipnew.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wdhhr.wswsvipnew.R;
import com.wdhhr.wswsvipnew.activity.ShopSearchActivity;
import com.wdhhr.wswsvipnew.adapter.MyFragmentPagerAdapter;
import com.wdhhr.wswsvipnew.base.BaseFragment;
import com.wdhhr.wswsvipnew.constant.HomeContants;
import com.wdhhr.wswsvipnew.model.ShopCommonBean;
import com.wdhhr.wswsvipnew.model.dataBase.CategoryListBean;
import com.wdhhr.wswsvipnew.net.NetworkManager;
import com.wdhhr.wswsvipnew.net.LoadStatusSubscriberCallBack;
import com.wdhhr.wswsvipnew.utils.LoadErrorUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class HomeFragment extends BaseFragment {

    @BindView(R.id.tabs_layout)
    TabLayout mTabsLayout;
    @BindView(R.id.vp_View)
    ViewPager mVpView;
    @BindView(R.id.title_content)
    TextView mTvTitle;
    @BindView(R.id.icon_title_left)
    ImageView iconTitleLeft;
    @BindView(R.id.icon_title_right)
    ImageView iconTitleRight;

    private List<CategoryListBean> mGoodTypeList;
    private ArrayList<Fragment> mFragmentList;
    private ArrayList<String> mTitleList;
    private MyFragmentPagerAdapter mFragmentPagerAdapter;
    private final String TAG = HomeFragment.class.getSimpleName();
    private BaseFragment mCurrentFragment;
    private LoadErrorUtils mLoadErrorUtils;

    @Override
    protected int setViewId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void init() {

        mTvTitle.setText(R.string.home_title);
        iconTitleLeft.setVisibility(View.GONE);
        iconTitleRight.setImageResource(R.mipmap.catetory_search);

        mGoodTypeList = new ArrayList<>();
        mFragmentList = new ArrayList<>();
        mTitleList = new ArrayList<>();

        mFragmentPagerAdapter = new MyFragmentPagerAdapter(
                getActivity().getSupportFragmentManager(), mFragmentList, mTitleList);
        mVpView.setAdapter(mFragmentPagerAdapter);

        mTabsLayout.setupWithViewPager(mVpView);
        mTabsLayout.setTabsFromPagerAdapter(mFragmentPagerAdapter);

        mLoadErrorUtils = new LoadErrorUtils(mView, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void loadData() {
        showLoadPw();
        NetworkManager.getApiService().getGoodTypes()
                .subscribeOn(Schedulers.io())
                .map(new Function<ShopCommonBean, ShopCommonBean>() {
                    @Override
                    public ShopCommonBean apply(ShopCommonBean shopCommonBean) throws Exception {
                        return shopCommonBean;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new LoadStatusSubscriberCallBack<ShopCommonBean>(mLoadErrorUtils) {
                    @Override
                    public void onSuccess(ShopCommonBean shopCommonBean) {

                        mGoodTypeList = shopCommonBean.getData().getCategoryList();
                        initFragment();

                    }

                    @Override
                    public int getListStatus(ShopCommonBean shopCommonBean) {
                        return shopCommonBean.getStatus();
                    }

                    @Override
                    public List getList(ShopCommonBean shopCommonBean) {
                        return shopCommonBean.getData().getCategoryList();
                    }

                    @Override
                    public void onComplete() {
                        dismissLoadPw();
                    }
                });
    }

    private void initFragment() {

        // 添加标题
        mTitleList.add(getStr(R.string.hot_shop));
        mTitleList.add(getStr(R.string.new_shop));
        mTitleList.add(getStr(R.string.brand_shop));

        for (int i = 0; i < mTitleList.size(); i++) {
            mTabsLayout.addTab(mTabsLayout.newTab().setText(mTitleList.get(i)));
            if (i == 2) {
                mFragmentList.add(new HomeBrandsFragment());
            } else {
                // 前3页为固定内容，mode值从1开始，0表示无设置
                mFragmentList.add(Home_GoodsFragment.newInstance(i + 1));
            }
        }

        for (int i = 0; i < mGoodTypeList.size(); i++) {
            CategoryListBean categoryListBean = mGoodTypeList.get(i);
            mTabsLayout.addTab(mTabsLayout.newTab().setText(categoryListBean.getCategoryName()));
            mTitleList.add(categoryListBean.getCategoryName());
            mFragmentList.add(Home_GoodsFragment.newInstance(categoryListBean.getCategoryId(), HomeContants.MODE_CATETORY_SHOP));
        }

        mFragmentPagerAdapter.notifyDataSetChanged();

        mCurrentFragment = (Home_GoodsFragment) mFragmentList.get(0);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.icon_title_right:
                readyGo(ShopSearchActivity.class);
                break;
        }
    }
}
