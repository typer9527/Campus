package com.yl.campus.app.presenter;

import com.google.gson.Gson;
import com.yl.campus.app.model.Book;
import com.yl.campus.app.model.BookSearchModel;
import com.yl.campus.app.view.BookSearchView;
import com.yl.campus.common.base.BasePresenter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 书目推荐和搜索的数据和结果展示
 * Created by Luke on 2017/10/11.
 */

public class BookSearchPresenter extends BasePresenter<BookSearchView> {

    private final String url = "https://api.douban.com/v2/book/search";
    private BookSearchModel model = new BookSearchModel();
    private String keyword;

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void showBookList() {
        mView.showLoadView("");
        Observable.create(new ObservableOnSubscribe<BookSearchModel>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<BookSearchModel> e) {
                try {
                    if (keyword == null) {
                        getRecommendBooks();
                    } else {
                        getSearchBooks();
                    }
                } catch (IOException ex) {
                    e.onError(ex);
                }
                e.onNext(model);
                e.onComplete();

            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BookSearchModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull BookSearchModel bookSearchModel) {
                        List<Book> bookList = new ArrayList<>();
                        bookList.clear();
                        if (keyword == null) {
                            bookList.addAll(getPartOfAllBooks(
                                    bookSearchModel.getResult()));
                        } else {
                            int count = bookSearchModel.getResult().count;
                            if (count != 0) {
                                bookList.addAll(bookSearchModel.getResult().books);
                            } else {
                                mView.onNoSearchResult();
                            }
                        }
                        mView.hideLoadView();
                        mView.refreshBookList(bookList);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        e.printStackTrace();
                        mView.onNetworkError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void refreshRecommendBooks() {
        String booksPrefs = mView.getBooksPrefs("recommend_books");
        BookSearchModel.SearchResult allBooks = new Gson().fromJson(booksPrefs,
                BookSearchModel.SearchResult.class);
        mView.refreshBookList(getPartOfAllBooks(allBooks));
    }

    private void getSearchBooks() throws IOException {
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder()
                .add("q", keyword)
                .build();
        Request request = new Request.Builder()
                .url(url).post(requestBody).build();
        Response response = client.newCall(request).execute();
        String booksJson = response.body().string();
        BookSearchModel.SearchResult searchResult = new Gson().fromJson(booksJson,
                BookSearchModel.SearchResult.class);
        model.setResult(searchResult);
    }

    private void getRecommendBooks() throws IOException {
        String booksJson = mView.getBooksPrefs("recommend_books");
        if (booksJson == null) {
            OkHttpClient client = new OkHttpClient();
            RequestBody requestBody = new FormBody.Builder()
                    .add("tag", "经典")
                    .add("count", "100")
                    .build();
            Request request = new Request.Builder()
                    .url(url).post(requestBody).build();
            Response response = client.newCall(request).execute();
            booksJson = response.body().string();
            // 推荐阅读数目，存储Json至本地
            mView.saveBooksPrefs("recommend_books", booksJson);
        }
        BookSearchModel.SearchResult allBooks = new Gson().fromJson(booksJson,
                BookSearchModel.SearchResult.class);
        model.setResult(allBooks);
    }

    // 随机从所有推荐书目中随机选出10本
    private List<Book> getPartOfAllBooks(BookSearchModel.SearchResult allBooks) {
        List<Book> books = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int position = new Random().nextInt(allBooks.count);
            books.add(allBooks.books.get(position));
        }
        return books;
    }
}
