package com.dasheng.papa.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.dasheng.papa.R;
import com.dasheng.papa.databinding.ActivityBaseBinding;
import com.dasheng.papa.util.KeyBoardUtils;
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
        baseBinding.rlSearch.setVisibility(visible);
        if (visible == View.VISIBLE) {
            baseBinding.etSearch.setCursorVisible(false);
            baseBinding.etSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    baseBinding.etSearch.setCursorVisible(true);
                }
            });
        }
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

    /**
     * 清除editText的焦点
     *
     * @param v   焦点所在View
     * @param ids 输入框
     */
    public void clearViewFocus(View v, int... ids) {
        if (null != v && null != ids && ids.length > 0) {
            for (int id : ids) {
                if (v.getId() == id) {
                    v.clearFocus();
                    if (v instanceof EditText) {
                        ((EditText) v).setCursorVisible(false);
                    }
                    break;
                }
            }
        }


    }

    /**
     * 隐藏键盘
     *
     * @param v   焦点所在View
     * @param ids 输入框
     * @return true代表焦点在edit上
     */
    public boolean isFocusEditText(View v, int... ids) {
        if (v instanceof EditText) {
            EditText tmp_et = (EditText) v;
            for (int id : ids) {
                if (tmp_et.getId() == id) {
                    return true;
                }
            }
        }
        return false;
    }

    //是否触摸在指定view上面,对某个控件过滤
    public boolean isTouchView(View[] views, MotionEvent ev) {
        if (views == null || views.length == 0)
            return false;
        int[] location = new int[2];
        for (View view : views) {
            view.getLocationOnScreen(location);
            int x = location[0];
            int y = location[1];
            if (ev.getX() > x && ev.getX() < (x + view.getWidth())
                    && ev.getY() > y && ev.getY() < (y + view.getHeight())) {
                return true;
            }
        }
        return false;
    }
    //endregion

    //region 右滑返回上级


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (isTouchView(filterViewByIds(), ev))
                return super.dispatchTouchEvent(ev);
            if (hideSoftByEditViewIds() == null || hideSoftByEditViewIds().length == 0)
                return super.dispatchTouchEvent(ev);
            View v = getCurrentFocus();
            if (isFocusEditText(v, hideSoftByEditViewIds())) {
                //隐藏键盘
                KeyBoardUtils.hideInputForce(this);
                clearViewFocus(v, hideSoftByEditViewIds());
            }
        }
        return super.dispatchTouchEvent(ev);

    }

    /**
     * 传入EditText的Id
     * 没有传入的EditText不做处理
     *
     * @return id 数组
     */
    public int[] hideSoftByEditViewIds() {
        return null;
    }

    /**
     * 传入要过滤的View
     * 过滤之后点击将不会有隐藏软键盘的操作
     *
     * @return id 数组
     */
    public View[] filterViewByIds() {
        return null;
    }


    protected abstract void initView();

    protected abstract void initEvent();

}
