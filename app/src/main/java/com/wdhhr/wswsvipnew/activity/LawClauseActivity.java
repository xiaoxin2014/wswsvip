package com.wdhhr.wswsvipnew.activity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wdhhr.wswsvipnew.R;
import com.wdhhr.wswsvipnew.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/5 0005.
 */

public class LawClauseActivity extends BaseActivity {
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.tv_back)
    TextView mTvBack;
    @BindView(R.id.iv_more)
    ImageView mIvMore;
    @BindView(R.id.tv_right)
    TextView mTvRight;
    @BindView(R.id.tv_law_clause)
    TextView mTvLawClause;

    @Override
    protected int setViewId() {
        return R.layout.activity_law_clause;
    }

    @Override
    protected void init() {
        mTvBack.setVisibility(View.VISIBLE);
        mTitle.setText(R.string.law_clause);
        mTvLawClause.setText(Html.fromHtml(getResources().getString(R.string.law_clause_content)));
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
