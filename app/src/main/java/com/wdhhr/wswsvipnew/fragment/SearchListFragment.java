package com.wdhhr.wswsvipnew.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wdhhr.wswsvipnew.R;
import com.wdhhr.wswsvipnew.activity.ShopSearchSubActivity;
import com.wdhhr.wswsvipnew.adapter.CommonAdapter;
import com.wdhhr.wswsvipnew.adapter.RecyclerAdapter;
import com.wdhhr.wswsvipnew.adapter.ViewHolder;
import com.wdhhr.wswsvipnew.base.BaseFragment;
import com.wdhhr.wswsvipnew.constant.ShopSearchConstants;
import com.wdhhr.wswsvipnew.model.ShopCommonBean;
import com.wdhhr.wswsvipnew.model.dataBase.CategoryListBean;
import com.wdhhr.wswsvipnew.net.NetworkManager;
import com.wdhhr.wswsvipnew.net.LoadStatusSubscriberCallBack;
import com.wdhhr.wswsvipnew.utils.LoadErrorUtils;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by felear on 2017/8/15 0015.
 */

public class SearchListFragment extends BaseFragment {

    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.rcv)
    RecyclerView rcv;
    private CommonAdapter<CategoryListBean> mLvAdapter;

    private final String TAG = SearchListFragment.class.getSimpleName();

    private String mCategoryId = "";
    private RecyclerAdapter<CategoryListBean> mRcvAdapter;
    private List<CategoryListBean> mFirstList;
    private List<CategoryListBean> mSecondList;
    private LoadErrorUtils mXlvUtils;

    private int mLastPosition = 0;


    @Override
    protected int setViewId() {
        return R.layout.fragment_search_list;
    }

    @Override
    protected void init() {

        // 获取本地缓存
        mFirstList = new ArrayList<>();
        mSecondList = new ArrayList<>();

        // 设置一级列表
        mLvAdapter = new CommonAdapter<CategoryListBean>(getContext(), mFirstList, R.layout.item_search_tab_left) {
            private List<View> mViewList = new ArrayList<>();

            @Override
            public void convert(ViewHolder helper, final int position, final CategoryListBean item) {
                helper.setText(R.id.title, item.getCategoryName());

                setViewTyep(helper.getConvertView(), position);
                if (!mViewList.contains(helper.getConvertView())) {
                    mViewList.add(helper.getConvertView());
                }

                helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        for (View view : mViewList) {
                            setViewTyep(view, mLastPosition + 1);
                        }
                        mLastPosition = position;
                        setViewTyep(v, position);

                        mCategoryId = item.getCategoryId();
                        mSecondList.clear();
                        loadData();
                    }
                });

            }

            private void setViewTyep(View view, int position) {
                if (mLastPosition == position) {
                    view.findViewById(R.id.view_header).setBackgroundResource(R.color.colorPrimary);
                    ((TextView) view.findViewById(R.id.title)).setTextColor(getResources().getColor(R.color.colorPrimary));
                } else {
                    view.findViewById(R.id.view_header).setBackgroundColor(0x00000000);
                    ((TextView) view.findViewById(R.id.title)).setTextColor(getResources().getColor(R.color.fontTitle));

                }
            }
        };
        listView.setAdapter(mLvAdapter);

        // 设置二级列表
        mRcvAdapter = new RecyclerAdapter<CategoryListBean>(getContext(), mSecondList, R.layout.item_search_tab_right) {

            @Override
            public void convert(RecyclerAdapter.ViewHolder helper, final int position, CategoryListBean item) {
                helper.setText(R.id.tv_title, item.getCategoryName());
                helper.setImageByUrl(R.id.iv_icon, item.getPic(), getContext());
                helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("position", position);
                        bundle.putString("title", mFirstList.get(mLastPosition).getCategoryName());
                        bundle.putString("json_arr", new Gson().toJson(mSecondList));
                        readyGo(ShopSearchSubActivity.class, bundle);
                    }
                });
            }
        };

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false);
        rcv.setLayoutManager(gridLayoutManager);
        rcv.setAdapter(mRcvAdapter);

        mXlvUtils = new LoadErrorUtils(listView, mView, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });

    }

    @Override
    protected void initEvent() {
    }

    @Override
    protected void loadData() {

        HashMap<String, String> hashMap = new HashMap<>();

        if (!TextUtils.equals(mCategoryId, "")) {
            hashMap.put(ShopSearchConstants.CATEGORY_FID, mCategoryId);
        }

        showLoadPw();

        NetworkManager.getApiService().getShopIndexList(hashMap)
                .subscribeOn(Schedulers.io())
                .map(new Function<ShopCommonBean, ShopCommonBean>() {
                    @Override
                    public ShopCommonBean apply(ShopCommonBean shopCommonBean) throws Exception {
                        return shopCommonBean;
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new LoadStatusSubscriberCallBack<ShopCommonBean>(mXlvUtils) {
                    @Override
                    public void onSuccess(ShopCommonBean shopCommonBean) {
                        if (TextUtils.equals(mCategoryId, "")) {
                            mFirstList.clear();
                            mFirstList.addAll(shopCommonBean.getData().getCategoryList());
                            if (shopCommonBean.getData().getCategoryList().size() > 0) {
                                mCategoryId = shopCommonBean.getData().getCategoryList().get(0).getCategoryId();
                                loadData();
                            }
                        } else {
                            mSecondList.addAll(shopCommonBean.getData().getCategoryList());
                        }

                        DataSupport.saveAll(shopCommonBean.getData().getCategoryList());
                    }

                    @Override
                    public int getListStatus(ShopCommonBean shopCommonBean) {
                        return shopCommonBean.getStatus();
                    }

                    @Override
                    public List getList(ShopCommonBean shopCommonBean) {
                        return shopCommonBean.getData().getCategoryList();
                    }

                    @Override
                    public void onComplete() {
                        dismissLoadPw();
                        mLvAdapter.notifyDataSetChanged();
                        mRcvAdapter.notifyDataSetChanged();

                    }
                });
    }

    @Override
    public void onClick(View view) {

    }
}
