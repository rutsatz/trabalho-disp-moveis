package com.unisc.trabalhodispmoveis;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class servicosTab extends Activity {

    private TextView userIDtv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicos_tab);

        userIDtv = findViewById(R.id.userIdserv);

        //userIDtv.setText(Integer.toString(MainActivity.getUserId()));
    }

    public void onClickBusca(View view) {
    }
}
