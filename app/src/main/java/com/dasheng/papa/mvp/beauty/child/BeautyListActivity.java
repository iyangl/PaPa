package com.dasheng.papa.mvp.beauty.child;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import com.dasheng.papa.R;
import com.dasheng.papa.adapter.BeautyListPagerAdapter;
import com.dasheng.papa.base.BaseActivity;
import com.dasheng.papa.base.OnItemClickListener;
import com.dasheng.papa.bean.ApiSingleResBean;
import com.dasheng.papa.bean.BeautyPicBean;
import com.dasheng.papa.bean.ImgBean;
import com.dasheng.papa.databinding.ActivityBeautyListBinding;
import com.dasheng.papa.util.Constant;
import com.dasheng.papa.util.PermissionUtil;
import com.dasheng.papa.util.ToastUtil;
import com.dasheng.papa.util.glidedownload.DownLoadImageService;
import com.dasheng.papa.util.glidedownload.ImageDownLoadCallBack;
import com.tbruyelle.rxpermissions.RxPermissions;

public class BeautyListActivity extends BaseActivity<ActivityBeautyListBinding>
        implements View.OnClickListener, BeautyListContact.View {

    private BeautyListPagerAdapter beautyListPagerAdapter;
    private String mId;
    private boolean isLoading;
    private BeautyListPresenter beautyListPresenter;
    private BeautyPicBean.PreBean prePic;
    private BeautyPicBean.NextBean nextPic;
    private RxPermissions mRxPermissions;
    private String picTitle;
    private TranslateAnimation mTopShowAction;
    private TranslateAnimation mTopHiddenAction;
    private TranslateAnimation mBottomShowAction;
    private TranslateAnimation mBottomHiddenAction;
    private TranslateAnimation mLeftShowAction;
    private TranslateAnimation mLeftHiddenAction;
    private TranslateAnimation mRightShowAction;
    private TranslateAnimation mRightHiddenAction;

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
            binding.setTitle(beautyPic.getTitle());
            binding.setAddtime(beautyPic.getAddtime());
        } else {
            finish();
        }
        setTitle(R.string.beauty_title);
        setNavigationIcon();
        initViewPager();
        initAnimation();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideAround();
            }
        }, 300);
    }


    private void initViewPager() {
        beautyListPagerAdapter = new BeautyListPagerAdapter();
        binding.pager.setAdapter(beautyListPagerAdapter);
        beautyListPagerAdapter.setOnItemClickListener(new OnItemClickListener<String>() {
            @Override
            public void onClick(String s, int position) {
                if (binding.header.getVisibility() == View.VISIBLE) {
                    hideAround();
                } else {
                    showAround();
                }
            }
        });
    }

    @Override
    protected void initEvent() {
        mRxPermissions = new RxPermissions(this);
        isLoading = true;
        beautyListPresenter = new BeautyListPresenter(this);
        beautyListPresenter.loadPics(mId);

        binding.pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                binding.setPage(String.format("%s/%s 页", position + 1, beautyListPagerAdapter.getCount()));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        binding.save.setOnClickListener(this);
        binding.left.setOnClickListener(this);
        binding.right.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (isLoading) {
            return;
        }
        switch (v.getId()) {
            case R.id.save:
                String selectItemUrl = Constant.Api.BASE_URL +
                        beautyListPagerAdapter.getItem(binding.pager.getCurrentItem());
                savePic(selectItemUrl, String.format("%s(%s)", picTitle, binding.pager.getCurrentItem() + 1));
                break;
            case R.id.left:
                if (prePic == null) {
                    ToastUtil.show(this, "没有前一篇了");
                    return;
                }
                beautyListPresenter.loadPics(prePic.getId());
                break;
            case R.id.right:
                if (nextPic == null) {
                    ToastUtil.show(this, "没有下一篇了");
                    return;
                }
                beautyListPresenter.loadPics(nextPic.getId());
                break;
        }
    }

    private void savePic(final String selectItemUrl, final String picName) {
        PermissionUtil.externalStorage(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                startSavePic(selectItemUrl, picName);
            }

            @Override
            public void onRequestPermissionFailed() {
                ToastUtil.show("未获取权限，保存失败！");
            }
        }, mRxPermissions);
    }

    private void startSavePic(String selectItemUrl, String picName) {
        new Thread(new DownLoadImageService(this,
                selectItemUrl, picName, new ImageDownLoadCallBack() {

            @Override
            public void onDownLoadSuccess(Bitmap bitmap) {
                ToastUtil.show("保存成功");
            }

            @Override
            public void onDownLoadFailed(Throwable e) {
                ToastUtil.show("保存失败");
            }
        })).start();
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
        beautyListPagerAdapter.clear();
        beautyListPagerAdapter.addAll(pics.getRes().getContent());
        binding.pager.setCurrentItem(0, false);
        binding.setPage(String.format("%s/%s 页", binding.pager.getCurrentItem() + 1,
                beautyListPagerAdapter.getCount()));
        picTitle = pics.getRes().getInfo().getTitle();
        binding.setTitle(picTitle);
        binding.setAddtime(pics.getRes().getInfo().getAddtime());

        prePic = pics.getRes().getPre();
        nextPic = pics.getRes().getNext();
    }

    @Override
    public void onError(Throwable e) {
        isLoading = false;
    }

    private void initAnimation() {
        mTopShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        mTopShowAction.setDuration(300);
        mTopHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f);
        mTopHiddenAction.setDuration(300);

        mBottomShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        mBottomShowAction.setDuration(300);
        mBottomHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                1.0f);
        mBottomHiddenAction.setDuration(300);

        mLeftShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, -1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        mLeftShowAction.setDuration(300);
        mLeftHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, -1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f);
        mLeftHiddenAction.setDuration(300);

        mRightShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        mRightShowAction.setDuration(300);
        mRightHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f);
        mRightHiddenAction.setDuration(300);
    }

    private void showAround() {
        binding.header.setVisibility(View.VISIBLE);
        binding.header.startAnimation(mTopShowAction);
        binding.footer.setVisibility(View.VISIBLE);
        binding.footer.startAnimation(mBottomShowAction);
        binding.left.setVisibility(View.VISIBLE);
        binding.left.startAnimation(mLeftShowAction);
        binding.right.setVisibility(View.VISIBLE);
        binding.right.startAnimation(mRightShowAction);
    }

    private void hideAround() {
        binding.header.setVisibility(View.GONE);
        binding.header.startAnimation(mTopHiddenAction);
        binding.footer.setVisibility(View.GONE);
        binding.footer.startAnimation(mBottomHiddenAction);
        binding.left.setVisibility(View.GONE);
        binding.left.startAnimation(mLeftHiddenAction);
        binding.right.setVisibility(View.GONE);
        binding.right.startAnimation(mRightHiddenAction);
    }
}
