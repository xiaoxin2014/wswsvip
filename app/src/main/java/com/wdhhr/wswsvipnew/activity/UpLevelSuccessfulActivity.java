package com.wdhhr.wswsvipnew.activity;

import android.view.View;

import com.wdhhr.wswsvipnew.R;
import com.wdhhr.wswsvipnew.base.BaseActivity;
import com.wdhhr.wswsvipnew.constant.EventConstants;
import com.wdhhr.wswsvipnew.dao.UserDao;

import org.simple.eventbus.Subscriber;

public class UpLevelSuccessfulActivity extends BaseActivity {

    private String mstrAccount;
    private String mstrPwd;

    @Override
    protected int setViewId() {
        return R.layout.activity_up_level_successful;
    }

    @Override
    protected void init() {
        mstrAccount = getIntent().getExtras().getString("account");
        mstrPwd = getIntent().getExtras().getString("pwd");
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_go:
                UserDao.login(mstrAccount, mstrPwd, this);
                break;
        }
    }

    @Subscriber(tag = EventConstants.LOG_STATUS_CHANGE)
    private void loginStatusChange(int num) {
        if (num == 0) {
            finish();
        }
    }
}
