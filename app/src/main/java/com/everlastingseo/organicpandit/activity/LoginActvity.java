package com.everlastingseo.organicpandit.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.everlastingseo.organicpandit.BuildConfig;
import com.everlastingseo.organicpandit.R;
import com.everlastingseo.organicpandit.helper.ApiClient;
import com.everlastingseo.organicpandit.helper.ApiService;
import com.everlastingseo.organicpandit.helper.ApplicationConstatnt;
import com.everlastingseo.organicpandit.helper.CustomProgressDialog;
import com.everlastingseo.organicpandit.helper.PrefUtils;
import com.everlastingseo.organicpandit.pojo.login.LoginResponse;
import com.everlastingseo.organicpandit.pojo.usertypr.UserTypeData;
import com.everlastingseo.organicpandit.pojo.usertypr.UserTypeResponse;

import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class LoginActvity extends AppCompatActivity implements View.OnClickListener {
    Button mBtnLogin;
    Context mContext;
    TextView mTxtDontAccountHere;
    String UserTypeID = "";
    ApiService apiService;
    List<UserTypeData> userTypeData = new ArrayList<>();
    AdapterView.OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            UserTypeID = userTypeData.get(position).getId();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    private Spinner mLoginUserType;
    private EditText mEditUserName;
    private EditText mEditPassword;
    private TextView mTxtForgotPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(R.layout.activity_login_actvity);
        if (savedInstanceState == null) {
            bindview();
        }


    }

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }


    private void bindview() {
        mContext = LoginActvity.this;
        apiService = ApiClient.getClient(getApplicationContext())
                .create(ApiService.class);
        mBtnLogin = (Button) findViewById(R.id.BtnLogin);
        mTxtDontAccountHere = (TextView) findViewById(R.id.TxtDontAccountHere);
        mLoginUserType = (Spinner) findViewById(R.id.LoginUserType);
        mEditUserName = (EditText) findViewById(R.id.EditUserName);
        mEditPassword = (EditText) findViewById(R.id.EditPassword);
        mTxtForgotPassword = (TextView) findViewById(R.id.TxtForgotPassword);
      CheckUPdate();
        callUserTypeData();


        mTxtDontAccountHere.setOnClickListener(this);
        mBtnLogin.setOnClickListener(this);
        mLoginUserType.setOnItemSelectedListener(listener);
        mTxtForgotPassword.setOnClickListener(this);

    }

    void checkinternetconnection() {
        Intent intent = new Intent(mContext, Activity_InterNetConncetion.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.BtnLogin:
                if (TextUtils.isEmpty(mEditUserName.getText().toString().trim())) {
                    mEditUserName.setError("Enter Username");
                } else {
                    mEditUserName.setError(null);
                    if (TextUtils.isEmpty(mEditPassword.getText().toString().trim())) {
                        mEditPassword.setError("Enter Password");
                    } else {
                        mEditPassword.setError(null);
                    }
                    if (isNetworkAvailable(mContext)) {
                        calllogin(mEditUserName.getText().toString().trim(), mEditPassword.getText().toString().trim(), UserTypeID);
                    } else {
                        checkinternetconnection();
                    }
                }
                break;
            case R.id.TxtDontAccountHere:
                if (isNetworkAvailable(mContext)) {
                    Intent intent = new Intent(mContext, PreRegistrationActvity.class);
                    startActivity(intent);
                } else {
                    checkinternetconnection();
                }

                break;
            case R.id.TxtForgotPassword:
                Intent intent1 = new Intent(mContext, ActivityForgotActivity.class);
                startActivity(intent1);
                break;
        }
    }

    private void calllogin(String username, String password, String userTypeID) {
        final ProgressDialog progressDialog = CustomProgressDialog.ctor(LoginActvity.this);
        progressDialog.show();
        apiService.getLogin(username, password, userTypeID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<LoginResponse>() {
                    @Override
                    public void onSuccess(LoginResponse loginResponse) {
                        progressDialog.dismiss();
                        if (loginResponse.getSuccess()) {
                            PrefUtils.saveToPrefs(mContext, "Login", "true");
                            PrefUtils.saveToPrefs(mContext, "user_id", loginResponse.getData().getUserId());
                            PrefUtils.saveToPrefs(mContext, "FullName", loginResponse.getData().getFullname());
                            PrefUtils.saveToPrefs(mContext, "Email", loginResponse.getData().getEmailId());
                            PrefUtils.saveToPrefs(mContext, "Mobile", loginResponse.getData().getMobileNo());
                            PrefUtils.saveToPrefs(mContext, "UserTYPE_ID", UserTypeID);
                            PrefUtils.saveToPrefs(mContext, "userToken", loginResponse.getUserToken());
                            PrefUtils.saveToPrefs(mContext, "Is_subscription", loginResponse.getData().getIs_subscription());
                            Intent intent = new Intent(mContext, DashBoardActivity.class);
                            startActivity(intent);
                        } else {
                            ApplicationConstatnt.getDialog(mContext, "Respose", loginResponse.getMessage());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressDialog.dismiss();
                        Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void callUserTypeData() {

        if (isNetworkAvailable(mContext)) {
            final ProgressDialog progressDialog = CustomProgressDialog.ctor(LoginActvity.this);
            progressDialog.show();

            apiService.getUserTypeList()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSingleObserver<UserTypeResponse>() {
                        @Override
                        public void onSuccess(UserTypeResponse userTypeResponse) {
                            progressDialog.dismiss();
                            if (userTypeResponse.getSuccess()) {
                                userTypeData = userTypeResponse.getData();
                                UserTypeData userTypeDatatest = new UserTypeData();
                                userTypeDatatest.setName("Select User");
                                userTypeData.add(0, userTypeDatatest);
                                ArrayAdapter<UserTypeData> adapter = new ArrayAdapter<UserTypeData>(mContext, R.layout.spiner_item, userTypeData);
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                mLoginUserType.setAdapter(adapter);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            progressDialog.dismiss();
                            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            checkinternetconnection();
        }


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        System.exit(0);
    }
    private void CheckUPdate() {
        VersionChecker versionChecker = new VersionChecker();
        try
        {   String appVersionName = BuildConfig.VERSION_NAME;
            String mLatestVersionName = versionChecker.execute().get();
            if(!appVersionName.equals(mLatestVersionName)){
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
                alertDialog.setTitle("Please update your app");
                alertDialog.setCancelable(false);
                alertDialog.setMessage("This app version is no longer supported. Please update your app from the Play Store.");
                alertDialog.setPositiveButton("UPDATE NOW", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        final String appPackageName = getPackageName();
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                        }
                    }
                });
                alertDialog.show();
            }

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
    @SuppressLint("StaticFieldLeak")
    public class VersionChecker extends AsyncTask<String, String, String> {
        private String newVersion;
        @Override
        protected String doInBackground(String... params) {

            try {
                newVersion = Jsoup.connect("https://play.google.com/store/apps/details?id="+getPackageName())
                        .timeout(30000)
                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                        .referrer("http://www.google.com")
                        .get()
                        .select(".hAyfc .htlgb")
                        .get(7)
                        .ownText();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return newVersion;
        }
    }
}
