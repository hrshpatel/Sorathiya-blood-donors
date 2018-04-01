package com.app.sorathiya.blooddonors.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.app.sorathiya.blooddonors.fragments.RegisterFragment;
import com.app.sorathiya.blooddonors.mailutils.SendMailAsync;
import com.app.sorathiya.blooddonors.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class FirebaseHelper {
    private final Activity mActivity;
    private FirebaseDatabase database;
    private DatabaseReference newUser;
    private ProgressDialogHandler dialogHandler;
    private DatabaseReference databaseReference;
    private boolean returnValue;
    private ProgressDialogHandler mProgressDialogUtils;

    public FirebaseHelper(Activity activity) {
        this.mActivity = activity;
        dialogHandler = new ProgressDialogHandler(mActivity);
    }

    public void initializeDatabase() {
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
    }

    public void addData(String phone, String email, String firstName, String lastName, String dob, String password) {
        newUser = database.getReference(phone);

        newUser.child("email").setValue(email);
        newUser.child("first_name").setValue(firstName);
        newUser.child("last_name").setValue(lastName);
        newUser.child("dob").setValue(dob);
        newUser.child("password").setValue(password);
        newUser.child("phone").setValue(phone);

    }

    public void registerUser(final RegisterFragment fragment, final Context context, final String userName, String bloodGroup
            , String contact, final String email, String address) {

        mProgressDialogUtils = new ProgressDialogHandler(context);
        mProgressDialogUtils.setDialog("Please Wait", false);
        mProgressDialogUtils.setDialogMessage("Loading...");
        mProgressDialogUtils.startDialog();

        UserModel userModel = new UserModel();
        userModel.setUserName(userName);
        userModel.setUserBloodGroup(bloodGroup);
        userModel.setUserPhone(contact);
        userModel.setUserEmail(email);
        userModel.setUserAddress(address);

        databaseReference.child(bloodGroup).child(CommonUtils.getCurrentDate())
                .setValue(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.e("////////////////", "Complete :: " + task.isSuccessful());
                if (task.isSuccessful()) {
                    fragment.clearData();

                    if (!CommonUtils.isNullString(email)) {
                        new SendMailAsync(mProgressDialogUtils, fragment.getActivity(), email
                                , userName).execute();
                    } else {
                        mProgressDialogUtils.cancelDialog();
                        Toast.makeText(context, "Thank you for registration"
                                , Toast.LENGTH_SHORT).show();
                    }

                } else {
                    mProgressDialogUtils.cancelDialog();
                    Toast.makeText(context, "Server not responding try again later."
                            , Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void validateUserData(final String phone, final String email, final String firstName, final String lastName, final String date, final String password) {
        dialogHandler.setDialog("Signing Up", false);
        dialogHandler.startDialog();
        database.getReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("////////////", "" + dataSnapshot.hasChild(phone));
                if (dataSnapshot.hasChild(phone)) {
                    Toast.makeText(mActivity, "Number Already exists", Toast.LENGTH_SHORT).show();
                    dialogHandler.cancelDialog();
                } else {
                    addData(phone, email, firstName, lastName, date, password);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public FirebaseDatabase getDatabase() {
        return database;
    }

    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }

    public boolean isAvailableOnDatabase(String phoneNumber) {

        returnValue = false;
//        Query query = database.getReference().orderByChild("phone").equalTo(phoneNumber);

//        query.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Log.e("/////////////", "is Available data :: " + dataSnapshot.child("phone").getValue());
//                if ((dataSnapshot.child("phone").getValue()) == null) {
//                    Log.e("/////////////", "is Available :: null value");
//                    returnValue = false;
//                } else {
//                    returnValue = true;
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
        return returnValue;
    }

}
