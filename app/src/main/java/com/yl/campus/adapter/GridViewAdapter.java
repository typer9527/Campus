package com.yl.campus.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yl.campus.R;

/**
 * 功能网格适配器
 * Created by Luke on 2017/9/28.
 */

public class GridViewAdapter extends BaseAdapter {

    private final String[] functionNames = {"新闻展示"};
    private final int[] functionImages = {R.drawable.ic_home};

    @Override
    public int getCount() {
        return functionImages.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(parent.getContext(), R.layout.item_grid_view, null);
        ImageView ivItem = (ImageView) view.findViewById(R.id.itemImage);
        TextView tvItem = (TextView) view.findViewById(R.id.itemName);
        tvItem.setText(functionNames[position]);
        ivItem.setImageResource(functionImages[position]);
        return view;
    }
}
