package com.wdhhr.wswsvipnew.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wdhhr.wswsvipnew.R;
import com.wdhhr.wswsvipnew.adapter.CommonAdapter;
import com.wdhhr.wswsvipnew.adapter.ViewHolder;
import com.wdhhr.wswsvipnew.base.BaseActivity;
import com.wdhhr.wswsvipnew.model.dataBase.ExpressTracesBean;
import com.wdhhr.wswsvipnew.model.dataBase.OrdersListBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

public class LogisticsDetailActivity extends BaseActivity {
    @BindView(R.id.title)
    TextView mTvTitle;
    @BindView(R.id.tv_back)
    TextView mTvBack;
    @BindView(R.id.lv_logistics)
    ListView mLvLogistics;
    @BindView(R.id.tv_logistics_name)
    TextView mTvLogisticsName;
    @BindView(R.id.tv_logistics_name2)
    TextView mTvLogisticsName2;
    @BindView(R.id.tv_logistics_no)
    TextView mTvLogisticsNo;
    private OrdersListBean mOrdersListBean;
    private CommonAdapter<ExpressTracesBean.TracesBean> mAdapter;

    @Override
    protected int setViewId() {
        return R.layout.activity_logistics_detail;
    }

    @Override
    protected void init() {
        mTvBack.setVisibility(View.VISIBLE);
        mTvTitle.setText(R.string.logistics_detail);

        String json = getIntent().getExtras().getString("json");
        Gson gson = new Gson();
        mOrdersListBean = gson.fromJson(json, OrdersListBean.class);
        mAdapter = new CommonAdapter<ExpressTracesBean.TracesBean>(this, null, R.layout.item_logistics_detail) {

            @Override
            public void convert(ViewHolder helper, int position, ExpressTracesBean.TracesBean item) {
                helper.setText(R.id.tv_detail, item.getAcceptStation())
                        .setText(R.id.tv_time, item.getAcceptTime());

                View view = helper.getView(R.id.view_top);
                ImageView ivDot = helper.getView(R.id.iv_dot);
                TextView tvDetail = helper.getView(R.id.tv_detail);
                TextView tvTime = helper.getView(R.id.tv_time);

                if (position == 0) {
                    view.setVisibility(View.INVISIBLE);
                    ivDot.setImageResource(R.drawable.shap_circle_red);
                    tvDetail.setTextColor(getResources().getColor(R.color.colorPrimary));
                    tvTime.setTextColor(getResources().getColor(R.color.colorPrimary));
                } else {
                    view.setVisibility(View.VISIBLE);
                    ivDot.setImageResource(R.drawable.shap_circle_gray);
                    tvDetail.setTextColor(getResources().getColor(R.color.fontSoftText));
                    tvTime.setTextColor(getResources().getColor(R.color.fontSoftText));
                }
            }
        };
        mLvLogistics.setAdapter(mAdapter);

    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void loadData() {
        boolean isEmpty = false;
        String expressTraces = mOrdersListBean.getExpressTraces();
        mTvLogisticsName.setText(mOrdersListBean.getExpressName());
        mTvLogisticsName2.setText(getStr(R.string.logistics_company_name) + mOrdersListBean.getExpressName());
        mTvLogisticsNo.setText(getStr(R.string.logistics_no) + mOrdersListBean.getExpressId());
        if (expressTraces != null && !TextUtils.equals(expressTraces, "")) {
            ExpressTracesBean expressTracesBean = new Gson().fromJson(expressTraces, ExpressTracesBean.class);
            List<ExpressTracesBean.TracesBean> traces = expressTracesBean.getTraces();

            if (traces == null || traces.size() == 0) {
                // 暂无物流信息
                isEmpty = true;
            } else {
                Collections.sort(traces, new Comparator<ExpressTracesBean.TracesBean>() {
                    @Override
                    public int compare(ExpressTracesBean.TracesBean o1, ExpressTracesBean.TracesBean o2) {
                        String time1 = o1.getAcceptTime();
                        String time2 = o2.getAcceptTime();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        int cmp = -1;
                        try {
                            Date parse1 = simpleDateFormat.parse(time1);
                            Date parse2 = simpleDateFormat.parse(time2);
                            cmp = (int) (parse2.getTime() - parse1.getTime());

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        return cmp;
                    }
                });
                mAdapter.refresh(traces);
            }
        } else {
            // 暂无物流信息
            isEmpty = true;
        }
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
