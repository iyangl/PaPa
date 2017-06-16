package com.dasheng.papa;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    public static StateListDrawable getStateListDrawable(Drawable normal) {
        StateListDrawable listDrawable = new StateListDrawable();
        Bitmap srcBitmap = ((BitmapDrawable) normal).getBitmap();
        Bitmap bmp = Bitmap.createBitmap(srcBitmap.getWidth(),
                srcBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        int brightness = 60 - 127;
        ColorMatrix cMatrix = new ColorMatrix();
        cMatrix.set(new float[]{1, 0, 0, 0, brightness, 0, 1, 0, 0,
                brightness, 0, 1, 0, brightness, 0, 0, 0, 1, 0}); // 改变亮度
        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(cMatrix));
        Canvas canvas = new Canvas(bmp);
        // 在Canvas上绘制一个Bitmap
        canvas.drawBitmap(srcBitmap, 0, 0, paint);
        Drawable pressed = new BitmapDrawable(bmp);
        listDrawable.addState(
                new int[]{android.R.attr.state_pressed}, pressed);
        listDrawable.addState(
                new int[]{android.R.attr.state_selected}, pressed);
        listDrawable.addState(
                new int[]{android.R.attr.state_enabled}, normal);
        return listDrawable;
    }
}