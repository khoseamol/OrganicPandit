package com.everlastingseo.organicpandit.subcriptionplan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.everlastingseo.organicpandit.R;
import com.everlastingseo.organicpandit.helper.ApiClient;
import com.everlastingseo.organicpandit.helper.ApiService;
import com.everlastingseo.organicpandit.helper.ApplicationConstatnt;
import com.everlastingseo.organicpandit.helper.CustomProgressDialog;
import com.everlastingseo.organicpandit.helper.PrefUtils;
import com.everlastingseo.organicpandit.pojo.preparepaymentgatway.ResponsePrepareForPaymentGateway;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonObject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class SubPlanTabActivityMain extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    ApiService apiService;

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_plan_tab_main);
        apiService = ApiClient.getClient(getApplicationContext())
                .create(ApiService.class);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Subscription Plan");

        tabLayout=(TabLayout)findViewById(R.id.tabLayout);
        viewPager=(ViewPager)findViewById(R.id.viewPager);

        tabLayout.addTab(tabLayout.newTab().setText("Standard"));
        tabLayout.addTab(tabLayout.newTab().setText("Premium"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final MyAdapter adapter = new MyAdapter(this,getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
    private void getSubPlan() {
        JsonObject mainJsonObject = new JsonObject();
        mainJsonObject.addProperty(ApplicationConstatnt.Request_app_version, "1.0");
        JsonObject objAuth = new JsonObject();
        objAuth.addProperty(ApplicationConstatnt.Request_type, "user");
        objAuth.addProperty(ApplicationConstatnt.Request_token, PrefUtils.getFromPrefs(getApplicationContext(), "userToken", ""));
        mainJsonObject.add(ApplicationConstatnt.Request_Auth, objAuth);
        JsonObject objMethod = new JsonObject();
        objMethod.addProperty(ApplicationConstatnt.Request_name, "GetSubscriptionPlans");

        mainJsonObject.add(ApplicationConstatnt.Request_method, objMethod);
        JsonObject objParameter = new JsonObject();
        objMethod.add(ApplicationConstatnt.Request_parameters, objParameter);
        final ProgressDialog progressDialog = CustomProgressDialog.ctor(getApplicationContext());
        progressDialog.show();

        apiService.getSubcriptionPlanApi(mainJsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ResponsePrepareForPaymentGateway>() {
                    @Override
                    public void onSuccess(ResponsePrepareForPaymentGateway loginResponse) {
                        progressDialog.dismiss();

                        if (loginResponse.getResponse().getData().getSuccess()==false) {
                            ApplicationConstatnt.getDialog(getApplicationContext(), "", loginResponse.getResponse().getData().getMessage());
                        } else {


                        }


                    }

                    @Override
                    public void onError(Throwable e) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}