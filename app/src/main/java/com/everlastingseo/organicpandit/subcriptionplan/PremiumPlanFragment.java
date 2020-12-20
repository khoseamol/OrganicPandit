package com.everlastingseo.organicpandit.subcriptionplan;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.easebuzz.payment.kit.PWECouponsActivity;
import com.everlastingseo.organicpandit.R;
import com.everlastingseo.organicpandit.activity.LoginActvity;
import com.everlastingseo.organicpandit.helper.ApiClient;
import com.everlastingseo.organicpandit.helper.ApiService;
import com.everlastingseo.organicpandit.helper.ApplicationConstatnt;
import com.everlastingseo.organicpandit.helper.CustomProgressDialog;
import com.everlastingseo.organicpandit.helper.PrefUtils;
import com.everlastingseo.organicpandit.pojo.preparepaymentgatway.ResponsePrepareForPaymentGateway;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import datamodels.PWEStaticDataModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class PremiumPlanFragment extends Fragment {
    Button mBtnPemimumPlan;
ApiService apiService;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_standred_prememun_fragment, container, false);
        bindview(view);
        return view;
    }

    private void bindview(View view) {
        apiService = ApiClient.getClient(getActivity())
                .create(ApiService.class);
        mBtnPemimumPlan = view.findViewById(R.id.BtnPremiMumPlan);
        mBtnPemimumPlan.setText(" " + getResources().getString(R.string.Rs) + " 3500" + " -  Get Started");
        mBtnPemimumPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsonObject mainJsonObject = new JsonObject();
                mainJsonObject.addProperty(ApplicationConstatnt.Request_app_version, "1.0");
                JsonObject objAuth = new JsonObject();
                objAuth.addProperty(ApplicationConstatnt.Request_type, "user");
                objAuth.addProperty(ApplicationConstatnt.Request_token, PrefUtils.getFromPrefs(getActivity(), "userToken", ""));
                mainJsonObject.add(ApplicationConstatnt.Request_Auth, objAuth);
                JsonObject objMethod = new JsonObject();
                objMethod.addProperty(ApplicationConstatnt.Request_name, "AddSubscriptionOrder");

                mainJsonObject.add(ApplicationConstatnt.Request_method, objMethod);
                JsonObject objParameter = new JsonObject();
                objMethod.add(ApplicationConstatnt.Request_parameters, objParameter);
                objParameter.addProperty("user_id", PrefUtils.getFromPrefs(getActivity(), "user_id", ""));
                objParameter.addProperty("user_type_id", PrefUtils.getFromPrefs(getActivity(), "UserTYPE_ID", ""));
                objParameter.addProperty("fullname", PrefUtils.getFromPrefs(getActivity(), "FullName", ""));
                objParameter.addProperty("email_id", PrefUtils.getFromPrefs(getActivity(), "Email", ""));
                objParameter.addProperty("mobile_no", PrefUtils.getFromPrefs(getActivity(), "Mobile", ""));
                objParameter.addProperty("payment_method", "2");
                objParameter.addProperty("subscription_plan_id", "2");
                CallSubcriptionAPi(mainJsonObject);
            }
        });
    }
    private void CallSubcriptionAPi(JsonObject mainJsonObject) {
        final ProgressDialog progressDialog = CustomProgressDialog.ctor(getActivity());
        progressDialog.show();

        apiService.SubscriptionPaymentResponse(mainJsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ResponsePrepareForPaymentGateway>() {
                    @Override
                    public void onSuccess(ResponsePrepareForPaymentGateway loginResponse) {
                        progressDialog.dismiss();

                        if (loginResponse.getResponse().getData().getSuccess() == true) {
//                            ApplicationConstatnt.getDialog(getActivity(), "", loginResponse.getResponse().getData().getMessage());
                            Intent intentProceed = new Intent(getActivity(), PWECouponsActivity.class);
                            intentProceed.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT); // This is mandatory flag
                            intentProceed.putExtra("txnid", loginResponse.getResponse().getData().getData().getPaymentDetails().getTxnid());
                            intentProceed.putExtra("amount", Double.valueOf(loginResponse.getResponse().getData().getData().getPaymentDetails().getAmount()));
                            intentProceed.putExtra("productinfo", loginResponse.getResponse().getData().getData().getPaymentDetails().getProductinfo());
                            intentProceed.putExtra("firstname", loginResponse.getResponse().getData().getData().getPaymentDetails().getFirstname());
                            intentProceed.putExtra("email", loginResponse.getResponse().getData().getData().getPaymentDetails().getEmail());
                            intentProceed.putExtra("phone", loginResponse.getResponse().getData().getData().getPaymentDetails().getPhone());
                            intentProceed.putExtra("key", loginResponse.getResponse().getData().getData().getPaymentDetails().getKey());
                            intentProceed.putExtra("udf1", loginResponse.getResponse().getData().getData().getPaymentDetails().getUdf1());
                            intentProceed.putExtra("udf2", loginResponse.getResponse().getData().getData().getPaymentDetails().getUdf2());
                            intentProceed.putExtra("udf3", loginResponse.getResponse().getData().getData().getPaymentDetails().getUdf3());
                            intentProceed.putExtra("udf4", loginResponse.getResponse().getData().getData().getPaymentDetails().getUdf4());
                            intentProceed.putExtra("udf5", loginResponse.getResponse().getData().getData().getPaymentDetails().getUdf5());
                            intentProceed.putExtra("hash", loginResponse.getResponse().getData().getData().getPaymentDetails().getHash());
                            intentProceed.putExtra("pay_mode", loginResponse.getResponse().getData().getData().getPaymentDetails().getPay_mode());
                            startActivityForResult(intentProceed, PWEStaticDataModel.PWE_REQUEST_CODE);
                        } else {
                            ApplicationConstatnt.getDialog(getActivity(), "", loginResponse.getResponse().getData().getMessage());

                        }


                    }

                    @Override
                    public void onError(Throwable e) {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (data != null) {
                if (requestCode == PWEStaticDataModel.PWE_REQUEST_CODE) {
                    String result = data.getStringExtra("result");
                    String payment_response = data.getStringExtra("payment_response");
                    try {
                        try {
                            JsonObject mainJsonObject = new JsonObject();
                            mainJsonObject.addProperty(ApplicationConstatnt.Request_app_version, "1.0");
                            JsonObject objAuth = new JsonObject();
                            objAuth.addProperty(ApplicationConstatnt.Request_type, "user");
                            objAuth.addProperty(ApplicationConstatnt.Request_token, PrefUtils.getFromPrefs(getActivity(), "userToken", ""));
                            mainJsonObject.add(ApplicationConstatnt.Request_Auth, objAuth);
                            JsonObject objMethod = new JsonObject();
                            JsonObject jsonObject = new JsonParser().parse(payment_response).getAsJsonObject();
                            objMethod.addProperty(ApplicationConstatnt.Request_name, "SubscriptionPaymentResponse");
                            mainJsonObject.add(ApplicationConstatnt.Request_method, objMethod);
                            objMethod.add(ApplicationConstatnt.Request_parameters, jsonObject);
//                            ApplicationConstatnt.getDialog(mContext, "Response", result);

                            sendSubcriptionApiResponse(mainJsonObject);
                        } catch (Exception e) {
                            e.printStackTrace();
                            ApplicationConstatnt.toast(getContext(), "Somthing went wrong");

                        }

                    } catch (Exception e) {
                        ApplicationConstatnt.toast(getActivity(), e.getMessage());

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void sendSubcriptionApiResponse(JsonObject mainJsonObject) {
        final ProgressDialog progressDialog = CustomProgressDialog.ctor(getActivity());
        progressDialog.show();

        apiService.sendPaymentresponse(mainJsonObject)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ResponsePrepareForPaymentGateway>() {
                    @Override
                    public void onSuccess(ResponsePrepareForPaymentGateway loginResponse) {
                        progressDialog.dismiss();

                        if(loginResponse!=null){
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                            alertDialogBuilder.setMessage(loginResponse.getResponse().getData().getMessage());
                            alertDialogBuilder.setTitle("Response");
                            alertDialogBuilder.setPositiveButton("yes",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface arg0, int arg1) {
                                            PrefUtils.saveToPrefs(getActivity(), "Login", "false");
                                            PrefUtils.removeFromPrefs(getActivity(), "UserTYPE_ID");
                                            PrefUtils.removeAllFromPrefs(getActivity());
                                            Intent intent = new Intent(getActivity(), LoginActvity.class);
                                            startActivity(intent);
                                        }
                                    });
                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                        }else {
                            Toast.makeText(getActivity(), "Error ", Toast.LENGTH_SHORT).show();

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
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

}