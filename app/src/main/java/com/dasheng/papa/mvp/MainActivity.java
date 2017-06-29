package com.dasheng.papa.mvp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.dasheng.papa.R;
import com.dasheng.papa.adapter.MainPagerAdapter;
import com.dasheng.papa.base.BaseActivity;
import com.dasheng.papa.base.BaseFragment;
import com.dasheng.papa.bean.ResponseItemBean;
import com.dasheng.papa.databinding.ActivityMainBinding;
import com.dasheng.papa.mvp.beauty.BeautyFragment;
import com.dasheng.papa.mvp.category.CategoryFragment;
import com.dasheng.papa.mvp.category.child.CategoryDetailFragment;
import com.dasheng.papa.mvp.home.HomeFragment;
import com.dasheng.papa.mvp.rank.RankFragment;
import com.dasheng.papa.mvp.search.SearchActivity;
import com.dasheng.papa.util.Constant;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class MainActivity extends BaseActivity<ActivityMainBinding> implements View.OnClickListener {

    private List<BaseFragment> mFragments = new ArrayList<>(4);
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initView() {
        initViewPager();
        mFragmentManager = getSupportFragmentManager();
        binding.home.setOnClickListener(this);
        binding.category.setOnClickListener(this);
        binding.beauty.setOnClickListener(this);
        binding.rank.setOnClickListener(this);
    }

    @Override
    protected void initEvent() {
        binding.setState(R.id.home);
    }

    private void initViewPager() {
        mFragments.add(new HomeFragment());
        mFragments.add(new CategoryFragment());
        mFragments.add(new BeautyFragment());
        mFragments.add(new RankFragment());
        MainPagerAdapter mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager(), mFragments);
        binding.subContent.setAdapter(mainPagerAdapter);
        binding.subContent.setOffscreenPageLimit(mFragments.size());
        binding.subContent.setCurrentItem(0, false);
    }

    @Override
    public void onClick(View v) {
        binding.setState(v.getId());
        switch (v.getId()) {
            case R.id.home:
                binding.subContent.setCurrentItem(0, false);
                backFragment();
                break;
            case R.id.category:
                binding.subContent.setCurrentItem(1, false);
                break;
            case R.id.beauty:
                binding.subContent.setCurrentItem(2, false);
                backFragment();
                break;
            case R.id.rank:
                binding.subContent.setCurrentItem(3, false);
                backFragment();
                break;
        }
    }

    public void gotoBeauty() {
        binding.subContent.setCurrentItem(2);
        binding.setState(R.id.beauty);
    }

    public void switchFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.sub_container, fragment).addToBackStack(null).commit();
    }

    public void backFragment() {
        if (mFragmentManager.getBackStackEntryCount() > 0) {
            mFragmentManager.popBackStack();
        }
    }

    @Override
    public void onBackPressed() {
        Timber.d("onBackPressed");
        if (mFragmentManager.getBackStackEntryCount() > 0) {
            setTitle(R.string.category_title);
        }
        super.onBackPressed();
    }

    @Override
    public int[] hideSoftByEditViewIds() {
        return new int[]{R.id.et_search};
    }

    public void gotoCategory(String type_id, String type_name) {
        CategoryDetailFragment categoryDetailFragment = new CategoryDetailFragment();
        Bundle bundle = new Bundle();
        ResponseItemBean bean = new ResponseItemBean();
        bean.setName(type_name);
        bean.setId(type_id);
        bundle.putSerializable(Constant.Intent_Extra.CATEGORY_TYPE, bean);
        categoryDetailFragment.setArguments(bundle);
        binding.subContent.setCurrentItem(1, false);
        binding.setState(R.id.category);
        switchFragment(categoryDetailFragment);
    }

    @Override
    protected void onSearchClicked(String searchText) {
        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
        intent.putExtra(Constant.Intent_Extra.SEARCH_CONTENT, searchText);
        startActivity(intent);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                clearSearchContent();
            }
        }, 100);
    }

    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mFragmentManager.getBackStackEntryCount() <= 0 &&
                keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - exitTime > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
