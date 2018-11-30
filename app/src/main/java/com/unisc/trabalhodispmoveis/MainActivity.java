package com.unisc.trabalhodispmoveis;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TabHost;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity {

    int userId;
    boolean primeiroLogin;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        userId = intent.getIntExtra("id_login", -1);
        primeiroLogin = intent.getBooleanExtra("primeiroLogin", true);

        Log.d("teste", "MainActivity userId " + userId);
        Log.d("teste", "MainActivity primeiroLogin " + primeiroLogin);

        TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);

        TabHost.TabSpec tabPerfil = tabHost.newTabSpec("abaPerfil");
        TabHost.TabSpec tabCliente = tabHost.newTabSpec("abaCliente");
        TabHost.TabSpec tabContato = tabHost.newTabSpec("abaContato");
        TabHost.TabSpec tabServico = tabHost.newTabSpec("abaServico");

        tabPerfil.setIndicator("Meu Perfil");
        tabCliente.setIndicator("Meus Clientes");
        tabContato.setIndicator("Meus Contatos");
        tabServico.setIndicator("Serviços");

        tabPerfil.setContent(new Intent(this, MeuPerfilActivity.class));
        tabCliente.setContent(new Intent(this, MeusClientesActivity.class));
        tabContato.setContent(new Intent(this, MeusContatosActivity.class));
        tabServico.setContent(new Intent(this, servicosTab.class));

        tabHost.addTab(tabPerfil);
        tabHost.addTab(tabCliente);
        tabHost.addTab(tabContato);
        tabHost.addTab(tabServico);
        if (!primeiroLogin) {
            tabHost.getTabWidget().getChildAt(1).setVisibility(View.GONE);
            tabHost.getTabWidget().getChildAt(2).setVisibility(View.GONE);
            tabHost.getTabWidget().getChildAt(3).setVisibility(View.GONE);
        }

        context = this;
    }


}
