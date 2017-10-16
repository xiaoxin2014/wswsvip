package com.wdhhr.wswsvipnew.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.wdhhr.wswsvipnew.MyApplication;
import com.wdhhr.wswsvipnew.R;
import com.wdhhr.wswsvipnew.activity.BrandDetailActivity;
import com.wdhhr.wswsvipnew.activity.LoginActivity;
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
import com.wdhhr.wswsvipnew.utils.DeviceUtils;
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
 * Created by felear on 2017/8/17 0017.
 */

public class HomeBrandsFragment extends BaseFragment {

    @BindView(R.id.listView)
    XListView mXListView;
    @BindView(R.id.view_alpha)
    View mVAlpha;

    private int miPage = 1;

    private CommonAdapter<BrandListBean> mListAdapter;
    private ArrayList<BrandListBean> mBrandList = new ArrayList<>();
    private boolean isLoad;
    private LoadErrorUtils mXlvUtils;
    private int miHeight;

    @Override
    protected int setViewId() {
        return R.layout.fragment_home_goods;
    }

    @Override
    protected void init() {

        miHeight = (int) (DeviceUtils.getScreenWdith() / 2.25);

        // 设置商品列表
        mListAdapter = new CommonAdapter<BrandListBean>(getContext(), mBrandList, R.layout.item_home_good) {

            @Override
            public void convert(ViewHolder helper, final int position, final BrandListBean item) {

                // 设置图片高度
                ImageView ivBrand = helper.getView(R.id.iv_goods_pic);
                ViewGroup.LayoutParams params = ivBrand.getLayoutParams();
                params.height = miHeight;
                ivBrand.setLayoutParams(params);

                helper.setText(R.id.title, item.getBrandName());
                helper.getView(R.id.layout_price).setVisibility(View.GONE);
                helper.setText(R.id.shop_num, getStrFormat(R.string.onlineSaleNum, item.getOnline()));
                helper.setImageByUrl(R.id.iv_goods_pic, item.getBrandPic(), getContext());
                //分享品牌信息
                helper.getView(R.id.tv_share).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UsersBean userInfo = MyApplication.getUserInfo();
                        if (userInfo == null) {
                            readyGo(LoginActivity.class);
                            return;
                        }
                        final String url = UrlConstants.BASE_URl + "resources/H5/index.html?brandId=" + item.getBrandId() + "&userId=" + MyApplication.getUserInfo().getUsersId();
                        ShareUtils.ShowShareBord(MyConstants.SHARE_BRAND, getActivity(), url, item.getBrandName(), getResources().getString(R.string.share_brand_subtitle), item.getBrandLogo(), null);
                    }
                });
                helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("json", new Gson().toJson(item));
                        readyGo(BrandDetailActivity.class, bundle);
                    }
                });

                CheckBox checkBox = helper.getView(R.id.cb_add_shop);
                ShopDao.setBrandCollection(checkBox, item, HomeBrandsFragment.this);
            }
        };

        mXListView.setAdapter(mListAdapter);
        mXlvUtils = new LoadErrorUtils(mXListView, mView, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                freshData();
            }
        });

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
    }

    //店铺管理取消收藏时刷新首页热销单品
    @Subscriber(tag = EventConstants.BRAND_COLLECTION_CHANGE)
    private void UpdateBusinessIcon(BrandListBean brandListBean) {
        if (mBrandList.contains(brandListBean)) {
            for (int i = 0; i < mBrandList.size(); i++) {
                if (brandListBean.equals(mBrandList.get(i))) {
                    mBrandList.remove(i);
                    mBrandList.add(i, brandListBean);
                    break;
                }
            }
            mListAdapter.notifyDataSetChanged();
        }
    }

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

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("page", miPage + "");
        hashMap.put("isBrandCounter", "1");

        NetworkManager.getApiService().getBrandShop(hashMap)
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
                        if (miPage == 1) {
                            mBrandList.clear();
                        }
                        List<BrandListBean> brandList = shopCommonBean.getData().getBrandList();
                        for (int i = 0; i < brandList.size(); i++) {
                            if (!mBrandList.contains(brandList.get(i))) {
                                mBrandList.add(brandList.get(i));
                            }
                        }
                    }

                    @Override
                    public int getListStatus(ShopCommonBean shopCommonBean) {
                        return shopCommonBean.getStatus();
                    }

                    @Override
                    public List getList(ShopCommonBean shopCommonBean) {
                        return shopCommonBean.getData().getBrandListAndSave();
                    }

                    @Override
                    public void onComplete() {
                        dismissLoadPw();
                        mListAdapter.notifyDataSetChanged();
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
