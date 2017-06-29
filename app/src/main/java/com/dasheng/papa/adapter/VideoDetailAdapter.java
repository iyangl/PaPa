package com.dasheng.papa.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dasheng.papa.R;
import com.dasheng.papa.base.BaseRecyclerViewAdapter;
import com.dasheng.papa.base.BaseRecyclerViewHolder;
import com.dasheng.papa.bean.ResponseItemBean;
import com.dasheng.papa.databinding.ItemCategoryDetailBinding;
import com.dasheng.papa.databinding.ItemVideoListHeadBinding;

import java.util.List;

public class VideoDetailAdapter extends BaseRecyclerViewAdapter<ResponseItemBean> implements View.OnClickListener {
    public static final int TYPE_HEAD = 0xffff06;
    public static final int TYPE_LIST = 0xffff07;

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEAD) {
            View head = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video_list_head, null, false);
            return new VideoHeadViewHolder(head);
        }
        if (viewType == TYPE_LIST) {
            View list = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_detail, null, false);
            list.setOnClickListener(this);
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

    public void addItems(List<ResponseItemBean> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        data.add(new ResponseItemBean());
        data.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            int position = (int) v.getTag();
            if (data.size() > position && data.get(position) != null) {
                listener.onClick(data.get(position), position);
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    class VideoHeadViewHolder extends BaseRecyclerViewHolder<ResponseItemBean, ItemVideoListHeadBinding> {

        public VideoHeadViewHolder(View view) {
            super(view);
        }

        @Override
        public void onBindViewHolder(ResponseItemBean object, int position) {

        }
    }

    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////
    class CategoryDetailViewHolder extends BaseRecyclerViewHolder<ResponseItemBean, ItemCategoryDetailBinding> {

        public CategoryDetailViewHolder(View view) {
            super(view);
        }

        @Override
        public void onBindViewHolder(ResponseItemBean object, int position) {
            binding.setRank(object);
        }
    }
}
