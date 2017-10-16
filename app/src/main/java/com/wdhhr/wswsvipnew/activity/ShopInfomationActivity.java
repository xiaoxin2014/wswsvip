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
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wdhhr.wswsvipnew.MyApplication;
import com.wdhhr.wswsvipnew.R;
import com.wdhhr.wswsvipnew.base.BaseActivity;
import com.wdhhr.wswsvipnew.base.OnSelTipsPwSureListener;
import com.wdhhr.wswsvipnew.constant.EventConstants;
import com.wdhhr.wswsvipnew.model.dataBase.BusinessInfoBean;
import com.wdhhr.wswsvipnew.net.ApiSubscriberCallBack;
import com.wdhhr.wswsvipnew.net.NetworkManager;
import com.wdhhr.wswsvipnew.utils.FileUtil;
import com.wdhhr.wswsvipnew.utils.ImageUtils;
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

import static com.wdhhr.wswsvipnew.constant.MyConstants.REQUEST_WRITE_CAMERA;
import static com.wdhhr.wswsvipnew.constant.MyConstants.REQUEST_WRITE_PHOTO;

public class ShopInfomationActivity extends BaseActivity {

    @BindView(R.id.icon_title_left)
    ImageView iconTitleLeft;
    @BindView(R.id.title_content)
    TextView titleContent;
    @BindView(R.id.icon_text_right)
    TextView iconTextRight;
    @BindView(R.id.tv_shopkeeper)
    TextView tvShopkeeper;
    @BindView(R.id.icon_title_right)
    ImageView iconTitleRight;
    @BindView(R.id.shop_head)
    ImageView shopHead;
    @BindView(R.id.rela_shop_head)
    RelativeLayout relaShopHead;
    @BindView(R.id.rela_shop_wallpaper)
    RelativeLayout relaShopWallpaper;
    @BindView(R.id.rela_shopkeeper)
    LinearLayout relaShopKeeper;
    final int REQUEST_WRITE = 1;
    @BindView(R.id.tv_shopname)
    TextView tvShopname;
    @BindView(R.id.rela_shopname)
    LinearLayout relaShopname;
    @BindView(R.id.shop_intro)
    TextView tvShopIntro;
    @BindView(R.id.rela_shop_intro)
    LinearLayout relaShopIntro;
    @BindView(R.id.im_shop_wallpager)
    ImageView imShopWallpager;
    @BindView(R.id.business_name)
    TextView tvBusinessName;


    private String TAG = ShopInfomationActivity.class.getSimpleName();
    private Boolean PHOTOTAG = true;
    private PopupWindow mAlphaPw;
    private String mstrPath;

    @Override
    protected int setViewId() {
        return R.layout.activity_shop_infomation;
    }

    @Override
    protected void init() {
        iconTitleLeft.setImageResource(R.mipmap.title_back);
        titleContent.setText(R.string.shopinfo_infomation);
        iconTitleRight.setVisibility(View.GONE);
    }

    @Override
    protected void initEvent() {

    }

    //更新店铺信息
    @Subscriber(tag = EventConstants.UPDATE_SHOP_INFO)
    private void updateShopInfo(int num) {
        loadData();
    }


    @Override
    protected void loadData() {
        if (MyApplication.shopInfo != null) {
            ImageUtils.loadCircleImageUrl(shopHead, MyApplication.shopInfo.getBusinessPic(), R.mipmap.icon_head, this);
            if (TextUtils.isEmpty(MyApplication.shopInfo.getBusinessName())) {
                tvShopname.setText(MyApplication.getUserInfo().getUserPhone() + getResources().getString(R.string.business_name_after));
            } else {
                tvShopname.setText(MyApplication.shopInfo.getBusinessName());
            }

            if (TextUtils.isEmpty(MyApplication.shopInfo.getBusinessContactName())) {
                tvShopkeeper.setText(getResources().getString(R.string.unsetting));
            } else {
                tvShopkeeper.setText(MyApplication.shopInfo.getBusinessContactName());
            }

            if (TextUtils.isEmpty(MyApplication.shopInfo.getBusinessDesc())) {
                tvShopIntro.setText(getResources().getString(R.string.unsetting));
            } else {
                tvShopIntro.setText(MyApplication.shopInfo.getBusinessDesc());
            }
            ImageUtils.loadImageUrl(imShopWallpager, MyApplication.shopInfo.getBusinessBackImg(), R.mipmap.bg_my_shop, this);
        }

    }

    void changeShopIcon(RequestBody body) {
        NetworkManager.getApiService().changeShopIcon(body).subscribeOn(Schedulers.io())
                .map(new Function<BusinessInfoBean, BusinessInfoBean>() {
                    @Override
                    public BusinessInfoBean apply(BusinessInfoBean BusinessInfoBean) throws Exception {
                        return BusinessInfoBean;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriberCallBack<BusinessInfoBean>() {
                    @Override
                    public void onSuccess(BusinessInfoBean BusinessInfoBean) {
                        if (BusinessInfoBean.getStatus() == 0) {
                            if (mstrPath != null) {
                                if (PHOTOTAG) {
                                    MyApplication.shopInfo.setBusinessPic(BusinessInfoBean.getData().getBusinessPic());
                                    Glide.with(ShopInfomationActivity.this).load(mstrPath).into(shopHead);
                                } else {
                                    MyApplication.shopInfo.setBusinessBackImg(BusinessInfoBean.getData().getBusinessBackImg());
                                    Glide.with(ShopInfomationActivity.this).load(mstrPath).into(imShopWallpager);
                                }
                                EventBus.getDefault().post(0, EventConstants.UPDATE_SHOP_INFO);
                                EventBus.getDefault().post(0, EventConstants.UPDATE_SHOP_INFO);
                            }
                        } else {
                            showLongToast(R.string.net_business);
                        }

                        dismissLoadPw();
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Log.v(TAG, t.getMessage());
                    }
                });
    }

    //打开相机或相册回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        File outFile = new File(getCacheDir(), System.currentTimeMillis() + ".jpg");
        switch (requestCode) {
            //相册
            case 1:
                if (PHOTOTAG) {
                    //参数1：要裁剪的图片 参数2：裁剪完成生成的图片
                    UCrop.of(data.getData(), Uri.fromFile(outFile))
                            .withMaxResultSize(500, 500)
                            .withAspectRatio(1, 1)
                            .start(this);
                } else {
                    UCrop.of(data.getData(), Uri.fromFile(outFile))
                            .withMaxResultSize(1600, 900)
                            .withAspectRatio(16, 9)
                            .start(this);
                }

                break;
            //拍照
            case 2:
                if (PHOTOTAG) {
                    UCrop.of(Uri.fromFile(new File(FileUtil.getAppPath("user", FileUtil.CAMERA))), Uri.fromFile(outFile))
                            .withMaxResultSize(500, 500)
                            .withAspectRatio(1, 1)
                            .start(this);
                } else {
                    UCrop.of(Uri.fromFile(new File(FileUtil.getAppPath("user", FileUtil.CAMERA))), Uri.fromFile(outFile))
                            .withMaxResultSize(1600, 900)
                            .withAspectRatio(16, 9)
                            .start(this);
                }
                break;
            //裁剪照片回调
            case UCrop.REQUEST_CROP:
                mstrPath = UCrop.getOutput(data).getPath();
                showLoadPw();
                if (mstrPath == null) {
                    return;
                }
                BitmapFactory.Options opt = new BitmapFactory.Options();
                opt.inPreferredConfig = Bitmap.Config.ARGB_4444;
                Bitmap bitmap = BitmapFactory.decodeFile(mstrPath, opt);
                String strBase64 = ImageUtils.bitmaptoString(bitmap);
                if (PHOTOTAG) {
                    RequestBody body = new FormBody.Builder()
                            .add("businessPic", strBase64)
                            .add("changeStatus", "0").build();
                    changeShopIcon(body);
                } else {
                    RequestBody body = new FormBody.Builder()
                            .add("businessBackImg", strBase64)
                            .add("changeStatus", "3").build();
                    changeShopIcon(body);
                }
                break;
        }
    }


    private void dialogView() {
        mAlphaPw = WindowUtils.getAlphaPw(this, R.layout.pw_selectphoto);
        WindowUtils.setWindowAlpha(this, 0.5f);
        mAlphaPw.setAnimationStyle(R.style.pw_slide);
        mAlphaPw.showAtLocation(ShopInfomationActivity.this.findViewById(R.id.shopinformation), Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.icon_title_left:
                finish();
                break;
            //修改头像
            case R.id.rela_shop_head:
                mstrPath = null;
                PHOTOTAG = true;
                dialogView();
                break;
            //修改墙纸
            case R.id.rela_shop_wallpaper:
                mstrPath = null;
                PHOTOTAG = false;
                dialogView();
                break;
            //修改店主
            case R.id.rela_shopkeeper:
                Bundle bundle = new Bundle();
                bundle.putString("businessContactName", tvShopkeeper.getText().toString().trim());
                readyGo(BusinessContactNameEditActivity.class, bundle);
                break;
            //修改店名
            case R.id.rela_shopname:
                Bundle bundlename = new Bundle();
                bundlename.putString("shopname", tvShopname.getText().toString().trim());
                readyGo(ShopNameEditActivity.class, bundlename);
                break;
            //修改简介
            case R.id.rela_shop_intro:
                Bundle bundleintro = new Bundle();
                bundleintro.putString("shopintro", tvShopIntro.getText().toString().trim());
                readyGo(ShopIntroEditActivity.class, bundleintro);
                break;
            //取消
            case R.id.tv_cancle:
                WindowUtils.closePW(mAlphaPw);
                break;

            case R.id.tv_camera:
                //相机
                if (Build.VERSION.SDK_INT >= 23) {
                    //判断是否有这个权限
                    if ((ContextCompat.checkSelfPermission(ShopInfomationActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(ShopInfomationActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)) {
                        //2、申请权限: 参数二：权限的数组；参数三：请求码
                        Log.v(TAG, "申请权限");
                        mAlphaPw.dismiss();
                        ActivityCompat.requestPermissions(ShopInfomationActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, REQUEST_WRITE_CAMERA);
                    } else {
                        mAlphaPw.dismiss();
                        openCamera(ShopInfomationActivity.this, 2);
                    }
                } else {
                    mAlphaPw.dismiss();
                    openCamera(ShopInfomationActivity.this, 2);
                }
                break;
            case R.id.tv_photo:
                //相册
                if (Build.VERSION.SDK_INT >= 23) {
                    //判断是否有这个权限
                    if (ContextCompat.checkSelfPermission(ShopInfomationActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        //2、申请权限: 参数二：权限的数组；参数三：请求码
                        mAlphaPw.dismiss();
                        ActivityCompat.requestPermissions(ShopInfomationActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_CAMERA);
                    } else {
                        mAlphaPw.dismiss();
                        openPhoto(1);
                    }
                } else {
                    mAlphaPw.dismiss();
                    openPhoto(1);
                }
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
                            Intent intent = getAppDetailSettingIntent(ShopInfomationActivity.this);
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
                            Intent intent = getAppDetailSettingIntent(ShopInfomationActivity.this);
                            startActivity(intent);
                        }
                    });
                } else {
                    //权限被允许
                    mAlphaPw.dismiss();
                    openCamera(ShopInfomationActivity.this, 2);
                }
                break;
        }
    }

}
