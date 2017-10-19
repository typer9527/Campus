package com.yl.campus.presenter;

import com.yl.campus.model.NewsModel;
import com.yl.campus.util.CrawlerUtil;
import com.yl.campus.view.NewsView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 处理新闻的model和view
 * Created by Luke on 2017/9/27.
 */

public class NewsPresenter {

    private NewsView view;
    private NewsModel model;
    private Document topImageDoc;
    private Document newsListDoc;
    private Document moreNewsDoc;
    private boolean isOnRefresh;
    public boolean isOnLoadMore = false;

    public NewsPresenter(NewsView view) {
        this.view = view;
        model = new NewsModel();
        handleNewsData();
    }

    public void refreshNews(boolean isOnRefresh) {
        if (isOnRefresh) {
            this.isOnRefresh = true;
            handleNewsData();
        }
    }

    public void loadMoreNews(boolean isOnLoadMore) {
        if (isOnLoadMore) {
            this.isOnLoadMore = true;
            if (moreNewsDoc == null) {
                moreNewsDoc = newsListDoc;
            }
            handleNewsData();
        }
    }

    private void handleNewsData() {
        if (!isOnRefresh && !isOnLoadMore)
            view.showProgressBar();
        Observable.create(new ObservableOnSubscribe<NewsModel>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<NewsModel> e)
                    throws Exception {
                String topImage = view.getTopImagePrefs();
                String newsList = view.getNewsListPrefs();
                if (isOnRefresh) {
                    getNewsFromServer();
                }
                if (isOnLoadMore) {
                    String moreNewsUrl = CrawlerUtil.parseMoreNewsUrl(moreNewsDoc);
                    if (moreNewsUrl == null) e.onError(new Throwable());
                    moreNewsDoc = CrawlerUtil.requestDocument(moreNewsUrl);
                    model.setNewsList(CrawlerUtil.parseDocToNewsList(moreNewsDoc));
                    if (model.getNewsList() == null) e.onError(new Throwable());
                    e.onNext(model);
                    e.onComplete();
                }
                if (!isOnRefresh && !isOnLoadMore && topImage != null &&
                        newsList != null) {
                    topImageDoc = Jsoup.parse(topImage);
                    newsListDoc = Jsoup.parse(newsList);
                } else {
                    getNewsFromServer();
                }
                model.setTopNewses(CrawlerUtil.parseDocToTopNews(topImageDoc));
                model.setNewsList(CrawlerUtil.parseDocToNewsList(newsListDoc));
                if (model.isNull()) {
                    e.onError(new Throwable());
                }
                e.onNext(model);
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NewsModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull NewsModel model) {
                        if (isOnLoadMore) {
                            view.showMoreNews(model.getNewsList());
                            return;
                        }
                        view.refreshNews(model.getTopNewses(), model.getNewsList());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        if (isOnRefresh) {
                            view.onRefreshFailed();
                            isOnRefresh = false;
                            return;
                        }
                        if (isOnLoadMore) {
                            view.onLoadFailed();
                            isOnLoadMore = false;
                            return;
                        }
                        view.hideProgressDialog();
                        view.onLoadFailed();
                    }

                    @Override
                    public void onComplete() {
                        if (isOnRefresh) {
                            isOnRefresh = false;
                            view.endRefresh();
                            return;
                        }
                        if (isOnLoadMore) {
                            isOnLoadMore = false;
                            view.endLoadMore();
                            return;
                        }
                        view.hideProgressDialog();
                    }
                });
    }

    private void getNewsFromServer() {
        topImageDoc = CrawlerUtil.requestDocument(CrawlerUtil.homeUrl);
        newsListDoc = CrawlerUtil.requestDocument(CrawlerUtil.newsUrl);
        // 缓存
        view.saveTopImagePrefs(topImageDoc.toString());
        view.saveNewsListPrefs(newsListDoc.toString());
    }
}
