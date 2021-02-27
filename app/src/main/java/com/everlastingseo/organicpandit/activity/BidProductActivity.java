package com.everlastingseo.organicpandit.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.everlastingseo.organicpandit.R;
import com.everlastingseo.organicpandit.helper.ApiClient;
import com.everlastingseo.organicpandit.helper.ApiService;
import com.everlastingseo.organicpandit.helper.ApplicationConstatnt;
import com.everlastingseo.organicpandit.helper.CustomProgressDialog;
import com.everlastingseo.organicpandit.pojo.AplyBidResponse;
import com.everlastingseo.organicpandit.pojo.postrequirment.PostRequirmentData;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class BidProductActivity extends AppCompatActivity implements View.OnClickListener {
    PostRequirmentData postRequirmentData;
    Context mContext;
    Button mTxtAplyBid;
    ApiService apiService;
    private TextView mTxtProductName;
    private TextView mTxtQuality;
    private TextView mTxtValidForm;
    private TextView mTxtValidTO;
    private TextView mTxtExpectedRate;
    private TextView mTxtPrice;
    private TextView mTxtAddress;
    private TextView mTxtQuantity;
    private TextView mTxtPaymnetTerms;
    private TextView mTxtCertification;
    private TextView mTxtOtherDetails;
    private TextView mTt;
    private EditText mEditAmount;
    private EditText mEditCommit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bid_product);
        bindview();

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void bindview() {
        mContext = BidProductActivity.this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Bid for Product");
        apiService = ApiClient.getClient(getApplicationContext())
                .create(ApiService.class);
        mTxtProductName = (TextView) findViewById(R.id.TxtProductName);
        mTxtQuality = (TextView) findViewById(R.id.TxtQuality);
        mTxtValidForm = (TextView) findViewById(R.id.TxtValidForm);
        mTxtValidTO = (TextView) findViewById(R.id.TxtValidTO);
        mTxtExpectedRate = (TextView) findViewById(R.id.TxtExpectedRate);
        mTxtPrice = (TextView) findViewById(R.id.TxtPrice);
        mTxtAddress = (TextView) findViewById(R.id.TxtAddress);
        mTxtQuantity = (TextView) findViewById(R.id.TxtQuantity);
        mTxtPaymnetTerms = (TextView) findViewById(R.id.TxtPaymnetTerms);
        mTxtCertification = (TextView) findViewById(R.id.TxtCertification);
        mTxtOtherDetails = (TextView) findViewById(R.id.TxtOtherDetails);
        mTt = (TextView) findViewById(R.id.tt);
        mTxtAplyBid = (Button) findViewById(R.id.TxtAplyBid);
        mEditAmount = (EditText) findViewById(R.id.EditAmount);
        mEditCommit = (EditText) findViewById(R.id.EditCommit);
        mTxtAplyBid.setOnClickListener(this);


        postRequirmentData = (PostRequirmentData) getIntent().getSerializableExtra("bidData");

        mTxtProductName.setText("Product : " + postRequirmentData.getProductName());
        mTxtQuality.setText("Quality  : " + postRequirmentData.getQualitySpecification());
        mTxtValidForm.setText("Valid From : " + postRequirmentData.getFromDate());
        mTxtValidTO.setText("To : " + postRequirmentData.getToDate());
        mTxtExpectedRate.setText("Expected  : " + mContext.getResources().getString(R.string.Rs) + " " + postRequirmentData.getTotalPrice());
        mTxtQuantity.setText("Quantity  : " + postRequirmentData.getQuantity());
        mTxtPrice.setText("Total : " + mContext.getResources().getString(R.string.Rs) + " " + postRequirmentData.getTotalPrice());
        mTxtAddress.setText("Address : " + postRequirmentData.getDeliveryAddress());
        mTxtPaymnetTerms.setText("Pay Terms : " + postRequirmentData.getPaymentTerms());

        if(postRequirmentData.getCertification_name()!=null){
            mTxtCertification.setText("Certification  : " + postRequirmentData.getCertification_name());

        }else {
            mTxtCertification.setText("Certification  : " + " NA ");

        }

        mTxtOtherDetails.setText("Details  : " + postRequirmentData.getOtherDetails());


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.TxtAplyBid:
                if (TextUtils.isEmpty(mEditAmount.getText().toString().trim())) {
                    mEditAmount.setError("Enter amount");
                } else {
                    mEditAmount.setError(null);
                    callAplyabid(postRequirmentData.getId(), postRequirmentData.getUserId(), mEditAmount.getText().toString().trim(), mEditCommit.getText().toString().trim());
                }
                break;
        }
    }

    private void callAplyabid(String postRequirmentDataId, String userId, String amount, String commite) {
        final ProgressDialog progressDialog = CustomProgressDialog.ctor(BidProductActivity.this);
        progressDialog.show();

        apiService.aplybid(userId,postRequirmentDataId , amount, commite)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<AplyBidResponse>() {
                    @Override
                    public void onSuccess(AplyBidResponse userTypeResponse) {
                        progressDialog.dismiss();

                        if (userTypeResponse.getSuccess()) {
                            if (userTypeResponse.getSuccess()) {
                                ApplicationConstatnt.getDialog(mContext, "", userTypeResponse.getMessage());
                                mEditAmount.setText("");
                                mEditCommit.setText("");
                            } else {
                                ApplicationConstatnt.getDialog(mContext, "", userTypeResponse.getMessage());
                            }


                        } else {
                            ApplicationConstatnt.getDialog(mContext, "", userTypeResponse.getMessage());
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
