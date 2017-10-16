package com.wdhhr.wswsvipnew.net;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.wdhhr.wswsvipnew.api.ApiService;
import com.wdhhr.wswsvipnew.constant.UrlConstants;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by felear on 2017/8/13 0013.
 */

public class NetworkManager {

    private static Context mContext;
    private static ApiService apiService;   //全局网络接口


    public static void setContext(Context context) {
        mContext = context;
    }

    public static void init() {
        //处理畸形Json
        Gson mGson = new GsonBuilder()
                .setLenient()
                .create();

        OkHttpClient okClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new MyInterceptor())//添加网络拦截器,拦截网络请求，写响应头，配置Cache-Control，然后交给Okhttp解析
                .cookieJar(new NovateCookieManger(mContext))
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UrlConstants.BASE_URl)
                .client(okClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(mGson))
                .build();

        apiService = retrofit.create(ApiService.class);
    }


    public static ApiService getApiService() {
        return apiService;
    }

}

