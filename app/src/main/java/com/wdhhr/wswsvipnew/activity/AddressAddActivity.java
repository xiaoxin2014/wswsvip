package com.wdhhr.wswsvipnew.activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.gson.Gson;
import com.wdhhr.wswsvipnew.R;
import com.wdhhr.wswsvipnew.base.BaseActivity;
import com.wdhhr.wswsvipnew.base.OnSelTipsPwSureListener;
import com.wdhhr.wswsvipnew.constant.EventConstants;
import com.wdhhr.wswsvipnew.model.UserCommonBean;
import com.wdhhr.wswsvipnew.model.dataBase.AddressListBean;
import com.wdhhr.wswsvipnew.model.dataBase.CityBean;
import com.wdhhr.wswsvipnew.net.NetworkManager;
import com.wdhhr.wswsvipnew.net.ApiSubscriberCallBack;
import com.wdhhr.wswsvipnew.utils.LocalUtils;
import com.wdhhr.wswsvipnew.utils.NetworkUtils;
import com.wdhhr.wswsvipnew.utils.StringUtils;
import com.wdhhr.wswsvipnew.utils.WindowUtils;
import com.wdhhr.wswsvipnew.widget.wheelview.LocalPopupWindow;

import org.simple.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class AddressAddActivity extends BaseActivity {
    private static final String TAG = "AddressAddActivity";
    String ischeck = "0";
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.tv_back)
    TextView mTvBack;
    @BindView(R.id.iv_more)
    ImageView mIvMore;
    TextView mTvRight;
    @BindView(R.id.et_address_user)
    EditText mEtAddressUser;
    @BindView(R.id.et_address_phone)
    EditText mEtAddressPhone;
    @BindView(R.id.tv_add_city)
    TextView mTvAddCity;
    @BindView(R.id.tv_delete)
    TextView mTvDelete;
    @BindView(R.id.et_address_details)
    EditText mEtAddressDetails;
    @BindView(R.id.address_slidebutton)
    ToggleButton mAddressSlidebutton;
    @BindView(R.id.et_address_default)
    RelativeLayout mEtAddressDefault;
    private int mFlag;
    private LocalPopupWindow mPwLocal;
    private AddressListBean mAddress;
    private List<CityBean> mLocalBeanList;
    private boolean isFirst;
    private String mstrName;
    private String mstrPhone;
    private String mstrAddress;
    private String mstrDetailAddress;

    @Override
    protected int setViewId() {
        return R.layout.activity_address_add;
    }

    @Override
    protected void init() {
        mTvBack.setVisibility(View.VISIBLE);
        mTvRight = ((TextView) findViewById(R.id.tv_right));
        mTvRight.setText("保存");
        mTvRight.setEnabled(false);
        mTvRight.setTextColor(getResources().getColor(R.color.soft_white));
        mTvRight.setVisibility(View.VISIBLE);
        mIvMore.setVisibility(View.INVISIBLE);
        mEtAddressUser.setSelection(mEtAddressUser.getText().length());

        String json = getIntent().getExtras().getString("json");

        if (json != null) {
            mAddress = new Gson().fromJson(json, AddressListBean.class);
        }

        // 初始化地址选择弹出窗
        mPwLocal = new LocalPopupWindow(AddressAddActivity.this);
        View view = LayoutInflater.from(AddressAddActivity.this).inflate(R.layout.pw_local_header, (ViewGroup) mPwLocal.getContentView(), false);
        mPwLocal.addHeaderView(view);

    }

    @Override
    protected void initEvent() {
        mAddressSlidebutton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //选中
                    ischeck = "1";
                } else {
                    //未选中
                    ischeck = "0";
                }
            }
        });// 添加监听事件


        mEtAddressUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    mstrName = s + "";
                } else {
                    mstrName = null;
                }
                setBtnSaveStatus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEtAddressPhone.addTextChangedListener(new TextWatcher() {
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
                setBtnSaveStatus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEtAddressDetails.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    mstrDetailAddress = s + "";
                } else {
                    mstrDetailAddress = null;
                }
                setBtnSaveStatus();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setBtnSaveStatus() {
        Log.d(TAG, "setBtnSaveStatus: " + mstrName + " : " + mstrPhone + " : " + mstrAddress + " : " + mstrDetailAddress);
        if (mstrName != null && mstrPhone != null
                && mstrAddress != null && mstrDetailAddress != null) {
            mTvRight.setEnabled(true);
            mTvRight.setTextColor(getResources().getColor(R.color.white));
        } else {
            mTvRight.setEnabled(false);
            mTvRight.setTextColor(getResources().getColor(R.color.soft_white));
        }
    }

    @Override
    protected void loadData() {
        mFlag = getIntent().getExtras().getInt("flag");
        isFirst = getIntent().getExtras().getBoolean("first", false);
        if (mFlag == 0) {
            // 初始化值
            mEtAddressUser.setText(mAddress.getName());
            mstrName = mAddress.getName();
            mEtAddressPhone.setText(mAddress.getPhone());
            mstrPhone = mAddress.getPhone();
            mEtAddressDetails.setText(mAddress.getAddressDesc());
            mstrDetailAddress = mAddress.getAddressDesc();
            mTitle.setText(R.string.address_edit);
            if (mAddress.getIsDefualt() == 0) {
                mAddressSlidebutton.setChecked(false);
            } else {
                mAddressSlidebutton.setChecked(true);
                mAddressSlidebutton.setEnabled(false);
                mTvDelete.setVisibility(View.GONE);
            }

            mPwLocal.setData(LocalUtils.getLocalList(), mAddress.getProvice(), mAddress.getCity(),
                    mAddress.getArea());
            mTvAddCity.setText(mPwLocal.getLocalName());
            mstrAddress = mPwLocal.getLocalName();
            mTvAddCity.setTextColor(getResources().getColor(R.color.color_text02));
        } else {
            mTitle.setText(R.string.address_create);
            mPwLocal.setData(LocalUtils.getLocalList());
            mTvDelete.setVisibility(View.GONE);
            if (isFirst) {
                mAddressSlidebutton.setChecked(true);
                mAddressSlidebutton.setEnabled(false);
            }
        }

        setBtnSaveStatus();

        mLocalBeanList = mPwLocal.getLocalBean();
    }

    //保存新建后的地址
    void saveNewAddress(Map<String, String> map) {
        showLoadPw();
        NetworkManager.getApiService().addAddress(map).subscribeOn(Schedulers.io())
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
                        if (UserCommonBean.getStatus() == 0) {
                            EventBus.getDefault().post(mFlag, EventConstants.UPDATE_ADDRESS);
                            finish();
                        } else {
                            showLongToast(UserCommonBean.getMsg());
                        }
                        dismissLoadPw();
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        if (NetworkUtils.isOnline()) {
                            showLongToast(R.string.service_error);
                        } else {
                            showLongToast(R.string.net_connect_error);
                        }
                        dismissLoadPw();
                    }
                });
    }

    //保存修改后的地址
    void saveEditAddress(Map<String, String> map) {
        showLoadPw();
        NetworkManager.getApiService().updateAddress(map)
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
                        if (UserCommonBean.getStatus() == 0) {
                            EventBus.getDefault().post(0, EventConstants.UPDATE_ADDRESS);
                            readyGoThenKill(AddressGoodsActivity.class);
                        } else {
                            showLongToast(UserCommonBean.getMsg());
                        }
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
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_right:
                String phone = mEtAddressPhone.getText().toString();
                if (!StringUtils.isMobileNumber(phone)) {
                    showLongToast("请输入合法的手机号码");
                    return;
                }
                Map<String, String> map = new HashMap<>();
                map.put("provice", mLocalBeanList.get(0).getAREA_ID() + "");
                map.put("city", mLocalBeanList.get(1).getAREA_ID() + "");
                map.put("area", mLocalBeanList.get(2).getAREA_ID() + "");

                if (mAddressSlidebutton.isChecked()) {
                    map.put("isDefualt", "1");
                } else {
                    map.put("isDefualt", "0");
                }

                map.put("name", mstrName);
                map.put("phone", mstrPhone);
                map.put("addressDesc", mstrDetailAddress);
                if (mFlag == 0) {
                    //保存编辑地址
                    map.put("addressId", mAddress.getAddress_id());
                    saveEditAddress(map);
                } else {
                    //保存新建地址
                    saveNewAddress(map);
                }

                break;
            //选择省市区
            case R.id.tv_add_city:
                WindowUtils.setWindowAlpha(this, 0.6f);
                mPwLocal.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
                mPwLocal.refresh();
                break;
            case R.id.tv_pw_confirm:
                // 地区选择确认
                WindowUtils.closePW(mPwLocal);
                mTvAddCity.setText(mPwLocal.getLocalName());
                mstrAddress = mPwLocal.getLocalName();
                mTvAddCity.setTextColor(getResources().getColor(R.color.color_text02));
                mLocalBeanList = mPwLocal.getLocalBean();
                setBtnSaveStatus();
                break;
            case R.id.tv_pw_cancel:
                WindowUtils.closePW(mPwLocal);
                break;
            case R.id.tv_delete:
                showSelTipsPw(R.string.delete_tip, new OnSelTipsPwSureListener() {
                    @Override
                    public void onSure() {
                        showLoadPw();
                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("addressId", mAddress.getAddress_id());
                        NetworkManager.getApiService().deleteAddress(hashMap)
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
                                        EventBus.getDefault().post(mFlag, EventConstants.UPDATE_ADDRESS);
                                        dismissLoadPw();
                                        readyGoThenKill(AddressGoodsActivity.class);
                                    }

                                    @Override
                                    public void onFailure(Throwable t) {
                                        showLongToast("网络异常，请重试");
                                        dismissLoadPw();
                                    }
                                });
                    }
                });
                break;
        }
    }

}
