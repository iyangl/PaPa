package com.dasheng.papa.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dasheng.papa.R;
import com.dasheng.papa.base.BaseRecyclerViewAdapter;
import com.dasheng.papa.base.BaseRecyclerViewHolder;
import com.dasheng.papa.bean.RankItemBean;
import com.dasheng.papa.databinding.ItemCategoryDetailBinding;

import java.util.List;

public class RankAdapter extends BaseRecyclerViewAdapter<RankItemBean> implements View.OnClickListener {
    private List<RankItemBean> dayList;
    private List<RankItemBean> weekList;

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

    public void addRankItem(List<RankItemBean> list, int type_id, boolean isClear) {
        if (isClear) {
            if (type_id == 1) {
                dayList.addAll(list);
            } else if (type_id == 2) {
                weekList.addAll(list);
            }
        }
    }

    public void addRankItem(List<RankItemBean> list, int type_id) {
        addRankItem(list, type_id, false);
    }

    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    class CategoryDetailViewHolder extends BaseRecyclerViewHolder<RankItemBean, ItemCategoryDetailBinding> {

        public CategoryDetailViewHolder(View view) {
            super(view);
        }

        @Override
        public void onBindViewHolder(RankItemBean object, int position) {

        }
    }
}
