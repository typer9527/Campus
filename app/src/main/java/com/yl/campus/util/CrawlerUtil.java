package com.yl.campus.util;

import android.util.Log;

import com.yl.campus.model.News;
import com.yl.campus.model.TopNews;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * 网页爬取工具类
 * Created by Luke on 2017/9/28.
 */

public class CrawlerUtil {
    private final static String imageUrl = "http://www.ahstu.edu.cn";
    private final static String newsUrl = "http://www.ahstu.edu.cn/index/akxw.htm";

    public static List<TopNews> requestTopNews() {
        List<TopNews> topNewses = null;
        try {
            Document document = Jsoup.connect(imageUrl).get();
            Elements image = document.select("div.inner");
            Elements title = document.select("div.text1");
            topNewses = new ArrayList<>();
            for (int i = 0; i < image.size(); i++) {
                TopNews topNews = new TopNews();
                topNews.imageUrl = imageUrl + "/" +
                        image.get(i).select("img[src]").attr("src");
                topNews.title = title.get(i).text();
                topNewses.add(topNews);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return topNewses;
    }

    public static List<News> requestNewsInfo() {
        List<News> newsList = null;
        try {
            Document document = Jsoup.connect(newsUrl).get();
            Elements elements = document.select("a.rigthConBox-conList");
            newsList = new ArrayList<>();
            for (int i = 0; i < elements.size(); i++) {
                News news = new News();
                news.title = elements.get(i).select("span.conListWord").text();
                news.date = elements.get(i).select("span.conListTime").text();
                news.url = imageUrl + "/" + elements.get(i).attr("href");
                newsList.add(news);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newsList;
    }
}
