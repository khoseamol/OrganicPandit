package com.everlastingseo.organicpandit;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.everlastingseo.organicpandit.activity.AboutUsActivity;
import com.everlastingseo.organicpandit.activity.ContactUsActivity;
import com.everlastingseo.organicpandit.activity.LoginActvity;
import com.everlastingseo.organicpandit.activity.SocialMediaActivity;
import com.everlastingseo.organicpandit.addproduct.UserAddProductActivity;
import com.everlastingseo.organicpandit.helper.ApplicationConstatnt;
import com.everlastingseo.organicpandit.helper.PrefUtils;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public FrameLayout mframelayout;
    TextView mTxtName, mtxtEmail;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        bindview();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        mTxtName = header.findViewById(R.id.TxtName);
        mTxtName.setText(PrefUtils.getFromPrefs(MainActivity.this, "FullName", ""));
        mTxtName = header.findViewById(R.id.TxtName);
        mtxtEmail = header.findViewById(R.id.txtEmail);
        mtxtEmail.setText(PrefUtils.getFromPrefs(MainActivity.this, "Email", ""));
    }

    private void bindview() {
        mframelayout = findViewById(R.id.framelayout);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_aboutus) {
            Intent intent = new Intent(MainActivity.this, AboutUsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_share) {

            Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Insert Subject here");
            String app_url = " https://play.google.com/store/apps/details?id=organicpandit";
            shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, app_url);
            startActivity(Intent.createChooser(shareIntent, "Share via"));
        } else if (id == R.id.nav_contact) {
            Intent intent = new Intent(MainActivity.this, ContactUsActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_logout) {
            PrefUtils.saveToPrefs(MainActivity.this, "Login", "false");
            PrefUtils.removeFromPrefs(MainActivity.this, "UserTYPE_ID");
            PrefUtils.removeAllFromPrefs(MainActivity.this);
            Intent intent = new Intent(MainActivity.this, LoginActvity.class);
            startActivity(intent);

        } else if (id == R.id.nav_social) {
            Intent intent = new Intent(MainActivity.this, SocialMediaActivity.class);
            startActivity(intent);

        } else if (id == R.id.addproduct) {

            if (PrefUtils.getFromPrefs(MainActivity.this, "UserTYPE_ID", "").equals("1") ||
                    PrefUtils.getFromPrefs(MainActivity.this, "UserTYPE_ID", "").equals("5")) {
                Intent intent = new Intent(MainActivity.this, UserAddProductActivity.class);
                startActivity(intent);
            } else {
                ApplicationConstatnt.getDialog(MainActivity.this, "", "You are not Authorised this service");

            }

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
