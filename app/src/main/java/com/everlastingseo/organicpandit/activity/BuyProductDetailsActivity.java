package com.everlastingseo.organicpandit.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.everlastingseo.organicpandit.R;
import com.everlastingseo.organicpandit.dialog.BuyProductInquiryDialog;
import com.everlastingseo.organicpandit.helper.ApiClient;
import com.everlastingseo.organicpandit.helper.ApiService;
import com.everlastingseo.organicpandit.helper.ApplicationConstatnt;
import com.everlastingseo.organicpandit.helper.CustomProgressDialog;
import com.everlastingseo.organicpandit.helper.db.AppDao;
import com.everlastingseo.organicpandit.helper.db.AppDatabase;
import com.everlastingseo.organicpandit.helper.db.AppEntity;
import com.everlastingseo.organicpandit.pojo.sellproduct.SellProductResponseData;
import com.everlastingseo.organicpandit.pojo.sellproductdetails.SellProductDetailsResponse;
import com.everlastingseo.organicpandit.productcart.activity.ProductAddCartActivity;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class BuyProductDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    Context mContext;
    Vibrator vibrator;
    SellProductResponseData mSellProductResponseData;
    AppDao appDao;
    ApiService apiService;
    private TextView mTxtUserName;
    private TextView mTxtCategory;
    private TextView mTxtProduct;
    private TextView mTxtQuantity;
    private TextView mTxtExpectedPrice;
    private TextView mTxtTotalPrice;
    private TextView mTxtCertificateAgency;
    private TextView mTxtVariety;
    private TextView mTxtColor;
    private TextView mTxtCropYear;
    private TextView mTxtBrokenratio;
    private TextView mTxtMoisture;
    private TextView mTxtSupplyquantity;
    private TextView mTxtPackagingType;
    private TextView mTxtProductDescription;
    private TextView mTXtd;
    private TextView mTxtDeliveryLocation;
    private TextView mTxtState;
    private TextView mTxtLeadDay;
    private TextView mTxtDeliveryType;
    private TextView mTxtOtherDetails;
    private TextView mTxtAddtocart;
    private TextView mTxtAddEnqiry;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buyproduct_details);
        bindview();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.cartAction) {
            Intent intent = new Intent(mContext, ProductAddCartActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void bindview() {
        mContext = BuyProductDetailsActivity.this;
        apiService = ApiClient.getClient(getApplicationContext())
                .create(ApiService.class);
        appDao = Room.databaseBuilder(mContext, AppDatabase.class, mContext.getString(R.string.app_name)).allowMainThreadQueries().build().taskDao();
        mSellProductResponseData = (SellProductResponseData) getIntent().getSerializableExtra("DataDetails");
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("View Product Details");
        CallApiDetails(mSellProductResponseData.getSellProductId());

        mTxtUserName = findViewById(R.id.TxtUserName);
        mTxtCategory = findViewById(R.id.TxtCategory);
        mTxtProduct = findViewById(R.id.TxtProduct);
        mTxtQuantity = findViewById(R.id.TxtQuantity);
        mTxtExpectedPrice = findViewById(R.id.txtExpectedPrice);
        mTxtTotalPrice = findViewById(R.id.TxtTotalPrice);
        mTxtCertificateAgency = findViewById(R.id.TxtCertificateAgency);
        mTxtVariety = findViewById(R.id.TxtVariety);
        mTxtColor = findViewById(R.id.TxtColor);
        mTxtCropYear = findViewById(R.id.TxtCropYear);
        mTxtBrokenratio = findViewById(R.id.TxtBrokenratio);
        mTxtMoisture = findViewById(R.id.TxtMoisture);
        mTxtSupplyquantity = findViewById(R.id.TxtSupplyquantity);
        mTxtPackagingType = findViewById(R.id.TxtPackagingType);
        mTxtProductDescription = findViewById(R.id.TxtProductDescription);
        mTXtd = findViewById(R.id.TXtd);
        mTxtDeliveryLocation = findViewById(R.id.TxtDeliveryLocation);
        mTxtState = findViewById(R.id.TxtState);
        mTxtLeadDay = findViewById(R.id.TxtLeadDay);
        mTxtDeliveryType = findViewById(R.id.TxtDeliveryType);
        mTxtOtherDetails = findViewById(R.id.TxtOtherDetails);
        mTxtAddtocart = findViewById(R.id.TxtAddtocart);
        mTxtAddEnqiry = findViewById(R.id.TxtAddEnqiry);

        mTxtAddEnqiry.setOnClickListener(this);
        mTxtAddtocart.setOnClickListener(this);

    }

    private void CallApiDetails(String sellProductId) {
        final ProgressDialog progressDialog = CustomProgressDialog.ctor(BuyProductDetailsActivity.this);
        progressDialog.show();

        apiService.sellproductDetails(sellProductId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<SellProductDetailsResponse>() {
                    @Override
                    public void onSuccess(SellProductDetailsResponse userTypeResponse) {
                        progressDialog.dismiss();

                        if (userTypeResponse.getSuccess()) {
                            mTxtCategory.setText("Category : " + userTypeResponse.getData().getCategoryName());
                            mTxtUserName.setText("UserName : " + userTypeResponse.getData().getUsername());
                            mTxtProduct.setText("Product : " + userTypeResponse.getData().getProductName());
                            mTxtQuantity.setText("Qty (Kg) : " + userTypeResponse.getData().getSellQuantity());
                            mTxtExpectedPrice.setText("Expected (Kg): " + getResources().getString(R.string.Rs) + " " + userTypeResponse.getData().getPrice());
                            mTxtTotalPrice.setText("Total : " + getResources().getString(R.string.Rs) + " " + userTypeResponse.getData().getTotalPrice());
                            mTxtCertificateAgency.setText("Agency : " + userTypeResponse.getData().getCertificatonAgencyName());
                            mTxtVariety.setText("Variety : " + userTypeResponse.getData().getVariety());
                            mTxtColor.setText("Color : " + userTypeResponse.getData().getColour());
                            mTxtCropYear.setText("CropYear : " + userTypeResponse.getData().getCropYear());
                            mTxtBrokenratio.setText("Broken : " + userTypeResponse.getData().getBrokenRatio());
                            mTxtMoisture.setText("Moisture : " + userTypeResponse.getData().getMoisture());
                            mTxtSupplyquantity.setText("Supply Qty : " + userTypeResponse.getData().getSupplyQuantity());
                            mTxtPackagingType.setText("Package : " + userTypeResponse.getData().getPackagingType());
                            mTxtProductDescription.setText("Desc : " + userTypeResponse.getData().getProductDescription());
                          if(userTypeResponse.getData().getDeliveryLocationName()!=null)
                            mTxtDeliveryLocation.setText("Delivery : " + userTypeResponse.getData().getDeliveryLocationName());
                          else
                              mTxtDeliveryLocation.setText("Delivery : " + " NA ");

                            mTxtState.setText("State : " + userTypeResponse.getData().getState_name());
                            mTxtLeadDay.setText("LeadDay : " + userTypeResponse.getData().getLeadTime());

                            if(userTypeResponse.getData().getDeliveryTypeName()!=null){
                                mTxtDeliveryType.setText("D type : " + userTypeResponse.getData().getDeliveryTypeName());
                            }else
                                mTxtDeliveryType.setText("D type : " + " NA ");

                            mTxtOtherDetails.setText("Other Details : " + userTypeResponse.getData().getOtherDetails());

                            if (userTypeResponse.getData().getStock().equals("1")) {
                                mTxtAddtocart.setText("Out Of Stock");
                                mTxtAddtocart.setClickable(false);

                            } else {
                                mTxtAddtocart.setText("Add to Cart");
                                mTxtAddtocart.setClickable(true);
                            }


                        } else {
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.TxtAddtocart:
                if (appDao.getAll().size() < 9) {

                    if (appDao.isDataExist(mSellProductResponseData.getProductId())) {

                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
                        alertDialogBuilder.setMessage("Product is already present in cart");
                        alertDialogBuilder.setTitle("Response");
                        alertDialogBuilder.setPositiveButton("View cart",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        Intent intent = new Intent(mContext, ProductAddCartActivity.class);
                                        startActivity(intent);
                                        arg0.dismiss();
                                    }
                                });
                        alertDialogBuilder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();


                    } else {
                        if (Build.VERSION.SDK_INT >= 26) {
                            vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
                        } else {
                            vibrator.vibrate(200);
                        }
                        AppEntity model = new AppEntity();
                        model.setProductId(mSellProductResponseData.getProductId());
                        model.setProductType("Buy Product");
                        model.setproductName(mSellProductResponseData.getProductName());
                        model.setpPrice(mSellProductResponseData.getPrice());
                        model.setBPsell_product_id(mSellProductResponseData.getSellProductId());
                        model.setProductImg(mSellProductResponseData.getPrimaryImage());
                        model.setItemcount("1");
                        appDao.insert(model);
                        ApplicationConstatnt.toast(mContext, "Item Added");
                    }

                } else {
                    ApplicationConstatnt.getDialog(mContext, "", "More then 9 item not allowed");
                }

                break;
            case R.id.TxtAddEnqiry:
                BuyProductInquiryDialog productInquiryDialog = new BuyProductInquiryDialog(this, mSellProductResponseData.getSellProductId(), mSellProductResponseData.getUserId(), mSellProductResponseData);
                productInquiryDialog.show();
                break;
        }
    }
}
