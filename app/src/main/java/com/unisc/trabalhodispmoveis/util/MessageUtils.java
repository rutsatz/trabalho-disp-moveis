package com.unisc.trabalhodispmoveis.util;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.Toast;

public class MessageUtils {

    public static final void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static final void showAlert(Context context, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(msg);
        builder.setTitle("Atenção!");
        builder.setPositiveButton("OK", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
