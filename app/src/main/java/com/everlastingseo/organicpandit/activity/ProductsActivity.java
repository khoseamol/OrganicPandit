package com.everlastingseo.organicpandit.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.everlastingseo.organicpandit.R;
import com.everlastingseo.organicpandit.dialog.ProductInquiryDialog;

public class ProductsActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView mProductRecycleView;
    Context mContext;
    TextView mTxtProductView;
    RelativeLayout mRelativeInQuiryFrom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_);
        bindview();
    }

    private void bindview() {
        mContext = ProductsActivity.this;

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle(getIntent().getStringExtra("TITLE") + " Search");

        mRelativeInQuiryFrom = (RelativeLayout) findViewById(R.id.RelativeInQuiryFrom);

        mProductRecycleView = (RecyclerView) findViewById(R.id.ProductRecycleView);
        mProductRecycleView.setLayoutManager(new LinearLayoutManager(mContext));

        mTxtProductView = (TextView) findViewById(R.id.TxtProductView);
        mTxtProductView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ProductDetailsActivity.class);
                intent.putExtra("TITLE", getIntent().getStringExtra("TITLE"));
                startActivity(intent);
            }
        });
        mRelativeInQuiryFrom.setOnClickListener(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.RelativeInQuiryFrom:
                break;
        }
    }
}
