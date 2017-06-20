package com.dasheng.papa.mvp.rank;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.dasheng.papa.R;
import com.dasheng.papa.base.BaseFragment;
import com.dasheng.papa.databinding.FragmentRankBinding;

import timber.log.Timber;

public class RankFragment extends BaseFragment<FragmentRankBinding> {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
        initData();
    }

    private void initView() {
    }

    private void initData() {

    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        Timber.d("onFragmentVisibleChange: %b", isVisible);
        if (isVisible) {
            baseActivity.setTitle(R.string.rank_title);
        }
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
