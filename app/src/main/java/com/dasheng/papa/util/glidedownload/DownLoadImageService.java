package com.dasheng.papa.util.glidedownload;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.dasheng.papa.util.Constant;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Note:
 * Author:leolee
 * Time:2017/5/9
 */
public class DownLoadImageService implements Runnable {
    private String url;
    private Context context;
    private ImageDownLoadCallBack callBack;
    private File currentFile;
    private Handler mHandler;
    private String imgName;

    public DownLoadImageService(Context context, String url, String imgName, ImageDownLoadCallBack callBack) {
        this.url = url;
        this.callBack = callBack;
        this.context = context;
        this.imgName = imgName;
        mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void run() {
        File file = null;
        Bitmap bitmap = null;
        try {
            //            file = Glide.with(context)
            //                    .load(url)
            //                    .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
            //                    .get();
            bitmap = Glide.with(context)
                    .load(url)
                    .asBitmap()
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
            if (bitmap != null) {
                // 在这里执行图片保存方法
                saveImageToGallery(context, bitmap);
            }
        } catch (final Exception e) {
            e.printStackTrace();
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    callBack.onDownLoadFailed(e);
                }
            });
        } finally {
            //            if (file != null) {
            //                callBack.onDownLoadSuccess(file);
            //            } else {
            //                callBack.onDownLoadFailed();
            //            }
            if (bitmap != null && currentFile.exists()) {
                final Bitmap finalBitmap = bitmap;
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onDownLoadSuccess(finalBitmap);
                    }
                });
            }
        }
    }

    public void saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsoluteFile();
        //注意小米手机必须这样获得public绝对路径
        String fileName = Constant.APP_NAME + "/pics";
        File appDir = new File(file.getParentFile(), fileName);
        if (!appDir.exists()) {
            appDir.mkdirs();
        }
        fileName = imgName + ".jpg";
        currentFile = new File(appDir, fileName);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(currentFile);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 其次把文件插入到系统图库
        //        try {
        //            MediaStore.Images.Media.insertImage(context.getContentResolver(),
        //                    currentFile.getAbsolutePath(), fileName, null);
        //        } catch (FileNotFoundException e) {
        //            e.printStackTrace();
        //        }

        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.fromFile(new File(currentFile.getPath()))));
    }
}