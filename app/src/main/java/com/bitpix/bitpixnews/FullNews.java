package com.bitpix.bitpixnews;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class FullNews extends AppCompatActivity {

    private WebView webView;
    private WebSettings webSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_news);

        webView = findViewById(R.id.fn_webview);
        webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        String url = getIntent().getStringExtra("URL");

        if(url!=null) {
            if(url.length()>1) {
                loadWebsite(url);
            }
        }
    }

    private void loadWebsite(String url) {
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

}
