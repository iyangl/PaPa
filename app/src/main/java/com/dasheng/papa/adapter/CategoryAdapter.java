package com.dasheng.papa.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dasheng.papa.R;
import com.dasheng.papa.base.BaseRecyclerViewAdapter;
import com.dasheng.papa.base.BaseRecyclerViewHolder;
import com.dasheng.papa.bean.ResponseItemBean;
import com.dasheng.papa.databinding.ItemCategoryDivideBinding;
import com.dasheng.papa.databinding.ItemCategoryGridBinding;

import java.util.List;

public class CategoryAdapter extends BaseRecyclerViewAdapter<ResponseItemBean> implements View.OnClickListener {
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
        if (data.get(position) == null) {
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
            int postion = (int) v.getTag();
            listener.onClick(postion == data.size() ? data.get(postion - 1) : data.get(postion), postion);
        }
    }

    public void addItemList(List<ResponseItemBean> list) {
        if (list == null || list.size() == 0) {
            return;
        }
        data.clear();
        data.addAll(list);
        data.add(null);
        ResponseItemBean bean = new ResponseItemBean();
        bean.setName("关注公众号");
        data.add(bean);
        notifyDataSetChanged();
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////
    class CategoryGridViewHolder extends BaseRecyclerViewHolder<ResponseItemBean, ItemCategoryGridBinding> {

        public CategoryGridViewHolder(View view) {
            super(view);
        }

        @Override
        public void onBindViewHolder(ResponseItemBean categoryBean, int position) {
            binding.setCategory(categoryBean);
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////////////////////////
    class DividerViewHolder extends BaseRecyclerViewHolder<ResponseItemBean, ItemCategoryDivideBinding> {

        public DividerViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindViewHolder(ResponseItemBean object, int position) {

        }
    }
}
