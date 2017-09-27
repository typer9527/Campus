package com.yl.campus.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yl.campus.R;
import com.yl.campus.model.NewsModel;
import com.yl.campus.model.OnGetImageListener;
import com.youth.banner.Banner;

import java.util.List;

public class NewsActivity extends AppCompatActivity {

    private Banner banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        banner = (Banner) findViewById(R.id.banner);
        banner.setImageLoader(new GlideImageLoader());
        NewsModel model = new NewsModel();
        model.getTopImage(new OnGetImageListener() {
            @Override
            public void onSucceed(final List<String> images) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        banner.setImages(images);
                        banner.start();
                    }
                });
            }

            @Override
            public void onFailed() {

            }
        });

    }
}
