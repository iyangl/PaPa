package com.dasheng.papa.mvp.beauty.child;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;

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
    }

    private void initViewPager() {
        beautyListPagerAdapter = new BeautyListPagerAdapter();
        binding.pager.setAdapter(beautyListPagerAdapter);
        beautyListPagerAdapter.setOnItemClickListener(new OnItemClickListener<String>() {
            @Override
            public void onClick(String s, int position) {
                if (binding.header.getVisibility() == View.VISIBLE) {
                    binding.header.setVisibility(View.GONE);
                    binding.footer.setVisibility(View.GONE);
                    binding.left.setVisibility(View.GONE);
                    binding.right.setVisibility(View.GONE);
                } else {
                    binding.header.setVisibility(View.VISIBLE);
                    binding.footer.setVisibility(View.VISIBLE);
                    binding.left.setVisibility(View.VISIBLE);
                    binding.right.setVisibility(View.VISIBLE);
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
                new Thread(new DownLoadImageService(BeautyListActivity.this,
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
            public void onRequestPermissionFailed() {
                ToastUtil.show("未获取权限，保存失败！");
            }
        }, mRxPermissions);
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
}
