package com.wdhhr.wswsvipnew.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wdhhr.wswsvipnew.MyApplication;
import com.wdhhr.wswsvipnew.R;
import com.wdhhr.wswsvipnew.base.BaseActivity;
import com.wdhhr.wswsvipnew.constant.EventConstants;
import com.wdhhr.wswsvipnew.constant.HomeContants;
import com.wdhhr.wswsvipnew.dao.UserDao;
import com.wdhhr.wswsvipnew.model.OrderCommonBean;
import com.wdhhr.wswsvipnew.model.SetOrderBean;
import com.wdhhr.wswsvipnew.model.UserCommonBean;
import com.wdhhr.wswsvipnew.model.cache.PayResultBean;
import com.wdhhr.wswsvipnew.model.dataBase.AddressListBean;
import com.wdhhr.wswsvipnew.model.dataBase.WeibiCommonBean;
import com.wdhhr.wswsvipnew.net.NetworkManager;
import com.wdhhr.wswsvipnew.net.ApiSubscriberCallBack;
import com.wdhhr.wswsvipnew.utils.ImageUtils;
import com.wdhhr.wswsvipnew.utils.LocalUtils;
import com.wdhhr.wswsvipnew.utils.WindowUtils;
import com.wdhhr.wswsvipnew.wxapi.PayPopupWindow;

import org.simple.eventbus.Subscriber;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class OrderConfirmActivity extends BaseActivity {

    private static final String TAG = "OrderConfirmActivity";

    @BindView(R.id.title)
    TextView mTvTitle;
    @BindView(R.id.tv_back)
    TextView mTvBack;
    @BindView(R.id.tv_name_tel)
    TextView mTvNameTel;
    @BindView(R.id.tv_location)
    TextView mTvLocation;
    @BindView(R.id.layout_location)
    LinearLayout mLayoutLocation;
    @BindView(R.id.tv_location_add)
    TextView mTvLocationAdd;
    @BindView(R.id.layout_goods_detail)
    LinearLayout mLayoutGoodsDetail;
    @BindView(R.id.edit_remarks)
    EditText mEditRemarks;
    @BindView(R.id.tv_goods_amount)
    TextView mTvGoodsAmount;
    @BindView(R.id.tv_micro_deduction)
    TextView mTvMicroDeduction;
    @BindView(R.id.tv_freight)
    TextView tvFreight;
    @BindView(R.id.tv_amount)
    TextView mTvAmount;
    @BindView(R.id.tv_total_amount)
    TextView mTvTotalAmount;
    @BindView(R.id.tv_total_freight)
    TextView mTvTotalFreight;

    private AddressListBean mAddressListBean;
    private String mstrOrderNo;
    private String mStrOrdersArray;
    private SetOrderBean.DataBean mDataBean;
    private List<SetOrderBean.DataBean.OrderDetailBean> mOrderDetail;
    private PayPopupWindow mPwPay;
    private double miAmount;
    private WeibiCommonBean.DataBean.GoldListBean mGoldListBean;
    private String mStrShopCarIds;
    private int miWiBi;
    private String mStrOrderId;

    @Override
    protected int setViewId() {
        return R.layout.activity_order_confirm;
    }

    @Override
    protected void init() {
        mTvTitle.setText(R.string.order_confirm);
        mTvBack.setVisibility(View.VISIBLE);

        mStrOrdersArray = getIntent().getExtras().getString("ordersArray");
        mStrShopCarIds = getIntent().getExtras().getString("shopCarIds");
        mStrOrderId = getIntent().getExtras().getString("orderId");
        String json = getIntent().getExtras().getString("json");
        Log.e(TAG, json);

        mDataBean = new Gson().fromJson(json, SetOrderBean.DataBean.class);

        mOrderDetail = mDataBean.getOrderDetail();

        miAmount = 0;

        for (int i = 0; i < mOrderDetail.size(); i++) {

            SetOrderBean.DataBean.OrderDetailBean orderDetailBean = mOrderDetail.get(i);
            String[] specNames = orderDetailBean.getGoodsSpec().split(",");
            String specName = getStr(R.string.spec) + ":" + specNames[0];

            for (int j = 1; j < specNames.length; j++) {
                specName += "+" + specNames[j];
            }

            if (orderDetailBean.getGoodsPrice() != null && !TextUtils.equals("", orderDetailBean.getGoodsPrice())) {
                double price = Double.parseDouble(orderDetailBean.getGoodsPrice());
                miAmount += price * orderDetailBean.getBuyNum();
            }


            View view = LayoutInflater.from(this).inflate(R.layout.item_shop_car, mLayoutGoodsDetail, false);
            view.findViewById(R.id.cb_shop_car).setVisibility(View.GONE);
            ((TextView) view.findViewById(R.id.tv_title)).setText(orderDetailBean.getGoodsName());
            ((TextView) view.findViewById(R.id.tv_shop_num)).setText("×" + orderDetailBean.getBuyNum());
            ((TextView) view.findViewById(R.id.tv_price)).setText(getStr(R.string.money_unit) + orderDetailBean.getGoodsPrice());
            ((TextView) view.findViewById(R.id.tv_title_sub)).setText(specName);

            ImageView imageView = (ImageView) view.findViewById(R.id.iv_goods_pic);
            ImageUtils.loadImageUrl(imageView, orderDetailBean.getGoodsPic(), this);
            String[] split = orderDetailBean.getGoodsPic().split(",");
            if (split.length > 0) {
                ImageUtils.loadImageUrl(imageView, split[0], R.mipmap.defalut_bg, OrderConfirmActivity.this);
            }

            final int position = i;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Bundle bundle = new Bundle();
                    bundle.putString(HomeContants.GOOD_ID, mOrderDetail.get(position).getGoodsId());
                    readyGo(GoodDetailsActivity.class, bundle);
                }
            });

            mLayoutGoodsDetail.addView(view);
        }
        mPwPay = new PayPopupWindow(this);

        // 设置价格
        // 金额相关
        mTvGoodsAmount.setText(getStr(R.string.money_unit) + miAmount);
        // 微币抵扣
        if (TextUtils.equals(MyApplication.mProfitAmountBean.getMyGold(), "0") ||
                TextUtils.equals(MyApplication.mProfitAmountBean.getMyGold(), "")) {
            mTvMicroDeduction.setEnabled(false);
            mTvMicroDeduction.setText(R.string.weibi_none);
            mTvMicroDeduction.setCompoundDrawables(null, null, null, null);
        }
//        mTvMicroDeduction.setText(getStr(R.string.money_unit) + mDataBean.getWMoney());
        // 运费
        mTvTotalFreight.setText(getStrFormat(R.string.freight, "¥0.00"));
        // 总金额
        mTvAmount.setText(getStr(R.string.money_unit) + miAmount);
        mTvTotalAmount.setText(getStrFormat(R.string.total_money, miAmount + ""));

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void loadData() {
        showLoadPw();
        NetworkManager.getApiService().getAddressList()
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
                        Log.d(TAG, userCommonBean.getData().getAddressList().size() + "");
                        List<AddressListBean> addressList = userCommonBean.getData().getAddressList();
                        for (int i = 0; i < addressList.size(); i++) {
                            if (addressList.get(i).getIsDefualt() == 1) {
                                mAddressListBean = addressList.get(i);
                                setAddressUi(mAddressListBean);
                                break;
                            }
                        }
                        dismissLoadPw();
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        dismissLoadPw();
                    }
                });
    }

    @Subscriber(tag = EventConstants.UPDATE_ADDRESS)
    void getAddressList(int flag) {
        loadData();
    }

    @Subscriber(tag = EventConstants.LOCATION_STATUS_SELECT)
    private void setAddressUi(AddressListBean address) {
        if (address == null) {
            mLayoutLocation.setVisibility(View.GONE);
            mTvLocationAdd.setVisibility(View.VISIBLE);
        } else {
            mLayoutLocation.setVisibility(View.VISIBLE);
            mTvLocationAdd.setVisibility(View.GONE);
            mAddressListBean = address;
            mTvNameTel.setText(address.getName() + " " + address.getPhone());
            mTvLocation.setText(LocalUtils.getLocalName(address.getProvice()
                    , address.getCity()
                    , address.getArea(), "")
                    + address.getAddressDesc());
        }

    }

    @Subscriber(tag = EventConstants.PAY_RESULT)
    private void payResult(PayResultBean payResultBean) {
        WindowUtils.closePW(mPwPay);
        if (payResultBean.getResult() == 10000) {
            showLongToast("支付成功");
            Bundle bundle = new Bundle();
            bundle.putString("outTradeNo", mstrOrderNo);
            readyGoThenKill(TradeSuccessfulActivity.class, bundle);
        }
    }

    @Subscriber(tag = EventConstants.WEIBI_DEDUCTION)
    private void weibiDeduction(WeibiCommonBean.DataBean.GoldListBean item) {
        mTvMicroDeduction.setText(getStr(R.string.money_unit) + item.getMoney());
        mGoldListBean = item;
        miWiBi = item.getMoney();
        mTvAmount.setText(getStr(R.string.money_unit) + (miAmount - miWiBi));
        mTvTotalAmount.setText(getStrFormat(R.string.total_money, miAmount - miWiBi + ""));
    }

    @Override
    public void onClick(View view) {

        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.layout_location:
                // 跳转至地址选择页面
                bundle.putInt("isSel", 1);
                readyGo(AddressGoodsActivity.class, bundle);
                break;
            case R.id.tv_location_add:
                // 新增收货地址
                bundle.putInt("flag", 1);
                bundle.putBoolean("first", true);
                readyGo(AddressAddActivity.class, bundle);
                break;
            case R.id.layout_micro_deduction:
                // 选择微币
                Bundle wbundle = new Bundle();
                wbundle.putInt("order", 1);
                readyGo(MyMoneyActivity.class, wbundle);
                break;
            case R.id.tv_commit:
                // 提交订单
                 if (mAddressListBean == null) {
                    bundle.putInt("flag", 1);
                    bundle.putBoolean("first", true);
                    readyGo(AddressAddActivity.class, bundle);
                    return;
                }

                if (mstrOrderNo != null) {
                    // 弹出支付选择窗
                    mPwPay.setOrderNo(mstrOrderNo);
                    mPwPay.setAmount(getStr(R.string.money_unit) + (miAmount - miWiBi));
                    WindowUtils.setWindowAlpha(this, 0.6f);
                    mPwPay.showAtLocation(mTvTitle, Gravity.BOTTOM, 0, 0);
                    return;
                }

                HashMap<String, String> map = new HashMap<>();
                map.put("ordersArray", mStrOrdersArray);
                map.put("addressId", mAddressListBean.getAddress_id());
                if (mStrOrderId != null) {
                    map.put("ordersId", mStrOrderId);
                }
                // 微币
                if (mGoldListBean != null) {
                    map.put("goldId", mAddressListBean.getAddress_id());
                }
                String str = mEditRemarks.getText() + "";

                if (!TextUtils.equals(str, "")) {
                    map.put("orderDestribute", str);

                }

                if (mStrShopCarIds != null) {
                    map.put("shopCarIds", mStrShopCarIds);
                }

                NetworkManager.getApiService().creatOrder(map)
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
                                mstrOrderNo = orderCommonBean.getData().getOrderId();
                                mPwPay.setOrderNo(mstrOrderNo);
                                mPwPay.setAmount(getStr(R.string.money_unit) + (miAmount - miWiBi));
                                // 弹出支付选择窗
                                WindowUtils.setWindowAlpha(OrderConfirmActivity.this, 0.6f);
                                mPwPay.showAtLocation(mTvTitle, Gravity.BOTTOM, 0, 0);
                                UserDao.loadShopCarCount();
                            }

                            @Override
                            public void onFailure(Throwable t) {

                            }
                        });
                break;
        }

    }

}
