package com.dasheng.papa.util;

import android.databinding.BindingAdapter;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dasheng.papa.R;

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
        if (TextUtils.isEmpty(imgUrl)) {
            iv.setBackgroundColor(CommonUtils.randomColor());
        }
    }

    @BindingAdapter("color")
    public static void setRadioButtonColor(RadioButton radio, String color) {
        if (radio.isChecked()) {
            radio.setTextColor(CommonUtils.getColor(R.color.white));
        } else {
            radio.setTextColor(CommonUtils.getColor(R.color.rank_title_bg));
        }
    }
}
