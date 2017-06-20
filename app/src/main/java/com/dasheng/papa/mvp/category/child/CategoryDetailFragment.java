package com.dasheng.papa.mvp.category.child;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;

import com.dasheng.papa.R;
import com.dasheng.papa.adapter.CategoryDetailAdapter;
import com.dasheng.papa.base.BaseFragment;
import com.dasheng.papa.base.OnItemClickListener;
import com.dasheng.papa.bean.ApiBean;
import com.dasheng.papa.databinding.FragmentCategoryDetailBinding;
import com.dasheng.papa.mvp.MainActivity;
import com.dasheng.papa.util.Constant;

import timber.log.Timber;

public class CategoryDetailFragment extends BaseFragment<FragmentCategoryDetailBinding> {

    private CategoryDetailAdapter categoryDetailAdapter;
    private MainActivity mainActivity;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
        initEvent();
    }

    private void initView() {
        initRecyclerView();
        mainActivity = (MainActivity) getActivity();
        String title = getArguments().getString(Constant.Intent_Extra.CATEGORY_TYPE);
        mainActivity.setTitle(title);
        mainActivity.setNavigationIcon();
    }

    private void initRecyclerView() {
        binding.recycler.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));
        categoryDetailAdapter = new CategoryDetailAdapter();
        binding.recycler.setAdapter(categoryDetailAdapter);
        binding.recycler.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        categoryDetailAdapter.setOnItemClickListener(new OnItemClickListener<ApiBean>() {
            @Override
            public void onClick(ApiBean apiBean, int position) {

            }
        });
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
        Timber.d("onFragmentVisibleChange, %b", isVisible);
    }

    @Override
    protected void onFragmentFirstVisible() {

    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_category_detail;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mainActivity.hideNavigationIcon();
    }

}
