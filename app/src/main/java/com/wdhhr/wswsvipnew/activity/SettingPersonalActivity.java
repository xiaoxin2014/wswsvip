package com.wdhhr.wswsvipnew.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wdhhr.wswsvipnew.MyApplication;
import com.wdhhr.wswsvipnew.R;
import com.wdhhr.wswsvipnew.base.BaseActivity;
import com.wdhhr.wswsvipnew.base.OnSelTipsPwSureListener;
import com.wdhhr.wswsvipnew.constant.EventConstants;
import com.wdhhr.wswsvipnew.constant.UserContants;
import com.wdhhr.wswsvipnew.model.UserCommonBean;
import com.wdhhr.wswsvipnew.model.dataBase.UsersBean;
import com.wdhhr.wswsvipnew.net.ApiSubscriberCallBack;
import com.wdhhr.wswsvipnew.net.NetworkManager;
import com.wdhhr.wswsvipnew.utils.ImageUtils;
import com.wdhhr.wswsvipnew.utils.FileUtil;
import com.wdhhr.wswsvipnew.utils.StringUtils;
import com.wdhhr.wswsvipnew.utils.SystemUtil;
import com.wdhhr.wswsvipnew.utils.WindowUtils;
import com.yalantis.ucrop.UCrop;


import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.io.File;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public class SettingPersonalActivity extends BaseActivity {

    final int REQUEST_WRITE_CAMERA = 1;
    final int REQUEST_WRITE_PHOTO = 0;
    @BindView(R.id.title)
    TextView mTvTitle;

    @BindView(R.id.tv_back)
    TextView mTvBack;
    @BindView(R.id.rv_header)
    ImageView mRvHeader;
    @BindView(R.id.tv_nick_name)
    TextView mTvNickName;
    @BindView(R.id.tv_phone_num)
    TextView mTvPhoneNum;
    @BindView(R.id.tv_level)
    TextView mTvLevel;
    @BindView(R.id.iv_more)
    ImageView mIvMore;
    @BindView(R.id.tv_right)
    TextView mTvRight;
    @BindView(R.id.layout_header)
    RelativeLayout mLayoutHeader;
    @BindView(R.id.layout_nick_name)
    LinearLayout mLayoutNickName;
    @BindView(R.id.tv_expire_time)
    TextView mTvExpireTime;
    @BindView(R.id.btn_exit)
    TextView mBtnExit;
    @BindView(R.id.layout_expire_time)
    LinearLayout mLayoutExpireTime;

    private String TAG = SettingPersonalActivity.class.getSimpleName();
    private UsersBean mUserInfo;
    private PopupWindow mAlphaPw;
    private String mstrPath;

    @Override
    protected int setViewId() {
        return R.layout.activity_setting_personal;
    }

    @Override
    protected void init() {
        mTvTitle.setText(R.string.user_data);
        mTvBack.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void loadData() {
        setHeadIcon(0);
    }

    private void dialogView() {
        mAlphaPw = WindowUtils.getAlphaPw(this, R.layout.pw_selectphoto);
        WindowUtils.setWindowAlpha(this, 0.5f);
        mAlphaPw.setAnimationStyle(R.style.pw_slide);
        mAlphaPw.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);

    }

    //修改用户名
    @Subscriber(tag = EventConstants.USER_INFO_CHANGE)
    private void setHeadIcon(int num) {
        mUserInfo = MyApplication.getUserInfo();
        mTvPhoneNum.setText(mUserInfo.getUserAccount());
        mTvLevel.setText(StringUtils.getUserLevel(mUserInfo.getUserLevel()));
        mUserInfo = MyApplication.getUserInfo();
        mTvNickName.setText(mUserInfo.getUserName());
        if (mUserInfo.getUtime() == null) {
            mLayoutExpireTime.setVisibility(View.GONE);
        } else {
            mTvExpireTime.setText(StringUtils.getDataHByLongTime(mUserInfo.getUtime().getTime()));
        }
        ImageUtils.loadCircleImageUrl(mRvHeader, mUserInfo.getUserPhoto(), R.mipmap.icon_head, this);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        File outFile = new File(getCacheDir(), System.currentTimeMillis() + ".jpg");
        switch (requestCode) {
            //打开相册选择照片的回调
            case 1:
                Uri data1 = data.getData();
                Uri uri = Uri.fromFile(outFile);
                UCrop.of(data1, uri)
                        .withMaxResultSize(500, 500)
                        .withAspectRatio(1, 1)
                        .start(this);
                break;
            case 2:
                //拍照回调
                UCrop.of(Uri.fromFile(new File(FileUtil.getAppPath("user", FileUtil.CAMERA))), Uri.fromFile(outFile))
                        .withMaxResultSize(500, 500)
                        .withAspectRatio(1, 1)
                        .start(this);
                break;
            //裁剪照片回调
            case UCrop.REQUEST_CROP:
                mstrPath = UCrop.getOutput(data).getPath();
                changeHeadIcon();
                break;
            default:
                break;
        }
    }

    void changeHeadIcon() {

        if (mstrPath == null) {
            return;
        }

        showLoadPw();
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.ARGB_4444;
        Bitmap bitmap = BitmapFactory.decodeFile(mstrPath, opt);
        String strBase64 = ImageUtils.bitmaptoString(bitmap);

        Log.d(TAG, strBase64.length() + "");

        RequestBody body = new FormBody.Builder()
                .add("picBase64", strBase64)
                .add("changeStatus", "0").build();

        NetworkManager.getApiService().changeHeadIcon(body).subscribeOn(Schedulers.io())
                .map(new Function<UserCommonBean, UserCommonBean>() {
                    @Override
                    public UserCommonBean apply(UserCommonBean changeHeadIconBean) throws Exception {
                        return changeHeadIconBean;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriberCallBack<UserCommonBean>() {
                    @Override
                    public void onSuccess(UserCommonBean userCommonBean) {
                        if (userCommonBean.getStatus() == 0) {
                            MyApplication.getUserInfo().setUserPhoto(mstrPath);
                            EventBus.getDefault().post(0, EventConstants.USER_INFO_CHANGE);
                        } else {
                            showLongToast(R.string.net_business);
                        }
                        dismissLoadPw();
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        dismissLoadPw();
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            //修改头像
            case R.id.layout_header:
                mstrPath = null;
                dialogView();
                break;
            //修改昵称
            case R.id.layout_nick_name:
                Bundle bundle = new Bundle();
                bundle.putString("nickname", mTvNickName.getText().toString());
                readyGo(SettingPersonalNickNameActivity.class, bundle);
                break;
            // 退出登录
            case R.id.btn_exit:

                showSelTipsPw(R.string.log_out_tips, new OnSelTipsPwSureListener() {

                    @Override
                    public void onSure() {
                        showLoadPw();
                        NetworkManager.getApiService().clearSession()
                                .subscribeOn(Schedulers.io())
                                .map(new Function<UserCommonBean, UserCommonBean>() {
                                    @Override
                                    public UserCommonBean apply(UserCommonBean userCommonBean) throws Exception {
                                        return userCommonBean;
                                    }
                                })
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new ApiSubscriberCallBack<UserCommonBean>() {
                                    @Override
                                    public void onSuccess(UserCommonBean userCommonBean) {
                                        dismissLoadPw();
                                        SystemUtil.setSharedString(UserContants.userPwd, null);
                                        MyApplication.setUserInfo(null);
                                        MyApplication.shopInfo = null;
                                        MyApplication.mShopNumInfo = null;
                                        EventBus.getDefault().post(0, EventConstants.LOG_STATUS_CHANGE);
                                        readyGoThenKill(LoginActivity.class);
                                    }

                                    @Override
                                    public void onFailure(Throwable t) {
                                        dismissLoadPw();
                                    }
                                });
                    }
                });
                break;
            //拍照
            case R.id.tv_camera:
                //相机
                if (Build.VERSION.SDK_INT >= 23) {
                    //判断是否有这个权限
                    if ((ContextCompat.checkSelfPermission(SettingPersonalActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(SettingPersonalActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)) {
                        //2、申请权限: 参数二：权限的数组；参数三：请求码
                        Log.v(TAG, "申请权限");
                        mAlphaPw.dismiss();
                        ActivityCompat.requestPermissions(SettingPersonalActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, REQUEST_WRITE_CAMERA);
                    } else {
                        mAlphaPw.dismiss();
                        openCamera(SettingPersonalActivity.this, 2);
                    }
                } else {
                    mAlphaPw.dismiss();
                    openCamera(SettingPersonalActivity.this, 2);
                }
                break;
            //从手机相册选择
            case R.id.tv_photo:
                if (Build.VERSION.SDK_INT >= 23) {
                    //判断是否有这个权限
                    Log.v(TAG, "" + ActivityCompat.checkSelfPermission(SettingPersonalActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    );
                    if (ContextCompat.checkSelfPermission(SettingPersonalActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        //2、申请权限: 参数二：权限的数组；参数三：请求码
                        mAlphaPw.dismiss();
                        ActivityCompat.requestPermissions(SettingPersonalActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_CAMERA);
                    } else {
                        mAlphaPw.dismiss();
                        openPhoto(1);
                    }
                } else {
                    mAlphaPw.dismiss();
                    openPhoto(1);
                }

                break;
            case R.id.tv_cancle:
                mAlphaPw.dismiss();
                break;
        }
    }

    //权限申请的回调
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_WRITE_PHOTO:
                //权限被拒绝
                if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    showSelTipsPw(R.string.headicon_permission_denied, new OnSelTipsPwSureListener() {
                        @Override
                        public void onSure() {
                            Intent intent = getAppDetailSettingIntent(SettingPersonalActivity.this);
                            startActivity(intent);
                        }
                    });
                } else {
                    //权限被允许
                    mAlphaPw.dismiss();
                    openPhoto(1);
                }
                break;
            case REQUEST_WRITE_CAMERA:
                //权限被拒绝
                if (grantResults[0] == PackageManager.PERMISSION_DENIED || grantResults[1] == PackageManager.PERMISSION_DENIED) {
                    showSelTipsPw(R.string.headicon_permission_denied, new OnSelTipsPwSureListener() {
                        @Override
                        public void onSure() {
                            Intent intent = getAppDetailSettingIntent(SettingPersonalActivity.this);
                            startActivity(intent);
                        }
                    });
                } else {
                    //权限被允许
                    mAlphaPw.dismiss();
                    openCamera(SettingPersonalActivity.this, 2);
                }
                break;
        }
    }


}
