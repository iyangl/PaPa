package com.dasheng.papa.util;

import android.databinding.BindingAdapter;
import android.text.Html;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dasheng.papa.R;
import com.github.chrisbanes.photoview.PhotoView;

public class DataBindingUtil {

    @BindingAdapter("state")
    public static void setState(RelativeLayout rl, int state) {
        if (rl == null) {
            return;
        }
        if (rl.getChildAt(0) instanceof ImageView) {
            rl.getChildAt(0).setSelected(rl.getId() == state);
        }
        if (rl.getChildAt(1) instanceof TextView) {
            ((TextView) rl.getChildAt(1)).setTextColor(CommonUtils.getColor(rl.getId() == state ?
                    R.color.colorAccent : R.color.text_color_unselected));
        }
    }

    @BindingAdapter("android:src")
    public static void setDrawableById(ImageView iv, int id) {
        if (id == 0) {
            return;
        }
        iv.setImageResource(id);
    }

    @BindingAdapter("imgurl")
    public static void loadImage(ImageView iv, String imgUrl) {
        if (iv instanceof PhotoView) {
            ImageLoader.loadXMLImage(iv.getContext(), imgUrl, iv);
        } else {
            ImageLoader.loadImage(iv.getContext(), imgUrl, iv);
        }
    }

    @BindingAdapter("category")
    public static void loadCategory(ImageView iv, String imgUrl) {
        if (TextUtils.isEmpty(imgUrl)) {
            iv.setImageResource(R.drawable.category_weixin);
            return;
        }
        ImageLoader.loadImage(iv.getContext(), imgUrl, iv);
    }

    @BindingAdapter("color")
    public static void setRadioButtonColor(RadioButton radio, String color) {
        if (radio.isChecked()) {
            radio.setTextColor(CommonUtils.getColor(R.color.white));
        } else {
            radio.setTextColor(CommonUtils.getColor(R.color.rank_title_bg));
        }
    }

    @BindingAdapter("click")
    public static void setClickText(TextView tv, String click) {
        if (!TextUtils.isEmpty(click)) {
            tv.setText(String.format("%s次观看", click));
        }
    }

    @BindingAdapter("text")
    public static void setPraiseText(TextView tv, int count) {
        tv.setText(count + "");
    }

    @BindingAdapter("search")
    public static void setSearchText(TextView tv, String content) {
        tv.setText(Html.fromHtml(content));
    }

}
