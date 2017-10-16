package com.wdhhr.wswsvipnew.activity;

import android.view.View;
import android.widget.TextView;

import com.wdhhr.wswsvipnew.MyApplication;
import com.wdhhr.wswsvipnew.R;
import com.wdhhr.wswsvipnew.base.BaseActivity;
import com.wdhhr.wswsvipnew.constant.EventConstants;
import com.wdhhr.wswsvipnew.utils.DataCleanManager;

import org.simple.eventbus.Subscriber;

import butterknife.BindView;


public class SettingActivity extends BaseActivity {


    @BindView(R.id.title)
    TextView mTvTitle;
    @BindView(R.id.tv_back)
    TextView mTvBack;
    @BindView(R.id.tv_cache_size)
    TextView mTvCacheSize;
    @BindView(R.id.tv_version)
    TextView mTvVersion;

    @Override
    protected int setViewId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void init() {
        mTvBack.setVisibility(View.VISIBLE);
        mTvTitle.setText(R.string.setting);
    }

    @Override
    protected void initEvent() {

    }

    @Subscriber(tag = EventConstants.LOG_STATUS_CHANGE)
    private void loginStatusChange(int num) {
        if (MyApplication.getUserInfo() == null) {
            finish();
        }
    }

    @Override
    protected void loadData() {
        mTvCacheSize.setText(DataCleanManager.getTotalCacheSize(this));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_user_data:
                if (MyApplication.getUserInfoAndLogin() != null) {
                    readyGo(SettingPersonalActivity.class);
                }
                break;
            case R.id.tv_about_me:
                readyGo(SettingAboutMeActivity.class);
                break;
            case R.id.tv_law:
                readyGo(LawClauseActivity.class);
                break;
            case R.id.layout_clear:
                // 清除缓存
                DataCleanManager.clearAllCache(this);
                showLoadPw();
                mTvCacheSize.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mTvCacheSize.setText(DataCleanManager.getTotalCacheSize(SettingActivity.this));
                        showLongToast("清除成功");
                        dismissLoadPw();
                    }
                }, 800);
                break;
        }
    }

}
