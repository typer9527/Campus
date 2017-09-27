package com.yl.campus.model;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 新闻图片和列表
 * Created by Luke on 2017/9/27.
 */

public class NewsModel {

    private final static String imageUrl = "http://www.ahstu.edu.cn";
    private final static String newsUrl = "http://www.ahstu.edu.cn/index/akxw.htm";
    private List<String> topImages = new ArrayList<>();
    private List<News> newsList = new ArrayList<>();

    public void getTopImage(final OnGetImageListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document document = Jsoup.connect(imageUrl).get();
                    Elements elements = document.select("div.inner");
                    for (int i = 0; i < elements.size(); i++) {
                        String topImage = imageUrl + "/" +
                                elements.get(i).select("img[src]").attr("src");
                        topImages.add(topImage);
                    }
                    listener.onSucceed(topImages);
                } catch (IOException e) {
                    e.printStackTrace();
                    listener.onFailed();
                }
            }
        }).start();
    }

    public void getNewsList(final OnGetNewsListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document document = Jsoup.connect(newsUrl).get();
                    Elements elements = document.select("a.rigthConBox-conList");
                    for (int i = 0; i < elements.size(); i++) {
                        News news = new News();
                        news.title = elements.get(i).select("span.conListWord").text();
                        news.date = elements.get(i).select("span.conListTime").text();
                        news.url = imageUrl + "/" + elements.get(i).attr("href");
                        Log.e("news", "run: " + news.toString());
                    }
                    listener.onSucceed(newsList);
                } catch (IOException e) {
                    e.printStackTrace();
                    listener.onFailed();
                }
            }
        }).start();
    }

}
