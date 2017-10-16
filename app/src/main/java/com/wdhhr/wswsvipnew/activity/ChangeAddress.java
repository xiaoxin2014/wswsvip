package com.wdhhr.wswsvipnew.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wdhhr.wswsvipnew.R;
import com.wdhhr.wswsvipnew.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by szq on 2017/8/23.
 */

class ChangeAddress extends BaseActivity {
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.tv_back)
    TextView mTvBack;
    @BindView(R.id.iv_more)
    ImageView mIvMore;
    @BindView(R.id.tv_right)
    TextView mTvRight;

    @Override
    protected int setViewId() {
        return R.layout.activity_address_change;
    }

    @Override
    protected void init() {

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
