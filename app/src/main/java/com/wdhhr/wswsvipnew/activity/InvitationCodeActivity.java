package com.wdhhr.wswsvipnew.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wdhhr.wswsvipnew.MyApplication;
import com.wdhhr.wswsvipnew.R;
import com.wdhhr.wswsvipnew.base.BaseActivity;
import com.wdhhr.wswsvipnew.utils.ShareUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InvitationCodeActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView mTvTitle;
    @BindView(R.id.tv_back)
    TextView mTvBack;
    @BindView(R.id.iv_invitation_code)
    ImageView mIvInvitationCode;
    @BindView(R.id.tv_invitation_code)
    TextView mTvInvitationCode;
    private String mStrCode;

    @Override
    protected int setViewId() {
        return R.layout.activity_invitation_code;
    }

    @Override
    protected void init() {
        mTvTitle.setText(R.string.invitation_code);
        mTvBack.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void loadData() {
        mStrCode = MyApplication.getUserInfo().getInvitationCode();
        mTvInvitationCode.setText(mStrCode);
        ShareUtils.createImage(mStrCode, mIvInvitationCode, 500, 500);
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
