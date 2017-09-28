package com.yl.campus.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yl.campus.R;
import com.yl.campus.model.News;
import com.yl.campus.view.NewsView;

import java.util.List;

/**
 * 新闻列表的适配器
 * Created by Luke on 2017/9/28.
 */

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder> {
    private List<News> newsList;
    private NewsView newsView;

    public NewsListAdapter(List<News> newsList, NewsView view) {
        this.newsList = newsList;
        this.newsView = view;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                newsView.jumpToNewsContent(newsList.get(position).url);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        News news = newsList.get(position);
        holder.newsTitle.setText(news.title);
        holder.newsDate.setText(news.date);
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        TextView newsTitle;
        TextView newsDate;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            newsTitle = (TextView) itemView.findViewById(R.id.newsTitle);
            newsDate = (TextView) itemView.findViewById(R.id.newsDate);
        }
    }
}
