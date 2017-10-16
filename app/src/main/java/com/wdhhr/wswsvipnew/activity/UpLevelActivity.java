package com.wdhhr.wswsvipnew.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.wdhhr.wswsvipnew.MyApplication;
import com.wdhhr.wswsvipnew.R;
import com.wdhhr.wswsvipnew.base.BaseActivity;
import com.wdhhr.wswsvipnew.constant.EventConstants;
import com.wdhhr.wswsvipnew.constant.UrlConstants;
import com.wdhhr.wswsvipnew.dao.UserDao;
import com.wdhhr.wswsvipnew.model.OrderCommonBean;
import com.wdhhr.wswsvipnew.model.cache.PayResultBean;
import com.wdhhr.wswsvipnew.model.dataBase.UsersBean;
import com.wdhhr.wswsvipnew.net.NetworkManager;
import com.wdhhr.wswsvipnew.net.ApiSubscriberCallBack;
import com.wdhhr.wswsvipnew.utils.WindowUtils;
import com.wdhhr.wswsvipnew.wxapi.PayPopupWindow;

import org.simple.eventbus.Subscriber;

import java.util.HashMap;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class UpLevelActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView mTvTitle;
    @BindView(R.id.tv_back)
    TextView mTvBack;
    @BindView(R.id.webView)
    WebView mWebView;

    private static final String TAG = "UpLevelActivity";
    private String mstrAccount;
    private String mstrPwd;
    private PayPopupWindow mPwPay;
    private String mstrPayTradeNo;

    @Override
    protected int setViewId() {
        return R.layout.activity_up_level;
    }

    @Override
    protected void init() {
        mTvBack.setVisibility(View.VISIBLE);
        mTvTitle.setText(R.string.user_up);

        if (getIntent().getExtras() != null) {
            mstrAccount = getIntent().getExtras().getString("account");
            mstrPwd = getIntent().getExtras().getString("pwd");
        }
    }

    @Override
    protected void initEvent() {
        mWebView.loadUrl(UrlConstants.BASE_URl
                + "resources/H5/confirm-vipApp.html");
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                Log.d(TAG, "shouldOverrideUrlLoading: " + url);
                return true;
            }
        });

    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_buy:
                if (MyApplication.getUserInfo() == null) {
                    UserDao.login(mstrAccount, mstrPwd, 1, this);
                } else {
                    startPay(MyApplication.getUserInfo());
                }
                break;
        }
    }

    @Subscriber(tag = EventConstants.USER_INFO_LOAD)
    private void startPay(UsersBean userInfo) {
        if (mstrPayTradeNo != null) {
            showPayPw();
            return;
        }

        showLoadPw();
        HashMap<String, String> map = new HashMap<>();
        map.put("ordersArray", "[\n  {\n    \"goodsId\" : \"0Jr20022797b105208888\",\n    \"goodsDetailId\" : \"lD811ZW74175p6207G205\",\n    \"buyNum\" : 1\n  }\n]");
        map.put("addressId", "");
        map.put("isInvitation", "");
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
                        dismissLoadPw();
                        mstrPayTradeNo = orderCommonBean.getData().getOrderId();
                        showPayPw();
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        dismissLoadPw();
                        showLongToast(R.string.net_business);
                    }
                });
    }

    private void showPayPw() {
        // 初始化弹出窗
        if (mPwPay == null) {
            mPwPay = new PayPopupWindow(UpLevelActivity.this);
        }
        // 弹出支付选择窗
        mPwPay.setOrderNo(mstrPayTradeNo);
        mPwPay.setAmount(360 + "");
        WindowUtils.setWindowAlpha(UpLevelActivity.this, 0.6f);
        mPwPay.showAtLocation(UpLevelActivity.this.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }

    // 判断升级总代是否支付成功
    @Subscriber(tag = EventConstants.PAY_RESULT)
    private void payResult(PayResultBean payResultBean) {
        WindowUtils.closePW(mPwPay);
        if (payResultBean.getResult() == 10000) {
            Bundle bundle = new Bundle();
            bundle.putString("account",mstrAccount);
            bundle.putString("pwd",mstrPwd);
            readyGoThenKill(UpLevelSuccessfulActivity.class,bundle);
        }else {
            showLongToast(payResultBean.getMsg());
        }
    }

}


