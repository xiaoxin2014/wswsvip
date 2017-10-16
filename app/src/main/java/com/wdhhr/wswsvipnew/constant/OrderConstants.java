package com.wdhhr.wswsvipnew.constant;

/**
 * Created by felear on 2017/8/25 0025.
 */

public class OrderConstants {

    // 订单状态
    public static final String KEY_MODE_ORDER = "key_mode_order";
    // 是否客户订单
    public static final String KEY_MODE_ORDER_BUSINESS = "key_mode_order_business";
    // 是否售后订单
    public static final String KEY_MODE_ORDER_SERVICE = "key_mode_order_service";

    // 全部订单
    public static final int MODE_ORDER_ALL = -1;
    // 退款中
    public static final int MODE_ORDER_REFUNDING = 0;
    // 退款成功
    public static final int MODE_ORDER_REFUND_SUCCESSFUL = 1;
    // 退款失败
    public static final int MODE_ORDER_REFUND_FAIL = 2;
    // 待支付
    public static final int MODE_ORDER_PENDING_PAY = 3;
    // 已发货
    public static final int MODE_ORDER_SHIPPED = 4;
    // 确认收货
    public static final int MODE_ORDER_FINISH = 5;
    // 待发货
    public static final int MODE_ORDER_PENDING_SHIPMENT = 6;
    // 取消订单
    public static final int MODE_ORDER_CANCEL = 7;
    // 同意退货
    public static final int MODE_ORDER_REFUND_CONFIRM = 8;

}
