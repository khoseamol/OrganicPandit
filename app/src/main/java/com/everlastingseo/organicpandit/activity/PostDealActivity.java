package com.everlastingseo.organicpandit.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.everlastingseo.organicpandit.R;
import com.everlastingseo.organicpandit.helper.ApiClient;
import com.everlastingseo.organicpandit.helper.ApiService;
import com.everlastingseo.organicpandit.helper.ApplicationConstatnt;
import com.everlastingseo.organicpandit.helper.CustomProgressDialog;
import com.everlastingseo.organicpandit.helper.PrefUtils;
import com.everlastingseo.organicpandit.model.SelectCertificateList;
import com.everlastingseo.organicpandit.pojo.citylist.CityData;
import com.everlastingseo.organicpandit.pojo.citylist.CityRespose;
import com.everlastingseo.organicpandit.pojo.login.LoginResponse;
import com.everlastingseo.organicpandit.pojo.product.ProductResponse;
import com.everlastingseo.organicpandit.pojo.product.ProductResponseData;
import com.everlastingseo.organicpandit.pojo.statelist.StateData;
import com.everlastingseo.organicpandit.pojo.statelist.StateResponse;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class PostDealActivity extends AppCompatActivity implements View.OnClickListener {
    Context mContext;
    DatePickerDialog picker;
    ApiService apiService;
    List<StateData> stateDataList = new ArrayList<>();
    List<CityData> cityDataList = new ArrayList<>();
    String StateId = "";
    String CityId = "";
    String CERTIFICATION_ID = "";
    String LOGISTIC_ID = "";
    String PRODUCT_ID = "";

    List<ProductResponseData> productList = new ArrayList<>();
    AdapterView.OnItemSelectedListener listenerCity = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            if (cityDataList.size() > 0) {
                CityId = cityDataList.get(position).getId();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    List<SelectCertificateList> listList = new ArrayList<>();
    AdapterView.OnItemSelectedListener listenerSertifications = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            if (listList.size() > 0) {

                CERTIFICATION_ID = listList.get(position).getId();

            } else {

            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    AdapterView.OnItemSelectedListener listenerProduct = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            if (productList.size() > 0) {

                PRODUCT_ID = listList.get(position).getId();

            } else {

            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    private EditText mEditCompanyName;
    private com.toptoche.searchablespinnerlibrary.SearchableSpinner mSpinnerProduct;
    private EditText mEditquality_specification;
    private EditText mEditFromDate;
    private EditText mEditToDate;
    private EditText mEditprice;
    private EditText mEDitquantity;
    private EditText mEditToatalPrice;
    private EditText mEditdelivery_address;
    private com.everlastingseo.organicpandit.utils.font.CustomNormalEditText mEditdelivery_Pincode;
    private com.everlastingseo.organicpandit.utils.font.CustomNormalEditText mEditdelivery_PayemtTearms;
    private com.toptoche.searchablespinnerlibrary.SearchableSpinner mSpinnerState;
    private com.toptoche.searchablespinnerlibrary.SearchableSpinner mSpinnerCity;
    AdapterView.OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            if (stateDataList.size() > 0) {
                callCityList(stateDataList.get(position).getId());
                StateId = stateDataList.get(position).getId();

            } else {

            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    private com.toptoche.searchablespinnerlibrary.SearchableSpinner mSpinnerselectlogicalrequirment;
    AdapterView.OnItemSelectedListener listenerLogistic = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (mSpinnerselectlogicalrequirment.getSelectedItem().toString().trim().equals("YES")) {
                LOGISTIC_ID = "1";
            } else {
                LOGISTIC_ID = "2";
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    private com.toptoche.searchablespinnerlibrary.SearchableSpinner mSpinnerselectcertificatio;
    private EditText mEditOther;
    private Button mBtnCreatePOst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_deal);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Post Requirement");
        bindview();

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void bindview() {
        mContext = PostDealActivity.this;
        apiService = ApiClient.getClient(getApplicationContext())
                .create(ApiService.class);

        mEditCompanyName = (EditText) findViewById(R.id.EditCompanyName);

        mEditdelivery_Pincode = (com.everlastingseo.organicpandit.utils.font.CustomNormalEditText) findViewById(R.id.Editdelivery_Pincode);
        mEditdelivery_PayemtTearms = (com.everlastingseo.organicpandit.utils.font.CustomNormalEditText) findViewById(R.id.Editdelivery_PayemtTearms);
        mSpinnerProduct = (com.toptoche.searchablespinnerlibrary.SearchableSpinner) findViewById(R.id.SpinnerUserType);
        mEditquality_specification = (EditText) findViewById(R.id.Editquality_specification);
        mEditFromDate = (EditText) findViewById(R.id.EditFromDate);
        mEditToDate = (EditText) findViewById(R.id.EditToDate);
        mEditprice = (EditText) findViewById(R.id.Editprice);
        mEDitquantity = (EditText) findViewById(R.id.EDitquantity);
        mEditToatalPrice = (EditText) findViewById(R.id.EditToatalPrice);
        mEditdelivery_address = (EditText) findViewById(R.id.Editdelivery_address);
        mSpinnerState = (com.toptoche.searchablespinnerlibrary.SearchableSpinner) findViewById(R.id.SpinnerState);
        mSpinnerCity = (com.toptoche.searchablespinnerlibrary.SearchableSpinner) findViewById(R.id.SpinnerCity);
        mSpinnerselectlogicalrequirment = (com.toptoche.searchablespinnerlibrary.SearchableSpinner) findViewById(R.id.Spinnerselectlogicalrequirment);
        mSpinnerselectcertificatio = (com.toptoche.searchablespinnerlibrary.SearchableSpinner) findViewById(R.id.Spinnerselectcertificatio);
        mEditOther = (EditText) findViewById(R.id.EditOther);
        mBtnCreatePOst = (Button) findViewById(R.id.BtnCreatePOst);

        mEditFromDate.setInputType(InputType.TYPE_NULL);
        mEditToDate.setInputType(InputType.TYPE_NULL);
        mSpinnerState.setOnItemSelectedListener(listener);
        mSpinnerCity.setOnItemSelectedListener(listenerCity);
        mEditToatalPrice.setOnClickListener(this);
        mBtnCreatePOst.setOnClickListener(this);
        mSpinnerselectcertificatio.setOnItemSelectedListener(listenerSertifications);
        mSpinnerselectlogicalrequirment.setOnItemSelectedListener(listenerLogistic);
        mSpinnerProduct.setOnItemSelectedListener(listenerProduct);

        callStateList("101");
        callProduct();
        callCertifications();
        mEditFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog

                picker = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        mEditFromDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }


                }, year, month, day);
                picker.show();
            }
        });
        mEditToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog

                picker = new DatePickerDialog(mContext,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                                mEditToDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                datePicker.getMinDate();
                            }


                        }, year, month, day);
                picker.show();
            }
        });
        mEDitquantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void callProduct() {

        apiService.getproduct()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ProductResponse>() {
                    @Override
                    public void onSuccess(ProductResponse userTypeResponse) {
                        if (userTypeResponse.getSuccess()) {
                            productList.clear();
                            productList = userTypeResponse.getData();
                            ProductResponseData userTypeDatatest = new ProductResponseData();
                            userTypeDatatest.setName("Select Product");
                            productList.add(0, userTypeDatatest);
                            ArrayAdapter<ProductResponseData> adapter = new ArrayAdapter<ProductResponseData>(mContext, R.layout.spinner_item_textcolorblck, productList);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            mSpinnerProduct.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private void callCertifications() {
        listList.clear();
        listList = ApplicationConstatnt.getSelectCertificateLists();

        SelectCertificateList userTypeDatatest = new SelectCertificateList();
        userTypeDatatest.setName("Select Certification");
        listList.add(0, userTypeDatatest);
        ArrayAdapter<SelectCertificateList> adapter = new ArrayAdapter<SelectCertificateList>(mContext, R.layout.spinner_item_textcolorblck, listList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerselectcertificatio.setAdapter(adapter);


    }

    private void callStateList(String id) {
        final ProgressDialog progressDialog = CustomProgressDialog.ctor(PostDealActivity.this);
        progressDialog.show();

        apiService.getstateList(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<StateResponse>() {
                    @Override
                    public void onSuccess(StateResponse userTypeResponse) {
                        progressDialog.dismiss();
                        if (userTypeResponse.getSuccess()) {
                            stateDataList.clear();
                            stateDataList = userTypeResponse.getData();
                            StateData userTypeDatatest = new StateData();
                            userTypeDatatest.setName("Select State");
                            stateDataList.add(0, userTypeDatatest);
                            ArrayAdapter<StateData> adapter = new ArrayAdapter<StateData>(mContext, R.layout.spinner_item_textcolorblck, stateDataList);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            mSpinnerState.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressDialog.dismiss();
                        Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private void callCityList(String id) {
//        final ProgressDialog progressDialog = CustomProgressDialog.ctor(PostDealActivity.this);
//        progressDialog.show();

        apiService.getcities(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<CityRespose>() {
                    @Override
                    public void onSuccess(CityRespose userTypeResponse) {
//                        progressDialog.dismiss();
                        if (userTypeResponse.getSuccess()) {
                            cityDataList.clear();
                            cityDataList = userTypeResponse.getData();
                            CityData userTypeDatatest = new CityData();
                            userTypeDatatest.setName("Select City");
                            cityDataList.add(0, userTypeDatatest);
                            ArrayAdapter<CityData> adapter = new ArrayAdapter<CityData>(mContext, R.layout.spinner_item_textcolorblck, cityDataList);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            mSpinnerCity.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
//                        progressDialog.dismiss();
                        Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.EditToatalPrice:
                if (TextUtils.isEmpty(mEditprice.getText().toString().trim())) {
                    mEditprice.setError("Enter price");
                } else {
                    mEditprice.setError(null);
                    if (TextUtils.isEmpty(mEDitquantity.getText().toString().trim())) {
                        mEDitquantity.setError("Enter quantity");
                    } else {
                        mEDitquantity.setError(null);
                        mEditToatalPrice.setText("" + (Integer.parseInt(mEditprice.getText().toString().trim()) * (Integer.parseInt(mEDitquantity.getText().toString().trim()))));
                    }

                }

                break;
            case R.id.BtnCreatePOst:
                if (TextUtils.isEmpty(mEditCompanyName.getText().toString().trim())) {
                    mEditCompanyName.setError("Enter Company Name");
                } else {
                    mEditCompanyName.setError(null);
                    if (TextUtils.isEmpty(mEditquality_specification.getText().toString().trim())) {
                        mEditquality_specification.setError("Quality Specification");
                    } else {
                        mEditquality_specification.setError(null);
                        if (TextUtils.isEmpty(mEditFromDate.getText().toString().trim())) {
                            mEditFromDate.setError("Enter From Date");
                        } else {
                            mEditFromDate.setError(null);
                            if (TextUtils.isEmpty(mEditToDate.getText().toString().trim())) {
                                mEditToDate.setError("Enter To Date");
                            } else {
                                mEditToDate.setError(null);
                                if (TextUtils.isEmpty(mEditprice.getText().toString().trim())) {
                                    mEditprice.setError("Enter Price");
                                } else {
                                    mEditprice.setError(null);
                                    if (TextUtils.isEmpty(mEDitquantity.getText().toString().trim())) {
                                        mEDitquantity.setError("Enter quantity");
                                    } else {
                                        mEDitquantity.setError(null);
                                        if (TextUtils.isEmpty(mEditToatalPrice.getText().toString().trim())) {
                                            mEditToatalPrice.setError("Total price");
                                        } else {
                                            mEditToatalPrice.setError(null);
                                            if (TextUtils.isEmpty(mEditdelivery_address.getText().toString().trim())) {
                                                mEditdelivery_address.setError("Enter Delivery Address");
                                            } else {
                                                mEditdelivery_address.setError(null);
                                                if (TextUtils.isEmpty(mEditOther.getText().toString().trim())) {
                                                    mEditOther.setError("Other Details");
                                                } else {
                                                    mEditOther.setError(null);
                                                    if (TextUtils.isEmpty(mEditdelivery_Pincode.getText().toString().trim())) {
                                                        mEditdelivery_Pincode.setError("Pincode");
                                                    } else {
                                                        mEditdelivery_Pincode.setError(null);
                                                        if (TextUtils.isEmpty(mEditdelivery_PayemtTearms.getText().toString().trim())) {
                                                            mEditdelivery_PayemtTearms.setError("Payment Tearms");
                                                        } else {
                                                            mEditdelivery_PayemtTearms.setError(null);

                                                            callCreatePOST(mEditCompanyName.getText().toString().trim(), PRODUCT_ID,
                                                                    mEditquality_specification.getText().toString().trim(), mEditFromDate.getText().toString().trim(),
                                                                    mEditToDate.getText().toString().trim(), mEditprice.getText().toString().trim(),
                                                                    mEDitquantity.getText().toString().trim(), mEditToatalPrice.getText().toString().trim(),
                                                                    mEditdelivery_address.getText().toString().trim(), mEditdelivery_Pincode.getText().toString().trim(),
                                                                    mEditdelivery_PayemtTearms.getText().toString().trim(), StateId, CityId,
                                                                    LOGISTIC_ID, CERTIFICATION_ID, mEditOther.getText().toString().trim());
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                break;
        }
    }

    private void callCreatePOST(String companyname, String product_id, String qualityspeci, String framdate, String todate, String price, String quantity, String totalprice, String deliveryaddress, String pincode, String paymentterms, String stateId, String cityId, String logistic_id, String certification_id, String others) {
        final ProgressDialog progressDialog = CustomProgressDialog.ctor(PostDealActivity.this);
        progressDialog.show();

        apiService.getCreatePost(PrefUtils.getFromPrefs(mContext, "user_id", ""), companyname, product_id, qualityspeci, framdate, todate, price, quantity, totalprice,
                deliveryaddress, pincode, paymentterms, stateId, cityId, logistic_id, certification_id, others)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<LoginResponse>() {
                    @Override
                    public void onSuccess(LoginResponse userTypeResponse) {
                        progressDialog.dismiss();
                        if (userTypeResponse.getSuccess()) {
                            ApplicationConstatnt.getDialog(mContext, "", userTypeResponse.getMessage());
                            mEditCompanyName.setText("");
                            mEditquality_specification.setText("");
                            mEditFromDate.setText("");
                            mEditToDate.setText("");
                            mEDitquantity.setText("");
                            mEditprice.setText("");
                            mEditToatalPrice.setText("");
                            mEditdelivery_address.setText("");
                            mEditdelivery_Pincode.setText("");
                            mEditdelivery_PayemtTearms.setText("");
                            mEditOther.setText("");
                        }else {
                            ApplicationConstatnt.getDialog(mContext,"" , userTypeResponse.getMessage());
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
