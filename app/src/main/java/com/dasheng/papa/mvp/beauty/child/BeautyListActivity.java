package com.dasheng.papa.mvp.beauty.child;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.dasheng.papa.R;
import com.dasheng.papa.adapter.BeautyListPagerAdapter;
import com.dasheng.papa.base.BaseActivity;
import com.dasheng.papa.bean.ApiBean;
import com.dasheng.papa.databinding.ActivityBeautyListBinding;
import com.dasheng.papa.util.ToastUtil;

public class BeautyListActivity extends BaseActivity<ActivityBeautyListBinding> implements View.OnClickListener {

    private BeautyListPagerAdapter beautyListPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beauty_list);
    }

    @Override
    protected void initView() {
        setTitle(R.string.beauty_title);
        setNavigationIcon();
        initViewPager();
    }

    private void initViewPager() {
        beautyListPagerAdapter = new BeautyListPagerAdapter();
        binding.pager.setAdapter(beautyListPagerAdapter);
    }

    @Override
    protected void initEvent() {
        binding.pre.setOnClickListener(this);
        binding.next.setOnClickListener(this);

        beautyListPagerAdapter.add(new ApiBean());
        beautyListPagerAdapter.add(new ApiBean());
        beautyListPagerAdapter.add(new ApiBean());
        beautyListPagerAdapter.add(new ApiBean());
        beautyListPagerAdapter.add(new ApiBean());
        beautyListPagerAdapter.add(new ApiBean());
        beautyListPagerAdapter.add(new ApiBean());
        beautyListPagerAdapter.notifyDataSetChanged();
        binding.setPage(String.format("%s/%s 页", 1, beautyListPagerAdapter.getCount()));

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

    }

    @Override
    public void onClick(View v) {
        int currentItem = binding.pager.getCurrentItem();
        switch (v.getId()) {
            case R.id.pre:
                if (currentItem == 0) {
                    ToastUtil.show(BeautyListActivity.this, "已是第一张");
                    return;
                }
                binding.pager.setCurrentItem(currentItem - 1);
                break;
            case R.id.next:
                if (currentItem == beautyListPagerAdapter.getCount() - 1) {
                    ToastUtil.show(BeautyListActivity.this, "没有更多了");
                    return;
                }
                binding.pager.setCurrentItem(currentItem + 1);
                break;
        }
    }
}
