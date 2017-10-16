package com.wdhhr.wswsvipnew.activity;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.wdhhr.wswsvipnew.R;
import com.wdhhr.wswsvipnew.base.BaseActivity;
import com.wdhhr.wswsvipnew.model.dataBase.WeibiShareSucessBean;
import com.wdhhr.wswsvipnew.net.NetworkManager;
import com.wdhhr.wswsvipnew.net.ApiSubscriberCallBack;
import com.wdhhr.wswsvipnew.utils.ImageUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class WeibiShareDetailActivity extends BaseActivity {


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
    @BindView(R.id.iv_user_photo)
    ImageView mIvUserPhoto;
    @BindView(R.id.tv_user_name)
    TextView mTvUserName;
    @BindView(R.id.tv_weibi_get_time)
    TextView mTvWeibiGetTime;
    @BindView(R.id.tv_used)
    TextView mTvUsed;
    @BindView(R.id.tv_weibi_num)
    TextView mTvWeibiNum;
    @BindView(R.id.layout_share_detail)
    LinearLayout mLayoutShareDetail;
    private HashMap<String, String> mMap;

    @Override
    protected int setViewId() {
        return R.layout.activity_weibi_share_record;
    }

    @Override
    protected void init() {
        mTvShareTitle.setText(R.string.share_detail);
        mTvShareTime.setVisibility(View.GONE);
        mListviewShareRecord.setVisibility(View.GONE);
        mMap = new HashMap<>();
        String wGlod = getIntent().getStringExtra("wGlod");
        mMap.put("wGlod", wGlod);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void loadData() {
        loadShareDetailInfo(mMap);
    }


    private static final String TAG = "WeibiShareDetailActivity";

    private void loadShareDetailInfo(Map<String, String> map) {
        NetworkManager.getApiService().weibiShareSucess(map)
                .subscribeOn(Schedulers.io())
                .map(new Function<WeibiShareSucessBean, WeibiShareSucessBean>() {
                    @Override
                    public WeibiShareSucessBean apply(WeibiShareSucessBean WeibiShareSucessBean) throws Exception {
                        return WeibiShareSucessBean;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriberCallBack<WeibiShareSucessBean>() {
                    @Override
                    public void onSuccess(WeibiShareSucessBean WeibiShareSucessBean) {
                        mTvUserName.setText(WeibiShareSucessBean.getData().getName());
                        ImageUtils.loadCircleImageUrl(mIvUserPhoto, WeibiShareSucessBean.getData().getUserPhoto(), R.mipmap.icon_head, WeibiShareDetailActivity.this);
                        com.wdhhr.wswsvipnew.model.dataBase.WeibiShareSucessBean.DataBean.GoldBean.CtimeBean ctime = WeibiShareSucessBean.getData().getGold().getCtime();
                        mTvWeibiGetTime.setText(ctime.getYear() + "/" + ctime.getMonth() + "/" + ctime.getDay());
                        mTvWeibiNum.setText(String.valueOf(WeibiShareSucessBean.getData().getGold().getMoney()) + getResources().getString(R.string.weibi));
                        ImageUtils.loadCircleImageUrl(mIvHeadIcon, WeibiShareSucessBean.getData().getMyPhoto(), R.mipmap.icon_head, WeibiShareDetailActivity.this);
                        mTvShareMoney.setText(String.valueOf(WeibiShareSucessBean.getData().getGold().getMoney()));
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        showLongToast("网络繁忙" + t.getMessage());
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_share_back:
                finish();
                break;
        }
    }

}
