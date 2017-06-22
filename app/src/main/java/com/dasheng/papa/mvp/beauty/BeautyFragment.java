package com.dasheng.papa.mvp.beauty;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;

import com.dasheng.papa.R;
import com.dasheng.papa.adapter.BeautyPicAdapter;
import com.dasheng.papa.base.BaseFragment;
import com.dasheng.papa.base.OnItemClickListener;
import com.dasheng.papa.bean.ApiBean;
import com.dasheng.papa.databinding.FragmentBeautyBinding;
import com.dasheng.papa.mvp.beauty.child.BeautyListActivity;
import com.dasheng.papa.util.ToastUtil;

import timber.log.Timber;

public class BeautyFragment extends BaseFragment<FragmentBeautyBinding> {

    private BeautyPicAdapter beautyPicAdapter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
        initData();
    }

    private void initView() {
        initRecyclerView();
    }

    private void initRecyclerView() {
        binding.recycler.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        binding.recycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        beautyPicAdapter = new BeautyPicAdapter();
        beautyPicAdapter.add(new ApiBean());
        beautyPicAdapter.add(new ApiBean());
        beautyPicAdapter.add(new ApiBean());
        beautyPicAdapter.add(new ApiBean());
        beautyPicAdapter.add(new ApiBean());
        beautyPicAdapter.add(new ApiBean());
        beautyPicAdapter.add(new ApiBean());
        beautyPicAdapter.add(new ApiBean());
        beautyPicAdapter.add(new ApiBean());
        binding.recycler.setAdapter(beautyPicAdapter);
    }

    private void initData() {
        beautyPicAdapter.setOnItemClickListener(new OnItemClickListener<ApiBean>() {
            @Override
            public void onClick(ApiBean apiBean, int position) {
                ToastUtil.show(getActivity(), position + "");
                getActivity().startActivity(new Intent(getActivity(), BeautyListActivity.class));
            }
        });
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
