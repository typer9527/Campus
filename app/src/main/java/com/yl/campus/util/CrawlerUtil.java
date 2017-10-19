package com.yl.campus.util;

import com.yl.campus.model.News;
import com.yl.campus.model.TopNews;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 网页爬取工具类
 * Created by Luke on 2017/9/28.
 */

public class CrawlerUtil {
    public final static String homeUrl = "http://www.ahstu.edu.cn";
    //    public final static String newsUrl = "http://www.ahstu.edu.cn/index/akxw.htm";
    public final static String newsUrl = "http://www.ahstu.edu.cn/index/xydt.htm";

    public static Document requestDocument(String url) {
        Document document = null;
        try {
            document = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return document;
    }

    public static List<TopNews> parseDocToTopNews(Document document) {
        Elements image = document.select("div.inner");
        Elements title = document.select("div.text1");
        List<TopNews> topNewses = new ArrayList<>();
        for (int i = 0; i < image.size(); i++) {
            TopNews topNews = new TopNews();
            topNews.imageUrl = homeUrl + "/" +
                    image.get(i).select("img[src]").attr("src");
            topNews.title = title.get(i).text();
            topNewses.add(topNews);
        }
        return topNewses;
    }

    public static List<News> parseDocToNewsList(Document document) {
        List<News> newsList = new ArrayList<>();
        Elements elements = document.select("a.rigthConBox-conList");
        for (int i = 0; i < elements.size(); i++) {
            News news = new News();
            news.title = elements.get(i).select("span.conListWord").text();
            news.date = elements.get(i).select("span.conListTime").text();
            news.url = homeUrl + "/" + elements.get(i).attr("href");
            newsList.add(news);
        }
        return newsList;
    }

    public static String parseMoreNewsUrl(Document document) {
        Elements elements = document.select("a.Next");
        String url = elements.get(0).attr("href");
        return url.contains("xydt") ? homeUrl + "/index/" + url :
                homeUrl + "/index/xydt/" + url;

    }
}
