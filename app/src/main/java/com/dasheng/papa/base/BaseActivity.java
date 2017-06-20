package com.dasheng.papa.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.dasheng.papa.R;
import com.dasheng.papa.databinding.ActivityBaseBinding;
import com.zhy.autolayout.AutoLayoutActivity;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public abstract class BaseActivity<T extends ViewDataBinding> extends AutoLayoutActivity {
    private ActivityBaseBinding baseBinding;
    protected T binding;
    private CompositeSubscription compositeSubscription;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        //使用DataBindingUtil.setContentView()会报Stackoverflow
        baseBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_base, null, false);
        binding = DataBindingUtil.inflate(LayoutInflater.from(this), layoutResID, null, false);

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        binding.getRoot().setLayoutParams(layoutParams);
        baseBinding.container.addView(binding.getRoot());
        getWindow().setContentView(baseBinding.getRoot());

        initToolbar();

        initView();
        initEvent();
    }

    private void initToolbar() {
        setSupportActionBar(baseBinding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }


    public void setTitle(CharSequence title) {
        baseBinding.title.setText(title);
    }

    public void setLogoVisible(int visible) {
        baseBinding.papaLogo.setVisibility(visible);
    }

    public void setNavigationIcon() {
        baseBinding.toolbar.setNavigationIcon(R.drawable.back);
        baseBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void hideNavigationIcon() {
        baseBinding.toolbar.setNavigationIcon(null);
    }

    protected void addSubscription(Subscription subscription) {
        if (compositeSubscription == null) {
            compositeSubscription = new CompositeSubscription();
        }
        compositeSubscription.add(subscription);
    }

    protected void removeSubscription(Subscription subscription) {
        if (compositeSubscription == null) {
            compositeSubscription = new CompositeSubscription();
        }
        compositeSubscription.remove(subscription);
    }

    /**
     * 清空Subscription
     */
    protected void clearSubscription() {
        if (compositeSubscription == null) {
            compositeSubscription = new CompositeSubscription();
        }
        compositeSubscription.clear();
    }

    /**
     * 清空Subscription,且之后添加的Subscription也不会立即被注册
     */
    protected void removeAllSubscription() {
        if (compositeSubscription == null) {
            compositeSubscription = new CompositeSubscription();
        }
        compositeSubscription.unsubscribe();
    }

    protected abstract void initView();

    protected abstract void initEvent();

}
