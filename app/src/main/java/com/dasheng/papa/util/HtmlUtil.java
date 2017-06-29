package com.dasheng.papa.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HtmlUtil {

    public static String getNewContent(String htmltext) {

        Document doc = Jsoup.parse(htmltext);
        Elements elements = doc.getElementsByTag("img");
        for (Element element : elements) {
            element.attr("width", "100%").attr("height", "auto");
            String img = element.attr("src");
            if (!img.startsWith("http:")) {
                element.attr("src", Constant.Api.BASE_URL + img);
            }
        }
        return doc.toString();
    }
}
