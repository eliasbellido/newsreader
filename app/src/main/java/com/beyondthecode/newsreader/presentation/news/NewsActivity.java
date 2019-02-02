package com.beyondthecode.newsreader.presentation.news;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.beyondthecode.newsreader.R;

import dagger.android.support.DaggerAppCompatActivity;

public class NewsActivity extends DaggerAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
    }
}
