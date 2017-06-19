package com.dasheng.papa.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dasheng.papa.R;
import com.dasheng.papa.base.BaseRecyclerViewAdapter;
import com.dasheng.papa.base.BaseRecyclerViewHolder;
import com.dasheng.papa.bean.CategoryBean;
import com.dasheng.papa.databinding.ItemCategoryDivideBinding;
import com.dasheng.papa.databinding.ItemCategoryGridBinding;

public class CategoryAdapter extends BaseRecyclerViewAdapter<CategoryBean> implements View.OnClickListener {
    private static final int TYPE_GRID = 0xffff04;
    private static final int TYPE_DIVIDER = 0xffff05;

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_GRID) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_grid, null, false);
            view.setOnClickListener(this);
            return new CategoryGridViewHolder(view);
        }
        if (viewType == TYPE_DIVIDER) {
            View divider = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_divide, null, false);
            return new DividerViewHolder(divider);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 7) {
            return TYPE_DIVIDER;
        }
        return TYPE_GRID;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) manager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position) == TYPE_DIVIDER ? gridLayoutManager.getSpanCount() : 1;
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onClick(null, (int) v.getTag());
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////
    class CategoryGridViewHolder extends BaseRecyclerViewHolder<CategoryBean, ItemCategoryGridBinding> {

        public CategoryGridViewHolder(View view) {
            super(view);
        }

        @Override
        public void onBindViewHolder(CategoryBean categoryBean, int position) {
            binding.setCategory(categoryBean);
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////
    class DividerViewHolder extends BaseRecyclerViewHolder<CategoryBean, ItemCategoryDivideBinding> {

        public DividerViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindViewHolder(CategoryBean object, int position) {

        }
    }
}
