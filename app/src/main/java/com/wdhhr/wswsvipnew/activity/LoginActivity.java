package com.wdhhr.wswsvipnew.activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.wdhhr.wswsvipnew.MyApplication;
import com.wdhhr.wswsvipnew.R;
import com.wdhhr.wswsvipnew.base.BaseActivity;
import com.wdhhr.wswsvipnew.constant.EventConstants;
import com.wdhhr.wswsvipnew.constant.UserContants;
import com.wdhhr.wswsvipnew.dao.UserDao;
import com.wdhhr.wswsvipnew.utils.StatusBarUtil;
import com.wdhhr.wswsvipnew.utils.SystemUtil;

import org.simple.eventbus.Subscriber;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/8/14.
 */

public class LoginActivity extends BaseActivity {
    @BindView(R.id.et_account)
    EditText etLoginPhone;
    @BindView(R.id.et_pwd)
    EditText etLoginPwd;
    @BindView(R.id.btn_send)
    TextView mBtnSend;
    @BindView(R.id.cb_agreement)
    CheckBox cbAgreement;
    @BindView(R.id.tv_agreement)
    TextView tvAgreement;
    @BindView(R.id.btn_register)
    TextView mBtnRegister;

    private String mstrAccount;
    private String mstrPwd;
    private String TAG = LoginActivity.class.getSimpleName();

    @Override
    protected int setViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected void init() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //修改状态栏为亮色模式
        StatusBarUtil.StatusBarLightMode(this);
        mstrAccount = SystemUtil.getSharedString(UserContants.userAccount);
        mstrPwd = SystemUtil.getSharedString(UserContants.userPwd);
        if (mstrPwd != null) {
            etLoginPwd.setText(mstrPwd);
            etLoginPwd.setSelection(etLoginPwd.getText().length());
        }
        etLoginPhone.setText(mstrAccount);
        etLoginPhone.setSelection(etLoginPhone.getText().length());
        Log.d(TAG, mstrAccount + " " + mstrPwd);
        setBtnSendStatus();
    }

    @Override
    protected void initEvent() {

        cbAgreement.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setBtnSendStatus();
            }
        });

        etLoginPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0)
                    mstrAccount = s.toString();
                else
                    mstrAccount = null;
            }

            @Override
            public void afterTextChanged(Editable s) {
                setBtnSendStatus();
            }
        });

        etLoginPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    mstrPwd = s.toString();
                } else {
                    mstrPwd = null;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                setBtnSendStatus();
            }
        });



    }

    @Override
    protected void loadData() {

    }

    @Subscriber(tag = EventConstants.LOG_STATUS_CHANGE)
    private void loginStatusChange(int num) {
        dismissLoadPw();
        if (num == 0 && MyApplication.getUserInfo() != null) {
            readyGoThenKill(MainActivity.class);
        } else if (num == -72) {
//            showSelTipsPw(R.string.user_up_level_tip, new OnSelTipsPwSureListener() {
//                @Override
//                public void onSure() {
//                    Bundle bundle = new Bundle();
//                    bundle.putString("account", mstrAccount);
//                    bundle.putString("pwd", mstrPwd);
//                    readyGo(UpLevelActivity.class, bundle);
//                }
//            });
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_send:
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                showLoadPw();
                UserDao.login(mstrAccount, mstrPwd, this);
                break;
            case R.id.tv_forget_pwd:
                readyGo(ForgetPassWordActivity.class);
                break;
            case R.id.tv_agreement:
                readyGo(PlatfromProtocolActivity.class);
                break;
            case R.id.tv_back:
                finish();
                break;
            case R.id.btn_register:
                readyGo(RegistActivity.class);
                break;
        }
    }

    private void setBtnSendStatus() {
        if (mstrAccount != null && mstrPwd != null && cbAgreement.isChecked()) {
            mBtnSend.setEnabled(true);
        } else {
            mBtnSend.setEnabled(false);
        }
    }
}
