package com.yl.campus.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yl.campus.R;
import com.yl.campus.model.DateAndWeek;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 课表的日期星期适配器
 * Created by Luke on 2017/9/30.
 */

public class DateAndWeekAdapter extends BaseAdapter {

    private List<DateAndWeek> dateAndWeekList;

    public DateAndWeekAdapter() {
        this.dateAndWeekList = getDateAndWeek();
    }

    private List<DateAndWeek> getDateAndWeek() {
        final String[] weekName = {"", "周日", "周一", "周二", "周三", "周四",
                "周五", "周六"};
        List<DateAndWeek> list = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        int week = calendar.get(Calendar.DAY_OF_WEEK);
        int date = calendar.get(Calendar.DAY_OF_MONTH);
        for (int i = 0; i < 8; i++) {
            DateAndWeek dateAndWeek = new DateAndWeek();
            if (i == 0) {
                dateAndWeek.date = month + 1 + "月";
            } else {
                dateAndWeek.date = date - week + i + "号";
            }
            dateAndWeek.week = weekName[i];
            list.add(dateAndWeek);
        }
        return list;
    }

    @Override
    public int getCount() {
        return dateAndWeekList.size();
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
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_date_week, parent, false);
        TextView dateText = (TextView) view.findViewById(R.id.dateText);
        TextView weekText = (TextView) view.findViewById(R.id.weekText);
        dateText.setText(dateAndWeekList.get(position).date);
        weekText.setText(dateAndWeekList.get(position).week);
        if (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == position) {
            view.setBackgroundColor(Color.LTGRAY);
        }
        return view;
    }
}
