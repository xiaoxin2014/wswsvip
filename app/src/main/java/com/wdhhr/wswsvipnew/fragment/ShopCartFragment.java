package com.wdhhr.wswsvipnew.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wdhhr.wswsvipnew.MyApplication;
import com.wdhhr.wswsvipnew.R;
import com.wdhhr.wswsvipnew.activity.GoodDetailsActivity;
import com.wdhhr.wswsvipnew.activity.LoginActivity;
import com.wdhhr.wswsvipnew.adapter.CommonAdapter;
import com.wdhhr.wswsvipnew.adapter.ViewHolder;
import com.wdhhr.wswsvipnew.base.BaseFragment;
import com.wdhhr.wswsvipnew.constant.EventConstants;
import com.wdhhr.wswsvipnew.constant.HomeContants;
import com.wdhhr.wswsvipnew.dao.OrderDao;
import com.wdhhr.wswsvipnew.model.ShopCommonBean;
import com.wdhhr.wswsvipnew.model.dataBase.GoodsListBean;
import com.wdhhr.wswsvipnew.model.dataBase.GoodsSpecListBean;
import com.wdhhr.wswsvipnew.model.dataBase.ShopCarListBean;
import com.wdhhr.wswsvipnew.net.NetworkManager;
import com.wdhhr.wswsvipnew.net.ApiSubscriberCallBack;
import com.wdhhr.wswsvipnew.net.LoadStatusSubscriberCallBack;
import com.wdhhr.wswsvipnew.utils.LoadErrorUtils;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class ShopCartFragment extends BaseFragment {

//    @BindView(R.id.v_header)
//    View mVHeader;
    @BindView(R.id.title)
    TextView mTvTitle;
    @BindView(R.id.tv_right)
    TextView mTvRight;
    private final String TAG = ShopCartFragment.class.getSimpleName();
    @BindView(R.id.lv_shop_car)
    ListView mLvShopCar;
    @BindView(R.id.cb_select_all)
    CheckBox mCbSelectAll;
    @BindView(R.id.tv_shop_now)
    TextView mTvShopNow;
    @BindView(R.id.tv_tip_title)
    TextView mTvTipTitle;
    @BindView(R.id.tv_total_amount)
    TextView mTvTotalAmount;
    @BindView(R.id.tv_tip)
    TextView mTvTip;
    @BindView(R.id.tv_login)
    TextView mTvLogin;
    private List<ShopCarListBean> mShopCarList = new ArrayList<>();
    private CommonAdapter<ShopCarListBean> mAdapter;
    private boolean isEditMode;
    // 购物车编辑列表
    private HashMap<String, ShopCarListBean> mEditShopCarIdMap;
    // 购物车集合，键为ID，方便通过id检索
    private HashMap<String, ShopCarListBean> mMapShopCar;
    // 购物车编辑后数量列表
    private HashMap<String, Integer> mMapShopNum = new HashMap<>();
    private LoadErrorUtils mXlvUtils;
    private double miTotalAmount;

    @Override
    protected int setViewId() {
        return R.layout.activity_shop_cart;
    }

    @Override
    protected void init() {

        mTvTitle.setText(R.string.shop_car);
        mTvRight.setText(R.string.edit);
//        mVHeader.setVisibility(View.VISIBLE);

        mEditShopCarIdMap = new HashMap<>();
        mMapShopCar = new HashMap<>();

        mAdapter = new CommonAdapter<ShopCarListBean>(getActivity(), mShopCarList, R.layout.item_shop_car) {

            @Override
            public void convert(ViewHolder helper, final int position, ShopCarListBean item) {
                GoodsSpecListBean goodsSpec = item.getGoodsSpec();

                TextView tvTitle = helper.getView(R.id.tv_title);
                TextView tvShopNum = helper.getView(R.id.tv_shop_num);
                CheckBox checkBox = helper.getView(R.id.cb_shop_car);
                final View tvSub = helper.getView(R.id.tv_shop_sub);
                final TextView tvShopNumEdit = helper.getView(R.id.tv_shop_num_edit);

                String[] split = item.getGoods().getGoodsPic().split(",");
                if (split.length > 0) {
                    helper.setImageByUrl(R.id.iv_goods_pic, split[0], getActivity());
                }

                if (isEditMode) {
                    helper.getConvertView().setClickable(false);
                    tvTitle.setVisibility(View.GONE);
                    helper.getView(R.id.layout_edit_num).setVisibility(View.VISIBLE);
                    int num = 1;
                    if (mMapShopNum.containsKey(item.getShopcarId())) {
                        num = mMapShopNum.get(item.getShopcarId());
                    } else {
                        num = item.getNum();
                    }
                    tvShopNumEdit.setText(num + "");

                    if (num <= 1) {
                        tvSub.setEnabled(false);
                    } else {
                        tvSub.setEnabled(true);
                    }
                    helper.setText(R.id.tv_price, "");
                    tvShopNum.setText("");
                } else {
                    helper.getView(R.id.layout_edit_num).setVisibility(View.GONE);
                    tvTitle.setVisibility(View.VISIBLE);
                    tvTitle.setText(item.getGoods().getGoodsName());
                    helper.setText(R.id.tv_price, getStr(R.string.money_unit) + goodsSpec.getGoodsDetailPrice());

                    tvShopNum.setText("×" + item.getNum());

                    helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            GoodsListBean goods = mShopCarList.get(position).getGoods();
                            goods.save();
                            Bundle bundle = new Bundle();
                            bundle.putString("json", new Gson().toJson(mShopCarList.get(position).getGoods()));
                            bundle.putString(HomeContants.SPEC_ID, mShopCarList.get(position).getGoodsSpec().getGoodsDetailId() + "");
                            readyGo(GoodDetailsActivity.class, bundle);
                        }
                    });
                }

                // 编辑按键事件监听
                tvSub.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ShopCarListBean carListBean = mShopCarList.get(position);
                        int num;
                        if (mMapShopNum.containsKey(carListBean.getShopcarId())) {
                            num = mMapShopNum.get(carListBean.getShopcarId());
                        } else {
                            num = carListBean.getNum();
                        }

                        tvShopNumEdit.setText(--num + "");
                        if (num == carListBean.getNum()) {
                            mMapShopNum.remove(carListBean.getShopcarId());
                        } else {
                            mMapShopNum.put(carListBean.getShopcarId(), num);
                        }

                        if (num <= 1) {
                            tvSub.setEnabled(false);
                        }
                        refreshAmount();

                    }
                });

                helper.getView(R.id.tv_shop_add).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ShopCarListBean carListBean = mShopCarList.get(position);
                        int num;
                        if (mMapShopNum.containsKey(carListBean.getShopcarId())) {
                            num = mMapShopNum.get(carListBean.getShopcarId());
                        } else {
                            num = carListBean.getNum();
                        }

                        if (num < (carListBean.getGoodsSpec().getGoodsDetailCount() - carListBean.getGoodsSpec().getGoodsDetailInventory())) {
                            tvShopNumEdit.setText(++num + "");
                            if (num == carListBean.getNum()) {
                                mMapShopNum.remove(carListBean.getShopcarId());
                            } else {
                                mMapShopNum.put(carListBean.getShopcarId(), num);
                            }
                        }

                        if (num > 1) {
                            tvSub.setEnabled(true);
                        }
                        refreshAmount();

                    }
                });


                List<String> specList = goodsSpec.getSpecList();
                String specName = getStr(R.string.spec) + ":" + specList.get(0);

                for (int i = 1; i < specList.size(); i++) {
                    specName += "+" + specList.get(i);
                    Log.d(TAG, specList.get(i) + " tag");
                }

                helper.setText(R.id.tv_title_sub, specName);
                // 抢光了
                if (goodsSpec.getGoodsDetailCount() - goodsSpec.getGoodsDetailInventory() <= 0) {
                    helper.getView(R.id.tv_rob).setVisibility(View.VISIBLE);
                    checkBox.setVisibility(View.INVISIBLE);
                } else {
                    helper.getView(R.id.tv_rob).setVisibility(View.GONE);
                    checkBox.setVisibility(View.VISIBLE);
                }

                // 是否选中
                if (item.getIsCheck() == 1) {
                    checkBox.setChecked(true);
                } else {
                    checkBox.setChecked(false);
                }

                checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        CheckBox checkBox = (CheckBox) v;
                        int itemIsCheck = 0;

                        if (checkBox.isChecked()) {
                            itemIsCheck = 1;
                        }
                        mShopCarList.get(position).setIsCheck(itemIsCheck);

                        boolean isCheck = true;
                        for (ShopCarListBean shopCar : mShopCarList) {
                            if (shopCar.getIsCheck() == 0) {
                                isCheck = false;
                            }
                        }
                        mCbSelectAll.setChecked(isCheck);
                        refreshAmount();

                    }
                });
            }
        };

        mLvShopCar.setAdapter(mAdapter);
        mXlvUtils = new LoadErrorUtils(mLvShopCar, getActivity(), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });
        refreshAmount();
    }

    @Override
    protected void initEvent() {

    }

    private void refreshAmount() {
        miTotalAmount = 0.0;
        if (mShopCarList != null) {

            for (ShopCarListBean shopCar : mShopCarList) {
                if (shopCar.getIsCheck() == 1) {
                    String goodsDetailPrice = shopCar.getGoodsSpec().getGoodsDetailPrice();
                    double price = 0;
                    if (goodsDetailPrice != null && goodsDetailPrice.length() > 0) {
                        price = Double.parseDouble(goodsDetailPrice);
                    }
                    if (mMapShopNum.containsKey(shopCar.getShopcarId())) {
                        miTotalAmount += mMapShopNum.get(shopCar.getShopcarId()) * price;
                    } else {
                        miTotalAmount += shopCar.getNum() * price;
                    }
                }
            }
        }
        mTvTotalAmount.setText(getStrFormat(R.string.total_amount, miTotalAmount + ""));
    }

    @Override
    protected void loadData() {
        mTvRight.setVisibility(View.GONE);

        if (MyApplication.getUserInfo() != null) {
            mTvTip.setVisibility(View.VISIBLE);
            mTvTipTitle.setText(R.string.shop_car_empty);
            mTvLogin.setVisibility(View.GONE);
            showLoadPw();
            NetworkManager.getApiService().getShopCarList()
                    .subscribeOn(Schedulers.io())
                    .map(new Function<ShopCommonBean, ShopCommonBean>() {
                        @Override
                        public ShopCommonBean apply(ShopCommonBean shopCommonBean) throws Exception {
                            return shopCommonBean;
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new LoadStatusSubscriberCallBack<ShopCommonBean>(mXlvUtils) {
                        @Override
                        public void onSuccess(ShopCommonBean shopCommonBean) {
                            mShopCarList.clear();
                            mShopCarList.addAll(shopCommonBean.getData().getShopCarList());
                            freshUi();
                        }

                        @Override
                        public int getListStatus(ShopCommonBean shopCommonBean) {
                            return shopCommonBean.getStatus();
                        }

                        @Override
                        public List getList(ShopCommonBean shopCommonBean) {
                            return shopCommonBean.getData().getShopCarList();
                        }

                        @Override
                        public void onComplete() {
                            dismissLoadPw();
                            mAdapter.notifyDataSetChanged();
                        }
                    });
        } else {
            mXlvUtils.setEmpty();
            mTvTip.setVisibility(View.GONE);
            mTvTipTitle.setText(R.string.shop_car_empty_tip_login);
            mTvLogin.setVisibility(View.VISIBLE);
        }

    }

    @Subscriber(tag = EventConstants.LOG_STATUS_CHANGE)
    private void loginStatusChange(int num) {
        loadData();
    }

    @Subscriber(tag = EventConstants.SHOP_NUM_LOAD_COMPLETE)
    private void refreshShopCar(int num) {
        loadData();
    }

    private void freshUi() {

        if (mShopCarList != null && mShopCarList.size() > 0) {


            // 设置之前选中的项目

            mTvRight.setVisibility(View.VISIBLE);
            for (int i = 0; i < mShopCarList.size(); i++) {
                ShopCarListBean shopCarListBean = mShopCarList.get(i);
                if (mMapShopCar.containsKey(shopCarListBean.getShopcarId())) {
                    shopCarListBean.setIsCheck(mMapShopCar.get(shopCarListBean.getShopcarId()).getIsCheck());
                }
                mMapShopCar.put(mShopCarList.get(i).getShopcarId(), shopCarListBean);
            }

            refreshAmount();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_right:
                // 编辑
                if (isEditMode) {
                    isEditMode = false;
                    mTvShopNow.setText(R.string.shopping_now);
                    mTvRight.setText(R.string.edit);

                    if (!mMapShopNum.isEmpty()) {
                        String strJson = "[";
                        for (String strId : mMapShopNum.keySet()) {
                            strJson += "{\"shopcarId\":\"" + strId + "\",\"num\":" + mMapShopNum.get(strId) + "},";
                        }
                        strJson = strJson.substring(0, strJson.length() - 1) + "]";
                        HashMap<String, String> map = new HashMap<>();
                        map.put("goodsJSON", strJson);
                        showLoadPw();
                        NetworkManager.getApiService().updateShopCar(map)
                                .subscribeOn(Schedulers.io())
                                .map(new Function<ShopCommonBean, ShopCommonBean>() {
                                    @Override
                                    public ShopCommonBean apply(ShopCommonBean shopCommonBean) throws Exception {
                                        return shopCommonBean;
                                    }
                                })
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new ApiSubscriberCallBack<ShopCommonBean>() {
                                    @Override
                                    public void onSuccess(ShopCommonBean shopCommonBean) {
                                        loadData();
                                    }

                                    @Override
                                    public void onFailure(Throwable t) {
                                        showLongToast(R.string.net_connect_error);
                                        dismissLoadPw();
                                    }
                                });

                    }

                } else {
                    isEditMode = true;
                    mTvRight.setText(R.string.finish);
                    mTvShopNow.setText(R.string.delete);
                }

                mAdapter.notifyDataSetChanged();
                break;
            case R.id.cb_select_all:
                // 全选
                int isCheck = 0;
                if (mCbSelectAll.isChecked()) {
                    isCheck = 1;
                }
                for (int i = 0; i < mShopCarList.size(); i++) {
                    mShopCarList.get(i).setIsCheck(isCheck);
                }
                mAdapter.notifyDataSetChanged();
                refreshAmount();

                break;
            case R.id.tv_shop_now:

                int num = 0;
                for (int i = 0; i < mShopCarList.size(); i++) {
                    if (mShopCarList.get(i).getIsCheck() == 1) {
                        num++;
                        break;
                    }
                }

                if (num == 0) {
                    showLongToast("您还没有选中商品");
                    return;
                }

                if (isEditMode) {
                    // 删除订单

                    mEditShopCarIdMap.clear();
                    for (ShopCarListBean shopCar : mShopCarList) {
                        if (shopCar.getIsCheck() == 1) {
                            mEditShopCarIdMap.put(shopCar.getShopcarId(), shopCar);
                        }
                    }
                    String strJson = "[";
                    for (String shopCarId : mEditShopCarIdMap.keySet()) {
                        strJson += "{\"shopcarId\":\"" + shopCarId + "\"},";
                    }
                    strJson = strJson.substring(0, strJson.length() - 1) + "]";
                    HashMap<String, String> map = new HashMap<>();
                    map.put("shopcarIdJSON", strJson);

                    // 移出购物车
                    showLoadPw();
                    NetworkManager.getApiService().removeShopCar(map)
                            .subscribeOn(Schedulers.io())
                            .map(new Function<ShopCommonBean, ShopCommonBean>() {
                                @Override
                                public ShopCommonBean apply(ShopCommonBean shopCommonBean) throws Exception {
                                    return shopCommonBean;
                                }
                            })
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new ApiSubscriberCallBack<ShopCommonBean>() {

                                @Override
                                public void onSuccess(ShopCommonBean shopCommonBean) {
                                    int delNum = 0;
                                    for (String shopCarId : mEditShopCarIdMap.keySet()) {
                                        mShopCarList.remove(mEditShopCarIdMap.get(shopCarId));
                                        delNum++;
                                    }
                                    int goodsCount = MyApplication.mShopNumInfo.getGoodsCount();
                                    if (goodsCount > delNum) {
                                        MyApplication.mShopNumInfo.setGoodsCount(goodsCount - delNum);
                                    } else {
                                        MyApplication.mShopNumInfo.setGoodsCount(0);
                                    }
                                    EventBus.getDefault().post(0, EventConstants.SHOP_NUM_LOAD_COMPLETE);
                                    if (mShopCarList.size() == 0) {
                                        mXlvUtils.setEmpty();
                                    }
                                    refreshAmount();
                                    mAdapter.notifyDataSetChanged();
                                    dismissLoadPw();
                                }

                                @Override
                                public void onFailure(Throwable t) {
                                    dismissLoadPw();
                                    showLongToast(R.string.net_connect_error);
                                }
                            });
                } else {
                    // 生成订单

                    ArrayList<HashMap<String, String>> jsonList = new ArrayList<>();
                    String strShopCardId = "";
                    for (int i = 0; i < mShopCarList.size(); i++) {
                        ShopCarListBean shopCarListBean = mShopCarList.get(i);
                        if (shopCarListBean.getIsCheck() == 1) {
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("goodsId", shopCarListBean.getGoodsId());
                            hashMap.put("goodsDetailId", shopCarListBean.getGoodsDetailId());
                            hashMap.put("buyNum", shopCarListBean.getNum() + "");
                            jsonList.add(hashMap);
                            strShopCardId += shopCarListBean.getShopcarId() + ",";
                        }
                    }

                    strShopCardId = strShopCardId.substring(0, strShopCardId.length() - 1);

                    if (jsonList.size() == 0) {
                        return;
                    }

                    OrderDao.setOrder(jsonList, strShopCardId, null, this);
                }
                break;
            case R.id.tv_login:
                readyGo(LoginActivity.class);
                break;
        }

    }
}
