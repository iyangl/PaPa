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

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        double x = (double) 768 / 1242;
        double y = (double) 1280 / 2280;
        System.out.println("x:" + x + "  y:" + y);
        System.out.println("y35 = " + (double) 35 * y);
        System.out.println("y40 = " + (double) 40 * y);
        System.out.println("y45 = " + (double) 45 * y);
        System.out.println("y50 = " + (double) 50 * y);
        System.out.println("");
        System.out.println("x15 = " + (double) 15 * x);
        System.out.println("x35 = " + (double) 35 * x);
        System.out.println("x50 = " + (double) 35 * x);
        System.out.println("x75 = " + (double) 75 * x);
        System.out.println("x110 = " + (double) 110 * x);
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

    @Test
    public void test(){
        String s = "\\u6ca1\\u6709\\u67e5\\u8be2\\u5230";
        System.out.println(s);
    }
}