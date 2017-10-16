package com.wdhhr.wswsvipnew.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.wdhhr.wswsvipnew.R;
import com.wdhhr.wswsvipnew.utils.WindowUtils;

import org.simple.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2016/6/27.
 */
public abstract class BaseFragment extends Fragment implements BaseViewInterface {

    private Unbinder unbinder;
    private PopupWindow mPwLoad;
    private PopupWindow mPwLogOut;
    private boolean isShowLoad;
    protected View mView;
    protected LayoutInflater mInflater;
    protected long mTimeFreshData;
    protected boolean isViewInitiated;
    protected boolean isVisibleToUser;
    protected boolean isDataInitiated;
    private Activity mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getBaseActivity();
        mInflater = inflater;
        mView = inflater.inflate(setViewId(), container, false);
        // 弹出窗
        mPwLoad = WindowUtils.getLoadPopopWindow(getActivity());
        mPwLogOut = WindowUtils.getAlphaPwSmall(getActivity(), R.layout.pw_tips);
        // 取消选项
        mPwLogOut.getContentView().findViewById(R.id.tv_pw_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WindowUtils.closePW(mPwLogOut);
            }
        });

        unbinder = ButterKnife.bind(this, mView);
        EventBus.getDefault().register(this);
        init();
        initEvent();
        loadData();
        return mView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewInitiated = true;
        prepareFetchData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        prepareFetchData();
    }


    public boolean prepareFetchData() {
        return prepareFetchData(false);
    }

    public boolean prepareFetchData(boolean forceUpdate) {
        if (isVisibleToUser && isViewInitiated && (!isDataInitiated || forceUpdate)) {
            freshData();
            isDataInitiated = true;
            return true;
        }
        return false;
    }


    public void freshData() {

    }

    /**
     * 弹出加载弹出窗
     */
    @Override
    public void showLoadPw() {
        if (!isVisible()) {
            isShowLoad = true;
            return;
        }
        isShowLoad = false;
        if (!mPwLoad.isShowing()) {
            mPwLoad.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.CENTER, 0, 0);
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

        mPwLogOut.getContentView().findViewById(R.id.tv_pw_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WindowUtils.closePW(mPwLogOut);
                if (onSelTipsPwSureListener != null) {
                    onSelTipsPwSureListener.onSure();
                }
            }
        });
        ((TextView) mPwLogOut.getContentView().findViewById(R.id.tv_pw_content)).setText(strId);
        WindowUtils.setWindowAlpha(getActivity(), 0.6f);
        mPwLogOut.showAtLocation(getActivity().getWindow().getDecorView(), Gravity.CENTER, 0, 0);
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
    public void showShortToast(int strId) {
        Toast.makeText(getContext(), getString(strId), Toast.LENGTH_SHORT).show();
    }

    // 弹出Toast方法
    @Override
    public void showLongToast(int strId) {
        Toast.makeText(getContext(), getString(strId), Toast.LENGTH_LONG).show();
    }

    // 弹出Toast方法
    @Override
    public void showLongToast(String str) {
        Toast.makeText(getContext(), str, Toast.LENGTH_LONG).show();
    }

    // 弹出Toast方法
    @Override
    public void showShortToast(String str) {
        Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
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
    public String getStr(int id) {
        return getResources().getString(id);
    }

    /**
     * startActivity with bundle
     */
    @Override
    public void readyGo(Class<?> clazz, Bundle bundle) {
        if (clazz != null) {
            Intent intent = new Intent(getContext(), clazz);
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


    @Override
    public Activity getBaseActivity() {
        return getActivity();
    }

}
