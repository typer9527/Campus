package com.yl.campus.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.yl.campus.R;
import com.yl.campus.adapter.NewsListAdapter;
import com.yl.campus.model.News;
import com.yl.campus.model.TopNews;
import com.yl.campus.presenter.NewsPresenter;
import com.yl.campus.util.PrefsUtil;
import com.yl.campus.util.ToastUtil;
import com.yl.campus.view.NewsView;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EActivity
public class NewsActivity extends BaseActivity implements NewsView,
        SwipeRefreshLayout.OnRefreshListener {
    @ViewById
    SwipeRefreshLayout refreshNews;
    @ViewById
    RecyclerView recyclerView;
    @ViewById
    ProgressBar progressBar;
    private List<TopNews> topNewses = new ArrayList<>();
    private List<News> newsList = new ArrayList<>();
    private NewsPresenter presenter;
    private NewsListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new NewsListAdapter(topNewses, newsList, this);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int scrollState; // 滑动状态

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                scrollState = newState;
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (!(scrollState == RecyclerView.SCROLL_STATE_IDLE) &&
                        !recyclerView.canScrollVertically(1)) {
                    // 加载更多新闻
                    onLoadMore();
                    if (!presenter.isOnLoadMore)
                        presenter.loadMoreNews(true);
                }
            }
        });
        refreshNews.setColorSchemeColors(Color.parseColor("#3F51B5"));
        refreshNews.setOnRefreshListener(this);
        presenter = new NewsPresenter(this);
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
    public void onRefresh() {
        onRefreshing();
        presenter.refreshNews(true);
    }

    @Override
    public void onRefreshing() {
        refreshNews.setRefreshing(true);
        ToastUtil.showToast(this, "正在刷新", 0);
    }

    @Override
    public void endRefresh() {
        refreshNews.setRefreshing(false);
        ToastUtil.showToast(this, "刷新成功", 0);
    }

    @Override
    public void onRefreshFailed() {
        refreshNews.setRefreshing(false);
        ToastUtil.showToast(this, "刷新失败", 0);
    }

    @Override
    public void showMoreNews(List<News> newsList) {
        this.newsList.addAll(newsList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadMore() {
        ToastUtil.showToast(NewsActivity.this, "正在加载", 0);
    }

    @Override
    public void endLoadMore() {
        ToastUtil.showToast(NewsActivity.this, "加载成功", 0);
    }

    @Override
    public void refreshNews(List<TopNews> topNewses, List<News> newsList) {
        this.topNewses.clear();
        this.topNewses.addAll(topNewses);
        this.newsList.clear();
        this.newsList.addAll(newsList);
        adapter.notifyDataSetChanged();
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
        PrefsUtil.getEditor(this).putString("homeUrl", imageUrl).apply();
    }

    @Override
    public String getTopImagePrefs() {
        return PrefsUtil.getStringByKey(this, "homeUrl");
    }

    @Override
    public void saveNewsListPrefs(String newsList) {
        PrefsUtil.getEditor(this).putString("newsList", newsList).apply();
    }

    @Override
    public String getNewsListPrefs() {
        return PrefsUtil.getStringByKey(this, "newsList");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter = null;
    }
}
