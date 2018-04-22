package com.yl.campus.app.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.yl.campus.R;
import com.yl.campus.app.adapter.NewsAdapter;
import com.yl.campus.app.model.News;
import com.yl.campus.app.model.TopNews;
import com.yl.campus.app.presenter.NewsPresenter;
import com.yl.campus.app.view.NewsView;
import com.yl.campus.common.base.BaseActivity;
import com.yl.campus.common.utils.PrefsUtils;
import com.yl.campus.common.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class NewsActivity extends BaseActivity implements NewsView,
        SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.refreshNews)
    SwipeRefreshLayout refreshNews;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    private List<TopNews> topNewses = new ArrayList<>();
    private List<News> newsList = new ArrayList<>();
    private NewsPresenter presenter;
    private NewsAdapter adapter;

    @Override
    protected void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new NewsAdapter(newsList, topNewses);
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
    }

    @Override
    protected void initData() {
        presenter = new NewsPresenter(this);
    }

    @Override
    protected String getDefaultTitle() {
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
        ToastUtils.showToast(this, "正在刷新", 0);
    }

    @Override
    public void endRefresh() {
        if (presenter == null) return;
        refreshNews.setRefreshing(false);
        ToastUtils.showToast(this, "刷新成功", 0);
    }

    @Override
    public void onRefreshFailed() {
        if (presenter == null) return;
        refreshNews.setRefreshing(false);
        ToastUtils.showToast(this, "刷新失败", 0);
    }

    @Override
    public void showMoreNews(List<News> newsList) {
        this.newsList.addAll(newsList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadMore() {
        ToastUtils.showToast(NewsActivity.this, "正在加载", 0);
    }

    @Override
    public void endLoadMore() {
        if (presenter == null) return;
        ToastUtils.showToast(NewsActivity.this, "加载成功", 0);
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
            ToastUtils.showToast(this, "加载失败", 0);
        }
    }

    @Override
    public void jumpToNewsContent(String url) {
        Intent intent = new Intent(this, NewsContentActivity.class);
        intent.putExtra("news_url", url);
        startActivity(intent);
    }

    @Override
    public void saveTopImagePrefs(String imageUrl) {
        PrefsUtils.getEditor(this).putString("homeUrl", imageUrl).apply();
    }

    @Override
    public String getTopImagePrefs() {
        return PrefsUtils.getStringByKey(this, "homeUrl");
    }

    @Override
    public void saveNewsListPrefs(String newsList) {
        PrefsUtils.getEditor(this).putString("newsList", newsList).apply();
    }

    @Override
    public String getNewsListPrefs() {
        return PrefsUtils.getStringByKey(this, "newsList");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter = null;
    }
}
