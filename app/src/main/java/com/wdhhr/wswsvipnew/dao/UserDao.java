package com.wdhhr.wswsvipnew.dao;

import com.wdhhr.wswsvipnew.MyApplication;
import com.wdhhr.wswsvipnew.base.BaseViewInterface;
import com.wdhhr.wswsvipnew.constant.EventConstants;
import com.wdhhr.wswsvipnew.constant.UserContants;
import com.wdhhr.wswsvipnew.model.OrderCommonBean;
import com.wdhhr.wswsvipnew.model.ShopCommonBean;
import com.wdhhr.wswsvipnew.model.UserCommonBean;
import com.wdhhr.wswsvipnew.model.dataBase.UsersBean;
import com.wdhhr.wswsvipnew.net.NetworkManager;
import com.wdhhr.wswsvipnew.net.ApiSubscriberCallBack;
import com.wdhhr.wswsvipnew.utils.SystemUtil;

import org.simple.eventbus.EventBus;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/8/31 0031.
 */

public class UserDao {

    private final static int PLANT = 2;

    public static void login() {
        String mstrAccount = SystemUtil.getSharedString(UserContants.userAccount);
        String mstrPwd = SystemUtil.getSharedString(UserContants.userPwd);
        if (mstrAccount != null && mstrPwd != null) {
            login(mstrAccount, mstrPwd, PLANT, null);
        }
    }

    public static void login(final String account, final String pwd, final int plant, final BaseViewInterface baseViewInterface) {
        HashMap<String, String> param = new HashMap<>();
        param.put(UserContants.userAccount, account);
        param.put(UserContants.userPwd, pwd);
        param.put("loginPlant", plant + "");
        SystemUtil.setSharedString(UserContants.userAccount, account);
        SystemUtil.setSharedString(UserContants.userPwd, null);
        if (baseViewInterface != null) {
            baseViewInterface.showLoadPw();
        }
        NetworkManager.getApiService().login(param).subscribeOn(Schedulers.io())
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
                        if (plant == PLANT) {
                            if (userCommonBean.getStatus() == 0) {
                                UsersBean usersBean = userCommonBean.getData().getUsersBean();
                                usersBean.setRecommenderName(userCommonBean.getData().getRecommenderName());
                                usersBean.setRecommenderPhone(userCommonBean.getData().getRecommenderPhone());
                                MyApplication.setUserInfo(usersBean);
                                SystemUtil.setSharedString(UserContants.userPwd, pwd);
                                ShopDao.getShopInfo();
                                EventBus.getDefault().post(0, EventConstants.LOG_STATUS_CHANGE);
                                MyApplication.loginTime = System.currentTimeMillis();
                            } else {
                                EventBus.getDefault().post(userCommonBean.getStatus(), EventConstants.LOG_STATUS_CHANGE);
                                if (userCommonBean.getStatus() != -72) {
                                    if (baseViewInterface != null) {
                                        baseViewInterface.showLongToast(userCommonBean.getMsg());
                                    }
                                } else {
                                    baseViewInterface.showLongToast(userCommonBean.getMsg());
                                }
                            }
                        } else {
                            if (userCommonBean.getStatus() == 0) {
                                UsersBean usersBean = userCommonBean.getData().getUsersBean();
                                usersBean.setRecommenderName(userCommonBean.getData().getRecommenderName());
                                usersBean.setRecommenderPhone(userCommonBean.getData().getRecommenderPhone());
                                EventBus.getDefault().post(usersBean, EventConstants.USER_INFO_LOAD);
                            }
                        }
                        if (baseViewInterface != null)
                            baseViewInterface.dismissLoadPw();
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        EventBus.getDefault().post(-1, EventConstants.LOG_STATUS_CHANGE);
                        if (baseViewInterface != null)
                            baseViewInterface.dismissLoadPw();
                    }
                });
    }

    public static void login(final String account, final String pwd, final BaseViewInterface baseViewInterface) {
        login(account, pwd, PLANT, baseViewInterface);
    }

    /**
     * 获取提现余额
     */
    public static void getWithdrawBalance() {
        NetworkManager.getApiService().getCashAmount()
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
                        if (MyApplication.getUserInfo() != null) {
                            MyApplication.getUserInfo().setUserBalance(userCommonBean.getData().getAmount());
                            MyApplication.getUserInfo().setUserCarNum(userCommonBean.getData().getCarNum());
                            EventBus.getDefault().post(0, EventConstants.USER_INFO_CHANGE);
                            loadShopCarCount();
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        EventBus.getDefault().post(0, EventConstants.USER_INFO_CHANGE);
                    }
                });
    }


    public static void loadProfitData() {
        NetworkManager.getApiService().getProfitAmount()
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
                        MyApplication.mProfitAmountBean.setProfit(orderCommonBean.getData().getProfit());
                        MyApplication.mProfitAmountBean.setShipmentProfit(orderCommonBean.getData().getShipmentProfit() + "");
                        MyApplication.mProfitAmountBean.setTodayProfit(orderCommonBean.getData().getTodayProfit());
                        MyApplication.mProfitAmountBean.setMyGold(orderCommonBean.getData().getMyGold());
                        EventBus.getDefault().post(0, EventConstants.USER_INFO_CHANGE);
                        getWithdrawBalance();
                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                });
    }

    public static void loadShopCarCount() {
        NetworkManager.getApiService().getShopCarNum()
                .subscribeOn(Schedulers.io())
                .map(new Function<ShopCommonBean, ShopCommonBean>() {
                    @Override
                    public ShopCommonBean apply(ShopCommonBean shopCommonBean) throws Exception {
                        return shopCommonBean;
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriberCallBack<ShopCommonBean>() {
                    @Override
                    public void onSuccess(ShopCommonBean shopCommonBean) {
                        MyApplication.mShopNumInfo = shopCommonBean.getData();
                        EventBus.getDefault().post(0, EventConstants.SHOP_NUM_LOAD_COMPLETE);
                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                });

    }

}
