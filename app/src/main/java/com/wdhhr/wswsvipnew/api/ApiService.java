package com.wdhhr.wswsvipnew.api;

import com.wdhhr.wswsvipnew.constant.UrlConstants;
import com.wdhhr.wswsvipnew.model.OrderCommonBean;
import com.wdhhr.wswsvipnew.model.PayBean;
import com.wdhhr.wswsvipnew.model.SetOrderBean;
import com.wdhhr.wswsvipnew.model.ShopCommonBean;
import com.wdhhr.wswsvipnew.model.UserCommonBean;
import com.wdhhr.wswsvipnew.model.dataBase.BusinessInfoBean;
import com.wdhhr.wswsvipnew.model.dataBase.SystemMessageBean;
import com.wdhhr.wswsvipnew.model.dataBase.WeibiCommonBean;
import com.wdhhr.wswsvipnew.model.dataBase.WeibiShareSucessBean;

import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * Created by felear on 2017/8/13 0013.
 */

public interface ApiService {

    // User B

    /**
     * 注册
     */
    @POST(UrlConstants.REGISTER)
    Flowable<UserCommonBean> register(@QueryMap Map<String, String> registerMap);

    /**
     * 登录
     */
    @POST(UrlConstants.LOGIN)
    Flowable<UserCommonBean> login(@QueryMap Map<String, String> loginMap);

    /**
     * 获取验证码
     */
    @GET(UrlConstants.PHONECODE)
    Flowable<UserCommonBean> forgetCode(@QueryMap Map<String, String> forgetPwdMap);

    /**
     * 获取图形验证码
     */
    @GET(UrlConstants.VALIDATECODE)
    Flowable<UserCommonBean> validateCode(@QueryMap Map<String, String> forgetPwdMap);

    /**
     * 忘记密码
     */
    @POST(UrlConstants.FORGETPWD)
    Flowable<UserCommonBean> forgetPwd(@QueryMap Map<String, String> forgetPwdMap);

    /**
     * 删除Session
     */
    @POST(UrlConstants.CLEAR_SESSION)
    Flowable<UserCommonBean> clearSession();

    /**
     * 根据邀请码获取用户ID
     */
    @GET(UrlConstants.GET_USER_BY_CODE)
    Flowable<UserCommonBean> getUserIdByCode();

    // User E

    // home B

    /**
     * 商品分类
     */
    @GET(UrlConstants.GOODTYPES)
    Flowable<ShopCommonBean> getGoodTypes();

    /**
     * 热销商品列表
     */
    @GET(UrlConstants.HOST_SHOP)
    Flowable<ShopCommonBean> getHotShop(@QueryMap Map<String, String> map);

    /**
     * 品牌商品列表
     */
    @GET(UrlConstants.BRAND_TYPES)
    Flowable<ShopCommonBean> getBrandShop(@QueryMap Map<String, String> map);

    /**
     * 其他商品列表
     */
    @GET(UrlConstants.OTHER_SHOP)
    Flowable<ShopCommonBean> getOtherShop(@QueryMap Map<String, String> map);

    /**
     * 收藏商品
     */
    @POST(UrlConstants.ADD_GOOD_TO_BUSINESS)
    Flowable<ShopCommonBean> addGoodsToBusiness(@QueryMap Map<String, String> map);

    /**
     * 移除商品
     */
    @POST(UrlConstants.REMOVE_GOOD_TO_BUSINESS)
    Flowable<ShopCommonBean> removeGoodsToBusiness(@QueryMap Map<String, String> map);

    /**
     * 获取店铺收藏商品
     */
    @GET(UrlConstants.GET_GOODS_OF_BUSINESS)
    Flowable<ShopCommonBean> getGoodsOfBusiness(@QueryMap Map<String, String> map);


    /**
     * 收藏品牌
     */
    @POST(UrlConstants.ADD_BRAND_TO_BUSINESS)
    Flowable<ShopCommonBean> addBrandToBusiness(@QueryMap Map<String, String> map);

    /**
     * 移除品牌
     */
    @POST(UrlConstants.REMOVE_BRAND_TO_BUSINESS)
    Flowable<ShopCommonBean> removeBrandToBusiness(@QueryMap Map<String, String> map);
    // home E

    /**
     * 获取店铺收藏品牌
     */
    @GET(UrlConstants.GET_BRAND_OF_BUSINESS)
    Flowable<ShopCommonBean> getBrandOfBusiness(@QueryMap Map<String, String> map);

    // circle B

    /**
     * 获取话题分类列表
     */
    @GET(UrlConstants.CONVERSATION_TYPES)
    Flowable<ShopCommonBean> conversationTypeList();

    /**
     * 获取话题列表
     */
    @GET(UrlConstants.GET_CONVERSATION_LIST)
    Flowable<ShopCommonBean> conversationList(@QueryMap Map<String, String> map);

    /**
     * 获取话题列表
     */
    @GET(UrlConstants.GET_COMMENT)
    Flowable<ShopCommonBean> getComment(@QueryMap Map<String, String> map);

    /**
     * 获取话题
     */
    @POST(UrlConstants.CONVERSATION_LIST)
    Flowable<ShopCommonBean> conversationTopicList(@QueryMap Map<String, String> map);

    /**
     * 获取二级评论
     */
    @POST(UrlConstants.COMMENT_LIST)
    Flowable<ShopCommonBean> getCommentTwoStageList(@QueryMap Map<String, String> map);

    /**
     * 点赞话题
     */
    @POST(UrlConstants.UP_CONVERSATION)
    Flowable<ShopCommonBean> upConversation(@QueryMap Map<String, String> map);

    /**
     * 点赞评论
     */
    @POST(UrlConstants.UP_COMMENT)
    Flowable<ShopCommonBean> upComment(@QueryMap Map<String, String> map);

    /**
     * 发表评论
     */
    @POST(UrlConstants.ADD_COMMENT)
    Flowable<ShopCommonBean> addComment(@QueryMap Map<String, String> map);

    // circle E

    // ShopSearch B

    /**
     * 获取话题分类
     */
    @GET(UrlConstants.SHOP_INDEX_LIST)
    Flowable<ShopCommonBean> getShopIndexList(@QueryMap Map<String, String> map);

    /**
     * 获取话题分类
     */
    @GET(UrlConstants.HOT_SHOP_LIST)
    Flowable<ShopCommonBean> getHostShopList();
    // ShopSearch E

    // goodsDetail&BrandDetails B

    /**
     * 获取商品详情
     */
    @GET(UrlConstants.GOOD_DETAIL)
    Flowable<ShopCommonBean> getGoodDetails(@QueryMap Map<String, String> map);

    /**
     * 获取店铺品牌列表
     */
    @GET(UrlConstants.BUSINESS_BRAND)
    Flowable<ShopCommonBean> getBusinessBrand(@QueryMap Map<String, String> map);

    /**
     * 获取品牌商品列表
     */
    @GET(UrlConstants.BRAND_GOODS_LIST)
    Flowable<ShopCommonBean> getBrandGoodsList(@QueryMap Map<String, String> map);
    // goodsDetail&BrandDetails E

    // my 我的 B

    /**
     * 修改个人头像
     */
    @POST(UrlConstants.UPDATEUSER)
    Flowable<UserCommonBean> changeHeadIcon(@Body RequestBody params);

    /**
     * 版本查询
     */
    @POST(UrlConstants.GET_VERSION)
    Flowable<UserCommonBean> getVersion();

    /**
     * 修改店铺头像
     */
    @POST(UrlConstants.UPDATEBUSINESS)
    Flowable<BusinessInfoBean> changeShopIcon(@Body RequestBody params);

    /**
     * 获取直推人信息
     */
    @POST(UrlConstants.GETDIRECTPUSH)
    Flowable<UserCommonBean> getDirectPushInfo(@QueryMap Map<String, Integer> map);

    /**
     * 获取间推人信息
     */
    @POST(UrlConstants.BETWEENPUSH)
    Flowable<UserCommonBean> getBetweenPushInfo(@QueryMap Map<String, Integer> map);

    /**
     * 收益明细
     */
    @POST(UrlConstants.GET_PROFIT_DETAIL)
    Flowable<OrderCommonBean> getProfitDetail(@QueryMap Map<String, String> map);

    /**
     * 收益金额
     */
    @POST(UrlConstants.GET_PROFIT_AMOUNT)
    Flowable<OrderCommonBean> getProfitAmount();


    /**
     * 新增收获地址
     */
    @POST(UrlConstants.ADD_ADDRESS)
    Flowable<UserCommonBean> addAddress(@QueryMap Map<String, String> map);

    /**
     * 删除收获地址
     */
    @POST(UrlConstants.DELETE_ADDRESS)
    Flowable<UserCommonBean> deleteAddress(@QueryMap Map<String, String> map);

    /**
     * 修改收获地址
     */
    @POST(UrlConstants.EDIT_ADDRESS)
    Flowable<UserCommonBean> updateAddress(@QueryMap Map<String, String> map);

    /**
     * 获取收获地址列表
     */
    @GET(UrlConstants.GET_ADDRESS_LIST)
    Flowable<UserCommonBean> getAddressList();

    /**
     * 获取购物车列表
     */
    @POST(UrlConstants.GET_SHOP_CAR_LIST)
    Flowable<ShopCommonBean> getShopCarList();

    /**
     * 添加至购物车
     */
    @POST(UrlConstants.ADD_TO_SHOP_CAR)
    Flowable<ShopCommonBean> addToShopCar(@QueryMap Map<String, String> map);

    /**
     * 移出购物车
     */
    @POST(UrlConstants.REMOVE_SHOP_CAR)
    Flowable<ShopCommonBean> removeShopCar(@QueryMap Map<String, String> map);

    /**
     * 更新购物车
     */
    @POST(UrlConstants.UPDATE_SHOP_CAR)
    Flowable<ShopCommonBean> updateShopCar(@QueryMap Map<String, String> map);

    /**
     * 更新购物车
     */
    @GET(UrlConstants.GET_SHOP_CAR_NUM)
    Flowable<ShopCommonBean> getShopCarNum();

    /**
     * 创建订单
     */
    @POST(UrlConstants.CREATE_ORDER)
    Flowable<OrderCommonBean> creatOrder(@QueryMap Map<String, String> map);

    /**
     * 提交订单
     */
    @POST(UrlConstants.SET_ORDER)
    Flowable<SetOrderBean> setOrder(@QueryMap Map<String, String> map);

    /**
     * 修改订单
     */
    @POST(UrlConstants.UPDATE_ORDER_LIST)
    Flowable<OrderCommonBean> updateOrder(@QueryMap Map<String, String> map);

    /**
     * 上传图片
     */
    @POST(UrlConstants.UPLOAD_IMG)
    Flowable<UserCommonBean> uploadImg(@Body RequestBody params);

    /**
     * 删除订单
     */
    @POST(UrlConstants.DELETE_ORDER_LIST)
    Flowable<OrderCommonBean> deleteOrder(@QueryMap Map<String, String> map);

    /**
     * 获取订单
     */
    @GET(UrlConstants.GET_ORDER_LIST)
    Flowable<OrderCommonBean> getOrderList(@QueryMap Map<String, String> map);

    /**
     * 通过订单号或者流水号获取订单
     */
    @GET(UrlConstants.GET_ORDER_LIST_BY_ID)
    Flowable<OrderCommonBean> getOrderListById(@QueryMap Map<String, String> map);

    /**
     * 获取支付订单信息 ali和WeChat
     */
    @POST(UrlConstants.PAY_ORDER)
    Flowable<PayBean<Object>> payOrderAli(@QueryMap Map<String, String> map);

    @POST(UrlConstants.PAY_ORDER)
    Flowable<PayBean<PayBean.DataBean>> payOrderWechat(@QueryMap Map<String, String> map);

    /**
     * 支付结果查询
     */
    @POST(UrlConstants.PAY_STATUS_QUERY)
    Flowable<OrderCommonBean> payStatusQuery(@QueryMap Map<String, String> map);

    /**
     * 获取快递列表
     */
    @GET(UrlConstants.GET_EXPRESSLIST)
    Flowable<OrderCommonBean> getExpresslist();


    // my 我的 E
    //获取店铺信息
    @POST(UrlConstants.BUSINESSINFO)
    Flowable<BusinessInfoBean> getBusinessInfo(@QueryMap Map<String, String> map);

    //获取店铺信息
    @GET(UrlConstants.GET_COUNT_BY_BUSINESSID)
    Flowable<BusinessInfoBean> getCountByBusinessId(@QueryMap Map<String, String> map);

    //更新店铺信息
    @POST(UrlConstants.UPDATE_BUSINESSINFO)
    Flowable<BusinessInfoBean> updateBusinessInfo(@QueryMap Map<String, String> map);


    //置顶单品
    @POST(UrlConstants.TOP_GOODS)
    Flowable<ShopCommonBean> topgoods(@QueryMap Map<String, String> map);

    //获取微币
    @GET(UrlConstants.WEIBI)
    Flowable<WeibiCommonBean> getWeibi(@QueryMap Map<String, Integer> map);

    //微币分享成功
    @POST(UrlConstants.WEIBI_SHARE_SUCESS)
    Flowable<WeibiShareSucessBean> weibiShareSucess(@QueryMap Map<String, String> map);

    //获取系统消息
    @POST(UrlConstants.SYSTEM_MESSAGE)
    Flowable<SystemMessageBean> getSystemMessage(@QueryMap Map<String, Integer> map);    //获取系统消息

    //更改消息状态
    @POST(UrlConstants.UPDATE_MESSAGE_STATUS)
    Flowable<UserCommonBean> updateMessageStatus(@QueryMap Map<String, String> map);


    //获取粉丝信息(关系绑定)
    @POST(UrlConstants.GET_FANS_INFO)
    Flowable<UserCommonBean> getFansInfo(@QueryMap Map<String, Integer> map);

    /**
     * 查询是否绑定
     */
    @POST(UrlConstants.ALI_BAND_INFO)
    Flowable<UserCommonBean> isBand();

    /**
     * 查询是否绑定
     */
    @POST(UrlConstants.ALI_BAND)
    Flowable<UserCommonBean> aliBind(@QueryMap Map<String, String> map);

    /**
     * 查询是否绑定
     */
    @POST(UrlConstants.GET_CASH_AMOUNT)
    Flowable<UserCommonBean> getCashAmount();

    /**
     * 查询是否绑定
     */
    @POST(UrlConstants.GET_CASH_RECORD)
    Flowable<UserCommonBean> getCashRecord(@QueryMap Map<String, String> map);

    /**
     * 查询是否绑定
     */
    @POST(UrlConstants.WITHDRAW)
    Flowable<UserCommonBean> withdraw(@QueryMap Map<String, String> map);

    /**
     * 更新微币状态
     */
    @POST(UrlConstants.UPDATE_WEIBI)
    Flowable<WeibiCommonBean> updateWeibi(@QueryMap Map<String, String> map);

}
