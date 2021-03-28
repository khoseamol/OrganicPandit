package com.everlastingseo.organicpandit.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.easebuzz.payment.kit.PWECouponsActivity;
import com.everlastingseo.organicpandit.MainActivity;
import com.everlastingseo.organicpandit.R;
import com.everlastingseo.organicpandit.pojo.preparepaymentgatway.ResponsePrepareForPaymentGateway;

import datamodels.PWEStaticDataModel;

public class PaymentGatewayInitiate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_gateway_initiate);
        ResponsePrepareForPaymentGateway responsePrepareForPaymentGateway = new ResponsePrepareForPaymentGateway();

        Intent intentProceed = new Intent(PaymentGatewayInitiate.this, PWECouponsActivity.class);
        responsePrepareForPaymentGateway = (ResponsePrepareForPaymentGateway) this.getIntent().getSerializableExtra("Data");
        intentProceed.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT); // This is mandatory flag
        intentProceed.putExtra("txnid", responsePrepareForPaymentGateway.getResponse().getData().getData().getPaymentDetails().getTxnid());
        intentProceed.putExtra("amount", Double.valueOf(responsePrepareForPaymentGateway.getResponse().getData().getData().getPaymentDetails().getAmount()));
        intentProceed.putExtra("productinfo", responsePrepareForPaymentGateway.getResponse().getData().getData().getPaymentDetails().getProductinfo());
        intentProceed.putExtra("firstname", responsePrepareForPaymentGateway.getResponse().getData().getData().getPaymentDetails().getFirstname());
        intentProceed.putExtra("email", responsePrepareForPaymentGateway.getResponse().getData().getData().getPaymentDetails().getEmail());
        intentProceed.putExtra("phone", responsePrepareForPaymentGateway.getResponse().getData().getData().getPaymentDetails().getPhone());
        intentProceed.putExtra("key", responsePrepareForPaymentGateway.getResponse().getData().getData().getPaymentDetails().getKey());
        intentProceed.putExtra("udf1", responsePrepareForPaymentGateway.getResponse().getData().getData().getPaymentDetails().getUdf1());
        intentProceed.putExtra("udf2", responsePrepareForPaymentGateway.getResponse().getData().getData().getPaymentDetails().getUdf2());
        intentProceed.putExtra("udf3", responsePrepareForPaymentGateway.getResponse().getData().getData().getPaymentDetails().getUdf3());
        intentProceed.putExtra("udf4", responsePrepareForPaymentGateway.getResponse().getData().getData().getPaymentDetails().getUdf4());
        intentProceed.putExtra("udf5", responsePrepareForPaymentGateway.getResponse().getData().getData().getPaymentDetails().getUdf5());
        intentProceed.putExtra("address1", responsePrepareForPaymentGateway.getResponse().getData().getData().getPaymentDetails().getAddress1());
        intentProceed.putExtra("address2", responsePrepareForPaymentGateway.getResponse().getData().getData().getPaymentDetails().getAddress2());
        intentProceed.putExtra("city", responsePrepareForPaymentGateway.getResponse().getData().getData().getPaymentDetails().getCity());
        intentProceed.putExtra("state", responsePrepareForPaymentGateway.getResponse().getData().getData().getPaymentDetails().getState());
        intentProceed.putExtra("country", responsePrepareForPaymentGateway.getResponse().getData().getData().getPaymentDetails().getCountry());
        intentProceed.putExtra("zipcode", responsePrepareForPaymentGateway.getResponse().getData().getData().getPaymentDetails().getZipcode());
        intentProceed.putExtra("hash", responsePrepareForPaymentGateway.getResponse().getData().getData().getPaymentDetails().getHash());
//      intentProceed.putExtra("unique_id", responsePrepareForPaymentGateway.getResponse().getData().getData().getPaymentDetails().getUnique_id());
        intentProceed.putExtra("pay_mode", "test");
        startActivityForResult(intentProceed, PWEStaticDataModel.PWE_REQUEST_CODE);

//        Intent intentProceed = new Intent(PaymentGatewayInitiate.this, PWECouponsActivity.class);
////        responsePrepareForPaymentGateway = (ResponsePrepareForPaymentGateway) this.getIntent().getSerializableExtra("RegistrationResponseDataData");
//        intentProceed.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT); // This is mandatory flag
//        intentProceed.putExtra("txnid", "TXNID521603374270");
//        intentProceed.putExtra("amount", 850.0);
//        intentProceed.putExtra("productinfo", "ORDERNO001");
//        intentProceed.putExtra("firstname", "amol khose");
//        intentProceed.putExtra("email", "amolkhose15@gmail.com");
//        intentProceed.putExtra("phone", "98687998");
//        intentProceed.putExtra("key", "O1D3HUYIC3");
//        intentProceed.putExtra("udf1", "1");
//        intentProceed.putExtra("udf2", "");
//        intentProceed.putExtra("udf3", "");
//        intentProceed.putExtra("udf4", "");
//        intentProceed.putExtra("udf5", "");
////        intentProceed.putExtra("address1", responsePrepareForPaymentGateway.getResponse().getData().getData().getPaymentDetails().getAddress1());
////        intentProceed.putExtra("address2", responsePrepareForPaymentGateway.getResponse().getData().getData().getPaymentDetails().getAddress2());
////        intentProceed.putExtra("city", responsePrepareForPaymentGateway.getResponse().getData().getData().getPaymentDetails().getCity());
////        intentProceed.putExtra("state", responsePrepareForPaymentGateway.getResponse().getData().getData().getPaymentDetails().getState());
////        intentProceed.putExtra("country", responsePrepareForPaymentGateway.getResponse().getData().getData().getPaymentDetails().getCountry());
////        intentProceed.putExtra("zipcode", responsePrepareForPaymentGateway.getResponse().getData().getData().getPaymentDetails().getZipcode());
//        intentProceed.putExtra("hash", "318292bdd5f97f9cc09b05ca8756b5b82704ec48ee894fe32335deb34d54b90caccb43920d52e98e4474a510d12668c3d1886076261d471833613c87008a92d0");
////      intentProceed.putExtra("unique_id", responsePrepareForPaymentGateway.getResponse().getData().getData().getPaymentDetails().getUnique_id());
//        intentProceed.putExtra("pay_mode", "test");
//        startActivityForResult(intentProceed, PWEStaticDataModel.PWE_REQUEST_CODE);

//        Intent intentProceed = new Intent(PaymentGatewayInitiate.this, PWECouponsActivity.class);
//        intentProceed.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT); // This is mandatory flag
//        intentProceed.putExtra("txnid", "TXNID521603374271");
//        intentProceed.putExtra("amount", 850.0);
//        intentProceed.putExtra("productinfo", "ORDERNO001");
//        intentProceed.putExtra("firstname", "amol khose");
//        intentProceed.putExtra("email", "amolkhose15@gmail.com");
//        intentProceed.putExtra("phone", "98687998");
//        intentProceed.putExtra("key", "O1D3HUYIC3");
//        intentProceed.putExtra("udf1", "1");
//        intentProceed.putExtra("udf2", "");
//        intentProceed.putExtra("udf3", "");
//        intentProceed.putExtra("udf4", "");
//        intentProceed.putExtra("udf5", "");
//        intentProceed.putExtra("hash", "21b5ab36ecabf0c098796eff489652c7a3512b379228609bf0ffc8705106914e4e11131a3ed816f1f292d0c6bd9d2393eabe78eacbe29474614ad28cd6471bd6");
//        intentProceed.putExtra("pay_mode", "test");
//        startActivityForResult(intentProceed, PWEStaticDataModel.PWE_REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (data != null) {
                if (requestCode == PWEStaticDataModel.PWE_REQUEST_CODE) {
                    String result = data.getStringExtra("result");
                    String payment_response = data.getStringExtra("payment_response");
                    try {
                        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
// Handle response here
                    } catch (Exception e) {
// Handle exception here
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}