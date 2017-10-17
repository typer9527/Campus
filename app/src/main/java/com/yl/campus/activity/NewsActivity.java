package com.yl.campus.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.yl.campus.R;
import com.yl.campus.adapter.NewsListAdapter;
import com.yl.campus.model.News;
import com.yl.campus.model.TopNews;
import com.yl.campus.presenter.NewsPresenter;
import com.yl.campus.util.PrefsUtil;
import com.yl.campus.util.ToastUtil;
import com.yl.campus.view.NewsView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EActivity
public class NewsActivity extends BaseActivity implements NewsView {
    @ViewById
    Banner banner;
    @ViewById
    RecyclerView recyclerView;
    @ViewById
    ProgressBar progressBar;

    private NewsPresenter presenter = new NewsPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.show();
    }

    @Override
    protected String getToolbarTitle() {
        return getString(R.string.news_page);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_news;
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressDialog() {
        progressBar.setVisibility(View.GONE);
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
        if (presenter != null) {
            ToastUtil.showToast(this, "加载失败", 0);
        }
    }

    @Override
    public void jumpToNewsContent(String url) {
        Intent intent = new Intent(this, NewsContentActivity_.class);
        intent.putExtra("news_url", url);
        startActivity(intent);
    }

    @Override
    public void saveTopImagePrefs(String imageUrl) {
        PrefsUtil.getEditor(this).putString("imageUrl", imageUrl).apply();
    }

    @Override
    public String getTopImagePrefs() {
        return PrefsUtil.getStringByKey(this, "imageUrl");
    }

    @Override
    public void saveNewsListPrefs(String newsList) {
        PrefsUtil.getEditor(this).putString("newsList", newsList).apply();
    }

    @Override
    public String getNewsListPrefs() {
        return PrefsUtil.getStringByKey(this, "newsList");
    }

    private class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load(path).placeholder(R.drawable.ic_default)
                    .error(R.drawable.ic_default).into(imageView);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter = null;
    }
}
