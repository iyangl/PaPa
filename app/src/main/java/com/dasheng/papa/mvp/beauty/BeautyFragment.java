package com.dasheng.papa.mvp.beauty;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;

import com.dasheng.papa.R;
import com.dasheng.papa.adapter.BeautyPicAdapter;
import com.dasheng.papa.base.BaseFragment;
import com.dasheng.papa.base.OnItemClickListener;
import com.dasheng.papa.bean.ApiSingleResBean;
import com.dasheng.papa.bean.ImgBean;
import com.dasheng.papa.databinding.FragmentBeautyBinding;
import com.dasheng.papa.mvp.beauty.child.BeautyListActivity;
import com.dasheng.papa.util.Constant;
import com.dasheng.papa.util.ToastUtil;
import com.dasheng.papa.widget.springview.DefaultFooter;
import com.dasheng.papa.widget.springview.DefaultHeader;
import com.dasheng.papa.widget.springview.SpringView;

import timber.log.Timber;

public class BeautyFragment extends BaseFragment<FragmentBeautyBinding> implements BeautyContact.View {

    private BeautyPicAdapter beautyPicAdapter;
    private boolean isLoading;
    private int mCurrentPage;
    private int mTotalPages;
    private BeautyPresenter beautyPresenter;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
        initData();
    }

    private void initView() {
        initRecyclerView();
        initSwipeRefreshLayout();
    }

    private void initRecyclerView() {
        binding.recycler.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        binding.recycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        beautyPicAdapter = new BeautyPicAdapter();
        binding.recycler.setAdapter(beautyPicAdapter);
    }

    private void initSwipeRefreshLayout() {
        binding.swipe.setHeader(new DefaultHeader(getActivity()));
        binding.swipe.setFooter(new DefaultFooter(getActivity()));
        binding.swipe.setType(SpringView.Type.FOLLOW);
    }

    private void initData() {
        beautyPicAdapter.setOnItemClickListener(new OnItemClickListener<ImgBean.ImginfoBean>() {
            @Override
            public void onClick(ImgBean.ImginfoBean apiBean, int position) {
                ToastUtil.show(getActivity(), position + "");
                Intent intent = new Intent(getActivity(), BeautyListActivity.class);
                intent.putExtra(Constant.Intent_Extra.BEAUTY_PIC, apiBean);
                getActivity().startActivity(intent);
            }
        });

        binding.swipe.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                if (isLoading) {
                    return;
                }
                isLoading = true;
                beautyPresenter.refresh();
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
                beautyPresenter.loadMore(mCurrentPage + 1);
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
        beautyPresenter = new BeautyPresenter(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.swipe.callFresh();
            }
        }, 300);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_beauty;
    }

    @Override
    public void onShowLoading() {

    }

    @Override
    public void onLoadingDismiss() {

    }

    @Override
    public void onRefresh(ApiSingleResBean<ImgBean> apiBean) {
        resetLoadingStatus();
        binding.swipe.setDataFinish(false);
        mTotalPages = apiBean.getTotal();
        mCurrentPage = 1;
        beautyPicAdapter.addImg(apiBean.getRes().getImginfo(), true);
    }

    @Override
    public void onLoadMoreSuccess(ApiSingleResBean<ImgBean> apiBean) {
        resetLoadingStatus();
        mCurrentPage++;
        mTotalPages = apiBean.getTotal();
        if (mCurrentPage >= mTotalPages) {
            binding.swipe.setDataFinish(true);
        }
        beautyPicAdapter.addImg(apiBean.getRes().getImginfo());
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
