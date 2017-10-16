package com.wdhhr.wswsvipnew.activity;

import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.wdhhr.wswsvipnew.R;
import com.wdhhr.wswsvipnew.adapter.CommonAdapter;
import com.wdhhr.wswsvipnew.adapter.ViewHolder;
import com.wdhhr.wswsvipnew.base.BaseActivity;
import com.wdhhr.wswsvipnew.constant.MyConstants;
import com.wdhhr.wswsvipnew.model.OrderCommonBean;
import com.wdhhr.wswsvipnew.model.dataBase.ProfitBean;
import com.wdhhr.wswsvipnew.model.dataBase.TimeBean;
import com.wdhhr.wswsvipnew.net.NetworkManager;
import com.wdhhr.wswsvipnew.net.LoadStatusSubscriberCallBack;
import com.wdhhr.wswsvipnew.utils.LoadErrorUtils;
import com.wdhhr.wswsvipnew.utils.StringUtils;
import com.wdhhr.wswsvipnew.widget.XListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class ProfitActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView mTvTitle;
    @BindView(R.id.tv_back)
    TextView mTvBack;
    @BindView(R.id.tv_menu_right)
    TextView mTvMenuRight;
    @BindView(R.id.listView)
    XListView mXListView;
    private int miMode;
    private int miPage = 1;
    private HashMap<String, String> mMapApi;
    private ArrayList<ProfitBean> mListProfit;
    private CommonAdapter<ProfitBean> mAdapter;
    private LoadErrorUtils mXlvUtils;
    private boolean isLoad;

    @Override
    protected int setViewId() {
        return R.layout.activity_profit;
    }

    @Override
    protected void init() {

        miMode = getIntent().getExtras().getInt(MyConstants.KEY_PROFIT_MODE);
        mTvBack.setVisibility(View.VISIBLE);
        mMapApi = new HashMap<>();

        switch (miMode) {
            case MyConstants.MODE_PROFIT_PENDDING:
                mTvTitle.setText(R.string.profit_pending);
                mTvMenuRight.setText(R.string.arrival_time_forecast);
                mMapApi.put("profitType", "0");
                mMapApi.put("isToday", "0");
                break;
            case MyConstants.MODE_PROFIT_TODAY:
                mTvTitle.setText(R.string.profit_today);
                mMapApi.put("profitType", "0");
                mMapApi.put("isToday", "1");
                break;
            case MyConstants.MODE_PROFIT_TOTAL:
                mTvTitle.setText(R.string.profit_total);
                mMapApi.put("profitType", "1");
                mMapApi.put("isToday", "0");
                break;
        }

        mListProfit = new ArrayList<>();
        mAdapter = new CommonAdapter<ProfitBean>(this, mListProfit, R.layout.item_profit) {

            @Override
            public void convert(ViewHolder helper, int position, ProfitBean item) {
                helper.setText(R.id.tv_center, getStr(R.string.money_unit) + StringUtils.float2int(item.getProfitNum()));
                TimeBean ctime = item.getCtime();

                helper.setText(R.id.tv_right, StringUtils.getProfitDataHByLongTime(ctime.getTime()));

//                        helper.setText(R.id.tv_right, (ctime.getYear() + 1900) + "-"
//                                + (ctime.getMonth() + 1) + "-"
//                                + ctime.getDate() + "\n"
//                                + ctime.getHours() + ":"
//                                + ctime.getMinutes() + ":"
//                                + ctime.getSeconds()));

                TextView tvLeft = helper.getView(R.id.tv_left);

                switch (item.getProfitForm()) {
                    case 1:
                        tvLeft.setText(Html.fromHtml(getStr(R.string.profit_sell)));
                        break;
                    case 2:
                        tvLeft.setText(Html.fromHtml(getStr(R.string.profit_buy)));
                        break;
                    case 3:
                        tvLeft.setText(getStr(R.string.train_money));
                        break;
                }
            }
        };
        mXListView.setAdapter(mAdapter);
        mXlvUtils = new LoadErrorUtils(mXListView, this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });

    }

    @Override
    protected void initEvent() {
        // 上拉下拉监听
        mXListView.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                loadData();
            }

            @Override
            public void onLoadMore() {
                isLoad = true;
                loadData();
            }
        });
    }

    @Override
    protected void loadData() {

        if (isLoad) {
            miPage++;
        } else {
            miPage = 1;
        }
        isLoad = false;
        showLoadPw();

        mMapApi.put("page", miPage + "");
        NetworkManager.getApiService().getProfitDetail(mMapApi)
                .subscribeOn(Schedulers.io())
                .map(new Function<OrderCommonBean, OrderCommonBean>() {
                    @Override
                    public OrderCommonBean apply(OrderCommonBean orderCommonBean) throws Exception {
                        return orderCommonBean;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new LoadStatusSubscriberCallBack<OrderCommonBean>(mXlvUtils) {
                    @Override
                    public void onSuccess(OrderCommonBean orderCommonBean) {
                        if (miPage == 1) {
                            mListProfit.clear();
                        }
                        mListProfit.addAll(orderCommonBean.getData().getProfitList());
                    }

                    @Override
                    public int getListStatus(OrderCommonBean orderCommonBean) {
                        return orderCommonBean.getStatus();
                    }

                    @Override
                    public List getList(OrderCommonBean orderCommonBean) {
                        return orderCommonBean.getData().getProfitList();
                    }

                    @Override
                    public void onComplete() {
                        dismissLoadPw();
                        mAdapter.notifyDataSetChanged();
                    }
                });
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
