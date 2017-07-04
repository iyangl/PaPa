package com.dasheng.papa.mvp.beauty.child;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.dasheng.papa.R;
import com.dasheng.papa.adapter.BeautyListAdapter;
import com.dasheng.papa.base.BaseActivity;
import com.dasheng.papa.base.OnItemClickListener;
import com.dasheng.papa.bean.ApiSingleResBean;
import com.dasheng.papa.bean.BeautyPicBean;
import com.dasheng.papa.bean.ImgBean;
import com.dasheng.papa.databinding.ActivityBeautyListBinding;
import com.dasheng.papa.util.CommonUtils;
import com.dasheng.papa.util.Constant;
import com.dasheng.papa.widget.pulltorefreshhorizon.PullToRefreshBase;

public class BeautyListActivity extends BaseActivity<ActivityBeautyListBinding>
        implements View.OnClickListener, BeautyListContact.View,
        View.OnTouchListener, GestureDetector.OnGestureListener {

    private String mId;
    private boolean isLoading;
    private BeautyListPresenter beautyListPresenter;
    private BeautyListAdapter beautyListAdapter;

    int currentPosition = 0;
    GestureDetector detector;

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
        detector = new GestureDetector(this, this);
    }

    private void initViewPager() {
        binding.pager.setMode(PullToRefreshBase.Mode.BOTH);
        binding.pager.getLoadingLayoutProxy(true, false).setPullLabel("加载上一篇");
        binding.pager.getLoadingLayoutProxy(false, true).setPullLabel("加载下一篇");
        binding.pager.getLoadingLayoutProxy(true, true).setReleaseLabel("松开加载");
        binding.pager.getLoadingLayoutProxy(true, true).setRefreshingLabel("正在加载");
        binding.pager.getLoadingLayoutProxy().setLoadingDrawable(
                CommonUtils.getDrawable(R.drawable.default_ptr_rotate));
        binding.pager.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<RecyclerView>() {
            @Override
            public void onRefresh(PullToRefreshBase<RecyclerView> refreshView) {
                if (refreshView.isRefreshing()) {

                }
            }
        });
        beautyListAdapter = new BeautyListAdapter();
        binding.pager.setAdapter(beautyListAdapter);
        beautyListAdapter.setOnItemClickListener(new OnItemClickListener<String>() {
            @Override
            public void onClick(String s, int position) {
                if (binding.header.getVisibility() == View.VISIBLE) {
                    binding.header.setVisibility(View.GONE);
                    binding.footer.setVisibility(View.GONE);
                } else {
                    binding.header.setVisibility(View.VISIBLE);
                    binding.footer.setVisibility(View.VISIBLE);
                }
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            binding.pager.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                }
            });
        }
    }

    @Override
    protected void initEvent() {
        isLoading = true;
        beautyListPresenter = new BeautyListPresenter(this);
        beautyListPresenter.loadPics(mId);

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
        binding.setPage(String.format("%s/%s", 1, pics.getRes().getContent().size()));
        beautyListAdapter.addPics(pics.getRes().getContent());
    }

    @Override
    public void onError(Throwable e) {
        isLoading = false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (binding.header.getVisibility() == View.VISIBLE) {
                    binding.header.setVisibility(View.GONE);
                    binding.footer.setVisibility(View.GONE);
                } else {
                    binding.header.setVisibility(View.VISIBLE);
                    binding.footer.setVisibility(View.VISIBLE);
                }
                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.i("phc", "onFling");
        if (e1.getX() - e2.getX() > 0 && currentPosition < binding.pager.getChildCount()) {
            currentPosition++;
            // 手向左滑动，图片下一张
        } else if (e2.getX() - e1.getX() > 0 && currentPosition > 0) {
            // 向右滑动，图片上一张
            currentPosition--;

        }
        binding.pager.getRefreshableView().smoothScrollToPosition(currentPosition);

        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.i("phc", "onTouch");
        //传递event给GestureDetector对象
        detector.onTouchEvent(event);
        return true;
    }
}
