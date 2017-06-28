package com.dasheng.papa.mvp.splash;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.dasheng.papa.R;
import com.dasheng.papa.api.ApiFactory;
import com.dasheng.papa.api.BaseSubscriber;
import com.dasheng.papa.bean.ApiListResBean;
import com.dasheng.papa.bean.ResponseItemBean;
import com.dasheng.papa.mvp.MainActivity;
import com.dasheng.papa.util.Constant;
import com.dasheng.papa.util.GsonUtil;
import com.dasheng.papa.util.SPUtil;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class SplashActivity extends AppCompatActivity {

    private static final String IS_First = "isFirst";
    private boolean isFirst = true;
    private Handler handler = new Handler();
    private SharedPreferences.Editor editor;
    private Long requestTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);
        SharedPreferences preferences = getSharedPreferences(IS_First, MODE_PRIVATE);
        editor = preferences.edit();
        isFirst = preferences.getBoolean(IS_First, isFirst);
        if (isFirst) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashActivity.this, GuideActivity.class));
                    finish();
                    editor.putBoolean(IS_First, false);
                    editor.apply();
                }
            }, 0);
        } else {
            requestTime = System.currentTimeMillis();
            loadCategoryInfo();
        }
    }

    private void loadCategoryInfo() {
        ApiFactory.rank(null, null, null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<ApiListResBean<ResponseItemBean>>() {
                    @Override
                    public void onError(Throwable e) {
                        gotoMainActivity(System.currentTimeMillis() - requestTime);
                    }

                    @Override
                    public void onNext(ApiListResBean<ResponseItemBean> apiBean) {
                        if (apiBean.getRes() != null && apiBean.getRes().size() > 0) {
                            SPUtil.put(Constant.Api.CATEGORY_INFO_LIST, GsonUtil.GsonString(apiBean.getRes()));
                        }
                        gotoMainActivity(System.currentTimeMillis() - requestTime);
                    }
                });
    }

    public void gotoMainActivity(long l) {
        Long delay = 0L;
        if (l > 3000) {
            delay = l;
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }, delay);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

}
