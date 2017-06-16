package com.dasheng.papa.mvp.beauty;

import com.dasheng.papa.R;
import com.dasheng.papa.base.BaseFragment;
import com.dasheng.papa.databinding.FragmentBeautyBinding;

import timber.log.Timber;

public class BeautyFragment extends BaseFragment<FragmentBeautyBinding> {


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
        return R.layout.fragment_beauty;
    }
}
