package com.wdhhr.wswsvipnew.activity;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.wdhhr.wswsvipnew.R;
import com.wdhhr.wswsvipnew.adapter.CommonAdapter;
import com.wdhhr.wswsvipnew.adapter.ViewHolder;
import com.wdhhr.wswsvipnew.base.BaseActivity;
import com.wdhhr.wswsvipnew.model.UserCommonBean;
import com.wdhhr.wswsvipnew.model.dataBase.CashBean;
import com.wdhhr.wswsvipnew.net.NetworkManager;
import com.wdhhr.wswsvipnew.net.LoadStatusSubscriberCallBack;
import com.wdhhr.wswsvipnew.utils.LoadErrorUtils;
import com.wdhhr.wswsvipnew.widget.XListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MyWithdrawalsRecordActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_back)
    TextView tvBack;
    @BindView(R.id.lv_cash_record)
    XListView lvCashRecord;
    private List<CashBean> mCashList = new ArrayList<>();
    private CommonAdapter<CashBean> mAdapter;

    private static final String TAG = "MyWithdrawalsRecordActi";
    private LoadErrorUtils mXlvUtils;
    private boolean isLoad;
    private int miPage;

    @Override
    protected int setViewId() {
        return R.layout.activity_my_withdrawals_record;
    }

    @Override
    protected void init() {
        tvBack.setVisibility(View.VISIBLE);
        title.setText(R.string.record_title);

        mAdapter = new CommonAdapter<CashBean>(this, mCashList, R.layout.item_withdrawals_record) {
            @Override
            public void convert(ViewHolder helper, int position, CashBean item) {
                helper.setText(R.id.tv_withdraw_amount, "-" + item.getCashNum())
                        .setText(R.id.tv_withdraw_date, item.getCtimeStr())
                        .setText(R.id.tv_withdraw_status, getCashBeanStatus(item.getStatus()));

                TextView tvStatus = helper.getView(R.id.tv_withdraw_status);
                if (item.getStatus() == 1) {
                    // 提现成功灰色
                    tvStatus.setTextColor(getResources().getColor(R.color.fontSoftText));
                } else {
                    tvStatus.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
            }
        };
        lvCashRecord.setAdapter(mAdapter);
        mXlvUtils = new LoadErrorUtils(lvCashRecord, this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });
    }

    public String getCashBeanStatus(int mode) {
        Log.d(TAG, mode + "");
        switch (mode) {
            case 0:
                return getStr(R.string.withdraw_on);
            case 1:
                return getStr(R.string.withdraw_success);
            case 2:
                return getStr(R.string.withdraw_fail);
        }

        return null;
    }

    @Override
    protected void initEvent() {
        // 上拉下拉监听
        lvCashRecord.setXListViewListener(new XListView.IXListViewListener() {
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

        if (mCashList == null || mCashList.size() == 0) {
            showLoadPw();
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("page", "1");
        NetworkManager.getApiService().getCashRecord(map)
                .subscribeOn(Schedulers.io())
                .map(new Function<UserCommonBean, UserCommonBean>() {
                    @Override
                    public UserCommonBean apply(UserCommonBean userCommonBean) throws Exception {
                        return userCommonBean;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new LoadStatusSubscriberCallBack<UserCommonBean>(mXlvUtils) {
                    @Override
                    public void onSuccess(UserCommonBean userCommonBean) {
                        if (miPage == 1) {
                            mCashList.clear();
                        }
                        mCashList.addAll(userCommonBean.getData().getCashList());
                    }

                    @Override
                    public int getListStatus(UserCommonBean userCommonBean) {
                        return userCommonBean.getStatus();
                    }

                    @Override
                    public List getList(UserCommonBean userCommonBean) {
                        return userCommonBean.getData().getCashList();
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
