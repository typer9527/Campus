package com.yl.campus.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
 * 新闻列表的适配器
 * Created by Luke on 2017/9/28.
 */

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_NORMAL = 1;
    private static final int TYPE_FOOTER = 2;
    private List<TopNews> topNewsList;
    private List<News> newsList;
    private NewsView newsView;
    private View topBannerView;
    private View loadMoreView;

    public NewsListAdapter(List<TopNews> topNewsList,
                           List<News> newsList, NewsView newsView) {
        this.topNewsList = topNewsList;
        this.newsList = newsList;
        this.newsView = newsView;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        }
        if (position == newsList.size() + 1) {
            return TYPE_FOOTER;
        }
        return TYPE_NORMAL;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            topBannerView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_top_banner, parent, false);
            return new ViewHolder(topBannerView);
        }
        if (viewType == TYPE_FOOTER) {
            loadMoreView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_load_more, parent, false);
            return new ViewHolder(loadMoreView);
        }
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition() - 1;
                newsView.jumpToNewsContent(newsList.get(position).url);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case TYPE_HEADER:
                List<String> images = new ArrayList<>();
                List<String> titles = new ArrayList<>();
                for (TopNews topNews : topNewsList) {
                    images.add(topNews.imageUrl);
                    titles.add(topNews.title);
                }
                holder.banner.setBannerStyle(
                        BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
                holder.banner.setImageLoader(new GlideImageLoader());
                holder.banner.setImages(images);
                holder.banner.setBannerTitles(titles);
                holder.banner.start();
                break;
            case TYPE_NORMAL:
                int realPos = holder.getLayoutPosition() - 1;
                News news = newsList.get(realPos);
                holder.newsTitle.setText(news.title);
                holder.newsDate.setText(news.date);
                break;
            case TYPE_FOOTER:
                break;
            default:
        }
    }

    @Override
    public int getItemCount() {
        return newsList.size() + 2;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        Banner banner;
        ProgressBar progressBar;
        TextView loadMoreText;
        View itemView;
        TextView newsTitle;
        TextView newsDate;

        ViewHolder(View itemView) {
            super(itemView);
            if (itemView == topBannerView) {
                banner = (Banner) itemView.findViewById(R.id.banner);
            }
            if (itemView == loadMoreView) {
                progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
                loadMoreText = (TextView) itemView.findViewById(R.id.loadMoreText);
            }
            this.itemView = itemView;
            newsTitle = (TextView) itemView.findViewById(R.id.newsTitle);
            newsDate = (TextView) itemView.findViewById(R.id.newsDate);
        }
    }

    private class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load(path).placeholder(R.drawable.ic_default)
                    .error(R.drawable.ic_default).into(imageView);
        }
    }
}
