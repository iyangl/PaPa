package com.dasheng.papa.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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
import com.dasheng.papa.databinding.ItemHomeCategoryGridBinding;
import com.dasheng.papa.databinding.ItemHomeCategoryTitleBinding;
import com.dasheng.papa.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

public class HomeCategoryAdapter extends BaseRecyclerViewAdapter<ApiBean> {
    private static final int TYPE_BANNER = 0xffff01;
    private static final int TYPE_GRID = 0xffff02;
    private static final int TYPE_TITLE = 0xffff03;


    @Override
    public BaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_BANNER) {
            View banner = View.inflate(parent.getContext(), R.layout.item_home_category_banner, null);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    CommonUtils.dip2px(banner.getContext(), 140));
            banner.setLayoutParams(layoutParams);
            return new BannerViewHolder(banner);
        }
        if (viewType == TYPE_GRID) {
            View grid = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_home_category_grid, null, false);
            return new GridViewHolder(grid);
        }
        if (viewType == TYPE_TITLE) {
            View title = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_home_category_title, null, false);
            return new TitleViewHolder(title);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_BANNER;
        }
        if ((position - 1) % 5 == 0) {
            return TYPE_TITLE;
        }
        return TYPE_GRID;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void pauseAll() {

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) manager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    switch (getItemViewType(position)) {
                        case TYPE_BANNER:
                            return gridLayoutManager.getSpanCount();
                        case TYPE_GRID:
                            return gridLayoutManager.getSpanCount() / 2;
                    }
                    return gridLayoutManager.getSpanCount();
                }
            });
        }
    }

    //////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////
    class BannerViewHolder extends BaseRecyclerViewHolder<ApiBean, ItemHomeCategoryBannerBinding> {

        public BannerViewHolder(View view) {
            super(view);
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
            binding.banner.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    listener.onClick(null, position);
                }
            });
        }

        public class LocalImageHolderView implements Holder<String> {
            private ImageView imageView;

            @Override
            public View createView(Context context) {
                imageView = new ImageView(context);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setBackgroundColor(CommonUtils.randomColor());
                return imageView;
            }

            @Override
            public void UpdateUI(Context context, final int position, String data) {
                Glide.with(context).load(data).into(imageView);
            }
        }
    }

    //////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////
    class GridViewHolder extends BaseRecyclerViewHolder<ApiBean, ItemHomeCategoryGridBinding> {

        public GridViewHolder(View view) {
            super(view);
        }

        @Override
        public void onBindViewHolder(ApiBean object, int position) {
            binding.setBack(CommonUtils.randomColor());
            binding.setTitle("《楚乔传》林更新怀抱赵丽颖新高度");
        }
    }

    //////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////
    class TitleViewHolder extends BaseRecyclerViewHolder<ApiBean, ItemHomeCategoryTitleBinding> {

        public TitleViewHolder(View view) {
            super(view);
        }

        @Override
        public void onBindViewHolder(ApiBean object, int position) {

        }
    }
}
