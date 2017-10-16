package com.wdhhr.wswsvipnew;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.wdhhr.wswsvipnew.dao.UserDao;
import com.wdhhr.wswsvipnew.model.ShopCommonBean;
import com.wdhhr.wswsvipnew.model.cache.ProfitAmountBean;
import com.wdhhr.wswsvipnew.model.dataBase.BrandListBean;
import com.wdhhr.wswsvipnew.model.dataBase.BusinessInfoBean;
import com.wdhhr.wswsvipnew.model.dataBase.GoodsListBean;
import com.wdhhr.wswsvipnew.model.dataBase.UsersBean;
import com.wdhhr.wswsvipnew.net.NetworkManager;
import com.wdhhr.wswsvipnew.utils.DeviceUtils;
import com.wdhhr.wswsvipnew.utils.FileUtil;
import com.wdhhr.wswsvipnew.utils.LocalUtils;
import com.wdhhr.wswsvipnew.utils.NetworkUtils;
import com.wdhhr.wswsvipnew.utils.SystemUtil;

import org.litepal.LitePalApplication;
import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;
import org.simple.eventbus.EventBus;

/**
 * Created by felear on 2017/8/13 0013.
 */

public class MyApplication extends LitePalApplication {

    private static UsersBean userInfo;
    public static long loginTime;
    public static BusinessInfoBean.DataBean shopInfo;
    public static ShopCommonBean.DataBean mShopNumInfo;
    public static ProfitAmountBean mProfitAmountBean;

    public static void setUserInfo(UsersBean userInfo) {
        MyApplication.userInfo = userInfo;
    }

    public static UsersBean getUserInfoAndLogin() {
        if (userInfo != null) {
            return userInfo;
        } else {
            if (System.currentTimeMillis() - loginTime > 30000) {
                UserDao.login();
            }
        }
        return null;
    }

    public static UsersBean getUserInfo() {
        return userInfo;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SystemUtil.setContext(this);
        NetworkUtils.setContext(this);
        DeviceUtils.setContext(this);
        NetworkManager.setContext(this);
        FileUtil.setContext(this);
        NetworkManager.init();
        LocalUtils.setLocalList(getContext(), "area.json");
        Log.d("printl", "MyApp");

        SQLiteDatabase db = Connector.getDatabase();
        DataSupport.deleteAll(GoodsListBean.class);
        DataSupport.deleteAll(BrandListBean.class);

        EventBus.getDefault().register(this);
        mProfitAmountBean = new ProfitAmountBean();

        //友盟
        Config.DEBUG = true;
        UMShareAPI umShareAPI = UMShareAPI.get(this);

        PlatformConfig.setWeixin("wx7ddc386091092fce", "de1c744e7a2f5c0211d8d2e27c36f8c6");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad", "http://sns.whalecloud.com");
        Config.isUmengQQ = false;
        Config.isUmengSina = false;
        Config.isUmengWx = false;

    }

    @Override
    public void onTerminate() {
        EventBus.getDefault().unregister(this);
        super.onTerminate();
    }
}
