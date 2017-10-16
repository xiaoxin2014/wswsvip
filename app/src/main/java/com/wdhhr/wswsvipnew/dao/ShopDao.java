package com.wdhhr.wswsvipnew.dao;

import android.view.View;
import android.widget.CheckBox;

import com.wdhhr.wswsvipnew.MyApplication;
import com.wdhhr.wswsvipnew.R;
import com.wdhhr.wswsvipnew.base.BaseViewInterface;
import com.wdhhr.wswsvipnew.base.OnSelTipsPwSureListener;
import com.wdhhr.wswsvipnew.constant.EventConstants;
import com.wdhhr.wswsvipnew.model.ShopCommonBean;
import com.wdhhr.wswsvipnew.model.dataBase.BrandListBean;
import com.wdhhr.wswsvipnew.model.dataBase.BusinessInfoBean;
import com.wdhhr.wswsvipnew.model.dataBase.UsersBean;
import com.wdhhr.wswsvipnew.net.NetworkManager;
import com.wdhhr.wswsvipnew.net.ApiSubscriberCallBack;
import com.wdhhr.wswsvipnew.utils.NetworkUtils;

import org.simple.eventbus.EventBus;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/8/31 0031.
 */

public class ShopDao {

    // 设置店铺收藏
    public static void setBrandCollection(CheckBox checkBox, final BrandListBean brand, final BaseViewInterface baseView) {
        if (brand.getIsCollection() == 1) {
            checkBox.setChecked(true);
        } else {
            checkBox.setChecked(false);
        }

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UsersBean userInfo = MyApplication.getUserInfo();
                if (userInfo == null) {
                    return;
                }
                final CheckBox checkBox = (CheckBox) v;
                if (checkBox.isChecked()) {
                    // 收藏商品
                    baseView.showLoadPw();
                    checkBox.setChecked(false);
                    HashMap<String, String> map = new HashMap<>();
                    map.put("brandId", brand.getBrandId());
                    map.put("businessId", userInfo.getBusinessId());
                    NetworkManager.getApiService().addBrandToBusiness(map)
                            .subscribeOn(Schedulers.io())
                            .map(new Function<ShopCommonBean, ShopCommonBean>() {
                                @Override
                                public ShopCommonBean apply(ShopCommonBean shopCommonBean) throws Exception {
                                    return shopCommonBean;
                                }
                            })
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new ApiSubscriberCallBack<ShopCommonBean>() {
                                @Override
                                public void onSuccess(ShopCommonBean shopCommonBean) {
                                    if (shopCommonBean.getStatus() == 0) {

                                        baseView.showLongToast("添加品牌成功");
                                        checkBox.setChecked(true);
                                        brand.setIsCollection(1);
                                        brand.setOnline(brand.getOnline() + 1);
                                        EventBus.getDefault().post(brand, EventConstants.BRAND_COLLECTION_CHANGE);
                                    } else {
                                        baseView.showLongToast(shopCommonBean.getMsg());
                                    }
                                    baseView.dismissLoadPw();
                                }

                                @Override
                                public void onFailure(Throwable t) {
                                    if (NetworkUtils.isOnline()) {
                                        baseView.showLongToast(R.string.service_error);
                                    } else {
                                        baseView.showLongToast(R.string.net_connect_error);
                                    }
                                    baseView.dismissLoadPw();
                                }
                            });

                } else {
                    // 取消收藏
                    checkBox.setChecked(true);
                    baseView.showSelTipsPw(R.string.collection_cancel_tip, new OnSelTipsPwSureListener() {
                        @Override
                        public void onSure() {
                            HashMap<String, String> map = new HashMap<>();
                            map.put("brandId", brand.getBrandId());
                            map.put("businessId", MyApplication.getUserInfoAndLogin().getBusinessId());
                            baseView.showLoadPw();
                            NetworkManager.getApiService().removeBrandToBusiness(map)
                                    .subscribeOn(Schedulers.io())
                                    .map(new Function<ShopCommonBean, ShopCommonBean>() {
                                        @Override
                                        public ShopCommonBean apply(ShopCommonBean shopCommonBean) throws Exception {
                                            return shopCommonBean;
                                        }
                                    })
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new ApiSubscriberCallBack<ShopCommonBean>() {
                                        @Override
                                        public void onSuccess(ShopCommonBean shopCommonBean) {
                                            if (shopCommonBean.getStatus() == 0) {

                                                baseView.showLongToast("移除品牌成功");
                                                checkBox.setChecked(false);
                                                brand.setIsCollection(0);
                                                if (brand.getOnline() > 0) {
                                                    brand.setOnline(brand.getOnline() - 1);
                                                }
                                                EventBus.getDefault().post(brand, EventConstants.BRAND_COLLECTION_CHANGE);
                                            } else {
                                                baseView.showLongToast(shopCommonBean.getMsg());
                                            }
                                            baseView.dismissLoadPw();
                                        }

                                        @Override
                                        public void onFailure(Throwable t) {
                                            if (NetworkUtils.isOnline()) {
                                                baseView.showLongToast(R.string.service_error);
                                            } else {
                                                baseView.showLongToast(R.string.net_connect_error);
                                            }
                                            baseView.dismissLoadPw();
                                        }
                                    });
                        }
                    });

                }
            }
        });
    }

    // 获取店铺信息
    public static void getShopInfo() {

        if (MyApplication.getUserInfo() == null) {
            return;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("businessId", MyApplication.getUserInfo().getBusinessId());
        NetworkManager.getApiService().getBusinessInfo(map)
                .subscribeOn(Schedulers.io())
                .map(new Function<BusinessInfoBean, BusinessInfoBean>() {
                    @Override
                    public BusinessInfoBean apply(BusinessInfoBean BusinessInfoBean) throws Exception {
                        return BusinessInfoBean;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriberCallBack<BusinessInfoBean>() {
                    @Override
                    public void onSuccess(BusinessInfoBean businessInfoBean) {
                        if (businessInfoBean.getStatus() == 0) {
                            MyApplication.shopInfo = businessInfoBean.getData();
                            getCountByBusinessId();
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                    }
                });
    }

    // 获取店铺访问量
    public static void getCountByBusinessId() {

        HashMap<String, String> map = new HashMap<>();
        map.put("businessId", MyApplication.getUserInfo().getBusinessId());
        NetworkManager.getApiService().getCountByBusinessId(map)
                .subscribeOn(Schedulers.io())
                .map(new Function<BusinessInfoBean, BusinessInfoBean>() {
                    @Override
                    public BusinessInfoBean apply(BusinessInfoBean BusinessInfoBean) throws Exception {
                        return BusinessInfoBean;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriberCallBack<BusinessInfoBean>() {
                    @Override
                    public void onSuccess(BusinessInfoBean businessInfoBean) {
                        if (businessInfoBean.getStatus() == 0 && MyApplication.shopInfo != null) {
                            MyApplication.shopInfo.setToDayOrders(businessInfoBean.getData().getToDayOrders());
                            MyApplication.shopInfo.setToDayVisit(businessInfoBean.getData().getToDayVisit());
                            MyApplication.shopInfo.setVisitSum(businessInfoBean.getData().getVisitSum());
                            MyApplication.shopInfo.setOrderPriceSum(businessInfoBean.getData().getOrderPriceSum());
                            EventBus.getDefault().post(0, EventConstants.UPDATE_SHOP_INFO);
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                    }
                });
    }

}
