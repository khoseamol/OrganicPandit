package com.everlastingseo.organicpandit.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.everlastingseo.organicpandit.R;
import com.everlastingseo.organicpandit.adapter.fiveuseradapter.FiveUserCropInspectionDetailsAdapter;
import com.everlastingseo.organicpandit.adapter.fiveuseradapter.FiveUserInputOrganicDetailsAdapter;
import com.everlastingseo.organicpandit.adapter.fiveuseradapter.FiveUserMicroNutrientsDetailsAdapter;
import com.everlastingseo.organicpandit.adapter.fiveuseradapter.FiveUserProductDetailsAdapter;
import com.everlastingseo.organicpandit.adapter.fiveuseradapter.FiveUserSoilDetailsAdapter;
import com.everlastingseo.organicpandit.helper.ApiClient;
import com.everlastingseo.organicpandit.helper.ApiService;
import com.everlastingseo.organicpandit.helper.ApplicationConstatnt;
import com.everlastingseo.organicpandit.helper.CustomProgressDialog;
import com.everlastingseo.organicpandit.pojo.fetchuserdata_details.UserDataDetailsResponse;
import com.bumptech.glide.Glide;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class SearchUserProductDetailsActivity extends AppCompatActivity {
    Context mContext;

    ApiService apiService;
    ImageView mProfileImage;
    TextView mTXTMAINProductDetails, mTXTMAINCropInspectionDetails, mTxtMainSoilDetails, mTxtMainmicronutrientDetails, mTXTMAINInputOrganicDetails;
    private TextView mTXtName;
    private TextView mTxtCeoName;
    private TextView mTxtOrgnisationName, mTxtStory;
    private TextView mTXtCertificateAgencyName;
    private LinearLayout mLinearDataExtra;

    private TextView mTxtCategory;
    private TextView mTxtSubcategoty;
    private TextView mTxtBrand;

    private LinearLayout mLinarProductDetail, mLinearCropInspectionDetails, mLinearmicronutrientDetails, mLinearsoilDetails, mLinearInputOrganicDetails;
    private androidx.recyclerview.widget.RecyclerView mRecycleviewProductDetails;
    private androidx.recyclerview.widget.RecyclerView mRecycleviewCropInspectionDetails;
    private androidx.recyclerview.widget.RecyclerView mRecycleviewInputOrganicDetails, mRecycleviewmicronutrientDetails, mRecycleviewsoilDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_productdetailsactivity);
        bindview();
    }

    private void bindview() {
        mContext = SearchUserProductDetailsActivity.this;
        apiService = ApiClient.getClient(getApplicationContext())
                .create(ApiService.class);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle(getIntent().getStringExtra("TITLE") + " Details");
        mTXTMAINProductDetails = (TextView) findViewById(R.id.TXTMAINProductDetails);
        mTXTMAINCropInspectionDetails = (TextView) findViewById(R.id.TXTMAINCropInspectionDetails);
        mTXTMAINInputOrganicDetails = (TextView) findViewById(R.id.TXTMAINInputOrganicDetails);
        mTxtMainSoilDetails = (TextView) findViewById(R.id.TxtMainSoilDetails);
        mTxtMainmicronutrientDetails = (TextView) findViewById(R.id.TxtMainmicronutrientDetails);

        mTxtStory = (TextView) findViewById(R.id.TxtStory);
        mLinearDataExtra = (LinearLayout) findViewById(R.id.LinearDataExtra);
        mTxtCategory = (TextView) findViewById(R.id.TxtCategory);
        mTxtSubcategoty = (TextView) findViewById(R.id.TxtSubcategoty);
        mTxtBrand = (TextView) findViewById(R.id.TxtBrand);


        mTXtName = (TextView) findViewById(R.id.TXtName);
        mTxtCeoName = (TextView) findViewById(R.id.TxtCeoName);
        mTxtOrgnisationName = (TextView) findViewById(R.id.TxtOrgnisationName);
        mTXtCertificateAgencyName = (TextView) findViewById(R.id.TXtCertificateAgencyName);

        mLinearCropInspectionDetails = (LinearLayout) findViewById(R.id.LinearCropInspectionDetails);
        mLinearInputOrganicDetails = (LinearLayout) findViewById(R.id.LinearInputOrganicDetails);
        mLinarProductDetail = (LinearLayout) findViewById(R.id.LinarProductDetail);
        mLinearsoilDetails = (LinearLayout) findViewById(R.id.LinearsoilDetails);
        mLinearmicronutrientDetails = (LinearLayout) findViewById(R.id.LinearmicronutrientDetails);

        mRecycleviewProductDetails = (androidx.recyclerview.widget.RecyclerView) findViewById(R.id.RecycleviewProductDetails);
        mRecycleviewCropInspectionDetails = (androidx.recyclerview.widget.RecyclerView) findViewById(R.id.RecycleviewCropInspectionDetails);
        mRecycleviewInputOrganicDetails = (androidx.recyclerview.widget.RecyclerView) findViewById(R.id.RecycleviewInputOrganicDetails);
        mRecycleviewsoilDetails = (RecyclerView) findViewById(R.id.RecycleviewsoilDetails);
        mRecycleviewmicronutrientDetails = (RecyclerView) findViewById(R.id.RecycleviewmicronutrientDetails);

        mRecycleviewProductDetails.setLayoutManager(new LinearLayoutManager(mContext));
        mRecycleviewmicronutrientDetails.setLayoutManager(new LinearLayoutManager(mContext));
        mRecycleviewsoilDetails.setLayoutManager(new LinearLayoutManager(mContext));
        mRecycleviewInputOrganicDetails.setLayoutManager(new LinearLayoutManager(mContext));
        mRecycleviewCropInspectionDetails.setLayoutManager(new LinearLayoutManager(mContext));
        mProfileImage = (ImageView) findViewById(R.id.ProfileImage);
        getAllUserData(getIntent().getStringExtra("ID"));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void getAllUserData(String ID) {
        final ProgressDialog progressDialog = CustomProgressDialog.ctor(SearchUserProductDetailsActivity.this);
        progressDialog.show();

        apiService.fetchUserData(ID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<UserDataDetailsResponse>() {
                    @Override
                    public void onSuccess(UserDataDetailsResponse userTypeResponse) {
                        progressDialog.dismiss();
                        if (userTypeResponse.getSuccess()) {

                            Glide.with(mContext)
                                    .load(Uri.parse(userTypeResponse.getData().getProfileImage()))
//                                    .diskCacheStrategy(DiskCacheStrategy.NONE)
//                                    .skipMemoryCache(true)
                                    .error(R.drawable.ic_launcher_background)
                                    .into(mProfileImage);

                            if (userTypeResponse.getData().getUserTypeName().equals("Organic Input Company")) {

                                mLinearDataExtra.setVisibility(View.VISIBLE);
                            } else {
                                mLinearDataExtra.setVisibility(View.GONE);
                            }
                            mTXtName.setText(userTypeResponse.getData().getFullname() + " ( " + userTypeResponse.getData().getUsername() + " ) ");
                            mTxtCeoName.setText("CEO : " + userTypeResponse.getData().getCeoName());
                            mTxtOrgnisationName.setText("Company : " + userTypeResponse.getData().getOrganizationName());
                            mTXtCertificateAgencyName.setText("Agency : " + userTypeResponse.getData().getAgencyName());
                            mTxtStory.setText("Story : " + userTypeResponse.getData().getStory());

                            if (userTypeResponse.getData().getUserProductList().size() > 0) {
                                mLinarProductDetail.setVisibility(View.VISIBLE);
                                mRecycleviewProductDetails.setVisibility(View.VISIBLE);
                                FiveUserProductDetailsAdapter fiveUserProductDetails = new FiveUserProductDetailsAdapter(mContext, userTypeResponse.getData().getUserProductList());
                                mRecycleviewProductDetails.setAdapter(fiveUserProductDetails);

                            } else {
                                mLinarProductDetail.setVisibility(View.GONE);
                                mTXTMAINProductDetails.setText("Product Details - No Data Available");
                                mRecycleviewProductDetails.setVisibility(View.GONE);
                            }
                            if (userTypeResponse.getData().getUserCropList().size() > 0) {
                                mLinearCropInspectionDetails.setVisibility(View.VISIBLE);

                                mRecycleviewCropInspectionDetails.setVisibility(View.VISIBLE);
                                FiveUserCropInspectionDetailsAdapter userCropInspectionDetailsAdapter = new FiveUserCropInspectionDetailsAdapter(mContext, userTypeResponse.getData().getUserCropList());
                                mRecycleviewCropInspectionDetails.setAdapter(userCropInspectionDetailsAdapter);

                            } else {
                                mLinearCropInspectionDetails.setVisibility(View.GONE);
                                mTXTMAINCropInspectionDetails.setText("Crop Inspection Details - No Data Available");
                                mRecycleviewCropInspectionDetails.setVisibility(View.GONE);
                            }

                            if (userTypeResponse.getData().getUserInputList().size() > 0) {
                                mLinearInputOrganicDetails.setVisibility(View.VISIBLE);
                                mRecycleviewInputOrganicDetails.setVisibility(View.VISIBLE);
                                FiveUserInputOrganicDetailsAdapter userInputOrganicDetailsAdapter = new FiveUserInputOrganicDetailsAdapter(mContext, userTypeResponse.getData().getUserInputList());
                                mRecycleviewInputOrganicDetails.setAdapter(userInputOrganicDetailsAdapter);

                            } else {
                                mLinearInputOrganicDetails.setVisibility(View.GONE);

                                mTXTMAINInputOrganicDetails.setText("Input Organic Details - No Data Available");
                                mRecycleviewInputOrganicDetails.setVisibility(View.GONE);
                            }

                            if (userTypeResponse.getData().getUserSoilList().size() > 0) {
                                mLinearsoilDetails.setVisibility(View.VISIBLE);
                                mRecycleviewsoilDetails.setVisibility(View.VISIBLE);
                                FiveUserSoilDetailsAdapter fiveUserSoilDetailsAdapter = new FiveUserSoilDetailsAdapter(mContext, userTypeResponse.getData().getUserSoilList());
                                mRecycleviewsoilDetails.setAdapter(fiveUserSoilDetailsAdapter);

                            } else {

                                mLinearsoilDetails.setVisibility(View.GONE);
                                mTxtMainSoilDetails.setText("Soil Details - No Data Available");
                                mRecycleviewsoilDetails.setVisibility(View.GONE);
                            }
                            if (userTypeResponse.getData().getUserMicroList().size() > 0) {
                                mLinearmicronutrientDetails.setVisibility(View.VISIBLE);
                                mRecycleviewmicronutrientDetails.setVisibility(View.VISIBLE);
                                FiveUserMicroNutrientsDetailsAdapter fiveUserMicroNutrientsDetailsAdapter = new FiveUserMicroNutrientsDetailsAdapter(mContext, userTypeResponse.getData().getUserMicroList());
                                mRecycleviewmicronutrientDetails.setAdapter(fiveUserMicroNutrientsDetailsAdapter);

                            } else {

                                mLinearmicronutrientDetails.setVisibility(View.GONE);
                                mTxtMainmicronutrientDetails.setText("Micro Nutrients Details - No Data Available");
                                mRecycleviewmicronutrientDetails.setVisibility(View.GONE);
                            }


                        } else {
                            ApplicationConstatnt.getDialog(mContext, "Response", userTypeResponse.getMessage());

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
