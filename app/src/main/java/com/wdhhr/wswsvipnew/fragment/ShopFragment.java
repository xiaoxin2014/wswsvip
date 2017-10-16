package com.wdhhr.wswsvipnew.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.socialize.UMShareAPI;
import com.wdhhr.wswsvipnew.MyApplication;
import com.wdhhr.wswsvipnew.R;
import com.wdhhr.wswsvipnew.activity.LoginActivity;
import com.wdhhr.wswsvipnew.activity.MyOrderListActivity;
import com.wdhhr.wswsvipnew.activity.ShopConnectAgentActivity;
import com.wdhhr.wswsvipnew.activity.ShopInfomationActivity;
import com.wdhhr.wswsvipnew.activity.ShopManagerActivity;
import com.wdhhr.wswsvipnew.activity.ShopRelationBindActivity;
import com.wdhhr.wswsvipnew.base.BaseFragment;
import com.wdhhr.wswsvipnew.constant.EventConstants;
import com.wdhhr.wswsvipnew.constant.MyConstants;
import com.wdhhr.wswsvipnew.constant.OrderConstants;
import com.wdhhr.wswsvipnew.constant.UrlConstants;
import com.wdhhr.wswsvipnew.model.dataBase.UsersBean;
import com.wdhhr.wswsvipnew.utils.ImageUtils;
import com.wdhhr.wswsvipnew.utils.ShareUtils;

import org.simple.eventbus.Subscriber;

import butterknife.BindView;
import butterknife.Unbinder;


public class ShopFragment extends BaseFragment {


    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.tv_back)
    TextView mTvBack;
    @BindView(R.id.tv_back_white)
    TextView mTvBackWhite;
    @BindView(R.id.tv_right)
    TextView mTvRight;
    @BindView(R.id.shop_head)
    ImageView mShopHead;
    @BindView(R.id.tv_shop_name)
    TextView mTvShopName;
    @BindView(R.id.tv_login)
    TextView mTvLogin;
    @BindView(R.id.tv_business_name)
    TextView mTvBusinessName;
    @BindView(R.id.layout_shop)
    LinearLayout mLayoutShop;
    @BindView(R.id.shop_bg)
    RelativeLayout mShopBg;
    @BindView(R.id.tv_today_order)
    TextView mTvTodayOrder;
    @BindView(R.id.tv_today_visit)
    TextView mTvTodayVisit;
    @BindView(R.id.tv_total_visit)
    TextView mTvTotalVisit;
    @BindView(R.id.tv_total_sale)
    TextView mTvTotalSale;
    @BindView(R.id.ln_shop_manager)
    LinearLayout mLnShopManager;
    @BindView(R.id.tv_order_title)
    TextView mTvOrderTitle;
    @BindView(R.id.layout_all_order)
    LinearLayout mLayoutAllOrder;
    @BindView(R.id.tv_order_pending_pay)
    TextView mTvOrderPendingPay;
    @BindView(R.id.tv_corner_pending_pay)
    TextView mTvCornerPendingPay;
    @BindView(R.id.layout_pending_pay)
    RelativeLayout mLayoutPendingPay;
    @BindView(R.id.tv_order_pending_shipment)
    TextView mTvOrderPendingShipment;
    @BindView(R.id.tv_corner_pending_shipment)
    TextView mTvCornerPendingShipment;
    @BindView(R.id.layout_pending_shipment)
    RelativeLayout mLayoutPendingShipment;
    @BindView(R.id.tv_order_shipment)
    TextView mTvOrderShipment;
    @BindView(R.id.tv_corner_shipment)
    TextView mTvCornerShipment;
    @BindView(R.id.layout_shipped)
    RelativeLayout mLayoutShipped;
    @BindView(R.id.tv_order_cancel)
    TextView mTvOrderCancel;
    @BindView(R.id.tv_corner_cancel)
    TextView mTvCornerCancel;
    @BindView(R.id.layout_customer_service)
    RelativeLayout mLayoutCustomerService;
    @BindView(R.id.tv_relation_bind)
    TextView mTvRelationBind;
    @BindView(R.id.tv_connect_agent)
    TextView mTvConnectAgent;
    Unbinder unbinder;
    private String mUrl;
    private Bitmap mBitmap;

    @Override
    protected int setViewId() {
        return R.layout.fragment_shop;
    }

    private static final String TAG = "ShopFragment";

    @Override
    protected void init() {
        mTvBack.setVisibility(View.GONE);
        mTitle.setText(R.string.shop);
        mTvRight.setText(R.string.shop_share);
        mTvOrderTitle.setText(R.string.customer);
        mTitle.setTextColor(getResources().getColor(R.color.white));
        mTvRight.setTextColor(getResources().getColor(R.color.white));
    }

    @Override
    protected void initEvent() {
        mShopBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.getUserInfo() != null) {
                    readyGo(ShopInfomationActivity.class);
                } else {
                    readyGo(LoginActivity.class);
                }
            }
        });
    }

    @Override
    protected void loadData() {

        mTvCornerPendingPay.setVisibility(View.GONE);
        mTvCornerPendingShipment.setVisibility(View.GONE);
        mTvCornerShipment.setVisibility(View.GONE);
        mTvCornerCancel.setVisibility(View.GONE);

        if (MyApplication.shopInfo != null) {
            mTvLogin.setVisibility(View.GONE);
            mTvShopName.setVisibility(View.VISIBLE);
            mTvBusinessName.setVisibility(View.VISIBLE);
            ImageUtils.loadCircleImageUrl(mShopHead, MyApplication.shopInfo.getBusinessPic(), R.mipmap.icon_head, getActivity());
            ImageUtils.loadBackgroundUrl(mShopBg, MyApplication.shopInfo.getBusinessBackImg(), R.mipmap.bg_my_shop, getActivity());
            if (TextUtils.isEmpty(MyApplication.shopInfo.getBusinessName())) {
                mTvShopName.setText(MyApplication.getUserInfo().getUserPhone() + getResources().getString(R.string.business_name_after));
            } else {
                mTvShopName.setText(MyApplication.shopInfo.getBusinessName());
            }
            mTvBusinessName.setText(MyApplication.shopInfo.getBusinessContactName() + getResources().getString(R.string.business_name_after));
            // 设置样式
            mTvTodayOrder.setTextColor(getResources().getColor(R.color.fontTitle));
            mTvTodayVisit.setTextColor(getResources().getColor(R.color.fontTitle));
            mTvTotalVisit.setTextColor(getResources().getColor(R.color.fontTitle));
            mTvTotalSale.setTextColor(getResources().getColor(R.color.fontTitle));
            mTvTodayOrder.setText(MyApplication.shopInfo.getToDayOrders() + "");
            mTvTodayVisit.setText(MyApplication.shopInfo.getToDayVisit() + "");
            mTvTotalVisit.setText(MyApplication.shopInfo.getVisitSum() + "");
            mTvTotalSale.setText(MyApplication.shopInfo.getOrderPriceSum() + "");

            if (MyApplication.mShopNumInfo != null) {

                // 设置订单角标
                if (MyApplication.mShopNumInfo.getClientPendingPayment() > 0) {
                    mTvCornerPendingPay.setVisibility(View.VISIBLE);
                    mTvCornerPendingPay.setText(MyApplication.mShopNumInfo.getClientPendingPayment() + "");
                }

                if (MyApplication.mShopNumInfo.getClientShipmentPending() > 0) {
                    mTvCornerPendingShipment.setVisibility(View.VISIBLE);
                    mTvCornerPendingShipment.setText(MyApplication.mShopNumInfo.getClientShipmentPending() + "");
                }

                if (MyApplication.mShopNumInfo.getClientDelivered() > 0) {
                    mTvCornerShipment.setVisibility(View.VISIBLE);
                    mTvCornerShipment.setText(MyApplication.mShopNumInfo.getClientDelivered() + "");
                }

                if (MyApplication.mShopNumInfo.getClientAfterSales() > 0) {
                    mTvCornerCancel.setVisibility(View.VISIBLE);
                    mTvCornerCancel.setText(MyApplication.mShopNumInfo.getClientAfterSales() + "");
                }

            }

        } else {
            mTvLogin.setVisibility(View.VISIBLE);
            mTvShopName.setVisibility(View.INVISIBLE);
            mTvBusinessName.setVisibility(View.INVISIBLE);
            mShopHead.setImageResource(R.mipmap.icon_head);
            mShopBg.setBackgroundResource(R.mipmap.bg_my_shop);
            // 设置样式
            mTvTodayOrder.setTextColor(0xFFA4A4A4);
            mTvTodayVisit.setTextColor(0xFFA4A4A4);
            mTvTotalVisit.setTextColor(0xFFA4A4A4);
            mTvTotalSale.setTextColor(0xFFA4A4A4);

            mTvTodayOrder.setText("- -");
            mTvTodayVisit.setText("- -");
            mTvTotalVisit.setText("- -");
            mTvTotalSale.setText("- -");
        }
    }

    //用户修改店铺信息时刷新店铺信息
    @Subscriber(tag = EventConstants.UPDATE_SHOP_INFO)
    private void UpdateBusinessInfo(int num) {
        loadData();
    }

    //用户登录或者退出时刷新店铺信息
    @Subscriber(tag = EventConstants.LOG_STATUS_CHANGE)
    private void loginUpdateBusinessInfo(int num) {
        loadData();
    }

    @Subscriber(tag = EventConstants.SHOP_NUM_LOAD_COMPLETE)
    private void shopNumChange(int num) {
        loadData();
    }


    @Override
    public void onClick(View view) {
        UsersBean userInfo = MyApplication.getUserInfoAndLogin();
        if (userInfo == null) {
            readyGo(LoginActivity.class);
            return;
        }

        Bundle bundle = new Bundle();
        int iOrderPosition = -1;
        switch (view.getId()) {
            case R.id.ln_shop_manager:
                readyGo(ShopManagerActivity.class);
                break;
            case R.id.tv_right:
                //分享店铺
                mUrl = UrlConstants.BASE_URl + "resources/H5/index.html?businessId=" + userInfo.getBusinessId() + "&userId=" + userInfo.getUsersId();
                if (MyApplication.shopInfo != null) {
                    ShareUtils.ShowShareBord(MyConstants.SHARE_SHOP, getActivity(), mUrl, getResources().getString(R.string.share_shop_title, MyApplication.shopInfo.getBusinessContactName()), getResources().getString(R.string.share_shop_subtitle), MyApplication.shopInfo.getBusinessPic(), null);
                }
                break;
            case R.id.tv_relation_bind:
                //粉丝人数
                readyGo(ShopRelationBindActivity.class);
                break;
            case R.id.tv_connect_agent:
                //连接总代理
                readyGo(ShopConnectAgentActivity.class);
                break;
            case R.id.layout_all_order:
                // 客户订单
                iOrderPosition = 0;
                break;
            case R.id.tv_order_pending_pay:
                // 待付款
                iOrderPosition = 1;
                break;
            case R.id.tv_order_pending_shipment:
                // 待发货
                iOrderPosition = 2;
                break;
            case R.id.tv_order_shipment:
                // 已发货
                iOrderPosition = 3;
                break;
            case R.id.tv_order_cancel:
                // 售后
                iOrderPosition = 0;
                bundle.putBoolean(OrderConstants.KEY_MODE_ORDER_SERVICE, true);
                break;

        }
        if (iOrderPosition >= 0 && MyApplication.getUserInfoAndLogin() != null) {
            bundle.putInt("position", iOrderPosition);
            bundle.putBoolean(OrderConstants.KEY_MODE_ORDER_BUSINESS, true);
            readyGo(MyOrderListActivity.class, bundle);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(getActivity()).onActivityResult(requestCode, resultCode, data);
    }


}
