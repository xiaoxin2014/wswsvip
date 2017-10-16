package com.wdhhr.wswsvipnew.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.wdhhr.wswsvipnew.R;
import com.wdhhr.wswsvipnew.constant.UrlConstants;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by Administrator on 2017/8/18.
 */

public class ImageUtils {

    private static String TAG = "ImageUtils";

    //将imageurl转换成完整的地址
    public static String transformUrl(String url) {

        if (url == null || url.length() == 0) {
            return url;
        }
        String newUrl = url;
        if (url.startsWith("/") && !url.startsWith("/data")) {
            newUrl = UrlConstants.IMG_BASE + url;
        }

        Log.d(TAG, newUrl);

        return newUrl;

    }

    // 普通带默认图标
    public static void loadImageUrl(ImageView imageView, String url, Context context) {
        Glide.with(context).
                load(transformUrl(url))
                .placeholder(R.mipmap.defalut_bg)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);
    }

    // 加载圆形且带默认图标
    public static void loadCircleImageUrl(ImageView imageView, String url, int placeId, Context context) {

        Glide.with(context)
                .load(transformUrl(url))
                .bitmapTransform(new CropCircleTransformation(context))
                .placeholder(placeId)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);
    }

    //加载图片并设置布局背景
    public static void loadBackgroundUrl(final RelativeLayout layout, String url, int placeId, Context context) {

        Glide.with(context).load(transformUrl(url)).asBitmap().placeholder(placeId).fitCenter().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                Drawable drawable = new BitmapDrawable(resource);
                layout.setBackground(drawable);
            }
        });
    }

    // 普通带默认图标
    public static void loadImageUrl(ImageView imageView, String url, int placeId, Context context) {
        Glide.with(context)
                .load(transformUrl(url))
                .placeholder(placeId)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);
    }

    // 普通带默认图标并且图片centerCrop
    public static void loadCenterCropImageUrl(ImageView imageView, String url, int placeId, Context context) {

        Glide.with(context)
                .load(transformUrl(url))
                .placeholder(placeId)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);
    }


    //base64位转码
    public static String bitmaptoString(Bitmap bitmap) {
        String string = null;
        ByteArrayOutputStream bStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 95, bStream);
        byte[] bytes = bStream.toByteArray();
        string = Base64.encodeToString(bytes, Base64.DEFAULT);
        return string;
    }

    /**
     * 根据路径压缩图片
     *
     * @param filePath   要加载的图片路径
     * @param destWidth  显示图片的控件宽度
     * @param destHeight 显示图片的控件的高度
     * @return
     */
    public static Bitmap getBitmap(String filePath, int destWidth, int destHeight) {
        //第一次采样
        BitmapFactory.Options options = new BitmapFactory.Options();
        //该属性设置为true只会加载图片的边框进来，并不会加载图片具体的像素点
        options.inJustDecodeBounds = true;
        //第一次加载图片，这时只会加载图片的边框进来，并不会加载图片中的像素点
        BitmapFactory.decodeFile(filePath, options);
        //获得原图的宽和高
        int outWidth = options.outWidth;
        int outHeight = options.outHeight;
        //定义缩放比例
        int sampleSize = 1;
        while (outHeight / sampleSize > destHeight || outWidth / sampleSize > destWidth) {
            //如果宽高的任意一方的缩放比例没有达到要求，都继续增大缩放比例
            //sampleSize应该为2的n次幂，如果给sampleSize设置的数字不是2的n次幂，那么系统会就近取值
            sampleSize *= 2;
        }
        /********************************************************************************************/
        //至此，第一次采样已经结束，我们已经成功的计算出了sampleSize的大小
        /********************************************************************************************/
        //二次采样开始
        //二次采样时我需要将图片加载出来显示，不能只加载图片的框架，因此inJustDecodeBounds属性要设置为false
        options.inJustDecodeBounds = false;
        //设置缩放比例
        options.inSampleSize = sampleSize;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        //加载图片并返回
        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * 根据Uri压缩图片
     *
     * @param uri        要加载的图片路径
     * @param destWidth  显示图片的控件宽度
     * @param destHeight 显示图片的控件的高度
     * @return
     */
    public static Bitmap getBitmap(Uri uri, Activity activity, int destWidth, int destHeight) {
        //第一次采样
        BitmapFactory.Options options = new BitmapFactory.Options();
        //该属性设置为true只会加载图片的边框进来，并不会加载图片具体的像素点
        options.inJustDecodeBounds = true;
        //第一次加载图片，这时只会加载图片的边框进来，并不会加载图片中的像素点
        InputStream input = null;
        try {
            input = activity.getContentResolver().openInputStream(uri);
            BitmapFactory.decodeStream(input, null, options);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {

            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //获得原图的宽和高
        int outWidth = options.outWidth;
        int outHeight = options.outHeight;
        //定义缩放比例
        int sampleSize = 1;
        while (outHeight / sampleSize > destHeight || outWidth / sampleSize > destWidth) {
            //如果宽高的任意一方的缩放比例没有达到要求，都继续增大缩放比例
            //sampleSize应该为2的n次幂，如果给sampleSize设置的数字不是2的n次幂，那么系统会就近取值
            sampleSize *= 2;
        }
        /********************************************************************************************/
        //至此，第一次采样已经结束，我们已经成功的计算出了sampleSize的大小
        /********************************************************************************************/
        //二次采样开始
        //二次采样时我需要将图片加载出来显示，不能只加载图片的框架，因此inJustDecodeBounds属性要设置为false
        options.inJustDecodeBounds = false;
        //设置缩放比例
        options.inSampleSize = sampleSize;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        //加载图片并返回
        Bitmap bitmap = null;
        try {
            input = activity.getContentResolver().openInputStream(uri);
            bitmap = BitmapFactory.decodeStream(input, null, options);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {

            try {
                input.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    /**
            * 自适应宽度加载图片。保持图片的长宽比例不变，通过修改imageView的高度来完全显示图片。
            */
    public static void loadIntoUseFitWidth(final ImageView imageView, final String imageUrl, final Context context) {
        Glide.with(context)
                .load(transformUrl(imageUrl))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        if (imageView == null) {
                            return false;
                        }
                        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                        ViewGroup.LayoutParams params = imageView.getLayoutParams();
                        int vw = imageView.getWidth() - imageView.getPaddingLeft() - imageView.getPaddingRight();
                        float scale = (float) vw / (float) resource.getIntrinsicWidth();
                        int vh = Math.round(resource.getIntrinsicHeight() * scale);
                        params.height = vh + imageView.getPaddingTop() + imageView.getPaddingBottom();
                        imageView.setLayoutParams(params);
                        Glide.with(context).load(transformUrl(imageUrl)).into(imageView);
                        return false;
                    }
                }).into(imageView);
    }
}
