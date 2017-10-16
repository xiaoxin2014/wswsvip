package com.wdhhr.wswsvipnew.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;
import com.wdhhr.wswsvipnew.MyApplication;
import com.wdhhr.wswsvipnew.R;
import com.wdhhr.wswsvipnew.adapter.CommonAdapter;
import com.wdhhr.wswsvipnew.adapter.ViewHolder;
import com.wdhhr.wswsvipnew.base.BaseActivity;
import com.wdhhr.wswsvipnew.constant.EventConstants;
import com.wdhhr.wswsvipnew.constant.UrlConstants;
import com.wdhhr.wswsvipnew.model.dataBase.WeibiCommonBean;
import com.wdhhr.wswsvipnew.net.NetworkManager;
import com.wdhhr.wswsvipnew.net.ApiSubscriberCallBack;
import com.wdhhr.wswsvipnew.net.LoadStatusSubscriberCallBack;
import com.wdhhr.wswsvipnew.utils.WindowUtils;
import com.wdhhr.wswsvipnew.utils.LoadErrorUtils;

import org.simple.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static java.lang.Integer.parseInt;

public class MyMoneyActivity extends BaseActivity {
    @BindView(R.id.icon_title_left)
    ImageView mIconTitleLeft;
    @BindView(R.id.title_content)
    TextView mTitleContent;
    @BindView(R.id.icon_text_right)
    TextView mIconTextRight;
    @BindView(R.id.icon_title_right)
    ImageView mIconTitleRight;
    @BindView(R.id.layout_title_bg)
    RelativeLayout mLayoutTitleBg;
    @BindView(R.id.listView_mymoney)
    ListView mListView;
    private PopupWindow mPwRule;
    private List<WeibiCommonBean.DataBean.GoldListBean> mWeibiList;
    private CommonAdapter<WeibiCommonBean.DataBean.GoldListBean> mWeibiAdapter;
    private LoadErrorUtils mXlvUtils;

    @Override
    protected int setViewId() {
        return R.layout.activity_my_money;
    }

    @Override
    protected void init() {
        mIconTitleLeft.setImageResource(R.mipmap.title_back);
        mTitleContent.setText(R.string.my_coin);
        mIconTextRight.setText(R.string.share_record);
        mIconTextRight.setVisibility(View.VISIBLE);
        mIconTitleRight.setImageResource(R.mipmap.icon_question_mark);
        mWeibiAdapter = new CommonAdapter<WeibiCommonBean.DataBean.GoldListBean>(this
                , null, R.layout.item_mymoney_list) {
            @Override
            public void convert(final ViewHolder helper, int position, final WeibiCommonBean.DataBean.GoldListBean item) {
                //设置金额
                int money = item.getMoney();
                helper.setText(R.id.tv_money_num, String.valueOf(money));
                //设置获得时间
                String stimeStr = item.getStimeStr();
                helper.setText(R.id.tv_weibi_get_time, getStrFormat(R.string.weibi_get_time, stimeStr));
                //设置微币状态
                switch (item.getStatus()) {
                    case 0:
                        //未使用
                        helper.setText(R.id.tv_status, getResources().getString(R.string.unused));
                        helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (getIntent().getExtras() != null) {
                                    int order = getIntent().getExtras().getInt("order", 0);
                                    if (order == 1) {
                                        EventBus.getDefault().post(item, EventConstants.WEIBI_DEDUCTION);
                                        finish();
                                    }
                                }
                            }
                        });
                        break;
                    case 1:
                        //已使用
                        helper.setText(R.id.tv_status, getResources().getString(R.string.used));
                        break;
                    case 2:
                        //已分享
                        helper.setText(R.id.tv_status, getResources().getString(R.string.already_share));
                        break;
                    case 3:
                        //已分享
                        helper.setText(R.id.tv_status, getResources().getString(R.string.already_share));
                        break;
                    case 4:
                        //已过期
                        helper.setText(R.id.tv_status, getResources().getString(R.string.out_of_date));
                        break;
                    case 5:
                        //未使用
                        helper.setText(R.id.tv_status, getResources().getString(R.string.unused));
                        helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int order = getIntent().getExtras().getInt("order");
                                if (order == 1) {
                                    Map<String, String> map = new HashMap<String, String>();
                                    map.put("money", item.getMoney() + "");
                                    map.put("wGlod", item.getWGlod());
                                    EventBus.getDefault().post(map, EventConstants.WEIBI_DEDUCTION);
                                    finish();
                                }
                            }
                        });

                        break;
                }
                //设置条目背景和分享按钮
                if (((TextView) helper.getView(R.id.tv_status)).getText().toString().trim().equals(getResources().getString(R.string.unused))) {
                    //未使用，并且金额是13
                    if (money == 13) {
                        helper.getView(R.id.layout_weibi_bg).setBackgroundResource(R.mipmap.weibi_bg_yellow);
                        helper.setImageResource(R.id.iv_share_weibi, R.mipmap.weibi_share_yellow);
                    } else {
                        //未使用，并且金额是20
                        helper.getView(R.id.layout_weibi_bg).setBackgroundResource(R.mipmap.weibi_bg_pink);
                        helper.setImageResource(R.id.iv_share_weibi, R.mipmap.weibi_share_pink);
                    }
                    helper.getView(R.id.iv_share_weibi).setVisibility(View.VISIBLE);
                    //点击分享微币
                    helper.getView(R.id.iv_share_weibi).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (MyApplication.getUserInfo() == null) {
                                return;
                            }
                            String url = UrlConstants.BASE_URl + "resources/H5/redpock.html?userId=" + MyApplication.getUserInfo() + "?coinId=" + item.getWGlod() + "&coinPrice=" + item.getMoney();
                            final UMWeb sWeb = new UMWeb(url);
                            sWeb.setTitle(getStrFormat(
                                    R.string.share_weibi_title_front, MyApplication.getUserInfo().getUserName()) + getStrFormat(R.string.share_weibi_title_behind, item.getMoney()));
                            //标题
                            sWeb.setDescription(getResources().getString(R.string.share_weibi_subtitle));//描述
                            sWeb.setThumb(new UMImage(MyMoneyActivity.this, R.mipmap.weibi_share_icon));//缩略图
                            final Map<String, String> map = new HashMap<String, String>();
                            map.put("isFlag", 0 + "");
                            map.put("status", 2 + "");
                            map.put("wGlod", item.getWGlod());
                            new ShareAction(MyMoneyActivity.this).setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QQ, SHARE_MEDIA.SINA)
                                    .setShareboardclickCallback(new ShareBoardlistener() {
                                        @Override
                                        public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                                            updateWeibiStatus(map);
                                            switch (share_media) {
                                                case WEIXIN:
                                                    new ShareAction(MyMoneyActivity.this).setPlatform(SHARE_MEDIA.WEIXIN)
                                                            .withMedia(sWeb)
                                                            .share();
                                                    break;
                                                case WEIXIN_CIRCLE:
                                                    new ShareAction(MyMoneyActivity.this).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                                                            .withMedia(sWeb)
                                                            .share();
                                                    break;
                                                case QQ:
                                                    new ShareAction(MyMoneyActivity.this).setPlatform(SHARE_MEDIA.QQ)
                                                            .withMedia(sWeb)
                                                            .share();
                                                    break;
                                                case SINA:
                                                    new ShareAction(MyMoneyActivity.this).setPlatform(SHARE_MEDIA.SINA)
                                                            .withMedia(sWeb)
                                                            .share();
                                                    break;
                                            }
                                        }
                                    })
                                    .open(getShareBoardConfig());
                        }
                    });
                } else {
                    //如果已使用
                    helper.getView(R.id.layout_weibi_bg).setBackgroundResource(R.mipmap.weibi_bg_gray);
                    helper.getView(R.id.iv_share_weibi).setVisibility(View.GONE);
                }
                //设置微币类型对应
                switch (parseInt(item.getType())) {
                    case 0:
                        //好友赠送
                        helper.setText(R.id.tv_weibi_type, getResources().getString(R.string.friend_send));
                        break;
                    case 1:
                        //系统赠送
                        helper.setText(R.id.tv_weibi_type, getResources().getString(R.string.system_send));
                        break;
                    case 2:
                        //被退回的
                        helper.setText(R.id.tv_weibi_type, getResources().getString(R.string.share_return));
                        break;
                }

                if (item.getStatus() == 2 || item.getStatus() == 3) {
                    helper.setText(R.id.tv_weibi_type, getResources().getString(R.string.mine_share));
                }
            }

        };
        mListView.setAdapter(mWeibiAdapter);
        mXlvUtils = new LoadErrorUtils(mListView, this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });
        mListView.setDivider(new ColorDrawable(0x00000000));
        mListView.setDividerHeight(getResources().getDimensionPixelSize(R.dimen.listview_divider_height));
        mPwRule = WindowUtils.getAlphaPw(this, R.layout.pw_my_coin_rule);
        mPwRule.setAnimationStyle(R.style.pw_slide_down);
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void loadData() {
        HashMap<String, Integer> map = new HashMap<>();
        //查询所有
        map.put("isCheck", -1);
        showLoadPw();
        NetworkManager.getApiService().getWeibi(map)
                .subscribeOn(Schedulers.io())
                .map(new Function<WeibiCommonBean, WeibiCommonBean>() {
                    @Override
                    public WeibiCommonBean apply(WeibiCommonBean WeibiBean) throws Exception {
                        return WeibiBean;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new LoadStatusSubscriberCallBack<WeibiCommonBean>(mXlvUtils) {
                    @Override
                    public void onSuccess(WeibiCommonBean weibiCommonBean) {
                        mWeibiAdapter.refresh(weibiCommonBean.getData().getGoldList());
                    }

                    @Override
                    public int getListStatus(WeibiCommonBean weibiCommonBean) {
                        return weibiCommonBean.getStatus();
                    }

                    @Override
                    public List getList(WeibiCommonBean weibiCommonBean) {
                        return weibiCommonBean.getData().getGoldList();
                    }

                    @Override
                    public void onComplete() {
                        dismissLoadPw();
                    }
                });
    }

    //分享的回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

    }

    private static final String TAG = "MyMoneyActivity";


    //分享微币，更新状态
    private void updateWeibiStatus(Map<String, String> map) {
        showLoadPw();
        NetworkManager.getApiService().updateWeibi(map)
                .subscribeOn(Schedulers.io())
                .map(new Function<WeibiCommonBean, WeibiCommonBean>() {
                    @Override
                    public WeibiCommonBean apply(WeibiCommonBean WeibiBean) throws Exception {
                        return WeibiBean;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriberCallBack<WeibiCommonBean>() {
                    @Override
                    public void onSuccess(WeibiCommonBean WeibiBean) {
                        if (WeibiBean.getStatus() == 0) {
                            loadData();
                        }
                        dismissLoadPw();
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        showLongToast(R.string.net_connect_error);
                        dismissLoadPw();
                    }
                });

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.icon_title_left:
                finish();
                break;
            //微币规则
            case R.id.icon_title_right:
                WindowUtils.setWindowAlpha(this, 0.6f);
                mPwRule.showAtLocation(mTitleContent, Gravity.TOP, 0, 0);
                break;
            //分享记录
            case R.id.icon_text_right:
                readyGo(WeibiShareRecordActivity.class);
                break;
            case R.id.tv_close:
                WindowUtils.closePW(mPwRule);
                break;
        }
    }
}
