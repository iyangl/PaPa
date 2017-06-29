package com.dasheng.papa.mvp.home.child;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;

import com.dasheng.papa.R;
import com.dasheng.papa.adapter.HomeCategoryAdapter;
import com.dasheng.papa.base.BaseFragment;
import com.dasheng.papa.base.OnItemClickListener;
import com.dasheng.papa.bean.ApiListResBean;
import com.dasheng.papa.bean.ResponseItemBean;
import com.dasheng.papa.cache.ACache;
import com.dasheng.papa.databinding.FragmentHomeCategoryBinding;
import com.dasheng.papa.util.Constant;
import com.dasheng.papa.util.UrlUtils;
import com.dasheng.papa.widget.springview.DefaultFooter;
import com.dasheng.papa.widget.springview.DefaultHeader;
import com.dasheng.papa.widget.springview.SpringView;

import timber.log.Timber;

public class HomeCategoryFragment extends BaseFragment<FragmentHomeCategoryBinding>
        implements HomeCategoryContact.View {

    private HomeCategoryAdapter homeCategoryAdapter;
    private HomeCategoryPresenter homeCategotyPresenter;
    private String type_id;
    private boolean isLoading;
    private int mCurrentPage;
    private int mTotalPages;
    private ApiListResBean<ResponseItemBean> mCacheApiBean;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initEvent();
    }

    private void initView() {
        type_id = initType_Id();
        initRecyclerView();
        initSwipeRefreshLayout();
    }

    private void initSwipeRefreshLayout() {
        binding.swipe.setHeader(new DefaultHeader(getActivity()));
        binding.swipe.setFooter(new DefaultFooter(getActivity()));
        binding.swipe.setType(SpringView.Type.FOLLOW);
    }

    private void initRecyclerView() {
        binding.recycler.setLayoutManager(new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false));
        homeCategoryAdapter = new HomeCategoryAdapter();
        if (mAcache != null) {
            mCacheApiBean = (ApiListResBean<ResponseItemBean>) mAcache
                    .getAsObject(Constant.Cache.CACHE_HOME_CATEGORY + type_id);
            if (mCacheApiBean != null && mCacheApiBean.getRes() != null && mCacheApiBean.getRes().size() > 0) {
                homeCategoryAdapter.addData(mCacheApiBean, true);
            }
        }
        binding.recycler.setAdapter(homeCategoryAdapter);
    }

    private void initEvent() {
        homeCategoryAdapter.setOnItemClickListener(new OnItemClickListener<Object>() {
            @Override
            public void onClick(Object apiBean, int position) {
                if (apiBean instanceof ResponseItemBean) {
                    UrlUtils.jumpToArticleOrVideo(getActivity(), (ResponseItemBean) apiBean);
                }
            }
        });

        binding.swipe.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                if (isLoading) {
                    return;
                }
                isLoading = true;
                binding.swipe.setDataFinish(false);
                homeCategotyPresenter.refresh(type_id);
            }

            @Override
            public void onLoadMore() {
                if (isLoading) {
                    return;
                }
                if (mCurrentPage >= mTotalPages) {
                    binding.swipe.onFinishFreshAndLoad();
                    return;
                }
                isLoading = true;
                homeCategotyPresenter.loadMore(type_id, String.valueOf(mCurrentPage + 1));
            }
        });
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        Timber.d("onFragmentVisibleChange: %b", isVisible);
    }

    @Override
    protected void onFragmentFirstVisible() {
        homeCategotyPresenter = new HomeCategoryPresenter(this);
        type_id = initType_Id();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.swipe.callFresh();
            }
        }, 300);
    }

    private String initType_Id() {
        if (!TextUtils.isEmpty(type_id)) {
            return type_id;
        }
        Bundle bundle = getArguments();
        return bundle.getString(Constant.Intent_Extra.HOME_CATEGORY_TYPE);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_home_category;
    }

    @Override
    public void onShowLoading() {

    }

    @Override
    public void onLoadingDismiss() {

    }

    @Override
    public void onRefresh(ApiListResBean<ResponseItemBean> apiBean) {
        mAcache.remove(Constant.Cache.CACHE_HOME_CATEGORY + type_id);
        mAcache.put(Constant.Cache.CACHE_HOME_CATEGORY + type_id, apiBean, ACache.TIME_DAY);
        resetLoadingStatus();
        mCurrentPage = 1;
        mTotalPages = apiBean.getTotal();
        if (mCurrentPage >= mTotalPages) {
            binding.swipe.setDataFinish(true);
        }
        homeCategoryAdapter.addData(apiBean, true);
    }

    @Override
    public void onLoadMoreSuccess(ApiListResBean<ResponseItemBean> apiBean) {
        resetLoadingStatus();
        mCurrentPage++;
        mTotalPages = apiBean.getTotal();
        if (mCurrentPage >= mTotalPages) {
            binding.swipe.setDataFinish(true);
        }
        homeCategoryAdapter.addData(apiBean);
    }

    @Override
    public void onError(Throwable e) {
        resetLoadingStatus();
        Timber.d("onError: %s", e.getMessage());
    }

    private void resetLoadingStatus() {
        binding.swipe.onFinishFreshAndLoad();
        isLoading = false;
    }
}
