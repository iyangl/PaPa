package com.dasheng.papa.mvp.home.child;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;

import com.dasheng.papa.R;
import com.dasheng.papa.adapter.HomeCategoryAdapter;
import com.dasheng.papa.base.BaseFragment;
import com.dasheng.papa.base.OnItemClickListener;
import com.dasheng.papa.bean.ApiListResBean;
import com.dasheng.papa.bean.HomeResponseBean;
import com.dasheng.papa.databinding.FragmentHomeCategoryBinding;
import com.dasheng.papa.util.Constant;
import com.dasheng.papa.util.GsonUtil;
import com.dasheng.papa.util.ToastUtil;
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initEvent();
        Timber.d("onActivityCreated");
    }

    private void initView() {
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
        binding.recycler.setAdapter(homeCategoryAdapter);
    }

    private void initEvent() {
        homeCategoryAdapter.setOnItemClickListener(new OnItemClickListener<Object>() {
            @Override
            public void onClick(Object apiBean, int position) {
                ToastUtil.show(getActivity(), "click" + position);
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
                    binding.swipe.setDataFinish(true);
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
        Timber.d("onFragmentFirstVisible %b", getArguments() == null);
        homeCategotyPresenter = new HomeCategoryPresenter(this);
        Bundle bundle = getArguments();
        type_id = bundle.getString(Constant.Intent_Extra.HOME_CATEGORY_TYPE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.swipe.callFresh();
            }
        }, 300);
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
    public void onRefresh(ApiListResBean<HomeResponseBean> apiBean) {
        Timber.d("onNext %s", GsonUtil.GsonString(apiBean));
        resetLoadingStatus();
        mCurrentPage = 1;
        homeCategoryAdapter.addData(apiBean, true);
    }

    @Override
    public void onLoadMoreSuccess(ApiListResBean<HomeResponseBean> apiBean) {
        Timber.d("onNext %s", GsonUtil.GsonString(apiBean));
        resetLoadingStatus();
        mCurrentPage++;
        mTotalPages = apiBean.getTotal();
        homeCategoryAdapter.addData(apiBean, false);
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
