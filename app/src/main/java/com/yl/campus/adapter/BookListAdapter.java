package com.yl.campus.adapter;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yl.campus.R;
import com.yl.campus.model.Book;

import java.util.List;

/**
 * 书目搜索结果适配器
 * Created by Luke on 2017/10/10.
 */

public class BookListAdapter extends SuperAdapter<Book> {

    public BookListAdapter(List<Book> bookList) {
        super(bookList, R.layout.item_book, -1, -1);
    }

    @Override
    void bindHeaderLayout(SuperAdapter.BaseViewHolder holder) {

    }

    @Override
    void bindFooterLayout(SuperAdapter.BaseViewHolder holder) {

    }

    @Override
    void bindItemLayout(SuperAdapter.BaseViewHolder holder, Book book) {
        ImageView bookImage = (ImageView) holder.findViewById(R.id.bookImage);
        TextView bookTitle = (TextView) holder.findViewById(R.id.bookTitle);
        TextView bookAuthor = (TextView) holder.findViewById(R.id.bookAuthor);
        TextView bookSummary = (TextView) holder.findViewById(R.id.bookSummary);
        bookTitle.setText(book.title);
        String authors = "";
        for (String a : book.author) {
            authors += a + " ";
        }
        bookAuthor.setText(authors);
        Glide.with(getContext()).load(book.image).into(bookImage);
        bookSummary.setText(book.summary);
    }
}
