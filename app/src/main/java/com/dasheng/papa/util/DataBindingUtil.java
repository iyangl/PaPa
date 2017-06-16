package com.dasheng.papa.util;

import android.databinding.BindingAdapter;
import android.widget.ImageView;
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
}
