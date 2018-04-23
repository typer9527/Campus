package com.yl.campus.app.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.yl.campus.R;
import com.yl.campus.common.base.BaseActivity;
import com.yl.campus.common.utils.ToastUtils;

import butterknife.BindView;

public class NewsContentActivity extends BaseActivity {

    @BindView(R.id.webView)
    WebView webView;
    private int currentItem = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_news_content;
    }

    @Override
    protected String getDefaultTitle() {
        return getString(R.string.news_content);
    }

    @Override
    protected int getMenuId() {
        return R.menu.news_content_menu;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_text_size:
                showChooseTextSizeDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showChooseTextSizeDialog() {
        final String[] itemsText = {"特大号字", "大号字", "中号字", "小号字"};
        final int textSize[] = {350, 300, 250, 200};
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("字号选择");
        builder.setSingleChoiceItems(itemsText, currentItem,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        currentItem = which;
                    }
                });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                webView.getSettings().setTextZoom(textSize[currentItem]);
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void initView() {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setTextZoom(250);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(
                    WebView view, WebResourceRequest request) {
                return true;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request,
                                        WebResourceError error) {
                super.onReceivedError(view, request, error);
                showProgressDialog("");
                ToastUtils.showToast(NewsContentActivity.this, "网络错误", 0);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                webView.setVisibility(View.INVISIBLE);
                showProgressDialog("");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                String getDiv = "javascript:function getClass(parent,sClass) { " +
                        "var aEle=parent.getElementsByTagName('div');" +
                        " var aResult=[]; var i=0; for(i<0;i<aEle.length;i++) { " +
                        "if(aEle[i].className==sClass) { aResult.push(aEle[i]); } " +
                        "}; return aResult; } ";
                webView.loadUrl(getDiv);
                String hideDiv = "javascript:function hideOther() {" +
                        "getClass(document,'head')[0].style.display='none';" +
                        "getClass(document,'nav')[0].style.display='none';" +
                        "getClass(document,'rigthConBox-head')[0].style.display='none';" +
                        "getClass(document,'rigthConBox')[0].style.width='1000px';" +
                        "getClass(document,'rigthConBox-con')[0].style.width='900px';" +
                        "getClass(document,'list-rcon')[0].style.width='900px';" +
                        "getClass(document,'list-infor')[0].style.display='none';" +
                        "getClass(document,'leftNavBox')[0].style.display='none';" +
                        "getClass(document,'footb')[0].style.display='none';" +
                        "getClass(document,'copyright')[0].style.display='none';" +
                        "}";
                webView.loadUrl(hideDiv);
                webView.loadUrl("javascript:hideOther();");
                webView.setVisibility(View.VISIBLE);
                dismissProgressDialog();
            }
        });
    }

    @Override
    protected void initData() {
        String url = getIntent().getStringExtra("news_url");
        webView.loadUrl(url);
    }
}
