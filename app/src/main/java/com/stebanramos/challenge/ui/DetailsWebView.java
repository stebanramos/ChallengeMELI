package com.stebanramos.challenge.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.stebanramos.challenge.R;
import com.stebanramos.challenge.databinding.ActivitySearchBinding;
import com.stebanramos.challenge.databinding.WebViewDetailsBinding;

public class DetailsWebView extends AppCompatActivity {
    private static final String currentActivity = DetailsWebView.class.getSimpleName();

    private WebViewDetailsBinding binding;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = WebViewDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Log.d("d_funciones", currentActivity + " onCreate()");

        WebView webView = binding.webview;

        // Enable Javascript
        WebSettings webSettings = webView.getSettings();
        webView.loadUrl(getIntent().getStringExtra("permalink"));
    }
}
