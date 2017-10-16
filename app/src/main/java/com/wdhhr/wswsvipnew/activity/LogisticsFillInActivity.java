package com.wdhhr.wswsvipnew.activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wdhhr.wswsvipnew.R;
import com.wdhhr.wswsvipnew.base.BaseActivity;
import com.wdhhr.wswsvipnew.constant.EventConstants;
import com.wdhhr.wswsvipnew.constant.OrderConstants;
import com.wdhhr.wswsvipnew.dao.OrderDao;
import com.wdhhr.wswsvipnew.model.OrderCommonBean;
import com.wdhhr.wswsvipnew.model.dataBase.ExpressBean;
import com.wdhhr.wswsvipnew.model.dataBase.OrdersListBean;
import com.wdhhr.wswsvipnew.net.ApiSubscriberCallBack;
import com.wdhhr.wswsvipnew.utils.DeviceUtils;
import com.wdhhr.wswsvipnew.utils.NetworkUtils;
import com.wdhhr.wswsvipnew.utils.WindowUtils;
import com.wdhhr.wswsvipnew.widget.wheelview.StringWheelAdapter;
import com.wdhhr.wswsvipnew.widget.wheelview.WheelView;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

public class LogisticsFillInActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView mTvTitle;
    @BindView(R.id.tv_back)
    TextView mTvBack;
    @BindView(R.id.tv_right)
    TextView mTvRight;
    @BindView(R.id.tv_logistics_time)
    TextView mTvLogisticsTime;
    @BindView(R.id.tv_logistics_detail)
    TextView mTvLogisticsDetail;
    @BindView(R.id.tv_logistics_select)
    TextView mTvLogisticsName;
    @BindView(R.id.edit_logistics_num)
    EditText mEditLogisticsNum;
    private OrdersListBean mOrdersListBean;
    private PopupWindow mPwNameSel;
    private WheelView mWvName;
    private List<ExpressBean> mExpressList;
    private int miExpressPosition = -1;
    private String mstrNo;

    private static final String TAG = "LogisticsFillInActivity";

    @Override
    protected int setViewId() {
        return R.layout.activity_logistics_fill_in;
    }

    @Override
    protected void init() {
        mTvBack.setVisibility(View.VISIBLE);
        mTvTitle.setText(R.string.logistics_info);
        mTvRight.setText(R.string.submit);

        String json = getIntent().getExtras().getString("json");
        Gson gson = new Gson();
        mOrdersListBean = gson.fromJson(json, OrdersListBean.class);

//        mTvLogisticsDetail.setText(mOrdersListBean.getSendName() + " " + mOrdersListBean.getSendPhone()
//                + "\n" + LocalUtils.getLocalName(mOrdersListBean.getSendProvice(),
//                mOrdersListBean.getSendCity(),
//                mOrdersListBean.getSendArea(), "")
//                + mOrdersListBean.getSendAddress());

        mPwNameSel = WindowUtils.getAlphaPw(this, R.layout.pw_logistics_name_sel);
        mWvName = (WheelView) mPwNameSel.getContentView().findViewById(R.id.wv_pw_logistics);
        mWvName.TEXT_SIZE = DeviceUtils.dip2px(15);

    }

    @Override
    protected void initEvent() {
        mEditLogisticsNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (s.length() > 0 && s.toString().trim().length() > 5) {
                    mstrNo = s.toString().trim();
                } else {
                    mstrNo = null;
                }
                setSubmitStatus();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setSubmitStatus() {
        if (miExpressPosition >= 0 && mstrNo != null) {
            mTvRight.setEnabled(true);
        } else {
            mTvRight.setEnabled(false);
        }
    }

    @Override
    protected void loadData() {
        if (mOrdersListBean.getOrderProcedure() == OrderConstants.MODE_ORDER_REFUNDING) {
            mEditLogisticsNum.setText(mOrdersListBean.getSendExpressId());
            mTvLogisticsName.setText(mOrdersListBean.getSendExpressName());
            mTvLogisticsName.setEnabled(false);
            mTvLogisticsName.setCompoundDrawables(null, null, null, null);
            mEditLogisticsNum.setEnabled(false);
            mTvLogisticsName.setTextColor(getResources().getColor(R.color.fontTitle));
            mTvRight.setVisibility(View.GONE);
            mTvLogisticsTime.setVisibility(View.GONE);
        } else {
            OrderDao.getExpressList();
            mTvRight.setVisibility(View.VISIBLE);
            mEditLogisticsNum.setEnabled(true);
            mTvRight.setEnabled(false);
        }
    }

    @Subscriber(tag = EventConstants.EXPRESS_LOAD_COMPLETE)
    private void loadExpress(List<ExpressBean> expressList) {
        // 设置快递名称数据
        String[] strings = new String[expressList.size()];
        for (int i = 0; i < expressList.size(); i++) {
            strings[i] = expressList.get(i).getExpressName();
        }
        mWvName.setAdapter(new StringWheelAdapter(0, strings.length - 1, strings));

        mExpressList = expressList;
        Log.d(TAG, "hahahahhaha " + strings.length);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_right:
                // 提交
                ExpressBean expressBean = mExpressList.get(miExpressPosition);

                mOrdersListBean.setOrderProcedure(OrderConstants.MODE_ORDER_REFUNDING);
                mOrdersListBean.setSendExpressName(expressBean.getExpressName());
                mOrdersListBean.setSendExpressId(mstrNo);
                mOrdersListBean.setSendExpressJP(expressBean.getSortName());

                HashMap<String, String> map = new HashMap<>();
                map.put("ordersId", mOrdersListBean.getOrdersId());
                map.put("sendExpressJP", expressBean.getSortName());
                map.put("sendExpressName", expressBean.getExpressName());
                map.put("sendExpressId", mstrNo);
                map.put("orderProcedure", OrderConstants.MODE_ORDER_REFUNDING + "");
                OrderDao.updateOrder(map).subscribe(new ApiSubscriberCallBack<OrderCommonBean>() {
                    @Override
                    public void onSuccess(OrderCommonBean orderCommonBean) {
                        showLongToast("退货物流信息填写成功");
                        EventBus.getDefault().post(mOrdersListBean, EventConstants.ORDER_STATUS_CHANGE);
                        finish();
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        if (NetworkUtils.isOnline()) {
                            showLongToast(R.string.service_error);
                        } else {
                            showLongToast(R.string.net_connect_error);
                        }
                    }
                });
                break;
            case R.id.tv_logistics_select:
                // 选择快递公司
                WindowUtils.setWindowAlpha(this, 0.6f);
                mPwNameSel.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
                break;
            case R.id.tv_pw_cancel:
                WindowUtils.closePW(mPwNameSel);
                break;
            case R.id.tv_pw_confirm:
                // 确认快递
                miExpressPosition = mWvName.getCurrentItem();
                mTvLogisticsName.setText(mExpressList.get(miExpressPosition).getExpressName());
                mTvLogisticsName.setTextColor(getResources().getColor(R.color.fontTitle));
                WindowUtils.closePW(mPwNameSel);
                setSubmitStatus();
                break;
        }
    }

}
