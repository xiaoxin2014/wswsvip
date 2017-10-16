package com.wdhhr.wswsvipnew.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wdhhr.wswsvipnew.R;
import com.wdhhr.wswsvipnew.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/9/5 0005.
 */

public class PlatfromProtocolActivity extends BaseActivity {
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
        return R.layout.activity_platform_protocol;
    }

    @Override
    protected void init() {
        mTvBack.setVisibility(View.VISIBLE);
        mTitle.setText(R.string.platform_protocol);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View view) {
        finish();
    }
}
