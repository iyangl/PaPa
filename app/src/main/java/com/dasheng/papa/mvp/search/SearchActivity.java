package com.dasheng.papa.mvp.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;

import com.dasheng.papa.R;
import com.dasheng.papa.adapter.SearchAdapter;
import com.dasheng.papa.base.BaseActivity;
import com.dasheng.papa.base.OnItemClickListener;
import com.dasheng.papa.bean.ApiListResBean;
import com.dasheng.papa.bean.ResponseItemBean;
import com.dasheng.papa.databinding.ActivitySearchBinding;
import com.dasheng.papa.util.Constant;
import com.dasheng.papa.util.ToastUtil;
import com.dasheng.papa.util.UrlUtils;
import com.dasheng.papa.widget.springview.DefaultFooter;
import com.dasheng.papa.widget.springview.SpringView;

public class SearchActivity extends BaseActivity<ActivitySearchBinding>
        implements SearchContact.View {

    private SearchAdapter searchAdapter;
    private String searchContent;
    private int mCurrentPage = 1;
    private int mTotalPages;
    private SearchPresenter searchPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }

    @Override
    protected void initView() {
        searchPresenter = new SearchPresenter(this);
        searchContent = getIntent().getStringExtra(Constant.Intent_Extra.SEARCH_CONTENT);
        setNavigationIcon();
        setTitle(String.format("\"%s\"的搜索结果", searchContent));
        initRecyclerView();
        initSwipeRefreshLayout();
    }

    @Override
    protected void initEvent() {
        searchPresenter.loadMore(searchContent, mCurrentPage);
        binding.swipe.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
            }

            @Override
            public void onLoadMore() {
                if (mCurrentPage >= mTotalPages) {
                    binding.swipe.onFinishFreshAndLoad();
                    return;
                }
                searchPresenter.loadMore(searchContent, mCurrentPage + 1);
            }
        });

        searchAdapter.setOnItemClickListener(new OnItemClickListener<ResponseItemBean>() {
            @Override
            public void onClick(ResponseItemBean responseItemBean, int position) {
                UrlUtils.jumpToArticleOrVideo(SearchActivity.this, responseItemBean);
            }
        });
    }

    private void initSwipeRefreshLayout() {
        binding.swipe.setFooter(new DefaultFooter(SearchActivity.this));
        binding.swipe.setType(SpringView.Type.FOLLOW);
    }

    private void initRecyclerView() {
        binding.recycler.setLayoutManager(new LinearLayoutManager(SearchActivity.this,
                LinearLayoutManager.VERTICAL, false));
        binding.recycler.addItemDecoration(new DividerItemDecoration(SearchActivity.this,
                DividerItemDecoration.VERTICAL));
        searchAdapter = new SearchAdapter();
        binding.recycler.setAdapter(searchAdapter);
    }

    @Override
    public void onShowLoading() {

    }

    @Override
    public void onLoadingDismiss() {

    }

    @Override
    public void onLoadMoreSuccess(ApiListResBean<ResponseItemBean> apiBean) {
        binding.swipe.onFinishFreshAndLoad();
        mCurrentPage++;
        mTotalPages = apiBean.getTotal();
        if (mCurrentPage >= mTotalPages) {
            binding.swipe.setDataFinish(true);
        }
        if (apiBean.getRes() == null || apiBean.getRes().size() == 0) {
            ToastUtil.show(SearchActivity.this, "未搜索到内容");
            return;
        }
        searchAdapter.addItems(apiBean.getRes());
    }

    @Override
    public void onError(Throwable e) {
        binding.swipe.onFinishFreshAndLoad();
    }
}
