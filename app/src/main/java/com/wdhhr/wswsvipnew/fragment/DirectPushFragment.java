package com.wdhhr.wswsvipnew.fragment;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.wdhhr.wswsvipnew.R;
import com.wdhhr.wswsvipnew.adapter.CommonAdapter;
import com.wdhhr.wswsvipnew.adapter.ViewHolder;
import com.wdhhr.wswsvipnew.base.BaseFragment;
import com.wdhhr.wswsvipnew.constant.EventConstants;
import com.wdhhr.wswsvipnew.model.UserCommonBean;
import com.wdhhr.wswsvipnew.model.dataBase.UsersListBean;
import com.wdhhr.wswsvipnew.net.NetworkManager;
import com.wdhhr.wswsvipnew.net.LoadStatusSubscriberCallBack;
import com.wdhhr.wswsvipnew.utils.LoadErrorUtils;
import com.wdhhr.wswsvipnew.widget.XListView;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class DirectPushFragment extends BaseFragment {


    private static final String TAG = "DirectPushFragment";
    @BindView(R.id.listview_direct_push)
    XListView mListviewDirectPush;
    private int miPage = 1;
    private CommonAdapter<UsersListBean> mAdapter;
    List<UsersListBean> mUsersList;
    private boolean isLoad = false;
    private LoadErrorUtils mXlvUtils;

    @Override
    protected int setViewId() {
        return R.layout.fragment_direct_push;
    }

    @Override
    protected void init() {
        mUsersList = new ArrayList<>();
        mAdapter = new CommonAdapter<UsersListBean>(getActivity(), mUsersList, R.layout.item_direct_push) {
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
                helper.setCircleImageByUrl(R.id.iv_push_headicon, item.getUserPhoto(), R.mipmap.icon_head, getActivity());
            }
        };
        mListviewDirectPush.setAdapter(mAdapter);
        mXlvUtils = new LoadErrorUtils(mListviewDirectPush, mView, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                freshData();
            }
        });
    }

    @Override
    protected void initEvent() {
        // 上拉下拉监听
        mListviewDirectPush.setXListViewListener(new XListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                freshData();
            }

            @Override
            public void onLoadMore() {
                isLoad = true;
                freshData();
            }
        });
    }


    @Override
    protected void loadData() {

    }

    @Override
    public void freshData() {
        Map<String, Integer> map = new HashMap<>();
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
        NetworkManager.getApiService().getDirectPushInfo(map).subscribeOn(Schedulers.io())
                .map(new Function<UserCommonBean, UserCommonBean>() {
                    @Override
                    public UserCommonBean apply(UserCommonBean UserCommonBean) throws Exception {
                        return UserCommonBean;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new LoadStatusSubscriberCallBack<UserCommonBean>(mXlvUtils) {
                    @Override
                    public void onSuccess(UserCommonBean userCommonBean) {
                        if (userCommonBean.getStatus() == 0) {
                            List<UsersListBean> usersList = userCommonBean.getData().getUsersList();
                            Log.v(TAG, "" + usersList);
                            List<String> mTitleList = new ArrayList<>();
                            mTitleList.add(getStrFormat(R.string.direct_push_num, userCommonBean.getData().getDirectPushCount()));
                            mTitleList.add(getStrFormat(R.string.between_push_num, userCommonBean.getData().getJanePushedCALLBLE()));
                            EventBus.getDefault().post(mTitleList, EventConstants.UPDATE_PUSH_PERSON_NUM);

                            if (miPage == 1) {
                                mUsersList.clear();
                            }
                            if (usersList != null && usersList.size() > 0) {
                                mUsersList.addAll(usersList);
                            }
                        } else {
                            showLongToast(userCommonBean.getMsg());
                        }


                    }

                    @Override
                    public int getListStatus(UserCommonBean userCommonBean) {
                        return userCommonBean.getStatus();
                    }

                    @Override
                    public List getList(UserCommonBean userCommonBean) {
                        if (userCommonBean.getData().getDirectPushCount() != null
                                && userCommonBean.getData().getUsersList() == null) {
                            return new ArrayList();
                        }
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

    }
}
