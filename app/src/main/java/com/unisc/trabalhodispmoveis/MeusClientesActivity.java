package com.unisc.trabalhodispmoveis;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MeusClientesActivity extends Activity {

    private TextView userIDtv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meus_clientes);

        userIDtv = findViewById(R.id.userIdcliente);

        userIDtv.setText(Integer.toString(MainActivity.getUserId()));
    }
}
