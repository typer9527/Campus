package com.yl.campus.model;

import java.util.List;

/**
 * 图书搜索的数据模型
 * Created by Luke on 2017/10/10.
 */

public class BookSearchModel {
    private SearchResult result;

    public SearchResult getResult() {
        return result;
    }

    public void setResult(SearchResult result) {
        this.result = result;
    }

    public class SearchResult {
        public int count;
        public List<Book> books;
    }
}
