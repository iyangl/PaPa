package com.dasheng.papa.mvp.weixin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.dasheng.papa.R;
import com.dasheng.papa.base.BaseActivity;
import com.dasheng.papa.databinding.ActivityWeixinBinding;
import com.dasheng.papa.util.Constant;
import com.dasheng.papa.util.PermissionUtil;
import com.dasheng.papa.util.ToastUtil;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class WeiXinActivity extends BaseActivity<ActivityWeixinBinding> {

    private RxPermissions mRxPermissions;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weixin);
    }

    @Override
    protected void initView() {
        setTitle("关注啪叽福利公众号");
        setNavigationIcon();
    }

    @Override
    protected void initEvent() {
        mRxPermissions = new RxPermissions(this);
        binding.image.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                new AlertDialog.Builder(WeiXinActivity.this)
                        .setItems(new String[]{"保存"}, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                PermissionUtil.externalStorage(new PermissionUtil.RequestPermission() {
                                    @Override
                                    public void onRequestPermissionSuccess() {
                                        saveWeiXinPic();
                                    }

                                    @Override
                                    public void onRequestPermissionFailed() {
                                        ToastUtil.show("未获取权限，保存失败！");
                                    }
                                }, mRxPermissions);
                            }
                        })
                        .show();
                return true;
            }
        });
    }

    private void saveWeiXinPic() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Resources res = WeiXinActivity.this.getResources();
                BitmapDrawable d = (BitmapDrawable) res.getDrawable(R.drawable.weixin_paji);
                Bitmap img = d.getBitmap();

                File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                        .getAbsoluteFile();
                //注意小米手机必须这样获得public绝对路径
                String fileName = Constant.APP_NAME;
                File appDir = new File(file.getParentFile(), fileName);
                if (!appDir.exists()) {
                    appDir.mkdirs();
                }
                fileName = "啪叽福利公众号.png";
                File imgFile = new File(appDir, fileName);
                try {
                    OutputStream os = new FileOutputStream(imgFile);
                    img.compress(Bitmap.CompressFormat.PNG, 100, os);
                    os.close();
                    new Handler(getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.show("保存成功");
                        }
                    });
                } catch (Exception e) {
                    Log.e("TAG", "", e);
                }
            }
        }).start();
    }


}
