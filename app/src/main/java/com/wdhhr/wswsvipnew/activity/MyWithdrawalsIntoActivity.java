package com.wdhhr.wswsvipnew.activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.wdhhr.wswsvipnew.MyApplication;
import com.wdhhr.wswsvipnew.R;
import com.wdhhr.wswsvipnew.base.BaseActivity;
import com.wdhhr.wswsvipnew.constant.EventConstants;
import com.wdhhr.wswsvipnew.model.UserCommonBean;
import com.wdhhr.wswsvipnew.net.NetworkManager;
import com.wdhhr.wswsvipnew.net.ApiSubscriberCallBack;
import com.wdhhr.wswsvipnew.utils.DeviceUtils;
import com.wdhhr.wswsvipnew.utils.StringUtils;

import org.simple.eventbus.Subscriber;

import java.util.HashMap;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MyWithdrawalsIntoActivity extends BaseActivity {


    @BindView(R.id.title)
    TextView mTvTitle;
    @BindView(R.id.tv_back)
    TextView mTvBack;
    @BindView(R.id.tv_right)
    TextView mTvRight;
    @BindView(R.id.tv_account)
    TextView mTvAccount;
    @BindView(R.id.edit_withdraw_num)
    TextView mEditWithdrawNum;
    @BindView(R.id.tv_balance)
    TextView mTvBalance;
    @BindView(R.id.tv_withdraw_send)
    TextView mTvWithdrawSend;
    @BindView(R.id.iv_more)
    ImageView mIvMore;
    @BindView(R.id.scrollView)
    ScrollView mScrollView;
    private double miBalance;
    private double miWithDraw;

    @Override
    protected int setViewId() {
        return R.layout.activity_my_withdrawals_into;
    }

    @Override
    protected void init() {
        mTvTitle.setText(R.string.withdraw);
        mTvBack.setVisibility(View.VISIBLE);
        mTvRight.setVisibility(View.VISIBLE);
        mTvRight.setText(R.string.record_title);
        mTvAccount.setText(MyApplication.getUserInfo().getUserCarNum());
//        mTvAccount.setText(MyApplication.getUserInfo().getUserAccount());
        setBtnStatus();
        getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() { //当界面大小变化时，系统就会调用该方法
                        mScrollView.smoothScrollBy(0, DeviceUtils.dip2px(300));
                    }
                });

    }

    @Override
    protected void initEvent() {

        mEditWithdrawNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    miWithDraw = Double.parseDouble(s.toString());
                } else {
                    miWithDraw = 0;
                }

                setBtnStatus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void setBtnStatus() {
        //miWithDraw表示输入的提现金额， miBalance表示可提现余额，mTvWithdrawSend提现的按钮,mTvBalance提示的文字
//        0.如果等于0 显示可提现的余额
//        1.如果超出5万 按钮不可用 提示超出
//        2.如果低于等于5万
//        判断是否大于10元，
//           如果小于10元 按钮不可用，提示不能低于10元
//           如果大于等于10元 判断可用余额是否大于或者等于输入的提现金额 如果大于，按钮可用，提示可用余额     如果可用余额小于输入的提现金额，按钮不可用，提示超出可提现金额
//        if (miWithDraw == 0) {
//            mTvWithdrawSend.setEnabled(false);
//            mTvBalance.setText(getStrFormat(R.string.withdraw_balance_intro, StringUtils.double2int(miBalance)));
//            return;
//        }
//        if (miWithDraw > 50000) {
//            mTvWithdrawSend.setEnabled(false);
//            mTvBalance.setText(R.string.max_withdraw);
//        } else {
//            if (miWithDraw > 10 || miWithDraw == 10) {
//                if (miBalance > miWithDraw || miBalance == miWithDraw) {
//                    mTvBalance.setText(getStrFormat(R.string.withdraw_balance_intro, StringUtils.double2int(miBalance)));
//                } else {
//                    mTvBalance.setText(R.string.over_max_withdraw);
//                }
//            } else {
//                mTvWithdrawSend.setEnabled(false);
//                mTvBalance.setText(R.string.mix_withdraw);
//            }
//        }


        if (miBalance < 10) {
            mTvWithdrawSend.setEnabled(false);
        } else {
            mTvWithdrawSend.setEnabled(true);
        }

    }

    @Override
    protected void loadData() {
        miBalance = MyApplication.getUserInfo().getUserBalance();
        mTvBalance.setText(getStrFormat(R.string.withdraw_balance_intro, StringUtils.double2int(miBalance)));
        mEditWithdrawNum.setText(StringUtils.double2int(miBalance));
    }

    @Subscriber(tag = EventConstants.USER_INFO_CHANGE)
    private void balanceChange(int num) {
        loadData();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_right:
                // 提现记录
                readyGo(MyWithdrawalsRecordActivity.class);
                break;
//            case R.id.tv_withdraw_all:
//                // 全部提现
//                mEditWithdrawNum.setText(StringUtils.double2int(miBalance));
//                miWithDraw = miBalance;
//                mEditWithdrawNum.setSelection(mEditWithdrawNum.getText().length());
//                setBtnStatus();
//                break;
            case R.id.tv_withdraw_send:
                // 申请提现
                HashMap<String, String> map = new HashMap<>();
                map.put("amount", miWithDraw + "");
                showLoadPw();
                NetworkManager.getApiService().withdraw(map)
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

                                showLongToast(userCommonBean.getMsg());
                                dismissLoadPw();
                                readyGo(MyWithdrawalsRecordActivity.class);
                            }

                            @Override
                            public void onFailure(Throwable t) {
                                showLongToast(R.string.net_connect_error);
                                dismissLoadPw();
                            }
                        });
                break;
        }
    }

}
