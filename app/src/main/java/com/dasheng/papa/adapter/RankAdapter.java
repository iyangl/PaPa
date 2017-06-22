package com.dasheng.papa.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dasheng.papa.R;
import com.dasheng.papa.base.BaseRecyclerViewAdapter;
import com.dasheng.papa.base.BaseRecyclerViewHolder;
import com.dasheng.papa.bean.ApiBean;
import com.dasheng.papa.databinding.ItemCategoryDetailBinding;

public class RankAdapter extends BaseRecyclerViewAdapter<ApiBean> implements View.OnClickListener {
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
            listener.onClick(null, (int) v.getTag());
        }
    }


    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    class CategoryDetailViewHolder extends BaseRecyclerViewHolder<ApiBean, ItemCategoryDetailBinding> {

        public CategoryDetailViewHolder(View view) {
            super(view);
        }

        @Override
        public void onBindViewHolder(ApiBean object, int position) {

        }
    }
}
