package com.everlastingseo.organicpandit.activity;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.everlastingseo.organicpandit.R;

public class SocialMediaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_media);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Social-Media");
        RelativeLayout relativeLayout;
        relativeLayout=findViewById(R.id.LayoutImgFacebook);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                facebookopen();
            }
        });
        RelativeLayout LayoutImginsta;
        LayoutImginsta=findViewById(R.id.LayoutImginsta);
        LayoutImginsta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openWhatsApp();
            }
        });
        RelativeLayout LayoutImgtweeter;
        LayoutImgtweeter=findViewById(R.id.LayoutImgtweeter);
        LayoutImgtweeter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dial();
            }
        });

    }

    private void facebookopen() {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/OrganicPandit-105258831458131"));
            startActivity(intent);
        } catch(Exception e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com/appetizerandroid")));
        }
    }

    void dial() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("Comming soon");
        alertDialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });
        alertDialog.show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    private void openWhatsApp() {
        String smsNumber = "9145347218";
        boolean isWhatsappInstalled = whatsappInstalledOrNot("com.whatsapp");
        if (isWhatsappInstalled) {

            Intent sendIntent = new Intent("android.intent.action.MAIN");
            sendIntent.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.Conversation"));
            sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators(smsNumber) + "@s.whatsapp.net");//phone number without "+" prefix

            startActivity(sendIntent);
        } else {
            Uri uri = Uri.parse("market://details?id=com.whatsapp");
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            Toast.makeText(this, "WhatsApp not Installed",
                    Toast.LENGTH_SHORT).show();
            startActivity(goToMarket);
        }
    }

    private boolean whatsappInstalledOrNot(String uri) {
        PackageManager pm = getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }
}