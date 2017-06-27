package com.dasheng.papa.mvp.category;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;

import com.dasheng.papa.R;
import com.dasheng.papa.adapter.CategoryAdapter;
import com.dasheng.papa.base.BaseFragment;
import com.dasheng.papa.base.OnItemClickListener;
import com.dasheng.papa.bean.CategoryBean;
import com.dasheng.papa.databinding.FragmentCategoryBinding;
import com.dasheng.papa.mvp.MainActivity;
import com.dasheng.papa.mvp.category.child.CategoryDetailFragment;
import com.dasheng.papa.util.CommonUtils;
import com.dasheng.papa.util.Constant;
import com.dasheng.papa.util.ToastUtil;

import timber.log.Timber;

public class CategoryFragment extends BaseFragment<FragmentCategoryBinding> {


    private CategoryAdapter categoryAdapter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
        initData();
    }

    private void initView() {
        initRecyclerView();
    }

    private void initRecyclerView() {
        binding.recycler.setLayoutManager(new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false));
        categoryAdapter = new CategoryAdapter();
        categoryAdapter.setOnItemClickListener(new OnItemClickListener<CategoryBean>() {
            @Override
            public void onClick(CategoryBean categoryBean, int position) {
                ToastUtil.show(getActivity(), "position:" + position);
                CategoryDetailFragment categoryDetailFragment = new CategoryDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.Intent_Extra.CATEGORY_TYPE, categoryBean);
                categoryDetailFragment.setArguments(bundle);
                ((MainActivity) getActivity()).switchFragment(categoryDetailFragment);
            }
        });
    }

    private void initData() {
        categoryAdapter.add(new CategoryBean(CommonUtils.getString(R.string.category_entertainment_selection),
                R.drawable.category_entertainment_selection, 1));
        categoryAdapter.add(new CategoryBean(CommonUtils.getString(R.string.category_roast),
                R.drawable.category_roast, 2));
        categoryAdapter.add(new CategoryBean(CommonUtils.getString(R.string.category_spoof),
                R.drawable.category_spoof, 3));
        categoryAdapter.add(new CategoryBean(CommonUtils.getString(R.string.category_embarrass),
                R.drawable.category_embarrass, 4));
        categoryAdapter.add(new CategoryBean(CommonUtils.getString(R.string.category_funny_talent),
                R.drawable.category_funny_talent, 5));
        categoryAdapter.add(new CategoryBean(CommonUtils.getString(R.string.category_anecdote),
                R.drawable.category_anecdote, 6));
        categoryAdapter.add(new CategoryBean(CommonUtils.getString(R.string.category_beauty),
                R.drawable.category_beauty, 7));
        categoryAdapter.add(null);
        categoryAdapter.add(new CategoryBean(CommonUtils.getString(R.string.category_weixin),
                R.drawable.category_weixin, 8));
        binding.recycler.setAdapter(categoryAdapter);
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        Timber.d("onFragmentVisibleChange: %b", isVisible);
        if (isVisible) {
            baseActivity.setTitle(R.string.category_title);
        }
    }

    @Override
    protected void onFragmentFirstVisible() {
        Timber.d("onFragmentFirstVisible");
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_category;
    }
}
