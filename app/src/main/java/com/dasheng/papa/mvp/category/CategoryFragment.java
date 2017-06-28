package com.dasheng.papa.mvp.category;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;

import com.dasheng.papa.R;
import com.dasheng.papa.adapter.CategoryAdapter;
import com.dasheng.papa.base.BaseFragment;
import com.dasheng.papa.base.OnItemClickListener;
import com.dasheng.papa.bean.ApiListResBean;
import com.dasheng.papa.bean.ResponseItemBean;
import com.dasheng.papa.databinding.FragmentCategoryBinding;
import com.dasheng.papa.mvp.MainActivity;
import com.dasheng.papa.mvp.category.child.CategoryDetailFragment;
import com.dasheng.papa.util.Constant;
import com.dasheng.papa.util.GsonUtil;
import com.dasheng.papa.util.SPUtil;
import com.dasheng.papa.util.ToastUtil;

import java.util.List;

import timber.log.Timber;

public class CategoryFragment extends BaseFragment<FragmentCategoryBinding> implements CategoryContact.View {


    private CategoryAdapter categoryAdapter;
    private CategoryPresenter categoryPresenter;
    private List<ResponseItemBean> responseItemBeanList;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
    }

    private void initView() {
        initRecyclerView();
    }

    private void initRecyclerView() {
        binding.recycler.setLayoutManager(new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false));
        categoryAdapter = new CategoryAdapter();
        categoryAdapter.setOnItemClickListener(new OnItemClickListener<ResponseItemBean>() {
            @Override
            public void onClick(ResponseItemBean categoryBean, int position) {
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
        if (responseItemBeanList != null) {
            for (ResponseItemBean bean : responseItemBeanList) {
                categoryAdapter.add(bean);
            }
        }
        categoryAdapter.add(null);
        ResponseItemBean bean = new ResponseItemBean();
        bean.setName("关注公众号");
        categoryAdapter.add(bean);
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
        String categoryInfo = (String) SPUtil.get(Constant.Api.CATEGORY_INFO_LIST, "");
        if (TextUtils.isEmpty(categoryInfo)) {
            categoryPresenter = new CategoryPresenter(this);
            categoryPresenter.loadCategory();
        } else {
            responseItemBeanList = GsonUtil.jsonToList(categoryInfo, ResponseItemBean.class);
            initData();
        }
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_category;
    }

    @Override
    public void onShowLoading() {

    }

    @Override
    public void onLoadingDismiss() {

    }

    @Override
    public void onLoadCategoryInfoSuccess(ApiListResBean<ResponseItemBean> apiBean) {
        if (apiBean.getRes() != null && apiBean.getRes().size() > 0) {
            SPUtil.put(Constant.Api.CATEGORY_INFO_LIST, GsonUtil.GsonString(apiBean.getRes()));
            responseItemBeanList = apiBean.getRes();
            categoryAdapter.addItemList(responseItemBeanList);
        }
    }
}
