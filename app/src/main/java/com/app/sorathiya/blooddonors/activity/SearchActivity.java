package com.app.sorathiya.blooddonors.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.app.sorathiya.blooddonors.R;
import com.app.sorathiya.blooddonors.adapters.UserListAdapter;
import com.app.sorathiya.blooddonors.model.UserModel;
import com.app.sorathiya.blooddonors.utils.CommonUtils;
import com.app.sorathiya.blooddonors.utils.FirebaseHelper;
import com.app.sorathiya.blooddonors.utils.ProgressDialogHandler;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputEditText mEdtBloodGroup;
    private FirebaseHelper mFirebaseHelper;
    private RecyclerView mRecyclerViewUsers;
    private ProgressDialogHandler mProgressDialogUtils;
    private Toolbar mToolbar;
    private TextView mToolbarTitle;
    private TextView mTxtNoData;
    private TextView mTxtCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initializeViews();
        setListeners();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.frg_search_edt_blood_group:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Choose Blood group");

                String[] animals = {"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};
                builder.setItems(animals, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                mEdtBloodGroup.setText("A+");
                                searchForUserData();
                                break;
                            case 1:
                                mEdtBloodGroup.setText("A-");
                                searchForUserData();
                                break;
                            case 2:
                                mEdtBloodGroup.setText("B+");
                                searchForUserData();
                                break;
                            case 3:
                                mEdtBloodGroup.setText("B-");
                                searchForUserData();
                                break;
                            case 4:
                                mEdtBloodGroup.setText("AB+");
                                searchForUserData();
                                break;
                            case 5:
                                mEdtBloodGroup.setText("AB-");
                                searchForUserData();
                                break;
                            case 6:
                                mEdtBloodGroup.setText("O+");
                                searchForUserData();
                                break;
                            case 7:
                                mEdtBloodGroup.setText("O-");
                                searchForUserData();
                                break;
                        }
                    }
                });

                builder.show();

                break;
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    private void initializeViews() {

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbarTitle = (TextView) findViewById(R.id.txt_toolbar_title);
        mTxtCount = (TextView) findViewById(R.id.frg_search_txt_count);
        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        mToolbarTitle.setText(getString(R.string.app_name));

        mFirebaseHelper = new FirebaseHelper(this);
        mFirebaseHelper.initializeDatabase();

        mEdtBloodGroup = (TextInputEditText) findViewById(R.id.frg_search_edt_blood_group);
        mRecyclerViewUsers = (RecyclerView) findViewById(R.id.recycler_users);
        mRecyclerViewUsers.setLayoutManager(new LinearLayoutManager(this));

        mTxtNoData = (TextView) findViewById(R.id.frg_search_txt_no_data);

        if (getIntent().hasExtra("blood_group")) {
            mEdtBloodGroup.setText(getIntent().getStringExtra("blood_group"));
            searchForUserData();
        }
    }

    public void setListeners() {
        mEdtBloodGroup.setOnClickListener(this);
    }

    private void searchForUserData() {

        if (CommonUtils.isOnline(this)) {
            mProgressDialogUtils = new ProgressDialogHandler(this);
            mProgressDialogUtils.setDialog("Please Wait", false);
            mProgressDialogUtils.setDialogMessage("Loading...");
            mProgressDialogUtils.startDialog();
            mFirebaseHelper.getDatabaseReference().addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (dataSnapshot != null) {
                        ArrayList<UserModel> userModelArrayList = new ArrayList<UserModel>();
                        for (DataSnapshot userShot : dataSnapshot.child(mEdtBloodGroup.getText().toString())
                                .getChildren()) {
                            userModelArrayList.add(userShot.getValue(UserModel.class));
                        }
                        mTxtCount.setText("" + userModelArrayList.size());
                        UserListAdapter adapter = new UserListAdapter(userModelArrayList
                                , SearchActivity.this, mTxtNoData, mRecyclerViewUsers);

                        mRecyclerViewUsers.setAdapter(adapter);
                    }

                    mProgressDialogUtils.cancelDialog();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    databaseError.toException().printStackTrace();
                    Toast.makeText(SearchActivity.this, "Server not responding", Toast.LENGTH_SHORT).show();
                    mProgressDialogUtils.cancelDialog();
                }
            });

        } else {
            Toast.makeText(this, "Check internet Connection", Toast.LENGTH_SHORT).show();
        }
    }
}