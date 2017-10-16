package com.wdhhr.wswsvipnew.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.wdhhr.wswsvipnew.MyApplication;
import com.wdhhr.wswsvipnew.R;
import com.wdhhr.wswsvipnew.base.BaseActivity;
import com.wdhhr.wswsvipnew.constant.EventConstants;
import com.wdhhr.wswsvipnew.model.UserCommonBean;
import com.wdhhr.wswsvipnew.net.NetworkManager;
import com.wdhhr.wswsvipnew.net.ApiSubscriberCallBack;

import org.simple.eventbus.EventBus;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public class SettingPersonalNickNameActivity extends BaseActivity {


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
    private String TAG = SettingPersonalNickNameActivity.class.getSimpleName();

    @Override
    protected int setViewId() {
        return R.layout.activity_setting_personal_nick_name;
    }

    @Override
    protected void init() {
        mTvBack.setVisibility(View.VISIBLE);
        mTitle.setText("昵称");
        mTvRight.setText("保存");
        mTvRight.setVisibility(View.VISIBLE);
        mIvMore.setVisibility(View.INVISIBLE);
        Bundle bundle = this.getIntent().getExtras();
        mNickName.setText(bundle.getString("nickname"));
        mNickName.setSelection(bundle.getString("nickname").length());
        mNickName.setHint(R.string.nick_name);

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void loadData() {

    }

    void changeUserNick() {

        RequestBody body = new FormBody.Builder()
                .add("name", mNickName.getText().toString().trim())
                .add("changeStatus", "1").build();
        NetworkManager.getApiService().changeHeadIcon(body).subscribeOn(Schedulers.io())
                .map(new Function<UserCommonBean, UserCommonBean>() {
                    @Override
                    public UserCommonBean apply(UserCommonBean changeHeadIconBean) throws Exception {
                        return changeHeadIconBean;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriberCallBack<UserCommonBean>() {
                    @Override
                    public void onSuccess(UserCommonBean userCommonBean) {
                        if (userCommonBean.getStatus() == 0) {
                            MyApplication.getUserInfo().setUserName(mNickName.getText().toString().trim());
                            EventBus.getDefault().post(0, EventConstants.USER_INFO_CHANGE);
                            finish();
                        }
                        showLongToast(userCommonBean.getMsg());
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
                changeUserNick();
                break;
        }
    }

}
