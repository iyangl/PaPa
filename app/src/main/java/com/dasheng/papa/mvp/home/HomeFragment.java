package com.dasheng.papa.mvp.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;

import com.dasheng.papa.R;
import com.dasheng.papa.adapter.MainPagerAdapter;
import com.dasheng.papa.base.BaseFragment;
import com.dasheng.papa.bean.ApiListResBean;
import com.dasheng.papa.bean.ResponseItemBean;
import com.dasheng.papa.databinding.FragmentHomeBinding;
import com.dasheng.papa.mvp.home.child.HomeCategoryFragment;
import com.dasheng.papa.util.Constant;
import com.dasheng.papa.util.GsonUtil;
import com.dasheng.papa.util.SPUtil;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class HomeFragment extends BaseFragment<FragmentHomeBinding> implements HomeContact.View {

    private TabLayout tab;
    private String[] mDefaultItems;
    private String[] mDefaultItemIds;
    private ViewPager vpHome;
    private HomePresenter homePresenter;
    private List<ResponseItemBean> responseItemBeanList;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Timber.d("onActivityCreated");
    }

    private void initTabLayout() {
        tab = binding.tab;
        mDefaultItems = new String[]{"娱乐精选", "奇闻趣事", "搞笑段子", "原创恶搞"};
        mDefaultItemIds = new String[]{"2", "3", "5", "6"};
        for (String string : mDefaultItems) {
            tab.addTab(tab.newTab().setText(string));
        }
    }

    private void initViewPager() {
        vpHome = binding.vpHome;
        List<BaseFragment> fragments = new ArrayList<>();
        for (int i = 0; i < mDefaultItems.length; i++) {
            HomeCategoryFragment fragment = new HomeCategoryFragment();
            Bundle bundle = new Bundle();
            bundle.putString(Constant.Intent_Extra.HOME_CATEGORY_TYPE, getItemId(i));
            fragment.setArguments(bundle);
            fragments.add(fragment);
        }
        vpHome.setAdapter(new MainPagerAdapter(getChildFragmentManager(), fragments, mDefaultItems));
        vpHome.setOffscreenPageLimit(fragments.size());
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

    private String getItemId(int i) {
        if (responseItemBeanList != null) {
            for (ResponseItemBean responseItemBean : responseItemBeanList) {
                if (mDefaultItems[i].equals(responseItemBean.getName())) {
                    return responseItemBean.getId();
                }
            }
        }
        return mDefaultItemIds[i];
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        Timber.d("onFragmentVisibleChange: %b", isVisible);
        if (isVisible) {
            baseActivity.setTitle("");
            baseActivity.setLogoVisible(View.VISIBLE);
        } else {
            baseActivity.setLogoVisible(View.GONE);
        }
    }

    @Override
    protected void onFragmentFirstVisible() {
        Timber.d("onFragmentFirstVisible");
        String categoryInfo = (String) SPUtil.get(Constant.Api.CATEGORY_INFO_LIST, "");
        if (TextUtils.isEmpty(categoryInfo)) {
            homePresenter = new HomePresenter(this);
            homePresenter.loadCategory();
        } else {
            responseItemBeanList = GsonUtil.jsonToList(categoryInfo, ResponseItemBean.class);
            initTabLayout();
            initViewPager();
        }
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void onShowLoading() {

    }

    @Override
    public void onLoadingDismiss() {

    }

    @Override
    public void onLoadCategoryInfoSuccess(ApiListResBean<ResponseItemBean> apiBean) {
        if (apiBean.getRes() != null && apiBean.getRes().size() > 0) {
            SPUtil.put(Constant.Api.CATEGORY_INFO_LIST, GsonUtil.GsonString(apiBean.getRes()));
            responseItemBeanList = apiBean.getRes();
            initTabLayout();
            initViewPager();
        }
    }
}
