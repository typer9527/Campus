package com.yl.campus.app.activity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.yl.campus.R;
import com.yl.campus.app.adapter.BookListAdapter;
import com.yl.campus.app.model.Book;
import com.yl.campus.app.presenter.BookSearchPresenter;
import com.yl.campus.app.view.BookSearchView;
import com.yl.campus.common.base.BaseActivity;
import com.yl.campus.common.utils.PrefsUtils;
import com.yl.campus.common.utils.SearchViewHelper;
import com.yl.campus.common.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class BookSearchActivity extends BaseActivity implements
        BookSearchView, View.OnClickListener, SearchView.OnQueryTextListener,
        View.OnFocusChangeListener {

    @BindView(R.id.searchView)
    SearchView searchView;
    @BindView(R.id.bookListText)
    TextView bookListText;
    @BindView(R.id.refreshBookText)
    TextView refreshBookText;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    List<Book> bookList;
    BookListAdapter adapter;
    BookSearchPresenter presenter = new BookSearchPresenter(this);

    @Override
    protected int getLayoutId() {
        return R.layout.activity_book_search;
    }

    @Override
    protected String getDefaultTitle() {
        return getString(R.string.book_search);
    }

    @Override
    public void initView() {
        searchView.setIconifiedByDefault(false); // 设置总显示搜索图标
        searchView.onActionViewExpanded(); // 设置默认展开
        SearchViewHelper.setUnderLineColor(searchView, Color.TRANSPARENT);
        searchView.clearFocus(); // 设置消除焦点，保证默认展开时键盘关闭
        searchView.setOnQueryTextFocusChangeListener(this);
        searchView.setOnQueryTextListener(this);
        refreshBookText.setOnClickListener(this);
        bookList = new ArrayList<>();
        adapter = new BookListAdapter(bookList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        presenter.showBookList();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.refreshBookText:
                presenter.refreshRecommendBooks();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        presenter.setKeyword(query);
        presenter.showBookList();
        searchView.clearFocus();
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            bookListText.setText(getString(R.string.search_result));
            refreshBookText.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
        } else {
            bookList.clear();
            recyclerView.setVisibility(View.VISIBLE);
            // 表示退出搜索，展示推荐书目
            if (searchView.getQuery().length() == 0) {
                presenter.refreshRecommendBooks();
                bookListText.setText(getString(R.string.recommend_book));
                refreshBookText.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void refreshBookList(List<Book> bookList) {
        this.bookList.clear();
        this.bookList.addAll(bookList);
        adapter.notifyDataSetChanged();
        recyclerView.scrollToPosition(0);
    }

    @Override
    public void saveBooksPrefs(String key, String json) {
        SharedPreferences.Editor editor = PrefsUtils.getEditor(this);
        editor.putString(key, json).apply();
    }

    @Override
    public String getBooksPrefs(String key) {
        return PrefsUtils.getStringByKey(this, key);
    }

    @Override
    public void onNoSearchResult() {
        ToastUtils.showToast(this, "无搜索结果", 0);
    }

    @Override
    public void onNetworkError() {
        ToastUtils.showToast(this, "网络错误", 0);
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }
}
