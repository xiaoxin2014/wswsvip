package com.wdhhr.wswsvipnew.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wdhhr.wswsvipnew.MyApplication;
import com.wdhhr.wswsvipnew.R;
import com.wdhhr.wswsvipnew.adapter.CommonAdapter;
import com.wdhhr.wswsvipnew.adapter.ViewHolder;
import com.wdhhr.wswsvipnew.base.BaseActivity;
import com.wdhhr.wswsvipnew.constant.CircleConstants;
import com.wdhhr.wswsvipnew.constant.EventConstants;
import com.wdhhr.wswsvipnew.dao.CircleDao;
import com.wdhhr.wswsvipnew.model.ShopCommonBean;
import com.wdhhr.wswsvipnew.model.cache.CommentListBean;
import com.wdhhr.wswsvipnew.model.dataBase.ConversationListBean;
import com.wdhhr.wswsvipnew.net.NetworkManager;
import com.wdhhr.wswsvipnew.net.ApiSubscriberCallBack;
import com.wdhhr.wswsvipnew.net.LoadStatusSubscriberCallBack;
import com.wdhhr.wswsvipnew.utils.DeviceUtils;
import com.wdhhr.wswsvipnew.utils.ImageUtils;
import com.wdhhr.wswsvipnew.utils.LoadErrorUtils;
import com.wdhhr.wswsvipnew.utils.NetworkUtils;
import com.wdhhr.wswsvipnew.widget.XListView;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class ConverCommentDetailActivity extends BaseActivity {

    @BindView(R.id.xlv_comment)
    XListView mXlvComment;
    @BindView(R.id.edit_comment)
    EditText mEditComment;
    @BindView(R.id.tv_release)
    TextView mTvRelease;
    private String mstrConverId;
    private List<CommentListBean> mCommentList;
    private CommonAdapter<CommentListBean> mAdapter;
    private int miPage = 1;
    private ConversationListBean mConversationListBean;
    private boolean isLoad;
    private CommentListBean mCommentListBean;
    private LoadErrorUtils mXlvUtils;
    private LoadStatusSubscriberCallBack<ShopCommonBean> loadStatusSubscriberCallBack;
    private String mstrComment;

    private static final String TAG = "ConverCommentDetailActi";

    @Override
    protected int setViewId() {
        return R.layout.activity_conver_comment_detail;
    }

    @Override
    protected void init() {

        mCommentList = new ArrayList<>();

        mAdapter = new CommonAdapter<CommentListBean>(this, mCommentList, R.layout.item_comment_detail) {

            @Override
            public void convert(ViewHolder helper, final int position, CommentListBean item) {
                helper.setText(R.id.tv_account_name, item.getUserName())
                        .setText(R.id.tv_comment_content, item.getCommentContent())
                        .setText(R.id.tv_comment_time, item.getBeforTime())
                        .setCircleImageByUrl(R.id.iv_user_icon, item.getUserPhoto(), R.mipmap.icon_head, ConverCommentDetailActivity.this);

                TextView tvReplyNum = helper.getView(R.id.tv_comment_reply_num);
                if (item.getReplyNum() > 0) {
                    tvReplyNum.setVisibility(View.VISIBLE);
                    tvReplyNum.setText(getStrFormat(R.string.reply, item.getReplyNum()));
                } else {
                    tvReplyNum.setVisibility(View.GONE);
                }

                if (mstrConverId != null) {
                    helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Bundle bundle = new Bundle();
                            bundle.putString("comment_json", new Gson().toJson(mCommentList.get(position)));
                            readyGo(ConverCommentDetailActivity.class, bundle);
                        }
                    });
                }
                CheckBox cbFabulous = helper.getView(R.id.cb_fabulous);
                CircleDao.setCommentFabous(cbFabulous, item, ConverCommentDetailActivity.this);
            }
        };

        mXlvComment.setAdapter(mAdapter);

        String json = getIntent().getExtras().getString("json");
        String commentJson = getIntent().getExtras().getString("comment_json");

        if (json != null) {
            mConversationListBean = new Gson().fromJson(json, ConversationListBean.class);
            mstrConverId = mConversationListBean.getConversationId();
        } else {
            mCommentListBean = new Gson().fromJson(commentJson, CommentListBean.class);

            // 添加头布局
            LinearLayout layout = new LinearLayout(this);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layout.setLayoutParams(params);
            layout.setOrientation(LinearLayout.VERTICAL);

            View commentView = LayoutInflater.from(this)
                    .inflate(R.layout.item_comment_detail, layout, false);
            ((TextView) commentView.findViewById(R.id.tv_account_name)).setText(mCommentListBean.getUserName());
            ((TextView) commentView.findViewById(R.id.tv_comment_content)).setText(mCommentListBean.getCommentContent());
            ((TextView) commentView.findViewById(R.id.tv_comment_time)).setText(mCommentListBean.getBeforTime());

            ImageView imageView = (ImageView) commentView.findViewById(R.id.iv_user_icon);
            ImageUtils.loadCircleImageUrl(imageView, mCommentListBean.getUserPhoto(), R.mipmap.icon_head, this);

            CheckBox cbFabulous = (CheckBox) commentView.findViewById(R.id.cb_fabulous);
            CircleDao.setCommentFabous(cbFabulous, mCommentListBean, this);
            layout.addView(commentView);

            View view = new View(this);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DeviceUtils.dip2px(1)));
            view.setBackgroundColor(0xfff3f3f3);
            layout.addView(view);

            TextView textView = new TextView(this);
            textView.setLayoutParams(params);
            textView.setTextColor(0xFFA4A4A4);
            textView.setText(R.string.all_comment);
            int offset = DeviceUtils.dip2px(12);
            textView.setPadding(offset, offset, offset, offset);
            layout.addView(textView);
            mXlvComment.addHeaderView(layout);
        }

        mXlvUtils = new LoadErrorUtils(mXlvComment, this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });
    }

    @Override
    protected void initEvent() {

        // 上拉下拉监听
        mXlvComment.setXListViewListener(new XListView.IXListViewListener() {
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

        loadStatusSubscriberCallBack = new LoadStatusSubscriberCallBack<ShopCommonBean>(mXlvUtils) {
            @Override
            public void onSuccess(ShopCommonBean shopCommonBean) {
                if (miPage == 1) {
                    mCommentList.clear();
                }
                mCommentList.addAll(shopCommonBean.getData().getCommentList());
            }

            @Override
            public int getListStatus(ShopCommonBean shopCommonBean) {
                return shopCommonBean.getStatus();
            }

            @Override
            public List getList(ShopCommonBean shopCommonBean) {
                return shopCommonBean.getData().getCommentList();
            }

            @Override
            public void onComplete() {
                dismissLoadPw();
                mAdapter.notifyDataSetChanged();
            }
        };

        mEditComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null && s.toString().trim().length() > 0) {
                    mstrComment = s.toString().trim();
                    mTvRelease.setEnabled(true);
                } else {
                    mstrComment = null;
                    mTvRelease.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Subscriber(tag = EventConstants.CIRCLE_COMMENT_UPDATE)
    private void commentStatusChange(CommentListBean comment) {
        if (mCommentList.contains(comment)) {
            for (int i = 0; i < mCommentList.size(); i++) {
                if (comment.equals(mCommentList.get(i))) {
                    mCommentList.remove(i);
                    mCommentList.add(i, comment);
                    mAdapter.notifyDataSetChanged();
                    break;
                }
            }
        }
    }

    @Subscriber(tag = EventConstants.LOG_STATUS_CHANGE)
    private void loginStatusChange(int num) {
        loadData();
    }

    @Override
    protected void loadData() {
        if (isLoad) {
            miPage++;
        } else {
            miPage = 1;
        }
        isLoad = false;

        // 获取评论列表
        HashMap<String, String> map = new HashMap<>();
        map.put("page", miPage + "");

        if (mCommentList.size() == 0) {
            showLoadPw();
        }

        if (mstrConverId != null) {
            map.put(CircleConstants.CONVERSATION_ID, mstrConverId);
            NetworkManager.getApiService().getComment(map)
                    .subscribeOn(Schedulers.io())
                    .map(new Function<ShopCommonBean, ShopCommonBean>() {
                        @Override
                        public ShopCommonBean apply(ShopCommonBean shopCommonBean) throws Exception {
                            return shopCommonBean;
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(loadStatusSubscriberCallBack);
        } else {
            map.put("commentId", mCommentListBean.getCommentId());
            NetworkManager.getApiService().getCommentTwoStageList(map)
                    .subscribeOn(Schedulers.io())
                    .map(new Function<ShopCommonBean, ShopCommonBean>() {
                        @Override
                        public ShopCommonBean apply(ShopCommonBean shopCommonBean) throws Exception {
                            return shopCommonBean;
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(loadStatusSubscriberCallBack);
        }

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_release:
                // 提交评论
                if (MyApplication.getUserInfoAndLogin() == null) {
                    return;
                }

                showLoadPw();
                HashMap<String, String> map = new HashMap<>();
                map.put("commentContent", mstrComment);

                if (mstrConverId != null) {

                    map.put("conversationId", mstrConverId);
                    map.put("replyUserId", MyApplication.getUserInfoAndLogin().getUsersId());
                } else {
                    map.put("conversationId", "");
                    map.put("replyUserId", mCommentListBean.getCommentId());
                }

                NetworkManager.getApiService().addComment(map)
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
                                if (shopCommonBean.getStatus() == 0) {
                                    showLongToast("评论提交成功");
                                    mEditComment.setText("");
                                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                                    dismissLoadPw();
                                    loadData();
                                } else {
                                    showLongToast(shopCommonBean.getMsg());
                                }
                            }

                            @Override
                            public void onFailure(Throwable t) {
                                dismissLoadPw();
                                if (NetworkUtils.isOnline()) {
                                    showLongToast(R.string.service_error);
                                } else {
                                    showLongToast(R.string.net_connect_error);
                                }
                            }
                        });

                break;
        }

    }

}
