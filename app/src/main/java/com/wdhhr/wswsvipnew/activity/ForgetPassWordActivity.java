package com.wdhhr.wswsvipnew.activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.wdhhr.wswsvipnew.R;
import com.wdhhr.wswsvipnew.base.BaseActivity;
import com.wdhhr.wswsvipnew.model.UserCommonBean;
import com.wdhhr.wswsvipnew.net.NetworkManager;
import com.wdhhr.wswsvipnew.net.ApiSubscriberCallBack;
import com.wdhhr.wswsvipnew.utils.ImageUtils;
import com.wdhhr.wswsvipnew.utils.NetworkUtils;
import com.wdhhr.wswsvipnew.utils.StatusBarUtil;
import com.wdhhr.wswsvipnew.utils.StringUtils;
import com.wdhhr.wswsvipnew.utils.WindowUtils;

import java.util.HashMap;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class ForgetPassWordActivity extends BaseActivity {

    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.et_code)
    EditText mEtCode;
    @BindView(R.id.tv_get_code)
    TextView mTvGetCode;
    @BindView(R.id.et_forget_newpwd)
    EditText mEtPwdNew;
    @BindView(R.id.btn_forget_show)
    CheckBox mCbShowPwd;
    @BindView(R.id.forget_finish)
    TextView mTvSend;
    @BindView(R.id.et_forget_graph)
    EditText mEtCodeGraph;
    @BindView(R.id.btn_forget_graph)
    ImageView mIvCode;
    @BindView(R.id.title)
    TextView mTvTitle;
    @BindView(R.id.tv_back)
    TextView mTvBack;
    @BindView(R.id.scroolview_forgetpwd)
    ScrollView mScroolviewForgetpwd;
    private String TAG = ForgetPassWordActivity.class.getSimpleName();
    private String mstrPhone;
    private String mstrCode;
    private String mstrPhotoCode;
    private String mstrPwd;
    private boolean mIsSendCode;
    int flag = -1;
    boolean hasNavigationBar = false;
    private int mVirtualBarHeigh;
    private boolean isFirstGraph = true;

    @Override
    protected int setViewId() {
        return R.layout.activity_forgetpassword;
    }

    @Override
    protected void init() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //修改状态栏为亮色模式
        StatusBarUtil.StatusBarLightMode(this);
        showGraphMethod();
        mTvSend.setEnabled(false);
        mTvBack.setVisibility(View.VISIBLE);
        //判断是否有虚拟键盘
        hasNavigationBar = StatusBarUtil.checkDeviceHasNavigationBar(this);
        if (hasNavigationBar) {
            //获取虚拟键盘的高度
            mVirtualBarHeigh = StatusBarUtil.getVirtualBarHeigh(this);
        }
    }

    @Override
    protected void initEvent() {
        mCbShowPwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mCbShowPwd.setText(R.string.secret_text);
                    mEtPwdNew.setTransformationMethod(HideReturnsTransformationMethod
                            .getInstance());
                } else {
                    mCbShowPwd.setText(R.string.light_text);
                    mEtPwdNew.setTransformationMethod(PasswordTransformationMethod
                            .getInstance());
                }

            }
        });


        mEtPhone.addTextChangedListener(new TextWatcher() {
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

                if (StringUtils.isMobileNumber(mstrPhone)) {
                    mTvGetCode.setEnabled(true);
                } else {
                    mTvGetCode.setEnabled(false);
                }
                setBtnCompleteStatus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEtCode.addTextChangedListener(new TextWatcher() {
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
                setBtnCompleteStatus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEtCodeGraph.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    mstrPhotoCode = s + "";
                } else {
                    mstrPhotoCode = null;
                }
                setBtnCompleteStatus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEtPwdNew.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    mstrPwd = s + "";
                } else {
                    mstrPwd = null;
                }
                setBtnCompleteStatus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEtCodeGraph.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (hasNavigationBar) {
                        mScroolviewForgetpwd.smoothScrollTo(0, mEtCodeGraph.getHeight() / 3 + mVirtualBarHeigh);
                    } else {
                        mScroolviewForgetpwd.smoothScrollTo(0, mEtCodeGraph.getHeight() / 3);
                    }
                }
            }
        });

        mEtPwdNew.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    v.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (hasNavigationBar) {
                                mScroolviewForgetpwd.smoothScrollTo(0, mEtPwdNew.getHeight() * 7 / 5 + mVirtualBarHeigh);
                            } else {
                                mScroolviewForgetpwd.smoothScrollTo(0, mEtPwdNew.getHeight() * 7 / 5 + 8);
                            }
                        }
                    }, 500);


                }
            }
        });

    }

    private void setBtnCompleteStatus() {
        if (mstrPhone != null && mstrCode != null
                && mstrPhotoCode != null && mstrPwd != null) {
            mTvSend.setEnabled(true);
        } else {
            mTvSend.setEnabled(false);
        }
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //点击获取验证码
            case R.id.tv_get_code:
                mstrPhone = mEtPhone.getText().toString().trim();
                if (!StringUtils.isMobileNumber(mstrPhone)) {
                    showLongToast("请输入合法的手机号码");
                    return;
                }
                HashMap<String, String> param = new HashMap<>();
                param.put("plant", "0");
                param.put("modelType", "1");
                param.put("phone", mstrPhone);
                showLoadPw();
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
                                    WindowUtils.codeWait(mTvGetCode, 60);
                                } else {
                                    mIsSendCode = false;
                                }
                                showLongToast(userCommonBean.getMsg());
                                dismissLoadPw();
                            }

                            @Override
                            public void onFailure(Throwable t) {
                                Log.v(TAG, t.getMessage());
                                dismissLoadPw();
                                if (!NetworkUtils.isOnline()) {
                                    showLongToast(R.string.net_connect_error);
                                } else {
                                    showLongToast(R.string.service_error);
                                }
                            }
                        });
                break;
            case R.id.tv_back:
                finish();
                break;
            //点击完成
            case R.id.forget_finish:
                if (!mIsSendCode) {
                    showLongToast("请先获取验证码");
                    return;
                }
                mstrPhone = mEtPhone.getText().toString().trim();
                mstrCode = mEtCode.getText().toString().trim();
                mstrPhotoCode = mEtCodeGraph.getText().toString().trim();
                mstrPwd = mEtPwdNew.getText().toString().trim();
                HashMap<String, String> paramfinish = new HashMap<>();
                paramfinish.put("phone", mstrPhone);
                paramfinish.put("imgCode", mstrPhotoCode);
                paramfinish.put("code", mstrCode);
                paramfinish.put("password", mstrPwd);
                NetworkManager.getApiService().forgetPwd(paramfinish).subscribeOn(Schedulers.io())
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
                                    showLongToast(R.string.pwd_update_success);
                                    finish();
                                } else {
                                    showLongToast(userCommonBean.getMsg());
                                }

                            }

                            @Override
                            public void onFailure(Throwable t) {
                                showLongToast(R.string.net_business);
                                Log.d(TAG, t.getMessage());
                            }
                        });
                break;
            //点击图形验证
            case R.id.btn_forget_graph:
                showGraphMethod();
                break;
        }
    }

    void showGraphMethod() {
        HashMap<String, String> paramgraph = new HashMap<>();
        NetworkManager.getApiService().validateCode(paramgraph).subscribeOn(Schedulers.io())
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
                            if (!isFirstGraph) {
                                showLongToast("获取图形验证码成功");
                            }
                            isFirstGraph = false;
                        } else {
                            showLongToast(userCommonBean.getMsg());
                        }
                        ImageUtils.loadImageUrl(mIvCode, userCommonBean.getData().getFilePath(), ForgetPassWordActivity.this);
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
    }
}
