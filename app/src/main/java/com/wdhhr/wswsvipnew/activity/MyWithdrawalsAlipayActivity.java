package com.wdhhr.wswsvipnew.activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.wdhhr.wswsvipnew.R;
import com.wdhhr.wswsvipnew.base.BaseActivity;
import com.wdhhr.wswsvipnew.base.OnSelTipsPwSureListener;
import com.wdhhr.wswsvipnew.model.UserCommonBean;
import com.wdhhr.wswsvipnew.net.NetworkManager;
import com.wdhhr.wswsvipnew.net.ApiSubscriberCallBack;
import com.wdhhr.wswsvipnew.utils.NetworkUtils;
import com.wdhhr.wswsvipnew.utils.StringUtils;
import com.wdhhr.wswsvipnew.utils.WindowUtils;

import java.util.HashMap;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MyWithdrawalsAlipayActivity extends BaseActivity {


    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tv_back)
    TextView tvBack;
    @BindView(R.id.edit_ali_account)
    EditText editAliAccount;
    @BindView(R.id.edit_user_id)
    EditText editUserId;
    @BindView(R.id.edit_ali_phone)
    EditText editAliPhone;
    @BindView(R.id.edit_code)
    EditText editCode;
    @BindView(R.id.tv_get_code)
    TextView tvGetCode;
    @BindView(R.id.tv_bind_send)
    TextView tvBindSend;
    private String mstrAcount;
    private String mstrPhone;
    private String mstrId;
    private String mstrCode;
    private int miCnt;

    private static final String TAG = "MyWithdrawalsAlipayActi";
    private boolean mIsSendCode;

    @Override
    protected int setViewId() {
        return R.layout.activity_my_withdrawals_alipay;
    }

    @Override
    protected void init() {
        title.setText(R.string.alipay_bind);
        tvBack.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initEvent() {
        editAliAccount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    mstrAcount = s + "";
                } else {
                    mstrAcount = null;
                }
                setBtnSendStatus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editAliPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    mstrPhone = s + "";
                } else {
                    mstrPhone = null;
                }
                setBtnSendStatus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editUserId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    mstrId = s + "";
                } else {
                    mstrId = null;
                }
                setBtnSendStatus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    mstrCode = s + "";
                } else {
                    mstrCode = null;
                }
                setBtnSendStatus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void setBtnSendStatus() {
        if (mstrAcount != null && mstrCode != null
                && mstrId != null && mstrPhone != null) {
            tvBindSend.setEnabled(true);
        } else {
            tvBindSend.setEnabled(false);
        }
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_get_code:
                // 获取验证码
                if (!StringUtils.isMobileNumber(mstrPhone)) {
                    showLongToast("请输入合法的手机号码");
                    return;
                }

                showLoadPw();
                HashMap<String, String> param = new HashMap<>();
                param.put("modelType", "2");
                param.put("phone", mstrPhone);
                param.put("plant", "1");
                NetworkManager.getApiService().forgetCode(param).subscribeOn(Schedulers.io())
                        .map(new Function<UserCommonBean, UserCommonBean>() {
                            @Override
                            public UserCommonBean apply(UserCommonBean userCommonBean) throws Exception {
                                return userCommonBean;
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new ApiSubscriberCallBack<UserCommonBean>() {
                            @Override
                            public void onSuccess(UserCommonBean userCommonBean) {
                                if (userCommonBean.getStatus() == 0) {
                                    mIsSendCode = true;
                                    WindowUtils.codeWait(tvGetCode, 60);
                                } else {
                                    mIsSendCode = false;
                                }
                                showLongToast(userCommonBean.getMsg());
                                dismissLoadPw();
                            }

                            @Override
                            public void onFailure(Throwable t) {
                                mIsSendCode = false;
                                if (NetworkUtils.isOnline()) {
                                    showLongToast(R.string.service_error);
                                } else {
                                    showLongToast(R.string.net_connect_error);
                                }
                                dismissLoadPw();
                            }
                        });
                break;
            case R.id.tv_bind_send:
                // 绑定账号
                if (!mIsSendCode) {
                    showLongToast("请先获取验证码");
                    return;
                }

                if (!StringUtils.isIdentityId(mstrId)) {
                    showLongToast("您输入的身份证号码有误！");
                    return;
                }

                showSelTipsPw(R.string.alipay_bind_tip, new OnSelTipsPwSureListener() {
                    @Override
                    public void onSure() {
                        showLoadPw();
                        HashMap<String, String> map = new HashMap<>();
                        map.put("alipayCard", mstrAcount);
                        map.put("phone", mstrPhone);
                        map.put("phoneCode", mstrCode);
                        map.put("IDCard", mstrId);
                        NetworkManager.getApiService().aliBind(map)
                                .subscribeOn(Schedulers.io())
                                .map(new Function<UserCommonBean, UserCommonBean>() {
                                    @Override
                                    public UserCommonBean apply(UserCommonBean userCommonBean) throws Exception {
                                        return userCommonBean;
                                    }
                                })
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new ApiSubscriberCallBack<UserCommonBean>() {
                                    @Override
                                    public void onSuccess(UserCommonBean userCommonBean) {
                                        dismissLoadPw();
                                        showLongToast(userCommonBean.getMsg());
                                        if (userCommonBean.getStatus() == 0) {
                                            finish();
                                        }

                                    }

                                    @Override
                                    public void onFailure(Throwable t) {
                                        dismissLoadPw();
                                        showLongToast(R.string.net_connect_error);
                                    }
                                });
                    }
                });


                break;
        }
    }

}
