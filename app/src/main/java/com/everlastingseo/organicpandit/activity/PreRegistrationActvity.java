package com.everlastingseo.organicpandit.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.everlastingseo.organicpandit.R;
import com.everlastingseo.organicpandit.adapter.PreRegistrationAdapter;
import com.everlastingseo.organicpandit.model.DashboardServiceModel;

import java.util.ArrayList;
import java.util.List;


public class PreRegistrationActvity extends AppCompatActivity implements View.OnClickListener {
    TextView mTxtAlreadyLogin;
    Context mContext;
    RecyclerView mServiceRecycleView;
    List<DashboardServiceModel> modelList = new ArrayList<DashboardServiceModel>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(R.layout.registrationforuser);
        bindview();
    }

    private void bindview() {
        mContext= PreRegistrationActvity.this;
        mTxtAlreadyLogin = (TextView) findViewById(R.id.TxtAlreadyLogin);
        mTxtAlreadyLogin.setOnClickListener(this);
        mServiceRecycleView = (RecyclerView) findViewById(R.id.ServiceRecycleView);
        mServiceRecycleView.setLayoutManager(new GridLayoutManager(mContext, 4));
        generateServiceModel();
        PreRegistrationAdapter dashBoardServiceAdapter = new PreRegistrationAdapter(mContext, modelList);
        mServiceRecycleView.setAdapter(dashBoardServiceAdapter);

    }
    private void generateServiceModel() {
        DashboardServiceModel service1 = new DashboardServiceModel("1","Farmer", R.drawable.icon_farmer);
        DashboardServiceModel service2 = new DashboardServiceModel("2","FPO", R.drawable.icon_fpo);
        DashboardServiceModel service3 = new DashboardServiceModel("3","Trader", R.drawable.icon_trader);
        DashboardServiceModel service4 = new DashboardServiceModel("4","Processor", R.drawable.icon_processor);
        DashboardServiceModel service5 = new DashboardServiceModel("5","BUYER", R.drawable.icon_buyer);
        DashboardServiceModel service6 = new DashboardServiceModel("6","Organic Consultant", R.drawable.icon_organic_consultant);
        DashboardServiceModel service7 = new DashboardServiceModel("18","NGO", R.drawable.icon_ngo);
        DashboardServiceModel service8 = new DashboardServiceModel("17","Restaurants", R.drawable.icon_restaurant);
        DashboardServiceModel service9 = new DashboardServiceModel("16","Certification Agencies", R.drawable.icon_certification_agencies);
        DashboardServiceModel service10 = new DashboardServiceModel("15","Institution", R.drawable.icon_organic_inputs);
        DashboardServiceModel service11 = new DashboardServiceModel("14","Goverment Agent", R.drawable.icon_government_agencies);
        DashboardServiceModel service12 = new DashboardServiceModel("13","Lab", R.drawable.icon_labs);
        DashboardServiceModel service13 = new DashboardServiceModel("12","Shop", R.drawable.icon_shops);
        DashboardServiceModel service14 = new DashboardServiceModel("11","Exhibitor", R.drawable.icon_exhibitor);
        DashboardServiceModel service15 = new DashboardServiceModel("10","Farm Equipment", R.drawable.icon_farm_equipment);
        DashboardServiceModel service16 = new DashboardServiceModel("9","Logistic", R.drawable.icon_logistic);
        DashboardServiceModel service17 = new DashboardServiceModel("8","Packaging", R.drawable.icon_packaging);
        DashboardServiceModel service18 = new DashboardServiceModel("7","Organic Input", R.drawable.icon_organic_inputs);

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
        if (view.getId() == R.id.TxtAlreadyLogin) {
            Intent intent
                    = new Intent(PreRegistrationActvity.this, LoginActvity.class);
            startActivity(intent);
            finish();

        } else {

        }

    }
}

