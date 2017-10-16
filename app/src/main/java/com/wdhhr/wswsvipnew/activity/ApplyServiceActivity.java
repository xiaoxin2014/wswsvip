package com.wdhhr.wswsvipnew.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wdhhr.wswsvipnew.R;
import com.wdhhr.wswsvipnew.adapter.RecyclerAdapter;
import com.wdhhr.wswsvipnew.adapter.RecyclerViewSpaceItemDecoration;
import com.wdhhr.wswsvipnew.base.BaseActivity;
import com.wdhhr.wswsvipnew.base.OnSelTipsPwSureListener;
import com.wdhhr.wswsvipnew.constant.EventConstants;
import com.wdhhr.wswsvipnew.constant.OrderConstants;
import com.wdhhr.wswsvipnew.dao.OrderDao;
import com.wdhhr.wswsvipnew.model.OrderCommonBean;
import com.wdhhr.wswsvipnew.model.UserCommonBean;
import com.wdhhr.wswsvipnew.model.dataBase.OrdersListBean;
import com.wdhhr.wswsvipnew.net.ApiSubscriberCallBack;
import com.wdhhr.wswsvipnew.net.NetworkManager;
import com.wdhhr.wswsvipnew.utils.DeviceUtils;
import com.wdhhr.wswsvipnew.utils.FileUtil;
import com.wdhhr.wswsvipnew.utils.ImageUtils;
import com.wdhhr.wswsvipnew.utils.ShareUtils;
import com.wdhhr.wswsvipnew.utils.WindowUtils;
import com.wdhhr.wswsvipnew.widget.ImageDetailPopupWindow;

import org.simple.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.FormBody;
import okhttp3.RequestBody;

import static com.wdhhr.wswsvipnew.constant.MyConstants.REQUEST_WRITE_CAMERA;
import static com.wdhhr.wswsvipnew.constant.MyConstants.REQUEST_WRITE_PHOTO;


public class ApplyServiceActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView mTvTitle;
    @BindView(R.id.tv_back)
    TextView mTvBack;
    @BindView(R.id.tv_right)
    TextView mTvRight;
    @BindView(R.id.tv_reason_length)
    TextView mTvReasonLength;
    @BindView(R.id.edit_reason)
    EditText mEditReason;
    @BindView(R.id.rcv_photo)
    RecyclerView mRcvPhoto;
    @BindView(R.id.tv_refund_fail_reason)
    TextView tvRefundFailReason;
    @BindView(R.id.tv_apply_service)
    TextView mTvApplyService;
    @BindView(R.id.layout_refund_fail_reason)
    LinearLayout layoutRefundFailReason;
    private String mstrReason = "";
    private List<Bitmap> mListPhoto;
    private List<String> mListUrl;
    private RecyclerAdapter<String> mRcvAdapter;
    private PopupWindow mAlphaPw;
    private int miCurPhoto;
    private ImageView mvCur;

    private static final String TAG = "ApplyServiceActivity";
    private OrdersListBean mOrdersListBean;
    private String mstrUrl;
    private int miHeigh;
    private ImageDetailPopupWindow mPwImageDetail;
    private PopupWindow mAppealPw;
    private boolean mIsBusiness;

    @Override
    protected int setViewId() {
        return R.layout.activity_apply_service;
    }

    @Override
    protected void init() {
        mTvBack.setVisibility(View.VISIBLE);

        mTvRight.setText(R.string.submit);
        mTvRight.setEnabled(false);

        String json = getIntent().getExtras().getString("json");
        mIsBusiness = getIntent().getExtras().getBoolean(OrderConstants.KEY_MODE_ORDER_BUSINESS, false);
        Gson gson = new Gson();
        mOrdersListBean = gson.fromJson(json, OrdersListBean.class);

        mListPhoto = new ArrayList<>();
        mListUrl = new ArrayList<>();

        miHeigh = (DeviceUtils.getScreenWdith() - DeviceUtils.dip2px(36)) / 4;

        mRcvAdapter = new RecyclerAdapter<String>(this, mListUrl, R.layout.item_photo_sel) {

            @Override
            public void convert(RecyclerAdapter.ViewHolder helper, final int position, String item) {

                ImageView imageView = (ImageView) helper.getView(R.id.imageView);
                ImageView ivDel = (ImageView) helper.getView(R.id.iv_photo_delete);
                TextView tvNum = (TextView) helper.getView(R.id.tv_img_num);
                tvNum.setVisibility(View.GONE);

                if (!mIsBusiness && mOrdersListBean.getOrderProcedure() != OrderConstants.MODE_ORDER_REFUNDING) {

                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            WindowUtils.setWindowAlpha(ApplyServiceActivity.this, 0.6f);
                            mAlphaPw.showAtLocation(ApplyServiceActivity.this.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
                            miCurPhoto = position;
                            mvCur = (ImageView) v;
                        }
                    });
                    ivDel.setVisibility(View.VISIBLE);
                    ivDel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mListPhoto.remove(position);
                            mListUrl.remove(position);
                            if (mListUrl.size() == mListPhoto.size()) {
                                mListUrl.add("");
                            }
                            mRcvAdapter.notifyDataSetChanged();
                        }
                    });
                } else {
                    ivDel.setVisibility(View.GONE);
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mPwImageDetail == null) {
                                mPwImageDetail = new ImageDetailPopupWindow(ApplyServiceActivity.this, mListUrl);
                            }
                            mPwImageDetail.show(position,false);
                        }
                    });

                }

                ViewGroup.LayoutParams layoutParams = helper.getConvertView().getLayoutParams();
                layoutParams.height = miHeigh;
                helper.getConvertView().setLayoutParams(layoutParams);

                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                if (item.length() > 5) {
                    helper.setImageByUrl(R.id.imageView, item, ApplyServiceActivity.this);
                } else {
                    if (position < mListPhoto.size()) {
                        imageView.setImageBitmap(mListPhoto.get(position));
                    } else {
                        imageView.setImageResource(R.mipmap.apply_add);
                        imageView.setScaleType(ImageView.ScaleType.CENTER);
                        helper.setText(R.id.tv_img_num, position + 1 + "/5");
                        ivDel.setVisibility(View.GONE);
                        tvNum.setVisibility(View.VISIBLE);
                    }
                }

            }
        };

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false);
        mRcvPhoto.setLayoutManager(gridLayoutManager);
        mRcvPhoto.addItemDecoration(new RecyclerViewSpaceItemDecoration(DeviceUtils.dip2px(4), 4));
        mRcvPhoto.setAdapter(mRcvAdapter);

        mAlphaPw = WindowUtils.getAlphaPw(this, R.layout.pw_selectphoto);
    }


    @Override
    protected void initEvent() {
        mEditReason.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    mstrReason = s.toString();
                    if (mOrdersListBean.getOrderProcedure() == OrderConstants.MODE_ORDER_REFUND_FAIL) {
                        mTvApplyService.setEnabled(true);
                    } else {
                        mTvRight.setEnabled(true);
                    }
                } else {
                    if (mOrdersListBean.getOrderProcedure() == OrderConstants.MODE_ORDER_REFUND_FAIL) {
                        mTvApplyService.setEnabled(true);
                    } else {
                        mTvRight.setEnabled(true);
                    }
                    mstrReason = null;
                }

                if (mstrReason == null) {

                    mTvReasonLength.setText(0 + "/150");
                } else {
                    mTvReasonLength.setText(mstrReason.length() + "/150");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void loadData() {
        mListUrl.clear();
        String refundPic = mOrdersListBean.getRefundPic();
        int orderProcedure = mOrdersListBean.getOrderProcedure();
        switch (orderProcedure) {
            case OrderConstants.MODE_ORDER_REFUNDING:
                if (refundPic.length() > 5) {
                    String[] split = refundPic.split(",");
                    for (int i = 0; i < split.length; i++) {
                        mListUrl.add(split[i]);
                    }

                }
                layoutRefundFailReason.setVisibility(View.GONE);
                mTvRight.setVisibility(View.GONE);
                mTvTitle.setText(R.string.apply_service_reason);
                mEditReason.setText(mOrdersListBean.getRefundDesc());
                mEditReason.setEnabled(false);
                break;
            case OrderConstants.MODE_ORDER_REFUND_FAIL:
                // 查看退款拒绝理由
                String[] split = refundPic.split(",");
                for (int i = 0; i < split.length; i++) {
                    mListUrl.add(split[i]);
                    mListPhoto.add(null);
                }
                if (mIsBusiness) {
                    mEditReason.setEnabled(false);
                    mTvApplyService.setVisibility(View.GONE);
                } else {
                    if (split.length < 5) {
                        mListUrl.add("");
                    }
                }
                layoutRefundFailReason.setVisibility(View.VISIBLE);
                tvRefundFailReason.setText(mOrdersListBean.getRefuseRefundDesc());
                mEditReason.setText(mOrdersListBean.getRefundDesc());
                mTvTitle.setText(R.string.order_refund_fail_reason);
                mTvRight.setVisibility(View.GONE);
                break;
            default:
                mListUrl.add("");
                layoutRefundFailReason.setVisibility(View.GONE);
                mTvRight.setVisibility(View.VISIBLE);
                mTvTitle.setText(R.string.apply_service);
                break;
        }

        mRcvAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_apply_service:
            case R.id.tv_right:
                // 提交
                showLoadPw();
                mstrUrl = "";
                for (int i = 0; i < mListPhoto.size(); i++) {
                    if (mListPhoto.get(i) == null) {
                        mstrUrl += mListUrl.get(i) + ",";
                    }
                }
                mListUrl.clear();
                uploadBitmap();
                break;
            case R.id.tv_camera:
                //相机
                if (Build.VERSION.SDK_INT >= 23) {
                    //判断是否有这个权限
                    if ((ContextCompat.checkSelfPermission(ApplyServiceActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(ApplyServiceActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)) {
                        //2、申请权限: 参数二：权限的数组；参数三：请求码
                        Log.v(TAG, "申请权限");
                        mAlphaPw.dismiss();
                        ActivityCompat.requestPermissions(ApplyServiceActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, REQUEST_WRITE_CAMERA);
                    } else {
                        mAlphaPw.dismiss();
                        openCamera(ApplyServiceActivity.this, 2);
                    }
                } else {
                    mAlphaPw.dismiss();
                    openCamera(ApplyServiceActivity.this, 2);
                }
                break;
            case R.id.tv_photo:
                //相册
                mAlphaPw.dismiss();
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, 1);
                break;
            case R.id.tv_cancle:
                // 取消
                mAlphaPw.dismiss();
                break;
            case R.id.tv_appeal:
                // 我要申诉
                if (mAppealPw == null) {
                    mAppealPw = WindowUtils.getAlphaPw(this, R.layout.pw_apply_service);
                    mAppealPw.getContentView().findViewById(R.id.tv_save).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // 点击保存客服微信二维码
                            Bitmap bitmap = ShareUtils.getViewToImage(mAppealPw.getContentView().findViewById(R.id.iv_appeal_code));
                            ShareUtils.savePicToGallery(ApplyServiceActivity.this, bitmap);
                            WindowUtils.closePW(mAppealPw);
                        }
                    });

                    mAppealPw.getContentView().findViewById(R.id.iv_appeal_code).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // 点击保存客服微信二维码
                            WindowUtils.closePW(mAppealPw);
                        }
                    });
                }
                WindowUtils.setWindowAlpha(ApplyServiceActivity.this, 0.6f);
                mAppealPw.showAtLocation(getWindow().getDecorView(), Gravity.CENTER, 0, 0);
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        Bitmap bitmap = null;
        switch (requestCode) {
            //相册
            case 1:
                bitmap = ImageUtils.getBitmap(data.getData(), this, 1500, 1500);
                break;
            case 2:
                //拍照
                bitmap = ImageUtils.getBitmap(FileUtil.getAppPath("user",FileUtil.CAMERA), 1500, 1500);
                break;
            default:
                return;
        }

        if (bitmap == null) {
            return;
        }

        WindowUtils.closePW(mAlphaPw);


        if (miCurPhoto < mListPhoto.size()) {
            mListPhoto.remove(miCurPhoto);
            mListUrl.remove(miCurPhoto);
            mListUrl.add(miCurPhoto, "");
        } else if (mListUrl.size() < 5) {
            mListUrl.add("");
        }

        mListPhoto.add(miCurPhoto, bitmap);
        mRcvAdapter.notifyDataSetChanged();
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void uploadBitmap() {
        if (mListPhoto.size() == 0) {
            // 修改订单状态
            HashMap<String, String> map = new HashMap<>();
            map.put("ordersId", mOrdersListBean.getOrdersId());
            map.put("refundDesc", mstrReason);
            if (mListUrl.size() > 0) {

                for (int i = 0; i < mListUrl.size(); i++) {
                    mstrUrl += mListUrl.get(i) + ",";
                }
                map.put("refundPic", mstrUrl.substring(0, mstrUrl.length() - 1));
            }
            map.put("orderProcedure", OrderConstants.MODE_ORDER_REFUNDING + "");
            OrderDao.updateOrder(map)
                    .subscribe(new ApiSubscriberCallBack<OrderCommonBean>() {
                        @Override
                        public void onSuccess(OrderCommonBean orderCommonBean) {
                            dismissLoadPw();
                            mOrdersListBean.setOrderProcedure(OrderConstants.MODE_ORDER_REFUNDING);
                            mOrdersListBean.setRefundPic(mstrUrl);
                            mOrdersListBean.setRefundDesc(mstrReason);

                            EventBus.getDefault().post(mOrdersListBean, EventConstants.ORDER_STATUS_CHANGE);
                            loadData();
                            showLongToast("提交成功");
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            dismissLoadPw();
                            showLongToast("提交失败，请检查网络");

                        }
                    });
            return;
        }
        Bitmap bitmap = mListPhoto.get(0);

        if (bitmap == null) {
            mListPhoto.remove(0);
            uploadBitmap();
            return;
        }

        String string = ImageUtils.bitmaptoString(bitmap);
        RequestBody body = new FormBody.Builder()
                .add("base64Str", string)
                .add("fileType", "afterSalesPic").build();
        NetworkManager.getApiService().uploadImg(body)
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
                        if (userCommonBean.getStatus() == 0) {
                            Bitmap remove = mListPhoto.remove(0);
                            mListUrl.add(userCommonBean.getData().getFilePath());
                            Log.d(TAG, "hahahah " + remove);
                            uploadBitmap();
                        } else {
                            dismissLoadPw();
                        }

                    }

                    @Override
                    public void onFailure(Throwable t) {
                        showLongToast(t.getMessage());
                        dismissLoadPw();
                        showLongToast("提交失败，请检查网络");
                    }
                });
    }

    //权限申请的回调
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_WRITE_PHOTO:
                //权限被拒绝
                if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                    showSelTipsPw(R.string.headicon_permission_denied, new OnSelTipsPwSureListener() {
                        @Override
                        public void onSure() {
                            Intent intent = getAppDetailSettingIntent(ApplyServiceActivity.this);
                            startActivity(intent);
                        }
                    });
                } else {
                    //权限被允许
                    mAlphaPw.dismiss();
                    openPhoto(1);
                }
                break;
            case REQUEST_WRITE_CAMERA:
                //权限被拒绝
                if (grantResults[0] == PackageManager.PERMISSION_DENIED || grantResults[1] == PackageManager.PERMISSION_DENIED) {
                    showSelTipsPw(R.string.headicon_permission_denied, new OnSelTipsPwSureListener() {
                        @Override
                        public void onSure() {
                            Intent intent = getAppDetailSettingIntent(ApplyServiceActivity.this);
                            startActivity(intent);
                        }
                    });
                } else {
                    //权限被允许
                    mAlphaPw.dismiss();
                    openCamera(ApplyServiceActivity.this, 2);
                }
                break;
        }
    }

}
