package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class WebView extends AppCompatActivity {
    android.webkit.WebView webView;
    ProgressBar progressBar;
    private int flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        webView = findViewById(R.id.webView);
        progressBar = findViewById(R.id.p);
        progressBar.setVisibility(View.VISIBLE); // Show the progress bar initially

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");

        if (url != null) {
            webView.setWebViewClient(new WebViewClient());
            webView.loadUrl(url);
        }

        // Hide the ProgressBar once the page is loaded
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(android.webkit.WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE); // Hide the progress bar once the page is loaded
            }
        });
    }

}