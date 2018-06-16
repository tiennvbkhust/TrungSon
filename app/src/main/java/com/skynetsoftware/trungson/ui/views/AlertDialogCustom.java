package com.skynetsoftware.trungson.ui.views;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skynetsoftware.trungson.R;
import com.skynetsoftware.trungson.interfaces.ICallback;


/**
 * Created by Huy on 7/4/2017.
 */

public class AlertDialogCustom {
    public static AlertDialog dialogMessage(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.dialog_lost_connect, null, false);
        TextView button = (TextView) view.findViewById(R.id.tv_ok);
        builder.setView(view);
        builder.setCancelable(false);
        final AlertDialog alertDialog = builder.create();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        alertDialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return alertDialog;
    }

    public static AlertDialog showDialogSuccess(Context context, String shop) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.dialog_success, null, false);
        Button btnOk = (Button) view.findViewById(R.id.btnOK);

        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return alertDialog;
    }

    public static AlertDialog showDialogSuccess(Context context, int resourcePhoto, String text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.dialog_success, null, false);
        Button btnOk = (Button) view.findViewById(R.id.btnOK);
        TextView textView = (TextView) view.findViewById(R.id.tvMessage);
        ImageView img = (ImageView) view.findViewById(R.id.photo);
        img.setImageResource(resourcePhoto);
        textView.setText(text);
        builder.setView(view);
        final AlertDialog alertDialog = builder.create();
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return alertDialog;
    }

}
