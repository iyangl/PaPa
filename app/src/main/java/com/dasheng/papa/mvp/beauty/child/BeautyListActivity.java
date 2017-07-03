package com.dasheng.papa.mvp.beauty.child;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dasheng.papa.R;
import com.dasheng.papa.adapter.BeautyListAdapter;
import com.dasheng.papa.base.BaseActivity;
import com.dasheng.papa.bean.ApiSingleResBean;
import com.dasheng.papa.bean.BeautyPicBean;
import com.dasheng.papa.bean.ImgBean;
import com.dasheng.papa.databinding.ActivityBeautyListBinding;
import com.dasheng.papa.util.CommonUtils;
import com.dasheng.papa.util.Constant;
import com.dasheng.papa.widget.pulltorefreshhorizon.PullToRefreshBase;

import timber.log.Timber;

public class BeautyListActivity extends BaseActivity<ActivityBeautyListBinding>
        implements View.OnClickListener, BeautyListContact.View {

    private String mId;
    private boolean isLoading;
    private BeautyListPresenter beautyListPresenter;
    private BeautyListAdapter beautyListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beauty_list);
    }

    @Override
    protected void initView() {
        ImgBean.ImginfoBean beautyPic = (ImgBean.ImginfoBean) getIntent().
                getSerializableExtra(Constant.Intent_Extra.BEAUTY_PIC);
        if (beautyPic != null) {
            mId = beautyPic.getId();
            binding.setBeauty(beautyPic);
        } else {
            finish();
        }

        setTitle(R.string.beauty_title);
        setNavigationIcon();
        initViewPager();
    }

    private void initViewPager() {
        beautyListAdapter = new BeautyListAdapter();
        binding.pager.setAdapter(beautyListAdapter);
        binding.pager.setMode(PullToRefreshBase.Mode.BOTH);
        binding.pager.getLoadingLayoutProxy(true, false).setPullLabel("加载上一篇");
        binding.pager.getLoadingLayoutProxy(false, true).setPullLabel("加载下一篇");
        binding.pager.getLoadingLayoutProxy(true, true).setReleaseLabel("松开加载");
        binding.pager.getLoadingLayoutProxy(true, true).setRefreshingLabel("正在加载");
        binding.pager.getLoadingLayoutProxy().setLoadingDrawable(
                CommonUtils.getDrawable(R.drawable.progress));
        binding.pager.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<RecyclerView>() {
            @Override
            public void onRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                if (refreshView.isRefreshing()) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Timber.d("onRefresh");
                        }
                    }, 3000);
                }
            }
        });
    }

    @Override
    protected void initEvent() {
        isLoading = true;
        beautyListPresenter = new BeautyListPresenter(this);
        beautyListPresenter.loadPics(mId);

        binding.pre.setOnClickListener(this);
        binding.next.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void onShowLoading() {

    }

    @Override
    public void onLoadingDismiss() {

    }

    @Override
    public void onLoadPicsSuccess(ApiSingleResBean<BeautyPicBean> pics) {
        isLoading = false;
        beautyListAdapter.addPics(pics.getRes().getContent());
    }

    @Override
    public void onError(Throwable e) {
        isLoading = false;
    }
}
