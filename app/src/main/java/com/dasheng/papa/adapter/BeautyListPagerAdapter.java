package com.dasheng.papa.adapter;

import android.databinding.DataBindingUtil;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dasheng.papa.R;
import com.dasheng.papa.base.OnItemClickListener;
import com.dasheng.papa.databinding.ItemBeautyListBinding;

import java.util.ArrayList;
import java.util.List;

public class BeautyListPagerAdapter extends PagerAdapter {
    private List<String> data;
    private OnItemClickListener<String> listener;
    private SparseArray<View> mCacheView;

    public BeautyListPagerAdapter() {
        data = new ArrayList<>();
        mCacheView = new SparseArray<>();
    }

    public BeautyListPagerAdapter(List<String> data) {
        this.data = data;
        mCacheView = new SparseArray<>();
    }

    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View view = mCacheView.get(position);
        if (view == null) {
            ItemBeautyListBinding binding = DataBindingUtil.inflate(LayoutInflater.from(container.getContext()),
                    R.layout.item_beauty_list, null, false);
            binding.beautyPic.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (listener != null) {
                        listener.onClick(null, position);
                        return true;
                    }
                    return false;
                }
            });
            binding.setBeauty(data.get(position));
            view = binding.getRoot();
            mCacheView.append(position, view);
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    public void addAll(List<String> data) {
        if (data == null || data.size() == 0) {
            return;
        }
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public void add(String object) {
        data.add(object);
    }

    public void clear() {
        data.clear();
    }

    public void remove(String object) {
        data.remove(object);
    }

    public void remove(int position) {
        data.remove(position);
    }

    public void removeAll(List<String> data) {
        this.data.retainAll(data);
    }

    public void setOnItemClickListener(OnItemClickListener<String> listener) {
        this.listener = listener;
    }
}
