package com.everlastingseo.organicpandit.productcart.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.everlastingseo.organicpandit.R;
import com.everlastingseo.organicpandit.helper.ApiClient;
import com.everlastingseo.organicpandit.helper.ApiService;
import com.everlastingseo.organicpandit.helper.CustomProgressDialog;
import com.everlastingseo.organicpandit.helper.PrefUtils;
import com.everlastingseo.organicpandit.pojo.citylist.CityData;
import com.everlastingseo.organicpandit.pojo.citylist.CityRespose;
import com.everlastingseo.organicpandit.pojo.statelist.StateData;
import com.everlastingseo.organicpandit.pojo.statelist.StateResponse;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class DeliveryAddressActivity extends AppCompatActivity implements View.OnClickListener {
    Button mBtnSubmit;
    Context mContext;
    List<StateData> stateDataList = new ArrayList<>();
    List<CityData> cityDataList = new ArrayList<>();
    ApiService apiService;
    String StateId = "";
    String CityId = "";
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
    private EditText mEditName;
    private EditText mEditPincode;
    private EditText mEditFlatNo;
    private EditText mEditStreetName;
    private EditText mEditEmail;
    private EditText mEditMobile;
    private SearchableSpinner mSpinnerSelectState;
    private SearchableSpinner mSpinnerSelectCity;
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

    public static boolean isValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_address);
        bindview();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void bindview() {
        mContext = DeliveryAddressActivity.this;
        apiService = ApiClient.getClient(getApplicationContext())
                .create(ApiService.class);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Add Address");
         progressDialog = CustomProgressDialog.ctor(DeliveryAddressActivity.this);

        mEditName = (EditText) findViewById(R.id.EditName);
        mEditPincode = (EditText) findViewById(R.id.EditPincode);
        mEditFlatNo = (EditText) findViewById(R.id.EditFlatNo);
        mEditStreetName = (EditText) findViewById(R.id.EditStreetName);
        mEditEmail = (EditText) findViewById(R.id.EditEmail);
        mEditMobile = (EditText) findViewById(R.id.EditMobile);
        mBtnSubmit = (Button) findViewById(R.id.BtnSubmit);
        mSpinnerSelectState=(SearchableSpinner)findViewById(R.id.SpinnerSelectState);
        mSpinnerSelectCity=(SearchableSpinner)findViewById(R.id.SpinnerSelectCity);
        mBtnSubmit.setOnClickListener(this);
        mSpinnerSelectState.setOnItemSelectedListener(listener);
        mSpinnerSelectCity.setOnItemSelectedListener(listenerCity);
        callStateList("101");

    }

    private void callCityList(String id) {
//        final ProgressDialog progressDialog = CustomProgressDialog.ctor(DeliveryAddressActivity.this);
        progressDialog.show();

        apiService.getcities(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<CityRespose>() {
                    @Override
                    public void onSuccess(CityRespose userTypeResponse) {
                        progressDialog.dismiss();
                        if (userTypeResponse.getSuccess()) {
                            cityDataList.clear();
                            cityDataList = userTypeResponse.getData();
                            CityData userTypeDatatest = new CityData();
                            userTypeDatatest.setName("Select City");
                            cityDataList.add(0, userTypeDatatest);
                            ArrayAdapter<CityData> adapter = new ArrayAdapter<CityData>(mContext, R.layout.spinner_item_textcolorblck, cityDataList);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            mSpinnerSelectCity.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressDialog.dismiss();
                        Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void callStateList(String id) {
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
                            mSpinnerSelectState.setAdapter(adapter);
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
            case R.id.BtnSubmit:
                if (TextUtils.isEmpty(mEditName.getText().toString())) {
                    mEditName.setError("Enter FullName");
                } else {
                    mEditName.setError(null);
                    if (TextUtils.isEmpty(mEditPincode.getText().toString())) {
                        mEditPincode.setError("Enter PinCode");
                    } else {
                        mEditPincode.setError(null);
                        if (TextUtils.isEmpty(mEditStreetName.getText().toString())) {
                            mEditStreetName.setError("Enter StreetName");
                        } else {
                            mEditStreetName.setError(null);
                            if (TextUtils.isEmpty(mEditEmail.getText().toString())) {
                                mEditEmail.setError("Enter Email");
                            } else {
                                mEditEmail.setError(null);
                                if (isValid(mEditEmail.getText().toString())) {
                                    if (TextUtils.isEmpty(mEditMobile.getText().toString())) {
                                        mEditMobile.setError("Enter Mobile Number");
                                    } else {
                                        mEditMobile.setError(null);
                                        submitaddress(mEditName.getText().toString().trim(), mEditPincode.getText().toString().trim(),
                                                mSpinnerSelectState.getSelectedItem().toString().trim(), mSpinnerSelectState.getSelectedItem().toString().trim(), mEditFlatNo.getText().toString().trim(),
                                                mEditStreetName.getText().toString().trim(), mEditEmail.getText().toString().trim(), mEditMobile.getText().toString().trim());

                                    }
                                } else {
                                    mEditEmail.setError("Enter valid email");
                                }

                            }
                        }
                    }
                }
                break;
        }
    }

    private void submitaddress(String name
            , String pincode, String city, String state, String flatnumber, String streetname, String email, String mobile) {
        PrefUtils.saveToPrefs(mContext, "Address", "TRUE");
        PrefUtils.saveToPrefs(mContext, "ADDname", name);
        PrefUtils.saveToPrefs(mContext, "ADDpincode", pincode);
        PrefUtils.saveToPrefs(mContext, "ADDcity", city);
        PrefUtils.saveToPrefs(mContext, "ADDstate", state);
        PrefUtils.saveToPrefs(mContext, "ADDflatnumber", flatnumber);
        PrefUtils.saveToPrefs(mContext, "ADDstreetname", streetname);
        PrefUtils.saveToPrefs(mContext, "ADDemail", email);
        PrefUtils.saveToPrefs(mContext, "ADDmobile", mobile);
        PrefUtils.saveToPrefs(mContext, "ADDstateID", StateId);
        PrefUtils.saveToPrefs(mContext, "ADDcityID", CityId);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setMessage("Address has been added.");
        alertDialogBuilder.setTitle("Response");
        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        arg0.dismiss();
                        DeliveryAddressActivity.this.finish();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
