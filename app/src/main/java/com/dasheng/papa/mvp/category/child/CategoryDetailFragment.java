package com.dasheng.papa.mvp.category.child;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import com.dasheng.papa.R;
import com.dasheng.papa.adapter.CategoryDetailAdapter;
import com.dasheng.papa.base.BaseFragment;
import com.dasheng.papa.bean.ApiBean;
import com.dasheng.papa.databinding.FragmentCategoryDetailBinding;
import com.dasheng.papa.mvp.MainActivity;

public class CategoryDetailFragment extends BaseFragment<FragmentCategoryDetailBinding> {

    private CategoryDetailAdapter categoryDetailAdapter;
    private MainActivity mainActivity;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
    }

    private void initView() {
        initRecyclerView();
        mainActivity = (MainActivity) getActivity();
        mainActivity.setTitle("");
        mainActivity.setNavigationIcon();
    }

    private void initRecyclerView() {
        binding.recycler.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));
        categoryDetailAdapter = new CategoryDetailAdapter();
        binding.recycler.setAdapter(categoryDetailAdapter);
    }

    private void initEvent() {
        categoryDetailAdapter.add(new ApiBean());
        categoryDetailAdapter.add(new ApiBean());
        categoryDetailAdapter.add(new ApiBean());
        categoryDetailAdapter.add(new ApiBean());
        categoryDetailAdapter.add(new ApiBean());
        categoryDetailAdapter.add(new ApiBean());
        categoryDetailAdapter.add(new ApiBean());
        categoryDetailAdapter.add(new ApiBean());
        categoryDetailAdapter.add(new ApiBean());
        categoryDetailAdapter.add(new ApiBean());
        categoryDetailAdapter.add(new ApiBean());
        categoryDetailAdapter.add(new ApiBean());
        categoryDetailAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {

    }

    @Override
    protected void onFragmentFirstVisible() {
        initEvent();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_category_detail;
    }
}
