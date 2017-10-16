package com.wdhhr.wswsvipnew.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wdhhr.wswsvipnew.R;
import com.wdhhr.wswsvipnew.adapter.CommonAdapter;
import com.wdhhr.wswsvipnew.adapter.ViewHolder;
import com.wdhhr.wswsvipnew.base.BaseActivity;
import com.wdhhr.wswsvipnew.constant.EventConstants;
import com.wdhhr.wswsvipnew.model.UserCommonBean;
import com.wdhhr.wswsvipnew.model.dataBase.AddressListBean;
import com.wdhhr.wswsvipnew.net.NetworkManager;
import com.wdhhr.wswsvipnew.net.LoadStatusSubscriberCallBack;
import com.wdhhr.wswsvipnew.utils.LocalUtils;
import com.wdhhr.wswsvipnew.utils.LoadErrorUtils;

import org.simple.eventbus.EventBus;
import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class AddressGoodsActivity extends BaseActivity {
    @BindView(R.id.icon_title_left)
    ImageView iconTitleLeft;
    @BindView(R.id.title_content)
    TextView titleContent;
    @BindView(R.id.icon_text_right)
    TextView iconTextRight;
    @BindView(R.id.icon_title_right)
    ImageView iconTitleRight;
    @BindView(R.id.address_goods_list)
    ListView mXlvAddress;
    private static final String TAG = "AddressGoodsActivity";
    private CommonAdapter<AddressListBean> mAdapter;
    private int miMode;
    private LoadErrorUtils mXlvUtils;
    private List<AddressListBean> mAddressList = new ArrayList<>();

    @Override
    protected int setViewId() {
        return R.layout.activity_address_goods;
    }

    @Override
    protected void init() {
        iconTitleLeft.setImageResource(R.mipmap.title_back);
        titleContent.setText("收货地址");
        iconTitleRight.setVisibility(View.GONE);
        if (getIntent().getExtras() != null) {
            miMode = getIntent().getExtras().getInt("isSel", 0);
        }

        mAdapter = new CommonAdapter<AddressListBean>(this, mAddressList, R.layout.item_address_goods) {

            @Override
            public void convert(ViewHolder helper, int position, final AddressListBean item) {
                String localName = LocalUtils.getLocalName(item.getProvice(), item.getCity(), item.getArea(), "");
                helper.setText(R.id.name, item.getName());
                helper.setText(R.id.phone, item.getPhone());
                helper.setText(R.id.address, localName + item.getAddressDesc());
                TextView tvDefault = helper.getView(R.id.tv_default);
                if (item.getIsDefualt() == 1) {
                    tvDefault.setVisibility(View.VISIBLE);
                } else {
                    tvDefault.setVisibility(View.INVISIBLE);
                }

                //收货地址条目点击事件
                helper.getView(R.id.iv_edit).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putString("json", new Gson().toJson(item));
                        bundle.putInt("flag", 0);
                        readyGo(AddressAddActivity.class, bundle);
                    }
                });

                if (miMode == 1) {
                    helper.getConvertView().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            EventBus.getDefault().post(item, EventConstants.LOCATION_STATUS_SELECT);
                            finish();
                        }
                    });
                }
            }
        };
        mXlvAddress.setAdapter(mAdapter);
        mXlvUtils = new LoadErrorUtils(mXlvAddress, this, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });
    }

    @Override
    protected void initEvent() {
    }

    @Override
    protected void loadData() {
        showLoadPw();
        NetworkManager.getApiService().getAddressList()
                .subscribeOn(Schedulers.io())
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
                        mAddressList.clear();
                        if (userCommonBean.getData().getAddressList().size() == 0) {
                            Bundle bundle = new Bundle();
                            bundle.putInt("flag", 1);
                            bundle.putBoolean("first", true);
                            readyGo(AddressAddActivity.class, bundle);
                        }
                        mAddressList.addAll(userCommonBean.getData().getAddressList());
                    }

                    @Override
                    public int getListStatus(UserCommonBean userCommonBean) {
                        return userCommonBean.getStatus();
                    }

                    @Override
                    public List getList(UserCommonBean userCommonBean) {
                        return userCommonBean.getData().getAddressList();
                    }

                    @Override
                    public void onComplete() {
                        dismissLoadPw();
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }

    @Subscriber(tag = EventConstants.UPDATE_ADDRESS)
    void getAddressList(int flag) {
        loadData();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.address_create:
                Bundle bundle = new Bundle();
                bundle.putInt("flag", 1);
                readyGo(AddressAddActivity.class, bundle);
                break;

            case R.id.icon_title_left:
                finish();
                break;
        }
    }
}
