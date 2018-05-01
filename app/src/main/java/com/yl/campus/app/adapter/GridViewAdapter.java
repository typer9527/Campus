package com.yl.campus.app.adapter;

import android.annotation.SuppressLint;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.yl.campus.R;
import com.yl.campus.app.activity.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 功能网格适配器
 * Created by Luke on 2017/9/28.
 */

public class GridViewAdapter extends BaseAdapter {
    @BindView(R.id.itemImage)
    ImageView ivItem;
    @BindView(R.id.itemName)
    TextView tvItem;
    private final String[] functionNames = {"新闻展示", "我的课表", "图书搜索",
            "宿舍生活", "系统设置"};
    private final int[] functionImages = {R.drawable.ic_news, R.drawable.ic_timetable,
            R.drawable.ic_books, R.drawable.ic_dormitory, R.drawable.ic_setting};

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
        @SuppressLint("ViewHolder")
        View view = View.inflate(parent.getContext(), R.layout.item_grid_view, null);
        // 设置行高
        DisplayMetrics displayMetrics = new DisplayMetrics();
        MainActivity mainUi = (MainActivity) parent.getContext();
        mainUi.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = (displayMetrics.heightPixels - 400) / 3; // TODO: 2017/10/9 优化
        view.setLayoutParams(new GridView.LayoutParams(
                GridView.LayoutParams.MATCH_PARENT, height));
//        ImageView ivItem = (ImageView) view.findViewById(R.id.itemImage);
//        TextView tvItem = (TextView) view.findViewById(R.id.itemName);
        ButterKnife.bind(this, view);
        tvItem.setText(functionNames[position]);
        ivItem.setImageResource(functionImages[position]);
        return view;
    }
}
