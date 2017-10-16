package com.wdhhr.wswsvipnew.utils;

import android.app.Activity;
import android.view.View;
import android.widget.ListView;

import com.wdhhr.wswsvipnew.MyApplication;
import com.wdhhr.wswsvipnew.R;
import com.wdhhr.wswsvipnew.dao.UserDao;
import com.wdhhr.wswsvipnew.widget.XListView;

/**
 * Created by felear on 2017/9/4 0004.
 */

public class LoadErrorUtils {

    private XListView mXListView;
    // 重新加载事件
    private final View.OnClickListener onClickListener;
    private ListView mListView;

    private View mBadDataView;
    private View mNoNetView;
    private View mEmptyView;

    public void setEmptyView(View view) {
        mEmptyView = view;
    }

    public LoadErrorUtils(XListView xListView, Activity activity, View.OnClickListener onClickListener) {
        mXListView = xListView;
        mXListView.setPullLoadEnable(false);
        mEmptyView = activity.findViewById(R.id.layout_empty_no_data);
        mBadDataView = activity.findViewById(R.id.layout_empty_bad_data);
        mNoNetView = activity.findViewById(R.id.layout_empty_no_net);
        this.onClickListener = onClickListener;
    }

    public LoadErrorUtils(ListView listView, Activity activity, View.OnClickListener onClickListener) {
        mListView = listView;
        mEmptyView = activity.findViewById(R.id.layout_empty_no_data);
        mBadDataView = activity.findViewById(R.id.layout_empty_bad_data);
        mNoNetView = activity.findViewById(R.id.layout_empty_no_net);
        this.onClickListener = onClickListener;
    }

    public LoadErrorUtils(Activity activity, View.OnClickListener onClickListener) {
        mEmptyView = activity.findViewById(R.id.layout_empty_no_data);
        mBadDataView = activity.findViewById(R.id.layout_empty_bad_data);
        mNoNetView = activity.findViewById(R.id.layout_empty_no_net);
        this.onClickListener = onClickListener;
    }

    public LoadErrorUtils(XListView xListView, View view, View.OnClickListener onClickListener) {
        mXListView = xListView;
        mXListView.setPullLoadEnable(false);
        mEmptyView = view.findViewById(R.id.layout_empty_no_data);
        mBadDataView = view.findViewById(R.id.layout_empty_bad_data);
        mNoNetView = view.findViewById(R.id.layout_empty_no_net);
        this.onClickListener = onClickListener;
    }

    public LoadErrorUtils(ListView listView, View view, View.OnClickListener onClickListener) {
        mListView = listView;
        mEmptyView = view.findViewById(R.id.layout_empty_no_data);
        mBadDataView = view.findViewById(R.id.layout_empty_bad_data);
        mNoNetView = view.findViewById(R.id.layout_empty_no_net);
        this.onClickListener = onClickListener;
    }

    public LoadErrorUtils(View view, View.OnClickListener onClickListener) {
        mEmptyView = view.findViewById(R.id.layout_empty_no_data);
        mBadDataView = view.findViewById(R.id.layout_empty_bad_data);
        mNoNetView = view.findViewById(R.id.layout_empty_no_net);
        this.onClickListener = onClickListener;
    }

    /**
     * 是否可以加载更过
     *
     * @param load 是否
     */
    public void setLoadMoreStatus(boolean load) {
        if (mXListView != null) {
            mXListView.setPullLoadEnable(load);
        }
    }

    /**
     * 停止刷新
     */
    public void stopFresh() {
        if (mXListView != null) {
            mXListView.stopLoadMore();
            mXListView.stopRefresh();
            mXListView.setRefreshTime(StringUtils.getCurrentRefreshTime());
        }
    }

    // 设置无数据
    public void setSuccess() {
        changeEmptyLayout(null);
    }

    // 设置无数据
    public void setEmpty() {
        changeEmptyLayout(mEmptyView);
    }

    // 设置数据异常
    public void setBadData() {
        changeEmptyLayout(mBadDataView);
        if (System.currentTimeMillis() - MyApplication.loginTime > 30000) {
            UserDao.login();
        }
    }

    // 设置无网络
    public void setNoNet() {
        changeEmptyLayout(mNoNetView);
    }

    // 改变错误状态
    private void changeEmptyLayout(View view) {
        if (mXListView != null) {
            View emptyView = mXListView.getEmptyView();
            if (emptyView != null) {
                mXListView.removeHeaderView(emptyView);
                emptyView.setVisibility(View.GONE);
            }
            mXListView.setEmptyView(view);
        }

        if (mListView != null) {
            View emptyView = mListView.getEmptyView();
            if (emptyView != null) {
                mListView.removeHeaderView(emptyView);
                emptyView.setVisibility(View.GONE);
            }
            mListView.setEmptyView(view);
        }

        // 普通控件
        if (mXListView == null && mListView == null) {
            if (mEmptyView != null) {
                mEmptyView.setVisibility(View.GONE);
            }

            if (mBadDataView != null) {
                mBadDataView.setVisibility(View.GONE);
            }

            if (mNoNetView != null) {
                mNoNetView.setVisibility(View.GONE);
            }

            if (view != null) {
                view.setVisibility(View.VISIBLE);
            }
        }

        // 设置提示画面点击事件
        if (view != null) {
            View reload = view.findViewById(R.id.tv_empty_reload);
            if (reload != null && onClickListener != null) {
                reload.setOnClickListener(onClickListener);
            }
        }
    }

}
