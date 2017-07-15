package com.dasheng.papa.mvp.article;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.dasheng.papa.R;
import com.dasheng.papa.databinding.ActivityWebImageBinding;
import com.dasheng.papa.util.CommonUtils;
import com.dasheng.papa.util.Constant;
import com.dasheng.papa.util.PermissionUtil;
import com.dasheng.papa.util.ToastUtil;
import com.dasheng.papa.util.glidedownload.DownLoadImageService;
import com.dasheng.papa.util.glidedownload.ImageDownLoadCallBack;
import com.github.chrisbanes.photoview.OnOutsidePhotoTapListener;
import com.github.chrisbanes.photoview.OnPhotoTapListener;
import com.tbruyelle.rxpermissions.RxPermissions;

import timber.log.Timber;


public class ShowWebImageActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private String url;
    private RxPermissions mRxPermissions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityWebImageBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_web_image);
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

        binding.image.setOnPhotoTapListener(new OnPhotoTapListener() {
            @Override
            public void onPhotoTap(ImageView view, float x, float y) {
                hideOrShowIsQuesttion();
            }
        });
        binding.image.setOnOutsidePhotoTapListener(new OnOutsidePhotoTapListener() {
            @Override
            public void onOutsidePhotoTap(ImageView imageView) {
                hideOrShowIsQuesttion();
            }
        });
    }

    public void hideOrShowIsQuesttion() {
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
}
