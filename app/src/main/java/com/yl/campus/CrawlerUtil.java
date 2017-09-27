package com.yl.campus;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 网络爬虫工具类
 * Created by Luke on 2017/9/27.
 */

public class CrawlerUtil {
    private final static String imageUrl = "http://www.ahstu.edu.cn";

    public static void getTopImage(final OnCrawlListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document document = Jsoup.connect(imageUrl).get();
                    Elements elements = document.select("div.inner");
                    List<String> topImages = new ArrayList<>();
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

    interface OnCrawlListener {
        void onSucceed(List<String> topImages);

        void onFailed();
    }
}
