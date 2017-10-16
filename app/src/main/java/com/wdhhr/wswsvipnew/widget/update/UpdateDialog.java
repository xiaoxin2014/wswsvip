package com.wdhhr.wswsvipnew.widget.update;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;

import com.wdhhr.wswsvipnew.R;

import static com.wdhhr.wswsvipnew.constant.MyConstants.REQUEST_INSTALL_APP;

public class UpdateDialog {


    private static AlertDialog mDialog;
    private static boolean isDown;

    static void show(final Context context, String content, final String downloadUrl, final int isUpdate) {
        if (isContextValid(context)) {
            isDown = false;
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(R.string.android_auto_update_dialog_title);
            builder.setMessage(content)
                    .setPositiveButton(R.string.android_auto_update_dialog_btn_download, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            if (isUpdate == 1) {
                                // 不可关闭
                                keepDialogOpen(mDialog);
                            }
                            if (!isDown) {
                                if (Build.VERSION.SDK_INT >= 23) {
                                    //判断是否有这个权限
                                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                        //2、申请权限: 参数二：权限的数组；参数三：请求码
                                        ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_INSTALL_APP);
                                    } else {
                                        goToDownload(context, downloadUrl);
                                    }
                                } else {
                                    goToDownload(context, downloadUrl);
                                }

                                isDown = true;
                            }
                        }
                    });

            if (isUpdate != 1) {
                builder.setNegativeButton(R.string.android_auto_update_dialog_btn_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
            }

            mDialog = builder.create();
            //点击对话框外面,对话框不消失
            mDialog.setCanceledOnTouchOutside(false);
            // 返回键不可关闭
            mDialog.setOnKeyListener(keylistener);
            mDialog.show();
        }
    }

    //保持dialog不关闭的方法
    private static void keepDialogOpen(AlertDialog dialog) {
        try {
            java.lang.reflect.Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
            field.setAccessible(true);
            field.set(dialog, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static DialogInterface.OnKeyListener keylistener = new DialogInterface.OnKeyListener() {
        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            return keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0;
        }
    };

    private static boolean isContextValid(Context context) {
        return context instanceof Activity && !((Activity) context).isFinishing();
    }


    public static void goToDownload(Context context, String downloadUrl) {

        Intent intent = new Intent(context.getApplicationContext(), DownloadService.class);
        intent.putExtra(Constants.APK_DOWNLOAD_URL, downloadUrl);
        context.startService(intent);
    }
}
