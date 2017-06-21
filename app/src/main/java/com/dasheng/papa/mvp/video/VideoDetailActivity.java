package com.dasheng.papa.mvp.video;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.dasheng.papa.R;
import com.dasheng.papa.base.BaseActivity;
import com.dasheng.papa.databinding.ActivityVideoDetailBinding;

public class VideoDetailActivity extends BaseActivity<ActivityVideoDetailBinding> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initEvent() {

    }
}
