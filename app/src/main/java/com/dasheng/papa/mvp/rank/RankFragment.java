package com.dasheng.papa.mvp.rank;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.RadioGroup;

import com.dasheng.papa.R;
import com.dasheng.papa.adapter.RankPagerAdapter;
import com.dasheng.papa.base.BaseFragment;
import com.dasheng.papa.databinding.FragmentRankBinding;
import com.dasheng.papa.mvp.rank.child.RankListFragment;
import com.dasheng.papa.util.Constant;
import com.dasheng.papa.util.FragmentUserVisibleController;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class RankFragment extends BaseFragment<FragmentRankBinding> {
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
        initData();
    }

    private void initView() {
        initViewPager();
    }

    private void initViewPager() {
        List<BaseFragment> fragments = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            RankListFragment fragment = new RankListFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(Constant.Intent_Extra.RANK_DAY_TYPE, i);
            fragment.setArguments(bundle);
            fragments.add(fragment);
        }
        binding.vpHome.setAdapter(new RankPagerAdapter(getChildFragmentManager(), fragments));
        binding.vpHome.setOffscreenPageLimit(fragments.size());
    }

    private void initData() {
        onClick();
    }

    private void onClick() {
        binding.rgRank.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                binding.setColor("");
                switch (checkedId) {
                    case R.id.rb_day:
                        binding.vpHome.setCurrentItem(0);
                        break;
                    case R.id.rb_week:
                        binding.vpHome.setCurrentItem(1);
                        break;
                }
            }
        });
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        Timber.d("rank onFragmentVisibleChange  isVisible %b", isVisible);
        if (isVisible) {
            baseActivity.setTitle(R.string.rank_title);
        }
    }

    /**
     * viewpager+fragments中fragment再嵌套viewpager+fragments时，
     * 会导致子fragments中第一个fragment的setUserVisible在父fragment未显示时传入true值
     * 且在父fragment变为不可见状态时，子fragment不会回调setUserVisible，导致逻辑上的可见
     * 下面代码配合子fragment中setUserVisible中代码可暂时解决第一个问题。
     * {@link FragmentUserVisibleController}是网上找到的解决方案，尝试一次失败后暂时放弃，
     * 等待日后细读代码，研究逻辑
     */
    @Override
    protected void onFragmentFirstVisible() {
        Timber.d("rank onFragmentFirstVisible");
        binding.vpHome.setCurrentItem(0);
        binding.rgRank.check(R.id.rb_day);

        List<Fragment> fragments = getChildFragmentManager().getFragments();
        if (fragments != null && fragments.size() > 0) {
            fragments.get(0).setUserVisibleHint(true);
        }
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_rank;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        Timber.d("rank onFragmentVisibleChange isVisibleToUser %b", isVisibleToUser);
        super.setUserVisibleHint(isVisibleToUser);

    }
}
