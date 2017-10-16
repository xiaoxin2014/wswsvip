package com.wdhhr.wswsvipnew.dao;

import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wdhhr.wswsvipnew.MyApplication;
import com.wdhhr.wswsvipnew.R;
import com.wdhhr.wswsvipnew.activity.GoodDetailsActivity;
import com.wdhhr.wswsvipnew.activity.LoginActivity;
import com.wdhhr.wswsvipnew.base.BaseActivity;
import com.wdhhr.wswsvipnew.base.BaseViewInterface;
import com.wdhhr.wswsvipnew.base.OnSelTipsPwSureListener;
import com.wdhhr.wswsvipnew.constant.EventConstants;
import com.wdhhr.wswsvipnew.constant.MyConstants;
import com.wdhhr.wswsvipnew.constant.UrlConstants;
import com.wdhhr.wswsvipnew.model.ShopCommonBean;
import com.wdhhr.wswsvipnew.model.dataBase.GoodsListBean;
import com.wdhhr.wswsvipnew.model.dataBase.UsersBean;
import com.wdhhr.wswsvipnew.net.NetworkManager;
import com.wdhhr.wswsvipnew.net.ApiSubscriberCallBack;
import com.wdhhr.wswsvipnew.utils.DeviceUtils;
import com.wdhhr.wswsvipnew.utils.ImageUtils;
import com.wdhhr.wswsvipnew.utils.NetworkUtils;
import com.wdhhr.wswsvipnew.utils.ShareUtils;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/8/30 0030.
 */

public class GoodsDao {
    private static String mUrl;
    public final static List<GoodsListBean> mGuessHobbyList = new ArrayList<>();
    private static final String TAG = "GoodsDao";

    public static List<GoodsListBean> setGuessHobby(final BaseActivity activity, String goodsId, LinearLayout mLayoutGoods) {

        List<GoodsListBean> goodsList = new ArrayList<>(mGuessHobbyList.size());
        for (int i = 0; i < mGuessHobbyList.size(); i++) {
            if (!TextUtils.equals(goodsId, mGuessHobbyList.get(i).getGoodsId())) {
                goodsList.add(mGuessHobbyList.get(i));
            }
        }

        Log.d(TAG, "setGuessHobby: " + goodsList);

        setGoodsItems(activity, goodsList, mLayoutGoods);
        return goodsList;
    }

    public static void setGoodsItems(final BaseActivity activity, final List<GoodsListBean> mGoodsList, LinearLayout mLayoutGoods) {
        mLayoutGoods.removeAllViews();
        LinearLayout.LayoutParams divideParams = new LinearLayout
                .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DeviceUtils.dip2px(0.5f));
        divideParams.leftMargin = DeviceUtils.dip2px(12);
        divideParams.rightMargin = DeviceUtils.dip2px(12);

        for (int i = 0; i < mGoodsList.size(); i++) {
            final GoodsListBean goods = mGoodsList.get(i);
            View view = LayoutInflater.from(activity)
                    .inflate(R.layout.item_home_good_type2, mLayoutGoods, false);

            ((TextView) view.findViewById(R.id.title)).setText(goods.getGoodsName());
            ((TextView) view.findViewById(R.id.price)).setText(Html.fromHtml(activity.
                    getStrFormat(R.string.price, goods.getGoodsPrice())));
            ((TextView) view.findViewById(R.id.profit)).setText(Html.fromHtml(activity.
                    getStrFormat(R.string.price_cost, goods.getEarn())));
            ((TextView) view.findViewById(R.id.stock_num)).setText(activity.getStrFormat(R.string.onlineSaleNum, goods.getOnlineSaleNum()));
            ImageView imageView = (ImageView) view.findViewById(R.id.iv_goods_pic);
            String[] split = goods.getGoodsPic().split(",");
            if (split.length > 0) {
                ImageUtils.loadImageUrl(imageView, split[0], activity);
            }

            CheckBox checkBox = (CheckBox) view.findViewById(R.id.cb_add_shop);
            setGoodsCollection(checkBox, goods, activity);

            final int position = i;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("json", new Gson().toJson(mGoodsList.get(position)));
                    activity.readyGo(GoodDetailsActivity.class, bundle);
                }
            });

            view.findViewById(R.id.tv_share).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (MyApplication.getUserInfo() == null) {
                        activity.readyGo(LoginActivity.class);
                        return;
                    }
                    mUrl = UrlConstants.BASE_URl + "resources/H5/trademarkListInfo.html?goodsId=" + goods.getGoodsId() + "&userId=" + MyApplication.getUserInfo().getUsersId();
                    String[] split = goods.getGoodsPic().split(",");
                    ShareUtils.ShowShareBord(MyConstants.SHARE_GOODS, activity, mUrl, goods.getGoodsName(), activity.getResources().getString(R.string.share_goods_subtitle), split[0], goods);
                }
            });

            // 添加分割线
            View divide = new View(activity);
            divide.setLayoutParams(divideParams);
            divide.setBackgroundColor(0xffeeeeee);
            mLayoutGoods.addView(divide);
            mLayoutGoods.addView(view);
        }

    }


    public static void setGoodsCollection(CheckBox checkBox, final GoodsListBean goodsListBean, final BaseViewInterface baseView) {

        if (goodsListBean.getIsCollection() == 1) {
            checkBox.setChecked(true);
        } else {
            checkBox.setChecked(false);
        }

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UsersBean userInfo = MyApplication.getUserInfoAndLogin();
                if (userInfo == null) {
                    return;
                }

                final CheckBox checkBox = (CheckBox) v;
                if (checkBox.isChecked()) {
                    // 收藏商品
                    baseView.showLoadPw();
                    checkBox.setChecked(false);
                    HashMap<String, String> map = new HashMap<>();
                    map.put("goodsId", goodsListBean.getGoodsId());
                    map.put("businessId", userInfo.getBusinessId());
                    NetworkManager.getApiService().addGoodsToBusiness(map)
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
                                    baseView.showLongToast("添加商品成功");
                                    goodsListBean.setIsCollection(1);
                                    goodsListBean.setOnlineSaleNum(goodsListBean.getOnlineSaleNum() + 1);
                                    EventBus.getDefault().post(goodsListBean, EventConstants.GOODS_INFO_CHANGE);
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
                            map.put("goodsId", goodsListBean.getGoodsId());
                            map.put("businessId", MyApplication.getUserInfoAndLogin().getBusinessId());
                            baseView.showLoadPw();
                            NetworkManager.getApiService().removeGoodsToBusiness(map)
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
                                                baseView.showLongToast("移除商品成功");
                                                checkBox.setChecked(false);
                                                goodsListBean.setIsCollection(0);
                                                int num = goodsListBean.getOnlineSaleNum();
                                                if (num > 0) {
                                                    goodsListBean.setOnlineSaleNum(num - 1);
                                                }
                                                EventBus.getDefault().post(goodsListBean, EventConstants.GOODS_INFO_CHANGE);
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


}
