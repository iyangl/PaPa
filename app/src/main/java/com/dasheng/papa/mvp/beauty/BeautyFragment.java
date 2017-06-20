package com.dasheng.papa.mvp.beauty;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.dasheng.papa.R;
import com.dasheng.papa.base.BaseFragment;
import com.dasheng.papa.databinding.FragmentBeautyBinding;

import timber.log.Timber;

public class BeautyFragment extends BaseFragment<FragmentBeautyBinding> {

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
            baseActivity.setTitle(R.string.beauty_title);
        }
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
