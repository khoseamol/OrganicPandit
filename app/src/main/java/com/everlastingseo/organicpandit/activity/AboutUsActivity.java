package com.everlastingseo.organicpandit.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.everlastingseo.organicpandit.R;

public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_about_us);
        bindview();
    }

    private void bindview() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("About US");
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
