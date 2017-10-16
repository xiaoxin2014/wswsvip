package com.wdhhr.wswsvipnew.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wdhhr.wswsvipnew.R;
import com.wdhhr.wswsvipnew.base.BaseActivity;
import com.wdhhr.wswsvipnew.constant.EventConstants;
import com.wdhhr.wswsvipnew.dao.GoodsDao;
import com.wdhhr.wswsvipnew.model.dataBase.GoodsListBean;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.List;

import butterknife.BindView;

public class TradeSuccessfulActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_back)
    TextView tvBack;
    @BindView(R.id.layout_promotion)
    LinearLayout mLayoutPromotion;
    private String mOutTradeNo;
    private List<GoodsListBean> mGoodsList;

    @Override
    protected int setViewId() {
        return R.layout.activity_trade_successful;
    }

    @Override
    protected void init() {
        tvBack.setVisibility(View.VISIBLE);
        title.setText(R.string.trade_successful);

        mGoodsList = GoodsDao.setGuessHobby(this, null, mLayoutPromotion);
    }

    @Override
    protected void initEvent() {

    }

    //店铺管理取消收藏时刷新首页热销单品
    @Subscriber(tag = EventConstants.GOODS_INFO_CHANGE)
    private void UpdateBusinessIcon(GoodsListBean goodsListBean) {

        if (mGoodsList.contains(goodsListBean)) {
            mGoodsList = GoodsDao.setGuessHobby(this, null, mLayoutPromotion);
        }
    }

    @Override
    protected void loadData() {
        mOutTradeNo = getIntent().getExtras().getString("outTradeNo");
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_order_view:
                // 查看订单
//                showLoadPw();
//                HashMap<String, String> map = new HashMap<>();
//                map.put("outTradeNo", mOutTradeNo);
//                map.put("page", "1");
//                OrderDao.getOrderById(map).subscribe(new ApiSubscriberCallBack<OrderCommonBean>() {
//                    @Override
//                    public void onSuccess(OrderCommonBean orderCommonBean) {
//                        dismissLoadPw();
//                        List<OrdersListBean> ordersList = orderCommonBean.getData().getOrdersList();
//                        if (ordersList != null && ordersList.size() > 0) {
//                            OrdersListBean ordersListBean = ordersList.get(0);
//                            ordersListBean.setOrderProcedure(OrderConstants.MODE_ORDER_PENDING_SHIPMENT);
//                            String json = new Gson().toJson(ordersListBean, OrdersListBean.class);
//                            Bundle bundle = new Bundle();
//                            bundle.putString("json", json);
//                            readyGo(OrderDetailActivity.class, bundle);
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Throwable t) {
//                        dismissLoadPw();
//                    }
//                });
                EventBus.getDefault().post(3,EventConstants.TRAD_SUCCESSFUL_CLICK);
                Bundle bundle = new Bundle();
                bundle.putInt("position", 2);
                readyGoThenKill(MyOrderListActivity.class, bundle);
                break;
        }

    }

}
