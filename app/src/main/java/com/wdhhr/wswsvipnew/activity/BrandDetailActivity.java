package com.wdhhr.wswsvipnew.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wdhhr.wswsvipnew.MyApplication;
import com.wdhhr.wswsvipnew.R;
import com.wdhhr.wswsvipnew.adapter.CommonAdapter;
import com.wdhhr.wswsvipnew.adapter.ViewHolder;
import com.wdhhr.wswsvipnew.base.BaseActivity;
import com.wdhhr.wswsvipnew.constant.EventConstants;
import com.wdhhr.wswsvipnew.constant.HomeContants;
import com.wdhhr.wswsvipnew.constant.MyConstants;
import com.wdhhr.wswsvipnew.constant.UrlConstants;
import com.wdhhr.wswsvipnew.dao.ShopDao;
import com.wdhhr.wswsvipnew.model.ShopCommonBean;
import com.wdhhr.wswsvipnew.model.dataBase.BrandListBean;
import com.wdhhr.wswsvipnew.model.dataBase.GoodsListBean;
import com.wdhhr.wswsvipnew.net.NetworkManager;
import com.wdhhr.wswsvipnew.net.LoadStatusSubscriberCallBack;
import com.wdhhr.wswsvipnew.utils.ImageUtils;
import com.wdhhr.wswsvipnew.utils.LoadErrorUtils;
import com.wdhhr.wswsvipnew.utils.ShareUtils;
import com.wdhhr.wswsvipnew.utils.StatusBarUtil;
import com.wdhhr.wswsvipnew.widget.XListView;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class BrandDetailActivity extends BaseActivity {

    @BindView(R.id.iv_brand_pic)
    ImageView mIvBrandPic;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.cb_add_shop)
    CheckBox mCbAddShop;
    @BindView(R.id.tv_share)
    TextView mTvShare;
    @BindView(R.id.tv_shopping_num)
    TextView mTvShoppingNum;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.tv_back)
    TextView tvBack;
    @BindView(R.id.tv_back_white)
    TextView tvBackWhite;
    @BindView(R.id.xlv_goods)
    XListView mXlvGoods;

    private boolean isShowTop;

    private String TAG = BrandDetailActivity.class.getSimpleName();
    private BrandListBean mCurrentbrandListBean;
    private List<GoodsListBean> mGoodsList = new ArrayList<>();
    private String mUrl;
    private String mBrandId;
    private LoadErrorUtils mLoadErrorUtils;
    private boolean isLoad;
    private CommonAdapter<GoodsListBean> mAdapter;
    private int miPage;

    @Override
    protected int setViewId() {
        return R.layout.activity_brand_detail;
    }

    @Override
    protected void init() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        mBrandId = getIntent().getExtras().getString(HomeContants.BRAND_ID);
        String json = getIntent().getExtras().getString("json");

//        tvRight.setVisibility(View.VISIBLE);
//        tvRight.setText(R.string.share_earn);

        if (json == null) {
            // 首页Banner跳转

        } else {
            mCurrentbrandListBean = new Gson().fromJson(json, BrandListBean.class);
        }

        mAdapter = new CommonAdapter<GoodsListBean>(this, mGoodsList, R.layout.item_home_good_type2) {

            @Override
            public void convert(ViewHolder helper, final int position, GoodsListBean item) {

                String[] split = item.getGoodsPic().split(",");
                String imgUrl = null;
                if (split.length > 0) {
                    imgUrl = split[0];
                }

                helper.setText(R.id.title, item.getGoodsName())
                        .setText(R.id.stock_num, getStrFormat(R.string.onlineSaleNum, item.getOnlineSaleNum()))
                        .setImageByUrl(R.id.iv_goods_pic, imgUrl, BrandDetailActivity.this);

                ((TextView) helper.getView(R.id.price)).setText(Html.fromHtml(
                        getStrFormat(R.string.price, item.getGoodsPrice())));
                ((TextView) helper.getView(R.id.profit)).setText(Html.fromHtml(
                        getStrFormat(R.string.price_cost, item.getEarn())));


                helper.getView(R.id.cb_add_shop).setVisibility(View.GONE);
//                GoodsDao.setGoodsCollection(checkBox, item, BrandDetailActivity.this);

                helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("json", new Gson().toJson(mGoodsList.get(position)));
                        readyGo(GoodDetailsActivity.class, bundle);
                    }
                });

                helper.getView(R.id.tv_share).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (MyApplication.getUserInfoAndLogin() == null) {
                            return;
                        }
                        GoodsListBean goods = mGoodsList.get(position);
                        mUrl = UrlConstants.BASE_URl + "resources/H5/trademarkListInfo.html?goodsId=" + goods.getGoodsId() + "&userId=" + MyApplication.getUserInfo().getUsersId();
                        String[] split = goods.getGoodsPic().split(",");
                        ShareUtils.ShowShareBord(MyConstants.SHARE_GOODS, BrandDetailActivity.this, mUrl, goods.getGoodsName(), getResources().getString(R.string.share_goods_subtitle), split[0], goods);
                    }
                });
            }
        };

        mXlvGoods.setAdapter(mAdapter);

        mLoadErrorUtils = new LoadErrorUtils(mXlvGoods, this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });
    }

    @Override
    protected void initEvent() {
        appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset == 0) {
                    // 完全展开 "EXPANDED"
                    //修改状态栏微亮色模式
                    StatusBarUtil.StatusBarLightMode(BrandDetailActivity.this);
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                    // 完全收缩 "COLLAPSED"
                    title.setVisibility(View.VISIBLE);
                    tvRight.setTextColor(0xFFFFFFFF);
                    tvBackWhite.setVisibility(View.VISIBLE);
                    tvBack.setVisibility(View.GONE);
                    isShowTop = true;
                    //修改状态栏微暗色模式
                    StatusBarUtil.StatusBarDarkMode(BrandDetailActivity.this);

                } else {
                    // 滑动调用
                    if (isShowTop) {
                        title.setVisibility(View.GONE);
                        tvRight.setTextColor(0xFF424242);
                        tvBackWhite.setVisibility(View.GONE);
                        tvBack.setVisibility(View.VISIBLE);
                        isShowTop = false;
                        Log.d(TAG, "isShowTop " + false);
                    }
                }

            }
        });

        // 上拉下拉监听
        mXlvGoods.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                loadData();
            }

            @Override
            public void onLoadMore() {
                isLoad = true;
                loadData();
            }
        });

    }

    //店铺管理取消收藏时刷新首页热销单品等
    @Subscriber(tag = EventConstants.GOODS_INFO_CHANGE)
    private void UpdateBusinessIcon(GoodsListBean goodsListBean) {

        if (mGoodsList.contains(goodsListBean)) {
            for (int i = 0; i < mGoodsList.size(); i++) {
                if (goodsListBean.equals(mGoodsList.get(i))) {
                    mGoodsList.remove(i);
                    mGoodsList.add(i, goodsListBean);
                    break;
                }
            }
            mAdapter.notifyDataSetChanged();
        }
    }

    //店铺管理取消收藏时刷新首页热销单品
    @Subscriber(tag = EventConstants.BRAND_COLLECTION_CHANGE)
    private void UpdateBusinessIcon(BrandListBean brandListBean) {
        if (brandListBean.equals(mCurrentbrandListBean)) {
            mCurrentbrandListBean = brandListBean;
            mTvShoppingNum.setText(getStrFormat(R.string.onlineSaleNum, mCurrentbrandListBean.getOnline()));
        }
    }

    @Override
    protected void loadData() {

        if (mCurrentbrandListBean == null) {
            return;
        }

        ShopDao.setBrandCollection(mCbAddShop, mCurrentbrandListBean, this);

        mTvTitle.setText(mCurrentbrandListBean.getBrandName());
        mTvShoppingNum.setText(getStrFormat(R.string.onlineSaleNum, mCurrentbrandListBean.getOnline()));
        ImageUtils.loadImageUrl(mIvBrandPic, mCurrentbrandListBean.getBrandPic(), this);
        // initTileBar
        title.setText(mCurrentbrandListBean.getBrandName());
        title.setTextColor(0xFFFFFFFF);
        title.setVisibility(View.GONE);

        if (isLoad) {
            miPage++;
        } else {
            miPage = 1;
        }

        isLoad = false;

        if (mGoodsList.size() == 0) {
            showLoadPw();
        }

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("brandId", mCurrentbrandListBean.getBrandId());
        hashMap.put("page", miPage + "");
        NetworkManager.getApiService().getBrandGoodsList(hashMap)
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
                        if (miPage == 1) {
                            mGoodsList.clear();
                        }
                        mGoodsList.addAll(shopCommonBean.getData().getGoodsList());
                    }

                    @Override
                    public int getListStatus(ShopCommonBean shopCommonBean) {
                        return shopCommonBean.getStatus();
                    }

                    @Override
                    public List getList(ShopCommonBean shopCommonBean) {
                        return shopCommonBean.getData().getGoodsList();
                    }

                    @Override
                    public void onComplete() {
                        mAdapter.notifyDataSetChanged();
                        dismissLoadPw();
                    }
                });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
            case R.id.tv_back_white:
                finish();
                break;
            //分享品牌
            case R.id.tv_right:
            case R.id.tv_share:
                if (MyApplication.getUserInfo() != null) {
                    mUrl = UrlConstants.BASE_URl + "resources/H5/index.html?brandId=" + mBrandId + " & userId = " + MyApplication.getUserInfo().getUsersId();
                    ShareUtils.ShowShareBord(MyConstants.SHARE_BRAND, this, mUrl, mCurrentbrandListBean.getBrandName()
                            , getResources().getString(R.string.share_brand_subtitle), mCurrentbrandListBean.getBrandLogo(), null);
                } else {
                    readyGo(LoginActivity.class);
                }
                break;

        }
    }
}
