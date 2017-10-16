package com.wdhhr.wswsvipnew.activity;

import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.NestedScrollView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wdhhr.wswsvipnew.MyApplication;
import com.wdhhr.wswsvipnew.R;
import com.wdhhr.wswsvipnew.base.BaseActivity;
import com.wdhhr.wswsvipnew.constant.EventConstants;
import com.wdhhr.wswsvipnew.constant.HomeContants;
import com.wdhhr.wswsvipnew.constant.MyConstants;
import com.wdhhr.wswsvipnew.constant.UrlConstants;
import com.wdhhr.wswsvipnew.dao.GoodsDao;
import com.wdhhr.wswsvipnew.dao.OrderDao;
import com.wdhhr.wswsvipnew.dao.UserDao;
import com.wdhhr.wswsvipnew.model.ShopCommonBean;
import com.wdhhr.wswsvipnew.model.cache.SpecHeaderBean;
import com.wdhhr.wswsvipnew.model.dataBase.GoodsListBean;
import com.wdhhr.wswsvipnew.model.dataBase.GoodsSpecListBean;
import com.wdhhr.wswsvipnew.model.dataBase.UsersBean;
import com.wdhhr.wswsvipnew.net.ApiSubscriberCallBack;
import com.wdhhr.wswsvipnew.net.LoadStatusSubscriberCallBack;
import com.wdhhr.wswsvipnew.net.NetworkManager;
import com.wdhhr.wswsvipnew.utils.DeviceUtils;
import com.wdhhr.wswsvipnew.utils.GlideImageLoader;
import com.wdhhr.wswsvipnew.utils.ImageUtils;
import com.wdhhr.wswsvipnew.utils.LoadErrorUtils;
import com.wdhhr.wswsvipnew.utils.NetworkUtils;
import com.wdhhr.wswsvipnew.utils.ShareUtils;
import com.wdhhr.wswsvipnew.utils.StatusBarUtil;
import com.wdhhr.wswsvipnew.utils.WindowUtils;
import com.wdhhr.wswsvipnew.widget.FlowRadioGroup;
import com.wdhhr.wswsvipnew.widget.ImageDetailPopupWindow;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class GoodDetailsActivity extends BaseActivity {

    private final String TAG = GoodDetailsActivity.class.getSimpleName();
    @BindView(R.id.tv_good_title)
    TextView mTvGoodTitle;
    @BindView(R.id.tv_price)
    TextView mTvPrice;
    @BindView(R.id.tv_shop_num)
    TextView mTvShopNum;
    @BindView(R.id.tv_stock_num)
    TextView mTvStockNum;
    @BindView(R.id.tv_express_press)
    TextView mTvExpressPress;
    @BindView(R.id.tv_right)
    TextView mTvRight;
    @BindView(R.id.cb_add_shop)
    CheckBox mCbAddShop;
    @BindView(R.id.tv_shopping)
    TextView mTvShopping;
    @BindView(R.id.tv_sell)
    TextView mTvSell;
    @BindView(R.id.banner)
    Banner mLoopView;
    @BindView(R.id.webView)
    WebView mWebView;
    @BindView(R.id.layout_promotion)
    LinearLayout mLayoutPromotion;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tv_back)
    TextView tvBack;
    @BindView(R.id.tv_back_white)
    TextView tvBackWhite;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.tv_spec_sel_name)
    TextView tvSpecSelName;
    @BindView(R.id.tv_rob)
    TextView mTvRob;
    @BindView(R.id.tv_shop_car_num)
    TextView mTvShopCarNum;
    @BindView(R.id.tv_profit)
    TextView mTvProfit;
    @BindView(R.id.layout_price)
    LinearLayout mLayoutPrice;
    @BindView(R.id.nested_scrollview_goods_detail)
    NestedScrollView mNestedScrollview;
    @BindView(R.id.tv_top_back)
    ImageView mTvTopBack;


    private String mstrGoodId;
    // 当前Spec位置
    private int miSpec;
    private List<GoodsSpecListBean> mGoodsSpecList;
    private PopupWindow mPwSpec;
    private int miShoppingNum;
    private TextView mPwTvRob;
    private TextView mPwTvPrice;
    private TextView mPwTvStockNum;
    private TextView mPwTvShopNum;
    private TextView mPwTvSpecTip;
    private ArrayList<FlowRadioGroup> mFlowLayouts;
    private ArrayList<List<String>> mSpecNameList;
    private ArrayList<List<Byte>> mSpecGroupList;
    private ArrayList<Byte> mUserCheckSpecList;
    private GoodsListBean mCurrentGoodBean;
    private boolean isShowTop;
    private String mstrSpecId;
    private List<GoodsListBean> mGoodsList;
    private String mUrl;
    private LoadErrorUtils mLoadErrorUtils;
    private ArrayList<String> mUrlList = new ArrayList<>();
    private ImageDetailPopupWindow mPwImageDetail;
    private String mStrDetailUrl;
    private ImageView mPwIvGoodsPic;

    @Override
    protected int setViewId() {
        return R.layout.activity_good_details;
    }

    @Override
    protected void init() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //修改状态栏为亮色模式
        StatusBarUtil.StatusBarLightMode(GoodDetailsActivity.this);
        mTvRight.setText(R.string.share_earn);
        mTvTitle.setText(R.string.detail);
        mTvTitle.setVisibility(View.VISIBLE);

        mFlowLayouts = new ArrayList<>();
        mSpecNameList = new ArrayList<>();
        mSpecGroupList = new ArrayList<>();
        mUserCheckSpecList = new ArrayList<>();

        mstrGoodId = getIntent().getExtras().getString(HomeContants.GOOD_ID);
        mstrSpecId = getIntent().getExtras().getString(HomeContants.SPEC_ID);

        // 获取本地缓存信息
        if (mstrGoodId == null) {
            String json = getIntent().getExtras().getString("json");
            mCurrentGoodBean = new Gson().fromJson(json, GoodsListBean.class);
            mstrGoodId = mCurrentGoodBean.getGoodsId();
        }

        mLoadErrorUtils = new LoadErrorUtils(this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });

        // 设置Banner高度
        ViewGroup.LayoutParams layoutParams = mLoopView.getLayoutParams();
        layoutParams.height = DeviceUtils.getScreenWdith();
        mLoopView.setLayoutParams(layoutParams);

        if (mCurrentGoodBean != null) {
            mstrGoodId = mCurrentGoodBean.getGoodsId();
        }
        mGoodsList = GoodsDao.setGuessHobby(this, mstrGoodId, mLayoutPromotion);

    }

    @Override
    protected void initEvent() {
        appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset == 0) {
                    // 完全展开 "EXPANDED"
                    //修改状态栏为亮色模式
                    StatusBarUtil.StatusBarLightMode(GoodDetailsActivity.this);
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                    // 完全收缩 "COLLAPSED"
                    tvBackWhite.setVisibility(View.VISIBLE);
                    tvBack.setVisibility(View.GONE);
                    isShowTop = true;
                    mTvTitle.setTextColor(0xFFFFFFFF);
                    mTvRight.setTextColor(0xFFFFFFFF);
                    //修改状态栏为暗色模式
                    StatusBarUtil.StatusBarDarkMode(GoodDetailsActivity.this);
                } else {
                    // 滑动调用
                    if (isShowTop) {
                        tvBackWhite.setVisibility(View.GONE);
                        tvBack.setVisibility(View.VISIBLE);
                        mTvTitle.setTextColor(getResources().getColor(R.color.fontTitle));
                        mTvRight.setTextColor(getResources().getColor(R.color.fontTitle));
                        isShowTop = false;
                        StatusBarUtil.StatusBarLightMode(GoodDetailsActivity.this);
                    }
                }

            }
        });

        mLoopView.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if (mPwImageDetail == null) {
                    mPwImageDetail = new ImageDetailPopupWindow(GoodDetailsActivity.this, mUrlList);
                }

                mPwImageDetail.show(position, true);
            }
        });

        mNestedScrollview.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                if (scrollY > oldScrollY) {
                    // 向下滑动屏幕一半
                    if (scrollY > DeviceUtils.getScreenHeight() / 2) {
                        mTvTopBack.setVisibility(View.VISIBLE);
                    }
                }

                if (scrollY < oldScrollY) {
                    // 向上滑动屏幕一半
                    if (scrollY < DeviceUtils.getScreenHeight() / 2) {
                        mTvTopBack.setVisibility(View.GONE);
                    }
                }

                if (scrollY == 0) {
                    // 顶部

                }

                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    // 底部

                }
            }
        });


        mTvTopBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNestedScrollview.smoothScrollTo(0, 0);
                appBar.setExpanded(true);
            }
        });
    }

    @Override
    protected void loadData() {
        if (mCurrentGoodBean == null) {

            HashMap<String, String> map = new HashMap<>();
            map.put("goodsId", mstrGoodId);
            map.put("page", "1");
            showLoadPw();
            NetworkManager.getApiService().getOtherShop(map)
                    .subscribeOn(Schedulers.io())
                    .map(new Function<ShopCommonBean, ShopCommonBean>() {
                        @Override
                        public ShopCommonBean apply(ShopCommonBean shopCommonBean) throws Exception {
                            return shopCommonBean;
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new LoadStatusSubscriberCallBack<ShopCommonBean>(mLoadErrorUtils) {
                        @Override
                        public void onSuccess(ShopCommonBean shopCommonBean) {
                            List<GoodsListBean> goodsList = shopCommonBean.getData().getGoodsListAndSave();
                            if (goodsList.size() > 0) {
                                mCurrentGoodBean = goodsList.get(0);
                                loadData();
                            }
                        }

                        @Override
                        public int getListStatus(ShopCommonBean shopCommonBean) {
                            return shopCommonBean.getStatus();
                        }

                        @Override
                        public List getList(ShopCommonBean shopCommonBean) {
                            return shopCommonBean.getData().getGoodsListAndSave();
                        }

                        @Override
                        public void onComplete() {
                            dismissLoadPw();
                        }
                    });

            return;
        }

        mFlowLayouts.clear();
        mSpecNameList.clear();
        mSpecGroupList.clear();
        mUserCheckSpecList.clear();
        mUserCheckSpecList.add((byte) 0);
        GoodsDao.setGoodsCollection(mCbAddShop, mCurrentGoodBean, GoodDetailsActivity.this);

        // 设置基本信息
        mTvGoodTitle.setText(mCurrentGoodBean.getGoodsName());
        mTvPrice.setText(Html.fromHtml(
                getStrFormat(R.string.price, mCurrentGoodBean.getGoodsPrice()) +
                        getStrFormat(R.string.price_cost, mCurrentGoodBean.getEarn())));
        mTvShopNum.setText(getStrFormat(R.string.onlineSaleNum, mCurrentGoodBean.getOnlineSaleNum()));

        // 初始化弹出窗
        mPwSpec = WindowUtils.getAlphaPw(this, R.layout.pw_good_spec_sel);

        mPwTvRob = (TextView) mPwSpec.getContentView().findViewById(R.id.tv_pw_rob);
        mPwTvPrice = (TextView) mPwSpec.getContentView().findViewById(R.id.tv_price);
        mPwTvStockNum = (TextView) mPwSpec.getContentView().findViewById(R.id.tv_stock_num);
        mPwTvShopNum = (TextView) mPwSpec.getContentView().findViewById(R.id.tv_shopping_num);
        mPwTvSpecTip = (TextView) mPwSpec.getContentView().findViewById(R.id.tv_spec_tip);
        mPwIvGoodsPic = (ImageView) mPwSpec.getContentView().findViewById(R.id.iv_icon);

        // 抢光了
        if (mCurrentGoodBean.getGoodsCount() - mCurrentGoodBean.getGoodsInventory() == 0) {
            mTvRob.setVisibility(View.VISIBLE);
            mPwTvRob.setVisibility(View.VISIBLE);
            mPwTvShopNum.setText("0");
            mTvRob.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showLongToast(R.string.sold_out_tip);
                }
            });

            mPwTvRob.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showLongToast(R.string.sold_out_tip);
                }
            });
        } else {
            mTvRob.setVisibility(View.GONE);
            mPwTvRob.setVisibility(View.GONE);
            mPwTvShopNum.setText("1");
        }

        LinearLayout layout = (LinearLayout) mPwSpec.getContentView().findViewById(R.id.layout_spec);

        String goodsSpecJSON = mCurrentGoodBean.getGoodsSpecJSON();
        goodsSpecJSON = goodsSpecJSON.replace("[", "").replace("]", "").replace("},{", ",");
        Gson gson = new Gson();
        SpecHeaderBean specHeaderBean = gson.fromJson(goodsSpecJSON, SpecHeaderBean.class);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        List<String> list = specHeaderBean.toArrayList();

        for (int i = 0; i < list.size(); i++) {
            FlowRadioGroup flowRadioGroup = new FlowRadioGroup(this);
            flowRadioGroup.setLayoutParams(params);
            TextView textView = new TextView(this);
            textView.setLayoutParams(params);
            textView.setText(list.get(i));
            textView.setPadding(DeviceUtils.dip2px(10), DeviceUtils.dip2px(10), 0, 0);
            layout.addView(textView);
            layout.addView(flowRadioGroup);

            mFlowLayouts.add(flowRadioGroup);
            mSpecNameList.add(new ArrayList<String>());
        }

        setBanner();
        setGoodsDetail();

        setShopCarNum(0);

        HashMap<String, String> map = new HashMap<>();
        map.put(HomeContants.GOOD_ID, mstrGoodId);
        map.put("page", "1");

        NetworkManager.getApiService().getOtherShop(map)
                .subscribeOn(Schedulers.io())
                .map(new Function<ShopCommonBean, ShopCommonBean>() {
                    @Override
                    public ShopCommonBean apply(ShopCommonBean shopCommonBean) throws Exception {
                        return shopCommonBean;
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriberCallBack<ShopCommonBean>() {
                    @Override
                    public void onSuccess(ShopCommonBean shopCommonBean) {
                        List<GoodsListBean> goodsList = shopCommonBean.getData().getGoodsList();
                        if (goodsList.size() > 0) {
                            mGoodsSpecList = goodsList.get(0).getGoodsSpecList();
                            if (mstrSpecId == null) {
                                miSpec = 0;
                            } else {
                                for (int i = 0; i < mGoodsSpecList.size(); i++) {
                                    if (TextUtils.equals(mstrSpecId, mGoodsSpecList.get(i).getGoodsDetailId())) {
                                        miSpec = i;
                                        break;
                                    }
                                }
                            }
                            Log.d(TAG, "hahahahah" + mGoodsSpecList);
                            addSpecTag();
                        } else {
                            showLongToast(R.string.net_connect_error);
                        }


                    }

                    @Override
                    public void onFailure(Throwable t) {
                        showLongToast(R.string.net_connect_error);
                    }
                });

    }

    private void setGoodsDetail() {
        String[] split = mCurrentGoodBean.getGoodDetailPic().split(",");
        mStrDetailUrl = "";
        if (split.length > 0) {

            for (int i = 0; i < split.length; i++) {
                mStrDetailUrl += ImageUtils.transformUrl(split[i]) + ",";
            }

            mStrDetailUrl = mStrDetailUrl.substring(0, mStrDetailUrl.length() - 1);

            initWebView();

            Log.d(TAG, "setGoodsDetail: " + mStrDetailUrl);

        }
    }

    private void initWebView() {

        WebSettings webSettings = mWebView.getSettings();
        // 设置与Js交互的权限
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);//关键点
        webSettings.setLoadWithOverviewMode(true);
        // 先载入JS代码
        // 格式规定为:file:///android_asset/文件名.html
        mWebView.loadUrl("file:///android_asset/wqContent/goodsdetail.html");
        mWebView.requestFocus();

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mWebView.loadUrl("javascript:loadImg('" + mStrDetailUrl + "')");
            }
        });

    }

    // 设置轮播图
    private void setBanner() {
        mUrlList.clear();
        String[] split = mCurrentGoodBean.getGoodsPic().split(",");
        if (split.length > 0) {

            for (int i = 0; i < split.length; i++) {
                mUrlList.add(ImageUtils.transformUrl(split[i]));
            }
            mLoopView.setImages(mUrlList);
        }

        //设置图片加载器
        mLoopView.setImageLoader(new GlideImageLoader());
        //设置图片集合
        mLoopView.start();
    }

    @Subscriber(tag = EventConstants.TRAD_SUCCESSFUL_CLICK)
    private void TradSuccessful(int num) {
        finish();
    }

    @Subscriber(tag = EventConstants.SHOP_NUM_LOAD_COMPLETE)
    private void setShopCarNum(int count) {
        if (MyApplication.mShopNumInfo != null && MyApplication.mShopNumInfo.getGoodsCount() > 0) {
            MyApplication.mShopNumInfo.getGoodsCount();
            mTvShopCarNum.setVisibility(View.VISIBLE);
            mTvShopCarNum.setText(MyApplication.mShopNumInfo.getGoodsCount() + "");
        } else {
            mTvShopCarNum.setVisibility(View.GONE);
        }
    }


    //店铺管理取消收藏时刷新首页热销单品
    @Subscriber(tag = EventConstants.GOODS_INFO_CHANGE)
    private void UpdateBusinessIcon(GoodsListBean goodsListBean) {
        if (goodsListBean.equals(mCurrentGoodBean)) {
            mCurrentGoodBean = goodsListBean;
            loadData();
        }

        if (mGoodsList.contains(goodsListBean)) {
            mGoodsList = GoodsDao.setGuessHobby(this, mCurrentGoodBean.getGoodsId(), mLayoutPromotion);
        }
    }

    private void addSpecTag() {

        if (mSpecNameList.size() == 0) {
            return;
        }

        mSpecGroupList.clear();

        // 生成Tag标签名集合
        for (GoodsSpecListBean specBean : mGoodsSpecList) {
            List<String> specList = specBean.getSpecList();
            Log.d(TAG, specBean + "");
            for (int i = 0; i < mSpecNameList.size(); i++) {
                List<String> list = mSpecNameList.get(i);
                if (!list.contains(specList.get(i))) {
                    list.add(specList.get(i));
                }
            }
        }


        // 生成标签View
        for (int i = 0; i < mSpecNameList.size(); i++) {
            List<String> list = mSpecNameList.get(i);
            FlowRadioGroup flowRadioGroup = mFlowLayouts.get(i);
            flowRadioGroup.removeAllViews();
            for (byte j = 0; j < list.size(); j++) {
                addSpecTag(list.get(j), flowRadioGroup, i, j);
            }
        }

        // 生成商品选项集合,转换成数据位置集合
        for (int i = 0; i < mGoodsSpecList.size(); i++) {

            ArrayList<Byte> specGroup = new ArrayList<>();
            List<String> specList = mGoodsSpecList.get(i).getSpecList();

            for (byte j = 0; j < mSpecNameList.size(); j++) {
                String specName = specList.get(j);
                List<String> nameList = mSpecNameList.get(j);
                for (byte k = 0; k < nameList.size(); k++) {
                    if (TextUtils.equals(nameList.get(k), specName)) {
                        specGroup.add(k);
                        break;
                    }
                }
            }

            mSpecGroupList.add(specGroup);
        }

        // 第一个默认帮用户选中
        mUserCheckSpecList.clear();
        mUserCheckSpecList.add((byte) 0);

        refreshCheckSpecs();

    }

    // 用户选择Spec后触发
    private void refreshCheckSpecs() {
        // 选中第一个Spec
        List<Byte> specGroup = mSpecGroupList.get(miSpec);

        // 设置默认选中
        for (int i = 0; i < mFlowLayouts.size(); i++) {
            FlowRadioGroup flowRadioGroup = mFlowLayouts.get(i);
            ((RadioButton) flowRadioGroup.getChildAt(specGroup.get(i))).setChecked(true);
        }

        // 判断是否有选项
        for (int i = mUserCheckSpecList.size(); i < specGroup.size(); i++) {
            ArrayList<Byte> tempList = new ArrayList<>();
            for (int j = 0; j < mSpecGroupList.size(); j++) {
                List<Byte> bytes = mSpecGroupList.get(j);
                boolean isCommon = true;
                // 判断前面项目与用户选中项是否相同
                for (int k = 0; k < mUserCheckSpecList.size(); k++) {
                    if (mUserCheckSpecList.get(k) != -1
                            && mUserCheckSpecList.get(k) != bytes.get(k)) {
                        isCommon = false;
                        break;
                    }
                }

                if (isCommon) {
                    tempList.add(bytes.get(i));
                }
            }

            // 设置i行不可选中
            FlowRadioGroup flowRadioGroup = mFlowLayouts.get(i);
            for (byte j = 0; j < flowRadioGroup.getChildCount(); j++) {
                if (!tempList.contains(j)) {
                    flowRadioGroup.getChildAt(j).setEnabled(false);
                } else {
                    flowRadioGroup.getChildAt(j).setEnabled(true);
                }
            }
        }
        setUI();
    }

    private void addSpecTag(String keyWord, ViewGroup root, final int row, final byte column) {
        RadioButton radioButton = (RadioButton) getLayoutInflater().inflate(R.layout.raido_button_good_spec, root, false);
        radioButton.setText(keyWord);
        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 设置用户点击List
                if (mUserCheckSpecList.size() < row) {
                    for (int i = mUserCheckSpecList.size(); i < row; i++) {
                        mUserCheckSpecList.add((byte) -1);
                    }
                } else {
                    for (int i = mUserCheckSpecList.size() - 1; i >= row; i--) {
                        mUserCheckSpecList.remove(i);
                    }
                }
                mUserCheckSpecList.add(column);

                // 判断当前默认Spec位置
                for (int i = 0; i < mSpecGroupList.size(); i++) {
                    List<Byte> bytes = mSpecGroupList.get(i);
                    boolean isCommon = true;
                    for (int j = 0; j < mUserCheckSpecList.size(); j++) {
                        if (mUserCheckSpecList.get(j) != -1 && mUserCheckSpecList.get(j) != bytes.get(j)) {
                            isCommon = false;
                            break;
                        }
                    }

                    if (isCommon) {
                        miSpec = i;
                        break;
                    }
                }

                v.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshCheckSpecs();
                    }
                }, 100);
            }
        });
        root.addView(radioButton);
    }

    private void setUI() {
        if (mGoodsSpecList == null || mGoodsSpecList.size() == 0) {
            // 无内容
            return;
        }

        GoodsSpecListBean goodsSpecListBean = mGoodsSpecList.get(miSpec);
        List<String> specList = goodsSpecListBean.getSpecList();
        String strSpecTips = getStr(R.string.selected) + ":";
        for (int i = 0; i < specList.size(); i++) {
            strSpecTips += "\"" + specList.get(i) + "\" ";
        }

        // 库存
        mTvStockNum.setText(getStrFormat(R.string.stockNum, goodsSpecListBean.getGoodsDetailInventory() + ""));
        // 邮费
        mTvExpressPress.setText(getStrFormat(R.string.good_express, "0.0"));
        // 商品Spec提示
        mPwTvSpecTip.setText(strSpecTips);

        tvSpecSelName.setText(strSpecTips);


        // 设置基本信息
        mTvPrice.setText(Html.fromHtml(
                getStrFormat(R.string.price, goodsSpecListBean.getGoodsDetailPrice() + "")));
        mTvProfit.setText(Html.fromHtml(
                getStrFormat(R.string.price_cost, goodsSpecListBean.getEarn() + "")));

        // 弹出窗UI
        mPwTvPrice.setText("¥" + goodsSpecListBean.getGoodsDetailPrice());
        mPwTvStockNum.setText(getStrFormat(R.string.stockNum, goodsSpecListBean.getGoodsDetailInventory() + ""));
        String[] split = goodsSpecListBean.getGoodsDetailPic().split(",");
        if (split.length > 0) {
            ImageUtils.loadImageUrl(mPwIvGoodsPic, split[0], R.mipmap.defalut_bg, GoodDetailsActivity.this);
        }
        Log.d(TAG, goodsSpecListBean.getGoodsDetailInventory() + "");
    }

    @Override
    public void onClick(View view) {
        HashMap<String, String> map = new HashMap<>();

        switch (view.getId()) {
            //分享赚钱和卖出
            case R.id.tv_sell:
            case R.id.tv_right:
                //弹出分享面板
                UsersBean userInfo = MyApplication.getUserInfo();
                if (userInfo == null) {
                    readyGo(LoginActivity.class);
                    return;
                }
                String[] split = mCurrentGoodBean.getGoodsPic().split(",");
                mUrl = UrlConstants.BASE_URl + "resources/H5/trademarkListInfo.html?goodsId=" + mCurrentGoodBean.getGoodsId() + "&userId=" + MyApplication.getUserInfo().getUsersId();
                ShareUtils.ShowShareBord(MyConstants.SHARE_GOODS, this, mUrl, mCurrentGoodBean.getGoodsName(), getResources().getString(R.string.share_goods_subtitle), split[0], mCurrentGoodBean);
                break;
            case R.id.tv_back_white:
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_spec_sel:
                // 弹出规格选择窗
                WindowUtils.setWindowAlpha(this, 0.6f);
                mPwSpec.showAtLocation(mTvSell, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.tv_shopping_add:
                miShoppingNum = Integer.parseInt(mPwTvShopNum.getText().toString());
                if (miShoppingNum < mGoodsSpecList.get(miSpec).getGoodsDetailInventory()) {
                    mPwTvShopNum.setText(++miShoppingNum + "");
                }
                break;
            case R.id.tv_shopping_sub:
                miShoppingNum = Integer.parseInt(mPwTvShopNum.getText().toString());
                if (miShoppingNum > 1) {
                    mPwTvShopNum.setText(--miShoppingNum + "");
                }
                break;
            case R.id.layout_to_shop_car:
                // 跳转至购物车界面
                if (MyApplication.getUserInfoAndLogin() != null) {
                    readyGo(ShopCartActivity.class);
                }
                break;
            //购买
            case R.id.tv_shopping:
                WindowUtils.setWindowAlpha(this, 0.6f);
                mPwSpec.showAtLocation(mTvSell, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.tv_pw_shop_now:
                WindowUtils.closePW(mPwSpec);
                ArrayList<HashMap<String, String>> jsonList = new ArrayList<>();
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("goodsId", mCurrentGoodBean.getGoodsId());
                hashMap.put("goodsDetailId", mGoodsSpecList.get(miSpec).getGoodsDetailId());
                hashMap.put("buyNum", mPwTvShopNum.getText() + "");
                jsonList.add(hashMap);
                OrderDao.setOrder(jsonList, this);
                break;
            case R.id.tv_add_shop_car:
                // 添加至购物车
                if (MyApplication.getUserInfoAndLogin() == null) {
                    return;
                }
                map.put("goodsId", mGoodsSpecList.get(miSpec).getGoodsId());
                map.put("num", mPwTvShopNum.getText() + "");
                map.put("goodsDetailId", mGoodsSpecList.get(miSpec).getGoodsDetailId());

                NetworkManager.getApiService().addToShopCar(map)
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
                                showLongToast("添加成功");
                                WindowUtils.closePW(mPwSpec);
                                UserDao.loadShopCarCount();
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
            case R.id.banner:
                showLongToast("hahahah");
                break;
        }
    }

}
