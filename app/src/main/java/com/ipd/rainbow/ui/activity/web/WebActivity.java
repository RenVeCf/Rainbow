package com.ipd.rainbow.ui.activity.web;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ipd.rainbow.R;
import com.ipd.rainbow.ui.BaseUIActivity;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;

/**
 * Created by jumpbox on 16/9/18.
 */
public class WebActivity extends BaseUIActivity {
    SmoothProgressBar progressBar;
    WebView web_view;


    public static final int URL = 0, HTML = 1;

    public static void launch(Activity from, int flag, String data) {
        Intent intent = new Intent(from, WebActivity.class);
        intent.putExtra("flag", flag);
        intent.putExtra("data", data);
        from.startActivity(intent);
    }

    public static void launch(Activity from, int flag, String data, String title) {
        Intent intent = new Intent(from, WebActivity.class);
        intent.putExtra("flag", flag);
        intent.putExtra("data", data);
        intent.putExtra("title", title);
        from.startActivity(intent);
    }

    public static void launch(Activity from, int flag, String data, String title, boolean share) {
        Intent intent = new Intent(from, WebActivity.class);
        intent.putExtra("flag", flag);
        intent.putExtra("data", data);
        intent.putExtra("title", title);
        intent.putExtra("share", share);
        from.startActivity(intent);
    }


    @Override
    public String getToolbarTitle() {
        String title = getIntent().getStringExtra("title");
        return TextUtils.isEmpty(title) ? "" : title;
    }


    @Override
    protected int getContentLayout() {
        return R.layout.activity_web;
    }

    @Override
    protected void onViewAttach() {

    }

    @Override
    protected void onViewDetach() {

    }

    @Override
    public void initView(Bundle savedInstanceState) {
        initToolbar();
        progressBar = findViewById(R.id.progress_bar);
        web_view = findViewById(R.id.web_view);

        progressBar.setIndeterminate(true);
    }

    @Override
    public void loadData() {
        web_view.getSettings().setJavaScriptEnabled(true);

        web_view.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress < 100) {
                    progressBar.setVisibility(View.VISIBLE);
                } else if (newProgress >= 100) {
                    progressBar.setVisibility(View.GONE);
                }
                progressBar.setProgress(newProgress);
            }

        });

        web_view.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        int flag = getIntent().getIntExtra("flag", 0);
        String data = getIntent().getStringExtra("data");
        if (flag == URL) {
            if (!TextUtils.isEmpty(data)) {
                web_view.loadUrl(data);
            }
        } else {
            if (!TextUtils.isEmpty(data)) {
//                String htmlStr = "<html><body>" + data.replaceAll("\"", "") + "</body></html>";
                web_view.loadData(data, "text/html; charset=UTF-8", null);
            }
        }

    }

    @Override
    public void initListener() {

    }

    @Override
    public void onPause() {
        super.onPause();
        if (web_view != null) {
            web_view.onPause();
            web_view.pauseTimers();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (web_view != null) {
            web_view.resumeTimers();
            web_view.onResume();
        }
    }


    @Override
    protected void onDestroy() {
        if (web_view != null) {
            web_view.destroy();
            web_view = null;
        }
        super.onDestroy();
    }


}
