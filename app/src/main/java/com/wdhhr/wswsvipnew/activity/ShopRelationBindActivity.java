package com.wdhhr.wswsvipnew.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wdhhr.wswsvipnew.R;
import com.wdhhr.wswsvipnew.adapter.CommonAdapter;
import com.wdhhr.wswsvipnew.adapter.ViewHolder;
import com.wdhhr.wswsvipnew.base.BaseActivity;
import com.wdhhr.wswsvipnew.model.UserCommonBean;
import com.wdhhr.wswsvipnew.model.dataBase.UsersListBean;
import com.wdhhr.wswsvipnew.net.NetworkManager;
import com.wdhhr.wswsvipnew.net.LoadStatusSubscriberCallBack;
import com.wdhhr.wswsvipnew.utils.ImageUtils;
import com.wdhhr.wswsvipnew.utils.LoadErrorUtils;
import com.wdhhr.wswsvipnew.widget.XListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class ShopRelationBindActivity extends BaseActivity {

    @BindView(R.id.icon_title_left)
    ImageView iconTitleLeft;
    @BindView(R.id.title_content)
    TextView titleContent;
    @BindView(R.id.icon_text_right)
    TextView iconTextRight;
    @BindView(R.id.icon_title_right)
    ImageView iconTitleRight;
    @BindView(R.id.layout_title_bg)
    RelativeLayout mLayoutTitleBg;
    @BindView(R.id.listview_relation_bind)
    XListView mXListview;
    private boolean isLoad;
    private int miPage = 1;
    private static final String TAG = "ShopRelationBindActivit";
    private ArrayList<UsersListBean> mUsersList;
    private CommonAdapter<UsersListBean> mAdapter;
    private LoadErrorUtils mXlvUtils;

    @Override
    protected int setViewId() {
        return R.layout.activity_shop_fans_person;
    }

    @Override
    protected void init() {
        iconTitleLeft.setImageResource(R.mipmap.title_back);
        titleContent.setText(getResources().getString(R.string.shop_fans));
        iconTitleRight.setVisibility(View.GONE);
        mUsersList = new ArrayList<>();
        mAdapter = new CommonAdapter<UsersListBean>(this, mUsersList, R.layout.item_direct_push) {
            @Override
            public void convert(ViewHolder helper, int position, UsersListBean item) {
                helper.setText(R.id.tv_push_name, item.getUserName());
                //0.普通用户   1 总代理   2 经理   3 联合创始人

                switch (item.getUserLevel()) {
                    case 0:
                        helper.setText(R.id.bt_userleverl, getResources().getString(R.string.normal_user));
                        ((TextView) helper.getView(R.id.bt_userleverl)).setTextColor(getResources().getColor(R.color.fontSubTile));
                        helper.getView(R.id.bt_userleverl).setBackgroundColor(getResources().getColor(R.color.background));
                        break;
                    case 1:
                        helper.setText(R.id.bt_userleverl, getResources().getString(R.string.general_agency));
                        ((TextView) helper.getView(R.id.bt_userleverl)).setTextColor(getResources().getColor(R.color.fontSubTile));
                        helper.getView(R.id.bt_userleverl).setBackgroundColor(getResources().getColor(R.color.background));
                        break;
                    case 2:
                        helper.setText(R.id.bt_userleverl, getResources().getString(R.string.manager));
                        ((TextView) helper.getView(R.id.bt_userleverl)).setTextColor(getResources().getColor(R.color.white));
                        helper.getView(R.id.bt_userleverl).setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        break;
                    case 3:
                        helper.setText(R.id.bt_userleverl, getResources().getString(R.string.originator));
                        ((TextView) helper.getView(R.id.bt_userleverl)).setTextColor(getResources().getColor(R.color.white));
                        helper.getView(R.id.bt_userleverl).setBackgroundColor(getResources().getColor(R.color.fontOrange));
                        break;
                }
                helper.setImageByUrl(R.id.iv_push_headicon, item.getUserPhoto(), R.mipmap.icon_head, ShopRelationBindActivity.this);
                ImageUtils.loadCircleImageUrl(((ImageView) helper.getView(R.id.iv_push_headicon)), item.getUserPhoto(), R.mipmap.icon_head, ShopRelationBindActivity.this);
            }
        };
        mXListview.setAdapter(mAdapter);
        mXlvUtils = new LoadErrorUtils(mXListview, this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });
    }

    @Override
    protected void initEvent() {
        // 上拉下拉监听
        mXListview.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                loadData();
            }

            @Override
            public void onLoadMore() {
                isLoad = true;
                loadData();
            }
        });
    }

    @Override
    protected void loadData() {
        Map<String, Integer> map = new HashMap();
        if (isLoad) {
            miPage++;
        } else {
            miPage = 1;
        }
        isLoad = false;
        map.put("page", miPage);
        getDirectPushInfo(map);
    }

    void getDirectPushInfo(Map<String, Integer> map) {
        if (mUsersList == null || mUsersList.size() == 0) {
            showLoadPw();
        }
        NetworkManager.getApiService().getFansInfo(map).subscribeOn(Schedulers.io())
                .map(new Function<UserCommonBean, UserCommonBean>() {
                    @Override
                    public UserCommonBean apply(UserCommonBean directPushPersonBean) throws Exception {
                        return directPushPersonBean;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new LoadStatusSubscriberCallBack<UserCommonBean>(mXlvUtils) {
                    @Override
                    public void onSuccess(UserCommonBean userCommonBean) {
                        if (miPage == 1) {
                            mUsersList.clear();
                        }
                        mUsersList.addAll(userCommonBean.getData().getUsersList());
                    }

                    @Override
                    public int getListStatus(UserCommonBean userCommonBean) {
                        return userCommonBean.getStatus();
                    }

                    @Override
                    public List getList(UserCommonBean userCommonBean) {
                        return userCommonBean.getData().getUsersList();
                    }

                    @Override
                    public void onComplete() {
                        dismissLoadPw();
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.icon_title_left:
                finish();
                break;
        }
    }
}
