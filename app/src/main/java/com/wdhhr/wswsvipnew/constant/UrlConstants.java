package com.wdhhr.wswsvipnew.constant;

/**
 * Created by felear on 2017/8/13 0013.
 */

public class UrlConstants {

    public static final String BASE_URl = "http://www.woshi53.com/WSCPRO/";
    public static final String IMG_BASE = "http://www.woshi53.com";


    /**
     * 用户相关
     */
    // User B
    public static final String LOGIN = "userLogin";
    public static final String REGISTER = "userCheck";
    public static final String FORGETPWD = "forgetPwd";
    public static final String PHONECODE = "e/phoneCode";
    public static final String VALIDATECODE = "e/validateCode";
    public static final String UPDATEUSER = "nlg/updateUser";
    // 版本更新
    public static final String GET_VERSION = "e/getChoice";
    // 清除Session
    public static final String CLEAR_SESSION = "clearSession";
    // 根据邀请码查询用户ID
    public static final String GET_USER_BY_CODE = "nlg/getUsersIdByInvicationCode";
    // User E

    /**
     * 选品
     */
    // home B
    public static final String GOODTYPES = "category/getIndexCategoryList";
    public static final String BRAND_TYPES = "brand/queryBrand";
    public static final String HOST_SHOP = "index";
    public static final String OTHER_SHOP = "goodsListQuery";
    public static final String ADD_GOOD_TO_BUSINESS = "nlg/addGoodsToBusiness";
    public static final String REMOVE_GOOD_TO_BUSINESS = "nlg/delGoodsOFBusiness";
    public static final String GET_GOODS_OF_BUSINESS = "getBusinessGoods";
    public static final String ADD_BRAND_TO_BUSINESS = "brand/nlg/addBrandToBusiness";
    public static final String REMOVE_BRAND_TO_BUSINESS = "brand/nlg/delBrandOFBusiness";
    public static final String GET_BRAND_OF_BUSINESS = "brand/getBusinessBrand";
    // home E

    // ShopSearch B
    public static final String SHOP_INDEX_LIST = "category/categoryList";
    public static final String HOT_SHOP_LIST = "sellWellGoods";
    // ShopSearch E

    // goodsDetail&brandDetail B
    public static final String GOOD_DETAIL = "selectGoodsSpecByGoodsId";
    public static final String BUSINESS_BRAND = "brand/getBusinessBrand";
    public static final String BRAND_GOODS_LIST = "selectGoodsByBrandId";
    // goodsDetail&brandDetail E

    /**
     * 微圈
     */
    // Circle B
    public static final String CONVERSATION_TYPES = "conversationType/conversationTypeList";
    public static final String GET_CONVERSATION_LIST = "conversation/conversationOfType";
    public static final String GET_COMMENT = "comment/queryComment";
    public static final String CONVERSATION_LIST = "conversation/selectConversationByConversationTypeId";
    public static final String COMMENT_LIST = "comment/getcommentListBycommentId";
    // 点赞
    public static final String UP_CONVERSATION = "conversation/nlg/upConversation";
    // 点赞评论
    public static final String UP_COMMENT = "comment/nlg/upComment";
    public static final String ADD_COMMENT = "comment/nlg/addComment";
    // Circle E

    /**
     * My 我的
     */
    // changeShopIcon
    public static final String UPDATEBUSINESS = "business/nlg/updateBusiness";
    // 直推人
    public static final String GETDIRECTPUSH = "nlg/getDirectPush";
    // 间推人
    public static final String BETWEENPUSH = "nlg/janePushedCALLBLE";
    // 获取收益详情
    public static final String GET_PROFIT_DETAIL = "profit/nlg/profitDetail";
    // 获取收益金额
    public static final String GET_PROFIT_AMOUNT = "profit/nlg/queryPofitCount";
    // 添加地址
    public static final String ADD_ADDRESS = "e/nlg/addAddress";
    // 删除地址
    public static final String DELETE_ADDRESS = "e/nlg/deleAddress";
    // 修改地址
    public static final String EDIT_ADDRESS = "e/nlg/updateAddress";
    // 获取收货地址
    public static final String GET_ADDRESS_LIST = "e/nlg/usersAddressList";
    // 获取购物车商品列表
    public static final String GET_SHOP_CAR_LIST = "nlg/queryShopCarList";
    // 添加商品至购物车
    public static final String ADD_TO_SHOP_CAR = "nlg/addShopCar";
    // 商品移出购物车
    public static final String REMOVE_SHOP_CAR = "nlg/delShopCar";
    // 更新购物车
    public static final String UPDATE_SHOP_CAR = "nlg/updateShopCar";
    // 获取购物车商品数量
    public static final String GET_SHOP_CAR_NUM = "nlg/getGoodsCount";
    // 创建订单
    public static final String CREATE_ORDER = "ordes/nlg/createOrders";
    // 提交订单（修改）
    public static final String SET_ORDER = "ordes/nlg/setOrders";
    // 条件查询订单列表
    public static final String GET_ORDER_LIST = "ordes/nlg/getOrdersList";
    // 通过ID和流水号查询订单
    public static final String GET_ORDER_LIST_BY_ID = "ordes/getOrdersListByGoods";
    // 删除订单
    public static final String DELETE_ORDER_LIST = "ordes/delOrderByOrdersId";
    // 修改订单状态
    public static final String GET_EXPRESSLIST = "express/getExpressList";
    // 获取快递列表
    public static final String UPDATE_ORDER_LIST = "ordes/updateOrderByOrdersId";
    // 上传图片
    public static final String UPLOAD_IMG = "e/uploadFileImg";
    // 支付订单
    public static final String PAY_ORDER = "pay/nlg/orderPay";
    // 查询支付结果
    public static final String PAY_STATUS_QUERY = "pay/nlg/queryOrderStatus";
    //店铺信息
    public static final String BUSINESSINFO = "business/selectBusinessByBusinessId";
    // 获取今日订单、今日访问、累计访问、累计销售额
    public static final String GET_COUNT_BY_BUSINESSID = "business/nlg/getCountByBusinessId";
    //修改店铺信息
    public static final String UPDATE_BUSINESSINFO = "business/nlg/updateBusiness";
    //置顶单品
    public static final String TOP_GOODS = "topList";
    //获取微币信息
    public static final String WEIBI = "nlg/queryGoldList";
    //微币分享成功详情
    public static final String WEIBI_SHARE_SUCESS = "nlg/queryGolddetails";
    //获取消息列表
    public static final String SYSTEM_MESSAGE = "e/nlg/messsageList";
    //更改消息状态
    public static final String UPDATE_MESSAGE_STATUS = "e/nlg/updateMessage";
    //获取粉丝信息(关系绑定)
    public static final String GET_FANS_INFO = "nlg/fancesList";
    // 查询是否已经绑定支付宝
    public static final String ALI_BAND_INFO = "cash/nlg/checkBankIsBand";
    // 绑定支付宝账号
    public static final String ALI_BAND = "cash/nlg/bandAlipayCard";
    // 查询可提现金额
    public static final String GET_CASH_AMOUNT = "cash/nlg/getCashAmount";
    // 提现记录
    public static final String GET_CASH_RECORD = "nlg/selectCashListByUsersId";
    // 发起提现
    public static final String WITHDRAW = "cash/nlg/getUserCash";
    //更新微币状态
    public static final String UPDATE_WEIBI = "nlg/updateGold";

}
