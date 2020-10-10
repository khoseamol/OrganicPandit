package com.everlastingseo.organicpandit.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.everlastingseo.organicpandit.MainActivity;
import com.everlastingseo.organicpandit.R;
import com.everlastingseo.organicpandit.adapter.DashBoardServiceAdapter;
import com.everlastingseo.organicpandit.adapter.SliderAdapterExample;
import com.everlastingseo.organicpandit.helper.ApiClient;
import com.everlastingseo.organicpandit.helper.ApiService;
import com.everlastingseo.organicpandit.helper.ApplicationConstatnt;
import com.everlastingseo.organicpandit.helper.CustomProgressDialog;
import com.everlastingseo.organicpandit.helper.PrefUtils;
import com.everlastingseo.organicpandit.model.DashboardServiceModel;
import com.everlastingseo.organicpandit.pojo.sliderimage.SliderImageResponse;
import com.everlastingseo.organicpandit.pojo.worth_details.WorthResponse;
import com.everlastingseo.organicpandit.productcart.activity.ProductAddCartActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class DashBoardActivity extends MainActivity implements View.OnClickListener {

    Context mContext;
    RecyclerView mServiceRecycleView;
    List<DashboardServiceModel> modelList = new ArrayList<DashboardServiceModel>();

    TextView mTXtSerchDeal;
    ApiService apiService;
    SliderView sliderView;
    BottomNavigationView bottomNavigationView;
    private RelativeLayout mRelativeSearchDeal;
    private RelativeLayout mRelativeDealWorth;
    private RelativeLayout mRelativePostsDeals;
    private RelativeLayout mRelativeBuyProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_dash_board, mframelayout);
        bindView();
    }

    private void bindView() {
        mContext = DashBoardActivity.this;
        apiService = ApiClient.getClient(getApplicationContext())
                .create(ApiService.class);

        mServiceRecycleView = (RecyclerView) findViewById(R.id.ServiceRecycleView);
        mTXtSerchDeal = (TextView) findViewById(R.id.TXtSerchDeal);

        CallSilderIMAGE();
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.BottomNaviation);
        sliderView = findViewById(R.id.imageSlider);
        mRelativeSearchDeal = (RelativeLayout) findViewById(R.id.RelativeSearchDeal);
        mRelativeDealWorth = (RelativeLayout) findViewById(R.id.RelativeDealWorth);
        mRelativePostsDeals = (RelativeLayout) findViewById(R.id.RelativePostsDeals);
        mRelativeBuyProduct = (RelativeLayout) findViewById(R.id.RelativeBuyProduct);
        mRelativeBuyProduct.setOnClickListener(this);
        mRelativeDealWorth.setOnClickListener(this);
        mRelativePostsDeals.setOnClickListener(this);
        mRelativeSearchDeal.setOnClickListener(this);

        mTXtSerchDeal.setSelected(true);
        generateServiceModel();

        mServiceRecycleView.setLayoutManager(new GridLayoutManager(mContext, 4));
        DashBoardServiceAdapter dashBoardServiceAdapter = new DashBoardServiceAdapter(mContext, modelList, new DashBoardServiceAdapter.OnClick() {
            @Override
            public void gettitle(DashboardServiceModel dashboardServiceModel) {
                if (PrefUtils.getFromPrefs(mContext, "Is_subscription", "").equals("1")) {
                    Intent intent = new Intent(mContext, SearchUserProductActvity.class);
                    intent.putExtra("TITLE", dashboardServiceModel.getServicename());
                    intent.putExtra("ID", dashboardServiceModel.getUserID());
                    startActivity(intent);
                } else {
                    callsubScriptionDialog();
                }

            }


        });
        mServiceRecycleView.setAdapter(dashBoardServiceAdapter);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.navlogout) {
                    // on favorites clicked
                    return true;
                }
                return false;
            }
        });
    }

    private void callsubScriptionDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setMessage(ApplicationConstatnt.SUBCRIPTION_MESSGE);
        alertDialogBuilder.setTitle(ApplicationConstatnt.SUBCRIPTION_TITLE);
        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });
        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        arg0.dismiss();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void CallSilderIMAGE() {
        final ProgressDialog progressDialog = CustomProgressDialog.ctor(DashBoardActivity.this);
        progressDialog.show();

        apiService.getSliderImage()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<SliderImageResponse>() {
                    @Override
                    public void onSuccess(SliderImageResponse userTypeResponse) {
                        progressDialog.dismiss();
                        if (userTypeResponse.getSuccess()) {
                            if (userTypeResponse.getData().size() > 0) {
                                SliderAdapterExample adapter = new SliderAdapterExample(mContext, userTypeResponse.getData());
                                sliderView.setSliderAdapter(adapter);
                                sliderView.setIndicatorAnimation(IndicatorAnimations.WORM);
                                sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
                                sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
                                sliderView.setIndicatorSelectedColor(Color.WHITE);
                                sliderView.setIndicatorUnselectedColor(Color.GRAY);
                                sliderView.setScrollTimeInSec(4);
                                sliderView.startAutoCycle();
                            } else {

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

    private void generateServiceModel() {

        DashboardServiceModel service1 = new DashboardServiceModel("1", "Farmer", R.drawable.icon_farmer);
        DashboardServiceModel service2 = new DashboardServiceModel("2", "FPO", R.drawable.icon_fpo);
        DashboardServiceModel service3 = new DashboardServiceModel("3", "Trader", R.drawable.icon_trader);
        DashboardServiceModel service4 = new DashboardServiceModel("4", "Professor", R.drawable.icon_processor);
        DashboardServiceModel service5 = new DashboardServiceModel("5", "BUYER", R.drawable.icon_buyer);
        DashboardServiceModel service6 = new DashboardServiceModel("6", "Organic Consultant", R.drawable.icon_organic_consultant);
        DashboardServiceModel service7 = new DashboardServiceModel("18", "NGO", R.drawable.icon_ngo);
        DashboardServiceModel service8 = new DashboardServiceModel("17", "Restaurants", R.drawable.icon_restaurant);
        DashboardServiceModel service9 = new DashboardServiceModel("16", "Certification Agencies", R.drawable.icon_certification_agencies);
        DashboardServiceModel service10 = new DashboardServiceModel("15", "Institution", R.drawable.icon_organic_inputs);
        DashboardServiceModel service11 = new DashboardServiceModel("14", "Goverment Agent", R.drawable.icon_government_agencies);
        DashboardServiceModel service12 = new DashboardServiceModel("13", "Lab", R.drawable.icon_labs);
        DashboardServiceModel service13 = new DashboardServiceModel("12", "Shop", R.drawable.icon_shops);
        DashboardServiceModel service14 = new DashboardServiceModel("11", "Exhibitor", R.drawable.icon_exhibitor);
        DashboardServiceModel service15 = new DashboardServiceModel("10", "Farm Equipment", R.drawable.icon_farm_equipment);
        DashboardServiceModel service16 = new DashboardServiceModel("9", "Logistic", R.drawable.icon_logistic);
        DashboardServiceModel service17 = new DashboardServiceModel("8", "Packaging", R.drawable.icon_packaging);
        DashboardServiceModel service18 = new DashboardServiceModel("7", "Organic Input", R.drawable.icon_organic_inputs);


        modelList.add(service1);
        modelList.add(service2);
        modelList.add(service3);
        modelList.add(service4);
        modelList.add(service5);
        modelList.add(service6);
        modelList.add(service7);
        modelList.add(service8);
        modelList.add(service9);
        modelList.add(service10);
        modelList.add(service11);
        modelList.add(service12);
        modelList.add(service13);
        modelList.add(service14);
        modelList.add(service15);
        modelList.add(service16);
        modelList.add(service17);
        modelList.add(service18);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.RelativeSearchDeal:

                if (PrefUtils.getFromPrefs(mContext, "Is_subscription", "").equals("1")) {
                    Intent intent = new Intent(mContext, SearchDealPreActvity.class);
                    intent.putExtra("TITLE", "Search Deal");
                    startActivity(intent);
                } else {
                    callsubScriptionDialog();
                }

                break;
            case R.id.RelativeDealWorth:

                CallWorthDetails();
                break;
            case R.id.RelativePostsDeals:
                if (PrefUtils.getFromPrefs(mContext, "Is_subscription", "").equals("1")) {
                    Intent    intent = new Intent(mContext, PostDealActivity.class);
                    startActivity(intent);
                } else {
                    callsubScriptionDialog();
                }

                break;
            case R.id.RelativeBuyProduct:
                if (PrefUtils.getFromPrefs(mContext, "Is_subscription", "").equals("1")) {
                    Intent intent = new Intent(mContext, BuyProductMainActvity.class);
                    startActivity(intent);
                } else {
                    callsubScriptionDialog();
                }

                break;
        }
    }

    private void CallWorthDetails() {
        final ProgressDialog progressDialog = CustomProgressDialog.ctor(DashBoardActivity.this);
        progressDialog.show();

        apiService.fetchtotalworth()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<WorthResponse>() {
                    @Override
                    public void onSuccess(WorthResponse userTypeResponse) {
                        progressDialog.dismiss();
                        if (userTypeResponse.getSuccess()) {
                            ApplicationConstatnt.getDialog(mContext, "Response", "Total : " + mContext.getResources().getString(R.string.Rs) + " " + userTypeResponse.getData().getTotalPrice());
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_productcart) {
            if (PrefUtils.getFromPrefs(mContext, "Is_subscription", "").equals("1")) {
                Intent intent = new Intent(mContext, ProductAddCartActivity.class);
                startActivity(intent);
            } else {
                callsubScriptionDialog();
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
