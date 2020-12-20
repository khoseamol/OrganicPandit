package com.everlastingseo.organicpandit.productcart.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.easebuzz.payment.kit.PWECouponsActivity;
import com.everlastingseo.organicpandit.R;
import com.everlastingseo.organicpandit.activity.DashBoardActivity;
import com.everlastingseo.organicpandit.helper.ApiClient;
import com.everlastingseo.organicpandit.helper.ApiService;
import com.everlastingseo.organicpandit.helper.ApplicationConstatnt;
import com.everlastingseo.organicpandit.helper.CustomProgressDialog;
import com.everlastingseo.organicpandit.helper.PrefUtils;
import com.everlastingseo.organicpandit.helper.db.AppDao;
import com.everlastingseo.organicpandit.helper.db.AppDatabase;
import com.everlastingseo.organicpandit.pojo.preparepaymentgatway.ResponsePrepareForPaymentGateway;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import datamodels.PWEStaticDataModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ProceedToPayActivity extends AppCompatActivity implements View.OnClickListener {
    Context mContext;
    Button mBtnConfirmPay;
    ImageView mimg_refresh;
    CheckBox mCheckboxAddress;
    ApiService apiService;
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
    AppDao appDao;
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

        apiService = ApiClient.getClient(getApplicationContext())
                .create(ApiService.class);
        appDao = Room.databaseBuilder(mContext, AppDatabase.class, mContext.getString(R.string.app_name)).allowMainThreadQueries().build().taskDao();

        mimg_refresh = (ImageView) findViewById(R.id.img_refresh);
        mCheckboxAddress = (CheckBox) findViewById(R.id.CheckboxAddress);
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
        mTxtDeliveryCharge.setText(" : " + mContext.getResources().getString(R.string.Rs) + " " + "NA");
        double totalpayamount = totalamt ;
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

        mimg_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTxtName.setText(PrefUtils.getFromPrefs(mContext, "ADDname", ""));
                mTxtHouseNo.setText(PrefUtils.getFromPrefs(mContext, "ADDflatnumber", ""));
                mTxtStreetName.setText(PrefUtils.getFromPrefs(mContext, "ADDstreetname", ""));
                mTxtStatePin.setText(PrefUtils.getFromPrefs(mContext, "ADDcity", "") +
                        " " + PrefUtils.getFromPrefs(mContext, "ADDstate", "") + " " +
                        PrefUtils.getFromPrefs(mContext, "ADDpincode", "") + " ");
                mTxtEmail.setText(PrefUtils.getFromPrefs(mContext, "ADDemail", ""));
                mTxtMobile.setText(PrefUtils.getFromPrefs(mContext, "ADDmobile", ""));
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.TxtChangeAddress:
                Intent intent = new Intent(mContext, DeliveryAddressActivity.class);
                startActivity(intent);
                break;
            case R.id.BtnConfirmPay:
                if (mCheckboxAddress.isChecked()) {
                    ResponsePrepareForPaymentGateway loginResponse = new ResponsePrepareForPaymentGateway();
                    loginResponse = (ResponsePrepareForPaymentGateway) this.getIntent().getSerializableExtra("Data");

                    Intent intentProceed = new Intent(ProceedToPayActivity.this, PWECouponsActivity.class);
                    intentProceed.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT); // This is mandatory flag
                    intentProceed.putExtra("txnid", loginResponse.getResponse().getData().getData().getPaymentDetails().getTxnid());
                    intentProceed.putExtra("amount", Double.valueOf(loginResponse.getResponse().getData().getData().getPaymentDetails().getAmount()));
//                            intentProceed.putExtra("amount", Double.valueOf("2.0"));
                    intentProceed.putExtra("productinfo", loginResponse.getResponse().getData().getData().getPaymentDetails().getProductinfo());
                    intentProceed.putExtra("firstname", loginResponse.getResponse().getData().getData().getPaymentDetails().getFirstname());
                    intentProceed.putExtra("email", loginResponse.getResponse().getData().getData().getPaymentDetails().getEmail());
                    intentProceed.putExtra("phone", loginResponse.getResponse().getData().getData().getPaymentDetails().getPhone());
                    intentProceed.putExtra("key", loginResponse.getResponse().getData().getData().getPaymentDetails().getKey());
                    intentProceed.putExtra("udf1", loginResponse.getResponse().getData().getData().getPaymentDetails().getUdf1());
                    intentProceed.putExtra("udf2", loginResponse.getResponse().getData().getData().getPaymentDetails().getUdf2());
                    intentProceed.putExtra("udf3", loginResponse.getResponse().getData().getData().getPaymentDetails().getUdf3());
                    intentProceed.putExtra("udf4", loginResponse.getResponse().getData().getData().getPaymentDetails().getUdf4());
                    intentProceed.putExtra("udf5", loginResponse.getResponse().getData().getData().getPaymentDetails().getUdf5());
                    intentProceed.putExtra("address1", loginResponse.getResponse().getData().getData().getPaymentDetails().getAddress1());
                    intentProceed.putExtra("address2", loginResponse.getResponse().getData().getData().getPaymentDetails().getAddress2());
                    intentProceed.putExtra("city", loginResponse.getResponse().getData().getData().getPaymentDetails().getCity());
                    intentProceed.putExtra("state", loginResponse.getResponse().getData().getData().getPaymentDetails().getState());
                    intentProceed.putExtra("country", loginResponse.getResponse().getData().getData().getPaymentDetails().getCountry());
                    intentProceed.putExtra("zipcode", loginResponse.getResponse().getData().getData().getPaymentDetails().getZipcode());
                    intentProceed.putExtra("hash", loginResponse.getResponse().getData().getData().getPaymentDetails().getHash());
//      intentProceed.putExtra("unique_id", responsePrepareForPaymentGateway.getResponse().getData().getData().getPaymentDetails().getUnique_id());
                    intentProceed.putExtra("pay_mode", loginResponse.getResponse().getData().getData().getPaymentDetails().getPay_mode());
                    startActivityForResult(intentProceed, PWEStaticDataModel.PWE_REQUEST_CODE);

                } else {
                    ApplicationConstatnt.getDialog(mContext, "Response", "Please choose Address.");
                }
                break;

        }
    }


    public String hashCal(String type, String str) {
        byte[] hashseq = str.getBytes();
        StringBuffer hexString = new StringBuffer();
        try {
            MessageDigest algorithm = MessageDigest.getInstance(type);
            algorithm.reset();
            algorithm.update(hashseq);
            byte messageDigest[] = algorithm.digest();
            for (int i = 0; i < messageDigest.length; i++) {
                String hex = Integer.toHexString(0xFF & messageDigest[i]);
                if (hex.length() == 1) hexString.append("0");
                hexString.append(hex);
            }
        } catch (NoSuchAlgorithmException nsae) {
        }
        return hexString.toString();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (data != null) {
                if (requestCode == PWEStaticDataModel.PWE_REQUEST_CODE) {
                    String result = data.getStringExtra("result");
                    String payment_response = data.getStringExtra("payment_response");
                    try {
                        try {
                            JsonObject mainJsonObject = new JsonObject();
                            mainJsonObject.addProperty(ApplicationConstatnt.Request_app_version, "1.0");
                            JsonObject objAuth = new JsonObject();
                            objAuth.addProperty(ApplicationConstatnt.Request_type, "user");
                            objAuth.addProperty(ApplicationConstatnt.Request_token, PrefUtils.getFromPrefs(mContext, "userToken", ""));
                            mainJsonObject.add(ApplicationConstatnt.Request_Auth, objAuth);
                            JsonObject objMethod = new JsonObject();
                            JsonObject jsonObject = new JsonParser().parse(payment_response).getAsJsonObject();
                            objMethod.addProperty(ApplicationConstatnt.Request_name, "ProductPaymentResponse");
                            mainJsonObject.add(ApplicationConstatnt.Request_method, objMethod);
                            objMethod.add(ApplicationConstatnt.Request_parameters, jsonObject);
//                            ApplicationConstatnt.getDialog(mContext, "Response", result);

                            sendApiResponse(mainJsonObject);
                        } catch (Exception e) {
                            e.printStackTrace();
                            ApplicationConstatnt.toast(mContext, "Somthing went wrong");

                        }

                    } catch (Exception e) {
                        ApplicationConstatnt.toast(mContext, e.getMessage());

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void sendApiResponse(JsonObject mainJsonObject) {
        final ProgressDialog progressDialog = CustomProgressDialog.ctor(ProceedToPayActivity.this);
        progressDialog.show();

        apiService.sendPaymentresponse(mainJsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ResponsePrepareForPaymentGateway>() {
                    @Override
                    public void onSuccess(ResponsePrepareForPaymentGateway loginResponse) {
                        progressDialog.dismiss();

                       if(loginResponse!=null){
                           AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
                           alertDialogBuilder.setMessage(loginResponse.getResponse().getData().getMessage());
                           alertDialogBuilder.setTitle("Response");
                           alertDialogBuilder.setPositiveButton("yes",
                                   new DialogInterface.OnClickListener() {
                                       @Override
                                       public void onClick(DialogInterface arg0, int arg1) {

                                           appDao.deleteTable();
                                           Intent intent = new Intent(mContext, DashBoardActivity.class);
                                           startActivity(intent);
                                           ProceedToPayActivity.this.finish();
                                           arg0.dismiss();

                                       }
                                   });
                           AlertDialog alertDialog = alertDialogBuilder.create();
                           alertDialog.show();
                       }else {
                           Toast.makeText(mContext, "Error ", Toast.LENGTH_SHORT).show();

                       }
//                        if (loginResponse.getResponse().getData().getStatus().equals("")) {
//                        } else {
//
//
//                        }


                    }

                    @Override
                    public void onError(Throwable e) {
                        progressDialog.dismiss();
                        Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
