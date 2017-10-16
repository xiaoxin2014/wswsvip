package com.wdhhr.wswsvipnew.wxapi;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.wdhhr.wswsvipnew.R;
import com.wdhhr.wswsvipnew.base.BaseViewInterface;

/**
 * Created by felear on 2017/8/26 0026.
 */

public class PayPopupWindow extends PopupWindow {

    private String mstrOrderNo;
    private TextView mTvAmount;
    private BaseViewInterface mBaseView;
    private final RadioGroup mRg_pay_way;

    public PayPopupWindow(BaseViewInterface baseViewInterface) {
        super(LayoutInflater.from(baseViewInterface.getBaseActivity()).inflate(R.layout.pw_sel_pay, null, false)
                , ViewGroup.LayoutParams.MATCH_PARENT
                , ViewGroup.LayoutParams.WRAP_CONTENT,
                true);

        mBaseView = baseViewInterface;
        setTouchable(true);
        // 设置背景颜色
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setAnimationStyle(R.style.pw_slide);

        //  弹出窗监听
        setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams params = mBaseView.getBaseActivity().getWindow().getAttributes();
                params.alpha = 1;
                mBaseView.getBaseActivity().getWindow().setAttributes(params);
            }
        });

        mTvAmount = (TextView) getContentView().findViewById(R.id.tv_pw_amount);
        mRg_pay_way = (RadioGroup) getContentView().findViewById(R.id.rg_pay_way);

        getContentView().findViewById(R.id.tv_pw_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShowing()) {
                    dismiss();
                }
            }
        });

        //确认付款
        getContentView().findViewById(R.id.tv_pay_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBaseView.showLoadPw();
                if (mRg_pay_way.getCheckedRadioButtonId() == R.id.rb_ali) {
                    PayDao.aliPay(mstrOrderNo, mBaseView);
                } else if (mRg_pay_way.getCheckedRadioButtonId() == R.id.rb_wechat) {
                    PayDao.weChatPay(mstrOrderNo, mBaseView);
                }

                if (isShowing()) {
                    dismiss();
                }
            }
        });
    }

    public void setAmount(String amount) {
        mTvAmount.setText(amount);
    }

    public void setOrderNo(String orderNo) {
        mstrOrderNo = orderNo;
    }
}
