package com.yl.campus.app.view;

import com.yl.campus.app.model.Book;

import java.util.List;

/**
 * 书目搜索的功能
 * Created by Luke on 2017/10/11.
 */

public interface BookSearchView {

    void refreshBookList(List<Book> bookList);

    void saveBooksPrefs(String key, String json);

    String getBooksPrefs(String key);

    void onNoSearchResult();

    void onNetworkError();

    void showProgressBar();

    void hideProgressBar();
}
