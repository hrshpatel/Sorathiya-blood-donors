package com.app.sorathiya.blooddonors.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.app.sorathiya.blooddonors.R;
import com.app.sorathiya.blooddonors.utils.CommonUtils;
import com.app.sorathiya.blooddonors.utils.FirebaseHelper;
import com.app.sorathiya.blooddonors.utils.ProgressDialogHandler;

public class RegisterFragment extends Fragment implements View.OnClickListener {

    private TextInputEditText mEdtName;
    private TextInputEditText mEdtBloodGroup;
    private TextInputEditText mEdtContact;
    private TextInputEditText mEdtEmail;
    private TextInputEditText mEdtAddress;
    private Button mBtnRegister;
    private FirebaseHelper mFirebaseHelper;
    private ProgressDialogHandler mProgressDialogUtils;

    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        CommonUtils.closeKeyboard(getActivity());
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeViews(view);
        setListeners();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.frg_register_btn_register:
                validateUserData();
                break;
            case R.id.frg_register_edt_blood_group:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Choose Blood group");

                String[] animals = {"A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"};
                builder.setItems(animals, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                mEdtBloodGroup.setText("A+");
                                break;
                            case 1:
                                mEdtBloodGroup.setText("A-");
                                break;
                            case 2:
                                mEdtBloodGroup.setText("B+");
                                break;
                            case 3:
                                mEdtBloodGroup.setText("B-");
                                break;
                            case 4:
                                mEdtBloodGroup.setText("AB+");
                                break;
                            case 5:
                                mEdtBloodGroup.setText("AB-");
                                break;
                            case 6:
                                mEdtBloodGroup.setText("O+");
                                break;
                            case 7:
                                mEdtBloodGroup.setText("O-");
                                break;
                        }
                    }
                });

                builder.show();

                break;
        }
    }

    private void initializeViews(View view) {

        mFirebaseHelper = new FirebaseHelper(getActivity());
        mFirebaseHelper.initializeDatabase();

        mEdtName = (TextInputEditText) view.findViewById(R.id.frg_register_edt_name);
        mEdtBloodGroup = (TextInputEditText) view.findViewById(R.id.frg_register_edt_blood_group);
        mEdtContact = (TextInputEditText) view.findViewById(R.id.frg_register_edt_contact);
        mEdtEmail = (TextInputEditText) view.findViewById(R.id.frg_register_edt_email);
        mEdtAddress = (TextInputEditText) view.findViewById(R.id.frg_register_edt_address);

        mBtnRegister = (Button) view.findViewById(R.id.frg_register_btn_register);
    }

    public void setListeners() {
        mBtnRegister.setOnClickListener(this);
        mEdtBloodGroup.setOnClickListener(this);
    }

    private void validateUserData() {

        String userName = mEdtName.getText().toString();
        String bloodGroup = mEdtBloodGroup.getText().toString();
        String contact = mEdtContact.getText().toString();
        String email = mEdtEmail.getText().toString();
        String address = mEdtAddress.getText().toString();

        if (CommonUtils.isNullString(userName)) {
            Toast.makeText(getActivity(), R.string.empty_donor_name, Toast.LENGTH_SHORT).show();
        } else if (userName.length() < 2) {
            Toast.makeText(getActivity(), R.string.invalid_name, Toast.LENGTH_SHORT).show();
        } else if (CommonUtils.isNullString(bloodGroup)) {
            Toast.makeText(getActivity(), R.string.empty_blood_group, Toast.LENGTH_SHORT).show();
        } else if (CommonUtils.isNullString(contact)) {
            Toast.makeText(getActivity(), R.string.empty_contact, Toast.LENGTH_SHORT).show();
        } else if (contact.length() < 10) {
            Toast.makeText(getActivity(), R.string.invalid_contact, Toast.LENGTH_SHORT).show();
        } else if (CommonUtils.isNullString(address)) {
            Toast.makeText(getActivity(), R.string.empty_address, Toast.LENGTH_SHORT).show();
        } else {
            if (CommonUtils.isOnline(getActivity())) {
                mFirebaseHelper.registerUser(this, getActivity(), userName, bloodGroup, contact
                        , email, address);
            } else {
                Toast.makeText(getActivity(), "Check internet Connection", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void clearData() {
        mEdtName.getText().clear();
        mEdtBloodGroup.getText().clear();
        mEdtAddress.getText().clear();
        mEdtEmail.getText().clear();
        mEdtContact.getText().clear();

        mEdtAddress.clearFocus();
    }

}
