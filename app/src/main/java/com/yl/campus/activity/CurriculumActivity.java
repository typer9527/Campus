package com.yl.campus.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import com.yl.campus.R;
import com.yl.campus.adapter.DateAndWeekAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_curriculum)
public class CurriculumActivity extends AppCompatActivity {

    @ViewById
    GridView dateAndWeekGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @AfterViews
    public void init() {
        dateAndWeekGrid.setAdapter(new DateAndWeekAdapter());
    }
}
