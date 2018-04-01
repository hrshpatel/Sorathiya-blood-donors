package com.app.sorathiya.blooddonors.utils;

import android.app.ProgressDialog;
import android.content.Context;

public class ProgressDialogHandler {

    private Context mContext;
    private ProgressDialog progressDialog;

    public ProgressDialogHandler(Context mContext) {
        this.mContext = mContext;
    }

    public void setDialog(String title, boolean cancelable) {
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setTitle(title);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(cancelable);
    }

    public void setDialogMessage(String message) {
        progressDialog.setMessage(message);
    }

    public void startDialog() {
        progressDialog.show();
    }

    public void cancelDialog() {
        if (progressDialog.isShowing())
            progressDialog.cancel();
    }

}
