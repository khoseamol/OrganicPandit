package com.everlastingseo.organicpandit.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.everlastingseo.organicpandit.R;
import com.everlastingseo.organicpandit.helper.ApiClient;
import com.everlastingseo.organicpandit.helper.ApiService;
import com.everlastingseo.organicpandit.helper.ApplicationConstatnt;
import com.everlastingseo.organicpandit.helper.CustomProgressDialog;
import com.everlastingseo.organicpandit.pojo.ForgotpasswordResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ActivityForgotActivity extends AppCompatActivity {
    TextView mTxtUserName;
    Button mBtnSubmit;
    EditText mEditUsername;
    ApiService apiService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Forgot Password");
        bindview();
    }

    private void bindview() {
        mBtnSubmit = (Button) findViewById(R.id.BtnSubmit);
        mEditUsername = (EditText) findViewById(R.id.EditUsername);
        mBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(mEditUsername.getText().toString().trim())) {
                    mEditUsername.setError("Enter username");
                } else {
                    mEditUsername.setError(null);
                    callForgotPassword(mEditUsername.getText().toString().trim());
                }
            }
        });
    }

    private void callForgotPassword(String username) {
        final ProgressDialog progressDialog = CustomProgressDialog.ctor(ActivityForgotActivity.this);
        progressDialog.show();
        apiService = ApiClient.getClient(getApplicationContext())
                .create(ApiService.class);


        apiService.getforgotpassword(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ForgotpasswordResponse>() {
                    @Override
                    public void onSuccess(ForgotpasswordResponse loginResponse) {
                        progressDialog.dismiss();
                        if (loginResponse.getSuccess()) {
                            ApplicationConstatnt.getDialog(ActivityForgotActivity.this, "", loginResponse.getMessage());
                            mEditUsername.setText("");
                        } else {
                            ApplicationConstatnt.getDialog(ActivityForgotActivity.this, "", loginResponse.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressDialog.dismiss();
                        Toast.makeText(ActivityForgotActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
