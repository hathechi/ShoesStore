package com.example.shoesstore;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_web);
        WebView webView = findViewById(R.id.webView);
        Intent intent = getIntent();
        String link = intent.getStringExtra("link");
        if (link != null) {
            webView.loadUrl(link);
        }
    }
}