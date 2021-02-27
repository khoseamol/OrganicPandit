package com.everlastingseo.organicpandit.helper;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.everlastingseo.organicpandit.model.SelectCertificateList;

import java.util.ArrayList;
import java.util.List;

public class ApplicationConstatnt {
//    public static final String BASE_URL = "http://organicpandit.com/";
    public static final String BASE_URL = "http://demo.organicpandit.com/";
    public static final String BP_CART_TYPE_ID_1 = "BP";
    public static final String EOI_CART_TYPE_ID_2 = "EOI";
    public static final String ES_CART_TYPE_ID_3 = "ES";
    public static final String PAYMNET_METHOD = "2";

    public static final String Request_app_version = "app_version";
    public static final String Request_Auth = "auth";
    public static final String Request_type = "type";
    public static final String Request_token = "token";
    public static final String Request_method = "method";
    public static final String Request_name = "name";
    public static final String Request_parameters = "parameters";
    public static final String SUBCRIPTION_MESSGE = "You have not subscribed to our services. To get more excitement features please subscribed our services....! ";
    public static final String SUBCRIPTION_TITLE = "Subscription Required.";


    public static List<SelectCertificateList> selectCertificateLists = new ArrayList<>();

    public static void getDialog(Context mContext, String title, String Message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setMessage(Message);
        alertDialogBuilder.setTitle(title);
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

    public static void toast(Context mContext, String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
    }

    public static List<SelectCertificateList> getSelectCertificateLists() {
        SelectCertificateList list = new SelectCertificateList();
        list.setId("1");
        list.setName("NPOP");

        SelectCertificateList list1 = new SelectCertificateList();
        list1.setId("2");
        list1.setName("NOP");

        SelectCertificateList list2 = new SelectCertificateList();
        list2.setId("3");
        list2.setName("PGS");

        SelectCertificateList list3 = new SelectCertificateList();
        list3.setId("4");
        list3.setName("ACOS");

        SelectCertificateList list4 = new SelectCertificateList();
        list4.setId("5");
        list4.setName("EU");
        SelectCertificateList list5 = new SelectCertificateList();
        list5.setId("6");
        list5.setName("Both NPOP &amp; NOP");
        selectCertificateLists.add(list);
        selectCertificateLists.add(list1);
        selectCertificateLists.add(list2);
        selectCertificateLists.add(list3);
        selectCertificateLists.add(list4);
        selectCertificateLists.add(list5);

        return selectCertificateLists;
    }
}
