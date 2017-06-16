package com.dasheng.papa.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.dasheng.papa.R;
import com.dasheng.papa.base.BaseRecyclerViewAdapter;
import com.dasheng.papa.base.BaseRecyclerViewHolder;
import com.dasheng.papa.bean.ApiBean;
import com.dasheng.papa.databinding.ItemHomeCategoryBannerBinding;
import com.dasheng.papa.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HomeCategoryAdapter extends BaseRecyclerViewAdapter<ApiBean> implements View.OnClickListener {
    private static final int TYPE_BANNER = 1;
    private static final int TYPE_GRID = 2;

    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_BANNER:
                View banner = View.inflate(parent.getContext(), R.layout.item_home_category_banner, null);
                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        CommonUtils.dip2px(banner.getContext(), 200));
                banner.setLayoutParams(layoutParams);
                return new BannerViewHolder(banner);
            case TYPE_GRID:
                RecyclerView recyclerView = new RecyclerView(parent.getContext());
                recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), 2));
                recyclerView.setAdapter(new GridAdapter());
                return new GridViewHolder(recyclerView);
        }
        return null;
    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_BANNER;
        }
        if (position == 1) {
            return TYPE_GRID;
        }
        return super.getItemViewType(position);
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onClick(null, 1);
        }
    }

    class GridViewHolder extends BaseRecyclerViewHolder<ApiBean, ItemHomeCategoryBannerBinding> {

        public GridViewHolder(View view) {
            super(view);
        }

        @Override
        public void onBindViewHolder(ApiBean object, int position) {

        }
    }


    class BannerViewHolder extends BaseRecyclerViewHolder<ApiBean, ItemHomeCategoryBannerBinding> {

        public BannerViewHolder(View view) {
            super(view);
            binding.banner.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    listener.onClick(null, position);
                }
            });
        }

        @Override
        public void onBindViewHolder(ApiBean apiBean, int position) {
            //自定义你的Holder，实现更多复杂的界面，不一定是图片翻页，其他任何控件翻页亦可。
            final List localImages = new ArrayList();
            for (int i = 5; i > 0; i--) {
                localImages.add("");
            }
            binding.banner.setPages(
                    new CBViewHolderCreator<LocalImageHolderView>() {
                        @Override
                        public LocalImageHolderView createHolder() {
                            return new LocalImageHolderView();
                        }
                    }, localImages)
                    //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                    //设置指示器的方向
                    .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
                    .setCanLoop(true);
            if (binding.banner.isCanLoop() && !binding.banner.isTurning()) {
                binding.banner.startTurning(3000);
            }
        }

        public class LocalImageHolderView implements Holder<String> {
            private ImageView imageView;

            @Override
            public View createView(Context context) {
                imageView = new ImageView(context);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                //红色
                String red;
                //绿色
                String green;
                //蓝色
                String blue;
                //生成随机对象
                Random random = new Random();
                //生成红色颜色代码
                red = Integer.toHexString(random.nextInt(256)).toUpperCase();
                //生成绿色颜色代码
                green = Integer.toHexString(random.nextInt(256)).toUpperCase();
                //生成蓝色颜色代码
                blue = Integer.toHexString(random.nextInt(256)).toUpperCase();

                //判断红色代码的位数
                red = red.length() == 1 ? "0" + red : red;
                //判断绿色代码的位数
                green = green.length() == 1 ? "0" + green : green;
                //判断蓝色代码的位数
                blue = blue.length() == 1 ? "0" + blue : blue;
                //生成十六进制颜色值
                String color = "#" + red + green + blue;
                imageView.setBackgroundColor(Color.parseColor(color));
                return imageView;
            }

            @Override
            public void UpdateUI(Context context, final int position, String data) {
                Glide.with(context).load(data).into(imageView);
            }
        }
    }
}