package com.dasheng.papa.mvp.rank;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.dasheng.papa.R;
import com.dasheng.papa.adapter.RankAdapter;
import com.dasheng.papa.base.BaseFragment;
import com.dasheng.papa.base.headandfoot.HeaderAndFooterWrapper;
import com.dasheng.papa.bean.ApiBean;
import com.dasheng.papa.databinding.FragmentRankBinding;
import com.dasheng.papa.databinding.ItemRankTitleBinding;
import com.dasheng.papa.util.ToastUtil;
import com.dasheng.papa.widget.DividerItemDecoration;

import timber.log.Timber;

import static com.dasheng.papa.widget.DividerItemDecoration.VERTICAL_LIST;

public class RankFragment extends BaseFragment<FragmentRankBinding> {

    private RankAdapter rankAdapter;
    private HeaderAndFooterWrapper headerAndFooterWrapper;
    private ItemRankTitleBinding rankTitleBinding;

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
        binding.recycler.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        rankAdapter = new RankAdapter();
        addRankTitle();
        binding.recycler.setAdapter(headerAndFooterWrapper);
        binding.recycler.addItemDecoration(new DividerItemDecoration(getActivity(), VERTICAL_LIST, 1));
    }

    private void addRankTitle() {
        headerAndFooterWrapper = new HeaderAndFooterWrapper(rankAdapter);
        rankTitleBinding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
                R.layout.item_rank_title, null, false);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        rankTitleBinding.getRoot().setLayoutParams(layoutParams);
        rankTitleBinding.rgRank.check(R.id.rb_week);
        headerAndFooterWrapper.addHeaderView(rankTitleBinding.getRoot());
    }

    private void initData() {
        rankAdapter.add(new ApiBean());
        rankAdapter.add(new ApiBean());
        rankAdapter.add(new ApiBean());
        rankAdapter.add(new ApiBean());
        rankAdapter.add(new ApiBean());
        rankAdapter.add(new ApiBean());
        rankAdapter.add(new ApiBean());
        rankAdapter.add(new ApiBean());
        rankAdapter.add(new ApiBean());
        rankAdapter.add(new ApiBean());
        rankAdapter.notifyDataSetChanged();

        onClick();
    }

    private void onClick() {
        rankTitleBinding.rgRank.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                rankTitleBinding.setColor("");
                switch (checkedId) {
                    case R.id.rb_week:
                        ToastUtil.show(getActivity(), "周排行");
                        break;
                    case R.id.rb_month:
                        ToastUtil.show(getActivity(), "月排行");
                        break;
                }
            }
        });
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
