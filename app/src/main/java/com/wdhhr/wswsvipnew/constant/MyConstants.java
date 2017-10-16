package com.wdhhr.wswsvipnew.constant;

import android.os.Environment;

/**
 * Created by felear on 2017/8/26 0026.
 */

public class MyConstants {

    // 收益相关
    public static final String KEY_PROFIT_MODE = "key_profit_mode";
    public static final int MODE_PROFIT_PENDDING = 0;
    public static final int MODE_PROFIT_TODAY = 1;
    public static final int MODE_PROFIT_TOTAL = 2;

    //二维码和apk等默认保存位置
    public static final String PIC_DEFAULT_DIR = Environment.getExternalStorageDirectory() + "/wswsvipnew";
    //付费版APP下载地址
    public static final String VIP_UPLOAD_URL = "http://www.woshi53.com/upload/softWare/WSVIP.apk";

    //分享店铺
    public static final int SHARE_SHOP = 0;
    //分享商品
    public static final int SHARE_GOODS = 1;
    //分享品牌
    public static final int SHARE_BRAND = 2;


    //权限请求码

    //从相册选择图片
    public static final int REQUEST_WRITE_PHOTO = 0;
    //打开相机拍照
    public static final int REQUEST_WRITE_CAMERA = 1;
    //保存二维码到本地
    public static final int REQUEST_WRITE_CODE = 2;
    //下载并安装应用
    public static final int REQUEST_INSTALL_APP = 3;


}
