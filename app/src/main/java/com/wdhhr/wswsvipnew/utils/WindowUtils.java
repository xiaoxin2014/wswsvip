package com.wdhhr.wswsvipnew.utils;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.wdhhr.wswsvipnew.R;


/**
 * Created by Administrator on 2016/7/8.
 */
public class WindowUtils {


    // 设置加载数据弹出框(不可取消)
    public static PopupWindow getLoadPopopWindow(final Activity activity) {
        // 加载布局视图
        View pwView = LayoutInflater.from(activity).inflate(R.layout.pw_load, null, false);
        ((TextView) pwView.findViewById(R.id.tv_pw_title)).setText(R.string.on_load);
        // 创建PopupWindow，参数4 false为不获取焦点
        PopupWindow popupWindow = new PopupWindow(pwView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,
                true);

        // 是否可以取消
        popupWindow.setOutsideTouchable(false);
        // 设置弹出动画
        popupWindow.setAnimationStyle(android.support.v7.appcompat.R.anim.abc_popup_enter);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setWindowAlpha(activity, 1);
            }
        });

        return popupWindow;
    }

    // 菜单设置弹出框
    public static PopupWindow getAlphaPwSmall(final Activity activity, int id) {

        // 创建PopupWindow，参数4 false为不获取焦点
        View pwView = LayoutInflater.from(activity).inflate(id, null, false);
        PopupWindow popupWindow = new PopupWindow(pwView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,
                true);

        popupWindow.setTouchable(true);
        // 设置背景颜色
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //  弹出窗监听
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setWindowAlpha(activity, 1);
            }
        });

        return popupWindow;
    }

    // 菜单设置弹出框
    public static PopupWindow getAlphaPw(final Activity activity, int id) {

        // 创建PopupWindow，参数4 false为不获取焦点
        View pwView = LayoutInflater.from(activity).inflate(id, null, false);
        PopupWindow popupWindow = new PopupWindow(pwView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,
                true);

        popupWindow.setTouchable(true);
        // 设置背景颜色
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setAnimationStyle(R.style.pw_slide);

        //  弹出窗监听
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setWindowAlpha(activity, 1);
            }
        });

        return popupWindow;
    }

    public static void closePW(PopupWindow pw) {
        if (pw != null && pw.isShowing()) {
            pw.dismiss();
        }
    }

    public static void setWindowAlpha(Activity context, float alpha) {
        WindowManager.LayoutParams params = context.getWindow().getAttributes();
        params.alpha = alpha;
        context.getWindow().setAttributes(params);
    }


    //倒计时
    public static void codeWait(final TextView tvGetCode, int miCnt) {
        tvGetCode.setEnabled(false);
        new CountDownTimer(miCnt * 1000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                tvGetCode.setText(millisUntilFinished / 1000 + "s");
            }

            @Override
            public void onFinish() {
                tvGetCode.setEnabled(true);
                tvGetCode.setText(R.string.get_code);
            }
        }.start();
    }

}
