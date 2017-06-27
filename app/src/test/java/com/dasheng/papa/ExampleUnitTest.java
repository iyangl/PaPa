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

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public void test() {
        String str = "<p><span style=\\\"font-size: 14px;" +
                "\\\">有时是苗医传人，有时是蒙医传人，偶尔还会客串一下北大专家和医院退休的老院长……由于刘洪滨出现在电视画面中的身份完全不同，也被网友戏称为“虚假医药广告表演艺术家”。</span></p><p " +
                "style=\\\"text-align:center\\\"><span style=\\\"font-size: 14px;\\\"><img " +
                "src=\\\"/upload/a/170622/14981137107332.png\\\" title=\\\"神药专家假代言.png\\\" alt=\\\"神药专家假代言.png\\\" " +
                "height=\\\"225\\\" width=\\\"350\\\"/></span></p><p><strong><span style=\\\"font-size: 14px;" +
                "\\\">神药专家被揭底 代言假药月销近百万元</span></strong></p><p><span style=\\\"font-size: 14px;" +
                "\\\">刘洪滨节目中推销的产品留下了一长串的不良记录，而刘洪滨所谓的供职单位昨天均表示查无此人。北京青年报记者调查发现，刘洪滨参与推广的“药王风痛方”已经被认定为假药，刘洪滨背后的公司如何通过电视节目包装销售假药的流程也浮出水面，在两个月左右的时间里，仅刘洪滨“代言”的一个产品就曾卖出近200万的销售额。先后出现在西藏卫视、青海卫视、甘肃卫视、东南卫视、辽宁卫视、吉林卫视和黑龙江电视台等多家省市级电视台的“健康节目”中的她，因每次出现时的身份不同，所推销的药品名称、功效也不同，而被网友戏称为“虚假医药广告表演艺术家”。" +
                "</span></p><p style=\\\"text-align:center\\\"><span style=\\\"font-size: 14px;\\\">" +
                "<img src=\\\"/upload/a/170622/14981138321242.png\\\" title=\\\"神药专家.png\\\" alt=\\\"神药专家.png\\\" height=\\\"274\\\" width=\\\"350\\\"/></span></p><p><strong><span style=\\\"font-size: 14px;\\\">神药专家被揭底 广告产品仍在销售 刘洪滨竟有“分身”</span></strong></p><p style=\\\"text-align:center\\\"><img src=\\\"/upload/a/170622/14981138655141.png\\\" title=\\\"14981138655141.png\\\" alt=\\\"14981138655141.png\\\" height=\\\"185\\\" width=\\\"350\\\"/></p><p><span style=\\\"font-size: 14px;\\\">一名自称是“刘洪滨教授”的女性告诉北青报记者，她现在正在吉林省长春市坐诊，就是“健康节目”中的“刘老师”，是蒙古医生世家的第五代传人。她说，无论有哪些症状，只要使用“蒙药心脑丸”，用药百天之后保证能够药到病除，完全康复。北青报记者表示，想要与“刘老师”见面沟通病情时，“刘老师”小声避开话筒对他人说“他想过来”，旁边人同样小声地回复，“别让他来”。随后“刘老师”以自己年岁过高，精力有限，在指导中心不能接待患者，并且明天就要回内蒙古，没有时间进行推脱，拒绝见面。刘洪滨到底是谁？昨天下午，北京大学、吉林省人民医院、首都医科大学附属北京地坛医院均表示查无此人。北青报记者还留意到，刘洪滨所参与的“健康节目”中提及她是中华中医医学会镇咳副会长、东方咳嗽研究院副院长、中华中医医学会风湿分会委员。但是，民政部社会组织管理局全国性社会组织查询系统中查询不到有东方咳嗽研究院和中华中医医学会。昨天下午，北京市卫计委表示，所谓的“神药专家”刘洪滨（斌）北京市中医药管理局已查实，无此人。</span></p><p><br/></p>";
//        str = str.replaceAll("src=\"(.*?)/([\\w.]+)\"","src=\"../image/$2\"");
        str = str.replaceAll("/upload/a/","http://118.89.233.35:9090/upload/a/");
        System.out.println("imageSrc:" + str);
    }

    public static String replaceHtmlTag(String str, String beforeTag,
                                        String tagAttrib, String startTag, String endTag) {
        //  String regxpForTag = "<//s*" + beforeTag + "//s+([^>]*)//s*>" ;
        String regxpForTag = "<//s*" + beforeTag + "//s+([^>]*)//s*";
        String regxpForTagAttrib = tagAttrib + "=//s*/' ([ ^/']+)/\' ";
        //Pattern.CASE_INSENSITIVE 忽略大小写
        Pattern patternForTag = Pattern.compile(regxpForTag, Pattern.CASE_INSENSITIVE);
        Pattern patternForAttrib = Pattern.compile(regxpForTagAttrib, Pattern.CASE_INSENSITIVE);
        Matcher matcherForTag = patternForTag.matcher(str);
        StringBuffer sb = new StringBuffer();
        boolean result = matcherForTag.find();
        // 循环找出每个 img 标签
        while (result) {
            StringBuffer sbreplace = new StringBuffer("<img ");
            Matcher matcherForAttrib = patternForAttrib.matcher(matcherForTag
                    .group(1));
            if (matcherForAttrib.find()) {
                matcherForAttrib.appendReplacement(sbreplace, startTag
                        + matcherForAttrib.group(1) + endTag);
            }
            matcherForAttrib.appendTail(sbreplace);
            matcherForTag.appendReplacement(sb, sbreplace.toString());
            result = matcherForTag.find();
        }
        matcherForTag.appendTail(sb);
        return sb.toString();
    }

    public static List<String> getImageSrc(String htmlCode) {
        List<String> imageSrcList = new ArrayList<String>();
        Pattern p = Pattern.compile("<img\\b[^>]*\\bsrc\\b\\s*=\\s*('|\")?([^'\"\n\r\f>]+(\\.jpg|\\.bmp|\\.eps|\\" +
                ".gif|\\.mif|\\.miff|\\.png|\\.tif|\\.tiff|\\.svg|\\.wmf|\\.jpe|\\.jpeg|\\.dib|\\.ico|\\.tga|\\" +
                ".cut|\\.pic)\\b)[^>]*>", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(htmlCode);
        String quote = null;
        String src = null;
        while (m.find()) {
            quote = m.group(1);
            System.out.println("quote:" + quote);
            // src=https://sms.reyo.cn:443/temp/screenshot/zY9Ur-KcyY6-2fVB1-1FSH4.png
            src = (quote == null || quote.trim().length() == 0) ? m.group(2).split("\\s+")[0] : m.group(2);
            imageSrcList.add(src);

        }
        return imageSrcList;
    }

    public static List getImgStr(String htmlStr) {
        String img = "";
        Pattern p_image;
        Matcher m_image;
        List pics = new ArrayList();
        String regEx_img = "]*?>";
        p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
        m_image = p_image.matcher(htmlStr);
        while (m_image.find()) {
            img = img + "," + m_image.group();
            Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
            while (m.find()) {
                pics.add(m.group(1));
            }
        }
        return pics;
    }
}