package com.app.sorathiya.blooddonors.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.app.sorathiya.blooddonors.R;
import com.app.sorathiya.blooddonors.fragments.AboutUsFragment;
import com.app.sorathiya.blooddonors.fragments.ContactUsFragment;
import com.app.sorathiya.blooddonors.fragments.HomeFragment;
import com.app.sorathiya.blooddonors.fragments.RegisterFragment;
import com.app.sorathiya.blooddonors.fragments.SearchFragment;
import com.app.sorathiya.blooddonors.utils.CommonUtils;
import com.app.sorathiya.blooddonors.utils.FragmentUtils;

import java.security.Permission;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int REQ_CALL = 2323;
    private Toolbar mToolbar;
    private TextView mToolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbarTitle = (TextView) findViewById(R.id.txt_toolbar_title);
        setSupportActionBar(mToolbar);

        Log.e("///////////", "Internet :: " + CommonUtils.isOnline(this));

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        setToolbarTitle(getString(R.string.app_name));
        setDrawerLayout();

        FragmentUtils.replaceFragment(getSupportFragmentManager(), HomeFragment.newInstance()
                , R.id.frame_layout_content_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CALL_PHONE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, REQ_CALL);
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Do you want to exit?");

            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    MainActivity.super.onBackPressed();
                }
            });

            builder.show();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_register) {
            FragmentUtils.replaceFragment(getSupportFragmentManager()
                    , RegisterFragment.newInstance(), R.id.frame_layout_content_main);
        } else if (id == R.id.nav_home) {
            FragmentUtils.replaceFragment(getSupportFragmentManager()
                    , HomeFragment.newInstance(), R.id.frame_layout_content_main);
        } else if (id == R.id.nav_search) {
            FragmentUtils.replaceFragment(getSupportFragmentManager(),
                    SearchFragment.newInstance(), R.id.frame_layout_content_main);
        } else if (id == R.id.nav_share) {

            try {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                String shareLink = "\nThis app helps you to find blood donors around you " +
                        "and you can also register yourself to donate blood.\n\n";
                shareLink = shareLink + "https://play.google.com/store/apps/details?id=" +
                        getPackageName() + " \n";
                i.putExtra(Intent.EXTRA_TEXT, shareLink);
                startActivity(Intent.createChooser(i, "choose one"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (id == R.id.nav_about) {
            FragmentUtils.replaceFragment(getSupportFragmentManager(), AboutUsFragment.newInstance()
                    , R.id.frame_layout_content_main);
        } else if (id == R.id.nav_contact) {
            FragmentUtils.replaceFragment(getSupportFragmentManager(), ContactUsFragment.newInstance()
                    , R.id.frame_layout_content_main);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQ_CALL) {
            if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                finish();
            }
        }
    }

    private void setDrawerLayout() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, mToolbar
                , R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void setToolbarTitle(String title) {
        mToolbarTitle.setText(title);
    }
}
