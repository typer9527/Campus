package com.yl.campus.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.yl.campus.R;
import com.yl.campus.view.NewContentView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_news_content)
public class NewsContentActivity extends AppCompatActivity implements NewContentView {

    @ViewById
    public WebView webView;
    @ViewById
    public ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @AfterViews
    @Override
    public void showNewsContent() {
        String url = getIntent().getStringExtra("news_url");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                showProgressBar();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                hideProgressBar();
            }
        });
        webView.loadUrl(url);
    }

    @Override
    public void onLoadFailed() {

    }
}
