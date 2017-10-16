package com.wdhhr.wswsvipnew.fragment;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wdhhr.wswsvipnew.MyApplication;
import com.wdhhr.wswsvipnew.R;
import com.wdhhr.wswsvipnew.activity.AddressGoodsActivity;
import com.wdhhr.wswsvipnew.activity.InvitationCodeActivity;
import com.wdhhr.wswsvipnew.activity.LoginActivity;
import com.wdhhr.wswsvipnew.activity.MyMessageActivity;
import com.wdhhr.wswsvipnew.activity.MyMoneyActivity;
import com.wdhhr.wswsvipnew.activity.MyOrderListActivity;
import com.wdhhr.wswsvipnew.activity.MyWithdrawalsAlipayActivity;
import com.wdhhr.wswsvipnew.activity.MyWithdrawalsIntoActivity;
import com.wdhhr.wswsvipnew.activity.ProfitActivity;
import com.wdhhr.wswsvipnew.activity.SettingActivity;
import com.wdhhr.wswsvipnew.activity.SettingPersonalActivity;
import com.wdhhr.wswsvipnew.activity.ShopCartActivity;
import com.wdhhr.wswsvipnew.base.BaseFragment;
import com.wdhhr.wswsvipnew.constant.EventConstants;
import com.wdhhr.wswsvipnew.constant.MyConstants;
import com.wdhhr.wswsvipnew.constant.OrderConstants;
import com.wdhhr.wswsvipnew.constant.UrlConstants;
import com.wdhhr.wswsvipnew.dao.UserDao;
import com.wdhhr.wswsvipnew.model.UserCommonBean;
import com.wdhhr.wswsvipnew.model.cache.ProfitAmountBean;
import com.wdhhr.wswsvipnew.model.dataBase.UsersBean;
import com.wdhhr.wswsvipnew.net.NetworkManager;
import com.wdhhr.wswsvipnew.net.ApiSubscriberCallBack;
import com.wdhhr.wswsvipnew.utils.ImageUtils;
import com.wdhhr.wswsvipnew.utils.ShareUtils;
import com.wdhhr.wswsvipnew.utils.StringUtils;

import org.simple.eventbus.Subscriber;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class MyFragment extends BaseFragment {


    @BindView(R.id.riv_header)
    ImageView mRivHeader;
    @BindView(R.id.tv_login)
    TextView mTvLogin;
    @BindView(R.id.tv_account)
    TextView mTvAccount;
    @BindView(R.id.tv_general)
    TextView mTvGeneral;
    @BindView(R.id.iv_msg_dot)
    ImageView mIvMsgDot;
    @BindView(R.id.iv_shop_car_dot)
    ImageView mIvShopCarDot;
    @BindView(R.id.app_bar)
    AppBarLayout mAppBar;
    @BindView(R.id.tv_balance)
    TextView mTvBalance;
    @BindView(R.id.layout_withdrawals)
    LinearLayout mLayoutWithdrawals;
    @BindView(R.id.tv_earn_pending)
    TextView mTvEarnPending;
    @BindView(R.id.layout_profit_pending)
    LinearLayout mLayoutProfitPending;
    @BindView(R.id.tv_earn_today)
    TextView mTvEarnToday;
    @BindView(R.id.layout_profit_today)
    LinearLayout mLayoutProfitToday;
    @BindView(R.id.tv_accumulated_income)
    TextView mTvAccumulatedIncome;
    @BindView(R.id.layout_profit_total)
    LinearLayout mLayoutProfitTotal;
    @BindView(R.id.tv_my_coin)
    TextView mTvMyCoin;
    @BindView(R.id.layout_my_coin)
    LinearLayout mLayoutMyCoin;
    @BindView(R.id.tv_order_title)
    TextView mTvOrderTitle;
    @BindView(R.id.layout_all_order)
    LinearLayout mLayoutAllOrder;
    @BindView(R.id.tv_order_pending_pay)
    TextView mTvOrderPendingPay;
    @BindView(R.id.layout_pending_pay)
    RelativeLayout mLayoutPendingPay;
    @BindView(R.id.tv_order_pending_shipment)
    TextView mTvOrderPendingShipment;
    @BindView(R.id.layout_pending_shipment)
    RelativeLayout mLayoutPendingShipment;
    @BindView(R.id.tv_order_shipment)
    TextView mTvOrderShipment;
    @BindView(R.id.layout_shipped)
    RelativeLayout mLayoutShipped;
    @BindView(R.id.tv_order_cancel)
    TextView mTvOrderCancel;
    @BindView(R.id.layout_customer_service)
    RelativeLayout mLayoutCustomerService;
    @BindView(R.id.tv_my_address)
    TextView mTvMyAddress;
    @BindView(R.id.tv_customer_service_online)
    TextView mTvCustomerServiceOnline;
    @BindView(R.id.tv_inviting_friend)
    TextView mTvInvitingFriend;
    @BindView(R.id.tv_referee)
    TextView mTvReferee;
    @BindView(R.id.layout_referee)
    LinearLayout mLayoutReferee;
    @BindView(R.id.tv_setting)
    TextView mTvSetting;
    @BindView(R.id.tv_corner_pending_pay)
    TextView mTvCornerPendingPay;
    @BindView(R.id.tv_corner_pending_shipment)
    TextView mTvCornerPendingShipment;
    @BindView(R.id.tv_corner_shipment)
    TextView mTvCornerShipment;
    @BindView(R.id.tv_corner_cancel)
    TextView mTvCornerCancel;
    @BindView(R.id.view_divide)
    View mViewDivide;

    @Override
    protected int setViewId() {
        return R.layout.fragment_my;
    }

    @Override
    protected void init() {
    }

    @Override
    public void freshData() {
        if (MyApplication.getUserInfoAndLogin() != null) {
            UserDao.loadProfitData();
        }
    }

    @Subscriber(tag = EventConstants.LOG_STATUS_CHANGE)
    private void setUserInfo(int num) {
        loadData();
    }

    @Subscriber(tag = EventConstants.USER_INFO_CHANGE)
    private void setHeadIcon(int num) {
        loadData();
    }

    @Subscriber(tag = EventConstants.SHOP_NUM_LOAD_COMPLETE)
    private void shopNumChange(int num) {
        loadData();
    }

    @Override
    protected void initEvent() {
        mRivHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApplication.getUserInfo() == null) {
                    readyGo(LoginActivity.class);
                } else {
                    readyGo(SettingPersonalActivity.class);
                }
            }
        });
    }

    @Override
    protected void loadData() {

        mIvMsgDot.setVisibility(View.GONE);
        mIvShopCarDot.setVisibility(View.GONE);
        mTvCornerPendingPay.setVisibility(View.GONE);
        mTvCornerPendingShipment.setVisibility(View.GONE);
        mTvCornerShipment.setVisibility(View.GONE);
        mTvCornerCancel.setVisibility(View.GONE);

        if (MyApplication.getUserInfo() != null) {
            mTvLogin.setVisibility(View.GONE);
            mTvAccount.setVisibility(View.VISIBLE);
            mTvGeneral.setVisibility(View.VISIBLE);
            mTvBalance.setTextColor(getResources().getColor(R.color.colorPrimary));
            mTvEarnPending.setTextColor(getResources().getColor(R.color.fontTitle));
            mTvEarnToday.setTextColor(getResources().getColor(R.color.fontTitle));
            mTvAccumulatedIncome.setTextColor(getResources().getColor(R.color.fontTitle));
            mTvMyCoin.setTextColor(getResources().getColor(R.color.fontTitle));
            mTvReferee.setTextColor(getResources().getColor(R.color.fontTitle));

            UsersBean userInfo = MyApplication.getUserInfo();
            mTvAccount.setText(userInfo.getUserName());
            ImageUtils.loadCircleImageUrl(mRivHeader, userInfo.getUserPhoto(), R.mipmap.icon_head, getActivity());

            mTvBalance.setText(StringUtils.double2int(userInfo.getUserBalance()));
            mTvGeneral.setText(StringUtils.getUserLevel(userInfo.getUserLevel()));
            ProfitAmountBean profitAmount = MyApplication.mProfitAmountBean;
            if (profitAmount != null) {
                if (TextUtils.isEmpty(profitAmount.getShipmentProfit()) || profitAmount.getShipmentProfit().equals("null")) {
                    mTvEarnPending.setText("0");
                } else {
                    mTvEarnPending.setText(profitAmount.getShipmentProfit());
                }
                if (TextUtils.isEmpty(profitAmount.getTodayProfit()) || profitAmount.getTodayProfit().equals("null")) {
                    mTvEarnToday.setText("0");
                } else {
                    mTvEarnToday.setText(profitAmount.getTodayProfit());
                }
                if (TextUtils.isEmpty(profitAmount.getProfit()) || profitAmount.getProfit().equals("null")) {
                    mTvAccumulatedIncome.setText("0");
                } else {
                    mTvAccumulatedIncome.setText(profitAmount.getProfit());
                }

                if (TextUtils.isEmpty(profitAmount.getMyGold()) || profitAmount.getMyGold().equals("null")) {
                    mTvMyCoin.setText("0");
                } else {
                    mTvMyCoin.setText(profitAmount.getMyGold());
                }


            }

            if (TextUtils.isEmpty(userInfo.getRecommenderName()) || TextUtils.isEmpty(userInfo.getRecommenderPhone())) {
                mLayoutReferee.setVisibility(View.GONE);
                mViewDivide.setVisibility(View.GONE);
            } else {
                mLayoutReferee.setVisibility(View.VISIBLE);
                mViewDivide.setVisibility(View.VISIBLE);
                mTvReferee.setText(userInfo.getRecommenderName() + " " + userInfo.getRecommenderPhone());
            }


            if (MyApplication.mShopNumInfo != null) {
                // 判断是否有消息
                if (MyApplication.mShopNumInfo.getMessage() > 0) {
                    mIvMsgDot.setVisibility(View.VISIBLE);
                }

                // 判断购物车是否有内容
                if (MyApplication.mShopNumInfo.getGoodsCount() > 0) {
                    // 购物车不为空
                    mIvShopCarDot.setVisibility(View.VISIBLE);
                }

                // 设置订单角标
                if (MyApplication.mShopNumInfo.getMyPendingPayment() > 0) {
                    mTvCornerPendingPay.setVisibility(View.VISIBLE);
                    mTvCornerPendingPay.setText(MyApplication.mShopNumInfo.getMyPendingPayment() + "");
                }

                if (MyApplication.mShopNumInfo.getMyShipmentPending() > 0) {
                    mTvCornerPendingShipment.setVisibility(View.VISIBLE);
                    mTvCornerPendingShipment.setText(MyApplication.mShopNumInfo.getMyShipmentPending() + "");
                }

                if (MyApplication.mShopNumInfo.getMyDelivered() > 0) {
                    mTvCornerShipment.setVisibility(View.VISIBLE);
                    mTvCornerShipment.setText(MyApplication.mShopNumInfo.getMyDelivered() + "");
                }

                if (MyApplication.mShopNumInfo.getMyAfterSales() > 0) {
                    mTvCornerCancel.setVisibility(View.VISIBLE);
                    mTvCornerCancel.setText(MyApplication.mShopNumInfo.getMyAfterSales() + "");
                }

            }

        } else {
            mTvLogin.setVisibility(View.VISIBLE);
            mTvAccount.setVisibility(View.GONE);
            mTvGeneral.setVisibility(View.GONE);
            mRivHeader.setImageResource(R.mipmap.icon_head);
            mTvBalance.setTextColor(0xFFA4A4A4);
            mTvEarnPending.setTextColor(0xFFA4A4A4);
            mTvEarnToday.setTextColor(0xFFA4A4A4);
            mTvAccumulatedIncome.setTextColor(0xFFA4A4A4);
            mTvMyCoin.setTextColor(0xFFA4A4A4);
            mTvReferee.setTextColor(0xFFA4A4A4);
            mTvBalance.setText("- -");
            mTvEarnPending.setText("- -");
            mTvEarnToday.setText("- -");
            mTvAccumulatedIncome.setText("- -");
            mTvMyCoin.setText("- -");
            mTvReferee.setText("- -");
        }
    }

    private static final String TAG = "MyFragment";

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.tv_setting:
                readyGo(SettingActivity.class);
                return;
            case R.id.iv_more:
                // 购物车
                readyGo(ShopCartActivity.class);
                return;
        }

        Bundle bundle = new Bundle();
        int iOrderPosition = -1;

        if (MyApplication.getUserInfoAndLogin() == null) {
            return;
        }

        switch (view.getId()) {
            //点击消息
            case R.id.tv_back:
                readyGo(MyMessageActivity.class);
                break;
            //收货地址
            case R.id.tv_my_address:
                readyGo(AddressGoodsActivity.class);

//                showLoadPw();
//                NetworkManager.getApiService().getAddressList()
//                        .subscribeOn(Schedulers.io())
//                        .map(new Function<UserCommonBean, UserCommonBean>() {
//                            @Override
//                            public UserCommonBean apply(UserCommonBean UserCommonBean) throws Exception {
//                                return UserCommonBean;
//                            }
//                        })
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(new ApiSubscriberCallBack<UserCommonBean>() {
//                            @Override
//                            public void onSuccess(UserCommonBean userCommonBean) {
//                                if (userCommonBean.getStatus() == 0) {
//                                    if (userCommonBean.getData().getAddressList() != null && userCommonBean.getData().getAddressList().size() == 0) {
//                                        Bundle bundle = new Bundle();
//                                        bundle.putInt("flag", 1);
//                                        readyGo(AddressAddActivity.class,bundle);
//                                    } else {
//                                        readyGo(AddressGoodsActivity.class);
//                                    }
//                                } else {
//                                    showLongToast(userCommonBean.getMsg());
//                                }
//                                dismissLoadPw();
//                            }
//
//                            @Override
//                            public void onFailure(Throwable t) {
//                                dismissLoadPw();
//                            }
//                        });
                break;
            case R.id.tv_invitation_code:
                // 邀请码
                readyGo(InvitationCodeActivity.class);
                break;
            case R.id.tv_inviting_friend:
                //邀请好友
                String url = UrlConstants.BASE_URl
                        + "resources/H5/confirm-vip.html?userId="
                        + MyApplication.getUserInfo().getUsersId();
                dismissLoadPw();
                ShareUtils.ShowShareBord(getActivity(), url
                        , "【" + MyApplication.getUserInfo().getUserName() + "】邀请您成为VIP会员"
                        , "从此买东西省钱，卖东西挣钱，平均1天投资不到1块钱。"
                        , R.mipmap.ic_launcher, null);

                break;
            case R.id.layout_withdrawals:
                // 提现
                NetworkManager.getApiService().isBand()
                        .subscribeOn(Schedulers.io())
                        .map(new Function<UserCommonBean, UserCommonBean>() {
                            @Override
                            public UserCommonBean apply(UserCommonBean userCommonBean) throws Exception {
                                return userCommonBean;
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new ApiSubscriberCallBack<UserCommonBean>() {
                            @Override
                            public void onSuccess(UserCommonBean userCommonBean) {
                                if (userCommonBean.getStatus() == 0) {
                                    // 已绑定
                                    readyGo(MyWithdrawalsIntoActivity.class);
                                } else {
                                    // 绑定支付宝
                                    readyGo(MyWithdrawalsAlipayActivity.class);
                                }
                            }

                            @Override
                            public void onFailure(Throwable t) {
                                showLongToast("网络连接失败");
                            }
                        });

                break;
            case R.id.tv_login:
                readyGo(LoginActivity.class);
                break;
            case R.id.layout_all_order:
                // 我的订单
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
            case R.id.layout_profit_pending:
                // 待收益
                bundle.putInt(MyConstants.KEY_PROFIT_MODE, MyConstants.MODE_PROFIT_PENDDING);
                readyGo(ProfitActivity.class, bundle);
                break;
            case R.id.layout_profit_today:
                // 今日收益
                bundle.putInt(MyConstants.KEY_PROFIT_MODE, MyConstants.MODE_PROFIT_TODAY);
                readyGo(ProfitActivity.class, bundle);
                break;
            case R.id.layout_profit_total:
                // 累计收益
                bundle.putInt(MyConstants.KEY_PROFIT_MODE, MyConstants.MODE_PROFIT_TOTAL);
                readyGo(ProfitActivity.class, bundle);
                break;
            case R.id.layout_my_coin:
                // 我的微币
                readyGo(MyMoneyActivity.class);
                break;
        }

        if (iOrderPosition >= 0 && MyApplication.getUserInfoAndLogin() != null) {
            bundle.putInt("position", iOrderPosition);
            readyGo(MyOrderListActivity.class, bundle);
        }
    }

}
