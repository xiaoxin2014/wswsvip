package com.wdhhr.wswsvipnew.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.WindowManager;

import com.umeng.socialize.UMShareAPI;
import com.wdhhr.wswsvipnew.MyApplication;
import com.wdhhr.wswsvipnew.R;
import com.wdhhr.wswsvipnew.base.BaseActivity;
import com.wdhhr.wswsvipnew.base.BaseFragment;
import com.wdhhr.wswsvipnew.base.OnSelTipsPwSureListener;
import com.wdhhr.wswsvipnew.constant.EventConstants;
import com.wdhhr.wswsvipnew.constant.MyConstants;
import com.wdhhr.wswsvipnew.fragment.CircleFragment;
import com.wdhhr.wswsvipnew.fragment.HomeFragment;
import com.wdhhr.wswsvipnew.fragment.MyFragment;
import com.wdhhr.wswsvipnew.fragment.ShopFragment;
import com.wdhhr.wswsvipnew.model.UserCommonBean;
import com.wdhhr.wswsvipnew.model.dataBase.VersionBean;
import com.wdhhr.wswsvipnew.net.NetworkManager;
import com.wdhhr.wswsvipnew.net.ApiSubscriberCallBack;
import com.wdhhr.wswsvipnew.utils.ShareUtils;
import com.wdhhr.wswsvipnew.utils.SystemUtil;
import com.wdhhr.wswsvipnew.widget.update.FsAppUpdate;
import com.wdhhr.wswsvipnew.widget.update.UpdateDialog;

import org.simple.eventbus.Subscriber;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static com.wdhhr.wswsvipnew.constant.MyConstants.REQUEST_INSTALL_APP;

public class MainActivity extends BaseActivity {

    private final static String TAG = MainActivity.class.getSimpleName();
    private HomeFragment mHomeFragment;
    private CircleFragment mCircleFragment;
    private ShopFragment mShopFragment;
    private MyFragment mMyFragment;

    private BaseFragment mLastFragment;

    @Override
    protected int setViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        mcanBack = false;
        mHomeFragment = new HomeFragment();
        mCircleFragment = new CircleFragment();
        mShopFragment = new ShopFragment();
        mMyFragment = new MyFragment();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.add(R.id.frame, mCircleFragment);
        transaction.hide(mCircleFragment);
        transaction.add(R.id.frame, mShopFragment);
        transaction.hide(mShopFragment);
        transaction.add(R.id.frame, mHomeFragment);
        transaction.hide(mHomeFragment);
        transaction.add(R.id.frame, mMyFragment);
        mLastFragment = mMyFragment;
        transaction.commit();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                changeFragment(mHomeFragment);
            }
        }, 10);

    }

    @Override
    protected void initEvent() {

    }

    @Subscriber(tag = EventConstants.TRAD_SUCCESSFUL_CLICK)
    private void TradSuccessful(int num) {
        changeFragment(mMyFragment);
    }

    @Subscriber(tag = EventConstants.LOG_STATUS_CHANGE)
    private void loginStatusChange(int num) {
        if (MyApplication.getUserInfo() == null) {
            finish();
        }
    }

    @Override
    protected void loadData() {

        // 版本更新
        NetworkManager.getApiService().getVersion()
                .subscribeOn(Schedulers.io())
                .map(new Function<UserCommonBean, UserCommonBean>() {
                    @Override
                    public UserCommonBean apply(UserCommonBean userCommonBean) throws Exception {
                        return userCommonBean;
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriberCallBack<UserCommonBean>() {
                    @Override
                    public void onSuccess(UserCommonBean userCommonBean) {
                        if (userCommonBean.getStatus() == 0) {
                            VersionBean getChoice = userCommonBean.getData().getGetChoice();
                            if (getChoice.getAndroidUpdate() > SystemUtil.getSystemVersionCode()) {
                                FsAppUpdate.showDialog(MainActivity.this
                                        , "您当前版本过低，请立即升级"
                                        , MyConstants.VIP_UPLOAD_URL
                                        , getChoice.getIsUpdate());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                });
    }

    @Override
    public void onClick(View view) {

        BaseFragment fragment = null;


        switch (view.getId()) {
            case R.id.rb_home:
                fragment = mHomeFragment;
                break;
            case R.id.rb_circle:
                fragment = mCircleFragment;
                break;
            case R.id.rb_shop:
                fragment = mShopFragment;
                break;
            case R.id.rb_my:
                fragment = mMyFragment;
                break;
            default:
                mLastFragment.onClick(view);
        }

        if (fragment != null) {
            changeFragment(fragment);
        }

    }

    private void changeFragment(BaseFragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(mLastFragment);
        transaction.show(fragment);
        mLastFragment = fragment;
        transaction.commit();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //权限被拒绝
        if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
            if (requestCode == REQUEST_INSTALL_APP) {
                return;
            }
            showSelTipsPw(R.string.headicon_permission_denied, new OnSelTipsPwSureListener() {
                @Override
                public void onSure() {
                    Intent intent = getAppDetailSettingIntent(MainActivity.this);
                    startActivity(intent);
                }
            });
        } else {
            //权限被允许
            if (requestCode == REQUEST_INSTALL_APP) {
                UpdateDialog.goToDownload(MainActivity.this,MyConstants.VIP_UPLOAD_URL);
            }else {
                ShareUtils.compressPic(this, ShareUtils.sBitmap);
            }

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

    }


}
