package com.wdhhr.wswsvipnew.dao;

import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import com.wdhhr.wswsvipnew.MyApplication;
import com.wdhhr.wswsvipnew.R;
import com.wdhhr.wswsvipnew.base.BaseViewInterface;
import com.wdhhr.wswsvipnew.constant.EventConstants;
import com.wdhhr.wswsvipnew.model.ShopCommonBean;
import com.wdhhr.wswsvipnew.model.cache.CommentListBean;
import com.wdhhr.wswsvipnew.model.dataBase.ConversationListBean;
import com.wdhhr.wswsvipnew.net.NetworkManager;
import com.wdhhr.wswsvipnew.net.ApiSubscriberCallBack;

import org.simple.eventbus.EventBus;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/8/31 0031.
 */

public class CircleDao {

    private static final String TAG = "CircleDao";

    // 设置评论点赞按钮和事件
    public static void setCommentFabous(final CheckBox cbFabulous, final CommentListBean comment, final BaseViewInterface baseView) {
        cbFabulous.setText(comment.getUpNum() + "");
        if (comment.getIsCheck() == 1) {
            cbFabulous.setChecked(true);
        }else {
            cbFabulous.setChecked(false);
        }

        cbFabulous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (cbFabulous.isChecked()) {

                    cbFabulous.setChecked(false);
                    if (MyApplication.getUserInfoAndLogin() == null) {
                        return;
                    }

                    HashMap<String, String> map = new HashMap<>();
                    map.put("commentId", comment.getCommentId());

                    NetworkManager.getApiService().upComment(map)
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
                                    cbFabulous.setChecked(true);
                                    comment.setIsCheck(1);
                                    comment.setUpNum(comment.getUpNum() + 1);
                                    cbFabulous.setText(comment.getUpNum() + "");
                                    EventBus.getDefault().post(comment, EventConstants.CIRCLE_COMMENT_UPDATE);
                                }

                                @Override
                                public void onFailure(Throwable t) {

                                }
                            });
                } else {
                    baseView.showLongToast(R.string.up_conversation_tips);
                    cbFabulous.setChecked(true);
                }
            }
        });
    }

    // 设置文章点赞按钮和事件处理
    public static void setConverFabous(final CheckBox cbFabulous, final ConversationListBean item, final BaseViewInterface baseView) {

        cbFabulous.setText(item.getUpNum() + "");
        if (item.getIsCheck() == 1) {
            cbFabulous.setChecked(true);
        } else {
            cbFabulous.setChecked(false);
        }

        cbFabulous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (cbFabulous.isChecked()) {

                    cbFabulous.setChecked(false);
                    if (MyApplication.getUserInfoAndLogin() == null) {
                        return;
                    }

                    HashMap<String, String> map = new HashMap<>();
                    map.put("conversationId", item.getConversationId());
                    NetworkManager.getApiService().upConversation(map)
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
                                    Log.d(TAG, shopCommonBean + "");
                                    item.setIsCheck(1);
                                    item.setUpNum(item.getUpNum() + 1);
                                    cbFabulous.setChecked(true);
                                    cbFabulous.setText(item.getUpNum() + "");
                                    EventBus.getDefault().post(item, EventConstants.CIRCLE_CONVER_UPDATE);
                                }

                                @Override
                                public void onFailure(Throwable t) {

                                }
                            });
                } else {
                    baseView.showShortToast(R.string.up_conversation_tips);
                    cbFabulous.setChecked(true);
                }
            }
        });

    }

}
