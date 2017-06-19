package com.dasheng.papa.mvp.home.child;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.widget.Toast;

import com.dasheng.papa.R;
import com.dasheng.papa.adapter.HomeCategoryAdapter;
import com.dasheng.papa.base.BaseFragment;
import com.dasheng.papa.base.OnItemClickListener;
import com.dasheng.papa.bean.ApiBean;
import com.dasheng.papa.databinding.FragmentHomeCategoryBinding;
import com.dasheng.papa.util.Constant;

import timber.log.Timber;

public class HomeCategoryFragment extends BaseFragment<FragmentHomeCategoryBinding> {

    private boolean isFirst;
    private HomeCategoryAdapter homeCategoryAdapter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        String title = bundle.getString(Constant.Intent_Extra.HOME_CATEGORY_TYPE);
        Timber.d("onActivityCreated: %s  %b", title, isFirst);
        initView();
        initEvent();
    }

    private void initView() {
        initRecyclerView();
        initSwipeRefreshLayout();
    }

    private void initSwipeRefreshLayout() {
        binding.swipe.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
    }

    private void initRecyclerView() {
        binding.recycler.setLayoutManager(new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false));
        homeCategoryAdapter = new HomeCategoryAdapter();
        binding.recycler.setAdapter(homeCategoryAdapter);
        homeCategoryAdapter.setOnItemClickListener(new OnItemClickListener<ApiBean>() {
            @Override
            public void onClick(ApiBean apiBean, int position) {
                Toast.makeText(HomeCategoryFragment.this.getActivity(), "click" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initEvent() {
        homeCategoryAdapter.add(new ApiBean());
        homeCategoryAdapter.add(new ApiBean());
        homeCategoryAdapter.add(new ApiBean());
        homeCategoryAdapter.add(new ApiBean());
        homeCategoryAdapter.add(new ApiBean());
        homeCategoryAdapter.add(new ApiBean());
        homeCategoryAdapter.add(new ApiBean());
        homeCategoryAdapter.add(new ApiBean());
        homeCategoryAdapter.add(new ApiBean());
        homeCategoryAdapter.add(new ApiBean());
        homeCategoryAdapter.add(new ApiBean());
        homeCategoryAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        Timber.d("onFragmentVisibleChange: %b", isVisible);
        if (isVisible) {

        } else {
            if (binding.swipe.isRefreshing()) {
                binding.swipe.setRefreshing(false);
            }
        }
    }

    @Override
    protected void onFragmentFirstVisible() {
        Timber.d("onFragmentFirstVisible");
        isFirst = true;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_home_category;
    }
}
