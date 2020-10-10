package com.everlastingseo.organicpandit.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.everlastingseo.organicpandit.R;
import com.everlastingseo.organicpandit.helper.ApiClient;
import com.everlastingseo.organicpandit.helper.ApiService;
import com.everlastingseo.organicpandit.helper.ApplicationConstatnt;
import com.everlastingseo.organicpandit.helper.CustomProgressDialog;
import com.everlastingseo.organicpandit.pojo.buyproductinquiry.BuyProductinquryResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ProductInquiryDialog extends Dialog implements DialogInterface.OnClickListener {
    Activity activity;
    Context mContext;
    String uTYPE;
    String uID;
    ApiService apiService;
    Button mBtnSubmit;
    private com.everlastingseo.organicpandit.utils.font.CustomNormalEditText mEditEmail;
    private com.everlastingseo.organicpandit.utils.font.CustomNormalEditText mEditMobile;
    private com.everlastingseo.organicpandit.utils.font.CustomNormalEditText mEditDesc;
    private com.everlastingseo.organicpandit.utils.font.CustomNormalEditText mEditFullname;
    private com.everlastingseo.organicpandit.utils.font.CustomBoldTextView mTxt_dia;

    public ProductInquiryDialog(Context context, String uTYPE, String uID) {
        super(context);
        this.mContext = context;
        this.uID = uID;

        this.uTYPE = uTYPE;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.product_inquiryform_dialog);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        bidview();
    }

    private void bidview() {
        mContext = getContext();
        apiService = ApiClient.getClient(mContext)
                .create(ApiService.class);


        mEditEmail = (com.everlastingseo.organicpandit.utils.font.CustomNormalEditText) findViewById(R.id.EditEmail);
        mEditMobile = (com.everlastingseo.organicpandit.utils.font.CustomNormalEditText) findViewById(R.id.EditMobile);
        mEditDesc = (com.everlastingseo.organicpandit.utils.font.CustomNormalEditText) findViewById(R.id.EditDesc);
        mEditFullname = (com.everlastingseo.organicpandit.utils.font.CustomNormalEditText) findViewById(R.id.EditFullname);

        mBtnSubmit = (Button) findViewById(R.id.BtnSubmit);
        mBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mEditFullname.getText().toString().trim())) {
                    mEditFullname.setError("Enter fullname");
                } else {
                    mEditFullname.setError(null);
                    if (TextUtils.isEmpty(mEditEmail.getText().toString().trim())) {
                        mEditEmail.setError("Enter Email");
                    } else {
                        mEditEmail.setError(null);
                        if (TextUtils.isEmpty(mEditMobile.getText().toString().trim())) {
                            mEditMobile.setError("Enter mobile number");
                        } else {
                            mEditMobile.setError(null);
                            if (TextUtils.isEmpty(mEditDesc.getText().toString().trim())) {
                                mEditDesc.setError("Enter Description");
                            } else {
                                mEditDesc.setError(null);
                                hideKeyboard();
                                callEnqaryform(mEditFullname.getText().toString().trim(), mEditEmail.getText().toString().trim(), mEditMobile.getText().toString().trim(),
                                        mEditDesc.getText().toString().trim());

                            }
                        }
                    }
                }
            }
        });
    }
    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
    @Override
    public void onClick(DialogInterface dialogInterface, int i) {

    }

    private void callEnqaryform(String fname, String email, String mobile, String desc) {
        final ProgressDialog progressDialog = CustomProgressDialog.ctor(mContext);
        progressDialog.show();

        apiService.userSendInquiry(uTYPE, uID, fname, email, mobile, desc)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<BuyProductinquryResponse>() {
                    @Override
                    public void onSuccess(BuyProductinquryResponse loginResponse) {
                        progressDialog.dismiss();
                        if (loginResponse.getSuccess()) {
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
                            alertDialogBuilder.setMessage(loginResponse.getMessage());
                            alertDialogBuilder.setTitle("Response");
                            alertDialogBuilder.setPositiveButton("yes",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface arg0, int arg1) {
                                            arg0.dismiss();
                                            dismiss();
                                        }
                                    });
                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                        } else {
                            ApplicationConstatnt.getDialog(mContext, "Respose", loginResponse.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressDialog.dismiss();
                        Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
