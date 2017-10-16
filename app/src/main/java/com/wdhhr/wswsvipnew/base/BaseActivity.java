package com.wdhhr.wswsvipnew.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.socialize.shareboard.ShareBoardConfig;
import com.wdhhr.wswsvipnew.R;
import com.wdhhr.wswsvipnew.utils.FileUtil;
import com.wdhhr.wswsvipnew.utils.WindowUtils;

import org.simple.eventbus.EventBus;

import java.io.File;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/6/27.
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseViewInterface {

    private long mend;
    private long mstart;
    protected boolean mcanBack = true;
    private PopupWindow mPwLoad;
    private PopupWindow mPwTips;
    private boolean isShowLoad;
    private long mPressTime;
    private Activity mContext;

    @Override
    protected void onResume() {
        super.onResume();
        mstart = 0;
    }

    @Override
    public void onBackPressed() {

        if (mcanBack) {
            finish();
        } else {
            mend = System.currentTimeMillis();
            if (mend - mstart < 2000) {
                finish();
            } else {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mstart = mend;
            }
        }

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setViewId());
        mContext =getBaseActivity();
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        init();
        initEvent();
        loadData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        WindowUtils.closePW(mPwLoad);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (isShowLoad) {
            isShowLoad = false;
            showLoadPw();
        }
    }

    /**
     * 弹出加载弹出窗
     */
    @Override
    public void showLoadPw() {

        if (!hasWindowFocus()) {
            isShowLoad = true;
            return;
        }

        if (mPwLoad == null) {
            mPwLoad = WindowUtils.getLoadPopopWindow(this);
        }

        if (!mPwLoad.isShowing()) {
            mPwLoad.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
        }

    }

    @Override
    public void dismissLoadPw() {
        isShowLoad = false;
        WindowUtils.closePW(mPwLoad);
    }

    /**
     * 弹出是否确定选择弹出窗
     */
    @Override
    public void showSelTipsPw(int strId, final OnSelTipsPwSureListener onSelTipsPwSureListener) {
        if (mPwTips == null) {
            mPwTips = WindowUtils.getAlphaPwSmall(this, R.layout.pw_tips);
            // 取消选项
            mPwTips.getContentView().findViewById(R.id.tv_pw_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WindowUtils.closePW(mPwTips);
                }
            });

        }
        mPwTips.getContentView().findViewById(R.id.tv_pw_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WindowUtils.closePW(mPwTips);
                if (onSelTipsPwSureListener != null) {
                    onSelTipsPwSureListener.onSure();
                }
            }
        });
        ((TextView) mPwTips.getContentView().findViewById(R.id.tv_pw_content)).setText(strId);
        WindowUtils.setWindowAlpha(this, 0.6f);
        mPwTips.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
    }

    /**
     * 加载布局
     */
    protected abstract int setViewId();

    /**
     * 数据初始化
     */
    protected abstract void init();

    /**
     * 初始化各种事件监听
     */
    protected abstract void initEvent();

    /**
     * 加载数据
     */
    protected abstract void loadData();

    /**
     * 按键监听事件
     */
    public abstract void onClick(View view);

    // 弹出Toast方法
    @Override
    public void showLongToast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_LONG).show();
    }

    // 弹出Toast方法
    @Override
    public void showLongToast(int strId) {
        Toast.makeText(this, getString(strId), Toast.LENGTH_LONG).show();
    }

    // 弹出Toast方法
    @Override
    public void showShortToast(int strId) {
        Toast.makeText(this, getString(strId), Toast.LENGTH_SHORT).show();
    }

    // 弹出Toast方法
    @Override
    public void showShortToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    /**
     * 获取指定ID字符串
     *
     * @param id 资源id,如R.string.app_name
     */
    public String getStr(int id) {
        return getResources().getString(id);
    }

    /**
     * 获取指定ID颜色
     *
     * @param id 资源id,如R.color.white
     */
    public int getCol(int id) {
        return getResources().getColor(id);
    }


    /**
     * 获取指定ID字符串
     *
     * @param id 资源id,如R.string.app_name
     */
    public String getStrFormat(int id, int num) {
        return String.format(getStr(id), num);
    }

    public String getStrFormat(int id, String str) {
        return String.format(getStr(id), str);
    }

    /**
     * startActivity with bundle
     */
    @Override
    public void readyGo(Class<?> clazz, Bundle bundle) {
        if (clazz != null) {
            Intent intent = new Intent(this, clazz);
            if (null != bundle) {
                intent.putExtras(bundle);
            }
            startActivity(intent);
        }
    }

    /**
     * startActivity
     */
    @Override
    public void readyGo(Class<?> clazz) {
        readyGo(clazz, null);
    }

    /**
     * startActivity with bundle and finish
     */
    public void readyGoThenKill(Class<?> clazz, Bundle bundle) {
        if (clazz != null) {
            Intent intent = new Intent(this, clazz);
            if (null != bundle) {
                intent.putExtras(bundle);
            }
            startActivity(intent);
            finish();
        }
    }

    /**
     * startActivity and finish
     */
    public void readyGoThenKill(Class<?> clazz) {
        readyGoThenKill(clazz, null);
    }

    //设置分享面板样式
    public ShareBoardConfig getShareBoardConfig() {
        ShareBoardConfig config = new ShareBoardConfig();
        config.setTitleText(getResources()
                .getString(R.string.share)).setTitleTextColor(getResources().getColor(R.color.fontTitle))
                .setMenuItemBackgroundColor(Color.parseColor("#FFFFFFFF"))
                .setIndicatorVisibility(false)
                .setCancelButtonBackground(getResources().getColor(R.color.background))
                .setCancelButtonText(getResources().getString(R.string.cancle))
                .setCancelButtonTextColor(getResources().getColor(R.color.fontSoftText))
                .setShareboardBackgroundColor(Color.parseColor("#FFFFFFFF"));
        return config;
    }


    /**
     * 获取应用详情页面intent
     *
     * @return
     */
    public Intent getAppDetailSettingIntent(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        return localIntent;
    }

    /**
     * 打开相机
     * 兼容7.0
     *
     * @param activity    Activity
     * @param requestCode result requestCode
     */
    public void openCamera(Activity activity, int requestCode) {
        if (activity == null) {
            return;
        }
        //这个可以作为参数，自定义
        File file = new File(FileUtil.getAppPath("user", FileUtil.CAMERA));
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, FileUtil.getUriForFile(activity, file));
        startActivityForResult(intent, requestCode);
    }


    //打开图库
    public void openPhoto(int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, 1);
    }

    @Override
    public Activity getBaseActivity() {
        return this;
    }

    //点击非EditText区域隐藏软键盘
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPressTime = System.currentTimeMillis();
                break;
            case MotionEvent.ACTION_UP:
                if (System.currentTimeMillis() - mPressTime < 300) {
                    View v = getCurrentFocus();
                    if (isShouldHideInput(v, ev)) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        if (imm != null) {
                            assert v != null;
                            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        }
                    }
                }
                break;
        }

        return super.dispatchTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }


}
