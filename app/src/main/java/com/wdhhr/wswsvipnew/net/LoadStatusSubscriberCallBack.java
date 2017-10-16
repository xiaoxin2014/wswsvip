package com.wdhhr.wswsvipnew.net;

import android.util.Log;

import com.wdhhr.wswsvipnew.utils.LoadErrorUtils;
import com.wdhhr.wswsvipnew.utils.NetworkUtils;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.List;

/**
 * Created by felear on 2017/8/13 0013.
 */

public abstract class LoadStatusSubscriberCallBack<T> implements Subscriber<T> {

    private final LoadErrorUtils loadErrorUtils;

    private static final String TAG = "XListViewSubscriberCall";

    public LoadStatusSubscriberCallBack(LoadErrorUtils loadErrorUtils) {
        this.loadErrorUtils = loadErrorUtils;
    }

    @Override
    public void onSubscribe(Subscription s) {
        s.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(T t) {
        try {
            List list = getList(t);
            loadErrorUtils.setLoadMoreStatus(false);
            if (getListStatus(t) == 0 && list != null) {
                // 请求成功
                onSuccess(t);

                int listLength = list.size();
                if (listLength > 0) {
                    if (listLength > 9) {
                        loadErrorUtils.setLoadMoreStatus(true);
                    }
                    loadErrorUtils.setSuccess();
                } else {
                    loadErrorUtils.setEmpty();
                }

            } else {
                loadErrorUtils.setBadData();
            }
            loadErrorUtils.stopFresh();
        } catch (Exception e) {
            Log.e(TAG, "onNext: " + e.getMessage());
        }

    }

    @Override
    public void onError(Throwable t) {
        try {
            if (!NetworkUtils.isOnline()) {
                loadErrorUtils.setNoNet();
            } else {
                loadErrorUtils.setBadData();
            }
            loadErrorUtils.stopFresh();
            onComplete();
        } catch (Exception e) {
            Log.e(TAG, "onNext: " + e.getMessage());
        }

    }

    public abstract void onSuccess(T t);

    public abstract int getListStatus(T t);

    public abstract List getList(T t);

}
