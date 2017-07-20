package com.dasheng.papa.mvp.article;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.dasheng.papa.R;
import com.dasheng.papa.databinding.ActivityWebImageBinding;
import com.dasheng.papa.util.CommonUtils;
import com.dasheng.papa.util.Constant;
import com.dasheng.papa.util.PermissionUtil;
import com.dasheng.papa.util.ToastUtil;
import com.dasheng.papa.util.glidedownload.DownLoadImageService;
import com.dasheng.papa.util.glidedownload.ImageDownLoadCallBack;
import com.tbruyelle.rxpermissions.RxPermissions;

import me.xiaopan.sketch.display.FadeInImageDisplayer;
import me.xiaopan.sketch.drawable.ImageAttrs;
import me.xiaopan.sketch.request.CancelCause;
import me.xiaopan.sketch.request.DisplayListener;
import me.xiaopan.sketch.request.DownloadProgressListener;
import me.xiaopan.sketch.request.ErrorCause;
import me.xiaopan.sketch.request.ImageFrom;
import timber.log.Timber;


public class ShowWebImageActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private String url;
    private RxPermissions mRxPermissions;
    private ActivityWebImageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_web_image);
        url = getIntent().getStringExtra(Constant.Intent_Extra.WEB_IMAGE_URL);
        binding.setUrl(url);

        mRxPermissions = new RxPermissions(this);
        toolbar = binding.imageToolbar;
        toolbar.setNavigationIcon(R.drawable.back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowWebImageActivity.this.finish();
            }
        });

        toolbar.inflateMenu(R.menu.toolbar_web_image_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                downImages(url);
                return false;
            }
        });
        initSketch();
    }

    private void initSketch() {
        binding.image.getOptions().setDecodeGifImage(true)
                .setImageDisplayer(new FadeInImageDisplayer());
        binding.image.setZoomEnabled(true);
        binding.image.setShowDownloadProgressEnabled(true);
        binding.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideOrShowIsQuestion();
            }
        });
        binding.image.setDownloadProgressListener(new DownloadProgressListener() {
            @Override
            public void onUpdateDownloadProgress(int totalLength, int completedLength) {
                binding.hint.setProgress(totalLength, completedLength);
            }
        });
        binding.image.setDisplayListener(new DisplayListener() {
            @Override
            public void onCompleted(Drawable drawable, ImageFrom imageFrom, ImageAttrs imageAttrs) {
                binding.hint.hidden();
            }

            @Override
            public void onStarted() {
                binding.hint.loading(null);
            }

            @Override
            public void onError(ErrorCause errorCause) {
                binding.hint.hint(R.drawable.ic_error, "图片显示失败", "重新显示", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        binding.setUrl(url);
                    }
                });
            }

            @Override
            public void onCanceled(CancelCause cancelCause) {
            }
        });
    }

    public void hideOrShowIsQuestion() {
        if (toolbar.getVisibility() == View.VISIBLE) {
            toolbar.setVisibility(View.GONE);
        } else {
            toolbar.setVisibility(View.VISIBLE);
        }
    }

    private void downImages(final String url) {
        PermissionUtil.externalStorage(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                savePic(url);
            }

            @Override
            public void onRequestPermissionFailed() {
                ToastUtil.show("未获取权限，保存失败！");
            }
        }, mRxPermissions);
    }

    private void savePic(String url) {
        new Thread(new DownLoadImageService(this, url,
                CommonUtils.getMD5Str(url), new ImageDownLoadCallBack() {

            @Override
            public void onDownLoadSuccess(Bitmap bitmap) {
                Timber.i("保存成功");
                ToastUtil.show("保存成功");
            }

            @Override
            public void onDownLoadFailed(Throwable e) {
                Timber.e(e.getMessage());
                ToastUtil.show("保存失败");
            }
        })).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
