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
import android.widget.EditText;
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

public class BuyProductInquiryDialog extends Dialog implements DialogInterface.OnClickListener {
    Context mContext;
    String pID = "";
    String uID = "";


    ApiService apiService;

    public BuyProductInquiryDialog(Context context, String pId, String uID) {
        super(context);
        this.mContext = context;
        this.pID = pId;
        this.uID = uID;
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buy_product_inquiryform_dialog);

        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        bindview();
    }

    private void bindview() {
        mContext = getContext();
        apiService = ApiClient.getClient(mContext)
                .create(ApiService.class);


        final EditText mEditDescription = (EditText) findViewById(R.id.EditDescription);
        Button mBtnSend = (Button) findViewById(R.id.BtnSend);
        mBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mEditDescription.getText().toString().trim())) {
                    mEditDescription.setError("Enter Description");
                } else {
                    mEditDescription.setError(null);
                    hideKeyboard();
                    callEnqaryform(mEditDescription.getText().toString().trim());

                }
            }
        });
    }

    private void callEnqaryform(String dsc) {
        final ProgressDialog progressDialog = CustomProgressDialog.ctor(mContext);
        progressDialog.show();

        apiService.sellproductDetailsEnquiry(pID, uID, dsc)
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

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {

    }
}
