package com.dasheng.papa.mvp.home.child;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;

import com.dasheng.papa.R;
import com.dasheng.papa.adapter.HomeCategoryAdapter;
import com.dasheng.papa.base.BaseFragment;
import com.dasheng.papa.base.OnItemClickListener;
import com.dasheng.papa.bean.ApiBean;
import com.dasheng.papa.databinding.FragmentHomeCategoryBinding;
import com.dasheng.papa.util.Constant;
import com.dasheng.papa.util.ToastUtil;
import com.dasheng.papa.widget.springview.DefaultFooter;
import com.dasheng.papa.widget.springview.DefaultHeader;
import com.dasheng.papa.widget.springview.SpringView;

import timber.log.Timber;

public class HomeCategoryFragment extends BaseFragment<FragmentHomeCategoryBinding> {

    private boolean isFirst;
    private HomeCategoryAdapter homeCategoryAdapter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle bundle = getArguments();
        String title = bundle.getString(Constant.Intent_Extra.HOME_CATEGORY_TYPE);
        Timber.d("onActivityCreated: %s  %b", title, isFirst);
        initView();
        initEvent();
    }

    private void initView() {
        initRecyclerView();
        initSwipeRefreshLayout();
    }

    private void initSwipeRefreshLayout() {
        binding.swipe.setHeader(new DefaultHeader(getActivity()));
        binding.swipe.setFooter(new DefaultFooter(getActivity()));
        binding.swipe.setType(SpringView.Type.FOLLOW);
        binding.swipe.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        binding.swipe.onFinishFreshAndLoad();
                        initEvent();
                    }
                }, 3000);
            }

            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initEvent();
                        binding.swipe.onFinishFreshAndLoad();
                    }
                }, 3000);
            }
        });
    }

    private void initRecyclerView() {
        binding.recycler.setLayoutManager(new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false));
        homeCategoryAdapter = new HomeCategoryAdapter();
        binding.recycler.setAdapter(homeCategoryAdapter);
        homeCategoryAdapter.setOnItemClickListener(new OnItemClickListener<ApiBean>() {
            @Override
            public void onClick(ApiBean apiBean, int position) {
                ToastUtil.show(getActivity(), "click" + position);
            }
        });
    }

    private void initEvent() {
        homeCategoryAdapter.add(new ApiBean());
        homeCategoryAdapter.add(new ApiBean());
        homeCategoryAdapter.add(new ApiBean());
        homeCategoryAdapter.add(new ApiBean());
        homeCategoryAdapter.add(new ApiBean());
        homeCategoryAdapter.add(new ApiBean());
        homeCategoryAdapter.add(new ApiBean());
        homeCategoryAdapter.add(new ApiBean());
        homeCategoryAdapter.add(new ApiBean());
        homeCategoryAdapter.add(new ApiBean());
        homeCategoryAdapter.add(new ApiBean());
        homeCategoryAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        Timber.d("onFragmentVisibleChange: %b", isVisible);
        if (isVisible) {

        } else {

        }
    }

    @Override
    protected void onFragmentFirstVisible() {
        Timber.d("onFragmentFirstVisible");
        isFirst = true;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_home_category;
    }
}
