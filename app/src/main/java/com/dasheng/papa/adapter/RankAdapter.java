package com.dasheng.papa.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dasheng.papa.R;
import com.dasheng.papa.base.BaseRecyclerViewAdapter;
import com.dasheng.papa.base.BaseRecyclerViewHolder;
import com.dasheng.papa.bean.ResponseItemBean;
import com.dasheng.papa.databinding.ItemCategoryDetailBinding;

import java.util.List;

public class RankAdapter extends BaseRecyclerViewAdapter<ResponseItemBean> implements View.OnClickListener {

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_category_detail, null, false);
        view.setOnClickListener(this);
        return new CategoryDetailViewHolder(view);
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            int position = (int) v.getTag();
            listener.onClick(data.get(position), position);
        }
    }

    public void addRankItem(List<ResponseItemBean> list, boolean isClear) {
        if (isClear) {
            data.clear();
        }
        data.addAll(list);
        notifyDataSetChanged();
    }

    public void addRankItem(List<ResponseItemBean> list) {
        addRankItem(list, false);
    }

    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
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
