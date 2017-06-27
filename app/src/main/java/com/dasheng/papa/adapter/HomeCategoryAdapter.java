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
import com.dasheng.papa.R;
import com.dasheng.papa.base.BaseRecyclerViewAdapter;
import com.dasheng.papa.base.BaseRecyclerViewHolder;
import com.dasheng.papa.bean.ApiListResBean;
import com.dasheng.papa.bean.ResponseItemBean;
import com.dasheng.papa.databinding.ItemHomeCategoryBannerBinding;
import com.dasheng.papa.databinding.ItemHomeCategoryGridBinding;
import com.dasheng.papa.databinding.ItemHomeCategoryTitleBinding;
import com.dasheng.papa.util.CommonUtils;
import com.dasheng.papa.util.ImageLoader;

import java.util.List;

public class HomeCategoryAdapter extends BaseRecyclerViewAdapter<Object> implements View.OnClickListener {
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
            grid.setOnClickListener(this);
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
        if (position == 1) {
            return TYPE_TITLE;
        }
        return TYPE_GRID;
    }

    @Override
    public int getItemCount() {
        return data.size();
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

    public void addData(ApiListResBean<ResponseItemBean> apiBean, boolean isClear) {
        if (isClear) {
            data.clear();
            data.add(apiBean);
            data.add(null);
        }
        data.addAll(apiBean.getRes());
        notifyDataSetChanged();
    }

    public void addData(ApiListResBean<ResponseItemBean> apiBean) {
        addData(apiBean, false);
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            int position = (int) v.getTag();
            if (data.size() > position && data.get(position) != null &&
                    data.get(position) instanceof ResponseItemBean) {
                listener.onClick(data.get(position), position);
            }
        }
    }

    //////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////
    class BannerViewHolder extends BaseRecyclerViewHolder<Object, ItemHomeCategoryBannerBinding> {

        public BannerViewHolder(View view) {
            super(view);
        }

        @Override
        public void onBindViewHolder(Object apiBean, int position) {
            if (apiBean instanceof ApiListResBean) {
                final List banner = ((ApiListResBean) apiBean).getBanner();
                if (banner == null || banner.size() <= 0 ||
                        !(banner.get(0) instanceof ResponseItemBean)) {
                    return;
                }
                binding.banner.setPages(
                        new CBViewHolderCreator<LocalImageHolderView>() {
                            @Override
                            public LocalImageHolderView createHolder() {
                                return new LocalImageHolderView();
                            }
                        }, banner)
                        .setPageIndicator(new int[]{R.drawable.shape_page_indicator,
                                R.drawable.shape_page_indicator_focused})
                        .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
                        .setCanLoop(true);
                if (binding.banner.isCanLoop() && !binding.banner.isTurning() && banner.size() > 1) {
                    binding.banner.startTurning(3000);
                }
                binding.banner.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        listener.onClick(banner.get(position), position);
                    }
                });
            }
        }

        public class LocalImageHolderView implements Holder<ResponseItemBean> {
            private ImageView imageView;

            @Override
            public View createView(Context context) {
                imageView = new ImageView(context);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                return imageView;
            }

            @Override
            public void UpdateUI(Context context, final int position, ResponseItemBean data) {
                ImageLoader.loadImage(context, data.getImg(), imageView);
            }
        }
    }

    //////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////
    class GridViewHolder extends BaseRecyclerViewHolder<Object, ItemHomeCategoryGridBinding> {

        public GridViewHolder(View view) {
            super(view);
        }

        @Override
        public void onBindViewHolder(Object object, int position) {
            if (object instanceof ResponseItemBean) {
                binding.setResponse((ResponseItemBean) object);
            }
        }
    }

    //////////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////////////
    class TitleViewHolder extends BaseRecyclerViewHolder<Object, ItemHomeCategoryTitleBinding> {
        private int type_id;

        public TitleViewHolder(View view, int type_id) {
            super(view);
            this.type_id = type_id;
        }

        public TitleViewHolder(View view) {
            super(view);
        }

        @Override
        public void onBindViewHolder(Object object, int position) {
            binding.iconGoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}
