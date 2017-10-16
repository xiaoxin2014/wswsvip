package com.wdhhr.wswsvipnew.activity;

import android.view.View;
import android.widget.TextView;

import com.wdhhr.wswsvipnew.R;
import com.wdhhr.wswsvipnew.base.BaseActivity;

import butterknife.BindView;

public class SettingAboutMeActivity extends BaseActivity {


    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_back)
    TextView tvBack;

    @Override
    protected int setViewId() {
        return R.layout.activity_setting_about_me;
    }

    @Override
    protected void init() {

        title.setText(R.string.about_me);
        tvBack.setVisibility(View.VISIBLE);
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
            case R.id.tv_back:
                finish();
                break;
        }
    }

}
