package com.app.sorathiya.blooddonors.fragments;


import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.sorathiya.blooddonors.R;
import com.app.sorathiya.blooddonors.utils.CommonUtils;
import com.app.sorathiya.blooddonors.utils.ProgressDialogHandler;

import static android.app.Activity.RESULT_OK;

public class ContactUsFragment extends Fragment implements View.OnClickListener {

    private static final int REQ_EMAIL = 7878;
    private TextInputEditText mEdtMessage;
    private TextInputEditText mEdtName;
    private TextInputEditText mEdtContactNumber;
    private TextInputEditText mEdtEmail;
    private Button mBtnSendMessage;
    private ImageView mImgContact;
    private TextView mTxtContactUs;
    private ProgressDialogHandler dialogHandler;

    public static ContactUsFragment newInstance() {
        ContactUsFragment fragment = new ContactUsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        CommonUtils.closeKeyboard(getActivity());
        return inflater.inflate(R.layout.fragment_contact_us, container, false);
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
            case R.id.frg_contact_us_btn_register:
                validateMessageData();
                break;
            case R.id.frg_contact_us_img_contact:
                showCallContactDialog();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("///////////", "ACTIVITY RES" + resultCode);

        if (resultCode == RESULT_OK) {
            Toast.makeText(getActivity()
                    , "We have got your mail we will contact you shortly"
                    , Toast.LENGTH_SHORT).show();
        }
    }

    private void showCallContactDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Do you want to call " + getString(R.string.contact_person));

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.fromParts("tel", getString(R.string.contact_person_number)
                        , null));
                if (ActivityCompat.checkSelfPermission(getActivity()
                        , Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getActivity(), "Need to give permission from settings for calling"
                            , Toast.LENGTH_LONG).show();
                    return;
                }
                startActivity(intent);
            }
        });

        builder.show();

    }

    private void initializeViews(View view) {
        mEdtName = (TextInputEditText) view.findViewById(R.id.frg_contact_us_edt_name);
        mEdtContactNumber = (TextInputEditText) view.findViewById(R.id.frg_contact_us_edt_contact);
        mEdtEmail = (TextInputEditText) view.findViewById(R.id.frg_contact_us_edt_email);
        mEdtMessage = (TextInputEditText) view.findViewById(R.id.frg_contact_us_edt_msg);

        mTxtContactUs = (TextView) view.findViewById(R.id.frg_contact_us_txt_contact_us);

        String contactUsString = getString(R.string.contact_us_text)
                + getString(R.string.contact_person) + getString(R.string.contact_email) + getString(R.string.contact_person_number);
        mTxtContactUs.setText(contactUsString);

        mBtnSendMessage = (Button) view.findViewById(R.id.frg_contact_us_btn_register);
        mImgContact = (ImageView) view.findViewById(R.id.frg_contact_us_img_contact);
    }

    private void setListeners() {
        mBtnSendMessage.setOnClickListener(this);
        mImgContact.setOnClickListener(this);
    }

    private void validateMessageData() {

        CommonUtils.closeKeyboard(getActivity());
        String userName = mEdtName.getText().toString();
        String contact = mEdtContactNumber.getText().toString();
        String email = mEdtEmail.getText().toString();
        String message = mEdtMessage.getText().toString();

        if (CommonUtils.isNullString(userName)) {
            Toast.makeText(getActivity(), R.string.empty_name, Toast.LENGTH_SHORT).show();
        } else if (CommonUtils.isNullString(contact)) {
            Toast.makeText(getActivity(), R.string.empty_contact, Toast.LENGTH_SHORT).show();
        } else if (contact.length() < 10) {
            Toast.makeText(getActivity(), R.string.invalid_contact, Toast.LENGTH_SHORT).show();
        } else if (CommonUtils.isNullString(email)) {
            Toast.makeText(getActivity(), R.string.empty_email, Toast.LENGTH_SHORT).show();
        } else if (CommonUtils.checkEmailAddress(email)) {
            Toast.makeText(getActivity(), R.string.invalid_email, Toast.LENGTH_SHORT).show();
        } else if (CommonUtils.isNullString(message)) {
            Toast.makeText(getActivity(), R.string.empty_message, Toast.LENGTH_SHORT).show();
        } else {
            if (CommonUtils.isOnline(getActivity())) {
                Intent intent = new Intent(Intent.ACTION_SEND);

                intent.setData(Uri.parse("mailto:"));
                intent.setType("text/plain");
                intent.setClassName("com.google.android.gm"
                        , "com.google.android.gm.ComposeActivityGmail");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"sorathiyablooddonors@gmail.com"});
                intent.putExtra(Intent.EXTRA_SUBJECT, "Blood donation inquiry");
                intent.putExtra(Intent.EXTRA_TEXT, mEdtMessage.getText().toString()
                        + " \n\n my contact number is :: " + mEdtContactNumber.getText().toString()
                        + " \n\n and email is :: " + mEdtEmail.getText().toString());

                intent.setType("message/rfc822");

                mEdtMessage.getText().clear();
                mEdtContactNumber.getText().clear();
                mEdtEmail.getText().clear();
                mEdtName.getText().clear();

                mEdtMessage.clearFocus();

                startActivityForResult(Intent.createChooser(intent, "Choose email client"), REQ_EMAIL);
            } else {
                Toast.makeText(getActivity(), "Check internet Connection", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
