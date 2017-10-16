package com.wdhhr.wswsvipnew.dao;

/**
 * Created by felear on 2017/8/27 0027.
 */

public interface DaoInterface<T> {

    void onSuccess(T t);

    void onFail();

}
