package com.wdhhr.wswsvipnew.net;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by felear on 2017/8/13 0013.
 */

public class MyInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Log.d("retrofitRequest", String.format("Sending request %s on %s%n%s",
                request.url(), chain.connection(), request.headers()));
        Response response = chain.proceed(request);
        ResponseBody body = response.peekBody(1024*1024);
        String ss = body.string();
        Log.d("retrofitResponse", ss);
        return response;
    }
}
