package com.dasheng.papa.mvp.rank;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.widget.RadioGroup;

import com.dasheng.papa.R;
import com.dasheng.papa.adapter.RankAdapter;
import com.dasheng.papa.base.BaseFragment;
import com.dasheng.papa.base.OnItemClickListener;
import com.dasheng.papa.base.headandfoot.HeaderAndFooterWrapper;
import com.dasheng.papa.bean.ApiListResBean;
import com.dasheng.papa.bean.RankItemBean;
import com.dasheng.papa.databinding.FragmentRankBinding;
import com.dasheng.papa.databinding.ItemRankTitleBinding;
import com.dasheng.papa.mvp.video.VideoDetailActivity;
import com.dasheng.papa.util.ToastUtil;
import com.dasheng.papa.widget.DividerItemDecoration;
import com.dasheng.papa.widget.springview.DefaultFooter;
import com.dasheng.papa.widget.springview.DefaultHeader;
import com.dasheng.papa.widget.springview.SpringView;

import timber.log.Timber;

import static com.dasheng.papa.widget.DividerItemDecoration.VERTICAL_LIST;

public class RankFragment extends BaseFragment<FragmentRankBinding> implements RankContact.View {

    private RankAdapter rankAdapter;
    private HeaderAndFooterWrapper headerAndFooterWrapper;
    private ItemRankTitleBinding rankTitleBinding;

    private boolean isLoading;
    private int mCurrentPage;
    private int mTotalPages;
    private RankPresenter rankPresenter;
    private int type_id = 1;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
        initData();
    }

    private void initView() {
        initRecyclerView();
        initSwipeRefreshLayout();
        binding.rgRank.check(R.id.rb_day);
    }

    private void initRecyclerView() {
        binding.recycler.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        rankAdapter = new RankAdapter();
        binding.recycler.setAdapter(rankAdapter);
        binding.recycler.addItemDecoration(new DividerItemDecoration(getActivity(), VERTICAL_LIST, 1));
        rankAdapter.setOnItemClickListener(new OnItemClickListener<RankItemBean>() {
            @Override
            public void onClick(RankItemBean apiBean, int position) {
                ToastUtil.show(getActivity(), "position:" + position);
                getActivity().startActivity(new Intent(getActivity(), VideoDetailActivity.class));
            }
        });
    }

    private void initSwipeRefreshLayout() {
        binding.swipe.setHeader(new DefaultHeader(getActivity()));
        binding.swipe.setFooter(new DefaultFooter(getActivity()));
        binding.swipe.setType(SpringView.Type.FOLLOW);
    }

    private void initData() {
        onClick();

        binding.swipe.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                if (isLoading) {
                    return;
                }
                isLoading = true;
                binding.swipe.setDataFinish(false);
                rankPresenter.refresh(type_id);
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
                rankPresenter.loadMore(type_id, mCurrentPage + 1);
            }
        });
    }

    private void onClick() {
        binding.rgRank.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                binding.setColor("");
                switch (checkedId) {
                    case R.id.rb_day:
                        type_id = 1;

                        break;
                    case R.id.rb_week:
                        type_id = 2;

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
        rankPresenter = new RankPresenter(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.swipe.callFresh();
            }
        }, 300);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_rank;
    }

    @Override
    public void onShowLoading() {

    }

    @Override
    public void onLoadingDismiss() {

    }

    @Override
    public void onRefreshSuccess(ApiListResBean<RankItemBean> apiBean) {
        resetLoadingStatus();
        mCurrentPage = 1;
        mTotalPages = apiBean.getTotal();
    }

    @Override
    public void onLoadMoreSuccess(ApiListResBean<RankItemBean> apiBean) {
        resetLoadingStatus();
        mCurrentPage++;
        mTotalPages = apiBean.getTotal();
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
