package com.dasheng.papa.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.dasheng.papa.R;
import com.dasheng.papa.base.BaseRecyclerViewAdapter;
import com.dasheng.papa.base.BaseRecyclerViewHolder;
import com.dasheng.papa.bean.ApiBean;
import com.dasheng.papa.databinding.ItemCategoryDetailBinding;
import com.dasheng.papa.databinding.ItemVideoHeadBinding;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class VideioDetailAdapter extends BaseRecyclerViewAdapter<ApiBean> {
    public static final int TYPE_HEAD = 0xffff06;
    public static final int TYPE_LIST = 0xffff07;

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEAD) {
            View head = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video_head, null, false);
            return new VideoHeadViewHolder(head);
        }
        if (viewType == TYPE_LIST) {
            View list = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_detail, null, false);
            return new CategoryDetailViewHolder(list);
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEAD;
        }
        return TYPE_LIST;
    }

    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    class VideoHeadViewHolder extends BaseRecyclerViewHolder<ApiBean, ItemVideoHeadBinding> {

        public VideoHeadViewHolder(View view) {
            super(view);
        }

        @Override
        public void onBindViewHolder(ApiBean object, int position) {
            binding.player.setUp("http://2449.vod.myqcloud.com/2449_22ca37a6ea9011e5acaaf51d105342e3.f20.mp4"
                    , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, "嫂子闭眼睛");
            Glide.with(binding.player.getContext())
                    .load("http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640")
                    .centerCrop()
                    .into(binding.player.thumbImageView);
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////
    class CategoryDetailViewHolder extends BaseRecyclerViewHolder<ApiBean, ItemCategoryDetailBinding> {

        public CategoryDetailViewHolder(View view) {
            super(view);
        }

        @Override
        public void onBindViewHolder(ApiBean object, int position) {

        }
    }
}
