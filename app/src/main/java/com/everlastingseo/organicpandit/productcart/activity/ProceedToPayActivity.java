package com.everlastingseo.organicpandit.productcart.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.everlastingseo.organicpandit.R;
import com.everlastingseo.organicpandit.helper.PrefUtils;
import com.easebuzz.payment.kit.PWECouponsActivity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import datamodels.PWEStaticDataModel;

public class ProceedToPayActivity extends AppCompatActivity implements View.OnClickListener {
    Context mContext;
    Button mBtnConfirmPay;
    private TextView mTxtName;
    private TextView mTxtHouseNo;
    private TextView mTxtStreetName;
    private TextView mTxtStatePin;
    private TextView mTxtEmail;
    private TextView mTxtMobile;
    private com.everlastingseo.organicpandit.utils.font.CustomBoldTextView mTxtChangeAddress;
    private com.everlastingseo.organicpandit.utils.font.CustomNormalTextView mTxtTotalAmount;
    private com.everlastingseo.organicpandit.utils.font.CustomNormalTextView mTxtDeliveryCharge;
    private com.everlastingseo.organicpandit.utils.font.CustomBoldTextView mTxtPayAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proceedto_pay);
        bindview();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void bindview() {
        mContext = ProceedToPayActivity.this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Confirm & Pay");

        mTxtName = (TextView) findViewById(R.id.TxtName);
        mTxtHouseNo = (TextView) findViewById(R.id.TxtHouseNo);
        mTxtStreetName = (TextView) findViewById(R.id.TxtStreetName);
        mTxtStatePin = (TextView) findViewById(R.id.TxtStatePin);
        mTxtEmail = (TextView) findViewById(R.id.TxtEmail);
        mTxtMobile = (TextView) findViewById(R.id.TxtMobile);
        mBtnConfirmPay = (Button) findViewById(R.id.BtnConfirmPay);
        mTxtChangeAddress = (com.everlastingseo.organicpandit.utils.font.CustomBoldTextView) findViewById(R.id.TxtChangeAddress);
        mTxtTotalAmount = (com.everlastingseo.organicpandit.utils.font.CustomNormalTextView) findViewById(R.id.TxtTotalAmount);
        mTxtDeliveryCharge = (com.everlastingseo.organicpandit.utils.font.CustomNormalTextView) findViewById(R.id.TxtDeliveryCharge);
        mTxtPayAmount = (com.everlastingseo.organicpandit.utils.font.CustomBoldTextView) findViewById(R.id.TxtPayAmount);

        mTxtTotalAmount.setText(" : " + mContext.getResources().getString(R.string.Rs) + " " + getIntent().getStringExtra("TotalAmount"));
        double shoppingcharge = 100;
        double totalamt = Double.valueOf(getIntent().getStringExtra("TotalAmount"));
        mTxtDeliveryCharge.setText(" : " + mContext.getResources().getString(R.string.Rs) + " " + shoppingcharge);
        double totalpayamount = totalamt + shoppingcharge;
        mTxtPayAmount.setText(" : " + mContext.getResources().getString(R.string.Rs) + " " + String.valueOf(totalpayamount));
        mTxtChangeAddress.setOnClickListener(this);
        mBtnConfirmPay.setOnClickListener(this);
        mTxtName.setText(PrefUtils.getFromPrefs(mContext, "ADDname", ""));
        mTxtHouseNo.setText(PrefUtils.getFromPrefs(mContext, "ADDflatnumber", ""));

        mTxtStreetName.setText(PrefUtils.getFromPrefs(mContext, "ADDstreetname", ""));

        mTxtStatePin.setText(PrefUtils.getFromPrefs(mContext, "ADDcity", "") +
                " " + PrefUtils.getFromPrefs(mContext, "ADDstate", "") + " " +
                PrefUtils.getFromPrefs(mContext, "ADDpincode", "") + " ");

        mTxtEmail.setText(PrefUtils.getFromPrefs(mContext, "ADDemail", ""));
        mTxtMobile.setText(PrefUtils.getFromPrefs(mContext, "ADDmobile", ""));


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.TxtChangeAddress:
                Intent intent = new Intent(mContext, DeliveryAddressActivity.class);
                startActivity(intent);
                break;
            case R.id.BtnConfirmPay:
             Intent intentProceed = new Intent(ProceedToPayActivity.this, PWECouponsActivity.class);
                intentProceed.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT); // This is mandatory flag
                intentProceed.putExtra("txnid", "343432");
                intentProceed.putExtra("amount", 121.0);
                intentProceed.putExtra("productinfo", "organic pandit");
                intentProceed.putExtra("firstname", "everlastingseo khose");
                intentProceed.putExtra("email", "everlastingseo.khose@gmail.com");
                intentProceed.putExtra("phone", "9673346489");
                intentProceed.putExtra("key", "WRLZI5L9HS");
                intentProceed.putExtra("udf1", "test");
                intentProceed.putExtra("udf2", "test");
                intentProceed.putExtra("udf3", "test");
                intentProceed.putExtra("udf4", "test");
                intentProceed.putExtra("udf5", "test");
                intentProceed.putExtra("address1", "Pune Maharastra");
                intentProceed.putExtra("address2", "koradgaon");
                intentProceed.putExtra("city", "Pune");
                intentProceed.putExtra("state", "Maharastra");
                intentProceed.putExtra("country", "India");
                intentProceed.putExtra("zipcode", 411043);
                intentProceed.putExtra("hash", hashCal("SHA-512","WRLZI5L9HS|343432|121.0|organic pandit|everlastingseo khose|everlastingseo.khose@gmail.com|test|test|test|test|test||||||05T01W8655|WRLZI5L9HS"));
                intentProceed.putExtra("unique_id", "test");
                intentProceed.putExtra("pay_mode", "test");
                startActivityForResult(intentProceed, PWEStaticDataModel.PWE_REQUEST_CODE);

                break;

        }
    }


    public String hashCal(String type,String str){
        byte[] hashseq=str.getBytes();
        StringBuffer hexString = new StringBuffer();
        try{
            MessageDigest algorithm = MessageDigest.getInstance(type);
            algorithm.reset();
            algorithm.update(hashseq);
            byte messageDigest[] = algorithm.digest();
            for (int i=0;i<messageDigest.length;i++) {
                String hex=Integer.toHexString(0xFF & messageDigest[i]);
                if(hex.length()==1) hexString.append("0");
                hexString.append(hex);
            }
        }catch(NoSuchAlgorithmException nsae){ }
        return hexString.toString();
    }
}
