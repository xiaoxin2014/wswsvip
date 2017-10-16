package com.wdhhr.wswsvipnew.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wdhhr.wswsvipnew.MyApplication;
import com.wdhhr.wswsvipnew.R;
import com.wdhhr.wswsvipnew.base.BaseActivity;
import com.wdhhr.wswsvipnew.constant.CircleConstants;
import com.wdhhr.wswsvipnew.constant.EventConstants;
import com.wdhhr.wswsvipnew.constant.MyConstants;
import com.wdhhr.wswsvipnew.constant.UrlConstants;
import com.wdhhr.wswsvipnew.dao.CircleDao;
import com.wdhhr.wswsvipnew.model.ShopCommonBean;
import com.wdhhr.wswsvipnew.model.cache.CommentListBean;
import com.wdhhr.wswsvipnew.model.dataBase.ConversationListBean;
import com.wdhhr.wswsvipnew.net.ApiSubscriberCallBack;
import com.wdhhr.wswsvipnew.net.NetworkManager;
import com.wdhhr.wswsvipnew.utils.ImageUtils;
import com.wdhhr.wswsvipnew.utils.NetworkUtils;
import com.wdhhr.wswsvipnew.utils.ShareUtils;
import com.wdhhr.wswsvipnew.widget.ImageDetailPopupWindow;

import org.simple.eventbus.Subscriber;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class ConverDetailActivity extends BaseActivity {


    @BindView(R.id.title)
    TextView mTvTitle;
    @BindView(R.id.tv_right)
    TextView mTvRight;
    @BindView(R.id.tv_back)
    TextView mTvBack;
    @BindView(R.id.layout_comment)
    LinearLayout mLayoutComment;
    @BindView(R.id.tv_more_comment)
    TextView mTvMoreComment;
    @BindView(R.id.edit_comment)
    EditText mEditComment;
    @BindView(R.id.tv_read_num)
    TextView mTvReadNum;
    @BindView(R.id.tv_message)
    TextView mTvMessage;
    @BindView(R.id.tv_release)
    TextView mTvRelease;
    @BindView(R.id.layout_comment_title)
    LinearLayout mLayoutCommentTitle;
    @BindView(R.id.cb_fabulous)
    CheckBox mCbFabulous;
    @BindView(R.id.webView)
    WebView mWebView;

    private ConversationListBean mConverBean;
    private final String TAG = ConverDetailActivity.class.getSimpleName();
    private List<CommentListBean> mCommentList;
    private ImageDetailPopupWindow mPwImageDetail;
    private String mstrComment;

    @Override
    protected int setViewId() {
        return R.layout.activity_conver_detail;
    }

    @Override
    protected void init() {

        mTvBack.setVisibility(View.VISIBLE);
        mTvRight.setVisibility(View.VISIBLE);
        mTvTitle.setText(R.string.detail);
        mTvRight.setText(R.string.share);

        String json = getIntent().getExtras().getString("json");

        mConverBean = new Gson().fromJson(json, ConversationListBean.class);

        initWebView();

    }

    @SuppressLint("JavascriptInterface")
    private void initWebView() {

        WebSettings webSettings = mWebView.getSettings();
        // 设置与Js交互的权限
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);//关键点 `wode

        // 先载入JS代码
        // 格式规定为:file:///android_asset/文件名.html
        mWebView.loadUrl("file:///android_asset/wqContent/content.html");

        mWebView.addJavascriptInterface(this, "Android");
        mWebView.requestFocus();
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                freshData();
            }
        });

        Log.d(TAG, "initWebView: " + mConverBean.getConversationContent());

    }


    @JavascriptInterface
    public String getValue() {
        return mConverBean.getConversationContent();
    }

    @JavascriptInterface
    public void showImgDetail(String imgSrc, String currentImgSrc) {
        Log.v(TAG, imgSrc + "+++" + currentImgSrc);
        int position = 0;
        String[] split = imgSrc.split(",");
        List<String> mUrlList = Arrays.asList(split);
        for (int i = 0; i < mUrlList.size(); i++) {
            if (mUrlList.get(i).equals(currentImgSrc)) {
                position = i;
            }
        }
        Log.v(TAG, "我的位置是：" + position);
        mPwImageDetail = new ImageDetailPopupWindow(ConverDetailActivity.this, mUrlList);
        mPwImageDetail.show(position, false);
    }

    private void freshData() {
        // 添加话题内容
        mTvReadNum.setText(getStrFormat(R.string.read, mConverBean.getReadNum()));
        mTvMessage.setText(mConverBean.getComment() + "");
        mTvMessage.setVisibility(View.VISIBLE);
        mCbFabulous.setVisibility(View.VISIBLE);

        showLoadPw();

        // 获取评论
        HashMap<String, String> map = new HashMap<>();
        map.put(CircleConstants.CONVERSATION_ID, mConverBean.getConversationId());
        map.put("page", "1");
        NetworkManager.getApiService().getComment(map)
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
                        int commentCount = shopCommonBean.getData().getCommentCount();
                        mCommentList = shopCommonBean.getData().getCommentList();
                        if (commentCount > 5) {
                            mTvMoreComment.setVisibility(View.VISIBLE);
                            mTvMoreComment.setText(getStrFormat(R.string.more_comment, commentCount));
                        }

                        addComment();
                        dismissLoadPw();
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        dismissLoadPw();
                    }
                });
    }

    @Override
    protected void initEvent() {
        // 话题点赞事件
        CircleDao.setConverFabous(mCbFabulous, mConverBean, this);

        // 更多评论
        mTvMoreComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("json", new Gson().toJson(mConverBean));
                readyGo(ConverCommentDetailActivity.class, bundle);
            }
        });

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

    @Subscriber(tag = EventConstants.CIRCLE_CONVER_UPDATE)
    private void ConverStatusChange(ConversationListBean conversationListBean) {
        if (mConverBean.equals(conversationListBean)) {
            mConverBean = conversationListBean;
            if (mConverBean.getIsCheck() == 1) {
                mCbFabulous.setChecked(true);
            } else {
                mCbFabulous.setChecked(false);
            }
            mCbFabulous.setText(mConverBean.getUpNum() + "");

        }
    }

    @Subscriber(tag = EventConstants.LOG_STATUS_CHANGE)
    private void loginStatusChange(int num) {
        freshData();
    }

    @Override
    protected void loadData() {

    }

    @Subscriber(tag = EventConstants.CIRCLE_COMMENT_UPDATE)
    private void commentStatusChange(CommentListBean comment) {
        if (mCommentList.contains(comment)) {
            for (int i = 0; i < mCommentList.size(); i++) {
                if (comment.equals(mCommentList.get(i))) {
                    mCommentList.remove(i);
                    mCommentList.add(i, comment);
                    // 修改界面显示
                    View childAt = mLayoutComment.getChildAt(i);
                    CheckBox cbFabulous = (CheckBox) childAt.findViewById(R.id.cb_fabulous);
                    cbFabulous.setText(comment.getUpNum() + "");
                    if (comment.getIsCheck() == 1) {
                        cbFabulous.setChecked(true);
                    } else {
                        cbFabulous.setChecked(false);
                    }
                    break;
                }
            }
        }
    }

    private void addComment() {
        mLayoutCommentTitle.setVisibility(View.GONE);
        if (mCommentList == null) {
            return;
        }

        if (mCommentList.size() > 0) {
            mLayoutCommentTitle.setVisibility(View.VISIBLE);
        }

        mLayoutComment.removeAllViews();

        for (int i = 0; i < mCommentList.size() && i < 6; i++) {
            CommentListBean comment = mCommentList.get(i);
            final int position = i;
            View commentView = LayoutInflater.from(this)
                    .inflate(R.layout.item_comment_detail, mLayoutComment, false);
            ((TextView) commentView.findViewById(R.id.tv_account_name)).setText(comment.getUserName());
            ((TextView) commentView.findViewById(R.id.tv_comment_content)).setText(comment.getCommentContent());
            ((TextView) commentView.findViewById(R.id.tv_comment_time)).setText(comment.getBeforTime());

            // 回复数量
            TextView tvReplyNum = (TextView) commentView.findViewById(R.id.tv_comment_reply_num);
            if (comment.getReplyNum() > 0) {
                tvReplyNum.setVisibility(View.VISIBLE);
                tvReplyNum.setText(getStrFormat(R.string.reply, comment.getReplyNum()));
            } else {
                tvReplyNum.setVisibility(View.GONE);
            }

            commentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("comment_json", new Gson().toJson(mCommentList.get(position)));
                    readyGo(ConverCommentDetailActivity.class, bundle);
                }
            });
            ImageView imageView = (ImageView) commentView.findViewById(R.id.iv_user_icon);
            ImageUtils.loadCircleImageUrl(imageView, comment.getUserPhoto()
                    , R.mipmap.icon_head
                    , ConverDetailActivity.this);

            CheckBox cbFabulous = (CheckBox) commentView.findViewById(R.id.cb_fabulous);
            CircleDao.setCommentFabous(cbFabulous, comment, this);

            mLayoutComment.addView(commentView);
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
                map.put("conversationId", mConverBean.getConversationId());
                map.put("replyUserId", MyApplication.getUserInfoAndLogin().getUsersId());
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
                                showLongToast(R.string.comment_validate);
                                mEditComment.setText("");
                                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                                dismissLoadPw();
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
            case R.id.tv_right:
                // 分享
                if (MyApplication.getUserInfoAndLogin() == null) {
                    return;
                }
                String url = UrlConstants.BASE_URl + "resources/H5/shareArticle.html?conversationTypeId="
                        + mConverBean.getConversationId()
                        + "&userId=" + MyApplication.getUserInfo().getUsersId();
                String[] split = mConverBean.getPicUrl().split(",");
                ShareUtils.ShowShareBord(MyConstants.SHARE_BRAND, this, url, mConverBean.getConversationTitle(), getResources().getString(R.string.share_circle_subtitle), split[0], null);
                break;
        }
    }
}
