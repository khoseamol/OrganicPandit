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
import com.everlastingseo.organicpandit.adapter.OrganicInputProductAdapter;
import com.everlastingseo.organicpandit.helper.ApiClient;
import com.everlastingseo.organicpandit.helper.ApiService;
import com.everlastingseo.organicpandit.helper.ApplicationConstatnt;
import com.everlastingseo.organicpandit.helper.CustomProgressDialog;
import com.everlastingseo.organicpandit.helper.db.AppDao;
import com.everlastingseo.organicpandit.helper.db.AppDatabase;
import com.everlastingseo.organicpandit.helper.db.AppEntity;
import com.everlastingseo.organicpandit.pojo.organicInput_product.OrganicProductData;
import com.everlastingseo.organicpandit.pojo.organicInput_product.OrganicProductREsponse;
import com.everlastingseo.organicpandit.pojo.sellproduct.SellProductResponseData;
import com.everlastingseo.organicpandit.productcart.activity.ProductAddCartActivity;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class OrganicInputProductActivity extends AppCompatActivity {
    RecyclerView mRecycleview;
    Context mContext;
    ApiService apiService;
    RelativeLayout mRelativeToop;
    TextView mTxtName, mTxtTotalProduct,mTxtEmpty;
    Vibrator vibrator;
    ProgressDialog progressDialog;
    AppDao appDao;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycleview_activity);
        bindview();
    }

    private void bindview() {
        mContext = OrganicInputProductActivity.this;
        apiService = ApiClient.getClient(getApplicationContext())
                .create(ApiService.class);

        appDao = Room.databaseBuilder(mContext, AppDatabase.class, mContext.getString(R.string.app_name)).allowMainThreadQueries().build().taskDao();
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle(getIntent().getStringExtra("TIITLE") + " ");

        mRelativeToop = (RelativeLayout) findViewById(R.id.RelativeToop);
        mTxtName = (TextView) findViewById(R.id.TxtName);
        mTxtTotalProduct = (TextView) findViewById(R.id.TxtTotalProduct);
        mTxtEmpty=(TextView)findViewById(R.id.TxtEmpty);

        mRecycleview = (RecyclerView) findViewById(R.id.Recycleview);
        mRecycleview.setLayoutManager(new LinearLayoutManager(mContext));


        mRelativeToop.setVisibility(View.VISIBLE);
        progressDialog = CustomProgressDialog.ctor(OrganicInputProductActivity.this);

        callOrganicData(getIntent().getStringExtra("USERID"));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.cart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.cartAction) {
            Intent intent = new Intent(mContext, ProductAddCartActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void callOrganicData(String userid) {
        final ProgressDialog progressDialog = CustomProgressDialog.ctor(OrganicInputProductActivity.this);
        progressDialog.show();

        apiService.FetchorganicProduct(userid, "1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<OrganicProductREsponse>() {
                    @Override
                    public void onSuccess(OrganicProductREsponse userTypeResponse) {
                        progressDialog.dismiss();
                        if (userTypeResponse.getSuccess()) {
                            if (userTypeResponse.getData().size() > 0) {
                                mTxtName.setText("Name : " + getIntent().getStringExtra("NAME"));
                                mTxtTotalProduct.setText("Product : " + userTypeResponse.getData().size());
                                OrganicInputProductAdapter organicInputProductAdapter = new OrganicInputProductAdapter(mContext, userTypeResponse.getData(), new
                                        OrganicInputProductAdapter.ClickEvent() {
                                            @Override
                                            public void getClickData(OrganicProductData organicProductData) {



                                                if (appDao.getAll().size() < 9) {

                                                    if (appDao.isDataExist(organicProductData.getId())) {

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


                                                    }else{
                                                        if(!organicProductData.getPrice().isEmpty()){
                                                            if (Build.VERSION.SDK_INT >= 26) {
                                                                vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
                                                            } else {
                                                                vibrator.vibrate(200);
                                                            }
                                                            AppEntity model = new AppEntity();
                                                            model.setProductId(organicProductData.getId());
                                                            model.setProductType("Organic Pandit");
                                                            model.setproductName(organicProductData.getCategoryName());
                                                            model.setpPrice(organicProductData.getPrice());
                                                            model.setEOIorganic_input_ecommerce_id(organicProductData.getId());
                                                            model.setEOIcategory_id(organicProductData.getCategoryId());
                                                            model.setEOIsub_category_id(organicProductData.getSubCategoryId());
                                                            model.setEOIbrand(organicProductData.getEcommerceBrandId());
                                                            model.setProductImg(organicProductData.getImages());

                                                            model.setItemcount("1");
                                                            appDao.insert(model);
                                                            ApplicationConstatnt.toast(mContext, "Item Added");
                                                        }else {
                                                            ApplicationConstatnt.getDialog(mContext, "", "Price null");

                                                        }
                                                    }

                                                } else {
                                                    ApplicationConstatnt.getDialog(mContext, "", "More then 9 item not allowed");
                                                }
                                            }
                                        });
                                mRecycleview.setAdapter(organicInputProductAdapter);
                                mRecycleview.setVisibility(View.VISIBLE);
                                mRelativeToop.setVisibility(View.VISIBLE);
                            } else {
                                mRelativeToop.setVisibility(View.GONE);
                                mRecycleview.setVisibility(View.GONE);
                                mTxtEmpty.setVisibility(View.VISIBLE);
                                mTxtEmpty.setText(userTypeResponse.getMessage());
                            }
                        } else {
                            mRelativeToop.setVisibility(View.GONE);
                            mRecycleview.setVisibility(View.GONE);
                            mTxtEmpty.setVisibility(View.VISIBLE);
                            mTxtEmpty.setText(userTypeResponse.getMessage());
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
