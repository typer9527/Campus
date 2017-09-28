package com.yl.campus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.yl.campus.R;
import com.yl.campus.adapter.GridViewAdapter;
import com.yl.campus.view.MainView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity implements MainView {

    @ViewById
    public DrawerLayout drawerLayout;
    @ViewById
    public GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @AfterViews
    public void init() {
        final GridViewAdapter adapter = new GridViewAdapter();
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(MainActivity.this, NewsActivity_.class));
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Click
    public void homeButton(View view) {
        drawerLayout.openDrawer(GravityCompat.START);
    }
}
