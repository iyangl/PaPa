package com.dasheng.papa.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dasheng.papa.R;
import com.dasheng.papa.base.BaseRecyclerViewAdapter;
import com.dasheng.papa.base.BaseRecyclerViewHolder;
import com.dasheng.papa.databinding.ItemBeautyListBinding;

import java.util.List;

public class BeautyListAdapter extends BaseRecyclerViewAdapter<String> {
    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View beauty = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_beauty_list, null, false);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        beauty.setLayoutParams(layoutParams);
        return new BeautyListViewHolder(beauty);
    }

    public void addPics(List<String> pics) {
        data.clear();
        data.addAll(pics);
        notifyDataSetChanged();
    }

    private class BeautyListViewHolder extends BaseRecyclerViewHolder<String, ItemBeautyListBinding> {

        BeautyListViewHolder(View view) {
            super(view);
        }

        @Override
        public void onBindViewHolder(String imgUrl, int position) {
            binding.setBeauty(imgUrl);
        }
    }
}
