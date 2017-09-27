package com.yl.campus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CrawlerUtil.getTopImage(new CrawlerUtil.OnCrawlListener() {
            @Override
            public void onSucceed(List<String> topImages) {
                for (String topImage : topImages)
                    Log.e("hh", "onSucceed: " + topImage);
            }

            @Override
            public void onFailed() {

            }
        });
    }
}
