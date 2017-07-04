package com.dasheng.papa.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dasheng.papa.R;
import com.dasheng.papa.base.BaseRecyclerViewAdapter;
import com.dasheng.papa.base.BaseRecyclerViewHolder;
import com.dasheng.papa.databinding.ItemBeautyListBinding;
import com.github.chrisbanes.photoview.OnOutsidePhotoTapListener;
import com.github.chrisbanes.photoview.OnPhotoTapListener;

import java.util.List;

public class BeautyListAdapter extends BaseRecyclerViewAdapter<String> implements View.OnClickListener {
    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View beauty = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_beauty_list, null, false);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        beauty.setLayoutParams(layoutParams);
        beauty.setOnClickListener(this);
        return new BeautyListViewHolder(beauty);
    }

    public void addPics(List<String> pics) {
        data.clear();
        data.addAll(pics);
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onClick(null, (int) v.getTag());
        }
    }

    private class BeautyListViewHolder extends BaseRecyclerViewHolder<String, ItemBeautyListBinding> {

        BeautyListViewHolder(View view) {
            super(view);
        }

        @Override
        public void onBindViewHolder(String imgUrl, int position) {
            binding.setBeauty(imgUrl);
            binding.beautyPic.setOnPhotoTapListener(new OnPhotoTapListener() {
                @Override
                public void onPhotoTap(ImageView view, float x, float y) {
                    listener.onClick(null, 0);
                }
            });
            binding.beautyPic.setOnOutsidePhotoTapListener(new OnOutsidePhotoTapListener() {
                @Override
                public void onOutsidePhotoTap(ImageView imageView) {
                    listener.onClick(null, 0);
                }
            });
        }
    }
}
