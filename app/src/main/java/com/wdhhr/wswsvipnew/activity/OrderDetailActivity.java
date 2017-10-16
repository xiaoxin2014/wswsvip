package com.wdhhr.wswsvipnew.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wdhhr.wswsvipnew.R;
import com.wdhhr.wswsvipnew.base.BaseActivity;
import com.wdhhr.wswsvipnew.constant.EventConstants;
import com.wdhhr.wswsvipnew.constant.OrderConstants;
import com.wdhhr.wswsvipnew.dao.OrderDao;
import com.wdhhr.wswsvipnew.model.cache.PayResultBean;
import com.wdhhr.wswsvipnew.model.dataBase.ExpressTracesBean;
import com.wdhhr.wswsvipnew.model.dataBase.OrdersListBean;
import com.wdhhr.wswsvipnew.utils.ImageUtils;
import com.wdhhr.wswsvipnew.utils.LocalUtils;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class OrderDetailActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView mTvTitle;
    @BindView(R.id.tv_back)
    TextView mTvBack;
    @BindView(R.id.tv_order_status)
    TextView mTvOrderStatus;
    @BindView(R.id.tv_name_tel)
    TextView mTvNameTel;
    @BindView(R.id.tv_location)
    TextView mTvLocation;
    @BindView(R.id.tv_remarks)
    TextView mTvRemarks;
    @BindView(R.id.tv_goods_amount)
    TextView mTvGoodsAmount;
    @BindView(R.id.tv_micro_deduction)
    TextView mTvMicroDeduction;
    @BindView(R.id.tv_freight)
    TextView mTvFreight;
    @BindView(R.id.tv_amount)
    TextView mTvAmount;
    @BindView(R.id.tv_bottom_left)
    TextView mTvBottomLeft;
    @BindView(R.id.tv_bottom_center)
    TextView mTvBottomCenter;
    @BindView(R.id.tv_bottom_right)
    TextView mTvBottomRight;
    @BindView(R.id.tv_bottom_orange)
    TextView mTvBottomOrange;
    @BindView(R.id.tv_bottom_black)
    TextView mTvBottomBlack;
    @BindView(R.id.layout_goods_detail)
    LinearLayout mLayoutGoodsDetail;
    @BindView(R.id.layout_logistics)
    LinearLayout mLayoutLogistics;
    @BindView(R.id.tv_logistics_detail)
    TextView mTvLogisticsDetail;
    @BindView(R.id.tv_logistics_time)
    TextView mTvLogisticsTime;
    @BindView(R.id.iv_logistics_arrow)
    ImageView mIvLogisticsArrow;
    @BindView(R.id.layout_bottom)
    LinearLayout layoutBottom;
    @BindView(R.id.tv_insert_name)
    TextView mTvInsertName;
    @BindView(R.id.tv_insert_amount)
    TextView mTvInsertAmount;
    @BindView(R.id.layout_insert)
    LinearLayout mLayoutInsert;
    @BindView(R.id.tv_order_no)
    TextView mTvOrderNo;
    @BindView(R.id.tv_create_time)
    TextView mTvCreateTime;
    @BindView(R.id.tv_pay_time)
    TextView mTvPayTime;
    @BindView(R.id.layout_pay_time)
    LinearLayout mLayoutPayTime;
    private OrdersListBean mOrderBean;
    private ArrayList<TextView> textViews;

    private static final String TAG = "OrderDetailActivity";
    private boolean mIsBusiness;
    private List<OrdersListBean.OrdersDetailListBean> mOrdersDetailList;
    private boolean mIsHideBottom;

    @Override
    protected int setViewId() {
        return R.layout.activity_order_detail;
    }

    @Override
    protected void init() {
        String json = getIntent().getExtras().getString("json");
        mOrderBean = new Gson().fromJson(json, OrdersListBean.class);
        mTvTitle.setText(R.string.order_detail);
        mTvBack.setVisibility(View.VISIBLE);

        textViews = new ArrayList<>();
        textViews.add(mTvBottomLeft);
        textViews.add(mTvBottomCenter);
        textViews.add(mTvBottomRight);
        textViews.add(mTvOrderStatus);
        textViews.add(mTvBottomOrange);
        textViews.add(mTvBottomBlack);
        textViews.add(mTvInsertName);
        textViews.add(mTvInsertAmount);


        mOrdersDetailList = mOrderBean.getOrdersDetailList();

        if (mOrdersDetailList != null && mOrdersDetailList.size() > 0) {
            for (int i = 0; i < mOrdersDetailList.size(); i++) {

                OrdersListBean.OrdersDetailListBean ordersDetailListBean = mOrdersDetailList.get(i);

//                num += ordersDetailListBean.getBuyNum();
                List<String> specList = ordersDetailListBean.getGoodsSpec().getSpecList();
                String specName = getStr(R.string.spec) + ":" + specList.get(0);
                if (specList.size() > 0) {
                    for (int j = 1; j < specList.size(); j++) {
                        specName += "+" + specList.get(j);
                    }
                }

                View view = LayoutInflater.from(this).inflate(R.layout.item_order_inner, mLayoutGoodsDetail, false);
                ((TextView) view.findViewById(R.id.tv_title)).setText(ordersDetailListBean.getGoods().getGoodsName());
                ((TextView) view.findViewById(R.id.tv_price_num)).setText(String.format(getStr(R.string.order_price_num)
                        , ordersDetailListBean.getGoodsSpec().getGoodsDetailPrice(), ordersDetailListBean.getBuyNum()));
                ((TextView) view.findViewById(R.id.tv_sub_title)).setText(specName);

                String[] split = ordersDetailListBean.getGoodsSpec().getGoodsDetailPic().split(",");
                if (split.length > 0) {
                    ImageView imageView = (ImageView) view.findViewById(R.id.iv_goods_pic);
                    ImageUtils.loadImageUrl(imageView, split[0], this);
                }
                final int position = i;
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Bundle bundle = new Bundle();
                        Gson gson = new Gson();
                        String bean = gson.toJson(mOrdersDetailList.get(position).getGoods());
                        bundle.putString("json", bean);
                        readyGo(GoodDetailsActivity.class, bundle);
                    }
                });
                mLayoutGoodsDetail.addView(view);
            }
        }
    }

    @Subscriber(tag = EventConstants.PAY_RESULT)
    private void payFresh(PayResultBean payResultBean) {
        if (payResultBean.getResult() == 10000) {
            mOrderBean.setOrderProcedure(OrderConstants.MODE_ORDER_PENDING_SHIPMENT);
            loadData();
        }else {
            showLongToast(payResultBean.getMsg());
        }
    }

    @Subscriber(tag = EventConstants.ORDER_STATUS_CHANGE)
    private void orderDelete(OrdersListBean ordersListBean) {
        if (mOrderBean.equals(ordersListBean)) {
            if (ordersListBean.getId() == -1) {
                finish();
            } else {
                mOrderBean = ordersListBean;
                loadData();
            }
        }
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void loadData() {
        mIsBusiness = getIntent().getExtras().getBoolean(OrderConstants.KEY_MODE_ORDER_BUSINESS, false);

        // 订单信息
        if (mIsBusiness) {
            mIsHideBottom = OrderDao.setCustomOrderStatus(textViews, mOrderBean, this, mLayoutInsert);
        } else {
            OrderDao.setOrderStatus(textViews, mOrderBean, this);
        }

        if (mIsHideBottom || mOrderBean.getOrderProcedure() == 5 || mOrderBean.getOrderProcedure() == 6) {
            layoutBottom.setVisibility(View.GONE);
        } else {
            layoutBottom.setVisibility(View.VISIBLE);
        }

        // 收货地址
        mTvNameTel.setText(mOrderBean.getContactName() + " " + mOrderBean.getContactPhone());
        mTvLocation.setText(LocalUtils.getLocalName(mOrderBean.getProvice()
                , mOrderBean.getCity()
                , mOrderBean.getArea(), "")
                + mOrderBean.getAddress());

        // 物流信息
        if (mOrderBean.getOrderProcedure() == OrderConstants.MODE_ORDER_PENDING_SHIPMENT) {
            mLayoutLogistics.setVisibility(View.VISIBLE);
            mTvLogisticsTime.setVisibility(View.GONE);
            mIvLogisticsArrow.setVisibility(View.GONE);
            mTvLogisticsDetail.setText(R.string.logistics_detail_none);
        } else if (mOrderBean.getOrderProcedure() == OrderConstants.MODE_ORDER_PENDING_PAY) {
            mLayoutLogistics.setVisibility(View.GONE);
        } else {
            mLayoutLogistics.setVisibility(View.VISIBLE);
            mTvLogisticsTime.setVisibility(View.VISIBLE);
            mIvLogisticsArrow.setVisibility(View.VISIBLE);

            String expressTraces = mOrderBean.getExpressTraces();
            boolean isEmpty = false;
            if (expressTraces != null && !TextUtils.equals(expressTraces, "")) {
                ExpressTracesBean expressTracesBean = new Gson().fromJson(expressTraces, ExpressTracesBean.class);
                List<ExpressTracesBean.TracesBean> traces = expressTracesBean.getTraces();
                if (traces == null || traces.size() == 0) {
                    // 暂无物流信息
                    isEmpty = true;
                } else {
                    ExpressTracesBean.TracesBean tracesBean = traces.get(traces.size() - 1);
                    if (mOrderBean.getOrderProcedure() == OrderConstants.MODE_ORDER_FINISH) {
                        mTvLogisticsDetail.setText("快件已签收\r\n" + tracesBean.getAcceptStation());
                    } else {
                        mTvLogisticsDetail.setText(tracesBean.getAcceptStation());
                    }
                    mTvLogisticsTime.setText(tracesBean.getAcceptTime());
                }
            } else {
                // 暂无物流信息
                isEmpty = true;
            }

            if (isEmpty) {
                mTvLogisticsDetail.setText(R.string.logistics_detail_empty);
                mTvLogisticsTime.setVisibility(View.GONE);
            }
        }

        // 备注
        mTvRemarks.setText(mOrderBean.getDesc());
        // 金额相关
        mTvGoodsAmount.setText(getStr(R.string.money_unit) + mOrderBean.getOrderPrice());
        // 微币抵扣
        mTvMicroDeduction.setText(getStr(R.string.money_unit) + mOrderBean.getWMoney());
        // 运费
        mTvFreight.setText(getStr(R.string.money_unit) + "0.0");
        // 总金额
        mTvAmount.setText(getStr(R.string.money_unit) + mOrderBean.getOrderPrice());

        // 订单号
        mTvOrderNo.setText(mOrderBean.getOrdersId());

        // 创建时间
        mTvCreateTime.setText(mOrderBean.getCtime());

        // 付款时间
        if (mOrderBean.getOrderProcedure() == OrderConstants.MODE_ORDER_PENDING_PAY) {
            mLayoutPayTime.setVisibility(View.GONE);
        } else {
            mLayoutPayTime.setVisibility(View.VISIBLE);
            mTvPayTime.setText(mOrderBean.getPaymentTime());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.layout_logistics:
                // 查看物流详情
                if (mOrderBean.getOrderProcedure() == OrderConstants.MODE_ORDER_PENDING_SHIPMENT) {
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("json", new Gson().toJson(mOrderBean));
                readyGo(LogisticsDetailActivity.class, bundle);
                break;
        }
    }
}
