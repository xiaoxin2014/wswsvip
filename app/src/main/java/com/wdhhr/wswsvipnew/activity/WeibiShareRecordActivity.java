package com.wdhhr.wswsvipnew.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.wdhhr.wswsvipnew.MyApplication;
import com.wdhhr.wswsvipnew.R;
import com.wdhhr.wswsvipnew.adapter.CommonAdapter;
import com.wdhhr.wswsvipnew.adapter.ViewHolder;
import com.wdhhr.wswsvipnew.base.BaseActivity;
import com.wdhhr.wswsvipnew.model.dataBase.WeibiCommonBean;
import com.wdhhr.wswsvipnew.net.NetworkManager;
import com.wdhhr.wswsvipnew.net.ApiSubscriberCallBack;
import com.wdhhr.wswsvipnew.utils.ImageUtils;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class WeibiShareRecordActivity extends BaseActivity {


    @BindView(R.id.ib_share_back)
    ImageButton mIbShareBack;
    @BindView(R.id.tv_share_title)
    TextView mTvShareTitle;
    @BindView(R.id.tv_renminbi)
    TextView mTvRenminbi;
    @BindView(R.id.tv_share_money)
    TextView mTvShareMoney;
    @BindView(R.id.tv_share_time)
    TextView mTvShareTime;
    @BindView(R.id.iv_head_icon)
    ImageView mIvHeadIcon;
    @BindView(R.id.listview_share_record)
    ListView mListviewShareRecord;
    @BindView(R.id.layout_share_detail)
    LinearLayout mLayoutShareDetail;
    private CommonAdapter<WeibiCommonBean.DataBean.GoldListBean> mAdapter;
    private List<WeibiCommonBean.DataBean.GoldListBean> mGoldList;

    @Override
    protected int setViewId() {
        return R.layout.activity_weibi_share_record;
    }

    @Override
    protected void init() {
        mTvShareTitle.setText(R.string.share_record);
        mLayoutShareDetail.setVisibility(View.GONE);
        mAdapter = new CommonAdapter<WeibiCommonBean.DataBean.GoldListBean>(this, null, R.layout.item_share_record) {
            @Override
            public void convert(ViewHolder helper, int position, final WeibiCommonBean.DataBean.GoldListBean item) {
                helper.setText(R.id.tv_money_num, getStrFormat(R.string.weibi_share_num, item.getMoney()));
                //分享时间
                helper.setText(R.id.tv_share_time, item.getStimeStr());
                switch (item.getStatus()) {
                    case 2:
                        helper.setText(R.id.tv_get_status, getResources().getString(R.string.un_get));
                        break;
                    case 3:
                        helper.setText(R.id.tv_get_status, getResources().getString(R.string.already_get));
                        helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Bundle bundle = new Bundle();
                                bundle.putString("wGlod", item.getWGlod());
                                readyGo(WeibiShareDetailActivity.class, bundle);
                            }
                        });
                        break;
                    case 4:
                        helper.setText(R.id.tv_get_status, getResources().getString(R.string.out_of_date));
                        break;
                    case 5:
                        helper.setText(R.id.tv_get_status, getResources().getString(R.string.already_return));
                        break;
                    default:
                        break;
                }


            }

        };
        mListviewShareRecord.setAdapter(mAdapter);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void loadData() {
        loadShareRecord();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_share_back:
                finish();
                break;
        }
    }

    public void loadShareRecord() {
        if (MyApplication.getUserInfo() != null) {
            HashMap<String, Integer> map = new HashMap<>();
            map.put("isCheck", 1);
            NetworkManager.getApiService().getWeibi(map)
                    .subscribeOn(Schedulers.io())
                    .map(new Function<WeibiCommonBean, WeibiCommonBean>() {
                        @Override
                        public WeibiCommonBean apply(WeibiCommonBean WeibiCommonBean) throws Exception {
                            return WeibiCommonBean;
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new ApiSubscriberCallBack<WeibiCommonBean>() {
                        @Override
                        public void onSuccess(WeibiCommonBean WeibiCommonBean) {
                            com.wdhhr.wswsvipnew.model.dataBase.WeibiCommonBean.DataBean data = WeibiCommonBean.getData();
                            mGoldList = data.getGoldList();
                            mTvShareMoney.setText(String.valueOf(data.getTatolMoney()));
                            mTvShareTime.setText(getStrFormat(R.string.already_share_time, data.getCount()));
                            ImageUtils.loadCircleImageUrl(mIvHeadIcon,data.getUserPhoto(),R.mipmap.icon_head,WeibiShareRecordActivity.this);
                            mAdapter.refresh(mGoldList);
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            showLongToast("网络繁忙");
                        }
                    });
        }

    }

}
