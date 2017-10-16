package com.wdhhr.wswsvipnew.activity;

import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wdhhr.wswsvipnew.MyApplication;
import com.wdhhr.wswsvipnew.R;
import com.wdhhr.wswsvipnew.adapter.MyPagerAdapter;
import com.wdhhr.wswsvipnew.base.BaseActivity;
import com.wdhhr.wswsvipnew.constant.EventConstants;
import com.wdhhr.wswsvipnew.constant.HomeContants;
import com.wdhhr.wswsvipnew.constant.UserContants;
import com.wdhhr.wswsvipnew.dao.UserDao;
import com.wdhhr.wswsvipnew.utils.DeviceUtils;
import com.wdhhr.wswsvipnew.utils.SystemUtil;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;

import butterknife.BindView;

public class WelcomeActivity extends BaseActivity {

    @BindView(R.id.iv_startup)
    ImageView mIvStartup;
    @BindView(R.id.vp_guide)
    ViewPager mVpGuide;
    @BindView(R.id.layout_dot)
    LinearLayout mLayoutDot;

    private String mstrAccount;
    private String mstrPwd;
    private String TAG = WelcomeActivity.class.getSimpleName();
    private int miCurVersion;
    private boolean misFirstUse;
    private ArrayList<View> views;
    private MyPagerAdapter mAdapter;
    private float miMovePosition;
    private boolean isLastView;
    private long mStartTime;
    private Class mSkipClass = LoginActivity.class;

    @Override
    protected int setViewId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void init() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        mcanBack = false;

        mstrAccount = SystemUtil.getSharedString(UserContants.userAccount);
        mstrPwd = SystemUtil.getSharedString(UserContants.userPwd);
        if (mstrAccount != null && mstrPwd != null) {
            UserDao.login(mstrAccount, mstrPwd, this);
            mStartTime = System.currentTimeMillis();
        }

        miCurVersion = SystemUtil.getSystemVersionCode();
        int lastVersion = SystemUtil.getSharedInt(HomeContants.VERSION_STRING, -1);

        if (lastVersion == -1 || miCurVersion > lastVersion) {
            // 设置当前模式为第一次登录模式
            misFirstUse = true;
            views = new ArrayList<>();
            mAdapter = new MyPagerAdapter(views, null);
            mVpGuide.setAdapter(mAdapter);
        } else {
            mIvStartup.setVisibility(View.VISIBLE);
            mVpGuide.setVisibility(View.GONE);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    skip();
                }
            }, 1500);

        }

    }

    @Subscriber(tag = EventConstants.LOG_STATUS_CHANGE)
    private void loginResult(int num) {
        if (num == 0 && MyApplication.getUserInfo() != null) {
            mSkipClass = MainActivity.class;
        } else {
            mSkipClass = LoginActivity.class;
        }
    }

    private void skip() {
        readyGoThenKill(mSkipClass);
    }

    @Override
    protected void initEvent() {
        if (misFirstUse) {
            mVpGuide.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    for (int i = 0; i < views.size(); i++) {
                        ImageView dotIv = (ImageView) mLayoutDot.getChildAt(i);
                        if (i == position) {
                            dotIv.setImageResource(R.mipmap.indicator_check);
                        } else {
                            dotIv.setImageResource(R.mipmap.indicator);
                        }
                    }

                    isLastView = position == views.size() - 1;

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

            mVpGuide.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    if (isLastView) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                miMovePosition = event.getX();
                                break;
                        }

                        if (miMovePosition - event.getX() > 100) {
                            SystemUtil.setSharedInt(HomeContants.VERSION_STRING, miCurVersion);
                            skip();
                        }
                    }
                    return false;
                }
            });
        }
    }


    @Override
    protected void loadData() {

        // 判断是否为更新后第一次使用
        if (misFirstUse) {

            int[] ints = {R.mipmap.guide1, R.mipmap.guide2, R.mipmap.guide3};

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-2, -2);
            params.leftMargin = DeviceUtils.dip2px(5);
            params.rightMargin = DeviceUtils.dip2px(5);

            // 添加页面及小点
            for (int i = 0; i < ints.length; i++) {

                ImageView imageView = new ImageView(this);
                imageView.setImageResource(ints[i]);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                views.add(imageView);

                ImageView dotIv = new ImageView(this);
                if (i == 0) {
                    dotIv.setImageResource(R.mipmap.indicator_check);
                } else {
                    dotIv.setImageResource(R.mipmap.indicator);
                }
                dotIv.setLayoutParams(params);
                mLayoutDot.addView(dotIv);
            }

            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View view) {

    }
}
