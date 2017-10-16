package com.wdhhr.wswsvipnew.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wdhhr.wswsvipnew.MyApplication;
import com.wdhhr.wswsvipnew.R;
import com.wdhhr.wswsvipnew.activity.OrderDetailActivity;
import com.wdhhr.wswsvipnew.adapter.CommonAdapter;
import com.wdhhr.wswsvipnew.adapter.ViewHolder;
import com.wdhhr.wswsvipnew.base.BaseFragment;
import com.wdhhr.wswsvipnew.constant.EventConstants;
import com.wdhhr.wswsvipnew.constant.OrderConstants;
import com.wdhhr.wswsvipnew.model.OrderCommonBean;
import com.wdhhr.wswsvipnew.model.dataBase.OrdersListBean;
import com.wdhhr.wswsvipnew.net.NetworkManager;
import com.wdhhr.wswsvipnew.dao.OrderDao;
import com.wdhhr.wswsvipnew.net.LoadStatusSubscriberCallBack;
import com.wdhhr.wswsvipnew.utils.ImageUtils;
import com.wdhhr.wswsvipnew.utils.LoadErrorUtils;
import com.wdhhr.wswsvipnew.widget.XListView;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by felear on 2017/8/25 0025.
 */

public class OrderListFragment extends BaseFragment {

    @BindView(R.id.listView)
    XListView mXListView;

    private static final String TAG = "OrderListFragment";

    private int miModeOrder;
    private int miPage = 1;
    private CommonAdapter<OrdersListBean> mAdapter;
    private List<OrdersListBean> mOrdersList = new ArrayList<>();
    private HashMap<String, String> mMapApi;
    private boolean isLoad;
    private LoadErrorUtils mXlvUtils;
    private boolean mIsBusiness;
    private boolean mIsService;
    private Handler mHandler;

    public static OrderListFragment newInstance(int modeOrder, boolean isBusiness, boolean isService) {
        OrderListFragment fragment = new OrderListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(OrderConstants.KEY_MODE_ORDER, modeOrder);
        bundle.putBoolean(OrderConstants.KEY_MODE_ORDER_BUSINESS, isBusiness);
        bundle.putBoolean(OrderConstants.KEY_MODE_ORDER_SERVICE, isService);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int setViewId() {
        return R.layout.fragment_order_list;
    }

    @Override
    protected void init() {

        mAdapter = new CommonAdapter<OrdersListBean>(getContext(), mOrdersList, R.layout.item_order_list) {

            @Override
            public void convert(ViewHolder helper, final int position, OrdersListBean item) {

                helper.setText(R.id.tv_order_no, getStr(R.string.order_no) + item.getOrdersId());

                // 多条商品
                List<OrdersListBean.OrdersDetailListBean> ordersDetailList = item.getOrdersDetailList();
                LinearLayout layoutGoods = helper.getView(R.id.layout_goods_detail);
                layoutGoods.removeAllViews();
                int num = 0;
                if (ordersDetailList != null && ordersDetailList.size() > 0) {
                    for (int i = 0; i < ordersDetailList.size(); i++) {

                        OrdersListBean.OrdersDetailListBean ordersDetailListBean = ordersDetailList.get(i);

                        num += ordersDetailListBean.getBuyNum();
                        List<String> specList = ordersDetailListBean.getGoodsSpec().getSpecList();
                        String specName = getStr(R.string.spec) + ":" + specList.get(0);
                        if (specList.size() > 0) {
                            for (int j = 1; j < specList.size(); j++) {
                                specName += "+" + specList.get(j);
                            }
                        }

                        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_order_inner, layoutGoods, false);
                        ((TextView) view.findViewById(R.id.tv_title)).setText(ordersDetailListBean.getGoods().getGoodsName());
                        ((TextView) view.findViewById(R.id.tv_price_num)).setText(String.format(getStr(R.string.order_price_num)
                                , ordersDetailListBean.getGoods().getGoodsPrice(), ordersDetailListBean.getBuyNum()));
                        ((TextView) view.findViewById(R.id.tv_sub_title)).setText(specName);

                        String[] split = ordersDetailListBean.getGoodsSpec().getGoodsDetailPic().split(",");
                        if (split.length > 0) {
                            ImageView imageView = (ImageView) view.findViewById(R.id.iv_goods_pic);
                            ImageUtils.loadImageUrl(imageView, split[0], getContext());
                        }
                        layoutGoods.addView(view);
                    }
                }

                helper.setText(R.id.tv_order_info, String.format(getStr(R.string.order_info)
                        , num
                        , item.getPrice()
                        , item.getExpressMoney()));

                TextView tvLeft = helper.getView(R.id.tv_bottom_left);
                TextView tvCenter = helper.getView(R.id.tv_bottom_center);
                // 红色付款按钮
                TextView tvRight = helper.getView(R.id.tv_bottom_right);
                // 订单状态
                TextView tvOrderStatus = helper.getView(R.id.tv_order_status);
                TextView tvOrange = helper.getView(R.id.tv_bottom_orange);
                TextView tvBlack = helper.getView(R.id.tv_bottom_black);
                ArrayList<TextView> textViews = new ArrayList<>();
                textViews.add(tvLeft);
                textViews.add(tvCenter);
                textViews.add(tvRight);
                textViews.add(tvOrderStatus);
                textViews.add(tvOrange);
                textViews.add(tvBlack);

                if (mIsBusiness) {
                    OrderDao.setCustomOrderStatus(textViews, item, OrderListFragment.this, null);
                } else {
                    OrderDao.setOrderStatus(textViews, item, OrderListFragment.this);
                }

                helper.getConvertView().setClickable(false);
                if (num > 0) {
                    helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            OrdersListBean ordersListBean = mOrdersList.get(position);
                            String json = new Gson().toJson(ordersListBean, OrdersListBean.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("json", json);
                            bundle.putBoolean(OrderConstants.KEY_MODE_ORDER_BUSINESS, mIsBusiness);
                            readyGo(OrderDetailActivity.class, bundle);
                        }
                    });
                }

            }
        };

        mXListView.setAdapter(mAdapter);
        mXlvUtils = new LoadErrorUtils(mXListView, mView, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                freshData();
            }
        });
    }

    private void setMapApi() {
        // 设置数据请求方式
        mIsBusiness = getArguments().getBoolean(OrderConstants.KEY_MODE_ORDER_BUSINESS, false);
        mIsService = getArguments().getBoolean(OrderConstants.KEY_MODE_ORDER_SERVICE, false);

        String strBusiness = mIsBusiness ? "1" : "0";
        String strService = mIsService ? "1" : "0";

        miModeOrder = getArguments().getInt(OrderConstants.KEY_MODE_ORDER);

        mMapApi = new HashMap<>();
        mMapApi.put("isCheckBusinessOrders", strBusiness);
        mMapApi.put("isCheckMyselfOrder", strService);

        if (miModeOrder == OrderConstants.MODE_ORDER_ALL) {
            mMapApi.put("orderStatus", "-1");
        } else {
            mMapApi.put("orderStatus", "" + miModeOrder);
        }
    }

    @Subscriber(tag = EventConstants.ORDER_STATUS_CHANGE)
    private void orderStatusChange(OrdersListBean ordersListBean) {
        if (mOrdersList.contains(ordersListBean)) {

            for (int i = 0; i < mOrdersList.size(); i++) {
                if (mOrdersList.get(i).equals(ordersListBean)) {
                    mOrdersList.remove(i);
                    // 修改或删除
                    if (ordersListBean.getId() != -1) {
                        mOrdersList.add(i, ordersListBean);
                        Log.d(TAG, "orderStatusChange: " + ordersListBean.getSendExpressId());
                    }
                    break;
                }
            }

            mAdapter.notifyDataSetChanged();
        }
    }

    private void setCount(int count) {
        // 刷新售后订单数据
        if (mIsService && miModeOrder == OrderConstants.MODE_ORDER_ALL) {
            if (mIsBusiness) {
                MyApplication.mShopNumInfo.setMyAfterSales(count);
            } else {
                MyApplication.mShopNumInfo.setClientAfterSales(count);
            }
        }

        // 刷新客户订单
        switch (miModeOrder) {
            case OrderConstants.MODE_ORDER_PENDING_PAY:
                // 待付款
                if (mIsBusiness) {
                    MyApplication.mShopNumInfo.setClientPendingPayment(count);
                } else {
                    MyApplication.mShopNumInfo.setMyPendingPayment(count);
                }
                break;
            case OrderConstants.MODE_ORDER_PENDING_SHIPMENT:
                // 待发货
                if (mIsBusiness) {
                    MyApplication.mShopNumInfo.setClientShipmentPending(count);
                } else {
                    MyApplication.mShopNumInfo.setMyShipmentPending(count);
                }
                break;
            case OrderConstants.MODE_ORDER_SHIPPED:
                // 已发货
                if (mIsBusiness) {
                    MyApplication.mShopNumInfo.setClientDelivered(count);
                } else {
                    MyApplication.mShopNumInfo.setMyDelivered(count);
                }
                break;
        }

        EventBus.getDefault().post(0, EventConstants.SHOP_NUM_LOAD_COMPLETE);

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

    public void freshData() {

        if (isLoad) {
            miPage++;
        } else {
            miPage = 1;
        }

        isLoad = false;
        if (mOrdersList == null || mOrdersList.size() == 0) {
            showLoadPw();
        }
        if (mMapApi == null) {
            setMapApi();
        }
        mMapApi.put("page", miPage + "");
        NetworkManager.getApiService().getOrderList(mMapApi)
                .subscribeOn(Schedulers.io())
                .map(new Function<OrderCommonBean, OrderCommonBean>() {
                    @Override
                    public OrderCommonBean apply(OrderCommonBean orderCommonBean) throws Exception {
                        return orderCommonBean;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new LoadStatusSubscriberCallBack<OrderCommonBean>(mXlvUtils) {
                    @Override
                    public void onSuccess(OrderCommonBean orderCommonBean) {
                        List<OrdersListBean> ordersList = orderCommonBean.getData().getOrdersList();
                        if (miPage == 1) {
                            mOrdersList.clear();
                        }
                        for (int i = 0; i < ordersList.size(); i++) {
                            if (ordersList.get(i).getOrderProcedure() != 7 &&
                                    !mOrdersList.contains(ordersList.get(i))) {
                                mOrdersList.add(ordersList.get(i));
                            }
                        }
                        setCount(orderCommonBean.getData().getCount());

                    }

                    @Override
                    public int getListStatus(OrderCommonBean orderCommonBean) {
                        return orderCommonBean.getStatus();
                    }

                    @Override
                    public List getList(OrderCommonBean orderCommonBean) {
                        return orderCommonBean.getData().getOrdersList();
                    }

                    @Override
                    public void onComplete() {
                        mAdapter.notifyDataSetChanged();
                        dismissLoadPw();
                        mTimeFreshData = System.currentTimeMillis();
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
