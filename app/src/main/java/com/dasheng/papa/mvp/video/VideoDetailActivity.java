package com.dasheng.papa.mvp.video;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import com.dasheng.papa.R;
import com.dasheng.papa.adapter.VideioDetailAdapter;
import com.dasheng.papa.base.BaseActivity;
import com.dasheng.papa.bean.ApiBean;
import com.dasheng.papa.databinding.ActivityVideoDetailBinding;
import com.dasheng.papa.widget.DividerItemDecoration;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

public class VideoDetailActivity extends BaseActivity<ActivityVideoDetailBinding> {

    private VideioDetailAdapter videioDetailAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);
        setTitle("高考在即，唐僧神吐槽高考花式减压法！");
        setNavigationIcon();
    }

    @Override
    protected void initView() {
        initRecyclerView();
    }

    private void initRecyclerView() {
        binding.recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        videioDetailAdapter = new VideioDetailAdapter();
        binding.recycler.setAdapter(videioDetailAdapter);
        binding.recycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, 1));
    }

    @Override
    protected void initEvent() {
        videioDetailAdapter.add(new ApiBean());
        videioDetailAdapter.add(new ApiBean());
        videioDetailAdapter.add(new ApiBean());
        videioDetailAdapter.add(new ApiBean());
        videioDetailAdapter.add(new ApiBean());
        videioDetailAdapter.add(new ApiBean());
        videioDetailAdapter.add(new ApiBean());
        videioDetailAdapter.add(new ApiBean());
        videioDetailAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }
}
