package com.dasheng.papa.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dasheng.papa.R;
import com.dasheng.papa.base.BaseRecyclerViewAdapter;
import com.dasheng.papa.base.BaseRecyclerViewHolder;
import com.dasheng.papa.bean.ImgBean;
import com.dasheng.papa.databinding.ItemBeautyPicsBinding;

import java.util.List;

public class BeautyPicAdapter extends BaseRecyclerViewAdapter<ImgBean.ImginfoBean> implements View.OnClickListener {
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

    public void addImg(List<ImgBean.ImginfoBean> imginfoBeen, boolean isClear) {
        if (isClear) {
            data.clear();
        }
        data.addAll(imginfoBeen);
        notifyDataSetChanged();
    }

    public void addImg(List<ImgBean.ImginfoBean> imginfoBeen) {
        addImg(imginfoBeen, false);
    }


    ///////////////////////////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////////////
    class BeautyPicViewHolder extends BaseRecyclerViewHolder<ImgBean.ImginfoBean, ItemBeautyPicsBinding> {

        public BeautyPicViewHolder(View view) {
            super(view);
        }

        @Override
        public void onBindViewHolder(ImgBean.ImginfoBean imginfoBean, int position) {
            binding.setBeauty(imginfoBean);
        }
    }
}
