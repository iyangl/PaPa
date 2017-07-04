package com.dasheng.papa.adapter;

import android.databinding.DataBindingUtil;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dasheng.papa.R;
import com.dasheng.papa.base.OnItemClickListener;
import com.dasheng.papa.databinding.ItemBeautyListBinding;
import com.github.chrisbanes.photoview.OnOutsidePhotoTapListener;
import com.github.chrisbanes.photoview.OnPhotoTapListener;

import java.util.ArrayList;
import java.util.List;

public class BeautyListPagerAdapter extends PagerAdapter {
    private List<String> data;
    private OnItemClickListener<String> listener;
    private SparseArray<View> mCacheView;
    private int mChildCount;

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
            binding.beautyPic.setOnPhotoTapListener(new OnPhotoTapListener() {
                @Override
                public void onPhotoTap(ImageView view, float x, float y) {
                    if (listener != null) {
                        listener.onClick(null, 0);
                    }
                }
            });
            binding.beautyPic.setOnOutsidePhotoTapListener(new OnOutsidePhotoTapListener() {
                @Override
                public void onOutsidePhotoTap(ImageView imageView) {
                    if (listener != null) {
                        listener.onClick(null, 0);
                    }
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

    @Override
    public int getItemPosition(Object object) {
        if (mChildCount > 0) {
            mChildCount--;
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
    }

    @Override
    public void notifyDataSetChanged() {
        mChildCount = getCount();
        super.notifyDataSetChanged();
    }

    public void addAll(List<String> data) {
        if (data == null || data.size() == 0) {
            return;
        }
        clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public void add(String object) {
        data.add(object);
    }

    public void clear() {
        data.clear();
        mCacheView.clear();
        notifyDataSetChanged();
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

    public String getItem(int position) {
        return position < getCount() ? data.get(position) : "";
    }
}
