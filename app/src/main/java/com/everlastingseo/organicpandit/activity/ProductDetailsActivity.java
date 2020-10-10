package com.everlastingseo.organicpandit.activity;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.everlastingseo.organicpandit.R;

public class ProductDetailsActivity extends AppCompatActivity {
    Context mContext;
    TextView mTxtPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.productdetailsactivity);
        bindview();
    }

    private void bindview() {
        mContext = ProductDetailsActivity.this;

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle(getIntent().getStringExtra("TITLE") + " Details");

        mTxtPrice=(TextView)findViewById(R.id.TxtPrice);
        mTxtPrice.setText(mContext.getResources().getString(R.string.Rs)+" 139288");
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
