package com.wdhhr.wswsvipnew.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wdhhr.wswsvipnew.MyApplication;
import com.wdhhr.wswsvipnew.R;
import com.wdhhr.wswsvipnew.activity.BrandDetailActivity;
import com.wdhhr.wswsvipnew.activity.GoodDetailsActivity;
import com.wdhhr.wswsvipnew.activity.LoginActivity;
import com.wdhhr.wswsvipnew.adapter.CommonAdapter;
import com.wdhhr.wswsvipnew.adapter.ViewHolder;
import com.wdhhr.wswsvipnew.base.BaseFragment;
import com.wdhhr.wswsvipnew.constant.EventConstants;
import com.wdhhr.wswsvipnew.constant.HomeContants;
import com.wdhhr.wswsvipnew.constant.MyConstants;
import com.wdhhr.wswsvipnew.constant.UrlConstants;
import com.wdhhr.wswsvipnew.dao.GoodsDao;
import com.wdhhr.wswsvipnew.model.ShopCommonBean;
import com.wdhhr.wswsvipnew.model.dataBase.AdvertisementListBean;
import com.wdhhr.wswsvipnew.model.dataBase.GoodsListBean;
import com.wdhhr.wswsvipnew.net.LoadStatusSubscriberCallBack;
import com.wdhhr.wswsvipnew.net.NetworkManager;
import com.wdhhr.wswsvipnew.utils.DeviceUtils;
import com.wdhhr.wswsvipnew.utils.GlideImageLoader;
import com.wdhhr.wswsvipnew.utils.ImageUtils;
import com.wdhhr.wswsvipnew.utils.LoadErrorUtils;
import com.wdhhr.wswsvipnew.utils.ShareUtils;
import com.wdhhr.wswsvipnew.widget.XListView;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by felear on 2017/8/15 0015.
 */

public class Home_GoodsFragment extends BaseFragment {

    @BindView(R.id.listView)
    XListView mXListView;
    @BindView(R.id.layout_empty_no_data)
    View mLayoutEmpty;
    private Banner mBanner;

    private final String TAG = Home_GoodsFragment.class.getSimpleName();
    private int miMODE;
    private CommonAdapter<GoodsListBean> mListAdapter;
    private String mstrKey;

    // 排序方式
    private int miOdrderType;
    private int miPage = 1;
    private List<AdvertisementListBean> mAdvertisementList = new ArrayList<>();
    private List<GoodsListBean> mGoodsList = new ArrayList<>();
    private boolean isLoad;
    private String mUrl;


    private LoadErrorUtils mXlvUtils;
    private View mLayoutBanner;
    private int miHeight;

    public static Home_GoodsFragment newInstance(String key, int mode) {
        Home_GoodsFragment fragment = new Home_GoodsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(HomeContants.KEY_GOODS_TYPE_STRING, key);
        bundle.putInt(HomeContants.KEY_GOODS_TYPE_MODE, mode);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static Home_GoodsFragment newInstance(int mode) {
        Home_GoodsFragment fragment = new Home_GoodsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(HomeContants.KEY_GOODS_TYPE_MODE, mode);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int setViewId() {
        return R.layout.fragment_home_goods;
    }

    @Override
    protected void init() {
        mLayoutBanner = mInflater.inflate(R.layout.layout_banner, mXListView, false);
        mBanner = (Banner) mLayoutBanner.findViewById(R.id.banner);
        // 判断当前页面展示内容类型
        mstrKey = getArguments().getString(HomeContants.KEY_GOODS_TYPE_STRING);
        miMODE = getArguments().getInt(HomeContants.KEY_GOODS_TYPE_MODE);

        int layoutId;

        if (miMODE == HomeContants.MODE_HOT_SHOP) {
            layoutId = R.layout.item_home_good;
            miHeight = (int) (DeviceUtils.getScreenWdith() / 2.25);
            ViewGroup.LayoutParams params = mLayoutBanner.getLayoutParams();
            params.height = miHeight;
            mLayoutBanner.setLayoutParams(params);
        } else {
            layoutId = R.layout.item_home_good_type2;
        }

        // 设置商品列表
        mListAdapter = new CommonAdapter<GoodsListBean>(getContext(), mGoodsList, layoutId) {

            @Override
            public void convert(ViewHolder helper, final int position, final GoodsListBean item) {
                helper.setText(R.id.title, item.getGoodsName());
                ((TextView) helper.getView(R.id.price)).setText(
                        String.format(getResources().getString(R.string.price), item.getGoodsPrice()));
                ((TextView) helper.getView(R.id.profit)).setText(Html.fromHtml(
                        getStrFormat(R.string.price_cost, item.getEarn())));

                Log.d(TAG, item.getGoodsPrice());

                if (miMODE == HomeContants.MODE_HOT_SHOP) {
                    helper.setText(R.id.shop_num, getStrFormat(R.string.onlineSaleNum, item.getOnlineSaleNum()));
                    // 设置图片比例2.25:1
                    ImageView ivGoods = helper.getView(R.id.iv_goods_pic);
                    ViewGroup.LayoutParams layoutParams = ivGoods.getLayoutParams();
                    layoutParams.height = miHeight;
                    ivGoods.setLayoutParams(layoutParams);
                } else {
                    helper.setText(R.id.stock_num, getStrFormat(R.string.stockNum, item.getGoodsInventory()));
                }

                // 设置抢光了
                if (item.getGoodsInventory() == 0) {
                    helper.getView(R.id.tv_rob).setVisibility(View.VISIBLE);
                } else {
                    helper.getView(R.id.tv_rob).setVisibility(View.GONE);
                }

                String[] split;
                if (miMODE == HomeContants.MODE_HOT_SHOP) {
                    split = item.getBannerPic().split(",");
                } else {
                    split = item.getGoodsPic().split(",");
                }
                if (split.length > 0) {
                    helper.setImageByUrl(R.id.iv_goods_pic, split[0], getContext());
                }

                CheckBox checkBox = helper.getView(R.id.cb_add_shop);

                GoodsDao.setGoodsCollection(checkBox, item, Home_GoodsFragment.this);

                helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        Gson gson = new Gson();
                        String bean = gson.toJson(item);
                        bundle.putString("json", bean);
                        readyGo(GoodDetailsActivity.class, bundle);
                    }
                });

                helper.getView(R.id.tv_share).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (MyApplication.getUserInfo() == null) {
                            readyGo(LoginActivity.class);
                            return;
                        }
                        String[] split = item.getGoodsPic().split(",");
                        mUrl = UrlConstants.BASE_URl + "resources/H5/trademarkListInfo.html?goodsId=" + item.getGoodsId() + "&userId=" + MyApplication.getUserInfo().getUsersId();
                        ShareUtils.ShowShareBord(MyConstants.SHARE_GOODS, getActivity(), mUrl, item.getGoodsName(), getResources().getString(R.string.share_goods_subtitle), split[0], item);
                    }
                });
            }
        };

        mXListView.setAdapter(mListAdapter);
        mXlvUtils = new LoadErrorUtils(mXListView, mView, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                freshData();
            }
        });
        if (miMODE == HomeContants.MODE_SEARCH_BY_NAME_SHOP) {
            freshData();
        }
    }


    @Override
    protected void initEvent() {

        // 上拉下拉监听
        mXListView.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                freshData();
            }

            @Override
            public void onLoadMore() {
                isLoad = true;
                freshData();
            }
        });
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                AdvertisementListBean advertisementListBean = mAdvertisementList.get(position);
                if (advertisementListBean.getType() == 1) {
                    Bundle bundle = new Bundle();
                    bundle.putString(HomeContants.GOOD_ID, advertisementListBean.getJoinId() + "");
                    readyGo(GoodDetailsActivity.class, bundle);
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString(HomeContants.BRAND_ID, advertisementListBean.getJoinId() + "");
                    readyGo(BrandDetailActivity.class, bundle);
                }
            }
        });
        // 轮播图点击
//        mLoopView.setOnItemClickLisenter(new FunBanner.OnItemClickListener() {
//            @Override
//            public void onClick(int position, ImageView imageView) {
//                AdvertisementListBean advertisementListBean = mAdvertisementList.get(position);
//                if (advertisementListBean.getType() == 1) {
//                    Bundle bundle = new Bundle();
//                    bundle.putString(HomeContants.GOOD_ID, advertisementListBean.getJoinId() + "");
//                    readyGo(GoodDetailsActivity.class, bundle);
//                } else {
//                    Bundle bundle = new Bundle();
//                    bundle.putString(HomeContants.BRAND_ID, advertisementListBean.getJoinId() + "");
//                    readyGo(BrandDetailActivity.class, bundle);
//                }
//            }
//        });

    }

    //店铺管理取消收藏时刷新首页热销单品
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
            mListAdapter.notifyDataSetChanged();
        }
    }

    public void freshData() {

        if (mGoodsList.size() == 0) {
            showLoadPw();
        }

        HashMap<String, String> hashMap = new HashMap<>();
        if (isLoad) {
            miPage++;
        } else {
            miPage = 1;
        }
        isLoad = false;
        hashMap.put("page", miPage + "");
        switch (miMODE) {
            case HomeContants.MODE_HOT_SHOP:
                if (mAdvertisementList.size() == 0) {
                    hashMap.put("isGet", "0");
                } else {
                    hashMap.put("isGet", "1");
                }
                hashMap.put("isHost", "1");
                NetworkManager.getApiService().getHotShop(hashMap)
                        .subscribeOn(Schedulers.io())
                        .map(new Function<ShopCommonBean, ShopCommonBean>() {
                            @Override
                            public ShopCommonBean apply(ShopCommonBean shopCommonBean) throws Exception {
                                return shopCommonBean;
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new LoadStatusSubscriberCallBack<ShopCommonBean>(mXlvUtils) {
                            @Override
                            public void onComplete() {
                                dismissLoadPw();
                                mListAdapter.notifyDataSetChanged();
                                setBannerData();
                            }

                            @Override
                            public void onSuccess(ShopCommonBean shopCommonBean) {
                                List<GoodsListBean> goodsList = shopCommonBean.getData().getGoodsListAndSave();
                                if (miPage == 1) {
                                    mGoodsList.clear();
                                    GoodsDao.mGuessHobbyList.clear();
                                    GoodsDao.mGuessHobbyList.addAll(goodsList);
                                }
                                for (int i = 0; i < goodsList.size(); i++) {
                                    if (!mGoodsList.contains(goodsList.get(i))) {
                                        mGoodsList.add(goodsList.get(i));
                                    }
                                }

                                List<AdvertisementListBean> advertisementList = shopCommonBean.getData().getAdvertisementList();
                                if (advertisementList != null && advertisementList.size() > 0) {
                                    mAdvertisementList.addAll(advertisementList);
                                }
                            }

                            @Override
                            public int getListStatus(ShopCommonBean shopCommonBean) {
                                return shopCommonBean.getStatus();
                            }

                            @Override
                            public List getList(ShopCommonBean shopCommonBean) {
                                return shopCommonBean.getData().getGoodsList();
                            }
                        });

                break;
            default:

                if (miMODE == HomeContants.MODE_NEW_SHOP) {
                    hashMap.put("isNewArrivals", "1");
                } else if (miMODE == HomeContants.MODE_CATETORY_SHOP) {
                    hashMap.put("catetoryId", mstrKey + "");
                    hashMap.put("orderByType", miOdrderType + "");
                } else if (miMODE == HomeContants.MODE_SEARCH_BY_NAME_SHOP) {
                    hashMap.put("goodsName", mstrKey + "");
                }

                hashMap.put("pageSize", "20");

                NetworkManager.getApiService().getOtherShop(hashMap)
                        .subscribeOn(Schedulers.io())
                        .map(new Function<ShopCommonBean, ShopCommonBean>() {
                            @Override
                            public ShopCommonBean apply(ShopCommonBean shopCommonBean) throws Exception {
                                return shopCommonBean;
                            }
                        }).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new LoadStatusSubscriberCallBack<ShopCommonBean>(mXlvUtils) {
                            @Override
                            public void onComplete() {
                                dismissLoadPw();
                                mListAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onSuccess(ShopCommonBean shopCommonBean) {
                                List<GoodsListBean> goodsList = shopCommonBean.getData().getGoodsListAndSave();
                                if (miPage == 1) {
                                    mGoodsList.clear();
                                }
                                for (int i = 0; i < goodsList.size(); i++) {
                                    if (!mGoodsList.contains(goodsList.get(i))) {
                                        mGoodsList.add(goodsList.get(i));
                                    }
                                }
                            }

                            @Override
                            public int getListStatus(ShopCommonBean shopCommonBean) {
                                return shopCommonBean.getStatus();
                            }

                            @Override
                            public List getList(ShopCommonBean shopCommonBean) {
                                return shopCommonBean.getData().getGoodsList();
                            }
                        });
        }
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View view) {

    }


    private void setBannerData() {

        if (mLayoutBanner == null || mXListView == null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    setBannerData();
                }
            }, 50);
            return;
        }

        mXListView.removeHeaderView(mLayoutBanner);
        if (miMODE != 1 || mAdvertisementList.size() == 0) {
            mXlvUtils.setEmptyView(mLayoutEmpty);
            return;
        }

        mXlvUtils.setEmptyView(null);
        mXlvUtils.setEmpty();

        List<String> urlList = new ArrayList<>();

        for (int i = 0; i < mAdvertisementList.size(); i++) {
            urlList.add(ImageUtils.transformUrl(mAdvertisementList.get(i).getAdvertisementPic()));
        }

        mBanner.setImages(urlList);
        mBanner.setImageLoader(new GlideImageLoader());
        mBanner.start();
        mXListView.addHeaderView(mLayoutBanner);

    }

    public void setSort(int sortNum) {
        miOdrderType = sortNum;
        freshData();
    }

}
