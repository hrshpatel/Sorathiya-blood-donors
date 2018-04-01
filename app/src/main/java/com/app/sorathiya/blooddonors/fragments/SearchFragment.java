package com.app.sorathiya.blooddonors.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.app.sorathiya.blooddonors.R;
import com.app.sorathiya.blooddonors.activity.SearchActivity;
import com.app.sorathiya.blooddonors.adapters.UserListAdapter;
import com.app.sorathiya.blooddonors.model.UserModel;
import com.app.sorathiya.blooddonors.utils.CommonUtils;
import com.app.sorathiya.blooddonors.utils.FirebaseHelper;
import com.app.sorathiya.blooddonors.utils.ProgressDialogHandler;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchFragment extends Fragment implements View.OnClickListener {

    private TextInputEditText mEdtBloodGroup;
    private FirebaseHelper mFirebaseHelper;
    private RecyclerView mRecyclerViewUsers;
    private ProgressDialogHandler mProgressDialogUtils;
    private TextView mTxtNoData;
    private TextView mTxtCount;

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        CommonUtils.closeKeyboard(getActivity());
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.frg_search_edt_blood_group:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeViews(view);
        setListeners();
    }

    private void initializeViews(View view) {

        mFirebaseHelper = new FirebaseHelper(getActivity());
        mFirebaseHelper.initializeDatabase();

        mEdtBloodGroup = (TextInputEditText) view.findViewById(R.id.frg_search_edt_blood_group);
        mRecyclerViewUsers = (RecyclerView) view.findViewById(R.id.recycler_users);
        mRecyclerViewUsers.setLayoutManager(new LinearLayoutManager(getActivity()));

        mTxtNoData = (TextView) view.findViewById(R.id.frg_search_txt_no_data);

        mTxtCount = (TextView) view.findViewById(R.id.frg_search_txt_count);
    }

    public void setListeners() {
        mEdtBloodGroup.setOnClickListener(this);
    }

    private void searchForUserData() {

        if (CommonUtils.isOnline(getActivity())) {

            mProgressDialogUtils = new ProgressDialogHandler(getActivity());
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
                                , getActivity(), mTxtNoData, mRecyclerViewUsers);

                        mRecyclerViewUsers.setAdapter(adapter);

                        mProgressDialogUtils.cancelDialog();
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    databaseError.toException().printStackTrace();
                    Toast.makeText(getActivity(), "Server not responding", Toast.LENGTH_SHORT).show();
                    mProgressDialogUtils.cancelDialog();
                }
            });
        } else {
            Toast.makeText(getActivity(), "Check internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

}
