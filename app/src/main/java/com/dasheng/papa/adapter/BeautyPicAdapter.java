package com.dasheng.papa.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dasheng.papa.R;
import com.dasheng.papa.base.BaseRecyclerViewAdapter;
import com.dasheng.papa.base.BaseRecyclerViewHolder;
import com.dasheng.papa.bean.ApiBean;
import com.dasheng.papa.databinding.ItemBeautyPicsBinding;

public class BeautyPicAdapter extends BaseRecyclerViewAdapter<ApiBean> implements View.OnClickListener {
    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View pic = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_beauty_pics, null, false);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        pic.setLayoutParams(layoutParams);
        pic.setOnClickListener(this);
        return new BeautyPicViewHolder(pic);
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onClick(null, (int) v.getTag());
        }
    }


    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    class BeautyPicViewHolder extends BaseRecyclerViewHolder<ApiBean, ItemBeautyPicsBinding> {

        public BeautyPicViewHolder(View view) {
            super(view);
        }

        @Override
        public void onBindViewHolder(ApiBean object, int position) {

        }
    }
}
