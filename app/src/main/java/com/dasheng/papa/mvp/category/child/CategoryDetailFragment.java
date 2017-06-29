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
import com.dasheng.papa.bean.ApiListResBean;
import com.dasheng.papa.bean.ResponseItemBean;
import com.dasheng.papa.cache.ACache;
import com.dasheng.papa.databinding.FragmentCategoryDetailBinding;
import com.dasheng.papa.mvp.MainActivity;
import com.dasheng.papa.util.Constant;
import com.dasheng.papa.util.UrlUtils;
import com.dasheng.papa.widget.springview.DefaultFooter;
import com.dasheng.papa.widget.springview.DefaultHeader;
import com.dasheng.papa.widget.springview.SpringView;

import timber.log.Timber;

public class CategoryDetailFragment extends BaseFragment<FragmentCategoryDetailBinding> implements
        CategoryDetailContact.View {

    private CategoryDetailAdapter categoryDetailAdapter;
    private MainActivity mainActivity;
    private int mId;
    private ResponseItemBean categoryBean;
    private ApiListResBean<ResponseItemBean> mCacheApiBean;

    private boolean isLoading;
    private int mCurrentPage;
    private int mTotalPages;
    private CategoryDetailPresenter categoryDetailPresenter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
        initEvent();
    }

    private void initView() {
        mainActivity = (MainActivity) getActivity();
        if (categoryBean != null) {
            mainActivity.setTitle(categoryBean.getName());
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
        if (mAcache != null) {
            mCacheApiBean = (ApiListResBean<ResponseItemBean>) mAcache
                    .getAsObject(Constant.Cache.CACHE_CATEGORY_DETAIL + mId);
            if (mCacheApiBean != null && mCacheApiBean.getRes() != null && mCacheApiBean.getRes().size() > 0) {
                categoryDetailAdapter.addItems(mCacheApiBean.getRes(), true);
            }
        }
        binding.recycler.setAdapter(categoryDetailAdapter);
        binding.recycler.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        categoryDetailAdapter.setOnItemClickListener(new OnItemClickListener<ResponseItemBean>() {
            @Override
            public void onClick(ResponseItemBean apiBean, int position) {
                UrlUtils.jumpToArticleOrVideo(getActivity(), apiBean);
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
                categoryDetailPresenter.refresh(mId);
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
                categoryDetailPresenter.loadMore(mId, mCurrentPage + 1);
            }
        });
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        Timber.d("onFragmentVisibleChange, %b", isVisible);
    }

    @Override
    protected void onFragmentFirstVisible() {
        categoryBean = (ResponseItemBean) getArguments().getSerializable(Constant.Intent_Extra.CATEGORY_TYPE);
        if (categoryBean != null) {
            mId = Integer.parseInt(categoryBean.getId());
            getActivity().setTitle(categoryBean.getName());
        }
        categoryDetailPresenter = new CategoryDetailPresenter(this);
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

    @Override
    public void onShowLoading() {

    }

    @Override
    public void onLoadingDismiss() {

    }

    @Override
    public void onRefreshSuccess(ApiListResBean<ResponseItemBean> apiBean) {
        mAcache.remove(Constant.Cache.CACHE_CATEGORY_DETAIL + mId);
        mAcache.put(Constant.Cache.CACHE_CATEGORY_DETAIL + mId, apiBean, ACache.TIME_DAY);
        resetLoadingStatus();
        mCurrentPage = 1;
        mTotalPages = apiBean.getTotal();
        categoryDetailAdapter.addItems(apiBean.getRes(), true);
    }

    @Override
    public void onLoadMoreSuccess(ApiListResBean<ResponseItemBean> apiBean) {
        resetLoadingStatus();
        mCurrentPage++;
        mTotalPages = apiBean.getTotal();
        categoryDetailAdapter.addItems(apiBean.getRes());
    }

    @Override
    public void onError(Throwable e) {
        resetLoadingStatus();
    }

    private void resetLoadingStatus() {
        binding.swipe.onFinishFreshAndLoad();
        isLoading = false;
    }
}
