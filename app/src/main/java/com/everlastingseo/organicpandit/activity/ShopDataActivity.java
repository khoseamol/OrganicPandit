package com.everlastingseo.organicpandit.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.everlastingseo.organicpandit.R;
import com.everlastingseo.organicpandit.adapter.ShopDataAdapter;
import com.everlastingseo.organicpandit.helper.ApiClient;
import com.everlastingseo.organicpandit.helper.ApiService;
import com.everlastingseo.organicpandit.helper.ApplicationConstatnt;
import com.everlastingseo.organicpandit.helper.CustomProgressDialog;
import com.everlastingseo.organicpandit.helper.db.AppDao;
import com.everlastingseo.organicpandit.helper.db.AppDatabase;
import com.everlastingseo.organicpandit.helper.db.AppEntity;
import com.everlastingseo.organicpandit.pojo.shopdata.ShopData;
import com.everlastingseo.organicpandit.pojo.shopdata.ShopDataDetails;
import com.everlastingseo.organicpandit.productcart.activity.ProductAddCartActivity;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class ShopDataActivity extends AppCompatActivity {
    RecyclerView mRecycleview;
    Context mContext;
    ApiService apiService;
    RelativeLayout mRelativeToop;
    TextView mTxtName, mTxtTotalProduct, mTxtEmpty;
    Vibrator vibrator;
    AppDao appDao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycleview_activity);
        bindview();
    }

    private void bindview() {
        mContext = ShopDataActivity.this;
        apiService = ApiClient.getClient(getApplicationContext())
                .create(ApiService.class);

        appDao = Room.databaseBuilder(mContext, AppDatabase.class, mContext.getString(R.string.app_name)).allowMainThreadQueries().build().taskDao();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle(getIntent().getStringExtra("TIITLE") + " Data");
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        mRelativeToop = (RelativeLayout) findViewById(R.id.RelativeToop);
        mTxtName = (TextView) findViewById(R.id.TxtName);
        mTxtTotalProduct = (TextView) findViewById(R.id.TxtTotalProduct);
        mTxtEmpty = (TextView) findViewById(R.id.TxtEmpty);


        mRelativeToop.setVisibility(View.VISIBLE);

        mRecycleview = (RecyclerView) findViewById(R.id.Recycleview);
        mRecycleview.setLayoutManager(new LinearLayoutManager(mContext));

        callshopdata(getIntent().getStringExtra("USERID"));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void callshopdata(String userid) {
        final ProgressDialog progressDialog = CustomProgressDialog.ctor(ShopDataActivity.this);
        progressDialog.show();

        apiService.fetchshopData(userid, "1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ShopDataDetails>() {
                    @Override
                    public void onSuccess(ShopDataDetails userTypeResponse) {
                        progressDialog.dismiss();
                        if (userTypeResponse.getSuccess()) {
                            if (userTypeResponse.getData().size() > 0) {
                                mRecycleview.setVisibility(View.VISIBLE);
                                mRelativeToop.setVisibility(View.VISIBLE);
                                mTxtEmpty.setVisibility(View.GONE);
                                mTxtName.setText("Name : " + getIntent().getStringExtra("NAME"));
                                mTxtTotalProduct.setText("Product : " + userTypeResponse.getData().size());

                                ShopDataAdapter organicInputProductAdapter = new ShopDataAdapter(mContext, userTypeResponse.getData(),
                                        new ShopDataAdapter.getClick() {
                                    @Override
                                    public void getclickedEvent(ShopData data) {

                                        if (appDao.getAll().size() < 9) {

                                            if (appDao.isDataExist(data.getProductId())) {

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


                                            }else {
                                                if (!data.getPrice().isEmpty()) {
                                                    if (Build.VERSION.SDK_INT >= 26) {
                                                        vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
                                                    } else {
                                                        vibrator.vibrate(200);
                                                    }
                                                    AppEntity model = new AppEntity();
                                                    model.setProductType("Shop");
                                                    model.setproductName(data.getProductName());
                                                    model.setProductId(data.getProductId());
                                                    model.setpPrice(data.getPrice());
                                                    model.setESuser_ecommerce_id(data.getUserEcommerceId());
                                                    model.setESopcategory_id(data.getCategoryId());
                                                    model.setESopproduct_id(data.getProductId());
                                                    model.setItemcount("1");
                                                    appDao.insert(model);
                                                    ApplicationConstatnt.toast(mContext, "Item Added");
                                                } else {
                                                    ApplicationConstatnt.getDialog(mContext, "Response", "Price null");
                                                }
                                            }



                                        } else {
                                            ApplicationConstatnt.getDialog(mContext, "Response", "More then 9 item not allowed");
                                        }

                                    }
                                });
                                mRecycleview.setAdapter(organicInputProductAdapter);
                            } else {
                                mRelativeToop.setVisibility(View.GONE);
                                mTxtEmpty.setVisibility(View.VISIBLE);
                                mTxtEmpty.setText(userTypeResponse.getMessage());
                                mRecycleview.setVisibility(View.GONE);
                            }
                        } else {
                            mRelativeToop.setVisibility(View.GONE);
                            mTxtEmpty.setVisibility(View.VISIBLE);
                            mTxtEmpty.setText(userTypeResponse.getMessage());
                            mRecycleview.setVisibility(View.GONE);
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
