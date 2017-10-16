package com.wdhhr.wswsvipnew.wxapi;

import android.os.Handler;
import android.util.Log;

import com.alipay.sdk.app.PayTask;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.wdhhr.wswsvipnew.base.BaseViewInterface;
import com.wdhhr.wswsvipnew.constant.EventConstants;
import com.wdhhr.wswsvipnew.model.OrderCommonBean;
import com.wdhhr.wswsvipnew.model.PayBean;
import com.wdhhr.wswsvipnew.model.cache.NameValuePair;
import com.wdhhr.wswsvipnew.model.cache.PayResultBean;
import com.wdhhr.wswsvipnew.net.NetworkManager;
import com.wdhhr.wswsvipnew.net.ApiSubscriberCallBack;
import com.wdhhr.wswsvipnew.utils.MD5;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by felear on 2017/8/26 0026.
 */

public class PayDao {

    private static final String TAG = "PayDao";


    //生成微信支付订单信息
    public static void weChatPay(final String orderNo, final BaseViewInterface baseViewInterface) {
        HashMap<String, String> map = new HashMap<>();
        map.put("outTradeNo", orderNo);
        map.put("payType", "APP");
        map.put("channelType", "1");
        map.put("payDisinguish", "2");

        NetworkManager.getApiService().payOrderWechat(map)
                .subscribeOn(Schedulers.io())
                .map(new Function<PayBean<PayBean.DataBean>, PayBean<PayBean.DataBean>>() {
                    @Override
                    public PayBean<PayBean.DataBean> apply(PayBean<PayBean.DataBean> payBean) throws Exception {
                        return payBean;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriberCallBack<PayBean<PayBean.DataBean>>() {
                    @Override
                    public void onSuccess(PayBean<PayBean.DataBean> payBean) {
                        if (payBean.getStatus() != 0) {
                            baseViewInterface.showLongToast(payBean.getMsg());
                        } else {
                            payByWeChat(payBean.getData(), orderNo, baseViewInterface);
                        }
                        baseViewInterface.dismissLoadPw();
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        baseViewInterface.showLongToast("支付失败，请重新支付");
                        baseViewInterface.dismissLoadPw();
                    }
                });

    }

    //生成阿里支付订单信息（经过加密）
    public static void aliPay(final String orderNo, final BaseViewInterface baseViewInterface) {
        HashMap<String, String> map = new HashMap<>();
        map.put("outTradeNo", orderNo);
        map.put("payType", "APP");
        map.put("channelType", "2");
        map.put("payDisinguish", "2");

        NetworkManager.getApiService().payOrderAli(map)
                .subscribeOn(Schedulers.io())
                .map(new Function<PayBean<Object>, PayBean<Object>>() {
                    @Override
                    public PayBean<Object> apply(PayBean<Object> payBean) throws Exception {
                        return payBean;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriberCallBack<PayBean<Object>>() {
                    @Override
                    public void onSuccess(PayBean<Object> payBean) {
                        if (payBean.getStatus() != 0) {
                            baseViewInterface.showLongToast(payBean.getMsg());
                        } else {
                            payByAli(orderNo, payBean.getData() + "", baseViewInterface);
                        }
                        baseViewInterface.dismissLoadPw();
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        baseViewInterface.showLongToast("支付失败，请重新支付");
                        baseViewInterface.dismissLoadPw();
                    }
                });
    }

    //发起微信支付请求（请求参数需要自己拼装并签名）
    private static void payByWeChat(final PayBean.DataBean data, final String orderNo, BaseViewInterface baseViewInterface) {
        if (null == data) {
            //判断是否为空。
            return;
        }
        //初始化微信api
        final IWXAPI api = WXAPIFactory.createWXAPI(baseViewInterface.getBaseActivity(), PayConstants.APP_ID, false);
        //注册appid  appid可以在开发平台获取
        api.registerApp(PayConstants.APP_ID);
        new Thread(new Runnable() {
            @Override
            public void run() {
                //创建支付请求参数
                PayReq req = new PayReq();
                req.appId = PayConstants.APP_ID;
                req.partnerId = data.getPartnerid();
                req.prepayId = data.getPrepayid();
                req.packageValue = "Sign=WXPay";
                req.nonceStr = data.getNoncestr();
                req.timeStamp = data.getTimestamp() + "";
                //将支付参数进行签名
                req.sign = genAppSign(data);
                PayConstants.orderNo = orderNo;
                //发起微信支付请求
                api.sendReq(req);
            }
        }).start();

    }

    /**
     * 签名支付参数
     */
    public static String genAppSign(PayBean.DataBean data) {

        ArrayList<NameValuePair> list = new ArrayList<>();
        list.add(new NameValuePair("appid", PayConstants.APP_ID));
        list.add(new NameValuePair("noncestr", data.getNoncestr()));
        list.add(new NameValuePair("package", "Sign=WXPay"));
        list.add(new NameValuePair("partnerid", data.getPartnerid()));
        list.add(new NameValuePair("prepayid", data.getPrepayid()));
        list.add(new NameValuePair("timestamp", data.getTimestamp() + ""));

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i).getName());
            sb.append('=');
            sb.append(list.get(i).getValue());
            sb.append('&');
            Log.d(TAG, list.get(i).getName() + ":" + list.get(i).getValue());
        }
        sb.append("key=");
        sb.append(data.getPartnerkey());
        String appSign = MD5.md5(sb.toString()).toUpperCase();
        Log.d(TAG, sb.toString());
        return appSign;
    }

    //发起阿里支付请求（请求参数服务器已经为我们自动生成并签名了）
    private static void payByAli(final String orderNo, String userInfo, final BaseViewInterface baseViewInterface) {
        Log.d(TAG, userInfo);
        Observable.just(userInfo)
                .subscribeOn(Schedulers.io())
                .map(new Function<String, Map<String, String>>() {
                    @Override
                    public Map<String, String> apply(String userInfo) throws Exception {

                        PayTask alipay = new PayTask(baseViewInterface.getBaseActivity());
                        Map<String, String> result = alipay.payV2(userInfo, true);
                        return result;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Map<String, String>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Map<String, String> value) {
                        Log.d(TAG, value + "");
                        queryResult(orderNo, baseViewInterface);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //根据订单号查询订单支付结果
    public static void queryResult(final String strOrderNo, final BaseViewInterface baseViewInterface) {

        if (baseViewInterface != null) {
            baseViewInterface.showLoadPw();
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                HashMap<String, String> map = new HashMap<>();
                map.put("outTradeNo", strOrderNo);
                NetworkManager.getApiService().payStatusQuery(map)
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
                                if (orderCommonBean.getStatus() == 10000) {
                                    Log.d(TAG, "支付成功");
                                }

                                PayResultBean payResultBean = new PayResultBean();
                                payResultBean.setOrderNo(strOrderNo);
                                payResultBean.setResult(orderCommonBean.getStatus());
                                payResultBean.setMsg(orderCommonBean.getMsg());
                                EventBus.getDefault().post(payResultBean, EventConstants.PAY_RESULT);
                                if (baseViewInterface != null) {
                                    baseViewInterface.dismissLoadPw();
                                }
                            }

                            @Override
                            public void onFailure(Throwable t) {
                                if (baseViewInterface != null) {
                                    baseViewInterface.dismissLoadPw();
                                }
                            }
                        });
            }
        }, 500);


    }

}
