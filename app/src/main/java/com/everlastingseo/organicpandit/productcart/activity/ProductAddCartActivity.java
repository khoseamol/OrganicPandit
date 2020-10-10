package com.everlastingseo.organicpandit.productcart.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.everlastingseo.organicpandit.R;
import com.everlastingseo.organicpandit.adapter.PaymentGatewayInitiate;
import com.everlastingseo.organicpandit.helper.ApiClient;
import com.everlastingseo.organicpandit.helper.ApiService;
import com.everlastingseo.organicpandit.helper.ApplicationConstatnt;
import com.everlastingseo.organicpandit.helper.CustomProgressDialog;
import com.everlastingseo.organicpandit.helper.PrefUtils;
import com.everlastingseo.organicpandit.helper.db.AppDao;
import com.everlastingseo.organicpandit.helper.db.AppDatabase;
import com.everlastingseo.organicpandit.helper.db.AppEntity;
import com.everlastingseo.organicpandit.pojo.preparepaymentgatway.ResponsePrepareForPaymentGateway;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class ProductAddCartActivity extends AppCompatActivity implements View.OnClickListener {
    Context mContext;
    List<AppEntity> modelList = new ArrayList<>();
    RelativeLayout mRelativeNotItemData, mRelativeTotalRelated;
    RecyclerView mRecycleviewCartData;
    CartDataAdapter adapter;
    TextView mTxtTotalAmount, mTxtSubTotalItemCount, mTxtProceedToPay;
    AppDao appDao;
    double totalAmount = 00;
    int itemSize = 0;

    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_cart);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Cart Details");

        bindview();
    }

    private void bindview() {
        mContext = ProductAddCartActivity.this;
        apiService = ApiClient.getClient(getApplicationContext())
                .create(ApiService.class);
        mRecycleviewCartData = (RecyclerView) findViewById(R.id.RecycleviewCartData);
        mRecycleviewCartData.setLayoutManager(new LinearLayoutManager(mContext));
        mRelativeNotItemData = (RelativeLayout) findViewById(R.id.RelativeNotItemData);
        mRelativeTotalRelated = (RelativeLayout) findViewById(R.id.RelativeTotalRelated);
        mTxtTotalAmount = (TextView) findViewById(R.id.TxtTotalAmount);
        mTxtSubTotalItemCount = (TextView) findViewById(R.id.TxtSubTotalItemCount);
        mTxtProceedToPay = (TextView) findViewById(R.id.TxtProceedToPay);
        mTxtProceedToPay.setOnClickListener(this);

        appDao = Room.databaseBuilder(mContext, AppDatabase.class, mContext.getString(R.string.app_name)).allowMainThreadQueries().build().taskDao();
        modelList = appDao.getAll();

        if (modelList.size() > 0) {
            mRecycleviewCartData.setVisibility(View.VISIBLE);
            mRelativeTotalRelated.setVisibility(View.VISIBLE);
            mRelativeNotItemData.setVisibility(View.GONE);
        } else {
            mRelativeTotalRelated.setVisibility(View.GONE);
            mRecycleviewCartData.setVisibility(View.GONE);
            mRelativeNotItemData.setVisibility(View.VISIBLE);
        }


        for (AppEntity posotion : modelList) {
            totalAmount = totalAmount + Double.valueOf(posotion.getpPrice()) * Double.valueOf(posotion.getItemcount());
            itemSize = itemSize + Integer.valueOf(posotion.getItemcount());
        }
        mTxtTotalAmount.setText(String.valueOf(totalAmount));
        mTxtSubTotalItemCount.setText("Total " + getResources().getString(R.string.Rs));


        if (modelList.size() > 0) {
            mRecycleviewCartData.setVisibility(View.VISIBLE);
            mRelativeNotItemData.setVisibility(View.GONE);
            adapter = new CartDataAdapter(mContext, modelList, new CartDataAdapter.ClickeEventHandel() {
                @Override
                public void getdelete(AppEntity appEntity) {

                    appDao.delete(appEntity.getId());
                    adapter.updateData(appDao.getAll());
                    itemSize = itemSize - Integer.valueOf(appEntity.getItemcount());

                    if (totalAmount > Double.valueOf(appEntity.getpPrice())) {
                        double removeAmount = Double.valueOf(appEntity.getpPrice()) * Double.valueOf(appEntity.getItemcount());
                        double afterDeleteTotalAmount = totalAmount - removeAmount;
                        totalAmount = afterDeleteTotalAmount;
                        mTxtTotalAmount.setText(String.valueOf(totalAmount));
                        mTxtSubTotalItemCount.setText("Total " + getResources().getString(R.string.Rs));

                    } else {
                        callTotalAmount(appDao.getAll());
                        ApplicationConstatnt.toast(mContext, "Item Not Available");
                    }

                }

                @Override
                public void addItem(AppEntity appEntity) {

                    if (itemSize >= 9) {
                        ApplicationConstatnt.getDialog(mContext, "Response", "Greater then 9 item not Allowed");
                    } else {
                        itemSize = itemSize + 1;
                        double afteraddTotalAmount = totalAmount + Double.valueOf(appEntity.getpPrice());
                        totalAmount = afteraddTotalAmount;
                        mTxtTotalAmount.setText(String.valueOf(afteraddTotalAmount));
                        mTxtSubTotalItemCount.setText("Total " + getResources().getString(R.string.Rs));

                        appDao.update(String.valueOf(Integer.valueOf(appEntity.getItemcount()) + 1), appEntity.getId());
                        adapter.updateData(appDao.getAll());

                    }
                }

                @Override
                public void removeItem(AppEntity appEntity) {
                    itemSize = itemSize - 1;
                    if (Integer.parseInt(appEntity.getItemcount()) > 1) {
                        appDao.update(String.valueOf(Integer.valueOf(appEntity.getItemcount()) - 1), appEntity.getId());
                        adapter.updateData(appDao.getAll());
                    } else {
                        appDao.delete(appEntity.getId());
                        adapter.updateData(appDao.getAll());
//                        ApplicationConstatnt.toast(mContext, "Item Not Available");
                    }
                    double afterRemoveTotalAmount = totalAmount - Double.valueOf(appEntity.getpPrice());
                    totalAmount = afterRemoveTotalAmount;
                    mTxtTotalAmount.setText(String.valueOf(afterRemoveTotalAmount));
                    mTxtSubTotalItemCount.setText("Total " + getResources().getString(R.string.Rs));


                }
            });
            mRecycleviewCartData.setAdapter(adapter);
        } else {
            mRecycleviewCartData.setVisibility(View.GONE);
            mRelativeNotItemData.setVisibility(View.VISIBLE);
        }
    }

    private void callTotalAmount(List<AppEntity> modelList) {


        if (modelList.size() > 0) {
            mRecycleviewCartData.setVisibility(View.VISIBLE);
            mRelativeTotalRelated.setVisibility(View.VISIBLE);
            mRelativeNotItemData.setVisibility(View.GONE);

            for (AppEntity posotion : modelList) {
                totalAmount = totalAmount + Double.valueOf(posotion.getpPrice());
            }
            mTxtTotalAmount.setText(String.valueOf(totalAmount));
            mTxtSubTotalItemCount.setText("Subtotal ( " + itemSize + " items )");

        } else {
            mRelativeTotalRelated.setVisibility(View.GONE);
            mRecycleviewCartData.setVisibility(View.GONE);
            mRelativeNotItemData.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.TxtProceedToPay:
                if (Double.valueOf(mTxtTotalAmount.getText().toString().trim()) > 0) {

                    if (PrefUtils.getFromPrefs(mContext, "Address", "").equals("TRUE")) {
                        try {

                            JsonObject mainJsonObject = new JsonObject();
                            mainJsonObject.addProperty(ApplicationConstatnt.Request_app_version, "1.0");
                            JsonObject objAuth = new JsonObject();
                            objAuth.addProperty(ApplicationConstatnt.Request_type, "user");
                            objAuth.addProperty(ApplicationConstatnt.Request_token, PrefUtils.getFromPrefs(mContext, "userToken", ""));
                            mainJsonObject.add(ApplicationConstatnt.Request_Auth, objAuth);

                            JsonObject objMethod = new JsonObject();
                            objMethod.addProperty(ApplicationConstatnt.Request_name, "AddOrder");

                            mainJsonObject.add(ApplicationConstatnt.Request_method, objMethod);

                            JsonObject objParameter = new JsonObject();
                            objMethod.add(ApplicationConstatnt.Request_parameters, objParameter);
                            objParameter.addProperty("user_id", PrefUtils.getFromPrefs(mContext, "user_id", ""));
                            objParameter.addProperty("user_type_id", PrefUtils.getFromPrefs(mContext, "UserTYPE_ID", ""));
                            objParameter.addProperty("fullname", PrefUtils.getFromPrefs(mContext, "ADDname", ""));
                            objParameter.addProperty("email_id", PrefUtils.getFromPrefs(mContext, "ADDemail", ""));
                            objParameter.addProperty("mobile_no", PrefUtils.getFromPrefs(mContext, "ADDmobile", ""));
                            objParameter.addProperty("state_id", PrefUtils.getFromPrefs(mContext, "ADDstateID", ""));
                            objParameter.addProperty("city_id", PrefUtils.getFromPrefs(mContext, "ADDcityID", ""));
                            objParameter.addProperty("pincode", PrefUtils.getFromPrefs(mContext, "ADDpincode", ""));
                            objParameter.addProperty("address", PrefUtils.getFromPrefs(mContext, "ADDflatnumber", "") + " " + PrefUtils.getFromPrefs(mContext, "ADDstreetname", ""));
                            objParameter.addProperty("payment_method", ApplicationConstatnt.PAYMNET_METHOD);
                            objParameter.addProperty("total_amount", String.valueOf(totalAmount));


                            JsonArray jsonArrBuyProduct = new JsonArray();
                            JsonArray jsonArrEcommerceOrganicInput = new JsonArray();
                            JsonArray jsonArrShop = new JsonArray();
                            JsonObject jsonAdd = new JsonObject();

                            if (appDao.getAll().size() > 0) {
                                for (int i = 0; i < appDao.getAll().size(); i++) {
                                    if (appDao.getAll().get(i).getProductType().equals("Buy Product")) {
                                        JsonObject pnObj = new JsonObject();
                                        pnObj.addProperty("Id", appDao.getAll().get(i).getProductId());
                                        pnObj.addProperty("Qty", appDao.getAll().get(i).getItemcount());
                                        pnObj.addProperty("Price", appDao.getAll().get(i).getpPrice());
                                        pnObj.addProperty("Name", appDao.getAll().get(i).getproductName());
                                        pnObj.addProperty("subtotal", (Integer.valueOf(appDao.getAll().get(i).getpPrice()) * Integer.valueOf(appDao.getAll().get(i).getItemcount())));
                                        JsonObject jsObj_options = new JsonObject();

                                        pnObj.add("options", jsObj_options);
                                        jsObj_options.addProperty("cart_order_type", ApplicationConstatnt.BP_CART_TYPE_ID_1);
                                        jsObj_options.addProperty("product_id", appDao.getAll().get(i).getProductId());
                                        jsObj_options.addProperty("sell_product_id", appDao.getAll().get(i).getBPsell_product_id());
                                        jsonArrBuyProduct.add(pnObj);
                                        jsonAdd.add("buy_product", jsonArrBuyProduct);
                                    }

                                    if (appDao.getAll().get(i).getProductType().equals("Shop")) {
                                        JsonObject pnObjshop = new JsonObject();
                                        pnObjshop.addProperty("Id", appDao.getAll().get(i).getESuser_ecommerce_id());
                                        pnObjshop.addProperty("Qty", appDao.getAll().get(i).getItemcount());
                                        pnObjshop.addProperty("Price", appDao.getAll().get(i).getpPrice());
                                        pnObjshop.addProperty("Name", appDao.getAll().get(i).getproductName());
                                        pnObjshop.addProperty("subtotal", (Integer.valueOf(appDao.getAll().get(i).getpPrice()) * Integer.valueOf(appDao.getAll().get(i).getItemcount())));


                                        JsonObject jsObj_options = new JsonObject();

                                        pnObjshop.add("options", jsObj_options);
                                        jsObj_options.addProperty("cart_order_type", ApplicationConstatnt.ES_CART_TYPE_ID_3);
                                        jsObj_options.addProperty("user_ecommerce_id", appDao.getAll().get(i).getESuser_ecommerce_id());
                                        jsObj_options.addProperty("category_id", appDao.getAll().get(i).getESopcategory_id());
                                        jsObj_options.addProperty("product_id", appDao.getAll().get(i).getProductId());
                                        jsonArrShop.add(pnObjshop);
                                        jsonAdd.add("ecommerce_shops", jsonArrShop);
                                    }
                                    if (appDao.getAll().get(i).getProductType().equals("Organic Pandit")) {
                                        JsonObject pnObjOrganic = new JsonObject();
                                        pnObjOrganic.addProperty("Id", appDao.getAll().get(i).getEOIorganic_input_ecommerce_id());
                                        pnObjOrganic.addProperty("Qty", appDao.getAll().get(i).getItemcount());
                                        pnObjOrganic.addProperty("Price", appDao.getAll().get(i).getpPrice());
                                        pnObjOrganic.addProperty("Name", appDao.getAll().get(i).getproductName());
                                        pnObjOrganic.addProperty("subtotal", (Integer.valueOf(appDao.getAll().get(i).getpPrice()) * Integer.valueOf(appDao.getAll().get(i).getItemcount())));

                                        JsonObject jsObj_options = new JsonObject();

                                        pnObjOrganic.add("options", jsObj_options);
                                        jsObj_options.addProperty("cart_order_type", ApplicationConstatnt.EOI_CART_TYPE_ID_2);
                                        jsObj_options.addProperty("organic_input_ecommerce_id", appDao.getAll().get(i).getProductId());
                                        jsObj_options.addProperty("category_id", appDao.getAll().get(i).getEOIcategory_id());
                                        jsObj_options.addProperty("sub_category_id", appDao.getAll().get(i).getEOIsub_category_id());
                                        jsObj_options.addProperty("brand", appDao.getAll().get(i).getEOIbrand());


                                        jsonArrEcommerceOrganicInput.add(pnObjOrganic);
                                        jsonAdd.add("ecommerce_organic_input", jsonArrEcommerceOrganicInput);

                                    }

                                }
                                objParameter.add("product_details", jsonAdd);
                            }
                            CallOrderAPi(mainJsonObject);

                        } catch (Exception e) {
                            e.printStackTrace();
                            ApplicationConstatnt.toast(mContext, "Somthing went wrong");

                        }
//                        OrderJsonClass jsonClass = new OrderJsonClass();
//                        jsonClass.setAddress();
//                        jsonClass.setFullname(PrefUtils.getFromPrefs(mContext, "ADDname", ""));
//                        jsonClass.setMobile_no(PrefUtils.getFromPrefs(mContext, "ADDmobile", ""));
//                        jsonClass.setPincode(PrefUtils.getFromPrefs(mContext, "ADDpincode", ""));
//                        jsonClass.setCity_id(PrefUtils.getFromPrefs(mContext, "ADDcityID", ""));
//                        jsonClass.setState_id(PrefUtils.getFromPrefs(mContext, "ADDstateID", ""));
//                        jsonClass.setEmail_id(PrefUtils.getFromPrefs(mContext, "ADDemail", ""));
//                        jsonClass.setPayment_method("2");
//
//                        if (appDao.getAll().size() > 0) {
//                            ProductInformation productInformation = new ProductInformation();
//                            List<BuyProduct> buyProduct = new ArrayList<BuyProduct>();
//                            List<Shop> ShopList = new ArrayList<Shop>();
//                            List<OrganicPandit> organicPanditList = new ArrayList<OrganicPandit>();
//
//                            BuyProduct buyProduct1 = new BuyProduct();
//                            OrganicPandit organicPandit = new OrganicPandit();
//                            Shop shop = new Shop();
//                            for (int i = 0; i < appDao.getAll().size(); i++) {
//                                if (appDao.getAll().get(i).getProductType().equals("Buy Product")) {
//                                    buyProduct1.setId(appDao.getAll().get(i).getProductId());
//                                    buyProduct1.setQty(appDao.getAll().get(i).getItemcount());
//                                    buyProduct1.setPrice(appDao.getAll().get(i).getpPrice());
//                                    buyProduct1.setName(appDao.getAll().get(i).getproductName());
//                                    buyProduct1.setSubtotal(appDao.getAll().get(i).getpPrice());
//                                    buyProduct.add(buyProduct1);
//
//                                }
//
//                                if (appDao.getAll().get(i).getProductType().equals("Shop")) {
//                                    shop.setId(appDao.getAll().get(i).getProductId());
//                                    shop.setQty(appDao.getAll().get(i).getItemcount());
//                                    shop.setPrice(appDao.getAll().get(i).getpPrice());
//                                    shop.setName(appDao.getAll().get(i).getproductName());
//                                    shop.setSubtotal(appDao.getAll().get(i).getpPrice());
//                                    ShopList.add(shop);
//                                }
//                                if (appDao.getAll().get(i).getProductType().equals("Organic Pandit")) {
//                                    organicPandit.setId(appDao.getAll().get(i).getProductId());
//                                    organicPandit.setQty(appDao.getAll().get(i).getItemcount());
//                                    organicPandit.setPrice(appDao.getAll().get(i).getpPrice());
//                                    organicPandit.setName(appDao.getAll().get(i).getproductName());
//                                    organicPandit.setSubtotal(appDao.getAll().get(i).getpPrice());
//                                    organicPanditList.add(organicPandit);
//                                }
//
//                            }
//                            productInformation.setBuyProductList(buyProduct);
//                            productInformation.setShops(ShopList);
//                            productInformation.setOrganicPandit(organicPanditList);
//                            jsonClass.setProductInformation(productInformation);
//
//                        }
//
//
//                        try {
//                            JSONObject jo = new JSONObject(String.valueOf(jsonClass));
//                        } catch (Exception e) {
//
//                        }


                    } else {
                        Intent intent = new Intent(mContext, DeliveryAddressActivity.class);
                        startActivity(intent);
                    }


                } else {
                    ApplicationConstatnt.getDialog(mContext, "Response", "Item not found");

                }
                break;
        }
    }

    private void CallOrderAPi(JsonObject jsonObj) {
        final ProgressDialog progressDialog = CustomProgressDialog.ctor(ProductAddCartActivity.this);
        progressDialog.show();

        apiService.getorderAPi(jsonObj)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ResponsePrepareForPaymentGateway>() {
                    @Override
                    public void onSuccess(ResponsePrepareForPaymentGateway loginResponse) {
                        progressDialog.dismiss();
                        Intent intent=new Intent(mContext, PaymentGatewayInitiate.class);
                        intent.putExtra("Data",loginResponse);
                        startActivity(intent);

                    }

                    @Override
                    public void onError(Throwable e) {
                        progressDialog.dismiss();
                        Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
