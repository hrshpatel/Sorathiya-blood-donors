package com.app.sorathiya.blooddonors.mailutils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.app.sorathiya.blooddonors.utils.CommonUtils;
import com.app.sorathiya.blooddonors.utils.ProgressDialogHandler;

public class SendMailAsync extends AsyncTask<String, Integer, Void> {

    private final String userName;
    private ProgressDialogHandler mProgressDialogHandler;
    private Activity mActivity;
    private String receiverMail;

    public SendMailAsync(ProgressDialogHandler mProgressDialogHandler, Activity mActivity
            , String receiverMail, String userName) {
        this.userName = userName;
        this.mProgressDialogHandler = mProgressDialogHandler;
        this.mActivity = mActivity;
        this.receiverMail = receiverMail;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Toast.makeText(mActivity, "Thank you for registration"
                , Toast.LENGTH_SHORT).show();
        mProgressDialogHandler.cancelDialog();
    }

    protected Void doInBackground(String... params) {
        Mail m = new Mail(CommonUtils.USERNAME, CommonUtils.PASSWORD);

        String[] toArr = {receiverMail};
        m.setTo(toArr);
        m.setFrom(CommonUtils.USERNAME);
        m.setSubject("Successful registration for blood donation.");
        m.setBody("Dear " + userName + "\n\n,    Thank you for registering in sorathiya blood donors " +
                "app . Welcome to our app. Your are doing a great social work donating blood please" +
                " keep going on. \n\nThanks,\nRegards\nSorathiya blood donors team");

        try {
            if (!m.send()) {
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(mActivity, "Email was not sent.", Toast.LENGTH_LONG).show();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("MailApp", "Could not send email", e);
            mProgressDialogHandler.cancelDialog();
        }
        return null;
    }
}
