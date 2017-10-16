package com.wdhhr.wswsvipnew.fragment;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wdhhr.wswsvipnew.MyApplication;
import com.wdhhr.wswsvipnew.R;
import com.wdhhr.wswsvipnew.activity.GoodDetailsActivity;
import com.wdhhr.wswsvipnew.adapter.CommonAdapter;
import com.wdhhr.wswsvipnew.adapter.ViewHolder;
import com.wdhhr.wswsvipnew.base.BaseFragment;
import com.wdhhr.wswsvipnew.constant.EventConstants;
import com.wdhhr.wswsvipnew.constant.MyConstants;
import com.wdhhr.wswsvipnew.constant.UrlConstants;
import com.wdhhr.wswsvipnew.dao.GoodsDao;
import com.wdhhr.wswsvipnew.model.ShopCommonBean;
import com.wdhhr.wswsvipnew.model.dataBase.GoodsListBean;
import com.wdhhr.wswsvipnew.net.NetworkManager;
import com.wdhhr.wswsvipnew.net.ApiSubscriberCallBack;
import com.wdhhr.wswsvipnew.net.LoadStatusSubscriberCallBack;
import com.wdhhr.wswsvipnew.utils.LoadErrorUtils;
import com.wdhhr.wswsvipnew.utils.ShareUtils;
import com.wdhhr.wswsvipnew.widget.XListView;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/8/16.
 */

public class ShopManagerSingProductFragment extends BaseFragment {


    @BindView(R.id.lv_single_product)
    XListView mLvSingleProduct;
    private CommonAdapter<GoodsListBean> mGoodsAdapter;
    private int flag = 0;
    private List<GoodsListBean> mGoodsList = new ArrayList<>();
    private LoadErrorUtils mXlvUtils;
    private boolean isLoad;
    private int miPage;

    @Override
    protected int setViewId() {
        return R.layout.fragment_shopmanage_single_product;
    }

    @Override
    protected void init() {
        // 设置单品列表
        mGoodsAdapter = new CommonAdapter<GoodsListBean>(getActivity(), mGoodsList, R.layout.item_business_single_product) {
            @Override
            public void convert(ViewHolder helper, final int position, final GoodsListBean item) {
                helper.setText(R.id.tv_goods_desc, item.getGoodsName());
                ((TextView) helper.getView(R.id.tv_goods_price)).setText(Html.fromHtml(
                        getStrFormat(R.string.price, item.getGoodsPrice()) + "/" +
                                getStrFormat(R.string.price_cost, item.getEarn())));
                helper.setText(R.id.tv_goods_stock, getStrFormat(R.string.stockNum, item.getGoodsCount() - item.getGoodsInventory()));
                String[] picUrl = item.getGoodsPic().split(",");
                helper.setImageByUrl(R.id.iv_goods_icon, picUrl[0], R.mipmap.defalut_bg, getActivity());
                if (flag == 1) {
                    //点击排序变成1，显示置顶图标
                    helper.getView(R.id.iv_top).setVisibility(View.VISIBLE);
                } else {
                    //点击完成变成0，隐藏置顶图标
                    helper.getView(R.id.iv_top).setVisibility(View.GONE);

                }
                final CheckBox checkBox = helper.getView(R.id.cb_add_shop);
                GoodsDao.setGoodsCollection(checkBox, item, ShopManagerSingProductFragment.this);

                //点击置顶图标
                helper.getView(R.id.iv_top).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        GoodsListBean goodsListBean = mGoodsList.get(position);
                        mGoodsList.remove(position);
                        mGoodsList.add(0, goodsListBean);
                        mGoodsAdapter.refresh(mGoodsList);
                        HashMap<String, String> map = new HashMap<>();
                        map.put("goodsId", item.getGoodsId());
                        topgoods(map);
                    }
                });
                //点击分享
                helper.getView(R.id.tv_share_goods).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String[] split = item.getGoodsPic().split(",");
                        String mUrl = UrlConstants.BASE_URl + "resources/H5/trademarkListInfo.html?goodsId=" + item.getGoodsId() + "&userId=" + MyApplication.getUserInfo().getUsersId();
                        ShareUtils.ShowShareBord(MyConstants.SHARE_GOODS, getActivity(), mUrl, item.getGoodsName(), getResources().getString(R.string.share_goods_subtitle), split[0], item);
                    }
                });
                helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("json", new Gson().toJson(item));
                        readyGo(GoodDetailsActivity.class, bundle);
                    }
                });
            }
        };
        mLvSingleProduct.setAdapter(mGoodsAdapter);
        mXlvUtils = new LoadErrorUtils(mLvSingleProduct, mView, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                freshData();
            }
        });
    }

    @Override
    protected void initEvent() {
        // 上拉下拉监听
        mLvSingleProduct.setXListViewListener(new XListView.IXListViewListener() {
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

    @Subscriber(tag = EventConstants.BRAND_SORT)
    private void brandSort(int num) {
        flag = num;
        mGoodsAdapter.notifyDataSetChanged();
    }

    @Subscriber(tag = EventConstants.BRAND_COMPLETE)
    private void brandComplete(int num) {
        flag = num;
        mGoodsAdapter.notifyDataSetChanged();
    }

    //店铺管理取消收藏时刷新首页热销单品
    @Subscriber(tag = EventConstants.GOODS_INFO_CHANGE)
    private void UpdateBusinessIcon(GoodsListBean goodsListBean) {
        if (mGoodsList.contains(goodsListBean)) {
            for (int i = 0; i < mGoodsList.size(); i++) {
                if (goodsListBean.equals(mGoodsList.get(i))) {
                    mGoodsList.remove(i);
                    break;
                }
            }
            mGoodsAdapter.notifyDataSetChanged();
            if (mGoodsList.size() == 0) {
                mXlvUtils.setEmpty();
            }
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
        if (mGoodsList == null || mGoodsList.size() == 0) {
            showLoadPw();
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("businessId", MyApplication.getUserInfo().getBusinessId());
        map.put("page", miPage + "");

        NetworkManager.getApiService().getGoodsOfBusiness(map)
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
                        List<GoodsListBean> goodsList = shopCommonBean.getData().getGoodsList();
                        if (miPage == 1) {
                            mGoodsList.clear();
                        }

                        mGoodsList.addAll(goodsList);
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
                        dismissLoadPw();
                        mGoodsAdapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    protected void loadData() {


    }

    private void topgoods(Map<String, String> map) {
        NetworkManager.getApiService().topgoods(map)
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
                        Log.v(TAG, "置顶成功");
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.v(TAG, "网络繁忙");
                    }
                });
    }

    private static final String TAG = "ShopManagerSingProductF";

    @Override
    public void onClick(View view) {

    }


}
