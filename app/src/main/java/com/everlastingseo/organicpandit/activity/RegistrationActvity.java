package com.everlastingseo.organicpandit.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.everlastingseo.organicpandit.R;
import com.everlastingseo.organicpandit.helper.ApiClient;
import com.everlastingseo.organicpandit.helper.ApiService;
import com.everlastingseo.organicpandit.helper.ApplicationConstatnt;
import com.everlastingseo.organicpandit.helper.CustomProgressDialog;
import com.everlastingseo.organicpandit.helper.PrefUtils;
import com.everlastingseo.organicpandit.pojo.certificate_agency.AgencyData;
import com.everlastingseo.organicpandit.pojo.certificate_agency.CertificateAgencyResponse;
import com.everlastingseo.organicpandit.pojo.citylist.CityData;
import com.everlastingseo.organicpandit.pojo.citylist.CityRespose;
import com.everlastingseo.organicpandit.pojo.contrylist.CountryData;
import com.everlastingseo.organicpandit.pojo.contrylist.CountryResponse;
import com.everlastingseo.organicpandit.pojo.otp.OTPResponse;
import com.everlastingseo.organicpandit.pojo.registrationpojo.RegistrationResponse;
import com.everlastingseo.organicpandit.pojo.statelist.StateData;
import com.everlastingseo.organicpandit.pojo.statelist.StateResponse;
import com.google.gson.JsonObject;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;


public class RegistrationActvity extends AppCompatActivity implements View.OnClickListener {
    final List<String> selectCertificateLists = new ArrayList<>();
    Context mContext;
    TextView mTxtAlreadyLogin, mTxtTitleName;
    ApiService apiService;
    List<CountryData> countryDataList = new ArrayList<>();
    List<StateData> stateDataList = new ArrayList<>();
    List<CityData> cityDataList = new ArrayList<>();
    List<AgencyData> agencyData = new ArrayList<>();
    String contryID = "";
    TextView mTxtCertificationValue;
    String stateID = "";
    String cityID = "";
    String agencyID = "";
    RelativeLayout mRelativeSelectCertificate;
    AdapterView.OnItemSelectedListener listenerCity = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (cityDataList.size() > 0) {

                cityID = cityDataList.get(position).getId();
            } else {

            }


        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    AdapterView.OnItemSelectedListener listenerAgency = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (agencyData.size() > 0) {

                agencyID = agencyData.get(position).getAgencyId();
            } else {

            }


        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    ProgressDialog progressDialog;
    AdapterView.OnItemSelectedListener listenerState = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (stateDataList.size() > 0) {

                if (stateDataList.get(position).getName().equals("Select State")) {
                    String[] arrCity = {"Select City"};

                    ArrayAdapter<String> adapterCity = new ArrayAdapter<String>(mContext, R.layout.spiner_item, arrCity);
                    adapterCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    mSpinnerSelectCity.setAdapter(adapterCity);
                } else {
                    callCityList(stateDataList.get(position).getId());
                }

                stateID = stateDataList.get(position).getId();
            } else {

            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    Button mBtnSignup;
    private EditText mEditFullname;
    private EditText mEditUserName;
    private EditText mEditEmail;
    private EditText mEditMobilNumber;
    private EditText mEditPassword;
    private EditText mEditConfirmPassword;
    private EditText mEditAddress;
    private SearchableSpinner mSpinnerSelectCountry;
    private SearchableSpinner mSpinnerSelectState, mSpinnerSelectCity, mSpinnerSelectAgency;
    AdapterView.OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (countryDataList.size() > 0) {

                if (countryDataList.get(position).getName().equals("Select Country")) {

                } else {
                    callStateList(countryDataList.get(position).getId());

                }
                contryID = countryDataList.get(position).getId();
            } else {

            }


        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_registration);
        bindview();
    }

    private void bindview() {
        mContext = RegistrationActvity.this;
        apiService = ApiClient.getClient(getApplicationContext())
                .create(ApiService.class);
        progressDialog = CustomProgressDialog.ctor(RegistrationActvity.this);

        mTxtAlreadyLogin = (TextView) findViewById(R.id.TxtAlreadyLogin);
        mTxtTitleName = (TextView) findViewById(R.id.TxtTitleName);
        mEditFullname = (EditText) findViewById(R.id.EditFullname);
        mEditUserName = (EditText) findViewById(R.id.EditUserName);
        mEditEmail = (EditText) findViewById(R.id.EditEmail);
        mEditMobilNumber = (EditText) findViewById(R.id.EditMobilNumber);
        mEditPassword = (EditText) findViewById(R.id.EditPassword);
        mEditConfirmPassword = (EditText) findViewById(R.id.EditConfirmPassword);
        mEditAddress = (EditText) findViewById(R.id.EditAddress);
        mSpinnerSelectCountry = (SearchableSpinner) findViewById(R.id.SpinnerSelectCountry);
        mSpinnerSelectState = (SearchableSpinner) findViewById(R.id.SpinnerSelectState);
        mSpinnerSelectCity = (SearchableSpinner) findViewById(R.id.SpinnerSelectCity);
        mSpinnerSelectAgency = (SearchableSpinner) findViewById(R.id.SpinnerSelectAgency);
        mRelativeSelectCertificate = (RelativeLayout) findViewById(R.id.RelativeSelectCertificate);
        mBtnSignup = (Button) findViewById(R.id.BtnSignup);

        mTxtCertificationValue = (TextView) findViewById(R.id.TxtCertificationValue);

        mRelativeSelectCertificate.setOnClickListener(this);
        mSpinnerSelectCountry.setOnItemSelectedListener(listener);
        mSpinnerSelectState.setOnItemSelectedListener(listenerState);
        mSpinnerSelectCity.setOnItemSelectedListener(listenerCity);
        mSpinnerSelectAgency.setOnItemSelectedListener(listenerAgency);
        mBtnSignup.setOnClickListener(this);

        String[] arrState = {"Select State"};
        ArrayAdapter<String> adapterState = new ArrayAdapter<String>(mContext, R.layout.spiner_item, arrState);
        adapterState.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerSelectState.setAdapter(adapterState);

        String[] arrCity = {"Select City"};

        ArrayAdapter<String> adapterCity = new ArrayAdapter<String>(mContext, R.layout.spiner_item, arrCity);
        adapterCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerSelectCity.setAdapter(adapterCity);

        callContryList();
        getAgencyList();
        mTxtTitleName.setText(getIntent().getStringExtra("TitleName") + "  Registration");
        mTxtAlreadyLogin.setOnClickListener(this);


    }

    private void callCityList(String id) {
//        final ProgressDialog progressDialog = CustomProgressDialog.ctor(RegistrationActvity.this);
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
                            ArrayAdapter<CityData> adapter = new ArrayAdapter<CityData>(mContext, R.layout.spiner_item, cityDataList);
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
//        final ProgressDialog progressDialog = CustomProgressDialog.ctor(RegistrationActvity.this);
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
                            ArrayAdapter<StateData> adapter = new ArrayAdapter<StateData>(mContext, R.layout.spiner_item, stateDataList);
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

    private void callContryList() {
        progressDialog.show();

        apiService.getContryList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<CountryResponse>() {
                    @Override
                    public void onSuccess(CountryResponse userTypeResponse) {
                        progressDialog.dismiss();
                        if (userTypeResponse.getSuccess()) {
                            countryDataList.clear();
                            countryDataList = userTypeResponse.getData();
                            CountryData userTypeDatatest = new CountryData();
                            userTypeDatatest.setName("Select Country");
                            countryDataList.add(0, userTypeDatatest);
                            ArrayAdapter<CountryData> adapter = new ArrayAdapter<CountryData>(mContext, R.layout.spiner_item, countryDataList);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            mSpinnerSelectCountry.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressDialog.dismiss();
                        Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private void getAgencyList() {

        apiService.getAgencyList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<CertificateAgencyResponse>() {
                    @Override
                    public void onSuccess(CertificateAgencyResponse userTypeResponse) {
                        if (userTypeResponse.getSuccess()) {
                            agencyData.clear();
                            agencyData = userTypeResponse.getData();
                            AgencyData userTypeDatatest = new AgencyData();
                            userTypeDatatest.setName("Select Certification Agency");
                            agencyData.add(0, userTypeDatatest);
                            ArrayAdapter<AgencyData> adapter = new ArrayAdapter<AgencyData>(mContext, R.layout.spiner_item, agencyData);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            mSpinnerSelectAgency.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.TxtAlreadyLogin) {
            Intent intent = new Intent(RegistrationActvity.this, LoginActvity.class);
            startActivity(intent);
            finish();

        } else if (view.getId() == R.id.RelativeSelectCertificate) {
            callSelectCertificateDialog();
        } else if (view.getId() == R.id.BtnSignup) {
            if (TextUtils.isEmpty(mEditFullname.getText().toString().trim())) {
                mEditFullname.setError("Enter FullName");
            } else {
                mEditFullname.setError(null);
                if (TextUtils.isEmpty(mEditUserName.getText().toString().trim())) {
                    mEditUserName.setError("Enter username");
                } else {
                    mEditUserName.setError(null);
                    if (TextUtils.isEmpty(mEditEmail.getText().toString().trim())) {
                        mEditEmail.setError("Enter email address");
                    } else {
                        mEditEmail.setError(null);
                        if (TextUtils.isEmpty(mEditMobilNumber.getText().toString().trim())) {
                            mEditMobilNumber.setError("Enter mobile number");
                        } else {
                            mEditMobilNumber.setError(null);
                            if (mEditMobilNumber.getText().toString().length() > 10) {
                                mEditMobilNumber.setError("Enter correct mobile number");

                            } else {
                                if (TextUtils.isEmpty(mEditPassword.getText().toString().trim())) {
                                    mEditPassword.setError("Enter password");
                                } else {
                                    mEditPassword.setError(null);
                                    if (TextUtils.isEmpty(mEditConfirmPassword.getText().toString().trim())) {
                                        mEditConfirmPassword.setError("Enter confirm password");
                                    } else {
                                        mEditConfirmPassword.setError(null);
                                        if (mEditPassword.getText().toString().trim().equals(mEditConfirmPassword.getText().toString().trim())) {
                                            if (TextUtils.isEmpty(mEditAddress.getText().toString().trim())) {
                                                mEditAddress.setError("Enter address");
                                            } else {
                                                mEditAddress.setError(null);
                                                if (contryID == null) {
                                                    ApplicationConstatnt.getDialog(mContext, "", "Please select contry");
                                                } else {
                                                    if (stateID == null) {
                                                        ApplicationConstatnt.getDialog(mContext, "", "Please select State");

                                                    } else {
                                                        if (cityID == null) {
                                                            ApplicationConstatnt.getDialog(mContext, "", "Please select City");

                                                        } else {
                                                            if (selectCertificateLists.size() > 0) {
                                                                StringBuilder Cerificatio = new StringBuilder();
                                                                for (String s : selectCertificateLists) {
                                                                    Cerificatio.append(s + ",");
                                                                }
                                                                if (Cerificatio.equals("")) {
                                                                    ApplicationConstatnt.getDialog(mContext, "", "Select Certification");
                                                                } else {

                                                                    if (agencyID == null) {
                                                                        ApplicationConstatnt.getDialog(mContext, "", "Select Certificate Agency");

                                                                    } else {

                                                                        callRegisterApi(mEditFullname.getText().toString().trim(), mEditUserName.getText().toString().trim(),
                                                                                mEditEmail.getText().toString().trim(), mEditMobilNumber.getText().toString().trim(),
                                                                                mEditPassword.getText().toString().trim(), mEditConfirmPassword.getText().toString().trim(),
                                                                                mEditAddress.getText().toString().trim(), contryID, stateID, cityID, Cerificatio.toString(), agencyID);

                                                                    }
                                                                }

                                                            } else {
                                                                ApplicationConstatnt.getDialog(mContext, "", "Please select Certificate");

                                                            }


                                                        }
                                                    }
                                                }
                                            }
                                        } else {
                                            ApplicationConstatnt.getDialog(mContext, "", "Password miss match");
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

    private void callRegisterApi(String fullname, String username, String emailid, final String mobile, String password, String confimpassword, String address, String contryID, String stateID, String cityID, String cerificatio, String agencyID) {
        final ProgressDialog progressDialog = CustomProgressDialog.ctor(RegistrationActvity.this);
        progressDialog.show();

        apiService.getregistration(getIntent().getStringExtra("USERID"), fullname, username, emailid, mobile, password, confimpassword, address, contryID, stateID, cityID, cerificatio, agencyID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<RegistrationResponse>() {
                    @Override
                    public void onSuccess(final RegistrationResponse userTypeResponse) {
                        progressDialog.dismiss();
                        if (userTypeResponse.getSuccess()) {
//

                            final Dialog dialog = new Dialog(mContext);
                            dialog.setCancelable(false);
                            dialog.setContentView(R.layout.otp_dailog);
                            String mask = mobile.replaceAll("\\w(?=\\w{4})", "*");

                            TextView mTxtMobilenumberText = (TextView) dialog.findViewById(R.id.TxtMobilenumberText);
                            mTxtMobilenumberText.setText("The OTP has been sent to" + mask);
                            ImageView mImgclose = dialog.findViewById(R.id.Imgclose);
                            mImgclose.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.dismiss();
                                }
                            });
                            final EditText EditOTP = (EditText) dialog.findViewById(R.id.EditOTP);
                            Button mbtnOTPSumbmit = (Button) dialog.findViewById(R.id.btnOTPSumbmit);
                            mbtnOTPSumbmit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (TextUtils.isEmpty(EditOTP.getText().toString().trim())) {
                                        EditOTP.setError("Enter OTP");
                                    } else {
                                        callOTPVerificationAPI(EditOTP.getText().toString().trim(), mobile, userTypeResponse.getData().getUserId().toString());

                                    }

                                }
                            });

                            dialog.show();


                        } else {
                            ApplicationConstatnt.getDialog(mContext, "", userTypeResponse.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressDialog.dismiss();
                        Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private void callOTPVerificationAPI(String OTP, String mobile, String userid) {
        try {
            JsonObject mainJsonObject = new JsonObject();
            mainJsonObject.addProperty(ApplicationConstatnt.Request_app_version, "1.0");
            JsonObject objAuth = new JsonObject();
            objAuth.addProperty(ApplicationConstatnt.Request_type, "user");
            objAuth.addProperty(ApplicationConstatnt.Request_token, PrefUtils.getFromPrefs(mContext, "userToken", ""));
            mainJsonObject.add(ApplicationConstatnt.Request_Auth, objAuth);
            JsonObject objMethod = new JsonObject();

            JsonObject objParameter = new JsonObject();
            objParameter.addProperty("user_id", userid);
            objParameter.addProperty("otp", OTP);


            objMethod.addProperty(ApplicationConstatnt.Request_name, "ValidateOtp");
            mainJsonObject.add(ApplicationConstatnt.Request_method, objMethod);
            objMethod.add(ApplicationConstatnt.Request_parameters, objParameter);
//                            ApplicationConstatnt.getDialog(mContext, "Response", result);

            sendOtpReuestApiResponse(mainJsonObject);
        } catch (Exception e) {
            e.printStackTrace();
            ApplicationConstatnt.toast(mContext, "Somthing went wrong");

        }

    }

    private void sendOtpReuestApiResponse(JsonObject mainJsonObject) {
        final ProgressDialog progressDialog = CustomProgressDialog.ctor(RegistrationActvity.this);
        progressDialog.show();

        apiService.sendotp(mainJsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<OTPResponse>() {
                    @Override
                    public void onSuccess(OTPResponse loginResponse) {
                        progressDialog.dismiss();

                        if (loginResponse != null) {

                            if (loginResponse.getResponse().getData().getSuccess()) {
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
                                alertDialogBuilder.setMessage(loginResponse.getResponse().getData().getMessage());
                                alertDialogBuilder.setTitle("");
                                alertDialogBuilder.setPositiveButton("yes",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface arg0, int arg1) {
                                                arg0.dismiss();
                                                Intent intent = new Intent(mContext, LoginActvity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        });


                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();
                            } else {
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
                                alertDialogBuilder.setMessage(loginResponse.getResponse().getData().getMessage());
                                alertDialogBuilder.setTitle("");
                                alertDialogBuilder.setPositiveButton("yes",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface arg0, int arg1) {
                                                arg0.dismiss();

                                            }
                                        });


                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();
                            }

                        } else {
                            Toast.makeText(mContext, "Error ", Toast.LENGTH_SHORT).show();

                        }
//                        if (loginResponse.getResponse().getData().getStatus().equals("")) {
//                        } else {
//
//
//                        }


                    }

                    @Override
                    public void onError(Throwable e) {
                        progressDialog.dismiss();
                        Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void callSelectCertificateDialog() {
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.getWindow().setLayout(600, 400);
        dialog.setContentView(R.layout.certificate_select);

        dialog.setCancelable(false);
        ImageView mImageClose = (ImageView) dialog.findViewById(R.id.ImageClose);
        mImageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        Button mBtnSubmit = (Button) dialog.findViewById(R.id.BtnSubmit);


        final CheckBox mCheckNoxNPOP;
        final CheckBox mCheckBoxNOP;
        final CheckBox mCheckBoxPGS;
        final CheckBox mCheckBoxACOS;
        final CheckBox mCheckBoxEU;
        mCheckNoxNPOP = (CheckBox) dialog.findViewById(R.id.CheckNoxNPOP);
        mCheckBoxNOP = (CheckBox) dialog.findViewById(R.id.CheckBoxNOP);
        mCheckBoxPGS = (CheckBox) dialog.findViewById(R.id.CheckBoxPGS);
        mCheckBoxACOS = (CheckBox) dialog.findViewById(R.id.CheckBoxACOS);
        mCheckBoxEU = (CheckBox) dialog.findViewById(R.id.CheckBoxEU);
        CheckBox mCheckbothNPOPandnop = (CheckBox) dialog.findViewById(R.id.CheckbothNPOPandnop);
        mCheckbothNPOPandnop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    selectCertificateLists.add("6");
                } else {
                    if (selectCertificateLists.contains("6")) {
                        selectCertificateLists.remove(("6"));
                    } else {
                    }
                }
            }
        });

        mCheckNoxNPOP.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    selectCertificateLists.add("1");
                } else {
                    if (selectCertificateLists.contains("1")) {
                        selectCertificateLists.remove(("1"));
                    } else {
                    }
                }

            }


        });
        mCheckBoxNOP.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    selectCertificateLists.add("2");
                } else {
                    if (selectCertificateLists.contains("2")) {
                        selectCertificateLists.remove(("2"));
                    } else {
                    }
                }

            }

        });
        mCheckBoxPGS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    selectCertificateLists.add("3");
                } else {
                    if (selectCertificateLists.contains("3")) {
                        selectCertificateLists.remove(("3"));
                    } else {
                    }
                }

            }

        });
        mCheckBoxACOS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    selectCertificateLists.add("4");
                } else {
                    if (selectCertificateLists.contains("4")) {
                        selectCertificateLists.remove(("4"));
                    } else {
                    }
                }

            }

        });
        mCheckBoxEU.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    selectCertificateLists.add("5");
                } else {
                    if (selectCertificateLists.contains("5")) {
                        selectCertificateLists.remove(("5"));
                    } else {
                    }
                }

            }

        });
        mBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (selectCertificateLists.size() > 0) {
                    dialog.dismiss();
                    mTxtCertificationValue.setText("TRUE");
                } else {
                    mTxtCertificationValue.setText(" ");
                    ApplicationConstatnt.getDialog(mContext, " ", "Select Certifications");
                }
            }
        });
        dialog.show();
    }
}

