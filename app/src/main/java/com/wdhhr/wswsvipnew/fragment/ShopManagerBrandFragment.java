package com.wdhhr.wswsvipnew.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.google.gson.Gson;
import com.wdhhr.wswsvipnew.MyApplication;
import com.wdhhr.wswsvipnew.R;
import com.wdhhr.wswsvipnew.activity.BrandDetailActivity;
import com.wdhhr.wswsvipnew.adapter.CommonAdapter;
import com.wdhhr.wswsvipnew.adapter.ViewHolder;
import com.wdhhr.wswsvipnew.base.BaseFragment;
import com.wdhhr.wswsvipnew.constant.EventConstants;
import com.wdhhr.wswsvipnew.constant.MyConstants;
import com.wdhhr.wswsvipnew.constant.UrlConstants;
import com.wdhhr.wswsvipnew.dao.ShopDao;
import com.wdhhr.wswsvipnew.model.ShopCommonBean;
import com.wdhhr.wswsvipnew.model.dataBase.BrandListBean;
import com.wdhhr.wswsvipnew.model.dataBase.UsersBean;
import com.wdhhr.wswsvipnew.net.NetworkManager;
import com.wdhhr.wswsvipnew.net.LoadStatusSubscriberCallBack;
import com.wdhhr.wswsvipnew.utils.LoadErrorUtils;
import com.wdhhr.wswsvipnew.utils.ShareUtils;
import com.wdhhr.wswsvipnew.widget.XListView;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/8/16.
 */

public class ShopManagerBrandFragment extends BaseFragment {
    private static final String TAG = "ShopManagerBrandFragmen";

    @BindView(R.id.lv_shopmanage_brand)
    XListView mLvShopmanageBrand;
    private CommonAdapter<BrandListBean> mBrandAdapter;
    private int flag = 0;
    private List<BrandListBean> mBrandList = new ArrayList<>();
    private UsersBean mUserInfo;
    private boolean isLoad;
    private LoadErrorUtils mXlvUtils;
    private int miPage;

    @Override
    protected int setViewId() {
        return R.layout.fragment_shopmanage_brand;
    }

    @Override
    protected void init() {
        mUserInfo = MyApplication.getUserInfo();
        // 设置品牌列表
        mBrandAdapter = new CommonAdapter<BrandListBean>(getActivity(), mBrandList, R.layout.item_business_brand) {
            @Override
            public void convert(ViewHolder helper, final int position, final BrandListBean item) {
                helper.setImageByUrl(R.id.iv_brand_icon, item.getBrandPic(), R.mipmap.defalut_bg, getActivity());
                helper.setText(R.id.brand_name, item.getBrandName());
                helper.setText(R.id.shop_num, getStrFormat(R.string.onlineSaleNum, item.getOnline()));
                helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("json", new Gson().toJson(item));
                        readyGo(BrandDetailActivity.class, bundle);
                    }
                });

                //分享品牌信息
                helper.getView(R.id.tv_share_brand).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String url = UrlConstants.BASE_URl + "resources/H5/index.html?brandId=" + item.getBrandId() + "&userId=" + MyApplication.getUserInfo().getUsersId();
                        ShareUtils.ShowShareBord(MyConstants.SHARE_BRAND, getActivity(), url, item.getBrandName(), getResources().getString(R.string.share_brand_subtitle), item.getBrandLogo(), null);
                    }
                });
                final CheckBox checkBox = helper.getView(R.id.cb_add_shop);
                ShopDao.setBrandCollection(checkBox, item, ShopManagerBrandFragment.this);
            }
        };
        mLvShopmanageBrand.setAdapter(mBrandAdapter);
        mXlvUtils = new LoadErrorUtils(mLvShopmanageBrand, mView, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                freshData();
            }
        });
    }

    @Override
    protected void initEvent() {
        // 上拉下拉监听
        mLvShopmanageBrand.setXListViewListener(new XListView.IXListViewListener() {
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
    }

    //店铺管理取消收藏时刷新首页热销单品
    @Subscriber(tag = EventConstants.BRAND_COLLECTION_CHANGE)
    private void UpdateBusinessIcon(BrandListBean brandListBean) {
        if (mBrandList.contains(brandListBean)) {
            for (int i = 0; i < mBrandList.size(); i++) {
                if (brandListBean.equals(mBrandList.get(i))) {
                    mBrandList.remove(i);
                    break;
                }
            }
        }

        if (brandListBean.getIsCollection() == 1) {
            mBrandList.add(brandListBean);
        }
        mBrandAdapter.notifyDataSetChanged();
        if (mBrandList.size() == 0) {
            mXlvUtils.setEmpty();
        }
    }

    @Override
    public void freshData() {
        if (isLoad) {
            miPage++;
        } else {
            miPage = 1;
        }

        isLoad = false;
        if (mBrandList == null || mBrandList.size() == 0) {
            showLoadPw();
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("businessId", MyApplication.getUserInfo().getBusinessId());
        map.put("page", miPage + "");
        NetworkManager.getApiService().getBusinessBrand(map)
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
                    public void onSuccess(ShopCommonBean shopCommonBean) {

                        List<BrandListBean> brandList = shopCommonBean.getData().getBrandList();
                        if (miPage == 1) {
                            mBrandList.clear();
                        }
                        mBrandList.addAll(brandList);
                    }

                    @Override
                    public int getListStatus(ShopCommonBean shopCommonBean) {
                        return shopCommonBean.getStatus();
                    }

                    @Override
                    public List getList(ShopCommonBean shopCommonBean) {
                        return shopCommonBean.getData().getBrandList();
                    }

                    @Override
                    public void onComplete() {
                        dismissLoadPw();
                        mBrandAdapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View view) {

    }

}
