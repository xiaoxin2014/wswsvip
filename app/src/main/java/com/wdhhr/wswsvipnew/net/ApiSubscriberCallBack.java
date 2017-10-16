package com.wdhhr.wswsvipnew.net;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * Created by felear on 2017/8/13 0013.
 */

public abstract class ApiSubscriberCallBack<T> implements Subscriber<T> {

    @Override
    public void onSubscribe(Subscription s) {
        s.request(Long.MAX_VALUE);
    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    @Override
    public void onError(Throwable t) {
        onFailure(t);
    }

    @Override
    public void onComplete() {

    }

    public abstract void onSuccess(T t);

    public abstract void onFailure(Throwable t);
}
