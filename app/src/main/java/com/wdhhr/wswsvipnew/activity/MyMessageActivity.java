package com.wdhhr.wswsvipnew.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wdhhr.wswsvipnew.MyApplication;
import com.wdhhr.wswsvipnew.R;
import com.wdhhr.wswsvipnew.adapter.CommonAdapter;
import com.wdhhr.wswsvipnew.adapter.ViewHolder;
import com.wdhhr.wswsvipnew.base.BaseActivity;
import com.wdhhr.wswsvipnew.constant.EventConstants;
import com.wdhhr.wswsvipnew.model.UserCommonBean;
import com.wdhhr.wswsvipnew.model.dataBase.SystemMessageBean;
import com.wdhhr.wswsvipnew.net.NetworkManager;
import com.wdhhr.wswsvipnew.net.ApiSubscriberCallBack;
import com.wdhhr.wswsvipnew.net.LoadStatusSubscriberCallBack;
import com.wdhhr.wswsvipnew.utils.StringUtils;
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

public class MyMessageActivity extends BaseActivity {

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
    @BindView(R.id.listview_mymessage)
    XListView mXListView;
    private boolean isLoad;
    private Integer miPage;
    private ArrayList<SystemMessageBean.DataBean.MessageListBean> mMessageList;
    private CommonAdapter<SystemMessageBean.DataBean.MessageListBean> mAdapter;
    private LoadErrorUtils mXlvUtils;
    List<String> mStringList = new ArrayList<>();

    @Override
    protected int setViewId() {
        return R.layout.activity_my_message;
    }

    @Override
    protected void init() {
        mMessageList = new ArrayList<>();
        mIconTitleLeft.setImageResource(R.mipmap.title_back);
        mTitleContent.setText("系统消息");
        mIconTitleRight.setVisibility(View.GONE);
        mAdapter = new CommonAdapter<SystemMessageBean.DataBean.MessageListBean>(this, mMessageList, R.layout.item_mymessage) {
            @Override
            public void convert(ViewHolder helper, int position, SystemMessageBean.DataBean.MessageListBean item) {
                helper.setText(R.id.tv_system_message, item.getContent());
                helper.setText(R.id.tv_time, StringUtils.getDataVByLongTime(item.getCtime().getTime()));
                if (item.getIsRead() == 0) {
                    //0未读
                    ((TextView) helper.getView(R.id.tv_system_message)).setTextColor(getResources().getColor(R.color.colorPrimary));
                    mStringList.add(item.getMessgeid());
                } else {
                    //1已读
                    ((TextView) helper.getView(R.id.tv_system_message)).setTextColor(getResources().getColor(R.color.fontTitle));
                }
            }
        };
        mXListView.setAdapter(mAdapter);
        mXlvUtils = new LoadErrorUtils(mXListView, this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });
    }

    @Override
    protected void initEvent() {
        // 上拉下拉监听
        mXListView.setXListViewListener(new XListView.IXListViewListener() {
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
        loadMessage(map);
    }

    private void loadMessage(Map<String, Integer> map) {
        if (mMessageList == null || mMessageList.size() == 0) {
            showLoadPw();
        }
        NetworkManager.getApiService().getSystemMessage(map)
                .subscribeOn(Schedulers.io())
                .map(new Function<SystemMessageBean, SystemMessageBean>() {
                    @Override
                    public SystemMessageBean apply(SystemMessageBean SystemMessageBean) throws Exception {
                        return SystemMessageBean;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new LoadStatusSubscriberCallBack<SystemMessageBean>(mXlvUtils) {
                    @Override
                    public void onSuccess(SystemMessageBean systemMessageBean) {
                        if (miPage == 1) {
                            mMessageList.clear();
                        }
                        mMessageList.addAll(systemMessageBean.getData().getMessageList());
                    }

                    @Override
                    public int getListStatus(SystemMessageBean systemMessageBean) {
                        return systemMessageBean.getStatus();
                    }

                    @Override
                    public List getList(SystemMessageBean systemMessageBean) {
                        return systemMessageBean.getData().getMessageList();
                    }

                    @Override
                    public void onComplete() {
                        dismissLoadPw();
                        mAdapter.notifyDataSetChanged();
                        for (int i = 0; i < mStringList.size(); i++) {
                            updateMessage(mStringList.get(i));
                        }
                        //未读消息的数量
                        if (MyApplication.mShopNumInfo!=null){
                            MyApplication.mShopNumInfo.setMessage(MyApplication.mShopNumInfo.getMessage() - mStringList.size());
                        }
                        EventBus.getDefault().post(0, EventConstants.SHOP_NUM_LOAD_COMPLETE);
                        mStringList.clear();
                    }
                });
    }

    private void updateMessage(String messgeid) {

        Map<String, String> map = new HashMap<>();
        map.put("messgeid", messgeid);
        map.put("isRead", 1 + "");
        map.put("flag", 0 + "");
        NetworkManager.getApiService().updateMessageStatus(map)
                .subscribeOn(Schedulers.io())
                .map(new Function<UserCommonBean, UserCommonBean>() {
                    @Override
                    public UserCommonBean apply(UserCommonBean UserCommonBean) throws Exception {
                        return UserCommonBean;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriberCallBack<UserCommonBean>() {
                    @Override
                    public void onSuccess(UserCommonBean UserCommonBean) {

                    }

                    @Override
                    public void onFailure(Throwable t) {
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
