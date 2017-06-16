package com.dasheng.papa.mvp.category;

import com.dasheng.papa.R;
import com.dasheng.papa.base.BaseFragment;
import com.dasheng.papa.databinding.FragmentCategoryBinding;

import timber.log.Timber;

public class CategoryFragment extends BaseFragment<FragmentCategoryBinding> {


    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        Timber.d("onFragmentVisibleChange: %b", isVisible);
    }

    @Override
    protected void onFragmentFirstVisible() {
        Timber.d("onFragmentFirstVisible");
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_category;
    }
}
