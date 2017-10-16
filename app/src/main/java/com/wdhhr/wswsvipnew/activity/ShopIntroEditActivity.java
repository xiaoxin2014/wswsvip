package com.wdhhr.wswsvipnew.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.wdhhr.wswsvipnew.MyApplication;
import com.wdhhr.wswsvipnew.R;
import com.wdhhr.wswsvipnew.base.BaseActivity;
import com.wdhhr.wswsvipnew.constant.EventConstants;
import com.wdhhr.wswsvipnew.model.dataBase.BusinessInfoBean;
import com.wdhhr.wswsvipnew.net.NetworkManager;
import com.wdhhr.wswsvipnew.net.ApiSubscriberCallBack;

import org.simple.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class ShopIntroEditActivity extends BaseActivity {


    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.tv_back)
    TextView mTvBack;
    @BindView(R.id.iv_more)
    ImageView mIvMore;
    @BindView(R.id.tv_right)
    TextView mTvRight;
    @BindView(R.id.nick_name)
    EditText mNickName;
    @BindView(R.id.tv_num)
    TextView mTvNum;
    @BindView(R.id.tv_limitnum)
    TextView mTvLimitnum;
    private String TAG = ShopIntroEditActivity.class.getSimpleName();

    @Override
    protected int setViewId() {
        return R.layout.activity_setting_shopintro;
    }

    @Override
    protected void init() {
        mTitle.setText(getResources().getText(R.string.shop_intro));
        mTvRight.setText(getResources().getText(R.string.complete));
        mTvBack.setVisibility(View.VISIBLE);
        mTvRight.setVisibility(View.VISIBLE);
        mIvMore.setVisibility(View.GONE);
        Bundle bundle = this.getIntent().getExtras();
        mNickName.setText(bundle.getString("shopintro"));
        mNickName.setSelection(mNickName.getText().length());
        mTvNum.setText(String.valueOf(mNickName.getText().length()));
    }

    @Override
    protected void initEvent() {

        mNickName.setFilters(new InputFilter[]{new InputFilter.LengthFilter(150)});
        mNickName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() <= 150) {
                    mTvNum.setText(String.valueOf(s.toString().length()));
                } else {
                    mTvNum.setText(getResources().getString(R.string.limit_maxnum));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void loadData() {

    }

    void changeShopIntro(Map<String, String> changeMap) {
        NetworkManager.getApiService().updateBusinessInfo(changeMap).subscribeOn(Schedulers.io())
                .map(new Function<BusinessInfoBean, BusinessInfoBean>() {
                    @Override
                    public BusinessInfoBean apply(BusinessInfoBean BusinessInfoBean) throws Exception {
                        return BusinessInfoBean;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriberCallBack<BusinessInfoBean>() {
                    @Override
                    public void onSuccess(BusinessInfoBean businessInfoBean) {
                        if (businessInfoBean.getStatus() == 0) {
                            MyApplication.shopInfo.setBusinessDesc(mNickName.getText().toString().trim());
                            EventBus.getDefault().post(0, EventConstants.UPDATE_SHOP_INFO);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.d(TAG, t.getMessage());
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_right:
                Map<String, String> changeMap = new HashMap<String, String>();
                changeMap.put("businessDesc", mNickName.getText().toString().trim());
                changeMap.put("changeStatus", "2");
                changeShopIntro(changeMap);
                break;
        }
    }

}
