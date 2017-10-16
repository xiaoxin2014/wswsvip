package com.wdhhr.wswsvipnew.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wdhhr.wswsvipnew.R;
import com.wdhhr.wswsvipnew.activity.ConverDetailActivity;
import com.wdhhr.wswsvipnew.adapter.CommonAdapter;
import com.wdhhr.wswsvipnew.adapter.RecyclerAdapter;
import com.wdhhr.wswsvipnew.base.BaseFragment;
import com.wdhhr.wswsvipnew.constant.CircleConstants;
import com.wdhhr.wswsvipnew.constant.EventConstants;
import com.wdhhr.wswsvipnew.dao.CircleDao;
import com.wdhhr.wswsvipnew.model.ShopCommonBean;
import com.wdhhr.wswsvipnew.model.dataBase.ConversationListBean;
import com.wdhhr.wswsvipnew.model.dataBase.ConversationTypeListBean;
import com.wdhhr.wswsvipnew.net.NetworkManager;
import com.wdhhr.wswsvipnew.net.LoadStatusSubscriberCallBack;
import com.wdhhr.wswsvipnew.utils.DeviceUtils;
import com.wdhhr.wswsvipnew.utils.ImageUtils;
import com.wdhhr.wswsvipnew.utils.LoadErrorUtils;
import com.wdhhr.wswsvipnew.widget.XListView;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class CircleFragment extends BaseFragment {

    @BindView(R.id.title)
    TextView textView;
    RecyclerView mRcvTopic;

    private final String TAG = CircleFragment.class.getSimpleName();
    @BindView(R.id.listView)
    XListView mXListView;
    private List<ConversationListBean> mConversationList;
    private List<ConversationTypeListBean> mTypeList;
    private RecyclerAdapter<ConversationTypeListBean> mTypeAdapter;
    private int miPage = 1;
    private String mstrCurrentId;
    private CommonAdapter<ConversationListBean> mConversationAdapter;
    private boolean isLoad;
    private LoadErrorUtils mXlvUtils;

    @Override
    protected int setViewId() {
        return R.layout.fragment_circle;
    }

    @Override
    protected void init() {

        textView.setText(R.string.circle);

        mRcvTopic = new RecyclerView(getActivity());
        mRcvTopic.setBackgroundColor(0xFFFFFFFF);

        mConversationList = new ArrayList<>();
        mConversationAdapter = new CommonAdapter<ConversationListBean>(getActivity()
                , mConversationList, R.layout.item_cicle_list) {

            @Override
            public void convert(com.wdhhr.wswsvipnew.adapter.ViewHolder helper, final int position, ConversationListBean item) {
                helper.setCircleImageByUrl(R.id.img_head, item.getThumbNailImg(), R.mipmap.circle_conversation_header, getActivity());
                String[] split = item.getPicUrl().split(",");
                int length = split.length;
                if (split.length >= 3) {
                    length = 3;
                }

                int width = (DeviceUtils.getScreenWdith() - DeviceUtils.dip2px(16 + (length - 1) * 4)) / length;
                Log.v(TAG, "" + width);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, width);
                params.leftMargin = DeviceUtils.dip2px(4);
                LinearLayout layout = helper.getView(R.id.layout_circle_pic);
                layout.removeAllViews();
                for (int i = 0; i < length; i++) {

                    ImageView imageView = new ImageView(getActivity());
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    imageView.setLayoutParams(params);
                    ImageUtils.loadImageUrl(imageView, split[i], getActivity());
                    layout.addView(imageView);
                }
                helper.setText(R.id.tv_conversation_time, item.getBeforTime());
                helper.setText(R.id.tv_content, item.getConversationTitle());
                helper.setText(R.id.tv_read_num, getStrFormat(R.string.read, item.getReadNum()));
                helper.setText(R.id.tv_message, item.getComment() + "");

                // 页面跳转
                helper.getView(R.id.layout_circle_pic).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("json", new Gson().toJson(mConversationList.get(position)));
                        readyGo(ConverDetailActivity.class, bundle);
                    }
                });
                //点击消息进详情
                helper.getView(R.id.tv_message).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("json", new Gson().toJson(mConversationList.get(position)));
                        readyGo(ConverDetailActivity.class, bundle);
                    }
                });
                // 点赞
                CheckBox cbFabulous = helper.getView(R.id.cb_fabulous);
                CircleDao.setConverFabous(cbFabulous, item, CircleFragment.this);

            }
        };

        mXListView.setAdapter(mConversationAdapter);

        // 话题分类集合
        mTypeAdapter = new RecyclerAdapter<ConversationTypeListBean>(getContext(), mTypeList, R.layout.item_circle_header) {

            @Override
            public void convert(RecyclerAdapter.ViewHolder helper, final int position, ConversationTypeListBean item) {
                helper.setText(R.id.text, item.getConversationName());
                helper.setImageByUrl(R.id.imageView, item.getConversationPic(), getContext());
                helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mstrCurrentId = mTypeList.get(position).getConversationTypeId();
                        showLoadPw();
                        loadConversation();
                    }
                });

            }
        };

        // 1. 线性布局 参数2：方向；参数3：是否逆向展示(true 逆向展示)
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext()
                , LinearLayoutManager.HORIZONTAL, false);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        mRcvTopic.setLayoutManager(linearLayoutManager);
        mRcvTopic.setAdapter(mTypeAdapter);
        mXListView.addHeaderView(mRcvTopic);

        mXlvUtils = new LoadErrorUtils(mXListView, mView, new View.OnClickListener() {
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
                loadConversation();
            }

            @Override
            public void onLoadMore() {
                isLoad = true;
                loadConversation();
            }
        });

    }

    @Subscriber(tag = EventConstants.LOG_STATUS_CHANGE)
    private void loginStatusChange(int num) {
        loadConversation();
    }

    @Subscriber(tag = EventConstants.CIRCLE_CONVER_UPDATE)
    private void ConverStatusChange(ConversationListBean conversationListBean) {
        if (mConversationList.contains(conversationListBean)) {
            for (int i = 0; i < mConversationList.size(); i++) {
                if (conversationListBean.equals(mConversationList.get(i))) {
                    mConversationList.remove(i);
                    mConversationList.add(i, conversationListBean);
                    mConversationAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    protected void loadData() {
        /**
         * 获取话题类型
         */

        showLoadPw();
        NetworkManager.getApiService().conversationTypeList()
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
                        mTypeList = shopCommonBean.getData().getConversationTypeList();
                        mTypeAdapter.refresh(mTypeList);
                        if (mTypeList != null && mTypeList.size() > 0) {
                            mstrCurrentId = mTypeList.get(0).getConversationTypeId();
                            loadConversation();
                        }
                    }

                    @Override
                    public int getListStatus(ShopCommonBean shopCommonBean) {
                        return shopCommonBean.getStatus();
                    }

                    @Override
                    public List getList(ShopCommonBean shopCommonBean) {
                        return shopCommonBean.getData().getConversationTypeList();
                    }

                    @Override
                    public void onComplete() {
                        dismissLoadPw();
                    }
                });
    }

    /**
     * 获取话题列表
     */
    private void loadConversation() {

        if (isLoad) {
            miPage++;
        } else {
            miPage = 1;
        }
        isLoad = false;

        if (mConversationList == null || mConversationList.size() == 0) {
            showLoadPw();
        }

        mXlvUtils.setEmptyView(null);

        HashMap<String, String> map = new HashMap<>();
        map.put(CircleConstants.CONVERSATIONTYPE_ID, mstrCurrentId);
        map.put("page", miPage + "");

        NetworkManager.getApiService().conversationList(map)
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
                        if (miPage == 1) {
                            mConversationList.clear();
                        }
                        mConversationList.addAll(shopCommonBean.getData().getConversationList());
                    }

                    @Override
                    public int getListStatus(ShopCommonBean shopCommonBean) {
                        return shopCommonBean.getStatus();
                    }

                    @Override
                    public List getList(ShopCommonBean shopCommonBean) {
                        return shopCommonBean.getData().getConversationList();
                    }

                    @Override
                    public void onComplete() {
                        mConversationAdapter.notifyDataSetChanged();
                        dismissLoadPw();
                    }
                });
    }

    @Override
    public void onClick(View view) {

    }

}
