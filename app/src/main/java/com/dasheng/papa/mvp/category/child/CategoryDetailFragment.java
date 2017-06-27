package com.dasheng.papa.mvp.category.child;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;

import com.dasheng.papa.R;
import com.dasheng.papa.adapter.CategoryDetailAdapter;
import com.dasheng.papa.base.BaseFragment;
import com.dasheng.papa.base.OnItemClickListener;
import com.dasheng.papa.bean.ApiBean;
import com.dasheng.papa.bean.CategoryBean;
import com.dasheng.papa.databinding.FragmentCategoryDetailBinding;
import com.dasheng.papa.mvp.MainActivity;
import com.dasheng.papa.util.Constant;
import com.dasheng.papa.widget.springview.DefaultFooter;
import com.dasheng.papa.widget.springview.DefaultHeader;
import com.dasheng.papa.widget.springview.SpringView;

import timber.log.Timber;

public class CategoryDetailFragment extends BaseFragment<FragmentCategoryDetailBinding> {

    private CategoryDetailAdapter categoryDetailAdapter;
    private MainActivity mainActivity;
    private int mId;
    private CategoryBean categoryBean;

    private boolean isLoading;
    private int mCurrentPage;
    private int mTotalPages;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
        initEvent();
    }

    private void initView() {
        mainActivity = (MainActivity) getActivity();
        if (categoryBean != null) {
            mainActivity.setTitle(categoryBean.getTitle());
        }
        mainActivity.setNavigationIcon();
        initRecyclerView();
        initSwipeRefreshView();
    }

    private void initSwipeRefreshView() {
        binding.swipe.setHeader(new DefaultHeader(getActivity()));
        binding.swipe.setFooter(new DefaultFooter(getActivity()));
        binding.swipe.setType(SpringView.Type.FOLLOW);
    }

    private void initRecyclerView() {
        binding.recycler.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));
        categoryDetailAdapter = new CategoryDetailAdapter();
        binding.recycler.setAdapter(categoryDetailAdapter);
        binding.recycler.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        categoryDetailAdapter.setOnItemClickListener(new OnItemClickListener<ApiBean>() {
            @Override
            public void onClick(ApiBean apiBean, int position) {

            }
        });
    }

    private void initEvent() {
        binding.swipe.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                if (isLoading) {
                    return;
                }
                isLoading = true;
                binding.swipe.setDataFinish(false);
            }

            @Override
            public void onLoadMore() {
                if (isLoading) {
                    return;
                }
                if (mCurrentPage >= mTotalPages) {
                    binding.swipe.setDataFinish(true);
                    binding.swipe.onFinishFreshAndLoad();
                    return;
                }
                isLoading = true;
            }
        });
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        Timber.d("onFragmentVisibleChange, %b", isVisible);
    }

    @Override
    protected void onFragmentFirstVisible() {
        categoryBean = (CategoryBean) getArguments().getSerializable(Constant.Intent_Extra.CATEGORY_TYPE);
        if (categoryBean != null) {
            mId = categoryBean.getId();
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.swipe.callFresh();
            }
        }, 300);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_category_detail;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mainActivity.hideNavigationIcon();
    }

}
