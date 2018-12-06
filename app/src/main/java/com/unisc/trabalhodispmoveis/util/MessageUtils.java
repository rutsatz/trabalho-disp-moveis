package com.unisc.trabalhodispmoveis.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.unisc.trabalhodispmoveis.service.ContratoService;

import java.util.ArrayList;

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

    public static final void showConfirm(Context context, String msg, final ArrayList<String> dados, final JsonHttpResponseHandler handler) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(msg);
        builder.setTitle("Atenção!");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ContratoService contratoService = new ContratoService();
                contratoService.salvarNovoContrato(dados.get(0), dados.get(1), dados.get(2), dados.get(3), dados.get(4), dados.get(5), handler);
            }
        });
        builder.setNegativeButton("Não", null);
        AlertDialog dialog = builder.create();

        dialog.show();
    }

}
