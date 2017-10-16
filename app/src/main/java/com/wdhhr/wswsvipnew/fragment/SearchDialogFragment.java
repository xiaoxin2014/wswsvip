package com.wdhhr.wswsvipnew.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.wdhhr.wswsvipnew.R;
import com.wdhhr.wswsvipnew.base.BaseFragment;
import com.wdhhr.wswsvipnew.base.OnSelTipsPwSureListener;
import com.wdhhr.wswsvipnew.constant.ShopSearchConstants;
import com.wdhhr.wswsvipnew.model.ShopCommonBean;
import com.wdhhr.wswsvipnew.model.dataBase.SearchKeyBean;
import com.wdhhr.wswsvipnew.net.NetworkManager;
import com.wdhhr.wswsvipnew.net.ApiSubscriberCallBack;
import com.wdhhr.wswsvipnew.utils.DeviceUtils;
import com.wdhhr.wswsvipnew.widget.FlowLayout;

import org.litepal.crud.DataSupport;
import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by felear on 2017/8/15 0015.
 */

public class SearchDialogFragment extends BaseFragment {

    private final String TAG = SearchDialogFragment.class.getSimpleName();

    @BindView(R.id.tv_clear_all_history)
    TextView mTvClearAllHistory;
    @BindView(R.id.flowlayout_hot)
    FlowLayout mFlowHot;
    @BindView(R.id.flowlayout_history)
    FlowLayout mFlowHistory;
    Unbinder unbinder;
    private int mPaddingOffset = DeviceUtils.dip2px(8);
    private List<SearchKeyBean> mKeyBeanList;

    @Override
    protected int setViewId() {
        return R.layout.fragment_search_dialog;
    }

    @Override
    protected void init() {
    }

    @Override
    protected void initEvent() {
        mTvClearAllHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSelTipsPw(R.string.confirm_clear_all_history, new OnSelTipsPwSureListener() {
                    @Override
                    public void onSure() {
                        mFlowHistory.removeAllViews();
                        //清空数据库
                        DataSupport.deleteAll(SearchKeyBean.class);
                        mTvClearAllHistory.setVisibility(View.GONE);
                    }
                });

            }
        });
    }

    @Override
    protected void loadData() {
        loadHistoryKey(0);

        // 获取热销商品
        NetworkManager.getApiService().getHostShopList()
                .subscribeOn(Schedulers.io())
                .map(new Function<ShopCommonBean, ShopCommonBean>() {
                    @Override
                    public ShopCommonBean apply(ShopCommonBean shopCommonBean) throws Exception {
                        return shopCommonBean;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriberCallBack<ShopCommonBean>() {
                    @Override
                    public void onSuccess(ShopCommonBean shopCommonBean) {
                        List<String> goodsName = shopCommonBean.getData().getGoodsName();

                        for (int i = 0; i < goodsName.size() && i < 10; i++) {
                            if (goodsName.get(i) != null) {
                                mFlowHot.addView(getKeyWordView(goodsName.get(i)));
                            }
                        }


                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                });
    }

    private TextView getKeyWordView(final String keyWord) {
        TextView textView = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.layout_search_tag, mFlowHot, false);
        textView.setPadding(mPaddingOffset, mPaddingOffset, mPaddingOffset, mPaddingOffset);
        textView.setText(keyWord);
        //设置流式布局数据
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(keyWord, ShopSearchConstants.EVENT_START_SEARCH);
            }
        });
        return textView;
    }

    @Subscriber(tag = ShopSearchConstants.EVENT_UPDATE_HISTORYKEY)
    private void loadHistoryKey(final int num) {
        // 历史搜索关键字
        mKeyBeanList = DataSupport
                .limit(15)
                .order("id desc")
                .find(SearchKeyBean.class);
        mFlowHistory.removeAllViews();

        for (int i = 0; i < mKeyBeanList.size(); i++) {
            if (mKeyBeanList.get(i).getKeyWord() != null) {
                mFlowHistory.addView(getKeyWordView(mKeyBeanList.get(i).getKeyWord()));
            }
        }


        if (mFlowHistory.getChildCount() == 0) {
            mTvClearAllHistory.setVisibility(View.GONE);
        } else {
            mTvClearAllHistory.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {


    }


}
