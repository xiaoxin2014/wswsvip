package com.wdhhr.wswsvipnew.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.wdhhr.wswsvipnew.MyApplication;
import com.wdhhr.wswsvipnew.R;
import com.wdhhr.wswsvipnew.base.BaseActivity;
import com.wdhhr.wswsvipnew.constant.MyConstants;
import com.wdhhr.wswsvipnew.model.dataBase.GoodsListBean;
import com.wdhhr.wswsvipnew.model.dataBase.UsersBean;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Hashtable;

import static android.R.attr.path;
import static android.R.attr.tag;
import static com.wdhhr.wswsvipnew.constant.MyConstants.PIC_DEFAULT_DIR;
import static com.wdhhr.wswsvipnew.constant.MyConstants.REQUEST_WRITE_CODE;

/**
 * Created by Administrator on 2017/9/3 0003.
 */

public class ShareUtils {

    private static PopupWindow sPw_sharebord;
    private static UMWeb sWeb;
    private static Activity sContex;
    private static String surl;
    private static int mQR_width;
    private static int mQR_height;
    private static PopupWindow mAlphaPw;
    private static int stag;
    private static GoodsListBean sBean;
    private static final String TAG = "ShareUtils";
    public static Bitmap sBitmap;

    //缩略图为url时
    public static void ShowShareBord(int tag, Activity context, String url, String title, String desc, String thumbUrl, GoodsListBean bean) {

        mQR_width = context.getResources().getDimensionPixelSize(R.dimen.qr_width);
        mQR_height = context.getResources().getDimensionPixelSize(R.dimen.qr_height);
        sContex = context;
        surl = url;
        stag = tag;
        sBean = bean;
        UsersBean userInfo = MyApplication.getUserInfo();
        sWeb = new UMWeb(url);
        sWeb.setTitle(title);//标题
        sWeb.setDescription(desc);//描述
        if (!TextUtils.isEmpty(thumbUrl)) {
            sWeb.setThumb(new UMImage(context, ImageUtils.transformUrl(thumbUrl)));//缩略图
        }


        //弹出分享面板
        switch (tag) {
            case MyConstants.SHARE_SHOP:
                sPw_sharebord = WindowUtils.getAlphaPw(context, R.layout.pw_sharebord);
                sPw_sharebord.getContentView().findViewById(R.id.layout_code).setVisibility(View.VISIBLE);
                break;
            case MyConstants.SHARE_GOODS:
                sPw_sharebord = WindowUtils.getAlphaPw(context, R.layout.pw_sharebord_goods);
                ((TextView) sPw_sharebord.getContentView().findViewById(R.id.tv_earn_num)).setText("赚" + bean.getEarn());
                ((TextView) sPw_sharebord.getContentView().findViewById(R.id.tv_share_content)).setText(Html.fromHtml(((BaseActivity) sContex).getStrFormat(R.string.share_content, bean.getEarn())));
                break;
            case MyConstants.SHARE_BRAND:
                sPw_sharebord = WindowUtils.getAlphaPw(context, R.layout.pw_sharebord);
                sPw_sharebord.getContentView().findViewById(R.id.layout_code).setVisibility(View.GONE);
                break;
        }

        View contentView = sPw_sharebord.getContentView();
        contentView.findViewById(R.id.bt_wechat).setOnClickListener(mOnclikListener);
        contentView.findViewById(R.id.bt_wxcircle).setOnClickListener(mOnclikListener);
        contentView.findViewById(R.id.bt_qq).setOnClickListener(mOnclikListener);
        contentView.findViewById(R.id.bt_sina).setOnClickListener(mOnclikListener);
        contentView.findViewById(R.id.bt_goods_code).setOnClickListener(mOnclikListener);
        contentView.findViewById(R.id.tv_share_cancle).setOnClickListener(mOnclikListener);
        WindowUtils.setWindowAlpha(context, 0.6f);
        sPw_sharebord.showAtLocation(context.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }

    //缩略图为资源id时
    public static void ShowShareBord(Activity context, String url, String title, String desc, int thumbUrl, GoodsListBean bean) {

        mQR_width = context.getResources().getDimensionPixelSize(R.dimen.qr_width);
        mQR_height = context.getResources().getDimensionPixelSize(R.dimen.qr_height);
        sContex = context;
        surl = url;
        stag = tag;
        sBean = bean;
        UsersBean userInfo = MyApplication.getUserInfo();
        sWeb = new UMWeb(url);
        sWeb.setTitle(title);//标题
        sWeb.setDescription(desc);//描述
        if (thumbUrl != 0) {
            sWeb.setThumb(new UMImage(context, thumbUrl));//缩略图
        }


        //弹出分享面板
        WindowUtils.setWindowAlpha(context, 0.6f);
        sPw_sharebord = WindowUtils.getAlphaPw(context, R.layout.pw_sharebord);
        sPw_sharebord.getContentView().findViewById(R.id.layout_code).setVisibility(View.GONE);

        View contentView = sPw_sharebord.getContentView();
        contentView.findViewById(R.id.bt_wechat).setOnClickListener(mOnclikListener);
        contentView.findViewById(R.id.bt_wxcircle).setOnClickListener(mOnclikListener);
        contentView.findViewById(R.id.bt_qq).setOnClickListener(mOnclikListener);
        contentView.findViewById(R.id.bt_sina).setOnClickListener(mOnclikListener);
        contentView.findViewById(R.id.bt_goods_code).setOnClickListener(mOnclikListener);
        contentView.findViewById(R.id.tv_share_cancle).setOnClickListener(mOnclikListener);
        sPw_sharebord.showAtLocation(context.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }


    static View.OnClickListener mOnclikListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bt_wechat:
                    WindowUtils.closePW(sPw_sharebord);
                    new ShareAction(sContex).setPlatform(SHARE_MEDIA.WEIXIN)
                            .withMedia(sWeb)
                            .share();
                    break;
                case R.id.bt_wxcircle:
                    WindowUtils.closePW(sPw_sharebord);
                    new ShareAction(sContex).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                            .withMedia(sWeb)
                            .share();
                    break;
                case R.id.bt_qq:
                    WindowUtils.closePW(sPw_sharebord);
                    new ShareAction(sContex).setPlatform(SHARE_MEDIA.QQ)
                            .withMedia(sWeb)
                            .share();
                    break;
                case R.id.bt_sina:
                    WindowUtils.closePW(sPw_sharebord);
                    new ShareAction(sContex).setPlatform(SHARE_MEDIA.SINA)
                            .withMedia(sWeb)
                            .share();
                    break;
                case R.id.bt_goods_code:
                    switch (stag) {
                        case MyConstants.SHARE_SHOP:
                            WindowUtils.closePW(sPw_sharebord);
                            mAlphaPw = WindowUtils.getAlphaPw(sContex, R.layout.pw_two_dimension_code);
                            View contentView = mAlphaPw.getContentView();
                            ((TextView) contentView.findViewById(R.id.tv_shop_name)).setText(MyApplication.shopInfo.getBusinessName());
                            ((TextView) contentView.findViewById(R.id.tv_business_contact)).setText(MyApplication.shopInfo.getBusinessContactName() + sContex.getResources().getString(R.string.business_name_after));
                            createImage(surl, ((ImageView) contentView.findViewById(R.id.iv_two_dimension_code)), mQR_width, mQR_height);
                            ImageUtils.loadCircleImageUrl(((ImageView) contentView.findViewById(R.id.iv_shop_pic)), MyApplication.shopInfo.getBusinessPic(), R.mipmap.icon_head, sContex);
                            contentView.findViewById(R.id.tv_save_code).setOnClickListener(mOnclikListener);
                            contentView.findViewById(R.id.iv_share_close).setOnClickListener(mOnclikListener);
                            WindowUtils.setWindowAlpha(sContex, 0.6f);
                            mAlphaPw.showAtLocation(sContex.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
                            break;
                        case MyConstants.SHARE_GOODS:
                            WindowUtils.closePW(sPw_sharebord);
                            mAlphaPw = WindowUtils.getAlphaPw(sContex, R.layout.pw_goods_two_dimension_code);
                            View goodsContentView = mAlphaPw.getContentView();
                            ((TextView) goodsContentView.findViewById(R.id.tv_goods_info)).setText(sBean.getGoodsName());
                            String[] split = sBean.getGoodsPic().split(",");
                            if (split.length > 0) {
                                ImageUtils.loadCenterCropImageUrl(((ImageView) goodsContentView.findViewById(R.id.iv_goods_pic)), split[0], R.mipmap.defalut_bg, sContex);
                            }
                            ((TextView) goodsContentView.findViewById(R.id.tv_price)).setText("¥" + sBean.getGoodsPrice());
                            ((TextView) goodsContentView.findViewById(R.id.tv_goods_desc)).setText(sBean.getDescribe());
                            goodsContentView.findViewById(R.id.tv_close).setOnClickListener(mOnclikListener);
                            goodsContentView.findViewById(R.id.tv_save_code).setOnClickListener(mOnclikListener);
                            createImage(surl, ((ImageView) goodsContentView.findViewById(R.id.iv_two_dimension_code)), sContex.getResources().getDimensionPixelSize(R.dimen.qr_width), sContex.getResources().getDimensionPixelSize(R.dimen.qr_height));
                            WindowUtils.setWindowAlpha(sContex, 0.6f);
                            mAlphaPw.showAtLocation(sContex.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
                            break;
                    }

                    break;
                //取消分享
                case R.id.tv_share_cancle:
                    WindowUtils.closePW(sPw_sharebord);
                    break;
                //关闭店铺分享二维码popwindow
                case R.id.iv_share_close:
                    WindowUtils.closePW(mAlphaPw);
                    break;
                //关闭商品分享二维码popwindow
                case R.id.tv_close:
                    WindowUtils.closePW(mAlphaPw);
                    break;
                //保存二维码到相册
                case R.id.tv_save_code:
                    View codeview = mAlphaPw.getContentView().findViewById(R.id.layout_top);
                    savePicToGallery(sContex, getViewToImage(codeview));
                    WindowUtils.closePW(mAlphaPw);
                    break;
            }
        }
    };

    //保存图片到相册
    public static void savePicToGallery(Activity mcontext, Bitmap bitmap) {
        //保存二维码到本地相册
        if (Build.VERSION.SDK_INT >= 23) {
            //判断是否有这个权限
            if (ContextCompat.checkSelfPermission(mcontext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                //2、申请权限: 参数二：权限的数组；参数三：请求码(传的是哪个context就在哪里回调)
                ActivityCompat.requestPermissions(mcontext, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_CODE);
            } else {
                compressPic(mcontext, bitmap);
            }
        } else {
            compressPic(mcontext, bitmap);
        }

    }

    //把图片缓存至本地，并通知图库更新
    public static void compressPic(Activity mContext, Bitmap bitmap) {
        // 首先保存图片
        File appDir = new File(PIC_DEFAULT_DIR);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        final String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            //把图片缓存至本地
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            //把图片插入系统图库
            MediaStore.Images.Media.insertImage(mContext.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 最后通知图库更新
        mContext.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));
        ((BaseActivity) mContext).showLongToast("图片已保存至" + file.getAbsolutePath());
    }

    //根据字符串生成二维码bitmap,并设置到指定的imageView
    public static void createImage(String codeFormat, ImageView codeImg, int QR_WIDTH, int QR_HEIGHT) {
        try {
            if (codeFormat == null || "".equals(codeFormat) || codeFormat.length() < 1) {
                return;
            }
            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            BitMatrix bitMatrix = new QRCodeWriter().encode(codeFormat, BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
            int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
            for (int y = 0; y < QR_HEIGHT; y++) {
                for (int x = 0; x < QR_WIDTH; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * QR_WIDTH + x] = 0xff000000;
                    } else {
                        pixels[y * QR_WIDTH + x] = 0xffffffff;
                    }
                }
            }
            sBitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT, Bitmap.Config.ARGB_8888);
            sBitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);
            codeImg.setImageBitmap(sBitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    //将View的内容映射成Bitmap
    public static Bitmap getViewToImage(View view) {
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.destroyDrawingCache();
        view.setDrawingCacheEnabled(false);
        return bitmap;
    }

}