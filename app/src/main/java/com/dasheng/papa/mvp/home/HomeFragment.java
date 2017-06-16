package com.dasheng.papa.mvp.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.dasheng.papa.R;
import com.dasheng.papa.adapter.MainPagerAdapter;
import com.dasheng.papa.base.BaseFragment;
import com.dasheng.papa.databinding.FragmentHomeBinding;
import com.dasheng.papa.mvp.home.child.HomeCategoryFragment;
import com.dasheng.papa.util.Constant;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class HomeFragment extends BaseFragment<FragmentHomeBinding> {

    private TabLayout tab;
    private String[] strings;
    private ViewPager vpHome;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initTabLayout();
        initViewPager();
    }

    private void initTabLayout() {
        tab = binding.tab;
        strings = new String[]{"11111", "22222", "33333", "44444", "55555", "66666", "77777", "888888", "99999"};
        for (String string : strings) {
            tab.addTab(tab.newTab().setText(string));
        }

    }

    private void initViewPager() {
        vpHome = binding.vpHome;
        List<BaseFragment> fragments = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            HomeCategoryFragment fragment = new HomeCategoryFragment();
            Bundle bundle = new Bundle();
            bundle.putString(Constant.Intent_Extra.HOME_CATEGORY_TYPE, strings[i]);
            fragment.setArguments(bundle);
            fragments.add(fragment);
        }
        vpHome.setAdapter(new MainPagerAdapter(getChildFragmentManager(), fragments, strings));
        tab.setupWithViewPager(vpHome);
        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //取消ViewPager滑动过度动画
                vpHome.setCurrentItem(tab.getPosition(), false);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

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
        return R.layout.fragment_home;
    }
}
