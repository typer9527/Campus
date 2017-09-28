package com.yl.campus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.yl.campus.adapter.NewsListAdapter;
import com.yl.campus.R;
import com.yl.campus.model.News;
import com.yl.campus.model.TopNews;
import com.yl.campus.presenter.NewsPresenter;
import com.yl.campus.util.LoadingUtil;
import com.yl.campus.util.ToastUtil;
import com.yl.campus.view.NewsView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EActivity(R.layout.activity_news)
public class NewsActivity extends AppCompatActivity implements NewsView {
    @ViewById
    public Banner banner;
    @ViewById
    public RecyclerView recyclerView;

    private NewsPresenter presenter = new NewsPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.show();
    }

    @Override
    public void showProgressDialog() {
        LoadingUtil.startLoad(this);
    }

    @Override
    public void hideProgressDialog() {
        LoadingUtil.endLoad();
    }

    @Override
    public void showTopNewses(List<TopNews> topNewses) {
        List<String> images = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        for (TopNews topNews : topNewses) {
            images.add(topNews.imageUrl);
            titles.add(topNews.title);
        }
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        banner.setImageLoader(new GlideImageLoader());
        banner.setImages(images);
        banner.setBannerTitles(titles);
        banner.start();
    }

    @Override
    public void showNewsList(List<News> newsList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new NewsListAdapter(newsList, this));
    }

    @Override
    public void onLoadFailed() {
        ToastUtil.showToast(this, "加载失败", 0);
    }

    @Override
    public void jumpToNewsContent(String url) {
        Intent intent = new Intent(this, NewsContentActivity_.class);
        intent.putExtra("news_url", url);
        startActivity(intent);
    }
}
