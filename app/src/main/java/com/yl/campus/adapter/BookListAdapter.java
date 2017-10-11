package com.yl.campus.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.ViewHolder> {
    private List<Book> bookList;
    private Context context;

    public BookListAdapter(List<Book> bookList, Context context) {
        this.bookList = bookList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_book, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Book book = bookList.get(position);
        holder.bookTitle.setText(book.title);
        String authors = "";
        for (String a : book.author) {
            authors += a + " ";
        }
        holder.bookAuthor.setText(authors);
        Glide.with(context).load(book.image).into(holder.bookImage);
        holder.bookSummary.setText(book.summary);
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView bookImage;
        TextView bookTitle, bookAuthor, bookSummary;

        ViewHolder(View itemView) {
            super(itemView);
            bookImage = (ImageView) itemView.findViewById(R.id.bookImage);
            bookTitle = (TextView) itemView.findViewById(R.id.bookTitle);
            bookAuthor = (TextView) itemView.findViewById(R.id.bookAuthor);
            bookSummary = (TextView) itemView.findViewById(R.id.bookSummary);
        }
    }
}
