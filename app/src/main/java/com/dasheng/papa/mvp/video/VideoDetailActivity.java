package com.dasheng.papa.mvp.video;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.dasheng.papa.R;
import com.dasheng.papa.adapter.VideoDetailAdapter;
import com.dasheng.papa.base.BaseActivity;
import com.dasheng.papa.base.OnItemClickListener;
import com.dasheng.papa.bean.ApiBean;
import com.dasheng.papa.bean.ApiSingleResBean;
import com.dasheng.papa.bean.ResponseItemBean;
import com.dasheng.papa.bean.VideoDetailBean;
import com.dasheng.papa.databinding.ActivityVideoDetailBinding;
import com.dasheng.papa.mvp.article.ArticleDetailActivity;
import com.dasheng.papa.util.Constant;
import com.dasheng.papa.util.ImageLoader;
import com.dasheng.papa.util.ToastUtil;
import com.dasheng.papa.util.UrlUtils;
import com.dasheng.papa.widget.DividerItemDecoration;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;
import timber.log.Timber;

public class VideoDetailActivity extends BaseActivity<ActivityVideoDetailBinding>
        implements VideoDetailContact.View, View.OnClickListener {

    private VideoDetailAdapter videoDetailAdapter;
    private ResponseItemBean responseItemBean;
    private int mId;
    private VideoDetailPresenter videoDetailPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);
        setNavigationIcon();
    }

    @Override
    protected void initView() {
        responseItemBean = (ResponseItemBean) getIntent()
                .getSerializableExtra(Constant.Intent_Extra.VIDEO_DETAIL_INFO);
        if (responseItemBean != null) {
            mId = Integer.parseInt(responseItemBean.getId());
        }
        initRecyclerView();
        binding.player.player.setUp("", JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "");
    }

    private void initJCVideoPlayer() {
        binding.player.setVideo(responseItemBean);
        binding.player.player.setUp(UrlUtils.formatUrl(responseItemBean.getContent())
                , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "");
        ImageLoader.loadImage(this, responseItemBean.getImg(), binding.player.player.thumbImageView);
    }

    private void initRecyclerView() {
        binding.recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        videoDetailAdapter = new VideoDetailAdapter();
        binding.recycler.setAdapter(videoDetailAdapter);
        binding.recycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST, 1));
    }

    @Override
    protected void initEvent() {
        videoDetailPresenter = new VideoDetailPresenter(this);
        videoDetailPresenter.refresh(mId);
        videoDetailAdapter.setOnItemClickListener(new OnItemClickListener<ResponseItemBean>() {
            @Override
            public void onClick(ResponseItemBean responseItemBean, int position) {
                if (TextUtils.isEmpty(responseItemBean.getContent())) {
                    Intent intent = new Intent(VideoDetailActivity.this, ArticleDetailActivity.class);
                    intent.putExtra(Constant.Intent_Extra.VIDEO_DETAIL_INFO, responseItemBean);
                    VideoDetailActivity.this.startActivity(intent);
                } else {
                    videoDetailPresenter.refresh(Integer.parseInt(responseItemBean.getId()));
                }
            }
        });

        binding.player.praiseAdd.setOnClickListener(this);
        binding.player.praiseDel.setOnClickListener(this);
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

    @Override
    public void onShowLoading() {

    }

    @Override
    public void onLoadingDismiss() {

    }

    @Override
    public void onRefreshSuccess(ApiSingleResBean<VideoDetailBean> apiBean) {
        if (apiBean.getRes().getHead() != null && apiBean.getRes().getHead().size() > 0) {
            responseItemBean = apiBean.getRes().getHead().get(0);
            videoDetailAdapter.addItems(apiBean.getRes().getFoot());
            initJCVideoPlayer();
            setTitle(responseItemBean.getTitle());
        }
    }

    @Override
    public void onZanSuccess(ApiBean apiSingleResBean, String status) {
        Timber.d("onZanSuccess: %s  status : %s", apiSingleResBean.getMsg(), status);
        ToastUtil.show(this, apiSingleResBean.getMsg());
        if (Constant.Api.ZAN_STATUS_ADD.equals(status)) {
            binding.player.praiseCountAdd.setText(
                    Integer.parseInt(binding.player.praiseCountAdd.getText().toString().trim()) + 1 + "");
        } else if (Constant.Api.ZAN_STATUS_DEL.equals(status)) {
            binding.player.praiseCountDel.setText(
                    Integer.parseInt(binding.player.praiseCountDel.getText().toString().trim()) + 1 + "");
        }
    }

    @Override
    public void onError(Throwable e) {
        Timber.d("onError: %s", e.getMessage());
        ToastUtil.show(this, e.getMessage());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.praise_add:
                int i1 = Integer.parseInt(binding.player.praiseCountAdd.getText().toString().trim());
                videoDetailPresenter.zan(String.valueOf(mId), Constant.Api.ZAN_STATUS_ADD);
                break;
            case R.id.praise_del:
                int i = Integer.parseInt(binding.player.praiseCountDel.getText().toString().trim());
                videoDetailPresenter.zan(String.valueOf(mId), Constant.Api.ZAN_STATUS_DEL);
                break;
        }
    }
}
