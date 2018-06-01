package com.skynetsoftware.trungson.ui.views;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by hoaph on 10/6/2017.
 */

public class ProgressDialogUtils {
    
    public static ProgressDialog createDialog(Context context, String message, boolean flagCancel) {
        ProgressDialog progressDialog = new ProgressDialog(context);

        progressDialog.setMessage(message);
        progressDialog.setCancelable(flagCancel);
        return progressDialog;
    }
}
