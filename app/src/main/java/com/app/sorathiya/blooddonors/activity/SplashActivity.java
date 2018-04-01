package com.app.sorathiya.blooddonors.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.app.sorathiya.blooddonors.R;
import com.app.sorathiya.blooddonors.utils.CommonUtils;
import com.app.sorathiya.blooddonors.utils.FirebaseHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashActivity extends AppCompatActivity {

    private Handler mPostHandler;
    private Runnable mRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        FirebaseHelper mFirebaseHelper = new FirebaseHelper(this);
        mFirebaseHelper.initializeDatabase();

        mFirebaseHelper.getDatabaseReference().child("password")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        CommonUtils.PASSWORD = dataSnapshot.getValue().toString();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        mPostHandler = new Handler();

        mRunnable = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                finish();
                startActivity(intent);
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPostHandler.postDelayed(mRunnable, 5000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPostHandler.removeCallbacks(mRunnable);
    }

    @Override
    public void onBackPressed() {
    }
}
