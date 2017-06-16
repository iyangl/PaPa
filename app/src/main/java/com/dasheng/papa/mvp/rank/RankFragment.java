package com.dasheng.papa.mvp.rank;

import com.dasheng.papa.R;
import com.dasheng.papa.base.BaseFragment;
import com.dasheng.papa.databinding.FragmentRankBinding;

import timber.log.Timber;

public class RankFragment extends BaseFragment<FragmentRankBinding> {


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
        return R.layout.fragment_rank;
    }
}
