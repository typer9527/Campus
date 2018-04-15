package com.yl.campus.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yl.campus.R;
import com.yl.campus.model.News;
import com.yl.campus.model.TopNews;
import com.yl.campus.view.NewsView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 新闻展示的适配器
 * Created by Luke on 2017/10/20.
 */

public class NewsAdapter extends SuperAdapter<News> {
    private List<TopNews> topNewsList;

    public NewsAdapter(List<News> dataList, List<TopNews> topNewsList) {
        super(dataList, R.layout.item_news, R.layout.item_top_banner,
                R.layout.item_load_more);
        this.topNewsList = topNewsList;
    }

    @Override
    void bindHeaderLayout(SuperAdapter.BaseViewHolder holder) {
        Banner banner = (Banner) holder.findViewById(R.id.banner);
        List<String> images = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        for (TopNews topNews : topNewsList) {
            images.add(topNews.imageUrl);
            titles.add(topNews.title);
        }
        banner.setDelayTime(3500);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        banner.setImageLoader(new GlideImageLoader());
        banner.setImages(images);
        banner.setBannerTitles(titles);
        banner.start();
    }

    @Override
    void bindFooterLayout(SuperAdapter.BaseViewHolder holder) {

    }

    @Override
    void bindItemLayout(SuperAdapter.BaseViewHolder holder, News news) {
        TextView newsTitle = (TextView) holder.findViewById(R.id.newsTitle);
        TextView newsDate = (TextView) holder.findViewById(R.id.newsDate);
        newsTitle.setText(news.title);
        newsDate.setText(news.date);
    }

    @Override
    void onItemClick(int position) {
        ((NewsView) getContext()).jumpToNewsContent(getData(position).url);
    }

    private class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load(path).placeholder(R.drawable.ic_default)
                    .error(R.drawable.ic_default).into(imageView);
        }
    }
}
