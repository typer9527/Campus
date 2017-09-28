package com.yl.campus.presenter;

import com.yl.campus.model.NewsModel;
import com.yl.campus.util.CrawlerUtil;
import com.yl.campus.view.NewsView;

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

    public NewsPresenter(NewsView view) {
        this.view = view;
    }

    public void show() {
        view.showProgressDialog();
        Observable.create(new ObservableOnSubscribe<NewsModel>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<NewsModel> e)
                    throws Exception {
                NewsModel model = new NewsModel();
                model.setTopNewses(CrawlerUtil.requestTopNews());
                model.setNewsList(CrawlerUtil.requestNewsInfo());
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
                        view.showTopNewses(model.getTopNewses());
                        view.showNewsList(model.getNewsList());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.hideProgressDialog();
                        view.onLoadFailed();
                    }

                    @Override
                    public void onComplete() {
                        view.hideProgressDialog();
                    }
                });
    }
}
