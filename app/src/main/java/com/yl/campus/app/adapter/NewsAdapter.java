package com.yl.campus.app.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yl.campus.R;
import com.yl.campus.app.model.News;
import com.yl.campus.app.model.TopNews;
import com.yl.campus.app.view.NewsView;
import com.yl.campus.common.base.BaseRVAdapter;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * 新闻展示的适配器
 * Created by Luke on 2017/10/20.
 */

public class NewsAdapter extends BaseRVAdapter<News> {
    private List<TopNews> topNewsList;

    public NewsAdapter(List<News> dataList, List<TopNews> topNewsList) {
        super(dataList, R.layout.item_news, R.layout.item_top_banner,
                R.layout.item_load_more);
        this.topNewsList = topNewsList;
    }

    @Override
    public void bindHeaderLayout(BaseRVAdapter.BaseViewHolder holder) {
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
    public void bindFooterLayout(BaseRVAdapter.BaseViewHolder holder) {

    }

    @Override
    public void bindItemLayout(BaseRVAdapter.BaseViewHolder holder, News news) {
        TextView newsTitle = (TextView) holder.findViewById(R.id.newsTitle);
        TextView newsDate = (TextView) holder.findViewById(R.id.newsDate);
        newsTitle.setText(news.title);
        newsDate.setText(news.date);
    }

    @Override
    public void onItemClick(int position) {
        ((NewsView) getContext()).jumpToNewsContent(getData(position).url);
    }

    private class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load(path).placeholder(R.mipmap.ic_default)
                    .error(R.mipmap.ic_default).into(imageView);
        }
    }
}
