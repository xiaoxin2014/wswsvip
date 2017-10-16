package com.wdhhr.wswsvipnew.dao;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wdhhr.wswsvipnew.MyApplication;
import com.wdhhr.wswsvipnew.R;
import com.wdhhr.wswsvipnew.activity.ApplyServiceActivity;
import com.wdhhr.wswsvipnew.activity.LogisticsDetailActivity;
import com.wdhhr.wswsvipnew.activity.LogisticsFillInActivity;
import com.wdhhr.wswsvipnew.activity.OrderConfirmActivity;
import com.wdhhr.wswsvipnew.base.BaseActivity;
import com.wdhhr.wswsvipnew.base.BaseViewInterface;
import com.wdhhr.wswsvipnew.base.OnSelTipsPwSureListener;
import com.wdhhr.wswsvipnew.constant.EventConstants;
import com.wdhhr.wswsvipnew.constant.OrderConstants;
import com.wdhhr.wswsvipnew.model.OrderCommonBean;
import com.wdhhr.wswsvipnew.model.SetOrderBean;
import com.wdhhr.wswsvipnew.model.dataBase.ExpressBean;
import com.wdhhr.wswsvipnew.model.dataBase.OrdersListBean;
import com.wdhhr.wswsvipnew.net.NetworkManager;
import com.wdhhr.wswsvipnew.net.ApiSubscriberCallBack;
import com.wdhhr.wswsvipnew.utils.NetworkUtils;
import com.wdhhr.wswsvipnew.utils.StringUtils;

import org.litepal.crud.DataSupport;
import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by felear on 2017/8/25 0025.
 */

public class OrderDao {

    private static final String TAG = "OrderDao";

    // 删除订单
    public static Flowable<OrderCommonBean> deleteOrder(String orderId) {
        HashMap<String, String> map = new HashMap<>();
        map.put("ordersId", orderId);
        return NetworkManager.getApiService().deleteOrder(map)
                .subscribeOn(Schedulers.io())
                .map(new Function<OrderCommonBean, OrderCommonBean>() {
                    @Override
                    public OrderCommonBean apply(OrderCommonBean orderCommonBean) throws Exception {
                        return orderCommonBean;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    // 修改订单
    public static Flowable<OrderCommonBean> updateOrder(HashMap<String, String> map) {
        return NetworkManager.getApiService().updateOrder(map)
                .subscribeOn(Schedulers.io())
                .map(new Function<OrderCommonBean, OrderCommonBean>() {
                    @Override
                    public OrderCommonBean apply(OrderCommonBean orderCommonBean) throws Exception {
                        return orderCommonBean;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static void setOrder(ArrayList<HashMap<String, String>> jsonList, final BaseActivity activity) {
        setOrder(jsonList, null, null, activity);
    }

    // 提交订单
    public static void setOrder(ArrayList<HashMap<String, String>> jsonList,
                                final String shopCarIds,
                                final String orderId,
                                final BaseViewInterface activity) {

        if (MyApplication.getUserInfoAndLogin() == null) {
            return;
        }

        // 下订单
        HashMap<String, String> map = new HashMap<>();
        final String json = StringUtils.getJson(jsonList);

        map.put("ordersArray", json);
        if (shopCarIds != null) {
            map.put("shopCarIds", shopCarIds);
        }

        activity.showLoadPw();
        NetworkManager.getApiService().setOrder(map)
                .subscribeOn(Schedulers.io())
                .map(new Function<SetOrderBean, SetOrderBean>() {
                    @Override
                    public SetOrderBean apply(SetOrderBean orderCommonBean) throws Exception {
                        return orderCommonBean;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriberCallBack<SetOrderBean>() {
                    @Override
                    public void onSuccess(SetOrderBean orderCommonBean) {
                        if (orderCommonBean.getStatus() == 0) {
                            Bundle bundle = new Bundle();
                            bundle.putString("ordersArray", json);
                            bundle.putString("shopCarIds", shopCarIds);
                            bundle.putString("orderId", orderId);
                            bundle.putString("json", new Gson().toJson(orderCommonBean.getData(), SetOrderBean.DataBean.class));
                            activity.readyGo(OrderConfirmActivity.class, bundle);
                        } else {
                            Log.d(TAG, orderCommonBean.getMsg());
                        }
                        activity.dismissLoadPw();
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        activity.dismissLoadPw();
                        if (NetworkUtils.isOnline()) {
                            activity.showLongToast(R.string.service_error);
                        } else {
                            activity.showLongToast(R.string.net_connect_error);
                        }
                        Log.e(TAG, "onFailure: " + t.getMessage() + "");
                    }
                });
    }

    // 查询订单
    public static Flowable<OrderCommonBean> getOrderById(HashMap<String, String> map) {
        return NetworkManager.getApiService().getOrderListById(map)
                .subscribeOn(Schedulers.io())
                .map(new Function<OrderCommonBean, OrderCommonBean>() {
                    @Override
                    public OrderCommonBean apply(OrderCommonBean orderCommonBean) throws Exception {
                        return orderCommonBean;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    // 获取快递名称列表
    public static void getExpressList() {


        List<ExpressBean> list = DataSupport.findAll(ExpressBean.class);
        if (list.size() == 0) {

            NetworkManager.getApiService().getExpresslist()
                    .subscribeOn(Schedulers.io())
                    .map(new Function<OrderCommonBean, OrderCommonBean>() {
                        @Override
                        public OrderCommonBean apply(OrderCommonBean orderCommonBean) throws Exception {
                            return orderCommonBean;
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new ApiSubscriberCallBack<OrderCommonBean>() {
                        @Override
                        public void onSuccess(OrderCommonBean orderCommonBean) {
                            DataSupport.saveAll(orderCommonBean.getData().getExpressList());
                            EventBus.getDefault().post(orderCommonBean.getData().getExpressList(), EventConstants.EXPRESS_LOAD_COMPLETE);
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            // 网络访问失败
                            EventBus.getDefault().post(null, EventConstants.EXPRESS_LOAD_COMPLETE);
                        }
                    });

        } else {
            EventBus.getDefault().post(list, EventConstants.EXPRESS_LOAD_COMPLETE);
        }
    }

    /**
     * 设置客户订单状态
     */
    public static boolean setCustomOrderStatus(List<TextView> views
            , final OrdersListBean ordersListBean
            , final BaseViewInterface baseViewInterface
            , LinearLayout layoutInsert) {

        boolean isHideBottom = false;
        if (ordersListBean == null) {
            return isHideBottom;
        }
        TextView tvLeft = views.get(0);
        TextView tvCenter = views.get(1);
        // 红色付款按钮
        TextView tvRight = views.get(2);
        // 订单状态
        TextView tvOrderStatus = views.get(3);
        TextView tvOrderOrange = views.get(4);
        TextView tvOrderBlack = views.get(5);
        TextView tvInsertName = null;
        TextView tvInsertAmount = null;

        boolean isActivity = false;
        if (layoutInsert != null) {
            tvInsertName = views.get(6);
            tvInsertAmount = views.get(7);
            isActivity = true;
        }

        tvLeft.setVisibility(View.GONE);
        tvCenter.setVisibility(View.GONE);
        tvRight.setVisibility(View.GONE);
        tvLeft.setClickable(false);
        tvCenter.setClickable(false);
        tvRight.setClickable(false);
        tvOrderOrange.setText("");
        tvOrderBlack.setText("");

        Class classCenter = null;

        double earn = 0;
        for (int i = 0; i < ordersListBean.getOrdersDetailList().size(); i++) {
            OrdersListBean.OrdersDetailListBean ordersDetailListBean = ordersListBean.getOrdersDetailList().get(i);
            String earnMount = ordersDetailListBean.getGoodsSpec().getEarn();
            if (earnMount != null && !TextUtils.equals(earnMount, "")) {
                double earnD = Double.parseDouble(earnMount);
                earn += earnD;
            }
        }

        String strEarnAmount = earn + "";

        switch (ordersListBean.getOrderProcedure()) {
            case 0:// 0申请退款
                if (ordersListBean.getSendExpressId() != null && ordersListBean.getSendExpressId().length() > 5) {
                    tvOrderStatus.setText(R.string.order_status_on_return);
                    tvCenter.setText(R.string.order_status_refunding_express);
                    tvCenter.setVisibility(View.VISIBLE);
                    classCenter = LogisticsFillInActivity.class;
                } else {
                    tvOrderStatus.setText(R.string.order_status_refunding);
                    tvCenter.setVisibility(View.VISIBLE);
                    tvCenter.setText(R.string.apply_service_reason);
                    classCenter = ApplyServiceActivity.class;
                }
                break;
            case 1:// 1退款成功
                tvOrderStatus.setText(R.string.order_status_refund_successful);
                String str = baseViewInterface.getStr(R.string.order_status_refund_successful_tip_orange);

                tvOrderOrange.setText(str + ordersListBean.getOrderPrice());
                tvOrderBlack.setText(R.string.order_status_refund_successful_tip_black);
                break;
            case 2:// 2商家拒绝退款
                tvOrderStatus.setText(R.string.order_status_refund_fail);
                tvCenter.setVisibility(View.VISIBLE);
                tvCenter.setText(R.string.order_refund_fail_result);
                classCenter = ApplyServiceActivity.class;
                break;
            case 3:// 待支付
                tvCenter.setVisibility(View.VISIBLE);
                tvCenter.setText(R.string.order_view);
                isHideBottom = true;
                tvOrderStatus.setText(R.string.order_status_pending_pay);
                break;
            case 4:// 已发货
                tvLeft.setVisibility(View.VISIBLE);
                tvLeft.setText(R.string.view_logistics);
                // 收益金额
//                tvOrderOrange.setText(baseViewInterface.getStr(R.string.profit_pending) + strEarnAmount);
                tvOrderStatus.setText(R.string.order_status_shipped);

                tvLeft.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("json", new Gson().toJson(ordersListBean));
                        baseViewInterface.readyGo(LogisticsDetailActivity.class, bundle);
                    }
                });

                // 设置Activity
                if (isActivity) {
//                    layoutInsert.setVisibility(View.VISIBLE);
                    tvInsertName.setText(R.string.profit_pending);
                    tvInsertAmount.setText(strEarnAmount);
                }
                isHideBottom = true;
                break;
            case 5: // 交易成功
                tvOrderStatus.setText(R.string.trade_successful);
                tvCenter.setVisibility(View.VISIBLE);
                tvCenter.setText(R.string.order_view);
                // 收益金额
//                tvOrderOrange.setText(baseViewInterface.getStr(R.string.profit_already) + strEarnAmount);

                // 设置Activity
                if (isActivity) {

//                    layoutInsert.setVisibility(View.VISIBLE);
                    tvInsertName.setText(R.string.profit_already);
                    tvInsertAmount.setText(strEarnAmount);
                }
                isHideBottom = true;
                break;
            case 6:// 6待发货
                tvCenter.setVisibility(View.VISIBLE);
                tvCenter.setText(R.string.order_view);
                // 收益金额

//                tvOrderOrange.setText(baseViewInterface.getStr(R.string.profit_pending) + strEarnAmount);
                tvOrderStatus.setText(R.string.order_status_pending_shipment);

                // 设置Activity
                if (isActivity) {

//                    layoutInsert.setVisibility(View.VISIBLE);
                    tvInsertName.setText(R.string.profit_pending);
                    tvInsertAmount.setText(strEarnAmount);
                }
                isHideBottom = true;
                break;
            case 8:// 退货中
                tvCenter.setVisibility(View.VISIBLE);
                tvCenter.setText(R.string.order_view);
                isHideBottom = true;
                // 填写物流信息
                tvOrderStatus.setText(R.string.order_status_refund_agree);
                break;
        }
        if (classCenter != null) {
            final Class classC = classCenter;
            tvCenter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putBoolean(OrderConstants.KEY_MODE_ORDER_BUSINESS, true);
                    bundle.putString("json", new Gson().toJson(ordersListBean));
                    baseViewInterface.readyGo(classC, bundle);
                }
            });
        }

        return isHideBottom;
    }

    /**
     * 设置我的订单状态
     * 0申请退款 1退款成功 2商家拒绝退款 3 待支付 4 已发货 5确认收货 6待发货
     */

    public static void setOrderStatus(List<TextView> views
            , final OrdersListBean ordersListBean
            , final BaseViewInterface baseViewInterface) {
        TextView tvLeft = views.get(0);
        TextView tvCenter = views.get(1);
        // 红色付款按钮
        TextView tvRight = views.get(2);
        // 订单状态
        TextView tvOrderStatus = views.get(3);
        TextView tvOrderOrange = views.get(4);
        TextView tvOrderBlack = views.get(5);

        tvLeft.setVisibility(View.GONE);
        tvCenter.setVisibility(View.GONE);
        tvRight.setVisibility(View.GONE);
        tvLeft.setClickable(false);
        tvCenter.setClickable(false);
        tvRight.setClickable(false);
        tvOrderOrange.setText("");
        tvOrderBlack.setText("");

        Class classCenter = null;

        switch (ordersListBean.getOrderProcedure()) {
            case 0:// 0申请退款
                if (ordersListBean.getSendExpressId() != null && ordersListBean.getSendExpressId().length() > 3) {
                    tvOrderStatus.setText(R.string.order_status_on_return);
                    tvCenter.setText(R.string.order_status_refunding_express);
                    tvCenter.setVisibility(View.VISIBLE);
                    classCenter = LogisticsFillInActivity.class;
                } else {
                    tvOrderStatus.setText(R.string.order_status_refunding);
                    tvOrderBlack.setText(R.string.order_status_refunding_tip);
                    tvCenter.setVisibility(View.VISIBLE);
                    tvCenter.setText(R.string.apply_service_reason);
                    classCenter = ApplyServiceActivity.class;
                }
                break;
            case 1:// 1退款成功
                tvOrderStatus.setText(R.string.order_status_refund_successful);
                String str = baseViewInterface.getStr(R.string.order_status_refund_successful_tip_orange);
                tvOrderOrange.setText(str + ordersListBean.getOrderPrice());
                tvOrderBlack.setText(R.string.order_status_refund_successful_tip_black);
                break;
            case 2:// 2商家拒绝退款
                tvOrderStatus.setText(R.string.order_status_refund_fail);
                tvCenter.setVisibility(View.VISIBLE);
                tvCenter.setText(R.string.order_refund_fail_result);
                classCenter = ApplyServiceActivity.class;
                break;
            case 3:// 待支付
                tvCenter.setVisibility(View.VISIBLE);
                tvCenter.setText(R.string.order_cancel);
                tvCenter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        baseViewInterface.showSelTipsPw(R.string.order_cancel_tip, new OnSelTipsPwSureListener() {
                            @Override
                            public void onSure() {
                                OrderDao.deleteOrder(ordersListBean.getOrdersId())
                                        .subscribe(new ApiSubscriberCallBack<OrderCommonBean>() {
                                            @Override
                                            public void onSuccess(OrderCommonBean orderCommonBean) {
                                                // 刷新数据
                                                baseViewInterface.showLongToast("删除成功");
                                                ordersListBean.setId(-1);
                                                EventBus.getDefault().post(ordersListBean, EventConstants.ORDER_STATUS_CHANGE);
                                                MyApplication.mShopNumInfo.setMyPendingPayment(
                                                        MyApplication.mShopNumInfo.getMyPendingPayment() > 1 ?
                                                                MyApplication.mShopNumInfo.getMyPendingPayment() - 1 : 0);
                                                EventBus.getDefault().post(0, EventConstants.SHOP_NUM_LOAD_COMPLETE);
                                            }

                                            @Override
                                            public void onFailure(Throwable t) {
                                                baseViewInterface.showLongToast("网络连接失败");
                                            }
                                        });

                            }
                        });

                    }
                });
                tvRight.setVisibility(View.VISIBLE);
                tvOrderStatus.setText(R.string.order_status_pending_pay);
                tvRight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // 跳转至订单确认界面
                        ArrayList<HashMap<String, String>> jsonList = new ArrayList<>();
                        List<OrdersListBean.OrdersDetailListBean> ordersDetailList = ordersListBean.getOrdersDetailList();
                        for (int i = 0; i < ordersDetailList.size(); i++) {
                            OrdersListBean.OrdersDetailListBean ordersDetail = ordersDetailList.get(i);
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("goodsId", ordersDetail.getGoodsId());
                            hashMap.put("goodsDetailId", ordersDetail.getGoodsDetailId());
                            hashMap.put("buyNum", ordersDetail.getBuyNum() + "");
                            jsonList.add(hashMap);
                        }

                        if (jsonList.size() == 0) {
                            return;
                        }
                        OrderDao.setOrder(jsonList, null, ordersListBean.getOrdersId(), baseViewInterface);
                    }
                });
                break;
            case 4:// 已发货
                tvLeft.setVisibility(View.VISIBLE);
                tvLeft.setText(R.string.view_logistics);
                tvOrderStatus.setText(R.string.order_status_shipped);
                tvLeft.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("json", new Gson().toJson(ordersListBean));
                        baseViewInterface.readyGo(LogisticsDetailActivity.class, bundle);
                    }
                });
                break;
            case 5: // 交易成功
                tvOrderStatus.setText(R.string.trade_successful);
                tvCenter.setVisibility(View.VISIBLE);
                tvCenter.setText(R.string.apply_service);
                classCenter = ApplyServiceActivity.class;
                break;
            case 6:// 6待发货
                tvOrderStatus.setText(R.string.order_status_pending_shipment);
                tvCenter.setVisibility(View.VISIBLE);
                tvCenter.setText(R.string.order_view);
                break;
            case 8:// 退货中
                tvCenter.setVisibility(View.VISIBLE);
                tvCenter.setText(R.string.logistics_fill_in);
                // 填写物流信息
                classCenter = LogisticsFillInActivity.class;
                tvOrderStatus.setText(R.string.order_status_refund_agree);
                break;
        }
        if (classCenter != null) {
            final Class classC = classCenter;
            tvCenter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("json", new Gson().toJson(ordersListBean));
                    baseViewInterface.readyGo(classC, bundle);
                }
            });
        }
    }

}
